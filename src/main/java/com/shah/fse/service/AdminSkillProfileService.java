package com.shah.fse.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.shah.fse.dto.EngineerSkill;
import com.shah.fse.dto.EngineerSkillProfile;
import com.shah.fse.dto.SkillSet;
import com.shah.fse.entity.EngineerDetails;
import com.shah.fse.entity.EngineerId;
import com.shah.fse.entity.EngineerSkillset;
import com.shah.fse.exception.AdminSkillProfileException;
import com.shah.fse.repository.AdminSkillProfileRepository;

@Service
public class AdminSkillProfileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminSkillProfileService.class);

	@Autowired
	AdminSkillProfileRepository adminSkillProfileRepository;
	
	public List<EngineerDetails> save(EngineerDetails engineerDetails ) {
		List<EngineerDetails> engineerSkillProfiles = adminSkillProfileRepository.getEngineerSkillProfiles();
		engineerSkillProfiles.add(engineerDetails);
		adminSkillProfileRepository.save(engineerSkillProfiles);
		List<EngineerDetails> engineerProfilesFromCache = this.adminSkillProfileRepository.getEngineerSkillProfiles();
		System.out.println("engineerProfilesFromCache ::::save() "+ engineerProfilesFromCache.size());
		for(EngineerDetails engineerDetails1: engineerProfilesFromCache) {
			System.out.println("engineer name:: "+engineerDetails1.getUserName());
			System.out.println("engineer associateid:: "+engineerDetails1.getAssociateId());
		}
		return engineerSkillProfiles;
	}
	
	//@HystrixCommand(fallbackMethod = "defaultProfile")
	public List<EngineerSkillProfile> searchProfiles(String criteria, String criteriaValue) {
		LOGGER.info("Enter AdminSkillProfileService::searchProfiles");
		List<EngineerSkillProfile> engineerSkillProfiles = null;
		List<EngineerDetails> engineerProfiles = null;
		List<EngineerDetails> engineerProfilesFromCache = this.adminSkillProfileRepository.getEngineerSkillProfiles();
		System.out.println("engineerProfilesFromCache ::::searchProfiles() "+ engineerProfilesFromCache.size());
		for(EngineerDetails engineerDetails1: engineerProfilesFromCache) {
			System.out.println("engineer name:: "+engineerDetails1.getUserName());
			System.out.println("engineer associateid:: "+engineerDetails1.getAssociateId());
		}
		try {
			switch(criteria) {
			
				case "username": 
									if(!ObjectUtils.isEmpty(engineerProfilesFromCache) && !engineerProfilesFromCache.isEmpty()) {
										engineerProfiles = engineerProfilesFromCache.stream().filter(engineerDetails -> (engineerDetails.getUserName().equalsIgnoreCase(criteriaValue))).collect(Collectors.toList());
									}
									break;
				
				case "associateid": 
									if(!ObjectUtils.isEmpty(engineerProfilesFromCache) && !engineerProfilesFromCache.isEmpty()) {
										engineerProfiles = engineerProfilesFromCache.stream().filter(engineerDetails -> (engineerDetails.getAssociateId().equalsIgnoreCase(criteriaValue))).collect(Collectors.toList());
									}
									break;
				
				case "skillname": 
									if(!ObjectUtils.isEmpty(engineerProfilesFromCache) && !engineerProfilesFromCache.isEmpty()) {
										engineerProfiles = engineerProfilesFromCache.stream().
												filter(
														engineerDetails -> engineerDetails.getSkillSets().stream().anyMatch(engineerSkillset -> (engineerSkillset.getSkillName().equalsIgnoreCase(criteriaValue)))
												).collect(Collectors.toList());
									}
									break;
			
			}
		}
		catch(Exception exception) {
			LOGGER.error("Error occured while searching engineer skill profile ", exception);
			throw new AdminSkillProfileException(exception.getMessage());
		}
		engineerSkillProfiles = processSearchProfilesResponse(engineerProfiles);
		LOGGER.info("Exit AdminSkillProfileService::searchProfiles");
		return engineerSkillProfiles;
	}
	
	@SuppressWarnings("unused")
	private List<EngineerSkillProfile> defaultProfile(String criteria, String criteriaValue){
		List<EngineerSkillProfile> engineerSkillProfiles = new ArrayList<>();
		EngineerSkillProfile engineerSkillProfile = new EngineerSkillProfile();
		engineerSkillProfile.setAssociateId("testId");
		engineerSkillProfile.setEmailAddress("testEmail");
		engineerSkillProfile.setMobileNo("testMobileNo");
		engineerSkillProfile.setUserName("testUserName");
		EngineerSkill engineerSkill = new EngineerSkill();
		engineerSkill.setSkillName("testSkillName");
		engineerSkill.setSkillScore("testSkillScore");
		List<EngineerSkill> engineerSkills = new ArrayList<>();
		engineerSkills.add(engineerSkill);
		engineerSkillProfile.setSkills(engineerSkills);
		engineerSkillProfiles.add(engineerSkillProfile);
		return engineerSkillProfiles;
	}
	
	private List<EngineerSkillProfile> processSearchProfilesResponse(List<EngineerDetails> engineerProfiles) {
		LOGGER.info("Enter AdminSkillProfileService::processSearchProfilesResponse");
		List<EngineerSkillProfile> engineerSkillProfiles = new ArrayList<>();
		if(!ObjectUtils.isEmpty(engineerProfiles) && !engineerProfiles.isEmpty()) {
			engineerProfiles.forEach( engineerDetails -> {
				EngineerSkillProfile engineerSkillProfile = new EngineerSkillProfile();
				engineerSkillProfile.set_id(engineerDetails.getUserId());
				engineerSkillProfile.setAssociateId(engineerDetails.getAssociateId());
				engineerSkillProfile.setEmailAddress(engineerDetails.getEmailAddress());
				engineerSkillProfile.setMobileNo(engineerDetails.getMobileNo());
				engineerSkillProfile.setUserName(engineerDetails.getUserName());
				if(!ObjectUtils.isEmpty(engineerDetails.getSkillSets()) && !engineerDetails.getSkillSets().isEmpty()) {
					engineerDetails.getSkillSets().forEach(skillSet -> {
						EngineerSkill engineerSkill = new EngineerSkill();
						engineerSkill.setSkillName(skillSet.getSkillName());
						engineerSkill.setSkillScore(String.valueOf(skillSet.getSkillScore()));
						engineerSkill.setSkillType(skillSet.getSkillType());
						engineerSkill.setSkillId(skillSet.getSkillId());
						engineerSkillProfile.getSkills().add(engineerSkill);
					});
				}
				engineerSkillProfiles.add(engineerSkillProfile);
			});
		}
		LOGGER.info("Exit AdminSkillProfileService::processSearchProfilesResponse");
		return engineerSkillProfiles;
	}
	
	public EngineerSkillProfile addEngineerSkillProfile(EngineerSkillProfile engineerSkillProfile) {
		LOGGER.info("Enter AdminSkillProfileService::addEngineerSkillProfile");
		EngineerDetails engineerDetails = prepareAddProfileRequest(engineerSkillProfile);
		save(engineerDetails);
		engineerSkillProfile = processAddProfileResponse(engineerSkillProfile, engineerDetails);
		LOGGER.info("Exit AdminSkillProfileService::addEngineerSkillProfile");
		return engineerSkillProfile;
	}
	
	private EngineerDetails prepareAddProfileRequest(EngineerSkillProfile engineerSkillProfile) {
		LOGGER.info("Enter AdminSkillProfileService::prepareAddProfileRequest");
		EngineerDetails engineerDetails = new EngineerDetails();
		engineerDetails.setAssociateId(engineerSkillProfile.getAssociateId());
		engineerDetails.setEmailAddress(engineerSkillProfile.getEmailAddress());
		engineerDetails.setMobileNo(engineerSkillProfile.getMobileNo());
//		EngineerId engineerId = new EngineerId();
//		engineerId.setUserName(engineerSkillProfile.getUserName());
		engineerDetails.setUserName(engineerSkillProfile.getUserName());
		engineerDetails.setAddedDate(new Timestamp(new Date().getTime()));
		if(!ObjectUtils.isEmpty(engineerSkillProfile.getSkills()) && !engineerSkillProfile.getSkills().isEmpty() ) {
			engineerSkillProfile.getSkills().forEach(engineerSkill -> {
				EngineerSkillset engineerSkillset = new EngineerSkillset();
				SkillSet skillSet = SkillSet.valueOf(engineerSkill.getSkillName());
				engineerSkillset.setSkillId(skillSet.getSkillId());
				engineerSkillset.setSkillName(skillSet.getSkillName());
				engineerSkillset.setSkillScore(Integer.parseInt(engineerSkill.getSkillScore()));
				engineerSkillset.setSkillType(skillSet.getSkillType());
				engineerSkillset.setAddedDate(new Timestamp(new Date().getTime()));
				engineerDetails.getSkillSets().add(engineerSkillset);
			});
		}
		LOGGER.info("Exit AdminSkillProfileService::prepareAddProfileRequest");
		return engineerDetails;
	}
	
	private EngineerDetails prepareUpdateProfileRequest(EngineerDetails engineerDetails, EngineerSkillProfile engineerSkillProfile) {
		LOGGER.info("Enter AdminSkillProfileService::prepareUpdateProfileRequest");
		engineerDetails.setAssociateId(!ObjectUtils.isEmpty(engineerSkillProfile.getAssociateId()) ? engineerSkillProfile.getAssociateId() : engineerDetails.getAssociateId());
		engineerDetails.setEmailAddress(!ObjectUtils.isEmpty(engineerSkillProfile.getEmailAddress()) ? engineerSkillProfile.getEmailAddress() : engineerDetails.getEmailAddress());
		engineerDetails.setMobileNo(!ObjectUtils.isEmpty(engineerSkillProfile.getMobileNo()) ? engineerSkillProfile.getMobileNo() : engineerDetails.getMobileNo());
		EngineerId engineerId = new EngineerId();
		engineerId.setUserName(engineerSkillProfile.getUserName());
		//engineerDetails.setEngineerId(engineerId);
		engineerDetails.setUpdatedDate(new Timestamp(new Date().getTime()));
		// check skill set updates
		if(engineerSkillProfile.getSkills() != null && !engineerSkillProfile.getSkills().isEmpty()) {
			List<EngineerSkill> skills = engineerSkillProfile.getSkills();
			List<EngineerSkillset> currentSkillSets = engineerDetails.getSkillSets();
			skills.stream().forEach( skill -> {
				SkillSet skillSet = SkillSet.valueOf(skill.getSkillName());
				 Optional<EngineerSkillset> matchedEngineerSkillset = currentSkillSets.stream().filter(engineerSkillset -> (engineerSkillset.getSkillName().equalsIgnoreCase(skillSet.getSkillName()))).findFirst();
				 EngineerSkillset updatedEngineerSkillset = null;
				 if(matchedEngineerSkillset.isPresent() && matchedEngineerSkillset.get() != null) {
					 updatedEngineerSkillset = matchedEngineerSkillset.get();
					 updatedEngineerSkillset.setSkillScore(Integer.valueOf(skill.getSkillScore()));
					updatedEngineerSkillset.setUpdatedDate(new Timestamp(new Date().getTime()));
				}
				 //currentEngineerDetails.getSkillSets().add(engineerSkillset);
			});
		}
		LOGGER.info("Exit AdminSkillProfileService::prepareUpdateProfileRequest");
		return engineerDetails;
	}
	
	private EngineerSkillProfile processAddProfileResponse(EngineerSkillProfile engineerSkillProfile, EngineerDetails engineerDetails) {
		LOGGER.info("Enter AdminSkillProfileService::processAddProfileResponse");
		engineerSkillProfile.set_id(engineerDetails.getUserId());
		LOGGER.info("Exit AdminSkillProfileService::processAddProfileResponse");
		return engineerSkillProfile;
	}
}
