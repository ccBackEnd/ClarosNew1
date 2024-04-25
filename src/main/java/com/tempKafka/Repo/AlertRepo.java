package com.tempKafka.Repo;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tempKafka.model.Alerts;

public interface AlertRepo extends ElasticsearchRepository<Alerts,String> {

	List<Alerts> findAllBySourceofalert(String string);


	List<Alerts> findAllBySourceofalertAndDateBetween(String graphNegative, Date date, Date date2);


	List<Alerts> findAllBySourceofalertAndDateGreaterThanEqual(String graphNegative, Date date);


	List<Alerts> findAllBySourceofalertAndDateLessThanEqual(String graphNegative, Date date);


	List<Alerts> findAllByDateBetween( Date fdate, Date tdate);


	List<Alerts> findAllByDateGreaterThanEqualAndDateLessThanEqual(Date fdate, Date tdate);


	List<Alerts> findAllByDescriptionStartingWithAndPriorityAndDateGreaterThanEqualAndDateLessThanEqual(String search,
			String priority, Date fdate, Date tdate);


	List<Alerts> findAllByDescriptionContainingAndPriorityAndDateGreaterThanEqualAndDateLessThanEqual(String search,
			String priority, Date fdate, Date tdate);


	List<Alerts> findAllByUseridIn(List<String> lst);


//	Optional<Alerts> findByUserid(String userId);

	
}
