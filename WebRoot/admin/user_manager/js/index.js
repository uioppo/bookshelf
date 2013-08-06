function loadFunctionByRoleId(){
	jQuery.ajax({
		type:"post",
		url:path+"/admin/user/findFunctionByRoleId.action",
		dataType:"json",
		cache:false,
		success:function(json){
			var _p = ""; 
			var sub;
			var fids = [];
			jQuery(json.rows).each(function(i){
				if(_p!=this.parentNode){
					sub=$("<li class='subMenu'><a href='javascript:void(0);' class='a_op_ul' >"+this.parentNode+"</a><ul style=\"display: none;\"></ul></li>");
				}
				if (jQuery.inArray(this.shortcut,fids)==-1) {
					sub.find("ul").append("<li><a href=\""+this.functionUrl+"\" class='fun_link' target='right'>"+this.functionName+"</a></li>");
					fids.push(this.functionId);
				}
				if(_p!=this.parentNode){
					$("#menu_fun").append(sub);
					_p=this.parentNode;
				}
			});
			sub=$("<li class='subMenu'><a href='javascript:void(0);' class='a_op_ul' >账户信息</a><ul style=\"display: none;\"><li><a href=\"user_manager/editUser.jsp\" class='fun_link' target='right'>账户信息</a></li></ul></li>");
			$("#menu_fun").append(sub);
			loadmenu();
		}
	});
}

function selectdangqian(obj){
	$('.fun_link').parent().css('background-image',"url('images/index-submenu-bg.gif')");
	$(this).parent().css('background-image',"url('images/index-submenu-bg-1.gif')");
}

function loadmenu(){
	//menu on and off
	$('.btn').click(function(){
		$('.menu').toggle();
		
		if($(".menu").is(":hidden")){
			$('.page iframe').width($(window).width()-15+5);
			}else{
			$('.page iframe').width($(window).width()-15-168);
			}
		});
		
	//
	$('.subMenu .a_op_ul').click(function(){
		$(this).next('ul').toggle();
		return false;
	});
	
	$('.fun_link').click(selectdangqian);
//	
}
