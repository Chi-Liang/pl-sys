package com.hanye.info.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hanye.info.constant.RoleEnum;
import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Role;
import com.hanye.info.model.User;
import com.hanye.info.repository.UserRepository;
import com.hanye.info.vo.UserVO;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private static BeanCopier voToEntity = BeanCopier.create(UserVO.class, User.class, false);
	private static BeanCopier entityToVo = BeanCopier.create(User.class, UserVO.class, true);

	public List<UserVO> findAllExcludeAdmin() {
		List<User> userList = 
				StreamSupport.stream(userRepository.findAll().spliterator(), false)
					.filter(user -> !user.getUid().equals("admin")).collect(Collectors.toList());
		List<UserVO> voList = new ArrayList<UserVO>();
		for(User user:userList) {
			UserVO vo = new UserVO();
			entityToVo.copy(user, vo, new BeanConverter());
			voList.add(vo);
		}
		
		return voList;
	}

	public UserVO findUser(String uid) {
		User user = userRepository.findById(uid).get();
		UserVO userVO = new UserVO();
		entityToVo.copy(user, userVO, new BeanConverter());
		
		return userVO;
	}

	public void saveUser(UserVO userVO) {
		User user = new User();
		voToEntity.copy(userVO, user, null);
		user.setPwd(new BCryptPasswordEncoder().encode(user.getPwd()));
		user.setCreateDate(new Date());
		user.getRoles().add(new Role(RoleEnum.ROLE_GENERAL.toString()));

		userRepository.save(user);
	}
	
	public void editUser(UserVO userVO) {
		User user = userRepository.findById(userVO.getUid()).get();
		user.setName(userVO.getName());
		user.setUpdateDate(new Date());
		
		userRepository.save(user);
	}
	
	public void changeUserPwd(UserVO userVO) {
		User user = userRepository.findById(userVO.getUid()).get();
		user.setPwd(new BCryptPasswordEncoder().encode(userVO.getPwd()));
		user.setUpdateDate(new Date());
		
		userRepository.save(user);
	}
	
	public void deleteUser(String uid) {
		userRepository.deleteById(uid);
	}
}
