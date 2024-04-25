//package com.tempKafka.Controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import com.kafka.Notification;
//@Service
//public class ClarosProducer {
//	
//	@Autowired
//	  private  KafkaTemplate<String, Notification> kafkaTemplate;
//	
//	private  String notificationTopic = "claros_notifications";
//	
//	 public  void send3(Notification notification) {
//		 System.out.println("====================== notification sent to kafka ======================");
//		 
//
//			kafkaTemplate.executeInTransaction(kafkaTemplate -> {
//				 kafkaTemplate.send(notificationTopic, notification);
//				return true;
//			});
//		 
//		
//	 }
//}
