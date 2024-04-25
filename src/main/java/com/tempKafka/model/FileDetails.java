//package com.tempKafka.model;
//
//import java.time.LocalDateTime;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//public class FileDetails {
//	private String fileName;
//	private String fileUrl;
//	private String comment;
//	private int flagNumber;
//	@JsonFormat(pattern = "dd MMM yyyy")
//	private LocalDateTime signedOn;
//	private boolean isSigned;
//	private String prevVersionId;
//	private String annotationId;
//	private String subject;
//	private String status;
//	private String serviceLetterId;
//	private String fileId;
//	@JsonFormat(pattern = "dd MMM yyyy")
//	private LocalDateTime uploadTime;
//	private boolean isCoverNote;
//	private String uploader;
//
//	public FileDetails() {
//		super();
//		comment = "";
//		annotationId = "";
//		uploadTime = LocalDateTime.now();
//		isCoverNote = false;
//	}
//	
//	
//
//	@Override
//	public String toString() {
//		return "{\"fileName\":\"" + fileName + "\", \"fileUrl\":\"" + fileUrl + "\", \"comment\":\"" + comment 
//				+ "\", \"flagNumber\":\""+ flagNumber + "\", \"signedOn\":\"" + signedOn + "\", \"isSigned\":\"" 
//				+ isSigned+ "\", \"prevVersionId\":\"" + prevVersionId+ "\", \"annotationId\":\"" + annotationId 
//				+ "\", \"subject\":\"" + subject + "\", \"status\":\""+ status + "\", \"serviceLetterId\":\"" 
//				+ serviceLetterId + "\", \"fileId\"" + fileId + "\", \"uploadTime\":\""+ uploadTime 
//				+ "\", \"isCoverNote\":\"" + isCoverNote + "\", \"uploader\":\"" + uploader + "\"}";
//	}
//
//}
