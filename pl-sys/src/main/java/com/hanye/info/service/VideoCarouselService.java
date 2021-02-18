package com.hanye.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Video;
import com.hanye.info.model.VideoCarousel;
import com.hanye.info.repository.VideoCarouselRepository;
import com.hanye.info.vo.ReturnVideoCarouselVO;
import com.hanye.info.vo.VideoCarouselVO;
import com.hanye.info.vo.VideoVO;

@Service
public class VideoCarouselService {
	
	@Autowired
	private VideoCarouselRepository videoCarouselRepository;
	
	private static BeanCopier voToEntity = BeanCopier.create(VideoCarouselVO.class, VideoCarousel.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(VideoCarousel.class, VideoCarouselVO.class, true);
	
	
	public ReturnVideoCarouselVO findAll() {
		try {
			List<VideoCarousel> videoCarouselList = 
					StreamSupport.stream(videoCarouselRepository.findAll().spliterator(), false).collect(Collectors.toList());
			VideoCarouselVO videoCarouselVO = new VideoCarouselVO();
			if(!CollectionUtils.isEmpty(videoCarouselList) && videoCarouselList.size() == 1) {
				entityToVo.copy(videoCarouselList.get(0), videoCarouselVO, new BeanConverter());
			}
			
			return new ReturnVideoCarouselVO("success","",videoCarouselVO);
		}catch (Exception e) {
			return new ReturnVideoCarouselVO("fail",e.getMessage(),null);
		}
	}
	
	public VideoCarouselVO findVideo(Long vid) {
		VideoCarousel videoCarousel = videoCarouselRepository.findById(vid).get();
		VideoCarouselVO videoCarouselVO = new VideoCarouselVO();
		entityToVo.copy(videoCarousel, videoCarouselVO, new BeanConverter());
		return videoCarouselVO;
	}

	public void saveVideo(VideoCarouselVO videoCarouselVO) {
		VideoCarousel videoCarousel = new VideoCarousel();
		voToEntity.copy(videoCarouselVO, videoCarousel, null);
		videoCarouselRepository.save(videoCarousel);
	}
}
