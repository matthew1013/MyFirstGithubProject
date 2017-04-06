<%@ include file="/include/header.jsp"%>
<!-- BEGIN PAGE LEVEL STYLES -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>
<link href="/assets/pages/css/profile.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/account-skill.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL STYLES -->

<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"> </div>
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
                <div class="col-xs-12">
                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <span class="caption-subject uppercase test">技能管理</span>
                                <div class="caption-desc font-grey-cascade">
                                 	如果您拥有以下的技能，请点击。
                                </div>
                            </div>
                        </div>
                        <div class="portlet-body">
                        	<div class="row specialty-my">
                    			<p class="hd">已添加技能：</p>
                    			<div class="bd">
                        			<div class="scroll">
	                       	 			
                       	 			</div>
                    			</div>
                			</div>
                        	
                   			<!-- BEGIN 技能列表 -->
                            <div class="row specialty-list"> 
                            </div>
                            <!-- END 列表 -->
                            <div class="margin-top-30">
	                           	<span class="desc">认证技能记录</span>
	                            <table class="table table-striped table-bordered table-hover table-checkable order-column auth-record">
	                                <thead>
	                                    <tr>
	                                        <th>技能 </th>
	                                        <th>分类 </th>
	                                        <th>认证凭证 </th>
	                                        <th>状态</th>
	                                        <th>认证说明</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                </tbody>
	                                
	                            </table>
                            </div>
                        
                        	<!-- BEGIN FORM-->
                        	<form action="#" class="form-horizontal margin-top-30" id="auth-specialty-form">
                                <span class="desc">提交技能认证</span>
                                <div class="form-body">
                                    <div class="row">
                                        <label class="col-md-2 col-xs-2 control-label"  style="text-align: right;">技能:</label>
                                        <div class="col-md-4 col-xs-8">
                                            <select name="auth-specialty" class="form-control radius0">
                                              	<option value="">请选择认证技能</option>
                                            </select>
                                        </div>

                                    </div>
                                    
                                    <div class="row auth-state">
                                    	<label class="col-md-2 control-label"></label>
                                    	 <div class="col-md-10">
                                            <span class="font-red-flamingo"></span>
                                        </div>
                                    </div>
									
                                    <div class="row" id="content-div">
                                        <label class="col-md-2">
                                        	<span style="float:right;">技能描述：</span>
                                        </label>
                                        <div class="col-md-9">
                                            <textarea type="text" name="content" rows="6" placeholder="请输入技能描述..." class="form-control radius"></textarea>
                                        </div>
                                    </div>
                                    <div class="row enclosure-div">
                                    	<div class="col-md-offset-2 col-md-10">
		                                	<div class="row enclosure-list">
												
											</div>
		                                </div>
                                    	
                                        <div class="col-md-offset-2 col-md-10">
		                                	<span class="btn enclosure-upload fileinput-button">
	                                     		<span>添加凭证</span>
	                                       		<input type="file" name="files[]" data-url="/specialty/enclosure_upload" multiple="">
	                                   		</span>
	                                   		<span class="font-red-flamingo enclosure-format">最大能上传5个附件，格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar</span>
	                                   	</div>
	                               	</div>
									<!-- <div class="form-group last" id="alias-div">
                                        <label class="control-label col-md-1" style="margin-right:20px;">具体的技能:</label>
                                        <div class="col-md-4">
                                            <input type="text" id="auth-alias" class="form-control radius0"/>
                                        </div>
                                    </div> -->
                                </div>
                                <div class="form-actions" id="submit-div">
                                	<div class="row">
                                		<div class="col-md-offset-2 col-md-6">
                                			<span class="font-red-flamingo">注意:提交认证，需要扣费$50认证费用</span>
                                		</div>
                                	</div>
                                    <div class="row">
                                        <div class="col-md-offset-2 col-md-10">
                                            <button type="submit" class="btn btn-sumbit">提交</button>
                                            <button type="reset" class="btn btn-sumbit">取消</button>
                                            <input type="checkbox" checked="checked" name="isAgree"/>
                                            <a href="/agreement/skill.jsp" target="_blank">同意技能认证使用条款 </a>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <!-- END FORM-->
                  			
                        </div>
                    </div>
                </div>

            </div>


            <!-- END PAGE BASE CONTENT -->

        </div>
        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<script id="auth-item-tmpl" type="html/x-jsrender">
{{for dataList}}
	{{if state==1||state==2||state==3}}
	<tr class='odd gradeX item-specialty'>
		<td>
			{{if type==0}}
				{{:specialty.name/}}
			{{else type==1}}
				{{:name}}
			{{/if}}
		</td>
    	<td>
			{{if type==0}}
				{{specialtyTypeFormat specialtyTypeId/}}
			{{else type==1}}
				自定义
			{{/if}}
		</td>
    	<td>
			{{if enclosure1}}<a href="{{:enclosure1}}" target="_blank">{{:enclosure1Name}}<a/><br/>{{/if}}
			{{if enclosure2}}<a href="{{:enclosure2}}" target="_blank">{{:enclosure2Name}}<a/><br/>{{/if}}
			{{if enclosure3}}<a href="{{:enclosure3}}" target="_blank">{{:enclosure3Name}}<a/><br/>{{/if}}
			{{if enclosure4}}<a href="{{:enclosure4}}" target="_blank">{{:enclosure4Name}}<a/><br/>{{/if}}
			{{if enclosure5}}<a href="{{:enclosure5}}" target="_blank">{{:enclosure5Name}}<a/><br/>{{/if}}
		</td>
		<td>{{specialtyStateFormat state/}}</td>
		<td>
			{{if remark}}
				<a data-remark="{{:remark}}" href="javascript:;" class="show-remark"><span>查看点评</span></a>
			{{else}}
				- 
			{{/if}}
		</td>
		
	</tr>
{{/if}}
{{/for}}
</script>

<script id="custom-auth-item-tmpl" type="html/x-jsrender">
{{for dataList}}
	{{if state==1||state==2||state==3}}
	<tr class='odd gradeX item-specialty'>
		<td>{{:name/}}</td>
    	<td>自定义</td>
    	<td>
			{{if enclosure1}}<a href="{{:enclosure1}}" target="_blank">{{:enclosure1Name}}<a/><br/>{{/if}}
			{{if enclosure2}}<a href="{{:enclosure2}}" target="_blank">{{:enclosure2Name}}<a/><br/>{{/if}}
			{{if enclosure3}}<a href="{{:enclosure3}}" target="_blank">{{:enclosure3Name}}<a/><br/>{{/if}}
			{{if enclosure4}}<a href="{{:enclosure4}}" target="_blank">{{:enclosure4Name}}<a/><br/>{{/if}}
			{{if enclosure5}}<a href="{{:enclosure5}}" target="_blank">{{:enclosure5Name}}<a/><br/>{{/if}}
		</td>
		<td>{{:remark}}</td>
		<td>{{specialtyStateFormat state/}}</td>
		<td>{{:authDate}}</td>
	</tr>
	{{/if}}
{{/for}}
</script>

<script type="html/x-jsrender" id="specialty-my-item-tmpl">
{{if type==0}}
<span class="special-label" data-type="{{:type}}" data-id="{{:id}}" data-specialtyid="{{:specialtyId}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}" title="{{:name}}">
	<em></em>
	<b>{{:name}}</b>
	<i class="delete"></i>
</span>
{{else type==1}}
<span class="special-label custom" data-type="{{:type}}"  data-id="{{:id}}" data-name="{{:name}}" title="{{:name}}">
	<em></em>
	<b>{{:name}}</b>
	<i  class="delete"></i>
</span>
{{/if}}
</script>

<script type="html/x-jsrender" id="specialty-my-tmpl">
{{for dataList}}
{{if type==0}}
	<span class="special-label" data-type="{{:type}}" data-id="{{:id}}" data-specialtyid="{{:specialtyId}}"  data-typeid="{{:specialty.specialtyTypeId}}" data-name="{{:specialty.name}}" title="{{:specialty.name}}">
		<em></em>
		<b>{{:specialty.name}}</b>
		{{if state==2}}
			<i class="glyphicon glyphicon-saved auth-pass" title="认证通过"></i>
		{{else}}
			<i class="delete"></i>
		{{/if}}	
	</span>
{{else type==1}}
<span class="special-label" data-type="{{:type}}" data-id="{{:id}}" data-name="{{:name}}" title="{{:name}}">
	<em></em>
	<b>{{:name}}</b>
	<i class="delete"></i>
</span>
{{/if}}
{{/for}}
</script>
<script type="html/x-jsrender" id="specialty-tmpl">
    <div class="specialty-item">
    	<div class="row"> 
			<div  class="left desc">    
				技能选择
			</div>
			<div class="left">
				<ul class="nav nav-tabs">
					{{for dataList}}
            		<li {{if #index == 0 }} class="active" {{/if}}>
               		 	<a href="#tab_1_{{:#index}}" data-toggle="tab">{{:name}}</a>
                	</li>
					{{/for}}

           		</ul>
			</div>
		</div>
		
		<div class="row">
			<div class="tab-content">
				{{for dataList}}
	         	<div class="tab-pane {{if #index==0}}active{{/if}}" id="tab_1_{{:#index}}">
					{{if #index==0}}
					<div class ="row margin-top-10">
						<div class="left">
							<span>文科：</span>
						</div>
						<div class="middle hide">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right">
							{{for specialtys}} 
							{{if flag==="1"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>
				
					<div class ="row margin-top-10">
						<div class="left">
							<span>理科：</span>
						</div>
						<div class="middle">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right hide">
							{{for specialtys}} 
							{{if flag==="2"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>

					<div class ="row margin-top-10">
						<div class="left">
							<span>工程学：</span>
						</div>
						<div class="middle">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right hide">
							{{for specialtys}} 
							{{if flag==="3"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>

					<div class ="row margin-top-10">
						<div class="left">
							<span>医学：</span>
						</div>
						<div class="middle">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right hide">
							{{for specialtys}} 
							{{if flag==="4"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>

					<div class ="row margin-top-10">
						<div class="left">
							<span>管理学：</span>
						</div>
						<div class="middle">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right hide">
							{{for specialtys}} 
							{{if flag==="5"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>
					<div class ="row margin-top-10">
						<div class="left">
							<span>经济学：</span>
						</div>
						<div class="middle">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right hide">
							{{for specialtys}} 
							{{if flag==="6"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>
					{{else #index==1}}
					<div class ="row margin-top-10">
						<div class="left">
							<span>学习类：</span>
						</div>
						<div class="middle hide">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right">
							{{for specialtys}} 
							{{if flag==="7"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>
					<div class ="row margin-top-10">
						<div class="left">
							<span>工作类：</span>
						</div>
						<div class="middle">
							<a href="javascript:;" class="zoom">
								<i class="iconfont icon-add"></i>
								<span>展开</span>
							</a>
						</div>
						<div class="right hide">
							{{for specialtys}} 
							{{if flag==="8"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>
					{{else #index==2}}
					<div class ="row margin-top-10">
						<div class="left">
							<span>生活娱乐：</span>
						</div>
						<div class="right">
							{{for specialtys}} 
							{{if flag==="9"}}
							<a href="javascript:;" class="special-label" data-id="{{:id}}" data-typeid="{{:specialtyTypeId}}" data-name="{{:name}}">
								<em></em>
								<span>{{:name}}</span>
							</a>
							{{/if}}
							{{/for}}
						</div>
					</div>
					{{/if}}
				</div>
				{{/for}}
			</div>
		</div>
    </div>

	<div class="specialty-custom">
    	<!-- 自定义技能 -->
		<div class ="row">
			<div class="left">
				<span class="desc">自定义技能：</span>
			</div>
			<div class="right">
				<input type="text" maxlength="50" class="form-control specialty-custom-add" placeholder="请输入自定义技能名称"/>
				<button type="buttom" class="btn specialty-custom-submit">添加</button>
					
			</div>
		</div>
    </div>
	
</script>

<!-- delete -->
<script id="specialty-custom-item" type="html/x-jsrender">
<a href="javascript:;" class="special-label active" data-id="{{:id}}" data-name="{{:name}}">
	<em></em>
	<span>{{:name}}</span>
</a>
</script>
<!-- delete -->
<script type="html/x-jsrender" id="specialty-custom-my-item">
<span class="special-custom-label" data-id="{{:id}}" data-name="{{:name}}" title="{{:name}}">
	<em></em>
	<b>{{:name}}</b>
	<i  class="delete"></i>
</span>
</script>

<script type="html/x-jsrender" id="specialty-custom-my-tmpl">

<span class="special-custom-label" data-id="{{:id}}" data-name="{{:name}}" title="{{:name}}">
	<em></em>
	<b>{{:name}}</b>
	{{if state==2}}
		<i class="glyphicon glyphicon-saved auth-pass" title="认证通过"></i>
	{{else}}
		<i class="delete"></i>
	{{/if}}
</span>
</script>

<!-- delete -->
<script id="specialty-type-tmpl" type="html/x-jsrender">
{{for dataList}}
	<option value="{{:id}}">{{:name}}</option>
{{/for}}
	<option value="0">自定义</option>
</script>

<!-- 认证技能下拉框 -->
<script id="specialty-item-tmpl" type="html/x-jsrender">
<option value="">请选择认证技能</option>
{{for dataList}}
	{{if type==0}}
		<option value="{{:id}}">{{:specialty.name}}</option>
	{{else type==1}}	
		<option value="{{:id}}">{{:name}}</option>
	{{/if}}
{{/for}}
</script>

<!-- <script id="specialty-item-tmpl" type="html/x-jsrender">
<option value="">请选择认证技能</option>
{{for dataList}}
	{{if type==1}}
		{{if #parent.parent.data.stId == id}}
		{{for specialtys}}
		<option value="{{:id}}">{{:name}}</option>
		{{/for}}	
	{{if type==1}}	
	{{/if}}
	{{/if}}
{{/for}}
</script>
 -->

<script id="specialty-custom-item-tmpl" type="html/x-jsrender">
<option value="">请选择认证技能</option>
{{if dataList.length>0}}
	{{for dataList}}
	<option value="{{:id}}" class="custom">{{:name}}</option>
	{{/for}}
{{else}}
	<option value="-1" class="custom">你还没增加自定义技能，请增加再认证</option>
{{/if}}
</script>


<script id="enclosure-item-tmpl" type="html/x-jsrender">
<div class="enclosure-item row">
	<div class="col-md-5 col-sm-5 col-xs-5">
		<a href="{{:saveFileName}}" class="title" data-id="{{:saveFileName}}">{{:fileName}}</a>
	</div>
	<div class="col-md-5 col-sm-5 col-xs-5">
		<a href="javascript:void(0);" class="glyphicon glyphicon-remove enclosure-delete"></a>
	</div>
</div>
</script>


<%@ include file="/include/footer.jsp"%>
<!-- BEGIN GLOBAL LEVEL SCRIPTS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js " type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>

<!-- END GLOBAL LEVEL SCRIPTS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/pages/scripts/account-skill.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->