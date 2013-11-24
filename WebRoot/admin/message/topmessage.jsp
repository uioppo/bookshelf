<%@page import="java.io.FileInputStream"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
ServletContext sc = config.getServletContext();
Properties prop = new Properties();//属性集合对象    
prop.load(sc.getResourceAsStream("/WEB-INF/topmessage.properties"));
%>
<!doctype html>
<html>
  <head>
    <title>消息提示设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/message/topmessage.js"></script>
  	<script type="text/javascript">
  		$.ajaxSetup ({ 
		     cache: false //关闭AJAX相应的缓存 
		 });
  	</script>
  	<style type="text/css">
  	.lab {
		font-weight: bold;
		font-size: 12px;
		text-align: center;
		background-color: #F3F7F9;
		}
	td {
		background-color: white;
		text-align: center;
		}
	table {
		background-color: #E9EDF1;
		}
	</style>
  </head>
  <body style="background-color: #fff">
  	<form id="theForm" action="<%=path %>/topMessage.action" method="post" class="addspeaker_form" enctype="multipart/form-data">
  	<div id="newbookdiv" style="display: none">
  	</div>
  	<input id="inputHidden1" type="hidden" name="newBook1"/>
  	<input id="inputHidden2" type="hidden" name="newBook2"/>
  	<table border="0" cellpadding="4" cellspacing="1">
  		<tr><th class="lab" width='250px'>文件</th><th class="lab" width='250px'>标题</th><th class="lab" width='250px'>链接</th><th class="lab" width='100px'>操作</th></tr>
  		<tbody id="tbody">
  		<%
  		if(prop!=null && !prop.isEmpty()){
  			
  			for(Object key:prop.keySet()){
  				String value = prop.getProperty(key.toString());
  				String[] elements = value.split(",");
  				if(!key.equals("version")){
  		%>
  			<tr>
				<td><img name="image" src="<%=elements[1] %>" /><input type="file" name="topmessage" /></td>
				<td><input name="topmessageTitle" value="<%=elements[0]%>" /></td>
				<td><input name="topmessageHref" value="<%=elements[2]%>" /></td>
				<td><a href="javascript:void(0);" onclick="delthis(this,'<%=key.toString() %>');" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a></td>
			</tr>
  		<%	
  				}
  			}
  			if(prop.getProperty("version")!=null){
  				String value = prop.getProperty("version");
  				String[] elements = value.split(",");
  		%>
  			<!-- tr>
				<td>版本号：</td>
				<td><input type="text" value="<%=elements[1]%>" name="versionName"  /> </td>
				<td><input type="text" value="<%=elements[0]%>" name="versionKey" disabled="disabled"  /></td>
				<td><input type="text" value="<%=elements[2]%>" name="versionHref"  /></td>
			</tr-->
  		<%			
  			}
  		}else{
  		%>
  			<tr>
				<td><input type="file" name="topmessage" /></td>
				<td><input name="topmessageTitle"/></td>
				<td><input name="topmessageHref"/></td>
				<td><a href="javascript:void(0);" onclick="delthis(this);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a></td>
			</tr>
			<tr>
				<td><input type="file" name="topmessage" /></td>
				<td><input name="topmessageTitle"/></td>
				<td><input name="topmessageHref"/></td>
				<td><a href="javascript:void(0);" onclick="delthis(this);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a></td>
			</tr>
			<!-- tr>
				<td>版本号：</td>
				<td><input type="text" name="versionName" /></td>
				<td><input type="text" value="" name="versionHref"  /></td>
				<td><a href="javascript:void(0);" onclick="delthis(this);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a></td>
			</tr-->
			<%} %>
  		</tbody>
  	</table>
  	<br/><br/>
  	<a href="javascript:void(0);" onclick="addone();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
  	<a href="javascript:saveSetting()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
  	<BR>
  	
  	</form>
  	<div style="padding: 5px; border: dashed 1px #ccc; margin-top: 20px;">
  		请选择图片尺寸为 1039 * 117 的图片进行上传；<br/>
  		图片格式为：*.jpg,*.png
  	</div>
  </body>
</html>
