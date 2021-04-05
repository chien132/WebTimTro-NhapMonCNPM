<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
</head>

<body>
	<ul id="featured">
		<li>
			<div class="row">
				<c:forEach var="p" items="${products}">
					<div class="col-md-3 col-sm-6">
						<div class="products">
							<div class="offer">
								-
								<f:formatNumber type="percent" value="${p.discount/100}" />
							</div>
							<div class="thumbnail">
								<a href="details/${p.id}.htm"><img src="${p.image}"
									style="max-height: 100%"></a>
							</div>
							<div class="productname"><b>${p.name}</b></div>
							<div class="price">
								<h5 style="color: black;text-decoration: line-through;">
									<f:formatNumber type="currency" currencySymbol=""
										maxFractionDigits="0" value="${p.price}" />
									đ

								</h5>
							</div>
							<h3 class="price">
								<f:formatNumber type="currency" currencySymbol=""
									maxFractionDigits="0" value="${p.price*(100-p.discount)/100}" />
								đ

							</h3>
							
							<!-- 											<div class="product-list-description "> -->
							<%-- 												<span class="old_price" style=""> ${p.price} <sup> --%>
							<!-- 														đ </sup> -->
							<!-- 												</span> -->
							<!-- 											</div> -->
							<div class="button_group">

								<c:choose>
									<c:when test="${p.quantity>0}">
										<form action="addtocart/${p.id}/1/index.htm" method="post">
											<button class="button add-cart" type="submit">Add To
												Cart</button>
										</form>
										<b style="color: green; font-size: 20px; font-weight: bold;">${p.quantity}
											in stock</b>
									</c:when>
									<c:when test="${p.quantity==0}">
										<form action="addtocart/${p.id}/1/index.htm" method="post">
											<button class="button add-cart"
												style="pointer-events: none; opacity: 0.4;" type="submit">Add
												To Cart</button>
										</form>
										<b
											style="color: red; font-size: 20px; font-weight: bold; text-decoration: line-through;">Out
											of stock</b>
									</c:when>
								</c:choose>
							</div>
						</div>
					</div>
				</c:forEach>

			</div>
		</li>
	</ul>
</body>

</html>