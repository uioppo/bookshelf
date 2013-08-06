<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/icon.css">
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/order_manager/js/orderList.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			loadOrderList();
		});
	</script>
	<style type="text/css">
		#cart_total{margin-top: 10px;}
		#funTable td { 
		border-right: 1px solid #fff; 
		border-bottom: 1px solid #fff; 
		background: #fff; 
		font-size:13px; 
		padding: 6px 6px 6px 12px; 
		color: #4f6b72; 
		font-family: 宋体, Arial; 
		} 
	</style>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
 	<div id="orderlist_bar">
 		<input id="ss" class="easyui-searchbox" searcher="searcher" prompt="请输入搜索内容..." menu="#mm" style="width:300px"></input>&nbsp;&nbsp;
		<div id="mm" style="width:120px">
			<div name="orderId_">订单号</div>
			<div name="orderUserName_">订单用户</div>
		</div>
		<label><input type="radio" name="orderState" value="all" checked="checked" onclick="serchStatus()"/>全部</label>
		<label><input type="radio" name="orderState" value="0" onclick="serchStatus()"/>未付款</label>
		<label><input type="radio" name="orderState" value="1" onclick="serchStatus()"/>已付款</label>
		<label><input type="radio" name="orderState" value="2" onclick="serchStatus()"/>已取消</label>
 	</div>
    <table id="orderlist"></table>
    
    <div style="display: none;">
    	<div id="orderDetail" title="订单详细" style="width: 700px;height: 500px;">
    		<table id="funTable" style="width: 100%;">
    			<tbody></tbody>
    		</table>
    	</div>
    </div>
  </body>
</html>
