<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
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
				<c:if test="${authUser.id != null }">
				<li><a href="${pageContext.request.contextPath}/${authUser.id }/blog/basic">블로그 관리</a></li>
				</c:if>
			</ul>
			
		</div>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>Spring Camp 2016 참여기</h4>
					
					
					<p>
						${postList2.contents } 
					<p>
				</div>
				<c:forEach items='${postList }' var='vo2' varStatus='status'>
				<ul class="blog-list">
					<li><a href="${pageContext.request.contextPath}/${vo2.id }/${vo2.categoryNo}/${vo2.no}">${vo2.title }</a> <span>${vo2.regDate }</span></li>

				</ul>
				</c:forEach>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVO.logo }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>			
			<c:forEach items='${categoryList }' var='vo' varStatus='status'>
				<ul>
					<li><a href="${pageContext.request.contextPath}/${vo.id }/${vo.no}">${vo.name }</a></li>
				</ul>
			</c:forEach> 
		</div>
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
	</div>
</body>
</html>