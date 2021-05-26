<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@page import="ptithcm.entity.Province"%>
<%@page import="ptithcm.entity.Truong"%>
<%@page import="ptithcm.entity.KhachThue"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<jsp:include page="header.jsp"></jsp:include>

<body
	style='background: url("resources/images/background/background.png"); background-size: cover;'>
	<!-- Page Contents -->

		<div class="ui card" style="margin-left: 2%; position: fixed; border-radius: 10%">
			<div class="ui large image">
				<img src="resources/images/avatar/${sessionScope['username']}.png" style="border-radius: 10%" alt="">
			</div>
			<div class="content">
				<a class="header" href="account">${user.username}</a>
				<div class="meta">
				<i class="user icon"></i>
				${user.hoTen}</div>
				<div class="meta">
				<i class="calendar icon"></i>
				Tham gia: ${user.ngayDangKy}</div>
			</div>
		</div>
		<div class="ui grid" style="margin-left: 20%; margin-top: 17px; border-radius: 5px">
		<div class="twelve wide column" style="background-color: white; border-radius: 5px">
			<h2 style="text-align: center; color: red; -webkit-text-stroke-width: 1px; -webkit-text-stroke-color: black;">Thông tin thêm của bạn</h2>
			<table class="ui table" >
				<tbody>
					<tr>
						<td><h4>Họ tên: </h4></td>
						<td>${user.hoTen}</td>
						<td>
							<i class="circular teal edit icon" onclick="$('.large.modal').modal('show');"></i>
						</td>
					</tr>
					<tr>
						<td><h4>Số chứng minh thư/căn cước</h4></td>
						<td>${user.cmnd}</td>
					</tr>
					<tr>
						<td><h4>Điện thoại</h4></td>
						<td>${user.dienThoai}</td>
					</tr>
					<tr>
						<td><h4>Email</h4></td>
						<td>${user.email}</td>
					</tr>
				</tbody>
			</table>
			<c:if test="${error}">
				<div class="ui error">
					<div class="header">${error}</div>
				</div>
			</c:if>
			
			<div class="ui large modal">
			<form action="khachthue/thongtinthem" method="post" enctype="multipart/form-data">
				
			</form>
		</div>
		
		</div>
		</div>
	<script type="text/javascript">
		$('.special.cards .image').dimmer({
			on : 'hover'
		});
		let selected_province = "$('#comboboxProvince').find('option:selected')";
		let selected_truong = '';
		let list_provinces = [];
		let selected_que = "$('#comboboxQue').find('option:selected')";
		let list_ques = [];

		init();
		document.getElementById("comboboxProvince").selectedIndex = "0";
		document.getElementById("comboboxQue").selectedIndex = "0";

		$('#comboboxProvince').change(function () {
		    var optionSelected = $(this).find("option:selected");
		    var valueSelected  = optionSelected.val();
		    var textSelected   = optionSelected.text();

		    selected_province = list_provinces.find((p)=>{
		    	return p.id == valueSelected;
		    })
		    console.log(selected_province);
		    let htmlTruongs = '<option disabled="disabled">--Chọn trường--</option>';
		    let truongs = selected_province.truongs;
		    
		    for(let i = 0; i < truongs.length; i++) {
		    	htmlTruongs += '<option value="' + truongs[i].id + '">' + truongs[i].name + '</option>';
			}
			$('#comboboxTruong').html(htmlTruongs);
			document.getElementById("comboboxTruong").selectedIndex = "0";
		    
		});

		$('#comboboxTruong').change(function () {
			var optionSelected = $(this).find("option:selected");
			var valueSelected  = optionSelected.val();
		    var textSelected   = optionSelected.text();
		    selected_truong = selected_province.truong.find((t)=>{
		    	return t.id == valueSelected;
		    })
		    console.log(selected_truong);
		})
		
		$('#comboboxQue').change(function () {
		    var optionSelected = $(this).find("option:selected");
		    var valueSelected  = optionSelected.val();
		    var textSelected   = optionSelected.text();

		    selected_que = list_ques.find((q)=>{
		    	return q.id == valueSelected;
		    })
		    console.log(selected_que);
		});

		function init() {
			let province ={};
			let truong ={};
			let que ={};
			
			<%ArrayList<Province> provinces = (ArrayList) request.getAttribute("provinces");%>
			<%for (Province p : provinces) {%>
					province = {
							name : "<%=p.getName()%>",
							id : <%=p.getId()%>,
							truongs:[],
					}
							<%List<Truong> truongs = (List) p.getTruongs();%>
							<%for (Truong t : truongs) {%>
								truong = {
										name : "<%=t.getTen()%>",
										id : <%=t.getId()%>
								}
								province.truongs.push(truong);
					<%}%>
					list_provinces.push(province);
		   	<%}%>
		   	<%ArrayList<Province> ques = (ArrayList) request.getAttribute("ques");%>
			<%for (Province p : ques) {%>
					que = {
							name : "<%=p.getName()%>",
							id : <%=p.getId()%>,
					}
					list_ques.push(que);
		   	<%}%>
		}
		CKEDITOR.replace('description');
		var ckeditor=CKEDITOR.replace('description');
		CKFinder.setupCKEditor(ckeditor,'${pageContext.request.contextPath}/resources/ckfinder/');
	</script>
</body>

</html>