var shopCartPath=(typeof(path)!='undefined'?(path+"/"):"")+"shopCart/shopCart.jsp";
var proDetailPath=(typeof(path)!='undefined'?(path+"/"):"")+"web/findProductsById.action?id=";
$(function(){
	var _gouwuche_f = $("<div id='float_shopping_cart' style='position:fixed;bottom:10px;right:0px;'></div>");
	$("body").append(_gouwuche_f);
	init();
});
function init(){
	$("#float_shopping_cart").html("");
	var _top_link = $("<div id='float_shopping_con'><ul id='menumore01' style='display:none;'><li class='go_shoppingcart'><a href='javascript:linkShopCart();'><img src='"+(typeof(path)!='undefined'?(path+"/"):"")+"images/float_button01.gif' /></a></li><li><div class='float_shoppingcart_tit'></div><ul id='float_list'></ul></li><li class='go_shoppingcart'><a href='javascript:linkShopCart();'><img src='"+(typeof(path)!='undefined'?(path+"/"):"")+"images/float_button02.gif' /></a></li></ul></div><div id='cart_banners'><a href='javascript:void(0)' onclick='showmore01()'>购物车（<span id ='_gouwu_count'></span>）<a href='javascript:linkShopCart();' style='margin-left: 80px;'>去购物车结算&gt;&gt;<a></a></div>");
	$("#float_shopping_cart").append(_top_link);
	loadStr();
}
function loadStr(){
	var _gouwuce_list = $("#float_list");
	var __gouwu_count = $("#_gouwu_count");
	if(!_gouwuce_list)
		return;
	var v = $.cookie("the_bs_product");
	if(v){
		var a = v.split(",");
		if(a){
			jQuery.ajax({
				type:"post",
				url:(typeof(path)!='undefined'?(path+"/"):"")+"findFloatCartProducts.action",
				data:{"math":Math.random()},
				dataType:"json",
				success:function(data){
					var o = data.cartProductList;
					if(o==null||o.length<=0){
						$("#float_shopping_cart").html("");
						topShopCart();
						return;
					}
					_gouwuce_list.html("");
							var _list_li=$("<li id='liid'></li>");
							jQuery(o).each(function(i){
								var li2="";
								li2+="<dl><dt>";
								li2+="<a href='";
								li2+=proDetailPath+this.productId;
								li2+="'><img src='"+this.thumbnail_1+"' title='"+this.productName+"' width='36px' height='50px' /><span>";
								if(this.productName.length>7){
									li2+=this.productName.substring(0,7)+"...";
								}else{
									li2+=this.productName;
								}
								li2+="</span></a></dt><dd>";
								li2+="<a href='javascript:void(0)' onclick='delOne(";
								li2+=this.productId;
								li2+=")'>";
								li2+="<img src='"+(typeof(path)!='undefined'?(path+"/"):"")+"images/float_delete.gif' /></a></dd>";
								li2+="<dd class='float_price'>";
								
								if(this.vipPrice_ != null && this.vipPrice_ != "" &&this.vipPrice_ != "0.00"){
									li2+=this.vipPrice_;
								}else if(this.downPrice_ != null && this.downPrice_ != ""&&this.downPrice_ != "0.00"){
									li2+=this.webDownPrice_;
								}else if(this.price_ != null && this.price_!= ""&&this.price_ != "0.00"){
									li2+=this.price_;
								}else{
									li2+="0.00";
								}
								li2+="</dd></dl>";
								_list_li.append(li2);
							});
							_gouwuce_list.append(_list_li);
							var v1 = $.cookie("the_bs_product");
							if(v1){
								var a1 = v1.split(",");
								__gouwu_count.html("");
								__gouwu_count.append(a1.length);
								topShopCart();
							}
				} });
		}
	}else{
		$("#float_shopping_cart").html("");
	}
}
function delOne(p){
	var v = $.cookie("the_bs_product");
	var a = [];
	if(v!=null && v!=undefined && ""!=v){
		a = v.split(",");
		var b=[];
		for(var i=0;i<a.length;i++){
			var id_count_arr=a[i].split("_");
			b.push(id_count_arr[0]);
		}
		var t = jQuery.inArray(p+"",b);
		if(t>=0){
			a.splice(t,1);
			$.cookie("the_bs_product", a.join(","), { expires: 7, path:'/'});
			loadStr();
			topShopCart();
		}
	}
}

function showmore01()
{
	loadStr();
	topShopCart();
	var obj=document.getElementById("menumore01");
	if(obj.style.display == "block")
	{
	obj.style.display="none";
	}else
	{
	obj.style.display="block";
	}
}
function tocart(proId){
	//加入购物车   proId  商品ID
	var v = $.cookie("the_bs_product");
	var a = [];
	if(v){
		a = v.split(",");
		var idArr=[];
		var countArr=[];
		var idArrStr="";
		for(var i=0;i<a.length;i++){
			var id_count=a[i].split("_");
			idArr.push(id_count[0]);
			idArrStr+=id_count[0]+",";
			countArr.push(id_count[1]);
		}
		if($.inArray(proId+"",idArr)>=0){
			var id_index_=$.inArray(proId+"",idArr);
			var id_count_arr=a[id_index_].split("_");
			var proCount=id_count_arr[1];
			var id_count_str_=proId+"_"+(parseInt(proCount)+1);
			a.splice(id_index_,1);
			a.push(id_count_str_);
			$.cookie("the_bs_product", a.join(","), { expires: 7, path:'/'});
			loadStr();
			return;
		}
	}
	var str=proId+"_"+1;
	a.push(str);
	$.cookie("the_bs_product", a.join(","), { expires: 7, path:'/'});
	if(a.length==1){
		init();
	}else{
		loadStr();
	}
	topShopCart();
}