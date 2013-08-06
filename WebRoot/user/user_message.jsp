<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String fileDomain=DomainUtil.getFileDomain(); 
String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
  <head> 
    <title>我的帐户 - 人教云汉商城</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" href="<%=path %>/css/jquery-ui-1.9.1.custom.min.css">
	<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript">var fileDomain="<%=fileDomain %>";</script>
	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/js/user/addShopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
	<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
	<script src="<%=path %>/js/jquery-ui-1.9.1.custom.min.js"></script>
	<script src="<%=path %>/js/checkLogin.js"></script>
	<script type="text/javascript" src="<%=path%>/user/js/user_message.js"></script>
	<script type="text/javascript">
		var sheng ;
		var shi ;
		var xian;
		var school;
		$(function() {
		school='<s:property value="user.bsUserInfo.school"/>';
		sheng='<s:property value="user.bsUserInfo.sheng"/>';
		shi='<s:property value="user.bsUserInfo.shi"/>';
		xian='<s:property value="user.bsUserInfo.xian"/>';
		showarea();
		showplus(sheng);
		showcounty(shi);
	if(xian!=null&&xian!=-1&&xian!=""&&xian!=0){
		showSchool(xian);
	}else if(shi!=null&&shi!=-1&&shi!=""&&shi!=0){
		showSchool(shi);
	}
		$("#school").val(school);
		$("#mobile").blur(function() {
			checkmobile();
		});
		/*$("#age").blur(function() {
			checkAge();
		});*/
		var birdate=$( "#birthday" ).val();
		$( "#birthday" ).datepicker();
		$( "#birthday" ).datepicker( "option", "dateFormat","yy-mm-dd" );
		$( "#birthday" ).datepicker({ changeYear: true });
		var changeYear = $( "#birthday" ).datepicker( "option", "changeYear" ); 
		$( "#birthday" ).datepicker( "option", "changeYear", true );
		$( "#birthday" ).attr( "value",birdate );
	});
	
	</script>
</head>
<body>
<div id="container">
<div style="position:relative; z-index:0;" id="top_box">
  <div id="top_left"></div>
  <div id="top_right"></div>
  <div id="top" style="position:relative;z-index:1000;">
  	<div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path%>/index.html'"></div>
    <div class="top_tip"><span class="_top_tip"> <span class="top_style01">您好，欢迎来人教云汉商城！</span><a href="3">登录</a>|<a href="#">注册</a></span><span class="top_style02">购物车（0）件</span> </div>
    <div id="nav">
      <a href="<%=path %>/index.html">首页</a>|
      <a id="nav_current" href="<%=path%>/user/findUserName.action">我的账户</a>|
      <a href="<%=path %>/user/orderList.jsp">我的订单</a>|
      <a href="<%=path %>/myBook/myBookList.jsp">我的电子书</a>|
      <a href="<%=path %>/help/helpCenter.jsp">帮助中心</a>
    </div>
  </div>
</div>
<!--<div id="banner">
	<iframe src="<%=path%>/hot/banner.html" height="147px" width="1490px" marginheight="0px" marginwidth="0px" scrolling="no" style="border: none;">
    </iframe>
</div>
  --><div id="search_box">
  <div id="search">
  <p class="search_left"></p>
  <p class="search_center"><input class="search_center_input" value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
   <div id="main_box">
  <div id="main">
    <div id="main_con">
      <!--<div id="main_banner"><img src="<%=path%>/images/main_banner.jpg" /></div>
      --><div id="in_current">当前位置：<a href="<%=path %>/index.html">首页</a>|<span>我的账户</span></div>
      <div id="in_tit">我的账户</div>
      <div id="in_con">
      <form id="myform" action="<%=path%>/user/updateUser.action" method="post">
	  <table id="account" width="984" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td width="198" align="right" valign="middle">姓&nbsp;&nbsp;&nbsp;&nbsp;名：</td>
    <td width="786" align="left" valign="middle">
    <input type="text" id="name" name="user.bsUserInfo.realName" value="${user.bsUserInfo.realName}" maxlength="40"/></td>
  </tr>
  <tr>
    <td align="right" valign="middle">性&nbsp;&nbsp;&nbsp;&nbsp;别：</td>
    <td id="sex" align="left" valign="middle">
    	<label><input type="radio" name="user.bsUserInfo.sex" value="3" checked>保密</label>
    	<label><span class="sex_style"><input name="user.bsUserInfo.sex" value="1" ${user.bsUserInfo.sex==1?"checked":""} type="radio" />男</span></label>
    <label><span class="sex_style"><input name="user.bsUserInfo.sex" value="2" ${user.bsUserInfo.sex==2?"checked":""} type="radio"/>女</span></label>
    </td>
  </tr>
  <tr>
    <td width="198" align="right" valign="middle">昵&nbsp;&nbsp;&nbsp;&nbsp;称：</td>
    <td width="786" align="left" valign="middle">
    <input type="text" id="nickName" name="user.bsUserInfo.nickName" value="${user.bsUserInfo.nickName}" maxlength="30"/></td>
  </tr>
  <!--
  <tr>
    <td align="right" valign="middle">年&nbsp;&nbsp;&nbsp;&nbsp;龄：</td>
    <td align="left" valign="middle"><input id="age" name="user.bsUserInfo.age" value="${user.bsUserInfo.age }" type="text" />
    	<span id="ageMessage"></span></td>
  </tr>
   --><tr>
    <td align="right" valign="middle">出生日期：</td>
    <td align="left" valign="middle"><input id="birthday"  name="user.bsUserInfo.birthday" value="${user.bsUserInfo.birthday_}" type="text" readonly="readonly"/></td>
  </tr>
 <tr>
    <td align="right" valign="middle">联系电话：</td>
    <td align="left" valign="middle"><input id="mobile" name="user.bsUserInfo.mobile" value="${user.bsUserInfo.mobile}" maxlength="14" type="text" />
    	<span id="mobileMessage"></span></td>
  </tr>
   <tr>
    <td align="right" valign="middle">地&nbsp;&nbsp;&nbsp;&nbsp;址：</td>
    <td align="left" valign="middle">
    <span class="adress_style01"><select id="area"  name="user.bsUserInfo.sheng" onchange="showplus(this.value)"><option value="-1">--请选择--</option></select>省</span>
    <span class="adress_style02"><select id="areaplus" name="user.bsUserInfo.shi" onchange="showcounty(this.value)"><option value="-1">--请选择--</option></select>市</span>
    <span class="adress_style03"><select id="county" name="user.bsUserInfo.xian" onchange="showSchool(this.value)"><option value="-1">--请选择--</option></select>县</span>
   	<s:hidden name="user.bsUserInfo.id"></s:hidden>
    </td>
  </tr>
   <tr>
    <td align="right" valign="middle">学&nbsp;&nbsp;&nbsp;&nbsp;校：</td>
    <td align="left" valign="middle"><select id="school" name="user.bsUserInfo.school"  ><option value="-1">--请选择--</option></select></td>
  </tr>
  <tr>
    <td id="account_button" colspan="2" align="right" valign="middle"> <input name="in_button01" onclick="updateUser();" type="button" value="保存" /></td>
    </tr>
</table>
</form>
        <!-- 推荐 -->
			 <div id="recommend_book">
		      </div>
		      <script type="text/javascript">
		     	$(document).ready(function(){
		     		$('#recommend_book').load('<%=path%>/hot/recommendBook.html?version='+getCurrentDate());
		     	});
		      </script>
          <!-- 推荐END -->
      </div>
    </div>
  </div>
</div>
  </div>
  <div id="foot">
  <p><a href="http://www.pep.com.cn/" target="_blank">人教网首页</a>|<a href="#">本站首页</a>|<a href="#">版权声明</a>|<a href="#">服务热线</a>|<a href="#">关于我们</a></p>
    <p>京ICP备05019902号|新出网证(京)字016|京公网安备110402440009号&nbsp;&nbsp;版权所有：人民教育出版社有限公司</p>
  </div>
</body>
</html>
