<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%String path = request.getContextPath(); %>

<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'banner.jsp' starting page</title>
    	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
    <script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#msg").html("<s:property value='msg'/>");
		});
		/*function subBanner() {
			var ban = $("#banner").val();
			if(ban == null || ban == ''){
				$("#msg").html("请选择添加的图片");
				return false ;
			}else {
				var type = ban.substring(ban.lastIndexOf(".")+1);
				if(type != "jpg") {
					$("#msg").html("请选择jpg文件");
					return false;
				}else {
					$("#form").submit();
				}
			}
		}*/
		function subBanner() {
			var ban = $("#banner").val();
			if(ban == null || ban == ''){
				$("#msg").html("请选择添加的广告横幅");
				return false ;
			}else {
				var type = ban.substring(ban.lastIndexOf(".")+1);
				if(type == "jpg"||type == "png"||type == "gif"||type == "jpeg"||type == "swf") {
					$("#form").submit();
				}else {
					$("#msg").html("请选择jpg、png、gif、jpeg、swf文件");
					return false;
				}
			}
		}
	</script>
  </head>
  
  <body>
    <form action="<%=path%>/admin/index/uploadBanner.action" method="post" enctype="multipart/form-data" id="form">
    	<input type="file"" name="banner" id="banner"/>
    	<label id="msg" style="color: red;"></label>
    	<input type="button" value="确定" onclick="subBanner();">
    </form>
    <div style="border: dashed 1px #ccc;">请选择  1490 * 147 的文件进行上传。</div>
    <hr width="100%"/>
    <b>当前横幅预览：</b><br>
    <div id="img">
    <iframe src="<%=path%>/hot/banner.html?varsion=<%=Math.random() %>" height="147px" width="1490px" marginheight="0px" marginwidth="0px" scrolling="no" style="border: none;">
    </iframe>
    </div>
  </body>
</html>
