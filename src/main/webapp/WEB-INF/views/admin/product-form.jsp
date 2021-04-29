<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>Product - ${action}</title>
<script>
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$('#thisimage').attr('src', e.target.result).width(600)

			};

			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
<script class="jsbin"
	src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>

<jsp:include page="navmenu.jsp"></jsp:include>
</head>

<body>
	<div>
		<!-- 		<div style="padding: 20px;"></div> -->
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid" style="margin-top: 3vh;">
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="index.htm">Dashboard</a></li>
						<li class="breadcrumb-item active">Bill Table</li>
						<li
							style="float: right; color: blue; margin-left: 37vw; text-transform: uppercase; font-size: 20px;">${message}</li>
					</ol>
					<div class="table-responsive"
						style="text-align: left; margin-top: -6vh">
						<div class="col-lg-12">
							<div class="card shadow-lg border-0 rounded-lg mt-5">
								<div class="card-header">
									<h3 class="text-center font-weight-light my-4"
										style="text-transform: capitalize;">${action} Product</h3>
								</div>
								<div class="card-body" style="min-height: 80vh;">
									<form:form action="admin/product/${action}.htm"
										modelAttribute="p" enctype="multipart/form-data" method="post">
										<div class="row">
											<div class="col-lg-9">
												<div class="form-row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="small mb-1"><h5>Name</h5></label>
															<form:input type="text" path="name" class="form-control"
																required="required" />
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="small mb-1"><h5>Image</h5> </label> <input
																name="photo" type="file" onchange="readURL(this);"
																class="form-control" /> <i style="color: green">leave
																blank if you don't want to change</i>
														</div>
													</div>
												</div>
												<div class="form-row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="small mb-1"><h5>Category</h5></label>
															<form:select class="form-control" path="category.id"
																items="${categories}" itemValue="id" itemLabel="name"></form:select>
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="small mb-1"><h5>Brand</h5></label>
															<form:select class="form-control" path="brand.id"
																items="${brands}" itemValue="id" itemLabel="name"></form:select>
														</div>
													</div>
												</div>
												<div class="form-row">
													<div class="col-md-4">
														<div class="form-group">
															<label class="small mb-1"><h5>Price</h5></label>
															<form:input type="number" min="1000"
																path="price" required="required" class="form-control" />
														</div>
													</div>
													<div class="col-md-4">
														<div class="form-group">
															<label class="small mb-1"><h5>Discount (%)</h5></label>
															<form:input type="number" min="0" max="100" step="1"
																path="discount" required="required" class="form-control" />
														</div>
													</div>
													<div class="col-md-4">
														<div class="form-group">
															<label class="small mb-1"><h5>Quantity</h5></label>
															<form:input path="quantity" type="number" min="0"
																required="required" class="form-control" />
														</div>
													</div>
												</div>
												<div class="form-row">
													<div class="col-md-12">
														<div class="form-group">
															<label class="small mb-1"><h5>Description</h5></label>
															<form:textarea style="height: 20vh;" type="text"
																path="des" required="required" class="form-control" />

														</div>
													</div>
												</div>

												<div class="form-group mt-4 mb-0">
													<h5 style="color: red;">${message}</h5>
													<button class="btn btn-primary btn-block" type="submit">Submit</button>
													<button class="btn btn-primary btn-block"
														onclick="location.href='admin/product/table.htm'"
														type="button">Cancel</button>
												</div>
											</div>
											<div class="col-lg-3">
												<img id="thisimage"
													style="max-width: -webkit-fill-available; max-height: 70vh;"
													alt="" src="${p.image}">
											</div>
										</div>
									</form:form>
								</div>
							</div>
						</div>

					</div>
				</div>
			</main>
		</div>
	</div>
</body>

</html>