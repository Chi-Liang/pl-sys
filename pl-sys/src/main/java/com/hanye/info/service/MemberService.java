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
import com.hanye.info.vo.AddMemberVO;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.MemberVO;

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
		
		memberRepository.save(member);
	}
	
	public void changeMemberPwd(MemberVO memberVO) {
		Member member = memberRepository.findById(memberVO.getMid()).get();
		member.setPwd(new BCryptPasswordEncoder().encode(memberVO.getPwd()));
		member.setUpdateDate(new Date());
		
		memberRepository.save(member);
	}
	
	public void deleteMember(String mid) {
		memberRepository.deleteById(mid);
	}
	
	public LoginVO checkMember(String mid, String pwd) {
		Optional<Member> member = memberRepository.findById(mid);
		if(member.isEmpty()) return new LoginVO("N");
		
		if(new BCryptPasswordEncoder().matches(
                pwd.toString(), member.get().getPwd()))
			return new LoginVO("Y");
		else
			return new LoginVO("N");
	}
	
	public AddMemberVO addMember(MemberVO memberVO) {
		List<Member> memberList = 
				StreamSupport.stream(memberRepository.findAll().spliterator(), false).collect(Collectors.toList());
		for(Member member:memberList) {
			if(StringUtils.equals(member.getMid(),memberVO.getMid())) {
				return new AddMemberVO("N", PLExceptionCode.DUPLICATE_ACCOUNT.getMsg());
			}
			if(StringUtils.equals(member.getEmail(),memberVO.getEmail())) {
				return new AddMemberVO("N", PLExceptionCode.DUPLICATE_EMAIL.getMsg());
			}
		}
		saveMember(memberVO);
		return new AddMemberVO("Y", "成功");
	}
	

}
