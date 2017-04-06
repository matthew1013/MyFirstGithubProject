<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
%>
	<%@ include file="/include/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/assets/global/css/components-rounded.css" data-genuitec-lp-enabled="false" data-genuitec-file-id="wc1-478" data-genuitec-path="/anyonehelps-master/WebRoot/task/detail.jsp"/>
	<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/task-detail.css"/>
	<%
	String ip = request.getHeader("x-forwarded-for");
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getHeader("WL-Proxy-Client-IP");
	}
	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		ip = request.getRemoteAddr();
	}
	%>
	<input type="hidden" value="<%=ip%>" name="ip"/> 
	<input type="hidden" value="${param.id }" name="taskId"/>
	<input type="hidden" value="${param.view }" name="view"/>
	<input type="hidden" value="${user_id_session_key}" name="userId"/>
	<!--content-->
	<div class="container"> 
	
		<div class="row" style="height:90px"></div>
		
		<div class="row">
			<!-- 任务查找页面 start -->
			<div class="col-md-8 col-lg-8" style="padding-right: 10px;padding-left: 10px;">
				 <div class="task">
				</div>
			</div>
			<!-- 任务查找页面 end -->

			<!-- 任务查找右边 start -->
			<div class="col-md-4 col-lg-4" style="padding-right: 10px;padding-left: 10px;">
				<div>
					<!-- 任务资料 start -->
					<div class="task-right row">
						
					</div>
					<!-- 任务资料 end -->

					<!-- 任务进度 start -->
					<div class="task-state row">
						
					</div>
					<!-- 任务进度 start -->
					
					<!-- 相关问题 start -->
					<div class="relat-issues row">
						<span class="desc">相关问题</span>
						<div class="line"></div>
						<ul class="task-relvant-list">
						</ul>
					</div>
					<!-- 相关问题  end -->

					<!-- 标签 start  haokun modified 2017/03/07 -->
					<div class="right-tag row">
						<div class="tag-header">
							<div class="right-title">生活标签</div>
						</div>
						<div >
							<ul>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="买房"><div class="tag-item">#买房</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="买车"><div class="tag-item">#买车</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="卖车"><div class="tag-item">#卖车</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="租房"><div class="tag-item">#租房</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="独立卫生间"><div class="tag-item">#独立卫生间</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="纽约"><div class="tag-item">#纽约</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="深度游"><div class="tag-item">#深度游</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="骑行"><div class="tag-item">#骑行</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="自行车"><div class="tag-item">#自行车</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="摄影"><div class="tag-item">#摄影</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="摄影器材"><div class="tag-item">#摄影器材</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="dota2"><div class="tag-item">#dota2</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="搬家"><div class="tag-item">#搬家</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="太阳能"><div class="tag-item">#太阳能</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="减肥"><div class="tag-item">#减肥</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="滑雪"><div class="tag-item">#滑雪</div></a></li>
							</ul>
						</div>

						<div class="tag-header">
							<div class="right-title" style="margin-top:20px;">学术标签</div>
						</div>
						<div >
							<ul>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="数学"><div class="tag-item">#数学</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="论文"><div class="tag-item">#论文</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="语法"><div class="tag-item">#语法</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="英文"><div class="tag-item">#英文</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="润色"><div class="tag-item">#润色</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="哲学"><div class="tag-item">#哲学</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="物理"><div class="tag-item">#物理</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="生物"><div class="tag-item">#生物</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="选课"><div class="tag-item">#选课</div></a></li>
						
							</ul>
						</div>
						<div class="tag-header">
							<div class="right-title" style="margin-top:20px;">工作标签</div>
						</div>
						<div >
							<ul>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="导游"><div class="tag-item">#导游</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="伴游"><div class="tag-item">#伴游</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="留学生"><div class="tag-item">#留学生</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="兼职"><div class="tag-item">#兼职</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="计划"><div class="tag-item">#计划</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="辅导"><div class="tag-item">#辅导</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="纯手工"><div class="tag-item">#纯手工</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="同城"><div class="tag-item">#同城</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="送货"><div class="tag-item">#送货</div></a></li>
								<li><a href="javascript:void(0)" name="tag-a" data-tag="接送"><div class="tag-item">#接送</div></a></li>
							</ul>
						</div>
						<div class="tag-header">
							<div class="right-title" style="margin-top:20px;">金额标签</div>
						</div>
						<div class="amount-intervals">
							<ul>
								<li><a href="javascript:void(0)" name="tag-intervals-a" data-minamount="0"
									data-maxamount="99.99" data-tag="$100以下"><div class="tag-item">#100以下</div></a></li>
								<li><a href="javascript:void(0)" name="tag-intervals-a" data-minamount="100"
									data-maxamount="299.99" data-tag="$100~$300"><div class="tag-item">#100~300</div></a></li>
								<li><a href="javascript:void(0)" name="tag-intervals-a" data-minamount="300"
									data-maxamount="499.99" data-tag="$300~$500"><div class="tag-item">#300~500</div></a></li>
								<li><a href="javascript:void(0)" name="tag-intervals-a" data-minamount="500"
									data-maxamount="99999" data-tag="$500以上"><div class="tag-item">500以上</div></a></li>
							</ul> 
						</div>
						
						<div style="float: left;margin-top: 20px;">
							<p style="margin-left:20px;font-size: 16px;font-weight: 500;"></p>
		
						</div>
						
					</div>
					<!-- 标签 end  haokun modified 2017/03/07 -->
				</div>
			</div>
			<!-- 任务查找右边 end -->
		</div>
	</div>

<script id="task-content-right-tmpl" type="html/x-jsrender">
	<span class="desc">任务资料</span>
	<div class="line"></div> 
	<ul>
		<li>
			<div class="dot"></div>
			<div ><span class="task-amount">悬赏金额：   $ {{:task.amount}}</span></div>
		</li>
		<li>
			<div class="dot"></div>
			<div ><span class="task-amount">截止时间：{{:task.expireDate}}</span></div>
		</li>
		<li>
			<div class="dot"></div>
			<div ><span class="task-amount">任务位置：{{locationFormat task.locationName/}}</span></div>
		</li>
	</ul>

</script>

<script id="task-state-tmpl" type="html/x-jsrender">
	<span class="desc">任务进度</span>
	{{if state == "0"||state == "1"||state == "2"}}
	<div class="row" style="margin-top:20px;margin-left: -20px;">
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
	</div>
	{{else state == "3"}}
	<div class="row" style="margin-top:20px;margin-left: -20px;">
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
	</div>
	{{else state == "4"}}
	<div class="row" style="margin-top:20px;margin-left: -20px;">
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
			<div class="state-desc" title="关闭">关闭</div>
		</div>
	</div>
	{{else state == "5"}}
	<div class="row" style="margin-top:20px;margin-left: -20px;">
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
	</div>
	{{else state == "6"||state == "7"}}
	<div class="row" style="margin-top:20px;margin-left: -20px;">
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
	</div>
	{{else state == "8"}}
	<div class="row" style="margin-top:20px;margin-left: -20px;">
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
	</div>
	{{/if}}
</script>

<script id="reply-message-items-tmpl" type="html/x-jsrender">
<div class="form-group row" id="reply-content-div">
	<textarea rows="2" name="reply-content" placeholder="请输入您的回复 ..." class="form-control c-square reply-content"></textarea>
</div>
<div class="form-group" id="reply-submit-div">
	<button type="submit" class="btn reply-submit-btn" id="reply-submit">提交回复</button>
</div>
</script>

<script id="task-content-tmpl" type="html/x-jsrender">

{{if task.tag}}
<div class="task-tag row"> 
	<ul>
		{{tagFormat task.tag/}}
	</ul>
</div>
{{/if}}
<div class="task-title row"> 
	<div class="title">
		<span>{{:task.title}}</span>
	</div>

	{{if task.userId === "${user_id_session_key}"}}
	<div class="dropdown" style="float: right;">
		<button type="button" class="btn btn-default dropdown-toggle nav-show" id="dropdownMenu1" data-toggle="dropdown">
      		<span class="glyphicon glyphicon-sort-by-attributes"></span>
   		</button>
   		<ul class="dropdown-menu pull-right" role="menu" aria-labelledby="dropdownMenu1">
			{{if task.state === "0"|| task.state === "1"}}
			<li role="presentation">
         		<a role="menuitem" tabindex="-1" class="task-edit" href="/dashboard/Task/edit.jsp?id={{base64Encode task.id/}}">修改任务</a>
      		</li>
			{{/if}}
		
			{{if task.state === "0"|| task.state === "1"}}
			<li role="presentation">
         		<a role="menuitem" tabindex="-1" class="task-end" href="javascript:void(0);">关闭任务</a>
      		</li>
			{{/if}}
			{{if task.state === "2"}}
			<li role="presentation">
         		<a role="menuitem" tabindex="-1" class="task-complete" href="javascript:void(0);">任务终止</a>
      		</li>
			{{/if}}
			{{if task.state === "5"|| task.state === "6"}}
			<li role="presentation">
				<a role="menuitem" tabindex="-1" class="task-pay" href="javascript:void(0);">全额支付</a>
			</li>
			{{/if}}
			<li role="presentation">
				<a role="menuitem" tabindex="-1" target="_blank" href="/dashboard/Task/pubDetail.jsp?id={{base64Encode task.id/}}">更多操作</a>
			</li>
	   </ul>
	</div>

	{{else}}
	<button type="button" class="btn follow-btn follow-cancel {{if task.follow=="1"}}{{else}}hide{{/if}}">取消关注</button>
	<button type="button" class="btn follow-btn follow {{if task.follow=="1"}}hide{{else}}{{/if}}"" >关注任务</button>
	{{/if}}
</div>

<div class="row user-info"> 
	
	<a href="javascript:;" class="user-pic" data-id="{{:task.userId}}">
		<img class="img-circle" src="{{:task.user.picUrl}}">
	</a>
	<a href="javascript:;" class="task-username user-pic" data-id="{{:task.userId}}">{{:task.userNickName}}</a>
	
	<a href="javascript:void(0)" class="show-grade" data-grade="{{:task.user.grade}}" data-align="bottom left">Lv{{calculateGrade task.user.grade/}}</a>
	<div class="star show-evaluate" data-userid="{{:task.user.id}}" data-evaluatecount="{{:task.user.evaluateCount}}" data-evaluatepublishcount="{{:task.user.evaluatePublishCount}}" data-honest="{{:task.user.honest}}" data-quality="{{:task.user.quality}}" data-punctual="{{:task.user.punctual}}" data-honestpublish="{{:task.user.honestPublish}}" data-align="bottom left">
		{{evaluateFormat task.user.evaluate task.user.evaluateCount task.user.evaluatePublish task.user.evaluatePublishCount/}}
	</div>	
	<div><span class="task-create-date">{{:task.createDate}}</span></div>

	{{if task.userId !== "${user_id_session_key}"}}  <!--haokun added 2017/02/27 不能和自己发送-->
	<a class="private-message-btn" data-toggle="tooltip" data-userid="{{base64Encode task.userId/}}" data-username="{{:task.userNickName}}" title="私信"><i class="fa fa-commenting-o" style="font-size:24px"></i></a>   <!--haokun added 2017/02/27 增加聊天标志-->
	{{/if}}   <!--haokun added 2017/02/27 不能和自己发送-->
</div>

<div class="row"> 
	<div class="task-content"> {{:task.content}}</div>
</div>

<div class="row"> 
	<div class="task-achieve"> 
		<i class="iconfont icon-info-warning"></i>
		<span class="achieve-tip">成果要求：</span>
		<pre class="achieve">{{:task.achieve}}</pre>
	</div>
</div>

<!--haokun added 2017/03/09 start 增加私密成果要求;只有中标人可见-->
{{for task.receiveDemands}}
{{if state==1 && (userId === "${user_id_session_key}" || #parent.parent.data.task.userId === "${user_id_session_key}" )}}
<div class="row"> 
	<div class="task-secret-achieve"> 
		<!--<i class="iconfont icon-info-warning"></i> -->
		<span class="secret-achieve-tip">私密成果要求：</span>
		<pre class="secret-achieve">{{:#parent.parent.parent.data.task.secretAchieve}}</pre>
	</div>
</div>
{{/if}}
{{/for}}
<!--haokun added 2017/03/09 end 增加私密成果要求-->

<div class="divider"></div>
{{if task.enclosure1}}
<div class="row"> 
	<div class="task-enclosure">
		<span class="enclosure-tip">附件：</span>
		<ul>
			{{if task.enclosure1}}
			<li><a target = "_blank" href="{{:task.enclosure1}}"><span>{{:task.enclosure1Name}}</span></a></li>
			{{/if}}
			{{if task.enclosure2}}
			<li><a target = "_blank" href="{{:task.enclosure2}}">{{:task.enclosure2Name}}</a></li>
			{{/if}}
			{{if task.enclosure3}}
			<li><a target = "_blank" href="{{:task.enclosure3}}">{{:task.enclosure3Name}}</a></li>
			{{/if}}
			{{if task.enclosure4}}
			<li><a target = "_blank" href="{{:task.enclosure4}}">{{:task.enclosure4Name}}</a></li>
			{{/if}}
			{{if task.enclosure5}}
			<li><a target = "_blank" href="{{:task.enclosure5}}">{{:task.enclosure5Name}}</a></li>
			{{/if}}
		</ul>
	</div>
	<div class="divider"></div>
</div>
{{/if}}

{{if task.ds.length > 0}}
<div class="row"> 
	<div class="specialty-list row">
		<div class="specialty-tip">技能要求:</div>
		<ul>
			{{for task.ds}}
			<li><div class="tag-item">{{:specialty.name}}</div></li>
			{{/for}}
		</ul>
	</div>
	<div class="divider margin-top-20"></div>
</div>
{{/if}}

{{if task.da.length > 0}}
<div class="row"> 
	<div class="attach-list">
		{{for task.da}}
		<div class="attach-tip row">附加任务{{:#index+1}}:</div>
		<div class="row"> 
			<div class="content"> {{:content}}</div>
		</div>
		<div class="row"> 
			<div class="attach-achieve"> 
				<i class="iconfont icon-info-warning"></i>
				<span class="attach-tip">成果要求：</span>
				<pre class="achieve">{{:achieve}}</pre>
			</div>
		</div>
		<div class="divider"></div>
		{{/for}}
	</div>
</div>
{{/if}}


{{if task.userId !== "${user_id_session_key}"&&(task.state==0||task.state==1)}}
<div class="row">
	{{if ~isShowBtn(task.receiveDemands)}}
	<button type="button" class="btn task-received">已投标任务...</button>
	{{else}}
	<button type="button" class="btn show-task-receive">我要接任务</button>
	{{/if}}
</div>

{{/if}}


{{if task.state == 2||task.state == 3||task.state == 5||task.state == 6||task.state == 7}}
<div class="row">
	<button type="button" class="btn task-starting">任务正在进行中...</button>
</div>
{{for task.receiveDemands}}
	{{if state==1}}
	<div class="row">
		<div class="col-md-6">
			<div class="left">
				<span>中标人</span>
			</div>
			<div class="right">
				<a href="/profile.jsp?userId={{base64Encode userId/}}" target="_blank">
					<img class="img-circle" src="{{:user.picUrl}}" style="height: 24px;width: 24px;"> 
					<span>{{:user.nickName}}</span>
				</a>
			</div>
		</div>
		<div class="col-md-6">
			<div style="float: left;width:30px;margin-left:30px;">
				<span class="show-grade" data-grade="{{:grade}}" data-align="bottom left" style="color: #999999;font-size: 12px;line-height: 24px;">Lv{{calculateGrade grade/}}</span>
			</div> 
			<div style="float:left;margin-left:30px;" class="star show-evaluate" data-userid="{{:user.id}}" data-evaluatecount="{{:user.evaluateCount}}" data-evaluatepublishcount="{{:user.evaluatePublishCount}}" data-honest="{{:user.honest}}" data-quality="{{:user.quality}}" data-punctual="{{:user.punctual}}" data-honestpublish="{{:user.honestPublish}}" data-align="bottom left">
				{{evaluateFormat user.evaluate user.evaluateCount user.evaluatePublish user.evaluatePublishCount/}}
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-6">
			<div class="left">
				<span>中标价格</span>
			</div>
			<div class="right">
				<span>\${{if amount}}{{:amount}}{{else}}{{:#parent.parent.parent.parent.data.task.amount}}{{/if}}</span>
			</div>
		</div>
		<div class="col-md-6">
			<div style="float: left;width:70px;margin-left:30px;">
				<span>承诺时长：</span>
			</div> 
			<div style="float:left;">
				{{if finishDay}}{{:finishDay}}天{{else}}无承诺{{/if}}
			</div>	
		</div>
	</div>

	<div class="row margin-bottom-30">
		<div class="col-md-12">
			<div class="left">
				<span>本任务成果评价:</span>
			</div>
			<div class="right">
				{{if evaluate}}
				<div class="show-task-evaluate" data-evaluate="{{:evaluate.evaluate}}" data-punctual="{{:evaluate.punctual}}" data-quality="{{:evaluate.quality}}">{{taskEvaluateFormat evaluate.evaluate evaluate.punctual evaluate.quality/}}</div>
				{{else}}
				暂未评价
				{{/if}}
			</div>
		</div>
		{{if evaluate}}
		<div class="col-md-12">
			<div class="left">
				<span>&nbsp;</span>
			</div>
			<div class="right">
				{{:evaluate.description}}
			</div>
		</div>
		{{/if}}
	</div>

	<div class="row margin-bottom-10">
		<div class="col-md-12">
			<div class="left">
				<span>对发单人的评级:</span>
			</div>
			<div class="right">
				{{if evaluateReceiver}}
				<div class="show-task-pub-evaluate" data-evaluate="{{:evaluateReceiver.evaluate}}">{{taskPubEvaluateFormat evaluateReceiver.evaluate/}}</div>
				{{else}}
				暂无
				{{/if}}
			</div>
		</div>
		{{if evaluateReceiver}}
		<div class="col-md-12">
			<div class="left">
				<span>&nbsp;</span>
			</div>
			<div class="right">
				{{:evaluateReceiver.description}}
			</div>
		</div>
		{{/if}}
	</div>

	<div class="margin-bottom-10"></div>
	{{/if}}
{{/for}}
{{/if}}

{{if task.state == 4}}
<div class="row">
	<button type="button" class="btn task-starting">任务已关闭</button>
</div>
{{/if}}

{{if task.state == 8}}
<div class="row">
	<button type="button" class="btn task-starting">任务已结束</button>
</div>

{{for task.receiveDemands}}
	{{if state==1}}
	<div class="row margin-bottom-10">
		<div class="col-md-6">
			<div class="left">
				<span>中标人</span>
			</div>
			<div class="right">
				<a href="/profile.jsp?userId={{base64Encode userId/}}" target="_blank">
					<img class="img-circle" src="{{:user.picUrl}}" style="height: 24px;width: 24px;"> 
					<span>{{:user.nickName}}</span>
				</a>
			</div>
		</div>
		<div class="col-md-6">
			<div style="float: left;width:30px;margin-left:30px;">
				<span class="show-grade" data-grade="{{:grade}}" data-align="bottom left" style="color: #999999;font-size: 12px;line-height: 24px;">LV{{calculateGrade grade/}}</span>
			</div> 
			<div style="float:left;margin-left:30px;" class="star show-evaluate" data-userid="{{:user.id}}" data-evaluatecount="{{:user.evaluateCount}}" data-evaluatepublishcount="{{:user.evaluatePublishCount}}" data-honest="{{:user.honest}}" data-quality="{{:user.quality}}" data-punctual="{{:user.punctual}}" data-honestpublish="{{:user.honestPublish}}" data-align="bottom left">
				{{evaluateFormat user.evaluate user.evaluateCount user.evaluatePublish user.evaluatePublishCount/}}
			</div>	
		</div>
	</div>

	<div class="row margin-bottom-10">
		<div class="col-md-6">
			<div class="left">
				<span>中标价格</span>
			</div>
			<div class="right">
				<span>\${{if amount}}{{:amount}}{{else}}{{:#parent.parent.parent.parent.data.task.amount}}{{/if}}</span>
			</div>
		</div>
		<div class="col-md-6">
			<div style="float: left;width:70px;margin-left:30px;">
				<span>承诺时长：</span>
			</div> 
			<div style="float:left;">
				{{if finishDay}}{{:finishDay}}天{{else}}无承诺{{/if}}
			</div>	
		</div>
	</div>

	<div class="row margin-bottom-30">
		<div class="col-md-12">
			<div class="left">
				<span>发单人评价:</span>
			</div>
			<div class="right">
				{{if evaluate}}
				<div class="show-task-evaluate" data-evaluate="{{:evaluate.evaluate}}" data-punctual="{{:evaluate.punctual}}" data-quality="{{:evaluate.quality}}">{{taskEvaluateFormat evaluate.evaluate evaluate.punctual evaluate.quality/}}</div>
				{{else}}
				暂未评价
				{{/if}}
			</div>
		</div>
		{{if evaluate}}
		<div class="col-md-12">
			<div class="left">
				<span>&nbsp;</span>
			</div>
			<div class="right">
				{{:evaluate.description}}
			</div>
		</div>
		{{/if}}
	</div>

	<div class="row margin-bottom-30">
		<div class="col-md-12">
			<div class="left">
				<span>接单人评价:</span>
			</div>
			<div class="right">
				{{if evaluateReceiver}}
				<div class="show-task-evaluate" data-evaluate="{{:evaluateReceiver.evaluate}}">{{taskPubEvaluateFormat evaluateReceiver.evaluate/}}</div>
				{{else}}
				暂未评价
				{{/if}}
			</div>
		</div>
		{{if evaluateReceiver}}
		<div class="col-md-12">
			<div class="left">
				<span>&nbsp;</span>
			</div>
			<div class="right">
				{{:evaluateReceiver.description}}
			</div>
		</div>
		{{/if}}
	</div>

	{{if rulePercent}}
	<div class="row margin-bottom-10">
		<div class="col-md-12">
			<div class="left">
				<span>裁决付款比例:</span>
			</div>
			<div class="right">
				<span>{{:rulePercent}}%</span>
			</div>
		</div>
	</div>
	{{/if}}

	{{if ruleReason}}
	<div class="row margin-bottom-10">
		<div class="col-md-12">
			<div class="left">
				<span>裁决理由:</span>
			</div>
			<div class="right">
				{{:ruleReason}}
			</div>
		</div>
	</div>
	{{/if}}

	<div class="margin-bottom-10"></div>
	{{/if}}
{{/for}}
{{/if}}

<div class="row hide receive-demand">
	<form action="#" method="post" >
		<div class="form-group">
			<div class="row">
				<div class="col-md-6">
					<span class="receive-amount-desc">我的报价($):</span>
					<input type="number" min="1" name="amount" placeholder="单位($),空则发单价" class="form-control receive-amount">
				</div>
				<div class="col-md-6">
					<span class="finish-day-desc">完成时长(天):</span>
					<input type="number" min="1" name="finishDay" placeholder="单位(天)" class="form-control finish-day">
				</div>
			</div>
		</div>
		<div class="form-group">
			<textarea rows="5" name="receive_remark"  placeholder="自我介绍一下吧！" class="form-control receive-remark"></textarea>
		</div>
		<div class="row">
        	<div class="location-share">
        		<input type="checkbox" name="location-open" />分享我的位置
       		</div>
			<div class="map-info">
				<input type="text" class="form-control hide" id="map-region" placeholder="请在地图上选择分享的位置...">
				<input type="hidden" class="form-control" id="map-district" value=""/>
				<input type="hidden" class="form-control" id="map-province" value=""/>
				<input type="hidden" class="form-control" id="map-country" value=""/>
				<input type="hidden" class="form-control" id="map-latitude" value="0"/>
				<input type="hidden" class="form-control" id="map-longitude" value="0"/>
     		</div>
			<div class="operate">
				<button type="button" class="btn close-task-receive">取消</button>
				<button type="submit" class="btn receive-submit">确认竞标</button>
			</div>
        </div>
         <div class="form-group hide">
         	<div id="map" class="col-md-12" style="height: 400px;margin-top: 10px;"></div>
     	</div>
	</form>
	<div class="divider margin-top-20"></div>
</div>

<div class="row">
	<div class="tabbable-custom">
		<ul class="nav nav-tabs ">
			<li class="active">
				<a href="#tab_15_2" data-toggle="tab" aria-expanded="false" id="task-receive-a">竞标人列表</a>
			</li>

			<!--   haokun modified 2017/02/27 屏蔽旧私信功能
			{{if task.userId !== "${user_id_session_key}"}}
			<li>
				<a href="#tab_15_0" data-toggle="tab" aria-expanded="false" > 发私信 </a>
			</li>
			{{/if}}
			-->
			
			<li>
				<a href="#tab_15_1" data-toggle="tab" aria-expanded="true" id="task-message-count"> 我要留言 ({{MessageCount task.demandMessages/}})</a>
			</li>
			
		</ul>
		<div class="tab-content">
			<!--  haokun modified 2017/02/27 屏蔽旧私信功能
			<div class="tab-pane" id="tab_15_0">
				<div class="blog-comments" style="display:block;">
					<form action="#" method="post" id="submit-message">
						<div class="form-group">
							<textarea rows="5" name="message-private" placeholder="输入您的私信内容 ..."
								class="form-control c-square message-content"></textarea>
						</div>
						<div class="row">
							<button type="submit" class="btn blue message-submit" data-userid="{{:task.userId}}">发送</button>
						</div>
					</form>
				</div>
			</div>
            -->

			<div class="tab-pane" id="tab_15_1">
				<div class="blog-comments" style="display:block;">
					<div class="c-comment-list" id="task-message-list">
						{{for task.demandMessages}}
						<div>
							<div class="media-left">
								<img class="img-circle user-pic" alt="" src="{{:userPicUrl}}" data-id='{{:userId}}'>
						    </div>
							<div class="media-body">
								<h4 class="media-heading">
									<a class="user-pic user-sender" data-id='{{:userId}}'>{{:userNickName}}</a>:
									<a href="{{:id}}" data-userid="{{:userId}}" name="message-reply" class="message-reply-a">回复</a>
								</h4>
								<span>{{:content}}</span> 
		
								{{for replyMessages}}
								<div class="media">
									<div class="media-left">
										<img class="img-circle user-pic" alt="" src="{{:userPicUrl}}" data-id='{{:userId}}'>
									</div>
									<div class="media-body">
  						  				<h4 class="media-heading">
											<a class="user-pic user-sender" data-id="{{:userId}}">{{:userNickName}}</a>  
											<a class="user-pic" data-id="{{:receiverId}}">@{{:receiverNickName}}</a>:
											<a href="{{:#parent.parent.data.id}}" data-userid="{{:userId}}" name="message-reply" class="message-reply-a">回复</a>
  						  				</h4> 
										<span>{{:content}}</span>
  						  			</div>
								</div>
								{{/for}}
							</div>
						</div>
						{{/for}}
					</div>
					<form action="#" method="post" id="submit-task-message" data-userid="{{:task.userId}}">
						<div class="form-group">
							<textarea rows="8" name="message" placeholder="输入您的留言 ..."
								class="form-control c-square message-content"></textarea>
						</div>
						<div class="form-group">
							<button type="submit" class="btn blue receive-submit">留言</button>
						</div>
					</form>
				</div>
			</div>

			<div class="tab-pane active" id="tab_15_2">
                <table class="table table-striped table-bordered table-hover table-checkable order-column" id="sample_1">
				  {{if task.userId === "${user_id_session_key}"&&(task.state==0||task.state==1)}} 
              		<thead>
                 		<tr>
                        	<th>竞标人 </th>
                         	<th width="100px"> 报价($)</th>
                          	<th width="100px"> 完成时长(天) </th>
	                        <th width="75px">私信</th>   <!--haokun modified 2017/02/28 把width从100px 改为75px-->
							<th width="75px">匹配</th>   <!--haokun modified 2017/02/28 把width从100px 改为75px-->
                        </tr>
                  	</thead>
                    <tbody>		  
						{{for task.receiveDemands}}
						<tr class='odd gradeX item-rd'> 
                        	<td>
								<div class="item-rd-pic">
									<a href="/profile.jsp?userId={{base64Encode user.id/}}" target="_blank" ><img class="img-circle" src="{{:user.picUrl}}"></a>
								</div>
								<div class="item-rd-username user-pic-info"> 
									<a href="/profile.jsp?userId={{base64Encode user.id/}}" target="_blank" ><span>{{:user.nickName}}</span></a>
								</div>
								<div class="item-rd-grade hidden-xs">
									<span class="show-grade" data-grade="{{:grade}}" data-align="bottom left">Lv.{{calculateGrade grade/}}</span>
								</div>
								<div class="star show-evaluate item-rd-evaluate hidden-xs" data-userid="{{:user.id}}" data-evaluatecount="{{:user.evaluateCount}}" data-evaluatepublishcount="{{:user.evaluatePublishCount}}" data-honest="{{:user.honest}}" data-quality="{{:user.quality}}" data-punctual="{{:user.punctual}}" data-honestpublish="{{:user.honestPublish}}" data-align="bottom left">
									{{evaluateFormat user.evaluate user.evaluateCount user.evaluatePublish user.evaluatePublishCount/}}
								</div>	

								{{if user.pro>0}}
								<div class="item-rd-pro hidden-xs">
									<a href="javascript:;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">Pro</a>
								</div>
								{{/if}}
							</td>
                         	<td>{{if amount}}{{:amount}}{{else}}{{:#parent.parent.parent.data.task.amount}}{{/if}}</td>
                          	<td>{{if finishDay}}{{:finishDay}}{{/if}}</td>

							<td>
							{{if user.id !== "${user_id_session_key}"}}  <!--haokun added 2017/02/27 不能和自己发送-->
							<a class="private-message-btn receiver-private-message-btn" data-toggle="tooltip" data-userid="{{base64Encode user.id/}}" data-username="{{:user.nickName}}" title="私信"><i class="fa fa-commenting-o" style="font-size:24px"></i></a>
							{{/if}}
							</td>      
							<!--haokun modified 2017/02/28 修改聊天标志-->
                            <td><a class="btn task-select" data-id="{{:id}}" style="margin-top:4px;margin-left:-10px;float:left;" data-amount="{{:amount}}" data-toggle="tooltip" title="匹配"><i class="fa fa-handshake-o" style="font-size:24px"></i></a></td>   <!--haokum modified-->
                        </tr>
						{{/for}}

						{{if task.receiveDemands.length==0}}
						<tr class='odd gradeX item-rd'>
                        	<td colspan="5">   <!--haokun modified 2017/02/27-->
								暂无竞标
							</td>
                        </tr>
						{{/if}}
                  	</tbody>
				  {{else}}	
				    <thead>
                 		<tr>
                        	<th>竞标人 </th>
                         	<th width="100px"> 报价($)</th>
                          	<th width="100px"> 完成时长(天) </th>
							<th width="100px">私信</th>
                        </tr>
                  	</thead>
                    <tbody>		  
						{{for task.receiveDemands}}
						<tr class='odd gradeX item-rd'>
                        	<td>
								<div class="item-rd-pic">
									<a href="/profile.jsp?userId={{base64Encode user.id/}}" target="_blank" ><img class="img-circle" src="{{:user.picUrl}}"></a>
								</div>
								<div class="item-rd-username">
									<a href="/profile.jsp?userId={{base64Encode user.id/}}" target="_blank" ><span>{{:user.nickName}}</span></a>
								</div>
								<div class="item-rd-grade hidden-xs">
									<span class="show-grade" data-grade="{{:grade}}" data-align="bottom left">Lv.{{calculateGrade grade/}}</span>
								</div>
								<div class="star show-evaluate item-rd-evaluate hidden-xs" data-userid="{{:user.id}}" data-evaluatecount="{{:user.evaluateCount}}" data-evaluatepublishcount="{{:user.evaluatePublishCount}}" data-honest="{{:user.honest}}" data-quality="{{:user.quality}}" data-punctual="{{:user.punctual}}" data-honestpublish="{{:user.honestPublish}}" data-align="bottom left">
									{{evaluateFormat user.evaluate user.evaluateCount user.evaluatePublish user.evaluatePublishCount/}}
								</div>	

								{{if user.pro>0}}
								<div class="item-rd-pro hidden-xs">
									<a href="javascript:;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">Pro</a>
								</div>
								{{/if}}
							</td>
                         	<td>{{if amount}}{{:amount}}{{else}}{{:#parent.parent.parent.data.task.amount}}{{/if}}</td>
                          	<td>{{if finishDay}}{{:finishDay}}{{/if}}</td>
							<td>
							{{if user.id !== "${user_id_session_key}"}}  <!--haokun added 2017/02/27 不能和自己发送-->
							    <a class="private-message-btn receiver-private-message-btn" data-toggle="tooltip" data-userid="{{base64Encode user.id/}}" data-username="{{:user.nickName}}"  title="私信"><i class="fa fa-commenting-o" style="font-size:24px"></i></a>
							{{/if}}
							</td>      <!--haokun added 2017/02/27 增加聊天标志-->
                        </tr>
						{{/for}}

						{{if task.receiveDemands.length==0}}
						<tr class='odd gradeX item-rd'>
                        	<td colspan="4">    <!--haokun modified 2017/02/27-->
								暂无竞标
							</td>
                        </tr>
						{{/if}}
                  	</tbody>
				  {{/if}}
           		</table>

			</div>
			
		</div>
	</div>
</div>

</script>

<script id="task-relvant-item" type="html/x-jsrender">
{{for dataList}}
<li>
	<div><a href="/task/detail.jsp?id={{base64Encode id/}}" target = "_blank"><span class="task-amount">{{:title}}</span><a></div>
</li>
{{/for}}
</script>	

<script id="show-task-evaluate-tmpl" type="html/x-jsrender">
<div class="task-evaluate">
	<div class="row margin-top-10">
		<div class="left">
			诚信
		</div>
		<div class="middle">
			{{evaluateStarFormat evaluate/}}
		</div>
		<div class="right">
			{{:evaluate}}
		</div>
	</div>
	{{if quality}}
	<div class="row margin-top-10">
		<div class="left">
			质量
		</div>
		<div class="middle">
			{{evaluateStarFormat quality/}}
		</div>
		<div class="right">
			{{:quality}}
		</div>
	</div>
	{{/if}}
	{{if punctual}}
	<div class="row margin-top-10">
		<div class="left">
			准时
		</div>
		<div class="middle">
			{{evaluateStarFormat punctual/}}
		</div>
		<div class="right">
			{{:punctual}}
		</div>
	</div>
	{{/if}}
</div>
</script>	

<script id="show-task-pub-evaluate-tmpl" type="html/x-jsrender">
<div class="task-evaluate">
	<div class="row margin-top-10">
		<div class="left">
			诚信
		</div>
		<div class="middle">
			{{evaluateStarFormat evaluate/}}
		</div>
		<div class="right">
			{{:evaluate}}
		</div>
	</div>
</div>
</script>	


<!--haokun added-->	
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
							<div class="pro" style="border-radius: 10px;width: 30px;height: 12px;margin-left: 5px;float: left;text-align: center;margin-top: 5px;">
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

<script id="tip-dlg1" type="html/x-jsrender">
<div class="tip-dlg-content1">
	<div class="row title">
		<span>系统提示</span>
	</div>
	<div class="row content">
		<i class="iconfont icon-info-warning"></i>
		<span>温馨提示，您已成功接收任务，请耐心等待匹配结果。</span>
	</div>
	<div class="row operate">
		<button type="button" class="btn">我知道了</button>
	</div>
</div>
</script>
	
<!--haokun added-->	
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
							<div class="pro" style="border-radius: 10px;width: 30px;height: 12px;margin-left: 5px;float: left;text-align: center;margin-top: 5px;">
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
			<input type="hidden" name="amount_1" value="{{creditPayAmountFormat amount/}}"/>   <!--haokun modified 2017/03/09 增加格式creditpayamountforFormat-->
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
	    	<div class="col-md-3 col-sm-3 col-xs-4">
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
	    	<div class="col-md-3 col-sm-3 col-xs-4">
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
	    	<div class="col-md-3 col-sm-3 col-xs-4">
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
	    	<div class="col-md-3 col-sm-3 col-xs-4">
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
	    	<div class="col-md-3 col-sm-3 col-xs-4">
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
	    	<div class="col-md-3 col-sm-3 col-xs-4">
	        	<span class="left">实际付款总额<span class="font-red">*</span>：</span>
	        </div>
	        <div class="col-md-3 col-sm-3 col-xs-3">
	        	<span class="total-amount" data-amount="{{creditPayAmountFormat amount/}}">$ {{creditPayAmountFormat amount/}}</span>   <!--haokun modified 2017/03/09 增加格式creditpayamountforFormat-->
	        </div>
	       	<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    </div>
	   	<div class="row margin-top-20">
			<div class="col-md-1 col-sm-1 col-xs-1"> </div>
	    	<div class="col-md-3 col-sm-3 col-xs-4">
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


<script id="private-message-tmpl" type="html/x-jsrender">
    <div class="private-message">
		<div class="row">
			<textarea class="form-control message-content" rows="6"
						placeholder="私信内容..."></textarea>
		</div>
		<div class="row margin-top-20">
			<a href="/dashboard/Account/messages.jsp?name=&userId={{:userIdEncoded}}/#tab_1_3" class="to-dialg hide"><i class="iconfont icon-liuyan"></i><span>发起对话</span></a>
					<button class="submit-btn" data-friendid="{{:userId}}">发送</button>
		</div>
	</div>
</script>	

<%@ include file="/include/footer.jsp"%>

<!-- END PAGE LEVEL SCRIPTS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
	
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src='//maps.google.com/maps/api/js?libraries=places&key=AIzaSyDTF_j2DcmcP8A7llS_Y2mnqvWtTO5ejSU&sensor=false' async type="text/javascript"></script>
<script src="/assets/global/plugins/locationpicker.jquery.js"></script>
<script src="/assets/pages/scripts/task-detail.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
