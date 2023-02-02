package com.hanye.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hanye.info.model.ContactUs;
import com.hanye.info.model.ContractGroup;

@Repository
public interface ContractGroupRepository extends JpaRepository<ContractGroup, Long> {

}
