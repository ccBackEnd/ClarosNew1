package com.tempKafka.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "unclassified_audio")
public class UserData {

	@Id
	private String id;
	//	private String user;
	private String fileUrl;
	private String content;
	private boolean inProgress;
	private String type;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime time;
	private String displayTime;
	private List<UserAudioMatch> matchPercentage;
	private int minimumMatchPercentage;
	private Object videoBuffer;


	public UserData() {
		super();
		matchPercentage = new ArrayList<>();
	}

	public UserData(String id, String fileUrl, String content, boolean inProgress,String type,LocalDateTime time, String displayTime, List<UserAudioMatch> matchPercentage, int minimumMatchPercentage) {
		super();
		this.id = id;
//		this.user = user;
		this.fileUrl = fileUrl;
		this.content = content;
		this.inProgress = inProgress;
		this.type=type;
		this.time=time;
		this.displayTime = displayTime;
		this.matchPercentage = matchPercentage;
		this.minimumMatchPercentage = minimumMatchPercentage;
	}

	public int getMinimumMatchPercentage() {
		return minimumMatchPercentage;
	}

	public void setMinimumMatchPercentage(int minimumMatchPercentage) {
		this.minimumMatchPercentage = minimumMatchPercentage;
	}

	public String getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	

	public Object getVideoBuffer() {
		return videoBuffer;
	}

	public void setVideoBuffer(Object videoBuffer) {
		this.videoBuffer = videoBuffer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
//
//	public String getUser() {
//		return user;
//	}
//
//	public void setUser(String user) {
//		this.user = user;
//	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isInProgress() {
		return inProgress;
	}

	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}

	public List<UserAudioMatch> getMatchPercentage() {
		return matchPercentage;
	}

	public void setMatchPercentage(List<UserAudioMatch> matchPercentage) {
		this.matchPercentage = matchPercentage;
	}

	@Override
	public String toString() {
		return "UserData [id=" + id + ", fileUrl=" + fileUrl + ", content=" + content + ", inProgress=" + inProgress
				+ ", type=" + type + ", time=" + time + ", displayTime=" + displayTime + ", matchPercentage="
				+ matchPercentage + ", minimumMatchPercentage=" + minimumMatchPercentage + ", videoBuffer="
				+ videoBuffer + "]";
	}


}
