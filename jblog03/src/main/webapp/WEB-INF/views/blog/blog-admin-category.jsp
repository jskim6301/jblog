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
		<c:import url="/WEB-INF/views/blog/includes/header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/${authUser.id }/blog/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id }/blog/write">글작성</a></li>
				</ul>

				<c:set var ="cnt" value='${fn:length(categoryList) }'/>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
					<c:forEach items='${categoryList }' var='vo' varStatus='status'>
						<tr>
							<td>${cnt-status.index }</td>
							<td>${vo.name }</td>
							<td>${vo.categoryCnt }</td>
							<td>${vo.description }</td>	
							
							<td>
								<c:if test="${vo.categoryCnt == 0 && vo.no != categoryVoMin }">
									<a href="${pageContext.request.contextPath}/${authUser.id }/blog/category/delete/${vo.no}"><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a>
								</c:if>
							</td>	
						</tr>
					</c:forEach>
				</table>
      	
   		   		<form action="${pageContext.request.contextPath}/${authUser.id }/blog/category" method="post">
	      			<h4 class="n-c">새로운 카테고리 추가</h4>
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input type="text" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" name="description"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table> 
		      	</form>
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
		
	</div>
</body>
</html>