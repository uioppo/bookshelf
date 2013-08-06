<!--
function getQueryStringRegExp(name)
{
	try{
	var hr = decodeURIComponent(location.href);
	}catch(e){
		return "";
	}
    var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
    if (reg.test(hr)) return unescape(RegExp.$2.replace(/\+/g, " ")); return "";
};
function getCurrentDate(){
	var myDate = new Date();
	return myDate.getFullYear()+""+myDate.getMonth()+""+myDate.getDate();
}

//-->