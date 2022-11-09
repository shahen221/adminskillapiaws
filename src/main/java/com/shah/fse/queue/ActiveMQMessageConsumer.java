package com.shah.fse.queue;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.shah.fse.entity.EngineerDetails;
import com.shah.fse.repository.AdminSkillProfileRepository;
import com.shah.fse.service.AdminSkillProfileService;

//@Component
public class ActiveMQMessageConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQMessageConsumer.class);
	
	//@Autowired
	MessageConsumer messageConsumer;
	
	//@Autowired
	AdminSkillProfileRepository adminSkillProfileRepository;
	
	//@Autowired
	AdminSkillProfileService adminSkillProfileService;
	
	//@JmsListener(destination = "engineerProfileQueue")
	public void listenForMessage() {
		Message consumerMessage = null;
		try {
			 messageConsumer.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
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
			});
		} catch (JMSException jmsException) {
			LOGGER.error("Error occured while getting message from AWS MQ  ", jmsException);
		}
	}
	
}
