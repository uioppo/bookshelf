function findAllGrade(){
	$("#main").datagrid({
		loadMsg:'数据加载中请稍后.....',
		width:'100%',
		height:$(document).height(),
		url:path+'/admin/dictionary/findDictionaryByGrade.action',
		striped:true,
		nowrap:false,
		rownumbers:true,
		singleSelect:true,
		isField:'id',
		rownumbers:true,
		columns:[[
				{title:'年级编号',field:'dicCode',width:200,align:'center'},
				{title:'类型',field:'type',width:150,align:'center',formatter:function(value,row,index){
					if(row.type==2){
						return '年级';
					}
				}},
				{title:'年级名称',field:'name',width:200,align:'center'},
				{title:'学段',field:'schoolStage',width:200,align:'center',formatter:function(value,row,index){
					if(row.schoolStage == 1){
							return '小学';
						}else if(row.schoolStage == 2) {
							return '初中';
						}else if(row.schoolStage == 3) {
							return '高中';
						}
				}},
				{title:'操作',field:'cz',width:400,align:'center',formatter:function(value,row,index){
					var s = "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='findDictionarybyId("+row.id+")'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px; '>编辑</span></span><a/>";
					s += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					s += "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='deleteDictionary("+row.id+")'><span class='l-btn-left'><span class='l-btn-text icon-no' style='padding-left: 20px; '>删除</span></span><a/>";
					return s;
				}}
			]],
			toolbar:"#top"
	});
}
function addDictionary(){
	$("#dicCode").val("");
	$("#dicName").val("");
	$("#addschoolStage").val("");
	$("#add").dialog({
		closable:false,
		title:'新增年级',
		modal:true
	});
}
function closeAddDiv(){
	$("#add").dialog('close');
}
function closeEditDiv(){
	$("#edit").dialog('close');
}
function addSubmit(){
	var dicCode = $("#dicCode").val();
	var type = 2;
	var name = $("#dicName").val();
	var schoolStage = $("#addschoolStage").val();
	if(name==null || ""==name || undefined==name){
		$.messager.alert('温馨提示','请输入年级名称.','info');
		return;
	}
	if(schoolStage==null || ""==schoolStage || undefined==schoolStage || schoolStage==-1){
		$.messager.alert('温馨提示','请选择学段.','info');
		return;
	}
	$.ajax({
		type:"POST",
		url:path+"/admin/dictionary/addDictionary.action",
		async:false,
		data:"bsDictionary.dicCode="+dicCode+"&bsDictionary.type="+type+"&bsDictionary.name="+name+"&bsDictionary.schoolStage="+schoolStage,
		success:function(){
			closeAddDiv();
			$("#main").datagrid('reload');
		}
	});
}
function findDictionarybyId(value){
	$("#edit").dialog({
		closable:false,
		title:'编辑年级',
		modal:true
	});
	$.ajax({
			type:"POST",
			url:path+"/admin/dictionary/findDictionaryById.action",
			data:"bsDictionary.id="+value,
			async:false,
			success:function(msg){
				$("#editId").val("");
				$("#editDicCode").val("");
				$("#editDicName").val("");
				$("#editSchoolStage").val("");
				
				$("#editId").val(msg.bsDictionary.id);
				$("#editDicCode").val(msg.bsDictionary.dicCode);
				$("#editDicName").val(msg.bsDictionary.name);
				$("#editSchoolStage").val(msg.bsDictionary.schoolStage);
			}
		});
}
function editSubmit(){
	var id = $("#editId").val();
	var dicCode = $("#editDicCode").val();
	var type = 2;
	var name = $("#editDicName").val();
	var schoolStage = $("#editSchoolStage").val();
	
	if(name==null || ""==name || undefined==name){
		$.messager.alert('温馨提示','请输入年级名称.','info');
		return;
	}
	if(schoolStage==null || ""==schoolStage || undefined==schoolStage || schoolStage==-1){
		$.messager.alert('温馨提示','请选择学段.','info');
		return;
	}
	
	$.ajax({
		type:"POST",
		url:path+"/admin/dictionary/updateDictionary.action",
		async:false,
		data:"bsDictionary.id="+id+"&bsDictionary.dicCode="+dicCode+"&bsDictionary.type="+type+"&bsDictionary.name="+name+"&bsDictionary.schoolStage="+schoolStage,
		success:function(){
			closeEditDiv();
			$("#main").datagrid('reload');
		}
	});
}

function deleteDictionary(value){
	$.messager.confirm('确认','确认删除该记录?',function(r){
		if(r){
			$.ajax({
				type:"POST",
				url:path+"/admin/dictionary/deleteDictionary.action",
				async:false,
				data:"bsDictionary.id="+value,
				success:function(){
					$("#main").datagrid('reload');
				}	
			});
		}
	});
		
}