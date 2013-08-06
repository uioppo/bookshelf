<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>

<!doctype html>
<html>
  <head>
    <title>批次详细信息</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script> 
	<script type="text/javascript">
		function exportEx(batchId){
			window.location.href=path+"/license/exportExcel.action?batch="+batchId;
		}
	</script>
	<style type="text/css">
  		.lab{
			font-weight: bold;
			font-size:12px;
			text-align: left;
			padding-right: 0px;
			background-color: #F3F7F9;
		}
	table {
		background-color: #E9EDF1;
	}
  	</style> 
  </head>
  
  <body>
  	<font style="font-size: 22px;font-weight: bold;font-family: cursive;">授权码批次详细信息</font>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	<a href="javascript:exportEx('${batch}');" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">导出</a>
  	<br/><br/>
  	<table id="table">
  		<tr>
  			<td class="lab" width="300px;">批次：</td>
  			<td style="background-color: #fff;" width="300px">${batch}</td>
  		</tr>
  		<tr>
  			<td class="lab">学校：</td>
  			<td style="background-color: #fff;">${schoolName}</td>
  		</tr>
  		<tr>
  			<td class="lab">学段：</td>
  			<td style="background-color: #fff;">${schoolStage}</td>
  		</tr>
  		<tr>
  			<td class="lab">授权码有效截止日期：</td>
  			<td style="background-color: #fff;">${lifeTime}</td>
  		</tr>
  		<s:iterator value="bookSet" var="s" status="i">
  		<tr>
  			<td class="lab">电子书名称：</td>
  			<td style="background-color: #fff;">${s.productName }</td>
  		</tr>
  		<tr>
  			<td class="lab">电子书价格：</td>
  			<td style="background-color: #fff;">${s.price_ }</td>
  		</tr>
  		<tr>
  			<td class="lab">电子书学科：</td>
  			<td style="background-color: #fff;">${s.subject }</td>
  		</tr>
  		</s:iterator>
  	</table>
  	<br/>
  	<font style="font-size: 22px;font-weight: bold;font-family: cursive;">授权码</font>
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	<font style="font-size: 12px;font-weight: bold;font-family: cursive;">共${keyTotalSize}个   已用${usedKeyTotalSize}个</font>
  	<br/><br/>
  	<table style="text-align: center;">
  		<tr>
  			<td class="lab" width="200px" style="text-align: center;">
  				授权码
  			</td>
  			<td class="lab" width="250px" style="text-align: center;">
  				序列号
  			</td>
  			<td class="lab" width="150px" style="text-align: center;">
  				状态
  			</td>
  		</tr>
  		<s:iterator value="bsLicenseKeyList" var="s" status="i">
  			<s:if test="#i.even">
	  			<tr style="background-color: #eee;">
	  				<td>${s.keyId }</td>
	  				<td>${s.serialNum }</td>
	  				<td><s:if test="#s.useStatus==1">已使用</s:if>
	  					<s:else>未使用</s:else>
	  				</td>
	  			</tr>
  			</s:if>
  			<s:else>
  				<tr style="background-color: #fff;">
	  				<td>${s.keyId }</td>
	  				<td>${s.serialNum }</td>
	  				<td><s:if test="#s.useStatus==1">已使用</s:if>
	  					<s:else>未使用</s:else>
	  				</td>
	  			</tr>
  			</s:else>
  		</s:iterator>
  	</table>
  </body>
</html>
