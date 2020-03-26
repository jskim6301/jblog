package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	

	
	public Boolean join(UserVO vo) {
		int count = userRepository.insert(vo);
		return count == 3;
		
	}

	public UserVO getUser(UserVO vo) {
		return userRepository.findByIdAndPassword(vo);
	}

}


