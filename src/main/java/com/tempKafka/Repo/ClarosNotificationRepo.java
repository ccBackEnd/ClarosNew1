package com.tempKafka.Repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.ClarosNotification;


public interface ClarosNotificationRepo extends ElasticsearchRepository<ClarosNotification, String>{

	List<ClarosNotification> findAllByUserIdOrderByGeneratedAtDesc(String rolename);

}
