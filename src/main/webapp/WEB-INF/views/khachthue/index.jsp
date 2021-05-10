<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="f"%>
<jsp:include page="header.jsp"></jsp:include>

<body
	style='background: url("resources/images/background/background.png") no-repeat; background-size: cover;'>
	<!-- Page Contents -->
	<div class="four wide column" style="padding-left: 5%;">
			<div class="ui segment column"
				style="position: fixed; max-width: 10%">
				<form action="khachthue/loc.htm" method="post">
				<h4>Điểm đánh giá</h4>
				<div class="ui checkbox">
					<input type="radio" name="diem" value="1"><label>⭐     </label> <br>
				</div>
				<div class="ui checkbox">
					<input type="radio" name="diem" value="2"> <label>⭐⭐   </label> <br>
				</div>
				<div class="ui checkbox">
					<input type="radio" name="diem" value="3"> <label>⭐⭐⭐  </label> <br>
				</div>
				<div class="ui checkbox">
					<input type="radio" name="diem" value="4"> <label>⭐⭐⭐⭐ </label> <br>
				</div>	
				<div class="ui checkbox">
					<input type="radio" name="diem" value="5"> <label>⭐⭐⭐⭐⭐</label> <br>
				</div>
				<div class="ui checkbox" hidden="true">
					<input type="radio" name="diem" value="" checked="checked"> <br>
				</div>
				<br>
				<h4>Số lượt đánh giá</h4>	
				<div class="ui checkbox">
					<input type="radio" name="soluot" value="0">  <label>Bài vừa được đăng</label> <br>
				</div>	 
				<div class="ui checkbox">
					<input type="radio" name="soluot" value="10">  <label>10+</label> <br>
				</div>
				<div class="ui checkbox">	
					<input type="radio" name="soluot" value="20"> <label>20+</label> <br>
				</div>
				<div class="ui checkbox">	
					<input type="radio" name="soluot" value="50"> <label>50+</label> <br>
				</div>
				<div class="ui checkbox">	
					<input type="radio" name="soluot" value="100"> <label>100+</label> <br>
				</div>
				<div class="ui checkbox" hidden="true">
					<input type="radio" name="soluot" value="" checked="checked"><br>
				</div>
				<br>
				<h4>Số người trên phòng</h4>
				<div class="ui checkbox">
					<input type="radio" name="songuoi" value="1">  <label>1</label> <br>
				</div>	 
				<div class="ui checkbox">
					<input type="radio" name="songuoi" value="2">  <label>2+</label> <br>
				</div>
				<div class="ui checkbox">	
					<input type="radio" name="songuoi" value="4"> <label>4+</label> <br>
				</div>
				<div class="ui checkbox">	
					<input type="radio" name="songuoi" value="8"> <label>8+</label> <br>
				</div>
				<div class="ui checkbox">	
					<input type="radio" name="songuoi" value="12"> <label>12+</label> <br>
				</div>
				<div class="ui checkbox" hidden="true">
					<input type="radio" name="songuoi" value="" checked="checked"> <br>
				</div>	
				<br>
				<h4>Giá thuê</h4>
				<div class="ui checkbox">
					<input type="radio" name="giathue" value="1000000">  <label>-1 000 000</label> <br>
				</div>	 
				<div class="ui checkbox">
					<input type="radio" name="giathue" value="2000000">  <label>-2 000 000</label> <br>
				</div>
				<div class="ui checkbox">	
					<input type="radio" name="giathue" value="3000000"> <label>-3 000 000</label> <br>
				</div>
				<div class="ui checkbox" hidden="true">
					<input type="radio" name="giathue" value="" checked="checked"> <br>
				</div>
				<br>
				<button class="fluid ui primary left labeled icon button">
  					<i class="right arrow icon"></i>Lọc
				</button>
				</form>
				<br>
				<a class="fluid ui red left labeled icon button" href="khachthue/tudong">
					<i class="crosshairs icon"></i> Tự động lọc
				</a>
			</div>
		</div>
	
	<div class="ui grid"
		style="padding-left:20%; max-width: 120%; margin-top: 17px; border-radius: 5px">
		<div class="twelve wide column" style="background-color: white; border-radius: 5px">
			<div class="row" style="margin-top: 10px">
				<jsp:include page="diachi.jsp"></jsp:include>
			</div>
	</div>
	</div>
	<br>
	<div class="ui grid"
		style="padding-left:20%; max-width: 150%; margin-top: 17px; border-radius: 5px">
		<div class="twelve wide column" style="background-color: white; border-radius: 5px">
			<br>
			<div class="ui divided items">
				<c:if test="${message!=null}"> <h3>${message}</h3> </c:if>
				<c:forEach var="nhatro" items="${nhatros}">
					<div class="item">
						<div class="ui small image">
							<div class="image">
								<a class="header" href="khachthue/nhatro/${nhatro.id}.htm">
									<img src="resources/images/nhatro/${nhatro.id}_1.png" alt="">
								</a>
							</div>
						</div>

						<div class="content">
							<a class="header" href="khachthue/nhatro/${nhatro.id}.htm">${nhatro.tieuDe}</a>
							<div class="meta">
								<span>Địa chỉ: ${nhatro.diachi.diaChi}/${nhatro.diachi.ward.name}/${nhatro.diachi.ward.district.name}/${nhatro.diachi.ward.district.province.name}</span>
							</div>
							<div class="meta">
								<span>Diện tích: ${nhatro.dienTich}m2</span>
							</div>
							<div class="meta">
								<span>${nhatro.soNguoiTrenPhong} người/phòng</span>
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
								<span><i class="user icon"></i>${nhatro.soLuot}
									đã thuê</span>
							</div>
						</div>
						<br>
					</div>
				</c:forEach>
			</div>
			<form action="${pageContext.servletContext.contextPath}/khachthue/timkiem.htm">
			<div class="row">
				<br><button class="ui button" name="page" value="1">Đầu</button>
				<c:forEach var="pagenumber" begin="${page}" end="${page + 5}">
					<c:if test="${page+5<=end}">
					<button class="ui button" name="page" value="pagenumber">${pagenumber}</button>
					</c:if>
				</c:forEach>
				<c:if test="${page+5<=end}"><button class="ui button" name="page" value="${end}">Cuối</button></c:if>
			</div>
			</form>
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