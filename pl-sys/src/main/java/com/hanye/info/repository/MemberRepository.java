package com.hanye.info.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hanye.info.model.Member;
import com.hanye.info.vo.MemberVO;

@Repository
public interface MemberRepository extends CrudRepository<Member, String> {
	
	String sql = "select  m.mid, m.name, m.tel,m.email,m.address,m.create_date,m.update_date,m.free_or_paid,m.points,m.which_group,"
			+ " group_concat(c.name order by c.cid asc separator ';') as categoryNames"
			+ " from member m left join member_categroy mc on m.mid = mc.member_id left join category c on c.cid = mc.category_id"
			+ " group by m.mid";
	
	List<Member> findByCategories_Cid(Long cid);
	
	@Query(value="select * from member b where binary mid = ?1",nativeQuery = true)
	public Member findByJPQL(String mid);
	
	@Query(value = sql, nativeQuery = true)
	public List<Object[]> findAllByNativeSql();
}
