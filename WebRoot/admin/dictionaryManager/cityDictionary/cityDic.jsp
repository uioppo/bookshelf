<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>地区列表</title>
	<meta http-equiv="expires" content="0">    
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<script type="text/javascript">var path='<%=path %>';</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path %>/admin/dictionaryManager/cityDictionary/city.js"></script>
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
			findAllCity();
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
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
  		<div id="top" style="padding:5px;height:auto">
	  		<div id="excel_div">
	 		<form action="/admin/area/importExcel.action" enctype="multipart/form-data" method="post"  onsubmit="return checkImport();">
			&nbsp;&nbsp;
			文件:       
			<input type="file" id="excel" name="excel" style="width:140px">
			<input type="submit" value="导入Excel" name="submit" /> 
	  		&nbsp;&nbsp;|<a href="/admin/area/exportExcel.action" class="easyui-linkbutton" icon="icon-redo" plain="true" >导出Excel</a>
	  		</form>
	  		</div>
	  		<div id="search">
			&nbsp;&nbsp;
			地区名称:       
			<input id="searchAreaName" style="width:140px">
			区号：
			<input id="searchAreaCode" style="width:140px">
			<a href="javascript:void(0);" onclick="javascript:search1();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	  		</div>
  			<div>
  			<a href="javascript:;" onclick="addArea()" modal="true" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加地区</a>
  			<div class="datagrid-btn-separator" style="float: left;"></div>
  			<a href="javascript:;" onclick="editArea()" modal="true" class="easyui-linkbutton" iconCls="icon-edit" plain="true">编辑地区</a>
  			<div class="datagrid-btn-separator"></div>
  			<a href="javascript:;" onclick="deleteArea()" modal="true" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除地区</a>
  			</div>
  		</div>
    	<table id="main" title="地区列表" class="easyui-treegrid" style="width:100%;height:100%"
			idField="id" treeField="name"
			pagination="false" fitColumns="true">
			<thead>
				<tr>
					<th field="name" width="50%" editor="text">地区名称</th>
					<th data-options="field:'areaCode',editor:'text'" width="50%" >区号</th>
				</tr>
			</thead>
		</table>
    	<div style="display: none;">
    		<div id="addArea" align="center" style="width: 300px;height: 150px;vertical-align: middle;">
    				<input type="hidden" id="areaId_"/>
		    		<table style="font-size: 10pt;">
			    		<tr>
			    			<td>地区名称:</td>
			    			<td><input type="text" id="addAreaName"/></td>
			    		</tr>
			    		<tr>
			    			<td>区&nbsp;&nbsp;&nbsp;&nbsp;号:</td>
			    			<td><input type="text" id="addAreaCode"/></td>
			    		</tr>
			    		<tr>
			    			<td colspan="2" align="center">
			    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="insertArea();">保存</a>&nbsp;
			    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeAddDiv()">取消</a>
			    			</td>
			    		</tr>
		    	</table>
    		</div>
    	
    		<div id="editArea" align="center" style="width: 300px;height: 150px;vertical-align: middle;">
    			<input type="hidden" id="editAreaId"/>
	    		<table style="font-size: 10pt">
		    		<tr>
		    			<th>地区名称:</th>
		    			<td>
		    				<input type="text" id="editAreaName"/>
		    			</td>
		    		</tr>
		    		<tr>
			    			<td>区&nbsp;&nbsp;&nbsp;&nbsp;号:</td>
			    			<td><input type="text" id="editAreaCode"/></td>
			    		</tr>
		    		<tr>
		    			<td colspan="2" align="center">
		    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateArea()">保存</a>&nbsp;
			    			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeEditDiv()">取消</a>
		    			</td>
		    		</tr>
	    	</table>
    	</div>
   </div>
  </body>
</html>
