<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String fileDomain=DomainUtil.getFileDomain(); 
String path = request.getContextPath();
%>

<!doctype html>
<html>
  <head>
    <title>我的电子书详细 - 人教云汉商城</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">var path='<%=path %>';</script>
	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
	<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
	<script src="<%=path %>/js/checkLogin.js"></script>
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
		});
	</script>
  </head>
<body>
<input type="hidden" id="proBookCode" value="${bsMybook.bsProducts.bookCode }">
<div id="container">
  <div style="position:relative; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
    <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path%>/index.html'"></div>
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
      <div id="main_con"><!--
        <div id="main_banner"><img src="<%=path %>/images/main_banner.jpg" /></div>
        --><div id="in_current">当前位置：<a href="<%=path %>/index.html">首页</a>|<a href="<%=path %>/myBook/myBookList.jsp">我的电子书</a>|<span>${bsMybook.bsProducts.productName}</span></div>
        <div id="in_tit" class="in_titts"><img src="<%=path %>/images/book_list.gif" />${bsMybook.bsProducts.productName}</div>
        <div id="in_con">
		
		<div id="detial_top">
		
		<div id="detial_show">
		<p class="detial_pic"><img src="<%=fileDomain %>${bsMybook.bsProducts.thumbnail}" onclick="myBookDetail(${bsMybook.mybookId})" /></p>
		<p class="detial_probation"><a href="#"><img src="<%=path %>/images/probation_button.gif" /></a></p>
		</div>
		
		<div id="detial_filed">
		<p>教材名称： ${bsMybook.bsProducts.productName}</p>
		<p>出 版 社：${bsMybook.bsProducts.publishing}</p>
		<p>开 发 者：${bsMybook.bsProducts.author}</p>
		<p>对应教材版本：${bsMybook.bsProducts.teachVersion}</p>
		<p>适用学段：<s:if test="bsMybook.bsProducts.schoolStage==1">小学</s:if>
    						<s:elseif test="bsMybook.bsProducts.schoolStage==2">初中</s:elseif>
    						<s:else>高中</s:else></p>
		<p>适用年级：${bsMybook.bsProducts.grade}</p>
		<p>适用对象：${bsMybook.bsProducts.applyObject}</p>
		<p>科&nbsp;&nbsp;&nbsp;&nbsp;目：${bsMybook.bsProducts.subject}</p>
		<p>页&nbsp;&nbsp;&nbsp;&nbsp;数：${bsMybook.bsProducts.pageNumber}页</p>
		<p>语&nbsp;&nbsp;&nbsp;&nbsp;种：${bsMybook.bsProducts.language}</p>
		<p style="width: 247px;">电子书大小：${bsMybook.bsProducts.bookSize}M</p>
		<p>版&nbsp;&nbsp;&nbsp;&nbsp;本：${bsMybook.bsProducts.edition}年 <span id="ebookVersion"></span></p>
		<p>发布时间：${bsMybook.bsProducts.publishTime_}</p>
		<p class="detial_filed_style01">类&nbsp;&nbsp;&nbsp;&nbsp;别：<s:if test="bsMybook.bsProducts.productType==0">数字教辅</s:if>
		<s:elseif test="bsMybook.bsProducts.productType==1">数字教材</s:elseif><s:else>其他</s:else></p>
		<p class="detial_filed_style02">购买时间：${bsMybook.addTime_}</p>
		<p>剩余硬件授权次数：${bsMybook.downNumbers}次</p>
		<p>使用期限：${bsMybook.deadline_}</p>
		</div>
		</div>
		
		
		<div id="content_introduce">
		<div class="content_introduce_tit">内容详细</div>
		<div class="content_introduce_detial">
			<p>${bsMybook.bsProducts.description}</p>
		<!--
		
		     <p style="text-indent:25px;">人教版《生物<七年级上册>》数字教材是以人民教育出版社出版发行、通过2012年教育部审定的《生物<七年级上册>》纸质教材为依托，充分结合教材内容，发挥媒体资
源优势，把教材内容与媒体资源有机结合，是对纸质教材的有效拓展和补充。</p>
    <p style="text-indent:25px;">人教版《生物<七年级上册>》数字教材是在纸质教材的基础上，突破纸质教材的原有局限，拓展纸质教材的内涵和外延，增大知识容量、丰富呈现形式；突出数字教材示范
性、工具性、拓展性、交互性、趣味性特点，充分满足教师个性化教学、学生自主性学习的需要。</p>
    <p style="text-indent:25px;">人教版《生物<七年级上册>》数字教材能够满足用户使用不同硬件的要求。用户在购买后可以分别下载到台式电脑、笔记本或者苹果iPad移动终端等多种硬件中，简单、方
便、易用。</p>

<p style="text-align:center;padding-top:25px;"><img src="<%=path %>/images/content_pic01.jpg" /></p>
		--></div>
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
</body>
</html>