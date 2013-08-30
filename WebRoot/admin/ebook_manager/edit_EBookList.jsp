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
	<script type="text/javascript" src="<%=path %>/admin/ebook_manager/js/edit_eBookList.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			loadNEBookList(3);
			$('#schoolStage').combobox({
				onSelect:mid
			});
			loadGradeSub($("#schoolStage").combobox('getValue'));
			var year= new Date().getFullYear();
			var str = [];
			for(var i = -5 ; i <= 5;i++){
				str[i+5] ={"year":(parseInt(year)+i)};
			}
			$("#version").combobox({
				data:str,
				valueField:'year',
				textField:'year'
			});
			$('#version').combobox('setValue',str[5].year);
			$.ajax({
				type:"post",
				url: '../dictionary/findByType.action?dd='+Math.random(),
				data:"type=5",
				success:function(json) {
					$("#publishing").combobox({
						data:json.rows,
						valueField:'dicCode',
						textField:'name'
					});
					if(json.rows != null && json.rows.length > 0){
						$('#publishing').combobox('setValue',"1");
					}
				}
			});
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
    	<div id="advice" title="编辑属性" style="width: 500px; height: 200px; padding: 10px;" data-options="iconCls:'icon-edit',modal:true">
    		<div style="width: 300px;">
    			<span id="eBookName" style="width:200px; font-weight: bold;"></span>
    			<a href="javascript:void(0);" onclick="showBook();" class="easyui-linkbutton" icon="icon-search">查看</a>
    		</div>
    		<table style="width: 100%;" >
     			<tr>
		 			<td width="100" height="25" align="right">学段&nbsp;</td>
		 			<td height="25">
		 				<input type="hidden" id="bookIdHidden"/>
		 				<input type="hidden" id="gCode"/>
			 			<select id="schoolStage" panelHeight="auto" style="width:140px;" editable=false class="easyui-combobox">
			 				<option value="1">小学</option>
							<option value="2" selected="selected">初中</option>
							<option value="3">高中</option>
			 			</select>
		 			</td>
		 			<td width="100" height="25" align="right">年级&nbsp;</td>
		 			<td height="25">
		 				<select id="gradeCode" class="easyui-combobox" panelHeight="auto" editable=false style="width:140px;"></select>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">科目&nbsp;</td>
		 			<td width="200" height="25">
						<select id="subject" class="easyui-combobox" panelHeight="auto" editable=false style="width:140px;"></select>
					</td>
					<td width="100" height="25" align="right">册&nbsp;</td>
		 			<td width="200" height="25">
						<select id="volume" style="width: 140px;" class="easyui-combobox" panelHeight="auto" editable=false>
							<option value="上册">上册</option>
							<option value="下册">下册</option>
							<option value="全册">全册</option>
						</select>
					</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">适用对象</td>
		 			<td width="200" height="25">
			 			<select id="applyObject" panelHeight="auto" style="width:140px;" editable=false class="easyui-combobox">
			 				<option value="学生">学生</option>
							<option value="教师" selected="selected">教师</option>
							<option value="教师、学生">教师、学生</option>
			 			</select>
		 			</td>
		 			<td width="100" height="25" align="right">版本&nbsp;</td>
		 			<td width="200" height="25">
		 				<select id="version" panelHeight="auto" style="width:140px;" editable=false class="easyui-combobox"></select>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">商品类型&nbsp;</td>
		 			<td width="200" height="25">
			 			<select id="productType" name="bsProducts.productType" style="width: 140px;background-color: #ffffff;" class="easyui-combobox" panelHeight="auto" editable=false>
							<option value="0">数字教辅</option>
							<option value="1" selected="selected">数字教材</option>
						</select>
		 			</td>
		 			<td width="100" height="25" align="right">出版社&nbsp;</td>
		 			<td width="200" height="25">
		 				<select id="publishing" panelHeight="auto" style="width:140px;" editable=false class="easyui-combobox"></select>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">语种&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="language" type="text" class="easyui-validatebox" style="width:160px" validType="length[1,20]" required="true"/>
		 			</td>
		 			<td width="100" height="25" align="right">对应教材版本&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="teachVersion" type="text" class="easyui-validatebox"  style="width:160px" required="true"/>
		 			</td>
		 		</tr>
		 		<tr>
		 			<td width="100" height="25" align="right">开发者&nbsp;</td>
		 			<td width="200" height="25">
		 				<input id="author" type="text" class="easyui-validatebox" style="width:160px" validType="length[1,20]" />
		 			</td>
		 			<td width="100" height="25" align="right">页数</td>
		 			<td width="200" height="25">
		 				<input id="pageNumber" class="easyui-numberbox" data-options="min:1,precision:0,required:true"/>
		 			</td>
		 		</tr>
     		</table>	
    	</div>
    </div>
  </body>
</html>
