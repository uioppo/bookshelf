<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%><%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String fileDomain=DomainUtil.getFileDomain(); 
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title><%=request.getParameter("keyword") %>商品搜索 - 人教云汉商城</title>
<link href="css/main.css" rel="stylesheet" type="text/css" />
<link href="css/in.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="css/pagination.css" />
<link href="<%=path %>/css/dialog/dialog.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="js/tool.js"></script>
<script type="text/javascript" src="js/jquery.pagination.js"></script>
<script type="text/javascript">var path='<%=path %>';</script>
<script type="text/javascript">var fileDomain="<%=fileDomain %>";</script>
<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/floatShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/highlight.js"></script>
<script src="<%=path %>/js/checkLogin.js"></script>
<script type="text/javascript">
var _total = ${total};
var this_proid;
$(function(){
	<%String type_ = request.getParameter("type");%>
	var str='在&nbsp;<%=type_!=null?(type_.equals("1")?"数字教材":"数字教辅"):"全部商品"%>';
	var xd='<%=request.getParameter("xd")%>';
	var xk='<%=request.getParameter("xk")%>';
	var keyword='<%=request.getParameter("keyword")%>';
	if(xd!=null&&xd!=""&&xd!="null"){
		if(xd==1||xd=="1"){
			str+="&nbsp;&gt;&nbsp;小学  ";
		}else if(xd==2||xd=="2"){
			str+="&nbsp;&gt;&nbsp;初中 ";
		}else if(xd==3||xd=="3"){
			str+="&nbsp;&gt;&nbsp;高中 ";
		}
	}
	if(xk!=null&&xk!=""&&xk!="null"){
		str+="&nbsp;&gt;&nbsp;"+xk;
	}
	$('#s_fw').html(str+"&nbsp;中搜索&nbsp;&nbsp;");
	$("#alreadySerch").html(keyword);
	var keyword_arr = keyword.split(" ");
	jQuery.each(keyword_arr,function(){
		$(".in_product_name").highlight(this);
	});
	
})
function isLogin_(str){
	this_proid=str;
	$.ajax({
		type:"post",
		url: path + "/checkLogin.action",
		success:function(json) {
			if(json != null && json != '') {
				window.location.href=path+"/web/findProductsById.action?id="+str+"&openBox=1&ok=a";
			} else {
				var _scrollHeight = $(document).scrollTop();
				$('.ui-widget-overlay').height($(document).height());
				$('.ui-widget-shadow').css('left',$(window).width()/2-190);
				$('#ui-widget-confirm').css('left',$(window).width()/2-190);
				$('.ui-widget-shadow').css('top',$(window).height()/2-90+_scrollHeight);
				$('#ui-widget-confirm').css('top',$(window).height()/2-90+_scrollHeight);
				$("#msgBox").show();
			}
		}
		
	});
}
function closeMag(){
		$("#msgBox").hide();
		
	}
function goLogin(){
	 var url = encodeURIComponent("web/findProductsById.action?id="+this_proid+"&openBox=1&ok=a#main_aaa");
		window.location.href=path+"/login.jsp?url="+url;
	}
</script>
<script type="text/javascript" src="js/search.js"></script>
<script type="text/javascript" src="js/search_bar.js"></script>

<style type="text/css">
	.pagination{float: right; margin-top: 2px;}
	#but {
		background: url(<%=path%>/images/input_bg02.gif) no-repeat;
		border-style: none;
		font-family: "Microsoft YaHei",微软雅黑,"Microsoft JhengHei",华文细黑,STHeiti,MingLiu;
		font-size: 14px;
		cursor: pointer;
		width: 121px;
		height: 30px;
		line-height: 30px;
		text-align: center;
		color: white;
		}
		.highlight{color: #C30; font-weight: bold;}
</style>
</head>
<body>
<div id="container">
  <div style="position:relative; z-index:0;" id="top_box">
    <div id="top_left"></div>
    <div id="top_right"></div>
    <div id="top" style="position:relative;z-index:1000;">
      <div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='index.html'"></div>
      <div class="top_tip"><span class="_top_tip"> <span class="top_style01">您好，欢迎来人教云汉商城！</span><a href="3">登录</a>|<a href="#">注册</a></span><span class="top_style02">购物车（0）件</span> </div>
      <div id="nav">
      <a href="<%=path %>/index.html">首页</a>|
      <a href="<%=path%>/user/findUserName.action">我的账户</a>|
      <a href="<%=path %>/user/orderList.jsp">我的订单</a>|
      <a href="<%=path %>/myBook/myBookList.jsp">我的电子书</a>|
      <a href="<%=path %>/help/helpCenter.jsp">帮助中心</a>
    </div>
    </div>
  </div><!--
  <div id="banner">
  	<iframe src="<%=path%>/hot/banner.html" height="147px" width="1490px" marginheight="0px" marginwidth="0px" scrolling="no" style="border: none;">
    </iframe>
  </div>
   --><div id="search_box">
  <div id="search">
  <p class="search_left"></p>
  <p class="search_center"><input value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
  <div id="main_box">
    <div id="main">
      <div id="main_con">
        <div id="main_menu">
          <div id="menu_tit"><img src="images/menu_list01.gif" /><img src="images/menu_tit_1.gif" /></div>
          <div id="menu_con" style="height: 550px;">
            <ul id="menu_list">
              <li><a  id="menu_current"  href="javascript:void(0);" onclick="searchByType(1,null,null);return false;">数字教材</a>
                <ul id="ChildMenu01" class="expanded" >
                  <li>
                    <div class="s_menu">
                      <ul>
                        <li class="grade" onclick="searchByType(1,1,null);return false;" style="cursor: pointer;">【小学】</li>
                        <li id="t1s1"><a href="javascript:void(0);" onclick="searchByType(1,1,'语文');return false;">语文</a></li>
                        <li id="t1s2"><a href="javascript:void(0);" onclick="searchByType(1,1,'数学');return false;">数学</a></li>
                        <li id="t1s3"><a href="javascript:void(0);" onclick="searchByType(1,1,'英语');return false;">英语</a></li>
                      </ul>
                      <ul>
                        <li class="grade" onclick="searchByType(1,2,null);return false;" style="cursor: pointer;">【初中】</li>
                        <li id="t1c1"><a href="javascript:void(0);" onclick="searchByType(1,2,'语文');return false;">语文</a></li>
                        <li id="t1c2"><a href="javascript:void(0);" onclick="searchByType(1,2,'数学');return false;">数学</a></li>
                        <li id="t1c3"><a href="javascript:void(0);" onclick="searchByType(1,2,'英语');return false;">英语</a></li>
                        <li id="t1c4"><a href="javascript:void(0);" onclick="searchByType(1,2,'物理');return false;">物理</a></li>
                        <li id="t1c5"><a href="javascript:void(0);" onclick="searchByType(1,2,'化学');return false;">化学</a></li>
                        <li id="t1c6"><a href="javascript:void(0);" onclick="searchByType(1,2,'生物');return false;">生物</a></li>
                      </ul>
                    </div>
                  </li>
                </ul>
              </li>
              <li><a href="javascript:;" onclick="searchByType(0,null,null);return false;">数字教辅</a>
                <ul id="ChildMenu02" class="expanded">
                  <li>
                    <div class="s_menu">
                      <ul>
                        <li class="grade">【小学】</li>
                        <li id="t0s1"><a href="javascript:void(0);" onclick="searchByType(0,1,'语文');return false;">语文指导书</a></li>
                        <li id="t0s2"><a href="javascript:void(0);" onclick="searchByType(0,1,'数学');return false;">数学指导书</a></li>
                      </ul>
                      <ul>
                        <li class="grade">【初中】</li>
                        <li id="t0c1"><a href="javascript:void(0);" onclick="searchByType(0,2,'语文');return false;">语文指导书</a></li>
                        <li id="t0c2"><a href="javascript:void(0);" onclick="searchByType(0,2,'数学');return false;">数学指导书</a></li>
                      </ul>
                    </div>
                  </li>
                </ul>
              </li>
            </ul>
<!--             <script src="js/main_menu.js" type="text/javascript"></script> -->
          </div>
        </div>
		
	<div id="in_product">
	
	<div class="sort_search">排序：
	<input name="price_sort" type="image" by='1' id="price_sort" src="images/price_sort.gif" width="48" height="26" />
	<input name="time_sort" id="time_sort" by='0' type="image" src="images/time_sort.gif" width="72" height="26" />
	<span class="search_totial">共0个商品</span>
	<span id="alreadySerch" style="color:#cc3300; font-size:14px;font-weight: bold; float: right;line-height: 26px;height: 26px; margin-top:0px;*margin-top:-26px;_margin-top:-26px; padding-right: 15px;"></span>
	<span id="s_fw" style="float: right;line-height: 26px;height: 26px; margin-top:0px;*margin-top:-26px;_margin-top:-26px;" >搜索：</span>
	</div>
	<div id="in_product_list" class='book_link'>
	<s:iterator value="products" >
	<ul>
	<li class="in_product_pic"><a href="web/findProductsById.action?id=<s:property value='productId'/>"  target='_blank'><img src='<s:property value="thumbnail_1"/>' /></a></li>
	<li class="in_product_name"><a href="web/findProductsById.action?id=<s:property value='productId'/>"  target='_blank'><s:property value="productName"/></a></li>
	<li class="in_product_grade"><s:property value="grade"/>（<s:property value="volume"/>）</li>
	<li class="in_product_price">¥ 
		<s:if test="vipPrice_!=null&&vipPrice_!=''">
			<s:property value="vipPrice_"/>&nbsp;&nbsp;<span style="text-decoration:line-through;font-size: 12px;color: #787878;font-weight: normal;"><s:property value="price_"/></span></s:if>
		<s:elseif test="webDownPrice_!=null&&webDownPrice_!=''">
			<s:property value="webDownPrice_"/>&nbsp;&nbsp;<span style="font-size: 12px;color: #787878;font-weight: normal;">​​<s:property value="downPrice_"/>折</span></s:elseif>
		<s:else>
			<s:property value="price_"/></s:else>
	</li>
	<li class="in_product_cart">
		<input name="product_cart" type="image" onclick="tocart(<s:property value='productId'/>)" src="images/prdouct_cart.gif" width="70" height="21" />
		<input name="product_car" type="image" onclick="isLogin_(<s:property value='productId'/>)" src="images/activatebutton.png" width="50" height="21" />
	</li>
	</ul>
	</s:iterator>
<div class="sort_search sort_searchts" id='Pagination' style="float: right;" >
</div>	
	</div>
	</div>	
		<!-- 推荐 -->
	 <div id="recommend_book">
      </div>
      <script type="text/javascript">
     	$(document).ready(function(){
     		$('#recommend_book').load('hot/recommendBook.html?version='+getCurrentDate());
     	});
      </script>
          <!-- 推荐END -->
      </div>
    </div>
  </div>
</div>
<div id="foot">
  <p><a href="http://www.pep.com.cn/" target="_blank">人教网首页</a>|<a href="#">本站首页</a>|<a href="#">版权声明</a>|<a href="#">服务热线</a>|<a href="#">关于我们</a></p>
  <p>京ICP备05019902号|新出网证(京)字016|京公网安备110402440009号&nbsp;&nbsp;版权所有：人民教育出版社有限公司</p>
</div>
<div id="msgBox" style="display:none;">
	<div class="ui-overlay"><div class="ui-widget-overlay" style="z-index: 20;"></div><div class="ui-widget-shadow ui-corner-all" style="width: 392px; height: 182px; position: absolute; left: 50px; top: 500px;z-index: 21;"></div></div>
	<div id="ui-widget-confirm" style="position: absolute; width: 390px; height: 180px;left: 250px; top:500px;z-index: 22;" class="ui-widget ui-widget-content ui-corner-all">
		<div class="ui-dialog-content ui-widget-content" style="background: none; border: 0;">
			  <div id="code_top">
    <p class="code_top01"></p>
    <p class="code_top02">温馨提示<input style="borde:none;width:18px;height:18px;" name="close" type="image" src="<%=path %>/images/close.gif" onclick="closeMag()" /></p>
    <p class="code_top03"></p>
  </div>
  <div id="code_con" style="height: 203px;">
    <ul>
      <li class="account_tit">您还没有登录商城，请先登录商城再做激活操作！</li>
      <li class="account_input" style="text-align: center;">
        <span>
       <input id="but" type="button" onclick="goLogin()" value="去 登 录" >
        </span>
        </li>
    </ul>
  </div>
</div>
	</div>
	</div>
</body>
</html>
