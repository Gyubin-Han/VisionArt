<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/header.css">
<script type="text/javascript">

</script>
</head>
<body>
	<header class="header">
		<div class="header_top">
			<div class="header_home">
				<a href="/">
					<img class="" src="/media/images/logo.jpg" width="150" height="70" alt="logo">
				</a>
			</div>
			<div class="search_bar">
			 <input type="text" placeholder="검색">
			</div>
			<!-- <div class="chat">
				<a href="">
					<img class="" src="/media/images/chat.jpg" width="50" height="50" alt="chat">
				</a>
			</div> -->
			<div class="user_button">
				<%
				if(session.getAttribute("memberId")==null){
					%>
					<div class="login">
						<a href="login"><span class="join">로그인</span></a>   
					</div>
					<div class="join">
						<a href="join"><span class="join">회원가입</span></a>
					</div>
					<%
				}else{
					%>
					<div class="my_page">
						<a href="/mypage" class="user_button">
							<img src="/media/images/mypage1.jpg" width="50" height="50" alt="mypage" id="mypage">
						</a>
					</div>
					<!-- <div class="join">
						<span class="nickname"><%= session.getAttribute("nickname") %>님</span>
					</div> -->
					<div class="logout">
						<a href="logout"><span class="join">로그아웃</span></a>
					</div>
					<%
				}
				%>
				
			</div>
		</div>
		<div class="header_bottom">
			<a href ="">자유 게시판</a>
			<a href ="">유저 갤러리</a>
			<a href ="">명화 갤러리</a>
			<a href ="">전시회 정보</a>
			<a href ="">공지사항</a>
		</div>
	</header>
</body>
</html>