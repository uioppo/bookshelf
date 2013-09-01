

function loadReader() {
	$(function(){
		var urle = path + "/admin/reader/loadReaderList.action?dd="+Math.random() ;
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
			columns:[[	
				{field:'exePath_',title:'操作',width:100,align:'center',resizable:true,
					formatter:function(value,row,rec) {
						if(row.exePath!=null&&row.exePath!=""){
							return '<a href="'+path+'/admin/reader/downloadThisReader.action?readerPath='+row.exePath+'" title="下载" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-download" style="padding-left: 20px;">下载</sapn></span></a>';
						}else{
							return '<a href="javascript:tishi();" title="下载" class="l-btn l-btn-plain"><span class="l-btn-left"><span class="l-btn-text icon-download" style="padding-left: 20px;">下载</sapn></span></a>';
						}
					}
				},
				{field:'category',title:'阅读器类型',width:180,align:'center',resizable:true,
					formatter: function(value,row,index){
						if (row.category=="01"){
							return "PC";
						} else if(row.category=="02"){
							return "Andirod";
						}
					}
				},
				{field:'version',title:'版本',width:180,align:'center',resizable:true},
				{field:'createTime_',title:'创建时间',width:180,align:'center',resizable:true},
				{field:'operator',title:'操作者',width:200,align:'center',resizable:true},
				{field:'changeLog',title:'更新日志',width:480}
				
			]],
			pagination:true,
			rownumbers:true
		});
	});
	
}
function tishi(){
	$.messager.alert("温馨提示","该阅读器不存在！");
}
