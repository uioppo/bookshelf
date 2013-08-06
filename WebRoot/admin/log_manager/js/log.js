function loadLogList(){
		$('#logList').datagrid({
		loadMsg:'数据加载中请稍后……', 
		width:'100%',
		height:$(document).height(),
		nowrap: false,
		striped: true,
		collapsible:false,
		url:path+'/admin/log/findLogList.action',
		remoteSort: false,
		sortName: 'time_',
		sortOrder: 'desc',
		idField:'logId',
		singleSelect:true,
		columns:[[
            {title:'操作者',field:'userName',width:150,align:'center'},
            {title:'角色',field:'roleName',width:100,align:'center'},
            {title:'时间',field:'time_',width:185,sortable:true,align:'center'},
	        {title:'日志信息',field:'operateInfo',width:400,align:'left'},
	        {title:'日志类型',field:'operateType',width:200,align:'center'},
	        {title:'ip',field:'ip',width:111,align:'center'}
		]],
		pagination:true,
		rownumbers:true,
		toolbar:"#loglist_bar"
	});
	
	var p = $('#logList').datagrid('getPager');
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
	$('#logList').datagrid("load",{
		searchValue:value,
		searchName:name
	});
}
