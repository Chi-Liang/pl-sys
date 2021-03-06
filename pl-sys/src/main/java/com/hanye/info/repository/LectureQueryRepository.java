package com.hanye.info.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.LatestInfo;
import com.hanye.info.model.LectureQuery;

@Repository
public interface LectureQueryRepository extends JpaRepository<LectureQuery, Long> {
	//List<LectureQuery> findByMid(String mid);
	
	@Query("SELECT a FROM LectureQuery a ORDER BY a.createDate DESC")
	List<LectureQuery> findAllOrderByCreateDate();
}
