<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/head.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@include file="../include/memberheader.jsp" %>
	<div class="row">
		<form action="/member/register" method="post">
			<label for="id">아이디<input type="text" name="id" /></label> <label
				for="name">이름<input type="text" name="name" /></label> <label
				for="password">비밀번호<input type="password" name="password" /></label>
			<label for="email">이메일<input type="email" name="email" /></label>
			<button class="btn big-login" type="submit">회원가입</button>
		</form>
	</div>
	<script src="/static/js/jQuery-3.6.0.js"></script>
	<script src="/static/js/bootstrap.min.js"></script>
</body>
</html>