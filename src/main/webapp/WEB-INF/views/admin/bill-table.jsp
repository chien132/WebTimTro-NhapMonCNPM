<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>Bill Table</title>

<jsp:include page="navmenu.jsp"></jsp:include>
<style type="text/css">
.table th, .table td {
	padding: 0.35rem;
	vertical-align: top;
	border-top: 1px solid #dee2e6;
}

.table th {
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
						<li class="breadcrumb-item active">Bill Table</li>
						<li
							style="float: right; color: blue; margin-left: 37vw; text-transform: uppercase; font-size: 20px;">${message}</li>
					</ol>

					<div class="col-lg-12">
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<thead>
										<tr>
											<th>ID</th>
											<th>User</th>
											<th>Buy Date</th>
											<th>Paid</th>
											<th>Status</th>
											<th></th>
											<th>Action</th>
										</tr>
									</thead>
									<tfoot>
										<tr>
											<th>ID</th>
											<th>User</th>
											<th>Buy Date</th>
											<th>Paid</th>
											<th>Status</th>
											<th></th>
											<th>Action</th>
										</tr>
									</tfoot>
									<tbody>
										<c:forEach var="b" items="${bills}">
											<tr>
												<td>${b.id}</td>
												<td>${b.user.fullname}</td>
												<td>${b.buydate}</td>
												<td>${b.paid?"Paid":"Waiting for Payment"}</td>
												<td>${b.status?"Done":"In Progress"}</td>
												<td style="width: 5vh"><c:if test="${!b.status}">
														<button class="btn btn-primary"
															onclick="location.href='admin/bill/complete/${b.id}.htm'"
															style="width: 100%; min-width: 55px;">Complete</button>
													</c:if></td>
												<td>

													<button class="btn btn-primary"
														onclick="location.href='admin/bill/view/${b.id}.htm'"
														style="width: 20%; min-width: 55px;">Xem</button>
													<button class="btn btn-primary"
														onclick="location.href='admin/bill/update/${b.id}.htm'"
														style="width: 20%; min-width: 55px;">Sửa</button> <c:if
														test="${b.cartqty==0}">
														<button class="btn btn-primary"
															onclick="location.href='admin/bill/delete/${b.id}.htm'"
															style="width: 20%; background-color: red; min-width: 55px;">Delete</button>
													</c:if> <!-- 		<div style="padding: 3px;"></div> 													<button class="btn btn-primary" -->
													<%-- 															onclick="location.href='admin/bill/delete/${b.id}.htm'" --%>
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
			</main>
		</div>
	</div>
</body>

</html>