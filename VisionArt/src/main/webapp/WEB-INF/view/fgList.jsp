<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
 
 <!DOCTYPE html>
<html lang="en">
    <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <title>게시물 목록</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="/css/fgList.css">
	<script src="/js/jquery-3.7.1.min.js"></script>
	<script>
		const startPage=${result.startPage};
		let currentPage=${result.page};
		const endPage=${result.endPage};
		const uri="/fg";

		$(function(){
			setupPagination();
		});
	</script>
</head>
<body>
<%@ include file="header.jsp" %>
<section class="home">
<div class="container">
    <div class="container mt-4">
        <h2>게시물 목록</h2>
        <div class="card-container">
            <!-- JSTL foreach문을 써서 뿌리세요. el표현식과 함께 -->
            <c:forEach var="post" items="${result.posts}">
				<div class="card col-md-12 m-2">
					<!-- 썸네일 이미지 경로를 동적으로 설정 -->
					<img src="/attachment/fg/${post.postAttPath}" class="card-img-top" alt="${post.title}">
					<div class="card-body">
						<a href="fg/${post.postId}">
							<h5 class="card-title">${post.title}</h5>
						</a>
					</div>
				</div>
			</c:forEach>
        </div>
    </div>
</div>
</section>
	<c:if test="${memberId!=null}"> <div id="post-add-button"><a href="/fg/write" class="btn btn-success">글 작성</a></div> </c:if>
	<!-- disabled -->
	<ul class="pagination justify-content-center">
		<!-- 페이지 바 -->
		<div id="page" class="page">
			<div id="pagination">
			  <button onclick="goToPreviousPage()">이전</button>
			  <span id="page-numbers"></span>
			  <button onclick="goToNextPage()">다음</button>
			</div>
			<script src="/js/page.js"></script>
			
		  </div>
		<!-- <c:choose>
			<c:when test="${empty param.keyword}">
				<c:set var="pagePrev" value="/blog/board?cmd=list&page=${param.page-1}"/>
				<c:set var="pageNext" value="/blog/board?cmd=list&page=${param.page+1}"/>
			</c:when>
			<c:otherwise>
				<c:set var="pagePrev" value="/fg?page=${param.page-1}&keyword=${param.keyword}"/>
				<c:set var="pageNext" value="/fg?page=${param.page+1}&keyword=${param.keyword}"/>
			</c:otherwise>
		</c:choose> -->
		
		<!-- <c:choose>
			<c:when test="${param.page == 0}">
				<li class="page-item disabled"><a class="page-link" href="#">이전</a></li>	
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="${pageScope.pagePrev}">이전</a></li>
			</c:otherwise>
		</c:choose>
		
		<c:choose>
			<c:when test="${lastPage == param.page}">
				<li class="page-item disabled"><a class="page-link" href="#">다음</a></li>		
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="${pageScope.pageNext}">다음</a></li>
			</c:otherwise>
		</c:choose> -->
		
		
	</ul>
 
</body>
</html>


