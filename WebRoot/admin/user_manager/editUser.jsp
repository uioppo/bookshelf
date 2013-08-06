<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
	<head>
		<title>后台个人账户修改</title>

		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  		<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
		<script type="text/javascript">var path = "<%=path%>";</script>
		<script type="text/javascript" src="<%=path%>/admin/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript">
		$(document).ready(function(){
			$("#password2").keyup(function(event){
				if(event.keyCode==13){
					tijiao();
				}
			});
			$('#email1').blur(function(){
				validateEmail();
			});
			$('#password').blur(function(){
				validatePassword();
			});
			$('#password1').blur(function(){
				validatePassword1();
			});
			$('#password2').blur(function(){
				validatePasswordRepeat();
			});
		});
		function tijiao() {
			var flag = true;
			flag = flag && validateEmail();
			flag = flag && validatePassword();
			flag = flag && validatePassword1();
			flag = flag && validatePasswordRepeat();
			if (flag) {
				$.ajax({
					type : "POST",
					url : path + "/admin/user/editUser.action",
					data : "bsAdminUser.email=" + $("#email1").val()
							+ "&bsAdminUser.password=" + $("#password1").val()
							+ "&math=" + Math.random(),
					async : false,
					success : function(msg) {
						parent.location.href = path + "/admin/login.jsp";
					}
				});
			}
		}
		function validateEmail() {
			var flag = false;
			var email1 = $("#email1").val();
			if (email1 != "" && $.trim(email1) != "") {
				var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
				if (!reg.test(email1)) {
					$("#email1").attr('class','form-error');
					$("#email1Span").text("请输入正确邮箱");
					flag = false;
				} else {
					$("#email1").attr('class','');
					$("#email1Span").text("");
					flag = true;
				}
			} else {
				$("#email1").attr('class','form-error');
				$("#email1Span").text("请输入邮箱");
				flag = false;
			}
			return flag;
		}
		function validatePassword() {
			var flag = false;
			var password = $("#password").val();
			var passwordSession = $("#passwordSession").val();
			if (password != "" || $.trim(password) != "") {
				$.ajax({
					type : "POST",
					url : path + "/admin/user/validatePassword.action",
					data : "password=" + password + "&math=" + Math.random(),
					async : false,
					success : function(msg) {
						if (msg) {
							$("#password").attr('class','');
							$("#passwordSpan").text("");
							flag = true;
						} else {
							$("#password").attr('class','form-error');
							$("#passwordSpan").text("原密码错误");
							flag = false;
						}
					}
				});
			} else {
				$("#password").attr('class','form-error');
				$("#passwordSpan").text("请输入原密码");
				flag = false;
			}
			return flag;
		}
		function validatePassword1() {
			var password1 = $("#password1").val();
			if (password1 != "" || $.trim(password1) != "") {
				var reg = /^[a-zA-Z0-9|_]{0,32}$/;
				if (!reg.test(password1)) {
					$("#password1").attr('class','form-error');
					$("#password1Span").text("密码由字母、数字、下划线组成");
					return false;
				} else {
					$("#password1").attr('class','');
					$("#password1Span").text("");
					return true;
				}
			} else {
				$("#password1").attr('class','form-error');
				$("#password1Span").text("请输入新密码");
				return false;
			}
		}
		function validatePasswordRepeat() {
			var password1 = $("#password1").val();
			var password2 = $("#password2").val();
			if (password2 != "" || $.trim(password2) != "") {
				if (password1 == password2) {
					$("#password2").attr('class','');
					$("#password2Span").text("");
					return true;
				} else {
					$("#password2").attr('class','form-error');
					$("#password2Span").text("两次密码不一致");
					return false;
				}
			} else {
				$("#password2").attr('class','form-error');
				$("#password2Span").text("请再次输入密码");
				return false;
			}
		}
	</script>
	<style type="text/css">
		.form-error {
  			border: 1px solid #dd4b39;
  		}
  		.form-element {
			  position: relative;
			  margin: 0 0 1.3em;
			  width: 310px;
			  }
		  .form-element label {
			  display: inline-block;
			  width: 100%;
			  position: relative;
			  }
		 .form-element strong {
			  margin: 0 0 .5em;
			  display: inline-block;
			  width: 100%;
			  -webkit-user-select: none;
			  -moz-user-select: none;
			  user-select: none;
			  }
		  .form-element.multi-field label strong {
			  display: none;
			  }
		  input[type=text],
		  input[type=password],
		  select {
		 	width: 100%;
		 	height: 24px;
		 	font-size: 13px;
		  }
		  .errormsg {
			margin: .5em 0 0;
			display: block;
			color: #DD4B39;
			line-height: 17px;
			}
	</style>
	</head>
	<body style="background-color: #fff; margin: 0px;">
		<div style="width: 310px; margin-left: auto; margin-right: auto; padding: 20px 100px 20px 100px; background-color: #E5E5E5;">
			<div style="width: 310px;">
				<div class="form-element">
					<label>
						<strong>用户名</strong>
						<input type="text" name="bsAdminUser.userName" value='<s:property value="#session.adminuser.userName"/>' readonly="readonly"/>
					</label>
				</div>
				<div class="form-element">
					<label>
						<strong>姓名</strong>
						<input type="text" name="name" value='<s:property value="#session.adminuser.realName"/>' readonly="readonly"/>
					</label>
				</div>
				<div class="form-element">
					<label>
						<strong>邮箱</strong>
						<input type="text" id="email1" name="bsAdminUser.email" value='<s:property value="#session.adminuser.email"/>'maxlength="50" readonly="readonly"/>
					</label>
					<span class="errormsg" id="email1Span"></span>
				</div>
				<div class="form-element">
					<label>
						<strong>原密码</strong>
						<input type="password" id="password" name="password" maxlength="32"/>
					</label>
					<span class="errormsg" id="passwordSpan"></span>
				</div>
				<div class="form-element">
					<label>
						<strong>设置密码</strong>
						<input type="password" id="password1" name="bsAdminUser.password" maxlength="32"/>
					</label>
					<span class="errormsg" id="password1Span"></span>
				</div>
				<div class="form-element">
					<label>
						<strong>确认密码</strong>
						<input type="password" id="password2" name="password2" maxlength="32"/>
					</label>
					<span class="errormsg" id="password2Span"></span>
				</div>
				<div>
					<a href="#" class="l-btn l-btn-plain" onclick="tijiao();"><span class="l-btn-left"><span class="l-btn-text icon-save" style="padding-left: 20px; ">保存</span></span></a>
				</div>
			</div>
		</div>
	</body>
</html>
