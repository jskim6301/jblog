package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.UserVO;

@Repository
public class UserRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int insert(UserVO userVO) {
		int count = 0;
		count += count + sqlSession.insert("user.insert1",userVO);
		count += sqlSession.insert("user.insert2",userVO);
		count += sqlSession.insert("user.insert3",userVO);
		return count;
	}

	public UserVO findByIdAndPassword(UserVO userVO) {
		return sqlSession.selectOne("user.findByIdAndPassword",userVO);
	}

}
