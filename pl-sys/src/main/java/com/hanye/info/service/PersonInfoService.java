package com.hanye.info.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.PersonInfoVO;

@Service
public class PersonInfoService {
	
	@Autowired
	private VideoRepository videoRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PersonInfoRepository personInfoRepository;
	
	
	private static BeanCopier voToEntity = BeanCopier.create(PersonInfoVO.class, PersonInfo.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(PersonInfo.class, PersonInfoVO.class, true);
	
	public List<PersonInfoVO> findAll() {
		List<PersonInfo> personInfoList = 
				StreamSupport.stream(personInfoRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<PersonInfoVO> voList = new ArrayList<PersonInfoVO>();
		for(PersonInfo personInfo:personInfoList) {
			PersonInfoVO vo = new PersonInfoVO();
			entityToVo.copy(personInfo, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	public PersonInfoVO findMember(String mid) {
		PersonInfo personInfo = personInfoRepository.findById(mid).get();
		PersonInfoVO personInfoVO = new PersonInfoVO();
		entityToVo.copy(personInfo, personInfoVO, new BeanConverter());
		return personInfoVO;
	}
	
	public void editPersonInfo(PersonInfoVO personInfoVO) {
		PersonInfo personInfo = new PersonInfo();
		voToEntity.copy(personInfoVO, personInfo, null);
		personInfoRepository.save(personInfo);
	}
}
