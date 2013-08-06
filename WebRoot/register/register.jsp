<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title>注册 - 人教云汉商城</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">var path="<%=path %>";</script>
<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path %>/register/js/register.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
<script type="text/javascript">
  	$(document).ready(function(){
  		$("#userNameDiv").hide();
  		$("#passwordDiv").hide();
  		$("#passwordRepeatDiv").hide();
  		$("#validCodeDiv").hide();
  		$("#userName").focus();
  		$('#kaptchaImage').click(function() {
	        show_vcode();
	    });
		$("#userName").blur(function(){
			validateUserName();
		});
		$("#userName").focus(function(){
			$("#userNameDiv").hide();
		});
		
		$("#password").blur(function(){
			validatePassword();
		});
		$("#password").focus(function(){
			$("#passwordDiv").hide();
		});
		$("#passwordRepeat").blur(function(){
			validatePasswordRepeat();
		});
		$("#passwordRepeat").focus(function(){
			$("#passwordRepeatDiv").hide();
		});
		$("#validationCode").blur(function(){
			validCode();
		});
		$("#validationCode").focus(function(){
			$("#validCodeDiv").hide();
		});
	});
	function show_vcode(){
	 $('#kaptchaImage').attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
	}
</script>
</head>
<body>
<div id="container">
  <div style="position:relative; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
    <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path%>/index.html'"></div>
      <div class="top_tip"> <span class="top_style01">您好，欢迎来人教云汉商城！</span><a href="<%=path %>/login.jsp">登录</a>|<a href="<%=path %>/register/register.jsp#reg">注册</a><span class="top_style02">购物车（0）件</span> </div>
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
  <a id="reg"></a>
  <p class="search_center"><input class="search_center_input" value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
    <div id="main_box">
    <div id="main">
      <div id="main_con" class="reg">
        <div id="reg_info">
          <ul>
            <li class="reg_info_top">注册<span>欢迎注册人教云汉商城</span></li>
            <li class="reg_info_center">
            <form id="registerForm" name="registerForm" action="<%=path%>/register/registerWebUser.action" method="post">
  			<s:token></s:token>
  			<div class="register_con">
  			<div class="register_con_br"><label>请输入用户名：</label><span class="register_con_span"><input id="userName" type="text" name="bsWebUser.wuEmail" maxlength="50" autocomplete="off"/></span><div id="userNameDiv" class="register_form_wrong" style="top: 74px;"><span id="userNameSpan">请填写您的Email。</span></div>
  			<div class="register_form_tips">请填写您常用的Email方便您接收验证邮件、找回密码</div>
  			</div>
  			<div class="register_con_br"><label>请设定密码：</label><span class="register_con_span"><input id="password" type="password" name="bsWebUser.wuPassword" maxlength="20"/></span><div id="passwordDiv" class="register_form_wrong" style="top: 136px;"><span id="passwordSpan">请填写您的Email。</span></div>
  			<div class="register_form_tips">密码可由大小写英文字母或数字组成长度为6-20字符</div>
  			</div>
  			<div class="register_con_br"><label>请确定密码：</label><span class="register_con_span"><input id="passwordRepeat" type="password" maxlength="20"/></span><div id="passwordRepeatDiv" class="register_form_wrong" style="top: 198px;"><span id="passwordRepeatSpan">请填写您的Email。</span></div>
  			<div class="register_form_tips">确认您设定的密码</div>
  			</div>
  			<div class="register_con_br"><label>请输入验证码：</label><span class="register_con_span"><input id="validationCode" name="validCode" type="text" maxlength="4"  autocomplete="off"/></span><div id="validCodeDiv" class="register_form_wrong" style="top: 260px;"><span id="validCodeSpan">请填写您的Email。</span></div>
  			<div class="register_form_tips"><img src="../Kaptcha.jpg" id="kaptchaImage" title="点击更换验证码" style="cursor:pointer;"/><span style="margin-left:5px;">看不清？<a href="javascript:show_vcode()" name="change_code_link">换张图</a></span></div>
  			</div>
             </div>
              <div class="reg_con_tiaoyue"><span>&nbsp;<input id="fig" name="reg_checkbox" type="checkbox" checked="checked"/></span><span>我已阅读并同意《<a href="#">人教云汉商城用户协议</a>》</span></div>
             <div class="reg_con_bottom"><input id="submitButton" class="register_submitButton" type="button" value="注册" onclick="registerSubmit();"/><span id="pass_dd" style="display:none;">请稍后...</span></div>
             
              </form>
            </li>
            <li class="reg_info_bottom"></li>
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