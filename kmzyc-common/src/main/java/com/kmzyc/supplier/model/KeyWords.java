package com.kmzyc.supplier.model;


import java.io.Serializable;

public class KeyWords implements Serializable{
	//关键字
	private String keyWords;
	//替换字
	private String repWords;
	//有效状态
	private int status;
	//关键字类型 1提交：2注册
	private int wordsType;
	
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getRepWords() {
		return repWords;
	}
	public void setRepWords(String repWords) {
		this.repWords = repWords;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getWordsType() {
		return wordsType;
	}
	public void setWordsType(int wordsType) {
		this.wordsType = wordsType;
	}
	
	@Override
	public String toString() {
		return "KeyWords [keyWords=" + keyWords
				+ ", repWords=" + repWords + ", status=" + status
				+ ", wordsType=" + wordsType + "]";
	}
}
