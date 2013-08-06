<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%String path = request.getContextPath();
String fileDomain=DomainUtil.getFileDomain(); %>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><s:property value="bsOrder.orderId"/>订单信息 - 人教云汉商城</title>
<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/in.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">var path='<%=path %>';</script>
<script type="text/javascript">var fileDomain="<%=fileDomain %>";</script>
<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/user/addShopCart.js"></script>
<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
<script src="<%=path %>/js/checkLogin.js"></script>
<script type="text/javascript">
	var path = "<%=path%>";
	function cancel(orderId) {
		if(confirm("确实取消此订单吗？")){
			$.ajax({
		  		type: "POST",
		  		url: "<%=path%>/user/cancelOrder.action",
				data : "orderId=" + orderId + "&dd=" +Math.random(),
				cache : false,
				success : function(msg) {
					if (msg.flag == false) {
							alert('服务器异常，请稍候操作!');
						}else {
							window.location.reload();
						}
					}
				});
		}	
	}
	function purchase(pId){
		tocart(pId);
		alert("成功加入购物车！");
	}
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
      <div id="main_con"><!--
        <div id="main_banner"><img src="<%=path%>/images/main_banner.jpg" /></div>
        --><div id="in_current">当前位置：<a href="<%=path %>/index.html">首页</a>|<a href="<%=path%>/user/orderList.jsp">我的订单</a>|<span>订单详细</span></div>
        <div id="in_tit">订单详细</div>
        <div id="in_con">
        
		<div id="order_tit">
			<div class="order_title">
				<p>
					<span class="order_filed">订单编号：<span class="order_style01"><s:property value="bsOrder.orderId"/></span></span>
					<span class="order_filed">订单时间：<s:property value="bsOrder.orderTime_"/></span>
					<span class="order_filed">订单状态：<span class="order_style02"><s:if test="%{bsOrder.orderState == 1}">已支付</s:if><s:elseif test="%{bsOrder.orderState == 2}">已取消</s:elseif><s:else><a>未支付</a></s:else></span></span><br/>
					<span class="order_filed">订单价格：<span class="order_style02">¥<s:property value="bsOrder.orderMoney_"/></span></span>
					<span class="order_filed">已优惠：<span class="order_style02">¥<s:property value="bsOrder.privileged_"/></span></span>
				</p>
			</div>
			
			<div class="order_button">
				<s:if test="%{bsOrder.orderState == 0}">
					<input name="total_button" type="button" value="付 款" />
					<span class="order_buttonts">
						<input name="total_button" type="button" value="取消订单" onclick='cancel("<s:property value='bsOrder.orderId'/>")'/>
					</span>
				</s:if>
			</div>
		</div>
		
		
          <table id="book_list"  class="cart order" width="984" border="0" cellpadding="0" cellspacing="0">
            <tr>
			 <td width="121" align="center" valign="middle" class="book_tit">商品编号</td>
			  <td width="106" align="center" valign="middle" class="book_tit">&nbsp;</td>
              <td width="170" align="center" valign="middle" class="book_tit">商品名称</td>
		      <td width="157" align="center" valign="middle" class="book_tit">硬件授权次数</td>
              <td width="100" align="center" valign="middle" class="book_tit">购买金额</td>
              <td width="100" align="center" valign="middle" class="book_tit">已优惠</td>
              <td width="90" align="center" valign="middle" class="book_tit">数量</td>
              <s:if test="%{bsOrder.orderState == 2}">
              	<td width="140" align="center" valign="middle" class="book_tit">操作</td>
              	<s:set name="biaozhi" value="true"></s:set>
              </s:if>
            </tr>
            <s:iterator value="bsOrder.bsOrderProducts" status="st">
                  <s:if test="#st.Even"> 
                  	 <tr bgcolor='#DDF0EE'>
                  </s:if>
                  <s:else>
                  	 <tr>
                  </s:else>
				 <td  align="center" valign="middle"><s:property value="bsProducts.productId"/></td>
	              <td align="center" valign="middle"><div class="book_pic"><a href="<%=path%>/web/findProductsById.action?id=<s:property value='bsProducts.productId'/>" target="_blank"><img src="<%=fileDomain%><s:property value='bsProducts.thumbnail'/>" /></a></div></td>
					<td align="center" valign="middle"><s:property value="bsProducts.productName"/></td>
					<td align="center" valign="middle"> <s:property value="buyCount*3"/>/<s:property value="buyCount*3"/></td>
	              <td class="book_price" align="center" valign="middle">¥<s:property value="buyPrice_"/></td>
	              <td class="book_discount" align="center" valign="middle">¥<s:property value="privileged_"/></td>
	              <td align="center" valign="middle"><div id="featureContainer">
	                  <div id="feature">
	                    <div id="block">
	                        <s:property value="buyCount"/>
	                    </div>
	                  </div>
	              </div></td>
	              <s:if test="#biaozhi">
	              	 <td align="center" valign="middle"><a href="javascript:void(0)" onclick="purchase(<s:property value='bsProducts.productId'/>)">再买一次</a></td>
	              </s:if>
	            </tr>
            </s:iterator>
          </table>
          <div id="cart_total">
            <div class="check">
              <p class="c_num"><span class="cnum_style"><s:property value="bsOrder.bsOrderProducts.size()"/></span>件商品</p>
              <p class="money">总计：<span class="money_style"><span class="money_code">¥</span><s:property value="bsOrder.orderMoney_"/></span></p>
            </div>
          </div>
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
