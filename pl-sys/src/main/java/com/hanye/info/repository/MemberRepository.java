package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {
	
	List<Member> findByCategories_Cid(Long cid);
	
	@Query(value="select * from member b where binary mid = ?1",nativeQuery = true)
	public Member findByJPQL(String mid);
}
