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
	max-width: 550px;
}
</style>
</head>

<body id="homelogin">
	<div class="ui middle aligned center aligned grid">
		<div class="column">


			<form:form class="ui large form" action="register.htm"
				modelAttribute="user" method="post">
				<div class="ui stacked segment">
					<h2 class="ui big">
						<i class="signup icon"></i> Tạo tài khoản
					</h2>
					<div class="field">
						<label style="float: left;">Tài khoản<b
							style="color: red;">*</b></label>
						<div class="ui left input">
							<!-- <i class="user icon"></i> -->
							<form:input path="username" value="${user.username}" type="text"
								placeholder="Tài khoản" />
							<i><form:errors style="color: red;font-size: 15px;"
									path="username" /></i>
						</div>
					</div>

					<div class="field">
						<label style="float: left;">Mật khẩu<b style="color: red;">*</b></label>
						<div class="ui left input">
							<!-- 	<i class="lock icon"></i> -->
							<form:input path="password" value="${user.password}"
								type="password" placeholder="Mật khẩu" />
							<!-- 	<i class="lock icon"></i> -->

							<form:input path="password" value="${user.password}"
								type="password" placeholder="Nhập lại mật khẩu" />
							<form:errors style="color: red;font-size: 15px;" path="password" />
						</div>
					</div>

					<div class="field">
						<label style="float: left;">Họ tên <i style="color: red;">
								*</i>
						</label>
						<div class="ui left input">
							<form:input path="email" value="${user.email}" type="email"
								placeholder="Họ tên" />
							<form:errors style="color: red;font-size: 15px;" path="email" />
						</div>
					</div>

					<div class="field">
						<label style="float: left;">CMND <i style="color: red;">
								*</i>
						</label>
						<div class="ui left input">
							<form:input path="email" value="${user.email}" type="email"
								placeholder="CMND" />
							<form:errors style="color: red;font-size: 15px;" path="email" />
						</div>
					</div>

					<div class="field">
						<label style="float: left;">SĐT <i style="color: red;">
								*</i>
						</label>
						<div class="ui left input">
							<form:input path="email" value="${user.email}" type="email"
								placeholder="SĐT" />
							<form:errors style="color: red;font-size: 15px;" path="email" />
						</div>
					</div>

					<div class="field">
						<label style="float: left;">Email <i style="color: red;">
								*</i>
						</label>
						<div class="ui left input">
							<form:input path="email" value="${user.email}" type="email"
								placeholder="Email address" />
							<form:errors style="color: red;font-size: 15px;" path="email" />
						</div>
					</div>

					<div class="ui form">
						<div class="grouped fields">
							<label style="float: left;">Mục đích của bạn là gì?</label>
							<div class="field" style="float: left;">
								<div class="ui radio checkbox">
									<input type="radio" name="example2" checked="checked">
									<label>Tìm trọ</label>
								</div>
							</div>
							<div class="field" style="float: left;">
								<div class="ui radio checkbox">
									<input type="radio" name="example2"> <label>Cho
										thuê trọ</label>
								</div>
							</div>
						</div>
					</div>
					<button class="ui fluid large teal submit button">Tạo tài
						khoản</button>
				</div>
			</form:form>
			<div class="card-footer text-center" style="margin-top: 5px">
				<div class="ui message">
					<a href="login.htm">Đã có tài khoản? Đăng nhập</a>
				</div>
			</div>
		</div>
	</div>
</body>

</html>