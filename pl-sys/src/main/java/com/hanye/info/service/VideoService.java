package com.hanye.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Category;
import com.hanye.info.model.Video;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.ReturnVideoVO;
import com.hanye.info.vo.VideoVO;

@Service
public class VideoService {
	
	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private static BeanCopier voToEntity = BeanCopier.create(VideoVO.class, Video.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(Video.class, VideoVO.class, true);

	public List<VideoVO> findAll() {
		List<Video> videoList = 
				StreamSupport.stream(videoRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<VideoVO> voList = new ArrayList<VideoVO>();
		for(Video video:videoList) {
			VideoVO vo = new VideoVO();
			entityToVo.copy(video, vo, new BeanConverter());
			vo.setCname(video.getCategory().getName());
			voList.add(vo);
		}
		
		return voList;
	}

	public VideoVO findVideo(Long vid) {
		Video video = videoRepository.findById(vid).get();
		VideoVO videoVO = new VideoVO();
		entityToVo.copy(video, videoVO, new BeanConverter());
		videoVO.setCid(video.getCategory().getCid());
		
		return videoVO;
	}

	public void saveVideo(VideoVO videoVO) {
		Video video = new Video();
		voToEntity.copy(videoVO, video, null);
		Category category = categoryRepository.findById(videoVO.getCid()).get();
		video.setCategory(category);

		videoRepository.save(video);
	}
	
	public void editVideo(VideoVO videoVO) {
		Video video = videoRepository.findById(videoVO.getVid()).get();
		voToEntity.copy(videoVO, video, null);
		Category category = categoryRepository.findById(videoVO.getCid()).get();
		video.setCategory(category);
		
		videoRepository.save(video);
	}
	
	public void deleteVideo(Long vid) {
		videoRepository.deleteById(vid);
	}
	
	public ReturnVideoVO findVedioByCategory(Long cid) {
		
		try {
			Category category = categoryRepository.findById(cid).get();
			Video video = (Video)category.getVideos().toArray()[0];
			VideoVO vo = new VideoVO();
			entityToVo.copy(video, vo, new BeanConverter());
			vo.setCid(cid);
			vo.setCname(category.getName());
			return new ReturnVideoVO("success","",vo);
		}catch (Exception e) {
			return new ReturnVideoVO("fail",e.getMessage(),null);
		}
	}
	
}
