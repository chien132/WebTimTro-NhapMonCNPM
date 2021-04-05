<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>Product - Table</title>
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
						<li class="breadcrumb-item active">Product Table</li>
						<li
							style="float: right; color: blue; margin-left: 37vw; text-transform: uppercase; font-size: 20px;">${message}</li>
					</ol>

					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-bordered" id="dataTable" width="100%"
								cellspacing="0">
								<thead>
									<tr>
										<th>ID</th>
										<th>Image</th>
										<th>Category</th>
										<th>Brand</th>
										<th>Name</th>
										<th>Description</th>
										<th>Price</th>
										<th>Qty</th>
										<th>Disc</th>
										<th>Action</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th>ID</th>
										<th>Image</th>
										<th>Category</th>
										<th>Brand</th>
										<th>Name</th>
										<th>Description</th>
										<th>Price</th>
										<th>Qty</th>
										<th>Disc</th>
										<th>Action</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach var="p" items="${products}">
										<tr>
											<td>${p.id}</td>
											<th><img src="${p.image}" style="max-height: 100px"></th>
											<td>${p.category.name}</td>
											<td>${p.brand.name}</td>
											<td style="max-width: 22vh;">${p.name}</td>
											<td style="max-width: 62vh;">${p.des}</td>
											<td><f:formatNumber type="currency"
													maxFractionDigits="0" currencySymbol="" value="${p.price}" /></td>
											<td style="min-width: 5vh;">${p.quantity}</td>
											<td style="min-width: 5vh;"><f:formatNumber
													type="percent" value="${p.discount/100}" /></td>
											<td><button class="btn btn-primary"
													onclick="location.href='admin/product/update/${p.id}.htm'"
													style="width: 45%; min-width: 55px;">Sửa</button>
												<div style="padding: 3px;"></div> <c:if
													test="${p.boughtqty==0}">
													<button class="btn btn-primary"
														onclick="location.href='admin/product/delete/${p.id}.htm'"
														style="width: 45%; min-width: 55px; background-color: red;">Xóa</button>
												</c:if></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

				</div>
			</main>
		</div>
	</div>
</body>

</html>