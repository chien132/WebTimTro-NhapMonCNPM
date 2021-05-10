<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>

<body
	style='background: url(resources/images/background/background.png); background-size: cover;'>
	<!-- Page Contents -->

	<div class="ui grid"
		style="padding-left:20%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="twelve wide column"
			style="background-color: white; border-radius: 5px">
			<div class="row" style="margin-top: 10px">
				<jsp:include page="diachi.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<div class="four wide column" style="padding-left:2%; border-radius: 2%; background-color: white">
		<div class="ui segment column" style="position: fixed; max-width: 15%">
			<div class="ui small image">
				<img src="resources/images/avatar/${nhatro.chuTro.account.username}.png" style="border-radius:  10%">
			</div>
			<div class="content">
				<div class="header">Người đăng: ${nhatro.chuTro.account.username}</div>
				<div class="meta">Anh/chị: ${nhatro.chuTro.account.hoTen}</div>
				<div class="meta">Điện thoại liên lạc: ${nhatro.chuTro.account.dienThoai}</div>
				<div class="meta">Email: ${nhatro.chuTro.account.dienThoai}</div>
			</div>
			<hr>
			<form:form modelAttribute="lichhen" action="khachthue/datlich/${nhatro.id}.htm">
			<h3>Đăng kí lịch xem phòng</h3>
			<c:if test="${datlich!=null}"> <span style="color: red">${datlich}</span> </c:if>
			<div class="fluid ui input">
				<form:input type="date" path="thoigian" value="${lichhen.thoigian}" placeholder="${lichhen.thoigian}" />
			</div>
			<br>
			<button class="fluid ui primary left labeled icon button" >
				<i class="calendar icon"></i> Đặt lịch
			</button>
			</form:form>
		</div>
	</div>
		<div class="ui grid"
		style="padding-left:20%; max-width: 150%; margin-top: 17px; border-radius: 5px">
		<div class="twelve wide column"
			style="background-color: white; border-radius: 5px">
			<div class="ui divided items">
				<c:if test="${message!=null}">
					<div class="alert alert-danger" role="alert" id="alert">
						<h3>${message}</h3>
					</div>
				</c:if>
				<div class="item">
					<div class="ui large image">
						<div class="ui slide masked reveal image">
							<img src="resources/images/nhatro/${nhatro.id}_1.png" class="visible content">
							<img src="resources/images/nhatro/${nhatro.id}_2.png" class="hidden content">
						</div>
					</div>
					<div class="content">
						<a class="header" href="khachthue/nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
						<div class="meta">
							<span>Địa chỉ:
								${nhatro.diachi.diaChi}/${nhatro.diachi.ward.name}/${nhatro.diachi.ward.district.name}/${nhatro.diachi.ward.district.province.name}</span>
						</div>
						<div class="meta">
							<span>Diện tích: ${nhatro.dienTich} m2</span>
						</div>
						<div class="meta">
							<span>${nhatro.soNguoiTrenPhong} người/phòng</span>
						</div>
						<div class="meta">
							<span>Có tất cả: ${nhatro.soPhongChoThue} phòng</span>
						</div>
						<div class="meta">
							<span>Giá thuê: ${nhatro.tienThue}vnd</span>
						</div>
						<div class="meta">
							<span>Điểm đánh giá: ${nhatro.diem}⭐</span>
						</div>
						<div class="meta">
							<span>Ngày thêm: ${nhatro.ngayThem}</span>
						</div>
						<div class="extra">
							<span><i class="user icon"></i>${nhatro.soLuot} đã
								thuê</span>
						</div>
					</div>
					<br>
				</div>
				<br>
				<div>
					${nhatro.moTa}
				</div>
			</div>
		</div>
	</div>
	<br>
	
	<script type="text/javascript">
		$('.special.cards .image').dimmer({
			on : 'hover'
		});
		
	</script>
</body>

</html>