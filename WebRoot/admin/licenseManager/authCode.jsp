<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%String path = request.getContextPath();%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>授权码搜索</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" href="<%=path %>/admin/js/themes/icon.css" type="text/css"></link>
  	<link rel="stylesheet" href="<%=path %>/admin/js/themes/gray/easyui.css" type="text/css"></link>
  	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/product/css/main.css">
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
  	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
  	<script type="text/javascript">
  		function loadAuthCode(){
  			$.ajax({
		   		type: "POST",
		   		url: path+"/license/loadByAuthCode.action",
		   		data: "authCode="+$("#authCode").val()+"&math="+Math.random(),
		   		success: function(data){
			   		$("#batchList").html("");
				   if(data  != null){
				   	   var str = "<tr>";
				  	  str += "<td class='lab'>商品名称</td><td>" + data.bsProductses[0].productName + "</td>";
				  	   if(data.bsWebUser != null){
					   		str += "<td class='lab'>使用者</td><td>" + data.bsWebUser.wuEmail + "</td>";
					   }else{
					   		str += "<td class='lab' colspan='2' align='center'>该验证码未使用</td>";
					   }
					   str += "</tr>";
					   $("#batchList").append(str);
					   
					   if(data.bsLicenseBatch.schoolStage == 3){
					   	  str = "高中";
					   }else if(data.bsLicenseBatch.schoolStage == 2){
					   	  str = "初中";
					   }else {
					   	  str = "小学";
					   }
					   
					   $("#batchList").append("<tr><td class='lab'>批次</td><td>" + data.bsLicenseBatch.batchId + "</td><td class='lab'>学段</td><td>" + str + "</td></tr>");
					   $("#batchList").append("<tr><td class='lab'>学科</td><td>" + data.bsProductses[0].subject + "</td><td class='lab'>册</td><td>" + data.bsProductses[0].volume + "</td></tr>");
					   $("#batchList").append("<tr><td class='lab'>截止使用时间</td><td>" + data.lifeTime_ + "</td><td class='lab'>序列号</td><td>" + data.serialNum + "</td></tr>");
					   if(data.bsProductses[0].productType == 1){
					   	  str = "数字教材" ;
					   }else{
					   	  str = "数字教辅";
					   }
					   $("#batchList").append("<tr><td class='lab'>产品类型</td><td>" + str + "</td><td class='lab'>出版社</td><td>" + data.bsProductses[0].publishing + "</td></tr>");
					   $("#batchList").append("<tr><td class='lab'>语种</td><td>" + data.bsProductses[0].language + "</td><td class='lab'>电子书编码</td><td>" + data.bsProductses[0].bookCode + "</td></tr>");
					  	
				   }else{
					   $("#batchList").html("该授权码不存在！");
				   }
		   		}
			});
  		}
  	</script>
  	<style type="text/css">
		#cart_total{margin-top: 10px;}
		#batchList td { 
		border-right: 1px solid #fff; 
		border-bottom: 1px solid #fff; 
		background: #fff; 
		font-size:13px; 
		padding: 6px 6px 6px 12px; 
		color: #4f6b72; 
		font-family: 宋体, Arial; 
		} 
  	</style>
  </head>
  
  <body style="margin: 0px; padding: 0px; background-color: #fff;">
  		<div style="padding:10px; margin-left: auto; margin-right: auto; width: 820px;">
	  <div id="tb" style="padding:5px;height:30px">
		授权码：<input id="authCode" name="authCode" />&nbsp;
		<a href="javascript:loadAuthCode();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
	  </div>
	  
	  <form class="addspeaker_form">
	  <table id="batchList" ></table>
	  </form>
	  </div>
  </body>
</html>
