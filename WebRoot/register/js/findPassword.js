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
function validatePassword(){
	var password=$("#password").val();
	if(password!=""||$.trim(password)!=""){
		var reg=/^(\w){6,20}$/;
		if(!reg.test(password)){
			$("#validCodeSpan").text("密码由6到20位字母、数字、下划线组成");
			$("#passwordDiv").show();
			return false;
		}else{
			$("#validCodeSpan").text("");
			$("#passwordDiv").hide();
			return true;
		}
	}else{
		$("#validCodeSpan").text("请输入密码");
		$("#passwordDiv").show();
		return false;
	}
	
}
function validatePasswordRepeat(){
	var password=$("#password").val();
	var passwordRepeat=$("#passwordRepeat").val();
	if(passwordRepeat!=""||$.trim(passwordRepeat)!=""){
		if(password==passwordRepeat){
			$("#validCodeSpan").text("");
			$("#passwordRepeatDiv").hide();
			return true;
		}else{
			$("#validCodeSpan").text("两次密码不一致");
			$("#passwordRepeatDiv").show();
			return false;
		}
	}else{
		$("#validCodeSpan").text("请再次输入密码");
		$("#passwordRepeatDiv").show();
		return false;
	}
}
function findPasswordSubmit(){
	$("#submitButton").hide();
	var flag=true;
	flag=flag&&validatePassword();
	flag=flag&&validatePasswordRepeat();
	flag=flag&&validCode();
	if(flag){
		$("#findPasswordForm").submit();
	}else{
		$("#validCodeDiv").show();
		$("#kaptchaImage").attr('src', '../Kaptcha.jpg?' + Math.floor(Math.random()*100));
		$("#submitButton").show();
		
	}
}