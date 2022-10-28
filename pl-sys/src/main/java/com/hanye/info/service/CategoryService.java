package com.hanye.info.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
import com.hanye.info.exception.PLException;
import com.hanye.info.exception.PLExceptionCode;
import com.hanye.info.model.Category;
import com.hanye.info.model.Member;
import com.hanye.info.model.Video;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.MemberRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.CategoryVO;
import com.hanye.info.vo.LoginVO;
import com.hanye.info.vo.ReturnCategoryVO;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private VideoRepository videoRepository;
	
	@Autowired
	private UploadPictureService uploadPictureService;
	
	private static BeanCopier voToEntity = BeanCopier.create(CategoryVO.class, Category.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(Category.class, CategoryVO.class, true);

	public List<CategoryVO> findAll() {
		List<Category> categoryList = 
				StreamSupport.stream(categoryRepository.findAll().spliterator(), false).collect(Collectors.toList());
		List<CategoryVO> voList = new ArrayList<CategoryVO>();
		for(Category category:categoryList) {
			CategoryVO vo = new CategoryVO();
			entityToVo.copy(category, vo, new BeanConverter());
			voList.add(vo);
		}
		
		return voList;
	}

	public CategoryVO findCategory(Long cid) {
		Category category = categoryRepository.findById(cid).get();
		CategoryVO categoryVO = new CategoryVO();
		entityToVo.copy(category, categoryVO, new BeanConverter());
		
		return categoryVO;
	}

	public void saveCategory(CategoryVO categoryVO) {
		Category category = new Category();
		voToEntity.copy(categoryVO, category, null);
		MultipartFile file = categoryVO.getFile();
		String fileName = "";
		
		try {
			if(!file.isEmpty()) {
				category.setPicture(file.getBytes());
				fileName = uploadPictureService.uploadPicture(file);
			}else{
				File file1 = new File("C:\\image\\small.jpg");
			   	InputStream inputStream = new FileInputStream(file1);
			   	MultipartFile multipartFile = new MockMultipartFile(file1.getName(), file1.getName(),
						"jpg", inputStream);
			   	category.setPicture(multipartFile.getBytes());
			   	fileName = "small.jpg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!StringUtils.isEmpty(fileName)) {
			category.setFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + fileName);
//			category.setFileName("http://localhost:8080/api/getPhoto/" + fileName);
		}
		categoryRepository.save(category);
	}
	
	public void editCategory(CategoryVO categoryVO) {
		Category category = categoryRepository.findById(categoryVO.getCid()).get();
		String tempFliename  = category.getFileName();
		byte[] tempPicture  = category.getPicture();
		category.setName(categoryVO.getName());
		category.setFileName(tempFliename);
		category.setPicture(tempPicture);
		category.setWhichWebSite(categoryVO.getWhichWebSite());
		MultipartFile file = categoryVO.getFile();
		String fileName = "";
		try {
			if(!file.isEmpty()) {
				category.setPicture(file.getBytes());
				fileName = uploadPictureService.uploadPicture(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(!StringUtils.isEmpty(fileName)) {
			category.setFileName("https://www.fundodo.net/pl-admin-test/api/getPhoto/" + fileName);
//			category.setFileName("http://localhost:8080/api/getPhoto/" + fileName);
		}	
		categoryRepository.save(category);
	}
	
	public void deleteCategory(Long cid) {
		Category category = new Category();
		category.setCid(cid);
		List<Video> videos = videoRepository.findByCategory(category);
		if(videos.size() > 0)
			throw new PLException(PLExceptionCode.CATEGORY_IS_USED);
		List<Member> members = memberRepository.findByCategories_Cid(cid);
		if(members.size() > 0)
			throw new PLException(PLExceptionCode.CATEGORY_IS_USED);
		
		categoryRepository.deleteById(cid);
	}
	
	public ReturnCategoryVO findCategoryByMember(String mid) {
		try {
			Member member = memberRepository.findById(mid).get();
			List<CategoryVO> voList = new ArrayList<CategoryVO>();
			Set<Category> categories = member.getCategories();
			for(Category category:categories) {
				CategoryVO vo = new CategoryVO();
				entityToVo.copy(category, vo, new BeanConverter());
				if(vo.getPicture() != null) {
				vo.setPictureUrl("https://www.fundodo.net/pl-admin-test/api/getPhotoCategory/" + vo.getCid());
//				vo.setPictureUrl("http://localhost:8080/api/getPhotoCategory/" + vo.getCid());
			}
				voList.add(vo);
			}
			
			return new ReturnCategoryVO("success","",voList);
			
		}catch(Exception e) {
			return new ReturnCategoryVO("fail",e.getMessage(),null);
		}
		
	}
}
