package com.shah.fse.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.shah.fse.entity.EngineerDetails;
import com.shah.fse.entity.EngineerSkillset;

@Repository
public class AdminSkillProfileRepository {
	
	@Cacheable(value = "engineerSkillProfiles", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
	public List<EngineerDetails> getEngineerSkillProfiles(){
		List<EngineerDetails> engineerSkillProfiles = new ArrayList<>();
		EngineerDetails engineerDetails = new EngineerDetails();
		engineerDetails.setAssociateId("testId");
		engineerDetails.setEmailAddress("testEmail");
		engineerDetails.setMobileNo("testMobileNo");
		engineerDetails.setUserName("testUserName");
		EngineerSkillset engineerSkill = new EngineerSkillset();
		engineerSkill.setSkillName("testSkillName");
		engineerSkill.setSkillScore(15);
		List<EngineerSkillset> engineerSkills = new ArrayList<>();
		engineerSkills.add(engineerSkill);
		engineerDetails.setSkillSets(engineerSkills);
		engineerSkillProfiles.add(engineerDetails);
		simulateSlowService();
		return engineerSkillProfiles;
	}
	
	@CachePut(value = "engineerSkillProfiles", key = "T(org.springframework.cache.interceptor.SimpleKey).EMPTY")
	public List<EngineerDetails> save(List<EngineerDetails> engineerSkillProfiles) {
		simulateSlowService();
		return engineerSkillProfiles;
	}
	
   private void simulateSlowService() {
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	
}