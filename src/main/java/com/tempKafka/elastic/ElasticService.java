package com.tempKafka.elastic;

import java.io.IOException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@SuppressWarnings("deprecation")
@Service
public class ElasticService {

	@Autowired
	RestHighLevelClient restHighLevelClient;
	
	@Autowired
	RestClient restclient;

//			"histndata";

	@Autowired
	ObjectMapper objectMapper;
	
	public EsResponse searchESTemp(EsRequest esRequest) throws IOException {


		SearchResponse searchResponse;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		SearchRequest searchRequest = null;

		if (esRequest.getQueryBuilder() != null)
			sourceBuilder = sourceBuilder.query(esRequest.getQueryBuilder());

		ScoreSortBuilder sortBuilder = SortBuilders.scoreSort(); 
		sortBuilder.order(SortOrder.DESC);
		sourceBuilder = sourceBuilder.sort(sortBuilder);

//		if (esRequest.getSortMap() != null) {
//			for (String key : esRequest.getSortMap().keySet()) {
//				sourceBuilder = sourceBuilder.sort(key, SortOrder.fromString(esRequest.getSortMap().get(key)));
//			}
//		}
		if (esRequest.getOffset() != null && esRequest.getNoOfRecords() != null) {
			sourceBuilder = sourceBuilder.size(esRequest.getNoOfRecords().intValue())
					.from(esRequest.getOffset().intValue());
		}

		if (esRequest.getIncludeFields() != null && esRequest.getIncludeFields().length > 0) {
			sourceBuilder = sourceBuilder.fetchSource(esRequest.getIncludeFields(), null);
		} else if (!esRequest.isFetchSource()) {
			sourceBuilder = sourceBuilder.fetchSource(false);
		}

		if (esRequest.getIndex() != null)
			searchRequest = new SearchRequest(esRequest.getIndex());
		else
			searchRequest = new SearchRequest(esRequest.getIndices());

		searchRequest.source(sourceBuilder).indicesOptions(IndicesOptions.lenientExpandOpen());

		searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

		EsResponse esResponse = new EsResponse();
		if (searchResponse.getHits() != null) {
			esResponse.setHits(searchResponse.getHits().getHits());
			esResponse.setTotalHits(searchResponse.getHits().getTotalHits().value);
		}
		return esResponse;
	
		
	}





	public EsResponse searchES(EsRequest esRequest,int f, int s) throws ElasticsearchStatusException, IOException {

		SearchResponse searchResponse;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().from(f).size(s);
		SearchRequest searchRequest = null;

		if (esRequest.getQueryBuilder() != null)
			sourceBuilder = sourceBuilder.query(esRequest.getQueryBuilder());

		ScoreSortBuilder sortBuilder = SortBuilders.scoreSort(); 
//				SortBuilders.fieldSort("caCreatedOn");
		sortBuilder.order(SortOrder.DESC);
		sourceBuilder = sourceBuilder.sort(sortBuilder);

//		if (esRequest.getSortMap() != null) {
//			for (String key : esRequest.getSortMap().keySet()) {
//				sourceBuilder = sourceBuilder.sort(key, SortOrder.fromString(esRequest.getSortMap().get(key)));
//			}
//		}
		if (esRequest.getOffset() != null && esRequest.getNoOfRecords() != null) {
			sourceBuilder = sourceBuilder.size(esRequest.getNoOfRecords().intValue())
					.from(esRequest.getOffset().intValue());
		}

		if (esRequest.getIncludeFields() != null && esRequest.getIncludeFields().length > 0) {
			sourceBuilder = sourceBuilder.fetchSource(esRequest.getIncludeFields(), null);
		} else if (!esRequest.isFetchSource()) {
			sourceBuilder = sourceBuilder.fetchSource(false);
		}

		if (esRequest.getIndex() != null)
			searchRequest = new SearchRequest(esRequest.getIndex());
		else
			searchRequest = new SearchRequest(esRequest.getIndices());

		
		
		searchRequest.source(sourceBuilder).indicesOptions(IndicesOptions.lenientExpandOpen());

		searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		

		EsResponse esResponse = new EsResponse();
		if (searchResponse.getHits() != null) {
			esResponse.setMaxScore(searchResponse.getHits().getMaxScore());
			esResponse.setHits(searchResponse.getHits().getHits());
			esResponse.setTotalHits(searchResponse.getHits().getTotalHits().value);
		}
		return esResponse;
	}

	public EsResponse searchES2(EsRequest esRequest) throws ElasticsearchStatusException, IOException {

		SearchResponse searchResponse;
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().size(1000);
		SearchRequest searchRequest = null;

		if (esRequest.getQueryBuilder() != null)
			sourceBuilder = sourceBuilder.query(esRequest.getQueryBuilder());

////		FieldSortBuilder sortBuilder = SortBuilders.fieldSort("caCreatedOn");
//		sortBuilder.order(SortOrder.DESC);
////		sourceBuilder = sourceBuilder.sort(sortBuilder);

//		  if (esRequest.getSortMap() != null) {
//			
//			
//			for (String key : esRequest.getSortMap().keySet()) {
//				sourceBuilder = sourceBuilder.sort(key, SortOrder.fromString(esRequest.getSortMap().get(key)));
//			}
//		}
		if (esRequest.getOffset() != null && esRequest.getNoOfRecords() != null) {
			sourceBuilder = sourceBuilder.size(esRequest.getNoOfRecords().intValue())
					.from(esRequest.getOffset().intValue());
		}

		if (esRequest.getIncludeFields() != null && esRequest.getIncludeFields().length > 0) {
			sourceBuilder = sourceBuilder.fetchSource(esRequest.getIncludeFields(), null);
		} else if (!esRequest.isFetchSource()) {
			sourceBuilder = sourceBuilder.fetchSource(false);
		}

		if (esRequest.getIndex() != null)
			searchRequest = new SearchRequest(esRequest.getIndex());
		else
			searchRequest = new SearchRequest(esRequest.getIndices());

		searchRequest.source(sourceBuilder).indicesOptions(IndicesOptions.lenientExpandOpen());

		searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

		EsResponse esResponse = new EsResponse();

		if (searchResponse.getHits() != null) {
			esResponse.setHits(searchResponse.getHits().getHits());

			System.out.println("TOTALHITS" + searchResponse.getHits().getTotalHits().value);
			esResponse.setHits(searchResponse.getHits().getHits());
//			esResponse.setTotalHits(2);
			esResponse.setTotalHits(searchResponse.getHits().getTotalHits().value);
		}
		return esResponse;
	}

	///////////////////------------------Synonyms test
//	 @Autowired
//	    private ElasticsearchRestTemplate elasticsearchRestTemplate;
//
//	    public void applySynonyms() {
//	        String indexName = "your_index_name"; // Replace with your index name
//	        String synonymsFilePath = "path_to_synonyms_file"; // Replace with the path to your synonyms file
//
//	        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName));
//	        Map<String, Object> settings = Map.of(
//	            "analysis.analyzer.custom_analyzer.type", "custom",
//	            "analysis.analyzer.custom_analyzer.tokenizer", "standard",
//	            "analysis.analyzer.custom_analyzer.filter", Arrays.asList("lowercase", "synonym_filter"),
//	            "analysis.filter.synonym_filter.type", "synonym",
//	            "analysis.filter.synonym_filter.synonyms_path", synonymsFilePath
//	        );
//
//	        indexOperations.createSettings(settings);
//	    }


//	public EsResponse getAggregations(EsRequest esRequest) throws ElasticsearchStatusException, IOException {
//		SearchResponse searchResponse;
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		SearchRequest searchRequest;
//		if (esRequest.getIndex() != null)
//			searchRequest = new SearchRequest(esRequest.getIndex());
//		else
//			searchRequest = new SearchRequest(esRequest.getIndices());
//
//		if (esRequest.getQueryBuilder() != null)
//			sourceBuilder = sourceBuilder.query(esRequest.getQueryBuilder());
//		for (AggregationBuilder aggregationBuilder : esRequest.getAggBuilder()) {
//			sourceBuilder.aggregation(aggregationBuilder);
//			searchRequest.source(sourceBuilder);
//		}
//		sourceBuilder.fetchSource(false);
//
//		searchRequest.source(sourceBuilder).indicesOptions(IndicesOptions.lenientExpandOpen());
//		searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//		
//		EsResponse esResponse = new EsResponse();
//		esResponse.setAggs(searchResponse.getAggregations());
//		return esResponse;
//	}
//
//	public String deleteESRecord(EsRequest esRequest,String docId) throws IOException {
//
//		DeleteRequest deleteRequest = new DeleteRequest().setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
//
//		if (docId != null) {
//			deleteRequest = deleteRequest.id(docId);
//		}
//		if (esRequest.getIndex() != null) {
//			deleteRequest = deleteRequest.index(esRequest.getIndex());
//		}
//
//		DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
//		return "ok";
////		deleteResponse.getResult().name().toLowerCase();
//	}
//
//	public String updateESRecord(EsRequest esRequest) throws IOException {
//
//		byte[] docByteArray = new ObjectMapper().writeValueAsBytes(esRequest.getDocObject());
//
//		UpdateRequest updateRequest = new UpdateRequest().index(esRequest.getIndex()).id(esRequest.getDocId())
//				.doc(docByteArray, XContentType.JSON);
//		if (esRequest.isRefreshPolicy())
//			updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
//
//		UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
//		return updateResponse.getResult().name();
//	}
//
//	public EsResponse insertIntoES(EsRequest esRequest) throws IOException {
//
//		byte[] docByteArray = new ObjectMapper().writeValueAsBytes(esRequest.getDocObject());
//		IndexRequest indexRequest = new IndexRequest();
//		indexRequest.index(esRequest.getIndex()).source(docByteArray, XContentType.JSON);
//
//		IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
//
//		EsResponse esResponse = new EsResponse();
//		esResponse.setMessage(indexResponse.getResult().name());
//		esResponse.setDocId(indexResponse.getId());
//		return esResponse;
//	}
//
//	public BulkItemResponse[] bulkUpdateESRecords(List<EsRequest> bulkESRequest) throws IOException {
//
//		BulkRequest bulkRequest = new BulkRequest();
//
//		for (EsRequest esRequest : bulkESRequest) {
//			byte[] docByteArray = new ObjectMapper().writeValueAsBytes(esRequest.getDocObject());
//			UpdateRequest updateRequest = new UpdateRequest().id(esRequest.getDocId())
//					.doc(docByteArray, XContentType.JSON).index(esRequest.getIndex());
//			bulkRequest.add(updateRequest);
//		}
//
//		BulkResponse updateResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//		return updateResponse.getItems();
//	}

//	 @Autowired
//	    private ElasticsearchClient elasticsearchClient;
//
//
//
//	    public String createOrUpdateDocument(PCModel pcModel,String indexName) throws IOException {
//
//	        co.elastic.clients.elasticsearch.core.IndexResponse response = elasticsearchClient.index(i -> i
//	                .index(indexName)
//	                .id(null)
//	                .document(pcModel)
//	        );
//	        if(response.result().name().equals("Created")){
//	            return new StringBuilder("Document has been successfully created.").toString();
//	        }else if(response.result().name().equals("Updated")){
//	            return new StringBuilder("Document has been successfully updated.").toString();
//	        }
//	        return new StringBuilder("Error while performing the operation.").toString();
//	    }
//
//	

}
