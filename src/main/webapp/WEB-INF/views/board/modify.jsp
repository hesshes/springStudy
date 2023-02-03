<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Modify</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>
			<form role="form" action="/board/modify" method="post">
				<div class="panel-body">
					<div class="form-gorup">
						<label>Bno</label><input class="form-control" name="bno" value="${board.bno }" readonly="readonly">
					</div>
					<div class="form-gorup">
						<label>Title</label><input class="form-control" name="title" value="${board.title }">
					</div>
					<div class="form-gorup">
						<label>Text area</label>
						<textarea class="form-control" rows="3" name="content">${board.content }</textarea>
					</div>
					<div class="form-gorup">
						<label>Writer</label><input class="form-control" name="writer" value="${board.writer }" readonly="readonly">
					</div>
					<div class="form-gorup">
						<label>regDate</label><input class="form-control" name="regDate" value='<fmt:formatDate value="${board.regdate }" pattern="yyyy/MM/dd"/>' readonly="readonly">
					</div>
					<div class="form-gorup">
						<label>updateDate</label><input class="form-control" name="updateDate" value='<fmt:formatDate value="${board.updateDate }" pattern="yyyy/MM/dd"/>' readonly="readonly">
					</div>
					<button type="submit" data-ooper="modify" class="btn btn-default">Modify</button>
					<button type="submit" data-ooper="remove" class="btn btn-danger">Remove</button>
					<button data-oper="list" class="btn btn-info" onclick="location.href='/board/list'">List</button>
				</div>
				<!-- /. end panel-body -->
			</form>
		</div>
		<!--  /. end panel-default -->
	</div>
	<!--  /. end col -->
</div>
<!-- /. end row -->
<%@include file="../includes/footer.jsp"%>
<script>
	$(document).ready(function() {
		var formObj = $("form");

		$("button").on("click", function(e) {
			debugger
			e.preventDefault();
			var operation = $(this).data("oper");
			console.log(operation);

			if (operation === 'remove') {
				formObj.attr("action", "/board/remove");
			} else if (operation === "list") {
				formObj.attr("action","/board/list").attr("method","get");
				formObj.empty();
			}
			formObj.submit();
		});
	});
</script>