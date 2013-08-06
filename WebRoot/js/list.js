var _xd = getQueryStringRegExp('xd');
var _xk = getQueryStringRegExp('xk');
var _cup = getQueryStringRegExp('page');
var _type = getQueryStringRegExp('type');
var _order = getQueryStringRegExp('order');
var _sort = getQueryStringRegExp('sort');
$(document).ready(function(){

	$("#Pagination").pagination(_total, {
		num_edge_entries: 1,
	    num_display_entries: 10,
	    current_page:_cup,
	    link_to:'ebooks.action?xd='+_xd+"&xk="+encodeURIComponent(_xk)+"&page=__id__&order="+_order+"&sort="+_sort+"&type="+_type+"&gao=ren",
	    callback: function callbackInitPagination(page_index, jq){
//
	    },
	    items_per_page:12,
	    prev_text:"<<",
	    next_text:">>",
	    ellipse_text:"..."
	});
	if(_order=="price"){
		if(_sort=='0'){
			$('#price_sort').attr('src','images/price_down.gif');
		}else{
			$('#price_sort').attr('src','images/price_up.gif');
		}
	}else if(_order=="time"){
		if(_sort=='0'){
			$('#time_sort').attr('src','images/time_down.gif');
		}else{
			$('#time_sort').attr('src','images/time_up.gif');
		}
	}
	//price_sort
	$('#price_sort').click(function () {
		if(_sort=='1'){
			window.location.href = 'ebooks.action?xd='+_xd+"&xk="+encodeURIComponent(_xk)+"&page=0&order=price&sort=0&type="+_type+"&gao=re1n";
		}else{
			window.location.href = 'ebooks.action?xd='+_xd+"&xk="+encodeURIComponent(_xk)+"&page=0&order=price&sort=1&type="+_type+"&gao=r2en";
		}
	});
	$('#time_sort').click(function () {
		if(_sort=='1'){
			window.location.href = 'ebooks.action?xd='+_xd+"&xk="+encodeURIComponent(_xk)+"&page=0&order=time&sort=0&type="+_type+"&gao=re3n";
		}else{
			window.location.href = 'ebooks.action?xd='+_xd+"&xk="+encodeURIComponent(_xk)+"&page=0&order=time&sort=1&type="+_type+"&gao=ren1";
		}
	});
	
	//
	var cu_id;
	
	if(_xd==1){
		switch (_xk) {
			case '语文':
				cu_id = 's1';
				break
			case '数学':
				cu_id = 's2';
				break
			case '英语':
				cu_id = 's3';
				break
			default:
				cu_id = null;
		}
	}else if(_xd==2){
		switch (_xk) {
		case '语文':
			cu_id = 'c1';
			break
		case '数学':
			cu_id = 'c2';
			break
		case '英语':
			cu_id = 'c3';
			break
		case '物理':
			cu_id = 'c4';
			break
		case '化学':
			cu_id = 'c5';
			break
		case '生物':
			cu_id = 'c6';
			break
		default:
			cu_id = null;
		}
	}
	var type_aus="t1";
	if(_type=='0')
		type_aus = "t0";
	if(cu_id){
		$('#'+type_aus+cu_id).css('background','url(images/menu_list05.gif) no-repeat 32px 12px #FFE6D5');
		$('#'+type_aus+cu_id+' a').css('background','url(images/menu_list07.gif) no-repeat 170px 8px');
	}
});

function ebookByType(gtype,gxd){
	if(gxd==null)
		gxd = '';
	window.location.href = 'ebooks.action?xd='+gxd+"&xk=&page=0&order=time&sort=1&type="+gtype+"&gao=re1n";
}