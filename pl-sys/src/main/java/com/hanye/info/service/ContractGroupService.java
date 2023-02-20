package com.hanye.info.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Category;
import com.hanye.info.model.ContactUs;
import com.hanye.info.model.Contract;
import com.hanye.info.model.ContractGroup;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.ContactUsRepository;
import com.hanye.info.repository.ContractGroupRepository;
import com.hanye.info.repository.ContractRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.ContactUsVO;
import com.hanye.info.vo.ContractGroupVO;
import com.hanye.info.vo.ContractVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnVO;

@Service
public class ContractGroupService {
	
	@Autowired
	private ContractGroupRepository contractGroupRepository;
	
	@Autowired
	private ContractRepository contractRepository;
	
	private static BeanCopier voToEntity = BeanCopier.create(ContractGroupVO.class, ContractGroup.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(ContractGroup.class, ContractGroupVO.class, true);
	
	private static BeanCopier voToEntityContract = BeanCopier.create(ContractVO.class, Contract.class, false);
	private static BeanCopier entityToVoContract = BeanCopier.create(Contract.class, ContractVO.class, true);
	
	public List<ContractGroupVO> findAll() {
		List<ContractGroup> contractGroupList = 
				StreamSupport.stream(contractGroupRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<ContractGroupVO> voList = new ArrayList<ContractGroupVO>();
		for(ContractGroup contractGroup:contractGroupList) {
			ContractGroupVO vo = new ContractGroupVO();
			entityToVo.copy(contractGroup, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	public void saveCategory(ContractGroupVO contractGroupVO) {
		ContractGroup contractGroup = new ContractGroup();
		voToEntity.copy(contractGroupVO, contractGroup, null);
		setContractContent(contractGroupVO,contractGroup);
		contractGroupRepository.save(contractGroup);
		
		
//		Contract aa = new Contract();
//		aa.setGroupId(2L);
//		aa.setUserId("333");
//		MultipartFile file = contractGroupVO.getFile();
//		try {
//			if(!file.isEmpty()) {
//				aa.setContent(file.getBytes());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		contractRepository.save(aa);
		
	}
	
	public ContractGroupVO findContractGroup(Long groupId) {
		ContractGroup contractGroup = contractGroupRepository.findById(groupId).get();
		ContractGroupVO contractGroupVO = new ContractGroupVO();
		entityToVo.copy(contractGroup, contractGroupVO, new BeanConverter());
		
		return contractGroupVO;
	}
	
	public void editContractGroup(ContractGroupVO contractGroupVO) {
		ContractGroup contractGroup = contractGroupRepository.findById(contractGroupVO.getGroupId()).get();
		contractGroup.setGroupName(contractGroupVO.getGroupName());
		setContractContent(contractGroupVO,contractGroup);
		contractGroupRepository.save(contractGroup);
	}
	
	public List<ContractVO> findByGroupId(Long groupId) {
		List<Contract> contractList = 
				StreamSupport.stream(contractRepository.findByGroupId(groupId).spliterator(), false).collect(Collectors.toList());
		List<ContractVO> voList = new ArrayList<ContractVO>();
		for(Contract contract:contractList) {
			ContractVO vo = new ContractVO();
			entityToVoContract.copy(contract, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	
	public void downloadPdf(HttpServletRequest request, HttpServletResponse response,ContractVO contractVO) throws Exception {
		
		byte[] data = contractRepository.findById(contractVO.getGroupId()).get().getContent();
		
		String filename = contractVO.getUserId()  + ".pdf";
		String headerFileName = new String(filename.getBytes(), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + headerFileName);
		InputStream is = new ByteArrayInputStream(data);
		IOUtils.copy(is, response.getOutputStream());
		

	}
	
    public void downloadDocx(HttpServletRequest request, HttpServletResponse response,Long groupId) throws Exception {
		
		ContractGroup contractGroup = contractGroupRepository.findById(groupId).get();
		byte[] data = contractGroup.getContent();
		String groupName = contractGroup.getGroupName();
		
		String filename = groupName  + ".docx";
		String headerFileName = new String(filename.getBytes(), "ISO8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=" + headerFileName);
		InputStream is = new ByteArrayInputStream(data);
		IOUtils.copy(is, response.getOutputStream());
		

	}
	
	private void setContractContent(ContractGroupVO contractGroupVO, ContractGroup contractGroup) {
		
		MultipartFile file = contractGroupVO.getFile();
		try {
			if(!file.isEmpty()) {
				contractGroup.setContent(file.getBytes());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
