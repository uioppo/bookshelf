
$(function(){
	$("#addSellDiv").dialog({
		closed:true,
		modal:true,
		width:610,
		height:360,
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				insertSell();
			}
		},{
			text:'取消',
			handler:function(){
				$('#addSellDiv').dialog('close');
			}
		}]
	});
	$("#editSellDiv").dialog({
		closed:true,
		modal:true,
		width:610,
		height:360,
		buttons:[{
			text:'修改',
			iconCls:'icon-ok',
			handler:function(){
				updateSell();
			}
		},{
			text:'取消',
			handler:function(){
				$('#editSellDiv').dialog('close');
			}
		}]
	});
	$("#special").html("折扣");
});

function loadSetSelling() {
	$("#setselling_list").datagrid( {
		loadMsg : '数据加载中请稍后……',
		width : '100%',
		height : $(document).height(),
		nowrap : false,
		striped : true,
		collapsible : false,
		url : path + '/admin/sellSetting/list.action',
		remoteSort : false,
		idField : 'id',
		columns : [ [ {
			title : '选择',
			field : 'id',
			checkbox : true
		}, {
			title : '促销名称',
			field : 'saleName',
			width : 145,
			sortable : true,
			align : 'center'
		},{
			title : '促销条件',
			field : 'priceLevel',
			width : 60,
			sortable : true,
			align : 'center'
		},{
			title : '优惠/折扣',
			field : 'priceSetting',
			width : 65,
			sortable : true,
			align : 'center',
			formatter : function(value, row, index) {
				if (1 == row.saleType) {
					return row.priceSetting + "元";
				} else {
					return row.priceSetting + "折";
				}
			}
		},{
			title : '开始时间',
			field : 'startTime_',
			width : 120,
			align : 'center'
		}, {
			title : '结束时间',
			field : 'endTime_',
			width : 120,
			align : 'center'
		}, {
			title : '创建时间',
			field : 'createTime_',
			width : 130,
			align : 'center'
		}, {
			title : '描述',
			field : 'descrption',
			width : 300,
			align : 'center'
		}, {
			title : '创建人',
			field : 'operator',
			width : 100,
			align : 'center'
		},{
			title : '操作',
			field : 'cz',
			width : 90,
			align : 'center',
			formatter:function(value,row,index){
				var s = "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='edit_sell("+row.id+")'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px; '>编辑促销</span></span><a/>";
				return s;
			}
		} ] ],
		pagination : true,
		rownumbers : true,
		toolbar : "#setselling_bar"
	});
	var p = $('#setselling_list').datagrid('getPager');
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
	function delete_sell(){
		var id = [];
		var rows = $('#setselling_list').datagrid('getSelections');
		if(rows==null || rows.length==0){
			$.messager.alert('温馨提示','请选择要删除的记录','info');
			return;
		}
		$.messager.confirm('确认','确人删除',function(f){
			if(f){
				for(var i = 0;i<rows.length;i++){
					id.push(rows[i].id);
				}
				jQuery.ajax({
				type:"post",
				url:path+"/admin/sellSetting/delete.action",
				data:"id="+id.toString(),
				async:false,
				success:function(data){
					$('#setselling_list').datagrid('clearSelections'); 
					$('#setselling_list').datagrid('reload'); 
				}
				});
			}
		});
	}
	function insertSell(){
		var saleName = $("#saleName").val();
		var saleType = $("#saleType").val();
		var startTime = $("#startTime").datebox('getValue');
		var stopTime = $("#stopTime").datebox('getValue');
		var priceSetting = $("#priceSetting").numberbox('getValue');
		var priceLevel = $("#priceLevel").numberbox('getValue');
		var descrption = $("#descrption").val();
		
		if(saleName==null || saleName=="" || saleName==undefined){
			$.messager.alert("温馨提醒","促销名称不能为空!","info");
			return;
		}
		
		if(priceSetting!=null && priceSetting!=""){
			if(0==saleType){
				if(parseFloat(priceSetting) < 0 || parseFloat(priceSetting) > 9.9){
					$.messager.alert("温馨提醒","折扣设置范围限制0.0---9.9!","info");
					return ;
				}
			}else {
				if(parseFloat(priceSetting) < 0 ){
					$.messager.alert("温馨提醒","优惠设置不能小于0","info");
					return ;
				}
				if(parseFloat(priceSetting) > parseFloat(priceLevel)){
					$.messager.alert("温馨提醒","优惠设置不能大于促销条件","info");
					return ;
				}
				
			}
		}else {
			if(0==saleType){
				$.messager.alert("温馨提醒","折扣设置不能为空!","info");
				return;
			}else {
				$.messager.alert("温馨提醒","优惠设置不能为空!","info");
				return;
			}
		}
		
		if(priceLevel==null || priceLevel=="" || priceLevel==undefined){
			$.messager.alert("温馨提醒","促销条件不能为空!","info");
			return;
		}
	
		if(startTime==null || startTime=="" || startTime==undefined){
			$.messager.alert("温馨提醒","开始时间不能为空!","info");
			return;
		}
		if(stopTime==null || stopTime=="" || stopTime==undefined){
			$.messager.alert("温馨提醒","结束时间不能为空!","info");
			return;
		}
		if(stopTime<=startTime){
			$.messager.alert("温馨提醒","结束时间必须大于开始时间!","info");
			return;
		}
		startTime=startTime+":00";
		stopTime=stopTime+":00";
		jQuery.ajax({
			type:"post",
			url:path+"/admin/sellSetting/save.action",
			data:{"sell.saleName":saleName,"sell.saleType":saleType,"sell.startTime":startTime,"sell.endTime":stopTime,"sell.priceSetting":priceSetting,"sell.priceLevel":priceLevel,"sell.descrption":descrption},
			dataType:"json",
			async:false,
			success:function(data){

				if(data.flag == false || data.flag == 'false'){
					$.messager.alert("温馨提醒","操作有误，请返回。","info");
					return;
				}else{
					closeSellDiv();
					$("#setselling_list").datagrid("reload");
				}
			}
		});
	}
	function edit_sell(id){
		jQuery.ajax({
			type:"post",
			url:path+"/admin/sellSetting/findById.action",
			data:{"sell.id":id},
			async:false,
			success:function(data){
				if(data.sell.saleType == 0){
					$("#editSpecial").html("折扣");
					$("#zhekou").val(data.sell.priceSetting);
					$("#editPriceSetting").numberbox({
						min:0.0,
						max:null,
						precision:1,
						suffix:'折'
						
					});
				}else{
					$("#editSpecial").html("优惠");
					$("#youhui").val(data.sell.priceSetting);
					$("#editPriceSetting").numberbox({
						min:0.0,
						max:null,
						precision:2,
						suffix:'元'
					});
				}
				$("#editSaleName").val(data.sell.saleName);
				$("#editSaleType").val(data.sell.saleType);
				$("#editStartTime").datebox('setValue',data.sell.startTime_);
				$("#editStopTime").datebox('setValue',data.sell.endTime_);
				$("#editPriceSetting").numberbox("setValue",data.sell.priceSetting);
				$("#editPriceLevel").numberbox("setValue",data.sell.priceLevel);
				$("#editDescrption").val(data.sell.descrption);
				$("#sellId").val(data.sell.id);
				$("#editSellDiv").dialog("open");
			}
		});
	}
	function updateSell(){
		var saleName = $("#editSaleName").val();
		var saleType = $("#editSaleType").val();
		var startTime = $("#editStartTime").datebox('getValue');
		var stopTime = $("#editStopTime").datebox('getValue');
		var priceSetting = $("#editPriceSetting").numberbox('getValue');
		var priceLevel = $("#editPriceLevel").numberbox('getValue');
		var descrption = $("#editDescrption").val();
		var id = $("#sellId").val();
		
		if(saleName==null || saleName=="" || saleName==undefined){
			$.messager.alert("温馨提醒","促销名称不能为空!","info");
			return;
		}
		
		if(priceSetting!=null && priceSetting!=""){
			if(0==saleType){
				if(parseFloat(priceSetting) < 0 || parseFloat(priceSetting) > 9.9){
					$.messager.alert("温馨提醒","折扣设置范围限制0.0---9.9!","info");
					return ;
				}
			}else {
				if(parseFloat(priceSetting) < 0 ){
					$.messager.alert("温馨提醒","优惠设置不能小于0","info");
					return ;
				}
				if(parseFloat(priceSetting) > parseFloat(priceLevel)){
					$.messager.alert("温馨提醒","优惠设置不能大于促销条件","info");
					return ;
				}
				
			}
		}else {
			if(0==saleType){
				$.messager.alert("温馨提醒","折扣设置不能为空!","info");
				return;
			}else {
				$.messager.alert("温馨提醒","优惠设置不能为空!","info");
				return;
			}
		}
		
		if(priceLevel==null || priceLevel=="" || priceLevel==undefined){
			$.messager.alert("温馨提醒","促销条件不能为空!","info");
			return;
		}
	
		
		if(startTime==null || startTime=="" || startTime==undefined){
			$.messager.alert("温馨提醒","开始时间不能为空!","info");
			return;
		}
		if(stopTime==null || stopTime=="" || stopTime==undefined){
			$.messager.alert("温馨提醒","结束时间不能为空!","info");
			return;
		}
		if(stopTime<=startTime){
			$.messager.alert("温馨提醒","结束时间必须大于开始时间!","info");
			return;
		}
		startTime=startTime+":00";
		stopTime=stopTime+":00";
		jQuery.ajax({
			type:"post",
			url:path+"/admin/sellSetting/update.action",
			data:{"sell.saleName":saleName,"sell.saleType":saleType,"sell.startTime":startTime,"sell.endTime":stopTime,"sell.priceSetting":priceSetting,"sell.priceLevel":priceLevel,"sell.descrption":descrption,"sell.id":id},
			dataType:"json",
			async:false,
			success:function(data){
				closeEditSellDiv();
				$("#setselling_list").datagrid("reload");
			}
			
			});
	}
	function closeSellDiv(){
			$('#addSellDiv').dialog('close');
		}
	function closeEditSellDiv(){
		$("#youhui").val("");
		$("#zhekou").val("");
		$('#editSellDiv').dialog('close');
	}
	function checkType(){
		var saleType = $("#saleType").val();
		if(saleType == 0){
			$("#priceSetting").numberbox("setValue","");
			$("#priceSetting").attr("title","范围限制0.0---9.9");
			$("#special").html("折扣");
			$("#priceSetting").numberbox({
				min:0.0,
				max:null,
				precision:1,
				suffix:'折'
			});
		}else{
			$("#priceSetting").numberbox("setValue","");
			$("#priceSetting").attr("title","");
			$("#special").html("优惠");
			$("#priceSetting").numberbox({
				min:0.00,
				max:null,
				precision:2,
				suffix:'元'
			});
		}
	}
	function checkEditType(){
		var saleEditType = $("#editSaleType").val();
		if(parseInt(saleEditType) == 0){
			$("#editPriceSetting").numberbox("setValue",$("#zhekou").val());
			$("#editPriceSetting").attr("title","范围限制0.0---9.9");
			$("#editSpecial").html("折扣");
			$("#editPriceSetting").numberbox({
				min:0.0,
				max:null,
				precision:1,
				suffix:'折'
			});
		}else{
			$("#editPriceSetting").numberbox("setValue",$("#youhui").val());
			$("#editPriceSetting").attr("title","");
			$("#editSpecial").html("优惠");
			$("#editPriceSetting").numberbox({
				min:0.00,
				max:null,
				precision:2,
				suffix:'元'
			});
		}
	}
	function add_sell(){
		$("#saleName").val("");
		$("#saleType").val("");
		var now = new Date();
        var year = now.getFullYear();       //年
        var month = now.getMonth() + 1;     //月
        var day = now.getDate();   			//日
        var str=year+"-"+month+"-"+day;
		$("#startTime").datebox('setValue',str+" 00:00");
		$("#stopTime").datebox('setValue',str+" 23:59");
		$("#priceSetting").numberbox("setValue","");
		$("#priceLevel").numberbox("setValue","");
		$("#operator").val("");
		$("#descrption").val("");
		$("#priceSetting").numberbox({
			min:0.0,
			max:null,
			precision:1,
			suffix:'折'
		});
		$("#special").html("折扣");
		$("#addSellDiv").dialog("open");
	}