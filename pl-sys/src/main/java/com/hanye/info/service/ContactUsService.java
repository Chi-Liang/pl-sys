package com.hanye.info.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Category;
import com.hanye.info.model.ContactUs;
import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.LatestInfo;
import com.hanye.info.model.Lecture;
import com.hanye.info.model.Member;
import com.hanye.info.model.OnlineCourse;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.ContactUsRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.LatestInfoRepository;
import com.hanye.info.repository.LectureRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.ContactUsVO;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LatestInfoVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.MemberVO;
import com.hanye.info.vo.OnlineCourseVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnVO;

@Service
public class ContactUsService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ContactUsRepository contactUsRepository;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(ContactUsVO.class, ContactUs.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(ContactUs.class, ContactUsVO.class, true);
	
	public List<ContactUsVO> findAll() {
		List<ContactUs> latestNewsList = 
				StreamSupport.stream(contactUsRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<ContactUsVO> voList = new ArrayList<ContactUsVO>();
		for(ContactUs latestNews:latestNewsList) {
			ContactUsVO vo = new ContactUsVO();
			entityToVo.copy(latestNews, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	public ContactUsVO findMember(Long lid) {
		ContactUs contactUs = contactUsRepository.findById(lid).get();
		ContactUsVO contactUsVO = new ContactUsVO();
		entityToVo.copy(contactUs, contactUsVO, new BeanConverter());
		return contactUsVO;
	}

	public ReturnVO addContactUs(ContactUsVO contactUsVO) {
		ContactUs contactUs = new ContactUs();
		voToEntity.copy(contactUsVO, contactUs, null);
		contactUs.setDetail(contactUsVO.getDetail());
		contactUs.setTitle(contactUsVO.getTitle());
		contactUs.setCreateDate(new Date());
		Set<Category> categories = new HashSet<Category>();
		contactUsRepository.save(contactUs);
		return new ReturnVO("Y", "成功");
	}
	
}
