<%@page import="com.choi.board.common.Board"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
session = request.getSession(false);
Board board = (Board)request.getAttribute("board");
AuthUser loginUser = (AuthUser) session.getAttribute("loginUser");
if (loginUser == null && !board.getWriter().equals(loginUser.getId())) {
	response.sendRedirect("list");
}
%>
<!DOCTYPE html>
<html>
<%@ include file="../include/head.jsp"%>
<style>
body {
	padding-top: 70px;
	padding-bottom: 30px;
}

li {
	display: list-item !important;
}

.container {
	display: block;
	float: right;
	width: 82vw;
}
</style>
<%@ include file="../include/header.jsp"%>
<%@ include file="../include/aside.jsp"%>
<script>
	$(document).on('click', '#btnSave', function(e) {
		e.preventDefault();
		$("#form").submit();

	});
</script>
<body>

	<article>

		<div class="container" role="main">

			<h2>board Form</h2>

			<form name="form" id="form" role="form" method="post" action="modify">

				<div class="mb-3">

					<label for="title">제목</label> <input type="text"
						class="form-control" name="title" value="${board.title}">

				</div>



				<div class="mb-3">

					<label for="reg_id">작성자</label> <input type="text"
						class="form-control" name="writer" value="${loginUser.id}"
						readonly="readonly">

				</div>



				<div class="mb-3">

					<label for="content">내용</label>

					<textarea class="form-control" rows="5" name="content" id="content"
						>${board.contents}</textarea>

				</div>



			</form>

			<div>
				<a class="btn btn-sm btn-primary" href="../board/list">목록</a>
				<button type="button" class="btn btn-sm btn-primary" id="btnSave">저장</button>


			</div>

		</div>

	</article>
	<script type="text/javascript">
		$(function() {
			if ('${msg}') {
				alert('${msg}');
			}
		});
	</script>
	<script src="/static/js/ckeditor.js"></script>
</body>
</html>