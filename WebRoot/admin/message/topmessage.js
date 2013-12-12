function saveSetting() {
	if($("#tbody tr").length <= 0){
		$.messager.alert("系统提示","至少添加一张图片",'info');
		return ;
	}
	
	$.messager.confirm("系统提示","请确认是否要保存设置？",function(f){
		if(f){
			var s=jQuery("input[name='topmessage']").map(function(){
				return jQuery(this).val();
			}).get();
			var img = jQuery("input[name='image']").map(function(){
				return jQuery(this).val();
			}).get();
			for(var i=0;i<s.length;i++){
				if(i>img.length || img[i] == ""){
					if(s[i] == "") {
						$.messager.alert("系统提示","图片不能为空",'info');
						return false ;
					}
				}else{
					var type = s[i].substring(s[i].lastIndexOf(".")) ;
					if(type == ".jpg" || type == ".JPG" || type == ".JPEG" || type == ".PNG" || type == ".jpeg" || type == ".png") {				
					} else {
						$.messager.alert("系统提示","第" + (i+1) + "行 " + "图片格式只能为jpg或jpeg或png");
						return false;
					}
				}
			}
			var describe=jQuery("input[name='topmessageTitle']").map(function(){
				return jQuery(this).val();
			}).get();
			for(var i=0;i<describe.length;i++){
				if(describe[i] == "") {
					$.messager.alert("系统提示","第" + (i+1) + "行 " + "标题不能为空");
					jQuery("input[name='topmessageTitle']")[i].val("");
					return false ;
				}
			}
			var address=jQuery("input[name='topmessageHref']").map(function(){
				return jQuery(this).val();
			}).get();
			for(var i=0;i<address.length;i++){
				if(address[i] == "") {
					$.messager.alert("系统提示","第" + (i+1) + "行 " + "链接地址不能为空");
					jQuery("input[name='topmessageHref']")[i].val("");
					return false ;
				}else if(address[i].indexOf("http://")!=0){
					$.messager.alert("系统提示","第" + (i+1) + "行 " + "链接地址应以 http://开头");
					jQuery("input[name='topmessageHref']")[i].val("");
					return false ;
				}
			}
			$('#theForm').submit();
		}
	});
}
function delthis(o,key){
	if(confirm("确认删除？")){
		if(key){
			$.post(path+'/delTopMessage.action',{"key":key},function(data){
				if(data && data.flag=="1"){
					alert("删除成功！");
				}else if(data && data.flag=="-1"){
					alert(data.msg);
				}
			},'json');
		}
		$(o).parents("tr").remove();
	}
}
function addone(){
	var _tr=$("<tr></tr>");
	var str="<td><input type='file' name='topmessage' /></td>";
		str+="<td><input name='topmessageTitle'/></td>";
		str+="<td><input name='topmessageHref'/></td>";
		str+="<td>";
		str+="<a href='javascript:void(0);' onclick='delthis(this);' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-cancel' style='padding-left: 20px; '>删除</span></span><a/></td>";
		_tr.append(str);
	$("#tbody").append(_tr);
}