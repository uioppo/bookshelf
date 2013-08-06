
$(function(){
	$("#advice").dialog({
		closed:true,
		modal:true,
		width:610,
		height:400,
		buttons:[{
			text:'审核通过',
			iconCls:'icon-ok',
			handler:function(){
				adviceStatus(1);
			}
		},{
			text:'内容不通过，删除',
			iconCls:'icon-no',
			handler:function(){
				adviceStatus(2);
			}
		},{
			text:'属性不通过，退回',
			iconCls:'icon-back',
			handler:function(){
				adviceStatus(3);
			}
		},{
			text:'取消',
			handler:function(){
				$('#advice').dialog('close');
			}
		}]
	});
});

function loadNEBookList(s){
	$('#nEBookList').datagrid({
		loadMsg:'数据加载中请稍后……', 
		width:'100%',
		height:$(document).height(),
		nowrap: true,
		striped: true,
		singleSelect:true,
		collapsible:false,
		url:path+'/admin/ebook/findEBookList.action?bookStatus=0&subject='+s,
		remoteSort: false,
		idField:'id',
		frozenColumns:[[
						 {title:'操作',field:'cz',width:150,align:'center',formatter:function(value,row,index){
				        	var str = "<a href='javascript:void(0);' onclick='audit(\""+row.id+"\",\""+row.bookName+"\"," + row.pageNumber + ",\"" + row.author + "\",\"" + row.teachVersion + "\",\"" + row.publishing + "\",\"" + row.language + "\"," + row.productType + "," + row.edition + ",\"" + row.applyObject + "\",\"" + row.volume + "\",\"" + row.grade + "\",\"" + row.subject + "\"," + row.schoolStage + ")' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-search' style='padding-left: 20px; '>审核</span></span><a/>";
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
	        {title:'创建人',field:'operator',width:130,align:'center'},
	        {title:'code',field:'bookCode',width:150}
		]],
		pagination:true,
		rownumbers:true,
		toolbar:"#nEbooklist_bar"
	});
	
	var p = $('#nEBookList').datagrid('getPager');
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
	$('#nEBookList').datagrid("load",{
		searchValue:value,
		searchName:name
	});
}

function showBook(){
	var bookId = $("#bookIdHidden").val();
	alert(bookId);
}

function audit(bookId,bookName,pageNumber,author,teachVersion,publishing,language,productType,edition,applyObject,volume,grade,subject,schoolStage){
	$("#pageNumber").val(pageNumber);
	$("#author").val(author);
	$("#teachVersion").val(teachVersion);
	$("#publishing").val(publishing);
	$("#language").val(language);
	if(productType == 1){
		$("#productType").val("数字教材");
	}else{
		$("#productType").val("数字教辅");
	}
	
	$("#edition").val(edition+"年");
	$("#applyObject").val(applyObject);
	$("#volume").val(volume);
	$("#grade").val(grade);
	$("#subject").val(subject);
	if(schoolStage == 1){
		$("#schoolStage").val("小学");
	}else if(schoolStage == 2){
		$("#schoolStage").val("初中");
	}else {
		$("#schoolStage").val("高中");
	}
	
	
	$("#eBookName").text("");
	$("#eBookName").text(bookName);
	$("#bookIdHidden").val("");
	$("#bookIdHidden").val(bookId);
	$("#adviceIdea").val("");
	$('#advice').dialog("open");
}

function adviceStatus(status){
	if(status == 1){
		var msg = "确定要通过审核吗？";
	}else if(status == 2){
		var msg = "确定要删除吗？";
	}if(status == 3){
		var msg = "确定要退回吗？";
	}
	
	$.messager.confirm('确认',msg,function(f){
		if(f){
			var text_ = $("#adviceIdea").val();
			if(text_!=null && text_!=undefined && text_.length>500){
				$.messager.alert('温馨提示','审批意见不能超过500字.','info');
				return;
			}
			var bookId = $("#bookIdHidden").val();
			jQuery.ajax({
				type:"post",
				url:path+"/admin/ebook/updateEbookStatus.action",
				data:{"bookId":bookId,"bookStatus":status,"advice":text_},
				async:false,
				success:function(){
					closeDiv_();
					$('#nEBookList').datagrid('reload'); 
				}
			});
		}
	});
}

function closeDiv_(){
	$('#advice').dialog("close");
}

//function delete_(id){
//	$.messager.confirm('确认','确定要删除么?',function(f){
//		if(f){
//			jQuery.ajax({
//				type:"post",
//				url:path+"/admin/ebook/deleteEbookById.action",
//				data:{"bookId":id},
//				async:false,
//				success:function(){
//					$('#nEBookList').datagrid('reload'); 
//				}
//			});
//		}
//	});
//}

//function status_(id){
//	$.messager.confirm('确认','确定么?',function(f){
//		if(f){
//			var status = 1;
//			jQuery.ajax({
//				type:"post",
//				url:path+"/admin/ebook/updateEbookStatus.action",
//				data:{"bookId":id,"bookStatus":status},
//				async:false,
//				success:function(){
//					$('#nEBookList').datagrid('reload'); 
//				}
//			});
//		}
//	});
//}

