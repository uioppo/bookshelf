function loadSchoolList(){
	$('#schoolList').datagrid({
		loadMsg:'数据加载中请稍后……', 
		width:'100%',
		height:$(document).height(),
		nowrap: false,
		striped: true,
		collapsible:false,
		url:path+'/school/loadSchoolList.action',
		remoteSort: false,
		idField:'schoolId',
		singleSelect:true,
		columns:[[
			//{title:'选择',field:'schoolId',checkbox:true},
            {title:'学校名称',field:'schoolName',width:300,sortable:true,align:'center'},
            {title:'学校地址',field:'bsArea.areaName',width:473,align:'center',formatter: 
            	function(value,row,index){
					if (row.bsArea!=null){
						return row.bsArea.areaName;
					}else{
						return "";
					} 
			}},
	        {title:'操作',field:'cz',width:330,align:'center',formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='loadSchoolById("+row.schoolId+")'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px; '>编辑</span></span><a/>";
	        }}
		]],
		pagination:true,
		rownumbers:true,
		toolbar:"#addSchool_bar"
	});
		var p = $('#schoolList').datagrid('getPager');
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

function search1(){
    var searchSchoolName = $("#searchSchoolName").val();  
    if(searchSchoolName != ''){
    	var queryParams = $('#schoolList').datagrid('options').queryParams;
    	queryParams.schoolName = searchSchoolName;
        $('#schoolList').datagrid('options').queryParams=queryParams; 
    }
    $('#schoolList').datagrid('reload'); 
} 
function addSchool(){
	var schoolName=$("#addSchoolName").val();
	var schoolId=$("#schoolId").val();
	var shi=$("#shi").val();
	var qu=$("#qu").val();
	var areaId;
	if(schoolName==null||schoolName==""){
		$.messager.alert('系统提示','请填写学校名称');
		return;
	}
	if(qu!=null&&qu!=""&&qu!='-1'){
		areaId=qu;
	}else if(shi!=null&&shi!=""&&shi!='-1'){
		areaId=shi;
	}else{
		$.messager.alert('系统提示','请选择学校地址');
		return;
	}
	$.ajax({
		type: "POST",
		   url: path+"/school/addOrUpdate.action",
		   data: "bsSchool.bsArea.areaId="+areaId+"&bsSchool.schoolName="+schoolName+"&bsSchool.schoolId="+schoolId+"&math="+Math.random(),
		   success: function(data){
			   if(data.flag==1){
				   $.messager.alert('系统提示','操作成功');
			   }else{
				   $.messager.alert('系统提示','操作失败');
			   }
			   $('#addSchoolDiv').dialog('close');
				$('#schoolList').datagrid('reload'); 
		   },
			error:function(){
				$.messager.alert('系统提示','操作失败');
			}
		   
	});
}
function deleteSchool(){
	var ids = [];
	var rows = $('#schoolList').datagrid('getSelections');
	if(rows==null || rows.length==0){
		$.messager.alert('系统提示','请选择要删除的记录','info');
		return;
	}		
	$.messager.confirm('确认','确认删除?',function(f){
		if(f){
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].schoolId);
			}
			jQuery.ajax({
				type:"post",
				url:path+"/school/deleteSchool.action",
				data:"ids="+ids.toString(),
				async:false,
				success:function(data){
					if(data.flag==1){
						$.messager.alert('系统提示','删除成功');
						$('#schoolList').datagrid('reload'); 
					}else{
						$.messager.alert('系统提示','删除失败');
						$('#schoolList').datagrid('reload'); 
					}
				}
			});
		}
	});  
}
function loadSchoolById(schoolId){
	sheng();
	if(schoolId!=null&&schoolId!=""){
		$.ajax({
			   type: "POST",
			   url: path+"/school/loadSchoolById.action",
			   data: "bsSchool.schoolId="+schoolId+"&math="+Math.random(),
			   success: function(data){
				   if(data.bsSchool!=null){
					   $("#schoolId").val(data.bsSchool.schoolId);
					   $("#addSchoolName").val(data.bsSchool.schoolName);
					   if(data.bsSchool.bsArea!=null){
						   if(data.bsSchool.bsArea.bsArea.areaId!=null){
							   if(data.bsSchool.bsArea.bsArea.bsArea!=null){
								   $("#sheng").attr("value",data.bsSchool.bsArea.bsArea.bsArea.areaId);
								   shi();
								   $("#shi").attr("value",data.bsSchool.bsArea.bsArea.areaId);
								   qu();
								   $("#qu").attr("value",data.bsSchool.bsArea.areaId);
							   }else{
								   $("#sheng").attr("value",data.bsSchool.bsArea.bsArea.areaId);
								   shi();
								   $("#shi").attr("value",data.bsSchool.bsArea.areaId);
								   qu();
							   }
						   }
					   }
					   $('#addSchoolDiv').dialog({
						    closable:false,
							title:'编辑学校',
							modal:true
					   });
					   
					   
					  // $('#addSchoolDiv').dialog('option','title','aaa');
				   }
			   }
		});
	}
}
function openAddSchoolDiv(){
	$("#schoolId").val("");
	$("#addSchoolName").val("");
	 $('#addSchoolDiv').dialog({
		    closable:false,
			title:'添加学校',
			modal:true
	   });
	sheng();
}
function closeAddSchoolDiv(){
	$('#addSchoolDiv').dialog('close');
	$('#schoolList').datagrid('reload'); 
}
function sheng(){
	$.ajax({
		   type: "POST",
		   url: path+"/license/loadSheng.action",
		   data: "shengId="+null+"&math="+Math.random(),
		   async:false,
		   success: function(data){
			   $("#sheng").html("");
			   if(data.areaList.length>0){
				   $(data.areaList).each(function(){  
					   $("#sheng").append("<option value="+$(this).attr("areaId")+">" + $(this).attr("areaName")+ "</option>");
				   });
			   }else{
				   $("#sheng").append("<option value='-1' selected>--请选择--</option>");
				   $("#shi").html("<option value='-1' selected>--请选择--</option>");
				   $("#qu").html("<option value='-1' selected>--请选择--</option>");
			   }
			   shi();
		   }
		});
}
function shi(){
	var shengId=$("#sheng").val();
	$.ajax({
		   type: "POST",
		   url: path+"/license/loadSheng.action",
		   data: "shengId="+shengId+"&math="+Math.random(),
		   async:false,
		   success: function(data){
			   $("#shi").html("");
			   if(data.areaList.length>0){
				   $("#shi").html("");
				   $("#shi").append("<option value='-1' selected>--请选择--</option>");
				   $(data.areaList).each(function(){ 
					   $("#shi").append("<option value="+$(this).attr("areaId")+">" + $(this).attr("areaName")+ "</option>");
				   });
			   }else{
				   $("#shi").append("<option value='-1' selected>--请选择--</option>");
				   $("#qu").html("<option value='-1' selected>--请选择--</option>");
			   }
			   qu();
		   }
		});
}
function qu(){
	var shengId=$("#shi").val();
	$.ajax({
		   type: "POST",
		   url: path+"/license/loadSheng.action",
		   data: "shengId="+shengId+"&math="+Math.random(),
		   async:false,
		   success: function(data){
			   $("#qu").html("");
			   if(data.areaList.length>0){
				   $("#qu").html("");
				   $("#qu").append("<option value='-1' selected>--请选择--</option>");
				   $(data.areaList).each(function(){  
					   $("#qu").append("<option value="+$(this).attr("areaId")+">" + $(this).attr("areaName")+ "</option>");
				   });
			   }else{
				   $("#qu").append("<option value='-1' selected>--请选择--</option>");
			   }
		   }
		});
}