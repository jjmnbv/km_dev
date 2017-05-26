/** 
 * project : B2C 康美健康商城
 * module : elasticsearch-analysis-dynamic-synonym 
 * package : com.bellszhu.elasticsearch.plugin.synonym.analysis 
 * date: 2016年9月1日下午2:31:48 
 * Copyright (c) 2016, KM@km.com All Rights Reserved. 
 */ 
package com.bellszhu.elasticsearch.plugin.synonym.analysis;

import java.io.Reader;
import java.sql.ResultSet;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.synonym.SynonymMap;
import org.elasticsearch.common.logging.ESLogger;
import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.env.Environment;

import com.km.search.synonym.utils.DBConnection;

/** 
 * 数据库同义词数据加载类
 * @author   KM 
 * @date   2016年9月1日 下午2:31:48 
 * @version   
 * @see       
 */
public class DBSynonymFile implements SynonymFile{
	public static ESLogger logger = Loggers.getLogger("dynamic-synonym");

	private String format;

	private boolean expand;

	private Analyzer analyzer;

	private Environment env;
	
	private static final String querySynomymSql = "select t.keyword,t.unidir_word,t.synonym_word from search_synonym_dict_t t";
	
	private static final int PAGE_SIZE = 200;
	

	public DBSynonymFile(Environment env, Analyzer analyzer, boolean expand,
			String format) {
		this.analyzer = analyzer;
		this.expand = expand;
		this.format = format;
		this.env = env;
		
	}

	@Override
	public SynonymMap reloadSynonymMap(){
		DBSynonymParser parser = new DBSynonymParser(true, expand, analyzer);		
		DBConnection dbConn = null;
		try {
			dbConn = DBConnection.openConnection();
			int count = dbConn.queryCount(querySynomymSql);
			if(count > 0 ){
				int pages = count%PAGE_SIZE > 0 ? count/PAGE_SIZE + 1 : count/PAGE_SIZE;
				for(int page = 1 ; page<=pages ; page++){
					ResultSet resultSet = dbConn.queryPage(querySynomymSql, page, PAGE_SIZE);
					while(resultSet.next()){
						String keyWord = resultSet.getString(1);
						String unidirWord = resultSet.getString(2);
						String synonymWrod = resultSet.getString(3);
						parser.parse(keyWord, unidirWord, synonymWrod);										
					}				
				}	
			}else{
				//确保分词器都使用上同义词过滤器对象
				parser.parse("1","1",null);	
			}
			return parser.build();
		} catch (Exception e) {
			logger.error("reload DB synonym error!", e);
			throw new IllegalArgumentException("could not reload DB synonyms to build synonyms", e);
		} finally {
			if(dbConn != null){
				dbConn.close();
			}
			
		}		
		
		
//		try {
//	
//			
//		} catch (Exception e) {
//	
//			throw new IllegalArgumentException("could not reload local synonyms file to build synonyms", e);
//		}

	}



	@Override
	public boolean isNeedReloadSynonymMap() {
		return false;
	}

	@Override
	public Reader getReader() {	
		return null;
	}

}
  