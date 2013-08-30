<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>

<!doctype html>
<html>
  <head>
    <title>web用户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/webuser_manager/js/webuser.js"></script>
  	<%
		String importExcelSuccess = request.getParameter("importExcelSuccess");
		String exportExcelFail = request.getParameter("exportExcelFail");
	%>
	<script type="text/javascript">
	var isImportExcelSuccess = <%=(importExcelSuccess!=null && "1".equals(importExcelSuccess))?"true":"false"%>
	var idExportExcelFail = <%=(exportExcelFail!=null && "1".equals(exportExcelFail))?"true":"false"%>
	$(document).ready(function(){
		if(isImportExcelSuccess){
			alert("上传成功！");
		}
		if(idExportExcelFail){
			alert("导出失败，请联系管理员！");
		}
		loadWebUserList();
  	});
	function checkImport(){
		if($("#excel").val()==""){
			alert("请选择文件！");
			return false;
		}
		return true;
	}
  	</script>
  </head>
  
    <body style="margin: 0px; padding: 0px; background-color: #fff;">
	  <div id="tb" style="padding:5px;height:70px">
      <div id="excel_div">
 		<form action="/webuser/importExcel.action" enctype="multipart/form-data" method="post" onsubmit="return checkImport();">
		&nbsp;&nbsp;
		文件:       
		<input type="file" id="excel" name="excel" style="width:140px">
		<input type="submit" value="导入Excel" name="submit" /> 
  		&nbsp;&nbsp;|<a href="/webuser/exportExcel.action" class="easyui-linkbutton" icon="icon-redo" plain="true" >导出Excel</a>
  		</form>
  	  </div>
		用户名：<input id="wuEmail" name="wuEmail"/>
		<a href="javascript:loadWebUserList();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	  </div>
	  <table id="webUserList"></table>
  </body>
</html>
