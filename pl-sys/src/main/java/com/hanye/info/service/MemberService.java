package com.hanye.info.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;
import com.hanye.info.convert.BeanConverter;
import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.model.Category;
import com.hanye.info.model.Member;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.MemberRepository;
import com.hanye.info.vo.ReturnVO;
import com.hanye.info.vo.CheckMemberVO;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.MemberVO;
import com.hanye.info.vo.ReturnLoginVO;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private static BeanCopier voToEntity = BeanCopier.create(MemberVO.class, Member.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(Member.class, MemberVO.class, true);

	public List<MemberVO> findAll() {
		List<Member> memberList = 
				StreamSupport.stream(memberRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<MemberVO> voList = new ArrayList<MemberVO>();
		for(Member member:memberList) {
			MemberVO vo = new MemberVO();
			Set<Category> categories = member.getCategories();
			entityToVo.copy(member, vo, new BeanConverter());
			StringBuffer categoryNames = new StringBuffer();
			for(Category c:categories) {
				categoryNames.append(c.getName()).append(";");
			}
			vo.setCategoryNames(categoryNames.toString());
			voList.add(vo);
		}
		
		return voList;
	}

	public MemberVO findMember(String mid) {
		Member member = memberRepository.findById(mid).get();
		MemberVO memberVO = new MemberVO();
		entityToVo.copy(member, memberVO, new BeanConverter());
		Set<Category> categories = member.getCategories();
		Iterator<Category> it = categories.iterator();
		Long[] categoryValues = new Long[categories.size()];
		int i = 0;
		while(it.hasNext()) {
			categoryValues[i] = it.next().getCid();
			i++;
		}
		
		memberVO.setCategories(categoryValues);
		
		return memberVO;
	}

	public void saveMember(MemberVO memberVO) {
		Member member = new Member();
		voToEntity.copy(memberVO, member, null);
		member.setPwd(new BCryptPasswordEncoder().encode(member.getPwd()));
		member.setCreateDate(new Date());
		Set<Category> categories = new HashSet<Category>();
		if(memberVO.getCategories() != null) {
			for(Long answer:memberVO.getCategories()) {
				Category category = categoryRepository.findById(answer).get();
				categories.add(category);
			}
		}
		member.setCategories(categories);
		memberRepository.save(member);
	}
	
	public void editMember(MemberVO memberVO) {
		Member member = memberRepository.findById(memberVO.getMid()).get();
		member.setName(memberVO.getName());
		Set<Category> categories = new HashSet<Category>();
		for(Long answer:memberVO.getCategories()) {
			Category category = categoryRepository.findById(answer).get();
			categories.add(category);
		}
		member.setCategories(categories);
		member.setTel(memberVO.getTel());
		member.setEmail(memberVO.getEmail());
		member.setAddress(memberVO.getAddress());
		member.setUpdateDate(new Date());
		member.setPoints(memberVO.getPoints());
		member.setFreeOrPaid(memberVO.getFreeOrPaid());
		member.setWhichGroup(memberVO.getWhichGroup());
		memberRepository.save(member);
	}
	
	public ReturnVO changeMemberPwd(MemberVO memberVO) {
		try {
			Member member = memberRepository.findById(memberVO.getMid()).get();
			member.setPwd(new BCryptPasswordEncoder().encode(memberVO.getPwd()));
			member.setUpdateDate(new Date());
			memberRepository.save(member);
			return new ReturnVO("success","");
		}catch(Exception e) {
			return new ReturnVO("fail",e.getMessage());
		}
	}
	
	public void deleteMember(String mid) {
		memberRepository.deleteById(mid);
	}
	
	public ReturnLoginVO checkMember(CheckMemberVO checkMemberVO) {
		try {
			Member member = memberRepository.findByJPQL(checkMemberVO.getMid());
			if(member == null) return new ReturnLoginVO("fail","帳號不存在",null);
			
			if(new BCryptPasswordEncoder().matches(
					checkMemberVO.getPwd().toString(), member.getPwd())) {
				MemberVO memberVO = new MemberVO();
				memberVO.setMid(member.getMid());
				memberVO.setName(member.getName());
				memberVO.setEmail(member.getEmail());
				memberVO.setTel(member.getTel());
				memberVO.setWhichGroup(member.getWhichGroup());
				return new ReturnLoginVO("success","",memberVO);
			}
			else
				return new ReturnLoginVO("fail","帳號或密碼輸入錯誤",null);
		}catch(Exception e) {
			return new ReturnLoginVO("fail",e.getMessage(),null);
		}
		
	}
	
	public ReturnVO addMember(MemberVO memberVO) {
		
		try {
			List<Member> memberList = 
					StreamSupport.stream(memberRepository.findAll().spliterator(), false).collect(Collectors.toList());
			for(Member member:memberList) {
				if(StringUtils.equals(member.getMid(),memberVO.getMid())) {
					return new ReturnVO("fail", PLExceptionCode.DUPLICATE_ACCOUNT.getMsg());
				}
				if(StringUtils.equals(member.getEmail(),memberVO.getEmail())) {
					return new ReturnVO("fail", PLExceptionCode.DUPLICATE_EMAIL.getMsg());
				}
			}
			memberVO.setFreeOrPaid("0");
			memberVO.setPoints("100");
			memberVO.setWhichGroup("1");
			saveMember(memberVO);
			return new ReturnVO("success", "");
			
		}catch (Exception e) {
			return new ReturnVO("fail", e.getMessage());
		}
	}

}
