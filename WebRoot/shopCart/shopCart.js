var _up_bool = 0;
var _down_bool = 0;
function changeValue(a){
			var _value=$("#proCount"+a).val();
			var proId=$("#proId"+a).val();
			if(parseInt(_value)<99){
				$("#proCount"+a).val(parseInt(_value)+1);
				_up_bool +=1;
				setTimeout(function(){
					if(_up_bool<=1){
						var _value1=$("#proCount"+a).val();
						changeCookie(proId,_value1);
						loadCartList();
					}
						_up_bool-=1;
					
				}, 800);
			}
		}
		function changeValue1(a){
			var _value=$("#proCount"+a).val();
			var proId=$("#proId"+a).val();
			if(parseInt(_value)>=2){
				$("#proCount"+a).val(parseInt(_value)-1);
				_down_bool +=1;
				setTimeout(function(){
					if(_down_bool<=1){
						var _value1=$("#proCount"+a).val();
						changeCookie(proId,_value1);
						loadCartList();
					}
					_down_bool-=1;
					
				}, 800);
			}
		}
		function validateValue(a){
			var _value=$("#proCount"+a).val();
			var proId=$("#proId"+a).val();
			if(parseInt(_value)>=1){
				changeCookie(proId,(parseInt(_value)));
				loadCartList();
			}else{
				loadCartList();
			}
		}
		function changeCookie(proId,proCount){
			var v = $.cookie("the_bs_product");
			var a = [];
			if(v){
				a = v.split(",");
				var idArr=[];
				var idArrStr="";
				for(var i=0;i<a.length;i++){
					var id_count=a[i].split("_");
					idArr.push(id_count[0]);
					idArrStr+=id_count[0]+",";
				}
				if($.inArray(proId+"",idArr)>=0){
					var id_index_=$.inArray(proId+"",idArr);
					var id_count_str_=proId+"_"+proCount;
					a.splice(id_index_,1);
					a.push(id_count_str_);
					$.cookie("the_bs_product", a.join(","), { expires: 7, path:'/'});
					return;
				}
			}
			var str=proId+"_"+1;
			a.push(str);
			$.cookie("the_bs_product", a.join(","), { expires: 7, path:'/'});
			loadCartFromLocal();
		}
		function fmoney(s, n){
		   n = n > 0 && n <= 20 ? n : 2;
		   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		   var l = s.split(".")[0].split("").reverse(),
		   r = s.split(".")[1];
		   t = "";
		   for(i = 0; i < l.length; i ++ ){
		      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		   }
		   return t.split("").reverse().join("") + "." + r;
		}
		
		function loadCartFromLocal(){
			var v = $.cookie("the_bs_product");
			if(v){
				var a = v.split(",");
				if(a){
					$("#cart").text(a.length);
				}else{
					$("#cart").text("0");
				}
			}else{
				$("#cart").text("0");
			}
		}
		
		function loadCartList(){
			var v = $.cookie("the_bs_product");
			if(v!=null && v!=undefined && ""!=v){
				$("#tablediv").show();
				$("#emptydiv").hide();
				var a=[];
				a = v.split(",");
				var b_arr=[];
				for(var i=0;i<a.length;i++){
					var id_count=a[i].split("_");
					b_arr.push(id_count[0]);
				}
				jQuery.ajax({
					type:"post",
					url:path+"/findCartProducts.action",
					data:{"math":Math.random()},
					dataType:"json",
					success:function(data){
						if(data.cartProductList!=null&&data.cartProductList.length>0){
							dataPage(data,a,b_arr);
						}else{
							$("#tablediv").hide();
							$("#emptydiv").show();
						}
						topShopCart();
					}
				});
			}else{
				$("#tablediv").hide();
				$("#emptydiv").show();
			}
		}
		function dataPage(data,a,b_arr){
			$("#cartMain").html("");
			var total_ = 0;
			var o = data.cartProductList;
			var bestPrice=data.bestPrice;
			var totalPrice=data.totalPrice;
			var bestSaleName=data.bestSaleName;
			var bestDescrprion=data.bestDescrprion;
			var total=data.total;
			var totalVipPrice=0;
			jQuery(o).each(function(i){
				var id_index=jQuery.inArray(this.productId+"", b_arr);  
				var id_count_arr=a[id_index].split("_");
				var _tbody=$("#cartMain");
				var _tr ;
				if((i+1)%2==0){
					_tr = $("<tr bgcolor='#DDF0EE'></tr>");
				}else{
					_tr = $("<tr></tr>");
				}
				var str="<td align='left' valign='middle'><div class='book_pic'><a href='"+proDetailPath+this.productId+"'><img src='"+fileDomain+this.thumbnail+"' /></a></div>";
				str+="<div class='book_name'><a href='"+proDetailPath+this.productId+"'>";
				str+=this.productName;
				str+="</a></div></td>";
				_tr.append(str);
				var str1="<td id='pricetd"+i+"' class='book_price' align='center' valign='middle'>";
				var t ;
				var t1;
				if(this.vipPrice_ != null && this.vipPrice_ != ""&& this.vipPrice_ != "0.00"){
					t1=this.vipPrice_;
					totalVipPrice+= (this.price-this.vipPrice)*parseInt(id_count_arr[1]);
					str1+=this.price_;
				}else if(this.downPrice_ != null && this.downPrice_ != ""&& this.downPrice_ != "0.00"){
					t1=this.webDownPrice_;
					totalVipPrice+= (this.price-t1)*parseInt(id_count_arr[1]);
					str1+=this.price_;
				}else if(this.price_ != null && this.price_!= ""&&this.price_ != "0.00"){
					str1+=this.price_;
					//t=this.price*parseInt(id_count_arr[1]);
					t1="0.00";
				}else{
					str1+="0.00";
					//t=0;
					t1="0.00";
				}
				str1+="</td>";
				_tr.append(str1);
				var str2="<td class='book_discount' align='center' valign='middle'>";
				if(t1=="0.00"){
					str2+="";
				}else{
					str2+=fmoney(this.price_-t1,2);
				}
				str2+="</td>";
				_tr.append(str2);
				str3="<td align='center' valign='middle'><div class='num_select' style='float:right'><div class='num_input'>";
				str3+="<input id='proId"+i+"' value='"+this.productId+"' type='hidden'/>";
				str3+="<input id='proCount"+i+"' value='"+id_count_arr[1]+"' onblur='validateValue("+i+")' name='proCount' type='text' maxlength='2'/></div>";
				str3+="<div class='num_button' ><a id='add' onclick='changeValue("+i+")' href='javascript:void(0)'><img src='"+path+"/images/up.gif' /></a>";
				str3+="<a id='minus' onclick='changeValue1("+i+")' href='javascript:void(0)'><img src='"+path+"/images/down.gif' /></a>";
				str3+="</div></div>";
				str3+="</td>";
				_tr.append(str3);
				str4="<td id='shouquan"+i+"' align='center' valign='middle'>";
				str4+=parseInt(id_count_arr[1])*3;
				str4+="</td>";
				_tr.append(str4);
				str5="<td align='center' valign='middle'>";
				if(this.usePeriod==null){
					str5+="";
				}else if(this.usePeriod == 1200){
					str5+="永久";
				}else {
					str5+=this.usePeriod+"月";
				}
				str5+="</td>";
				_tr.append(str5);
				str6="<td class='delete' align='center' valign='middle'><a href='javascript:void(0)' onclick='removeOne(";
				str6+=this.productId;
				str6+=",this);'>删除</a></td>";
				_tr.append(str6);
				_tbody.append(_tr);
			});
			//ss+="商品已优惠："+fmoney(totalVipPrice,2)+" ";
			
			$("#total_no").html("¥"+fmoney(totalPrice,2));
			$("#youhui_no1").html("-&nbsp;¥"+fmoney(totalVipPrice,2));
			if(bestPrice>0){
				if(bestSaleName!=null&&bestSaleName!="null"&&bestSaleName!=""){
					$('#show_tips').html("您已参与<font color='red' style='cursor: pointer;' title='"+bestDescrprion+"'>"+bestSaleName+"</font>活动!");
				}
				if(totalVipPrice>0){
					$('#show_tips').append("打折和特价商品不能参与优惠活动");
				}
				$('#show_tips').show();
			}else{
				$('#show_tips').hide();
			}
			var _base_pac = 0;
			if(bestPrice>0){
				_base_pac = (total-bestPrice);
				$("#youhui_no").html("-&nbsp;¥"+fmoney((total-bestPrice),2));
			}else{
				$("#youhui_no").html("-&nbsp;¥0.00");
			}
			//
			$("#total").html(fmoney((totalPrice-totalVipPrice-_base_pac),2));
			loadCartFromLocal();
		}

		function a(){
		var a=[1,2];
			$.cookie("s",a.join(","),{ expires: 7, path:'/'});
		}
		function removeOne(p,o){
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
					loadCartFromLocal();
					$(o).parents("tr").remove();
					loadCartList();
					topShopCart();
				}
			}
		}
		
		function clearAll(){
			$.cookie('the_bs_product', null, { expires: 7, path:'/'});
			$("#cartMain").html("");
			$("#cart").text("0");
			$("#total").text("0.00");
			$("#tablediv").hide();
			$("#emptydiv").show();
			topShopCart();
		}
		function createOrder(){
			var v = $.cookie("the_bs_product");
			if(v!=null && v!=undefined && ""!=v){
				window.location.href=path+"/createOrder.action";
			}else{
				alert("请先选择商品");
			}
		}
		function jiesuan(){
			$("#jiesuan").attr("disabled","disabled");
			window.location.href=path+"/user/createOrder.action";
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
					return;
				}
			}
			var str=proId+"_"+1;
			a.push(str);
			$.cookie("the_bs_product", a.join(","), { expires: 7, path:'/'});
			loadCartFromLocal();
		}