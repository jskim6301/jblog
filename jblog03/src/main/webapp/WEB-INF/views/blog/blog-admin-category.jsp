<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-3.4.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript">
var startNo = 0;

var listItemTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-item-template.ejs"
});

var listTemplate = new EJS({
	url: "${pageContext.request.contextPath }/assets/js/ejs/list-template.ejs"
}); 


var messageBox = function(title,message,callback){
	$('#dialog-message p').text(message);
	$('#dialog-message')
		.attr("title",title)
		.dialog({
			modal:true,
			buttons:{
				"OK" : function(){
					$(this).dialog("close");
				}
			},
			close:callback
		});
}

var fetchList = function(){
		
		$.ajax({
 			url:'${pageContext.request.contextPath }/${authUser.id}/api/category/list',
 			async:true,
 			type:'get',
 			dataType:'json',
 			data:'',
 			success: function(response){
 				
  				var contextPath= '${pageContext.request.contextPath }';
 				response.data.contextPath=contextPath;
 				
 				
   				var imgUrl= '${pageContext.request.contextPath}/assets/images/delete.jpg';
 				response.data.imgUrl = imgUrl;
 				
 				
 	 			var html = listTemplate.render(response);
 				$('.admin-cat').append(html); 
 				
 				/*
 				var a = $('.admin-cat tr:nth-child(2) td')[0];
				console.log(a);
				var b = $('.admin-cat tr:first-child td')[0]; //=> first는 th 부분
				console.log("b>> ",b);
				
				var firstRow = $('.admin-cat tr:last-child td')[0];
				console.log(firstRow);
				*/
 			},
 			error: function(xhr,status,e){
 				console.error(status + ":" + e);
 			}
 		});	
}


	
$(function(){

	//삭제 dialog
	var dialogDelete = $("#dialog-delete-form").dialog({
		autoOpen: false,
		width:300,
		height:220,
		modal:true,
		buttons:{
			"삭제" : function(){
				var no = $('#hidden-no').val();
				$.ajax({
					url: "${pageContext.request.contextPath }/${authUser.id}/api/category/delete/"+ no,
					async:true,
					type: 'delete',
					dataType:'json',
					data:'',
					success: function(response){
						if(response.result != "success"){
							console.error(response.message);
							return;
						}
						
						if(response.data != -1){
							console.log("this >>>> ",this);
							$('.admin-cat a[href='+response.data+']').parents('tr').remove();
							/* $('.admin-cat a[data-no='+response.data+']').parents('tr').remove(); */
							/* $('.admin-cat tr:nth-child(2) td').remove();  안됨 => 다시 삭제할 경우 오류*/
							/* $('.admin-cat a[data-no='+response.data+']').remove(); a태그만 삭제 */
							$('.admin-cat td').remove();
							fetchList();
							dialogDelete.dialog('close');
							return;
						}
						
						
						
						$('#dialog-delete-form p.validateTips.error').show();
					},
					error:function(xhr,status,e){
						console.error(status+ ":" + e);
					}
				});
			},
			"취소" : function(){
				$(this).dialog("close");
			} 
		},
		close: function(){
			
		}
	});
	
	
	$('#add-form').submit(function(event){
		event.preventDefault();
		console.log("clicked!!!");
		
		var vo = {};
		
		vo.name = $('#input-category').val();
		if(vo.name == ''){
			//alert("카테고리 명을 입력하세요");
			messageBox("카테고리 입력","카테고리 명이 비어있습니다!!",function(){
				$('#input-category').focus();
			});
			return;
		}
		
		vo.description = $('#input-description').val();
		if(vo.description == ''){
			//alert("설명을 입력하세요");
			messageBox("설명 입력","설명이 비어있습니다!!",function(){
				$('#input-description').focus();
			});
			return;			
		}
		
 		$.ajax({
			url:'${pageContext.request.contextPath }/${authUser.id}/api/category/add',
			async: true,
			type: 'post',
			dataType: 'json',
			contentType:'application/json',
			data: JSON.stringify(vo),
			success:function(response){
				
 				if(response.result != "success"){
					console.error(response.message);
					return;
				}
 				
 				console.log(response.data);
  				var imgUrl= '${pageContext.request.contextPath}/assets/images/delete.jpg';
 				response.data.imgUrl = imgUrl;
 				
 				
 				var html = listItemTemplate.render(response.data);
 				
 				$('#categoryList').after(html);
 				
 				
 				//form reset
 				$("#add-form")[0].reset();
 				
			},
			error: function(xhr,status,e){
				console.error(status + ":" + e);
			}
		});
 		
	});
	
		
	//처음 리스트가져오기
	fetchList();
	
	
	$(document).on('click','.admin-cat a',function(event){
		event.preventDefault();
		
		//console.log($(this).parents('tr').children(":first")[0].innerText); //문자열 no값임
		
		//console.log($(this).parents('tr')[0].childNodes(1));
		//console.log($(this).parents('tr')[0].attr("href"));
		
		//console.log($(this).attr("href"));
		
		
		var no = $(this).attr("href");
		console.log(no);
		/* var no = $(this).data('no'); */
		
		$('#hidden-no').val(no);
		
		dialogDelete.dialog("open");
	});
	
});



</script>
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
		      		<tr id="categoryList">
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
					
<%--    					<c:forEach items='${categoryList }' var='vo' varStatus='status'>
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
					</c:forEach>    --%>
				</table>

   		   		<form id="add-form" action="" method="">
	      			<h4 class="n-c">새로운 카테고리 추가</h4>
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="t">카테고리명</td>
			      			<td><input id="input-category" type="text" name="name"></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input id="input-description" type="text" name="description"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table> 
		      	</form>


			<div id="dialog-delete-form" class="delete-form" title="메세지 삭제" style="display:none">
  				<p class="validateTips normal">정말로 삭제하시겠습니까?</p>
  				<p class="validateTips error" style="display:none">삭제실패</p>
  				<form>
					<input type="hidden" id="hidden-no" value="">
  				</form>
			</div>
		      	
			<div id="dialog-message" title="" style="display:none">
  				<p></p>
			</div>		      	
		      	
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/blog/includes/footer.jsp" />
		
	</div>
</body>
</html>