
var financeRecord = function() {
	var financeRecordInit = function() {
		$("title").html("财务记录 | 财务管理 | AnyoneHelps");
    	$(".nav3").addClass("active open");
		$(".nav32").addClass("active open");
		$('.start-date>input, .end-date>input').datetimepicker({
            language: 'zh-CN',
            autoclose: 1,
            todayBtn: 1,
            pickerPosition: "bottom-right",
            weekStart: 1,  
            startView: 2,  
            minView: 2,  
            format: 'yyyy-mm-dd'
           
        });
    }
    var recordLoad = function() {
    	
    	
    	var searchCondition = {
    		key : "",
    		type : [],
    		state : "",
    		sdate : "",
    		edate : "",
    		demandId : $(":hidden[name='taskId']").val(),
    		pageIndex : 1,
    	};
    	
    	$(".search-start").click(function(){
    		g_flag = true;
    		var type = $("select[name='type']").val();
    		/*  haokun delete 2017/03/02 注释掉旧版本，新的现在下面
    		if(type == "0"){
    			searchCondition.type = [];
        		searchCondition.type.push(11);
        		searchCondition.type.push(12);
        		searchCondition.type.push(13);
        		searchCondition.type.push(14);
        		searchCondition.type.push(15);
    		}else if(type == "1"){
    			searchCondition.type = [];
    			searchCondition.type.push(33);
    			searchCondition.type.push(36);
    			searchCondition.type.push(39);
    			
    		}else if(type == "2"){
    			searchCondition.type = [];
    			searchCondition.type.push(41);
    		}else if(type == "3"){
    			searchCondition.type = [];
    			searchCondition.type.push(18);
    			searchCondition.type.push(19);
    			searchCondition.type.push(20);
    			searchCondition.type.push(21);
    			searchCondition.type.push(23);
    		}else if(type == "4"){
    			searchCondition.type = [];
    			searchCondition.type.push(22);
    			searchCondition.type.push(25);
    		}else if(type == "5"){
    			searchCondition.type = [];
    			searchCondition.type.push(24);
    			searchCondition.type.push(40);
    		}else if(type == "6"){
    			searchCondition.type = [];
    			searchCondition.type.push(34);
    		}else if(type == "7"){
    			searchCondition.type = [];
    			searchCondition.type.push(38);
    		}
			*/
			/*haokun add 2017/03/02 start新的*/
			if(type == "0"){  //haokun added 2017/03/02 所有
    			searchCondition.type = [];
        		searchCondition.type.push(11);
        		searchCondition.type.push(12);
        		searchCondition.type.push(13);
        		searchCondition.type.push(14);
        		searchCondition.type.push(15);
				//searchCondition.type.push(16);
    			//searchCondition.type.push(17);
    			searchCondition.type.push(18);
    			searchCondition.type.push(19);
    			searchCondition.type.push(20);
        		searchCondition.type.push(21);
        		searchCondition.type.push(22);
        		searchCondition.type.push(23);
        		searchCondition.type.push(24); 
        		searchCondition.type.push(25);
				//searchCondition.type.push(26);
    			//searchCondition.type.push(31);
    			//searchCondition.type.push(32);
    			searchCondition.type.push(33);
    			searchCondition.type.push(34);
        		//searchCondition.type.push(35);
        		searchCondition.type.push(36);
        		//searchCondition.type.push(37);
        		searchCondition.type.push(38);
        		searchCondition.type.push(39);
				searchCondition.type.push(40);
    			searchCondition.type.push(41);		
				searchCondition.type.push(42);
    		}else if(type == "1"){   //收入-所有
    			searchCondition.type = [];			
				searchCondition.type.push(11);
        		searchCondition.type.push(12);
        		searchCondition.type.push(13);
        		searchCondition.type.push(14);
        		searchCondition.type.push(15);
    			searchCondition.type.push(18);
    			searchCondition.type.push(19);
    			searchCondition.type.push(20);
        		searchCondition.type.push(21); 
        		searchCondition.type.push(22);
        		searchCondition.type.push(23);
        		//searchCondition.type.push(24);  提现算支出
        		searchCondition.type.push(25);
    		}else if(type == "2"){   //收入-充值
    			searchCondition.type = [];
				searchCondition.type.push(11);
        		searchCondition.type.push(12);
        		searchCondition.type.push(13);
        		searchCondition.type.push(14);
        		searchCondition.type.push(15);
    		}else if(type == "3"){   //收入-任务收款
    			searchCondition.type = [];
				searchCondition.type.push(22);
    		}else if(type == "4"){   //收入-冻结金额返还
    			searchCondition.type = [];
				searchCondition.type.push(18);
				searchCondition.type.push(19);
				searchCondition.type.push(20);
                searchCondition.type.push(21);
				searchCondition.type.push(23);
    		}else if(type == "5"){   //收入-邀请奖励
    			searchCondition.type = [];
				searchCondition.type.push(0);  //插入一个空值
    		}else if(type == "6"){   //收入-提成奖励
    			searchCondition.type = [];
				searchCondition.type.push(25);
    		}else if(type == "7"){   //支出-所有
    			searchCondition.type = [];
    			searchCondition.type.push(34);
				searchCondition.type.push(38);
				searchCondition.type.push(40);
    			searchCondition.type.push(41);	
				searchCondition.type.push(42);
    		}else if(type == "8"){   //支出-提现
    			searchCondition.type = [];		
                searchCondition.type.push(40);				
    		}else if(type == "9"){   //支出-任务支付
    			searchCondition.type = [];
				searchCondition.type.push(41);	
    		}else if(type == "10"){  //支出-手续费
    			searchCondition.type = [];
				searchCondition.type.push(38);
    		}else if(type == "11"){  //支出-技能认证
    			searchCondition.type = [];
				searchCondition.type.push(34);
    		}else if(type == "12"){  //支出-大牛认证
    			searchCondition.type = [];
				searchCondition.type.push(42);
    		}else if(type == "13"){  //冻结-所有
    			searchCondition.type = [];
				searchCondition.type.push(33);
				searchCondition.type.push(36);
				searchCondition.type.push(39);
    		}else if(type == "14"){  //冻结-任务金额冻结
    			searchCondition.type = [];
				searchCondition.type.push(33);
				searchCondition.type.push(36);
				searchCondition.type.push(39);
				
    		}
			/*haokun add 2017/03/02 end新的*/
			searchCondition.key =  $(this).prev().val();
    		searchCondition.demandId =  $(this).parent().parent().find("#demandId").val();
    		//searchCondition.type = $("select[name='type']").val();
    		searchCondition.state = $("select[name='state']").val();
    		searchCondition.sdate = $(".start-date>input").val();
    		searchCondition.edate = $(".end-date>input").val();
    		searchCondition.pageIndex = 1;
    		loadcwjlinfo();
    	})
    	function loadcwjlinfo() { 
    		$.ajax({
    			type : "get",
    			url : "/account/detail/search",
    			data : $.param({
    				"key" : searchCondition.key,
    				"type" : searchCondition.type,
    				"state" : searchCondition.state,
    				"sd" : searchCondition.sdate,
    				"ed" : searchCondition.edate,
    				"demandId" : searchCondition.demandId,
    				"pageIndex" : searchCondition.pageIndex
    			},true),
    			success : function(response) {
    				var code = response.code;
    				if (code == "200") {
    					if (response.data != null) {
    						showcwjllist(response.data.datas);
    						showcwjllink(response.data);
    					} else {
    						showcwjllist(null);
    						showcwjllink(null);
    					}
    					//afterloadcwjllist(); 
    				} else if ("607" == code) {
    					alert("您尚未登录或登录已失效！");
    					logout();
    				} 
    			}
    		});
    	}

    	function showcwjllist(list) {
    		$("#sample_1 tbody").html("");
    		var str = "";
    		var i = 0;
    		if (list != null && list.length > 0) {
    			$.each(list, function() {
    				str += "<tr class='odd gradeX item'>";
					
					//haokun start 2017/03/08 调整
					str += "<td id='td-date' class='center'>" + this.createDate + "</td>";
    				str += "<td id='td-invoice'> "+this.invoiceNo+" </td>";  //haokun modified 2017/03/08 加了id
    				
    				if (this.type < "30") {   /*haokun modified 2017/03/02 收入*/
						str += "<td id='td-name' >" + this.name + "</td>";                   //haokun modified 2017/03/08 加了id 去掉颜色
    				}else if(this.type == "33" || this.type == "36" || this.type == "39" ){    //haokun modified 2017/03/08 加了id 
						str += "<td id='td-name' >" + this.name + "</td>";                 //haokun modified 2017/03/08 加了id  去掉颜色
    				} else {     /*haokun modified 2017/03/02 取现和支出*/
						str += "<td id='td-name' >" + this.name + "</td>";                           //haokun modified 2017/03/08 加了id 去掉颜色
    				}
    				
    				
    				var remark = "";
    				
    				if (this.demandId == null||this.demandId == "") {
    					remark += "";
    				}else{
    					var taskLink = "";
    					if (this.type == "18"
    							||this.type == "19"
    							||this.type == "20"
    							||this.type == "21"
    							||this.type == "23"
    							||this.type == "33"
    							||this.type == "36"
    							||this.type == "37"
        						||this.type == "39"
        						||this.type == "41"
    								) {
    						taskLink = "/dashboard/Task/pubDetail.jsp?id="+base64Encode(this.demandId);
    						
    					}else if(this.type == "22"||this.type == "38"){
    						taskLink = "/dashboard/Task/accDetail.jsp?id="+base64Encode(this.demandId);
    					}else if(this.type == "25"){ 
    						taskLink = "/task/detail.jsp?id="+base64Encode(this.demandId);
    					}
    					//alert("title="+ this.demand.title);
	                    
						//haokun modified start 2017/03/09
						if(this.demand != null){
    					   remark += "任务:<a href='"+taskLink+"'  target='_blank'>" +this.demand.title+"</a>";
						}
						//haokun modified start 2017/03/09
						
    				}
    				if (this.thirdNo == null||this.thirdNo == "") {
    					remark += "";
    				}else{
    					remark += "交易号:<a>" + this.thirdNo+"</a>"; 
    				}
    				if (this.remark1 != null&&this.remark1 != "") {
    					remark += this.remark1;
    				}
    				str += "<td id='td-remark'>"+ remark +"</td>"
    				var status = "";
    				
    				if (this.state == "0") {
    					status = "未处理";
    				}else if (this.state == '1')
    				{
    					status = "完成";
    				}
    				else if (this.state == '2') {
    					status = "失败";
    				}
    				str += "<td id='td-status'>" + status + "</td>";
					
					if (this.type < "30") {   /*haokun modified 2017/03/02 收入*/

    					str += "<td id='td-amount' style='color:#40bf40'>+$" + this.amount + "</td></tr>";    //haokun modified 2017/03/08 加了id 加上$
    				}else if(this.type == "33" || this.type == "36" || this.type == "39" ){    //haokun modified 2017/03/08 加了id 
    					str += "<td id='td-amount' style='color:#4db8ff'>$" + this.amount + "</td></tr>";    //haokun modified 2017/03/08 加了id  加上$
					} 
					else {     /*haokun modified 2017/03/02 取现和支出*/
    					str += "<td id='td-amount' style='color:red'>-$" + this.amount+"</td></tr>";     //haokun modified 2017/03/08 加了id     加上$
    				}
					//haokun 2017/03/08 end 调整
					
    				i++;
    			});
    		}else{
    			str += "<tr><td colspan=99>暂无数据.......</td></tr>";
    		}
    		$("#sample_1 tbody").html(str);
    		/*$(".task-id-a").click(function(){
    			g_flag = true;
    			$(".search .key .search-key").val("");
    			$("select[name='type']").val("");
    			$("select[name='state']").val("");
    			$("#demandId").val($(this).html());
    			searchCondition.demandId =  $(this).html();
    			searchCondition.key =  "";
        		searchCondition.type = "";
        		searchCondition.state = "";
        		searchCondition.pageIndex = 1;
        		loadcwjlinfo();
    		})*/
    	}

    	var g_page_size = 0;
        var g_flag = false; 
    	function showcwjllink(pageSplit) {
    		if (pageSplit != null) {
        		var rowCount = pageSplit.rowCount;
        		var pageSize = pageSplit.pageSize;
        		var pageNow = pageSplit.pageNow;
        		var pageCount = pageSplit.pageCount;
        		
        		if(g_page_size != pageCount && g_flag){  
        		    $(".search-pagination").html('<ul class="pagination"></ul>');  
        		    g_flag = false;
        		}  
        		g_page_size = pageCount;
        		
        		$(".pagination").twbsPagination({
                    totalPages: pageCount,
                    first: g_first,
                   	prev: g_prev,
                  	next: g_next,
                   	last: g_last,
                    //href: '?a=&page={{number}}&c=d' ,
                    onPageClick: function (event, page) {
                    	searchCondition.pageIndex = page;
                    	loadcwjlinfo(page);
                    }
                });
        	}
    	}

    	
		loadcwjlinfo()
    }
    return {
        //main function to initiate the module
        init: function() {
        	financeRecordInit();
        	recordLoad();
        }

    };

}();

jQuery(document).ready(function() {
	financeRecord.init();
	
	/*haokun added start 2级分类 2017/03/02*/
    //$(".type").hide();
    $("#typeFirst").change(function(){
        var typeFirstVal = $(this).val();
        if(typeFirstVal=="0"){
            $(".type").addClass("hide");
			$("#type option").remove();
			$("#type").append('<option value="0">所有</option>');
        }
        else if(typeFirstVal == "1"){
			$(".type").removeClass("hide");
		    $("#type option").remove();
            $("#type").append('<option value="1">所有</option><option value="2">充值</option><option value="3">任务收款</option><option value="4">冻结金额返还</option><option value="5">邀请奖励</option><option value="6">提成奖励</option>');
        }else if(typeFirstVal == "2"){
			$(".type").removeClass("hide");
		    $("#type option").remove();
            $("#type").append('<option value="7">所有</option><option value="8">提现</option><option value="9">任务支付</option><option value="10">手续费</option><option value="11">技能认证</option><option value="12">大牛认证</option>');
        }else if(typeFirstVal == "3"){
			$(".type").removeClass("hide");
		    $("#type option").remove();
            $("#type").append('<option value="13">所有</option><option value="14">任务金额冻结</option>');
        }
    });
	/*haokun added end 2级分类 2017/03/02*/
	
});