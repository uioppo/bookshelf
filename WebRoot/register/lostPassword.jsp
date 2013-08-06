<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title>忘记密码 - 人教云汉商城</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">var path="<%=path %>";</script>
<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path %>/register/js/lostPassword.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
<script type="text/javascript">
		$(document).ready(function(){
	  		$('#kaptchaImage').click(function() {
				//验证码图片
		         $(this).attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
		    });
			$("#userName").blur(function(){
				validateUserNameNull();
			});
			$("#validationCode").blur(function(){
				validCode();
			});
		});
	</script>
</head>
<body>
<div id="container">
  <div style="position:relative; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
    <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path%>/index.html'"></div>
      <div class="top_tip"> <span class="top_style01">您好，欢迎来人教云汉商城！</span><a href="<%=path %>/login.jsp">登录</a>|<a href="<%=path %>/register/register.jsp">注册</a><span class="top_style02">购物车（0）件</span> </div>
      <div id="nav">
      <a href="<%=path %>/index.html">首页</a>|
      <a href="<%=path%>/user/findUserName.action">我的账户</a>|
      <a href="<%=path %>/user/orderList.jsp">我的订单</a>|
      <a href="<%=path %>/myBook/myBookList.jsp">我的电子书</a>|
      <a href="<%=path %>/help/helpCenter.jsp">帮助中心</a>
    </div>
    </div>
  </div>
  <div id="banner">
  <iframe src="<%=path%>/hot/banner.html" height="147px" width="1490px" marginheight="0px" marginwidth="0px" scrolling="no" style="border: none;">
    </iframe>
  </div>
   <div id="search_box">
  <div id="search">
  <p class="search_left"></p>
  <p class="search_center"><input class="search_center_input" value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
  <div id="main_box">
    <div id="main">
      <div id="main_con" class="password">
        <div id="modular_info">
          <ul>
            <li class="modular_info_top">找回密码</li>
            <li class="modular_info_center">
			<div id="password_box">
			<form id="lostPasswordForm" name="lostPasswordForm" action="<%=path%>/register/registerAction_lostPassword.action" method="post">
              <table id="reg_content" width="477" border="0">
                <tr>
                  <td width="121" align="right" valign="top" class="reg_style01">用户名(邮箱)：</td>
                  <td width="322" align="left" valign="middle" class="reg_style02">
                  	<input id="userName" type="text" name="bsWebUser.wuEmail" /><br/>
                </tr>
                <tr>
                  <td class="reg_style01" align="right" valign="top">验证码：</td>
                  <td class="reg_style02" id="check" align="left" valign="middle">
	                  <input id="validationCode" name="validCode" type="text" autocomplete="off" />
  				<img src="../Kaptcha.jpg" id="kaptchaImage" title="点击更换验证码" style="cursor:pointer;"/><br/>
                  </td>
                </tr>
                <tr><td></td><td><span style="color: #c40;" id="userNameSpan" ></span></td></tr>
                <tr>
                  <td id="reg_button" style="padding-top: 20px;" colspan="2" align="center" valign="top">
                  	<input id="submitButton" type="button" value="提交" onclick="lostPasswordSubmit();"/>
                  </td>
                </tr>
              </table>
              </form>
			  <div id="password_tip">注：系统会将密码找回邮件发送到您的邮箱<br/>请您注意接收邮件！
</div>
			  </div>
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
