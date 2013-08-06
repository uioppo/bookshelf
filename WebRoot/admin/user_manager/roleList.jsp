<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
  <head>
    <title>角色权限</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/icon.css">
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/user_manager/js/roleList.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			loadRoleList();
		});
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
 	<div id="rolelist_bar">
 		<a href="javascript:;" onclick="add_Role()" class="easyui-linkbutton" icon="icon-add" plain="true" >添加角色</a>&nbsp;
 		<!--<a href="javascript:;" onclick="deleteRole()" class="easyui-linkbutton" icon="icon-remove" plain="true" >删除角色</a>&nbsp;
--><!--  		<a href="javascript:;" onclick="editRole()" class="easyui-linkbutton" icon="icon-edit" plain="true" >编辑角色</a>&nbsp;&nbsp;&nbsp; -->
 	</div>
    <table id="roleList"></table>
    <div style="display: none;">
	    <div id="addRoleDiv" title="添加角色" style="width: 300px; height: 220px;padding: 10px;">
		 		<table width="100%" border="0" cellpadding="0" cellspacing="1">
		 			<tr>
		 				<td width="100px" height="50">角色名称:</td>
		 				<td width="200px" height="50"><input type="text" id="addRoleNameInput"/></td>
		 			</tr>
		 			<tr>
		 				<td height="50">描述信息:</td>
		 				<td height="50"><input type="text" id="addRoleDescriptionInput"/></td>
		 			</tr>
		 			<tr>
		 				<td colspan="2" align="center" height="50">
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="insertIntoRole();">保存</a>
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeAddRoleDiv();">取消</a>
		 				</td>
		 			</tr>
		 		</table>
		 	</div>
		 	
		 	<div id="editRoleDiv" title="编辑角色" style="width: 300px; height: 220px; padding: 10px;">
		 		<input id="editUserRoleId" type="hidden"/>
		 		<table width="100%" border="0" cellpadding="0" cellspacing="1">
		 			<tr>
		 				<td width="100px" height="50">角色名称:</td>
		 				<td width="200px" height="50"><input type="text" id="editRoleName"/></td>
		 			</tr>
		 			<tr>
		 				<td height="50">描述信息:</td>
		 				<td height="50"><input type="text" id="editRoleDescription"/></td>
		 			</tr>
		 			<tr>
		 				<td colspan="2" align="center" height="50">
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateRole();">保存</a>
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeEditRoleDiv();">取消</a>
		 				</td>
		 			</tr>
		 		</table>
		 	</div>
		 	<div id="editFunction" title="权限管理" style="width: auto;height: auto;top: 30px;">
			 	<input type="hidden" id="roleFunction_R"/>
			 	<table id="funTable">
	    		</table>
    		</div>
	 	</div>
  </body>
</html>
