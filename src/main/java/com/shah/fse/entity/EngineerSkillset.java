package com.shah.fse.entity;

import java.io.Serializable;
import java.sql.Timestamp;

@SuppressWarnings("serial")
public class EngineerSkillset implements Serializable {
	
	private Integer skillId;
	
	private String skillName;
	
	private Integer skillScore;
	
	private String skillType;
	
	private Timestamp addedDate;
	
	private Timestamp updatedDate;

	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Integer getSkillScore() {
		return skillScore;
	}

	public void setSkillScore(Integer skillScore) {
		this.skillScore = skillScore;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
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
	
}
