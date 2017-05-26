package com.kmzyc.framework.sensitive;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.km.search.commons.trie.DTrie;

/**
 * 敏感词过滤器
 * @author river
 *
 */
@Component
public class SensitiveWordFilter {
	
	@Resource(name = "dicManager")
	private DicManager dicManager;
	
	/**
	 * 
	 * @param source  将要被过滤的字符串
	 * @param type   敏感词类型
	 * @return true:包含敏感词；false：不包含
	 */
	public boolean doFilt(String source, SensitiveType type) {
		DTrie trie = dicManager.getDic(type);
		char[] charArray = source.toCharArray();
		int start = 0;
		for (int i=1; i<=charArray.length; i++) {
			int match = trie.guessWord(charArray, start, i);
			// 成词
			if (match == DTrie.ONE_A_WORD) {
				return true;
			}
			// 不成词(非前缀)
			else if (match == DTrie.NOT_A_WORD) {
				start++;
			} 
		}
		return false;
	}
	
	/**
	 * 
	 * @param source 将要被过滤的字符串
	 * @param type	敏感词类型
	 * @return  返回包含的敏感词
	 */
	public String getSensitiveWord(String source, SensitiveType type) {
		DTrie trie = dicManager.getDic(type);
		char[] charArray = source.toCharArray();
		int start = 0;
		for (int i=1; i<=charArray.length; i++) {
			int match = trie.guessWord(charArray, start, i);
			// 成词
			if (match == DTrie.ONE_A_WORD) {
				return new String(charArray, start, i-start);
			}
			// 不成词(非前缀)
			else if (match == DTrie.NOT_A_WORD) {
				start++;
			} 
		}
		return null;
	}

	public DicManager getDicManager() {
		return dicManager;
	}

	public void setDicManager(DicManager dicManager) {
		this.dicManager = dicManager;
	}
}
