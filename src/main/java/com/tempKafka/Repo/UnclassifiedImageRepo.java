package com.tempKafka.Repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.UnclassifiedImage;

public interface UnclassifiedImageRepo extends ElasticsearchRepository<UnclassifiedImage,String> {

	List<UnclassifiedImage> findAllByUploadid(String userid);
	List<UnclassifiedImage> findAll();
	List<UnclassifiedImage> findAllByType(String type);
}
