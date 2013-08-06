<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<% String path = request.getContextPath(); %>
<!doctype html>
<html>
  <head>
    <title>阅读器列表</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/icon.css">
	<script type="text/javascript" src="<%=path%>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/reader/js/readerList.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		var path = "<%=path%>" ;
		loadReader();
	</script>
  </head>
  
  <body style="margin: 0px;padding: 0px; background-color: #fff;">
    <table id="productList"></table>
  </body>
</html>
