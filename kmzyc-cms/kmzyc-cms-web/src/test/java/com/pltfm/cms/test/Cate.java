package com.pltfm.cms.test;

import java.util.List;

public class Cate {

	private String name;
	
	private List<Cate> sublist;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Cate> getSublist() {
		return sublist;
	}

	public void setSublist(List<Cate> sublist) {
		this.sublist = sublist;
	}
}
