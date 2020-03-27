package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.PostVO;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;

	public int update(BlogVO blogVO) {
		return sqlSession.update("blog.update",blogVO);
	}

	public BlogVO findAll(String id) {
		return sqlSession.selectOne("blog.findAll",id);
	}

	public int insert(CategoryVO categoryVO) {
		return sqlSession.insert("blog.categoryInsert",categoryVO);
	}

	public List<CategoryVO> findCategoryById(String id) {
		List<CategoryVO> list = sqlSession.selectList("blog.findCategoryById",id);
		return list;
	}

	public int insert(PostVO postVO) {
		return sqlSession.insert("blog.postInsert",postVO);
	}

	public List<PostVO> findPostByNo(Long postNo) {
		List<PostVO> list = sqlSession.selectList("blog.findPostByNo",postNo);
		return list;
	}

	public PostVO findPostByPostNoAndCategoryNo(Long postNo, Long categoryNo) {
		Map<String,Object> map = new HashMap<>();
		map.put("postNo",postNo);
		map.put("categoryNo", categoryNo);
		PostVO postVO = sqlSession.selectOne("blog.findPostByPostNoAndCategoryNo",map);
		return postVO;
	}

	public int delete(Long categoryNo) {
		return sqlSession.delete("blog.delete",categoryNo);
	}

	public Integer deleteCategoryMin(String id) {
		int min = sqlSession.selectOne("blog.deleteCategoryMin",id);
		return min;
	}




	

	


	
}
