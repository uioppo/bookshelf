<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%
String path = request.getContextPath();
%>

<!doctype html>
<html>
  <head>
    <title>保存商品</title>
	<meta http-equiv="pragma" content="no-cache">
  </head>
  
  <body style="padding-top: 150px;background-color: #FFFFFF">
   
   
   <s:if test="%{msg == null || msg == ''}">
    	无权操作
    </s:if>
    <s:else>
    	<h3 align="center"><s:property value="msg" /></h3>
    	<h5 align="center"><a href="<%=path%>/admin/product/<s:property value='backspace'/>">返回</a></h5>
    </s:else>
  </body>
</html>
