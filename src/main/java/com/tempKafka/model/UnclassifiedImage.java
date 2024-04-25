package com.tempKafka.model;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "unclassified_images_1", createIndex = true)
public class UnclassifiedImage {

	@Id
	private String id;
	@Field(type = FieldType.Keyword, name = "upload_id")
	private String uploadid;
	@Field(type = FieldType.Keyword, name = "face_coordinates")
	Object face_coordinates;

	public UnclassifiedImage(String id) {
		super();
		this.id = id;
	}
	private int face_count;
	//	face_details
	@Field(type = FieldType.Binary, name = "img")
	Object img;
	@Field(type = FieldType.Binary, name = "boxImg")
	Object boxImg;
	@Field(type = FieldType.Nested, name= "face_details")
	private  List<Object> face_details;
	private String type;
	private String video_path;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime time;

	public UnclassifiedImage(String id, String uploadid, Object face_coordinates, int face_count, Object img,
							 Object boxImg, List<Object> face_details, String type, String video_path) {
		super();
		this.id = id;
		this.uploadid = uploadid;
		this.face_coordinates = face_coordinates;
		this.face_count = face_count;
		this.img = img;
		this.boxImg = boxImg;
		this.face_details = face_details;
		this.type = type;
		this.video_path = video_path;
	}

	public String getVideo_path() {
		return video_path;
	}

	public void setVideo_path(String video_path) {
		this.video_path = video_path;
	}

	public UnclassifiedImage(String id, String uploadid, Object face_coordinates, int face_count, Object img,
							 Object boxImg, List<Object> face_details,String type) {
		super();
		this.id = id;
		this.uploadid = uploadid;
		this.face_coordinates = face_coordinates;
		this.face_count = face_count;
		this.img = img;
		this.boxImg = boxImg;
		this.face_details = face_details;
		this.type=type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UnclassifiedImage() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUploadid() {
		return uploadid;
	}
	public void setUploadid(String uploadid) {
		this.uploadid = uploadid;
	}
	public Object getFace_coordinates() {
		return face_coordinates;
	}
	public void setFace_coordinates(Object face_coordinates) {
		this.face_coordinates = face_coordinates;
	}
	public int getFace_count() {
		return face_count;
	}
	public void setFace_count(int face_count) {
		this.face_count = face_count;
	}
	public Object getImg() {
		return img;
	}
	public void setImg(Object img) {
		this.img = img;
	}
	public Object getBoxImg() {
		return boxImg;
	}
	public void setBoxImg(Object boxImg) {
		this.boxImg = boxImg;
	}
	public List<Object> getFace_details() {
		return face_details;
	}
	public void setFace_details(List<Object> face_details) {
		this.face_details = face_details;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "UnclassifiedImage [id=" + id + ", uploadid=" + uploadid + ", face_coordinates=" + face_coordinates
				+ ", face_count=" + face_count + ", img=" + img + ", boxImg=" + boxImg + ", face_details="
				+ face_details + "]";
	}








}
