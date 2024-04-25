package com.tempKafka.dto;

public class SusSearch {

	private String name;
	private String userid;
	private Integer fromAge;
	private Integer toAge;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getFromAge() {
		return fromAge;
	}
	public void setFromAge(Integer fromAge) {
		this.fromAge = fromAge;
	}
	public Integer getToAge() {
		return toAge;
	}
	public void setToAge(Integer toAge) {
		this.toAge = toAge;
	}
	@Override
	public String toString() {
		return "SusSearch [name=" + name + ", userid=" + userid + ", fromAge=" + fromAge + ", toAge=" + toAge + "]";
	}
	public SusSearch() {
		super();
	}
	public SusSearch(String name, String userid, Integer fromAge, Integer toAge) {
		super();
		this.name = name;
		this.userid = userid;
		this.fromAge = fromAge;
		this.toAge = toAge;
	}

	
}
