package com.tempKafka.Repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.SaveAudio;
import com.tempKafka.model.SaveImage;

public interface SaveImageRepo extends ElasticsearchRepository<SaveImage, String>{

	List<SaveImage> findAllByUserId(String userid);



}
