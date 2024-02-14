<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mc.full17th2.dto.DBPostDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="path" value="${pageContext.request.contextPath}" />
<%request.setCharacterEncoding("utf-8"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항</title>
<!-- 공유하기 -->
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/notice.js"></script>
<link rel="stylesheet" href="/css/fontello-dede59aa/css/fontello-embedded.css" />
<link rel="stylesheet" type="text/css" href="/css/board.css" />
<link rel="stylesheet" type="text/css" href="/css/menu.css">
<script>
</script>
</head>

<body>
	<%@ include file="header.jsp" %>
	<div class="pageClass" id="board-containor">
		<header class="navi">
			<nav class="content-nav">
				<!-- 사방 패딩, 스크롤 고정,  -->
				<div class="location">
					<!-- 왼쪽 -->
					<strong>공지사항</strong>

					<div class="contents-util">
						<!-- 오른쪽, 아이콘  -->
						<div class="contents-share">
							<!-- 왼쪽으로 아코디언 -->
							<div class="shareList">
							</div>
						</div>
						<button type="button" class="icon icon-edit-2" onclick="location.href = '${path}/notice/write'" title="글쓰기"></button>
						<button type="button" class="icon icon-up-2" onclick="scrollToTop()" title="맨위로" ></button>
					</div>

				</div>

			</nav>
		</header>
		<main>
			<section class="section-title">
				<h1 class="title-h1">"공지사항 게시판입니다."</h1>
			</section>

			<section class="section-board">
				<div class="area-sort">
<!-- 					<a href="#"> 최신글</a>| <a href="#"> 인기글</a>| <a href="#"> 댓글 많은 글</a>| -->
				</div>





				<c:choose>
					<c:when test="${!result.status}">
						<div id="board-list">
							<ul class="board-ul">
								<li class="one-writing-li">
									<!-- b-m -->
									<div class="one-writing">
										<span class="comment-ctn"></span>
										<!-- r-m -->
										<div class="title-inform">
											<h1>게시물이 존재하지 않습니다.</h1>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</c:when>
					<c:otherwise>
						<div id="board-list">
							<ul class="board-ul">
								<c:forEach var="post" items="${result.posts}">
									<li class="one-writing-li">
										<div class="one-writing">
											<!-- 카테고리 -->
											<div class="title-inform">
													<!-- 제목 -->
													<a class="writing-title" href="/notice/${post.postId}">
														${ post.title }
													</a>
											</div>
											
											<!-- 작성자 -->
											<span class="writer-inform">
												<!-- <img src="${path}/resources/visionart/user.png" class="profile-img"> -->
												<p class="writer">
													<strong>${post.nickname}</strong>
												</p>
												<!--  작성일 -->
													<span class="date">${post.postDatetime}</span>
											</span>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</c:otherwise>
				</c:choose>
			</section>
		</main>

	<%@ include file="footer.jsp"%>
</body>
</html>















