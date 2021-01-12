package com.hanye.info.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.mock.web.MockMultipartFile;
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
import com.hanye.info.vo.ReturnLatestInfoVO;

@Service
public class LatestInfoService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private LatestInfoRepository latestInfoRepository;
	@Autowired
	private UploadPictureService uploadPictureService;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(LatestInfoVO.class, LatestInfo.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(LatestInfo.class, LatestInfoVO.class, true);
	
	public ReturnLatestInfoVO findAll() {
		try {
			List<LatestInfo> latestNewsList = 
					StreamSupport.stream(latestInfoRepository.findAllOrderByCreateDate().spliterator(), false).collect(Collectors.toList());
			List<LatestInfoVO> voList = new ArrayList<LatestInfoVO>();
			for(LatestInfo latestNews:latestNewsList) {
				LatestInfoVO vo = new LatestInfoVO();
				entityToVo.copy(latestNews, vo, new BeanConverter());
				if(vo.getBigPicture() != null) {
					vo.setBigPictureUrl("https://www.fundodo.net/pl-admin-test/api/getBigPhotoLatestInfo/" + vo.getLid());
//					vo.setBigPictureUrl("http://localhost:8080/api/getBigPhotoLatestInfo/" + vo.getLid());
				}
				if(vo.getSmallPicture() != null) {
					vo.setSmallPictureUrl("https://www.fundodo.net/pl-admin-test/api/getSmallPhotoLatestInfo/" + vo.getLid());
//					vo.setSmallPictureUrl("http://localhost:8080/api/getSmallPhotoLatestInfo/" + vo.getLid());
				}
				voList.add(vo);
			}
			return new ReturnLatestInfoVO("success","",voList);
			
		}catch (Exception e) {
			return new ReturnLatestInfoVO("fail",e.getMessage(),null);
		}
	}
	
	public void saveCategory(LatestInfoVO latestInfoVO) {
		LatestInfo latestInfo = new LatestInfo();
		voToEntity.copy(latestInfoVO, latestInfo, null);
		latestInfo.setCreateDate(new Date());
		MultipartFile bigFile = latestInfoVO.getBigFile();
		MultipartFile smallFile = latestInfoVO.getSmallFile();
		String bigFileName = "" ;
		String smallFileName = "" ;
		try {
			if(!bigFile.isEmpty()) {
				latestInfo.setBigPicture(bigFile.getBytes());
				bigFileName = uploadPictureService.uploadPicture(bigFile);
			}else {
				File file1 = new File("C:\\image\\big.jpg");
			   	InputStream inputStream = new FileInputStream(file1);
			   	MultipartFile multipartFile = new MockMultipartFile(file1.getName(), file1.getName(),
						"jpg", inputStream);
			   	latestInfo.setBigPicture(multipartFile.getBytes());
			   	bigFileName = "big.jpg";
				
			}
			if(!smallFile.isEmpty()) {
				latestInfo.setSmallPicture(smallFile.getBytes());
				smallFileName = uploadPictureService.uploadPicture(smallFile);
			}else {
				File file2 = new File("C:\\image\\small.jpg");
			   	InputStream inputStream = new FileInputStream(file2);
			   	MultipartFile multipartFile = new MockMultipartFile(file2.getName(), file2.getName(),
						"jpg", inputStream);
			   	latestInfo.setSmallPicture(multipartFile.getBytes());
			   	smallFileName = "small.jpg";
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!StringUtils.isEmpty(bigFileName)) {
			latestInfo.setBigFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + bigFileName);
//			latestInfo.setBigFileName("http://localhost:8080/api/getPhoto/" + bigFileName);
		}
		if(!StringUtils.isEmpty(smallFileName)) {
			latestInfo.setSmallFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + smallFileName);
//			latestInfo.setSmallFileName("http://localhost:8080/api/getPhoto/" + smallFileName);
		}
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
		MultipartFile bigFile = latestInfoVO.getBigFile();
		MultipartFile smallFile = latestInfoVO.getSmallFile();
		String bigFileName = "" ;
		String smallFileName = "" ;
		try {
			if(!bigFile.isEmpty()) {
				latestInfo.setBigPicture(bigFile.getBytes());
				bigFileName = uploadPictureService.uploadPicture(bigFile);
			}
			if(!smallFile.isEmpty()) {
				latestInfo.setSmallPicture(smallFile.getBytes());
				smallFileName = uploadPictureService.uploadPicture(smallFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(bigFileName)) {
			latestInfo.setBigFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + bigFileName);
//			latestInfo.setBigFileName("http://localhost:8080/api/getPhoto/" + bigFileName);
		}
		if(!StringUtils.isEmpty(smallFileName)) {
			latestInfo.setSmallFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + smallFileName);
//			latestInfo.setSmallFileName("http://localhost:8080/api/getPhoto/" + smallFileName);
		}
		latestInfoRepository.save(latestInfo);
	}
	
	public void deleteCategory(Long id) {
		latestInfoRepository.deleteById(id);
	}
}
