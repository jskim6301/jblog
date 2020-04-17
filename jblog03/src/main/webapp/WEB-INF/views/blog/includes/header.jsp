<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">

		<div id="header">
			<h1>${blogVO.title }</h1>
			<ul>
			<c:choose>
				<c:when test="${empty authUser }">			
					<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
				</c:when>
				<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
				</c:otherwise>
			</c:choose>
				<li><a href="${pageContext.request.contextPath}/${authUser.id}">내블로그</a></li>
			</ul>
		</div>