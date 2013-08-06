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
	<script type="text/javascript" src="<%=path%>/admin/product/js/discount.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
		var path = "<%=path%>" ;
	</script>
  </head>
  
  <body style="margin: 0px;padding: 0px; background-color: #fff;">
  
    <table id="productList"></table>
    <div id="tb" style="padding:5px;height:auto">
		<div>
		
		&nbsp;&nbsp;&nbsp;&nbsp;
		商品类型:       
		<select id="productType" style="width: 140px;background-color: #ffffff;" class="easyui-combobox" panelHeight="auto" editable=false>
			<option value="-1">全部</option>
			<option value="0">数字教辅</option>
			<option value="1">数字教材</option>
		</select>
		学段：
		<select id="schoolStage" style="width:140px;" class="easyui-combobox" panelHeight="auto" editable=false>
			<option value="-1">全部</option>
			<option value="1">小学</option>
			<option value="2">初中</option>
			<option value="3">高中</option>
		</select>
		科目：
		<select id="subject" style="width:140px;" class="easyui-combobox" panelHeight="auto" editable=false>
			<option value="-1">全部</option>
		</select>
		商品名称:<input id="sear" style="width:140px"></input>
		<a href="javascript:void(0);" onclick="javascript:search1();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
		<a href="javascript:void(0);" onclick="javascript:setBatch()" class="easyui-linkbutton" data-options="iconCls:'icon-much'">批量特价设置</a>
		</div>
	</div>
	
	<div id="setDisount">
		<table style="font-size: 13px;padding-top: 10px;font-size: 15px;">
					<input type="hidden" id="productId"/>
					<input type="hidden" id="vipPrice"/> 
					<input type="hidden" id="downPrice"/>
			<tr>
				<td style="text-align: center;">
						原价
				</td>
					<td>
					<label id="price"></label>元
				</td>
			</tr>
			<tr>
				<td style="text-align: center;line-height: 20px; height: 26px;">
						设置
				</td>
				<td>
					<label>
						<input type="radio" name="special" id="vip" value="0" onclick="choice(0)">优惠价
					</label>
					<label>
						<input type="radio" name="special" id="discount" value="1" onclick="choice(1)" >折扣
					</label>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;line-height: 20px; height: 26px;">
					<label id="special" style="text-align: center;"></label>
				</td>
				<td id="specialPrice">
					<label>
						<input id="nn" class="easyui-numberbox" style="border: 1px #AFAFAF solid;width: 175px;"/>
					</label>
				</td>
			</tr>
			<tr>
				<td>开始时间</td>
				<td>
					<input id="startTime" style="width: 180px;" class="easyui-datebox" editable=false name="bsProducts.publishTime"  required="true"/>
				</td>
			</tr>
			<tr>
				<td>结束时间</td>
				<td>
					<input id="endTime" style="width: 180px;" class="easyui-datebox" editable=false name="bsProducts.publishTime"  required="true"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="setBatchDisount">
		<table style="font-size: 13px;padding-top: 10px;font-size: 15px;">
			<tr>
				<td style="text-align: center;line-height: 20px; height: 26px;">
					折扣
				</td>
				<td id="specialPrice">
					<label>
						<input id="bDiscount" title="范围限制0.0---9.9" class="easyui-numberbox" style="border: 1px #AFAFAF solid;width: 175px;" data-options="precision:1,suffix:'折',required:true"/>
					</label>
				</td>
			</tr>
			<tr>
				<td>开始时间</td>
				<td>
					<input id="bStartTime" style="width: 180px;" class="easyui-datebox" editable=false name="bsProducts.publishTime"  required="true"/>
				</td>
			</tr>
			<tr>
				<td>结束时间</td>
				<td>
					<input id="bEndTime" style="width: 180px;" class="easyui-datebox" editable=false name="bsProducts.publishTime"  required="true"/>
				</td>
			</tr>
		</table>
	</div>
	
	
  </body>
</html>
