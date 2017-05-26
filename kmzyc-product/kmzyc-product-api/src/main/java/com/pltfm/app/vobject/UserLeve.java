package com.pltfm.app.vobject;

import java.io.Serializable;

public class UserLeve implements Serializable{
	private Long levelId;//N_LEVEL_ID
	private String code;//等级编号
	private String levelName;//等级名称
	public Long getLevelId() {
		return levelId;
	}
	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}
