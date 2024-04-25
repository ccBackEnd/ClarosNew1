package com.tempKafka.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "image_rec_criminal", createIndex = true)
public class SaveImage {

	@Id
	private String id;
	private String userId;
	private String fileUrl;
	private Object audio_encode;
	private LocalDateTime time;
	private String displayTime;
	private String description;


	public SaveImage() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Object getAudio_encode() {
		return audio_encode;
	}
	public void setAudio_encode(Object audio_encode) {
		this.audio_encode = audio_encode;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getDisplayTime() {
		return displayTime;
	}
	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}
	public SaveImage(String id, String userId, String fileUrl, Object audio_encode, LocalDateTime time) {
		super();
		this.id = id;
		this.userId = userId;
		this.fileUrl = fileUrl;
		this.audio_encode = audio_encode;
		this.time = time;
	}
	@Override
	public String toString() {
		return "SaveAudio [id=" + id + ", userId=" + userId + ", fileUrl=" + fileUrl + ", audio_encode=" + audio_encode
				+ ", time=" + time + "]";
	}



}
