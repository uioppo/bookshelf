var mark = false;
$(function(){
	$("#advice").dialog({
		closed:true,
		modal:true,
		width:610,
		height:360,
		buttons:[{
			text:'提交到待审核',
			iconCls:'icon-ok',
			handler:function(){
				addAttribute();
			}
		},{
			text:'取消',
			handler:function(){
				$('#advice').dialog('close');
			}
		}]
	});
});

function loadNEBookList(status){
	$('#nEBookList').datagrid({
		loadMsg:'数据加载中请稍后……', 
		width:'100%',
		height:$(document).height(),
		nowrap: true,
		striped: true,
		singleSelect:true,
		collapsible:false,
		url:path+'/admin/ebook/findEBookList.action?bookStatus='+status,
		remoteSort: false,
		idField:'id',
		columns:[[
			{title:'操作',field:'cz',width:200,align:'center',formatter:function(value,row,index){
				var str = "<a href='javascript:void(0);' onclick='audit("+row.id+",\""+row.bookName+"\"," + row.pageNumber + ",\"" + row.author + "\",\"" + row.teachVersion + "\",\"" + row.eduVersion + "\",\"" + row.language + "\"," + row.productType + "," + row.edition + ",\"" + row.applyObject + "\",\"" + row.volume + "\"," + row.gradeCode + ",\"" + row.grade + "\",\"" + row.subject + "\"," + row.schoolStage+")' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-edit' style='padding-left: 20px; '>编辑属性</span></span><a/>";
				return str;
			}},
            {title:'名称',field:'bookName',width:180,sortable:true,align:'center'},
            {title:'电子书类型',field:'bookType_name',width:150,align:'center'},
            {title:'大小(M)',field:'bookSize',width:100,align:'center'},
	        {title:'版本号',field:'version_',width:100,align:'center'},
	        {title:'操作时间',field:'operatorTime_',width:150,align:'center'},
	        {title:'创建时间',field:'createTime_',width:200,align:'center'},
	        {title:'创建人',field:'operator',width:130,align:'center'},
	        {title:'code',field:'bookCode',width:150}
		]],
		pagination:true,
		rownumbers:true,
		toolbar:"#nEbooklist_bar"
	});
}

function searcher(value,name){
	$('#nEBookList').datagrid("load",{
		searchValue:value,
		searchName:name
	});
}


function audit(bookId,bookName,pageNumber,author,teachVersion,eduVersion,language,productType,edition,applyObject,volume,gradeCode,grade,subject,schoolStage){
	$("#language").val("");
	$("#publishing").val("");
	$("#teachVersion").val("");
	$("#author").val("");
	$("#pageNumber").numberbox('setValue',0);
	if(pageNumber) {
		$('#pageNumber').numberbox('setValue',pageNumber);
	}
	if(author != "null" && author != ''){
		$("#author").val(author);
	}
	if(teachVersion != "null" && teachVersion != ''){
		$("#teachVersion").val(teachVersion);
	}
	if(language != "null" && language != ''){
		$("#language").val(language);
	}
	if(productType != null){
		$("#productType").combobox('setValue',productType);
	}
	if(edition != null && edition != '' && edition != "null"){
		$("#version").combobox('setValue',edition);
	}
	if(applyObject != null && applyObject != "" && applyObject != "null"){
		$("#applyObject").combobox('setValue',applyObject);
	}
	if(eduVersion != "null" && eduVersion != '' && eduVersion != null){
		$("#publishing").combobox('setValue',eduVersion);
	}
	if(volume != "null" && volume != ''){
		$("#volume").combobox('setValue',volume);
	}
	if(gradeCode != "null" && gradeCode != '' && gradeCode != null){
		$("#gradeCode").combobox('setValue',gradeCode);
		//$("#gCode").val(gradeCode);
	}
	if(subject != "null" && subject != '' && subject != null){
		$("#subject").combobox('setValue',subject);
	}
	if(schoolStage != null && schoolStage != ""){
		$("#schoolStage").combobox('setValue',schoolStage);
	}
	
	$("#eBookName").text("");
	$("#eBookName").text(bookName);
	$("#bookIdHidden").val("");
	$("#bookIdHidden").val(bookId);
	
	$('#advice').dialog("open");
}

function mid(){
	mark = true ;
	var schoolStage = $("#schoolStage").combobox('getValue') ;
	loadGradeSub(schoolStage);
}

function loadGradeSub(schoolStage) {
	$.ajax({
		type:"post",
		url: path + '/admin/dictionary/loadGrade.action',
		data:"schoolStage=" + schoolStage +"&dd=" + Math.random(),
		success:function(json) {
			$("#subject").combobox({
					data:json.subjectList,
					valueField:'name',
					textField:'name'
			});
			if(json.subjectList != null && json.subjectList.length > 0){
				$('#subject').combobox('setValue',json.subjectList[0].name);
			}
			$("#gradeCode").combobox({
				data:json.gradeList,
				valueField:'dicCode',
				textField:'name'
			});
			if(json.subjectList != null && json.subjectList.length > 0){
//				if(mark){
//					$('#gradeCode').combobox('setValue',json.gradeList[0].name);
//				}else {
					$('#gradeCode').combobox('setValue',json.gradeList[0].dicCode);
//				}
			}
		}
	});
}

function addAttribute() {
	var language = $("#language").val();
	
	if(language == null || language == ''){
		$.messager.alert('提示','语种不能为空！','info');
		return ;
	}
	var publishing = $("#publishing").combobox('getValue');
	var teachVersion = $("#teachVersion").val();
	if(teachVersion == null || teachVersion == ''){
		$.messager.alert('提示','对应教材版本不能为空！','info');
		return ;
	}
	var author = $("#author").val();
	if(author == null || author == ''){
		$.messager.alert('提示','开发者不能为空！','info');
		return ;
	}
	var pageNumber = $("#pageNumber").numberbox('getValue');
	if(pageNumber == null || pageNumber == ''){
		$.messager.alert('提示','页数不能为空！','info');
		return ;
	}
	var gradeCode = $("#gradeCode").combobox('getValue');
	var grade = $("#gradeCode").combobox('getText');
//	if(!mark){
//		grade = gradeCode;
//		gradeCode = $("#gCode").val();
//		
//	}
	var para = {"bsEbook.schoolStage":$("#schoolStage").combobox('getValue'),
			 "bsEbook.gradeCode":gradeCode,
			 "bsEbook.grade":grade,
			 "bsEbook.subject":$("#subject").combobox('getValue'),
			 "bsEbook.volume":$("#volume").combobox('getValue'),
			 "bsEbook.applyObject":$("#applyObject").combobox('getValue'), 
			 "bsEbook.edition":$("#version").combobox('getValue'),
			 "bsEbook.productType":$("#productType").combobox('getValue'),
			 "bsEbook.language":language ,
			 "bsEbook.publishing":$("#publishing").combobox('getText'),
			 "bsEbook.eduVersion":publishing,
			 "bsEbook.teachVersion":teachVersion,
			 "bsEbook.author":author,
			 "bsEbook.pageNumber":pageNumber,
			 "bsEbook.id":$("#bookIdHidden").val(),
			 "dd":Math.random()};
	$.ajax({
		type:"post",
		url:path+"/admin/ebook/editEbookAttr.action",
		data:para,
		success:function(json) {
			if(json == false){
				$.messager.alert("异常","请稍后操作!");
			}
			$('#advice').dialog('close');
			$('#nEBookList').datagrid("reload");
		}
	});
}

function showBook(){
	var bookId = $("#bookIdHidden").val();
	alert(bookId);
}
