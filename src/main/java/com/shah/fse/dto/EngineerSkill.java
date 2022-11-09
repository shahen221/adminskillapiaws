package com.shah.fse.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class EngineerSkill implements Serializable {
	
	private String skillName;
	private String skillScore;
	private String skillType;
	private Integer skillId;
}
