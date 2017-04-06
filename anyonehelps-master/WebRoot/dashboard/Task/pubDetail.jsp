<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-star-rating/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
<link href="/assets/pages/css/task-pub-detail.css" rel="stylesheet" type="text/css" />

<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/icheck-1.x/skins/flat/blue.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
.task-select-dlg .ui-dialog-content, .task-evaluate-dlg .ui-dialog-content, .task-arbitration-dlg .ui-dialog-content, .task-pay-dlg .ui-dialog-content{
	padding:0px;
}
</style>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->

<input type="hidden" value="${param.id }" name="taskId"/>

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
                                <span class="caption-subject uppercase"> 已发布任务 </span>
                                <span class="caption-helper">蓝色为公开项目，灰色为非公开项目</span>
                            </div>
                        </div>
                        <div class="portlet-body">
                        	<div class="task-list row">
                        		
                        	</div> 
                           
                        </div>
                    </div>
                    <!-- END EXAMPLE TABLE PORTLET -->
				</div>
			</div>
            <!-- END PAGE BASE CONTENT -->

        </div>
        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->
<script id="arbitration-upload-file" type="html/x-jsrender">
	<div class="upload-file-item row" data-id="{{:saveFileName}}" data-filename="{{:fileName}}">
		<div class="col-md-7 col-sm-7 col-xs-7">
			<a href="{{:saveFileName}}" class="filename" data-id="{{:saveFileName}}">{{:fileName}}</a>
		</div>
		<div class="col-md-5 col-sm-5 col-xs-5">
			<a href="javascript:void(0);" class="glyphicon glyphicon-remove upload-file-delete"></a>
		</div>
	</div>	
</script>

<script type="html/x-jsrender" id="task-arbitration-dlg">
	<div class="task-arbitration-list" data-title="{{:task.title}}"  data-taskid="{{:task.id}}">
    	<div class="row"> 
			<div class="title">
				<span>仲裁</span>
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		
		<div class="row user-info" data-id="{{:task.id}}">
				{{for task.receiveDemands}}
					{{if state==1&&!arbitration}}
					<div class="arbitration-item" data-userid={{:user.id}} data-rdid={{:id}}>
						<div class="left-div">
							<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
						</div>
						<div class="left-div">
							<div class="row">
								<span class="username">{{:user.nickName}}</span>
							</div>
							<div class="row percent">
								<span>仲裁付款比例:</span>
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="100" checked>100%
								<!-- <input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="90">90% -->
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="60">60%
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="30">30%
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="0">0%
							</div>
							<div class="row">
								<textarea type="text" rows="3" placeholder="请清楚描述不全额付款的理由" class="form-control radius0 arbitration"></textarea>
							</div>
							<div class="row upload-file-list">
	                        	
	                        </div>
							<div class="row">
	                        	<span class="btn quote-upload fileinput-button">
                                <span> 上传文件 </span>
                                	<input type="file" name="files[]" data-url="/arbitration/upload_file" multiple>
                               	</span>
                                <label class="control-label font-red-flamingo quote-format" style="margin-left:10px;margin-top:20px;font-size: 10px;">
									限5个，支持格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
								</label>
                           	</div>
							<div>
								<div class="progress" style="width:140px;border-radius: 0px;">
									<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
								</div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					{{else arbitration}}
					<div class="arbitration-item" data-isarbitration="1" data-userid="{{:user.id}}" data-rdid="{{:id}}">
						<div class="left-div">
							<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
						</div>
						<div class="left-div">
							<div class="row">
								<span class="username">{{:user.nickName}}</span>
							</div>
							<div class="row percent">
								<span>仲裁付款比例:</span> 
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="100" {{setOptionChecked 100 arbitration.percentage/}}>100% 
								<!-- <input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="90" {{setOptionChecked 90 arbitration.percentage/}}>90% -->
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="60" {{setOptionChecked 60 arbitration.percentage/}}>60%
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="30" {{setOptionChecked 30 arbitration.percentage/}}>30%
								<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="0" {{setOptionChecked 0 arbitration.percentage/}}>0%
							</div>
							<div class="row">
								<textarea type="text" rows="3" readonly="readonly" placeholder="" class="form-control radius0 arbitration">{{:arbitration.reason}}</textarea>
							</div>
							<div class="row upload-file-list">
	                        	<!-- <div class="upload-file-item row" data-id="{{:saveFileName}}">
									<div class="col-md-7 col-sm-7 col-xs-7">
									<a href="{{:saveFileName}}" class="filename" data-id="{{:saveFileName}}">{{:saveFileName}}</a>
									</div>
								</div>	-->
	                        </div>
							{{if arbitration.state == 0}}
							<div class="row">
								<button type="button" class="btn delete-arbitration" data-id="{{:arbitration.id}}">取消仲裁</button>
							</div>
							{{/if}}
						</div>
					</div>
					<div class="clearfix"></div>
					{{/if}}
				{{/for}}
		</div>
		<div class="row operate"> 
			<button type="button" class="btn task-arbitration-ok">提交</button>
			<button type="button" class="btn task-arbitration-cancel">取消</button>
		</div>
    </div>
</script>


<script type="html/x-jsrender" id="task-pay-reason">
	<div class="row">
		<textarea type="text" rows="3" placeholder="请输入您的付款理由" class="form-control radius0 pay-reason"></textarea>
	</div>
	<div class="row upload-file-list">
	</div>
	<div class="row">
		<span class="btn quote-upload fileinput-button">
        	<span> 上传文件 </span>
        	<input type="file" name="files[]" data-url="/arbitration/upload_file" multiple>
   		</span>
      	<label class="control-label font-red-flamingo quote-format" style="margin-left:10px;margin-top:20px;font-size: 10px;">
			限5个，支持格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
		</label>
    </div>
	<div>
		<div class="progress" style="width:140px;border-radius: 0px;">
			<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
		</div>
	</div>
</script>

<!--haokun added 2017/03/14 start 做了个对应发单人拒绝按钮的弹出框-->
<script type="html/x-jsrender" id="task-refusepay-dlg">
    <div class="task-pay-list" data-title="{{:task.title}}" data-taskid="{{:task.id}}">
	    <div class="row">
		    <div class="title">
			     <span>拒绝付款</span>
				 <a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		<div class="row user-info" data-id="{{:task.id}}">
			{{for task.receiveDemands}}
				{{if state == 1 && #parent.parent.data.task.state==5}}
				 <div class="pay-item" data-userid={{:user.id}} data-rdid={{:id}}>
					<div class="left-div">
						<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
					</div>
					<div class="left-div">
						<div class="row">
							<span class="username">{{:user.nickName}}</span>
						</div>
						<div class="row reason">
						     <!---->						
						    <div class="row">
	                         	<textarea type="text" rows="3" placeholder="请输入您拒绝付款的理由" class="form-control radius0 pay-reason"></textarea>
	                        </div>
	                        <div class="row upload-file-list">
	                        </div>
	                        <div class="row">
		                        <span class="btn quote-upload fileinput-button">
        	                        <span> 上传文件 </span>
        	                        <input type="file" name="files[]" data-url="/arbitration/upload_file" multiple>
   		                        </span>
      	                        <label class="control-label font-red-flamingo quote-format" style="margin-left:10px;margin-top:20px;font-size: 10px;">
			                         限5个，支持格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
		                       </label>
                            </div>
	                        <div>
		                        <div class="progress" style="width:140px;border-radius: 0px;">
			                       <div class="bar" style="width: 0%;height: 18px;background: green;"></div>
		                        </div>
	                        </div>
						<!----->
						</div> 
					</div>
				</div>
				<div class="clearfix"></div>     				
		    	{{/if}}
			{{/for}}
		</div>
		<div class="row operate"> 
			<button type="button" class="btn task-refusepay-ok">提交</button>
			<button type="button" class="btn task-refusepay-cancel">取消</button>
		</div>
	</div>
</script>
<!--haokun added 2017/03/14 start 做了个对应发单人拒绝按钮的弹出框-->

<script type="html/x-jsrender" id="task-pay-dlg">
	<div class="task-pay-list" data-title="{{:task.title}}"  data-taskid="{{:task.id}}">
    	<div class="row"> 
			<div class="title">
				<span>{{if task.state==7}}仲裁{{else}}付款{{/if}}</span>
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		
		<div class="row user-info" data-id="{{:task.id}}">
			{{for task.receiveDemands}}
				{{if state==1&& #parent.parent.data.task.state==5}}
				<div class="pay-item" data-userid={{:user.id}} data-rdid={{:id}}>
					<div class="left-div">
						<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
					</div>
					<div class="left-div">
						<div class="row">
							<span class="username">{{:user.nickName}}</span>
						</div>
						<div class="row percent">
							<span>付款情况:付款总额\${{totalAmount amount #parent.parent.parent.data.task.da /}}, 付款比例:</span>
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="100" checked>100%
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="60">60%
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="30">30%
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" value="0">0%
						</div>
						<div class="row reason">
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				{{else state==1&& #parent.parent.data.task.state==6}}
				<div class="pay-item" data-ispay="1" data-userid="{{:user.id}}" data-rdid="{{:id}}">
					<div class="left-div">
						<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
					</div>
					<div class="left-div">
						<div class="row">
							<span class="username">{{:user.nickName}}</span>
						</div>
						<div class="row" style="margin-top: 20px;">
							<span>不是全额付款，需要等待对方确认</span>
						</div>
						<div class="row percent">
							<span>付款比例:</span> 
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="100" {{setOptionChecked 100 payPercent/}}>100% 
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="60" {{setOptionChecked 60 payPercent/}}>60%
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="30" {{setOptionChecked 30 payPercent/}}>30%
							<input type="radio" calss="percentage" name="percentage{{: #getIndex()}}" disabled value="0" {{setOptionChecked 0 payPercent/}}>0%
						</div>
						<div class="row">
							<textarea type="text" rows="3" readonly="readonly" placeholder="" class="form-control radius0 pay-reason">{{:payReason}}</textarea>
						</div>
						<div class="row upload-file-list">
	                       	{{if payReasonUrl1}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl1}}" data-filename="{{:payReasonUrl1Name}}">
								<a href="{{:payReasonUrl1}}" class="filename" data-id="{{:payReasonUrl1}}">{{:payReasonUrl1Name}}</a>
							</div>	
							{{/if}}
							{{if payReasonUrl2}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl2}}" data-filename="{{:payReasonUrl2Name}}">
								<a href="{{:payReasonUrl2}}" class="filename" data-id="{{:payReasonUrl2}}">{{:payReasonUrl2Name}}</a>
							</div>
							{{/if}}
							{{if payReasonUrl3}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl3}}" data-filename="{{:payReasonUrl3Name}}">
								<a href="{{:payReasonUrl3}}" class="filename" data-id="{{:payReasonUrl3}}">{{:payReasonUrl3Name}}</a>
							</div>
							{{/if}}
							{{if payReasonUrl4}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl4}}" data-filename="{{:payReasonUrl4Name}}">
								<a href="{{:payReasonUrl4}}" class="filename" data-id="{{:payReasonUrl4}}">{{:payReasonUrl4Name}}</a>
							</div>
							{{/if}}
							{{if payReasonUrl5}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl5}}" data-filename="{{:payReasonUrl5Name}}">
								<a href="{{:payReasonUrl5}}" class="filename" data-id="{{:payReasonUrl5}}">{{:payReasonUrl5Name}}</a>
							</div>
							{{/if}}
	                    </div>
					</div>
				</div>
				<div class="clearfix"></div>
				{{else state==1&& #parent.parent.data.task.state==7}}
				<div class="pay-item" data-ispay="1" data-userid="{{:user.id}}" data-rdid="{{:id}}">
					<div class="left-div">
						<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
					</div>
					<div class="left-div">
						<div class="row">
							<span class="username">{{:user.nickName}}</span>
						</div>
						<div class="row" style="margin-top: 20px;">
							<span>对方反对您的付款比例，并提出仲裁，平台将对此任务付款比例进行裁决</span>
						</div>
						<div class="row" style="margin-top: 20px;">
							<span>我提出的付款比例和理由:</span>
						</div>
						<div class="row percent">
							<span>付款比例:</span> 
							<input type="radio" calss="percentage" name="payPercent" disabled value="100" {{setOptionChecked 100 payPercent/}}>100%
							<input type="radio" calss="percentage" name="payPercent" disabled value="60" {{setOptionChecked 60 payPercent/}}>60%
							<input type="radio" calss="percentage" name="payPercent" disabled value="30" {{setOptionChecked 30 payPercent/}}>30%
							<input type="radio" calss="percentage" name="payPercent" disabled value="0" {{setOptionChecked 0 payPercent/}}>0%
						</div>
						<div class="row">
							<textarea type="text" rows="3" readonly="readonly" placeholder="" class="form-control radius0 pay-reason">{{:payReason}}</textarea>
						</div>
						<div class="row upload-file-list">
	                       	{{if payReasonUrl1}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl1}}" data-filename="{{:payReasonUrl1Name}}">
								<a href="{{:payReasonUrl1}}" class="filename" data-id="{{:payReasonUrl1}}">{{:payReasonUrl1Name}}</a>
							</div>	
							{{/if}}
							{{if payReasonUrl2}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl2}}" data-filename="{{:payReasonUrl2Name}}">
								<a href="{{:payReasonUrl2}}" class="filename" data-id="{{:payReasonUrl2}}">{{:payReasonUrl2Name}}</a>
							</div>
							{{/if}}
							{{if payReasonUrl3}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl3}}" data-filename="{{:payReasonUrl3Name}}">
								<a href="{{:payReasonUrl3}}" class="filename" data-id="{{:payReasonUrl3}}">{{:payReasonUrl3Name}}</a>
							</div>
							{{/if}}
							{{if payReasonUrl4}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl4}}" data-filename="{{:payReasonUrl4Name}}">
								<a href="{{:payReasonUrl4}}" class="filename" data-id="{{:payReasonUrl4}}">{{:payReasonUrl4Name}}</a>
							</div>
							{{/if}}
							{{if payReasonUrl5}}
							<div class="upload-file-item row" data-id="{{:payReasonUrl5}}" data-filename="{{:payReasonUrl5Name}}">
								<a href="{{:payReasonUrl5}}" class="filename" data-id="{{:payReasonUrl5}}">{{:payReasonUrl5Name}}</a>
							</div>
							{{/if}}
	                    </div>

						<div class="row" style="margin-top: 20px;">
							<span>接单人提出的付款比例和理由:</span>
						</div>
						<div class="row percent">
							<span>付款比例:</span> 
							<input type="radio" calss="percentage" name="refutePercent" disabled value="100" {{setOptionChecked 100 refutePercent/}}>100% 
							<input type="radio" calss="percentage" name="refutePercent" disabled value="60" {{setOptionChecked 60 refutePercent/}}>60%
							<input type="radio" calss="percentage" name="refutePercent" disabled value="30" {{setOptionChecked 30 refutePercent/}}>30%
							<input type="radio" calss="percentage" name="refutePercent" disabled value="0" {{setOptionChecked 0 refutePercent/}}>0%
						</div>
						<div class="row">
							<textarea type="text" rows="3" readonly="readonly" placeholder="" class="form-control radius0 pay-reason">{{:refuteReason}}</textarea>
						</div>
						<div class="row upload-file-list">
	                       	{{if refuteReasonUrl1}}
							<div class="upload-file-item row" data-id="{{:refuteReasonUrl1}}" data-filename="{{:refuteReasonUrl1Name}}">
								<a href="{{:refuteReasonUrl1}}" class="filename" data-id="{{:refuteReasonUrl1}}">{{:refuteReasonUrl1Name}}</a>
							</div>	
							{{/if}}
							{{if refuteReasonUrl2}}
							<div class="upload-file-item row" data-id="{{:refuteReasonUrl2}}" data-filename="{{:refuteReasonUrl2Name}}">
								<a href="{{:refuteReasonUrl2}}" class="filename" data-id="{{:refuteReasonUrl2}}">{{:refuteReasonUrl2Name}}</a>
							</div>
							{{/if}}
							{{if refuteReasonUrl3}}
							<div class="upload-file-item row" data-id="{{:refuteReasonUrl3}}" data-filename="{{:refuteReasonUrl3Name}}">
								<a href="{{:refuteReasonUrl3}}" class="filename" data-id="{{:refuteReasonUrl3}}">{{:refuteReasonUrl3Name}}</a>
							</div>
							{{/if}}
							{{if refuteReasonUrl4}}
							<div class="upload-file-item row" data-id="{{:refuteReasonUrl4}}" data-filename="{{:refuteReasonUrl4Name}}">
								<a href="{{:refuteReasonUrl4}}" class="filename" data-id="{{:refuteReasonUrl4}}">{{:refuteReasonUrl4Name}}</a>
							</div>
							{{/if}}
							{{if refuteReasonUrl5}}
							<div class="upload-file-item row" data-id="{{:refuteReasonUrl5}}" data-filename="{{:refuteReasonUrl5Name}}">
								<a href="{{:refuteReasonUrl5}}" class="filename" data-id="{{:refuteReasonUrl5}}">{{:refuteReasonUrl5Name}}</a>
							</div>
							{{/if}}
	                    </div>
						<div class="row">
							<button type="button" class="btn task-pay-agree" data-id="{{:demandId}}">同意对方付款要求</button>
							<span>如同意，任务将按对方提出的付款比例给对方支付报酬</span>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
				{{/if}}
			{{/for}}
		</div>
		<div class="row operate"> 
			<button type="button" class="btn task-pay-ok">提交</button>
			<button type="button" class="btn task-pay-cancel">取消</button>
		</div>
    </div>
</script>

<script type="html/x-jsrender" id="task-evaluate-dlg">
	<div class="task-evaluate-list" data-taskid="{{:task.id}}">
    	<div class="row"> 
			<div class="title">
				<span>评价</span>
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		<div class="row user-info" data-id="{{:task.id}}">
			{{for task.receiveDemands}}
				{{if state==1}}
					{{if evaluateState==0}}
					<div class="evaluate-item" data-userid={{:user.id}}>
						<div class="left-div">
							<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
						</div>
						<div class="left-div">
							<div class="row">
								<span class="username">{{:user.nickName}}</span>
							</div>
							<div class="row star">
								<span class="desc">诚信打分:</span>
								<input type="number" value="5" class="rating evaluate-star" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false"  data-isevaluate="0">
							</div>
							<div class="row star">
								<span class="desc">质量打分:</span>
								<input type="number" value="5" class="rating quality-star" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false"  data-isevaluate="0">
							</div>
							<div class="row star">
								<span class="desc">准时性打分:</span>
								<input type="number" value="5" class="rating punctual-star" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false"  data-isevaluate="0">
							</div>
							<div class="row">
								<textarea type="text" rows="4" placeholder="请输入您的评价" class="form-control radius0 evaluate"></textarea>
							</div>

						</div>
					</div>
					<div class="clearfix"></div>
					{{else evaluate}}
					<div class="evaluate-item" data-userid={{:user.id}}>
						<div class="left-div">
							<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-50" src="{{:user.picUrl}}"></a>
						</div>
						<div class="left-div">
							<div class="row">
								<span class="username">{{:user.nickName}}</span>
							</div>
							<div class="row star">
								<span class="desc">诚信打分:</span>
								<input type="number" value="{{:evaluate.evaluate}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true" data-isevaluate="1">
							</div>
							<div class="row star">
								<span class="desc">质量打分:</span>
								<input type="number" value="{{:evaluate.quality}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true" data-isevaluate="1">
							</div>
							<div class="row star">
								<span class="desc">准时性打分:</span>
								<input type="number" value="{{:evaluate.punctual}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true" data-isevaluate="1">
							</div>
							<div class="row">
								<textarea type="text" rows="4" placeholder="请输入您的评价" class="form-control radius0">{{:evaluate.description}}</textarea>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
					{{/if}}
				{{/if}}
			{{/for}}
		</div>
		{{if ~noEvaluate(task.receiveDemands)}}
		<div class="row operate"> 
			<button type="button" class="btn task-evaluate-ok">提交</button>
			<button type="button" class="btn task-evaluate-cancel">取消</button>
		</div>
		{{/if}}
    </div>
</script>

<script type="html/x-jsrender" id="user-select-dlg">
	<div class="user-select-list" data-bidcount="{{:task.bidCount}}"  data-taskid="{{:task.id}}">
    	<div class="row"> 
			<div class="title">
				<span>匹配</span>
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		<div class="row user-info">
			{{if task.receiveDemands.length>0}}
				{{for task.receiveDemands}}
				<div class="col-md-12 col-sm-12 col-xs-12 user">
					<div class="checkbox-div">
						<input type="radio" name="userCheck" data-rdid="{{:id}}" data-amount="{{:amount}}" data-id="{{:user.id}}" data-pic="{{:user.picUrl}}" {{if state==1||state==2}}checked{{/if}}> 
					</div>
					<div class="img-div">
						<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:user.id}}"><img class="img-circle img-40 {{if state==0||state==2}}opacity08{{/if}}" src="{{:user.picUrl}}"></a>
					</div>
					<div class="user-div">
						<div class="row">	
							<span class="username" title="{{:user.nickName}}">{{:user.nickName}}</span>
						</div>
						<div class="row">
							<span class="evaluate show-evaluate" data-userid="{{:user.id}}" data-evaluatecount="{{:user.evaluateCount}}" data-evaluatepublishcount="{{:user.evaluatePublishCount}}" data-honest="{{:user.honest}}" data-quality="{{:user.quality}}" data-punctual="{{:user.punctual}}" data-honestpublish="{{:user.honestPublish}}" data-align="bottom left">
								{{evaluateFormat user.evaluate user.evaluateCount user.evaluatePublish user.evaluatePublishCount/}}
							</span>
						
							<a href="/profile.jsp?userId={{base64Encode user.id/}}#tab_1_2" target="_blank"><span class="evaluate-count">（{{evaluateCountFormat user.evaluateCount user.evaluatePublishCount/}}）</a>
							{{if user.pro>0}}
							<div class="pro" style="border-radius: 10px;width: 30px;height: 12px;margin-left: 5px;float: left;text-align: center;">
								<a href="javascript:;" style="font-size: 12px;color: #ffffff;letter-spacing: 0;text-align: left;height: 12;background: #ed75b8;border-radius: 10px;padding: 1px 8px;line-height: 12px;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">Pro</a>
							</div>
							{{/if}}
						</div>
					</div>

					<div class="demand-info">
						<div class="row">	
							<span>当前报价:</span>
							{{if amount}}
							<span class="font-red-flamingo">\${{:amount}}</span>
							{{else}}
							<span class="font-red-flamingo">{{:#parent.parent.parent.data.task.amount}}</span>
							{{/if}}
							{{if finishDay}}
							<span class="finish-day">完成天数:{{:finishDay}}</span>
							{{/if}}
						</div>
						<div class="row">	
							<div style="float:left;width:30px;">
								<span class="readme">自述:</span>
							</div>
							<div style="float:left;">
								<span class="readme">{{:readme}}</span>
							</div>
						
						</div>
					</div>
				</div>
				{{/for}}
			{{else}}
				<div style="text-align: center;font-size: 22px;padding-top: 25%;"><span>Oops，暂时还没有人投标！</span></div>
			{{/if}}
		</div>
		<div class="row operate"> 
			<button type="button" class="btn user-select-ok">确定</button>
			<button type="button" class="btn user-select-cancel">取消</button>
		</div>
    </div>
</script>
<script type="html/x-jsrender" id="attach-item">
		<div class="attach-item">
			<div class="row attach-info">
				<div class="open">
					<i class="iconfont icon-add"></i>
				</div>
				<div class="attach-title">
					<span>附加任务</span>
				</div>
 				<div class="attach-expire-date">
					<span>截止时间：{{:attach.expireDate}}</span>
				</div>
 				<div class="attach-amount">
					{{amountFormat attach.amount/}}
				</div>
				<div class="attach-state">
					<a href="javascript:;" data-id={{:attach.id}} class="attach-delete"><i class="glyphicon glyphicon glyphicon-trash"></i></a>
				</div>
			</div>
			<div class="row content hide">
				{{:attach.content}}
			</div> 
			<div class="row achieve hide margin-top-10">
				<div class="achieve-tip">
					<span>成果要求：</span>
				</div>
				<div>
					<pre>{{:attach.achieve}}</pre>
				</div>
			</div>
		</div> 
		<div class="line margin-top-10"></div>
</script>
<script type="html/x-jsrender" id="attach-add-item">
<div class="attach-add-item">
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-12">
	    	<span class="desc">附加任务金额：</span>
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-12">
		 	<input type="number" maxlength="10" min="0" step="1" class="form-control radius0 amount" placeholder="请输入附加任务金额">
	   	</div>
	</div>
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-12">
	    	<span class="desc">截止日期：</span>
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-12">
		 	<input type="text" placeholder="格式：yyyy-mm-dd hh:mm:ss" class="form-control radius0 expire-time">
	   	</div>
	</div>
	<div class="row content">
		<div class="col-md-2 col-sm-2 col-xs-12">
	    	<span class="desc">任务描述：</span>
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-12">
		 	<div class="attach-add-content"></div>
	   	</div>
	</div>
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-12">
	    	<span class="desc">成果要求<span class="font-red-flamingo">*</span>：</span>
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-12">
		 	<textarea type="text" rows="6" style="border-radius: 0px;" placeholder="注意：请明确附件任务成果要求，如发生纠纷，平台将根据交易达成时所述要求进行仲裁。 一旦匹配后，将无法更改。&#13;&#10;如有多个成果要求，请按如下格式填写：&#13;&#10;1、成果要求1&#13;&#10;2、成果要求2" class="form-control achieve"></textarea>
	   	</div>
	</div>
	<div class="row">
		<div class="col-md-2 col-sm-2 col-xs-12">
	     	<span class="desc"></span>
	   	</div>
	    <div class="col-md-10 col-sm-10 col-xs-12">
		 	<button type="button" class="btn attach-add-submit">提交</button>
			<button type="button" class="btn attach-add-cancel">取消</button>
	   </div>
	</div>
</div> 
</script>

<script type="html/x-jsrender" id="task-item-tmpl">

<div class="task-item" data-open="{{:task.open}}"  data-id="{{:task.id}}"> 
	<div class="row">
			<div class="title {{if task.open==0}}private{{/if}}">
   				<a href="/task/detail.jsp?id={{base64Encode task.id/}}" target="_blank">{{:task.title}}</a>
			</div>
			<div class="amount">
   				<span>
					{{amountFormat task.amount/}}
				</span>
			</div>
 	</div>
    <div class="row info">
     	<span>任务单号：</span><span>{{:task.id}}</span>
      	<span  class="expire-date">截止时间：</span><span>{{:task.expireDate}}</span>
  	</div>

	<div class="row content">
		{{:task.content}}
	</div>

	<div class="row achieve margin-top-10">
		<div class="achieve-tip">
			<span>成果要求：</span>
		</div>
		<div>
			<pre>{{:task.achieve}}</pre>
		</div>
	</div>

	<!--haokun added 2017/03/07 start 增加私密成果要求-->
	<div class="row secret-achieve hide margin-top-10">
	    <div class="secret-achieve-tip">
			<span>私密成果要求：</span>
	    </div>
		<div>
			<pre>{{:secretAchieve}}</pre>
		</div>
	</div>
	<!--haokun added 2017/03/07 end 增加私密成果要求-->

	{{if task.enclosure1}}
	<div class="enclosure row">
		<span>附件:</span>
		<ul>
		{{if task.enclosure1}}
			<li><a href="{{:task.enclosure1}}" target="_blank">{{:task.enclosure1Name}}</a></li>
		{{/if}}
		{{if task.enclosure2}}
			<li><a href="{{:task.enclosure2}}" target="_blank">{{:task.enclosure2Name}}</a></li>
		{{/if}}
		{{if task.enclosure3}}
			<li><a href="{{:task.enclosure3}}" target="_blank">{{:task.enclosure3Name}}</a></li>
		{{/if}}
		{{if task.enclosure4}}
			<li><a href="{{:task.enclosure4}}" target="_blank">{{:task.enclosure4Name}}</a></li>
		{{/if}}
		{{if task.enclosure5}}
			<li><a href="{{:task.enclosure5}}" target="_blank">{{:task.enclosure5Name}}</a></li>
		{{/if}}
		</ul>
	</div>
	{{/if}}

	<div class="line margin-top-10"></div>
	{{if task.da}}
	<div class="row attach-list">
		{{for task.da}}
		<div class="attach-item">
			<div class="row attach-info">
				<div class="open">
					<i class="iconfont icon-add"></i>
				</div>
				<div class="attach-title">
					<span>附加任务{{:#index+1}}</span>
				</div>
 				<div class="attach-expire-date">
					<span>截止时间：{{:expireDate}}</span>
				</div>
 				<div class="attach-amount">
					{{amountFormat amount/}}
				</div>
				<div class="attach-state">
					{{if state==0}}
					<a href="javascript:;" data-id={{:id}} class="attach-delete"><i class="glyphicon glyphicon glyphicon-trash"></i></a>
					{{else state == 1}}
					<span>接单人接受<span>
					{{else}}
					<span>接单人拒绝<span>
					{{/if}}
				</div>
			</div>
			<div class="row content hide">
				{{:content}}
			</div> 
			<div class="row achieve hide margin-top-10">
				<div class="achieve-tip">
					<span>成果要求：</span>
				</div>
				<div>
					<pre>{{:achieve}}</pre>
				</div>
			</div>
		</div> 
		<div class="line margin-top-10"></div>
		{{/for}}
	</div> 
	{{/if}}

	<div class="row attach-add">
		
	</div>
	
	<div class="row specialty">
		<div class="left">
			<div class="row task-state-desc">
				{{if task.state == "0"|| task.state == "1"|| task.state == "2"}}
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">竞标</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">开始</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999" title="关闭或者终止">完成</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">支付</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">结束</div>
				</div>
				{{else task.state == "3"}}
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">竞标</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">开始</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999" title="关闭或者终止">完成</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">支付</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">结束</div>
				</div>
				{{else task.state == "4"}}
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">竞标</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">开始</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc" title="关闭或者终止">关闭</div>
				</div>
				{{else task.state == "5"}}
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">竞标</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">开始</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc" title="关闭或者终止">完成</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">支付</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">结束</div>
				</div>
				{{else task.state == "6"|| task.state == "7"}}
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">竞标</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">开始</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc" title="关闭或者终止">完成</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">支付</div>
				</div>
				<div class="divider dashed"></div>
				<div class="state">
					<div class="task-state-dot annular"></div>
					<div class="state-desc color999999">结束</div>
				</div>
				{{else task.state == "8"}}
				<div class="state">
					<div class="task-state-dot"></div>
						<div class="state-desc">竞标</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">开始</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc" title="关闭或者终止">完成</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">支付</div>
				</div>
				<div class="divider"></div>
				<div class="state">
					<div class="task-state-dot"></div>
					<div class="state-desc">结束</div>
				</div>
				{{/if}}
			</div>
		</div>
		<div class="right">
			<ul>
         		<li><a href="/dashboard/Finance/records.jsp?taskId={{:task.id}}" class="record" target="_blank" title="任务财务记录"><i class="iconfont icon-task-finance"></i></a></li>
          		<li><a href="javascript:;" class="remark" title="备忘录" remark="{{:task.remark}}"  data-id="{{:task.id}}"><i class="iconfont icon-note"></i></a></li>
				<li><a href="/dashboard/Task/multiplex.jsp?id={{base64Encode task.id/}}"  class="multiplex" title="任务复用"><i class="iconfont icon-task-multiplex"></i></a></li>
     			{{if task.state == "0"|| task.state == "1"}} 
				<li><a href="javascript:;"  class="invite-user" title="邀请用户来接单" data-id="{{:task.id}}"><i class="iconfont icon-invite"></i></a></li>
     			{{/if}}
			</ul> 
		</div>
 	</div>
	<div class="line margin-top-20"></div>
	{{if task.state == "5"|| task.state == "6"|| task.state == "7"|| task.state == "8"}}
	{{for task.receiveDemands}}
	{{if state == "1"}}
	<div class="row achieve-receive">
		<div class="achieve-tip">
			<span>接单人提交的任务成果：</span>
		</div>
		<div class="row">
			<pre>{{:resultDesc}}</pre>
		</div>
		{{if resultUrl1}}
		<div class="enclosure row">
			<span>附件：</span>
			<ul>
			{{if resultUrl1}}
				<li><a href="{{:resultUrl1}}" target="_blank">{{:resultUrl1Name}}</a></li>
			{{/if}}
			{{if resultUrl2}}
				<li><a href="{{:resultUrl2}}" target="_blank">{{:resultUrl2Name}}</a></li>
			{{/if}}
			{{if resultUrl3}}
				<li><a href="{{:resultUrl3}}" target="_blank">{{:resultUrl3Name}}</a></li>
			{{/if}}
			{{if resultUrl4}}
				<li><a href="{{:resultUrl4}}" target="_blank">{{:resultUrl4Name}}</a></li>
			{{/if}}
			{{if resultUrl5}}
				<li><a href="{{:resultUrl5}}" target="_blank">{{:resultUrl5Name}}</a></li>
			{{/if}}
			</ul>
		</div>
		{{/if}}
	</div> 
	<div class="line margin-top-10"></div>
	{{/if}}
	{{/for}}
	{{/if}}

	{{for task.receiveDemands}}
		 {{if state == "1"}}
			{{if evaluate}} 
				<div class="row evaluate">
					<div>
						<div class="evaluate-tip">
							<span>我的评价：</span>
						</div>
						<div class="row star">
							<span class="desc">诚信打分:</span>
							<input type="number" value="{{:evaluate.evaluate}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
						</div>
						<div class="row star">
							<span class="desc">质量打分:</span>
							<input type="number" value="{{:evaluate.quality}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
						</div>
						<div class="row star">
							<span class="desc">准时性打分:</span>
							<input type="number" value="{{:evaluate.punctual}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
						</div>
						<div class="row star">
							<span class="desc" style="line-height: 20px;">评价描述:</span>
							<pre>{{:evaluate.description}}</pre>
						</div>
						
					</div>
					
				</div> 
				<div class="line margin-top-10"></div>
			{{/if}}
		{{/if}}
	{{/for}}

	{{for task.receiveDemands}}
		{{if state == "1"}}
			{{if evaluateReceiver}}
				<div class="row evaluate">
					<div>
						<div class="evaluate-tip">
							<span>接单的评价：</span>
						</div>
						<div class="row star">
							<span class="desc">诚信打分:</span>
							<input type="number" value="{{:evaluateReceiver.evaluate}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
						</div>
						<div class="row star">
							<span class="desc" style="line-height: 20px;">评价描述:</span>
							<pre>{{:evaluateReceiver.description}}</pre>
						</div>
					</div>
				</div> 
				<div class="line margin-top-10"></div>
			{{/if}}
		{{/if}}
	{{/for}}

	{{for task.receiveDemands}}
		{{if state == "1" && rulePercent}}
				<div class="row arbitration">
					<div>
						<div class="arbitration-tip">
							<span>平台仲裁：</span>
						</div>
						<div class="row star">
							<span class="desc">仲裁比例: {{:rulePercent}}%</span>
						</div>
						<div class="row star">
							<span class="desc">仲裁描述:</span>
							<pre>{{:ruleReason}}</pre>
						</div>
					</div>
				</div> 
				<div class="line margin-top-10"></div>
		{{/if}}
	{{/for}}

	<div class="bottom margin-top-20">
		<div class="user"> 
			{{if task.state == 2|| task.state == 3|| task.state == 4|| task.state == 5|| task.state == 6|| task.state == 7|| task.state == 8}}
				{{for task.receiveDemands}}
					{{if state==1}}
					<a href="javascript:void(0);" class="user-pic" data-id="{{:userId}}">
						<img class="img-circle" src="{{:user.picUrl}}">
					</a>
					<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}"><span class="username">{{:user.nickName}}</span></a>
					<span class="margin-left-20">中标价格：{{:amount}}</span>
					{{/if}}
				{{/for}}
			{{else}}
				{{if task.receiveDemands.length>0}}
					{{for task.receiveDemands}}
						{{if #index==0}}
						<span class="username"><a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}">{{:user.nickName}}</a></span>
						{{/if}}
						{{if #index==1}}
						<span class="username">、<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}">{{:user.nickName}}</a></span>
						{{/if}}
						{{if #index==2}}
						<span class="username">、<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}">{{:user.nickName}}</a></span>
						{{/if}}
					{{/for}} 
					<span>等{{:task.receiveDemands.length}}人竞标了此任务</span>
				{{else}}
					<span>暂无人竞标此任务</span>
				{{/if}}
			{{/if}}
		</div>
		<div class="row operate">
			<button type="button" class="btn task-attach-add {{if task.state !== "3" && task.state !== "5"}} hide {{/if}}" data-id="{{:task.id}}" data-date="{{:task.expireDate}}">增加附加任务</button>
			<button type="button" class="btn task-complete {{if task.state !== "3"}} hide {{/if}}" data-id="{{:task.id}}">关闭任务</button>
			<button type="button" class="btn task-end {{if task.state !== "0" && task.state !== "1"&& task.state !== "2"}} hide {{/if}}" data-id="{{:task.id}}">关闭任务</button>
			<button type="button" class="btn task-edit {{if task.state !== "0" && task.state !== "1"}} hide {{/if}}" data-id="{{:task.id}}">修改任务</button>
			<button type="button" class="btn task-select {{if task.state !== "0"&& task.state !== "1"}} hide {{/if}}" data-id="{{:task.id}}">查看并匹配</button>
			<button type="button" class="btn tooltip-test task-pay {{if task.state !== "5" && task.state !== "6"}} hide {{/if}}" data-id="{{:task.id}}" data-toggle="tooltip" title="点击后，您可以根据接单人的完成情况进行全额或者部分支付">确认支付</button>  <!--haokun modified 2017/03/14 增加了tooltip，指导用户；在class里增加了tooltip-test类-->
			<!--haokun added 2017/03/14 start 增加了一个拒绝支付的按钮；避免某些情况下用户不敢去点击“支付”这个按钮当他不想支付的时候-->
			<button type="button" class="btn task-refusepay {{if state !== "5"}} hide {{/if}}" data-id="{{:id}}">拒绝支付</button>
			<!--haokun added 2017/03/14 start 增加了一个拒绝支付的按钮；避免某些情况下用户不敢去点击“支付”这个按钮当他不想支付的时候-->
			<button type="button" class="btn task-pay {{if task.state !== "7"}} hide {{/if}}" data-id="{{:task.id}}">查看仲裁</button>
			<button type="button" class="btn task-evaluate {{if task.state !== "8"}} hide {{/if}}" data-id="{{:task.id}}">评价</button>
		</div>
	</div>
	
</div>

</script>


<script type="html/x-jsrender" id="friend-item-tmpl">
			{{for dataList}}
			<div class="row margin-top-20 ">
				<div class="col-md-2 col-sm-2 col-xs-2"></div>
				<div class="col-md-8 col-sm-8 col-xs-8">
					<div style="float:left;">
						<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:friendUser.id}}">
							<img src="{{:friendUser.picUrl}}" style="height: 44px;width:44px;">
						</a>
					</div>
					<div style="float:left;">
						<div class="row">
							<span class="username">{{:friendUser.nickName}}</span>
						</div>
						<div class="row">
							<span class="evaluate show-evaluate" data-userid="{{:friendUser.id}}" data-evaluatecount="{{:friendUser.evaluateCount}}" data-evaluatepublishcount="{{:friendUser.evaluatePublishCount}}" data-honest="{{:friendUser.honest}}" data-quality="{{:friendUser.quality}}" data-punctual="{{:friendUser.punctual}}" data-honestpublish="{{:friendUser.honestPublish}}" data-align="bottom left">
								{{evaluateFormat friendUser.evaluate friendUser.evaluateCount friendUser.evaluatePublish friendUser.evaluatePublishCount/}}
							</span>
							<span class="occupation"> {{:friendUser.occupation}} </span>
						</div>
					</div>
					<div style="float:right;">
						<button type="buttom" class="btn invite-btn" data-id="{{:friendUser.id}}">{{if invite == "0"}}邀请帮助{{else}}再次邀请{{/if}}</button>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-2"></div>
			</div>
			{{/for}}
</script>
<script type="html/x-jsrender" id="friend-tmpl">
	
    <div class="friend-item">
		<div class="row">
			<div class="col-md-2 col-sm-2 col-xs-2"></div>
			<div class="col-md-8 col-sm-8 col-xs-8">
				<a href="javasrcipt:void(0)" class="search-user-a"><i class="glyphicon glyphicon-search search-i"></i></a>
				<input type="text" class="form-control search-user" data-demandid = "{{:demandId}}" placeholder="搜索你想邀请的关注人物…">
			</div>
			<div class="col-md-2 col-sm-2 col-xs-2"></div>
		</div>
		<div class="invite-user-list">	
			{{if dataList}}
			{{for dataList}}
			<div class="row margin-top-20 ">
				<div class="col-md-2 col-sm-2 col-xs-2"></div>
				<div class="col-md-8 col-sm-8 col-xs-8">
					<div style="float:left;">
						<a href="javascript:void(0);" class="user-pic" data-id="{{:friendUser.id}}">
							<img src="{{:friendUser.picUrl}}" style="height: 44px;width:44px">
						</a>
					</div>
					<div style="float:left;">
						<div class="row">
							<span class="username">{{:friendUser.nickName}}</span>
						</div>
						<div class="row">
							<span class="evaluate show-evaluate" data-userid="{{:friendUser.id}}" data-evaluatecount="{{:friendUser.evaluateCount}}" data-evaluatepublishcount="{{:friendUser.evaluatePublishCount}}" data-honest="{{:friendUser.honest}}" data-quality="{{:friendUser.quality}}" data-punctual="{{:friendUser.punctual}}" data-honestpublish="{{:friendUser.honestPublish}}" data-align="bottom left">
								{{evaluateFormat friendUser.evaluate friendUser.evaluateCount friendUser.evaluatePublish friendUser.evaluatePublishCount/}}
							</span>
							<span class="occupation"> {{:friendUser.occupation}} </span>
						</div>
					</div>
					<div style="float:right;">
						<button type="buttom" class="btn invite-btn" data-id="{{:friendUser.id}}">{{if invite == "0"}}邀请帮助{{else}}再次邀请{{/if}}</button>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-2"></div>
			</div>
			{{/for}}
			{{/if}}
		</div>
		<div class="row">
			<div class="col-md-2 col-sm-2 col-xs-2"></div>
			<div class="col-md-8 col-sm-8 col-xs-8">
				<ul class="pagination" id="friendPageSplit">

				</ul>
			</div>
			<div class="col-md-2 col-sm-2 col-xs-2"></div>
		</div>
    </div>
    
</script>

<script id="evaluate-desc" type="html/x-jsrender">
	<div class="row evaluate {{:display}}">
		<div>
			<div class="evaluate-tip">
				<span>我的评价：</span>
			</div>
			<div class="row star">
				<span class="desc">诚信打分:</span>
				<input type="number" value="{{:evaluate}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
			</div>
			<div class="row star">
				<span class="desc">质量打分:</span>
				<input type="number" value="{{:quality}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
			</div>
			<div class="row star">
				<span class="desc">准时性打分:</span>
				<input type="number" value="{{:punctual}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
			</div>
			<div class="row star">
				<span class="desc" style="line-height: 20px;">评价描述:</span>
				<pre>{{:description}}</pre>
			</div>
						
		</div>
					
	</div> 
</script>

<script type="html/x-jsrender" id="task-state-tmpl">
{{if state == "0"||state == "1"||state == "2"}}
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">竞标</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">开始</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999" title="关闭或者终止">完成</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">支付</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">结束</div>
</div>
{{else state == "3"}}
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">竞标</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">开始</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999" title="关闭或者终止">完成</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">支付</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">结束</div>
</div>
{{else state == "4"}}
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">竞标</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">开始</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc" title="关闭或者终止">关闭</div>
</div>
{{else state == "5"}}
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">竞标</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">开始</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc" title="关闭或者终止">完成</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">支付</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">结束</div>
</div>
{{else state == "6"||state == "7"}}
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">竞标</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">开始</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc" title="关闭或者终止">完成</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">支付</div>
</div>
<div class="divider dashed"></div>
<div class="state">
	<div class="task-state-dot annular"></div>
	<div class="state-desc color999999">结束</div>
</div>
{{else state == "8"}}
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">竞标</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">开始</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc" title="关闭或者终止">完成</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">支付</div>
</div>
<div class="divider"></div>
<div class="state">
	<div class="task-state-dot"></div>
	<div class="state-desc">结束</div>
</div>
{{/if}}
</script>

<script id="tip-dlg" type="html/x-jsrender">
<div class="tip-dlg-content">
	<div class="row"> 
		<div class="title">
			<span>任务付款</span>
			<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
		</div>
	</div>
	<div class="row content">
		<i class="iconfont icon-info-warning"></i>
		<span>你选择匹配的任务需要支付金额$ {{:amount}}。</span>
	</div>
	<div class="row operate">
		<form method="post" class="paypal-form" id="paypal-form" action="https://www.paypal.com/cgi-bin/webscr" target="_blank">
		
			<button type="button" class="btn balance">余额支付</button>
			<button type="submit" class="btn paypal">Paypal支付</button>
			<button type="button" class="btn credit">信用卡支付</button>

			<input type="hidden" name="upload" value="1" /> 
			<input type="hidden" name="return" value="www.anyonehelps.com/dashboard/Finance/records.jsp" /> 
			<input type="hidden" name="notify_url" value="www.anyonehelps.com/account/paypal_notify_url"/>
			<input type="hidden" name="cmd" value="_cart" /> 
			<input type="hidden" name="business" value="teanjuly@gmail.com" />
			<input type="hidden" name="image_url" value="https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png"/>   <!--anyonehelps logo add by haokun-->
			<!-- Product 1  -->
			<input type="hidden" name="custom" value="task pay,{{:taskId}},{{:rdId}}"/>
			<input type="hidden" name="item_name_1" value="www.anyonehelps.com task pay" />
			<input type="hidden" name="item_number_1" value="p1" />
			<input type="hidden" name="quantity_1" value="1" />
			<input type="hidden" name="amount_1" value="{{creditPayAmountFormat amount/}}"/> 
		</form>
	</div>
	<div class="row content">
		<span>Paypal和信用卡支付需要回收2.9%+30美分的手续费</span>
	</div>
	
</div>
</script>

<script id="credit-pay-dlg" type="html/x-jsrender">
<div class="credit-pay-dlg-content">
	<div class="row"> 
		<div class="title">
			<span>任务付款</span>
			<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
		</div>
	</div>
	<div class="form-body">
		<div class="row margin-top-20">
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-3">
	       		<span class="left">信用卡类型<span class="font-red">*</span>：</span>
	     	</div>
			<div class="col-md-6 col-sm-6 col-xs-6">
				<select name="brand" class="form-control radius0">
					<option value="Visa">Visa</option>
					<option value="American Express">American Express</option>
					<option value="Visa">MasterCard</option>
					<option value="American Express">Discover</option>
					<option value="Visa">JCB</option>
					<option value="American Express">Diners Club</option>
					<option value="Visa">Unknown</option>
				</select>
	   	 	</div>
	    	<div class="col-md-1 col-sm-1 col-xs-1"> </div>
		</div>
	   	
		<div class="row margin-top-20">
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-3">
	       		<span class="left">姓名<span class="font-red">*</span>：</span>
	    	</div>
	        <div class="col-md-6 col-sm-6 col-xs-6">
				<input name="name" class="form-control radius0" type="text"
					placeholder="请输入信用卡持有人姓名" />
	        </div>
	        <div class="col-md-1 col-sm-1 col-xs-1"> </div>
	   	</div>
	   	
	   	<div class="row margin-top-20">
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-3">
	        	<span class="left">信用卡号码<span class="font-red">*</span>：</span>
	        </div>
	        <div class="col-md-6 col-sm-6 col-xs-6">
	        	<input name="creditNo" type="text" class="form-control radius0"
					placeholder="请输入信用卡号码" />
	        </div>
	        <div class="col-md-1 col-sm-1 col-xs-1"> </div>
	   	</div>
	   	
	   	<div class="row margin-top-20">
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-3">
	        	<span class="left">信用卡安全码<span class="font-red">*</span>：</span>
	        </div>
	        <div class="col-md-6 col-sm-6 col-xs-6">
	        	<input name="creditSecurityCode" type="text" maxlength="3"
					placeholder="请输入信用卡3位安全码" class="form-control radius0" />
	        </div>
	        <div class="col-md-1 col-sm-1 col-xs-1"> </div>
	   	</div>
	   	
	   	<div class="row margin-top-20">
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-3">
	        	<span class="left">失效月/年<span class="font-red">*</span>：</span>
	        </div>
	        <div class="col-md-6 col-sm-6 col-xs-6">
	        	<div class="input-group">
	        		<select name="expireMonth" class="form-control input-xsmall input-inline radius1">
						<option value="01">01</option>
						<option value="02">02</option>
						<option value="03">03</option>
						<option value="04">04</option>
						<option value="05">05</option>
						<option value="06">06</option>
						<option value="07">07</option>
						<option value="08">08</option>
						<option value="09">09</option>
						<option value="10">10</option>
						<option value="11">11</option>
						<option value="12">12</option>
					</select>
					<span class="help-inline pull-left"> &nbsp;/&nbsp;</span>  
					<select name="expireYear" class="form-control input-xsmall input-inline radius1">
						<option value="2016">16</option>
						<option value="2017">17</option>
						<option value="2018">18</option>
						<option value="2019">19</option>
						<option value="2020">20</option>
						<option value="2021">21</option>
						<option value="2022">22</option>
						<option value="2023">23</option>
					</select>
									
				</div>
	        </div>
	        <div class="col-md-1 col-sm-1 col-xs-1"> </div>
	   	</div>
	   	
	   	<div class="row margin-top-20"> 
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-3">
	        	<span class="left">实际付款总额<span class="font-red">*</span>：</span>
	        </div>
	        <div class="col-md-3 col-sm-3 col-xs-3">
	        	<span class="total-amount" data-amount="{{creditPayAmountFormat amount/}}">$ {{creditPayAmountFormat amount/}}</span>
	        </div>
	       	<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    </div>
	   	<div class="row margin-top-20">
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-3">
	        </div>
	        <div class="col-md-6 col-sm-6 col-xs-6">
	        	<a name="a_creditcard_recharge" class="btn credit-pay-btn">确认充值</a>
	        </div>
	        <div class="col-md-1 col-sm-1 col-xs-1"> </div>
	   	</div>
 	</div>
</div>
</script>



<script id="paypal-pay-dlg" type="html/x-jsrender">
<div class="paypal-pay-dlg-content">
	<div class="row"> 
		<div class="title">
			<span>任务付款</span>
			<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
		</div>
	</div>

	<div class="row content">
		<i class="iconfont icon-info-warning"></i>
		<span>支付完成前请不要关闭些窗口，完成支付后请根据你的情况点击下面的按钮</span>
	</div>
	<div class="row operate">
		<button type="button" class="btn pay-succ">已完成支付</button>
		<button type="submit" class="btn pay-fail">支付遇到问题</button>
	</div>
</div>
</script>

<%@ include file="/include/footer.jsp"%>
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>

<script src="/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-star-rating/js/star-rating.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js " type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/task-pub-detail.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
