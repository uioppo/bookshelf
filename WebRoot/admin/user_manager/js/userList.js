function loadUserList(){
			$('#userList').datagrid({
				loadMsg:'数据加载中请稍后……', 
				width:'100%',
				height:$(document).height(),
				nowrap: false,
				striped: true,
				collapsible:false,
				checkOnSelect:false,
				url:path+'/admin/user/findAllUser.action',
				sortName: 'code',
				sortOrder: 'desc',
				remoteSort: false,
				singleSelect:true,
				idField:'auId',
				columns:[[
					//{title:'选择',field:'auId',checkbox:true},
	                {title:'用户名',field:'userName',width:150,sortable:true,align:'center'},
	                {title:'邮箱',field:'email',width:160,align:'center'},
			        {title:'角色',field:'bsRaole',width:100,align:'center',
			        	formatter: function(value,row,index){
							return row.bsRole.roleName;
					}},
			        {title:'真实姓名',field:'realName',width:200,align:'center'},
			        {title:'状态',field:'status',width:100,align:'center',formatter: function(value,row,index){
							if(1==row.status){
								return "启用";
							}else{
								return "禁用";
							}
							
					}},
			        {title:'上次登录时间',field:'lastTimeString',width:200,align:'center'},
			        {title:'操作',field:'cz',width:200,align:'center',formatter:function(value,row,index){
			        	var s = "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='editUser("+row.auId+")'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px; '>编辑</span></span><a/>";
			        	if(row.userName=='admin'){
			        		return s;
			        	}else{
			        		s += "&nbsp;&nbsp;";
			        		if(row.status==1){
				        		s +="<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='status_("+row.status+","+row.auId+")'><span class='l-btn-left'><span class='l-btn-text icon-stop' style='padding-left: 20px; '>禁用</span></span><a/>";
				        	}else{
				        		s+="<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='status_("+row.status+","+row.auId+")'><span class='l-btn-left'><span class='l-btn-text icon-play' style='padding-left: 20px; '>启用</span></span><a/>";
				        	}
			        		s += "&nbsp;&nbsp;";
			        		s+="<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='deleteUser("+row.auId+")'><span class='l-btn-left'><span class='l-btn-text icon-no' style='padding-left: 20px; '>删除</span></span><a/>";
			        		return s;
			        	}
			        }}
				]],
				rowStyler:function(index,row,css){
					if (row.userName=='admin'){
						return 'background-color:#f3f3f3;color:#000;font-weight:bold;';
					}
				},
				pagination:true,
				rownumbers:true,
				toolbar:"#userlist_bar"
			});
			
			var p = $('#userList').datagrid('getPager');
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
		
		function status_(s,id){
			var str = "";
			if(s==1){
				str = "确认禁用该用户么?";
			}else{
				str = "确认启动该用户么?";
			}
			$.messager.confirm('确认',str,function(f){
				if(f){
					jQuery.ajax({
						type:"post",
						url:path+'/admin/user/updateStatusById.action',
						data:{"bsAdminUser.auId":id,"bsAdminUser.status":s},
						success:function(){
							$('#userList').datagrid('reload'); 
						}
					});
				}
			});
		}
		
		function fillRole(){
			jQuery.ajax({
				type:"post",url:path+'/admin/user/findAllRole.action',dataType:"json",success:function(data){
					var roleHtml = "";
					jQuery(data.rows).each(function(i){
						roleHtml += "<option value='"+this.roleId+"'>"+this.roleName+"</option>";
					});			
					$("#roleIdAddUserSelect").html(roleHtml);
				}
			});				
		}
		
		function insertIntoUser(){
			var userName = $("#addUserNameInput").val();
			var password = $("#addUserPasswordInput").val();
			var roleId = $("#roleIdAddUserSelect").val();
			var realName = $("#addRealNameInput").val();
			var email = $("#addEmailInput").val();
			var re = new RegExp("[\\u4e00-\\u9fa5]", "");
			if(userName==null || userName==""){
				$.messager.alert('温馨提示','请输入用户名.','info');
				return;
			}
			if(re.test(userName)){
				$.messager.alert('温馨提示','用户名中含有符号.','info');
				return;
			}
			if(password==null || password==""){
				$.messager.alert('温馨提示','请输入密码.','info');
				return;
			}
			if(email==null || email==""){
				$.messager.alert('温馨提示','请输入邮箱.','info');
				return;
			}
			if(realName==null || realName==""){
				$.messager.alert('温馨提示','请输入真实姓名.','info');
				return;
			}
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/saveUser.action",
				data:{"bsAdminUser.userName":userName,"bsAdminUser.password":password,"bsAdminUser.realName":realName,"bsAdminUser.bsRole.roleId":roleId,"bsAdminUser.email":email},
				dataType:"json",
				async:false,
				success:function(data){
					if(data.flag==false || data.flag=='false'){
						$.messager.alert('温馨提示','该用户名已经存在.','info');
						return ;
					}else{
						closeAddUserDiv();
						$('#userList').datagrid('reload'); 
					}
				}
			});
		}
		
		/*function deleteUser(){
			var ids = [];
			var rows = $('#userList').datagrid('getChecked');
			if(rows==null || rows.length==0){
				$.messager.alert('温馨提示','请选择要删除的记录','info');
				return;
			}		
			$.messager.confirm('确认','确认删除?',function(f){
				if(f){
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].auId);
					}
					jQuery.ajax({
						type:"post",
						url:path+"/admin/user/deleteUser.action",
						data:"ids="+ids.toString(),
						async:false,
						success:function(data){
							$('#userList').datagrid('reload'); 
						}
					});
				}
			});  
		}*/
		function deleteUser(userId){
			if(userId==null || userId==0){
				return;
			}		
			$.messager.confirm('确认','确认删除?',function(f){
				if(f){
					jQuery.ajax({
						type:"post",
						url:path+"/admin/user/deleteUser.action",
						data:"ids="+userId,
						async:false,
						success:function(data){
							$('#userList').datagrid('reload'); 
						}
					});
				}
			});  
		}
		
		function editUser(userId){
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/findUserById.action",
				data:{"bsAdminUser.auId":userId},
				async:false,
				success:function(data){
					$("#editUserName").val("");
					$("#passwordE").val("");
					$("#editEmailInput").val("");
					$("#editRealNameInput").val("");
					$("#roleIdEditUserSelect").val("");
					$("#editUserId").val("");
			 		$("#editUserStatus").val("");
			 		$("#editUserTime").val("");
			 		$("#newPassword").val("");
			 		$("#newPassword2").val("");
					jQuery.ajax({
						type:"post",url:path+'/admin/user/findAllRole.action',dataType:"json",success:function(json){
							var roleHtml = "";
							jQuery(json.rows).each(function(i){
								if(data.bsAdminUser.bsRole.roleId!=null && this.roleId!=null && data.bsAdminUser.bsRole.roleId==this.roleId){
									roleHtml += "<option value='"+this.roleId+"' selected='selected'>"+this.roleName+"</option>";
								}else{
									roleHtml += "<option value='"+this.roleId+"'>"+this.roleName+"</option>";
								}
							});			
							$("#roleIdEditUserSelect").html(roleHtml);
							if(data.bsAdminUser.userName=='admin'){
								$("#roleIdEditUserSelect").attr('disabled','disabled');
							}else{
								$("#roleIdEditUserSelect").attr('disabled',null);
							}
						}
					});
					$("#passwordE").val(data.bsAdminUser.password);
					$("#editUserName").val(data.bsAdminUser.userName);
					$("#editEmailInput").val(data.bsAdminUser.email);
					$("#editRealNameInput").val(data.bsAdminUser.realName);
					$("#roleIdEditUserSelect").val(data.bsAdminUser.bsRole.roleId);
			 		$("#editUserId").val(data.bsAdminUser.auId);
			 		$("#editUserStatus").val(data.bsAdminUser.status);
			 		$("#editUserTime").val(data.bsAdminUser.lastTime);
					$('#editUserDiv').dialog({});
				}
			});
		
		}
		
		function updateUser(){
			var userName = $("#editUserName").val();
			var email = $("#editEmailInput").val();
			var realName = $("#editRealNameInput").val();
			var roleId = $("#roleIdEditUserSelect").val();
			var password = $("#newPassword").val();
			var password2 = $("#newPassword2").val();
			var auId = $("#editUserId").val();
			var status = $("#editUserStatus").val();
			var time = $("#editUserTime").val();
			var tag = 1;
			if(password==null || password=="" || password==undefined){
				tag = 2;
				password = $("#passwordE").val();
			}else{
				if(password2==null || password2==""){
					$.messager.alert('温馨提示','请输入确认密码.','info');
					return;
				}
				if(password!=null && password!=password2){
					$.messager.alert('温馨提示','俩次密码不一致.','info');
					return;
				}
			}
			if(email==null || email==""){
				$.messager.alert('温馨提示','请输入邮箱.','info');
				return;
			}
			if(realName==null || realName==""){
				$.messager.alert('温馨提示','请输入真实姓名.','info');
				return;
			}
			
			jQuery.ajax({
				type:"post",
				url:path+"/admin/user/updateUser.action",
				data:{"bsAdminUser.userName":userName,"bsAdminUser.password":password,"bsAdminUser.realName":realName,"bsAdminUser.bsRole.roleId":roleId,"bsAdminUser.email":email
				,"bsAdminUser.auId":auId,"bsAdminUser.status":status,"bsAdminUser.lastTime":time,"tag":tag},
				async:false,
				success:function(data){
					closeEditUserDiv();
					$('#userList').datagrid('reload'); 
				}
			});
		}
		
		function closeAddUserDiv(){
			$('#addUserDiv').dialog('close');
		}
		
		function closeEditUserDiv(){
			$('#editUserDiv').dialog('close');
		}
		
		function searcher(value,name){
			$('#userList').datagrid("load",{
				searchValue:value,
				searchName:name
			});
		}
		
		function add_user(){
			$("#addUserNameInput").val("");
			$("#addUserPasswordInput").val("");
			$("#roleIdAddUserSelect").val("");
			$("#addRealNameInput").val("");
			$("#addEmailInput").val("");
			fillRole();
			$('#addUserDiv').dialog({});
		}