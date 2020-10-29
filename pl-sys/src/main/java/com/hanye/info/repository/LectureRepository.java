package com.hanye.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.Lecture;
import com.hanye.info.model.OnlineCourse;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {

}
