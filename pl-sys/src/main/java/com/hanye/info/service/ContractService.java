package com.hanye.info.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import com.hanye.info.repository.ContractRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.ContactUsVO;
import com.hanye.info.vo.ContractGroupVO;
import com.hanye.info.vo.ContractVO;
import com.hanye.info.vo.ReturnVO;

@Service
public class ContractService {
	
	@Autowired
	private ContractRepository contractRepository;
	
	private static BeanCopier voToEntity = BeanCopier.create(ContractVO.class, Contract.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(Contract.class, ContractVO.class, true);
	
	public List<ContractVO> findByGroupId(Long groupId) {
		List<Contract> contractList = 
				StreamSupport.stream(contractRepository.findByGroupId(groupId).spliterator(), false).collect(Collectors.toList());
		List<ContractVO> voList = new ArrayList<ContractVO>();
		for(Contract contract:contractList) {
			ContractVO vo = new ContractVO();
			entityToVo.copy(contract, vo, new BeanConverter());
			voList.add(vo);
		}
		return voList;
	}
	
	
}
