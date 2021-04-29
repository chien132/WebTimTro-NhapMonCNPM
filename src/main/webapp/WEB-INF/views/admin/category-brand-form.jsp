<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="utf-8" />
<title>${type} - ${action}</title>
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
						<li class="breadcrumb-item active">Category & Brand Table</li>
						<li
							style="float: right; color: blue; margin-left: 37vw; text-transform: uppercase; font-size: 20px;">${message}</li>
					</ol>
					<div class="table-responsive"
						style="text-align: left; margin-top: -6vh">
						<div class="col-lg-12">
							<div class="card shadow-lg border-0 rounded-lg mt-5">
								<div class="card-header">
									<h3 class="text-center font-weight-light my-4"
										style="text-transform: capitalize;">${action} ${type}</h3>
								</div>
								<div class="card-body" style="min-height: 80vh;">
									<form:form action="admin/${type}/${action}.htm"
										modelAttribute="p" method="post">
										<div class="col-lg-3">
											<div class="form-row">
												<div class="col-md-12">
													<div class="form-group">
														<label class="small mb-1"
															style="text-transform: capitalize;"><h5>${type} Name</h5></label>
														<form:input type="text" path="name" class="form-control"
															required="required" />
													</div>
												</div>

											</div>

											<div class="form-row">
												<h5 style="color: red;">${message}</h5>
												<button class="btn btn-primary btn-block" type="submit">Submit</button>
												<button class="btn btn-primary btn-block"
													onclick="location.href='admin/category/table.htm'"
													type="button">Cancel</button>
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