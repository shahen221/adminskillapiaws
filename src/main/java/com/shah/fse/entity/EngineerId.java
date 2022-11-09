package com.shah.fse.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class EngineerId implements Serializable {
	
	private String userId;
	
	private String userName;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
