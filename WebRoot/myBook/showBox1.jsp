<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title>我的电子书列表</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
	<script src="<%=path %>/js/more.js" type="text/javascript"></script>
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
  	<script type="text/javascript">
  		function addMyBooksByKey(){
  		$("#sub_button1").attr("disabled",true);
  		var productId=parent.getProductId();
  			var key=$("#licenseKey").val();
  			if(key!=null&&key!=""){
  				$.ajax({
  					url:path+"/mybook/activeBookByKey.action",
  					data:"licenseKey="+key+"&productId="+productId+"&math="+Math.random(),
  					success:function(data){
  						if(data.msg==""){
  							parent.keyboxclose(1);
  						}else{
  							$("#msg").text(data.msg);
  							$("#sub_button1").attr("disabled",false);
  						}
  					}
  				});
  			}else{
  				$("#msg").text("请输入授权码！");
  				$("#sub_button1").attr("disabled",false);
  			}
  		}
  		function showmore()
		{
			var obj=document.getElementById("menumore");
			if(obj.style.display == "block")
			{
			obj.style.display="none";
			}else
			{
			obj.style.display="block";
			}
			
		}
  	</script>
</head>
<body style="background:none; padding: 0px; margin: 0px;">
<!--弹出框-->

  <div id="code_top">
    <p class="code_top01"></p>
    <p class="code_top02">授权码<input id="close" style="borde:none;width:18px;height:18px;" name="close" type="image" src="<%=path %>/images/close.gif" onclick="parent.keyboxclose(0)" /></p>
    <p class="code_top03"></p>
  </div>
  <div id="code_con">
    <ul>
      <li class="account_tit">授权码</li>
      <li class="account_input">
        <input id="licenseKey" name="code_input" type="text" />
        <span>
        <input id="sub_button1" onclick="addMyBooksByKey()" name="code_button" type="image" src="<%=path %>/images/conde_submit.gif" />
        </span>
        <span id="msg"></span>
        </li>
      <li class="code_tit"><a href="<%=path %>/help/helpLicenseKey.html" target="_blank">什么是授权码？</a></li>
    </ul>
  </div>
</body>
</html>
