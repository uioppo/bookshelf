<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>后台登录</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<link href="images/login.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript">
if(parent!=null&&typeof(parent._ispand)!='undefined'){
window.parent.location.href="<%=path%>/admin/login.jsp";
}
	$(function() {
		$('.captcha').focus(function() {
			$(".yzm-box").html("<img src='../Kaptcha.jpg?xxx="+Math.floor(Math.random()*100)+"'/>");
			$('.yzm-box').show();
		});

		$('.captcha').focusout(function() {
			$('.yzm-box').hide();
		});
		var magsx="${msg}";
		if(magsx!=""){
			$("#message-box").show();
		}
	});
	function onsub(){
		if($('#username').val()==""){
		$('#username').focus();
		return false;
		}
		if($('#password').val()==""){
		$('#password').focus();
		return false;
		}
		if($('#validCode').val()==""){
		$('#validCode').focus();
		return false;
		}
		return true;
	}
</script>
</head>
<body>
	<div id="wrap">
		<div id="header"></div>
		<div id="content-wrap">
			<div class="space">
			<div id="message-box" style="margin-left: auto; margin-right: auto;" aa="ad">
				<s:property value="msg" />
				
			</div>
			</div>
			<form action="<%=path%>/admin/adminlogin.action" method="post" onsubmit="return onsub();">
				<div class="content">
					<div class="field">
						<label>账&nbsp;&nbsp;户：</label><input id="username" type="text" name="adminUser.userName" />
					</div>
					<div class="field">
						<label>密&nbsp;&nbsp;码：</label><input id="password" type="password"
							name="adminUser.password" /><br />
					</div>
					<div class="field">
						<label>验证码：</label><input id="validCode" class="captcha" maxlength="6" name="validCode"
							value="" type="text" /><br />
						<div class="yzm-box"></div>
					</div>
					<div class="btn">
						<input name="" type="submit" class="login-btn" value="" />
					</div>
				</div>
			</form>
		</div>
		<div id="footer"></div>
	</div>
</body>
</html>
