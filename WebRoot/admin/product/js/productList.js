
//0：下架 1：上架2:回退3：待审核4待发布
function loadProduct(status) {
	$(function(){
		var urle = path + "/admin/product/loadProductsList.action?state=" + status + "&dd="+Math.random() ;
		$('#productList').datagrid({
			loadMsg:'数据加载中请稍后……',
			width:'100%',
			height:$(document).height(),
			nowrap: true,
			striped: true,
			singleSelect:true,
			collapsible:false,
			url:urle,
			method:'post',
			remoteSort: false,
			autoRowHeight:true,
			frozenColumns:[[
				{field:'editx',title:'操作',width:105,align:'center',resizable:false,
					formatter:function(value,rec){
						var str ;
						if(rec.status == 0) {
							str = '<a href="javascript:void(0)" onclick=\"showProduct('+ rec.productId + ')\" title="查看商品信息" iconcls="icon-search" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-search" style="padding-left: 20px;"></span></span></a>';
							str += '<a href="javascript:void(0)" onclick=\"racking(' + rec.productId + ',' + rec.status + ',1)\" title="上架" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-up" style="padding-left: 20px;"></sapn></span></a>';
						} else if(rec.status == 1){
							str = '<a href="javascript:void(0)" onclick=\"editProduct('+ rec.productId + ')\" title="编辑商品信息" iconcls="icon-edit" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-edit" style="padding-left: 20px;"></span></span></a>';
							str += '<a href="javascript:void(0)" onclick=\"racking(' + rec.productId + ',' + rec.status + ',0)\" title="下架" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-down" style="padding-left: 20px;"></sapn></span></a>';
						}else if(rec.status == 4){
							str = '<a href="javascript:void(0)" onclick=\"showProduct('+ rec.productId + ')\" title="查看商品信息" iconcls="icon-search" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-search" style="padding-left: 20px;"></span></span></a>';
							str += '<a href="javascript:void(0)" onclick=\"racking(' + rec.productId + ',' + rec.status + ',1)\" title="发布" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-ok" style="padding-left: 20px;"></sapn></span></a>';
						}
						return str;					
					
					}
				},
                {title:'商品名称',field:'productName',sortable:false,resizable:true}
			]],
			columns:[[				
				{field:'price_',title:'原价',width:100,align:'center',resizable:true},
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
				{field:'downNumber',title:'下载次数',width:100,align:'center',resizable:true},
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

function search(value,name){
	$('#productList').datagrid('load',{
		value: value,
		name: name
	});
}


function racking(productId, oldStatus,newStatus) {
	var msg = '';
	if(oldStatus == 0) {
		msg = '确定让该商品<font color="red">上架</font>吗';
	}else if(oldStatus == 1){
		msg = '确定让该商品<font color="red">下架</font>吗';
	}else if(oldStatus == 4){
		msg = '确定让该商品<font color="red">发布</font>吗';
	}
	$.messager.confirm('提示', msg, function(r){
		if (r){
			$.ajax({
		  		type: "POST",
		  		url: path + "/admin/product/updateProductStatus.action?dd="+Math.random(),
				data : "productId=" + productId + "&oldSatus=" + oldStatus+"&newSatus=" + newStatus ,
				cache : false,
				success : function(msg) {
					if (msg == false) {
						$.messager.alert('提示','服务器异常，请稍候操作!');
						}else {
							$('#productList').datagrid('reload');
						}
					}
				});
			}
	});
}

function editProduct(pId) {
	window.location.href = path + "/admin/product/loadProduct.action?pId="+pId ;
}

function showProduct(pId) {
	window.open(path + "/admin/product/loadDownProduct.action?pId="+pId) ;
}
