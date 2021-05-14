<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="ptithcm.entity.Ward"%>
<%@page import="ptithcm.entity.District"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ptithcm.entity.Province"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.servletContext.contextPath}/">
<script src="resources/js/jquery-3.6.0.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="resources/semantic/semantic.min.css">
<script src="resources/semantic/semantic.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css"
	href="resources/semantic/semantic.css">
<script src="resources/semantic/semantic.js" type="text/javascript"></script>
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
							<a href="admin/account.htm"> <i class="table icon"></i> Quản
								lý bài đăng
							</a>
						</h2>
						<div class="ui divider"></div>
						<div class="ui grid">
							<div class="sixteen wide computer sixteen wide phone centered column">
								<c:if test="${message!=null}">
									<div class="ui positive message">
										<i class="close icon"></i>
										<div class="header">Message</div>
										<p>${message}</p>
									</div>
								</c:if>

								<form:form class="ui large form"
									action="admin/${action}nhatro.htm?chu=${chu}"
									modelAttribute="nhatro" enctype="multipart/form-data"
									method="post">
									<div class="ui stacked segment">
										<h2 class="ui big">
											<i class="signup icon"></i>
											<c:if test="${action=='add'}">Thêm bài đăng</c:if>
											<c:if test="${action=='edit'}">Cập nhật bài đăng</c:if>
										</h2>

										<%-- <div class="field">
											<label style="float: left;">Ảnh (2 ảnh)</label>
											<div class="ui left input">
												<!-- 	<i class="lock icon"></i> -->
												<input name="photo" type="file" class="form-control" />
											</div>
										</div>
										<div class="field">
											<label style="float: left;">Tài khoản<b
												style="color: red;">*</b></label>
											<div class="ui left input">

												<c:if test="${action=='edit'}">
													<form:input path="username" value="${user.username}"
														type="text" placeholder="Tài khoản" readonly="true" />
												</c:if>
												<c:if test="${action=='add'}">
													<form:input path="username" value="${user.username}"
														type="text" placeholder="Tài khoản" />
												</c:if>
												<i><form:errors style="color: red;font-size: 15px;"
														path="username" /></i>
											</div>
										</div>

										<div class="field">
											<label style="float: left;">Mật khẩu<b
												style="color: red;">*</b></label>
											<div class="ui left input">
												<!-- 	<i class="lock icon"></i> -->
												<form:input path="password" value="${user.password}"
													type="password" placeholder="Mật khẩu" />
												<form:errors style="color: red;font-size: 15px;"
													path="password" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">Họ tên <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="hoTen" type="text" placeholder="Họ tên" />
												<form:errors style="color: red;font-size: 15px;"
													path="hoTen" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">CMND <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="cmnd" type="text" placeholder="CMND" />
												<form:errors style="color: red;font-size: 15px;" path="cmnd" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">SĐT <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="dienThoai" type="text" placeholder="SĐT" />
												<form:errors style="color: red;font-size: 15px;"
													path="dienThoai" />
											</div>
										</div>

										<div class="field">
											<label style="float: left;">Email <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">
												<form:input path="email" value="${user.email}" type="email"
													placeholder="Email address" />
												<form:errors style="color: red;font-size: 15px;"
													path="email" />
											</div>
										</div> --%>

										<div class="field">
											<label style="float: left;">Email <i
												style="color: red;"> *</i>
											</label>
											<div class="ui left input">

												<div class="ui equal width grid">
													<div class="column">
														<b>Tỉnh/Thành phố</b>
													</div>
													<div class="column">
														<b>Quận/Huyện</b>
													</div>
													<div class="column">
														<b>Xã/Phường</b>
													</div>
													<div class="equal width row">
														<div class="column">
															<select class="ui search dropdown" id="comboboxProvince"
																name="province">
																<option disabled="disabled">--Chọn tỉnh--</option>
																<c:forEach var="p" items="${provinces}">
																	<option value="${p.id}">${p.name}</option>
																</c:forEach>
															</select>
														</div>
														<div class="column">
															<select class="ui search dropdown" id="comboboxDistrict"
																name="district"><option disabled="disabled">--Chọn
																	huyện--</option></select>
														</div>
														<div class="column">
															<select class="ui search dropdown" id="comboboxWard"
																name="ward"><option disabled="disabled">--Chọn
																	xã--</option></select>
														</div>
													</div>
												</div>



											</div>
										</div>
										<button class="ui fluid large teal submit button">
											<c:if test="${action=='add'}">Thêm</c:if>
											<c:if test="${action=='edit'}">Cập nhật</c:if>
										</button>
									</div>
								</form:form>

								<!-- END DATATABLE -->
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END CONTENT -->
	</div>
	<script type="text/javascript">
	$('.ui.dropdown')
	  .dropdown()
	;
	
let selected_province = "$('#comboboxProvince').find('option:selected')";
let selected_district = '';
let selected_ward = '';
let list_provinces = [];

init();
document.getElementById("comboboxProvince").selectedIndex = "0";

$('#comboboxProvince').change(function () {
    var optionSelected = $(this).find("option:selected");
    var valueSelected  = optionSelected.val();
    var textSelected   = optionSelected.text();

    selected_province = list_provinces.find((p)=>{
    	return p.id == valueSelected;
    })
    console.log(selected_province);
    let htmlDistricts = '<option disabled="disabled">--Chọn huyện--</option>';
    let districts = selected_province.districts;
    for(let i = 0; i < districts.length; i++) {
    	htmlDistricts += '<option value="' + districts[i].id + '">' + districts[i].name + '</option>';
	}
	$('#comboboxDistrict').html(htmlDistricts);
	document.getElementById("comboboxDistrict").selectedIndex = "0";
    
});

$('#comboboxDistrict').change(function () {
    var optionSelected = $(this).find("option:selected");
    var valueSelected  = optionSelected.val();
    var textSelected   = optionSelected.text();

    selected_district = selected_province.districts.find((d)=>{
    	return d.id == valueSelected;
    })
    console.log(selected_district);
    let htmlWards = '<option disabled="disabled">--Chọn xã--</option>';
    let wards = selected_district.wards;
    for(let i = 0; i < wards.length; i++) {
    	htmlWards += '<option value="' + wards[i].id + '">' + wards[i].name + '</option>';
	}
	$('#comboboxWard').html(htmlWards);
	document.getElementById("comboboxWard").selectedIndex = "0";
    
});

$('#comboboxWard').change(function () {
	var optionSelected = $(this).find("option:selected");
	var valueSelected  = optionSelected.val();
    var textSelected   = optionSelected.text();
    selected_ward = selected_district.wards.find((w)=>{
    	return w.id == valueSelected;
    })
    console.log(selected_ward);
})

$('#filterButton').click(function () {
	console.log(selected_ward)
	$('#result').html("<a href='" + selected_province.name + ".htm'>"  + selected_province.name +  " / " + "<a href='" + selected_district.name + ".htm'>"  + selected_district.name +  " / " + "<a href='" + selected_ward.name + ".htm'>"  + selected_ward.name)
})

function init() {
	let province ={};
	let district ={};
	let ward = {};
	
	<%ArrayList<Province> provinces = (ArrayList) request.getAttribute("provinces");%>
	<%for (Province p : provinces) {%>
			province = {
					name : "<%=p.getName()%>",
					id : <%=p.getId()%>,
					districts : []
			}
			
			<%List<District> districts = (List) p.getDistricts();%>
			<%for (District d : districts) {%>	
					district = {
							name : "<%=d.getName()%>",
							id : <%=d.getId()%>,
							wards : []
					}
					<%List<Ward> wards = (List) d.getWards();%>
					<%for (Ward w : wards) {%>
						ward = {
								name : "<%=w.getName()%>",
								id : <%=w.getId()%>
						}
						district.wards.push(ward);
					<%}%>
					province.districts.push(district);
			<%}%>
			list_provinces.push(province);
			
   	<%}%>
   	
   
}

</script>
</body>
<!-- inject:js -->
<jsp:include page="footer.jsp"></jsp:include>
</html>