<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="/include/header.jsp"%>
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/us-joinus.css" />
	<!--content-->
	<% 
		String title = "";
		String keywords = "";
		String description = "";
		Seo s = SeoUtil.getSeoByWebFlag("3");
		if(s != null){
			title = s.getTitle();
			keywords = s.getKeywords();
			description = s.getDescription();
		}
	%>
	<input type="hidden" name="seoTitle" value="<%=title %>">
	<input type="hidden" name="seoKeywords" value="<%=keywords %>">
	<input type="hidden" name="seoDescription" value="<%=description %>">

	<div class="row bg hidden-xs">
		<img class="bg-bg" src="/assets/pages/img/joinus/bg@1x.png"/>
		<img class="about-us hidden-xs" src="/assets/pages/img/joinus/about_us.png"/>
	</div>
	<div class="row bg hidden-sm hidden-dm hidden-lg">
		<img class="bg-bg" src="/assets/pages/img/joinus/bg_phone@1x.png"/>
	</div>

	<div class="position">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
				<div class="position-item">
					<div class="row">
		                <img class="left" src="/assets/pages/img/joinus/position-1.png">
		                <span class="left-title">UI|网页设计|美工</span>
					</div>
					<div class="row demand">
						<span class="left-demand">职位要求</span>
					</div>
					<div class="row">
						<span class="left-desc">
							1年以上html5开发经验；<br/>
							2年以上网页UI设计经验，有移动互联网设计经验者优先考虑；<br/>
							熟悉HTML/XHTML丶CSS丶JavaScript等网页技术，熟悉DIV+CSS及网页切图者优先考虑；<br/>
							精通Adobe(Photoshop/Dreamweaver/Illustrator)等设计软件，对图片渲染和视觉效果有较好认识；<br/>
							具有扎实的美术设计功底，独特的色彩把握能力及优秀的审美和创作能力；<br/>
							计算机或其它相关专业毕业；<br/>
		
							工作地点：404 E Huntington Dr, Arcadia, CA 91006<br/>
							邮箱至：hr@Anyonehelps.com<br/>
						</span>
					</div>
				</div>
			</div>
			
			<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
				<div class="position-item">
					<div class="row">
						<img class="left" src="/assets/pages/img/joinus/position-2.png">
						<span class="left-title">Web开发工程师</span>
					</div>
					<div class="row demand">
						<span class="left-demand">职位要求</span>
					</div>
					<div class="row">
						<span class="left-desc">
							1-3年Web前端开放经验，良好的团队合作意识和沟通能力，认真细致；<br/>
							二年及以上 Web 后端开发经验；熟练掌握java；<br/>
							熟悉关系型数据库，能熟练使用Mysql进行设计开发； <br/>
							对web后端技术架构有很好的理解和规划能力；<br/>
							熟悉JavaScript, JQuery, JSON；<br/>
							熟悉互联网产品和服务的开发过程。有规范化丶标准化的代码编及文档习惯；<br/>
							计算机或其它相关专业毕业；<br/>
		
							工作地点：404 E Huntington Dr, Arcadia, CA 91006<br/>
							邮箱至：hr@Anyonehelps.com<br/>
						</span>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
				<div class="position-item">
					<div class="row">
						<img class="left" src="/assets/pages/img/joinus/position-3.png">
						<span class="left-title">APP开发工程师</span>
					</div>
					<div class="row demand">
						<span class="left-demand">职位要求</span>
					</div>
					<div class="row">
						<span class="left-desc">
							熟悉IOS／Android 平台移动端软件开发丶调试及维护工作； <br/>
							熟练掌握Object-C及X-Code；<br/>
							熟悉iPhone/iPad开发中的界面布局丶控件使用丶后台运行丶数据存储丶多线程等知识；<br/>
							有在App Store发布过自己开发的作品者优先；<br/>
								
		
							工作地点：404 E Huntington Dr, Arcadia, CA 91006<br/>
							邮箱至：hr@Anyonehelps.com<br/>
						</span>
					</div>
				</div>
			</div>
		</div>
	</div>



	<%@ include file="/include/footer.jsp"%>
	<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
	<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script type="text/javascript">
$(function() {
	$("title").html($(":hidden[name='seoTitle']").val());
	$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
	$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
})
</script>