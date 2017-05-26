package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class BaseBean implements Serializable {
	private static final long serialVersionUID = 3832212849595688436L;
	/**
	 *唯一性标示
	 */
	protected Long id;
	/**
	 * 名称
	 */
	protected String name;
	/**
	 * 创建时间
	 */
	protected Date createTime;
	/**
	 * 创建人
	 */
	protected Integer createUser = 0;
	/**
	 * 创建人姓名
	 */
	protected String createUserName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
