var a1=true;
var a2=true;
var a3=true;
var a4=true;
function showSchool(shengId){
	if(shengId==null||shengId==-1||shengId==""||shengId==0){
		shengId=$("#areaplus").val();
		if(shengId==null||shengId==-1||shengId==""||shengId==0){
			$("#school").empty();
			$("#school").append("<option value='-1'>--请选择--</option>");
			return;
		}
	}
	$("#school").empty();
	$("#school").append("<option value='-1'>--请选择--</option>");
	$.ajax( {
		type : "post",
		data : "shengId=" + shengId,
		url : path + "/user/showSchool.action",
		dataType : "json",
		async:false,
		success : function(json) {
		$(json.listSchool).each(function(i) {
				if(this.schoolName==school&&a1){
					a1=false;
					$("#school").append("<option selected='selected' value='" + this.schoolName + "'>" + this.schoolName + "</option>");
				}else{
					$("#school").append("<option value='" + this.schoolName + "'>" + this.schoolName + "</option>");
				}
			});
		}
	});
}

function showarea() {
	$.ajax( {
		type : "post",
		url : path + "/user/showArea.action",
		dataType : "json",
		async:false,
		success : function(json) {
			$(json.listArea).each(function(i) {
						if(this.areaId==sheng&&a2){
							a2=false;
							$("#area").append("<option selected='selected' value='" + this.areaId + "'>" + this.areaName + "</option>");
						}else{
							$("#area").append("<option value='" + this.areaId + "'>" + this.areaName + "</option>");
						}
				});
		}
	});
}
function showplus(areaid) {
	$("#areaplus").empty();
	$("#areaplus").append("<option value='-1'>--请选择--</option>");
	$.ajax( {
		type : "post",
		data : "areaid=" + areaid,
		url : path + "/user/showAreaPlus.action",
		dataType : "json",
		async:false,
		success : function(json) {
		$(json.listAreaplus).each(function(i) {
				if(this.areaId==shi&&a3){
					a3=false;
					$("#areaplus").append("<option selected='selected' value='" + this.areaId + "'>" + this.areaName + "</option>");
				}else{
					$("#areaplus").append("<option value='" + this.areaId + "'>" + this.areaName + "</option>");
				}
			});
		$("#county").empty();
		$("#county").append("<option value='-1'>--请选择--</option>");
		$("#school").empty();
		$("#school").append("<option value='-1'>--请选择--</option>");
		}
	
	});
}
function showcounty(areaid) {
	showSchool(areaid);
	$("#county").empty();
	$("#county").append("<option value='-1'>--请选择--</option>");
	$.ajax( {
		type : "post",
		data : "areaid=" + areaid,
		url : path + "/user/showAreaPlus.action",
		dataType : "json",
		async:false,
		success : function(json) {
			$(json.listAreaplus).each(function(i) {
				if(this.areaId==xian&&a4){
					a4=false;
					$("#county").append("<option selected='selected' value='" + this.areaId + "'>" + this.areaName + "</option>");
				}else{
					$("#county").append("<option value='" + this.areaId + "'>" + this.areaName + "</option>");
				}
			});
		}
	});
}
function checkmobile() {
	var flag = false;
	var mobile = $("#mobile").val();
	if (mobile == "") {
	$("#mobileMessage").text("");
		flag = true;
		}
	if (mobile != "" || $.trim(mobile) != "") {
		//var reg = /^0?1[358]\d{9}$/;
		//var reg = /(^0?1[358]\d{9})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/;
		var reg = /(^1[358]\d{9}$)|(^\d{7,8}$)|(^(\d{4}|\d{3})-(\d{7,8})$)|(^\+861[358]\d{9}$)/;
		if (!reg.test(mobile)) {
			$("#mobileMessage").text("请输入正确的联系方式").css("color", "red");
			flag = false;
		} else {
			$("#mobileMessage").text("");
			flag = true;
		}
	}
	return flag;
}
/*function checkAge(){
	var flag = false;
	var age = $("#age").val();
	if (age == "") {
		$("#ageMessage").text("");
		flag = true;
	}
	if (age != "" || $.trim(age) != "") {
		var reg = /^\d{1,2}$/;
		if (!reg.exec(age)) {
			$("#ageMessage").text("请输入正确的年龄").css("color", "red");
			flag = false;
		} else {
			$("#ageMessage").text("");
			flag = true;
		}
	}
	return flag;
}*/
function updateUser(){
	var flag = true;
	//flag =  flag && checkAge();
	flag = flag && checkmobile();
	if(flag){
		$("#myform").submit();
	}
	
}
function reset(){
	return;
}
function formatDate(v) {
		if (v instanceof Date) {
			var y = v.getFullYear();
			var m = v.getMonth() + 1;
			var d = v.getDate();
			var h = v.getHours();
			var i = v.getMinutes();
			var s = v.getSeconds();
			var ms = v.getMilliseconds();
			if (ms > 0)
				return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s
						+ '.' + ms;
			if (h > 0 || i > 0 || s > 0)
				return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
			return y + '-' + m + '-' + d;
		}
		return '';
	}
jQuery(function($){
    $.datepicker.regional['zh-CN'] = {
            closeText: '关闭',
            prevText: '&#x3c;上月',
            nextText: '下月&#x3e;',
            currentText: '今天',
            monthNames: ['一月','二月','三月','四月','五月','六月',
            '七月','八月','九月','十月','十一月','十二月'],
            monthNamesShort: ['一','二','三','四','五','六',
            '七','八','九','十','十一','十二'],
            dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
            dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
            dayNamesMin: ['日','一','二','三','四','五','六'],
            weekHeader: '周',
            dateFormat: 'yy-mm-dd',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: true,
            yearSuffix: '年'};
    $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
});

