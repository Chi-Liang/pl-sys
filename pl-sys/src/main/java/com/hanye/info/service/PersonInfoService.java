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
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnPersonInfoVO;

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
	
	public ReturnPersonInfoVO findMid(String mid) {
		
		try {
			List<PersonInfoVO> personInfoVOList = findAll();
			PersonInfoVO personInfo = null;
			if(StringUtils.isEmpty(mid)) {
				throw new RuntimeException("帳號為空");
			}
			
			for (PersonInfoVO personInfoVO : personInfoVOList) {
				if(mid.equals(personInfoVO.getMid()) ) {
					personInfo = personInfoVO;
					break;
				}
			}
			if(personInfo == null) {
				throw new RuntimeException("找不到個人資訊");
			}
			
			return new ReturnPersonInfoVO("success","",personInfo);
		}catch (Exception e) {
			
			return new ReturnPersonInfoVO("fail",e.getMessage(),null);
		}
		
	}
	
	
	public PersonInfoVO findMember(String mid) {
		PersonInfo personInfo = personInfoRepository.findById(mid).get();
		PersonInfoVO personInfoVO = new PersonInfoVO();
		entityToVo.copy(personInfo, personInfoVO, new BeanConverter());
		editOtherLoansStr(personInfoVO);
		return personInfoVO;
	}
	
	public LoginVO editPersonInfo(PersonInfoVO personInfoVO) {
		try {
			PersonInfo personInfo = new PersonInfo();
			voToEntity.copy(personInfoVO, personInfo, null);
			personInfoRepository.save(personInfo);
			return new LoginVO("success","");
		}catch (Exception e) {
			return new LoginVO("fail",e.getMessage());
		}
	}
	
	private void editOtherLoansStr(PersonInfoVO personInfoVO) {
		String otherLoansStr = "";
		if("1".equals(personInfoVO.getStudentLoan())) {
			otherLoansStr += "學貸";
		}
		if("1".equals(personInfoVO.getCarLoan())) {
			if(StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "車貸";
			}else {
				otherLoansStr += "、車貸";
			}
		}
		if("1".equals(personInfoVO.getHousingLoan())) {
			if(StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "房貸";
			}else {
				otherLoansStr += "、房貸";
			}
		}
		if("1".equals(personInfoVO.getCreditLoan())) {
			if(StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "信貸";
			}else {
				otherLoansStr += "、信貸";
			}
		}
		if("1".equals(personInfoVO.getOtherLoans())) {
			if(StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "其他";
			}else {
				otherLoansStr += "、 其他";
			}
		}
		
		if(StringUtils.isEmpty(otherLoansStr)) {
			otherLoansStr = "無";
		}
		personInfoVO.setOtherLoansStr(otherLoansStr);
	}
}
