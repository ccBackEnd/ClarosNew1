package com.tempKafka.model;

import java.util.HashMap;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "face_rec_criminal", createIndex = true)
public class ImageSearchM {
	@Id
	private String id;
	@Field(type = FieldType.Text, name = "name")
	String name;
	@Field(type = FieldType.Text, name = "img")
	Object img;
	@Field(type = FieldType.Nested, name = "face_encoding")
	Object face_encoding;
	@Field(type = FieldType.Keyword, name = "face_coordinates")
	Object face_coordinates;
	@Field(type = FieldType.Nested, name = "con_ids")
	Object con_ids;
	@Field(type = FieldType.Keyword, name = "userid")
	String userid;
	@Field(type = FieldType.Keyword, name = "displayName")
	String displayName;
	@Field(type = FieldType.Boolean, name = "dedicatedPicture")
	boolean displayPicture;
	String age;


	public String getAge() {
		return age;
	}



	public void setAge(String age) {
		this.age = age;
	}



	public ImageSearchM(String id, String name, Object img, Object face_encoding, Object face_coordinates,
						Object con_ids, String userid, String displayName, boolean displayPicture, String age) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.face_encoding = face_encoding;
		this.face_coordinates = face_coordinates;
		this.con_ids = con_ids;
		this.userid = userid;
		this.displayName = displayName;
		this.displayPicture = displayPicture;
		this.age = age;
	}



	public ImageSearchM() {
		super();
	}



	public String getDisplayName() {
		return displayName;
	}



	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}



	public ImageSearchM(String id, String name, Boolean img, Object face_encoding, Object face_coordinates,
						Object con_ids, String userid, String displayName,boolean displayPicture) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.face_encoding = face_encoding;
		this.face_coordinates = face_coordinates;
		this.con_ids = con_ids;
		this.userid = userid;
		this.displayName = displayName;
	}



	public boolean getDisplayPicture() {
		return displayPicture;
	}



	public void setDisplayPicture(boolean displayPicture) {
		this.displayPicture = displayPicture;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getImg() {
		return img;
	}

	public void setImg(Object img) {
		this.img = img;
	}

	public Object getFace_encoding() {
		return face_encoding;
	}

	public void setFace_encoding(Object face_encoding) {
		this.face_encoding = face_encoding;
	}

	public Object getFace_coordinates() {
		return face_coordinates;
	}

	public void setFace_coordinates(Object face_coordinates) {
		this.face_coordinates = face_coordinates;
	}

	public Object getCon_ids() {
		return con_ids;
	}

	public void setCon_ids(Object con_ids) {
		this.con_ids = con_ids;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}



	@Override
	public String toString() {
		return "ImageSearchM [id=" + id + ", name=" + name + ", img=" + img + ", face_encoding=" + face_encoding
				+ ", face_coordinates=" + face_coordinates + ", con_ids=" + con_ids + ", userid=" + userid
				+ ", displayName=" + displayName + ", displayPicture=" + displayPicture + ", age=" + age + "]";
	}







}
