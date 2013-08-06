<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!doctype html>
<html>
  <head>
    <title>出错了- 人教云汉商城</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
  </head>
  <body>
<div id="container">
  <div style="position:relative;height:74px; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
    <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path %>/index.html'"></div>
    </div>
  </div>
     <div id="main_box">
    	<div id="" style="padding-bottom: 0px;">
      		<div align="center" style="margin-top:30px;height: 450px;">
      			<img alt="" src="<%=path %>/images/404.jpg">
      			<br/><br/><br/>
      			<font style="FONT-SIZE: 20px; FONT-WEIGHT: bold" color="#000000">&nbsp;&nbsp;&nbsp;&nbsp;很抱歉，您要访问的内容不存在...</font>
      			<br/><br/>
      			<a href="<%=path %>/index.html"><font face="黑体" size="4" color="#FF9900">回首页</font></a>
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
