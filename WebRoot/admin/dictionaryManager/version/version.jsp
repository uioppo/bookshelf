<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>版本</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/product/css/main.css">
	<script type="text/javascript" src="<%=path%>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/dictionaryManager/version/version.js"></script>
	<script type="text/javascript">
		var path = "<%=path%>";
		$(document).ready(function(){
			findAllVersion();
		});
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
  	<div id="top">
  			<a href="javascript:void(0);" onclick="open1()" modal="true" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加版本</a>
  		</div>
    <table id="versionList"></table>
    <div id="add" align="center" style="width: 300px;height: 200px;vertical-align: middle;">
    	<table style="font-size: 10pt;">
	    	<tr>
	    		<th>代码:</th>
	    			<td><input type="text" class="easyui-validatebox" validType="length[1,20]" type="text" id="dicCode" required="true"/></td>
	    		</tr>
	    		<tr>
	    			<th>版本:</th>
	    			<th><input type="text" id="dicName" class="easyui-validatebox" required="true" validType="length[1,50]"/></th>
	    		</tr>
    	</table>
  	</div>
  	<div id="edit" align="center" style="width: 300px;height: 200px;vertical-align: middle;">
  		<input type="hidden" id="dicId">
    	<table style="font-size: 10pt;">
	    	<tr>
	    		<th>代码:</th>
	    			<td><input type="text" class="easyui-validatebox" validType="length[1,20]" type="text" id="eDicCode" required="true"/></td>
	    		</tr>
	    		<tr>
	    			<th>版本:</th>
	    			<th><input type="text" id="eDicName" class="easyui-validatebox" required="true" validType="length[1,50]"/></th>
	    		</tr>
    	</table>
  	</div>
  </body>
</html>
