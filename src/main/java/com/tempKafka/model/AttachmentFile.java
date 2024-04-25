package com.tempKafka.model;

public class AttachmentFile {
	
	private String id;
	private String path;
	private String filename;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	@Override
	public String toString() {
		return "AttachmentFile [id=" + id + ", path=" + path + ", filename=" + filename + "]";
	}
	public AttachmentFile(String id, String path, String filename) {
		super();
		this.id = id;
		this.path = path;
		this.filename = filename;
	}
	public AttachmentFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
