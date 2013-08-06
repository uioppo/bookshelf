<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%
String path = request.getContextPath();
String fileDomain=DomainUtil.getFileDomain();
%>
<!doctype html>
<html>
  <head>
    <title>新书推荐</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/newBookSet/newBook.css" type="text/css"></link>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript">var fileDomain="<%=fileDomain%>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/newBookSet/newBook.js"></script>
  	<script type="text/javascript" src="js/slides.min.jquery.js"></script>
  	<script type="text/javascript">
  		$.ajaxSetup ({ 
		     cache: false //关闭AJAX相应的缓存 
		 });
  	</script>
  </head>
  	<body style="margin: 0px; padding: 0px; background-color: #fff;">
  	<div id="new1" style="display: none">
  	</div>
	<div class="index_con01" style="height:350px;padding: 10px;">
		<div id="index_new_rec_" style="background-color: #F4F4F4">
			<div id="new_rec_tit_">
				<p class="rec_title">
				</p>
				<p id="id1" class="rec02" onclick="selectEbook(1)">
					数字教材
				</p>
				<p id="id0" class="rec01" onclick="selectEbook(0)">
					数字教辅
				</p>
			</div>
			<div class="book_link" id="new_rec_con">
				<div class="new_rec_content" id="newRecContent1">
				</div>
				<div class="new_rec_content" id="newRecContent0" style="display: none">
				</div>
			</div>
		</div>
	</div>
	<div style="background:#efefef;border:1px solid #ccc;padding:5px;height:20px;float: left;width: 100%;">
	<a href="javascript:removeRow();" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" >清空</a>&nbsp;&nbsp;
		<a href="javascript:save1();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="save1">保存教材</a>&nbsp;&nbsp;&nbsp;
		<a href="javascript:save1();" class="easyui-linkbutton" data-options="iconCls:'icon-save'" id="save0" style="display: none;">保存教辅</a>&nbsp;&nbsp;&nbsp;
	</div>
	
	<div id="showbox">
	  <div id="tb" style="padding:0 0 0 0;height:28px">
		商品名称：<input id="productName" name="productName"/>
		<a href="javascript:search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>&nbsp;&nbsp;&nbsp;
	  </div>
  	<table id="dategrid1">
  	</table>
  </div>
  </body>
</html>
