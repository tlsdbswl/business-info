<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/common/header.jsp"%>

<html>
<head>
<meta charset="UTF-8">

<script>
	$(document).on('click', '#btnList', function(e) {
		e.preventDefault();
		location.href = "${pageContext.request.contextPath}/main.do";

	});
</script>
</head>

<body>

	<article>
		<div class="container" role="main">
			<h2>사업장 정보 조회</h2>
			<form name="form" id="form" method="get"
				action="${pageContext.request.contextPath}/api/business-info-end.do">

				<div class="mb-3">
					<input type="text" class="form-control" name="dgCode" id="dgCode"
						placeholder="시도(행정자치부 법정동 주소코드 참조)"> 
					<input type="text"
						class="form-control" name="sgguCode" id="sgguCode"
						placeholder="시군구">
					<input type="text" class="form-control"
						name="sgguEmdCode" id="sgguEmdCode" placeholder="읍면동"> 
					<input
						type="text" class="form-control" name="businessName"
						id="businessName" placeholder="* 사업장 이름">
					<input type="text"
						class="form-control" name="businessRegNo" id="businessRegNo"
						placeholder="사업자 등록 번호 (앞에서 6자리)">
				</div>
			</form>

			<button type="submit" class="btn btn-sm btn-primary" id="btnSubmit"
				form="form">조회</button>
			<button type="button" class="btn btn-sm btn-primary" id="btnList">목록</button>
		</div>
	</article>

</body>

</html>


