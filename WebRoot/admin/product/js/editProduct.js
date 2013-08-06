var catalog ;
var description;
var change = "0";
function createRichEditor() {
	$('body').append("<div></div>");
	var options = {
			items : [
						 'plainpaste','wordpaste', '|','undo', 'redo', '|', 'justifycenter','justifyleft', 'justifyright','justifyfull','|', 'link', 'unlink','anchor',  '|','image','multiimage','flash', 'hr',
						 'bold','italic', 'underline','strikethrough','subscript','superscript', '|','clearhtml','|','fontname','fontsize', '|', 'forecolor', 'hilitecolor','|','fullscreen','preview',
					],
			themeType:'example1',
			height:'500px',
			uploadJson : path + '/jsp/upload_json.jsp',
			fileManagerJson : path + '/jsp/file_manager_json.jsp',
		    allowFileManager : true
		};
		KindEditor.ready(function(K){
			description = K.create("#description",options);
		});
		KindEditor.ready(function(K){
			catalog = K.create("#catalog",options);
		});
}

function editProduct() {
	var flag = true ;
	flag = flag && $("#productName").validatebox("isValid");
	flag = flag && $("#price").validatebox("isValid");
	var publishTime = $("#publishTime").datebox('getValue');
	if(publishTime==null || publishTime==""){
		$.messager.alert("提示","出版时间不能为空!");
		return;
	}
	var source = $("#thumbnail").val();
	if(source == null || source == ''){
		$.messager.alert('提示','请上传缩略图');
		return ;
	}
	if(change != 0){
		var tryUrl = $("#tryUrl").val();
		if(tryUrl == null || tryUrl == ''){
			$.messager.alert('提示','请上传试读片段');
			return ;
		}
	}
	
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
		$("#ff").attr("action",path + "/admin/product/updateProduct.action?change="+change);
		$("#ff").submit();
		$('#save').linkbutton({disabled:true});
	}else {
		$.messager.alert('提示','请输入完整信息!');
		return ;
	}
}

function onImgUploadFinish(path,o){
	$("#thumbnail").val(path);
}

function onZipUploadFinish(path,o){
	change = "1" ;
	$("#tryUrl").val(path);
	$.messager.alert('提示','上传成功!','info');
}

function editImg() {
	var html = '<applet width="375" height="88"  code="com.hcctech.bookshelf.applet.FileFtpApplet" archive="../ftp/FileFtpApplet.jar"> '
				+ '<param name="jnlp_href" value="../ftp/launch.jnlp"/>'
				+ '<param name="targetDir" value="/images/"/>'
				+ '<param name="callback" value="onImgUploadFinish"/>'
				+ '<param name="filetype" value="image">'
				+ '</applet>'
				+ '<font style="font-size:12px;">尺寸：110*152,图片类型：png、jpg、jpeg、gif</font>';
	$("#preview").html(html);
}
