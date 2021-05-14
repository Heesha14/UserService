$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
}); 

$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateUserForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidUserIDSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "UsersAPI",
		type : type,
		data : $("#formUser").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onUserSaveComplete(response.responseText, status);
		}
	});
});

function onUserSaveComplete(response, status)
{ 

//	console.log("onUserSaveComplete:"+response);
	if (status == "success") {
			var resultSet = JSON.parse(response);

		
		if (resultSet.status.trim() == "success") {
			console.log("inside alert");
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divUsersGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidUserIDSave").val("");
	$("#formUser")[0].reset(); 
}

//$(document).on("click", ".btnUpdate", function(event) {
//	$("#hidUserIDSave").val($(this).data("userid"));
//	$("#username").val($(this).closest("tr").find('td:eq(0)').text());
//	$("#password").val($(this).closest("tr").find('td:eq(1)').text());
//	$("#email").val($(this).closest("tr").find('td:eq(2)').text());
//	$("#phone").val($(this).closest("tr").find('td:eq(3)').text());
//	$("#gender").val($(this).closest("tr").find('td:eq(4)').text());
//	$("#designation").val($(this).closest("tr").find('td:eq(5)').text());
//	$("#firstName").val($(this).closest("tr").find('td:eq(6)').text());
//	$("#lastName").val($(this).closest("tr").find('td:eq(7)').text());
//});
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidUserIDSave").val($(this).data("userid"));
	$("#firstName").val($(this).closest("tr").find('td:eq(0)').text());
	$("#firstName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#lastName").val($(this).closest("tr").find('td:eq(2)').text());
	$("#designation").val($(this).closest("tr").find('td:eq(3)').text());
	$("#phone").val($(this).closest("tr").find('td:eq(4)').text());
	$("#email").val($(this).closest("tr").find('td:eq(5)').text());
	$("#gender").val($(this).closest("tr").find('td:eq(6)').text());
	$("#username").val($(this).closest("tr").find('td:eq(7)').text());
	$("#password").val($(this).closest("tr").find('td:eq(8)').text());
});
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "UsersAPI",
		type : "DELETE",
		data : "userID=" + $(this).data("userid"),
		dataType : "text",
		complete : function(response, status) {
			onUserDeleteComplete(response.responseText, status);
		}
	});
});

function onUserDeleteComplete(response, status)
{ 

	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divUsersGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	} 
}
function validateUserForm() 
{ 
// firstName
if ($("#firstName").val().trim() == "") 
 { 
 return "Enter first Name."; 
 } 
// lastName
if ($("#lastName").val().trim() == "") 
 { 
 return "Enter Last Name."; 
 } 
// designation-------------------------------
if ($("#designation").val().trim() == "") 
 { 
 return "Enter designation."; 
 } 
//phone-------------------------------
if ($("#phone").val().trim() == "") 
 { 
 return "Enter phone number"; 
 }
// is numerical value
var phoneNum = $("#phone").val().trim(); 
if (!$.isNumeric(phoneNum)) 
 { 
 return "Insert valid phone number"; 
 } 
// username------------------------
if ($("#username").val().trim() == "") 
 { 
 return "Enter username."; 
 } 
//password------------------------
if ($("#password").val().trim() == "") 
{ 
return "Enter password."; 
} 
return true; 
}
