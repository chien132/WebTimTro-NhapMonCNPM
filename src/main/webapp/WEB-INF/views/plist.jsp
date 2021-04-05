<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<base href="${pageContext.servletContext.contextPath }/"><!-- 
<link rel="stylesheet" type="text/css" href="resources/csss/style.css"> -->
</head>

<body>
	<ul class="products-listItem">
		<c:forEach var="p" items="${products}">
			<li class="products">
				<div class="thumbnail">
					<img src="${p.image}" alt="Product Name">
				</div>
				<div class="product-list-description">
					<div class="productname"><a href="details/${p.id}.htm">${p.name}</a></div>

					<p>${p.des}</p>
					<div class="list_bottom">
						<div class="price">
							<span class="new_price"><f:formatNumber type="currency"
									currencySymbol="" maxFractionDigits="0">
                                                                 ${p.price*(100-p.discount)/100}</f:formatNumber>
								<sup> đ </sup> </span> <span class="old_price"><f:formatNumber
									type="currency" currencySymbol="" maxFractionDigits="0"
									value="${p.price}" /> <sup> đ </sup> </span>
						</div>
					</div>
				</div>
				<div class="product-list-description">
					<div class="list_bottom">
						<c:choose>
							<c:when test="${p.quantity>0}">
								<b style="color: green; font-size: 20px; font-weight: bold;">${p.quantity}
									in stock</b>
								<form action="addtocart/${p.id}/1/index.htm" method="post">
									<button style="margin-top: 15px;" class="button add-cart"
										type="submit">Add To Cart</button>
								</form>

							</c:when>
							<c:when test="${p.quantity==0}">
								<b
									style="color: red; font-size: 20px; font-weight: bold; text-decoration: line-through;">Out
									of stock</b>
								<form action="addtocart/${p.id}/1/index.htm" method="post">
									<button class="button add-cart"
										style="margin-top: 15px; pointer-events: none; opacity: 0.4;"
										type="submit">Add To Cart</button>
								</form>

							</c:when>
						</c:choose>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
</body>

</html>