package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.PostVO;
import com.douzone.jblog.vo.UserVO;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/{id:(?!assets).*}" )
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@RequestMapping( {"", "/{pathNo1}", "/{pathNo1}/{pathNo2}" } )
	public String main(
			@PathVariable("id") Optional<String> id,
			@PathVariable Optional<Long> pathNo1,
			@PathVariable Optional<Long> pathNo2,Model model) {
		
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if(pathNo2.isPresent()) {
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
			PostVO postList2 = blogService.getPostContents(postNo,categoryNo);
			model.addAttribute("postList2",postList2);
			
			List<PostVO> postList = blogService.getPost(categoryNo);
			model.addAttribute("postList",postList);
		}else if(pathNo1.isPresent()) {
			postNo = pathNo1.get();
			List<PostVO> postList = blogService.getPost(postNo);
			model.addAttribute("postList",postList);
		}//

		BlogVO blogVO = blogService.getContents(id.get());
		model.addAttribute("blogVO",blogVO);
		
		List<CategoryVO> categoryList = blogService.getCategory(id.get());
		model.addAttribute("categoryList",categoryList);

		return "blog/blog-main";
	}
	
	@RequestMapping(value="/blog/basic",method=RequestMethod.GET)
	public String basic(@AuthUser UserVO authUser,Model model,
			@PathVariable("id") Optional<String> id) {
		if(authUser == null) {
			return "redirect:/user/login";
		}		
		
		BlogVO blogVO = blogService.getContents(id.get());
		model.addAttribute("blogVO",blogVO);
		
		return "blog/blog-admin-basic";
	}
	@RequestMapping(value="/blog/basic",method=RequestMethod.POST)
	public String basic(@AuthUser UserVO authUser,@ModelAttribute BlogVO blogVO) {
		if(authUser == null) {
			return "redirect:/user/login";
		}	
		
		String url = blogService.restore(blogVO.getLogoFile());
		blogVO.setLogo(url);
		blogVO.setId(authUser.getId());
		blogService.modifyContents(blogVO);
		
		return "blog/blog-admin-basic";
	}	
	
	@RequestMapping(value="/blog/category",method=RequestMethod.GET)
	public String category(@PathVariable("id") Optional<String> id,@AuthUser UserVO authUser,Model model) {
		if(authUser == null) {
			return "redirect:/user/login";
		}
		
		BlogVO blogVO = blogService.getContents(id.get());
		model.addAttribute("blogVO",blogVO);
		
		
		
		
		Integer categoryVoMin = blogService.deleteCategoryMin(authUser.getId());
		model.addAttribute("categoryVoMin",categoryVoMin);
		
		List<CategoryVO> categoryList = blogService.getCategory(id.get());
		
		model.addAttribute("categoryList",categoryList);
		return "blog/blog-admin-category";
	}
	
	@RequestMapping(value="/blog/category",method=RequestMethod.POST)
	public String category(@AuthUser UserVO authUser,@ModelAttribute CategoryVO categoryVO) {
		if(authUser == null) {
			return "redirect:/user/login";
		}
		categoryVO.setId(authUser.getId());
		blogService.addCategory(categoryVO);
		
		return "redirect:/"+authUser.getId()+"/blog/category";
	}	
	
	@RequestMapping(value="/blog/category/delete/{no}",method=RequestMethod.GET)
	public String categoryDelete(
			@AuthUser UserVO authUser,
			@PathVariable("no") Long CategoryNo,
			Model model) {
		if(authUser == null) {
			return "redirect:/user/login";
		}

		blogService.deleteCategory(CategoryNo);
		return "redirect:/"+authUser.getId()+"/blog/category";
	}
	
	@RequestMapping(value="/blog/write",method=RequestMethod.GET)
	public String write(@PathVariable("id") Optional<String> id,@AuthUser UserVO authUser,Model model) {
		if(authUser == null) {
			return "redirect:/user/login";
		}	
		
		BlogVO blogVO = blogService.getContents(id.get());
		model.addAttribute("blogVO",blogVO);
		
		
		
		List<CategoryVO> categoryList = blogService.getCategory(id.get());
		model.addAttribute("categoryList",categoryList);
		return "blog/blog-admin-write";
	}
	
	@RequestMapping(value="/blog/write",method=RequestMethod.POST)
	public String write(@AuthUser UserVO authUser,@ModelAttribute PostVO postVO) {
		if(authUser == null) {
			return "redirect:/user/login";
		}	

		blogService.addContents(postVO);
		
		return "redirect:/"+authUser.getId()+"/blog/write";
	}
	
}

