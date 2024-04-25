package com.tempKafka.dto;

import java.util.List;

public class FaceDetailsUnclassified {

	 int id;  //id face_no
	 Object image; // face_image
     Object face_encoding; //
     List<String> possible_matches ; //possible_matches
     String selectedpossibleMatchId; // selectedMatchId
     Boolean status; // status selected
   
	public FaceDetailsUnclassified(int id, Object image, Object face_encoding, List<String> possible_matches,
			String selectedpossibleMatchId, Boolean status) {
		super();
		this.id = id;
		this.image = image;
		
		this.face_encoding = face_encoding;
		this.possible_matches = possible_matches;
		this.selectedpossibleMatchId = selectedpossibleMatchId;
		this.status = status;
	}
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Object getImage() {
		return image;
	}
	public void setImage(Object image) {
		this.image = image;
	}
	public Object getFace_encoding() {
		return face_encoding;
	}
	public void setFace_encoding(Object face_encoding) {
		this.face_encoding = face_encoding;
	}
	public List<String> getPossible_matches() {
		return possible_matches;
	}
	public void setPossible_matches(List<String> possible_matches) {
		this.possible_matches = possible_matches;
	}
	public String getSelectedpossibleMatchId() {
		return selectedpossibleMatchId;
	}
	public void setSelectedpossibleMatchId(String selectedpossibleMatchId) {
		this.selectedpossibleMatchId = selectedpossibleMatchId;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "FaceDetailsUnclassified [id=" + id + ", image=" + image + ", face_encoding=" + face_encoding
				+ ", possible_matches=" + possible_matches + ", selectedpossibleMatchId=" + selectedpossibleMatchId
				+ ", status=" + status + "]";
	}
     
     
     
//     
//	public String getSelectedUser() {
//		return selectedUser;
//	}
//	public void setSelectedUser(String selectedUser) {
//		this.selectedUser = selectedUser;
//	}
//	public Boolean getSelected() {
//		return selected;
//	}
//	public void setSelected(Boolean selected) {
//		this.selected = selected;
//	}
//	public FaceDetailsUnclassified(int face_no, Object face_img, Object face_encoding, Object possible_matches,
//			String selectedUser, Boolean selected) {
//		super();
//		this.face_no = face_no;
//		this.face_img = face_img;
//		this.face_encoding = face_encoding;
//		if(possible_matches!=null)
//		this.possible_matches = (List<String> )possible_matches;
//		this.selectedUser = selectedUser;
//		this.selected = selected;
//	}
//	public FaceDetailsUnclassified(int face_no, Object face_img, Object face_encoding, Object possible_matches) {
//		super();
//		this.face_no = face_no;
//		this.face_img = face_img;
//		this.face_encoding = face_encoding;
//		if(possible_matches!=null)
//			this.possible_matches = (List<String> )possible_matches;
//	}
//	public int getFace_no() {
//		return face_no;
//	}
//	public void setFace_no(int face_no) {
//		this.face_no = face_no;
//	}
//	public Object getFace_img() {
//		return face_img;
//	}
//	public void setFace_img(Object face_img) {
//		this.face_img = face_img;
//	}
//	public Object getFace_encoding() {
//		return face_encoding;
//	}
//	public void setFace_encoding(Object face_encoding) {
//		this.face_encoding = face_encoding;
//	}
//	public List<String> getPossible_matches() {
//		return possible_matches;
//	}
//	public void setPossible_matches(List<String> possible_matches) {
//		this.possible_matches = possible_matches;
//	}
//	@Override
//	public String toString() {
//		return "FaceDetailsUnclassified [face_no=" + face_no + ", face_img=" + face_img + ", face_encoding="
//				+ face_encoding + ", possible_matches=" + possible_matches + ", selectedUser=" + selectedUser
//				+ ", selected=" + selected + "]";
//	}
//     
//     
}
