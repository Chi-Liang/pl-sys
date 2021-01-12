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
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LatestInfoVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnKnowledgeArticleVO;
import com.hanye.info.vo.ReturnLatestInfoVO;

@Service
public class KnowledgeArticleService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private KnowledgeArticleRepository knowledgeArticleRepository;
	@Autowired
	private UploadPictureService uploadPictureService;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(KnowledgeArticleVO.class, KnowledgeArticle.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(KnowledgeArticle.class, KnowledgeArticleVO.class, true);
	
	public ReturnKnowledgeArticleVO findAll() {
		try {
			List<KnowledgeArticle> knowledgeArticleList = 
					StreamSupport.stream(knowledgeArticleRepository.findAllOrderByCreateDate().spliterator(), false).collect(Collectors.toList());
			List<KnowledgeArticleVO> voList = new ArrayList<KnowledgeArticleVO>();
			for(KnowledgeArticle knowledgeArticle:knowledgeArticleList) {
				KnowledgeArticleVO vo = new KnowledgeArticleVO();
				entityToVo.copy(knowledgeArticle, vo, new BeanConverter());
				if(vo.getBigPicture() != null) {
					vo.setBigPictureUrl("https://www.fundodo.net/pl-admin-test/api/getBigPhotoKnowledgeArticle/" + vo.getLid());
//					vo.setPictureUrl("http://localhost:8080/api/getBigPhotoKnowledgeArticle/" + vo.getLid());
				}
				if(vo.getSmallPicture() != null) {
					vo.setSmallPictureUrl("https://www.fundodo.net/pl-admin-test/api/getSmallPhotoKnowledgeArticle/" + vo.getLid());
//					vo.setPictureUrl("http://localhost:8080/api/getSmallPhotoKnowledgeArticle/" + vo.getLid());
				}
				voList.add(vo);
			}
			return new ReturnKnowledgeArticleVO("success","",voList);
		}catch (Exception e) {
			return new ReturnKnowledgeArticleVO("fail",e.getMessage(),null);
		}
	}
	
	public void saveCategory(KnowledgeArticleVO KnowledgeArticleVO) {
		KnowledgeArticle KnowledgeArticle = new KnowledgeArticle();
		voToEntity.copy(KnowledgeArticleVO, KnowledgeArticle, null);
		KnowledgeArticle.setCreateDate(new Date());
		MultipartFile bigFile = KnowledgeArticleVO.getBigFile();
		MultipartFile smallFile = KnowledgeArticleVO.getSmallFile();
		String bigFileName = "";
		String smallFileName = "";
		
		try {
			if(!bigFile.isEmpty()) {
				KnowledgeArticle.setBigPicture(bigFile.getBytes());
				bigFileName = uploadPictureService.uploadPicture(bigFile);
			}else {
				File file1 = new File("C:\\image\\big.jpg");
			   	InputStream inputStream = new FileInputStream(file1);
			   	MultipartFile multipartFile = new MockMultipartFile(file1.getName(), file1.getName(),
						"jpg", inputStream);
			   	KnowledgeArticle.setBigPicture(multipartFile.getBytes());
			   	bigFileName = "big.jpg";
			}
			if(!smallFile.isEmpty()) {
				KnowledgeArticle.setBigPicture(smallFile.getBytes());
				smallFileName = uploadPictureService.uploadPicture(smallFile);
			}else {
				File file2 = new File("C:\\image\\small.jpg");
			   	InputStream inputStream = new FileInputStream(file2);
			   	MultipartFile multipartFile = new MockMultipartFile(file2.getName(), file2.getName(),
						"jpg", inputStream);
			   	KnowledgeArticle.setBigPicture(multipartFile.getBytes());
			   	smallFileName = "small.jpg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!StringUtils.isEmpty(bigFileName)) {
			KnowledgeArticle.setBigFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + bigFileName);
//			KnowledgeArticle.setFileName("http://localhost:8080/api/getPhoto/" + bigFileName);
		}
		if(!StringUtils.isEmpty(smallFileName)) {
			KnowledgeArticle.setBigFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + smallFileName);
//			KnowledgeArticle.setFileName("http://localhost:8080/api/getPhoto/" + smallFileName);
		}
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
		MultipartFile bigFile = knowledgeArticleVO.getBigFile();
		String bigFileName = "";
		MultipartFile smallFile = knowledgeArticleVO.getSmallFile();
		String smallFileName = "";
		try {
			if(!bigFile.isEmpty()) {
				knowledgeArticle.setBigPicture(bigFile.getBytes());
				bigFileName = uploadPictureService.uploadPicture(bigFile);
			}
			if(!smallFile.isEmpty()) {
				knowledgeArticle.setBigPicture(smallFile.getBytes());
				smallFileName = uploadPictureService.uploadPicture(smallFile);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(bigFileName)) {
			knowledgeArticle.setBigFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + bigFileName);
//			knowledgeArticle.setFileName("http://localhost:8080/api/getPhoto/" + bigFileName);
		}
		if(!StringUtils.isEmpty(smallFileName)) {
			knowledgeArticle.setBigFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + smallFileName);
//			knowledgeArticle.setFileName("http://localhost:8080/api/getPhoto/" + smallFileName);
		}
		knowledgeArticleRepository.save(knowledgeArticle);
	}
	
	public void deleteCategory(Long id) {
		knowledgeArticleRepository.deleteById(id);
	}
	
}
