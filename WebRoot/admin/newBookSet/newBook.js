var height_=$(document).height()/2+20;
//0 教辅  1  教材
function loadAllBook(idStr,productType){
	var productName=$("#productName").val();
	$('#dategrid1').datagrid({
	height: height_,
	width:'100%',
	striped:true,
	url:path+'/recommendSet/loadProductAll.action',
	queryParams:{'productName':productName,'idStr':idStr,'productType':productType},
	loadMsg:'数据加载中请稍后……',
	frozenColumns:[[
		{field:'ck',checkbox:true},
		{field:'productId',title:'id',width:150,hidden:true},
		{field:'productName',title:'名称',width:220,sortable:true,align:'center'}]],
	columns:[[
		{field:'publishTime',title:'出版时间',width:200,sortable:true,align:'center',formatter:function(value,row,index){
			return row.publishTime_;
		}},
		{field:'price',title:'价格',width:150,sortable:true,align:'center',formatter:function(value,row,index){
			return row.price_;
		}},
		{field:'price1',title:'优惠价格',width:150,align:'center',
			formatter:function(value,row,index){
				if(row.vipPrice!=null&&row.vipPrice>0){
					return row.vipPrice_;
				}else if(row.downPrice!=null&&row.downPrice>0){
					return row.webDownPrice_;
				}else{
					return row.price_;
				}
			}},
		{field:'subject',title:'学科',width:160,sortable:true,align:'center'},
		{field:'productType',title:'类别',width:160,sortable:true,align:'center'},
		{field:'schoolStage',title:'学段', width:160,sortable:true,align:'center',
			formatter: function(value,row,index){
				if (row.schoolStage==1){
					return "小学";
				} else if(row.schoolStage==2){
					return "初中";
				}else{
					return "高中";
				}
			}
		}
	]],
	pagination:true,
	rownumbers:true,
	toolbar:"#tb"
	});
	var p = $('#dategrid1').datagrid('getPager');
	$(p).pagination({
		pageSize: 10,
		pageList: [5,10,15,20],
		beforePageText: '第',
		afterPageText: '页    共 {pages} 页',
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh:function(pageNumber,pageSize){
			$(this).pagination('loading'); 
			$(this).pagination('loaded');
			}
	});
}
$(function(){
	$("#new1").load(path+'/hot/aa.html?asd='+Math.random(),function(){
		$("#newRecContent1").html($("#newRecContent_1").html());
		$("#newRecContent0").html($("#newRecContent_0").html());
	});
	$('#showbox').dialog({
		closed:true,
		title:'选择',
		width:640,
		height:410,
		modal:true,
		buttons:[{
			text:'确定',
			iconCls:'icon-ok',
			handler:function(){
				addRow();
			}
		},{
			text:'关闭',
			handler:function(){
				$('#showbox').dialog('close');
			}
		}]
	});
});
function selectEbook(index){
	$(".rec02").attr("class","rec01");
	$("#id"+index).attr("class","rec02");
	$(".new_rec_content").hide();
	$("#newRecContent"+index).show();
	$("#save1").hide();
	$("#save0").hide();
	$("#save"+index).show();
	$('#showbox').dialog('open');
	var idStr=getIdStr();
	loadAllBook(idStr,index);
	
}

function addRow(){
	var s=$(".rec02").attr("id");
	s=s.substring(s.length-1,s.length);
	var rows = $('#dategrid1').datagrid('getSelections');
	if(rows.length<=0){
		$.messager.alert('温馨提示','请先选择要添加的商品!');
	}else{
		//判断是否可以添加 
		var html_s=$("#newRecContent"+s).html();
		html_s=html_s.trim();
		if(html_s!=null&&html_s!='null'&&html_s!=""){
			var ul_obj="#newRecContent"+s+" div[name='ul_list"+s+"']"+" ul";
			var ul_=$(ul_obj);
			if(ul_!=null&&ul_.length>=0&&rows.length+ul_.length<=3){
				//添加ul
				addul(rows,s);
			}else{
				$.messager.alert('温馨提示','最多添加4本');
				return;
			}
		}else{
			//添加main
			addMain(rows[0],s);
			rows.shift(); 
			if(rows.length>0){
				addul(rows,s);
			}
		}
			//$('#showbox').dialog('close');
		var idStr=getIdStr();
		loadAllBook(idStr,s);
	}
}
function addul(rows,s){
	var ullistobj=$("#newRecContent"+s+" div[name='ul_list"+s+"']");
	for(var i = 0 ; i < rows.length ; i++) {
		var pro = rows[i];
		var _titlex = filterHtmlTag(pro.productName);
		if(_titlex.length > 8){
			_titlex=_titlex.substring(0,8);
		}
		str1 = "<ul sid="+pro.productId+">";
		str1 += "<li class='rec_list_pic'><a href='web/findProductsById.action?id="+pro.productId+"' title='"+filterHtmlTag(pro.productName)+"' target='_blank'><img src='" + pro.thumbnail_1 + "' /></a></li>";
		str1 += "<li class='rec_list_title'><a href='web/findProductsById.action?id="+pro.productId+"' title='"+filterHtmlTag(pro.productName)+"' target='_blank'>" + _titlex + "</a></li>";
		str1 += "</ul>";
		ullistobj.append(str1);
	}
}
function addMain(bsPro,s){
	var str = '';
	str += "<div  class='new_rec_main'>";
  	str += "<div class='rec_main_pic'><a href='web/findProductsById.action?id="+bsPro.productId+" ' title='"+filterHtmlTag(bsPro.productName)+"' target='_blank'><img src='" + bsPro.thumbnail_1 + "' /></a></div>";
	str += "<div class='rec_main_summary'>";
	str += "<p><a name='proid"+s+"' sid='"+bsPro.productId+"' href='web/findProductsById.action?id="+bsPro.productId+"' target='_blank'>" + filterHtmlTag(bsPro.productName) + "</a></p>";
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
	str += "<div name='ul_list"+s+"' class='new_rec_list'>";
	str += "</div>";
	$("#newRecContent"+s).html(str);
}
function removeRow(){
	var s=$(".rec02").attr("id");
	s=s.substring(s.length-1,s.length);
	$("#newRecContent"+s).html("");
}
function save1(){
	var s=$(".rec02").attr("id");
	s=s.substring(s.length-1,s.length);
	$('#save'+s).linkbutton({disabled:true});
	var newBook1="";
	var newBook2="";
	var galleryStr=$("#galleryStr").html();
	if(s=='0'){
		newBook1=$("#newRecContent_1").html();
		newBook2=$("#newRecContent0").html();
	}else if(s=='1'){
		newBook1=$("#newRecContent1").html();
		newBook2=$("#newRecContent_0").html();
	}else{
		$.messager.alert('温馨提示','操作失败，请稍候重试');
		return;
	}
	$.ajax({
		type:"post",
		url:path+"/newBookSet/newBookSave.action",
		//data:"newBook1="+newBook1+"&newBook2="+newBook2+"&galleryStr="+galleryStr+"&math="+Math.random(),
		data:{newBook1:newBook1,newBook2:newBook2,galleryStr:galleryStr},
		success:function(data){
			$.messager.alert('温馨提示','保存成功！');
			$('#save'+s).linkbutton({disabled:false});
			$("#new1").load(path+'/hot/aa.html?asd='+Math.random(),function(){
				$("#newRecContent1").html($("#newRecContent_1").html());
				$("#newRecContent0").html($("#newRecContent_0").html());
			});
		}
	});
}
function getIdStr(){
	var s=$(".rec02").attr("id");
	s=s.substring(s.length-1,s.length);
	var proids="";
	var thisobj="#newRecContent"+s+" a[name='proid"+s+"']";
	var proid1=$(thisobj).attr("sid");
	if(proid1!=null&&proid1!="null"&&proid1!=""){
		proids+=proid1;
	}else{
		return proids;
	}
	var ulsobj="#newRecContent"+s+" div[name='ul_list"+s+"']"+" ul";
	var uls=$(ulsobj);
	if(uls!=null&&uls.length>0){
		for(var j=0;j<uls.length;j++){
			proids+=","+$(uls[j]).attr("sid");
		}
	}
	return proids;
}
function filterHtmlTag(s){
	var re1 = /(\<.[^\<]*\>)/g;
	return s.replace(/"/g,"").replace(/'/g,"").replace(re1,"");
} 