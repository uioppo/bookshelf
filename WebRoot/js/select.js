//var TagsA ;
var flag = true;
//var flagA;
//var TagsCntA;
var _remmx= getCurrentDate();
$(function(){
	
	//TagsA=$("#new_rec_tit .rec01"); //标题
	//TagsCntA=$("#new_rec_con .new_rec_content"); //内容
	//var lenA=TagsA.length; 
	//flagA=0;
	//TagsA[flagA].className='rec02';
	//TagsCntA[flagA].className='dis';
	//TagsCntA[flagA].style.display='block';


	

	/*for(i=0;i<lenA;i++)
	{
	 TagsA[i].value = i;
	 TagsA[i].onclick=function(){changeNavA(this.value);}; 
	 TagsCntA[i].className='undis'; 
	}
	*/
	newBook_(1,"teach");
	newBook_(0,"assisant");
	filterHtmlTag("");
});

/*function changeNavA(vA)
{ 
	if(flag){
		content(vA);
	}
	
 TagsA[flagA].className='rec01';
 TagsCntA[flagA].className='undis';
 TagsCntA[flagA].style.display='none';
 flagA=vA; 
 TagsA[vA].className='rec02';
 TagsCntA[vA].className='dis';
 TagsCntA[vA].style.display='block';
}*/


/*function content(type){
	if(type == 0){
		type = 1 ;
	}else {
		type = 0 ;
		flag = false ;
	}
	
	$.ajax({
		type:"post",
		url:"recoNewBook.action",
		data:"productType=" + type + "&dd="+_remmx,
		success:function(data){
			var bsPro = data.bsProducts;
			var str = '';
			str += "<div  class='new_rec_main'>";
		  	str += "<div class='rec_main_pic'><a href='web/findProductsById.action?id="+bsPro.productId+" ' title='"+filterHtmlTag(bsPro.productName)+"' target='_blank'><img src='" + bsPro.thumbnail_1 + "' /></a></div>";
			str += "<div class='rec_main_summary'>";
			str += "<p><a href='web/findProductsById.action?id="+bsPro.productId+"' target='_blank'>" + filterHtmlTag(bsPro.productName) + "</a></p>";
		    str += "<p>开发：" + bsPro.author + "</p>";
		    str += "<p>定价：" + bsPro.price_ + "</p>";
			str += "<p class='summary_style01'>本书介绍：" ;
			var _dexp = filterHtmlTag(bsPro.description);
			if(_dexp.length > 37){
				str += _dexp.substring(0,37)+"……";
			}else {
				str += _dexp;
			}
			str += "</p>";
			str += "<p class='summary_style02'>¥<span class='summary_style03'>";
				if(bsPro.downPrice != null && bsPro.downPrice != ''){
					str += Math.round(bsPro.downPrice*bsPro.price*10)/100;
					str += "</span><span class='summary_style04'>" + bsPro.downPrice_ + "折</span></p>";
				}else if(bsPro.vipPrice != null && bsPro.vipPrice != ''){
					str += bsPro.vipPrice_;
					str += "</span><span class='summary_style04'><strike>" + bsPro.price_ + "</strike></span></p>";
				}else {
					str += bsPro.price_;
					str += "</span><span class='summary_style04'></span></p>";
				}
			str += "</div></div>";
			
			str += "<div class='new_rec_list'>";
			for(var i = 0 ; i < data.products.length ; i++) {
				var pro = data.products[i];
				var _titlex = filterHtmlTag(pro.productName);
				if(_titlex.length > 8){
					_titlex=_titlex.substring(0,8);
				}
				str += "<ul>";
				str += "<li class='rec_list_pic'><a href='web/findProductsById.action?id="+pro.productId+"' title='"+filterHtmlTag(pro.productName)+"' target='_blank'><img src='" + pro.thumbnail_1 + "' /></a></li>";
				str += "<li class='rec_list_title'><a href='web/findProductsById.action?id="+pro.productId+"' title='"+filterHtmlTag(pro.productName)+"' target='_blank'>" + _titlex + "</a></li>";
				str += "</ul>";
			}
			str += "</div>";
			$("#newRecContent" + type).html(str);
		}
	});
}
*/
function newBook_(type,id) {
	$.ajax({
		type:"post",
		url:"newBook.action",
		data:"productType=" + type + "&dd="+_remmx,
		success:function(data){
			$("#"+id).html("");
			var str = '';
			for(var i = 0 ; i < data.newBook.length ; i++) {
				var pro = data.newBook[i];
				var proName = String(pro.productName);
				var _ul = $("<ul></ul>");
				_ul.append('<li class="materials_pic"><a href="web/findProductsById.action?id='+pro.productId+'" title="'+filterHtmlTag(pro.productName)+'" target="_blank" ><img src="' + pro.thumbnail_1 + '" style="width: 109px;height: 151px;"/></a></li>');
				var _name = $("<li class='materials_name'></li>");
				_name.html("<a href='web/findProductsById.action?id="+pro.productId+"' title='"+filterHtmlTag(pro.productName)+"' target='_blank' >" + filterHtmlTag(pro.productName) + "</a>");
				_ul.append(_name);
				var _zekou = $('<li class="materials_price">¥<span class="price_style01"></span><span class="price_style02"></span></li>');
				 
				if(pro.downPrice != null && pro.downPrice != ''){
						_zekou.find(".price_style01").text(Math.round(pro.downPrice*pro.price*10)/100);
						_zekou.find(".price_style02").text(pro.downPrice+"折");
					}else if(pro.vipPrice != null && pro.vipPrice != ''){
						_zekou.find(".price_style01").text(pro.vipPrice_);
						_zekou.find(".price_style02").html("<strike>"+pro.price_+"</strike>");
					}else {
						_zekou.find(".price_style01").text(pro.price_);
					}
				_ul.append(_zekou);
				$("#"+id).append(_ul);
			}
		}
	});
}
function filterHtmlTag(s){
	var re1 = /(\<.[^\<]*\>)/g;
	return s.replace(/"/g,"").replace(/'/g,"").replace(re1,"");
}
