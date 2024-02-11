<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 페이지</title>
 <link rel="stylesheet" type="text/css" href="/css/join.css">
 <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script>
$(function(){
    $("#user_id").focusout(function(){
        let userId=$("#user_id").val();

        $.ajax({
            type:'post',
            url:'/join/checkIdExsist',
            dataType:'json',
            data:{
                'id':userId
            },
            success:function(response){
                let message=$("#error_message_id");

                if(response.result===true){
                    message.removeClass("message_ok");
                    message.text("이미 사용중인 아이디 입니다.").addClass("message_error");
                }else{
                    message.removeClass("message_error");
                    message.text("사용 가능한 아이디 입니다.").addClass("message_ok");
                }
            },
            error:function(request,e){
                alert("통신 중 에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    });
    $("#user_email").focusout(function(){
        let userEmail=$("#user_email").val();
        let message=$("#error_message_email");

        if(!(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]+$/i).test(userEmail)){
            message.removeClass("message_ok").text("이메일 양식이 올바르지 않습니다.").addClass("message_error");
            return;
        }
        $.ajax({
            type:'post',
            url:'/join/checkEmailExsist',
            dataType:'json',
            data:{
                'email':userEmail
            },
            success:function(response){

                if(response.result==true){
                    message.removeClass("message_ok");
                    message.text("이미 사용중인 이메일 입니다.").addClass("message_error");
                }else{
                    message.removeClass("message_error");
                    message.text("사용 가능한 이메일 입니다.").addClass("message_ok");
                }
            },
            error:function(request,e){
                alert("통신 중 에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
            }
        });
    });
});
function validateForm() {
    // 아이디, 비밀번호, 비밀번호 확인 값 가져오기
    var userId = document.getElementById("user_id").value;
    var userEmail=document.getElementById("user_email").value;
    var password = document.getElementById("user_pw").value;
    var confirmPassword = document.getElementById("user_pw_check").value;

    // 오류 메시지 엘리먼트 가져오기
    var userIdError = document.getElementById("error_message_id");
    var passwordError = document.getElementById("error_message_password");

    // 아이디가 8글자 이상이면서 영문과 숫자가 모두 포함되지 않는 경우
    if (!(/^[a-zA-Z0-9]{6,50}$/.test(userId))) {
        userIdError.innerHTML = "6글자 이상, 영문/숫자 조합으로만 구성하십시오";
        userIdError.classList.add("message_error");
        // userIdError.style.color = "red";

        return false;
    } else {
        // 확인 시 오류메시지 제거
        // userIdError.innerHTML = "";
    }

    // 비밀번호가 다름
    if (password !== confirmPassword) {
        passwordError.innerHTML = "비밀번호와 비밀번호 확인이 다릅니다";
        passwordError.style.color = "red";

        return false;
    } else {
        // 확인 시 오류메시지 제거
        passwordError.innerHTML = "";
    }

    // 아이디,비밀번호(확인)일치
    $.ajax({
        type:'POST',
        url:'/join/apply',
        dataType:'json',
        data:{
            'id':userId,
            'email':userEmail,
            'password':password,
            'nickname':$('#user_nickname').val()
        },
        success:function(response){
            alert(response.result);
            if(response.result==false){
                alert("에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
                return false;
            }
            alert("회원가입이 완료 되었습니다");
            window.location.href = "/";
            return true;
        },
        error:function(request,e){
            alert("에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return false;
        }
    });
}

</script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="container">
    <div class="container_box">
        <div class="title">회원가입</div>
        회원가입을 환영합니다.
		<div class="member_info">
			<div class="input_member member_nickname">
				<input id="user_nickname"  name="user_nickame" placeholder="닉네임" type="text">
			</div>
			<div class="input_member member_id">
				<input id="user_id" name="user_id" placeholder="아이디" type="text">
				 <div id="error_message_id" class="error_message"></div>
			</div>
            <div class="input_member member_email">
				<input id="user_email" name="user_email" placeholder="이메일" type="email">
				 <div id="error_message_email" class="error_message"></div>
			</div>
			<div class="input_member member_pw">
				<input id="user_pw" name="user_pw" placeholder="비밀번호" type="password">
			</div>
			<div class="input_member member_pw_check">
				<input id="user_pw_check" name="user_pw_check" placeholder="비밀번호 확인" type="password">
				<div id="error_message_password" class="error_message"></div>
			</div>
		</div>
		<input type="submit" value="회원가입" onclick="return validateForm();">
	</div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>