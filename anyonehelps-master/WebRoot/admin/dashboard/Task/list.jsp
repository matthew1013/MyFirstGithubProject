<%@ include file="/admin/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-star-rating/css/star-rating.css" media="all" rel="stylesheet" type="text/css"/>
<link href="/assets/pages/admin/css/admin-task-list.css" rel="stylesheet" type="text/css" />

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

<input type="hidden" name="key"/>
<input type="hidden" name="pn"/>


<div class="page-container" style="margin-top:70px;">
    <%@ include file="/admin/include/leftsider.jsp"%>
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
                                <span class="caption-subject uppercase"> 任务列表 </span>
                                <span class="caption-helper">蓝色为公开项目，灰色为非公开项目</span>
                            </div>
                            <ul class="nav nav-tabs">
                            	<li class="task-state active" data-state="0">
                                 	<a href="#tab_1_0" data-toggle="tab">所有</a>
                           		</li>
                           		<li class="task-state" data-state="1">
                                 	<a href="#tab_1_1" data-toggle="tab">匹配中</a>
                           		</li>
                                <li class="task-state" data-state="2">
                                	<a href="#tab_1_2" data-toggle="tab">任务裁决</a>
                                </li>
                               	<li class="task-state" data-state="3">
                               		<a href="#tab_1_3" data-toggle="tab">已结束</a>
                               	</li>
                          	</ul>
                        </div>
                        <div class="portlet-body">
                        	<!-- 当前查找条件 start -->
							<div class="row search">
								<div class="type">
									<span>分类:</span>
									<select name="type">
										<option value="">所有</option>
										<option value="1">学习类</option>
										<option value="2">生活类</option>
										<option value="3">工作类</option>
									</select>
								</div>
								<div class="location">
									<span>位置:</span>
									<select name="location">
										<option value="">所有</option>
										<option value="us">美国</option>
										<option value="cn">中国</option>
										<option value="uk">英国</option>
										<option value="au">澳洲</option>
										<option value="jp">日本</option>
									</select>
								</div>
								<div class="sort">
									<span>排序:</span>
									<select name="sort">
										<option value="0">默认推荐</option>
										<option value="1">最新任务</option>
										<option value="2">最热任务</option>
										<option value="3">报酬高</option>
										<option value="4">快结束</option>
									</select>
								</div>
								
								<div class="key">
									<i class="glyphicon glyphicon-search"></i>
									<input class="search-key" type="text" placeholder="任务关键字或所在地" value="">
									<button class="search-start">搜索</button>
								</div>
								
							</div>
							<!-- 当前查找条件 end -->
                        	<div class="task-list row">
                        		
                        	</div> 
                           
                        </div>
                        <div class="search-pagination">
							<ul class="pagination">

							</ul>
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


<script type="html/x-jsrender" id="task-item-tmpl">
{{for dataList}}
<div class="task-item" data-open="{{:open}}"  data-id="{{:id}}"> 
	<div class="row title {{if open==0}}bg-gray{{/if}}">
    	<div class="col-xs-9 col-sm-9 col-dm-9 col-lg-9">
			<div>
   				<a href="/task/detail.jsp?id={{base64Encode id/}}" target="_blank">{{:title}}</a>
			</div>
		</div>
		<div class="col-xs-3 col-sm-3 col-dm-3 col-lg-3">
			<ul>
				<li>
					<a href="javascript:;" class="zoom up" title="展开">
						<i class="glyphicon glyphicon-chevron-left"></i>
						<span>展开</span>
					</a>
				</li>
				<li>
					<a href="javascript:;" class="remark" title="备注" remark="{{:remarkAdmin}}"  data-id="{{:id}}">
						<i class="iconfont icon-task-arbitration"></i>
					</a>
				</li>
     		</ul>
		</div> 
 	</div>
    <div class="row info">
     	<span class="create-date">{{:createDate}}</span>
      	<span>任务单号：</span><span class="task-id">{{:id}}</span>
		<span>截止时间：</span><span class="expire-date">{{:expireDate}}</span>
 		<span>悬赏金额：</span><span class="amount">\${{:amount}}</span>	
		<span>投标人数：</span><span class="bid-count">{{:receiveDemands.length}}</span>	
  	</div>
	<div class="row task-state-desc">
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
		{{else state == "4"||state == "5"}}
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
	</div>
 	<div class="row content hide">
		<div>
			{{:content}}
		</div> 
		{{if enclosure1}}
		<div class="enclosure">
			<span>附件：</span>
			<ul>
			{{if enclosure1}}
				<li><a href="{{:enclosure1}}" target="_blank"><img src="{{showFileFormat enclosure1/}}">{{:enclosure1Name}}</a></li>
			{{/if}}
			{{if enclosure2}}
				<li><a href="{{:enclosure2}}" target="_blank"><img src="{{showFileFormat enclosure2/}}">{{:enclosure2Name}}</a></li>
			{{/if}}
			{{if enclosure3}}
				<li><a href="{{:enclosure3}}" target="_blank"><img src="{{showFileFormat enclosure3/}}">{{:enclosure3Name}}</a></li>
			{{/if}}
			{{if enclosure4}}
				<li><a href="{{:enclosure4}}" target="_blank"><img src="{{showFileFormat enclosure4/}}">{{:enclosure4Name}}</a></li>
			{{/if}}
			{{if enclosure5}}
				<li><a href="{{:enclosure5}}" target="_blank"><img src="{{showFileFormat enclosure5/}}">{{:enclosure5Name}}</a></li>
			{{/if}}
			</ul>
		</div>
		<div style="clear: both;"></div>
		{{/if}}

		<div class="row achieve margin-top-10">
			<span class="font-red-flamingo">成果要求：</span>
			<pre>{{:achieve}}</pre>
		</div>

	</div> 
	
	{{if state == "5"||state == "6"||state == "7"||state == "8"}}
		{{for receiveDemands}}
			{{if state == "1"}}
			<div class="row achieve-receive hide">
				<div>
					<span class="font-red-flamingo">接单人提交的任务成果：</span>
					<pre>{{:resultDesc}}</pre>
				</div>
				<div style="clear: both;"></div>
				{{if resultUrl1}}
				<div class="enclosure">
					<span>附件：</span>
					<div style="clear: both;"></div>
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
			{{/if}}

			{{if #parent.parent.data.state == "7"}}
			<div class="row pay-reason hide">
				<div>
					<span class="font-red-flamingo">发单人提交的付款理由：</span>
					<div style="clear: both;"></div>
					<span class="font-red-flamingo">付款比例：{{:payPercent}}%</span>
					<pre>{{:payReason}}</pre>
				</div>
				<div style="clear: both;"></div>
				{{if payReasonUrl1}}
				<div class="enclosure">
					<span>附件：</span>
					<div style="clear: both;"></div>
					<ul>
					{{if payReasonUrl1}}
						<li><a href="{{:payReasonUrl1}}" target="_blank">{{:payReasonUrl1Name}}</a></li>
					{{/if}}
					{{if payReasonUrl2}}
						<li><a href="{{:payReasonUrl2}}" target="_blank">{{:payReasonUrl2Name}}</a></li>
					{{/if}}
					{{if payReasonUrl3}}
						<li><a href="{{:payReasonUrl3}}" target="_blank">{{:payReasonUrl3Name}}</a></li>
					{{/if}}
					{{if payReasonUrl4}}
						<li><a href="{{:payReasonUrl4}}" target="_blank">{{:payReasonUrl4Name}}</a></li>
					{{/if}}
					{{if payReasonUrl5}}
						<li><a href="{{:payReasonUrl5}}" target="_blank">{{:payReasonUrl5Name}}</a></li>
					{{/if}}
					</ul>
				</div>
				{{/if}}
			</div> 

			<div class="row refute-reason hide">
				<div>
					<span class="font-red-flamingo">接单人提交的付款仲裁理由：</span>
					<div style="clear: both;"></div>
					<span class="font-red-flamingo">仲裁比例：{{:refutePercent}}%</span>
					<pre>{{:refuteReason}}</pre>
				</div>
				<div style="clear: both;"></div>
				{{if refuteReasonUrl1}}
				<div class="enclosure">
					<span>附件：</span>
					<div style="clear: both;"></div>
					<ul>
					{{if refuteReasonUrl1}}
						<li><a href="{{:refuteReasonUrl1}}" target="_blank">{{:refuteReasonUrl1Name}}</a></li>
					{{/if}}
					{{if refuteReasonUrl2}}
						<li><a href="{{:refuteReasonUrl2}}" target="_blank">{{:refuteReasonUrl2Name}}</a></li>
					{{/if}}
					{{if refuteReasonUrl3}}
						<li><a href="{{:refuteReasonUrl3}}" target="_blank">{{:refuteReasonUrl3Name}}</a></li>
					{{/if}}
					{{if refuteReasonUrl4}}
						<li><a href="{{:refuteReasonUrl4}}" target="_blank">{{:refuteReasonUrl4Name}}</a></li>
					{{/if}}
					{{if refuteReasonUrl5}}
						<li><a href="{{:refuteReasonUrl5}}" target="_blank">{{:refuteReasonUrl5Name}}</a></li>
					{{/if}}
					</ul>
				</div>
				{{/if}}
			</div> 
			{{/if}}

			{{if #parent.parent.data.state == "8"&&rulePercent}}
			<div class="row rule-reason hide">
				<div>
					<span class="font-red-flamingo">系统裁决结果：</span>
					<div style="clear: both;"></div>
					<span class="font-red-flamingo">裁决付款比例：{{:rulePercent}}%</span>
					<pre>{{:ruleReason}}</pre>
				</div>
				<div style="clear: both;"></div>
				{{if ruleReasonUrl1}}
				<div class="enclosure">
					<span>附件：</span>
					<div style="clear: both;"></div>
					<ul>
					{{if ruleReasonUrl1}}
						<li><a href="{{:ruleReasonUrl1}}" target="_blank">{{:ruleReasonUrl1Name}}</a></li>
					{{/if}}
					{{if ruleReasonUrl2}}
						<li><a href="{{:ruleReasonUrl2}}" target="_blank">{{:ruleReasonUrl2Name}}</a></li>
					{{/if}}
					{{if ruleReasonUrl3}}
						<li><a href="{{:ruleReasonUrl3}}" target="_blank">{{:ruleReasonUrl3Name}}</a></li>
					{{/if}}
					{{if ruleReasonUrl4}}
						<li><a href="{{:ruleReasonUrl4}}" target="_blank">{{:ruleReasonUrl4Name}}</a></li>
					{{/if}}
					{{if ruleReasonUrl5}}
						<li><a href="{{:ruleReasonUrl5}}" target="_blank">{{:ruleReasonUrl5Name}}</a></li>
					{{/if}}
					</ul>
				</div>
				{{/if}}
			</div> 
			{{/if}}
		{{/for}}
	{{/if}}
	
	<div class="row attach-list hide">
		{{for da}}
		<div class="attach-item">
			<div class="row title">
				<span>附加任务</span> <span class="margin-left-20">金额：</span><span class="amount">{{:amount}}</span>
				<span class="margin-left-20">截止时间：</span><span class="amount">{{:expireDate}}</span>
			</div>
			<div class="row content">
				{{:content}}
			</div> 
		</div> 
		{{/for}}
	</div> 

	<div class="row attach-add">
		
	</div>

   	<div class="row user hide">
   		<div class="col-xs-7 col-sm-7 col-dm-7 col-lg-7 left">
         	<div class="vertical {{if open==0}}bg-gray{{/if}}">
            	投<br/>标<br/>人{{:receiveDemands.length}}
        	</div>
     		<div class="receive-user">
				<ul >
					{{for receiveDemands}}
					<li>
						<a href="javascript:void(0);" target="_blank" class="user-pic" data-state="{{:#parent.parent.data.state}}" data-taskid="{{:#parent.parent.data.id}}" data-id="{{:userId}}" data-userid="{{:userId}}" data-rdid="{{:id}}" data-userpic="{{:user.picUrl}}"><img class="img-circle img-28 {{if state==0||state==2}}opacity08{{/if}}" src="{{:user.picUrl}}"></a>
					</li>
					{{/for}}
				</ul>
     		</div>
  		</div>

      	<div class="col-xs-3 col-sm-3 col-dm-3 col-lg-3 right">
       		<div class="vertical {{if open==0}}bg-gray{{/if}}">
             	中<br/>标<br/>人
         	</div>
            <div class="bid-user">
				<ul  data-bidcount="{{:bidCount}}" data-taskid={{:id}}>
					{{for receiveDemands}}
						{{if state==1||state==2}}
							<li><a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}" data-rdid="{{:id}}" ><img class="img-circle img-50 {{if state==2}}opacity08{{/if}}" src="{{:user.picUrl}}"></a></li>
						{{/if}}
					{{/for}}
				</ul>
            </div>
		</div>
		<div class="col-xs-2 col-sm-2 col-dm-2 col-lg-2 right">
       		<div class="vertical {{if open==0}}bg-gray{{/if}}">
             	发<br/>单<br/>人
         	</div>
            <div class="bid-user">
				<ul>
					<li>
						<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:userId}}" data-userid="{{:userId}}">
							<img class="img-circle img-50" src="{{:user.picUrl}}">
						</a>
					</li>
				</ul>
            </div>
		</div>
	</div>
	<div class="row operate">
		<a href="/admin/dashboard/User/list.jsp?taskId={{:id}}" target= _blank><button type="button" class="btn task-select {{if state !== "0"&&state !== "1"}} hide {{/if}} {{if open==0}}bg-gray{{/if}}" data-id="{{:id}}">推荐给用户</button></a>
		<button type="button" class="btn task-arbitration {{if state !== "7"}} hide {{/if}} {{if open==0}}bg-gray{{/if}}" data-id="{{:id}}">任务裁决</button>
		<button type="button" class="btn task-evaluate {{if state !== "8"}} hide {{/if}} {{if open==0}}bg-gray{{/if}}" data-id="{{:id}}">任务评介</button>
	</div>
</div>
{{/for}}
</script>

<script id="arbitration-upload-file" type="html/x-jsrender">
<div class="arbitration-file-item row" data-id="{{:saveFileName}}" data-filename="{{:fileName}}">
	<div class="col-md-7 col-sm-7 col-xs-7">
		<a href="{{:saveFileName}}" class="filename" data-id="{{:saveFileName}}">{{:fileName}}</a>
	</div>
	<div class="col-md-5 col-sm-5 col-xs-5">
		<a href="javascript:void(0);" class="glyphicon glyphicon-remove arbitration-file-delete"></a>
	</div>
</div>	
</script>

<script type="html/x-jsrender" id="task-arbitration-dlg">
	<div class="task-arbitration-list"  data-taskid="{{:taskId}}">
    	<div class="row"> 
			<div class="title">
				<span>任务裁决</span>
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
			</div>
		</div>
		<div class="row arbitration" data-id="{{:taskId}}">
			<div class="row percent">
				<span>付款比例:</span> 
				<input type="radio" calss="percentage" name="rule-percent" value="100" checked>100%
				<input type="radio" calss="percentage" name="rule-percent" value="60">60%
				<input type="radio" calss="percentage" name="rule-percent" value="30">30%
				<input type="radio" calss="percentage" name="rule-percent" value="0">0%
			</div>
			<div class="row">
				<textarea type="text" rows="4" placeholder="请输入任务裁决理由,更多内容请使用附件" class="form-control radius0 arbitration-desc"></textarea>
			</div>
			<div class="row arbitration-file-list">
			</div>
			<div class="row">
				<span class="btn arbitration-upload fileinput-button">
    				<span> 上传附件 </span>
     				<input type="file" name="files[]" data-url="/admin/arbitration_upload_file" multiple>
   		 		</span>
  				<label class="control-label font-red-flamingo arbitration-format" style="margin-left:10px;margin-top:20px;font-size: 10px;">
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
			<button type="button" class="btn task-arbitration-ok" data-taskid="{{:taskId}}">提交</button>
			<button type="button" class="btn task-arbitration-cancel">取消</button>
		</div>
    </div>
</script>

<%@ include file="/admin/include/footer.jsp"%>
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

<!-- END PAGE LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script>
<script src="/assets/pages/admin/scripts/admin-task-list.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->