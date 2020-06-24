package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.Category;
import com.hanye.info.model.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
	
	List<Video> findByCategory(Category category);
}
