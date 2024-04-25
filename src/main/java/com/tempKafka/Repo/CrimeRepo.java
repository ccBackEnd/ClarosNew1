package com.tempKafka.Repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.Crime;

public interface CrimeRepo extends ElasticsearchRepository<Crime,String>{

}
