package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.jblog.vo.UserVO;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {


	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
		
		if(supportsParameter(parameter) == false) {
			return WebArgumentResolver.UNRESOLVED; //내가 해석할 수 있는 파라미터가 아니다
		}
		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest(); //톰캣관련
		
		HttpSession session = request.getSession(); //다른 곳에서 오는경우 검사 
		if(session == null) {
			return null;
		}
		return session.getAttribute("authUser"); // @Controller에 @AuthUser 값에 들어갈 곳
	}
	
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 안 붙어 있으면              
		if(authUser == null) {
			return false;
		}	
		
		//파라미터 타입이 UserVO인지 확인  =>  @AuthUser String   /   @AuthUser BoardVO 인 경우도 있으므로
		if(parameter.getParameterType().equals(UserVO.class)==false) { //파라미터 타입이 UserVO가 아니라면
			return false;
		}		
		return true;
	}
}
