<%@page import="com.hcctech.bookshelf.pojo.BsAdminUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	BsAdminUser user = (BsAdminUser)session.getAttribute("adminuser");
%>
<!DOCTYPE html>
<html>
  <head>
    <title>  </title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  </head>
  
  <body style="overflow: hidden;">
  <div style="position:relative;height:14px;width:823px;margin-bottom:-2px; margin-left: auto;margin-right: auto;">
     <div style="float:left;background:url(images/rCorner.gif) no-repeat;height:14px;width:12px;">
     </div>
     <div style="float: left; background-image: url(images/corner_bg.gif); background-attachment: initial; background-origin: initial; background-clip: initial; background-color: initial; height: 14px; width: 800px; background-repeat: repeat no-repeat; " id="gualiHeader">
     </div>
     <div style="float:right;background:url(images/corner_left.gif) no-repeat right top;height:14px;width:11px;">
     </div>
     <div style="background-color: #F9F9F9; width: 100%; height: 500px;float: left;">
		<div style="position:relative; top:20px; float:right; ">
		<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="540" height="454" align="middle">
            <param name="allowscriptaccess" value="always">
            <param name="quality" value="high">
            <param name="movie" value="flash/calendar.swf">
            <embed src="flash/calendar.swf" quality="high" width="540" height="454" align="middle" allowscriptaccess="always" wmode="opaque" type="application/x-shockwave-flash">
        </object>
        </div>
		<div style="position:relative; top:20px; left: 50px; font-size: 20px; font-weight: bold;">
     	<%=user.getRealName() %>，您好！
     	</div>
     	<div style="position:relative; top:60px; font-size: 20px; font-weight: bold; width: 250px;">
     	<iframe src="http://m.weather.com.cn/m/pn12/weather.htm?id=101010100T " width="250" height="110" marginwidth="0" marginheight="0" hspace="0" vspace="0" frameborder="0" scrolling="no"></iframe>
     	</div>
     </div>
  </div>
  </body>
</html>
