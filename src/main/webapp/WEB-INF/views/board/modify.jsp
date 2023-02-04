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
			<div class="panel-body">
				<form role="form" action="/board/modify" method="post">

					<input type="hidden" name="pageNum" value="${cri.pageNum }">
					<input type="hidden" name="amount" value="${cri.amount }">
					<input type="hidden" name="type" value="${cri.type }">
					<input type="hidden" name="keyword" value="${cri.keyword }">

					<div class="form-gorup">
						<label>Bno</label>
						<input class="form-control" name="bno" value="${board.bno }" readonly="readonly">
					</div>
					<div class="form-gorup">
						<label>Title</label>
						<input class="form-control" name="title" value="${board.title }">
					</div>
					<div class="form-gorup">
						<label>Text area</label>
						<textarea class="form-control" rows="3" name="content">${board.content }</textarea>
					</div>
					<div class="form-gorup">
						<label>Writer</label>
						<input class="form-control" name="writer" value="${board.writer }" readonly="readonly">
					</div>
					<div class="form-gorup">
						<label>regDate</label>
						<input class="form-control" name="regDate" value='<fmt:formatDate value="${board.regdate }" pattern="yyyy/MM/dd"/>' readonly="readonly">
					</div>
					<div class="form-gorup">
						<label>updateDate</label>
						<input class="form-control" name="updateDate" value='<fmt:formatDate value="${board.updateDate }" pattern="yyyy/MM/dd"/>' readonly="readonly">
					</div>
					<button type="submit" data-oper="modify" class="btn btn-default">Modify</button>
					<button type="submit" data-oper="remove" class="btn btn-danger">Remove</button>
					<button data-oper="list" class="btn btn-info" onclick="location.href='/board/list'">List</button>
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
		var formObj = $("form");

		$("button").on("click", function(e) {

			e.preventDefault();

			var operation = $(this).data("oper");

			console.log(operation);

			if (operation === 'remove') {
				formObj.attr("action", "/board/remove");
			} else if (operation === "list") {

				formObj.attr("action", "/board/list").attr("method", "get");
				var pageNumTag = $("input[name='pageNum']").clone();
				var amountTag = $("input[name='amount']").clone();
				var keywordTag = $("input[name='keyword']").clone();
				var typeTag = $("input[name='type']").clone(); 
				
				formObj.empty();
				
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				formObj.append(keywordTag);
				formObj.append(typeTag);

			}

			formObj.submit();
		});
	});
</script>