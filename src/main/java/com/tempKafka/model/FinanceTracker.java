package com.tempKafka.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "finance_tracker", createIndex = true)
public class FinanceTracker {

	@Id
	String id;
	String indexId;
	@Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
	private LocalDateTime time;
	private String suspectAccountNumber;
	private String transacrionPartnerAccountNumber;
	Double amount;
	String type;
	String bankTransferFrom;
	String bankTransferTo;

	public FinanceTracker() {
		super();
	}

	public FinanceTracker(String id, String indexId, LocalDateTime time, String suspectAccountNumber,
			String ransacrionPartnerAccountNumber, Double amount, String type, String bankTransferFrom,
			String bankTransferTo) {
		super();
		this.id = id;
		this.indexId = indexId;
		this.time = time;
		this.suspectAccountNumber = suspectAccountNumber;
		this.transacrionPartnerAccountNumber = ransacrionPartnerAccountNumber;
		this.amount = amount;
		this.type = type;
		this.bankTransferFrom = bankTransferFrom;
		this.bankTransferTo = bankTransferTo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIndexId() {
		return indexId;
	}

	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getSuspectAccountNumber() {
		return suspectAccountNumber;
	}

	public void setSuspectAccountNumber(String suspectAccountNumber) {
		this.suspectAccountNumber = suspectAccountNumber;
	}

	

	public String getTransacrionPartnerAccountNumber() {
		return transacrionPartnerAccountNumber;
	}

	public void setTransacrionPartnerAccountNumber(String transacrionPartnerAccountNumber) {
		this.transacrionPartnerAccountNumber = transacrionPartnerAccountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBankTransferFrom() {
		return bankTransferFrom;
	}

	public void setBankTransferFrom(String bankTransferFrom) {
		this.bankTransferFrom = bankTransferFrom;
	}

	public String getBankTransferTo() {
		return bankTransferTo;
	}

	public void setBankTransferTo(String bankTransferTo) {
		this.bankTransferTo = bankTransferTo;
	}

	@Override
	public String toString() {
		return "FinanceTracker [id=" + id + ", indexId=" + indexId + ", time=" + time + ", suspectAccountNumber="
				+ suspectAccountNumber + ", transacrionPartnerAccountNumber=" + transacrionPartnerAccountNumber
				+ ", amount=" + amount + ", type=" + type + ", bankTransferFrom=" + bankTransferFrom
				+ ", bankTransferTo=" + bankTransferTo + "]";
	}


}
