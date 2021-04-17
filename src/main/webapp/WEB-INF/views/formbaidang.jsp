<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<title>Login</title>
<!-- <link href="resources/css/styles.css" rel="stylesheet" /> -->

<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js"></script>
<style type="text/css">
#homelogin {
	background: url(resources/images/homeloan.jpg) center top no-repeat;
	background-size: cover;
	-webkit-background-size: cover;
	background-attachment: fixed;
}

body {
	background-color: #DADADA;
}

body>.grid {
	height: 100%;
}

.image {
	margin-top: -100px;
}

.column {
	max-width: 1050px;
}
</style>
</head>
<jsp:include page="header.jsp"></jsp:include>
<body
	style="background: url(resources/images/homeloan.jpg) center top no-repeat; background-size: cover; -webkit-background-size: cover; background-attachment: fixed;">
		<div class="ui middle aligned center aligned grid" style="background: #000000a3; height: 101.5vh;">
			<div class="column">


				<form:form class="ui large form" action="register.htm"
					modelAttribute="account" method="post">
					<div class="ui stacked segment">
						<h2 class="ui big">
							<i class="signup icon"></i> Tạo bài đăng
						</h2>
						<div class="field">
							<label style="float: left;">Tiêu đề<b style="color: red;">*</b></label>
							<div class="ui left input">
								<!-- <i class="user icon"></i> -->
								<form:input path="username" value="${account.username}" type="text" />
								<i><form:errors style="color: red;font-size: 15px;"
										path="username" /></i>
							</div>
						</div>

						<div class="field">
							<div class="ui left input" style="max-height: 40px;">

								<div class="grid eight wide column">
									<label><b>Số nhà: </b></label>
									<form:input path="password" value="${account.password}" />
								</div>
								<div class="grid eight wide column">
									<label style="align-self: center;"><b>Đường: </b></label>
									<form:input path="password" value="${account.password}" />
								</div>

								<div style="margin-left: 30px">
									<jsp:include page="demo/index.jsp"></jsp:include>
								</div>

								<form:errors style="color: red;font-size: 15px;" path="password" />
							</div>
						</div>

						<div class="field ui grid" style="margin-top: 30px">
							<div class="five wide column">
								<label style="float: left;"><b>Số phòng cho thuê</b> <i
									style="color: red;"> *</i> </label>
								<div class="ui left input">
									<form:input path="email" value="${account.email}" type="number" />
									<form:errors style="color: red;font-size: 15px;" path="email" />
								</div>
							</div>

							<div class="five wide column">
								<label style="float: left;"><b>Số người mỗi phòng</b><i
									style="color: red;"> *</i> </label>
								<div class="ui left input">
									<form:input path="email" value="${account.email}" type="number" />
									<form:errors style="color: red;font-size: 15px;" path="email" />
								</div>
							</div>
							<div class="five wide column">
								<label style="float: left;"><b>Diện tích</b><i
									style="color: red;"> *</i> </label>
								<div class="ui left input">
									<form:input path="email" value="${account.email}" type="email" />
									<form:errors style="color: red;font-size: 15px;" path="email" />
								</div>
							</div>
						</div>
						<div class="field ui grid">
							<div class="eight wide column">
								<label style="float: left;"><b>Tiền cọc</b> <i
									style="color: red;"> *</i> </label>
								<div class="ui left input">
									<form:input path="email" value="${account.email}" type="email" />
									<form:errors style="color: red;font-size: 15px;" path="email" />
								</div>
							</div>
							<div class="eight wide column">
								<label style="float: left;"><b>Tiền thuê</b> <i
									style="color: red;"> *</i> </label>
								<div class="ui left input">
									<form:input path="email" value="${account.email}" type="email" />
									<form:errors style="color: red;font-size: 15px;" path="email" />
								</div>
							</div>
						</div>
						<div class="field">
							<label style="float: left;">Mô tả<b style="color: red;">*</b></label>
							<div class="ui left input">
								<!-- <i class="user icon"></i> -->
								<form:textarea path="username" value="${account.username}"
									type="text" />
								<i><form:errors style="color: red;font-size: 15px;"
										path="username" /></i>
							</div>
						</div>
						
						<div class="field">
							<label style="float: left;">Hình ảnh<b style="color: red;">*</b></label>
							<div class="ui left input">
								<!-- <i class="user icon"></i> -->
								<form:input path="username" value="${account.username}"
									type="file" />
								<i><form:errors style="color: red;font-size: 15px;"
										path="username" /></i>
							</div>
						</div>

						<button class="ui fluid large teal submit button">Xác
							nhận</button>
					</div>
				</form:form>
			</div>
		</div>
</body>

</html>