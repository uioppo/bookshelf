function initPagination(){
		$("#Pagination").pagination(num_entries, {
			num_edge_entries: 1,
		    num_display_entries: 10,
			callback: function callbackInitPagination(page_index, jq){
						loadPageData(page_index+1);
			},
		    items_per_page:5,//每页条数
		    prev_text:"<<",
		    next_text:">>",
		    ellipse_text:"..."
		});
	}
  		function myBookDetail(myBookId){
  			if(myBookId!=null&&myBookId!=""){
  				window.location.href=path+"/mybook/myBookDetail.action?myBookId="+myBookId;
  			}
  		}
  		function loadPageData(nowNum){
  			var str="";
  			$.ajax({
  				url: path+"/mybook/myBookList.action",
  				data:"page="+nowNum+"&math="+Math.random(),
  				success: function(data){
  					num_entries=data.bsMybookPage.totalCount;
  					if(data!=null&&data.bsMybookPage!=null&&data.bsMybookPage.list!=null&&data.bsMybookPage.list.length>0){
	  					var lists=data.bsMybookPage.list;
	  					var s;
	  					$("#Pagination").show();
	  					$("#book_list").show();
	  					$("#emptydiv").hide();
	  					for(var i=0;i<lists.length;i++){
	  						s=lists[i];
	  						str+="<tr><td align='left' valign='middle'><div class='book_pic'>";
	  						str+="<img style='cursor: pointer;' width='30px' height='30px' src='";
	  						str+=fileDomain+s.bsProducts.thumbnail;
	  						str+="'  onclick='myBookDetail(";
	  						str+=s.mybookId;
	  						str+=")'/></div></td>";
	  						
	  						str+="<td align='center' valign='middle'>";
	  						str+=s.bsProducts.productName;
	  						str+="</td>";
	  						
	  						str+="<td align='center' valign='middle'>";
	  						if(s.bsProducts.schoolStage==1){
	  							str+="小学";
	  						}else if(s.bsProducts.schoolStage==2){
	  							str+="初中";
	  						}else{
	  							str+="高中";
	  						}
	  						str+="</td>";
	  						
	  						str+="<td align='center' valign='middle'>";
	  						str+=s.addTime_;
	  						str+="</td>";
	  						
	  						str+="<td align='center' valign='middle'>";
	  						str+=s.deadline_;
	  						str+="</td>";
	  						
	  						str+="<td align='center' valign='middle'>";
	  						str+=s.downNumbers;
	  						str+="/";
	  						str+=s.totalNumbers;
	  						str+="</td>";
	  						
	  						str+="</tr>";
	  					}
	  				}else{
	  					$("#Pagination").hide();
	  					$("#book_list").hide();
	  					$("#emptydiv").show();
	  				}
  					
  					$("#datapage").html(str);
  					if(initfenye){
  						initfenye=false;
  						initPagination();
  					}
			}});
  		}
  		function checkLoginAgain(){
  			$.ajax({
  				type:"post",
  				url: path + '/checkLogin.action',
  				async:false,
  				success:function(json) {
  					if(json != null && json != '') {
  					} else {
  						window.location.href = path + "/login.jsp?url="+window.location.href+"&no=back";
  					}
  				}
  				
  			});
  		}