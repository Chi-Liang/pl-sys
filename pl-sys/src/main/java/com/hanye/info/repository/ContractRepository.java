package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hanye.info.model.Contract;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
	
	public List<Contract> findByGroupId(Long groupId);
	
}
