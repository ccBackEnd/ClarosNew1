package com.tempKafka.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.ClarosUsers;

public interface ClarosUsersRepo extends ElasticsearchRepository<ClarosUsers,String>{

//	List<ClarosUsers> findAllByNameStartingWith(String name);
//
//	List<ClarosUsers> findAllByNameContaining(String name);

	List<ClarosUsers> findAllByUseridContaining(String userId);

	List<ClarosUsers> findAllByUserid(String string);

	List<ClarosUsers> findAllByName(String name);

	List<ClarosUsers> findAllByMonitored(boolean b);
	
	List<ClarosUsers> findAllIgnoreCaseByNameContainingIgnoreCase(String replace);

	List<ClarosUsers> findAllIgnoreCaseByUseridContainingIgnoreCase(String trim);

	Optional<ClarosUsers> findByUserid(String matchKey);

}
