package com.hanye.info.service;

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
import org.springframework.util.StringUtils;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.exception.PLException;
import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.model.Category;
import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.Lecture;
import com.hanye.info.model.Member;
import com.hanye.info.model.OnlineCourse;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.model.Video;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.LectureRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.OnlineCourseVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnLectureVO;

@Service
public class LectureService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private LectureRepository lectureRepository;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(LectureVO.class, Lecture.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(Lecture.class, LectureVO.class, true);
	
	public ReturnLectureVO findAll() {
		
		try {
			List<Lecture> lectureList = 
					StreamSupport.stream(lectureRepository.findAll().spliterator(), false).collect(Collectors.toList());
			List<LectureVO> voList = new ArrayList<LectureVO>();
			for(Lecture lecture:lectureList) {
				LectureVO vo = new LectureVO();
				entityToVo.copy(lecture, vo, new BeanConverter());
				voList.add(vo);
			}
			return new ReturnLectureVO("success","",voList);
		} catch (Exception e) {
			return new ReturnLectureVO("fail",e.getMessage(),null);
		}
		
	}
	
	public void saveCategory(LectureVO lectureVO) {
		Lecture lecture = new Lecture();
		voToEntity.copy(lectureVO, lecture, null);
		setTime(lecture,lectureVO);
		lectureRepository.save(lecture);
	}
	
	public LectureVO findCategory(Long id) {
		Lecture lecture = lectureRepository.findById(id).get();
		LectureVO lectureVO = new LectureVO();
		entityToVo.copy(lecture, lectureVO, new BeanConverter());
		
		return lectureVO;
	}
	
	public void editCategory(LectureVO lectureVO) {
		Lecture lecture = lectureRepository.findById(lectureVO.getId()).get();
		setTime(lecture,lectureVO);
		lecture.setSession(lectureVO.getSession());
		lectureRepository.save(lecture);
	}
	public void deleteCategory(Long id) {
		lectureRepository.deleteById(id);
	}
	
	private void setTime(Lecture lecture,LectureVO lectureVO) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm" );
			lecture.setStartTime(sdf.parse(lectureVO.getStartTime()));
			lecture.setEndTime(sdf.parse(lectureVO.getEndTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
