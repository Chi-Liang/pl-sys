package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.LatestInfo;
import com.hanye.info.model.Lecture;
import com.hanye.info.model.OnlineCourse;
import com.hanye.info.vo.LatestInfoVO;

@Repository
public interface LatestInfoRepository extends CrudRepository<LatestInfo, Long> {
//	public List<LatestInfo> findAllByOrderByCreateDateAtDesc();
	
	@Query("SELECT a FROM LatestInfo a ORDER BY a.createDate")
	List<LatestInfo> findAllOrderByCreateDate();
}
