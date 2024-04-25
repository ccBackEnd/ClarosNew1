package com.tempKafka.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.tempKafka.model.ClarosUsers;

public class signaldto {
	
	String address;
	String description;
	String type;
	String typeofcrime;
	List<ClarosUsers> poi;
	List<MultipartFile> attachments;
	
	
    public signaldto(String address, String description, String type, String typeofcrime, List<ClarosUsers> poi,
			List<MultipartFile> attachments, Object date) {
		super();
		this.address = address;
		this.description = description;
		this.type = type;
		this.typeofcrime = typeofcrime;
		this.poi = poi;
		this.attachments = attachments;
		this.date = date;
	}
	public List<MultipartFile> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<MultipartFile> attachments) {
		this.attachments = attachments;
	}
	Object date;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeofcrime() {
		return typeofcrime;
	}
	public void setTypeofcrime(String typeofcrime) {
		this.typeofcrime = typeofcrime;
	}
	public List<ClarosUsers> getPoi() {
		return poi;
	}
	public void setPoi(List<ClarosUsers> poi) {
		this.poi = poi;
	}
	public Object getDate() {
		return date;
	}
	public void setDate(Object date) {
		this.date = date;
	}
	public signaldto() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "signaldto [ address=" + address + ", description=" + description + ", type=" + type
				+ ", typeofcrime=" + typeofcrime + ", poi=" + poi + ", attachments=" + attachments + ", date=" + date
				+ "]";
	}
	
	
	
}
