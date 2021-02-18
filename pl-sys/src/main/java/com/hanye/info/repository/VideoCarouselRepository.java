package com.hanye.info.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hanye.info.model.VideoCarousel;

@Repository
public interface VideoCarouselRepository extends JpaRepository<VideoCarousel, Long> {
	
}
