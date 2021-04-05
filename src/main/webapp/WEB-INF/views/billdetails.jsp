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
                                <!-- 				<h3 style="color: green; font-weight: 500;">Success !</h3> -->
                                <h3 style="color: green; padding-left: 10vh; -webkit-text-stroke-width: medium;" class="title">Your bill list:</h3>
                                <div class="clearfix"></div>
                                <table class="shop-table" style="font-size: 18px;">
                                    <thead>
                                        <tr>
                                            <th>Buy Date</th>
                                            <th>Total</th>
                                            <th>Paid</th>
                                            <th>Status</th>
                                            <th>View</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="b" items="${ubills}">
                                            <c:if test="${b.paid=='true'}">
                                                <tr style="margin-bottom: 0px;">
                                                    <td>${b.buydate}</td>
                                                    <td>
                                                        <f:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" value="${b.cartvalue}" /> ₫</td>
                                                    <td>${b.paid?"Paid":"Waiting for Payment"}</td>
                                                    <td>${b.status?"Done":"In Progress"}</td>

                                                    <td>

                                                        <button class="btn btn-primary" onclick="location.href='checkout.htm?id=${b.id}'" style="width: 20%; min-width: 55px;">Xem</button>

                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </tbody>

                                </table>

                                <div class="clearfix"></div>
                                <h3 style="color: green; padding-left: 10vh; margin-top: 10vh; -webkit-text-stroke-width: medium;" class="title">Your bill details:</h3>
                                <table class="shop-table">
                                    <thead>
                                        <tr>
                                            <th>Image</th>
                                            <th>Details</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Price</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="bi" items="${bill.billItems}">
                                            <tr style="margin-bottom: 0px;">
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
                                                    <h4>
                                                        <f:formatNumber type="currency" currencySymbol="" maxFractionDigits="0" value="${bi.product.price*(100-bi.product.discount)/100}" /> ₫
                                                    </h4>
                                                </td>
                                                <td class="row" style="width:;"><label style="width: 30%; font-size: 20px; color: red; font-weight: bold;">${bi.amount}</label>
                                                </td>
                                                <td>
                                                    <h5>
                                                        <strong class="red"> <f:formatNumber type="currency"
												currencySymbol="" maxFractionDigits="0"
												value="${bi.product.price*(100-bi.product.discount)/100*bi.amount}" />
											₫
										</strong>
                                                    </h5>
                                                </td>

                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                    <tfoot>
                                        <tr>
                                            <td colspan="6"><label class=" pull-right" style="color: green; font-weight: bold;"><h3>
										Total:
										<f:formatNumber type="currency" currencySymbol=""
											maxFractionDigits="0" value="${bill.cartvalue}" />
										₫
									</h3> </label></td>
                                        </tr>

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