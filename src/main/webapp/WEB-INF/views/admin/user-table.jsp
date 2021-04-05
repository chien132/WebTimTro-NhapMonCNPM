<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>User - Table</title>
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
						<li class="breadcrumb-item active">User Table</li>
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
										<th>Avatar</th>
										<th>Full name</th>
										<th>Username</th>
										<th>Password</th>
										<th>Email</th>
										<th>Admin</th>
										<th>Action</th>
									</tr>
								</thead>
								<tfoot>
									<tr>
										<th>ID</th>
										<th>Avatar</th>
										<th>Full name</th>
										<th>Username</th>
										<th>Password</th>
										<th>Email</th>
										<th>Admin</th>
										<th>Action</th>
									</tr>
								</tfoot>
								<tbody>
									<c:forEach var="u" items="${users}">
										<tr>
											<td>${u.id}</td>
											<th><img src="${u.avatar}" style="max-height: 100px"></th>
											<td>${u.fullname}</td>
											<td>${u.username}</td>
											<td>${u.password}</td>
											<td>${u.email}</td>
											<td>${u.admin}</td>
											<td><button class="btn btn-primary"
													onclick="location.href='admin/user/update/${u.id}.htm'"
													style="width: 45%; min-width: 55px;">Sửa</button>
												<div style="padding: 3px;"></div> <c:if
													test="${u.billsize==0}">
													<button class="btn btn-primary"
														onclick="location.href='admin/user/delete/${u.id}.htm'"
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