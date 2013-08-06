function loadAdviceList() {
	$('#adviceList').datagrid({
		loadMsg : '数据加载中请稍后……',
		width : '100%',
		height : $(document).height(),
		nowrap : false,
		striped : true,
		singleSelect:true,
		collapsible : false,
		url : path + '/admin/ebook/findAdviceList.action',
		remoteSort : false,
		idField : 'flowId',
		columns : [ [
				{
					title : '操作时间',
					field : 'time_',
					width : 150,
					sortable : true,
					align : 'center'
				},
				{
					title : '审核对象',
					field : 'objectName',
					width : 148,
					sortable : true,
					align : 'center'
				},
				{
					title : '审核人',
					field : 'toName',
					width : 100,
					align : 'center'
				},
				{
					title : '被审核人',
					field : 'fromName',
					width : 100,
					align : 'center'
				}, 
				{
					title : '审核状态',
					field : 'isPass',
					width : 100,
					align : 'center',formatter:function(value,row,index){
						if(2==row.isPass){
							return "<font color='red'>内容未通过</font>";
						}else if(1==row.isPass){
							return "<font color='green'>通过</font>";
						}else{
							return "<font>属性不通过</font>";
						}
					}
				},
				{
					title : '审核意见',
					field : 'idea',
					width : 550,
					align : 'left'
				}
				] ],
		pagination:true,
		rownumbers : true,
		toolbar : "#adviceList_bar"
	});
	var p = $('#adviceList').datagrid('getPager');
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
	$('#adviceList').datagrid("load",{
		searchValue:value,
		searchName:name
	});
}