$(function(){
		var urle = path+"/admin/dictionary/findByType.action?type=3&dd="+Math.random() ;
		$('#publishingList').datagrid({
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
				{field:'dicCode',title:'代码',width:130,align:'center',resizable:true},
				{field:'name',title:'渠道',width:100,align:'center',resizable:true},
				{title:'操作',field:'cz',width:400,align:'center',formatter:function(value,row,index){
				var s = "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='findPublish("+row.id+")'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px; '>编辑</span></span><a/>";
				s += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				s += "<a href='javascript:void(0);' class='easyui-linkbutton l-btn l-btn-plain' onclick='deletePublish("+row.id+")'><span class='l-btn-left'><span class='l-btn-text icon-no' style='padding-left: 20px; '>删除</span></span><a/>";
				return s;
			}}
			]],
			pagination:true,
			rownumbers:true,
			toolbar:"#top"
		});
		
		
		jQuery("#add").dialog({
			closed:true,
			modal:true,
			title:"添加渠道",
			width:300,
			height:150,
			buttons:[{
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
					addPublish();
				}
			},{
				text:'取消',
				handler:function(){
					$('#add').dialog('close');
				}
			}]
		});
		
		jQuery("#edit").dialog({
			closed:true,
			modal:true,
			title:"添加渠道",
			width:300,
			height:150,
			buttons:[{
				text:'保存',
				iconCls:'icon-ok',
				handler:function(){
					editPublish();
				}
			},{
				text:'取消',
				handler:function(){
					$('#edit').dialog('close');
				}
			}]
		});
		
	});
	function open1(){
		$("#dicCode").val("");
		$("#dicName").val("");
		jQuery("#add").dialog("open");
	}
	function addPublish(){
		var flag = true ;
		flag = flag && $("#dicCode").validatebox("isValid");
		flag = flag && $("#dicName").validatebox("isValid");
		if(!flag){
			$.messager.alert("提示","请输入完整信息!");
			return;
		}
		$.ajax({
			type:"POST",
			url:path+"/admin/dictionary/addDictionary.action",
			async:false,
			data:"bsDictionary.dicCode="+$("#dicCode").val()+"&bsDictionary.type=3&bsDictionary.name="+$("#dicName").val(),
			success:function(){
				$('#add').dialog('close');
				$("#publishingList").datagrid('reload');
			}
		});
	}
	function findPublish(rId){
		$.ajax({
			type:"POST",
			url:path+"/admin/dictionary/findDictionaryById.action",
			async:false,
			data:"bsDictionary.id="+rId,
			success:function(msg){
				$("#eDicCode").val(msg.bsDictionary.dicCode);
				$("#eDicName").val(msg.bsDictionary.name);
				$("#dicId").val(msg.bsDictionary.id);
				$('#edit').dialog('open');
			}
		});
	}
	
	function editPublish(){
		var flag = true ;
		flag = flag && $("#eDicCode").validatebox("isValid");
		flag = flag && $("#eDicName").validatebox("isValid");
		if(!flag){
			$.messager.alert("提示","请输入完整信息!");
			return;
		}
		$.ajax({
			type:"POST",
			url:path+"/admin/dictionary/updateDictionary.action",
			async:false,
			data:"bsDictionary.dicCode="+$("#eDicCode").val()+"&bsDictionary.type=3&bsDictionary.name="+$("#eDicName").val() +"&bsDictionary.id=" + $("#dicId").val(),
			success:function(){
				$('#edit').dialog('close');
				$("#publishingList").datagrid('reload');
			}
		});
	}
	
	function deletePublish(pId){
		$.messager.confirm('确认','确认删除该记录?',function(r){
			if(r){
				$.ajax({
					type:"POST",
					url:path+"/admin/dictionary/deleteDictionary.action",
					async:false,
					data:"bsDictionary.id=" + pId,
					success:function(){
						$("#publishingList").datagrid('reload');
					}
				});
			}
		});
	}