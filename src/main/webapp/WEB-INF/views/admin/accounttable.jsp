<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
	<div class="ui grid">
		<jsp:include page="header.jsp"></jsp:include>
		<!-- BEGIN CONTEN -->
		<div
			class="right floated thirteen wide computer sixteen wide phone column"
			id="content">
			<div class="ui container grid" style="width: 100%;">
				<div class="row">
					<div
						class="fifteen wide computer sixteen wide phone centered column">
						<h2>
							<i class="table icon"></i> User Table
						</h2>
						<div class="ui divider"></div>
						<div class="ui grid">
							<div
								class="sixteen wide computer sixteen wide phone centered column">
								<c:if test="${message!=null}">
									<div class="ui positive message">
										<i class="close icon"></i>
										<div class="header">Message</div>
										<p>${message}</p>
									</div>
								</c:if>
								<h4></h4>
								<!-- BEGIN DATATABLE -->
								<div class="ui stacked segment rig">
									<div
										class="ui green icon label right floated compact segment stepper"
										style="margin-right: 0; font-size: 12px;">
										<label><a href="admin/addaccount.htm"
											style="font-size: 16px; color: white;"><i
												class="plus square outline icon"></i> Thêm</a></label>
									</div>
									<br> <br>
									<table id="mytable"
										class="ui celled table responsive nowrap unstackable"
										style="width: 100%">
										<thead style="text-align: center;">
											<tr>
												<th>Avatar</th>
												<th>Username</th>
												<th>Họ tên</th>
												<th>CMND</th>
												<th>Điện thoại</th>
												<th>Email</th>
												<th>Role</th>
												<th>Ngày đăng ký</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach var="i" items="${accounts}">
												<tr>
													<td><img style="max-width: 45px;" alt=""
														src="resources/images/avatar/${i.username}.png"></td>
													<td>${i.username}</td>
													<td>${i.hoTen}</td>
													<td>${i.cmnd}</td>
													<td>${i.dienThoai}</td>
													<td>${i.email}</td>
													<td>${i.role.name}<c:if test="${i.role.id==1}"> #${i.chuTro.id}</c:if>
														<c:if test="${i.role.id==2}"> #${i.khachThue.id}</c:if></td>
													<td>${i.ngayDangKy}.</td>
													<td style="text-align: center;"><a
														href="admin/editaccount/${i.username}.htm"><button
																style="color: green; font-size: 16px">Sửa</button></a> <!-- <button>Xóa</button> -->
													</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
								</div>
								<!-- END DATATABLE -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END CONTENT -->
	</div>
</body>
<!-- inject:js -->
<jsp:include page="footer.jsp"></jsp:include>
</html>