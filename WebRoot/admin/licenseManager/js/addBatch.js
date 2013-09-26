$(function(){
	$('#stepOne').dialog({
		closed:true,
		title:'选择商品',
		width:640,
		height:410,
		modal:true,
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				addRow();
				$('#stepOne').dialog('close');
			}
		},{
			text:'取消',
			handler:function(){
				$('#stepOne').dialog('close');
			}
		}]
	});
	$('#showbox').dialog({
		closed:true,
		title:'请稍候...',
		width:250,
		height:80,
		modal:true,
		closable:false
	});
	loadAllBook("");
	pattern_sel();
});
function loadAllBook(idStr){
	var productName=$("#productName").val();
	$('#dategrid1').datagrid({
	height: 340,
	width:'100%',
	striped:true,
	singleSelect:false,
	idField:"productId",
	url:path+'/license/loadProductAll.action',
	queryParams:{'productName':productName,'idStr':idStr},
	loadMsg:'数据加载中请稍后……',
	frozenColumns:[[ {field:'ck',checkbox:true},
	         	    {field:'productId',title:'id',width:150,hidden:true},
	         	   {field:'productName',title:'名称',width:150,sortable:true,align:'center'}]],
	columns:[[
		{field:'publishTime_',title:'出版时间',width:120,align:'center'},
		{field:'price',title:'价格',width:120,align:'center'},
		{field:'subject',title:'学科',width:120,align:'center'},
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
		}
	]],
	pagination:true,
	rownumbers:true,
	toolbar:"#tb"
	});
	var p = $('#dategrid1').datagrid('getPager');
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
function selectBook(){
	$('#stepOne').dialog('open');
//	loadAllBook("");
}
function addRow(){
	var selecteds = $('#dategrid1').datagrid('getSelections');
	if (selecteds){
		var idstrs = "";
		var productNames = "";
		$.each(selecteds,function(key,val){
			if(idstrs!=""){
				idstrs +=",";
			}
			if(productNames!=""){
				productNames +=",";
			}
			
			idstrs +=val.productId;
			productNames +=val.productName;
		});
		$('#idStr').val(idstrs);
		$('#selectbook').html(productNames);
	}
}

function pattern_sel(){
	$.ajax({
		   type: "POST",
		   url: path+"/admin/dictionary/findByType.action",
		   data: {"type":3},
		   success: function(data){
			   $("#pattern_sel").html("");
			   $(data.rows).each(function(){
				   $("#pattern_sel").append("<option value="+this.dicCode+">" + this.name+ "</option>");
			   });
		   }
		});
}
function sheng(){
	$.ajax({
		   type: "POST",
		   url: path+"/license/loadSheng.action",
		   data: "shengId="+null+"&math="+Math.random(),
		   success: function(data){
			   $("#sheng").html("");
			   if(data.areaList.length>0){
				   $(data.areaList).each(function(){  
					   $("#sheng").append("<option value="+this.areaId+">" + this.areaName+ "</option>");
				   });
			   }else{
				   $("#sheng").append("<option value='-1'>--请选择--</option>");
				   $("#shi").html("<option value='-1'>--请选择--</option>");
				   $("#qu").html("<option value='-1'>--请选择--</option>");
				   $("#school").html("<option value='-1'>--请选择--</option>");
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
		   success: function(data){
			   $("#shi").html("");
			   if(data.areaList.length>0){
				   $("#shi").append("<option value='-1' selected>--请选择--</option>");
				   $(data.areaList).each(function(){ 
					   $("#shi").append("<option value="+this.areaId+">" +this.areaName+ "</option>");
				   });
			   }else{
				   $("#shi").append("<option value='-1'>--请选择--</option>");
				   $("#qu").html("<option value='-1'>--请选择--</option>");
				   $("#school").html("<option value='-1'>--请选择--</option>");
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
		   success: function(data){
			   $("#qu").html("");
			   if(data.areaList.length>0){
				   $("#qu").append("<option selected value='-1'>--请选择--</option>");
				   $(data.areaList).each(function(){ 
					   $("#qu").append("<option value="+this.areaId+">" + this.areaName+ "</option>");
				   });
			   }else{
				   $("#qu").append("<option value='-1'>--请选择--</option>");
				   $("#school").html("<option value='-1'>--请选择--</option>");
			   }
			   school();
		   }
		});
}
function school(){
	var shengId;
	var quId=$("#qu").val();
	var shiId=$("#shi").val();
	if(quId!=null&&quId!=""&&quId!="-1"){
		shengId=quId;
	}else if(shiId!=null&&shiId!=""&&shiId!="-1"&&shiId!="--请选择--"){
		shengId=shiId;
	}else{
		$("#school").html("<option value='-1'>--请选择--</option>");
		return;
	}
	$.ajax({
		   type: "POST",
		   url: path+"/license/loadSchool.action",
		   data: "shengId="+shengId+"&math="+Math.random(),
		   success: function(data){
			   $("#school").html("");
			   if(data.schoolList.length>0){
				   $(data.schoolList).each(function(){  
					   $("#school").append("<option value="+this.schoolId+">" + this.schoolName+ "</option>");
				   });
			   }else{
				   $("#school").append("<option value='-1'>--请选择--</option>");
			   }
		   }
		});
}
function submitForm(){
	var idStr=$("#idStr").val();
	if(idStr==null||idStr==""||idStr.replace(',', '')==""){
		$.messager.alert('温馨提示','请选择授权的商品!','info');
		return;
	}
	var shengId;
	var quId=$("#qu").val();
	var shiId=$("#shi").val();
	if(quId!=null&&quId!=""&&quId!="-1"&&shiId!="--请选择--"){
		shengId=quId;
	}else if(shiId!=null&&shiId!=""&&shiId!="-1"&&shiId!="--请选择--"){
		shengId=shiId;
	}else{
		$.messager.alert('温馨提示','请先选择区域!','info');
		return;
	}
	$("#quId").val(shengId);
	var schoolId=$("#school").val();
	if(schoolId==null||schoolId=="-1"||schoolId==""||schoolId=="--请选择--"){
		$.messager.alert('温馨提示','请先选择学校!','info');
		return;
	}
	var keySize=$("#keySize").val();
	if(keySize==null||keySize==""){
		$.messager.alert('温馨提示','请先输入授权码数量!','info');
		return;
	}
	var lifetime=$("#lifetime1").datebox('getValue');
	if(lifetime==null||lifetime==""){
		$.messager.alert('温馨提示','请先选择使用期限!','info');
		return;
	}else{
		 try{
            var strdt1=lifetime.replace(/-/g, "/");
            var dt1=new Date(Date.parse(strdt1));
            if(dt1<new Date()){
            	$.messager.alert('温馨提示','使用期限要大于今天!');
            	return;
            }
	     }catch(e){
	    	 	$.messager.alert('温馨提示','操作失败！');
	    	 	return;
	     }
	}
	$('#showbox').dialog("open");
	$("#lifeTime").val(lifetime);
	$("#keyForm").submit();
}
function search(){
	loadAllBook('');
}