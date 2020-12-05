package com.hanye.info.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

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
		
		categoryRepository.save(category);
	}
	
	public void editCategory(CategoryVO categoryVO) {
		Category category = categoryRepository.findById(categoryVO.getCid()).get();
		category.setName(categoryVO.getName());
		
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
				voList.add(vo);
			}
			
			return new ReturnCategoryVO("success","",voList);
			
		}catch(Exception e) {
			return new ReturnCategoryVO("fail",e.getMessage(),null);
		}
		
	}
}
