package com.tempKafka.dto;

import java.util.List;

public class DTOaddRelation {
	
	String relationType;
	List<SusSearch>  selectedUser;
	Boolean relation;
	


	public DTOaddRelation(String relationType, List<SusSearch> selectedUser, Boolean relation) {
		super();
		this.relationType = relationType;
		this.selectedUser = selectedUser;
		this.relation = relation;
	}

	public Boolean getRelation() {
		return relation;
	}

	public void setRelation(Boolean relation) {
		this.relation = relation;
	}

	public DTOaddRelation() {
		super();
	}

	public DTOaddRelation(String relationType, List<SusSearch> selectedUser) {
		super();
		this.relationType = relationType;
		this.selectedUser = selectedUser;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public List<SusSearch> getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(List<SusSearch> selectedUser) {
		this.selectedUser = selectedUser;
	}

	@Override
	public String toString() {
		return "DTOaddRelation [relationType=" + relationType + ", selectedUser=" + selectedUser + ", relation="
				+ relation + "]";
	}
	
	

}
