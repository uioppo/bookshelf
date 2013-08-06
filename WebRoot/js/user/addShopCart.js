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
			return;
		}
	}
	var str=proId+"_"+1;
	a.push(str);
	$.cookie("the_bs_product", a.join(","), { expires: 7, path:'/'});
	topShopCart();
}