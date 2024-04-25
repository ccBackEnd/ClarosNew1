package com.tempKafka.dto;

public class TeamSyncDTO {
	
	String path;
	String username;
	String text;
	
	public TeamSyncDTO() {
		super();
	}

	public TeamSyncDTO(String path, String username, String text) {
		super();
		this.path = path;
		this.username = username;
		this.text = text;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "TeamSyncDTO [path=" + path + ", username=" + username + ", text=" + text + "]";
	}
	
	
	
	
}
