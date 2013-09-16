function loadWebUserList(){
	var wuEmail= $("#wuEmail").val();
	$('#webUserList').datagrid({
	height: $(document).height(),
	width:'100%',
	border:0,
	striped:true,
	singleSelect:true,
	url:path+'/webuser/loadWebUserList.action',
	queryParams:{'wuEmail':wuEmail,'sort':'wuId','order':'desc'},
	loadMsg:'数据加载中请稍后……',
	columns:[[
		{field:'wuEmail',title:'用户名',width:150,sortable:true,align:'center'},
		{field:'bsUserInfo.realName',title:'姓名',width:120,align:'center',formatter:function(value,row,index){
			if(row.bsUserInfo!=null){
				return row.bsUserInfo.realName;
			}
		}},
		{field:'bsUserInfo.school',title:'所在学校',width:220,align:'center',formatter:function(value,row,index){
			if(row.bsUserInfo!=null&&row.bsUserInfo.school!=-1&&row.bsUserInfo.school!=0){
				return row.bsUserInfo.school;
			}else{
				return "";
			}
		}},
		{field:'wuRegTime_',title:'注册时间', width:220,align:'center'},
		{field:'lastLogin_',title:'上次登录时间', width:220,align:'center'},
		{field:'wuActivestatus',title:'状态',width:180,sortable:true,align:'center',formatter:function(value,row,index){
			if(row.wuActivestatus==1){
				return "已激活";
			}else if(row.wuActivestatus==2){
				return "禁用";
			}else if(row.wuActivestatus==3){
				return "锁定";
			}else if(row.wuActivestatus==0){
				return "未激活";
			}
		}},
		{field:'edit',title:'操作',width:200,align:'right',
			formatter: function(value,row,index){
				if(row.wuActivestatus==1){
					return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-plain" onclick="updateStatus ('+row.wuId+',2,'+index+')"><span class="l-btn-left"><span class="l-btn-text icon-stop" style="padding-left: 20px; ">禁用</span></span></a>'+
					'<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-plain" onclick="editWebUser('+row.wuId+')"><span class="l-btn-left"><span class="l-btn-text icon-edit" style="padding-left: 20px; ">查看/编辑</span></span></a>'
					;
				}else if(row.wuActivestatus==2){
					return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-plain" onclick="updateStatus ('+row.wuId+',1,'+index+')"><span class="l-btn-left"><span class="l-btn-text icon-play" style="padding-left: 20px; ">启用</span></span></a>'+
					'<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-plain" onclick="editWebUser('+row.wuId+')"><span class="l-btn-left"><span class="l-btn-text icon-edit" style="padding-left: 20px; ">查看/编辑</span></span></a>'
					;
				}else{
					return '<a href="javascript:void(0)" class="easyui-linkbutton l-btn l-btn-plain" onclick="editWebUser('+row.wuId+')"><span class="l-btn-left"><span class="l-btn-text icon-edit" style="padding-left: 20px; ">查看/编辑</span></span></a>'
					;
				}
			}
		}
	]],
	pagination:true,
	rownumbers:true,
	toolbar:"#tb"
	});
	var p = $('#webUserList').datagrid('getPager');
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

function updateStatus(wuId,status,index){
	var str = "";
	if(status==2){
		str = "确认禁用该用户么?";
	}else{
		str = "确认启动该用户么?";
	}
	$.messager.confirm('确认',str,function(f){
		if(f){
			$.ajax({
				   type: "POST",
				   url: path+"/webuser/updateWebUserStatus.action",
				   data: "bsWebUser.wuId="+wuId+"&bsWebUser.wuActivestatus="+status+"&math="+Math.random(),
				   async: false,
				   success: function(msg){
					   $('#webUserList').datagrid('reload');
				   }
			});
		}
	});
}
function editWebUser(wuId){
	window.location.href=path+"/webuser/loadWebUserById.action?bsWebUser.wuId="+wuId;
}
function sheng(){
	$("#sheng").empty();
	$.ajax({
		   type: "POST",
		   url: path+"/license/loadSheng.action",
		   data: "shengId="+null+"&math="+Math.random(),
		   async:false,
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
	if(shengId==null||shengId==""){
		$("#shi").html("<option value='-1'>--请选择--</option>");
	   $("#qu").html("<option value='-1'>--请选择--</option>");
	   $("#school").html("<option value='-1'>--请选择--</option>");
	}else{
	$.ajax({
		   type: "POST",
		   url: path+"/license/loadSheng.action",
		   data: "shengId="+shengId+"&math="+Math.random(),
		   async:false,
		   success: function(data){
			   $("#shi").html("");
			   if(data.areaList.length>0){
				   $("#shi").html("<option value='-1'>--请选择--</option>");
				   $(data.areaList).each(function(){  
					   $("#shi").append("<option value="+this.areaId+">" +this.areaName+ "</option>");
				   });
			   }else{
				   $("#shi").html("<option value='-1'>--请选择--</option>");
				   $("#qu").html("<option value='-1'>--请选择--</option>");
				   $("#school").html("<option value='-1'>--请选择--</option>");
			   }
			   qu();
		   }
		});
	}
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
				   $("#qu").html("<option value='-1'>--请选择--</option>");
				   $(data.areaList).each(function(){  
					   $("#qu").append("<option value="+this.areaId+">" + this.areaName+ "</option>");
				   });
			   }else{
				   $("#qu").html("<option value='-1'>--请选择--</option>");
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
	$("#school").empty();
	if(quId!=null&&quId!=""&&quId!="-1"&&shiId!="--请选择--"){
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
		   async:false,
		   success: function(data){
			   $("#school").html("");
			   if(data.schoolList.length>0){
				   $("#school").html("<option value='-1'>--请选择--</option>");
				   $(data.schoolList).each(function(){  
					   $("#school").append("<option value="+this.schoolName+">" + this.schoolName+ "</option>");
				   });
			   }else{
				   $("#school").html("<option value='-1'>--请选择--</option>");
			   }
		   }
		});
}
function initarea(){
	if($("#sheng1").val()!=null&&$("#sheng1").val()!=""&&$("#sheng1").val()!=-1){
		$("#sheng").attr("value",$("#sheng1").val());
		shi();
	}
	if($("#shi1").val()!=null&&$("#shi1").val()!=""&&$("#shi1").val()!=-1){
		$("#shi").attr("value",$("#shi1").val());
		qu();
		school();
	}
	if($("#qu1").val()!=null&&$("#qu1").val()!=""&&$("#qu1").val()!=-1){
		$("#qu").attr("value",$("#qu1").val());
		school();
	}
	if($("#school1").val()!=null&&$("#school1").val()!=""&&$("#school1").val()!=-1){
		$("#school").attr("value",$("#school1").val());
	}
}
function webUserFormSub(){
	var flag=true;
	var lifetime=$("#lifetime1").datebox('getValue');
	$("#lifeTime").val(lifetime);
	flag=flag&&checkmobile();
	//flag=flag&&checkAge();
	if(flag){
		$("#webUserForm").submit();
	}
}
function checkmobile() {
	var flag = false;
	var mobile = $("#mobile").val();
	if (mobile==null||mobile == "") {
		$("#mobileMessage").text("");
		flag = true;
		return flag;
		}
	if (mobile != "" || $.trim(mobile) != "") {
		//var reg = /^0?1[358]\d{9}$/;
		var reg = /(^1[358]\d{9}$)|(^\d{7,8}$)|(^(\d{4}|\d{3})-(\d{7,8})$)|(^\+861[358]\d{9}$)/;
		if (!reg.test(mobile)) {
			$("#mobileMessage").text("请输入正确的联系方式").css("color", "red");
			flag = false;
		} else {
			$("#mobileMessage").text("");
			flag = true;

		}
	}
	return flag;
}

function add_user(){
	window.location.href=path+"/webuser/loadWebUserById.action";
}
/*function checkAge(){
	var flag = false;
	var age = $("#age").val();
	if (age == "") {
		$("#ageMessage").text("");
		flag = true;
	}
	if (age != "" || $.trim(age) != "") {
		var reg = /^\d{1,3}$/;
		if (!reg.exec(age)) {
			$("#ageMessage").text("请输入正确的年龄").css("color", "red");
			flag = false;
		} else {
			$("#ageMessage").text("");
			flag = true;
		}
	}
	return flag;
}*/