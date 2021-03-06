package com.hanye.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hanye.info.model.ModPicture;

@Repository
public interface ModPictureRepository extends JpaRepository<ModPicture, Long> {
	
}
