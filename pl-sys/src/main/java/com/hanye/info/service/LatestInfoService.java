package com.hanye.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
				StreamSupport.stream(latestInfoRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<LatestInfoVO> voList = new ArrayList<LatestInfoVO>();
		for(LatestInfo latestNews:latestNewsList) {
			LatestInfoVO vo = new LatestInfoVO();
			entityToVo.copy(latestNews, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
//	public LectureVO findFormName(String formName) {
//		Lecture lecture = latestNewsRepository.findById(formName).get();
//		LectureVO lectureVO = new LectureVO();
//		entityToVo.copy(lecture, lectureVO, new BeanConverter());
//		return lectureVO;
//	}
//	
//	public void editPersonInfo(PersonInfoVO personInfoVO) {
//		PersonInfo personInfo = new PersonInfo();
//		voToEntity.copy(personInfoVO, personInfo, null);
//		personInfoRepository.save(personInfo);
//	}
//	
//	private void editOtherLoansStr(PersonInfoVO personInfoVO) {
//		String otherLoansStr = "";
//		if("1".equals(personInfoVO.getStudentLoan())) {
//			otherLoansStr += "學貸";
//		}
//		if("1".equals(personInfoVO.getCarLoan())) {
//			if(StringUtils.isEmpty(otherLoansStr)) {
//				otherLoansStr += "車貸";
//			}else {
//				otherLoansStr += "、車貸";
//			}
//		}
//		if("1".equals(personInfoVO.getHousingLoan())) {
//			if(StringUtils.isEmpty(otherLoansStr)) {
//				otherLoansStr += "房貸";
//			}else {
//				otherLoansStr += "、房貸";
//			}
//		}
//		if("1".equals(personInfoVO.getCreditLoan())) {
//			if(StringUtils.isEmpty(otherLoansStr)) {
//				otherLoansStr += "信貸";
//			}else {
//				otherLoansStr += "、信貸";
//			}
//		}
//		if("1".equals(personInfoVO.getOtherLoans())) {
//			if(StringUtils.isEmpty(otherLoansStr)) {
//				otherLoansStr += "其他";
//			}else {
//				otherLoansStr += "、 其他";
//			}
//		}
//		
//		if(StringUtils.isEmpty(otherLoansStr)) {
//			otherLoansStr = "無";
//		}
//		personInfoVO.setOtherLoansStr(otherLoansStr);
//	}
}
