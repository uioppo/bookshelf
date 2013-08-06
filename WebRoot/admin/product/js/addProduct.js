var catalog ;		//目录
var description;	//内容简介
var imgPath ;		//缩略图路径
var zipPath ;		//试读片段路径
var bookName ;		//电子书名称
var bookCode ;		//电子书编码
var subject ;		//学科
var schoolStage ;	//学段代码
var period;			//学段
var grade ;			//年级
var applyObject ;	//使用对象
var volume ;		//册
var productType ;   //商品类型代码
var proType ;		//商品类型
var publishing ;   //出版社
var edition ; 	   //版本
var pageNO;		   //页数
var author;		   //开发者
var language ;	   //语种
var bookSize ;	   //电子书大小
var teachVersion ; //对应教材版本
var gradeCode ;    //年级代码
$(function (){	
	jQuery("#choiceBook").dialog({
		closed:true,
		modal:true,
		title:'选择电子书',
		width:750,
		height:520,
		onBeforeClose:function(){
			$(".menu_iframe").hide();
		},
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				$('#bookName').html(bookName);
				$('#bookCode').val(bookCode);
				$('#subject').val(subject);
				$('#period').val(period);
				$('#grade').val(grade);
				$('#applyObject').val(applyObject);
				$('#volume').val(volume);
				$('#proType').val(proType);
				$('#publishing').val(publishing);
				$('#edition').val(edition);
				$('#pageNumber').val(pageNO);
				$('#author').val(author);
				$('#language').val(language);
				$('#schoolStage').val(parseInt(schoolStage));
				$('#productType').val(parseInt(productType));
				$('#bookSize').val(parseInt(bookSize));
				$('#teachVersion').val(teachVersion);
				$('#gradeCode').val(gradeCode);
				$('#choiceBook').dialog('close');
			}
		},{
			text:'取消',
			handler:function(){
				$('#choiceBook').dialog('close');
			}
		}]
	});
	loadYEBookList();
	createRichEditor();
});

function createRichEditor() {
	$('body').append("<div></div>");
	var options = {
			items : [
						 'plainpaste','wordpaste', '|','undo', 'redo', '|', 'justifycenter','justifyleft', 'justifyright','justifyfull','|', 'link', 'unlink','anchor',  '|','image','multiimage','flash', 'hr',
						 'bold','italic', 'underline','strikethrough','subscript','superscript', '|','clearhtml','|','fontname','fontsize', '|', 'forecolor', 'hilitecolor','|','fullscreen','preview'
					],
			themeType:'example1',
			height:'500px',
			uploadJson :'../../jsp/upload_json.jsp',
			fileManagerJson : '../../jsp/file_manager_json.jsp',
		    allowFileManager : true
		};
		KindEditor.ready(function(K){
			description = K.create("#description",options);
		});
		KindEditor.ready(function(K){
			catalog = K.create("#catalog",options);
		});
}

function saveProduct() {
	var flag = true ;
	flag = flag && $("#productName").validatebox("isValid");
	flag = flag && $("#price").validatebox("isValid");
	var bookCode = $("#bookCode").val();
	var publishTime = $('#publishTime').datebox('getValue');
	if(publishTime == '' || publishTime == null){
		$.messager.alert('提示','请选择发布时间');
		 return ;
	}
	if(bookCode == '' || bookCode == null) {
		$.messager.alert('提示','请选择电子书');
		 return ;
	}
	if(imgPath == null || imgPath == ''){
		$.messager.alert('提示','请上传缩略图');
		return ;
	}else {
		$("#thumbnail").val(imgPath);
	}
//	if(zipPath == null || zipPath == ''){
//		$.messager.alert('提示','请上传试读片段');
//		return ;
//	}else {
//		$("#tryUrl").val(zipPath);
//	}
	description.sync();
	catalog.sync();
	if(description.html() == ""){
		$.messager.alert('提示','内容简介不能为空');
		return ;
	}
	if(catalog.html() == ""){
		$.messager.alert('提示','目录不能为空');
		return ;
	}
	if(flag) {
		$("#ff").submit();
		$('#save').linkbutton({disabled:true});
	}else {
		$.messager.alert('提示','请输入完整信息!');
		return ;
	}
}

function onImgUploadFinish(path){
	imgPath = path ;
}

function onZipUploadFinish(path){
	zipPath = path ;
	$.messager.alert('提示','上传成功!','info');
}

function loadYEBookList(){
	$('#yEBookList').datagrid({
		loadMsg:'数据加载中请稍后……', 
		width:'100%',
		height:450,
		nowrap: true,
		striped: true,
		singleSelect:true,
		collapsible:false,
		url:'../ebook/findUpdateEBookList.action',
		remoteSort: false,
		idField:'id',
		columns:[[
            {title:'名称',field:'bookName',width:200,align:'center'},
            {title:'科目',field:'subject',width:80,align:'center'},
            {title:'大小(M)',field:'bookSize',width:80,align:'center'},
	        {title:'版本',field:'version_',width:100,align:'center'},
	        {title:'创建时间',field:'createTime_',width:200,align:'center'},
	        {title:'创建人',field:'operator',width:130,align:'center'}
//	        {title:'操作',field:'cz',width:267,align:'center',formatter:function(value,row,index){
//	        	var str = "<a href='javascript:void(0);' onclick='showBook("+row.id+")'>查看<a/>";
//	        	str += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
//	        	return str;
//	        }}
		]],
		onClickRow:function(rowIndex, rowData){
			bookName = rowData.bookName ;
			bookCode = rowData.bookCode ;
			subject = rowData.subject ;
			schoolStage = rowData.schoolStage;
			if(rowData.schoolStage == 1){
				period = '小学';
			}else if(rowData.schoolStage == 2){
				period = '初中';
			}else{
				period = '高中';
			}
			 
			grade = rowData.grade;
			applyObject = rowData.applyObject ;
			volume = rowData.volume ;
			productType = rowData.productType ;
			if(rowData.productType == 1){
				proType = '数字教材';
			}else{
				proType = '数字教辅';
			}
			
			publishing = rowData.publishing;
			edition =  rowData.edition ;  
			pageNO = rowData.pageNumber ;
			author = rowData.author ;
			language = rowData.language;
			bookSize = rowData.bookSize;
			teachVersion = rowData.teachVersion;
			gradeCode = rowData.gradeCode;
		},
		pagination:true,
		rownumbers:true,
		toolbar:"#yEbooklist_bar"
	});
}

function searcher(value,name){
	$('#yEBookList').datagrid("load",{
		searchValue:value,
		searchName:name
	});
}

function choice() {
	$(".menu_iframe").show();
	jQuery('#choiceBook').dialog('open');
}
