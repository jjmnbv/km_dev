package com.pltfm.app.vobject;
/**
 * 返回结果信息类
 * @author humy
 * @since 2013-7-10
 */
public class Message implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4324381058140626114L;
	/**
	 * 模块
	 */
	private String module;
	/**
	 * 返回代码
	 */
	private int code;
	/**
	 * 返回标题
	 */
	private String title;
	/**
	 * 返回内容
	 */
	private String content;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
}
