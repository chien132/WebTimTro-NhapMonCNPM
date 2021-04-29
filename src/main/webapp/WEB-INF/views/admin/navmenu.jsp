<%@ page pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<base href="${pageContext.servletContext.contextPath}/">
<link href="css/styles.css" rel="stylesheet" />
<style type="text/css">
.dropbtn {
	background-color: #ffffff00;
	color: rgb(255, 255, 255);
	padding: 4px 45px;
	font-size: 15px;
	border: 2px solid #ffffff;
	border-radius: 10px;
	transition: 0.2s;
	text-transform: uppercase;
	margin: 10px;
	float: left;
}

.dropdown {
	position: relative;
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: rgba(0, 0, 0, 0.548);
	min-width: 15vw;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
	border-radius: 2px;
	margin-top: 54px;
}

.dropdown-content a {
	color: rgb(255, 255, 255);
	padding: 7px 20px;
	text-decoration: none;
	display: block;
	transition: 0.2s;
}

.dropdown-content a:hover {
	background-color: #ff0000;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropdown:hover .dropbtn {
	border: 2px solid #ff0000;
	color: #ff0000;
	font-weight: bold;
}
</style>
</head>

<body>
	<div style="padding: 20px;"></div>
	<div class="row"
		style="position: fixed; margin-top: -40px; background-color: #636363; min-width: 100%; z-index: 100; padding-left: 30px;">
		<div>
			<div style="color: white;">Logged in as</div>
			<b
				style="color: red; font-size: 20px; text-align: center; float: right;">${user.username}</b>
		</div>
		<div style="margin-left: 20px;"></div>
		<ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item dropdown"><a
				class=" dropdown-toggle dropbtn" href="admin/index.htm"></a>
				<div class="dropdown-content">
					<a href="admin/index.htm">Dashboard</a> <a href="index.htm">Homepage</a>
					<a href="logout.htm">Logout</a>
				</div></li>
		</ul>
		<ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item dropdown"><a
				class=" dropdown-toggle dropbtn" href="admin/user/table.htm">User</a>
				<div class="dropdown-content">
					<a href="admin/user/table.htm">Table</a> <a
						href="admin/user/add.htm">Add User</a>
				</div></li>
		</ul>
		<ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item dropdown"><a
				class=" dropdown-toggle dropbtn" href="admin/category/table.htm">Category & Brand</a>
				<div class="dropdown-content">
					<a href="admin/category/table.htm">Table</a> <a
						href="admin/category/insert.htm">Add Category</a><a
						href="admin/brand/insert.htm">Add Brand</a>
				</div></li>
		</ul>

		<ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item dropdown"><a
				class=" dropdown-toggle dropbtn" href="admin/product/table.htm">Product</a>
				<div class="dropdown-content">
					<a href="admin/product/table.htm">Table</a> <a
						href="admin/product/add.htm">Add Product</a>
				</div></li>
		</ul>
		<ul class="navbar-nav ml-auto ml-md-0">
			<li class="nav-item dropdown"><a
				class=" dropdown-toggle dropbtn" href="admin/bill/table.htm">Bill</a>
				<div class="dropdown-content">
					<a href="admin/bill/table.htm">Table</a> <a
						href="admin/bill/add.htm">Add Bill</a>
				</div></li>
		</ul>

	</div>
<!-- 		<div class="dropdown"> -->
<!-- 			<button class="dropbtn">Dropdown</button> -->
<!-- 			<div class="dropdown-content"> -->
<!-- 				<a href="#">Link 1</a> <a href="#">Link 2</a> <a href="#">Link 3</a> -->
<!-- 			</div> -->
<!-- 		</div> -->
</body>

</html>