<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-star-rating/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/icheck-1.x/skins/flat/blue.css" rel="stylesheet" type="text/css"/>

<link href="/assets/pages/css/task-acc-detail.css" rel="stylesheet" type="text/css" />

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
								<i class="icon-list"></i> <span
									class="caption-subject uppercase"> 我接收的任务</span>
							</div>
						</div>
						

						<div class="portlet-body">
                        	<div class="task-list row">
                        		
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
	
<script type="html/x-jsrender" id="task-evaluate-dlg">
	<div class="task-evaluate-list" data-taskid="{{:task.id}}">
    	<div class="row"> 
			<div class="title">
				<span>评价{{if task.receiveDemands[0].evaluateStateReceiver =="1"}}(已评){{/if}}</span>
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		<div class="row user-info" data-id="{{:task.id}}">
			{{if task.receiveDemands[0].evaluateStateReceiver =="0"}}
			<div class="evaluate-item" data-userid={{:task.user.id}}>
				<div class="left-div">
					<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:task.user.id}}"><img class="img-circle img-50" src="{{:task.user.picUrl}}"></a>
				</div>
				<div class="left-div">
					<div class="row">
						<span class="username">{{:task.user.nickName}}</span>
					</div>
					<div class="row star">
						<span class="desc">诚信打分:</span>
						<input type="number" value="5" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false"  data-isevaluate="0">
					</div>
					
					<div class="row">
						<textarea type="text" rows="4" placeholder="请输入您的评价" class="form-control radius0 evaluate"></textarea>
					</div>

				</div>
			</div>
			{{else task.receiveDemands[0].evaluateStateReceiver =="1"}}
			<div class="evaluate-item" data-userid={{:task.user.id}}>
				<div class="left-div">
					<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:task.user.id}}"><img class="img-circle img-50" src="{{:task.user.picUrl}}"></a>
				</div>
				<div class="left-div">
					<div class="row">
						<span class="username">{{:task.user.nickName}}</span>
					</div>
					<div class="row star">
						<input type="number" value="{{:task.receiveDemands[0].evaluateReceiver.evaluate}}" class="rating" min=0 max=5 step=0.5 data-size="md" data-show-clear="false" data-displayonly="true" data-isevaluate="1">
					</div>
					<div class="row">
						<textarea type="text" rows="4" placeholder="请输入您的评价" class="form-control radius0 evaluate">{{:task.receiveDemands[0].evaluateReceiver.description}}</textarea>
					</div>

				</div>
			</div>
			{{/if}}
		</div>
		<div class="row operate"> 
			<button type="button" class="btn task-evaluate-ok">提交</button>
			<button type="button" class="btn task-evaluate-cancel">取消</button>
		</div>
    </div>
</script>

<script id="result-upload-file" type="html/x-jsrender">
<div class="result-file-item row" data-id="{{:saveFileName}}" data-filename="{{:fileName}}">
	<div class="col-md-7 col-sm-7 col-xs-7">
		<a href="{{:saveFileName}}" class="filename" data-id="{{:saveFileName}}">{{:fileName}}</a>
	</div>
	<div class="col-md-5 col-sm-5 col-xs-5">
		<a href="javascript:void(0);" class="glyphicon glyphicon-remove result-file-delete"></a>
	</div>
</div>	
</script>

<script type="html/x-jsrender" id="task-complete-dlg">
	<div class="task-complete-list"  data-taskid="{{:taskId}}">
    	<div class="row"> 
			<div class="title">
				<span>提交任务成果(提交后，任务完成)</span>
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		<div class="row result" data-id="{{:taskId}}">
			<div class="row">
				<textarea type="text" rows="4" placeholder="请输入任务成果描述,更多内容请使用附件" class="form-control radius0 result-desc"></textarea>
			</div>
			<div class="row result-file-list">
			</div>
			<div class="row">
				<span class="btn result-upload fileinput-button">
    				<span> 上传附件 </span>
     				<input type="file" name="files[]" data-url="/demand/result_upload_file" multiple>
   		 		</span>
  				<label class="control-label font-red-flamingo result-format" style="margin-left:10px;margin-top:20px;font-size: 10px;">
					限5个，支持格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
				</label>
			</div>
			<div>
				<div class="progress" style="width:140px;border-radius: 0px;">
					<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
				</div>
			</div>
		</div>
		
		<div class="row operate"> 
			<button type="button" class="btn task-complete-ok" data-taskid="{{:taskId}}">提交</button>
			<button type="button" class="btn task-complete-cancel">取消</button>
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
		<span>任务编号：</span><span>{{:task.id}}</span>
      	<span class="expire-date">截止时间：</span><span>{{:task.expireDate}}</span>
		<span class="my-amount">我的报价：</span><span>\${{:task.receiveDemands[0].amount}}</span>	
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
					<button type="button" class="btn attach-accepte" data-id="{{:id}}">接受</button>
					<button type="button" class="btn attach-reject" data-id="{{:id}}">拒绝</button>
					{{else state == 1}}
					<span>已接受<span>
					{{else}}
					<span>已拒绝<span>
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
	<div class="row specialty">
		<div class="left">
			{{if task.ds.length > 0}}
			<ul>
				{{for task.ds}}
				<li><div class="specialty-item">{{:specialty.name}}</div></li>
				{{/for}}
			</ul>
			{{/if}}
		</div>
		<div class="right">
			<ul>
         		<li><a href="/dashboard/Finance/records.jsp?taskId={{:task.receiveDemands[0].demandId}}" class="record" target="_blank" title="任务财务记录"><i class="iconfont icon-task-finance"></i></a></li>
          		{{if task.state==0|| task.state==1}}<li><a herf="javascript:;" class="modify-amount" title="修改任务报价" taskamount="{{:task.amount}}" amount="{{:task.receiveDemands[0].amount}}" data-id="{{:task.receiveDemands[0].id}}"><i class="iconfont icon-task-add"></i></a></li>{{/if}}
          		<li><a herf="javascript:;" class="remark" title="任务备忘录" remark="{{:task.receiveDemands[0].remark}}"  data-id="{{:task.receiveDemands[0].id}}"><i class="iconfont icon-note"></i></a></li>
			</ul> 
		</div>
 	</div>

	<div class="line margin-top-20"></div>
	{{if task.state == "5"||task.state == "6"||task.state == "7"||task.state == "8"}}
	<div class="row achieve-receive">
		<div class="achieve-tip">
			<span>我提交的任务成果：</span>
		</div>
		<div class="row">
			<pre>{{:task.receiveDemands[0].resultDesc}}</pre>
		</div>
		{{if task.receiveDemands[0].resultUrl1}}
		<div class="enclosure row">
			<span>附件：</span>
			<ul>
			{{if task.receiveDemands[0].resultUrl1}}
				<li><a href="{{:task.receiveDemands[0].resultUrl1}}" target="_blank">{{:task.receiveDemands[0].resultUrl1Name}}</a></li>
			{{/if}}
			{{if task.receiveDemands[0].resultUrl2}}
				<li><a href="{{:task.receiveDemands[0].resultUrl2}}" target="_blank">{{:task.receiveDemands[0].resultUrl2Name}}</a></li>
			{{/if}}
			{{if task.receiveDemands[0].resultUrl3}}
				<li><a href="{{:task.receiveDemands[0].resultUrl3}}" target="_blank">{{:task.receiveDemands[0].resultUrl3Name}}</a></li>
			{{/if}}
			{{if task.receiveDemands[0].resultUrl4}}
				<li><a href="{{:task.receiveDemands[0].resultUrl4}}" target="_blank">{{:task.receiveDemands[0].resultUrl4Name}}</a></li>
			{{/if}}
			{{if task.receiveDemands[0].resultUrl5}}
				<li><a href="{{:task.receiveDemands[0].resultUrl5}}" target="_blank">{{:task.receiveDemands[0].resultUrl5Name}}</a></li>
			{{/if}}
			</ul>
		</div>
		{{/if}}
	</div> 
	<div class="line margin-top-10"></div>
	{{/if}}

	{{if task.receiveDemands[0].state == "1" && task.receiveDemands[0].rulePercent}}
		<div class="row arbitration">
			<div>
				<div class="arbitration-tip">
					<span>平台仲裁：</span>
				</div>
				<div class="row star">
					<span class="desc">仲裁比例: {{:task.receiveDemands[0].rulePercent}}%</span>
				</div>
				<div class="row star">
					<span class="desc">仲裁描述:</span>
					<pre>{{:task.receiveDemands[0].ruleReason}}</pre>
				</div>
			</div>
		</div> 
		<div class="line margin-top-10"></div>
	{{/if}}
 	
	{{if task.receiveDemands[0].evaluate}}
	<div class="row evaluate">
		<div>
			<div class="evaluate-tip">
				<span>发单人评价：</span>
			</div>
			<div class="row star">
				<span class="desc">诚信打分:</span>
				<input type="number" value="{{:task.receiveDemands[0].evaluate.evaluate}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
			</div>
			<div class="row star">
				<span class="desc">质量打分:</span>
				<input type="number" value="{{:task.receiveDemands[0].evaluate.quality}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
			</div>
			<div class="row star">
				<span class="desc">准时性打分:</span>
				<input type="number" value="{{:task.receiveDemands[0].evaluate.punctual}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
			</div>
			<div class="row star">
				<span class="desc">评价描述:</span>
				<pre>{{:task.receiveDemands[0].evaluate.description}}</pre>
			</div>
						
		</div>
	</div> 
	{{/if}}

	{{if task.receiveDemands[0].evaluateReceiver}}
	<div class="row evaluate">
		<div>
			<div class="evaluate-tip">
				<span>我的评价：</span>
			</div>
			<div class="row star">
				<span class="desc">诚信打分:</span>
				<input type="number" value="{{:task.receiveDemands[0].evaluateReceiver.evaluate}}" class="rating" min=0 max=5 step=0.5 data-size="sm" data-show-clear="false" data-displayonly="true" data-disabled="true">
			</div>
			<div class="row star">
				<span class="desc">评价描述:</span>
				<pre>{{:task.receiveDemands[0].evaluateReceiver.description}}</pre>
			</div>
		</div>
	</div> 
	{{/if}}

	{{if task.receiveDemands[0].evaluate|| task.receiveDemands[0].evaluateReceiver}}
		<div class="line margin-top-10"></div>
	{{/if}}

	<div class="row operate">
		<div class="left">
			<div class="row task-state-desc">
				{{if task.state == "0"||task.state == "1"||task.state == "2"}}
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
				{{else task.state == "6"||task.state == "7"}}
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
				<li  class="{{if task.state==="6" && task.receiveDemands[0].state === "1" &&　task.receiveDemands[0].payState === "2"}}{{else}}hide{{/if}}">
					<button class="btn task-pay" data-rdid="{{:task.receiveDemands[0].id}}" data-id="{{:task.id}}">收款</button>
				</li> 
				<li  class="{{if task.state==="7" && task.receiveDemands[0].state === "1"}}{{else}}hide{{/if}}">
					<button class="btn task-pay" data-rdid="{{:task.receiveDemands[0].id}}" data-id="{{:task.id}}">查看仲裁</button>
				</li>
				<li  class="{{if task.state === "8" && task.receiveDemands[0].evaluateStateReceiver =="0"}}{{else}}hide{{/if}}">
					<button class="btn task-evaluate" data-id={{:task.id}}>评价</button>
				</li>
				<li  class="{{if task.state === "0" || if task.state === "1"}}{{else}}hide{{/if}}">
					<button class="btn task-cancel" data-id={{:task.id}}>取消投标</button>
				</li>
				<li  class="{{if task.state === "2" && task.receiveDemands[0].state =="1"}}{{else}}hide{{/if}}">
					<button class="btn task-start"  data-id={{:task.id}}>开始任务</button>
				</li>
				<!--haokun added 2017/03/10 start 增加任务匹配后接单人能够拒绝任务，任务状态重新返回到匹配中,给发单人发送提示-->
				<li  class="{{if task.state === "2" && task.receiveDemands[0].state =="1"}}{{else}}hide{{/if}}">
					<button class="btn task-refuse" data-id={{:task.id}}>拒绝任务</button>
				</li>				
				<!--haokun added 2017/03/10 end 增加任务匹配后接单人能够拒绝任务，任务状态重新返回到匹配中,给发单人发送提示-->
				<li  class="{{if task.state === "3" && task.receiveDemands[0].state =="1"}}{{else}}hide{{/if}}">
					<button class="btn task-complete" data-id={{:task.id}}>提交成果</button>
				</li>
				<!--haokun added 2017/03/14 start 增加任务开始后接单人能够终止任务；点击“终止任务”后，也是到达提交任务成果；功能和提交成果一样-->
				<li  class="{{if task.state === "3" && task.receiveDemands[0].state =="1"}}{{else}}hide{{/if}}">
					<button class="btn task-complete" data-id={{:task.id}}>终止任务</button>
				</li>				
				<!--haokun added 2017/03/14 end 增加任务开始后接单人能够终止任务；点击“终止任务”后，也是到达提交任务成果；功能和提交成果一样-->
			</ul> 
		</div>
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
				<span class="desc">评价描述:</span>
				<pre>{{:description}}</pre>
			</div>
		</div>
	</div> 
</script>

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
<script type="html/x-jsrender" id="task-arbitration-item">
<div class="row percent">
	<span>请供不同意的理由和证据，供平台裁决:</span><br/>
	<span>应收报酬比例:</span>
	<input type="radio" calss="percentage" name="percent" value="100" checked>100%
	<input type="radio" calss="percentage" name="percent" value="60">60%
	<input type="radio" calss="percentage" name="percent" value="30">30%
	<input type="radio" calss="percentage" name="percent" value="0">0%
	<span style="margin-left:10px">比例下的金额:\$</span>
	<span class="my-pay-amount">{{payAmount task.receiveDemands[0].amount task.da 100/}}</span> 
</div>
<div class="row">
	<textarea type="text" rows="3" placeholder="请输入您的理由" class="form-control radius0 arbitration-reason"></textarea>
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
<div class="row">
	<button type="button" class="btn submit-arbitration" data-id="{{:task.taskId}}">提交</button>
	<button type="button" class="btn cancel-arbitration" data-id="{{:task.taskId}}">取消</button>
</div>
</script>
<script type="html/x-jsrender" id="task-pay-dlg">
<div class="task-pay-list" data-title="{{:task.title}}"  data-taskid="{{:task.id}}">
    <div class="row"> 
		<div class="title">
			<span>{{if task.state==7}}仲裁{{else}}收款{{/if}}</span>
			<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
		</div>
	</div>
	{{if task.receiveDemands[0].payState==4}}
	<div class="row desc">
		请等待平台裁决结果
	</div>
	{{/if}}
	<div class="row user-info" data-id="{{:task.id}}">
		{{if task.state==6 || task.state==7}}
		<div class="pay-item">
			<div class="left-div">
				<a href="javascript:;" target="_blank" class="user-pic" data-id="{{:task.user.id}}"><img class="img-circle img-50" src="{{:task.user.picUrl}}"></a>
			</div>
			<div class="left-div">
				<div class="row">
					<span class="username">{{:task.user.nickName}}</span>
				</div>
				<div class="row percent">
					<span>付款情况:任务总额\${{totalAmount task.receiveDemands[0].amount task.da /}}, 付款比例:{{:task.receiveDemands[0].payPercent}}%</span> 
					<span style="margin-left:10px">付款金额\${{payAmount task.receiveDemands[0].amount task.da task.receiveDemands[0].payPercent/}}</span> 
				</div>
				<div class="row">
					<textarea type="text" rows="3" readonly="readonly" placeholder="" class="form-control radius0 pay-reason">{{:task.receiveDemands[0].payReason}}</textarea>
				</div>
				<div class="row upload-file-list">
	                {{if task.receiveDemands[0].payReasonUrl1}}
					<div class="upload-file-item row">
						<a href="{{:task.receiveDemands[0].payReasonUrl1}}" class="filename" >{{:task.receiveDemands[0].payReasonUrl1Name}}</a>
					</div>	
					{{/if}}
					{{if task.receiveDemands[0].payReasonUrl2}}
					<div class="upload-file-item row">
						<a href="{{:task.receiveDemands[0].payReasonUrl2}}" class="filename" >{{:task.receiveDemands[0].payReasonUrl2Name}}</a>
					</div>	
					{{/if}}
					{{if task.receiveDemands[0].payReasonUrl3}}
					<div class="upload-file-item row">
						<a href="{{:task.receiveDemands[0].payReasonUrl3}}" class="filename" >{{:task.receiveDemands[0].payReasonUrl3Name}}</a>
					</div>	
					{{/if}}
					{{if task.receiveDemands[0].payReasonUrl4}}
					<div class="upload-file-item row">
						<a href="{{:task.receiveDemands[0].payReasonUrl4}}" class="filename" >{{:task.receiveDemands[0].payReasonUrl4Name}}</a>
					</div>	
					{{/if}}
					{{if task.receiveDemands[0].payReasonUrl5}}
					<div class="upload-file-item row">
						<a href="{{:task.receiveDemands[0].payReasonUrl5}}" class="filename" >{{:task.receiveDemands[0].payReasonUrl5Name}}</a>
					</div>	
					{{/if}}

	       		</div>
				{{if task.receiveDemands[0].payState==2}}
				<div class="row operate">
					<button type="button" class="btn btn-right task-argee-pay" data-id="{{:task.id}}">同意此比例报酬</button>
					<button type="button" class="btn btn-right show-arbitration" data-id="{{:task.id}}">不同意，提出仲裁</button>
				</div>
				{{/if}}
				{{if task.receiveDemands[0].payState==4}}
				<div class="row arbitration">
					<div class="row percent">
						<span>我提出的应收报酬比例:{{:task.receiveDemands[0].refutePercent}}%</span>
						<span style="margin-left:10px">比例下的金额:\$</span>
						<span>{{payAmount task.receiveDemands[0].amount task.da task.receiveDemands[0].refutePercent/}}</span> 
					</div>
					<div class="row">
						<textarea type="text" rows="3" readonly="readonly" placeholder="没有提供理由" class="form-control radius0 arbitration-reason">{{:task.receiveDemands[0].refuteReason}}</textarea>
					</div>
					<div class="row upload-file-list">
	                  	{{if task.receiveDemands[0].refuteReasonUrl1}}
						<div class="upload-file-item row">
							<a href="{{:task.receiveDemands[0].refuteReasonUrl1}}" class="filename" >{{:task.receiveDemands[0].refuteReasonUrl1Name}}</a>
						</div>	
						{{/if}}
						{{if task.receiveDemands[0].refuteReasonUrl2}}
						<div class="upload-file-item row">
							<a href="{{:task.receiveDemands[0].refuteReasonUrl2}}" class="filename" >{{:task.receiveDemands[0].refuteReasonUrl2Name}}</a>
						</div>	
						{{/if}}
						{{if task.receiveDemands[0].refuteReasonUrl3}}
						<div class="upload-file-item row">
							<a href="{{:task.receiveDemands[0].refuteReasonUrl3}}" class="filename" >{{:task.receiveDemands[0].refuteReasonUrl3Name}}</a>
						</div>	
						{{/if}}
						{{if task.receiveDemands[0].refuteReasonUrl4}}
						<div class="upload-file-item row">
							<a href="{{:task.receiveDemands[0].refuteReasonUrl4}}" class="filename" >{{:task.receiveDemands[0].refuteReasonUrl4Name}}</a>
						</div>	
						{{/if}}
						{{if task.receiveDemands[0].refuteReasonUrl5}}
						<div class="upload-file-item row">
							<a href="{{:task.receiveDemands[0].refuteReasonUrl5}}" class="filename" >{{:task.receiveDemands[0].refuteReasonUrl5Name}}</a>
						</div>	
						{{/if}}
	                </div>
				</div>
				{{/if}}
				<div class="row arbitration">
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
		{{/if}}
	<div>
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

<%@ include file="/include/footer.jsp"%>
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
	<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
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
	<script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="/assets/pages/scripts/task-acc-detail.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->