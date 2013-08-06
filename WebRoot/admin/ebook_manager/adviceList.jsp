<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
  <head>
    <title>审批记录</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/icon.css">
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/ebook_manager/js/advice.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			loadAdviceList();
		});
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
 	<div id="adviceList_bar">
 		<input id="ss" class="easyui-searchbox" searcher="searcher" prompt="请输入搜索内容..." menu="#mm" style="width:300px"></input>&nbsp;&nbsp;
		<div id="mm" style="width:120px">
			<div name="bookName_">审核对象</div>
			<div name="ider_">审批意见</div>
			<div name="toName_">审核人</div>
			<div name="fromName_">被审核人</div>
		</div>
 	</div>
    <table id="adviceList"></table>
  </body>
</html>
