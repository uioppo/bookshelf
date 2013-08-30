<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>

<!doctype html>
<html>
  <head>
    <title>编辑web用户信息</title>
    
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
	<script type="text/javascript">
	$(document).ready(function (){
		sheng();
		$("#sheng").change(function(){
			shi();
		});
		$("#shi").change(function(){
			qu();
		});
		$("#qu").change(function(){
			school();
		});
		initarea();
	});
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
  <form id="webUserForm" action="<%=path %>/webuser/updateWebUser.action" method="post">
  <input name="bsWebUser.wuId" value="${bsWebUser.wuId }" type="hidden"/>
  <input name="bsWebUser.bsUserInfo.id" value="${bsWebUser.bsUserInfo.id }" type="hidden"/>
  	<table id="table">
  		<tr>
  			<td class="lab" width="150px;">用户名：</td>
  			<td style="background-color: #fff;" width="550px">${bsWebUser.wuEmail}</td>
  		</tr>
  		<tr>
  			<td class="lab">姓名：</td>
  			<td style="background-color: #fff;">
  				<input name="bsWebUser.bsUserInfo.realName" value="${bsWebUser.bsUserInfo.realName}" maxlength="40"/>
  			</td>
  		</tr>
  		<tr>
  			<td class="lab">所在地：</td>
  			<td style="background-color: #fff;">
  				省：<select id="sheng" name="bsWebUser.bsUserInfo.sheng" style="width: 130px;">
				</select>
				<input id="sheng1" value="${bsWebUser.bsUserInfo.sheng }" type="hidden"/>
				市：<select id="shi" name="bsWebUser.bsUserInfo.shi" style="width: 130px;">
				</select>
				<input id="shi1" value="${bsWebUser.bsUserInfo.shi }" type="hidden"/>
				县：<select id="qu" name="bsWebUser.bsUserInfo.xian" style="width: 130px;">
				</select>
				<input id="qu1" value="${bsWebUser.bsUserInfo.xian }" type="hidden"/>
  			</td>
  		</tr>
  		<tr>
  			<td class="lab" width="200px;">学校：</td>
  			<td style="background-color: #fff;" width="200px">
  				<select name="bsWebUser.bsUserInfo.school" id="school">
  				</select>
  				<input id="school1" value="${bsWebUser.bsUserInfo.school }" type="hidden"/>
  			</td>
  		</tr>
  		<tr>
  			<td class="lab" width="200px;">昵称：</td>
  			<td style="background-color: #fff;" width="200px">
  				<input name="bsWebUser.bsUserInfo.nickName" value="${bsWebUser.bsUserInfo.nickName }" maxlength="30"/>
  			</td>
  		</tr>
  		<tr>
  			<td class="lab" width="200px;">性别：</td>
  			<td style="background-color: #fff;" width="200px">
  				<input name="bsWebUser.bsUserInfo.sex" type="radio" value="3" checked/>保密
  				<input name="bsWebUser.bsUserInfo.sex" type="radio" value="1" 
  				<s:if test="bsWebUser.bsUserInfo.sex==1">checked</s:if>
  				/>男
  				<input name="bsWebUser.bsUserInfo.sex" type="radio" value="2" 
  				<s:if test="bsWebUser.bsUserInfo.sex==2">checked</s:if>
  				/>女
  			</td>
  		</tr>
  		<tr>
  			<td class="lab" width="200px;">联系电话：</td>
  			<td style="background-color: #fff;" width="200px">
  				<input id="mobile" name="bsWebUser.bsUserInfo.mobile" value="${bsWebUser.bsUserInfo.mobile }" maxlength="14"/>
  				<span id="mobileMessage"></span>
  			</td>
  		</tr>
  		<!--<tr>
  			<td class="lab" width="200px;">年龄：</td>
  			<td style="background-color: #fff;" width="200px">
  				<input id="age" class="easyui-numberbox" name="bsWebUser.bsUserInfo.age" value="${bsWebUser.bsUserInfo.age }" maxlength="11"/>
  				<span id="ageMessage"></span>
  			</td>
  		</tr>
  		--><tr>
  			<td class="lab" width="200px;">出生日期：</td>
  			<td style="background-color: #fff;" width="200px">
  				<input id="lifetime1" type="text" name="lifetime1" class="easyui-datebox" value="${bsWebUser.bsUserInfo.birthdayStr}" editable=false/>
  				<input id="lifeTime" name="bsWebUser.bsUserInfo.birthday" value="${bsWebUser.bsUserInfo.birthday }" type="hidden"/>
  			</td>
  		</tr>
  	</table>
  	<br/>
  	<a href="<%=path %>/admin/webuser_manager/webUserList.jsp" class="easyui-linkbutton" data-options="iconCls:'icon-back'">返回</a>&nbsp;&nbsp;
  	<a href="javascript:webUserFormSub();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
  	</form>
  </body>
</html>
