<%@page import="com.hcctech.bookshelf.pojo.BsAdminUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
BsAdminUser adminUser = (BsAdminUser)request.getSession().getAttribute("adminuser");
if(adminUser==null||adminUser.getUserName()==null){
	response.sendRedirect(path + "/admin/login.jsp");
}
%>
<!doctype html>
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>后台首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<link href="images/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">var path = "<%=path%>";</script>
	<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="user_manager/js/index.js"></script>
	<script type="text/javascript">
	var _ispand = true;
	$(function(){
		
		//setMenuHeight
		$('.menu').height($(window).height()-30-27-26-5);
		$('.sidebar').height($(window).height()-30-27-26-5);
		$('.page').height($(window).height()-30-27-26-5);
		$('.page iframe').width($(window).width()-15-168);
		
		$('#menu_fun').height($(window).height()-30-27-26-5);
		loadFunctionByRoleId();
		
});
</script>


</head>

<body style="overflow-y:hidden">
<div id="wrap">
	<div id="header" style="height: 57px;">
    <div class="logo fleft" style="height: 30px;"></div>
    <div class="nav fleft">
    </div>
    <a class="logout fright" style="margin-top: 2px; margin-right: 10px;" title="退出" href="<%=path %>/admin/adminLogout.action"> </a>
    <div class="clear"></div>
    <div class="subnav">
    	<div class="subnavLeft fleft"></div>
        <div class="fleft" style="line-height: 28px;"><span style=""><%=adminUser!=null?adminUser.getRealName():"" %>，欢迎您！</span><span id="dangqian"></span></div>
        <div class="subnavRight fright"></div>
    </div>
    </div><!--#header -->
    <div id="content">
    <div class="space"></div>
    <div class="menu fleft">
    	<ul id="menu_fun" style=" overflow-y: auto; overflow-x: hidden;">
        	<li class="subMenuTitle">菜单列表</li>
        </ul>
    </div>
    <div class="sidebar fleft"><div class="btn"></div></div>
    <div class="page" style="width: auto;">
    <iframe width="100%" scrolling="auto" height="100%" frameborder="false" allowtransparency="true" style="border: medium none; background-color: #ffffff;" src="main.jsp" id="rightMain" name="right"></iframe>
    </div>
    </div><!--#content -->
    <div class="clear"></div>
    <div id="footer">CopyRight &copy; Young Dynasty Tech. </div><!--#footer -->
</div><!--#wrap -->
</body>
</html>
