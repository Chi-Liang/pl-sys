package com.hanye.info.service;

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
import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.LatestInfo;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LatestInfoVO;
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
	
	public void saveCategory(KnowledgeArticleVO KnowledgeArticleVO) {
		KnowledgeArticle KnowledgeArticle = new KnowledgeArticle();
		voToEntity.copy(KnowledgeArticleVO, KnowledgeArticle, null);
		knowledgeArticleRepository.save(KnowledgeArticle);
	}
	
	public KnowledgeArticleVO findCategory(Long id) {
		KnowledgeArticle knowledgeArticle = knowledgeArticleRepository.findById(id).get();
		KnowledgeArticleVO knowledgeArticleVO = new KnowledgeArticleVO();
		entityToVo.copy(knowledgeArticle, knowledgeArticleVO, new BeanConverter());
		
		return knowledgeArticleVO;
	}
	
	public void editCategory(KnowledgeArticleVO knowledgeArticleVO) {
		KnowledgeArticle knowledgeArticle = knowledgeArticleRepository.findById(knowledgeArticleVO.getLid()).get();
		knowledgeArticle.setTitle(knowledgeArticleVO.getTitle());
		knowledgeArticle.setDetail(knowledgeArticleVO.getDetail());
		knowledgeArticleRepository.save(knowledgeArticle);
	}
	public void deleteCategory(Long id) {
		knowledgeArticleRepository.deleteById(id);
	}
	
}
