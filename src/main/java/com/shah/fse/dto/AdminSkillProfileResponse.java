package com.shah.fse.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSkillProfileResponse {

	private String status;
	private String statusText;
	private List<String> errorMessages = new  ArrayList<>();
	private List<EngineerSkillProfile> engineerSkillProfiles = new ArrayList<>();
}
