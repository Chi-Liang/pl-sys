package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.LatestInfo;

@Repository
public interface KnowledgeArticleRepository extends JpaRepository<KnowledgeArticle, Long> {
	
	@Query("SELECT a FROM KnowledgeArticle a ORDER BY a.createDate")
	List<KnowledgeArticle> findAllOrderByCreateDate();
}
