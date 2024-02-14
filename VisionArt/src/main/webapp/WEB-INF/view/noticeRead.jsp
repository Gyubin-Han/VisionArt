<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<c:set var="post" value="${result.post}"/>
<c:set var = "path" value = "${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html> 
<head>
<meta charset="UTF-8">
<title>비전아트 게시판</title>
<!-- 공유하기 -->
<script src = "/js/jquery-3.7.1.min.js"></script>  
<script src = "/js/board.js"></script> 
<link rel="stylesheet" href="/css/fontello-dede59aa/css/fontello-embedded.css"  />
<link rel="stylesheet" type="text/css" href="/css/board.css" />
<script>
$(document).ready(function(){
	$('#delete').click(function(){
		$.ajax({
			type:'post',
			url:'/notice/delete',
			dataType:'json',
			data:{
				'postId':'${post.postId}'
			},
			success:function(response){
				if(response.result==="ok"){
					alert("게시글이 성공적으로 삭제되었습니다.");
					window.location.href='/notice';
				}else{
					alert("게시글 삭제에 실패하였습니다. 다시 시도해주세요.");
				}
			}
		});
	});
})
</script>
</head>

<body>
	<%@ include file="header.jsp"%>
	<div class="pageClass" id="board-containor">
		<header class="navi">
			<nav class="content-nav"><!-- 사방 패딩, 스크롤 고정,  -->
				<div class="location"><!-- 왼쪽 -->
					<strong>공지사항</strong>
					
					<div class="contents-util"><!-- 오른쪽, 아이콘  -->
						<!-- <div class="contents-share">왼쪽으로 아코디언
							<button type="button" id="contents_share-trigger" class="icon icon-share"></button>
							<div class="shareList">
								<button class="shareList-item icon icon-chat"></button>
								<button class="shareList-item shareList-item-copy"> 주소 복사 </button>
							</div>
						</div> -->
						<button type="button" class="icon icon-edit-2" onclick="location.href = '/notice/write'" title="글쓰기"></button>
						<button type="button" class="icon icon-up-2" onclick="scrollToTop()" title="맨위로"></button>
					</div>
					
				</div>
			</nav>
		</header>
		
		<main>
			<div class="writer-container" align="center">
				<table class="table boardWrite-table">
					<thead >
						<tr>
							<th colspan="2">
								<div id="title" data-post-id="${post.postId}">
								<h2>${post.title}</h2>
								</div>
							</th>
						</tr>
					</thead>
					<colgroup>
						<col>
						<col>
					</colgroup>
					<tbody >
						<tr id="readTr">
							<th scope="row"><label for="writer"> ${post.postDatetime} </label></th>
							<td>
								<div class="form-field form-field-30">
									<div class="form-field_inner form-field-submit">${post.nickname}</div>
										
									<c:if test="${memberId == post.memberId}">
										<div class="labelDiv">
											<a href="/notice/edit/${post.postId}"><label for="update" class="btn-update">수정하기</label></a>
											<label id="delete" for="delete" class="btn-delete">삭제하기</label> 
										</div>
									</c:if>
								</div>
							</td>
						</tr>
					</tbody>
				</table>

			<!-- 본문 -->
			<div id="postContent" data-post-id="${post.postId}">
				${post.content}
			</div>
		</main>	
	</div>
	<%@ include file="footer.jsp"%>
</body>
</html>















