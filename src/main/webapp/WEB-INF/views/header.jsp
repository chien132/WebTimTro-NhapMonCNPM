<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Tìm nhà trọ</title>
<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js" type="text/javascript"></script>
<script src="resources/ckfinder/ckfinder.js" type="text/javascript"></script>
<script type="text/javascript" src= "resources/ckeditor/ckeditor.js"></script>
<style type="text/css">
.dropbtn {
	background-color: #ffffff00;
	color: rgb(255, 255, 255);
	padding: 1vh 5vw;
	font-size: 18px;
	border: 2px solid #00000000;
	border-radius: 10px;
	transition: 0.2s;
	text-transform: uppercase;
}

.dropdown {
	position: relative;
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: rgba(0, 0, 0, 0.548);
	min-width: 13vw;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
	border-radius: 10px;
}

.dropdown-content a {
	color: rgb(255, 255, 255);
	padding: 7px 20px;
	text-decoration: none;
	display: block;
	font-size: 2vh;
	transition: 0.2s;
}

.dropdown-content a:hover {
	background-color: #ff0000;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropdown:hover .dropbtn {
	border: 2px solid #ff0000;
	color: #ff0000;
	font-weight: bold;
}
.thongbao{
	height: 20px;
	background: url("resources/images/icon/mail.png") ;
}
.form-popup {
  display: none;
  position: fixed;
  bottom: 0;
  right: 15px;
  border: 3px solid #f1f1f1;
  z-index: 9;
}
</style>
</head>

<body>
	<!-- Following Menu -->
	<div class="ui large top fixed hidden menu">
			<a href="index.htm" class="item">
				<i class="home icon"></i>
			</a>
			<div class="right menu">
					<a href="register.htm" class="item">
						<i class="user plus icon"></i>
						Đăng kí
					</a>
					<a href="login.htm" class=" item">
						<i class="sign in alternate icon"></i>
						Đăng nhập
					</a>
				</div>
			</div>
	<h3 class="ui inverted header">Padding</h3>
	<h1 style="text-align: center; color: white; -webkit-text-stroke-width: 1px;
  -webkit-text-stroke-color: black;">KÊNH THÔNG TIN PHÒNG TRỌ SỐ MỘT VIỆT NAM</h1>

<script>
</script>
</body>
</html>