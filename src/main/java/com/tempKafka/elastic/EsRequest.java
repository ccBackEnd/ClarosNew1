package com.tempKafka.elastic;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class EsRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	QueryBuilder queryBuilder;
	private Integer offset;
	private Integer noOfRecords;
	private String[] indices;
	private String[] includeFields;
	private boolean fetchSource = true;
	private String docId;
	private String index;
	private boolean refreshPolicy;
	private Object docObject;
	private List<AggregationBuilder> aggBuilder;
	private Map<String, String> sortMap;

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}

	public void setQueryBuilder(QueryBuilder queryBuilder) {
		this.queryBuilder = queryBuilder;
	}

	public Map<String, String> getSortMap() {
		return sortMap;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getNoOfRecords() {
		return noOfRecords;
	}

	public String[] getIncludeFields() {
		return includeFields;
	}

	public void setIncludeFields(String[] includeFields) {
		this.includeFields = includeFields;
	}

	public void setNoOfRecords(Integer noOfRecords) {
		this.noOfRecords = noOfRecords;
	}

	public String[] getIndices() {
		return indices;
	}

	public void setIndices(String[] indices) {
		this.indices = indices;
	}

	public boolean isFetchSource() {
		return fetchSource;
	}

	public void setFetchSource(boolean fetchSource) {
		this.fetchSource = fetchSource;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public boolean isRefreshPolicy() {
		return refreshPolicy;
	}

	public void setRefreshPolicy(boolean refreshPolicy) {
		this.refreshPolicy = refreshPolicy;
	}

	public Object getDocObject() {
		return docObject;
	}

	public void setDocObject(Object docObject) {
		this.docObject = docObject;
	}

	public List<AggregationBuilder> getAggBuilder() {
		return aggBuilder;
	}

	public void setAggBuilder(List<AggregationBuilder> aggBuilder) {
		this.aggBuilder = aggBuilder;
	}

	public EsRequest queryBuilder(QueryBuilder bq) {
		setQueryBuilder(bq);
		return this;
	}
	
	

	public EsRequest sortMap(Map<String, String> sortMap) {
		this.sortMap = sortMap;
		return this;
	}

	public EsRequest offset(Integer offset) {
		setOffset(offset);
		return this;
	}

	public EsRequest noOfRecords(Integer noOfRecords) {
		setNoOfRecords(noOfRecords);
		return this;
	}

	public EsRequest indices(String[] indices) {
		setIndices(indices);
		return this;
	}

	public EsRequest includeFields(String[] includeFields) {
		setIncludeFields(includeFields);
		return this;
	}

	public EsRequest fetchSource(boolean fetchSource) {
		setFetchSource(fetchSource);
		return this;
	}

	public EsRequest docId(String docId) {
		setDocId(docId);
		return this;
	}

	public EsRequest index(String index) {
		setIndex(index);
		return this;
	}

	public EsRequest refreshPolicy(boolean refreshPolicy) {
		setRefreshPolicy(refreshPolicy);
		return this;
	}

	public EsRequest docObject(Object docObject) {
		setDocObject(docObject);
		return this;
	}

	public EsRequest aggBuilder(List<AggregationBuilder> aggBuilder) {
		setAggBuilder(aggBuilder);
		return this;
	}
}
