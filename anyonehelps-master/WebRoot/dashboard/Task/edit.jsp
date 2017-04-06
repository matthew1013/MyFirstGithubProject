<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/bootstrap-summernote/summernote.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>
<link href="/assets/pages/css/task-edit.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />

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

                    <div class="portlet light bordered">
                        <div class="portlet-title">
                            <div class="caption">
                                <i class="icon-equalizer font-red-sunglo"></i>
                                <span class="caption-subject uppercase">修改任务</span>
                                <span class="caption-helper font-red-flamingo">注意：请明确任务成果要求，如发生纠纷，平台将跟据交易达成时所述要求进行仲裁！</span>
                                
                            </div>
                            
                        </div>
                        <div class="portlet-body form">
                        	
                        	<!-- BEGIN FORM-->
                            <form action="#" class="form-horizontal" id="task-form" data-id="${param.id}">
								
                                
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
    <!-- END CONTENT dashboard/task/add-->
</div>
<!-- END CONTAINER -->
<script type="html/x-jsrender" id="friend-item-tmpl">
	{{for dataList}}
	<div class="row margin-top-20 ">
		<div class="col-md-2 col-sm-2 col-xs-12"></div>
		<div class="col-md-8 col-sm-8 col-xs-12">
			<div style="float:left;">
				<a href="javascript:void(0);" target="_blank" class="user-pic" data-id="{{:friendUser.id}}">
					<img src="{{:friendUser.picUrl}}" style="height:44px;width:44px;">
				</a>
			</div>
			<div style="float:left;">
				<div class="row">
					<span class="username">{{:friendUser.nickName}}</span>
				</div>
				<div class="row">
					<span class="evaluate show-evaluate" data-userid="{{:friendUser.id}}" data-evaluatecount="{{:friendUser.evaluateCount}}" data-evaluatepublishcount="{{:friendUser.evaluatePublishCount}}" data-honest="{{:friendUser.honest}}" data-quality="{{:friendUser.quality}}" data-punctual="{{:friendUser.punctual}}" data-honestpublish="{{:friendUser.honestPublish}}" data-align="bottom left">
						{{evaluateFormat friendUser.evaluate friendUser.evaluateCount friendUser.evaluatePublish friendUser.evaluatePublishCount/}}</span>
					<span class="occupation hidden-xs"> {{:friendUser.occupation}} </span>
				</div>
			</div>
			<div style="float:right;">
				<button type="buttom" class="btn invite-btn" data-id="{{:friendUser.id}}">{{if invite == "0"}}邀请帮助{{else}}再次邀请{{/if}}</button>
			</div>
		</div>
		<div class="col-md-2 col-sm-2 col-xs-12"></div>
	</div>
	{{/for}}
</script>
<script type="html/x-jsrender" id="friend-tmpl">
	
    <div class="friend-item">
    	<div class="row"> 
			<div class="friend-item-title">
				<div class="title"><span>恭喜，发布任务成功！</span></div>
			</div>
			<div style="float:right;">
				<a href="javascript:void(0);" target="_blank" class="glyphicon glyphicon-remove wclose"></a>
 			</div>
		</div>
		{{if open===0}}
		<div class="row title-sub"> 
			<div class="col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
				您发布了一个私密任务，任务将不在首页显示，请手动邀请用户来接单。
			</div>
		</div>
		{{/if}}
		<hr/>
		<div class="row">
			<div class="col-md-2 col-sm-2 col-xs-12"></div>
			<div class="col-md-8 col-sm-8 col-xs-12">
				<a href="javasrcipt:void(0)" class="search-user-a"><i class="glyphicon glyphicon-search search-i"></i></a>
				<input type="text" class="form-control search-user" data-demandid = "{{:demandId}}" placeholder="搜索你想邀请的关注人…">
			</div>
			<div class="col-md-2 col-sm-2 col-xs-12"></div>
		</div>
		<div class="invite-user-list">	
			{{if dataList}}
			{{for dataList}}
			<div class="row margin-top-20 ">
				<div class="col-md-2 col-sm-2 col-xs-12"></div>
				<div class="col-md-8 col-sm-8 col-xs-12">
					<div style="float:left;">
						<a href="javascript:void(0);" class="user-pic" data-id="{{:friendUser.id}}">
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
							<span class="occupation hidden-xs"> {{:friendUser.occupation}} </span>
						</div>
					</div>
					<div style="float:right;">
						<button type="buttom" class="btn invite-btn" data-id="{{:friendUser.id}}">{{if invite == "0"}}邀请帮助{{else}}再次邀请{{/if}}</button>
					</div>
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12"></div>
			</div>
			{{/for}}
			{{/if}}
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12" style="text-align: center;">
				<ul class="pagination" id="pageSplit">

				</ul>
			</div>
		</div>
    </div>
    
</script>

<script type="html/x-jsrender" id="specialty-item-tmpl">
<div class="specialty" data-id="{{:id}}" data-typeid="{{:typeid}}">
	<div style="float:left;">
		<a href="javascript:void(0);" class="glyphicon glyphicon-remove specialty-remove"></a>
	</div>
	{{:name}}
</div>
</script>

<script type="html/x-jsrender" id="specialty-tmpl">
	
    <div class="specialty-item">
    	<div class="row"> 
			<div style="float:left;">
				<ul class="nav nav-tabs">
					{{for dataList}}
					
            		<li {{if #index == 0 }} class="active" {{/if}}>
               		 	<a href="#tab_1_{{:#index}}" data-toggle="tab">{{:name}}</a>
                	</li>
					{{/for}}

           		</ul>
			</div>
			<div style="float:left;">
				<input type="text" class="form-control search" placeholder="关键字搜索" style="height: 28px;margin-top: 5px;margin-left: 50px;">
			</div>
			<div style="float:right;">
				<a href="javascript:void(0);" class="glyphicon glyphicon-remove wclose"></a>
 			</div>
		</div>
		<div class="row">
			<div class="tab-content">

				{{for dataList}}
	         	<div class="tab-pane {{if #index==0}}active{{/if}}" id="tab_1_{{:#index}}">
					{{for specialtys}} 
					<button class="btn specialty-btn {{SpecialtyFormat id #parent.parent.parent.parent.data.specialtyId/}}" type="button" data-id="{{:id}}" data-typeid="{{:#parent.parent.data.id}}" data-name="{{:name}}">{{:name}}</button>
					{{/for}}
				</div>
				{{/for}}
			</div>
		</div>
    </div>
    
</script>

<script id="edit-items-tmpl" type="html/x-jsrender">
<div class="row margin-top-30">
	<span class="step">1</span>
	<span class="step-desc font-plus"><b>任务类型</b><span>   
</div>
								
<div class="row margin-top-10">
  
	<div class="left">
		&nbsp;
	</div>
    <div class="col-md-7 col-sm-7 col-xs-10">
    	<div class="col-md-4 col-sm-4 col-xs-5">
        	<select id="type" class="form-control radius0"> 
	            <option value="">其它</option>  
	            <option value="1">学术</option>
				<option value="2">生活</option>    
				<option value="3">工作</option>    
	        </select>
        </div>
		<div class="col-md-1 col-sm-1 col-xs-1" >    
        </div>
        <div class="col-md-4 col-sm-4 col-xs-5" >   
        	<select id="typeSecond" class="form-control radius0">  
	              <option value=""><b>请选择分类</b></option>
	     	</select>
        </div>
		
	    <div class="margin-top-30 ">
	        <div class="left">
		      &nbsp;
	        </div>
	        <div class="col-md-10 col-sm-10 col-xs-12">    
		      <span class="step-desc"><b>任务标签</b></span>
	          </div>
        </div>

		<div class="margin-top-10 ">
  	        <div class="left">
		       &nbsp;
	        </div>
 	        <div class="col-md-9 col-sm-9 col-xs-12">
    	       <input type="text" class="form-control radius0" placeholder="请输入标签，如有多个，请使用#号分开" name="tag" value="{{:demand.tag}}">
	        </div>
		</div>
		                    	
		<div class="margin-top-30 ">
  	   		<div class="left">
		      &nbsp;
	        </div>
	        <div class="col-md-10 col-sm-10 col-xs-10">
  		        <div class="row system-tag-div">
	  	        </div>
 	        </div>
		</div>	
    </div>

	<div class="right-safe-icon col-md-3 col-sm-3 col-xs-0">   
	    <div class="title"><center>免费发布任务!<center></div> 
	    <div class="content">
			<div><i style="color:#33ff33;margin-top:2px;" class="fa">&#xf058;</i><span>优秀的达人会在数分钟内向您竞标！<span><br></div>
            <div><i style="color:#33ff33" class="fa">&#xf058;</i><span>查看达人的个人主页和反馈，然后随时和他们聊天吧！</span><br></div>
			<div><i style="color:#33ff33" class="fa">&#xf058;</i><span>您只需100%满意之后再进行支付！</span></div>
        </div>			
	</div>
</div>

<div class="row margin-top-30">   
  	<div class="col-md-8 col-sm-8 col-xs-10">
		<span class="step">2</span>
		<span class="step-desc font-plus">
			<b>任务标题</b>
			<span class="font-red-flamingo">*</span>
			<span class="task-title-alert hide">必填：请输入任务标题</span>
		</span> 
		<span class="step-desc pull-right"><span class="title-limit-count">60</span>字</span>
  	</div>
</div>


<div class="row margin-top-10">   
	                          		
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-8 col-sm-8 col-xs-10">    
		<input type="text" class="form-control radius0" placeholder="请输入任务标题" name="title" value="{{:demand.title}}" maxlength="60">
	</div>
</div>
                            
<div  class="row margin-top-10">
  	 <div class="left">
		&nbsp;
	</div>
    <div class="col-md-10 col-sm-10 col-xs-10">
        <div class="share-location">
        	<div style="float:left;">
        		<input type="checkbox" name="location-open" {{if demand.locationOpen==1}} checked="checked"{{/if}}>
        		<span class="step-desc">分享本地位置</span>
        	</div>
        	<div class="location-input">
	       		<input type="text" class="form-control radius0 hide" id="map-region"  placeholder="请输入你的位置"/>
				<input type="hidden" class="form-control" id="map-district" value=""/>
				<input type="hidden" class="form-control" id="map-province" value=""/>
				<input type="hidden" class="form-control" id="map-country" value=""/>
				<input type="hidden" class="form-control" id="map-latitude" value="0">
				<input type="hidden" class="form-control" id="map-longitude" value="0">
	       	</div>
       	</div>
        	
       	<div id="map" style="width: 100%; height: 300px;" class="hide"></div>
	</div>
</div>

<div class="row margin-top-30">   
	<span class="step">3</span>
	<span class="step-desc font-plus"><b>任务描述</b><span>
</div>

<div class="row margin-top-10">  		
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
		<div name="summernote" id="summernote_1"></div>
		<div class="placeholder" style="display: block;">
		   <span>请清晰描述您的任务，如果需要提供更多细节，请展开高级选项使用附件。</span><br>
		</div>
	</div>
</div>
	                        	
<div class="row margin-top-30">    
	<span class="step">4</span>
	<span class="step-desc font-plus">
	<b>成果要求</b>
	<span class="font-red-flamingo">*</span>
	<span class="task-achieve-alert hide">必填：请输入成果要求<span><span>
</div>
	                        	
<div class="row margin-top-10"> 
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
	    <textarea type="text" rows="6" placeholder="请输入成果要求" class="form-control radius achieve">{{:demand.achieve}}</textarea>
	</div>
</div>


<div class="row margin-top-10 ">
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
		<span class="step-desc"><b>私密成果要求</b></span>
	</div>
</div>

<div class=" row margin-top-10 ">
  	<div class="left">
		&nbsp;
	</div>
 	<div class="col-md-10 col-sm-10 col-xs-10">
    	 <input type="text" class="form-control radius0 secret-achieve" placeholder="如果您有什么话想悄悄对中标人说，请输入..." name="secret-achieve" value="{{:demand.secretAchieve}}">
	</div>
</div>

<div class="row margin-top-30">   
	<span class="step">5</span>
	<span class="step-desc font-plus">
		<b>悬赏金额</b>
		<span class="font-red-flamingo">*</span>
		<span class="task-amount-alert hide">必填：请输入悬赏金额<span>
	<span>
</div>
 
<div class="row margin-top-10">  
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
	    <div class="col-md-5 col-sm-7 col-xs-12">
	    	<input type="number" maxlength="10"  class="form-control radius0" id="amount" placeholder="格式：xx或者xx.xx，最低金额为10美金" value="{{:demand.amount}}">
	    </div>
	    <div class="col-md-3 col-sm-4 col-xs-5 hidden-xs">
	    	<div class="my-amount">
	    		<span>余额:$</span><span class="usd-amount">0</span>
	    	</div>
		</div>
	</div>
</div>
	                        	

<div class="row margin-top-30">   
	<span class="step">6</span>
	<span class="step-desc font-plus">
		<b>截止时间</b>
		<span class="font-red-flamingo">*</span>
		<span class="task-expire-alert hide">必填：请输入截止时间<span>
	<span>
</div>
<div class="row margin-top-10">
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
	    <div class="col-md-5 col-sm-5 col-xs-12">
	    	<input type="text" class="form-control radius0" id="expire-date" placeholder="格式：yyyy-mm-dd hh:mm:ss" value="{{:demand.expireDate}}">
	    </div>
	  </div>
</div>
		                     	
<div class="row margin-top-10">
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
		<span class="step-desc">附件<span>
	  </div>
</div>
<div  class="row">
  	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
        <div class="row enclosure-list">
        	{{if demand.enclosure1}}
			<div class="enclosure-item row">
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:;" class="glyphicon glyphicon-pencil enclosure-edit"></a>
					<a href="{{:demand.enclosure1}}" class="title" target="_blank" data-id="{{:demand.enclosure1}}">{{:demand.enclosure1Name}}</a>
					<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
				</div>
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:void(0);" class="glyphicon glyphicon-remove enclosure-delete"></a>
				</div>
			</div>
			{{/if}}
			{{if demand.enclosure2}}
			<div class="enclosure-item row">
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:;" class="glyphicon glyphicon-pencil enclosure-edit"></a>
					<a href="{{:demand.enclosure2}}" class="title" target="_blank" data-id="{{:demand.enclosure2}}">{{:demand.enclosure2Name}}</a>
					<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
				</div>
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:void(0);" class="glyphicon glyphicon-remove enclosure-delete"></a>
				</div>
			</div>
			{{/if}}
			{{if demand.enclosure3}}
			<div class="enclosure-item row">
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:;" class="glyphicon glyphicon-pencil enclosure-edit"></a>
					<a href="{{:demand.enclosure3}}" class="title" target="_blank" data-id="{{:demand.enclosure3}}">{{:demand.enclosure3Name}}</a>
					<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
				</div>
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:void(0);" class="glyphicon glyphicon-remove enclosure-delete"></a>
				</div>
			</div>
			{{/if}}
			{{if demand.enclosure4}}
			<div class="enclosure-item row">
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:;" class="glyphicon glyphicon-pencil enclosure-edit"></a>
					<a href="{{:demand.enclosure4}}" class="title" target="_blank" data-id="{{:demand.enclosure4}}">{{:demand.enclosure4Name}}</a>
					<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
				</div>
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:void(0);" class="glyphicon glyphicon-remove enclosure-delete"></a>
				</div>
			</div>
			{{/if}}
			{{if demand.enclosure5}}
			<div class="enclosure-item row">
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:;" class="glyphicon glyphicon-pencil enclosure-edit"></a>
					<a href="{{:demand.enclosure5}}" class="title" target="_blank" data-id="{{:demand.enclosure5}}">{{:demand.enclosure5Name}}</a>
					<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
				</div>
				<div class="col-md-5 col-sm-5 col-xs-5">
					<a href="javascript:void(0);" class="glyphicon glyphicon-remove enclosure-delete"></a>
				</div>
			</div>
			{{/if}}
        </div>
        <div class="row">
        	<span class="btn enclosure-upload fileinput-button">
	            <span> 上传文件 </span>
	            <input type="file" name="files[]" data-url="/demand/enclosure_upload"> 
	      	</span>
	      	<label class="control-label font-red-flamingo" style="margin-left:10px;font-size: 10px;" id="enclosure-format">
				最大能上传5个附件，支持附件格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
			</label>
	     </div>
 	</div>
</div>

<div  class="row hide">
  	<div class="left">
		&nbsp;
	</div>
  	<div class="col-md-10 col-sm-10 col-xs-10">
		<div id="progress" class="progress" style="width:140px;border-radius: 0px;">
			<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
		</div>
	</div>
</div>
		                       	
<div class="row margin-top-30">
	<div class="left">
		&nbsp;
	</div>
	<div class="col-md-10 col-sm-10 col-xs-10">
		<a href="javascript:;" class="show-detail">
			<i class="glyphicon glyphicon-plus"></i>
	  		<span class="desc">高级选项</span>
		</a>
	</div>
</div>


<div class="detail hide">
	<div class="row margin-top-10">
		<div class="left">
			&nbsp;
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-10">
			<span class="step-desc">不公开招标<span>
	  	</div>
	</div>
		                       	 	
	<div class="row">
  		<div class="left">
			&nbsp;
        </div>
        <div class="col-md-10 col-sm-10 col-xs-10">
        	<input type="checkbox" name="private-task" {{setOptionChecked "0" demand.open/}}>
			<span class="caption-helper" style="margin-left:10px;">如勾选，则任务不公开，任务不在首页显示，需要自己手动邀请用户来接任务</span>
      	</div>
	</div>
		                       	 	
	<div class="row margin-top-10">
		<div class="left">
			&nbsp;
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-10">
			<span class="step-desc">地理位置<span>
	  	</div>
	</div>
	<div class="row">
  		<div class="left">
			&nbsp;
        </div>
        <div class="col-md-10 col-sm-10 col-xs-10">
        	<div class="radio-list">
        		<div class="urgent">
					<input type="radio" name="nationality" value="" {{setOptionChecked "" demand.nationality/}}>
					不限 
				</div>
				<div class="urgent margin-left-21">
					<input type="radio" name="nationality" value="us"  {{setOptionChecked "us" demand.nationality/}}>
					美国
				</div>
				<div class="urgent margin-left-21">
					<input type="radio" name="nationality" value="cn"  {{setOptionChecked "cn" demand.nationality/}}>
					中国
				</div>
				<div class="urgent margin-left-21">
					<input type="radio" name="nationality" value="au"  {{setOptionChecked "au" demand.nationality/}}>
					澳洲
				</div>
				<div class="urgent margin-left-21">
					<input type="radio" name="nationality" value="ca"  {{setOptionChecked "ca" demand.nationality/}}>
					加拿大
				</div>
				<div class="urgent margin-left-21">
					<input type="radio" name="nationality" value="uk"  {{setOptionChecked "uk" demand.nationality/}}>
					英国
				</div>
	         </div>
      	</div>
	</div>
	
	
	<div class="row margin-top-10 hidden-xs">
		<div class="left">
			&nbsp;
	    </div>
	    <div class="col-md-10 col-sm-10 col-xs-10">
			<span class="step-desc">技能要求<span>  
	  	</div>
	</div>
	<div class="row hidden-xs">
  		<div class="left">
			&nbsp;
        </div>
        <div class="col-md-10 col-sm-10 col-xs-10">
        	<div class="specialty-list">
				{{for demand.ds}}
				<div class="specialty" data-id="{{:specialtyId}}" data-typeid="{{:specialtyTypeId}}">
					<div style="float:left;">
						<a href="javascript:void(0);" class="glyphicon glyphicon-remove specialty-remove"></a>
					</div>
				{{:specialty.name}}
				</div>
				{{/for}}
			</div>
			<div >
      			<button class="btn specialty-add" type="button">点击添加</button>
      		</div>
      	</div>
	</div>
</div>
		                       	
           
<div  class="row margin-top-30" >
	<div class="col-md-11 col-sm-11 col-xs-11">
	    <div>
	    	<button onclick="window.location.href='/index.jsp'" class="btn reset-btn">取消</button>
		    <button type="submit" class="btn submit-btn">提交</button>
		</div>
	</div>
</div>
</script>

<script id="enclosure-item-tmpl" type="html/x-jsrender">
	<div class="enclosure-item row">
		<div class="col-md-6 col-sm-6 col-xs-6">
			<a href="javascript:;" class="glyphicon glyphicon-pencil enclosure-edit"></a>
			<a href="{{:saveFileName}}" target="_blank" class="title" data-id="{{:saveFileName}}">{{:fileName}}</a>
			<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
		</div>
		<div class="col-md-5 col-sm-5 col-xs-5">
			<a href="javascript:void(0);" class="glyphicon glyphicon-remove enclosure-delete"></a>
		</div>
	</div>
</script>

<script id="tag-items-tmpl" type="html/x-jsrender">
{{for dataList}}
<label for="system-tag" class="control-label">
	<span class="system-tag-span" data-value="{{:name}}">\#{{:name}}</span>
</label>
{{/for}}
</script>

<script id="tag-items-tmpl" type="html/x-jsrender">
{{for dataList}}
<label for="system-tag">
	<span class="system-tag-span" data-value="{{:name}}">\#{{:name}}</span>
</label>
{{/for}}
</script>
<script id="type-second-tmpl" type="html/x-jsrender">
	<option value="">请选择分类</option>
{{if type=="1"}}
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
{{else type=="3"}}
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
{{else type=="2"}}
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

<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>


<script src="/assets/global/plugins/bootstrap-summernote/summernote.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>

<script src="/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js " type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>
<script src='//maps.google.com/maps/api/js?libraries=places&key=AIzaSyDTF_j2DcmcP8A7llS_Y2mnqvWtTO5ejSU&sensor=false' async type="text/javascript"></script>
<script src="/assets/global/plugins/locationpicker.jquery.js"></script>
<script src="/assets/pages/scripts/components-editors.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
<!--BEGIN ADD SCRIPTS BY CKH-->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/task-edit.js" type="text/javascript"></script>
<!--END ADD SCRIPTS BY CKH-->
<!-- END PAGE LEVEL SCRIPTS -->
