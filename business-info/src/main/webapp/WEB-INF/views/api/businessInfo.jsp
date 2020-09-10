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
					<tbody>
						<c:forEach items="${list}" var="list" varStatus="status">
							<tr>
								<th>업종</th>
								<td>${list.vldtVlKrnNm}</td>
							</tr>
							<tr>
								<th>사업자 번호</th>
								<td>${list.no}</td>
							</tr>
							<tr>
								<th>사업장 상태</th>
								<td>${list.wkplJnngStcd}</td>
							</tr>
							<tr>
								<th>사업장 등록일</th>
								<td>${list.adptDt}</td>
							</tr>
							<tr>
								<th>사업장명</th>
								<td>${list.name}</td>
							</tr>
							<tr>
								<th>가입자수</th>
								<td>${list.jnngpCnt}</td>
							</tr>
							<tr>
								<th>입사자</th>
								<td>${list.nwAcqzrCnt}</td>
							</tr>
							<tr>
								<th>퇴사자</th>
								<td>${list.lssJnngpCnt}</td>
							</tr>
							<tr>
								<th>상세 주소</th>
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

