<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>Category & Brand Table</title>

<jsp:include page="navmenu.jsp"></jsp:include>
<style type="text/css">
.table th, .table td {
	padding: 0.35rem;
	vertical-align: top;
	border-top: 1px solid #dee2e6;
}
.table th{
color: blue;
}
</style>
</head>

<body>
	<div id="layoutSidenav">
		<div id="layoutSidenav_content">
			<main>
				<div class="container-fluid" style="margin-top: 3vh;">
					<ol class="breadcrumb mb-4">
						<li class="breadcrumb-item"><a href="index.htm">Dashboard</a></li>
						<li class="breadcrumb-item active">Category & Brand Table</li>
						<li
							style="float: right; color: blue; margin-left: 37vw; text-transform: uppercase; font-size: 20px;">${message}</li>
					</ol>

					<div class="row">
						<div class="col-lg-6">
							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%"
										cellspacing="0">
										<thead>
											<tr>
												<th>ID</th>
												<th>Category</th>
												<th>Action</th>
											</tr>
										</thead>
<!-- 										<tfoot> -->
<!-- 											<tr> -->
<!-- 												<th>ID</th> -->
<!-- 												<th>Category</th> -->
<!-- 												<th>Action</th> -->
<!-- 											</tr> -->
<!-- 										</tfoot> -->
										<tbody>
											<c:forEach var="c" items="${categories}">
												<tr>
													<td>${c.id}</td>
													<td>${c.name}</td>
													<td><button class="btn btn-primary"
															onclick="location.href='admin/category/update/${c.id}.htm'"
															style="width: 40%; min-width: 55px;">Sửa</button> <!-- 		<div style="padding: 3px;"></div> 													<button class="btn btn-primary" -->
														<%-- 															onclick="location.href='admin/product/delete/${p.id}.htm'" --%>
														<!-- 															style="width: 45%; min-width: 55px;">Xóa</button> -->
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>

						</div>
						<div class="col-lg-6">
							<div class="card-body">
								<div class="table-responsive">
									<table class="table table-bordered" id="dataTable" width="100%"
										cellspacing="0">
										<thead>
											<tr>
												<th>ID</th>
												<th>Brand</th>
												<th>Action</th>
											</tr>
										</thead>
<!-- 										<tfoot> -->
<!-- 											<tr> -->
<!-- 												<th>ID</th> -->
<!-- 												<th>Brand</th> -->
<!-- 												<th>Action</th> -->
<!-- 											</tr> -->
<!-- 										</tfoot> -->
										<tbody>
											<c:forEach var="b" items="${brands}">
												<tr>
													<td>${b.id}</td>
													<td>${b.name}</td>
													<td><button class="btn btn-primary"
															onclick="location.href='admin/brand/update/${b.id}.htm'"
															style="width: 40%; min-width: 55px;">Sửa</button> <!-- 			<div style="padding: 3px;"></div>													<button class="btn btn-primary" -->
														<%-- 															onclick="location.href='admin/product/delete/${p.id}.htm'" --%>
														<!-- 															style="width: 45%; min-width: 55px;">Xóa</button> -->
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
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