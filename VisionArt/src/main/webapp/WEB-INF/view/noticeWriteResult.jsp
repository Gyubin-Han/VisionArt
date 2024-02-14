<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${result}">
        <script>
            alert('정상적으로 등록되었습니다.');
            location.href='/notice';
        </script>
    </c:when>
    <c:otherwise>
        <script>
            alert('오류가 발생했습니다. 다시 시도해주세요.');
            window.history.back();
        </script>
    </c:otherwise>
</c:choose>