<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
</head>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/ejs/ejs.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
var listEJS = new EJS({
	url: "${pageContext.request.contextPath }/ejs/list-template.ejs"
});

var listItemEJS = new EJS({
 	url: "${pageContext.request.contextPath }/ejs/listItem-template.ejs"
 });

var fetch = function() {
	$.ajax({
		url: "${pageContext.request.contextPath }/category/list/" + "${blogVo.id}",
		dataType: "json", // 받을 때 포멧 
		type: "get",	  // 요청 method
		success: function(response){
			var html = listEJS.render(response);
			$("#list").after(html);
		}
	});
}

var valid = function(titles, message, callback) { 
	 $("#dialog-message p").text(message);
	 $("#dialog-message").attr("title", titles).dialog({
			modal: true,
			buttons: {
				"확인": function(){
					$("#dialog-message").dialog("close");
				}
			},
			close: callback
		});
}	

var addlist = function() {
	 $("#add-form").submit(function(event){
			event.preventDefault();
			
			vo = {}
			
			vo.name = $("#name").val();
			// validation name
			if(vo.name == "") {
				valid("경고", "카테고리명이 비었습니다.", function(){
					$("#name").focus();
				});
				return;
			}
			vo.description = $("#description").val();
			// validation password
			if(vo.description == "") {
				valid("경고", "설명이 비었습니다.", function(){
					$("#description").focus();
				});
				return;
			}
			
			// 데이터 등록
		$.ajax({
			url: "${pageContext.request.contextPath }/category/addlist/" + "${blogVo.id}",
			dataType: "json",
			type: "post",
			contentType: "application/json",   
			data: JSON.stringify(vo),
			success: function(response){
				let index = $(".admin-cat tr").length
				
				if(response.result != "success"){
					console.error(response.message);
					return;
				}	
				
				response.data.index = index;
				var html = listItemEJS.render(response.data);
				$(".admin-cat tr:last").after(html);
				
				// form reset
				$("#add-form")[0].reset();
			}
		});		
		
	});
}

var del = function(){
	$(document).on("click", ".admin-cat td a", function(event) {
		event.preventDefault();
		let no = $(this).data("no");
		$("#hidden-no").val(no);
		deleteDialog.dialog("open");
	});

	const deleteDialog = $("#dialog-delete-form").dialog({
		autoOpen: false,
		width: 300,
		height: 220,
		modal: true,
		buttons: {
			"삭제": function(){
				const no = $("#hidden-no").val();
				const password = $("#password-delete").val();
				$.ajax({
					url: "${pageContext.request.contextPath }/category/delete",
					dataType: "json", // 받을 때 포멧 
					type: "post",	  // 요청 method
					data: "no=" + no,
					success: function(response){
						$("#admin-cat tr[data-no="+ response.data + "]").remove();
						deleteDialog.dialog("close");
					}
				});
			},
			"취소": function(){
				$(this).dialog("close");
			}
		},
		close: function() {
			// no 비우기
			$("#hidden-no").val("");
		}
	});
}
	
$(function() {
	addlist();
	del();
	fetch();
});
</script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath }/${authUser.id }/admin/basic">기본설정</a></li>
					<li class="selected"><a href="${pageContext.request.contextPath }/${authUser.id }/admin/category">카테고리</a></li>
					<li><a href="${pageContext.request.contextPath }/${authUser.id }/admin/write">글작성</a></li>
				</ul>
				<table class="admin-cat">
					<tr id="list">
						<th>번호</th>
						<th>카테고리명</th>
						<th>포스트 수</th>
						<th>설명</th>
						<th>삭제</th>
					</tr>
				</table>

				<h4 class="n-c">새로운 카테고리 추가</h4>
				<form action="" method="post" id="add-form" >
					<table id="admin-cat-add">
						<tr>
							<td class="t">카테고리명</td>
							<td><input type="text" id="name" name="name"></td>
						</tr>
						<tr>
							<td class="t">설명</td>
							<td><input type="text" id="description" name="description"></td>
						</tr>
						<tr>
							<td class="s">&nbsp;</td>
							<td><input type="submit" value="카테고리 추가"></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div id="dialog-delete-form" title="카테고리 삭제" style="display: none">
			<p class="validateTips normal">카테고리를 삭제하시겠습니까?</p>
			<form>
				<input type="hidden" id="hidden-no" value=""> 
			</form>
		</div>
		<div id="dialog-message" title="" style="display: none">
			<p></p>
		</div>
		<c:import url="/WEB-INF/views/includes/blog-footer.jsp"></c:import>
	</div>
</body>
</html>