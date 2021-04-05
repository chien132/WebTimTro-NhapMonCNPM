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
<script src="resources/js/jquery-3.6.0.js"></script>
<link rel="stylesheet" type="text/css"
	href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js"></script>
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
</style>
</head>

<body>
	<!-- Following Menu -->
	<div class="ui large top fixed hidden menu">
		<div class="ui container">
			<a href="" class="active item">Trang chủ</a> <a class="item">TP. Hồ Chí
				Minh</a> <a class="item">TP. Hà Nội</a>
			<div class="right menu">
				<div class="item">
					<a href="login.htm" class="ui button">Đăng nhập</a>
				</div>
				<div class="item">
					<a href="register.htm" class="ui primary button">Đăng ký</a>
				</div>
			</div>
		</div>
	</div>
	
	<%-- 
	<div class="header">
		<div class="container">
			<div class="row">
				<div class="col-md-2 col-sm-2" style="padding: 0;">
					<div class="logo">
						<a href="index.htm"> <img src="resources/images/logo.png"
							alt="XGear"></a>
					</div>
				</div>
				<div class="col-md-10 col-sm-10">
					<div class="header_top">
						<div class="row">

							<div class="col-md-12">
								<ul class="option">
									<li id="search" class="search">
										<form action="index.htm">
											<input class="search-submit" type="submit" value=""><input
												class="search-input" placeholder="Enter your search term..."
												type="text" value="" name="search"> <input
												type="hidden" value="grid" name="view" />
											<input type="hidden" value="1" name="page" />
										</form>
									</li>
									<li class="option-cart"><a href="cart.htm"
										class="cart-icon">cart <span class="cart_no"><c:if
													test="${cart!=null}">${cart.cartqty}</c:if></span>
									</a>
										<ul class="option-cart-item">
											<c:if test="${cartitem!=null}">
												<c:forEach var="bi" items="${cartitem}">
													<li>
														<div class="cart-item">
															<div class="image">
																<img src="${bi.product.image}">
															</div>
															<div class="item-description">
																<p class="name">${bi.product.name}</p>
																<p>
																	Quantity: <span class="light-red">${bi.amount}</span>
																</p>
															</div>
															<div>
																<p class="price">
																	<f:formatNumber type="currency" currencySymbol=""
																		maxFractionDigits="0"
																		value="${bi.product.price*bi.amount*(100-bi.product.discount)/100}" />
																	đ
																</p>
																<a
																	href="removeitem/${bi.id}/${indetail==null?'index':'cart'}.htm"
																	class="remove"><img
																	src="resources/images/remove.png" alt="remove"></a>
															</div>
														</div>
													</li>
												</c:forEach>
											</c:if>
											<li><span class="total">Total <strong><f:formatNumber
															type="currency" currencySymbol="" maxFractionDigits="0"
															value="${cart.cartvalue}" /> </strong></span>đ
												<button class="checkout" onclick="location.href='cart.htm'">CheckOut</button></li>
										</ul></li>
								</ul>
								<ul class="usermenu row " style="margin-right: 40px;">
									<c:choose>
										<c:when test="${user==null}">

											<li><a href="register.htm" class="reg"> Register </a></li>
											<li><a href="login.htm" class="log"> Login </a></li>

										</c:when>
										<c:when test="${user!=null}">

											<li><a href="profile.htm" class="log"
												style="color: cyan;">Logged in as ${user.username}</a></li>
											<li><a href="logout.htm"> Logout </a></li>

										</c:when>
									</c:choose>
								</ul>
							</div>
						</div>
					</div>

					<div class="header_bottom">

						<div class="col-md-12">
							<div class="dropdown">
								<button class="dropbtn mybtn">Category</button>
								<div class="dropdown-content">
									<c:forEach var="c" items="${categories}">
										<a href="index.htm?cate=${c.id}&page=1&view=grid">${c.name}</a>
									</c:forEach>
								</div>
							</div>
							<div class="dropdown">
								<button class="dropbtn mybtn">Brand</button>
								<div class="dropdown-content">
									<c:forEach var="b" items="${brands}">
										<a href="index.htm?brand=${b.id}&page=1&view=grid">${b.name}</a>
									</c:forEach>
								</div>
							</div>
							<c:if test="${user.admin}">
								<div class="dropdown">
									<a href="admin/index.htm"><button class="dropbtn mybtn"
											style="background-color: green;">Admin</button></a>
								</div>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--             <div class="clearfix"></div> -->
	<div class="hom-slider" style="max-height: 32vh;">
		<div class="container">
			<div id="sequence"></div>
		</div>

	</div> --%>
</body>
</html>