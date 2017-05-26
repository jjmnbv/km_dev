package com.pltfm.app.thread;

public class CacheThread extends Thread{
	Object object;
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public CacheThread(	Object object){
		this.object = object;
	}
	
	public void run(){
		
	}
}
