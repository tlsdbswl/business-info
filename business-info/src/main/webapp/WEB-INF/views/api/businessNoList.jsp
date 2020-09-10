<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<%@ include file="/WEB-INF/views/common/header.jsp"%>

<html>
<head>
<title>Home</title>
</head>
<body>
	<article>
		<div class="container">
			<h2>사업자 상태</h2>
			<div class="table-responsive">
				<table class="table table-sm">
					<thead>

					</thead>
					<tbody>
						<c:forEach items="${list}" var="list" varStatus="status">
							<tr>
								<td>${list}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div>
				<!--  
				<button type="button" class="btn btn-sm btn-primary"
					id="btnMessageForm">글쓰기</button>
				-->
			</div>
		</div>
	</article>
	<script>
	$(document)
	.on(
			'click',
			'#btnDeviceList',
			function(e) {
				e.preventDefault();
				location.href = "${pageContext.request.contextPath}/message/message-info.do";

			});
	
	$(document)
	.on(
			'click',
			'#btnMessageForm',
			function(e) {
				e.preventDefault();
				location.href = "${pageContext.request.contextPath}/message/message-form.do";

			});
	</script>
</body>

</html>

