function validateUserNameNull(){
	var userName=$("#userName").val();
	if(userName==null||$.trim(userName)==""){
		$("#userNameSpan").text("用户名(邮箱)不能为空");
	}else{
		$("#userNameSpan").text("");
		validateUserName();
	}
}
function validateUserName(){
	var flag=false;
	var userName=$("#userName").val();
	if(userName!=""&&$.trim(userName)!=""){
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if(!reg.test(userName)){
			$("#userNameSpan").text("请输入正确的用户名(邮箱)");
			flag=false;
		}else{
			flag=true;
		}
	}else{
		$("#userNameSpan").text("请输入邮箱");
		flag=false;
	}
	return flag;
}
function validCode(){
	var flag=false;
	var validCode=$("#validationCode").val();
	if(validCode!=""&&$.trim(validCode)!=""){
		$.ajax({
			   type: "POST",
			   url: path+"/register/registerAction_validCodeIsRight.action",
			   data: "validCode="+validCode+"&math="+Math.random(),
			   async: false,
			   success: function(msg){
				   if(msg.flag==0){
					   $("#userNameSpan").text("验证码输入错误");
					   $("#kaptchaImage").attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
					   flag=false;
				   }else{
					   $("#userNameSpan").text("");
					   flag=true;
				   }
			   }
			});
	}else{
		$("#userNameSpan").text("请输入验证码");
		flag=false;
	}
	return flag;
}
function validateUserName_2(){
	var flag = false;
	var userName=$("#userName").val();
	$.ajax({
		   type: "POST",
		   url: path+"/register/registerAction_validateUserName.action",
		   data: "bsWebUser.wuEmail="+userName+"&math="+Math.random(),
		   async: false,
		   success: function(msg){
			   if(msg.flag!=1){
				   $("#userNameSpan").text("用户名不存在");
				   flag=false;
			   }else{
				   $("#userNameSpan").text("");
				   flag=true;
			   }
		   }
		});
	return flag;
}
function lostPasswordSubmit(){
	$('#submitButton').hide();
	var flag=true;
	flag=flag&&validateUserName();
	if(!flag){
		$("#kaptchaImage").attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
		$('#submitButton').show();
		$('#submitButton').show();
		return;
	}
	flag=flag&&validCode();
	if(!flag){
		$('#submitButton').show();
		return;
	}
	flag=flag&&validateUserName_2();
	if(flag){
		$("#lostPasswordForm").submit();
	}else{
		$("#kaptchaImage").attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
		$('#submitButton').show();
	}
		
}