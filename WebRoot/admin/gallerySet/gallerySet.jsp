<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
%>
<!doctype html>
<html>
  <head>
    <title>画廊设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/gallerySet/gallerySet.js"></script>
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
  	<form id="theForm" action="<%=path %>/gallerySet.action" method="post" class="addspeaker_form" enctype="multipart/form-data">
  	<div id="newbookdiv" style="display: none">
  	</div>
  	<input id="inputHidden1" type="hidden" name="newBook1"/>
  	<input id="inputHidden2" type="hidden" name="newBook2"/>
  	<table border="0" cellpadding="4" cellspacing="1">
  		<tr><th class="lab" width='250px'>文件</th><th class="lab" width='250px'>标题</th><th class="lab" width='250px'>链接</th><th class="lab" width='100px'>操作</th></tr>
  		<tbody id="tbody">
  			<tr>
				<td><input type="file" name="gallery" /></td>
				<td><input name="galleryTitle"/></td>
				<td><input name="galleryHref"/></td>
				<td><a href="javascript:void(0);" onclick="delthis(this);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a></td>
			</tr>
			<tr>
				<td><input type="file" name="gallery" /></td>
				<td><input name="galleryTitle"/></td>
				<td><input name="galleryHref"/></td>
				<td><a href="javascript:void(0);" onclick="delthis(this);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a></td>
			</tr>
			<tr>
				<td><input type="file" name="gallery" /></td>
				<td><input name="galleryTitle"/></td>
				<td><input name="galleryHref"/></td>
				<td><a href="javascript:void(0);" onclick="delthis(this);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-cancel'">删除</a></td>
			</tr>
  		</tbody>
  	</table>
  	<br/><br/>
  	<a href="javascript:void(0);" onclick="addone();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
  	<a href="javascript:saveSetting()" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
  	<BR>
  	
  	</form>
  	<div style="padding: 5px; border: dashed 1px #ccc; margin-top: 20px;">
  		请选择图片尺寸为 472 * 235 的图片进行上传；<br/>
  		图片格式为：*.jpg,*.png
  	</div>
  </body>
</html>
