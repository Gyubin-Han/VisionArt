<%@page import="com.mc.full17th2.dto.FamousGallaryDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <!DOCTYPE html>
    <html lang="en">
        <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="0">
		<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
		<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
		<link rel="icon" href="data:;base64,iVBORw0KGgo=">
		<link rel="stylesheet" type="text/css" href="/css/fgpostId.css">
		<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
		<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
	</head>
<body>
<%@ include file="header.jsp" %>
<c:set var="post" value="${result.post}"/>

<div class="allElements">
		<div id="body">
			 

<div>

<div>
    <h2 ><b>${post.title}</b></h2>
    <hr />
<h6 class="m-2">작성자: <i>${post.nickname}</i> | 조회수: <i>${post.views}</i> | 날짜: <i>${post.postDatetime}</i></h6>


    
</div>

	<br />
	
	<div class="form-group position-relative">
	
		<div class="m-2">
		
			<div id="postContent">
				<img src="/attachment/fg/${post.postAttPath}"><br>
				<ul id="famousInformation">
					<li>작가명 : ${post.artistName}</li>
					<li>작품 분류 : ${post.artFieldName}</li>
					<!-- <li>제작시기 : </li> -->
				</ul>
						${post.content}
					</div>
		
		</div>
		<br />	<br />
		<div class="position-absolute" style="bottom: 0; right: 0;">
			<c:if test="${memberId == post.memberId}">
				<a href="/fg/edit/${post.postId}" class="btn btn-warning">수정</a>
				<button onClick="deleteById(${post.postId})" class="btn btn-danger">삭제</button>
			</c:if>
		</div>
	</div>
</div>
	<hr />
<div id="btns">
    <button id="likeBtn" type="button">
        <span> <img src="/media/images/Likeit.png" width="30" height="30"></span>
    </button>
    <!-- 카운트를 표시할 요소 -->
    <span id="likeCount">${post.likes}</span>
</div>

		 
	 
	<br /> <br />
	 
 
<!-- 댓글 박스 -->
	<div class="row bootstrap snippets">
		<div class="col-md-12">
			<div class="comment-wrapper">
				<div class="panel panel-info">
						<div class="panel-heading m-2"style="text-align: left;">
						<b>Comment</b>
					</div>
					<div class="panel-body">
						<c:if test="${memberId!=null}">
						<input type="hidden" name="boardId" value="${post.postId}" />
						<textarea id="content" id="reply__write__form"
							class="form-control" placeholder="write a comment..." rows="2"></textarea>
						<br>

							<button
								onClick="replySave(${memberId}, ${post.postId})"
								class="btn btn-primary pull-right">댓글쓰기</button>
						</c:if>

						<div class="clearfix"></div>
						<hr />

						<!-- 댓글 리스트 시작-->
						<ul id="reply__list" class="media-list">
							<c:forEach var="reply" items="${result.comments}">
								<!-- 댓글 아이템 -->
								<li id="reply-${reply.commentId}" class="media">
									<div class="media-body">
										<strong class="text-primary">${reply.nickname}</strong>
										<p>${reply.commentContent}</p>
									</div>
									<div class="m-2">
										<c:if test="${memberId == reply.memberId }">
											<i onclick="deleteReply(${reply.commentId})" class="material-icons">delete</i>
										</c:if>
									</div>
								</li>
							</c:forEach>


						</ul>
						<!-- 댓글 리스트 끝-->
					</div>
				</div>
			</div>

		</div>
	</div>
	<!-- 댓글 박스 끝 -->
</div>
 
 
 
<script>
function addReply(data){
	console.log(data);
	let replyItem=$('<li>').attr('id','reply-'+data.commentId).addClass("media");
	let replyItem2=$('<div>').addClass('media-body');
	let replyItem3=$('<strong>').addClass('text-primary').text('${nickname}');
	let replyItem4=$('<p>').text(data.commentContent);
	let replyItem5=$('<div>').addClass('m-2');
	let replyItem6=$('<i>').attr('onclick','deleteReply('+data.commentId+')').addClass('material-icons').text('delete');

	replyItem5.append(replyItem6);
	replyItem2.append(replyItem3).append(replyItem4);
	replyItem.append(replyItem2).append(replyItem5);
	// var replyItem = `<li id="reply-${data.commentId}" class="media">`;
	// replyItem += `<div class="media-body">`;
	// replyItem += `<strong class="text-primary">${data.nickname}</strong>`;
	// replyItem += `<p>${data.commentContent}.</p></div>`;
	// replyItem += `<div class="m-2">`;
	
	// replyItem += `<i onclick="deleteReply(${data.commentId})" class="material-icons">delete</i></div></li>`;
	
	$("#reply__list").prepend(replyItem);
}

function deleteReply(id){
	// 세션의 유저의 id와 reply의 userId를 비교해서 같을때만!!
	$.ajax({
		type : "post",
		url : "/fg/comment/delete",
		dataType : "json",
		data:{
			"commentId":id
		},
		success:function(response){
			if(response.result==="ok"){
				console.log(response.result);
				$("#reply-"+id).remove();
			} else {
				alert("댓글삭제 실패");
			}
		}
	});
}

function replySave(userId, boardId) {
	$.ajax({
		type : "post",
		url : "/fg/comment/save",
		data : {
			memberId : userId,
			postId : boardId,
			commentContent : $('#content').val()
		},
		dataType : "json",
		success:function(response){
			console.log(response);
			if(response.status){
				addReply(response.data);
				$("#content").val("");
			}else{
				alert("댓글쓰기 실패");
			}
		},
		error:function(request,e){
			alert("오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
		}
	});
}


function deleteById(boardId){
	$.ajax({
		type: "post",
		url: "/fg/delete",
		dataType: "json",
		data:{
			"postId":boardId
		},
		success: function(response){
			if(response.result==="ok"){
				console.log(response.result);
				alert("게시글이 삭제되었습니다.");
				location.href="/fg"
			}else{
				console.log(response.result);
				alert("게시글 삭제에 실패하였습니다. 잠시 후에 다시 시도해주세요.");
			}
		},
		error:function(request,error){
			console.log(request.state+" / "+error);
			alert("에러가 발생했습니다. 잠시 후에 다시 시도해주세요.");
		}
	});
}

$(document).ready(function(){
    <%
	if(session.getAttribute("memberId")!=null){
		%>
		isPostMemberLike();
		$('#likeBtn').click(function(){
			likeBtnClick(${post.postId});
		});
		<%
	}
	%>
});

function likeCountFontWeight(isLike){
	if(isLike){
		$('#likeCount').css({"font-weight":"bold"});
	}else{
		$('#likeCount').css({'font-weight':'normal'});
	}
}

function isPostMemberLike(){
	$.ajax({
		url:"/fg/isLike",
		type:"post",
		dataType:"json",
		data:{
			"postId": "${post.postId}"
		},
		success: function(response){
			likeCountFontWeight(response.isLike);
		}
	});
}

function likeBtnClick(postId) {
    $.ajax({
        url: "/fg/clickLike", // 좋아요 및 조회수 증가를 처리하는 서버 엔드포인트 URL
        type: "POST",
		dataType: "json",
        data: { "postId": postId },
        success: function (response) {
            // 서버에서 반환된 데이터를 사용하여 조회수와 좋아요 수를 업데이트
            if(response.isLike){
				$('#likeCount').text(Number($('#likeCount').text())+1);
			}else{
				$('#likeCount').text(Number($('#likeCount').text())-1);
			}

			likeCountFontWeight(response.isLike);
        },
        error: function (request,error) {
            console.error("좋아요 요청 실패: " + error);
        }
    });
}


//버튼 클릭 이벤트 핸들러
// document.getElementById("likeBtn").addEventListener("click", function() {
//     // 현재 카운트 가져오기
//     var currentCount = parseInt(document.getElementById("likeCount").textContent);
    
//     // 카운트 증가
//     var newCount = currentCount + 1;
    
//     // 증가된 카운트를 화면에 표시
//     document.getElementById("likeCount").textContent = newCount;
// });

</script>
</body>
</html>





