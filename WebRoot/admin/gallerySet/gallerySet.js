$(function(){
	$("#newbookdiv").load(path+'/hot/aa.html',function(){
		$("#inputHidden1").val($("#newRecContent_1").html());
		$("#inputHidden2").val($("#newRecContent_0").html());
	});
});
function saveSetting() {
	if($("#tbody tr").length <= 0){
		$.messager.alert("系统提示","至少添加一张图片",'info');
		return ;
	}
	
	$.messager.confirm("系统提示","请确认是否要保存设置？",function(f){
		if(f){
			var s=jQuery("input[name='gallery']").map(function(){
				return jQuery(this).val();
			}).get();
			for(var i=0;i<s.length;i++){
				if(s[i] == "") {
					$.messager.alert("系统提示","图片不能为空",'info');
					return false ;
				}
				var type = s[i].substring(s[i].lastIndexOf(".")) ;
				if(type == ".jpg" || type == ".JPG" || type == ".JPEG" || type == ".PNG" || type == ".jpeg" || type == ".png") {				
				} else {
					$.messager.alert("系统提示","第" + (i+1) + "行 " + "图片格式只能为jpg或jpeg或png");
					return false;
				}
			}
			var describe=jQuery("input[name='galleryTitle']").map(function(){
				return jQuery(this).val();
			}).get();
			for(var i=0;i<describe.length;i++){
				if(describe[i] == "") {
					$.messager.alert("系统提示","第" + (i+1) + "行 " + "标题不能为空");
					jQuery("input[name='galleryTitle']")[i].val("");
					return false ;
				}
			}
			var address=jQuery("input[name='galleryHref']").map(function(){
				return jQuery(this).val();
			}).get();
			for(var i=0;i<address.length;i++){
				if(address[i] == "") {
					$.messager.alert("系统提示","第" + (i+1) + "行 " + "链接地址不能为空");
					jQuery("input[name='galleryHref']")[i].val("");
					return false ;
				}else if(address[i].indexOf("http://")!=0){
					$.messager.alert("系统提示","第" + (i+1) + "行 " + "链接地址应以 http://开头");
					jQuery("input[name='galleryHref']")[i].val("");
					return false ;
				}
			}
			$('#theForm').submit();
		}
	});
}
function delthis(o){
	$(o).parents("tr").remove();
}
function addone(){
	var _tr=$("<tr></tr>");
	var str="<td><input type='file' name='gallery' /></td>";
		str+="<td><input name='galleryTitle'/></td>";
		str+="<td><input name='galleryHref'/></td>";
		str+="<td>";
		str+="<a href='javascript:void(0);' onclick='delthis(this);' class='easyui-linkbutton l-btn l-btn-plain'><span class='l-btn-left'><span class='l-btn-text icon-cancel' style='padding-left: 20px; '>删除</span></span><a/></td>";
		_tr.append(str);
	$("#tbody").append(_tr);
}