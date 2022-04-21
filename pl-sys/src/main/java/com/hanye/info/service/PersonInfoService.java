package com.hanye.info.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Member;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.MemberRepository;
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
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PersonInfoService personInfoService;

	private static BeanCopier voToEntity = BeanCopier.create(PersonInfoVO.class, PersonInfo.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(PersonInfo.class, PersonInfoVO.class, true);

	public List<PersonInfoVO> findAll() {
		List<PersonInfo> personInfoList = StreamSupport.stream(personInfoRepository.findAll().spliterator(), false)
				.map(p -> {
					if(p.getCommunicationAddress() != null && "同上".equals(p.getCommunicationAddress().strip())) {
						p.setCommunicationAddress(p.getResidenceAddress());
					}
					return p;
				}).collect(Collectors.toList());
		List<PersonInfoVO> voList = new ArrayList<PersonInfoVO>();
		for (PersonInfo personInfo : personInfoList) {
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
			if (StringUtils.isEmpty(mid)) {
				throw new RuntimeException("帳號為空");
			}

			for (PersonInfoVO personInfoVO : personInfoVOList) {
				if (mid.equals(personInfoVO.getMid())) {
					personInfo = personInfoVO;
					break;
				}
			}
			if (personInfo == null) {
				throw new RuntimeException("找不到個人資訊");
			}
			Member member = memberRepository.findById(mid).get();
			personInfo.setPoints(member.getPoints());
			return new ReturnPersonInfoVO("success", "", personInfo);
		} catch (Exception e) {
			return new ReturnPersonInfoVO("fail", e.getMessage(), null);
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
			return new LoginVO("success", "");
		} catch (Exception e) {
			return new LoginVO("fail", e.getMessage());
		}
	}

	private void editOtherLoansStr(PersonInfoVO personInfoVO) {
		String otherLoansStr = "";
		if ("1".equals(personInfoVO.getStudentLoan())) {
			otherLoansStr += "學貸";
		}
		if ("1".equals(personInfoVO.getCarLoan())) {
			if (StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "車貸";
			} else {
				otherLoansStr += "、車貸";
			}
		}
		if ("1".equals(personInfoVO.getHousingLoan())) {
			if (StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "房貸";
			} else {
				otherLoansStr += "、房貸";
			}
		}
		if ("1".equals(personInfoVO.getCreditLoan())) {
			if (StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "信貸";
			} else {
				otherLoansStr += "、信貸";
			}
		}
		if ("1".equals(personInfoVO.getOtherLoans())) {
			if (StringUtils.isEmpty(otherLoansStr)) {
				otherLoansStr += "其他";
			} else {
				otherLoansStr += "、 其他";
			}
		}

		if (StringUtils.isEmpty(otherLoansStr)) {
			otherLoansStr = "無";
		}
		personInfoVO.setOtherLoansStr(otherLoansStr);
	}

	public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("個人資料查詢");

		List<PersonInfoVO> personInfoList = personInfoService.findAll();
		personInfoList.sort((s1, s2) -> -s2.getMid().compareTo(s1.getMid()));
		Object[] titles = { "姓名", "通訊電話", "信箱", "通訊地址", "服務公司", "職位" };
		Row row = sheet.createRow(0);
		int colTitle = 0;
		Cell cell = null;
		for (Object title : titles) {
			cell = row.createCell(colTitle++);
			cell.setCellValue((String) title);
		}

		int rowNum = 1;
		for (PersonInfoVO personInfo : personInfoList) {
			int colNum = 0;
			row = sheet.createRow(rowNum++);
			cell = row.createCell(colNum++);
			cell.setCellValue(personInfo.getName() != null ? personInfo.getName() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(personInfo.getPhone()!= null ? personInfo.getPhone() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(personInfo.getEmail()!= null ? personInfo.getEmail() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(personInfo.getCommunicationAddress()!= null ? personInfo.getCommunicationAddress() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(personInfo.getCompany()!= null ? personInfo.getCompany() : "");

			cell = row.createCell(colNum++);
			cell.setCellValue(personInfo.getPosition()!= null ? personInfo.getPosition() : "");

		}

		String filename = "個人資料查詢.xlsx";
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
}
