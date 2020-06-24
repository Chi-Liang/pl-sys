package com.hanye.info.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.PersonInfo;

@Repository
public interface PersonInfoRepository extends JpaRepository<PersonInfo, String> {
	

}
