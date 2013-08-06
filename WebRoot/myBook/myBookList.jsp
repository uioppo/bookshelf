<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="com.hcctech.bookshelf.util.DomainUtil"%>
<%String fileDomain=DomainUtil.getFileDomain(); 
String path = request.getContextPath();
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="x-ua-compatible" content="IE=7" />
<title>我的电子书列表 - 人教云汉商城</title>
<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link href="<%=path %>/css/main.css" rel="stylesheet" type="text/css" />
	<link href="<%=path %>/css/in.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="<%=path %>/css/pagination.css" />
	<link href="<%=path %>/css/dialog/dialog.min.css" rel="stylesheet" type="text/css" />
  	<script type="text/javascript">var path="<%=path %>";</script>
  	<script type="text/javascript">var fileDomain="<%=fileDomain %>";</script>
  	<script type="text/javascript" src="<%=path %>/js/jquery-1.8.0.min.js"></script>
  	<script src="<%=path %>/js/jquery.pagination.js"></script>
  	<script type="text/javascript" src="js/myBookList.js"></script>
  	<script type="text/javascript" src="<%=path %>/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="<%=path %>/js/user/topShopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/js/user/floatShopCart.js"></script>
	<script type="text/javascript" src="<%=path %>/js/tool.js"></script>
	<script type="text/javascript" src="<%=path %>/js/search_bar.js"></script>
	<script src="<%=path %>/js/checkLogin.js"></script>
  	<script type="text/javascript">
  		var num_entries=0;
  		var initfenye=true;
  		$(document).ready(function(){
  			$("#openbox").click(function(){
  				linkOpenBox();
  			});
  			loadPageData(1);
  			var openBox='<%=request.getParameter("openBox")%>';
	  		if(openBox!=null&&openBox!='null'&&openBox==1){
	  			setTimeout(linkOpenBox,500);
	  		}
	  		var continueAdd='<%=request.getParameter("continueAdd")%>';
	  		if(continueAdd!=null&&continueAdd!='null'&&continueAdd==1){
	  			setTimeout(openConfirm,500);
	  		}
  			$(window).resize(function(){
				$('.ui-widget-overlay').height($(document).height());
			});
  		});
		function keyboxclose(){
			$('#sb-container').hide();
		}
		function linkOpenBox(){
			checkLoginAgain();
			$('#sb-player').attr('src',path+"/myBook/showBox.jsp");
			$('.ui-widget-overlay').height($(document).height());
			$('.ui-widget-shadow').css('left',$(window).width()/2-190);
			$('#ui-widget-main').css('left',$(window).width()/2-190);
			$('#sb-container').show();
		}
		function reloadPage(){
			window.location.href=path+"/myBook/myBookList.jsp?continueAdd=1&&as=as";
		}
		function openConfirm(){
			$('.ui-widget-overlay').height($(document).height());
			$('.ui-widget-shadow').css('left',$(window).width()/2-190);
			$('#ui-widget-confirm').css('left',$(window).width()/2-190);
			$('#sb-confirm').show();
// 			if(confirm("提示您添加成功，要继续添加电子书吗？")){
// 				linkOpenBox();
// 	  		}
		}
		function confirm_close(){
			$('#sb-confirm').hide();
		}
		function confirm_goto(){
			confirm_close();
			linkOpenBox();
		}
  	</script>
</head>
<body>
<div id="container" style="min-height: 465px;">
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
      <a id="nav_current" href="#">我的电子书</a>|
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
  <a id="mid"></a>
  <p class="search_left"></p>
  <p class="search_center"><input class="search_center_input" value="请输入搜索内容" name="search_input" id="search_input" type="text" /></p>
  <p class="search_right"><input name="serarch_button" class="serarch_button" onclick="search_word()" type="button" value="搜索" /></p>
     </div>
   </div>
  <div id="main_box">
    <div id="main">
      <div id="main_con">
        <!--<div id="main_banner"><img src="<%=path %>/images/main_banner.jpg" /></div>
        --><div id="in_current">当前位置：<a href="<%=path %>/index.html">首页</a>|<span>我的电子书</span></div>
        <div id="in_tit">我的电子书</div>
        <div id="in_con" style="min-height: 245px;">
          <div class="in_con_tit">
            <ul>
              <li class="in_con_tit01">
                <a href="javascript:void(0)" ><img id="openbox" src="<%=path %>/images/code_eject.gif" /></a>
              </li>
              <li class="pagehs"><!--
                <sapn class="pager">
                总共有3个记录</span><a href="#" class="current_page">[1]</a><a href="#">[2]</a><a href="#">[3]</a><a class="pager" href="#">下一页</a> 
                </li>-->
            </ul>
          </div>
          <div id="emptydiv" style='height: 95px;border: 1px solid #DDD;margin-top: 25px;display: none'>
       		<div class="messages" style='height: 95px;background: #F3F3F3 url(<%=path%>/images/chat_007.jpg) no-repeat 250px 5px'>
                <ul style='padding-top: 15px;padding-left: 360px;'>
                       <li style='line-height: 26px;'>您现在还没有购买电子书哦，</li>
                       <li style='line-height: 26px;' class='go_home_a'>快去<a href="<%=path %>/index.html">首页</a>挑选喜欢的商品吧！</li>
                </ul>
   			</div>
       	</div>
          <table id="book_list" width="984" border="0" cellpadding="0" cellspacing="0" style="display: none">
            <tr>
              <td width="159" align="center" valign="middle" class="book_tit">&nbsp;</td>
              <td width="254" align="center" valign="middle" class="book_tit">名称</td>
              <td width="105" align="center" valign="middle" class="book_tit">学段</td>
              <td width="181" align="center" valign="middle" class="book_tit">购买时间</td>
              <td width="131" align="center" valign="middle" class="book_tit">使用期限</td>
              <td width="154" align="center" valign="middle" class="book_tit">硬件授权次数</td>
            </tr>
            <tbody id="datapage"></tbody>
          </table>
          <div class="in_con_tit">
            <ul>
              <li id="pageCount" class="pagehs">
                 <div id="Pagination"></div>
               </li>
            </ul>
          </div>
          <!-- 推荐 -->
			 <div id="recommend_book">
		      </div>
		      <script type="text/javascript">
		     	$(document).ready(function(){
		     		$('#recommend_book').load('<%=path%>/hot/recommendBook.html?version='+getCurrentDate(),function(_ds){
				     		if(_ds==""){
				     			$('#emptydiv').css('margin-top','85px');
				     		}else{
				     			$('#emptydiv').css('margin-top','45px');
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
  <p><a href="http://www.pep.com.cn/" target="_blank">人教网首页</a>|<a href="#">本站首页</a>|<a href="#">版权声明</a>|<a href="#">服务热线</a>|<a href="#">关于我们</a></p>
  <p>京ICP备05019902号|新出网证(京)字016|京公网安备110402440009号&nbsp;&nbsp;版权所有：人民教育出版社有限公司</p>
</div>
	<div id="sb-container" style="display:none;">
	<div class="ui-overlay"><div class="ui-widget-overlay" style="z-index: 20;"></div><div class="ui-widget-shadow ui-corner-all" style="width: 392px; height: 242px; position: absolute; left: 50px; top: 200px;z-index: 21;"></div></div>
	<div id="ui-widget-main" style="position: absolute; width: 390px; height: 240px;left: 250px; top:200px;z-index: 22;" class="ui-widget ui-widget-content ui-corner-all">
		<div class="ui-dialog-content ui-widget-content" style="background: none; border: 0;">
			<iframe id="sb-player" name="sb-player" height="240" width="100%" frameborder="0" marginwidth="0" marginheight="0" style="visibility: visible; " onload="this.style.visibility='visible'" scrolling="auto"></iframe>
		</div>
	</div>
	</div>
	<div id="sb-confirm" style="display:none;">
	<div class="ui-overlay"><div class="ui-widget-overlay" style="z-index: 20;"></div><div class="ui-widget-shadow ui-corner-all" style="width: 392px; height: 182px; position: absolute; left: 50px; top: 200px;z-index: 21;"></div></div>
	<div id="ui-widget-confirm" style="position: absolute; width: 390px; height: 180px;left: 250px; top:200px;z-index: 22;" class="ui-widget ui-widget-content ui-corner-all">
		<div class="ui-dialog-content ui-widget-content" style="background: none; border: 0;">
			  <div id="code_top">
    <p class="code_top01"></p>
    <p class="code_top02">操作成功<input id="close" style="borde:none;width:18px;height:18px;" name="close" type="image" src="<%=path %>/images/close.gif" onclick="confirm_close()" /></p>
    <p class="code_top03"></p>
  </div>
  <div id="code_con" style="height: 203px;">
    <ul>
      <li class="account_tit">操作成功，你已成功通过授权码获得一本电子书，是否继续添加？</li>
      <li class="account_input" >
        <span>
        <input onclick="confirm_goto()" name="code_button" type="image" src="<%=path %>/images/conde_submit.gif" />
        <input style="margin-left: 50px;" onclick="confirm_close()" name="code_close" type="image" src="<%=path %>/images/conde_close.gif" />
        </span>
        </li>
    </ul>
  </div>
</div>
	</div>
	</div>
	</body>
</html>