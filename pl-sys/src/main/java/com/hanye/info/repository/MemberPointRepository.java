package com.hanye.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.Category;
import com.hanye.info.model.MemberPoint;

@Repository
public interface MemberPointRepository extends JpaRepository<MemberPoint, String> {

}
