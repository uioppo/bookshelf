<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<% String path = request.getContextPath(); %>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!doctype html>
<html>
  <head>
    <title><s:property value='loadBsProducts.productName'/>  -- 商品信息</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
    <!-- easyui -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/js/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/admin/product/css/main.css">
	<script type="text/javascript" src="<%=path%>/admin/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=path%>/admin/js/jquery.easyui.min.js"></script>
  </head>
  
  <body style="margin: 0px; padding: 0px;background-color: #FFFFFF;margin-left: auto; margin-right: auto;">
    <div style="padding:10px; margin-left: auto; margin-right: auto; width: 820px;">
    <form class="addspeaker_form">
	      <table border="0" cellpadding="4" cellspacing="1" style="width: 800px;">
  			<tbody>
				<tr>
					<th class="lab">商品名称</th>
					<td><s:property value='loadBsProducts.productName'/></td>
					<th class="lab">价格</th>
					<td><s:property value='loadBsProducts.price_'/></td>
				</tr>
				<tr>
					<th class="lab">发布时间</th>
					<td>
						<s:property value='loadBsProducts.publishTime_'/>
					</td>
					<th class="lab">使用期限</th>
					<td >
						<s:if test="loadBsProducts.usePeriod == 1">
								一个月
						</s:if>
						<s:elseif test="loadBsProducts.usePeriod == 3">
								三个月
						</s:elseif>
						<s:elseif test="loadBsProducts.usePeriod == 6">
								半年
						</s:elseif>
						<s:elseif test="loadBsProducts.usePeriod == 12">
								一年
						</s:elseif>
						<s:else>
								永久
						</s:else>
					</td>
					
					
				</tr>
				<tr>
					<th class="lab">学段</th>
					<td>
						<s:if test="loadBsProducts.schoolStage == 1">
							小学
						</s:if>
						<s:elseif test="loadBsProducts.schoolStage == 2">
							初中
						</s:elseif>
						<s:else>
							高中
						</s:else>
					</td>
					<th class="lab">学科</th>
					<td>
						<s:property value='loadBsProducts.subject'/>
					</td>
					
				</tr>
				<tr>
					<th class="lab">年级</th>
					<td>
						<s:property value='loadBsProducts.grade'/>
					</td>
					<th class="lab">册</th>
					<td>
						<s:property value='loadBsProducts.volume'/>
					</td>
					
					
				</tr>
				<tr>
					<th class="lab">适用对象</th>
					<td >
						<s:property value='loadBsProducts.applyObject'/>
					</td>
					<th class="lab">商品类型</th>
					<td>
						<s:if test="%{loadBsProducts.productType == 0}">
							数字教辅
						</s:if>
						<s:else>
							数字教材
						</s:else>					
				</tr>
				<tr>
					<th class="lab">语种</th>
					<td><s:property value='loadBsProducts.language'/></td>
					<th class="lab">页数</th>
					<td>
						<s:property value='loadBsProducts.pageNumber'/>
					</td>
			 		
			 		
				</tr>
				<tr>
					<th class="lab">开发者</th>
					<td><s:property value='loadBsProducts.author'/></td>
					<th class="lab">出版社</th>
					<td><s:property value='loadBsProducts.publishing'/></td>
					
					
				</tr>
				<tr>
					<th class="lab">版本</th>
					<td><s:property value='loadBsProducts.edition'/>年</td>
					<th class="lab">对应教材版本</th>
					<td><s:property value='loadBsProducts.teachVersion'/></td>
				</tr>
				<tr>
					<th class="lab">缩略图</th>
					<td colspan="4" >
        				<div style="padding: 2px; z-index: -1;" id="preview">
        					<img src="<s:property value='fileDomain'/><s:property value='loadBsProducts.thumbnail'/>" width="280px" height="280px"/>
        				</div>
        			</td>
				</tr>
				<tr>
					<th class="lab">试读片段</th>
					<td colspan="3" >
						<input type="hidden" name="bsProducts.tryUrl" id="tryUrl" value="<s:property value='loadBsProducts.tryUrl'/>">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'">试读</a>
        			</td>
				</tr>
				<tr>
					<th class="lab1" colspan="5">内容简介</th>
				</tr>
				<tr>
					<td colspan="5">
						<s:property value='loadBsProducts.description' escape="false"/>
					</td>
				</tr>
				<tr>
					<th class="lab1" colspan="5">目录</th>
				</tr>
				<tr>
					<td colspan="5">
						<s:property value='loadBsProducts.catalog' escape="false"/>
					</td>
				</tr>	
			</tbody>
  		</table>
 		<div class="hr" width="100%"></div>
	  </form>
	</div>
  </body>
</html>
