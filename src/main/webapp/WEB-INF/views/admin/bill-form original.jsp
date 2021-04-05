<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>Bill ${action}</title>
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
										style="text-transform: capitalize;">${action} Bill</h3>
								</div>
								<div class="card-body row" style="min-height: 50vh;">
									<c:choose>
										<c:when test="${action=='update'||action=='add'}">
											<form:form class="col-lg-3" action="admin/bill/${action}.htm"
												modelAttribute="b" method="post">

												<div class="form-row">
													<div class="col-md-12">
														<div class="form-group">
															<label class="small mb-1"
																style="text-transform: capitalize;"><h5>User</h5></label>
															<form:select path="user.id" class="form-control"
																items="${users}" itemValue="id" itemLabel="username"
																required="required"></form:select>
														</div>
													</div>
												</div>

												<div class="form-row">
													<div class="col-md-12">
														<div class="form-group">
															<label class="small mb-1"
																style="text-transform: capitalize;"><h5>Buy
																	date</h5></label>
															<form:input type="date" path="buydate"
																class="form-control" />
														</div>
													</div>
												</div>

												<div class="form-row">
													<div class="col-md-6">
														<div class="form-group">
															<label class="small mb-1"
																style="text-transform: capitalize;"><h6>
																	Paid (Checked = Paid)
																	</h5></label>
															<div></div>
															<form:checkbox selected="true" class="form-control"
																style="max-width: 2vw;" path="paid" />
														</div>
													</div>
													<div class="col-md-6">
														<div class="form-group">
															<label class="small mb-1"
																style="text-transform: capitalize;"><h6>
																	Done (Checked = Done)
																	</h5></label>
															<div></div>
															<form:checkbox selected="true" class="form-control"
																style="max-width: 2vw;" path="status" />
														</div>
													</div>
												</div>

												<c:choose>
													<c:when test="${action=='add'}">
														<div class="form-row">
															<h5 style="color: red;">${message}</h5>
															<button class="btn btn-primary btn-block" type="submit">Add
																Items</button>
															<button class="btn btn-primary btn-block"
																onclick="location.href='admin/bill/table.htm'"
																type="button">Cancel</button>
														</div>
													</c:when>
													<c:when test="${action=='update'}">
														<div class="form-row">
															<h5 style="color: red;">${message}</h5>
															<button class="btn btn-primary btn-block" type="submit">Submit</button>
															<button class="btn btn-primary btn-block"
																onclick="location.href='admin/bill/table.htm'"
																type="button">Cancel</button>
														</div>
														<div class="form-row" style="margin-top: 5vh;">
															<h5 style="color: red;">${imessage}</h5>
															<button class="btn btn-primary btn-block"
																onclick="location.href='admin/bill/edititem/${b.id}.htm'"
																type="button">Update Bill Items</button>
														</div>

													</c:when>
												</c:choose>
											</form:form>
										</c:when>
										<c:when test="${action=='edititem'}">
											<form:form class="col-lg-3"
												action="admin/bill/${action}/${b.id}.htm"
												modelAttribute="bi" method="post">
												<div class="form-row">
													<div class="col-md-12">
														<div class="form-group">
															<label class="small mb-1"
																style="text-transform: capitalize;"><h5>Product</h5></label>
															<form:select path="product.id" class="form-control"
																items="${products}" itemValue="id" itemLabel="name"
																required="required"></form:select>
														</div>
													</div>
												</div>
												<div class="form-row">
													<div class="col-md-12">
														<div class="form-group">
															<label class="small mb-1"
																style="text-transform: capitalize;"><h5>Quantity</h5></label>
															<form:input path="amount" type="number" min="1" value="1"
																class="form-control" required="required" />
														</div>
													</div>
												</div>
												<div class="form-row">
													<h5 style="color: red;">${message}</h5>
													<button class="btn btn-primary btn-block" type="submit">Add
														Items</button>
													<button class="btn btn-primary btn-block"
														onclick="location.href='admin/bill/table.htm'"
														type="button">Cancel</button>
												</div>
											</form:form>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${action!='add'}">
											<div class="col-lg-9">
												<div class="card-body">
													<div class="table-responsive">
														<table style="color: red;" class="" id="dataTable"
															width="50%" cellspacing="0">
															<thead>
																<tr>
																	<th>ID</th>
																	<th>User</th>
																	<th>Buy Date</th>
																	<th>Is paid</th>
																	<th>Status</th>
																</tr>
															</thead>
															<tbody>
																<tr>
																	<td>${b.id}</td>
																	<td>${b.user.fullname}</td>
																	<td>${b.buydate}</td>
																	<td>${b.paid?"Paid":"In Progress"}</td>
																	<td>${b.status?"Done":"In Progress"}</td>
																</tr>
														</table>
														<table class="table table-bordered" id="dataTable"
															width="100%" cellspacing="0">
															<tr>
																<th>ID</th>
																<th>Product</th>
																<th>Quantity</th>
																<th>Action</th>
															</tr>
															<c:forEach var="i" items="${b.billItems}">
																<tr>
																	<td>${i.id}</td>
																	<td>${i.product.name}</td>
																	<td>${i.amount}</td>
																	<td>
																		<button class="btn btn-primary"
																			onclick="location.href='admin/bill/edititem/${b.id}/delete/${i.id}.htm'"
																			style="width: 80%; min-width: 55px;">Delete</button>
																	</td>
																</tr>
															</c:forEach>
															</tbody>
														</table>
													</div>
												</div>

											</div>
										</c:when>
									</c:choose>
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