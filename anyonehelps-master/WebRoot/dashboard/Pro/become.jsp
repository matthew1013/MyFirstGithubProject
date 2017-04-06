<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>

<link href="/assets/pages/css/pro-user-become.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="container">
    <%@ include file="/include/leftsider.jsp"%>
	<!-- BEGIN CONTENT -->
	<div class="page-content-wrapper">
		<!-- BEGIN CONTENT BODY -->
		<div class="page-content">
			<!-- BEGIN PAGE BASE CONTENT -->
			<div class="row">
				<div class="col-md-12">
					<!-- BEGIN EXAMPLE TABLE PORTLET-->
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption font-dark">
                                <i class=" icon-list"></i>
                                <span class="caption-subject uppercase">成为大牛</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                        	<div class="base-info">
                        		<div class="base-title">
                            		<div class="caption font-dark">
                                		<span class="caption-subject uppercase">基本信息</span>
                            		</div>
                        		</div>
	                            
	                            <div class="row margin-top-30">
	                        		<div class="col-md-2 col-sm-2 col-xs-12">
	                             		<span class="right-10">真实姓名<span class="font-red-flamingo">*</span>：</span>
	                                </div>
	                            	<div class="col-md-6 col-sm-6 col-xs-12">
	                               		<input type="text" name="realname" placeholder="请输入您的真实姓名" class="form-control radius0"/>
	                                </div>
	                         		<div class="col-md-4 col-sm-4 col-xs-12"> </div>
	                           	</div>
	                           	<div class="row margin-top-30">
	                        		<div class="col-md-2 col-sm-2 col-xs-12">
	                             		<span class="right-10">性别<span class="font-red-flamingo">*</span>：</span>
	                                </div>
	                            	<div class="col-md-9 col-sm-9 col-xs-12">
	                            		<div class="sex-list">
	                                		<div class="sex">
												<input type="radio" name="sex" value="1" checked>
												男性 
											</div>
											<div class="sex margin-left-21">
												<input type="radio" name="sex" value="2" >
												女性
											</div>
											<div class="sex margin-left-21">
												<input type="radio" name="sex" value="3" >
												保密
											</div>
                                         </div>
	                                </div>
	                           	</div>
	                           	
	                           	<div  class="row margin-top-30 id-upload">
	                          		<div class="col-md-2 col-sm-2 col-xs-12">
	                             		<span class="right-10">身份证明<span class="font-red-flamingo">*</span>：</span>
	                                </div>
	                                <div class="col-md-10 col-sm-10 col-xs-12">
	                                	<div class="row upload-list">
	                                		
	                                	</div>
	                                	<div class="row">
	                                		<span class="btn file-upload fileinput-button">
                                            	<span> 上传文件 </span>
                                            	<input type="file" name="file" data-url="/pro_user/upload">
                                       	 	</span>
                                       	 	<label class="control-label font-red-flamingo file-desc">
												提供有效的身份认证信息，支持格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
											</label>
                                        </div>
                                        <div class="progress">
										    <div class="bar"></div>
										</div>
	                              	</div>
	                        	</div>
	                           	
                        	</div>
                        	
                        	
                        	<div class="pro-info">
                        		<div class="base-title">
                            		<div class="caption font-dark">
                                		<span class="caption-subject uppercase">申请信息</span>
                            		</div>
                        		</div>
	                            
	                            <div class="row margin-top-30">
	                        		<div class="col-md-2 col-sm-2 col-xs-12">
	                             		<span class="right-10">申请专业领域<span class="font-red-flamingo">*</span>：</span>
	                                </div>
	                            	<div class="col-md-3 col-sm-3 col-xs-5">
	                            		<select name="proType" class="form-control radius0">
	                                    	<option value="">请选择领域分类</option>
	                                     	<option value="1">学术</option>
											<option value="2">学习与工作</option>
											<option value="3">生活娱乐</option>
	                              		</select>
	                                </div>
	                         		<div class="col-md-4 col-sm-4 col-xs-6"> 
	                         			<select name="pro" class="form-control radius0" style="margin-left:15px">
	                                    	<option value="">请选择领域</option>
	                              		</select>
	                         		</div>
	                           	</div>
	                           	
	                           	<div class="row margin-top-30">
	                             	<div class="col-md-2 col-sm-2 col-xs-12">
	                                   	<span class="right-10">申请理由<span class="font-red-flamingo">*</span>：</span>
	                               	</div>
	                              	<div class="col-md-10 col-sm-10 col-xs-12">
	                                  	<textarea type="text" rows="6" name="reason" placeholder="请输入申请理由" class="form-control radius0"></textarea>
	                             	</div>
	                          	</div>
	                           	
	                           	<div  class="row margin-top-30 education-upload">
	                          		<div class="col-md-2 col-sm-2 col-xs-12">
	                             		<span class="right-10">学历证明<span class="font-red-flamingo">*</span>：</span>
	                                </div>
	                                <div class="col-md-10 col-sm-10 col-xs-12">
	                                	<div class="row upload-list">
	                                		
	                                	</div>
	                                	<div class="row">
	                                		<span class="btn file-upload fileinput-button">
                                            	<span> 上传文件 </span>
                                            	<input type="file" name="file" data-url="/pro_user/upload" multiple>
                                       	 	</span>
                                       	 	<label class="control-label font-red-flamingo file-desc">
												提供有效的学历证明认证信息，支持格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
											</label>
                                        </div>
                                        <div class="progress">
										    <div class="bar"></div>
										</div>
	                              	</div>
	                        	</div>
	                        	
	                        	<div  class="row margin-top-30 other-upload">
	                          		<div class="col-md-2 col-sm-2 col-xs-12">
	                             		<span class="right-10">其他附件：</span>
	                                </div>
	                                <div class="col-md-10 col-sm-10 col-xs-12">
	                                	<div class="row upload-list">
	                                		
	                                	</div>
	                                	<div class="row">
	                                		<span class="btn file-upload fileinput-button">
                                            	<span> 上传文件 </span>
                                            	<input type="file" name="file" data-url="/pro_user/upload" multiple>
                                       	 	</span>
                                       	 	<label class="control-label font-red-flamingo file-desc">
												上传更多相关资料能更好的帮助您通过申请，支持格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
											</label> 
                                        </div>
                                        <div class="progress">
										    <div class="bar"></div>
										</div>
	                              	</div>
	                        	</div>
	                        	
	                        	<div  class="row margin-top-30">
	                          		<div class="col-md-2 col-sm-2 col-xs-12">
	                                </div>
	                                <div class="col-md-10 col-sm-10 col-xs-12">
	                                	<input type="checkbox" name="pro-service" checked>
	                                	<a href="/agreement/pro.jsp" target ="_blank" style="margin-left:10px">我已阅读并接受《服务条款》</a>
	                              	</div>
	                        	</div>
	                           	
                        	</div>
                        	<div class="row margin-top-30">
		                       	<button type="button" class="btn submit-btn">提交申请</button>
		                  	</div>
                        	
                        </div>
                    </div>
                    <!-- END EXAMPLE TABLE PORTLET-->
				</div>
			</div>
			<!-- END PAGE BASE CONTENT -->

		</div>
		<!-- END CONTENT BODY -->
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<script id="upload-item-tmpl" type="html/x-jsrender">
<div class="item row">
	<div class="col-md-5 col-sm-5 col-xs-5">
		<a href="{{:saveFileName}}" class="title" data-id="{{:saveFileName}}">{{:fileName}}</a>
	</div>
	<div class="col-md-5 col-sm-5 col-xs-5">
		<a href="javascript:void(0);" class="glyphicon glyphicon-remove delete"></a>
	</div>
</div>
</script>

<script id="pay-tmpl" type="html/x-jsrender">
<div class="pay row">
	<div class="row">
		<span class="glyphicon glyphicon-remove-sign wclose"></span>
	</div>
	<div class="row title">
		<span>温馨提示，提交申请成功！</span>
	</div>
	<div class="row">
		<div  class="line"></div>
	</div>
	<div class="row info">
		<span class="desc">您申请的大牛专业领域为：<span class="active">{{:proName}}</span>，Anyonehelps 将在48小时内对您提交的资料进行审核，如需帮助，您可以拨打专线电话<span class="active">(626) 662-2748</span>，或者发送邮件至<span class="active">help@anyonehelps.com</span>，更多信息请点击《<a target="_blank" href="/agreement/pro.jsp"><span class="active">Anyonehelps申请专业大牛服务说明</span></a>》，祝好！</span>
	</div>
	<div class="row">
		<button type="button" class="ok">确定</button>
	</div>
</div>
</script>

<script id="pro-tmpl" type="html/x-jsrender">
{{if type==""}}
	<option value="">请选择领域</option>
{{else type=="1"}}
	<option value="">请选择领域</option>
	<option value="1">哲学</option>
	<option value="2">逻辑学</option>
	<option value="3">伦理学</option>
	<option value="4">美学</option>
	<option value="5">宗教学</option>
	<option value="6">法学</option>
	<option value="7">政治学</option>
	<option value="8">国际政治学</option>
	<option value="9">国际关系学</option>
	<option value="10">外交学</option>
	<option value="11">社会学</option>
	<option value="12">人口学</option>
	<option value="13">人类学</option>
	<option value="14">教育学</option>
	<option value="15">心理学</option>
	<option value="16">应用心理学</option>
	<option value="17">文学</option>
	<option value="18">新闻学</option>
	<option value="19">传播学</option>
	<option value="20">艺术学</option>
	<option value="21">音乐学</option>
	<option value="22">美术学</option>
	<option value="23">设计艺术学</option>
	<option value="24">戏剧学</option>
	<option value="25">电影学</option>
	<option value="26">广播电视艺术学</option>
	<option value="27">舞蹈学</option>
	<option value="28">图书馆学</option>
	<option value="29">历史学</option>
	<option value="59">历史地理学</option>
	<option value="60">英语</option>
	<option value="61">法语</option>
	<option value="62">西班牙语</option>
	<option value="63">德语</option>
	<option value="64">中文</option>
	<option value="65">数学</option>
	<option value="66">应用数学</option>
	<option value="67">运筹学与控制论</option>
	<option value="68">物理学</option>
	<option value="69">化学</option>
	<option value="70">天文学</option>
	<option value="71">地理学</option>
	<option value="72">大气科学</option>
	<option value="73">气象学</option>
	<option value="74">海洋科学</option>
	<option value="75">地质学</option>
	<option value="76">生物学</option>
	<option value="77">植物学</option>
	<option value="78">动物学</option>
	<option value="79">生理学</option>
	<option value="80">生态学</option>
	<option value="81">工程学</option>
	<option value="82">机械工程</option>
	<option value="83">工业与系统工程</option>
	<option value="84">材料科学与工程</option>
	<option value="85">电气工程</option>
	<option value="86">计算机科学与技术</option>
	<option value="87">建筑学</option>
	<option value="88">土木工程</option>
	<option value="89">应用化学</option>
	<option value="90">航空宇航科学与技术</option>
	<option value="91">农业工程</option>
	<option value="92">环境科学</option>
	<option value="93">环境工程</option>
	<option value="94">农学</option>
	<option value="95">畜牧学</option>
	<option value="96">林学</option>
	<option value="97">水产学</option>
	<option value="98">临床医学</option>
	<option value="99">护理学</option>
	<option value="100">中医学</option>
	<option value="101">药学</option>
	<option value="102">药理学</option>
	<option value="103">管理科学与工程</option>
	<option value="104">工商管理学</option>
	<option value="105">会计学</option>
	<option value="106">行政管理学</option>
	<option value="107">经济学</option>
	<option value="108">应用经济学</option>
	<option value="109">财政学</option>
	<option value="110">金融学</option>
	<option value="111">产业经济学</option>
	<option value="112">国际贸易学</option>
	<option value="113">统计学</option>
	<option value="114">数量经济学</option>
{{else type=="2"}}
	<option value="">请选择领域</option>
	<option value="30">文章润色</option>
	<option value="31">论文编辑</option>
	<option value="32">文献转述</option>
	<option value="33">扩写</option>
	<option value="34">重复率查询</option>
	<option value="35">课后辅导</option>
	<option value="36">PS/RL/CV写作和编辑</option>
	<option value="37">简历润色</option>
	<option value="38">作业检查</option>
	<option value="39">研究助理</option>
	<option value="40">资料查询</option>
	<option value="41">英文翻译</option>
	<option value="42">论文指导</option>
	<option value="43">学校申请</option>
	<option value="44">教育咨询</option>
	<option value="45">建筑与装修</option>
	<option value="46">室内设计</option>
	<option value="47">家具设计</option>
	<option value="48">电脑硬件</option>
	<option value="49">电脑软件设计</option>
	<option value="50">网站开发</option>
	<option value="51">数据库/数据仓库</option>
	<option value="52">App开发</option>
	<option value="53">商务智能</option>
	<option value="54">UI 设计</option>
	<option value="55">企业VI设计</option>
	<option value="56">艺术设计</option>
	<option value="57">人力资源管理</option>
	<option value="58">行政管理</option>
	<option value="115">仓库管理</option>
	<option value="116">运营管理</option>
	<option value="117">工业设计</option>
	<option value="118">销售</option>
	<option value="119">会计</option>
	<option value="120">金融分析</option>
	<option value="121">股票与基金</option>
	<option value="122">保险</option>
	<option value="123">商业咨询</option>
	<option value="124">咨询服务</option>
	<option value="125">项目管理</option>
	<option value="126">运输</option>
	<option value="127">电视广播与主持</option>
	<option value="128">专题写作</option>
	<option value="129">法律咨询</option>
	<option value="130">模特</option>
	<option value="131">摄影</option>
	<option value="132">厨师</option>
	<option value="133">旅行导游</option>
	<option value="134">翻译</option>
	<option value="135">司机</option>
	<option value="136">驾驶培训</option>
	<option value="137">编剧</option>
	<option value="138">导演</option>
	<option value="139">摄像</option>	
	<option value="140">演艺</option>
	<option value="141">电商</option>
	<option value="142">社交平台管理</option>
	<option value="143">餐厅服务</option>
	<option value="144">侍酒师</option>
	<option value="145">兼职小时工</option>
{{else type=="3"}}
	<option value="146">旅游</option>
	<option value="147">红酒</option>
	<option value="148">高尔夫旅游</option>
	<option value="149">汽车达人</option>
	<option value="150">租房买房</option>
	<option value="151">健身教练</option>
	<option value="152">足球教练</option>
	<option value="153">网球教练</option>
	<option value="154">篮球教练</option>
	<option value="155">橄榄球教练</option>
	<option value="156">高尔夫教练</option>
	<option value="157">潜水教练</option>
	<option value="158">滑雪教练</option>
	<option value="159">营养师</option>
	<option value="160">电脑维修</option>
	<option value="161">家电维修</option>
	<option value="162">手机保养与维修</option>
	<option value="163">舞蹈教学</option>
	<option value="164">家政服务</option>
	<option value="165">保姆</option>
	<option value="166">老人陪护</option>
	<option value="167">下水道维护</option>
	<option value="168">上门杀虫</option>
	<option value="169">上门洗车</option>
	<option value="170">太阳能</option>
	<option value="171">园丁</option>
	<option value="172">花艺</option>
	<option value="173">按摩</option>
	<option value="174">足疗</option>
	<option value="175">美甲</option>
	<option value="176">美容</option>
	<option value="177">化妆</option>
	<option value="178">美发</option>
	<option value="179">快递</option>
	<option value="180">搬家</option>
	<option value="181">大件搬家</option>
	<option value="182">外卖</option>
	<option value="183">娱乐组局</option>
	<option value="184">游戏大神</option>
{{/if}}
</script>

<%@ include file="/include/footer.jsp"%>
<!-- BEGIN GLOBAL LEVEL PLUGINS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>

<script src="/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js " type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>
<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/pages/scripts/pro-user-become.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
