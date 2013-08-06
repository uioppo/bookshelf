function loadAllBook(idStr){
	var productName=$("#productName").val();
	$('#dategrid1').datagrid({
	height: $(document).height()/2+80,
	width:'100%',
	striped:true,
	singleSelect:true,
	url:path+'/license/loadProductAll.action',
	queryParams:{'productName':productName,'idStr':idStr},
	loadMsg:'数据加载中请稍后……',
	columns:[[
	    {field:'ck',checkbox:true},
	    {field:'productId',title:'id',width:150,hidden:true},
		{field:'productName',title:'名称',width:150,sortable:true,align:'center'},
		{field:'publishTime_',title:'出版时间',width:120,align:'center'},
		{field:'price',title:'价格',width:220,align:'center'},
		{field:'subject',title:'学科',width:220,align:'center'},
		{field:'schoolStage',title:'学段', width:220,sortable:true,align:'center',
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
	$('#dategrid2').datagrid({
		height: $(document).height()/2-200,
		width:'100%',
		border:0,
		striped:true,
		columns:[[
			{field:'ck1',checkbox:true},
			{field:'productId1',title:'id',width:150,hidden:true},
			{field:'productName1',title:'名称',width:150,sortable:true,align:'center'},
			{field:'publishTime1',title:'上架时间',width:120,align:'center'},
			{field:'price1',title:'价格',width:220,align:'center'},
			{field:'subject1',title:'学科',width:220,align:'center'},
			{field:'schoolStage1',title:'学段', width:220,sortable:true,align:'center',
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
		rownumbers:true,
		toolbar:"#tb1"
		});
}
function addRow(){
	var rows2 = $('#dategrid2').datagrid('getRows');
	if(rows2.length>0){
		$.messager.alert('温馨提示','只能选一本!');
		return;
	}
	var rows = $('#dategrid1').datagrid('getSelections');
	if(rows.length>0){
		for(var i =rows.length-1;i>=0;i--){
			$('#dategrid2').datagrid('insertRow',{
				row: {
				productId1:rows[i].productId,
				productName1: rows[i].productName,
				publishTime1: rows[i].publishTime,
				price1: rows[i].price,
				subject1: rows[i].subject,
				schoolStage1:rows[i].schoolStage
				}
			});
		}
		loadAllBook(getIdStr());
	}else{
		$.messager.alert('温馨提示','请先选择要添加的商品!');
	}
}
function removeRow(){
	var rows3 = $('#dategrid2').datagrid('getSelections');
	if(rows3.length>0){
		for(var k=rows3.length-1;k>=0;k--){
			var index = $('#dategrid2').datagrid('getRowIndex',rows3[k]);
			$('#dategrid2').datagrid('deleteRow',index);
		}
		loadAllBook("");
		var chk= $('input[type="checkBox"]:checked');
		if(chk!=null&&chk.length>0){
			for(var i=0;i<chk.length;i++){
				$(chk[i]).attr('checked',false);
			}
		}
	}else{
		$.messager.alert('温馨提示','请先选择要移除的商品!');
	}
}
/*function getIdStr(){
	var rows2 = $('#dategrid2').datagrid('getRows');
	if(rows2!=null){
		var idStr="";
		for(var j =0;j<rows2.length;j++){
			idStr+=rows2[j].productId1+",";
		}	
		if(idStr!=null&&idStr!=""&&idStr.replace(',', '')!=""){
			idStr=idStr.substring(0, idStr.length-1);
			return idStr;
		}else{
			return "";
		}
	}else{
		return "";
	}
}*/
function getIdStr(){
	var rows2 = $('#dategrid2').datagrid('getRows');
	if(rows2!=null&&rows2.length>0){
		var idStr=rows2[0].productId1;
		if(idStr!=null&&idStr!=""){
			return idStr;
		}else{
			return "";
		}
	}else{
		return "";
	}
}
function nextStep(){
	var idStr=getIdStr();
	if(idStr!=null&&idStr!=""){
		$("#idStr").val(idStr);
		$("#stepOne").hide();
		$("#stepTwo").show();
	}else{
		$.messager.alert('温馨提示','请先选择要添加的商品!');
	}
}
function lastStep(){
	$("#idStr").val("");
	$("#stepOne").show();
	$("#stepTwo").hide();
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
					   $("#sheng").append("<option value="+$(this).attr("areaId")+">" + $(this).attr("areaName")+ "</option>");
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
					   $("#shi").append("<option value="+$(this).attr("areaId")+">" + $(this).attr("areaName")+ "</option>");
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
					   $("#qu").append("<option value="+$(this).attr("areaId")+">" + $(this).attr("areaName")+ "</option>");
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
					   $("#school").append("<option value="+$(this).attr("schoolId")+">" + $(this).attr("schoolName")+ "</option>");
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
		$.messager.alert('温馨提示','操作出现错误,请重新操作!');
		lastStep();
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
		$.messager.alert('温馨提示','请先选择区域!');
		return;
	}
	$("#quId").val(shengId);
	var schoolId=$("#school").val();
	if(schoolId==null||schoolId=="-1"||schoolId==""||schoolId=="--请选择--"){
		$.messager.alert('温馨提示','请先选择学校!');
		return;
	}
	var keySize=$("#keySize").val();
	if(keySize==null||keySize==""){
		$.messager.alert('温馨提示','请先输入授权码数量!');
		return;
	}
	var lifetime=$("#lifetime1").datebox('getValue');
	if(lifetime==null||lifetime==""){
		$.messager.alert('温馨提示','请先选择使用期限!');
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
	$("#lifeTime").val(lifetime);
	$("#keyForm").submit();
}
function search(){
	var idStr=getIdStr();
	if(idStr!=null&&idStr!=""){
		loadAllBook(idStr);
	}else{
		loadAllBook('');
	}
}