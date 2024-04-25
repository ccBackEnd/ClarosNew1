package com.tempKafka.elastic;
import org.elasticsearch.search.SearchHit;

import org.elasticsearch.search.aggregations.Aggregations;



import java.io.Serializable;

public class EsResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private SearchHit[] hits = new SearchHit[0];
	private long totalHits;
	private String message;
	private String docId;
	private Aggregations aggs;
	 private float maxScore;

	public float getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(float maxScore) {
		this.maxScore = maxScore;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public SearchHit[] getHits() {
		return hits;
	}

	public long getTotalHits() {
		return totalHits;
	}

	public void setHits(SearchHit[] hits) {
		this.hits = hits;
	}

	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Aggregations getAggs() {
		return aggs;
	}

	public void setAggs(Aggregations aggs) {
		this.aggs = aggs;
	}

	
}
