package com.shah.fse.dto;

import lombok.Getter;

@Getter
public enum SkillSet {
	HTML_CSS_JAVASCRIPT(1, "HTML-CSS-JAVASCRIPT", "technical"),
	ANGULAR(2, "ANGULAR", "technical"),
	REACT(3, "REACT", "technical"),
	SPRING(4, "SPRING", "technical"),
	RESTFUL(5, "RESTFUL", "technical"),
	HIBERNATE(6, "HIBERNATE", "technical"),
	GIT(7, "GIT", "technical"),
	DOCKER(8, "DOCKER", "technical"),
	JENKINS(9, "JENKINS", "technical"),
	AWS(10, "AWS", "technical"),
	SPOKEN(11, "SPOKEN", "non-technical"),
	COMMUNICATION(12, "COMMUNICATION", "non-technical"),
	APTITUDE(13, "APTITUDE", "non-technical")
	
;

	private int skillId;
	private String skillName;
	private String skillType;
	
	SkillSet(int skillId, String skillName, String skillType) {
		this.skillId = skillId;
		this.skillName = skillName;
		this.skillType = skillType;
	}
	
	SkillSet() {
	}
	
	
}
