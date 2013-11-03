<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
  <head>
    <title>更新电子书</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="../js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="../js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="../product/css/main.css">
	<script type="text/javascript" src="../js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="../js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">
	
		$(document).ready(function(){
			function rand() {
				$.ajax({
					type:"post",
					url: '../reandom.action?dd='+Math.random(),
					cache : false,
					success:function(msg) {
					}
				});
			}
			setInterval(rand,180*1000);
		});
	
		function onUploadFinish(o,s,i){
			$("#bookPath").val(o);
			$("#bookSize").val(s);
			$.messager.alert('提示','上传成功!','info');
		}
		
		function saveEbook(){
			var bookPath = $("#bookPath").val();
			var isuploaded = false;
			if(bookPath==""){
				$.messager.alert('提示','请选择电子书上传并等待文件上传完成!','info');
				return;
			}else
				isuploaded = true;
			var isbookVersion = $("#bookVersion").validatebox("isValid");
			var version1=$("#version").val();
			var version2=$("#bookVersion").val();
			if(version2!=null&&version2!=""){
				if(parseFloat(version1)<parseFloat(version2)){
					isbookVersion=isbookVersion&&true;
				}else{
					$.messager.alert('提示','新版本号要大于原版本号','info');
					isbookVersion=false;
					return;
				}
			}
			if(isuploaded&&isbookVersion){
				$("#ff").submit();
			}
		}
	</script>
  </head>
  <body style="background-color: #fff;">
  	<div style="padding:10px;">
	    <form id="ff" method="post" action="../ebook/uploadEbook.action" class="addspeaker_form">
	      <table border="0" cellpadding="4" cellspacing="1">
  			<tbody>
				<tr>
					<th class="lab">选择电子书</th>
					<td colspan="3">
					<div style="background-color: #eeeeee; padding: 2px;">
						<applet width="408" height="88" code="com.hcctech.bookshelf.applet.FileFtpApplet" archive="../ftp/FileFtpApplet.jar">
           					<param name="jnlp_href" value="../ftp/launch.jnlp"/>
           					<param name="targetDir" value="/res/ybook/ebook/"/>
           					<param name="callback" value="onUploadFinish"/>
           					<param name="filetype" value="zip">
       					</applet>
        			</div>
					</td>
				</tr>
				<tr>
					<th class="lab">电子书名称</th>
					<td colspan="3"><input id="bookName" readonly="readonly" disabled="disabled" value="${ebook.bookName }" name="bookName"  style="width: 408px;"/></td>
				</tr>
				<tr>
					<th class="lab">原版本号</th>
					<td colspan="3"><input id="version" readonly="readonly" value="${ebook.version_ }"  style="width: 408px;"/></td>
				</tr>
				<tr>
					<th class="lab">新版本号</th>
					<td colspan="3"><input id="bookVersion" name="bookVersion" class="easyui-numberbox" data-options="min:1.00,precision:2,required:true" style="width: 408px;"/></td>
				</tr>
			</tbody>
  		</table>
 		<div class="hr" width="100%"></div>
 		<input name="ebook.id" value="<%=request.getParameter("bookId") %>" type="hidden"/>
 		<input  name="ebook.bookName" value="${ebook.bookName }" type="hidden"/>
 		<input name="ebook.subject" value="${ebook.subject}" type="hidden"/>
 		<input name="ebook.serVersion" value="${ebook.serVersion}" type="hidden"/>
 		<input name="ebook.eduVersion" value="${ebook.eduVersion}" type="hidden"/>
 		<input name="ebook.bookCode" value="${ebook.bookCode}" type="hidden"/>
 		<input name="ebook.version" value="${ebook.version}" type="hidden"/>
 		<input name="ebook.bookKey" value="${ebook.bookKey}" type="hidden"/>
 		<input name="ebook.secret" value="${ebook.secret}" type="hidden"/>
 		<input type="hidden" name="ebook.bookPath" id="bookPath" />
 		<input type="hidden" name="ebook.bookSize" id="bookSize" />
 		<input type="hidden" name="bookSecret" id="bookSecret" value="${ebook.secret}" />
 		<a class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="saveEbook()">保&nbsp;&nbsp;存</a>
	  </form>
	</div>
  </body>
</html>

