package com.hanye.info.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.LatestInfo;
import com.hanye.info.model.Lecture;
import com.hanye.info.model.OnlineCourse;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.LatestInfoRepository;
import com.hanye.info.repository.LectureRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LatestInfoVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.OnlineCourseVO;
import com.hanye.info.vo.PersonInfoVO;

@Service
public class LatestInfoService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private LatestInfoRepository latestInfoRepository;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(LatestInfoVO.class, LatestInfo.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(LatestInfo.class, LatestInfoVO.class, true);
	
	public List<LatestInfoVO> findAll() {
		List<LatestInfo> latestNewsList = 
				StreamSupport.stream(latestInfoRepository.findAllOrderByCreateDate().spliterator(), false).collect(Collectors.toList());
		List<LatestInfoVO> voList = new ArrayList<LatestInfoVO>();
		for(LatestInfo latestNews:latestNewsList) {
			LatestInfoVO vo = new LatestInfoVO();
			entityToVo.copy(latestNews, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	public void saveCategory(LatestInfoVO latestInfoVO) {
		LatestInfo latestInfo = new LatestInfo();
		voToEntity.copy(latestInfoVO, latestInfo, null);
		latestInfo.setCreateDate(new Date());
		latestInfoRepository.save(latestInfo);
	}
	
	public LatestInfoVO findCategory(Long id) {
		LatestInfo latestInfo = latestInfoRepository.findById(id).get();
		LatestInfoVO latestInfoVO = new LatestInfoVO();
		entityToVo.copy(latestInfo, latestInfoVO, new BeanConverter());
		
		return latestInfoVO;
	}
	
	public void editCategory(LatestInfoVO latestInfoVO) {
		LatestInfo latestInfo = latestInfoRepository.findById(latestInfoVO.getLid()).get();
		latestInfo.setTitle(latestInfoVO.getTitle());
		latestInfo.setDetail(latestInfoVO.getDetail());
		MultipartFile file = latestInfoVO.getFile();
		String fileName = uploadPicture(file);
		if(!StringUtils.isEmpty(fileName)) {
			latestInfo.setFileName(fileName);
		}
		latestInfoRepository.save(latestInfo);
	}
	
	private String uploadPicture(MultipartFile file) {
		if (file.isEmpty()) {
			return "";
		}
		String fileName = file.getOriginalFilename(); // 檔名
		String suffixName = fileName.substring(fileName.lastIndexOf(".")); // 字尾名
		String filePath = "C:\\image\\"; // 上傳後的路徑
		fileName = UUID.randomUUID() + suffixName; // 新檔名
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
	
	public void deleteCategory(Long id) {
		latestInfoRepository.deleteById(id);
	}
}
