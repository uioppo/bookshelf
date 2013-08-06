<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
  <head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title>帮助中心 - 人教云汉商城</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">var path='<%=path %>';</script>
<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
<script src="<%=path %>/js/checkLogin.js"></script>
</head>
<body>
<div id="container">
  <div style="position:relative; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
    <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path%>/index.html'"></div>
      <div class="top_tip"><span class="_top_tip"><span class="top_style01">您好，欢迎来人教云汉商城！</span><a href="3">登录</a>|<a href="#">注册</a></span><span class="top_style02">购物车（0）件</span> </div>
      <div id="nav">
      <a href="<%=path %>/index.html">首页</a>|
      <a href="<%=path%>/user/findUserName.action">我的账户</a>|
      <a href="<%=path %>/user/orderList.jsp">我的订单</a>|
      <a href="<%=path %>/myBook/myBookList.jsp">我的电子书</a>|
      <a id="nav_current" href="#">帮助中心</a>
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
      <div id="main_con"><!--
        <div id="main_banner"><img src="<%=path %>/images/main_banner.jpg" /></div>
        --><div id="in_current">当前位置：<a href="<%=path %>/index.html">首页</a>|<span>帮助中心</span></div>
        <div id="in_tit">帮助中心</div>
        <div id="in_con">
		<div id="help">
		<dl>
		<dt><img src="<%=path %>/images/help_list.gif" />常见问题</dt>
		<dd>
		<p>
		<p>1. 如何购买电子书？</p>
      <p style="text-indent:25px;">登录人教云汉商城，请先注册一个免费张航，用其注册成功的账号登录商城，即可购买您所需的电子书。</p>
	<p>2.如何支付？</p>
      <p style="text-indent:25px;">您可以根据页面提供的银联卡银行，支付电子书费。</p>
		</dd>
		</dl>
		
		<dl>
		<dt><img src="<%=path %>/images/help_list.gif" />其他问题一</dt>
		<dd>
		<p>
		<p>1. 如何购买电子书？</p>
      <p style="text-indent:25px;">登录人教云汉商城，请先注册一个免费张航，用其注册成功的账号登录商城，即可购买您所需的电子书。</p>
	<p>2.如何支付？</p>
      <p style="text-indent:25px;">您可以根据页面提供的银联卡银行，支付电子书费。</p>
		</dd>
		</dl>
		<dl>
		<dt><img src="<%=path %>/images/help_list.gif" />其他问题二</dt>
		<dd>
		<p>
		<p>1. 如何购买电子书？</p>
      <p style="text-indent:25px;">登录人教云汉商城，请先注册一个免费张航，用其注册成功的账号登录商城，即可购买您所需的电子书。</p>
	<p>2.如何支付？</p>
      <p style="text-indent:25px;">您可以根据页面提供的银联卡银行，支付电子书费。</p>
		</dd>
		</dl>
		
		
		
		</div>
         
        
          
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
    