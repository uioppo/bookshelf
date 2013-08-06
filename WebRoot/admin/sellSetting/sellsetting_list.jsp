<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>促销设置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path %>/admin/js/themes/icon.css">
	<script type="text/javascript">var path = "<%=path %>";</script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/js/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=path %>/admin/sellSetting/js/sellsetting.js"></script>
	<script type="text/javascript">
		$(function(){
			$('.easyui-datetimebox').datetimebox({
				showSeconds:false
			});
			loadSetSelling();
		});
	</script>
  </head>
  
  <body style="margin: 0px; padding: 0px; background: #fff;">
     	<div id="setselling_bar">
     	<a href="javascript:;" onclick="add_sell()" class="easyui-linkbutton" icon="icon-add" plain="true" >添加促销</a>&nbsp;
 		<a href="javascript:;" onclick="delete_sell()" class="easyui-linkbutton" icon="icon-remove" plain="true" >删除促销</a>&nbsp;
     	</div>
     	<table id="setselling_list"></table>
     	<div style="display: none;">
     	<div id="addSellDiv" title="添加促销" style="padding-top: 20px;">
     		<table  border="0" cellpadding="1" cellspacing="1" style="width: 100%;" >
     		<tr>
		 		<td width="100" height="25" align="right">促销名称&nbsp;</td>
		 		<td height="25" colspan="3"><input type="text" id="saleName" size="20" class="easyui-validatebox" style="width:153px" validType="length[1,50]"/></td>
		 	</tr>
		 	<tr>
		 		<td width="100" height="25" align="right">促销条件&nbsp;</td>
		 		<td height="25" colspan="3"><input type="text" id="priceLevel"  size="20" class="easyui-numberbox"  min="0.0" precision="2" style="width:153px" required="true"/>元(<font color="red">当满足促销条件的金额时，可享受以下促销设置</font>)</td>
		 	</tr>
		 	<tr>
		 		<td width="100" height="25" align="right">促销类型&nbsp;</td>
		 		<td width="200" height="25">
				<select id="saleType" style="width: 160px" onchange="checkType();" >
					<option value="0" style="width: 20px;">折扣</option>
					<option value="1">优惠</option>
				</select>
				</td>
				<td width="100" height="25" align="right"><label id="special"></label>设置&nbsp;</td>
		 		<td width="200" height="25"><input type="text" id="priceSetting" class="easyui-numberbox" required="true" style="width:153px"/></td>
		 	</tr>
		 	<tr>
		 		<td width="100" height="25" align="right">开始时间&nbsp;</td>
		 		<td width="200" height="25"><input type="text" id="startTime" class="easyui-datetimebox" editable=false style="width:160px"/></td>
		 		<td width="100" height="25" align="right">结束时间&nbsp;</td>
		 		<td width="200" height="25"><input type="text" id="stopTime" class="easyui-datetimebox" editable=false  style="width:160px"/></td>
		 	</tr>
		 	<tr>
		 		<td width="100" height="25" align="right">描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;</td>
		 		<td height="25" colspan="3">
		 		<textarea rows="8" cols="52" id="descrption" style="width:456px;height: 130px;"></textarea>
		 		</td>
		 	</tr>
		 	<tr>
		 	<!--<td colspan="4" align="center" height="25">
		 	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="insertSell()">确定</a>
		 	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeSellDiv()">关闭</a>
		 	</td>
		 	--></tr>
     		</table>		 	
     	</div>
     	<div id="editSellDiv" title="编辑促销" style="padding-top: 20px;">
     	<input type="hidden" id="sellId"/>
     	<input type="hidden" id="zhekou"/>
     	<input type="hidden" id="youhui"/>
     	<table border="0" cellpadding="1" cellspacing="1" style="width: 100%;">
     		<tr>
		 		<td width="100" height="25" align="right">促销名称&nbsp;</td>
		 		<td height="25" colspan="3"><input type="text" id="editSaleName" size="20" class="easyui-validatebox"  validType="length[1,50]" style="width:153px"/></td>
		 	</tr>
		 	<tr>
		 		<td width="100" height="25" align="right">促销条件&nbsp;</td>
		 		<td height="25" colspan="3"><input type="text" id="editPriceLevel" class="easyui-numberbox" required="true" min="0.00" precision="2" style="width:153px" size="20"/>元(<font color="red">当满足促销条件的金额时，可享受以下促销设置</font>)</td>
		 	</tr>
		 	<tr>
		 		<td width="100" height="25" align="right">促销类型&nbsp;</td>
		 		<td width="200" height="25">
					<select id="editSaleType" style="width:160px"  onchange="checkEditType();">
						<option value="0" style="width: 20px;">折扣</option>
						<option value="1">优惠</option>
					</select>
				</td>
				<td width="100" height="25" align="right"><label id="editSpecial"></label>设置&nbsp;</td>
		 		<td width="200" height="25"><input type="text" id="editPriceSetting" class="easyui-numberbox" required="true" style="width:153px" size="15" /></td>
		 	</tr>
		 	<tr>
		 		<td width="100" height="25" align="right">开始时间&nbsp;</td>
		 		<td width="200" height="25"><input type="text" id="editStartTime" class="easyui-datetimebox" editable="false" style="width:160px"/></td>
		 		<td width="100" height="25" align="right" >结束时间&nbsp;</td>
		 		<td width="200" height="25"><input type="text" id="editStopTime" class="easyui-datetimebox" editable=false style="width:160px"/></td>
		 	</tr>		 	
		 	<tr>
		 		<td width="100" height="25" align="right">描&nbsp;&nbsp;&nbsp;&nbsp;述&nbsp;</td>
		 		<td height="25" colspan="3">
		 		<textarea cols="52" id="editDescrption" style="width:456px; height: 130px;"></textarea>
		 		</td>
		 	</tr>
		 	<tr>
		 	<!--<td colspan="4" align="center" height="25">
		 	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateSell()">修改</a>
		 	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="closeEditSellDiv()">关闭</a>
		 	</td>
		 	--></tr>
     	</table>
     	</div>
     	</div>
  </body>
</html>
