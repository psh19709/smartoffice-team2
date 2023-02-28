<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css">
<script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script>
<style>
.btn-xs {
	margin-top: -4px; 
	--bs-btn-padding-y: .25rem; 
	--bs-btn-padding-x: .5rem; 
	--bs-btn-font-size: .75rem;
}
.form-select-xs {
	min-height: calc(1.5em + 0.5rem + 2px);
    padding: 0.25rem 0.5rem;
    font-size: .875rem;
    border-radius: 0.25rem;
    width: 90px;
    display: inline-block !important;
}
.form-control-xs{
 	min-height: calc(1.5em + 0.5rem + 2px);
    padding: 0.25rem 0.5rem;
    font-size: .875rem;
    border-radius: 0.25rem;
    width: 150px;
    display: inline-block !important;
 }
.w-150 {
	min-width: 150px !important;
}
  }
</style>
<title>SMART OFFICE</title>
</head>
<body>
<c:set var="top" value="admin" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
	<div class="row g-3">
		<div class="col-3">
			<c:set var="left" value="document" />
			<%@ include file="../common/left/admin.jsp" %>
		</div>
		<div class="col-9">
		<div class="col-12 mb-3">
			<h1 class="fs-4 border p-2">문서양식 관리</h1>
		</div>
		<div class="mb-3 d-flex justify-content-between">
			<input type="hidden" name="page" value="" />
			<div>
				<select class="form-select form-select-xs" name="rows">
					<option>10개씩</option>
					<option>15개씩</option>
					<option>20개씩</option>
				</select>
			</div>
			<div>
				<select class="form-select form-select-xs" name="opt">
					<option>등록자</option>
					<option>파일명</option>
				</select>
				<input type="text" class="form-control form-control-xs" name="keyword" value="" />
				<button type="button" class="btn btn-outline-secondary btn-xs" id="btn-search"><i class="fa fa-search"></i></button>
			</div>
		</div>
		<table class="table table-sm">
				<colgroup>
					<col width="5%">
					<col width="10%">
					<col width="*">
					<col width="17%">
					<col width="17%">
				</colgroup>
				<thead>
					<tr>
						<th>
							<div>
 								<input class="form-check-input" type="checkbox" id="checkboxNoLabel" value="" aria-label="...">
							</div>
						</th>
						<th class="text-center">등록자</th>
						<th class="text-center">파일명</th>
						<th class="text-center">등록일</th>
						<th class="text-center">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<div>
 								<input class="form-check-input" type="checkbox" id="checkboxNoLabel" value="" aria-label="...">
							</div>
						</td>
						<td class="text-center">강감찬</td>
						<td class="text-center"><a href="" class="text-decoration-none">대용량 주소록 업로드.xml</a></td>
						<td class="text-center">2023-01-31</td>
						<td class="text-center">2023-01-31</td>
					</tr>
					<tr>
						<td>
							<div>
 								<input class="form-check-input" type="checkbox" id="checkboxNoLabel" value="" aria-label="...">
							</div>
						</td>
						<td class="text-center">강감찬</td>
						<td class="text-center"><a href="" class="text-decoration-none">대용량 주소록 업로드.xml</a></td>
						<td class="text-center">2023-01-31</td>
						<td class="text-center">2023-01-31</td>
					</tr>
			</table>
			<div class="w3-bar">
			  <button class="w3-button w3-padding-small w3-round-large w3-black">삭제</button>
			  <button class="w3-button w3-padding-small w3-round-large w3-light-gray">보관</button>
			  <button class="w3-button w3-padding-small w3-right w3-round-large w3-black"><a class="text-decoration-none" href="">추가</a></button>
			</div>
			<div class="w3-center">
				<div class="w3-bar">
				  <a href="#" class="w3-button">«</a>
				  <a href="#" class="w3-button w3-light-gray">1</a>
				  <a href="#" class="w3-button">2</a>
				  <a href="#" class="w3-button">3</a>
				  <a href="#" class="w3-button">4</a>
				  <a href="#" class="w3-button">»</a>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>