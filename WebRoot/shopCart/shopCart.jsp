<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String fileDomain=DomainUtil.getFileDomain();
String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
  <head>
    <title>购物车 - 人教云汉商城</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">var path = "<%=path%>";</script>
	<script type="text/javascript">var fileDomain = "<%=fileDomain%>";</script>
	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=path %>/js/lrscroll.js"></script>
	<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/shopCart/shopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
	<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>	
	<script src="<%=path %>/js/checkLogin.js"></script>
	<script type="text/javascript">
		var proDetailPath=path+"/web/findProductsById.action?id=";
		$(document).ready(function(){
			loadCartList();
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
  <p class="search_center"><input class="search_center_input" value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
  <div id="main_box">
    <div id="main">
      <div id="main_con">
        <!--<div id="main_banner"><img src="<%=path %>/images/main_banner.jpg" /></div>
        --><div id="in_current">当前位置：<a href="<%=path %>/index.html">首页</a>|<span>我的购物车</span></div>
        <div id="in_tit">我的购物车</div>
        <div id="in_con">
        	<div id="emptydiv" style='height: 98px;border: 1px solid #DDD;margin-top: 25px'>
        		<div class="messages" style='height: 98px;background: #F3F3F3 url(<%=path%>/images/cart-empty-bg.png) no-repeat 250px 22px'>
	                <ul style='padding-top: 23px;padding-left: 341px;'>
                        <li style='line-height: 26px;'>购物车内暂时没有商品，</li>
                        <li style='line-height: 26px;' class='go_home_a'><a href="<%=path %>/index.html">去首页</a>挑选喜欢的商品</li>
	                </ul>
    			</div>
        	</div>
        	<div id="tablediv">
          <table id="book_list" class="cart" width="984" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="413" align="center" valign="middle" class="book_tit">商品名称</td>
              <td width="90" align="center" valign="middle" class="book_tit">金额</td>
              <td width="90" align="center" valign="middle" class="book_tit">已优惠</td>
              <td width="90" align="center" valign="middle" class="book_tit">数量</td>
              <td width="100" align="center" valign="middle" class="book_tit">授权次数</td>
              <td width="100" align="center" valign="middle" class="book_tit">使用期限</td>
              <td width="101" align="center" valign="middle" class="book_tit">操作</td>
            </tr>
          	<tbody id="cartMain"></tbody>
          </table>
          <div id="zongji" class="check" style="width: 100%">
		  <div id="show_tips" style="display:none; position: relative; left: 200px; background-color: #FEF647; float: left; padding: 5px;"></div>
          <div style="float: right; padding-right: 50px; line-height: 20px; width: 140px;">合&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计：<span style="float: right;" id="total_no"></span><br/>优&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;惠：<span style="float: right;" id="youhui_no1"></span><br/>活动优惠：<span style="float: right;" id="youhui_no">-&nbsp;¥0.00</span></div></div>
       
          <div id="cart_total">
            <div class="empty"><a href="javascript:clearAll();">清空购物车</a></div>
            <div class="check">
              <p class="go_buy"><a href="<%=request.getParameter("linkpath") %>"><img src="<%=path %>/images/go_buy.gif" /></a></p>
              <p class="c_num">共<span id="cart" class="cnum_style">0</span>件商品</p>
              <p class="money">
              	总计：<span class="money_style">¥<span id="total" class="money_code">0.00</span></span></p>
              <p class="total_button">
                <input id="jiesuan" name="total_button" onclick="jiesuan()" type="button" value="结 算" />
              </p>
            </div>
         </div>
         
          </div>
          <!-- 推荐 -->
			 <div id="recommend_book">
		      </div>
		      <script type="text/javascript">
		     	$(document).ready(function(){
		     		$('#recommend_book').load('<%=path%>/hot/recommendBook.html?version='+getCurrentDate(),function(_ds){
			     		if(_ds==""){
			     			$('#emptydiv').css('margin-top','85px');
			     		}
		     			$("#recommend_book input[name='in_button01']").click( function () {loadCartList();});
		     		});
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
