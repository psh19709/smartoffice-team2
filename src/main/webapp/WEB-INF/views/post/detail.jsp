<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
<title>애플리케이션</title>
</head>
<body>
<c:set var="top" value="post" />
<%@ include file="../common/navbar.jsp" %>
<div class="container">
	<div class="row">
		<div class="col-3"> <!-- 사이드 바 시작 -->
			<%@ include file="../common/left/post.jsp" %>
		</div> <!-- 사이드 바 끝 -->
		<div class="col-9">
			<div class="row border m-1 mb-4">
				<p class="fs-1 my-2">자유 게시판</p>
				<p class="fs-5 my-3">자유롭게 의견을 나누고 생각의 폭을 넓혀보세요.</p>
			</div>
			<div class="row">
				<div class="col">
					<div class="my-3 border-bottom border-secondary">
						<p class="fs-3 fw-bold">${post.title }</p>
					</div>
					<div class="my-3 border-bottom border-secondary">
						<div class="row mb-3">
							<div class="col-6">
								<div>
									<span class="fs-5">${post.name }</span>
								</div>
								<div>
									<span>${post.createdDate }</span>
									<span>조회수 ${post.scrapCount }</span>
									<c:forEach var="file" items="${post.attachedFiles }">
										<a href="/post/download?filename=${file.savedName }" class="btn btn-outline-dark btn-sm">${file.originalName }</a>
									</c:forEach>
								</div>
							</div>
							<div class="col-6 text-end pe-5">
								<i class="bi bi-pin-angle-fill fs-3"></i>
								<i class="bi bi-pin-angle fs-3"></i>
								<span>${post.scrapCount }</span>
							</div>
						</div>
						<div class="row mb-3">
							<p>${post.content }</p>
						</div>
						<div class="row mb-3">
							<div class="col py-3">
								<c:forEach var="tag" items="${post.tags }">
									<a href="/post/list?type=content&keyword=${tag.content }" >
									<span class="badge text-bg-success">${tag.content }</span>
									</a>
								</c:forEach>
							</div>
							<div class="col text-end pe-5">
								<div class="d-inline me-4">
									<button type="button" class="btn btn-outline-primary">수정</button>
									<button type="button" class="btn btn-outline-danger">삭제</button>
								</div>
								<div class="d-inline">
									<i class="bi bi-hand-thumbs-up fs-3"></i>
									<i class="bi bi-hand-thumbs-up-fill fs-3"></i>
									<span>${post.recommendCount }</span>
								</div>
							</div>
						</div>
					</div>
					<div class="my-5 border-bottom border-secondary">
						<div class="mb-3">
						  	<textarea class="form-control" rows="3" placeholder="내용을 입력해주세요."></textarea>
						</div>
						<div class="mb-3 text-end">
							<button type="button" class="btn btn-outline-primary">댓글 쓰기</button>
						</div>
					</div>					
					<div class="my-3 border-bottom">
						<div class="row mb-3">
							<div>
								<span>댓글 작성자</span>
							</div>
							<div>
								<span>약 1시간 전</span>
							</div>
						</div>
						<div class="row">
							<p>댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다. 댓글 내용입니다.  </p>
						</div>
						<div class="row mb-3">
							<div class="col text-end pe-5">
								<div class="d-inline me-4">
									<button type="button" class="btn btn-outline-primary btn-sm">수정</button>
									<button type="button" class="btn btn-outline-danger btn-sm">삭제</button>
								</div>
								<div class="d-inline">
									<i class="bi bi-hand-thumbs-up fs-5"></i>
									<i class="bi bi-hand-thumbs-up-fill fs-5"></i>
									<span> 10</span>
								</div>
							</div>						
						</div>
					</div>					
				</div>
			</div>
		</div>
	</div>  
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
</body>
</html>