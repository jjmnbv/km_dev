package com.pltfm.app.bean;


import java.util.Map;

import com.pltfm.app.vobject.BaseEntity;

/**
 * 
 * @author xkj
 *
 */
public class ResultMessage implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8454077455195429662L;
	private BaseEntity baseEntity; //返回对象
	private Object object; //返回对象
	private Object object2; //返回对象2
	private Long id;  //对象ID
	private Boolean isSuccess = false;   //true:操作成功;flase:操作失败
	private Integer mark = 1;  //标记
	private String resultCode;   //信息码
	private String message;   //信息内容
	@SuppressWarnings("rawtypes")
	private Map map;
	public BaseEntity getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(BaseEntity baseEntity) {
		this.baseEntity = baseEntity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Object getObject2() {
		return object2;
	}

	public void setObject2(Object object2) {
		this.object2 = object2;
	}

}
