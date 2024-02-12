<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(session.getAttribute("memberId")!=null){
	System.out.println("이미 로그인 상태! "+session.getAttribute("memberId"));
	%><script>
		alert("이미 로그인되어 있습니다.");
		window.location='/';
	</script><%
}
else{ %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>로그인 페이지</title>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/css/login.css">
	<script type="text/javascript" src="/js/login.js"></script>
</head>
<body>
<!-- 공통 헤더 부분 -->
<%@ include file="header.jsp" %>

<!-- 본문 -->
<div class="container">
	<div class="container_box">
		<div class="visionart_logo">
			<img class="logo" src="/media/images/logo.jpg"width="150" height="70" alt="vision art">
		</div> 
		<div class="member_info">
			<div>
				<input id="member_id" placeholder="아이디" type="text">
			</div>
			<div>
				<input id="member_pw" placeholder="비밀번호" type="password">
			</div>
		</div>
		<!-- <input id="login_button" type="submit" value="로그인"> -->
		<button id="login_button">로그인</button>
	</div>
</div>

<!-- 공통 풋터 부분 -->
<%@ include file="footer.jsp" %>
</body>
</html>
<% } %>