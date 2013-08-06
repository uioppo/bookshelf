<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String path = request.getContextPath();
String fileDomain=DomainUtil.getFileDomain();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/tr/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title>人教云汉商城</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/dialog/dialog.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">var path="<%=path%>";</script>
<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/checkLogin.js"></script>
<style type="text/css">
	#pay_select label {
	cursor: pointer;
}
</style>
<script type="text/javascript">
$(function(){
		$("#next_step_btn").click(function(){
			boxshow();
		});
		$(window).resize(function(){
			$('.ui-widget-overlay').height($(document).height());
		});
	});
	function boxshow(){
		$('.ui-widget-overlay').height($(document).height());
		$('.ui-widget-shadow').css('left',$(window).width()/2-190);
		$('#ui-widget-main').css('left',$(window).width()/2-190);
		$('#sb-container').show();
	}
	function keyboxclose(){
		$('#sb-container').hide();
	}
</script>
<style type="text/css">
.thickclose:link, .thickclose:visited {
display: block;
position: absolute;
z-index: 100000;
top: 7px;
right: 12px;
overflow: hidden;
width: 15px;
height: 15px;
background: url(../images/bg_thickbox.gif) no-repeat 0 -18px;
font-size: 0;
line-height: 100px;
}
.thicktitle {
height: 27px;
padding: 0 10px;
border: solid #C4C4C4;
background: #F3F3F3;
line-height: 27px;
font-family: arial,"宋体b8b\4f53";
font-size: 14px;
font-weight: bold;
color: #333;
border:none;
}
.thickcon {
overflow: auto;
background: white;
border: solid #C4C4C4;
padding: 10px;
border: none;
}
.icon-info03 {
vertical-align: middle;
margin-right: 10px;
width: 38px;
height: 38px;
display: inline-block;
font-size: 0;
line-height: 0;
overflow: hidden;
background: url(../images/i.png) no-repeat;
}
.btns {
padding-top: 30px;
}
.btn-11 {
margin: 0 3px;
line-height: 25px;
}
.btn {
margin-left:50px;
 border: none;
}
.mb{
	margin-top: 30px;
}
</style>
</head>
<body>
<div id="container">
  <div style="position:relative; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
   	  <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path%>/index.html'"></div>
      <div class="top_tip"><span class="_top_tip"> </span><span class="top_style02">&nbsp;&nbsp;</span> </div>
      <div id="nav">
		<a href="<%=path%>/index.html">首页</a>|
		<a href="<%=path%>/user/findUserName.action">我的账户</a>|
		<a href="<%=path%>/user/orderList.jsp">我的订单</a>|
		<a href="<%=path%>/myBook/myBookList.jsp">我的电子书</a>|
		<a href="<%=path%>/help/helpCenter.jsp">帮助中心</a>
		</div>
    </div>
  </div>
  <div id="main_box">
    <div id="main">
      <div id="main_con">
        <div id="in_con">
		
	<div id="pay_step_title">
	<p>订单编号：<span class="order_style01"><s:property value="bsOrder.orderId"/></span>支付方式：在线支付<span class="pay_step_style01">（备注：即时到帐，支持绝大多数银行卡借记卡及部分银行信用卡）</span></p>
	<p>订单金额：<span class="order_style02">¥<span class="pay_step_style02"><s:property value="bsOrder.orderMoney_"/></span></span></p>
	
	</div>
	
	<div id="pay_select">
	<dl>
   <dt>网上银行：<span class="select_title01">登录网上银行页面进行支付</span></dt>
   <dd>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/gsyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/pkyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/gfyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/nyyh.jpg" /></label></p>
   </dd>
   <dd>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/szfzyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/zgyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/zggdyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/xyyh.jpg" /></label></p>
   </dd>
 <dd>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/pfyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/jtyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/zsyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/zxyh.jpg" /></label></p>
   </dd>   
<dd>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/zgmsyh.jpg" /></label></p>
   <p><label><input name="select01" type="radio" value="" /><img src="../images/zgjsyh.jpg" /></label></p>
   </dd>  	</dl>
	</div>
	<div id="next_step"><input id="next_step_btn" name="total_button" type="button" value="下一步" /></div>
        </div>
      </div>
    </div>
  </div>
</div>
<div id="foot">
  <p><a href="http://www.pep.com.cn/" target="_blank">人教网首页</a>|<a href="#">本站首页</a>|<a href="#">版权声明</a>|<a href="#">服务热线</a>|<a href="#">关于我们</a></p>
  <p>京ICP备05019902号|新出网证(京)字016|京公网安备110402440009号&nbsp;&nbsp;版权所有：人民教育出版社有限公司</p>
</div>
<div id="sb-container" style="display:none;">
	<div class="ui-overlay"><div class="ui-widget-overlay" style="z-index: 20;"></div><div class="ui-widget-shadow ui-corner-all" style="width: 392px; height: 242px; position: absolute; left: 50px; top: 250px;z-index: 21;"></div></div>
	<div id="ui-widget-main" style="position: absolute; width: 390px; height: 240px;left: 250px; top:250px;z-index: 22;" class="ui-widget ui-widget-content ui-corner-all">
		<div class="ui-dialog-content ui-widget-content" style="background: none; border: 0;">
			<div class="thicktitle" id="" style="width:350"><span>支付</span></div>
			<div class="thickcon" id="" style="width:370px;height:185px;"><div class="m" id="flex"><div class="mc"><s class="icon-info03"></s><p style="display: inline;">请您在新打开的网上银行页面进行支付，支付完成前请不要关闭该窗口。</p><div class="btns"><a href="<%=path %>/pay/paySuccess.action?orderId=<s:property value="bsOrder.orderId"/>&dxasd=c" class="btn btn-11"><img src="../images/pay_ok.gif" border="0"/></a><a href="../help/helpPay.html#help" class="btn btn-11"><img src="../images/pay_er.gif" border="0"/></a></div></div><div class="mb"><a href="javascript:keyboxclose();" class="flk-05">返回重新选择银行</a></div></div></div>
			<a href="javascript:keyboxclose();" class="thickclose" id="">×</a>
		</div>
	</div>
	</div>
</body>
</html>
