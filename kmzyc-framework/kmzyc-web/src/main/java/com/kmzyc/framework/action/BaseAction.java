//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.kmzyc.framework.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kmzyc.framework.fileupload.Upload;
import com.kmzyc.framework.page.Pagination;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	static Logger logger = LoggerFactory.getLogger(BaseAction.class);
	public Pagination pagintion;
	protected int page = 1;
	protected Map<String, String> searchKeyword;
	private File upFile;
	private String upFileFileName;

	public BaseAction() {
	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = ServletActionContext.getRequest();

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException var3) {
			logger.error("",var3);
		}

		return request;
	}

	public HttpServletResponse getResponse() {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		return response;
	}

	public HttpSession getSession() {
		return this.getRequest().getSession();
	}

	public ServletContext getServletContext() {
		return ServletActionContext.getServletContext();
	}

	public String getRealyPath(String path) {
		return this.getServletContext().getRealPath(path);
	}

	public Pagination getPagination(int pagecode, int numperpage) {
		return new Pagination(this.page, pagecode, numperpage, this.searchKeyword);
	}

	public Pagination getPagination() {
		return new Pagination(this.page, this.searchKeyword);
	}

	public String uploadFile(long fileMaximumSize, String[] allowTypes, String savePath) {
		return Upload.uploadFile(this.upFile, this.upFileFileName, fileMaximumSize, allowTypes, savePath);
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Pagination getPagintion() {
		return this.pagintion;
	}

	public void setPagintion(Pagination pagintion) {
		this.pagintion = pagintion;
	}

	public Map<String, String> getSearchKeyword() {
		return this.searchKeyword;
	}

	public void setSearchKeyword(Map<String, String> searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public File getUpFile() {
		return this.upFile;
	}

	public void setUpFile(File upFile) {
		this.upFile = upFile;
	}

	public String getUpFileFileName() {
		return this.upFileFileName;
	}

	public void setUpFileFileName(String upFileFileName) {
		this.upFileFileName = upFileFileName;
	}
}
