package com.tempKafka.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "audio_rec_criminal")
public class SaveAudio {

	@Id
	private String id;
	private String userId;
	private String fileUrl;
	private String audio_encode_r;
	@Field(type = FieldType.Nested, name = "possible_matches")
	private List<Object> possible_matches;
	//	private Object possible_match_score;   //
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime time;
	private String description;
	private String uploadedBy;
	private String type;
	private String docid;
	private String displayTime;

	public SaveAudio() {
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
		return audio_encode_r;
	}
	public void setAudio_encode(String audio_encode) {
		this.audio_encode_r = audio_encode;
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

	public String getUploadedBy() {
		return uploadedBy;
	}
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<Object> getPossible_matches() {
		return possible_matches;
	}
	public void setPossible_matches(List<Object> possible_matches) {
		this.possible_matches = possible_matches;
	}
	public String getDocid() {
		return docid;
	}
	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getDisplayTime() {
		return displayTime;
	}
	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}
	public SaveAudio(String id, String userId, String fileUrl, String audio_encode, LocalDateTime time) {
		super();
		this.id = id;
		this.userId = userId;
		this.fileUrl = fileUrl;
		this.audio_encode_r = audio_encode;
		this.time = time;
	}
	@Override
	public String toString() {
		return "SaveAudio [id=" + id + ", userId=" + userId + ", fileUrl=" + fileUrl + ", audio_encode=" + audio_encode_r
				+ ", time=" + time + "]";
	}



}
