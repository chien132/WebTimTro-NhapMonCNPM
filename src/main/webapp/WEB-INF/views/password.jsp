<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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
	background: url(resources/images/razerlogin.jpg) center top no-repeat;
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
	max-width: 450px;
}
</style>
<!-- <script>
  $(document)
    .ready(function() {
      $('.ui.form')
        .form({
          fields: {
            email: {
              identifier  : 'email',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter your e-mail'
                },
                {
                  type   : 'email',
                  prompt : 'Please enter a valid e-mail'
                }
              ]
            },
            password: {
              identifier  : 'password',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Please enter your password'
                },
                {
                  type   : 'length[6]',
                  prompt : 'Your password must be at least 6 characters'
                }
              ]
            }
          }
        })
      ;
    })
  ;
  </script> -->
</head>

<body id="homelogin">
	<div class="ui middle aligned center aligned grid">
		<div class="column">
			<form:form class="ui large form" action="password.htm" modelAttribute="account" method="post">
				<div class="ui stacked segment">
					<h2 class="ui big">
						<i class="signup icon"></i> Qu??n m???t kh???u
					</h2>
					<div class="field">
						<div class="ui left icon input">
							<i class="user icon"></i>
							<form:input path="username" placeholder="T??i kho???n" />
						</div>
					</div>
					<i style="color: red; float: left;"><form:errors
							path="username"></form:errors></i>
					<div class="field">
						<div class="ui left icon input">
							<i class="mail icon"></i>
							<form:input path="email"
								type="email" placeholder="Email" />

						</div>
					</div>
					<i style="color: red; float: left;"><form:errors
							path="email"></form:errors></i>
					<button class="ui fluid large teal submit button">L???y l???i password</button>
				</div>
				<div class="ui error message"></div>
			</form:form>
			<div class="card-footer text-center" style="margin-top: 5px">

				<div class="ui message">
					???? c?? t??i kho???n? <a href="login.htm"> ????ng nh???p</a>
					<hr>
					Ch??a c?? t??i kho???n? <a href="register.htm"> ????ng k??!</a>
				</div>

			</div>
		</div>
	</div>
</body>

</html>