package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.CategoryVO;

@RestController("CategoryApiController")
@RequestMapping("/{id:(?!assets).*}/api/category")
public class CategoryController {
	
	@Autowired
	private BlogService blogService;
	 
	
	@GetMapping("/list")
	public JsonResult list(@PathVariable("id") String id) {
		List<CategoryVO> list = blogService.getCategory(id);
		return JsonResult.success(list);
	}
	
	@PostMapping("/add")
	public JsonResult add(@PathVariable("id") String id,@RequestBody CategoryVO vo) {
		vo.setId(id);
		blogService.addCategory(vo);

		
		System.out.println("vo.getNo() >>>>  "+vo.getNo()); // 제일 최근 삽입 데이터  => select last_insert_id()
		System.out.println("vo >>>>" + vo);
		CategoryVO cateogoryVO = blogService.getCategory(vo.getNo());
		System.out.println(cateogoryVO);
		
		cateogoryVO.setTotalCategoryCnt(blogService.getTotalCateogoryCount(id));
		
		return JsonResult.success(cateogoryVO);
	}
	
}
