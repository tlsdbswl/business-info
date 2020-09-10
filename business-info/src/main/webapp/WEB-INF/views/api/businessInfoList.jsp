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
			<h2>사업자 정보</h2>
			<div class="table-responsive">
				<table class="table table-sm">
					<thead>
						<tr>
							<th>자료생성년월</th>
							<th>식별번호</th>
							<th>사업자명</th>
							<th>사업자번호</th>
							<th>상세 주소</th>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="list" varStatus="status">
							<tr>
								<td>${list.date}</td>
								<td>${list.seq}</td>
								<td>
									<a href="${pageContext.request.contextPath}/api/business-info.do?seq=${list.seq}&date=${list.date}">${list.name}</a>
								</td>
								<td>${list.no}</td>
								<td>${list.addr}</td>
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

</body>

</html>

