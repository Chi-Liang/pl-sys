package com.hanye.info.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.hanye.info.convert.BeanConverter;
import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.model.Category;
import com.hanye.info.model.Member;
import com.hanye.info.model.MemberPoint;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.MemberPointRepository;
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
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberPointRepository memberPointRepository;
	
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
	
	public List<MemberVO> findAllByNativeSql() {
		List<Object[]> voList = memberRepository.findAllByNativeSql();
		return voList.stream().map( vo -> {
			BeanConverter beanConverter = new BeanConverter();
			MemberVO memberVO = new MemberVO();
			memberVO.setMid((String)vo[0]);
			memberVO.setName((String)vo[1]);
			memberVO.setTel((String)vo[2]);
			memberVO.setEmail((String)vo[3]);
			memberVO.setAddress((String)vo[4]);
			memberVO.setCreateDate((String)beanConverter.convert(vo[5], null, null));
			memberVO.setUpdateDate((String)beanConverter.convert(vo[6], null, null));
			memberVO.setFreeOrPaid((String)vo[7]);
			memberVO.setPoints((String)vo[8]);
			memberVO.setWhichGroup((String)vo[9]);
			memberVO.setCategoryNames((String)vo[10]);
			return memberVO;
		}).collect(Collectors.toList());
		
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
		
		String contracts = member.getContracts();
		if(!StringUtils.isEmpty(contracts)) {
			String[] contractArray = contracts.split(",");
			memberVO.setContractGroups(contractArray);
		}
		
		memberVO.setCategories(categoryValues);
		
		return memberVO;
	}

	public void saveMember(MemberVO memberVO) {
		Member member = new Member();
		MemberPoint memberPoint = new MemberPoint();
		memberPoint.setMid(memberVO.getMid());
		memberPoint.setPoints(memberVO.getPoints());
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
		
		String contractGroups = setContractGroups(memberVO);
		member.setContracts(contractGroups);
		member.setCategories(categories);
		memberRepository.save(member);
		memberPointRepository.save(memberPoint);
	}
	
	public void editMember(MemberVO memberVO) {
		Member member = memberRepository.findById(memberVO.getMid()).get();
		member.setName(memberVO.getName());
		Set<Category> categories = new HashSet<Category>();
		for(Long answer:memberVO.getCategories()) {
			Category category = categoryRepository.findById(answer).get();
			categories.add(category);
		}
		
		String contractGroups = setContractGroups(memberVO);
		member.setContracts(contractGroups);
		member.setCategories(categories);
		member.setTel(memberVO.getTel());
		member.setEmail(memberVO.getEmail());
		member.setAddress(memberVO.getAddress());
		member.setUpdateDate(new Date());
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
		memberPointRepository.deleteById(mid);
	}
	
	public ReturnLoginVO checkMember(CheckMemberVO checkMemberVO,boolean isContract) {
		try {
			Member member = memberRepository.findByJPQL(checkMemberVO.getMid());
			
			if(member == null) return new ReturnLoginVO("fail","帳號不存在",null);
			
			if(isContract && StringUtils.isEmpty(member.getContracts())) {
				return new ReturnLoginVO("fail","合約書未開通",null);
			}
			
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
	
	
	public boolean checkExistMember() {
		long count = memberRepository.count();
		return count > 0;
	}
	
	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("註冊會員維護");

		List<MemberVO> memberList = memberService.findAllByNativeSql();
		memberList.sort((s1, s2) -> -s2.getMid().compareTo(s1.getMid()));
		Object[] titles = { "帳號", "名稱", "電話", "信箱", "住址", "影片群組","建立日期","修改日期","免費/付費","group1/group2" };
		Row row = sheet.createRow(0);
		int colTitle = 0;
		Cell cell = null;
		for (Object title : titles) {
			cell = row.createCell(colTitle++);
			cell.setCellValue((String) title);
		}

		int rowNum = 1;
		for (MemberVO member : memberList) {
			int colNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum++);
			cell.setCellValue(member.getMid() != null ? member.getMid() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(member.getName()!= null ? member.getName() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(member.getTel()!= null ? member.getTel() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(member.getEmail()!= null ? member.getEmail() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(member.getAddress()!= null ? member.getAddress() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(member.getCategoryNames()!= null ? member.getCategoryNames() : "");
			
			cell = row.createCell(colNum++);
			cell.setCellValue(member.getCreateDate()!= null ? member.getCreateDate() : "");
			
			cell = row.createCell(colNum++);
			cell.setCellValue(member.getUpdateDate()!= null ? member.getUpdateDate() : "");
			
			String freeOrPaid = "";
			if("0".equals(member.getFreeOrPaid())){
				freeOrPaid = "免費";
			}else if("1".equals(member.getFreeOrPaid())) {
				freeOrPaid = "付費";
			}
			cell = row.createCell(colNum++);
			cell.setCellValue(freeOrPaid);
			
			String whichGroup = "";
			if("1".equals(member.getWhichGroup())){
				whichGroup = "group1";
			}else if("2".equals(member.getWhichGroup())) {
				whichGroup = "group2";
			}
			cell = row.createCell(colNum++);
			cell.setCellValue(whichGroup);
			
		}

		String filename = "註冊會員維護.xlsx";
		String headerFileName = new String(filename.getBytes(), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + headerFileName);

		OutputStream out = null;
		try {
			out = response.getOutputStream();
			workbook.write(out);
		} catch (IOException e) {
			throw new RuntimeException("下載失敗");
		} finally {
			try {
				if(out != null) {
					out.close();
				}
				if(workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String setContractGroups(MemberVO memberVO) {
		String contractGroups = "";
		String[] contractArray = memberVO.getContractGroups();
		if(contractArray != null && contractArray.length > 0 ) {
			contractGroups = StringUtils.join(contractArray,",");
		}
		return contractGroups;
	}
	
}
