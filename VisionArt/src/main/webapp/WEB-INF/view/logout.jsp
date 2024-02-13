<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

if(session.getAttribute("memberId")==null){
    %>
    <script>
        alert("이미 로그아웃된 상태입니다.");
        location.href="/";
    </script>
    <%
}else{
    session.setAttribute("memberId",null);
    %>
    <script>
        alert("정상적으로 로그아웃 처리되었습니다.");
        location.href='/';
    </script>
    <%
}
%>