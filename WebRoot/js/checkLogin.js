//变量 path = <%=path%>

$(function (){
	$.ajax({
		type:"post",
		url: (typeof(path)!='undefined'?(path+"/"):"") + 'checkLogin.action',
		success:function(json) {
			if(json != null && json != '') {
				$("._top_tip").html(json + "&nbsp;<span class='top_style01'>您好，欢迎来人教云汉商城！</span><a href='javascript:void(0)' onclick='loginOut()'>退出</a>");
			} else {
				$("._top_tip").html("游客&nbsp;<span class='top_style01'>您好，欢迎来人教云汉商城！</span><a href='javascript:void(0)' onclick='locateLogin();return false;'>登录</a>|<a href='javascript:void(0)' onclick='locateReg()'>注册</a>");
			}
		}
		
	});
	
});

function locateLogin() {
	window.location.href = (typeof(path)!='undefined'?(path+"/"):"") + "login.jsp?url="+window.location.href+"&no=back";
}

function locateReg(){
	window.location.href = (typeof(path)!='undefined'?(path+"/"):"") + "register/register.jsp#reg";
}

function loginOut(){
	
	$.ajax({
		type:"post",
		url:(typeof(path)!='undefined'?(path+"/"):"") + 'loginOut.action',
		success:function(json){
			window.location.href = (typeof(path)!='undefined'?(path+"/"):"") + "index.html";
		}
	});
}
