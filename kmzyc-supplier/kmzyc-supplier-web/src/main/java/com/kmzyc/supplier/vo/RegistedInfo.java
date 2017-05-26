package com.kmzyc.supplier.vo;


import java.io.Serializable;

public class RegistedInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private int regPoint;//注册积分
	private int mailValidPoint;//邮箱验证积分
	private String mailLink;//验证邮箱地址
	private String mail;
	public int getRegPoint() {
		return regPoint;
	}
	public void setRegPoint(int regPoint) {
		this.regPoint = regPoint;
	}
	public int getMailValidPoint() {
		return mailValidPoint;
	}
	public void setMailValidPoint(int mailValidPoint) {
		this.mailValidPoint = mailValidPoint;
	}
	public String getMailLink() {
		return mailLink;
	}
	public void setMailLink(String mailLink) {
		this.mailLink = mailLink;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
}
