<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="initial-scale=1, minimum-scale=1, width=device-width"
	name="viewport">
<meta name="robots" content="all,follow">
<title>Admin</title>
<link rel="icon" href="resources/images/avatar/admin-avt.png"
	sizes="32x32">
<!-- inject:css -->
<link rel="stylesheet"
	href="resources/vendors/fomantic-ui/semantic.min.css">
<link rel="stylesheet" href="resources/css/main.css">
<!-- endinject -->
<!-- datatables:css -->
<link rel="stylesheet"
	href="resources/vendors/datatables.net/datatables.net-se/css/dataTables.semanticui.min.css">
<link rel="stylesheet"
	href="resources/vendors/datatables.net/datatables.net-responsive-se/css/responsive.semanticui.min.css">
<link rel="stylesheet"
	href="resources/vendors/datatables.net/datatables.net-buttons-se/css/buttons.semanticui.min.css">
<!-- endinject -->
</head>
<body>
	<div class="row">
		<div class="ui grid">
			<!-- BEGIN NAVBAR -->
			<div class="computer only row">
				<div class="column">
					<div class="ui top fixed menu navcolor">
						<!-- <div class="item">
								<img src="resources/images/avatar/admin-avt.png" alt="SimpleIU">
							</div> -->
						<div class="left menu">
							<div class="nav item">
								<strong class="navtext">Admin Table</strong>
							</div>
						</div>
						<div
							class="ui top pointing dropdown admindropdown link item right">
							<img class="imgrad" src="resources/images/avatar/admin-avt.png"
								alt=""> <span class="clear navtext"><strong>${username}</strong></span>
							<i class="dropdown icon navtext"></i>
							<div class="menu">
								<!-- <div class="item">
										<p>
											<i class="settings icon"></i>Account Setting
										</p>
									</div> -->
								<div class="item" onclick="location.href='logout.htm'" >
									<p>
										<a href="logout.htm"><i class="sign out alternate icon"></i>Logout</a>
									</p>
								</div>
							</div>
						</div>
						<div class="ui top pointing dropdown admindropdown link item"
							style="width: 8%">
							<span class="clear navtext"><strong><i
									class="envelope outline icon"></i></strong></span> <i
								class="dropdown icon navtext"></i>
							<div class="menu">
								<c:forEach var="thongbao" items="${thongbaoadmin}">
									<!-- begin="0"	end="10"  -->
									<%-- <c:if test="${thongbao!=null}"> --%>
										<div class="item">
											<a
												href="${pageContext.servletContext.contextPath}/${thongbao.link}"><i class="info icon"></i>${thongbao.thongbao}</a>
										</div>
									<%-- </c:if> --%>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- BEGIN SIDEBAR -->
			<div class="computer only row">
				<div class="left floated three wide computer column"
					id="computersidebar" style="width: 15% !important;">
					<div class="ui vertical fluid menu scrollable" id="simplefluid" style="font-size: 20px">
						<div class="clearsidebar"></div>
						<div class="item">
							<img src="resources/images/avatar/admin-avt.png"
								id="sidebar-image">
						</div>
						<a class="item" href="admin/thongke.htm"><i
							class="chart line icon"></i>Thống kê</a> <a class="item"
							href="admin/account.htm"><i class="users icon"></i>Tài khoản</a>
						<a class="item" href="admin/chutro.htm"><i class="user icon"></i>Chủ
							trọ</a> <a class="item" href="admin/khachthue.htm"><i
							class="user outline icon"></i>Khách thuê</a> <a class="item"
							href="admin/nhatro.htm?chu=-1"><i class="home icon"></i>Bài đăng</a>
							<a class="item"
							href="admin/thongbao.htm?user="><i class="envelope icon"></i>Thông báo</a>

						<!-- <div class="ui item">
								<div class="ui fluid selection dropdown moredropdown">
									<div class="text">Menu</div>
									<i class="dropdown icon"></i>
									<div class="menu">
										<p class="item">Choice 1</p>
										<p class="item">Choice 2</p>
									</div>
								</div> 
							</div> -->

					</div>
				</div>
			</div>
			<!-- END SIDEBAR -->

		</div>
	</div>
</body>
</html>