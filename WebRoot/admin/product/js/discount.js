$(function(){
	loadProduct(1);
	$('#schoolStage').combobox({
		onSelect:loadSubject
	});
	jQuery("#setDisount").dialog({
		closed:true,
		modal:true,
		title:'特价设置',
		width:360,
		height:240,
		resizable:true,
		onOpen:function(){
			$('#ok').linkbutton({disabled:false});
		},
		onBeforeClose:function(){
			$("#vipPrice").val("");
			$("#downPrice").val("");
		},
		buttons:[{
			id : 'ok',
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				setPrice();
			}
		},{
			text:'取消',
			handler:function(){
				$("#vipPrice").val("");
				$("#downPrice").val("");
				$("#startTime").datebox("setValue","");
				$("#endTime").datebox("setValue","");
				$('#setDisount').dialog('close');
			}
		}]
	}); 
	
	jQuery("#setBatchDisount").dialog({
		closed:true,
		modal:true,
		title:'批量特价设置',
		width:330,
		height:180,
		resizable:true,
		onOpen:function(){
			$('#ok').linkbutton({disabled:false});
		},
		buttons:[{
			id : 'ok',
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				setBatchPrice();
			}
		},{
			text:'取消',
			handler:function(){
				$("#bDiscount").numberbox('setValue',"");
				$("#bStartTime").datebox("setValue","");
				$("#bEndTime").datebox("setValue","");
				$('#setBatchDisount').dialog('close');
			}
		}]
	}); 

});

function loadProduct(status) {
	var fileview = $.extend({}, $.fn.datagrid.defaults.view, { onAfterRender: function (target) { ischeckItem(); } });
	$(function(){
		var urle = path + "/admin/product/loadDiscountList.action?state=" + status + "&dd="+Math.random() ;
		$('#productList').datagrid({
			loadMsg:'数据加载中请稍后……',
			width:'100%',
			height:$(document).height(),
			nowrap: true,
			striped: true,
			singleSelect:false,
			collapsible:false,
			url:urle,
			method:'post',
			remoteSort: false,
			autoRowHeight:true,
			 idField: 'productId',
			 view: fileview,
			frozenColumns:[[
			    {title : '选择',field:'ck',checkbox : true},
				{field:'editx',title:'操作',width:55,align:'center',resizable:false,
					formatter:function(value,rec){
						var str = '<a href="javascript:void(0)" onclick=\"openDialog(' + rec.productId + ',\'' + rec.productName + '\',\'' + rec.price_ + '\',' + rec.switchs +',' + rec.tempPrice  +   ',\'' + rec.startTime_ + '\',\'' + rec.endTime_ + '\')\" title="特价设置" iconcls="icon-money" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-money" style="padding-left: 20px;"></span></span></a>';
						return str;	
					}
				},
                {title:'商品名称',field:'productName',sortable:false,resizable:true}
			]],
			columns:[[				
				{field:'price_',title:'原价',width:100,align:'center',resizable:true},
				{field:'switchs',title:'预优惠价/预折扣',width:100,align:'center',resizable:true,
					formatter:function(value,rec){
						if(rec.switchs == 0){
							return rec.tempPrice ;
						}else if(rec.switchs == 1){
							return rec.tempPrice + '折';
						}else {
							return '无';
						}
					}},
					{field:'startTime_',title:'开始时间',width:100,align:'center',resizable:true},
					{field:'endTime_',title:'结束时间',width:100,align:'center',resizable:true},
					{field:'productType',title:'商品类型',align:'center',resizable:true,
							formatter:function(value,rec) {
								if(rec.productType == 0){
									return '数字教辅';
								}else {
									return '数字教材';
								}
							}
					},
				{field:'updateTime_',title:'更新时间',width:120,align:'center',resizable:true},
				{field:'author',title:'开发者',width:100,align:'center',resizable:true},
				{field:'publishing',title:'出版社',width:100,align:'center',resizable:true},
				{field:'publishTime_',title:'发布时间',width:100,align:'center',resizable:true},
				{field:'edition',title:'版本',width:100,align:'center',resizable:true},
				{field:'teachVersion',title:'对应教材版本',width:100,align:'center',resizable:true},
				{field:'pageNumber',title:'页数',width:100,align:'center',resizable:true},				
				{field:'language',title:'语言',width:100,align:'center',resizable:true},
				{field:'schoolStage',title:'学段',width:100,align:'center',resizable:true,
					formatter:function(value,rec){
						if(rec.schoolStage == 1){
							return '小学';
						}else if(rec.schoolStage == 2) {
							return '初中';
						}else if(rec.schoolStage == 3) {
							return '高中';
						}
					}
				},
				{field:'grade',title:'年级',width:100,align:'center',resizable:true},
				{field:'subject',title:'科目',width:100,align:'center',resizable:true},
				{field:'volume',title:'册',width:100,align:'center',resizable:true},
				{field:'applyObject',title:'适用对象',width:100,align:'center',resizable:true},
				{field:'usePeriod',title:'使用期限',width:100,align:'center',resizable:true,
					formatter:function(value,rec){
						if(rec.usePeriod == 1200){
							return '永久';
						}else {
							return rec.usePeriod + '个月';
						}
						
					}
				},
				{field:'optionTime_',title:'操作时间',width:130,align:'center',resizable:true},
				{field:'optionAuthor',title:'操作人',width:100,align:'center',resizable:true},
				{field:'bookCode',title:'EBook编号',width:100,align:'center',resizable:true}
			]],
			pagination:true,
			rownumbers:true,
			toolbar : '#tb'
		});
	});
	
}

function search1(){
	var searchName=$('#sear').val();
	var subject = $("#subject").combobox('getValue') + "";
	if(subject == -1 || subject == '全部'){
		subject = "";
	}
	var productType = $("#productType").combobox('getValue') + "";
	if(productType == -1){
		productType = "";
	}
	var schoolStage = $("#schoolStage").combobox('getValue') + "";
	if(schoolStage == -1){
		schoolStage = "";
	}
	$('#productList').datagrid('clearSelections');
	$('#productList').datagrid('load',{
		searchName: searchName,
		productType:productType,
		schoolStage:schoolStage,
		subject:subject
	});
}

function openDialog(productId,productName,price_,switchs,tempPrice,startTime_,endTime_){
	
	$("#price").html(price_);
	$("#productId").val(productId);	
//	$("#accreditPrice").numberbox({
//		min:0.0,
//		precision:2,
//		value:accreditPrice
//	});
	if(switchs == 1) {
		$("#discount").attr("checked",true);
		$("#special").html("折扣");
		$("#nn").numberbox({
			min:0.0,
			max:null,
			precision:1,
			suffix:'折',
			value:tempPrice
		});
		$("#downPrice").val(tempPrice);
		$("#nn").attr("title","范围限制0.0---9.9");
	}else {
		$("#vip").attr("checked",true);
		$("#special").html("优惠价");
		$("#nn").numberbox({
			min:0.0,
			max:null,
			precision:2,	
			suffix:'元',
			value:tempPrice
		});
		$("#nn").attr("title","");
		$("#vipPrice").val(tempPrice);		
	}
	$("#setDisount").dialog({title:productName+'&nbsp;&nbsp;特价设置'});
	$("#setDisount").dialog('open');
	$("#startTime").datebox('setValue',startTime_);
	$("#endTime").datebox('setValue',endTime_);
}

function choice(value) {
	if(value == 0) {
		$("#vip").attr("checked",true);
		$("#special").html("优惠价");
		$("#nn").numberbox({
			min:0.0,
			max:null,
			precision:2,	
			suffix:'元',
			value:$("#vipPrice").val()
		});
		$("#nn").attr("title","");
	}else {
		$("#discount").attr("checked",true);
		$("#special").html("折扣");
		$("#nn").numberbox({
			min:0.0,
			max:null,
			precision:1,
			suffix:'折',
			value:$("#downPrice").val()
		});
		$("#nn").attr("title","范围限制0.0---9.9");
	}
}

function setPrice() {
	var specialPrice = $("#nn").numberbox('getValue');
//	var accreditPrice = $("#accreditPrice").numberbox('getValue');
	var col = $("input[type=radio]:checked").val() ;
	if(specialPrice == null || specialPrice == '') {
		$.messager.alert("提示","请填写完整信息");
		return ;
	}
//	if(accreditPrice == null || accreditPrice == '') {
//		$.messager.alert("提示","请输入授权价");
//		return ;
//	}
	var price = $("#price").html();
	if(col == 0 && parseFloat(price) < specialPrice){
		$.messager.alert("提示","优惠价大于原价");
		return ;
	}
	if(col == 1 && (parseFloat(specialPrice) < 0 || parseFloat(specialPrice) > 9.9)){
		$.messager.alert("提示","折扣范围限制0.0---9.9");
		$("#nn").numberbox('setValue','');
		return ;
	}
	var startTime = $("#startTime").datebox('getValue');
	if(startTime==null || startTime==""){
		$.messager.alert("提示","开始时间不能为空!");
		return;
	}
	var strdt1=startTime.replace(/-/g, "/");
    var dt1=new Date(Date.parse(strdt1));
	if(dt1 < new Date()){
    	$.messager.alert('提示','开始时间要大于今天!');
    	return;
    }
	var endTime = $("#endTime").datebox('getValue');
	if(endTime==null || endTime==""){
		$.messager.alert("提示","结束时间不能为空!");
		return;
	}
	if(startTime > endTime){
		$.messager.alert("提示","开始时间不能大于结束时间!");
		return;
	}
	
	$('#ok').linkbutton({disabled:true});
	$("#vipPrice").val("");
	$("#downPrice").val("");
	$.ajax({
		type:"post",
		url: path + '/admin/product/updatePrice.action?dd='+Math.random(),
		data:{"proId":$("#productId").val(),"specialPrice":specialPrice,"columnName":col,"startTime":startTime,"endTime":endTime},
		cache : false,
		success:function(msg) {
			if(msg == false) {
				$.messager.alert('提示','异常，请稍候操作!');
			}
		}
	});
	$('#setDisount').dialog('close');
	$('#productList').datagrid('reload');
}


function loadSubject() {
	var schoolStage = $("#schoolStage").combobox('getValue');
	if(schoolStage != -1){
		$.ajax({
			type:"post",
			url: path + '/admin/dictionary/loadSubject.action',
			data:"schoolStage=" + schoolStage +"&type=1&dd=" + Math.random(),
			success:function(json) {
				if(json.subjectList != null && json.subjectList.length > 0){
					$("#subject").combobox({
						data:json.subjectList,
						valueField:'name',
						textField:'name'
					});
				}else{
					$("#subject").combobox({
						data:[{"name":"全部","value":-1}],
						valueField:'value',
						textField:'name'
					});
				}
				$('#subject').combobox('setValue',"全部");
			}
		});
	}else{
		$("#subject").combobox({
			data:[{"name":"全部","value":-1}],
			valueField:'value',
			textField:'name'
		});
		$('#subject').combobox('setValue',"全部");
	}
	
}

var proIds = "" ;
function setBatch(){
	
	var rows = $('#productList').datagrid('getSelections');
	if(rows.length<=0){
		$.messager.alert('温馨提示','请先选择商品!');
	}else{
		for(var i = rows.length-1;i>=0;i--){
				proIds += rows[i].productId + ",";
		}
		proIds = proIds.substring(0,proIds.length-1);
		$('#setBatchDisount').dialog('open');
	}
	
}

function setBatchPrice(){
	var specialPrice = $("#bDiscount").numberbox('getValue');
	if(specialPrice == null || specialPrice == '') {
		$.messager.alert("提示","请填写折扣");
		return ;
	}
	if(specialPrice < 0 || specialPrice > 9.9){
		$.messager.alert("提示","折扣范围限制0.0---9.9");
		$("#bDiscount").numberbox('setValue','');
		return ;
	}
	var startTime = $("#bStartTime").datebox('getValue');
	if(startTime==null || startTime==""){
		$.messager.alert("提示","开始时间不能为空!");
		return;
	}
	var strdt1=startTime.replace(/-/g, "/");
    var dt1=new Date(Date.parse(strdt1));
	if(dt1 < new Date()){
    	$.messager.alert('提示','开始时间要大于今天!');
    	return;
    }
	var endTime = $("#bEndTime").datebox('getValue');
	if(endTime==null || endTime==""){
		$.messager.alert("提示","结束时间不能为空!");
		return;
	}
	if(startTime > endTime){
		$.messager.alert("提示","开始时间不能大于结束时间!");
		return;
	}
	$.ajax({
		type:"post",
		url: path + '/admin/product/updatePriceBatch.action?dd='+Math.random(),
		data:{"proIds":proIds,"specialPrice":specialPrice,"startTime":startTime,"endTime":endTime},
		cache : false,
		success:function(msg) {
			if(msg == false) {
				$.messager.alert('提示','异常，请稍候操作!');
			}
		}
	});
	$('#setBatchDisount').dialog('close');
	$("#bDiscount").numberbox('setValue',"");
	$("#bStartTime").datebox("setValue","");
	$("#bEndTime").datebox("setValue","");
	$('#productList').datagrid('clearSelections');
	checkedItems = [];
	$('#productList').datagrid('reload');
	proIds = "";
}

var checkedItems = [];
function ischeckItem() {
       for (var i = 0; i < checkedItems.length; i++) {
           $('#productList').datagrid('selectRecord', checkedItems[i]); //根据id选中行 
     }
 }
