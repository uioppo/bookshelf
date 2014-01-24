<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
  <head>
    <title>后台用户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/icon.css">
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/user_manager/js/userList.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			loadUserList();
		});
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
 	<div id="userlist_bar">
 		<a href="javascript:;" onclick="add_user()" class="easyui-linkbutton" icon="icon-add" plain="true" >添加用户</a>&nbsp;
 		<!--<a href="javascript:;" onclick="deleteUser()" class="easyui-linkbutton" icon="icon-remove" plain="true" >删除用户</a>&nbsp;
 		--><!--<a href="javascript:;" onclick="editUser()" class="easyui-linkbutton" icon="icon-edit" plain="true" >编辑用户</a>&nbsp;&nbsp;&nbsp;
 		--><input id="ss" class="easyui-searchbox" searcher="searcher" prompt="请输入搜索内容..." menu="#mm" style="width:300px"></input>&nbsp;&nbsp;
		<div id="mm" style="width:120px">
			<div name="userName_">用户名</div>
			<div name="realName_">真实姓名</div>
		</div>
 	</div>
    <table id="userList"></table>
    <div style="display: none;">
	    <div id="addUserDiv" title="添加用户" style="width: 300px; height: 220px; padding: 10px;">
		 		<table width="200px" border="0" cellpadding="0" cellspacing="1">
		 			<tr>
		 				<td width="100px" height="25">用&nbsp;户&nbsp;名&nbsp;&nbsp;</td>
		 				<td width="200px" height="25"><input type="text" id="addUserNameInput"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">密&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;</td>
		 				<td height="25"><input type="password" id="addUserPasswordInput"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">邮&nbsp;&nbsp;&nbsp;&nbsp;箱&nbsp;&nbsp;</td>
		 				<td height="25"><input type="text" id="addEmailInput"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">真实姓名</td>
		 				<td height="25"><input type="text" id="addRealNameInput"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">角&nbsp;&nbsp;&nbsp;&nbsp;色</td>
		 				<td height="25"><select id="roleIdAddUserSelect"></select></td>
		 			</tr>
		 			<tr>
		 				<td colspan="2" align="center" height="25">
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="insertIntoUser();">保存</a>
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeAddUserDiv();">取消</a>
		 				</td>
		 			</tr>
		 		</table>
		 	</div>
		 	
		 	<div id="editUserDiv" title="编辑用户" style="width: 300px; height: 220px; padding: 10px;">
		 		<input id="editUserName" type="hidden"/>
		 		<input id="editUserId" type="hidden"/>
		 		<input id="editUserStatus" type="hidden"/>
		 		<input id="editUserTime" type="hidden"/>
		 		<input id="passwordE" type="hidden"/>
		 		<table width="250px" border="0" cellpadding="0" cellspacing="1">
		 			<tr>
		 				<td height="25">新&nbsp;密&nbsp;码&nbsp;&nbsp;</td>
		 				<td height="25"><input type="password" id="newPassword"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">确认密码&nbsp;&nbsp;</td>
		 				<td height="25"><input type="password" id="newPassword2"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">邮&nbsp;&nbsp;&nbsp;&nbsp;箱&nbsp;&nbsp;</td>
		 				<td height="25"><input type="text" id="editEmailInput"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">真实姓名&nbsp;&nbsp;</td>
		 				<td height="25"><input type="text" id="editRealNameInput"/></td>
		 			</tr>
		 			<tr>
		 				<td height="25">角&nbsp;&nbsp;&nbsp;&nbsp;色&nbsp;&nbsp;</td>
		 				<td height="25"><select id="roleIdEditUserSelect" ></select></td>
		 			</tr>
		 			<tr>
		 				<td colspan="2" align="center" height="25">
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateUser();">保存</a>
		 					<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeEditUserDiv();">取消</a>
		 				</td>
		 			</tr>
		 		</table>
		 	</div>
	 	</div>
  </body>
</html>
