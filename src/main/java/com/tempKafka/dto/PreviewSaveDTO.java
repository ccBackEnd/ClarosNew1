package com.tempKafka.dto;

public class PreviewSaveDTO {

	private Integer faceid;
	private String faceuserid;

	public Integer getFaceid() {
		return faceid;
	}

	public void setFaceid(Integer faceid) {
		this.faceid = faceid;
	}

	public String getFaceuserid() {
		return faceuserid;
	}

	public void setFaceuserid(String faceuserid) {
		this.faceuserid = faceuserid;
	}

	public PreviewSaveDTO(Integer faceid, String faceuserid) {
		super();
		this.faceid = faceid;
		this.faceuserid = faceuserid;
	}

	@Override
	public String toString() {
		return "PreviewSaveDTO [faceid=" + faceid + ", faceuserid=" + faceuserid + "]";
	}

}
