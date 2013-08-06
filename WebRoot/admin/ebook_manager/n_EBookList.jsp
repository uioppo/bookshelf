<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
  <head>
    <title>待审核电子书列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/icon.css">
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/ebook_manager/js/n_eBookList.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			var s = "<%=request.getParameter("sj") %>";
			loadNEBookList(s);
		});
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
 	<div id="nEbooklist_bar">
 		<input id="ss" class="easyui-searchbox" searcher="searcher" prompt="请输入搜索内容..." menu="#mm" style="width:300px"></input>&nbsp;&nbsp;
		<div id="mm" style="width:120px">
			<div name="bookName_">电子书名称</div>
		</div>
 	</div>
    <table id="nEBookList"></table>
    <div style="display: none;">
    	<div id="advice" title="审核" style="width: 500px; height: 200px; padding: 10px;" data-options="iconCls:'icon-manager',modal:true">
    		<input type="hidden" id="bookIdHidden"/>
    		<div style="width: 300px;">
    			<span id="eBookName" style="width:200px; font-weight: bold;"></span>
    			<!--  a href="javascript:void(0);" onclick="showBook();" class="easyui-linkbutton" icon="icon-search">查看</a-->
    		</div>
    		<table style="width: 100%;" >
     			<tr>
		 			<td width="100" height="25" align="right">学段&nbsp;</td>
		 			<td height="25">
			 			<input id="schoolStage" type="text" readonly="readonly" size="23"/>
		 			</td>
		 			<td width="100" height="25" align="right">年级&nbsp;</td>
		 			<td height="25">
		 				<input id="grade" type="text" readonly="readonly" size="23"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">科目&nbsp;</td>
		 			<td width="200" height="25">
						<input id="subject" type="text" readonly="readonly" size="23" />
					</td>
					<td width="100" height="25" align="right">册&nbsp;</td>
		 			<td width="200" height="25">
						<input id="volume" type="text" readonly="readonly" size="23"/>
					</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">适用对象</td>
		 			<td width="200" height="25">
			 			<input id="applyObject" type="text" readonly="readonly" size="23"/>
		 			</td>
		 			<td width="100" height="25" align="right">版本&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="edition" type="text" readonly="readonly" size="23"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">商品类型&nbsp;</td>
		 			<td width="200" height="25">
			 			<input id="productType" type="text" readonly="readonly" size="23">
		 			</td>
		 			<td width="100" height="25" align="right">语种&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="language" type="text" readonly="readonly" size="23"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">出版社&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="publishing" type="text"  readonly="readonly" size="23"/>
		 			</td>
		 			<td width="100" height="25" align="right">对应教材版本&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="teachVersion" type="text" readonly="readonly" size="23"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">开发者&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="author" type="text" readonly="readonly" size="23"/>
		 			</td>
		 			<td width="100" height="25" align="right">页数</td>
		 			<td width="200" height="25">
		 				<input id="pageNumber" type="text" readonly="readonly" size="23"/>
		 			</td>
		 		</tr>
		 		<tr>
		 		<td width="100" height="25" align="right">审批意见</td>
		 		<td height="25" colspan="3">
		 			<textarea id="adviceIdea" rows="4" style="width: 475px;hight:34px;"></textarea>
		 		</td>
		 		</tr>
     		</table>	
    		
    		
    		<div style="">
    			
    		</div>
    		<div style="width: 300px;float: right;margin-right: 110px;">
<!--    			<a href="javascript:void(0);" onclick="adviceStatus(1);" class="easyui-linkbutton" icon="icon-ok">审核通过</a>-->
<!--    			<a href="javascript:void(0);" onclick="adviceStatus(2);" class="easyui-linkbutton" icon="icon-no">审核退回</a>-->
<!--    			<a href="javascript:void(0);" onclick="closeDiv_();" class="easyui-linkbutton" icon="icon-cancel">取消</a>-->
    		</div>
    	</div>
    </div>
  </body>
</html>
