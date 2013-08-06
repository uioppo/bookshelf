<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%
String path = request.getContextPath();
String fileDomain=DomainUtil.getFileDomain();
%>
<!doctype html>
<html>
  <head>
    <title>推荐电子书</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/recommendSet/css/recommendSet.css"/> 
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript">var fileDomain="<%=fileDomain%>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/recommendSet/js/recommendSet.js"></script>
  	<style type="text/css">
		.drag-item{
			display:block;
			padding:5px;
			margin:2px;
		}
		.indicator{
			position:absolute;
			width:10px;
			height:10px;
			display:none;
			color:red;
		}
	</style>
  	<script type="text/javascript">
  		$(document).ready(function(){
	  		$.ajaxSetup ({ 
			     cache: false //关闭AJAX相应的缓存 
			 });
			loadAllBook("");
			loadSelectBook();
	  	});
  	</script>
  </head>
  
  	<body style="margin: 0px; padding: 0px; background-color: #fff;">
	  <div id="tb" style="padding:5px;height:20px">
		<strong>要选择的电子书  </strong>     商品名称：<input id="productName" name="productName"/>
		<a href="javascript:search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>&nbsp;&nbsp;&nbsp;
		<a id="addRow" href="javascript:addRow()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
	  </div>
  	<table id="dategrid1">
  	</table>
  	<div style="background:#efefef;border:1px solid #ccc;padding:5px;height:20px;font-size:12px;">
		<strong>已选择的电子书</strong>
	</div>
	<div style="height:350px;padding: 10px;"><!-- class="recommend_con" -->
		<div id="selectedBook">
		</div>
	</div>
	<div style="background:#efefef;border:1px solid #ccc;padding:5px;height:20px;">
	<a href="javascript:removeRow();" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" >清空</a>&nbsp;&nbsp;
		<a href="javascript:save1();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="save">保存</a>&nbsp;&nbsp;&nbsp;
	</div>
	</body>
</html>
