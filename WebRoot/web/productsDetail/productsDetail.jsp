<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String fileDomain=DomainUtil.getFileDomain(); 
String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title>${bsProducts.productName } - 人教云汉商城</title>
<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/css/dialog/dialog.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">var path='<%=path %>';</script>
<script type="text/javascript">var fileDomain="<%=fileDomain %>";</script>
<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/floatShopCart.js?sss=ss"></script>
<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
<script type="text/javascript" src="<%=path %>/js/checkLogin.js"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			url:path+"/admin/ebook/getEbookVersion.action",
			data:"bookCode="+$("#proBookCode").val()+"&math="+Math.random(),
			success:function(data){
				if(data.bookVersion!=null&&data.bookVersion!=""){
					$("#ebookVersion").html(data.bookVersion+"版");
				}
			}
		});
		$("#licenseKey").click(function(){
			licenseKey();
		});
		var openBox='<%=request.getParameter("openBox")%>';
  		if(openBox!=null&&openBox!='null'&&openBox==1){
  			setTimeout(licenseKey,500);
  		}
		$(window).resize(function(){
			$('.ui-widget-overlay').height($(document).height());
		});
	});
	function getProductId(){
		var productId=$("#productId").val();
		return productId;
	}
	function licenseKey(){
		$.ajax({
			url:path+"/web/validateLogin.action",
			data:"math="+Math.random(),
			success:function(data){
				if(data.msg=="0"){
					$('#sb-player').attr('src',path+"/myBook/showBox1.jsp");
					$('.ui-widget-overlay').height($(document).height());
					$('.ui-widget-shadow').css('left',$(window).width()/2-190);
					$('#ui-widget-main').css('left',$(window).width()/2-190);
					$('#sb-container').show();
				}else{
					locateLogin();
				}
			}
		});
	}
	function keyboxclose(flag){
		$('#sb-container').hide();
		if(flag==1){
			$('.ui-widget-overlay').height($(document).height());
			$('.ui-widget-shadow').css('left',$(window).width()/2-190);
			$('#ui-widget-confirm').css('left',$(window).width()/2-190);
			$("#msgBox").show();
		}
	}
	function closeMag(){
		$("#msgBox").hide();
	}
</script>
</head>
<body>
<input type="hidden" id="productId" value="${bsProducts.productId}">
<input type="hidden" id="proBookCode" value="${bsProducts.bookCode }">
<div id="container">
  <div style="position:relative; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
      <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='../index.html'"></div>
      <div class="top_tip"><span class="_top_tip"> <span class="top_style01">您好，欢迎来人教云汉商城！</span><a href="3">登录</a>|<a href="#">注册</a></span><span class="top_style02">购物车（0）件</span> </div>
      <div id="nav">
      <a href="<%=path %>/index.html">首页</a>|
      <a href="<%=path%>/user/findUserName.action">我的账户</a>|
      <a href="<%=path %>/user/orderList.jsp">我的订单</a>|
      <a href="<%=path %>/myBook/myBookList.jsp">我的电子书</a>|
      <a href="<%=path %>/help/helpCenter.jsp">帮助中心</a>
      </div>
    </div>
  </div>
  <div id="banner">
  	<iframe src="<%=path%>/hot/banner.html" height="147px" width="1490px" marginheight="0px" marginwidth="0px" scrolling="no" style="border: none;">
    </iframe>
  </div>
   <div id="search_box">
  <div id="search">
  <p class="search_left"></p>
  <p class="search_center"><input class="search_center_input" value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
   <a id="main_aaa"></a>
  <div id="main_box">
    <div id="main">
      <div id="main_con">
        <!--<div id="main_banner"><img src="<%=path %>/images/main_banner.jpg" /></div>
        --><div id="in_current">当前位置：<a href="<%=path %>/index.html">首页</a>|<a href="<%=path %>/ebooks.action?xd=&xk=&page=0&order=&sort=&type=${bsProducts.productType }&gao=ren">电子书列表</a>|<span>${bsProducts.productName }</span></div>
        <div id="in_tit" class="in_titts"><img src="<%=path %>/images/book_list.gif" />${bsProducts.productName }</div>
        <div id="in_con">
		<div id="detial_top">
		<div id="detial_show">
		<p class="detial_pic"><img src="<%=fileDomain %>${bsProducts.thumbnail}" /></p>
		<p class="detial_probation"><a href="#"><img src="<%=path %>/images/probation_button.gif" /></a></p>
		</div>
		<div id="detial_filed">
		<s:if test="bsProducts.vipPrice_!=null&&bsProducts.vipPrice_!=''"><p>价&nbsp;&nbsp;&nbsp;&nbsp;格：<span class="detial_filed_style03">¥<span class="detial_filed_style04">
		<s:property value="bsProducts.vipPrice_"/></span></span>&nbsp;&nbsp;&nbsp;&nbsp;原&nbsp;&nbsp;&nbsp;&nbsp;价：<span style="text-decoration:line-through;"><s:property value="bsProducts.price_"/></span></p></s:if>
		<s:elseif test="bsProducts.webDownPrice_!=null&&bsProducts.webDownPrice_!=''"><p>价&nbsp;&nbsp;&nbsp;&nbsp;格：<span class="detial_filed_style03">¥<span class="detial_filed_style04"><s:property value="bsProducts.webDownPrice_"/></span></span>
		&nbsp;&nbsp;&nbsp;&nbsp;原&nbsp;&nbsp;&nbsp;&nbsp;价：<span style="text-decoration:line-through;"><s:property value="bsProducts.price_"/></span>
		</p><p>折&nbsp;&nbsp;&nbsp;&nbsp;扣：<s:property value="bsProducts.downPrice_"/>折</p>
		</s:elseif>
		<s:else><p>价&nbsp;&nbsp;&nbsp;&nbsp;格：<span class="detial_filed_style03">¥<span class="detial_filed_style04">
		<s:property value="bsProducts.price_"/></span></span></p></s:else>
		<p>出 版 社：${bsProducts.publishing}</p>
		<p>开 发 者：${bsProducts.author}</p>
		<p>对应教材版本：${bsProducts.teachVersion}</p>
		<p style="width: 247px;">适用学段：<s:if test="bsProducts.schoolStage==1">小学</s:if>
					<s:elseif test="bsProducts.schoolStage==2">初中</s:elseif>
					<s:else>高中</s:else></p>
		<p style="width: 247px;">适用年级：${bsProducts.grade}</p>
		<p style="width: 247px;">适用对象：${bsProducts.applyObject}</p>
		<p style="width: 247px;">科&nbsp;&nbsp;&nbsp;&nbsp;目：${bsProducts.subject}</p>
		<p style="width: 247px;">页&nbsp;&nbsp;&nbsp;&nbsp;数：${bsProducts.pageNumber}页</p>
		<p style="width: 247px;">语&nbsp;&nbsp;&nbsp;&nbsp;种：${bsProducts.language}</p>
		<p style="width: 247px;">版&nbsp;&nbsp;&nbsp;&nbsp;本：${bsProducts.edition}年 <span id="ebookVersion"></span></p>
		<p style="width: 247px;">电子书大小：${bsProducts.bookSize}M</p>
		<p style="width: 247px;">发布时间：${bsProducts.publishTime_}</p>
		<p style="width: 247px;">类&nbsp;&nbsp;&nbsp;&nbsp;别：<s:if test="bsProducts.productType==0">数字教辅</s:if>
		<s:elseif test="bsProducts.productType==1">数字教材</s:elseif><s:else>其他</s:else></p>
		<div class="detial_filed_style01">阅读介质：
		<a href="http://store.apple.com" title="下载人教云书架 IPAD版" target="_blank"><img src="<%=path %>/images/e-icon_ipad.gif"/></a>
		<a href="<%=path %>/downloadReader.action" title="下载人教云书架 PC版" target="_blank"><img src="<%=path %>/images/PC-online-big.jpg"/></a></div>
		<p class="detial_button">
			<input name="detial_button01" onclick="tocart(${bsProducts.productId})" type="image" src="<%=path %>/images/detial_button01.gif" width="118" height="35" />
			<a id="keylink" href="javascript:void(0)" rel="shadowbox;height=274;width=397">
			<input id="licenseKey" name="detial_button02" type="image" src="<%=path %>/images/detial_button02.gif" width="118" height="35" />
			</a>
		</p>
		</div>
		</div>
		<div id="content_introduce">
		<div class="content_introduce_tit">内容详细</div>
		<div class="content_introduce_detial">
		<p>${bsProducts.description}</p>
		</div>
		</div>
		<div id="content_introduce">
		<div class="content_introduce_tit">【目录】</div>
		<div class="content_introduce_detial">
		<p>${bsProducts.catalog}</p>
		</div>
		</div>
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
	<div class="ui-overlay"><div class="ui-widget-overlay" style="z-index: 20;"></div><div class="ui-widget-shadow ui-corner-all" style="width: 392px; height: 242px; position: absolute; left: 50px; top: 550px;z-index: 21;"></div></div>
	<div id="ui-widget-main" style="position: absolute; width: 390px; height: 240px;left: 250px; top:550px;z-index: 22;" class="ui-widget ui-widget-content ui-corner-all">
		<div class="ui-dialog-content ui-widget-content" style="background: none; border: 0;">
			<iframe id="sb-player" name="sb-player" height="240" width="100%" frameborder="0" marginwidth="0" marginheight="0" style="visibility: visible; " onload="this.style.visibility='visible'" scrolling="auto"></iframe>
		</div>
	</div>
	</div>
	<div id="msgBox" style="display:none;">
	<div class="ui-overlay"><div class="ui-widget-overlay" style="z-index: 20;"></div><div class="ui-widget-shadow ui-corner-all" style="width: 392px; height: 182px; position: absolute; left: 50px; top: 500px;z-index: 21;"></div></div>
	<div id="ui-widget-confirm" style="position: absolute; width: 390px; height: 180px;left: 250px; top:500px;z-index: 22;" class="ui-widget ui-widget-content ui-corner-all">
		<div class="ui-dialog-content ui-widget-content" style="background: none; border: 0;">
			  <div id="code_top">
    <p class="code_top01"></p>
    <p class="code_top02">操作成功<input style="borde:none;width:18px;height:18px;" name="close" type="image" src="<%=path %>/images/close.gif" onclick="closeMag()" /></p>
    <p class="code_top03"></p>
  </div>
  <div id="code_con" style="height: 203px;">
    <ul>
      <li class="account_tit">操作成功，你已成功通过授权码获得这本电子书，请到<a href="<%=path %>/myBook/myBookList.jsp" target="_blank"><font face="黑体" size="4" color="#FF9900">我的电子书</font></a>页面查看。</li>
      <li class="account_input" style="text-align: center;">
        <span>
        <input onclick="closeMag()" name="code_button" type="image" src="<%=path %>/images/conde_submit.gif" />
        </span>
        </li>
    </ul>
  </div>
</div>
	</div>
	</div>
</body>
</html>
