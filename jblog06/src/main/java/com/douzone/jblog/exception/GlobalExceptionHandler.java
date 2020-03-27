package com.douzone.jblog.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void handlerException(HttpServletRequest request,HttpServletResponse response,Exception e)throws Exception {
		
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		
		request.setAttribute("exception", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
	}
}