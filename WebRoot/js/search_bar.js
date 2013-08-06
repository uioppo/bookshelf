$(document).ready(function(){
	var _keyword = getQueryStringRegExp('keyword');
	var duf_test = '小学 数学';
	$('#search_input').val(duf_test);
	if(_keyword&&_keyword!=''){
		$('#search_input').val(_keyword);
	}
	$('#search_input').focus(function(){
		var _keyw = $('#search_input').val();
		if(duf_test==_keyw){
			$('#search_input').val('');
			$('#search_input').css('color','#333333');
		}
	});
	$('#search_input').blur(function(){
		var _keyw = $('#search_input').val();
		if(''==_keyw){
			$('#search_input').val(duf_test);
			$('#search_input').css('color','#999999');
		}
	});
	$('#search_input').keydown(function(event){
		  if(event.keyCode==13)
			  search_word();
	});
});

function search_word(){
	var _keyw = $.trim($('#search_input').val());
	if(_keyw == ''){
		return;
	}
	 window.location.href=(typeof(path)!='undefined'?(path+"/"):"")+"search.action?keyword="+encodeURIComponent(_keyw)+"&order=price&sort=1&page=0&gao=ren"; 
}
