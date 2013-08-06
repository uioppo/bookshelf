<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String path = request.getContextPath();
String fileDomain=DomainUtil.getFileDomain();%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>我的订单 - 人教云汉商城</title>
		<link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css" />
		<link href="<%=path%>/css/in.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="<%=path %>/css/pagination.css" />
		<script type="text/javascript">var path="<%=path %>";</script>
		<script type="text/javascript">var fileDomain="<%=fileDomain %>";</script>
		<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
		<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
		<script type="text/javascript" src="<%=path %>/js/user/addShopCart.js"></script>
		<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
		<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
  		<script src="<%=path %>/js/jquery.pagination.js"></script>
  		<script src="<%=path %>/js/checkLogin.js"></script>
  		<script type="text/javascript">
  			var path = "<%=path%>";
  			var num_entries=0;
	  		var initfenye=true;
	  		var pageIndex = 0 ;
  			$(document).ready(function(){
	  			loadPageData(1);
	  		});
	  		
	  		function initPagination(){
				$("#cart_total").pagination(num_entries, {
					num_edge_entries: 1,
				    num_display_entries: parseInt((num_entries+9)/10),
					callback: function callbackInitPagination(page_index, jq){
								pageIndex = page_index+1;
								loadPageData(pageIndex);
					},
					current_page:pageIndex,
					link_to:"javascript:void(0);",
				    items_per_page:10,//每页条数
				    prev_text:"<<",
				    next_text:">>",
				    ellipse_text:"..."
				});
			}
	  		
	  		function loadPageData(num){
	  			var str = "" ;
	  			$.ajax({
  				url: path+"/user/orderList.action",
  				data:"pageSize="+num+"&math="+Math.random(),
  				success: function(data){
	  				if(data.url != null && data.url != '') {
	  					window.location.href = "<%=path%>/login.jsp?url=" + escape(location.href).replace(/\//g,"%2F"); ;
	  				}
  					num_entries=data.total;
  					if(data!=null&&data.orderList!=null&&data.orderList.length>0){
  						$("#emptydiv").hide();
  						$("#book_list").show();
	  					var lists=data.orderList;
	  					var s;
	  					for(var i=0;i<lists.length;i++){
	  						s=lists[i];
	  						if(i%2==0){
	  							str+="<tr bgcolor='#DDF0EE'>";
	  						}else{
	  							str+="<tr>";
	  						}
	  						str+="<td align='center' valign='middle'><a href='javascript:void(0);' onclick=\"detail('" + s.orderId+ "')\">";
	  						str+=s.orderId;
	  						str+="</a></td>";
	  						
	  						str+="<td class='book_price' align='center' valign='middle'>";
	  						str += s.orderMoney_;
	  						str+="</td>";
	  						
	  						
	  						str+="<td class='book_discount' align='center' valign='middle'>";
	  						str += s.privileged_;
	  						str+="</td>";
	  						
	  						str+="<td align='center' valign='middle'>";
	  						str+=s.orderTime_;
	  						str+="</td>";
	  						
	  						str+="<td align='center' valign='middle'>";
	  						if(s.orderState == 1){
	  							str += "已支付";
	  							str += "</td><td class='operation' align='center' valign='middle'><a href='javascript:void(0);' onclick=\"detail('" + s.orderId+ "')\">查看详细</td>";
	  						} else if(s.orderState == 2) {
	  							str += "已取消";
	  							str += "</td><td class='operation' align='center' valign='middle'><a href='javascript:void(0);' onclick=\"detail('" + s.orderId+ "')\">查看详细</td>";
	  						}else {
	  							str += '未支付';
	  							str += "</td><td  class='operation' align='center' valign='middle'>立即支付|<a href='javascript:void(0);' onclick=\"cancel('" + s.orderId+ "')\">取消</a></td>";
	  						}
	  						str+="</td>";
	  						
	  						str+="</tr>";
	  					}
	  					$("#book_list tbody").html(str);
	  				}else{
	  					$("#cart_total").html("");
	  					$("#book_list").hide();
	  					$("#emptydiv").show();
	  					return ;
	  				}
  					if(initfenye){
  						initfenye=false;
  						initPagination();
  					}
			}});
	  		}
	  		
	  		function detail(orderId) {
	  			window.open("<%=path%>/user/loadDetailOrder.action?ordId="+orderId) ;
	  		}
	  		function cancel(orderId) {
	  			if(confirm("确实取消此订单吗？")){
	  				$.ajax({
				  		type: "POST",
				  		url: path + "/user/cancelOrder.action",
						data : "orderId=" + orderId + "&dd=" +Math.random(),
						cache : false,
						success : function(msg) {
							if (msg.flag == false) {
									alert('服务器异常，请稍候操作!');
								}else {
									if(pageIndex <= 0 ){
										loadPageData(1);
									}else {
										loadPageData(pageIndex);
									}
								}
							}
						});
	  			}	
	  		}
  		</script>
	</head>
	<body>
		<div id="container" style="min-height: 465px;">
			<div style="position: relative; z-index: 0;" id="top_box">
				<div id="top_left"></div>
				<div id="top_right"></div>
				<div id="top" style="position: relative; z-index: 1000;">
				<div style="position: relative; width: 230px; height: 70px; float: left; cursor: pointer;" onclick="window.location.href='<%=path%>/index.html'"></div>
					<div class="top_tip">
						<span class="_top_tip"></span>
						<span class="top_style02">购物车（0）件</span>
					</div>
					<div id="nav">
				      <a href="<%=path %>/index.html">首页</a>|
				      <a href="<%=path%>/user/findUserName.action">我的账户</a>|
				      <a id="nav_current" href="<%=path %>/user/orderList.jsp">我的订单</a>|
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
						<!--<div id="main_banner">
							<img src="<%=path%>/images/main_banner.jpg" />
						</div>
						--><div id="in_current">
							当前位置：
							<a href="<%=path %>/index.html">首页</a>|
							<span>我的订单</span>
						</div>
						<div id="in_tit">
							我的订单
						</div>
						<div id="in_con" style="min-height: 245px;">
							<div id="emptydiv" style='height: 95px;border: 1px solid #DDD;margin-top: 25px;display: none'>
				        		<div class="messages" style='height: 95px;background: #F3F3F3 url(<%=path%>/images/chat_007.jpg) no-repeat 250px 5px'>
					                <ul style='padding-top: 15px;padding-left: 360px;'>
				                        <li style='line-height: 26px;'>您现在还没有订单哦，</li>
				                        <li style='line-height: 26px;' class='go_home_a'>快去<a href="<%=path %>/index.html">首页</a>挑选喜欢的商品吧！</li>
					                </ul>
				    			</div>
				        	</div>
							<table id="book_list" class="cart order_all" width="984"
								border="0" cellpadding="0" cellspacing="0">
								<thead>
								<tr>
									<td width="201" align="center" valign="middle" class="book_tit">
										订单编号
									</td>
									<td width="178" align="center" valign="middle" class="book_tit">
										金额
									</td>
									<td width="180" align="center" valign="middle" class="book_tit">
										已优惠
									</td>
									<td width="140" align="center" valign="middle" class="book_tit">
										下单时间
									</td>
									<td width="120" align="center" valign="middle" class="book_tit">
										状态
									</td>
									<td width="165" align="center" valign="middle" class="book_tit">
										操作
									</td>
								</tr>
								</thead>
								<tbody>
								</tbody>
								<tfoot></tfoot>
							</table>
							<div id="cart_total">
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
			<p>
				<a href="http://www.pep.com.cn/" target="_blank">人教网首页</a>|
				<a href="#">本站首页</a>|
				<a href="#">版权声明</a>|
				<a href="#">服务热线</a>|
				<a href="#">关于我们</a>
			</p>
			<p>
				京ICP备05019902号|新出网证(京)字016|京公网安备110402440009号&nbsp;&nbsp;版权所有：人民教育出版社有限公司
			</p>
		</div>
	</body>
</html>
