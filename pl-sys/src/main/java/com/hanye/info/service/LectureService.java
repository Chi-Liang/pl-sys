package com.hanye.info.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.exception.PLException;
import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.model.Category;
import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.Lecture;
import com.hanye.info.model.Member;
import com.hanye.info.model.ModPicture;
import com.hanye.info.model.OnlineCourse;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.model.Video;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.LectureRepository;
import com.hanye.info.repository.ModPictureRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.ModPictureVO;
import com.hanye.info.vo.OnlineCourseVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnGroupVO;
import com.hanye.info.vo.ReturnLectureVO;

@Service
public class LectureService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private UploadPictureService uploadPictureService;
	@Autowired
	private ModPictureRepository modPictureRepository;
	
	private static BeanCopier voToEntity = BeanCopier.create(LectureVO.class, Lecture.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(Lecture.class, LectureVO.class, true);
	
	private static BeanCopier voToEntityPicture = BeanCopier.create(ModPictureVO.class, ModPicture.class, false);
	private static BeanCopier entityToVoPicture = BeanCopier.create(ModPicture.class, ModPictureVO.class, true);
	
	public List<LectureVO> findAll() {
		
			List<Lecture> lectureList = 
					StreamSupport.stream(lectureRepository.findAll().spliterator(), false).collect(Collectors.toList());
			List<LectureVO> voList = new ArrayList<LectureVO>();
			for(Lecture lecture:lectureList) {
				LectureVO vo = new LectureVO();
				entityToVo.copy(lecture, vo, new BeanConverter());
				voList.add(vo);
			}
			return voList;
	}
	
	public ReturnGroupVO findModPicture() {
		try {
			List<ModPicture> modPictureList = 
					StreamSupport.stream(modPictureRepository.findAll().spliterator(), false).collect(Collectors.toList());
			ModPictureVO vo = new ModPictureVO();
			if(!CollectionUtils.isEmpty(modPictureList)) {
				entityToVoPicture.copy(modPictureList.get(0), vo, new BeanConverter());
			}
			return new ReturnGroupVO("success","",vo);
			
		} catch (Exception e) {
			return new ReturnGroupVO("fail",e.getMessage(),null); 
		}
	}
	
	public ReturnLectureVO findFilterStartTime() {
		
		try {
			List<LectureVO> voList = findAll();
			Date now = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd 00:00:00"); 
		    String today = sdf.format(now);
			voList = voList
					 .stream()
					 .filter(c -> (c.getStartTime().compareTo(today))>=0 )
					 .collect(Collectors.toList());
			
			return new ReturnLectureVO("success","",voList);
		} catch (Exception e) {
			return new ReturnLectureVO("fail",e.getMessage(),null);
		}
		
	}
	
	public void saveCategory(LectureVO lectureVO) {
		Lecture lecture = new Lecture();
		voToEntity.copy(lectureVO, lecture, null);
//		setTime(lecture,lectureVO);
//		MultipartFile file = lectureVO.getFile();
//		String fileName = "";
//		try {
//			if(!file.isEmpty()) {
//				lecture.setPicture(file.getBytes());
//				fileName = uploadPictureService.uploadPicture(file);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(!StringUtils.isEmpty(fileName)) {
//			lecture.setFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + fileName);
////			lecture.setFileName("http://localhost:8080/api/getPhoto/" + fileName);
//		}	
		lectureRepository.save(lecture);
	}
	
	public void modPicture(ModPictureVO modPictureVO) {
		ModPicture modPicture = new ModPicture();
		if(modPictureVO.getId() != null) {
			modPicture = modPictureRepository.findById(modPictureVO.getId()).get();
		}else {
			voToEntityPicture.copy(modPictureVO, modPicture, null);
		}
		setModPicture(modPictureVO,modPicture);
		modPictureRepository.save(modPicture);
	}

	public LectureVO findCategory(Long id) {
		Lecture lecture = lectureRepository.findById(id).get();
		LectureVO lectureVO = new LectureVO();
		entityToVo.copy(lecture, lectureVO, new BeanConverter());
		
		return lectureVO;
	}
	
	public void editCategory(LectureVO lectureVO) {
		Lecture lecture = lectureRepository.findById(lectureVO.getId()).get();
//		setTime(lecture,lectureVO);
		lecture.setSession(lectureVO.getSession());
		lectureRepository.save(lecture);
	}
	public void deleteCategory(Long id) {
		lectureRepository.deleteById(id);
	}
	
	private void setModPicture(ModPictureVO modPictureVO,ModPicture modPicture) {
		try {
			if(!modPictureVO.getGroup1File().isEmpty()) {
				modPicture.setGroup1Picture(modPictureVO.getGroup1File().getBytes());
				String group1FileName = uploadPictureService.uploadPicture(modPictureVO.getGroup1File());
				modPicture.setGroup1FileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + group1FileName);
//				modPicture.setGroup1FileName("http://localhost:8080/api/getPhoto/" + group1FileName);
			}
			if(!modPictureVO.getGroup2File().isEmpty()) {
				modPicture.setGroup2Picture(modPictureVO.getGroup2File().getBytes());
				String group2FileName = uploadPictureService.uploadPicture(modPictureVO.getGroup2File());
				modPicture.setGroup2FileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + group2FileName);
//				modPicture.setGroup2FileName("http://localhost:8080/api/getPhoto/" + group2FileName);
			}
			modPicture.setGroup1Title(modPictureVO.getGroup1Title());
			modPicture.setGroup2Title(modPictureVO.getGroup2Title());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
//	private void setTime(Lecture lecture,LectureVO lectureVO) {
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm" );
//			lecture.setStartTime(sdf.parse(lectureVO.getStartTime()));
//			lecture.setEndTime(sdf.parse(lectureVO.getEndTime()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
