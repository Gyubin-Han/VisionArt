<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="path" value="${pageContext.request.contextPath}" />
<c:choose>
	<c:when test="${!empty result.post}">
		<c:set var="post" value="${result.post}"/>
		<c:set var="saveLink" value="/notice/edit/save"/>
	</c:when>
	<c:otherwise>
		<c:set var="saveLink" value="/notice/write/save"/>
	</c:otherwise>
</c:choose>

	<!-- post_field_id title content file -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<!-- 공유하기 -->
<script src="/js/jquery-3.7.1.min.js"></script>
<script src="/js/notice.js"></script>
<link rel="stylesheet" href="/css/fontello-dede59aa/css/fontello-embedded.css" />
<link rel="stylesheet" type="text/css" href="/css/board.css" />
<script>
	
	function submit(){
		let writeTitle=$('#writeTitle').val();
		let writeContent=$('#writeContent').val();

		if(writeTitle==''){
			alert('제목을 입력하세요.');
			return false;
		}else if(writeContent==''){
			alert('내용을 입력하세요.');
			return false;
		}

		$('#writeForm').submit();
	}

	function cancle(){
		location.href='/notice';
	}
</script>
</head>

<body>
	<%@ include file="header.jsp"%>
	<div class="pageClass" id="board-containor">
		<header class="navi">
			<nav class="content-nav">
				<!-- 사방 패딩, 스크롤 고정,  -->
				<div class="location">
					<!-- 왼쪽 -->
					<strong>공지사항 글쓰기</strong>

					<div class="contents-util">
						<!-- 오른쪽, 아이콘  -->
						<div class="contents-share">
							<!-- 왼쪽으로 아코디언 -->
							<div class="shareList">
							</div>
						</div>
						<button type="button" class="icon icon-up-2" onclick="scrollToTop()" title="맨위로"></button>
					</div>

				</div>

			</nav>
		</header>
		<main>
			<c:if test="${memberId==null}">
				<script>
					alert('로그인을 해주세요');
					location.href="/login";
				</script>
			</c:if>
			<%-- 관리자 권한 확인 --%>
			<c:if test="${!isAdmin}">
				<script>
					alert('관리자만 공지사항에 글을 작성할 수 있습니다');
					location.href="/notice";
				</script>
			</c:if>

			<!-- 게시판 글쓰기 -->
			<div class="writer-container" align="center">
				<form id="writeForm" method="post" action="${saveLink}" enctype="multipart/form-data">
					<c:if test="${!empty post.postId}">
						<input type="hidden" name="postId" value="${post.postId}">
					</c:if>
					<table class="table boardWrite-table">
						<thead>
							<tr>
								<th colspan="2">글쓰기</th>
							</tr>
						</thead>
						<colgroup>
							<col>
							<col>
						</colgroup>
						<tbody>
							<tr>
								<th></th>
								<td></td>
							</tr>
							<tr>
								<th scope="row"><label for="writeTitle"> <span class="required__mark">*</span> 제목
								</label></th>
								<td>
									<div class="form-field">
										<div class="form-field_inner">
											<input type="text" id="writeTitle" name="title" title="제목" maxlength="100" required value="${post.title}">
										</div>
									</div>
								</td>
							</tr>
							<tr>
								<th scope="row"><label for="writeContent"> <span class="required__mark">*</span> 내용
								</label></th>
								<td>
									<div class="form-field">
										<textarea id="writeContent" name="content" title="내용" maxlength="1000" required>${post.content}</textarea>
									</div>
								</td>
							</tr>
							<!-- <tr id="fileTr">
								<th scope="row"><label for="file"> 파일첨부 </label></th>
								<td class="fileTd">
									<div class="form-field">
										<input class="file-name" value="첨부파일"> <label for="file" class="btn-file">파일찾기</label> <input type="file" id="file" name="post_att_id" title="파일">
									</div>
									<script>
										$("#file").on('change', function() {
											var fileName = $("#file").val();
											$(".file-name").val(fileName);
										});
										
									</script>
								</td>
							</tr> -->
							<tr><th></th>
								<td>
									<div class="labelDiv">
										<label for="cancle" class="btn-cancle" onclick="cancle()">작성 취소</label> <label for="submit" class="btn-submit" onclick="submit()">글 저장</label>
									</div>
								</td>
								<!-- <td class="btn-right" colspan="2">
									<div class="form-field-submit ">
										<input type="button" id="cancle" title="취소"> <input type="submit" id="submit" name="post_submit" title="저장">
										<script>
											
										</script>
									</div>
								</td> -->
							</tr>
						</tbody>
					</table>
					<input type="hidden" name="member_id" value="${sessionid}">
				</form>
			</div>
		</main>
	</div>
</body>
</html>















