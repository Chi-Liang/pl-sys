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
import com.hanye.info.model.LectureQuery;
import com.hanye.info.model.Member;
import com.hanye.info.model.OnlineCourse;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.model.Video;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.LectureQueryRepository;
import com.hanye.info.repository.LectureRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LectureQueryVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.OnlineCourseVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnLectureVO;

@Service
public class LectureQueryService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private LectureQueryRepository lectureQueryRepository;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(LectureQueryVO.class, LectureQuery.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(LectureQuery.class, LectureQueryVO.class, true);
	
	public List<LectureQueryVO> findAll() {
		List<LectureQuery> lectureQueryList = 
				StreamSupport.stream(lectureQueryRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<LectureQueryVO> voList = new ArrayList<LectureQueryVO>();
		for(LectureQuery lectureQuery:lectureQueryList) {
			LectureQueryVO vo = new LectureQueryVO();
			entityToVo.copy(lectureQuery, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	public List<LectureQueryVO> findMember(String mid) {
		List<LectureQuery> lectureQueryList = lectureQueryRepository.findByMid(mid);
		List<LectureQueryVO> voList = new ArrayList<LectureQueryVO>();
		for(LectureQuery lectureQuery:lectureQueryList) {
			LectureQueryVO vo = new LectureQueryVO();
			entityToVo.copy(lectureQuery, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	public LoginVO save(LectureQueryVO lectureQueryVO) {
		try {
			LectureQuery lectureQuery = new LectureQuery();
			voToEntity.copy(lectureQueryVO, lectureQuery, null);
			lectureQueryRepository.save(lectureQuery);
			return new LoginVO("success","");
		}catch (Exception e) {
			return new LoginVO("fail",e.getMessage());
		}
	}
}
