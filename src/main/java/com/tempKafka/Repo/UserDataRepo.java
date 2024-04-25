package com.tempKafka.Repo;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.UserData;


public interface UserDataRepo extends ElasticsearchRepository<UserData, String>{



//	List<UserData> findAllByUserAndInProgressAndType(String user, boolean b, String string);

	List<UserData> findAllByInProgressAndType(boolean b, String string);

}
