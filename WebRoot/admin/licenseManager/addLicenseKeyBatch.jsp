<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>

<!doctype html>
<html>
  <head>
    <title>添加授权码</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/licenseManager/js/addBatch.js"></script>
  	<script type="text/javascript">
  		$(document).ready(function(){
			$("#stepTwo").show();
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
			$.ajax({
				type:"post",
				url: '<%=path %>/admin/dictionary/findByType.action?dd='+Math.random(),
				data:"type=4",
				success:function(data) {					
					 $("#serVersion").html("");
			   		 $(data.rows).each(function(){
				   		$("#serVersion").append("<option value="+this.dicCode+">" + this.name+ "</option>");
			   		});
				}
			});
	  	});
  	</script>
  	<style type="text/css">
  		.lab{
			font-weight: bold;
			font-size:12px;
			text-align: right;
			padding-right: 20px;
			background-color: #F3F7F9;
		}
	form input {
		border: 1px #AFAFAF solid; width: 520px; line-height: 25px; height: 26px;
	}
	form select {
		border: 1px #AFAFAF solid; width: 520px; height: 30px;
	}
	form table {
		background-color: #E9EDF1;
	}
	form table td{
		background-color: #fff;
	}
	
  	</style>
  </head>
  
  	<body style="margin: 0px; padding: 0px; background-color: #fff;">
  	<div id="stepTwo">
  		<form id="keyForm" method="post" action="<%=path %>/license/addLicenseKeyBatch.action">
  		<input id="idStr" name="idStr" type="hidden"/>
  			<table border="0" cellpadding="4" cellspacing="1">
  				<tr>
  					<th class="lab" width="200"><strong>地区</strong></th>
  					<td width="558px">
  						省：<select id="sheng" name="sheng" style="width: 130px;">
						</select>
						市：<select id="shi" name="shi" style="width: 130px;">
						</select>
						县：<select id="qu" name="qu" style="width: 150px;">
						</select>
						<input type="hidden" name="quId" id="quId"/>
  					</td>
  				</tr>
  				<tr>
  					<th class="lab"><strong>学校</strong></th>
  					<td>
  						<select name="schoolId" id="school">
  						</select>
  					</td>
  				</tr>
  				<tr>
  					<th class="lab"><strong>学段</strong></th>
  					<td>
  						<select name="schoolStage">
  							<option value="1" selected="selected">小学</option>
  							<option value="2">初中</option>
  							<option value="3">高中</option>
  						</select>
  					</td>
  				</tr>
  				<tr>
  					<th class="lab">版本</th>
					<td>
						<select id="serVersion" name="serVersion"></select>
					</td>
  				</tr>
  				<tr>
  					<th class="lab"><strong>销售渠道</strong></th>
  					<td>
  						<select name="pattern" id="pattern_sel">
  						</select>
  					</td>
  				</tr>
  				<tr>
  					<th class="lab"><strong>销售目标</strong></th>
  					<td>
  						<select name="pattern_target" id="pattern_target">
  							<option value="1">教育局</option>
  							<option value="2">学校</option>
  							<option value="3">培训机构</option>
  						</select>
  					</td>
  				</tr>
  				<tr>
  					<th class="lab"><strong>授权码数量</strong></th>
  					<td>
  						<input name="keySize" id="keySize" class="easyui-numberbox" style="width: 513px" data-options="min:1,max:20000,precision:0,required:true"/>
  					</td>
  				</tr>
  				<tr>
  					<th class="lab"><strong>截止使用日期</strong></th>
  					<td>
  						<input id="lifetime1" type="text" name="lifetime1" class="easyui-datebox" editable=false/>
  						<input id="lifeTime" name="lifeTime" type="hidden"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="lab"><a href="javascript:selectBook();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">选择商品</a></td>
  					<td class="lab" id="selectbook" style="text-align: left;">&nbsp;</td>
  				</tr>
  				<tr>
  					<td class="lab">&nbsp;</td>
  					<td class="lab"><a href="javascript:submitForm();" class="easyui-linkbutton" data-options="iconCls:'icon-save'">生成授权码</a></td>
  				</tr>
  			</table>
  		</form>
  	</div>
  	<div style="display: none;">
  	<div id="stepOne" >
	  <div id="tb" style="padding:2px 0 10px 5px;height:20px">
		商品名称：<input id="productName" name="productName"/>
		<a href="javascript:search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	  </div>
	  	<table id="dategrid1">
	  	</table>
	</div>
	<div id="showbox" style="padding: 10px;"><img alt="" src="../js/themes/default/images/pagination_loading.gif">正在生成授权码，请不要关闭窗口...</div>
	</div>
  </body>
</html>
