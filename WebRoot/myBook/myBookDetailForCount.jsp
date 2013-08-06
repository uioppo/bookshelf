<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String fileDomain=DomainUtil.getFileDomain(); %>
<%
String path = request.getContextPath();
%>

<!doctype html>
<html>
  <head>
    <title>购买硬件授权次数</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0"> 
	<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">var path="<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
	<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
	<script src="<%=path %>/js/checkLogin.js"></script>
	<script type="text/javascript">
		$(document).ready(function (){
			$("#keyCount").blur(function(){
				yanzheng();
			});
		});
		function tijiao(){
			if(yanzheng()){
				var keyCount = $("#keyCount").val();
				var myBookId=$("#myBookId").val();
				window.location.href=path+"/createOrderForCount.action?keyCount="+keyCount+"&myBookId="+myBookId;
			}
		}
		function yanzheng(){
			var keyCount = $("#keyCount").val();
				if(keyCount!=null&&keyCount!=""){
					var reg = new RegExp("^[1-9]*[1-9][0-9]*$");
					if(reg.test(keyCount)){
						var one=$("#keyCountSpan").text();
						$("#keyCountTotalSpan").text(one*keyCount);
						return true;
					}else{
						alert("请输入整数");	
						return false;	
					}
				}else{
					alert("请输入硬件授权次数");
					return false;
				}
		}
	</script>   
  </head>
<body>
<input id="myBookId" value="${bsMybook.bsProducts.productId}" type="hidden"/>
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
      <a href="<%=path %>/web/helpCenter.jsp">帮助中心</a>
      </div>
    </div>
  </div>
  <div id="banner"><img src="<%=path %>/images/banner.jpg" /></div>
   <div id="search_box">
  <div id="search">
  <p class="search_left"></p>
  <p class="search_center"><input class="search_center_input" value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
  <div id="main_box">
    <div id="main">
      <div id="main_con">
        <div id="main_banner"><img src="images/main_banner.jpg" /></div>
        <div id="in_current">当前位置：<a href="#">首页</a>|<a href="#">我的购物车</a>|<span>购买授权次数</span></div>
        <div id="in_tit" class="in_titts">购买授权次数</div>
        <div id="in_con">
		
		<div id="detial_top">
		<div id="detial_show">
		<p class="detial_pic"><img src="<%=fileDomain %>${bsMybook.bsProducts.thumbnail}" /></p>
		<p class="detial_times">硬件授权次数：<select id="keyCount" name="keyCount" ><option>1</option><option>2</option></select></p>
		<p class="times_price">一次<span id="keyCountSpan">${bsMybook.bsProducts.accreditPrice }</span>元     共<span id="keyCountTotalSpan">${bsMybook.bsProducts.accreditPrice }</span>元</p>
		<p class="detial_submit"><input width="114" height="35" name="detial_submit" type="image" onclick="tijiao()" src="<%=path %>images/detial_submit.gif" /></p>
		</div>
		<div id="detial_filed">
		<p>教材名称： ${bsMybook.bsProducts.productName}</p>
		<p>出 版 社：${bsMybook.bsProducts.publishing}</p>
		<p>开 发 者：${bsMybook.bsProducts.author}</p>
		<p>适用学段：<s:if test="bsMybook.bsProducts.schoolStage==1">小学</s:if>
    						<s:elseif test="bsMybook.bsProducts.schoolStage==2">初中</s:elseif>
    						<s:else>高中</s:else></p>
		<p>适用年级：${bsMybook.bsProducts.grade}</p>
		<p>适用对象：${bsMybook.bsProducts.applyObject}</p>
		<p>页&nbsp;&nbsp;&nbsp;&nbsp;数：${bsMybook.bsProducts.pageNumber}页</p>
		<p>语&nbsp;&nbsp;&nbsp;&nbsp;种：${bsMybook.bsProducts.language}</p>
		<p>版&nbsp;&nbsp;&nbsp;&nbsp;次：${bsMybook.bsProducts.subject}</p>
		<p>出版时间：${bsMybook.bsProducts.publishTime}</p>
		<p>定&nbsp;&nbsp;&nbsp;&nbsp;价：${bsMybook.bsProducts.price}</p>
		<p class="detial_filed_style01">类&nbsp;&nbsp;&nbsp;&nbsp;别：<s:if test="bsMybook.bsProducts.productType==0">教辅</s:if>
		<s:elseif test="bsMybook.bsProducts.productType==1">教参</s:elseif></p>
		</div>
		</div>
		 <div id="recommend_book">
            <div class="recommdeng_tit"><span>推荐数字教材<img src="images/new.gif" /></span></div>
            <div  class="recommend_con">
              <ul style="position:relative;">
                <li class="re_pic"><a href="#"><img src="images/re_book01.jpg" /></a></li>
                <li class="re_name">《同步练习册》</li>
                <li class="re_price">¥32.00</li>
                <li class="shoping_cart">
                  <input name="in_button01" type="button" value="加入购物车" />
                </li>
				<div class="discount"><img class="png"  src="images/75.png" /></div>
              </ul>
			  
              <ul style="position:relative;">
                <li class="re_pic"><a href="#"><img src="images/re_book01.jpg" /></a></li>
                <li class="re_name">《同步练习册》</li>
                <li class="re_price">¥32.00</li>
                <li class="shoping_cart">
                  <input name="in_button01" type="button" value="加入购物车" />
                </li>
				<div class="discount"><img class="png"  src="images/85.png" /></div>
              </ul>
			  				

              <ul>
                <li class="re_pic"><a href="#"><img src="images/re_book01.jpg" /></a></li>
                <li class="re_name">《同步练习册》</li>
                <li class="re_price">¥32.00</li>
                <li class="shoping_cart">
                  <input name="in_button01" type="button" value="加入购物车" />
                </li>
              </ul>
              <ul>
                <li class="re_pic"><a href="#"><img src="images/re_book01.jpg" /></a></li>
                <li class="re_name">《同步练习册》</li>
                <li class="re_price">¥32.00</li>
                <li class="shoping_cart">
                  <input name="in_button01" type="button" value="加入购物车" />
                </li>
              </ul>
              <ul>
                <li class="re_pic"><a href="#"><img src="images/re_book01.jpg" /></a></li>
                <li class="re_name">《同步练习册》</li>
                <li class="re_price">¥32.00</li>
                <li class="shoping_cart">
                  <input name="in_button01" type="button" value="加入购物车" />
                </li>
              </ul>
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
</body>
</html>
