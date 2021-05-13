<%@page import="com.repo.UserRepo"%>
<%@page import="com.service.UserService"%>
<%@page import="com.service.UserServiceImpl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>User Management </title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/Main.js"></script>
</head>
<body> 
<div class="container"><div class="row"><div class="col-6"> 
<h1>User Management </h1>
<form id="formUser" name="formUser">
 First Name: 
 <input id="firstName" name="firstName" type="text" 
 class="form-control form-control-sm">
 <br> Last name: 
 <input id="lastName" name="lastName" type="text" 
 class="form-control form-control-sm">
  Gender: 
 <input id="gender" name="gender" type="text" 
 class="form-control form-control-sm">
 <br> Email: 
 <input id="email" name="email" type="text" 
 class="form-control form-control-sm">
 <br> Phone number: 
 <input id="phone" name="phone" type="number" 
 class="form-control form-control-sm">
 <br> Designation: 
 <input id="designation" name="designation" type="text" 
 class="form-control form-control-sm">
 Username: 
 <input id="username" name="username" type="text" 
 class="form-control form-control-sm">
 <br> Password: 
 <input id="password" name="password" type="text" 
 class="form-control form-control-sm">
 <br>
 <input id="btnSave" name="btnSave" type="button" value="Save" 
 class="btn btn-primary">
 <input type="hidden" id="hidItemIDSave" 
 name="hidItemIDSave" value="">
</form>
<div id="alertSuccess" class="alert alert-success"></div>
<div id="alertError" class="alert alert-danger"></div>
<br>
<div id="divItemsGrid">
 <%
 UserService userServiceObj = new UserServiceImpl(); 
 out.print(userServiceObj.getAllUsersInfo()); 
 %>
</div>
</div> </div> </div> 
</body>
</html>