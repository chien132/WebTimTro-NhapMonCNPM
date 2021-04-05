<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>
<%--         <base href="${pageContext.servletContext.contextPath}/"> --%>
<head>
<<jsp:include page="header.jsp"></jsp:include>
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


</head>

<body id="homeall">
	<div class="wrapper">
		<div class="clearfix"></div>
		<div class="container_fullwidth" style="min-height: 57vh;">
			<div class="col-md-12" style="background: white;">
				<!-- 				<h3 style="color: green; font-weight: 500;">Success !</h3> -->
				<h3
					style="color: green; padding-left: 10vh; -webkit-text-stroke-width: medium;"
					class="title">Your Profile:</h3>
				<div class="clearfix"></div>
				<div class="card-body">
					<form:form action="editprofile.htm" modelAttribute="user"
						enctype="multipart/form-data" method="post">
						<div class="row">
							<div class="col-lg-7">
								<div class="form-row">
									<div class="col-md-6">
										<div class="form-group">
											<label class="small mb-1"><h5>Full Name</h5></label>
											<form:input type="text" path="fullname" class="form-control" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<label class="small mb-1"><h5>Avatar</h5> </label> <input
												name="photo" type="file" onchange="readURL(this);"
												class="form-control" /> <i style="color: green">leave
												blank if you don't want to change</i>
										</div>
									</div>
								</div>
								<div class="form-row">
									<div class="col-md-4">
										<div class="form-group">
											<label class="small mb-1"><h5>Username</h5></label>
											<form:input class="form-control" required="required"
												path="username" />
										</div>
									</div>
									<div class="col-md-4">
										<div class="form-group">
											<label class="small mb-1"><h5>Password</h5></label>
											<form:input type="password" class="form-control" required="required"
												path="password" />
										</div>
									</div>

								</div>

								<div class="form-row">
									<div class="col-md-12">
										<div class="form-group">
											<label class="small mb-1"><h5>Email</h5></label>
											<form:input class="form-control" type="email" path="email" />
										</div>
									</div>
								</div>

								<div class="form-group mt-4 mb-0">
									<h5 style="color: red;">${message}</h5>
									<button class="btn btn-primary btn-block" type="submit">Submit</button>
									<button class="btn btn-primary btn-block"
										onclick="location.href='admin/user/table.htm'" type="button">Cancel</button>
									<button class="btn btn-primary btn-block"
										onclick="location.href='billlist.htm'" type="button">View your bills</button>
								</div>
							</div>
							<div class="col-lg-3"><h3 style="margin-bottom: 5vh;">Avatar:</h3>
								<img id="thisimage"
									style="max-width: -webkit-fill-available; max-height: 70vh;"
									alt="" src="${user.avatar}">
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

	</div>


	<div class="clearfix"></div>

	<div class="clearfix"></div>
	<jsp:include page="footer.jsp"></jsp:include>

</body>

</html>