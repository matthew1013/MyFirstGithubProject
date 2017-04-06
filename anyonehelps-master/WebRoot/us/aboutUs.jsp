<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="/include/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/us-aboutus.css" />
	<!--content-->
	<% 
		String title = "";
		String keywords = "";
		String description = "";
		Seo s = SeoUtil.getSeoByWebFlag("1");
		if(s != null){
			title = s.getTitle();
			keywords = s.getKeywords();
			description = s.getDescription();
		}
	%>
	<input type="hidden" name="seoTitle" value="<%=title %>">
	<input type="hidden" name="seoKeywords" value="<%=keywords %>">
	<input type="hidden" name="seoDescription" value="<%=description %>">
		
	
	<div class="row bg">
		<!-- 
		<img class="bg-bg" src="/assets/pages/img/aboutus/bg.png"/>
		<img class="play" src="/assets/pages/img/index/player.png"
			onMouseOver="this.src='/assets/pages/img/index/player-hover.png'"
			onMouseOut="this.src='/assets/pages/img/index/player.png'"/>
		<div style="position: relative;top: -174px;height: 0px;">
			<span style="font-size:14px;color:#ffffff;letter-spacing:0.99px;text-align:left;">点击观看视频</span>
		</div>
		 -->
		<div class="player">
			<center>
				<a class="show-player" href="javascript:void(0)"> 
					<img src="/assets/pages/img/aboutus/play.png" 
					onmouseover="this.src='/assets/pages/img/aboutus/play-hover.png'" 
					onmouseout="this.src='/assets/pages/img/aboutus/play.png'">
				</a>

			</center>
			<center class="show-desc">
				<span>点击观看视频</span>
			</center>
		</div>
	</div>
	<!-- video play start -->
	<div class="row">
		<div class="video-play ">
			<div class="video-play-close hide">
				<a href="javascript:void(0);" name="video-play-close-a">
					<img src="/assets/pages/img/index/close-2.png">
				</a>
			</div>
			<!-- <div class="col-xs-0 col-sm-1 col-md-2 col-lg-2"></div> -->
		</div>
	</div>
	<!-- video play end -->
	
	<div class="row about-us">
		<div class="row title">
			<span>关于我们</span>
		</div>
		<div class="line row"></div>
		<div class="row content">
		    <p style="font-size:18px;font-weight:bold;margin-bottom:25px">“永远不要低估梦想，和实现梦想的决心。”</p>
			<p></p>
			<p>&nbsp;&nbsp; AnyoneHelps是由一群留学生建立的C2C网络互助平台，我们为留学生提供最专业的网络互助服务，把最有才的你和最需要帮助的他连接在一起，用留学生的才能解决留学生涯中学习、生活、工作的各种难题。</p>
			<p>&nbsp;&nbsp;作为留学生的我们，从学习、生活再到工作每一步都充满了挑战和困难，为了实现梦想，或许每天都做着与梦想毫无关联的工作，餐馆打工？接机送机？我们担心安全，我们担心语言，我们更担心在孤立无援的异国他乡饱一顿饥一顿。AnyoneHelps团队要让所有留学生在追逐梦想的道路上多一份心安理得！ 我们的前身就是一群从发挥己长、互补所短的互助中受益的留学生，我们相信如果一个留学生能用这个模式成功，那整个留学群体也能！</p>
			<p>&nbsp;&nbsp;经过一年半时间的市场调查、策划等准备工作，我们聚集了一群志同道合的小伙伴，从零开始踏上了创业之路。秉持着“每一个人都是天才”这一信条，我们建立让留学生各展才能的互助平台，帮助同学们更好的平衡学习和工作，并有效解决方方面的难题。我们从不说我们有过人的才能，也从不说有鹤立的项目，但我们有梦想，有决心，有信念，正是这些梦想、决心、信念凝聚了我们的团队。创业之初，每个小伙伴的家就是我们的大本营，营业到深夜的星巴克就是我们的会议室，废弃的房车就是工程师的工作室，条件的困难没有打消我们对梦想的追逐；求贤若渴的我们曾经彻夜奔袭北加三顾茅庐，我们曾经为了赶路在车上过夜，我们也为求教前辈再三登门被拒，这些种种让我们对创业这个游戏更加的不可自拔！有一天，我们希望，每一位留学生，都能拿上AnyoneHelps这支笔在遥远的异国他乡写上属于我们自己的故事，这个故事关于独立，这个故事关于梦想，这个故事关于成功，这个故事关于永不放弃！</p>
			<div class="row title-en">
				<span>About us</span>
			</div>
			<div class="en line row"></div>
			<div class="row content-en">
		    	<p style="font-size:18px;font-weight:bold;margin-bottom:25px">‘One should never underestimate the power of dream and the power of a determined mind.’</p>
				<p>&nbsp;&nbsp;AnyoneHelps is founded by a team of international students and it dedicates to build a C2C Internet-based helping and problem-solving eco system that provides professional services to link users together to match up talents and needs. We aim at helping international students to solve studying、living and working problems by using international students’ talents.</p>
				<p>&nbsp;&nbsp;As an international student, there are challenges and obstacles everywhere along the road of studying、living and working in a foreign country. In order to make the dreams come true, we might struggle to support the dreams with doing things not even relevant to the dream itself. We worry about our safety; we worry about language barrier; we even worry about being starving in this unfamiliar country. However, AnyoneHelps team is managing to make every international student’s life easier on the way to dream! We are international students who are benefited from using own talents to help each other to be successful. We believe that anyone can be successful with this mode because our success can be copied!</p>
				<p>&nbsp;&nbsp;AnyoneHelps gathered a great team with passion and same goal to start the project after one and half year market research and preparation works. Holding the creed, ‘anyone could be the most talented’, we built up AnyoneHelps to help international students to find good balance between studying and working, and to solve different types of problems. As a start-up team, we never claim we are more talented nor claim we have a unique project on hand, but we do claim we are equipped with dream, we are determined and we are confident. In the first few months we started, the homes of team members were the offices of our team, Starbucks were our meeting rooms and abandoned trailer home in backyard was our work room. Even though the environment of working was not good, we were still passionate about what we are doing. We drove overnight to Silicon Valley to meet up with an engineer; we used to travel from place to place and slept in our car; we also visited different successful business men or entrepreneurs even if we were refused at door. However, we always perceive the difficulties as the charm of start-up. We, AnyoneHelps, along with every international student, are trying hard to write a story of independence, dream, success and never-give-up on this foreign land! </p>
			</div>
		</div>
	</div>
	
	<!--
	<div class="row team">
		<div class="row title">
			<span>团队成员</span>
		</div>
		<div class="line row"></div>
		<div class="row team-list">
			<div class="col-md-4 col-sm-4 col-xs-4">
				<div class="team-item">
				 	 <div class="row" >
				 		<div class="pic"></div>
				 	</div> 
				 	<div class="row position" >
				 		<span>tean	CEO</span>
				 	</div>
				 	 <div class="row desc" >
				 		<span>Anyonehelps创始人、董事长兼首席执行官，全面负责Anyonehelps的战略规划和运营管理。</span>
				 	</div>
			 	</div>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<div class="team-item">
				 	 <div class="row" >
				 		<div class="pic"></div>
				 	</div> 
				 	<div class="row position" >
				 		<span>Y.Y</span>
				 	</div>
				 	 <div class="row desc" >
				 		<span>Anyonehelps创始人、业务执行官COO，全面负责Anyonehelps的业务管理和运营管理。</span>
				 	</div>
			 	</div>
			</div>
			<div class="col-md-4 col-sm-4 col-xs-4">
				<div class="team-item">
				 	 <div class="row" >
				 		<div class="pic"></div>
				 	</div> 
				 	<div class="row position" >
				 		<span>K.C</span>
				 	</div>
				 	 <div class="row desc" >
				 		<span>Anyonehelps创始人、技术执行官，全面负责Anyonehelps的技术管理和运营管理。</span>
				 	</div>
			 	</div>
			</div>
		</div>
	</div>
	-->

	<%@ include file="/include/footer.jsp"%>
	<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
	<script src="/assets/pages/scripts/us-aboutus.js" type="text/javascript"></script>
	

	