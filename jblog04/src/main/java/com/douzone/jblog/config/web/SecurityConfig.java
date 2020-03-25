package com.douzone.jblog.config.web;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.douzone.security.AuthUserHandlerMethodArgumentResolver;
import com.douzone.security.LoginInterceptor;
import com.douzone.security.LogoutInterceptor;

@Configuration
public class SecurityConfig extends WebMvcConfigurerAdapter {
	
	//Argument Resolver
	@Bean
	public HandlerMethodArgumentResolver authUserHandlerMethodArgumentResolver() {
		return new AuthUserHandlerMethodArgumentResolver();
	}
/*
	<!-- validator, conversionService, messageConverter를 자동으로 등록 -->
	<mvc:annotation-driven >
		<mvc:argument-resolvers>
			<bean class="com.douzone.security.AuthUserHandlerMethodArgumentResolver" />
		</mvc:argument-resolvers>
	</mvc:annotation-driven>	
 */

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(authUserHandlerMethodArgumentResolver());
	}
	
	//interceptors
	@Bean
	public HandlerInterceptorAdapter loginInterceptor() {
		return new LoginInterceptor();
	}
	
	@Bean
	public LogoutInterceptor logoutInterceptor() {
		return new LogoutInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor())
		.addPathPatterns("/user/auth");
		
		registry.addInterceptor(logoutInterceptor())
		.addPathPatterns("/user/logout");
	}	
	
	
}
