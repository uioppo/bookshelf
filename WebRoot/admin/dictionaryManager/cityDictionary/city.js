function findAllCity(){
	$("#main").treegrid({
		loadMsg:'数据加载中请稍后.....',
		width:'100%',
		height:$(document).height(),
		url:path+'/admin/area/findAllArea.action',
		rownumbers: false,
		singleSelect:true,
		animate:true,
		striped:true,
		collapsible:false,
		fitColumns:true,
		idField:'id',
		treeField:'name',
		toolbar:"#top"
	});
}


function search1(){
	var areaName = $("#searchAreaName").val();
	var areaCode = $("#searchAreaCode").val();
	var data = {};
	if(areaName != ''){
		data['bsArea.areaName']=areaName;
	}
	if(areaCode != ''){
		data['bsArea.areaCode']=areaCode;
	}
		
	$.post(path+'/admin/area/findAllArea.action',data,function(data){
	  $('#main').treegrid('loadData',data);
	},'json');
}

function addArea(){
	var node = $('#main').treegrid('getSelected');
	if (node){
		var p = $('#main').treegrid("getParent",node.id);
		if(p){
			var p_ = $('#main').treegrid("getParent",p.id);
			if(p_){
				$.messager.alert('温馨提示','县级地区不能增加结点.','info');
				return;
			}
		}
		$("#addAreaName").val("");
		$("#addAreaCode").val("");
		$("#areaId_").val("");
		$("#areaId_").val(node.id);
		$('#addArea').dialog('open');
	}else{
		$("#addAreaName").val("");
		$("#addAreaCode").val("");
		$("#areaId_").val("");
		$('#addArea').dialog('open');
	}
	
	
}

function insertArea(){
	var name = $("#addAreaName").val();
	var areaId = $("#areaId_").val();
	if(name==null || name== "" || name==undefined){
		$.messager.alert('温馨提示','请输入名称.','info');
		return;
	}
	var code =  $("#addAreaCode").val();
	jQuery.ajax({
		type:"post",
		url:path+"/admin/area/saveArea.action",
		async:false,
		data:{"bsArea.areaName":name,"bsArea.areaId":areaId,"bsArea.areaCode":code},
		success:function(){
			closeAddDiv();
			$('#main').treegrid('reload'); 
		}
	});
}

function closeAddDiv(){
	$('#addArea').dialog('close');
}

function closeEditDiv(){
	$('#editArea').dialog('close');
}
$(function(){
	$("#editArea").dialog({
		closable:false,
		closed:true,
		title:'编辑地区',
		modal:true
	});
	$("#addArea").dialog({
		closable:false,
		closed:true,
		title:'新增地区',
		modal:true
	});
});
function editArea(){
	var node = $('#main').treegrid('getSelected');
	if (node){
		jQuery.ajax({
			type:"post",
			url:path+"/admin/area/findAreaById.action",
			async:false,
			data:{"areaId":node.id},
			success:function(data){
				$("#editAreaId").val("");
				$("#editAreaName").val("");
				$("#editAreaId").val(data.bsArea.areaId);
				$("#editAreaName").val(data.bsArea.areaName);
				$("#editAreaCode").val(data.bsArea.areaCode);
				$("#editArea").dialog('open');
			}
		});
	}else{
		$.messager.alert('温馨提示','请选择要编辑的地区.','info');
	}
}

function updateArea(){
	var areaId = $("#editAreaId").val();
	var areaName = $("#editAreaName").val();
	if(areaName==null || areaName== "" || areaName==undefined){
		$.messager.alert('温馨提示','请输入名称.','info');
		return;
	}
	var areaCode = $("#editAreaCode").val();
	jQuery.ajax({
		type:"post",
		url:path+"/admin/area/updateArea.action",
		async:false,
		data:{"bsArea.areaId":areaId,"bsArea.areaName":areaName,"bsArea.areaCode":areaCode},
		success:function(data){
			closeEditDiv();
			var node = $('#main').treegrid('find',areaId);
			if(node){
				var p = $('#main').treegrid('getParent',areaId);
				if(p){$('#main').treegrid('reload',p.id);} 
			}
		}
	});
}

function deleteArea(){
	var node = $('#main').treegrid('getSelected');
	if (node){
		$.messager.confirm('确认','确认删除?',function(f){
			if(f){
				jQuery.ajax({
					type:"post",
					url:path+"/admin/area/deleteArea.action",
					async:false,
					data:{"bsArea.areaId":node.id},
					success:function(data){
						$('#main').treegrid('reload'); 
					}
				});
			}
		});
	}else{
		$.messager.alert('温馨提示','请选择要删除的地区.','info');
	}
}