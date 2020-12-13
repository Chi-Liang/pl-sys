package com.hanye.info.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hanye.info.convert.BeanConverter;
import com.hanye.info.model.Category;
import com.hanye.info.model.ContactUs;
import com.hanye.info.model.KnowledgeArticle;
import com.hanye.info.model.LatestInfo;
import com.hanye.info.model.Lecture;
import com.hanye.info.model.Member;
import com.hanye.info.model.OnlineCourse;
import com.hanye.info.model.PersonInfo;
import com.hanye.info.repository.CategoryRepository;
import com.hanye.info.repository.ContactUsRepository;
import com.hanye.info.repository.KnowledgeArticleRepository;
import com.hanye.info.repository.LatestInfoRepository;
import com.hanye.info.repository.LectureRepository;
import com.hanye.info.repository.PersonInfoRepository;
import com.hanye.info.repository.VideoRepository;
import com.hanye.info.vo.ContactUsVO;
import com.hanye.info.vo.KnowledgeArticleVO;
import com.hanye.info.vo.LatestInfoVO;
import com.hanye.info.vo.LectureVO;
import com.hanye.info.vo.MemberVO;
import com.hanye.info.vo.OnlineCourseVO;
import com.hanye.info.vo.PersonInfoVO;
import com.hanye.info.vo.ReturnVO;

@Service
public class UploadPictureService {
	
	public byte[] uploadPicture(String imgUrl) {
		File file = new File("C:/image/" + imgUrl );
		FileInputStream inputStream = null;
		byte[] bytes = null;
		try {
			inputStream = new FileInputStream(file);
			bytes = new byte[inputStream.available()];
			inputStream.read(bytes, 0, inputStream.available());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}
}
