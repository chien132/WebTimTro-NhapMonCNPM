<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html lang="en">
<base href="${pageContext.servletContext.contextPath}/">
<head>
<meta charset="utf-8" />
<title>Admin - Index</title>
<jsp:include page="navmenu.jsp"></jsp:include>
</head>

<body>
	<!-- 	<div style="padding: 20px;"></div> -->
	<div id="layoutSidenav_content">
		<main>
			<div class="container-fluid">
				<h1 class="mt-4">Dashboard</h1>
				<ol class="breadcrumb mb-4">
					<li class="breadcrumb-item active">Dashboard</li>
				</ol>

				<form id="p" action="admin/index.htm" method="post">
					<div class="col-lg-3">
						<div class="form-row">
							<div class="col-md-12">
								<div class="form-row">
									<div class="col-lg-6">
										<label class="small mb-1" style="text-transform: capitalize;"><h5>From
												date</h5></label> <input name="from" type="date" class="form-control"
											required="required">
									</div>
									<div class="col-lg-6">
										<label class="small mb-1" style="text-transform: capitalize;"><h5>To
												date</h5></label> <input name="to" type="date" class="form-control"
											required="required">
									</div>
									<button class="btn btn-primary btn-block"
										style="margin-top: 1vh;" type="submit">Submit</button>
								</div>
							</div>

						</div>
					</div>
				</form>
				<div class="col-lg-12">
					<div class="card-body">
						<div class="table-responsive">
							<table class="table table-bordered" id="dataTable" width="100%"
								cellspacing="0">
								<thead style="background-color: dodgerblue; color: white;">
									<tr>
										<th>ID</th>
										<th>User</th>
										<th>Buy Date</th>
										<th>Paid</th>
										<th>Status</th>
										<th>Value</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach var="b" items="${successlist}">
										<tr style="background-color: forestgreen; color: white;">
											<td>${b.id}</td>
											<td>${b.user.fullname}</td>
											<td><f:formatDate value="${b.buydate}"
													pattern="dd/MM/yyyy" /></td>
											<td>${b.paid?"Paid":"Waiting for Payment"}</td>
											<td>${b.status?"Done":"In Progress"}</td>
											<td><a style="color:white;" href="admin/bill/update/${b.id}.htm"><f:formatNumber type="currency" currencySymbol=""
													maxFractionDigits="0" value="${b.cartvalue}" /> ₫</a></td>
										</tr>
									</c:forEach>
									<c:forEach var="b" items="${progresslist}">
										<tr style="background-color: darkorange; color: white;">
											<td>${b.id}</td>
											<td>${b.user.fullname}</td>
											<td><f:formatDate value="${b.buydate}"
													pattern="dd/MM/yyyy" /></td>
											<td>${b.paid?"Paid":"Waiting for Payment"}</td>
											<td>${b.status?"Done":"In Progress"}</td>
											<td><f:formatNumber type="currency" currencySymbol=""
													maxFractionDigits="0" value="${b.cartvalue}" /> ₫</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

				</div>
				<div class="row">
					<div class="col-xl-4">
						<div class="card bg-primary text-white mb-4">
							<div class="card-body">
								<h4>Total Card: ${successnumber+progressnumber}</h4>
							</div>

						</div>
					</div>
					<div class="col-xl-4">
						<div class="card bg-warning text-white mb-4">
							<div class="card-body">
								<h4>${progressnumber }
									Processing:
									<f:formatNumber type="currency" value="${progresscash}"
										maxFractionDigits="0" currencySymbol="" />
									<sup>đ</sup>
								</h4>
							</div>

						</div>
					</div>
					<div class="col-xl-4">
						<div class="card bg-success text-white mb-4">
							<div class="card-body">
								<h4>
									${successnumber } Success:
									<f:formatNumber type="currency" value="${cash}"
										maxFractionDigits="0" currencySymbol="" />
									<sup>đ</sup>
								</h4>
							</div>

						</div>
					</div>

				</div>
			</div>
		</main>
	</div>
</body>

</html>