<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>

<body
	style="background: url(resources/images/homeloan.jpg) center top no-repeat; background-size: cover; -webkit-background-size: cover; background-attachment: fixed;">
	<!-- Page Contents -->
	<div class="pusher" style="background: #dcfffc6b; margin: 0px">
		<div class="ui inverted vertical masthead center aligned segment"
			style="background: #ffffff9c; color: black;">
			<div class="ui text container">
				<h1 class="ui inverted header">Padding</h1>
				<h2>KÊNH THÔNG TIN PHÒNG TRỌ SỐ MỘT VIỆT NAM</h2>
			</div>
		</div>
		<div class="ui grid ">
			<div class="nine wide column" style="margin-left: 20%">
				<div class="row"><jsp:include page="demo/index.jsp"></jsp:include></div>
				<div class="ui text container big breadcrumb">
					<a class="section">TP. Hồ Chí Minh</a> <i
						class="right chevron icon divider"></i> <a class="section">Quận
						9</a> <i class="right chevron icon divider"></i>
					<div class="active section">Phường Hiệp Phú</div>
				</div>
			</div>
		</div>
		<div class="ui grid" style="max-width: 80%">
			<div class="four wide column" style="padding-left: 10%;">
				<div class="ui segment" style="position: fixed; min-width: 10%">
					<h4>Đánh giá sao</h4>
					<div class="ui checkbox">
						<input type="checkbox" name="example"> <label>5
							sao</label><br> <input type="checkbox" name="example"> <label>4
							sao</label><br> <input type="checkbox" name="example"> <label>3
							sao</label> <br> <input type="checkbox" name="example"> <label>2
							sao</label> <br> <input type="checkbox" name="example"> <label>1
							sao</label>
					</div>
					<h4>Số lượt đánh giá</h4>
					<div class="ui checkbox">
						<input type="checkbox" name="example"> <label>10+</label>
						<br> <input type="checkbox" name="example"> <label>20+</label>
						<br> <input type="checkbox" name="example"> <label>50+</label>
						<br> <input type="checkbox" name="example"> <label>100+</label>
					</div>
				</div>
			</div>
			<div class="twelve wide column">
				<div class="">
					<h2>Sắp xếp theo</h2>
					<a href=""><div class="ui button">Giá tăng dần</div></a> <a href=""><div
							class="ui button">Giá giảm dần</div></a> <a href=""><div
							class="ui button">Đánh giá cao</div></a> <a href=""><div
							class="ui button">Gần bạn nhất</div></a>
				</div>
				<div class="ui placeholder segment">
					<div class="ui icon header">
						<i class="pdf file outline icon"></i> Khung hiển thị nhà trọ
					</div>
				</div>
				<div class="ui placeholder segment">
					<div class="ui icon header">
						<i class="pdf file outline icon"></i> Khung hiển thị nhà trọ
					</div>
				</div>
				<div class="ui placeholder segment">
					<div class="ui icon header">
						<i class="pdf file outline icon"></i> Khung hiển thị nhà trọ
					</div>
				</div>
				<div class="ui placeholder segment">
					<div class="ui icon header">
						<i class="pdf file outline icon"></i> Khung hiển thị nhà trọ
					</div>
				</div>
				<div class="ui placeholder segment">
					<div class="ui icon header">
						<i class="pdf file outline icon"></i> Khung hiển thị nhà trọ
					</div>
				</div>
			</div>
		</div>


	</div>


	<%-- <div class="wrapper">

		<div class="clearfix"></div>
		<div class="container_fullwidth" style="min-height: 57vh;">
			<div class="container">
				<div class="clearfix"></div>
				<div class="featured-products">
					<div class="row">
						<h3 class="title">
							<strong>Featured </strong> Products
						</h3>
						<c:choose>
							<c:when test="${search!=null}">
								<div class="toolbar">

									<div class="view-mode">
										<c:if test="${view=='list'}">
											<a class="list active"> List </a>
											<a href="index.htm?page=${page}&view=grid&search=${search}"
												class="grid"> Grid </a>
										</c:if>
										<c:if test="${view=='grid'}">
											<a href="index.htm?page=${page}&view=list&search=${search}"
												class="list"> List </a>
											<a class="grid active"> Grid </a>
										</c:if>
									</div>

									<div class="pager" style="display: flex;">
										<c:if test="${page==1}">
											<button style="pointer-events: none; opacity: 0.4;">Prev</button>
										</c:if>
										<c:if test="${page>1}">
											<a href="index.htm?page=${page-1}&view=${view}&search=${search}">
												<button>Prev</button>
											</a>
										</c:if>
										<h3 style="margin: 1vh;">${page}</h3>
										<c:if test="${page==maxpage}">
											<button style="pointer-events: none; opacity: 0.4;">Next</button>
										</c:if>
										<c:if test="${page<maxpage}">
											<a href="index.htm?page=${page+1}&view=${view}&search=${search}">
												<button>Next</button>
											</a>
										</c:if>
										<!-- 								<a href="# "> Prev </a> <a href="# " class="active "> 1 </a> <a -->
										<!-- 									href="# "> 2 </a> <a href="# "> 3 </a> <a href="# "> Next</a> -->
									</div>

								</div>
							</c:when>

							<c:when test="${brand!=null}">
								<div class="toolbar">

									<div class="view-mode">
										<c:if test="${view=='list'}">
											<a class="list active"> List </a>
											<a href="index.htm?page=${page}&view=grid&brand=${brand}"
												class="grid"> Grid </a>
										</c:if>
										<c:if test="${view=='grid'}">
											<a href="index.htm?page=${page}&view=list&brand=${brand}"
												class="list"> List </a>
											<a class="grid active"> Grid </a>
										</c:if>
									</div>

									<div class="pager" style="display: flex;">
										<c:if test="${page==1}">
											<button style="pointer-events: none; opacity: 0.4;">Prev</button>
										</c:if>
										<c:if test="${page>1}">
											<a href="index.htm?page=${page-1}&view=${view}&brand=${brand}">
												<button>Prev</button>
											</a>
										</c:if>
										<h3 style="margin: 1vh;">${page}</h3>
										<c:if test="${page==maxpage}">
											<button style="pointer-events: none; opacity: 0.4;">Next</button>
										</c:if>
										<c:if test="${page<maxpage}">
											<a href="index.htm?page=${page+1}&view=${view}&brand=${brand}">
												<button>Next</button>
											</a>
										</c:if>
										<!-- 								<a href="# "> Prev </a> <a href="# " class="active "> 1 </a> <a -->
										<!-- 									href="# "> 2 </a> <a href="# "> 3 </a> <a href="# "> Next</a> -->
									</div>

								</div>
							</c:when>

							<c:when test="${cate!=null}">
								<div class="toolbar">

									<div class="view-mode">
										<c:if test="${view=='list'}">
											<a class="list active"> List </a>
											<a href="index.htm?page=${page}&view=grid&cate=${cate}"
												class="grid"> Grid </a>
										</c:if>
										<c:if test="${view=='grid'}">
											<a href="index.htm?page=${page}&view=list&cate=${cate}"
												class="list"> List </a>
											<a class="grid active"> Grid </a>
										</c:if>
									</div>

									<div class="pager" style="display: flex;">
										<c:if test="${page==1}">
											<button style="pointer-events: none; opacity: 0.4;">Prev</button>
										</c:if>
										<c:if test="${page>1}">
											<a href="index.htm?page=${page-1}&view=${view}&cate=${cate}">
												<button>Prev</button>
											</a>
										</c:if>
										<h3 style="margin: 1vh;">${page}</h3>
										<c:if test="${page==maxpage}">
											<button style="pointer-events: none; opacity: 0.4;">Next</button>
										</c:if>
										<c:if test="${page<maxpage}">
											<a href="index.htm?page=${page+1}&view=${view}&cate=${cate}">
												<button>Next</button>
											</a>
										</c:if>
										<!-- 								<a href="# "> Prev </a> <a href="# " class="active "> 1 </a> <a -->
										<!-- 									href="# "> 2 </a> <a href="# "> 3 </a> <a href="# "> Next</a> -->
									</div>

								</div>
							</c:when>

							<c:otherwise>
								<div class="toolbar">

									<div class="view-mode">
										<c:if test="${view=='list'}">
											<a class="list active"> List </a>
											<a href="index.htm?page=${page}&view=grid" class="grid">
												Grid </a>
										</c:if>
										<c:if test="${view=='grid'}">
											<a href="index.htm?page=${page}&view=list" class="list">
												List </a>
											<a class="grid active"> Grid </a>
										</c:if>
									</div>

									<div class="pager" style="display: flex;">
										<c:if test="${page==1}">
											<button style="pointer-events: none; opacity: 0.4;">Prev</button>
										</c:if>
										<c:if test="${page>1}">
											<a href="index.htm?page=${page-1}&view=${view}">
												<button>Prev</button>
											</a>
										</c:if>
										<h3 style="margin: 1vh;">${page}</h3>
										<c:if test="${page==maxpage}">
											<button style="pointer-events: none; opacity: 0.4;">Next</button>
										</c:if>
										<c:if test="${page<maxpage}">
											<a href="index.htm?page=${page+1}&view=${view}">
												<button>Next</button>
											</a>
										</c:if>
										<!-- 								<a href="# "> Prev </a> <a href="# " class="active "> 1 </a> <a -->
										<!-- 									href="# "> 2 </a> <a href="# "> 3 </a> <a href="# "> Next</a> -->
									</div>

								</div>
							</c:otherwise>
						</c:choose>

					</div>
					<!-- 					<ul id="featured "> -->
					<!-- 						<li> -->
					<!-- 							<div class="row "> -->
													<c:forEach var="p " items="${products} ">
					<!-- 									<div class="col-md-3 col-sm-6 "> -->
					<!-- 										<div class="products "> -->
					<!-- 											<div class="offer "> -->
					<!-- 												- -->
																	<f:formatNumber type="percent " value="${p.discount/100} " />
					<!-- 											</div> -->
					<!-- 											<div class="thumbnail "> -->
																	<a href="details/${p.id}.htm "><img src="${p.image} "
					<!-- 													style="max-height: 100% "></a> -->
					<!-- 											</div> -->
																<div class="productname ">${p.name}</div>
					<!-- 											<h4 class="price "> -->
																	<f:formatNumber type="currency " currencySymbol=" "
																		maxFractionDigits="0 "
																		value="${p.price*(100-p.discount)/100} " />
					<!-- 												đ -->

					<!-- 											</h4> -->
					<!-- 																						<div class="product-list-description "> -->
																												<span class="old_price " style=" "> ${p.price} <sup>
					<!-- 																									đ </sup> -->
					<!-- 																							</span> -->
					<!-- 																						</div> -->
					<!-- 											<div class="button_group "> -->

																	<c:choose>
																		<c:when test="${p.quantity>0}">
																			<form action="addtocart/${p.id}/1/index.htm" method="post">
					<!-- 															<button class="button add-cart" type="submit">Add -->
					<!-- 																To Cart</button> -->
																			</form>
					<!-- 														<b -->
																				style="color: green; font-size: 20px; font-weight: bold;">${p.quantity}
					<!-- 															in stock</b> -->
																		</c:when>
																		<c:when test="${p.quantity==0}">
																			<form action="addtocart/${p.id}/1/index.htm" method="post">
					<!-- 															<button class="button add-cart" -->
					<!-- 																style="pointer-events: none; opacity: 0.4;" -->
					<!-- 																type="submit">Add To Cart</button> -->
																			</form>
					<!-- 														<b -->
					<!-- 															style="color: red; font-size: 20px; font-weight: bold; text-decoration: line-through;">Out -->
					<!-- 															of stock</b> -->
																		</c:when>
																	</c:choose>
					<!-- 											</div> -->
					<!-- 										</div> -->
					<!-- 									</div> -->
													</c:forEach>

					<!-- 							</div> -->
					<!-- 						</li> -->
					<!-- 					</ul> -->
					<c:if test="${view=='list'}">
						<jsp:include page="plist.jsp"></jsp:include>
					</c:if>
					<c:if test="${view=='grid'}">
						<jsp:include page="pgrid.jsp"></jsp:include>
					</c:if>
				</div>
				<div class="clearfix"></div>

			</div>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div> --%>
</body>

</html>