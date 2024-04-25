package com.tempKafka.dto;

import org.springframework.web.multipart.MultipartFile;

public class DTOPointData {
	
	MultipartFile file;
	String userid;
	String type;
	String description;
	String fName;
	public DTOPointData() {
		super();
	}
	public DTOPointData(String file, String userid, String type, String description) {
		super();
		this.fName = file;
		this.userid = userid;
		this.type = type;
		this.description = description;
	}
	public DTOPointData(MultipartFile file, String userid, String type, String description) {
		super();
		this.file = file;
		this.userid = userid;
		this.type = type;
		this.description = description;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "DTOPointData [file=" + file + ", userid=" + userid + ", type=" + type + ", description=" + description
				+ ", fName=" + fName + "]";
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}

}
