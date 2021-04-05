<%@ page pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
        <%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
            <!DOCTYPE html>
            <html>
            <%--         <base href="${pageContext.servletContext.contextPath}/"> --%>
                <jsp:include page="header.jsp"></jsp:include>

                <body id="homeall">
                    <div class="wrapper">
                        <div class="clearfix"></div>
                        <div class="container_fullwidth" style="min-height: 57vh;">
                            <div class="col-md-12" style="background: white;">
                                <h3 class="title">Shopping Cart</h3>
                                <h3 style="color: red;">${message}</h3>
                                <div class="clearfix"></div>
                                <table class="shop-table">
                                    <thead>
                                        <tr>
                                            <th>Image</th>
                                            <th>Details</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                            <th>Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="bi" items="${cartitem}">
                                            <tr>
                                                <td><img src="${bi.product.image}"></td>
                                                <td>
                                                    <div class="shop-details">
                                                        <div class="productname">
                                                            <h4 style="font-weight: 400;">
                                                                <a href="details/${bi.product.id}.htm">${bi.product.name}</a>
                                                            </h4>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <h5>
                                                        <f:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" value="${bi.product.price*(100-bi.product.discount)/100}" /> ₫
                                                    </h5>
                                                </td>
                                                <td class="row" style="width:;"><button onclick="location.href='edititem/${bi.id}/-1.htm'" style="width: 30%;">-</button> <label style="/*width: 30%;*/ font-size: 20px; color: red; font-weight: bold;">${bi.amount}</label>
                                                    <button onclick="location.href='edititem/${bi.id}/1.htm'" style="width: 30%;">+</button></td>
                                                <td>
                                                    <h5>
                                                        <strong class="red"> <f:formatNumber type="currency"
												currencySymbol="" maxFractionDigits="0"
												value="${bi.product.price*(100-bi.product.discount)/100*bi.amount}" />
											₫
										</strong>
                                                    </h5>
                                                </td>
                                                <td>
                                                    <a href="removeitem/${bi.id}/cart.htm"> <img src="resources/images/remove.png" alt="">
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="6"><label class=" pull-right" style="color: green; font-weight: bold;"><h3>
										Total:
										<f:formatNumber type="currency" currencySymbol=""
											maxFractionDigits="0" value="${cart.cartvalue}" />
										₫
									</h3> </label></td>
                                        </tr>
                                        <c:if test="${cart.cartvalue>0}">
                                            <tr>
                                                <td colspan="6"><a href="checkout.htm?id=${cart.id}"><button
											class=" pull-right"
											style="background-color: green; color: white; font-weight: bold;">
											<h3>Checkout</h3>
										</button></a></td>
                                            </tr>
                                        </c:if>

                                    </tfoot>
                                </table>
                                <div class="clearfix"></div>

                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                    <jsp:include page="footer.jsp"></jsp:include>

                </body>

            </html>