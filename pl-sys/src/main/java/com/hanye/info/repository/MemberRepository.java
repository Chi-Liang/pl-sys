package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {
	
	List<Member> findByCategories_Cid(Long cid);
}
