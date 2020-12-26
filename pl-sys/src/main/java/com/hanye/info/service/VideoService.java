package com.hanye.info.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Category;
import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.Video;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.KnowledgeArticleVO;
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
		MultipartFile file = videoVO.getFile();
		String fileName = "";
		
		try {
			if(!file.isEmpty()) {
				video.setPicture(file.getBytes());
				fileName = uploadPicture(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!StringUtils.isEmpty(fileName)) {
			video.setFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + fileName);
//			video.setFileName("http://localhost:8080/api/getPhoto/" + fileName);
		}
		videoRepository.save(video);
	}
	
	public void editVideo(VideoVO videoVO) {
		Video video = videoRepository.findById(videoVO.getVid()).get();
		String tempFliename  = video.getFileName();
		byte[] tempPicture  = video.getPicture();
		voToEntity.copy(videoVO, video, null);
		video.setFileName(tempFliename);
		video.setPicture(tempPicture);
		Category category = categoryRepository.findById(videoVO.getCid()).get();
		video.setCategory(category);
		MultipartFile file = videoVO.getFile();
		String fileName = "";
		try {
			if(!file.isEmpty()) {
				video.setPicture(file.getBytes());
				fileName = uploadPicture(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(fileName)) {
			video.setFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + fileName);
//			video.setFileName("http://localhost:8080/api/getPhoto/" + fileName);
		}
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
			vo.setPictureUrl("https://www.fundodo.net/pl-admin-test/api/getPhotoVideo/" + vo.getVid());
//			vo.setPictureUrl("http://localhost:8080/api/getPhotoVideo/" + vo.getVid());
			return new ReturnVideoVO("success","",vo);
		}catch (Exception e) {
			return new ReturnVideoVO("fail",e.getMessage(),null);
		}
	}
	
	public VideoVO findCategory(Long id) {
		Video video = videoRepository.findById(id).get();
		VideoVO videoVO = new VideoVO();
		entityToVo.copy(video, videoVO, new BeanConverter());
		return videoVO;
	}
	
	private String uploadPicture(MultipartFile file) {
		if (file.isEmpty()) {
			return "";
		}
		String fileName = file.getOriginalFilename(); // 檔名
		String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 字尾名
		String filePath = "C:\\image\\"; // 上傳後的路徑
		fileName = UUID.randomUUID() + fileName; // 新檔名
		File dest = new File(filePath + fileName);
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	} 
	
}
