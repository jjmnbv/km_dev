/** 
 * project : B2C 康美健康商城
 * module : elasticsearch-analysis-dynamic-synonym 
 * package : com.bellszhu.elasticsearch.plugin.synonym.analysis 
 * date: 2016年9月1日下午2:59:05 
 * Copyright (c) 2016, KM@km.com All Rights Reserved. 
 */
package com.bellszhu.elasticsearch.plugin.synonym.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.CharsRef;
import org.apache.lucene.util.CharsRefBuilder;

import com.km.search.synonym.utils.StringUtils;

/**
 * 数据库同义词数据处理类
 * 
 * @author KM
 * @date 2016年9月1日 下午2:59:05
 * @version
 * @see
 */
public class DBSynonymParser extends SynonymMap.Parser {
	private final boolean expand;

	public DBSynonymParser(boolean dedup, boolean expand, Analyzer analyzer) {
		super(dedup, analyzer);
		this.expand = expand;
	}

	@Override
	public void parse(Reader in) throws IOException, ParseException {
		LineNumberReader br = new LineNumberReader(in);
		try {
			addInternal(br);
		} catch (IllegalArgumentException e) {
			ParseException ex = new ParseException("Invalid synonym rule at line " + br.getLineNumber(), 0);
			ex.initCause(e);
			throw ex;
		} finally {
			br.close();
		}
	}
	
	public void parse(String keyword,String unidirWords,String synonymWords) throws IOException, ParseException {
		try {
			String unidirWordStr = null;
			String synonmyWordStr = null;			
			if(StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(unidirWords) && StringUtils.isBlank(synonymWords)){
				unidirWords = unidirWords.replaceAll("[,，；;]", ",");
				unidirWordStr = keyword + "=>" + unidirWords + "," + keyword;
			}else if(StringUtils.isNotBlank(keyword) && StringUtils.isBlank(unidirWords) && StringUtils.isNotBlank(synonymWords)){
				synonymWords = synonymWords.replaceAll("[,，；;]", ",");
				synonmyWordStr = keyword + "," + synonymWords;
			}else if(StringUtils.isNotBlank(keyword) && StringUtils.isNotBlank(unidirWords) && StringUtils.isNotBlank(synonymWords)){
				unidirWords = unidirWords.replaceAll("[,，；;]", ",");
				unidirWordStr = keyword + "=>" + unidirWords;
				synonymWords = synonymWords.replaceAll("[,，；;]", ",");
				synonmyWordStr = keyword + "," + synonymWords;
			}
			if(StringUtils.isNotBlank(unidirWordStr)){
				addInternal(unidirWordStr);
			}
			if(StringUtils.isNotBlank(synonmyWordStr)){
				addInternal(synonmyWordStr);
			}
		} catch (IllegalArgumentException e) {
			ParseException ex = new ParseException("Invalid synonym rule keyword -> " + keyword, 0);
			ex.initCause(e);
			throw ex;
		}
	}
	
	

	private void addInternal(String line) throws IOException {	
		String sides[] = split(line, "=>");
		if (sides.length > 1) { // explicit mapping
			if (sides.length != 2) {
				throw new IllegalArgumentException("more than one explicit mapping specified on the same line");
			}
			String inputStrings[] = split(sides[0], ",");
			CharsRef[] inputs = new CharsRef[inputStrings.length];
			for (int i = 0; i < inputs.length; i++) {
				inputs[i] = analyze(unescape(inputStrings[i]).trim(), new CharsRefBuilder());
			}

			String outputStrings[] = split(sides[1], ",");
			CharsRef[] outputs = new CharsRef[outputStrings.length];
			for (int i = 0; i < outputs.length; i++) {
				outputs[i] = analyze(unescape(outputStrings[i]).trim(), new CharsRefBuilder());
			}
			// these mappings are explicit and never preserve original
			for (int i = 0; i < inputs.length; i++) {
				for (int j = 0; j < outputs.length; j++) {
					add(inputs[i], outputs[j], false);
				}
			}
		} else {
			String inputStrings[] = split(line, ",");
			CharsRef[] inputs = new CharsRef[inputStrings.length];
			for (int i = 0; i < inputs.length; i++) {
				inputs[i] = analyze(unescape(inputStrings[i]).trim(), new CharsRefBuilder());
			}
			if (expand) {
				// all pairs
				for (int i = 0; i < inputs.length; i++) {
					for (int j = 0; j < inputs.length; j++) {
						if (i != j) {
							add(inputs[i], inputs[j], true);
						}
					}
				}
			} else {
				// all subsequent inputs map to first one; we also add inputs[0]
				// here
				// so that we "effectively" (because we remove the original
				// input and
				// add back a synonym with the same text) change that token's
				// type to
				// SYNONYM (matching legacy behavior):
				for (int i = 0; i < inputs.length; i++) {
					add(inputs[i], inputs[0], false);
				}
			}
		}
	}

	private void addInternal(BufferedReader in) throws IOException {
		String line = null;
		while ((line = in.readLine()) != null) {
			if (line.length() == 0 || line.charAt(0) == '#') {
				continue; // ignore empty lines and comments
			}

			// TODO: we could process this more efficiently.
			String sides[] = split(line, "=>");
			if (sides.length > 1) { // explicit mapping
				if (sides.length != 2) {
					throw new IllegalArgumentException("more than one explicit mapping specified on the same line");
				}
				String inputStrings[] = split(sides[0], ",");
				CharsRef[] inputs = new CharsRef[inputStrings.length];
				for (int i = 0; i < inputs.length; i++) {
					inputs[i] = analyze(unescape(inputStrings[i]).trim(), new CharsRefBuilder());
				}

				String outputStrings[] = split(sides[1], ",");
				CharsRef[] outputs = new CharsRef[outputStrings.length];
				for (int i = 0; i < outputs.length; i++) {
					outputs[i] = analyze(unescape(outputStrings[i]).trim(), new CharsRefBuilder());
				}
				// these mappings are explicit and never preserve original
				for (int i = 0; i < inputs.length; i++) {
					for (int j = 0; j < outputs.length; j++) {
						add(inputs[i], outputs[j], false);
					}
				}
			} else {
				String inputStrings[] = split(line, ",");
				CharsRef[] inputs = new CharsRef[inputStrings.length];
				for (int i = 0; i < inputs.length; i++) {
					inputs[i] = analyze(unescape(inputStrings[i]).trim(), new CharsRefBuilder());
				}
				if (expand) {
					// all pairs
					for (int i = 0; i < inputs.length; i++) {
						for (int j = 0; j < inputs.length; j++) {
							if (i != j) {
								add(inputs[i], inputs[j], true);
							}
						}
					}
				} else {
					// all subsequent inputs map to first one; we also add
					// inputs[0] here
					// so that we "effectively" (because we remove the original
					// input and
					// add back a synonym with the same text) change that
					// token's type to
					// SYNONYM (matching legacy behavior):
					for (int i = 0; i < inputs.length; i++) {
						add(inputs[i], inputs[0], false);
					}
				}
			}
		}
	}

	private static String[] split(String s, String separator) {
		ArrayList<String> list = new ArrayList<>(2);
		StringBuilder sb = new StringBuilder();
		int pos = 0, end = s.length();
		while (pos < end) {
			if (s.startsWith(separator, pos)) {
				if (sb.length() > 0) {
					list.add(sb.toString());
					sb = new StringBuilder();
				}
				pos += separator.length();
				continue;
			}

			char ch = s.charAt(pos++);
			if (ch == '\\') {
				sb.append(ch);
				if (pos >= end)
					break; // ERROR, or let it go?
				ch = s.charAt(pos++);
			}

			sb.append(ch);
		}

		if (sb.length() > 0) {
			list.add(sb.toString());
		}

		return list.toArray(new String[list.size()]);
	}

	private String unescape(String s) {
		if (s.indexOf("\\") >= 0) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++) {
				char ch = s.charAt(i);
				if (ch == '\\' && i < s.length() - 1) {
					sb.append(s.charAt(++i));
				} else {
					sb.append(ch);
				}
			}
			return sb.toString();
		}
		return s;
	}
}
