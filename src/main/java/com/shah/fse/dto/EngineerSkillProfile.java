package com.shah.fse.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class EngineerSkillProfile implements Serializable {
	
	private String associateId;
	private String userName;
	private String emailAddress;
	private String mobileNo;
	private List<EngineerSkill> skills = new ArrayList<>();
	private String _id;
	private String profileStatus;
	
}
