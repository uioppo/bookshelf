function loadYEBookList(){
	$('#yEBookList').datagrid({
		loadMsg:'数据加载中请稍后……', 
		width:'100%',
		height:$(document).height(),
		nowrap: true,
		striped: true,
		singleSelect:true,
		collapsible:false,
		url:path+'/admin/ebook/findEBookList.action?bookStatus=1',
		remoteSort: false,
		idField:'id',
		frozenColumns:[[
						 {title:'操作',field:'cz',width:150,align:'center',formatter:function(value,row,index){
							 var str = "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='showBook("+row.id+")'><span class='l-btn-left'><span class='l-btn-text icon-search' style='padding-left: 20px; '>查看</span></span><a/>";
				        	 str += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				        	 //str += "<a href='javascript:void(0);' onclick='status_("+row.id+")'>退回<a/>";
				        	 return str;
				        }},
				        {title:'名称',field:'bookName',width:200,sortable:true,align:'center'}
					]],
					columns:[[
					            {title:'大小',field:'bookSize',width:100,align:'right',
					            	formatter:function(value,rec){
					            		return rec.bookSize + "M";
					            	}},
						        {title:'版本(年)',field:'edition',width:100,align:'center',
					            		formatter:function(value,rec){
					            			if(rec.edition != null) {
					            				return rec.edition + "年";
					            			}
					                		return "";
					                	}},
					            {title:'版本号',field:'version_',width:80,align:'right',resizable:true},
					            {title:'对应教材版本',field:'teachVersion',width:100,align:'center',resizable:true},
						        {title:'商品类型',field:'productType',align:'center',resizable:true,
									formatter:function(value,rec) {
										if(rec.productType == 0){
											return '数字教辅';
										}else if(rec.productType == 1){
											return '数字教材';
										}
									}
						        },
						        {title:'学段',field:'schoolStage',width:100,align:'center',resizable:true,
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
								{title:'年级',field:'grade',width:100,align:'center',resizable:true},
						        {title:'科目',field:'subject',width:100,align:'center'},
						        {title:'册',field:'volume',width:100,align:'center',resizable:true},
								{title:'适用对象',field:'applyObject',width:100,align:'center',resizable:true},
						        {title:'页数',field:'pageNumber',width:100,align:'center',resizable:true},				
								{title:'语言',field:'language',width:100,align:'center',resizable:true},
						        {title:'开发者',field:'author',width:100,align:'center',resizable:true},
								{title:'出版社',field:'publishing',width:100,align:'center',resizable:true},
								{title:'操作时间',field:'operatorTime_',width:150,align:'center'},
						        {title:'创建时间',field:'createTime_',width:150,align:'center'},
						        {title:'创建人',field:'operator',width:130,align:'center'}
							]],
		pagination:true,
		rownumbers:true,
		toolbar:"#yEbooklist_bar"
	});
	
	var p = $('#yEBookList').datagrid('getPager');
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
	$('#yEBookList').datagrid("load",{
		searchValue:value,
		searchName:name
	});
}

function showBook(){
	
}

function status_(id){
	var status = 0;
	jQuery.ajax({
		type:"post",
		url:path+"/admin/ebook/updateEbookStatus.action",
		data:{"bookId":id,"bookStatus":status},
		async:false,
		success:function(){
			$('#yEBookList').datagrid('reload'); 
		}
	});
}