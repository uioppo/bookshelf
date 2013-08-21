<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%String path = request.getContextPath();%>
<!doctype html>
<html>
  <head>
    <title>修改商品</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <!-- easyui -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/product/css/main.css">
	<script type="text/javascript" src="<%=path%>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<!-- kindeditor -->
	<script type="text/javascript" src="<%=path%>/js/kindeditor-min.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=path%>/js/lang/zh_CN.js" charset="utf-8"></script>
	<script type="text/javascript" src="<%=path%>/admin/product/js/editProduct.js"></script>
	<script type="text/javascript">
		var path = "<%=path%>";
		
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
			$("#t1").hide();
			$("#show").bind("click",function () {
				if($("#t1").is(":hidden")){
					this.className = "l-btn-text icon-sub lab";
			     	$("#t1").show();
			     	$("#show").html("<span class='l-btn-left'><span class='l-btn-text' style='padding-left: 20px; '>收起电子书属性</span></span>");
				}else {
					this.className = "l-btn-text icon-plus lab";
			     	$("#t1").hide();
			     	$("#show").html("<span class='l-btn-left'><span class='l-btn-text' style='padding-left: 20px; '>查看电子书属性</span></span>");
				}
			 });
		});
		
		$(function (){
			createRichEditor();
			var schoolStage = <s:property value='loadBsProducts.schoolStage'/> ;
			if(schoolStage == 1){
				$("#schoolStage").val("小学");
			}else if(schoolStage == 2){
				$("#schoolStage").val("初中");
			}else {
				$("#schoolStage").val("高中");
			}
			var productType = <s:property value='loadBsProducts.productType'/> ;
			if(productType == 1){
				$("#productType").val("数字教材");
			}else {
				$("#productType").val("数字教辅");
			}
			$('#usePeriod').combobox("setValue","<s:property value='loadBsProducts.usePeriod' escape='false'/>");
		});	
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px;background-color: #FFFFFF">
    <div style="padding:10px;">
    <form id="ff" method="post" class="addspeaker_form">
    <input type="hidden" name="bsProducts.productId" id="productId" value="<s:property value='loadBsProducts.productId'/>">
    <input type="hidden" name="bsProducts.status" id="status" value="<s:property value='loadBsProducts.status'/>">
	      <table border="0" cellpadding="4" cellspacing="1">
  			<tbody>
				<tr>
					<th class="lab">商品名称</th>
					<td><input id="productName" name="bsProducts.productName" class="easyui-validatebox" required="true" validType="length[1,50]" value="<s:property value='loadBsProducts.productName'/>" autofocus="autofocus"/></td>
					<th class="lab">价格</th>
					<td><input id="price" name="bsProducts.price" class="easyui-numberbox" data-options="min:0,precision:2,required:true" value="<s:property value='loadBsProducts.price'/>"/></td>
				</tr>
				<tr>
					<th class="lab">发布时间</th>
					<td>
						<input id="publishTime" style="width: 180px;" class="easyui-datebox" required="true" editable=false name="bsProducts.publishTime" value="<s:property value='loadBsProducts.publishTime_'/>"/>
					</td>
					<th class="lab">使用期限</th>
					<td >
						<select id="usePeriod" name="bsProducts.usePeriod" style="width: 140px;background-color: #ffffff;" class="easyui-combobox" panelHeight="auto" editable=false>
							<option value="1">一个月</option>
							<option value="3">三个月</option>
							<option value="6">六个月</option>
							<option value="12">十二个月</option>
							<option value="1200">永久</option>
						</select>
					</td>
					
					
				</tr>
				<tr>
					<th class="lab">下载次数</th>
					<td>
						<input id="downNumber"  name="bsProducts.downNumber" class="easyui-numberbox" data-options="min:0,precision:0,required:true"/>
					</td>
					<th class="lab">&nbsp;</th>
					<td >
					</td>
					
					
				</tr>
				<tr>
					<td colspan="4"  id="show" class="l-btn-text icon-plus lab" style="text-align: left;cursor: pointer;background-color: #999">
						<span class='l-btn-left'><span class='l-btn-text' style='padding-left: 20px;'>查看电子书属性</span></span>
					</td>
				</tr>
				
				<tr>
				<td colspan="4">
				<table id="t1">
				<tr>
					<th class="lab">学段</th>
					<td>
						<input type="text" id="schoolStage" readonly="readonly">
					</td>
					<th class="lab">学科</th>
					<td>
						<input type="text" name="bsProducts.subject" value="<s:property value='loadBsProducts.subject'/>" readonly="readonly"/>
					</td>
					
					
				</tr>
				<tr>
					<th class="lab">年级</th>
					<td>
						<input type="text" id="grade" name="bsProducts.grade" value="<s:property value='loadBsProducts.grade'/>" readonly="readonly">
					</td>
					<th class="lab">册</th>
					<td>
						<input type="text" name="bsProducts.volume" id="volume" value="<s:property value='loadBsProducts.volume'/>" readonly="readonly">
					</td>
					
					
				</tr>
				<tr>
					<th class="lab">适用对象</th>
					<td >
						<input type="text" name="bsProducts.applyObject" id="applyObject" value="<s:property value='loadBsProducts.applyObject'/>" readonly="readonly">
					</td>
					<th class="lab">商品类型</th>
					<td>
						<input type="text" id="productType" readonly="readonly">
					</td>
				</tr>
				<tr>
					<th class="lab">语种</th>
					<td><input id="language" name="bsProducts.language"  value="<s:property value='loadBsProducts.language'/>" readonly="readonly"/></td>
			 		<th class="lab">页数</th>
					<td>
						<input id="pageNumber" name="bsProducts.pageNumber" value="<s:property value='loadBsProducts.pageNumber'/>" readonly="readonly"/>
					</td>
			 		
			 		
				</tr>
				<tr>
					<th class="lab">开发者</th>
					<td><input id="author" name="bsProducts.author" value="<s:property value='loadBsProducts.author'/>" readonly="readonly"/></td>
					<th class="lab">出版社</th>
					<td><input id="publishing" name="bsProducts.publishing" value="<s:property value='loadBsProducts.publishing'/>" readonly="readonly"/></td>
					
					
				</tr>
				<tr>
					<th class="lab">版本</th>
					<td> <input id="edition" name="bsProducts.edition" value="<s:property value='loadBsProducts.edition'/>年" readonly="readonly"/></td>
					<th class="lab">对应教材版本</th>
					<td> 
						<input id="teachVersion" name="bsProducts.teachVersion" type="text" value="<s:property value='loadBsProducts.teachVersion'/>" readonly="readonly"/>
					</td>
				</tr>
				</table>
				</td>
				</tr>
				<tr>
					<th class="lab">缩略图</th>
					<td colspan="4" >
						<input type="hidden" name="bsProducts.thumbnail" id="thumbnail" value="<s:property value='loadBsProducts.thumbnail'/>">
<!--						<div style="background-color: #eeeeee; padding: 2px; z-index: -1;display: none;" id="uploadThumbnail">-->
<!--							<applet width="375" height="88"  code="com.hcctech.bookshelf.applet.FileFtpApplet" archive="../ftp/FileFtpApplet.jar">-->
<!--            					<param name="jnlp_href" value="../ftp/launch.jnlp"/>-->
<!--            					<param name="targetDir" value="/images/"/>-->
<!--            					<param name="callback" value="onImgUploadFinish"/>-->
<!--            					<param name="filetype" value="image">-->
<!--        					</applet>-->
<!--        					<font style="font-size:12px;">尺寸：280*280,图片类型：png、jpg、jpeg、gif</font>-->
<!--        				</div>-->
        				<div style="background-color: #eeeeee; padding: 2px; z-index: -1;" id="preview">
        					<img src="<s:property value='fileDomain'/><s:property value='loadBsProducts.thumbnail'/>" width="280px" height="280px"/>
        					<a class="easyui-linkbutton" href="javascript:void(0)" onclick="editImg()">更&nbsp;&nbsp;改</a>
        				</div>
        			</td>
				</tr>
				<!-- tr>
					<th class="lab">试读片段</th>
					<td colspan="3" >
						<input type="hidden" name="bsProducts.tryUrl" id="tryUrl" value="<s:property value='loadBsProducts.tryUrl'/>">
						<div style="background-color: #eeeeee; padding: 2px;">
							<applet width="408" height="88" code="com.hcctech.bookshelf.applet.FileFtpApplet" archive="../ftp/FileFtpApplet.jar">
            					<param name="jnlp_href" value="../ftp/launch.jnlp"/>
            					<param name="targetDir" value="/res/smaple/"/>
            					<param name="callback" value="onZipUploadFinish"/>
            					<param name="filetype" value="zip">
        					</applet>
        				</div>
        			</td>
				</tr-->
				<tr>
					<th class="lab1" colspan="5">内容简介</th>
				</tr>
				<tr>
					<td colspan="5"><textarea id="description" name="bsProducts.description" style="width: 100%;" class="easyui-validatebox">
					<s:property value='loadBsProducts.description' escape="false"/>
					</textarea></td>
				</tr>
				<tr>
					<th class="lab1" colspan="5">目录</th>
				</tr>
				<tr>
					<td colspan="5"><textarea id="catalog" name="bsProducts.catalog" style="width: 100%;" class="easyui-validatebox">
					<s:property value='loadBsProducts.catalog' escape="false"/>
					</textarea></td>
				</tr>	
			</tbody>
  		</table>
 		<div class="hr" width="100%"></div>
 		<a class="easyui-linkbutton" iconCls="icon-save" href="javascript:void(0)" onclick="editProduct()" id="save">保&nbsp;&nbsp;存</a>
	  </form>
	</div>
  </body>
</html>
