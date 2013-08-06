function loadOrderList(){
		$('#orderlist').datagrid({
		loadMsg:'数据加载中请稍后……', 
		width:'100%',
		height:$(document).height(),
		nowrap: false,
		striped: true,
		collapsible:false,
		singleSelect:true,
		url:path+'/admin/order/findAllOrder.action',
		idField:'orderId',
		columns:[[
            {title:'订单号',field:'orderId',width:180,align:'center'},
            {title:'下单用户',field:'bsWebUser_.wuEmail',width:180,align:'center',formatter:function(value,row,index){
            	if(row.bsWebUser_!=null){return row.bsWebUser_.wuEmail;}
            	else{return "";}
            }},
            {title:'下单时间',field:'orderTime_',width:180,sortable:true,align:'center'},
	        {title:'订单价格(元)',field:'orderMoney_',width:120,align:'center'},
	        {title:'已优惠(元)',field:'privileged_',width:120,align:'center'},
	        {title:'订单状态',field:'orderState',width:111,align:'center',formatter:function(value,row,index){
	        	if(row.orderState==0){
	        		return "<font color='red'>未付款</font>";
	        	}else if(row.orderState==1){
	        		return "<font color='green'>已付款</font>";
	        	}else if(row.orderState==2){
	        		return "<font>已取消</font>";
	        	}else{
	        		return "";
	        	}
	        }},
	        {title:'操作',field:'a',width:135,align:'center',formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick='showOrderDetail(\""+row.orderId+"\")' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search' style='padding-left: 20px; '>查看详细</span></span></a>";
	        }}
		]],
		pagination:true,
		rownumbers:true,
		toolbar:"#orderlist_bar"
	});
	
	var p = $('#orderlist').datagrid('getPager');
	$(p).pagination({
		pageSize: 10,//每页显示的记录条数，默认为10  
		pageList: [5,10,15],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh:function(pageNumber,pageSize){ 
			$(this).pagination('loading'); 
 			$(this).pagination('loaded');
		}
	});
}

function searcher(value,name){
	var orderState=$("input[name='orderState']:checked").val();
	$('#orderlist').datagrid("load",{
		searchValue:value,
		searchName:name,
		orderState:orderState
	});
}
function serchStatus(){
	var thisName=$('#ss').searchbox('getName');
	var thisValue=$('#ss').searchbox('getValue');
	var orderState=$("input[name='orderState']:checked").val();
	$('#orderlist').datagrid("load",{
		searchValue:thisValue,
		searchName:thisName,
		orderState:orderState
	});
}

function showOrderDetail(orderId){
	jQuery.ajax({
		type:"post",
		url:path+"/admin/order/findOrderById.action",
		data:{"orderId":orderId},
		dataType:"json",
		async:false,
		success:function(json){
			showProductsByOrderId(json);
		}
	});
}

function showProductsByOrderId(json){
	$("#funTable tbody").html("");
	$("#funTable tbody").append("<tr><td>订单号:</td><td colspan='3'>"+json.bsOrder.orderId+"</td></tr>");
	if(json.bsOrder.bsWebUser_ != null){
		$("#funTable tbody").append("<tr><td>下单用户:</td><td>" + json.bsOrder.bsWebUser_.wuEmail + "</td></tr>");
	}else {
		$("#funTable tbody").append("<tr><td>下单用户:</td><td></td></tr>");
	}
	$("#funTable tbody").append("<tr><td>下单时间:</td><td colspan='3'>"+json.bsOrder.orderTime_+"</td></tr>");
	$("#funTable tbody").append("<tr>");
	$("#funTable tbody").append("<td>订单价格:</td>");
	$("#funTable tbody").append("<td>"+json.bsOrder.orderMoney_+"&nbsp;元"+"</td>");
	$("#funTable tbody").append("<td>优惠价格:</td>");
	$("#funTable tbody").append("<td>"+json.bsOrder.privileged_+"&nbsp;元"+"</td>");
	$("#funTable tbody").append("</tr>");
	
	$("#funTable tbody").append("<tr>");
	$("#funTable tbody").append("<td>订单状态:</td>");
	if(json.bsOrder.orderState==1){
		$("#funTable tbody").append("<td><font color='green'>已付款</font></td>");
	}else if(json.bsOrder.orderState==0){
		$("#funTable tbody").append("<td ><font color='red'>未付款</font></td>");
	}else if(json.bsOrder.orderState==2){
		$("#funTable tbody").append("<td>已取消</td>");
	}
	$("#funTable tbody").append("</tr>");
	$("#funTable tbody").append("<tr>");
	$("#funTable tbody").append("<td colspan='6' align='center' valign='middle' style='border-bottom:1px solid black;border-top:1px solid black'>商品列表</td>");
	$("#funTable tbody").append("</tr>");
	//商品列表
	$("#funTable tbody").append("<tr><td colspan='6'><table>");
	$("#funTable tbody").append("<tr>");
	$("#funTable tbody").append("<td width='121' align='center' valign='middle'>商品编号</td>");
	$("#funTable tbody").append("<td width='121' align='center' valign='middle'>商品名称</td>");
	$("#funTable tbody").append("<td width='121' align='center' valign='middle'>商品类型</td>");
	$("#funTable tbody").append("<td width='121' align='center' valign='middle'>商品价格</td>");
	$("#funTable tbody").append("<td width='121' align='center' valign='middle'>已优惠</td>");
	$("#funTable tbody").append("<td width='121' align='center' valign='middle'>数量</td>");
	jQuery(json.bsOrder.bsOrderProducts_).each(function(i){
		$("#funTable tbody").append("</tr>");
		$("#funTable tbody").append("<tr>");
		$("#funTable tbody").append("<td align='center' valign='middle'>"+this.bsProducts.bookCode+"</td>");
		$("#funTable tbody").append("<td align='center' valign='middle'>"+this.bsProducts.productName+"</td>");
		if(this.bsProducts.productType==0){
			$("#funTable tbody").append("<td align='center' valign='middle'>数字教辅</td>");
		}else if(this.bsProducts.productType==1){
			$("#funTable tbody").append("<td align='center' valign='middle'>数字教材</td>");
		}
		$("#funTable tbody").append("<td align='center' valign='middle'>"+this.buyPrice_+"</td>");
		$("#funTable tbody").append("<td align='center' valign='middle'>"+this.privileged_+"</td>");
		$("#funTable tbody").append("<td align='center' valign='middle'>"+this.buyCount+"</td>");
		$("#funTable tbody").append("</tr>");
	});
	$("#funTable tbody").append("</table></td></tr>");
	
	$('#orderDetail').dialog({
		buttons:[{
		text:'关闭',
		handler:function(){
			$('#orderDetail').dialog('close');
		}
	}]});
}
