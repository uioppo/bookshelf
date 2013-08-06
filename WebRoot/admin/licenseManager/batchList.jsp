<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!doctype html>
<html>
  <head>
    <title>授权码批次列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/licenseManager/js/batchList.js"></script>
  	<script type="text/javascript">
  		$(document).ready(function(){
			loadBatchPage();
	  	});
  	</script>
  </head>
  <body style="margin: 0px; padding: 0px; background-color: #fff;">
  <div id="tb" style="padding:5px;height:30px">
	批次：<input id="batch" name="batch" />&nbsp;
	学校：<input id="schoolName" name="schoolName"/>
	<a href="javascript:loadBatchPage();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
  </div>
  <table id="batchList"></table>
  </body>
</html>
