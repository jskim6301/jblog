package com.douzone.jblog.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.douzone.jblog.dto.JsonResult;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request,HttpServletResponse response,Exception e)throws Exception {
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		String accept = request.getHeader("accept");
		
		if( accept.matches(".*application/json.*")) {
			// 3.JSON 응답
			response.setStatus(HttpServletResponse.SC_OK); //헤더 부분 해결
			
			JsonResult jsonResult = JsonResult.fail(errors.toString());
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult); //new ObjectMapper() => jackson 라이브러리
			
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();			
		}		
		else {
			request.setAttribute("exception", errors.toString());
			request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
			
		}
	}
}
