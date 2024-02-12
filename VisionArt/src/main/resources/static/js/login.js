function login(){
    // 사용자 이름과 비밀번호 필드의 값을 가져옴
    var username = $("#member_id").val();
    var password = $("#member_pw").val();

    // AJAX 요청 수행
    $.ajax({
        url: "/login/loginCheck",
        type: "post",
        data: {
            'userId': username,
            'userPassword': password
        },
        dataType:'json',
        success: function(response) {
            if (response.result === "success") {
                // 로그인 성공
                // alert("로그인 되었습니다.");
                location.href="/";
            } else {
                // 로그인 실패
                alert("로그인 실패. 아이디와 비밀번호를 확인하세요");
            }
        },
        error: function() {
            //오류 처리
            alert("로그인 중 오류가 발생했습니다. 다시 시도하세요.");
        }
    });
}

$(document).ready(function() {
    // 로그인 버튼에 클릭 이벤트 핸들러 추가
    $("#login_button").click(function() {
        login();
    });
    // 입력창에서 엔터 키 이벤트 핸들러 추가
    $("#member_pw").keypress(function(e){
        if(e.keyCode && e.keyCode==13){
            login();
        }
    })
});