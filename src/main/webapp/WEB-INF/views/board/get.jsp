<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp"%>

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Board Register</h1>
	</div>
	<!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">Board Read Page</div>
			<div class="panel-body">
				<div class="form-gorup">
					<label>Bno</label>
					<input class="form-control" name="bno" value="${board.bno }" readonly="readonly">
				</div>
				<div class="form-gorup">
					<label>Title</label>
					<input class="form-control" name="bno" value="${board.title }" readonly="readonly">
				</div>
				<div class="form-gorup">
					<label>Text area</label>
					<textarea class="form-control" rows="3" name="content" readonly="readonly">${board.content }</textarea>
				</div>
				<div class="form-gorup">
					<label>Writer</label>
					<input class="form-control" name="writer" value="${board.writer }" readonly="readonly">
				</div>
				<button data-oper="modify" class="btn btn-default">Modify</button>
				<button data-oper="list" class="btn btn-info">List</button>

				<form id="operForm" action="/board/modify" method="get">
					<input type="hidden" id="bno" name="bno" value="${board.bno }">
					<input type="hidden" name="pageNum" value="${cri.pageNum }">
					<input type="hidden" name="amount" value="${cri.amount }">
					<input type="hidden" name="type" value="${cri.type }">
					<input type="hidden" name="keyword" value="${cri.keyword }">
				</form>
			</div>
			<!-- /. end panel-body -->
		</div>
		<!--  /. end panel-default -->
	</div>
	<!--  /. end col -->
</div>
<!-- /. end row -->
<%@include file="../includes/footer.jsp"%>

<script>
	$(document).ready(function() {

		var operForm = $("#operForm");

		$("button[data-oper='modify']").on("click", function(e) {
			operForm.attr("action", "/board/modify").submit();
		});

		$("button[data-oper='list']").on("click", function(e) {
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list");
			operForm.submit();
		});
	});
</script>