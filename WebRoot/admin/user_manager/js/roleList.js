$(function(){
	$("#editFunction").dialog({
		width:550,
		height:410,
		closed:true,
		modal:true,
		iconCls:"icon-manager",
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				updateFunction();
			}
		},{
			text:'取消',
			handler:function(){
				colseFunction();
			}
		}]
	});
	loadRoleFunctionList();
});	
function loadRoleList(){
			$('#roleList').datagrid({
				loadMsg:'数据加载中请稍后……', 
				width:'100%',
				height:$(document).height(),
				nowrap: false,
				checkOnSelect:false,
				striped: true,
				collapsible:false,
				url:path+'/admin/user/findAllRole.action',
				remoteSort: false,
				singleSelect:true,
				idField:'roleId',
				columns:[[
					//{title:'选择',field:'roleId',checkbox:true},
	                {title:'角色名称',field:'roleName',width:300,sortable:true,align:'center'},
	                {title:'详细信息',field:'description',width:473,align:'center'},
			        {title:'操作',field:'cz',width:350,align:'center',formatter:function(value,row,index){
			        	var s="";
			        	if(row.roleName=='管理员'){
			        		
			        	}else{
			        		s += "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='editRole("+row.roleId+")'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px; '>编辑</span></span><a/>";
				        	s += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			        		s +="<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='roleToFunction("+row.roleId+")'><span class='l-btn-left'><span class='l-btn-text icon-manager' style='padding-left: 20px; '>分配权限</span></span><a/>";
			        		s += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			        		s +="<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='delRole("+row.roleId+")'><span class='l-btn-left'><span class='l-btn-text icon-no' style='padding-left: 20px; '>删除</span></span><a/>";
			        	}
			        	return s;
			        }}
				]],
				rowStyler:function(index,row,css){
					if (row.roleName=='管理员'){
						return 'background-color:#f3f3f3;color:#000;font-weight:bold;';
					}
				},
				rownumbers:true,
				toolbar:"#rolelist_bar"
			});
		}
		
		function insertIntoRole(){
			var roleName = $("#addRoleNameInput").val();
			var description = $("#addRoleDescriptionInput").val();
			if(roleName==null || roleName=="" || roleName==undefined){
				$.messager.alert('温馨提示','请输入角色名.','info');
				return;
			}
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/saveRole.action",
				data:{"bsRole.roleName":roleName,"bsRole.description":description},
				dataType:"json",
				async:false,
				success:function(data){
					if(data.flag==false || data.flag=='false'){
						$.messager.alert('温馨提示','该角色名已经存在.','info');
						return ;
					}else{
						closeAddRoleDiv();
						$('#roleList').datagrid('reload'); 
					}
				}
			});
		}
		function delRole(roleId){
			if(roleId!=null&&roleId!=""){
				$.messager.confirm('确认','确认删除?',function(f){
					if(f){
						jQuery.ajax({
							type:"post",
							url:path+"/admin/user/deleteRole.action",
							data:"ids="+roleId,
							async:false,
							success:function(data){
								if(data.flag==true){
									$.messager.alert('温馨提示','删除成功');
									$('#roleList').datagrid('reload'); 
								}else{
									$.messager.alert('温馨提示','该角色已有用户使用 ，不能删除');
								}
							}
						});
					}
				}); 
			}
		}
		function editRole(roleId){
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/findRoleById.action",
				data:{"bsRole.roleId":roleId},
				async:false,
				success:function(data){
					$("#editUserRoleId").val("");
					$("#editRoleName").val("");
					$("#editRoleDescription").val("");
					$("#editUserRoleId").val(data.bsRole.roleId);
					$("#editRoleName").val(data.bsRole.roleName);
					$("#editRoleDescription").val(data.bsRole.description);
					$('#editRoleDiv').dialog({modal:true});
				}
			});
		
		}
		
		function updateRole(){
			var roleName = $("#editRoleName").val();
			var description = $("#editRoleDescription").val();
			var roleId = $("#editUserRoleId").val();
			if(roleName==null || roleName=="" || roleName==undefined){
				$.messager.alert('温馨提示','请输入角色名.','info');
				return;
			}
			
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/updateRole.action",
				data:{"bsRole.roleId":roleId,"bsRole.roleName":roleName,"bsRole.description":description},
				async:false,
				success:function(data){
					closeEditRoleDiv();
					$('#roleList').datagrid('reload'); 
				}
			});
		}
		
		function closeAddRoleDiv(){
			$('#addRoleDiv').dialog('close');
		}
		
		function closeEditRoleDiv(){
			$('#editRoleDiv').dialog('close');
		}
		
		function add_Role(){
			$("#addRoleNameInput").val("");
			$("#addRoleDescriptionInput").val("");
			$('#addRoleDiv').dialog({modal:true});
		}
		
		function loadRoleFunctionList(){
			$('#funTable').treegrid({
				loadMsg:'数据加载中请稍后……', 
				width:'100%',
				height:338,
				nowrap: false,
				rownumbers: false,
				animate:true,
				collapsible:false,
				singleSelect:false,
				cascadeCheck:true,
				url:path+'/admin/user/findAllFunction.action',
				idField:'id',
				treeField:'name',
				frozenColumns:[[
				    {title:'选择',field:'code',checkbox:true},
				    {title:'名称',field:'name',width:180}
				]],
				columns:[[
	                {title:'描述',field:'description',width:300, align:'left'}
				]],
				onSelect: function(node){
					var nodes = $('#funTable').treegrid('getChildren', node.id);
					for(var i=0; i<nodes.length; i++){
						 $('#funTable').treegrid('select', nodes[i].id);
					}
				},
				onUnselect:function(node){
					var nodes = $('#funTable').treegrid('getChildren', node.id);
					for(var i=0; i<nodes.length; i++){
						 $('#funTable').treegrid('unselect', nodes[i].id);
					}
				}
			});
		}

		function roleToFunction(roleId){
			$("#editFunction").dialog("open");
			$("#roleFunction_R").val("");
			$("#roleFunction_R").val(roleId);
			$('#funTable').treegrid("loading");
			$('#funTable').treegrid("unselectAll");
			
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/findRoleFunctionIds.action",
				dataType:"json",
				data:{'bsRole.roleId':roleId},
				success:function(json){
					jQuery(json).each(function(i){
						$('#funTable').treegrid("select",this);
					});
					$('#funTable').treegrid("loaded");
				}
			});
		}
		
		function updateFunction(){
			var roleId = $("#roleFunction_R").val();
			var functionIdString = "";
		
			$('#funTable').treegrid("getSelections");
			var ids = [];
			var rows = $('#funTable').treegrid('getSelections');
			for(var i=0;i<rows.length;i++){
				ids.push(rows[i].code);
			}
			functionIdString=ids.join(',');
			//alert(functionIdString);
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/updateRoleFunction.action",
				data:"bsRole.roleId="+roleId+"&functionIds="+functionIdString,
				async:false,
				success:function(){
					colseFunction();
					$('#roleList').datagrid('reload'); 
				}
			});
		}
		
		function colseFunction(){
			$("#editFunction").dialog("close");
		}
		function colseFunction_(){
			$("#loadFunction").dialog("close");
		}