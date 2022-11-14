package com.shah.fse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shah.fse.dto.AdminSkillProfileResponse;
import com.shah.fse.dto.EngineerSkillProfile;
import com.shah.fse.exception.AdminSkillProfileException;
import com.shah.fse.service.AdminSkillProfileService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/skill-tracker/api/v1/admin")
public class AdminSkillAPIController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminSkillAPIController.class);

	@Autowired
	AdminSkillProfileService adminSkillProfileService;

	@GetMapping("/{criteria}/{criteriaValue}")
	//@CrossOrigin(origins = {"http://localhost:4200"})
	public AdminSkillProfileResponse searchProfile(@PathVariable("criteria") String criteria, @PathVariable("criteriaValue") String criteriaValue) {
		LOGGER.info("Enter AdminSkillAPIController::searchProfile");
		AdminSkillProfileResponse adminSkillProfileResponse = new AdminSkillProfileResponse();
		List<EngineerSkillProfile> engineerSkillProfiles = this.adminSkillProfileService.searchProfiles(criteria, criteriaValue);
		adminSkillProfileResponse.setEngineerSkillProfiles(engineerSkillProfiles);
		adminSkillProfileResponse.setStatus("Success");
		adminSkillProfileResponse.setStatusText("Search Engineer Skill Profile(s) Successful");
		LOGGER.info("Exit AdminSkillAPIController::searchProfile");
		return adminSkillProfileResponse;
	}
	
	@PostMapping("/adduserprofile")
	public AdminSkillProfileResponse addEngineerSkillProfile(@RequestBody EngineerSkillProfile engineerSkillProfile) {
		LOGGER.info("Enter AdminSkillAPIController::addEngineerSkillProfile");
		AdminSkillProfileResponse adminSkillProfileResponse = new AdminSkillProfileResponse();
		engineerSkillProfile = this.adminSkillProfileService.addEngineerSkillProfile(engineerSkillProfile);
		adminSkillProfileResponse.setStatus("Success");
		adminSkillProfileResponse.setStatusText("Added Engineer Skill Profile Successfully");
		adminSkillProfileResponse.getEngineerSkillProfiles().add(engineerSkillProfile);
		LOGGER.info("Exit AdminSkillAPIController::addEngineerSkillProfile");
		return adminSkillProfileResponse;
	}
	
	@ExceptionHandler(value = AdminSkillProfileException.class)
	public AdminSkillProfileResponse handleError(AdminSkillProfileException adminSkillProfileException) {
		LOGGER.info("Enter AdminSkillAPIController::handleError");
		AdminSkillProfileResponse adminSkillProfileResponse = new AdminSkillProfileResponse();
		adminSkillProfileResponse.setStatus("Failure");
		adminSkillProfileResponse.setStatusText("Failure");
		adminSkillProfileResponse.getErrorMessages().add(adminSkillProfileException.getErrorMessage());
		LOGGER.info("Exit AdminSkillAPIController::handleError");
		return adminSkillProfileResponse;
	}
}
