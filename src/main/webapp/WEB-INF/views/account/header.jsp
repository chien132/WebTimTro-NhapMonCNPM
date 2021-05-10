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
			<a href="khachthue/index.htm" class="item">
				<i class="home icon"></i>
			</a>
			<div class="right menu">
				<div class="ui scrolling dropdown  icon item">
						<i class="mail icon"></i>
					<div class="menu">
					<c:forEach var="thongbao" items="${thongbaos}" begin="0" end="10">
						<c:if test="${thongbao!=null}">
							<div class="item"><a href="${pageContext.servletContext.contextPath}/${thongbao.link}">${thongbao.thongbao}</a></div>
						</c:if>
					</c:forEach>
					</div>
				</div>
				<div class="ui dropdown icon item">
					<img src="resources/images/avatar/${sessionScope['username']}.png" style="border-radius: 20%">
					<div class="menu">
						<div class="item">
						<a href="${pageContext.servletContext.contextPath}/account/${sessionScope['username']}.htm">
							<i class="user icon">Thông tin tài khoản</i></a></div>
						<div class="item">
						<a href="${pageContext.servletContext.contextPath}/khachthue/thongtinthem.htm">
							<i class="user icon">Thông tin thêm</i></a></div>
						<div class="item">
						<a href="${pageContext.servletContext.contextPath}/khachthue/lichhen.htm">
							<i class="calendar icon">Lịch hẹn</i></a></div>
						<div class="item">
						<a href="${pageContext.servletContext.contextPath}/khachthue/logout.htm">
							<i class="logout icon">Đăng xuất</i></a></div>
					</div>
				</div>
			</div>
		</div>
	<h1 class="ui inverted header">Padding</h1>
	<h1 style="text-align: center; color: white; -webkit-text-stroke-width: 1px;
  -webkit-text-stroke-color: black;">KÊNH THÔNG TIN PHÒNG TRỌ SỐ MỘT VIỆT NAM</h1>

<script>
</script>
</body>
</html>