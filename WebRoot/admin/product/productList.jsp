<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<% String path = request.getContextPath(); %>
<!doctype html>
<html>
  <head>
    <title>商品列表</title>
    <link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/icon.css">
	<script type="text/javascript" src="<%=path%>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/product/js/productList.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		var path = "<%=path%>" ;
		loadProduct(1);
	</script>
  </head>
  
  <body style="margin: 0px;padding: 0px; background-color: #fff;">
  
  <input type="hidden"" value="<%=path%>" id="path"/>
    <table id="productList"></table>
    <div id="tb" style="padding:5px;height:auto">
		<div>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="easyui-searchbox" data-options="prompt:'搜索...',menu:'#mm',
			searcher:function(value,name){search(value,name)}" style="width:300px"></input>
			
			<div id="mm" style="width:120px">
				<div data-options="name:'productName'">商品名称</div>
				<div data-options="name:'productId'">商品编号</div>
			</div>
		</div>
	</div>
  </body>
</html>
