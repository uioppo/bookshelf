<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>科目列表</title>
   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<script type="text/javascript">var path='<%=path %>';</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=path %>/admin/dictionaryManager/subjectDictionary/subject.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			findAllSubject();
		});
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
  		<div id="top">
  			<a href="javascript:void(0);" onclick="addDictionary()" modal="true" class="easyui-linkbutton" iconCls="icon-add" plain="true">增加科目</a>
  		</div>
    	<table id="main"></table>
    	<div style="display: none;">
    		<div id="add" align="center" style="width: 300px;height: 200px;vertical-align: middle;">
		    		<table style="font-size: 10pt;">
			    		<tr>
			    			<th>科目编号:</th>
			    			<td><input class="easyui-validatebox" validType="length[1,20]" type="text" id="dicCode"/></td>
			    		</tr>
			    		<tr>
			    			<th>科目名称:</th>
			    			<th><input class="easyui-validatebox" required="true" validType="length[1,50]" type="text" id="addname"/></th>
			    		</tr>
			    		<tr>
			    			<th>学段:</th>
			    			<td>
			    				<select id="addschoolStage">
			    					<option value="-1">--请选择学段--</option>
			    					<option value="1">小学</option> 
			    					<option value="2">初中</option>
			    					<option value="3">高中</option>
			    				</select>
			    			</td>
			    		</tr>
			    		<tr>
			    			<td colspan="2" align="center">
			    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="addSubmit()">保存</a>&nbsp;
			    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeAddDiv()">取消</a>
			    			</td>
			    		</tr>
		    	</table>
    		</div>
    		
    		<div id="edit" align="center">
	    	<form action="" method="post" id="editDictionaryForm">
	    		<table style="font-size: 10pt">
		    		<tr>
		    			<th>科目编号:</th>
		    			<td>
		    				<input type="hidden" value="" id="editId"/>
		    				<input type="text" name="dicCode" id="editDicCode" class="easyui-validatebox" validType="length[1,20]">
		    			</td>
		    		</tr>
		    		<tr>
		    			<th>科目名称:</th>
		    			<td><input type="text"  id="editDicName" class="easyui-validatebox" required="true" validType="length[1,50]"/></td>
		    		</tr>
		    		<tr>
		    			<th>学段:</th>
		    			<td>
		    				<select id="editSchoolStage">
		    					<option value="-1">--请选择学段--</option>
		    					<option value="1">小学</option>
		    					<option value="2">初中</option>
		    					<option value="3">高中</option>
		    				</select>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td colspan="2" align="center">
		    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="editSubmit()">保存</a>&nbsp;
			    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeEditDiv()">取消</a>
		    			</td>
		    		</tr>
	    	</table>
	    	</form>
    	</div>
   </div>
  </body>
</html>
