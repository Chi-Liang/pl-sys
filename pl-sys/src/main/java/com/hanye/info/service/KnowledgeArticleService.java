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
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.PersonInfoVO;

@Service
public class KnowledgeArticleService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private KnowledgeArticleRepository knowledgeArticleRepository;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(KnowledgeArticleVO.class, KnowledgeArticle.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(KnowledgeArticle.class, KnowledgeArticleVO.class, true);
	
	public List<KnowledgeArticleVO> findAll() {
		List<KnowledgeArticle> knowledgeArticleList = 
				StreamSupport.stream(knowledgeArticleRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<KnowledgeArticleVO> voList = new ArrayList<KnowledgeArticleVO>();
		for(KnowledgeArticle knowledgeArticle:knowledgeArticleList) {
			KnowledgeArticleVO vo = new KnowledgeArticleVO();
			entityToVo.copy(knowledgeArticle, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	public KnowledgeArticleVO findMember(Long cid) {
		KnowledgeArticle knowledgeArticle = knowledgeArticleRepository.findById(cid).get();
		KnowledgeArticleVO personInfoVO = new KnowledgeArticleVO();
		entityToVo.copy(knowledgeArticle, personInfoVO, new BeanConverter());
		return personInfoVO;
	}
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
