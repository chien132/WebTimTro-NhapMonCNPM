<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>

<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>

<body id="homeall">
	<div class="wrapper">
		<div class="clearfix"></div>
		<div class="container_fullwidth">
			<div class="container">
				<div class="row">
					<div class="col-md-9">
						<div class="products-details">
							<div class="preview_image">
								<div class="offer">
									-
									<f:formatNumber type="percent" value="${p.discount/100}" />
								</div>
								<div class="preview-small">
									<img style="max-height: 500px;" src="${p.image}">
								</div>
							</div>
							<div class="products-description">
								<h2 class="name">${p.name}</h2>
								<c:choose>
									<c:when test="${p.quantity>0}">
										<h4 style="color: green; margin-top: 5vh;">Availability:
											${p.quantity}</h4>
									</c:when>
									<c:when test="${p.quantity==0}">
										<h4
											style="color: red; margin-top: 5vh; text-decoration: line-through;">Out
											of stock</h4>
									</c:when>
								</c:choose>

								<hr class="border">
								<div class="price">
									Price : <span class="new_price"> <f:formatNumber
											type="currency" currencySymbol="" maxFractionDigits="0"
											value="${p.price*(100-p.discount)/100}" /> <sup> đ </sup>
									</span> <span class="old_price"><f:formatNumber type="currency"
											currencySymbol="" maxFractionDigits="0" value="${p.price}" />
										<sup> đ </sup></span>
								</div>
								<hr class="border">
								<div class="wided">

									<div class="button_group">
										<form action="adddetail/${p.id}.htm" method="post">
											<div class="qty">
												Quantity <input style="max-width: 90px;" type="number"
													value="1" min="1" max="${p.quantity}" name="qty" id="qty" />
											</div>
											<button class="button" type="submit">Add To Cart</button>
										</form>

									</div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
						<div class="clearfix"></div>
						<div class="tab-box">
							<div class="tab-content-wrap">
								<div class="tab-content" id="Descraption">

									<h5
										style="font-family: ui-serif; font-size: 20px; line-height: 3vh;">${p.des}</h5>
								</div>
							</div>

						</div>
					</div>
					<div class="tab-box">
						<div class="tab-content-wrap">
							<h2 style="color: green;">Comment:</h2>
							<c:forEach var="c" items="${p.comments}">
								<div class="tab-content" id="Descraption">
									<div class="row">
										<img alt="" style="max-height: 50px" src="${c.user.avatar}">
										<h5 style="color: blue; font-weight: bold;">${c.user.fullname}</h5>

									</div>
									<c:if test="${c.upvote}">
										<h5
											style="color: green; margin-top: 2vh; font-size: 20px; font-weight: bolder;">${c.comment}</h5>
									</c:if>
									<c:if test="${!c.upvote}">
										<h5
											style="color: red; margin-top: 2vh; font-size: 20px; font-weight: bolder;">${c.comment}</h5>
									</c:if>
								</div>
							</c:forEach>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>