package com.shah.fse.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class EngineerDetails implements Serializable {
	
	private EngineerId engineerId;
	
	//private String userId;
	
	private String associateId;
	
	//private String userName;
	
	private String emailAddress;
	
	private String mobileNo;
	
	private Timestamp addedDate;
	
	private Timestamp updatedDate;
	
	private List<EngineerSkillset> skillSets = new ArrayList<>();
	
	public EngineerDetails() {
		super();
	}

	public EngineerDetails(EngineerId engineerId) {
		super();
		this.engineerId = engineerId;
	}

	public String getUserId() {
		return engineerId != null ? engineerId.getUserId() : null;
	}

	public void setUserId(String userId) {
		if(engineerId == null) {
			engineerId = new EngineerId();
		}
		engineerId.setUserId(userId);
	}
	
	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}
	
	public String getUserName() {
		return engineerId != null ? engineerId.getUserName() : null;
	}

	public void setUserName(String userName) {
		if(engineerId == null) {
			engineerId = new EngineerId();
		}
		engineerId.setUserName(userName);
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public Timestamp getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Timestamp addedDate) {
		this.addedDate = addedDate;
	}
	
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	public List<EngineerSkillset> getSkillSets() {
		return skillSets;
	}

	public void setSkillSets(List<EngineerSkillset> skillSets) {
		this.skillSets = skillSets;
	}
	
}
