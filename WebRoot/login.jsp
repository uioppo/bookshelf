<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% String path = request.getContextPath(); %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>登录 - 人教云汉商城</title>
		<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
		<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="<%=path%>/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
		<script type="text/javascript">
			function login() {
				var username = $("#username").val();
				var pwd = $("#password").val();
				if(username == "" || username == null) {
					$("#loginMsgShow").html("请输入用户名!");
					$("#username").focus();
					return false ;
				}
				if(pwd == "" || pwd == null) {
					$("#loginMsgShow").html("请输入密码!");
					$("#password").focus();
					return false ;
				}
				var authCode = $("#validCode");
				if((authCode.length != 0) && (authCode.val()  == '' || authCode.val()  == null)){
					$("#loginMsgShow").html("请输入验证码！");
					return false ;
				} 
				
				$("#loginForm").attr("action","<%=path%>/userLogin.action");
				$("#loginForm").submit();
			}
			
			function change(){
		         $("#kaptchaImage").attr("src", "<%=path%>/Kaptcha.jpg?" + Math.floor(Math.random()*100));
			}
			$(document).ready(function(){
				$("#username").val("");
				$("#password").val("");
				$("#username").focus();
				$("#username").keydown(function(event){
  					if(event.keyCode==13) {
  						login();
  				}});
  				$("#password").keydown(function(event){
  					if(event.keyCode==13) {
  						login();
  				}});
				
				$('#loginMsgShow').show();
				$("#username").change(function(){
				  $("#password").val("");
				});
				
			});
			function vali(){
				$.ajax({
					type:"post",
					url:"<%=path%>/userEncode.action",
					data:"name=" + $("#username").val() + "&dd="+Math.floor(Math.random()*100),
					success:function(data){
						var num = $.cookie(data);
						if(parseInt(num) >= 5) {
							$("#authCOde").html("");
							$("#authCOde").append("<td width='121' align='right' valign='top' class='reg_style01'>验证码：</td>");
							$("#authCOde").append("<td width='322' align='left' valign='middle' class='reg_style02'><input style='width:130px;' type='text' id='validCode' name='validCode'/>&nbsp;" + 
							"<div style='top:-40px;left:140px;position: relative; height:0px; width:0px;'><img src='Kaptcha.jpg' id='kaptchaImage' title='看不清楚请点击图片' onclick='change()'/></div></td>");	
							$("#validCode").keydown(function(event){
			  					if(event.keyCode==13) {
			  						login();
			  				}});			
						}
						$("#password").val("");
					}
				});
			}
		</script>
	</head>
<body>
<div id="container">
  <div style="position:relative;height:74px; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
    <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='index.html'"></div>
    </div>
  </div>
     <div id="main_box">
    <div id="main" style="padding-bottom: 0px;">
      <div id="main_con" class="login">
        <div id="modular_info">
          <ul>
            <li class="modular_info_top"><div style="float: left;">登录</div><div style="float: right; padding-right: 35px; *padding-top: 8px;" class="gozhuce"><span>还不是人教云汉商城用户？</span><a  href="<%=path %>/register/register.jsp">立即注册</a></div></li>
            <li class="modular_info_center" style="height: 235px;">
            <form action="" name="loginForm" method="post" id="loginForm">
			<input type="hidden" value="<%=request.getParameter("url")%>" name="url">
              <table id="reg_content" width="477" border="0" >
                <tr>
                  <td width="121" align="right" valign="top" class="reg_style01">用户名：</td>
                  <td width="322" align="left" valign="middle" class="reg_style02"><input name="username" id="username" type="text" onblur="vali()"/></td>
                </tr>
                 <tr>
                  <td width="121" align="right" valign="top" class="reg_style01">密&nbsp;&nbsp;&nbsp;&nbsp;码：</td>
                  <td width="322" align="left" valign="middle" class="reg_style02 lasspassword"><input name="password" id="password" type="password" />
                  <a href="<%=path %>/register/lostPassword.jsp">忘记密码</a>
                  </td>
                </tr>
                <tr id="authCOde">
                </tr>
                <tr>
                  <td id="reg_button" colspan="2" align="center" valign="middle">
                  <input name="in_button01" type="button" onclick="return login()" value="登录" />
                   <div id="loginMsgShow" style="font-weight: normal; top:-20px; color: #C30; line-height: 26px; ">
						<s:property value="loginMsg" />
					</div>
                  </td>
                </tr>
              </table>
              </form>
			</li>
            <li class="modular_info_center" style="height: 132px;">
             <div id="login_other"><p>使用教师网络培训和服务平台账号<input name="login_buttonts" type="image" src="<%=path %>/images/login_buttonts.gif" width="97" height="23" /></p></div>
            </li>
            <li class="modular_info_bottom"></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="foot">
  <p><a href="http://www.pep.com.cn/" target="_blank">人教网首页</a>|<a href="#">本站首页</a>|<a href="#">版权声明</a>|<a href="#">服务热线</a>|<a href="#">关于我们</a></p>
  <p>京ICP备05019902号|新出网证(京)字016|京公网安备110402440009号&nbsp;&nbsp;版权所有：人民教育出版社有限公司</p>
</div>
</body>
</html>