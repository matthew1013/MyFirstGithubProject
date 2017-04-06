<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf8" pageEncoding="UTF-8"%>
<%
	String tag = request.getParameter("tag");
	if(tag!=null&&tag!=""){
		tag = new String(tag.getBytes("iso-8859-1"), "utf-8");  
	}else{
		tag = "";
	}
	
%>

	<%@ include file="/include/header.jsp"%>
	<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/index.css" />
	<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/header-video.css" />
	
	<% 
		String title = "";
		String keywords = "";
		String description = "";
		Seo s = SeoUtil.getSeoByWebFlag("0");
		if(s != null){
			title = s.getTitle();
			keywords = s.getKeywords();
			description = s.getDescription();
		}
	%>
	<input type="hidden" name="seoTitle" value="<%=title %>">
	<input type="hidden" name="seoKeywords" value="<%=keywords %>">
	<input type="hidden" name="seoDescription" value="<%=description %>">
	<input type="hidden" name="paramTag" value="<%=tag %>">
	<input type="hidden" name="paramTagType" value="${param.tagType }">
	<input type="hidden" name="paramMinAmount" value="${param.minAmount }">
	<input type="hidden" name="paramMaxAmount" value="${param.maxAmount }">
	
	<div style="height:70px"></div>
	<%
		if (sessionValue == null || sessionValue.equals("")) {
	%>
		<%@ include file="/include/header-video.jsp"%>
	<%
		}
	%>
	<!--content-->
	<div class="container"> 
		<div class="row">
			<!-- 任务查找页面 start 111111111111111-->
			<div class="col-md-8 col-lg-8 margin-left-right-10">
				<div class="search-bar row" id="region">
					<div class="search-type row">
						<ul>
							<li class="active"><a href="javascript:;" data-type="task">任务</a></li>
							<li><a href="javascript:;" data-type="user">人物</a></li>
						</ul>
					</div>
					<div class="search-line row">
						<div class="input-icon1 ">
							<i class="glyphicon glyphicon-search"></i>
							<input class="search-key" type="text" placeholder="请输入任务名称关键字或所在地" value="${param.keyword }"/>
						</div>
						<!----haokun added 2017/03/13 start 增加了一个空的elment控制边框-->
						<div class="pseudo-div">
						</div>
						<!----haokun added 2017/03/13 end 增加了一个空的elment控制边框-->
						<div class="search-div">
							<button class="search-button"><img src="/assets/pages/img/index/search-icon.png" height="36px" width="36px" /></button>    <!--haokun modified 2017/03/13 把“搜索“换成图片”-->
						</div>
					</div>
					
					<div class="area row">
						<span class="title">地区：</span> 
						<div class="nationality">
							<input type="radio" name="nationality" value="" checked>
							<span class="area-font">不限</span>
						</div>
						<div class="nationality">
							<input type="radio" name="nationality" value="us">
							<span class="area-font">美国</span>
						</div>
						<div class="nationality">
							<input type="radio" name="nationality" value="cn">
							<span class="area-font">中国</span>
						</div>
						<div class="nationality">
							<input type="radio" name="nationality" value="uk">
							<span class="area-font">英国</span>
						</div>
						<div class="nationality">
							<input type="radio" name="nationality" value="au">
							<span class="area-font">澳洲</span>
						</div>
						<div class="nationality">
							<input type="radio" name="nationality" value="ca">
							<span class="area-font">加拿大</span>
						</div>
						<div class="nationality">
							<input type="radio" name="nationality" value="other">
							<span class="area-font">其他</span>
						</div>
					</div>
					<div class="line row"></div>
					<div class="tag row" >
						<span class="title">热门标签：</span>
						<div>
							<a href="javascript:void(0)" name="tag-a" data-tag="论文"><div class="tag-item margin-right-10">#论文</div></a>
			               <a href="javascript:void(0)" name="tag-a" data-tag="卖车"><div class="tag-item margin-right-10">#卖车</div></a>
			               <a href="javascript:void(0)" name="tag-a" data-tag="旅游"><div class="tag-item margin-right-10">#旅游</div></a>
			               <a href="javascript:void(0)" name="tag-a" data-tag="留学"><div class="tag-item margin-right-10">#留学</div></a>
			               <a href="javascript:void(0)" name="tag-a" data-tag="数学"><div class="tag-item margin-right-10">#数学</div></a>
			               <a href="javascript:void(0)" name="tag-a" data-tag="摄影"><div class="tag-item margin-right-10">#摄影</div></a>
						</div> 
						<div class="refresh">
							<a href="javascript:void(0)" name="tag-refresh"><i class="glyphicon glyphicon-refresh"></i><span>换一组</span></a>
						</div>
					</div> 
				</div>
				
				
				<!-- 当前查找条件 start -->
				<div class="row search-result">
					<div class="task-all hidden-xs">
						<a href="javascript:void(0)">全部结果：</a>
					</div>
					<div class="line hidden-xs">
						<div></div>
					</div>
					<div class="type dropdown">
						<a class="dropdown-toggle curr-type" data-toggle="dropdown" href="javascript:void(0)" data-type="">
							<span>分类:</span>
							<span class="curr-type-name">所有</span>
							<b class="caret"></b>
						</a>
						<ul class="dropdown-menu type-menu" role="menu" aria-labelledby="location-label">
							<li role="presentation"><a href="" class="dropdown-type" role="menuitem" tabindex="-1">所有</a></li>
							<li role="presentation"><a href="1" class="dropdown-type" role="menuitem" tabindex="-1">学习类</a></li>
							<li role="presentation"><a href="2" class="dropdown-type" role="menuitem" tabindex="-1">生活类</a></li>
							<li role="presentation"><a href="3" class="dropdown-type" role="menuitem" tabindex="-1">工作类</a></li>
						</ul>
					</div>
					<div class="line">
						<div></div>
					</div>
					<div class="location dropdown">
						<a class="dropdown-toggle curr-location" data-toggle="dropdown" href="javascript:void(0)" data-location="">
							<span>地区:</span>
							<span class="curr-location-name">不限</span>
							<b class="caret"></b>
						</a>
											 
						<ul class="dropdown-menu" role="menu" aria-labelledby="location-label">
							<li role="presentation"><a href="" class="dropdown-location" role="menuitem" tabindex="-1">不限</a></li>
							<li role="presentation"><a href="us" class="dropdown-location" role="menuitem" tabindex="-1">美国</a></li>
							<li role="presentation"><a href="cn" class="dropdown-location" role="menuitem" tabindex="-1">中国</a></li>
							<li role="presentation"><a href="uk" class="dropdown-location" role="menuitem" tabindex="-1">英国</a></li>
							<li role="presentation"><a href="au" class="dropdown-location" role="menuitem" tabindex="-1">澳洲</a></li>
							<li role="presentation"><a href="ca" class="dropdown-location" role="menuitem" tabindex="-1">加拿大</a></li>
						</ul>
					</div>
					<div class="line">
						<div></div>
					</div>
					<div class="tag-div">
					</div>
					<div class="line hide">
						<div></div>
					</div>
					<div class="search-key-div hide">
						<a href="javascript:void(0);">
							<b></b>
							<i class="glyphicon glyphicon-remove"></i>
						</a>
					</div>
					
					<!-- 排序 start -->
					<div class="sorting dropdown">
						<a class="dropdown-toggle curr-sorting" data-toggle="dropdown" href="javascript:void(0)" data-sort="1">
							<span>排序:</span>
							<span class="curr-sorting-name">不限</span>
							<b class="caret"></b>
						</a>
											 
						<ul class="dropdown-menu" data-toggle="dropdown" role="menu" aria-labelledby="location-label">
							<li role="presentation"><a href="0" role="menuitem" tabindex="-1">默认推荐</a></li>  
					     	<li role="presentation"><a href="1" role="menuitem" tabindex="-1">最新任务</a></li>  
					    	<li role="presentation"><a href="2" role="menuitem" tabindex="-1">最热任务</a></li>  
					      	<li role="presentation"><a href="3" role="menuitem" tabindex="-1">报酬高</a></li>  
					      	<li role="presentation"><a href="4" role="menuitem" tabindex="-1">快结束</a></li>   
						</ul>
					</div>
					
      				<!-- 排序 end -->
				</div>

				<!-- 当前查找条件 end -->
				
				<!-- 当前查找条件 start -->
				<div class="row curr-search hide">
					<div class="crumbs-nav">
						<div class="crumbs-nav-main clearfix">
							<div class="crumbs-nav-item">
								<div class="crumbs-first">
									<a href="javascript:void(0)" name="all-task" style="color: #269aff;">全部结果</a>
								</div>
							</div>
							
							<i class="crumbs-arrow">&gt;</i>
							<div class="crumbs-nav-item">
							 
								<div class="menu-drop">
									<div class="trigger">
										<a class="dropdown-toggle curr curr-type" data-toggle="dropdown" href="javascript:void(0)" data-type="">分类:<span class="curr-type-name">所有</span><b class="caret"></b></a>
										
										<ul class="dropdown-menu pull-left" style="top:initial;" role="menu" aria-labelledby="dropdownMenu1">
											<li role="presentation"><a href="" class="dropdown-type">所有</a></li>
											<li role="presentation"><a href="1" class="dropdown-type">学习类</a></li>
											<li role="presentation"><a href="2" class="dropdown-type">生活类</a></li>
											<li role="presentation"><a href="3" class="dropdown-type">工作类</a></li>
										</ul>
									</div>
									
								</div>
							</div>
							
							<div class="dropdown-type-second-div hide">
								<i class="crumbs-arrow">&gt;</i>
								<div class="crumbs-nav-item">
								 
									<div class="menu-drop">
										<div class="trigger">
											<a class="dropdown-toggle curr curr-type-second" data-toggle="dropdown" href="javascript:void(0)" data-typesecond=""><span class="curr-type-name">学习类</span>:<span class="curr-type-second-name">所有</span><b class="caret"></b></a>
											<ul class="dropdown-menu" id="type-second-ul" style="top:initial;" role="menu" aria-labelledby="type-second-label">
												
											</ul>
										</div>
										
									</div>
								</div>
							</div>
							
								
							<div>
								<i class="crumbs-arrow">&gt;</i>
								<div class="crumbs-nav-item">
									<div class="menu-drop">
										<div class="trigger">
											<a id="location-label" class="dropdown-toggle curr curr-location" data-toggle="dropdown" href="javascript:void(0)" data-location="">地区:<span class="curr-location-name">不限</span><b class="caret"></b></a>
											 
											<ul class="dropdown-menu" style="top:initial;" role="menu" aria-labelledby="location-label">
												<li role="presentation"><a href="" class="dropdown-location">不限</a></li>
												<li role="presentation"><a href="us" class="dropdown-location">美国</a></li>
												<li role="presentation"><a href="cn" class="dropdown-location">中国</a></li>
												<li role="presentation"><a href="uk" class="dropdown-location">英国</a></li>
												<li role="presentation"><a href="au" class="dropdown-location">澳洲</a></li>
												<li role="presentation"><a href="ca" class="dropdown-location">加拿大</a></li>
											</ul>
										</div>
											
									</div>
								</div>
							</div>
							
							
							<div class="search-key-div">
								<i class="crumbs-arrow">&gt;</i>
								<div class="crumbs-nav-item">
									<strong>"<span class="search-key-span"></span>"</strong>
								</div>
							</div> 
						</div>
					</div>
				</div>
				<!-- 当前查找条件 end -->
				
				<!-- 查找人物  or 任务 start -->
				<!-- 
				<div class="search-type row hide">
					<div class="col-xs-6 col-sm-6 col-dm-6 col-lg-6 active" data-type="task">
						<a href="javasrcipt:void(0);" name="search-type""><span>任务</span></a>
					</div>
					<div class="col-xs-6 col-sm-6 col-dm-6 col-lg-6"  data-type="user">
						<a href="javasrcipt:void(0);" name="search-type"><span>人物</span></a>
					</div>
				</div>
				 -->
				<!-- 查找人物  or 任务 end -->
				
				<!-- 分类 start -->
				<div class="category row hide">
					<div class="type">	
						<table>
							<thead>
								<tr style="line-height: 40px;">
									<th class="type-active" data-type="">所有</th>
									<th data-type="1">学习类</th>
									<th data-type="2">生活类</th>
									<th data-type="3">工作类</th>
								</tr>
							</thead>
						</table>
					</div>
					<!-- 分类 end -->
					
					<!-- 排序 start -->
					<div class="dropdown sorting">  
						<button class="btn dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown">  
					    	<span class="sorting-active" data-sort="1">最新任务</span>   <span class="caret" style="margin-left: 25px;"></span>  
					  	</button>  
					  	<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" >  
					   		<li role="presentation"><a href="0" role="menuitem" tabindex="-1">默认推荐</a></li>  
					     	<li role="presentation"><a href="1" role="menuitem" tabindex="-1">最新任务</a></li>  
					    	<li role="presentation"><a href="2" role="menuitem" tabindex="-1">最热任务</a></li>  
					      	<li role="presentation"><a href="3" role="menuitem" tabindex="-1">报酬高</a></li>  
					      	<li role="presentation"><a href="4" role="menuitem" tabindex="-1">快结束</a></li>   
					   	</ul>  
					</div>
					<div class="sorting-desc"><span>排序：</span></div>  
      				<!-- 排序 end -->
				</div>
				
				
				<!-- 任务 or 人物内容 start -->
				<div class="task-content row"></div> 
				
				<div class="search-pagination">
					<ul class="pagination" id="pageSplit">
					</ul>
				</div>
				<!-- <div class="row task-more">
					<a href="javasrcipt:void(0);"><div class="content-click row">点击加载更多</div></a>
					<div class="search-pagination">
						<ul class="pagination" id="pageSplit">
							<li class="first disabled">
								<a href="#">首页</a>
							</li>
							<li class="prev disabled">
								<a href="#">上一页</a>
							</li>
							<li class="page active">
								<a href="#">1</a>
							</li><li class="page"><a href="#">2</a></li><li class="page"><a href="#">3</a></li><li class="page"><a href="#">4</a></li><li class="page"><a href="#">5</a></li><li class="next"><a href="#">下一页</a></li><li class="last"><a href="#">最后一页</a></li></ul>
					</div>
				</div> -->
				<!-- 任务 or 人物内容 end -->
			</div>
			<!-- 任务查找页面 end -->

			<!-- 任务查找右边 start -->
			<div class="col-md-4 col-lg-4 margin-left-right-10">
				<div>
					<!-- 当前悬赏金额和任务量 start -->
					<div class="money-task row">
						<div class="money-amount">
							<div class="money-amount-1">
								<i class="iconfont icon-money"></i>
								<span>悬赏金额</span>
							</div>
							<div class="money-amount-2"></div>
						</div>
						<div class="vertical-line"></div>
						<div class="task-amount">
							<div class="task-amount-1">
								<i class="iconfont icon-task-count"></i>
								<span>任务量</span>
							</div>
							<div class="task-amount-2"></div>
						</div>
					</div>
					<!-- 当前悬赏金额和任务量 end -->

					<!-- 名人堂 start -->
					<div class="famous-people row">
						<!-- 
						<div class="famous-people-header row">
							<div class="right-title">名人堂</div>
							<div class="more">
								<a href="/user/famousMore.jsp" target="_blank">更多</a> 
							</div>
						</div>
						 -->
						<ul class="nav nav-tabs">
							<li class="active"><a href="#famous-people" data-more="/user/famousMore.jsp" data-toggle="tab" aria-expanded="false">名人堂 </a></li>
							<li class=""><a href="#pro-user" data-more="/dashboard/Pro/list.jsp" data-toggle="tab" aria-expanded="true">大牛</a></li>
							<div class="more">
								<a href="/user/famousMore.jsp" target="_blank">更多</a> 
							</div>
						</ul>
						<div class="tab-content">
							<div class="tab-pane fade  active in" id="famous-people">
								<div class="famous-people-list"></div>
							</div>
							
							
							<div id="pro-user" class="pro tab-pane fade"> 
								<div class="pro-user-list"></div>
							</div>
						</div>
					</div>
					<!-- 名人堂 end -->
					
					<!-- 活动 start -->
					<div class="activity row">
						
					</div>
					<!-- 活动 end -->
					
					<!-- 新闻 start -->
					<div class="news row">

						<div class="news-header">
							<div class="right-title">资讯分享</div>
							<div class="more">
								<a href="/new/list.jsp" target="_blank">更多</a> 
							</div>
						</div>
						<div class="new-list">
							
						</div>
					</div>
					<!-- 新闻 end -->

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

<script type="html/x-jsrender" id="famous-people-tmpl">
{{for dataList}}
	<div class="famous-people-content">
		<div class="left">
			<div class="famous-people-img">
				<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">
					<img src="{{:picUrl}}">
				</a>
			</div>
			<!-- <div>
				<span class="show-grade famous-people-grade" data-grade="{{:grade}}" data-align="bottom right" data-left="353">Lv{{calculateGrade grade/}}</span>
			</div> -->
		</div>
		<div class="right"> 
			<div class="row"> 
				<div class="famous-people-name">
					<a href="javascript:void(0)" class="user-pic" data-id="{{:id}}" title="{{:nickName}}">{{:nickName}}</a>
				</div>
				<div class="show-grade user-grade"  data-grade="{{:grade}}" data-align="bottom right" data-left="353"><span>Lv{{calculateGrade grade/}}</span></div>
				{{if pro>0}}
				<div class="pro">
					<a href="javascript:;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">Pro</a>
				</div>
				{{/if}}
			</div>
			<div class="row margin-top-8"> 
				

				<div class="famous-evaluate show-evaluate" data-userid="{{:id}}" data-evaluatecount="{{:evaluateCount}}" data-evaluatepublishcount="{{:evaluatePublishCount}}" data-honest="{{:honest}}" data-quality="{{:quality}}" data-punctual="{{:punctual}}" data-honestpublish="{{:honestPublish}}" data-align="bottom right" data-left="353">
					{{evaluateFormat evaluate evaluateCount evaluatePublish evaluatePublishCount/}}
				</div>
				<div>
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_2" target="_blank"><span class="evaluate-count">（{{evaluateCountFormat evaluateCount evaluatePublishCount/}}）</span></a>
				</div>
			</div>
			{{if occupation}}
			<div class="row margin-top-8"> 
				<div class="famous-people-major">
					<a href="javascript:;"  title="{{:occupation}}">{{:occupation}}</a>
				</div>
			</div>
			{{/if}}
			<div class="row"> 
				<div class="famous-people-brief" title="{{:brief}}">{{:brief}}</div>
			</div>
		</div>
	</div>
{{/for}}								
</script>

<script type="html/x-jsrender" id="pro-user-tmpl">
{{for dataList}}
	<div class="famous-people-content">
		<div class="left">
			<div class="famous-people-img">
				<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">
					<img src="{{:picUrl}}">
				</a>
			</div>
		</div>
		<div class="right"> 
			<div class="row"> 
				<div class="famous-people-name">
					<a href="javascript:void(0)" class="user-pic" data-id="{{:id}}" title="{{:nickName}}">{{:nickName}}</a>
				</div>
			</div>
			<div class="row margin-top-8">
				<ul>
					{{for pu}}
					<li><a href="javascript:void(0)" name="pro-a"><div class="pro-item"><span>{{:proName}}</span></div></a></li>
					{{/for}}
				</ul>
			</div>
			<div class="row">
				<i class="iconfont icon-map"></i>
				<span class="location">{{countryFormat country/}}</span>
				<span class="occupation">{{:occupation}}</span>
			</div>
			
			<div class="row"> 
				<div class="famous-people-brief" title="{{:brief}}">{{:brief}}</div>
			</div>
		</div>
	</div>
{{/for}}								
</script>

<script type="html/x-jsrender" id="activity-tmpl">
<div id="myCarousel" class="carousel slide">
	<!-- 轮播（Carousel）指标 -->
	<ol class="carousel-indicators">
		{{for dataList}}
			<li data-target="#myCarousel" data-slide-to="{{:#index}}" class="{{if #index==0}}active{{/if}}"></li>
		{{/for}}
	</ol>   
	<!-- 轮播（Carousel）项目 -->
	<div class="carousel-inner">
		{{for dataList}}
		<div class="item {{if #index==0}}active{{/if}}" data-title="{{:title}}" data-time="{{datetimeFormat createDate/}}">
			<a href="/activity-detail.jsp?id={{:id}}" target= "_blank"><img src="{{:coverImg}}" alt="First slide"></a>
		</div>
		{{/for}}

	</div>
	<!-- 轮播（Carousel）导航 -->
	<a class="carousel-control left" href="#myCarousel" 
	   data-slide="prev">&lsaquo;</a>
	<a class="carousel-control right" href="#myCarousel" 
	   data-slide="next">&rsaquo;</a>
</div> 
{{if dataList}}
<div class="row title">
	<a href="/activity-detail.jsp?id={{:dataList[0].id}}" target= "_blank"><span>{{:dataList[0].title}}</span></a>
</div>
<div class="row time">
	<span>{{datetimeFormat dataList[0].createDate/}}</span>
</div>
{{/if}}
</script>



<script type="html/x-jsrender" id="task-item-tmpl">
{{for dataList}}
	<div class="content-item row">
		<div class="row">
			<div class="col-xs-9 col-sm-9 col-dm-9 col-lg-9">
				<div class="title">
					<a href="/task/detail.jsp?id={{base64Encode id/}}" target="_blank" title="{{:title}}">{{substrOfTitle title/}}</a>
				</div>
				<div class="type">
					{{typeFormat type/}}
				</div>
			</div>
			<div class="col-xs-3 col-sm-3 col-dm-3 col-lg-3">
				<div class="task-amount">
					
					<span>
						{{amountFormat amount/}}
					</span>
					
				</div>
			</div>
		</div>
		
		<div class="row time" style="">
     		<span>发布时间：</span><span class="create-date">{{:createDate/}}</span>
			<span style="margin-left: 30px;">截止时间：</span><span class="expire-date">{{expireDateFormat expireDate/}}</span>
  			{{if enclosure1}}
				<span class="glyphicon glyphicon-paperclip"></span>
			{{/if}}
			{{if state==2||state==3||state==5||state==6||state==7}}
				<span style="margin-left: 30px;color: #0088ff;">进行中...</span>
			{{/if}}
  		</div>
		<div class="row">
			<div class="content">{{substrOfContent content/}} </div>
		</div>
		{{if tag}}
		<div class="row">
			<div class="task-tag">{{tagFormat tag/}}</div>
		</div>
		{{/if}}
		<div class="row content-bottom">
			<div class="content-person">
				<a href="javascript:void(0);" class="user-pic" data-id="{{:user.id}}">
					<img class="img-circle" src="{{:user.picUrl}}">
				</a>
				<div class="username">
					<a href="javascript:void(0)" class="user-pic" data-id="{{:user.id}}" title="{{:user.nickName}}">{{:user.nickName}}</a>
				</div>
			</div>
			<div class="grade">
				<a href="javascript:void(0)" class="show-grade" data-grade="{{:user.grade}}" data-align="bottom left">Lv{{calculateGrade user.grade/}}</a>
			</div>

			<div class="pro" {{if user.pro<1}}style="visibility:hidden;"{{/if}}>
				<a href="javascript:;" class="tooltip-test" data-toggle="tooltip" title="PRO：大牛级别人物">Pro</a>
			</div>

			<div class="evaluate">
				<div class="star show-evaluate" data-userid="{{:user.id}}" data-evaluatecount="{{:user.evaluateCount}}" data-evaluatepublishcount="{{:user.evaluatePublishCount}}" data-honest="{{:user.honest}}" data-quality="{{:user.quality}}" data-punctual="{{:user.punctual}}" data-honestpublish="{{:user.honestPublish}}" data-align="bottom left">
					{{evaluateFormat user.evaluate user.evaluateCount user.evaluatePublish user.evaluatePublishCount/}}
				</div>	
			</div>
			<div class="evaluate-count-div"> 
				<a href="/profile.jsp?userId={{base64Encode user.id/}}#tab_1_2" target="_blank">
					<span class="evaluate-count">（{{evaluateCountFormat user.evaluateCount user.evaluatePublishCount/}}）
				</a>
			</div> 
			<div class="receive">
				<a  target="_blank" href="/task/detail.jsp?id={{base64Encode id/}}&view=tab_15_2#tab_15_2"> 
					<i class="iconfont icon-bidding"></i>
					<span>投标数 （{{:receiveDemands.length}}）</span>	
				</a>
			</div>
			<div class="message">
				<i class="iconfont icon-leave-message"></i>
					<a href="/task/detail.jsp?id={{base64Encode id/}}&view=tab_15_1#tab_15_1" target="_blank">留言数（{{:messageCount}}）</a>
			</div>
			<div class="location">
				<i class="iconfont icon-map"></i>
				<span title="{{locationFormat locationName/}}">{{locationFormat locationDistrict locationProvince locationCountry/}}</span>
			</div>
		</div>
	</div>

{{/for}}								
</script>

<script type="html/x-jsrender" id="user-item-tmpl">
{{for dataList}}
	<div class="user-content row">
		<div class="left">
			<div class="row">
				<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">
					<img class="img-circle" src="{{:picUrl}}">
				</a>
			</div>
			<div class="row {{if id === "${user_id_session_key}"}}hide{{/if}}">
				<button type="button" class="user-follow {{if follow=="1"}}{{else}}hide{{/if}}" data-follow="{{:follow}}" data-userid="{{:id}}">取消关注</button>
				<button type="button" class="user-follow {{if follow=="1"}}hide{{else}}{{/if}}"  data-follow="{{:follow}}" data-userid="{{:id}}">关注</button>
			</div>
		</div>
		<div class="right">
			<div class = "row"> 
				<div class="user-name">
					<a href="javascript:void(0);" class="user-pic" data-id="{{:id}}">{{:nickName}}</a>
				</div>
				<div class="show-grade user-grade"  data-grade="{{:grade}}" data-align="bottom left" ><span>Lv{{calculateGrade grade/}}</span></div>
				<div class="user-location">
					<i class="iconfont icon-map"></i>
					<span>{{countryFormat country/}}</span>
				</div>
				<div class = "user-specialty">
						{{for su}}
							{{if type==0}}
									<a href="javascript:void(0)" data-tag="{{:specialty.name}}" class="specialty-tag" data-id="{{:specialtyId}}" data-typeid="{{:specialtyTypeId}}">
										<div class="user-tag">\#{{:specialty.name}}</div>
									</a>
							{{else type==1}}
									<a href="javascript:void(0)" data-tag="{{:name}}" class="specialty-tag custom" data-id="{{:specialtyId}}" data-typeid="{{:specialtyTypeId}}">
										<div class="user-tag">\#{{:name}}</div>
									</a>
							{{/if}}
						{{/for}}
				</div>
			</div>
			<div class = "row"> 
				<div class="user-brief">{{:brief}}</div>
			</div>

			<div class = "row user-desc"> 
				<div class="user-pub-task">
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_3" target="_blank">
						已发布任务数：{{:countPubDemand}}
					</a>
				</div>
				<div class="user-acc-task">
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_2" target="_blank">
						已解决任务数：{{:countFinishRD}}
					</a>
				</div>
						
				<div class="user-level show-evaluate" data-userid="{{:id}}" data-evaluatecount="{{:evaluateCount}}" data-evaluatepublishcount="{{:evaluatePublishCount}}" data-honest="{{:honest}}" data-quality="{{:quality}}" data-punctual="{{:punctual}}" data-honestpublish="{{:honestPublish}}" data-align="bottom left">
					综合评级： {{evaluateFormat evaluate evaluateCount evaluatePublish evaluatePublishCount/}}
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_2" target="_blank"><span class="evaluate-count">（{{evaluateCountFormat evaluateCount evaluatePublishCount/}}）</span></a>
				</div>
				<div class="leave-message">
					<a href="/profile.jsp?userId={{base64Encode id/}}#tab_1_4" target="_blank"><i class="iconfont icon-leave-message"></i>留言（{{:commentCount}}）</a>
				</div>
		
			</div>

		</div>
	</div>

{{/for}}								
</script>

<script id="tag-items-tmpl" type="html/x-jsrender">
		<a href="javascript:void(0);" data-type="{{:type}}">
			{{if type==0}}
			<b>标签：</b>
			{{else type==1}}
			<b>技能：</b>
			{{else type==2}}
			<b>金额：</b>
			{{/if}}
			<b>{{:tag}}</b>  
			<i class="glyphicon glyphicon-remove"></i>
		</a>
</script>

<script id="type-second-tmpl" type="html/x-jsrender">
{{if type=="1"}}
	<a href="javascript:void(0)" name="type-second-a" data-typesecond=""><p class="p-now">所有</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="101"><p class="p-now">艺术</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="102"><p class="p-now">生物</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="103"><p class="p-now">商科</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="104"><p class="p-now">化学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="105"><p class="p-now">传播和媒体学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="105"><p class="p-now">研究性论文</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="107"><p class="p-now">经济学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="108"><p class="p-now">教育学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="109"><p class="p-now">工程学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="110"><p class="p-now">英文学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="111"><p class="p-now">伦理学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="112"><p class="p-now">历史学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="113"><p class="p-now">法学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="114"><p class="p-now">语言学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="115"><p class="p-now">文学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="116"><p class="p-now">管理学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="117"><p class="p-now">市场推广</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="118"><p class="p-now">数学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="119"><p class="p-now">药物健康学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="120"><p class="p-now">自然学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="121"><p class="p-now">哲学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="122"><p class="p-now">物理学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="123"><p class="p-now">政治学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="124"><p class="p-now">心理学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="125"><p class="p-now">宗教学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="126"><p class="p-now">社会学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="127"><p class="p-now">技术学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="128"><p class="p-now">旅游学</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="129"><p class="p-now">其他</p></a>
{{else type=="2"}}				
	<a href="javascript:void(0)" name="type-second-a" data-typesecond=""><p class="p-now">所有</p></a>  		
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="201"><p class="p-now">租车</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="202"><p class="p-now">买车</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="203"><p class="p-now">租房</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="204"><p class="p-now">导游</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="205"><p class="p-now">其他</p></a>
{{else type=="3"}}			
	<a href="javascript:void(0)" name="type-second-a" data-typesecond=""><p class="p-now">所有</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="301"><p class="p-now">实习</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="302"><p class="p-now">翻译</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="303"><p class="p-now">电脑维修</p></a>
	<a href="javascript:void(0)" name="type-second-a" data-typesecond="304"><p class="p-now">其他</p></a>
{{/if}}
</script>

<script id="dropdown-type-second-tmpl" type="html/x-jsrender">
{{if type=="1"}}
	<li role="presentation"><a href=""    name="dropdown-type-second-a" class="dropdown-type-second=">所有</a></li>
	<li role="presentation"><a href="101" name="dropdown-type-second-a" class="dropdown-type-second">艺术</a></li>
	<li role="presentation"><a href="102" name="dropdown-type-second-a" class="dropdown-type-second">生物</a></li>
	<li role="presentation"><a href="103" name="dropdown-type-second-a" class="dropdown-type-second">商科</a></li>
	<li role="presentation"><a href="104" name="dropdown-type-second-a" class="dropdown-type-second">化学</a></li>
	<li role="presentation"><a href="105" name="dropdown-type-second-a" class="dropdown-type-second">传播和媒体学</a></li>
	<li role="presentation"><a href="106" name="dropdown-type-second-a" class="dropdown-type-second">研究性论文</a></li>
	<li role="presentation"><a href="107" name="dropdown-type-second-a" class="dropdown-type-second">经济学</a></li>
	<li role="presentation"><a href="108" name="dropdown-type-second-a" class="dropdown-type-second">教育学</a></li>
	<li role="presentation"><a href="109" name="dropdown-type-second-a" class="dropdown-type-second">工程学</a></li>
	<li role="presentation"><a href="110" name="dropdown-type-second-a" class="dropdown-type-second">英文学</a></li>
	<li role="presentation"><a href="111" name="dropdown-type-second-a" class="dropdown-type-second">伦理学</a></li>
	<li role="presentation"><a href="112" name="dropdown-type-second-a" class="dropdown-type-second">历史学</a></li>
	<li role="presentation"><a href="113" name="dropdown-type-second-a" class="dropdown-type-second">法学</a></li>
	<li role="presentation"><a href="114" name="dropdown-type-second-a" class="dropdown-type-second">语言学</a></li>
	<li role="presentation"><a href="115" name="dropdown-type-second-a" class="dropdown-type-second">文学</a></li>
	<li role="presentation"><a href="116" name="dropdown-type-second-a" class="dropdown-type-second">管理学</a></li>
	<li role="presentation"><a href="117" name="dropdown-type-second-a" class="dropdown-type-second">市场推广</a></li>
	<li role="presentation"><a href="118" name="dropdown-type-second-a" class="dropdown-type-second">数学</a></li>
	<li role="presentation"><a href="119" name="dropdown-type-second-a" class="dropdown-type-second">药物健康学</a></li>
	<li role="presentation"><a href="120" name="dropdown-type-second-a" class="dropdown-type-second">自然学</a></li>
	<li role="presentation"><a href="121" name="dropdown-type-second-a" class="dropdown-type-second">哲学</a></li>
	<li role="presentation"><a href="122" name="dropdown-type-second-a" class="dropdown-type-second">物理学</a></li>
	<li role="presentation"><a href="123" name="dropdown-type-second-a" class="dropdown-type-second">政治学</a></li>
	<li role="presentation"><a href="124" name="dropdown-type-second-a" class="dropdown-type-second">心理学</a></li>
	<li role="presentation"><a href="125" name="dropdown-type-second-a" class="dropdown-type-second">宗教学</a></li>
	<li role="presentation"><a href="126" name="dropdown-type-second-a" class="dropdown-type-second">社会学</a></li>
	<li role="presentation"><a href="127" name="dropdown-type-second-a" class="dropdown-type-second">技术学</a></li>
	<li role="presentation"><a href="128" name="dropdown-type-second-a" class="dropdown-type-second">旅游学</a></li>
	<li role="presentation"><a href="129" name="dropdown-type-second-a" class="dropdown-type-second">其他</a></li>
{{else type=="2"}}				
	<li role="presentation"><a href=""    name="dropdown-type-second-a" class="dropdown-type-second">所有</a></li>      		
	<li role="presentation"><a href="201" name="dropdown-type-second-a" class="dropdown-type-second">租车</a></li>
	<li role="presentation"><a href="202" name="dropdown-type-second-a" class="dropdown-type-second">买车</a></li>
	<li role="presentation"><a href="203" name="dropdown-type-second-a" class="dropdown-type-second">租房</a></li>
	<li role="presentation"><a href="204" name="dropdown-type-second-a" class="dropdown-type-second">导游</a></li>
	<li role="presentation"><a href="205" name="dropdown-type-second-a" class="dropdown-type-second" >其他</a></li>
{{else type=="3"}}			
	<li role="presentation"><a href=""    name="dropdown-type-second-a" class="dropdown-type-second">所有</a></li>
	<li role="presentation"><a href="301" name="dropdown-type-second-a" class="dropdown-type-second">实习</a></li>
	<li role="presentation"><a href="302" name="dropdown-type-second-a" class="dropdown-type-second"</a></li>
	<li role="presentation"><a href="303" name="dropdown-type-second-a" class="dropdown-type-second">电脑维修</a></li>
	<li role="presentation"><a href="304" name="dropdown-type-second-a" class="dropdown-type-second">其他</a></li>
{{/if}}
</script>

<script id="sort-tmpl" type="html/x-jsrender">
{{if searchType=="task"}}
<li role="presentation"><a href="0" role="menuitem" tabindex="-1">默认推荐</a></li>  
<li role="presentation"><a href="1" role="menuitem" tabindex="-1">最新任务</a></li>  
<li role="presentation"><a href="2" role="menuitem" tabindex="-1">最热任务</a></li>  
<li role="presentation"><a href="3" role="menuitem" tabindex="-1">报酬高</a></li>  
<li role="presentation"><a href="4" role="menuitem" tabindex="-1">快结束</a></li>   
					   	
{{else searchType=="user"}}
<li role="presentation"><a href="0" role="menuitem" tabindex="-1">默认推荐</a></li>  
<li role="presentation"><a href="1" role="menuitem" tabindex="-1">发单金额高</a></li>  
<li role="presentation"><a href="2" role="menuitem" tabindex="-1">接单金额高</a></li>  
<li role="presentation"><a href="3" role="menuitem" tabindex="-1">评分高</a></li>  
<li role="presentation"><a href="4" role="menuitem" tabindex="-1">人气高</a></li>
<li role="presentation"><a href="5" role="menuitem" tabindex="-1">等级高</a></li>     
{{/if}}
</script>

<script id="new-item-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="new-item row">
	<div class="left">
		<a href="/new/detail.jsp?id={{:id}}" target="_blank">
			<img class="new-img" src="{{:coverImg}}">
		</a>
	</div>
	<div class="right">
		<div class="new-title">
			<a href="/new/detail.jsp?id={{:id}}" target="_blank">{{:title}}</a>
		</div>
		<div class="new-time row">
			<span>{{datetimeFormat createDate/}}</span>
			{{if hotFlag==1}}
			<span class="hot">HOT</span>
			{{/if}}
		</div>
	</div>
</div>
{{/for}}
</script>



	<%@ include file="/include/footer.jsp"%>
	<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
	<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery.twbsPagination.min.js" type="text/javascript"></script>
	<script src="/assets/layouts/global/scripts/header-video.js" type="text/javascript"></script>
	<script src="/assets/pages/scripts/index.js" type="text/javascript"></script>
	