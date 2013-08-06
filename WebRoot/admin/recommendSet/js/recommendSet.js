var height_=$(document).height()/2+20;
function init_(){
	var str1="<div class='recommdeng_tit'>";
	str1+="<span>";
	str1+="<img src='";
	str1+=path;
	str1+="/images/recommdeng_tit.gif' />";
	str1+="<img src='";
	str1+=path;
	str1+="/images/new.gif '/></span></div>";
	$("#selectedBook").append(str1);
	$("#selectedBook").append("<div class='recommend_con' id='foot_recommend_con'></div>");
}
function sort_(){
	var indicator = $('<div class="indicator">>></div>').appendTo('body');
	$('.drag-item').draggable({
		revert:true,
		deltaX:0,
		deltaY:0
	}).droppable({
		onDragOver:function(e,source){
			indicator.css({
				display:'block',
				top:$(this).offset().top+$(this).outerHeight()-5
			});
		},
		onDragLeave:function(e,source){
			indicator.hide();
		},
		onDrop:function(e,source){
			$(source).insertAfter(this);
			indicator.hide();
		}
	});
}
function loadSelectBook(type){
//	$.ajax({
//		type:"post",
//		url:path+"/recommendSet/loadSelectBook.action",
//		data:"math="+Math.random(),
//		success:function(data){
//			if(data.path!=null&&""!=data.path){
//			
//			}
//		}
//	});
	$("#selectedBook").load("/hot/recommendBook.html?xdax="+Math.random());
}
function loadAllBook(idStr){
	var productName=$("#productName").val();
	$('#dategrid1').datagrid({
	height: height_,
	width:'100%',
	striped:true,
	url:path+'/recommendSet/loadProductAll.action',
	queryParams:{'productName':productName,'idStr':idStr},
	loadMsg:'数据加载中请稍后……',
	columns:[[
	    {field:'ck',checkbox:true},
	    {field:'productId',title:'id',width:150,hidden:true},
		{field:'productName',title:'名称',width:220,sortable:true,align:'center'},
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

function addRow(){
	var rows = $('#dategrid1').datagrid('getSelections');
	//var idstrs1= $('input[name="checkBook"]:checked');
	var idstrs1=$("#selectedBook ul");
	var i=0;
	if(idstrs1!=null&&idstrs1.length>0){
		i=idstrs1.length;
	}else if(rows.length>0&&rows.length+i<=5){
		init_();
	}
	if(rows.length<=0){
		$.messager.alert('温馨提示','请先选择要添加的商品!');
		//$("#selectedBook").html("");
	}else if(rows.length>0&&rows.length+i<=5){
		for(var i =rows.length-1;i>=0;i--){
			var str="";
			//str+='<input type="checkbox" checked name="checkBook" value="';
			//str+=rows[i].productId;
			//str+='"style="display:none"/>';
			str+='<ul sid="'+rows[i].productId+'" style="position:relative;"><li class="re_pic"><a href="'+path+'/web/findProductsById.action?id='+rows[i].productId+'" target="_blank">';//商品详细页面
			str+='<img alt="';
			str+=rows[i].productName;
			str+='" src="';
			str+=fileDomain+rows[i].thumbnail;
			str+='"></a>';
			str+='<li class="re_name"><a href="'+path+'/web/findProductsById.action?id='+rows[i].productId+'" target="_blank">';
			str+=rows[i].productName;
			str+='</a></li><li class="re_price">¥';
			if(rows[i].vipPrice!=null&&rows[i].vipPrice>0){
				str+=rows[i].vipPrice_;
			}else if(rows[i].downPrice!=null&&rows[i].downPrice>0){
				str+=Math.round(rows[i].price*rows[i].downPrice*10)/100.0;
			}else{
				str+=rows[i].price_;
			}
			str+='</li><li class="shoping_cart"><input name="in_button01" type="button" value="加入购物车" onclick="tocart('+ rows[i].productId + ')"/></li></ul>';
			$("#foot_recommend_con").append(str);
		}
		loadAllBook(getIdStr());
	}else{
		$.messager.alert('温馨提示','推荐电子书最多5本!');
	}
	//sort_();
}
function removeRow(){
	$("#selectedBook").html("");
	loadAllBook("");
}
function getIdStr(){
	//var idstrs= $('input[name="checkBook"]:checked');
	var idstrs=$("#selectedBook ul");
	var idStr="";
	if(idstrs!=null&&idstrs.length>0){
		for(var i=0;i<idstrs.length;i++){
			idStr+=$(idstrs[i]).attr("sid")+",";
		}
		if(idStr!=null&&idStr!=""&&idStr.replace(',', '')!=""){
			idStr=idStr.substring(0, idStr.length-1);
		}
	}
	return idStr;
}
function search(){
	var idStr=getIdStr();
	if(idStr!=null&&idStr!=""){
		loadAllBook(idStr);
	}else{
		loadAllBook('');
	}
}
function save1(){
	var htmlStr=$("#selectedBook").html();
	var idArr1=$("#selectedBook ul");
	//var idArr1= $('input[name="checkBook"]:checked');
	if(idArr1.length<=5){
		$('#save').linkbutton({disabled:true});
		$.ajax({
  			type:"post",
  			url:path+"/recommendSet/recommendSave.action",
  			data:"htmlStr="+htmlStr+"&math="+Math.random(),
  			success:function(data){
  				if(data.flag==1){
  					$.messager.alert('温馨提示','保存成功！');
  				}else{
  					$.messager.alert('温馨提示','保存失败，请重新操作');
  				}
  			}
  		});
	}else{
		$.messager.alert('温馨提示','最多选择5本电子书');
	}
	$('#save').linkbutton({disabled:false});
	}
