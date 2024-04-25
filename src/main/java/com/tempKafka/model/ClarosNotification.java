package com.tempKafka.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "notification_claros", createIndex = true)
public class ClarosNotification {
	@Id
	private String id;
	private String type = "";
	private String time;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime generatedAt;
	private String status;
	private boolean shared;
	private String userId;
	private String deptUsername;
	private String deptDisplayUsername;

	public ClarosNotification() {
		super();
	}
	

	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public ClarosNotification(String id, String type, String time, LocalDateTime generatedAt, String status,
			boolean shared, String deptUsername, String deptDisplayUsername) {
		super();
		this.id = id;
		this.type = type;
		this.time = time;
		this.generatedAt = generatedAt;
		this.status = status;
		this.shared = shared;
		this.userId = deptUsername;
		this.deptDisplayUsername = deptDisplayUsername;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public LocalDateTime getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(LocalDateTime generatedAt) {
		this.generatedAt = generatedAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isShared() {
		return shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	public String getDeptUsername() {
		return deptUsername;
	}

	public void setDeptUsername(String deptUsername) {
		this.deptUsername = deptUsername;
	}

	public String getDeptDisplayUsername() {
		return deptDisplayUsername;
	}

	public void setDeptDisplayUsername(String deptDisplayUsername) {
		this.deptDisplayUsername = deptDisplayUsername;
	}

	@Override
	public String toString() {
		return "ClarosNotification [id=" + id + ", type=" + type + ", time=" + time + ", generatedAt=" + generatedAt
				+ ", status=" + status + ", shared=" + shared + ", deptUsername=" + deptUsername
				+ ", deptDisplayUsername=" + deptDisplayUsername + "]";
	}

}
