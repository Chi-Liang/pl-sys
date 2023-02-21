package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.hanye.info.model.ContactUs;
import com.hanye.info.model.ContractGroup;

@Repository
public interface ContractGroupRepository extends JpaRepository<ContractGroup, Long> {
	
	String sql = "select distinct b.group_id,b.group_name,b.content,b.file_name from contract a ,contract_group b"
			+ " where a.user_id =:mid and a.group_id = b.group_id";
	
	@Query(nativeQuery = true,value = sql)
	public List<ContractGroup> findContractByUserId(String mid);
	
}
