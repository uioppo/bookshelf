$(function(){
	topShopCart();
	$(".top_style02").click(function(){
		linkShopCart();
	});
});
function topShopCart(){
	var v = $.cookie("the_bs_product");
	if(v){
		var a = v.split(",");
		if(a){
			$(".top_style02").text("购物车（"+a.length+"）件");
		}else{
			$(".top_style02").text("购物车（0）件");
		}
	}else{
		$(".top_style02").text("购物车（0）件");
	}
}
function linkShopCart(){
	var shopCartPath_=(typeof(path)!='undefined'?(path+"/"):"")+"shopCart/shopCart.jsp";
	var thisLink=window.location.href;
	window.location.href=shopCartPath_+"?linkpath="+thisLink;
}