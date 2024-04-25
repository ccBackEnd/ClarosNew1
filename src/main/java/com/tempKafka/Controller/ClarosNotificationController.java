package com.tempKafka.Controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tempKafka.Repo.ClarosNotificationRepo;
import com.tempKafka.model.ClarosNotification;



@CrossOrigin("*")
@RestController
@RequestMapping("/clarosOne")
public class ClarosNotificationController {
	
	@Autowired
	private ClarosNotificationRepo notificationRepository;
	HashMap<String, String> response = new HashMap<String, String>();
	
	@GetMapping("/notification")
	public ResponseEntity<?> getNotification(@RequestHeader("rolename") String rolename,HttpServletRequest request) {
		try {
			response = new HashMap<String, String>();
			List<ClarosNotification> notifications = notificationRepository.findAllByUserIdOrderByGeneratedAtDesc(rolename);
			return ResponseEntity.ok(notifications);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", e.getMessage());
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/notification/change-status")
	public ResponseEntity<?> changeStatus(@RequestHeader("rolename") String rolename,HttpServletRequest request) {

		try {
			response = new HashMap<String, String>();
			List<ClarosNotification> notifications = notificationRepository.findAllByUserIdOrderByGeneratedAtDesc(rolename);
			for (ClarosNotification notification : notifications) {
				notification.setStatus("read");
				notificationRepository.save(notification);
			}
			return ResponseEntity.ok(notifications);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", e.getMessage());
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/notification/changeStatus/{id}")
	public ResponseEntity<?> changeStatus(@RequestHeader("rolename") String rolename,HttpServletRequest request, @PathVariable("id") String id) {
		try {
			response = new HashMap<String, String>();
			ClarosNotification notification = notificationRepository.findById(id).get();
			notification.setStatus("read");
			ClarosNotification save = notificationRepository.save(notification);
			return ResponseEntity.ok(save);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", e.getMessage());
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping("/delete_notification/{id}")
	public ResponseEntity<?> deleteNotification(@PathVariable("id") String id) {
		try {
			response = new HashMap<String, String>();
			notificationRepository.deleteById(id);
			return ResponseEntity.ok("Notification deleted successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", e.getMessage());
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@DeleteMapping("/delete_all_notification")
	public ResponseEntity<?> deleteAllNotification(HttpServletRequest request) {
		response = new HashMap<String, String>();
		try {
			String rolename = request.getHeader("rolename");
			List<ClarosNotification> notifications = notificationRepository.findAllByUserIdOrderByGeneratedAtDesc(rolename);
			notificationRepository.deleteAll(notifications);
			response.put("message", "Deleted all notifications successfully!!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("error", e.getMessage());
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
