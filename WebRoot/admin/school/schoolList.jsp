<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>学校管理列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">   
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/icon.css">
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/school/js/schoolList.js"></script>
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
			loadSchoolList();
			$("#sheng").change(function(){
				shi();
			});
			$("#shi").change(function(){
				qu();
			});
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
  
  <body style="background-color: #fff;margin:0px; ">
  	<div id="addSchool_bar" style="padding:5px;height:auto">
  		<div id="excel_div">
 		<form action="/school/importExcel.action" enctype="multipart/form-data" method="post"  onsubmit="return checkImport();">
		&nbsp;&nbsp;
		文件:       
		<input type="file" id="excel" name="excel" style="width:140px">
		<input type="submit" value="导入Excel" name="submit" /> 
  		&nbsp;&nbsp;|<a href="/school/exportExcel.action" class="easyui-linkbutton" icon="icon-redo" plain="true" >导出Excel</a>
  		</form>
  		</div>
  		<div id="search">
		&nbsp;&nbsp;
		学校名称:       
		<input id="searchSchoolName" style="width:140px">
		<a href="javascript:void(0);" onclick="javascript:search1();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
  		</div>
 		<div>
 		<a href="javascript:;" onclick="openAddSchoolDiv()" class="easyui-linkbutton" icon="icon-add" plain="true" >添加学校</a>&nbsp;
 		<!--<a href="javascript:;" onclick="deleteSchool()" class="easyui-linkbutton" icon="icon-remove" plain="true" >删除学校</a>&nbsp;-->
 		</div>
 	</div>
    <table id="schoolList"></table>
    <div style="display: none;">
	    <div id="addSchoolDiv" title="" style="width: 350px; height: 310px;">
	 		<table width="100%" border="0" cellpadding="0" cellspacing="1">
	 			<tr>
	 				<th width="100px" height="50">学校名称:</th>
	 				<td width="200px" height="50"><input type="text" id="addSchoolName"/>
	 					<input id="schoolId" value="" type="hidden"/>
	 				</td>
	 			</tr>
	 			<tr>
	 				<th height="40">学校地址</th>
	 				<td height="40">&nbsp;</td>
	 			</tr>
	 			<tr>
	 				<th height="40">省:</th>
	 				<td height="40"><select id="sheng" name="sheng" style="width: 150px;">
						</select>
					</td>
	 			</tr>
	 			<tr>
	 				<th height="40">市:</th>
	 				<td height="40"><select id="shi" name="shi" style="width: 150px;">
						</select>
					</td>
	 			</tr>
	 			<tr>
	 				<th height="40">县:</th>
	 				<td height="40"><select id="qu" name="quId" style="width: 150px;">
						</select>
					</td>
	 			</tr>
	 			<tr>
	 				<td colspan="2" align="center" height="50">
	 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addSchool();">保存</a>
	 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeAddSchoolDiv();">关闭</a>
	 				</td>
	 			</tr>
	 		</table>
		 </div>
	</div>
  </body>
</html>
