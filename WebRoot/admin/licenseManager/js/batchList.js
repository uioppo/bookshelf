function loadBatchPage(){
	var batch=$("#batch").val();
	var schoolName=$("#schoolName").val();
		$('#batchList').datagrid({
		height: $(document).height(),
		width:'100%',
		border:0,
		striped:true,
		singleSelect:true,
		url:path+'/license/loadBatchList.action',
		queryParams:{'batch':batch,'schoolName':schoolName},
		loadMsg:'数据加载中请稍后……',
		columns:[[
			{field:'batchId',title:'批次',width:130,sortable:true,align:'center'},
			{field:'shuliang',title:'已用/总数量',width:120,align:'center'},
			{field:'schoolName',title:'学校',width:220,align:'center'},
			{field:'schoolStage',title:'学段', width:120,sortable:true,align:'center',
				formatter: function(value,row,index){
					if (row.schoolStage==1){
						return "小学";
					} else if(row.schoolStage==2){
						return "初中";
					}else{
						return "高中";
					}
				}
			},
			{field:'operator',title:'操作人',width:200,sortable:true,align:'center'},
			{field:'createTime_',title:'操作时间',width:200,align:'center'},
			{field:'edit',title:'操作',width:150,align:'center',
				formatter: function(value,row,index){
					return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-plain"  onclick="seeDetail('+row.batchId+')"><span class="l-btn-left"><span class="l-btn-text icon-search" style="padding-left: 20px; ">查看</span></span></a>'+
					'<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-plain" onclick="exportExcel('+row.batchId+')"><span class="l-btn-left"><span class="l-btn-text icon-redo" style="padding-left: 20px; ">导出</span></span></a>'
					;
				}
			}
		]],
		pagination:true,
		rownumbers:true,
		toolbar:"#tb"
		});
		var p = $('#batchList').datagrid('getPager');
	$(p).pagination({
		pageSize: 10,
		pageList: [5,10,15,20],
		beforePageText: '第',
		afterPageText: '页    共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh:function(pageNumber,pageSize){
			$(this).pagination('loading'); 
			$(this).pagination('loaded');
			}
	});
}
function seeDetail(batchId){
	window.open(path+"/license/loadKeyByBatch.action?batch="+batchId);
}
function exportExcel(batchId){
	window.location.href=path+"/license/exportExcel.action?batch="+batchId;
}
