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
					   $("#validCodeSpan").text("验证码输入错误");
					   $("#validCodeDiv").show();
					   $("#kaptchaImage").attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
					   flag=false;
				   }else{
					   $("#validCodeSpan").text("");
					   $("#validCodeDiv").hide();
					   flag=true;
				   }
			   }
			});
	}else{
		$("#validCodeSpan").text("请输入验证码");
		$("#validCodeDiv").show();
		flag=false;
	}
	return flag;
}
function validateUserName(){
	var flag=false;
	var userName=$("#userName").val();
	if(userName!=""&&$.trim(userName)!=""){
		var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if(!reg.test(userName)){
			$("#userNameSpan").text("请输入正确邮箱");
			$("#userNameDiv").show();
			flag=false;
		}else{
		$.ajax({
			   type: "POST",
			   url: path+"/register/registerAction_validateUserName.action",
			   data: "bsWebUser.wuEmail="+userName+"&math="+Math.random(),
			   async: false,
			   success: function(msg){
				   if(msg.flag==1){
					   $("#userNameSpan").text("用户名已存在");
					   $("#userNameDiv").show();
					   flag=false;
				   }else{
					   $("#userNameSpan").text("");
					   $("#userNameDiv").hide();
					   flag=true;
				   }
			   }
			});
		}
	}else{
		$("#userNameSpan").text("请输入邮箱");
		$("#userNameDiv").show();
		flag=false;
	}
	return flag;
}
function validatePassword(){
	var password=$("#password").val();
	if(password!=""||$.trim(password)!=""){
		var reg=/^(\w){6,20}$/;
		if(!reg.test(password)){
			$("#passwordSpan").text("格式或者长度错误");
			$("#passwordDiv").show();
			return false;
		}else{
			$("#passwordSpan").text("");
			$("#passwordDiv").hide();
			return true;
		}
	}else{
		$("#passwordSpan").text("请输入密码");
		$("#passwordDiv").show();
		return false;
	}
	
}
function validatePasswordRepeat(){
	var password=$("#password").val();
	var passwordRepeat=$("#passwordRepeat").val();
	if(passwordRepeat!=""||$.trim(passwordRepeat)!=""){
		if(password==passwordRepeat){
			$("#passwordRepeatSpan").text("");
			$("#passwordRepeatDiv").hide();
			return true;
		}else{
			$("#passwordRepeatSpan").text("两次密码不一致");
			$("#passwordRepeatDiv").show();
			return false;
		}
	}else{
		$("#passwordRepeatSpan").text("请再次输入密码");
		$("#passwordRepeatDiv").show();
		return false;
	}
}
function registerSubmit(){
	$('#submitButton').hide();
	$('#pass_dd').show();
	var flag=true;
	flag=flag&&($("#fig").attr('checked')=='checked');
	if(!flag){
		$('#submitButton').show();
		$('#pass_dd').hide();
		return ;
	}
	flag=flag&&validateUserName();
	flag=flag&&validatePassword();
	flag=flag&&validatePasswordRepeat();
	flag=flag&&validCode();
	if(flag){
		$("#registerForm").submit();
	}else{
		 $('#kaptchaImage').attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
		$('#submitButton').show();
		$('#pass_dd').hide();
	}
}