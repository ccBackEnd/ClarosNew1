package com.tempKafka.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.ImageSearchM;

public interface ImgRepository extends ElasticsearchRepository<ImageSearchM,String>{

	Optional<ImageSearchM> findFirstByUserid(String string);

	Optional<ImageSearchM> findFirstByOrderByUseridAsc();

	List<ImageSearchM> findAllByUserid(String userid);

	Optional<ImageSearchM> findFirstByUseridAndDisplayPicture(String string, boolean b);

//	List<ImageSearchM> findAllByUseridAndType(String userid, String string);



}
