package com.shah.fse.exception;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class AdminSkillProfileException extends RuntimeException {
	
	private String errorMessage;
	
	private Integer userId;
	
	private Throwable error;

	public AdminSkillProfileException(String errorMessage, Throwable error) {
		super();
		this.errorMessage = errorMessage;
		this.error = error;
	}

	public AdminSkillProfileException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	public AdminSkillProfileException(String errorMessage, Integer userId) {
		super();
		this.errorMessage = errorMessage;
		this.userId = userId;
	}
	
	
}
