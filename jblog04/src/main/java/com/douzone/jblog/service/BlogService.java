package com.douzone.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVO;
import com.douzone.jblog.vo.CategoryVO;
import com.douzone.jblog.vo.PostVO;

@Service
public class BlogService {
	private static final String SAVE_PATH="/jblog-uploads";
	private static final String URL="/assets/upload-images/";
	@Autowired
	private BlogRepository blogRepository;
	
	public Boolean modifyContents(BlogVO blogVO) {
		return blogRepository.update(blogVO) == 1;
	}
	
	public String restore(MultipartFile multipartFile) {
		
		String url = null;
		if(multipartFile.isEmpty()) {
			return "1111";
		}
		
		try {
			String originalFilename = multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);
			
			String saveFileName = generateSaveFileName(extName);

			writeFile(multipartFile,saveFileName);
			url = URL + saveFileName;
		}catch(IOException ex) {
			throw new RuntimeException("file upload error:"+ex);
		}
		
		return url;
	}
	private String generateSaveFileName(String extName) {
		String fileName = "";
		Calendar calendar = Calendar.getInstance();
		fileName += calendar.get(Calendar.YEAR);
		fileName += calendar.get(Calendar.MONTH);
		fileName += calendar.get(Calendar.DATE);
		fileName += calendar.get(Calendar.HOUR);
		fileName += calendar.get(Calendar.MINUTE);
		fileName += calendar.get(Calendar.SECOND);
		fileName += calendar.get(Calendar.MILLISECOND);
		fileName += ("."+extName);
		return fileName;
	}

	private boolean writeFile(MultipartFile multipartFile,String saveFileName) throws IOException{
		boolean result = false;
		byte[] fileData = multipartFile.getBytes();
		OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFileName);
		os.write(fileData);
		os.close();
		return result;
	}
	
	public BlogVO getContents(String id) {
		BlogVO blogVO = blogRepository.findAll(id);
		return blogVO;
	}

	public boolean addCategory(CategoryVO vo) {
		return blogRepository.insert(vo) == 1;
		
	}

	public List<CategoryVO> getCategory(String id) {
		
		List<CategoryVO> categoryVO = blogRepository.findCategoryById(id);
		return categoryVO;
	}

	public boolean addContents(PostVO postVO) {
		return blogRepository.insert(postVO) == 1;
		
	}

	public List<PostVO> getPost(Long postNo) {
		List<PostVO> postVO = blogRepository.findPostByNo(postNo);
		return postVO;
	}

	public PostVO getPostContents(Long postNo, Long categoryNo) {
		PostVO postVO = blogRepository.findPostByPostNoAndCategoryNo(postNo,categoryNo);
		return postVO;
	}

	public boolean deleteCategory(Long categoryNo) {
		int count = blogRepository.delete(categoryNo);
		return count == 1;
		
	}

	public Integer deleteCategoryMin(String id) {
		Integer min = blogRepository.deleteCategoryMin(id);
		return min;
	}


}
