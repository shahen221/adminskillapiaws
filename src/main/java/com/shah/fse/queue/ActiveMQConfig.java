package com.shah.fse.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@Configuration
@EnableJms
public class ActiveMQConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ActiveMQConfig.class);
	
	@Value("${activemq.broker.url}")
	private String activeMQBokerURL;
	
	@Value("${activemq.user}")
	private String mqUser;
	
	@Value("${activemq.password}")
	private String mqPassword;
	
	@Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory 
          = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }
	
	private ConnectionFactory connectionFactory() {
		Connection consumerConnection = null;
		Session consumerSession = null;
		Queue destinationQueue = null;
		MessageConsumer messageConsumer = null;
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(activeMQBokerURL);
		activeMQConnectionFactory.setUserName(mqUser);
		activeMQConnectionFactory.setPassword(mqPassword);
		activeMQConnectionFactory.setTrustAllPackages(true);
		try {
			consumerConnection = activeMQConnectionFactory.createConnection();
			consumerConnection.start();
			consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destinationQueue = consumerSession.createQueue("engineerProfileQueue");
			//messageConsumer = consumerSession.createConsumer(destinationQueue);
			
		} catch (JMSException jmsException) {
			LOGGER.error("Error occured while getting MQ consumer connection/session ", jmsException);
		}
		return activeMQConnectionFactory;
	}
	//@Bean
	public MessageConsumer consumerSession() {
		Connection consumerConnection = null;
		Session consumerSession = null;
		Queue destinationQueue = null;
		MessageConsumer messageConsumer = null;
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(activeMQBokerURL);
		activeMQConnectionFactory.setUserName(mqUser);
		activeMQConnectionFactory.setPassword(mqPassword);
		try {
			consumerConnection = activeMQConnectionFactory.createConnection();
			consumerConnection.start();
			consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			destinationQueue = consumerSession.createQueue("engineerProfileQueue");
			messageConsumer = consumerSession.createConsumer(destinationQueue);
			
		} catch (JMSException jmsException) {
			LOGGER.error("Error occured while getting MQ consumer connection/session ", jmsException);
		}
		return messageConsumer;
	}
	
	/*private void cleanupResources() {
		try {
			if(!ObjectUtils.isEmpty(messageProducer)) {
				messageProducer.close();
			}
			if(!ObjectUtils.isEmpty(producerSession)) {
				producerSession.close();
			}
			if(!ObjectUtils.isEmpty(producerConnection)) {
				producerConnection.close();
			}
		} catch (JMSException jmsException) {
			LOGGER.error("Error occured while closing MQ resources ", jmsException);
		}
	}*/
}
