<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>

<body
	style='background: url("resources/images/background/background.png"); background-size: cover;'>
	<!-- Page Contents -->
	<div class="ui grid"
		style="padding-left:20%; margin-top: 17px; border-radius: 5px">
		<div class="twelve wide column" style="background-color: white; border-radius: 5px">
			<br>
			<div class="ui divided items">
				<c:if test="${message!=null}"> <h3>${message}</h3> </c:if>
				<table class="ui celled table">
					<thead><tr>
						<th>Thời gian</th>
						<th>Nhà trọ</th>
						<th>Đồng ý</th>
						<th>Thành công</th>
						<th>Hủy lịch hẹn</th>
					</tr></thead>
					<tbody>
						<c:forEach var="lichhen" items="${lichhens}">
							<tr>
								<td>${lichhen.thoigian}</td>
								<td><div class="content">
										<a class="header" href="khachthue/nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
										<div class="meta">
											<span>Địa chỉ: ${nhatro.diachi.diaChi}/${nhatro.diachi.ward.name}/${nhatro.diachi.ward.district.name}/${nhatro.diachi.ward.district.province.name}</span>
									</div>
								</div></td>
								<td><c:choose>
									<c:when test="${lichhen.dongy}">
										<div class="ui icon">
											<i class="green check circle icon"></i>
										</div>
									</c:when>
									<c:otherwise>
										<div class="ui icon">
											<i class="red ban icon"></i>
										</div>
									</c:otherwise>
								</c:choose></td>
								<td><c:choose>
									<c:when test="${lichhen.thanhcong}">
										<div class="ui icon">
											<i class="green check circle icon"></i>
										</div>
									</c:when>
									<c:otherwise>
										<div class="ui icon">
											<i class="red ban icon"></i>
										</div>
									</c:otherwise>
								</c:choose></td>
								<td><div class="ui animated button" tabindex="0">
									<div class="visible content">Hủy</div>
									<div class="hidden content">
									   	<i class="close icon"></i>
									   	<a href="khachthue/lichhen/huy/${lichhen.id}.htm"></a>
									</div>
								</div></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<div></div>
	<script type="text/javascript">
		$('.special.cards .image').dimmer({
			on : 'hover'
		});
	</script>
</body>
</html>