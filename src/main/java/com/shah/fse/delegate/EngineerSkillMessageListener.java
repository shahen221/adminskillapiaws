package com.shah.fse.delegate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.shah.fse.entity.EngineerDetails;
import com.shah.fse.service.AdminSkillProfileService;

@Component
public class EngineerSkillMessageListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EngineerSkillMessageListener.class);
	
	@Autowired
	AdminSkillProfileService adminSkillProfileService;

	@JmsListener(destination = "engineerProfileQueue")
	public void listenForMessage(Message consumerMessage) {
		if(consumerMessage instanceof ObjectMessage) {
			ObjectMessage engineerProfileMessage = (ObjectMessage)consumerMessage;
			EngineerDetails engineerDetails;
			try {
				engineerDetails = (EngineerDetails)engineerProfileMessage.getObject();
				LOGGER.info("Message received from MQ successfully.", engineerDetails);
				adminSkillProfileService.save(engineerDetails);
			} catch (JMSException jmsException) {
				LOGGER.error("Error occured while getting message from ActiveMQ ", jmsException);
			}
		}
	}
	
}
