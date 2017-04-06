<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="/include/header.jsp"%>
	<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/us-help.css" />
	<!--content-->
	<% 
		String title = "";
		String keywords = "";
		String description = "";
		Seo s = SeoUtil.getSeoByWebFlag("4");
		if(s != null){
			title = s.getTitle();
			keywords = s.getKeywords();
			description = s.getDescription();
		}
	%>
	<input type="hidden" name="seoTitle" value="<%=title %>">
	<input type="hidden" name="seoKeywords" value="<%=keywords %>">
	<input type="hidden" name="seoDescription" value="<%=description %>">
		
	<div style="height:70px">
	</div>
	<div class="row bg">
	</div>
	<style>
	.bg{
		background-image:url("/assets/pages/img/contactus/helpus.jpg");
		background-size: 100% 100%;
        background-repeat: no-repeat;
	}
	</style>
	<!-- <div class="info">
		<div class="right">
			<div class="row contactus">
				<span>帮助中心</span>
			</div>
			<div class="row address">
				<span>如果下面没有相应帮助问题，<br/>请发邮件到<a href="mailto:help@anyonehelps.com">help@anyonehelps.com</a> 进行咨询</span>
			</div>
		</div>
	</div>
	 -->
	
	
	<div class="row question-block">
		<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
			<div class="item-block">
				<div class="question-list">
					<div class="row title" >
				 		<span>新人指南</span>
				 	</div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q1：AnyoneHelps是什么？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：AnyoneHelps.com是一个为留学生提供C2C互助服务的在线交易平台，服务交易的品类涵盖了留学生学习、生活、工作的方方面面，不管你是什么专业，你来自
							哪里，只要任何关于留学生生活的困难，都可以在AnyoneHelps发表任务需求，寻找最适合你的帮助者来帮你完成你任务；只要你有一技之长或者专业的学识，都可以在
							AnyoneHelps接取相关任务，帮助别人完成任务并赚取最合理的报酬。AnyoneHelps志在“让才智技能变现，让天下再也没有难留的学”。我们的宗旨是，客户不仅是用户，
							也是“股东”，不仅为客户提供最专业、最佳的服务，同时也携手用户一起缔造最好用的网络互助平台。</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q2：如何注册？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：您可以通过以下步骤进行注册：<br>
							•	<a href="https://www.anyonehelps.com/login.jsp?view=register">点击这里</a> ，如果选择邮箱注册，输入您的邮箱地址、用户名及用户密码；如果选择手机验证，输入您的用户名、手机号码、用户名及用户密码并提交注册。<br>
							•	进行邮箱验证或手机验证。在您点击注册之后，您可以浏览网站的所有内容，但是，为了您能使用网站的功能，您必须进行邮箱验证或者是手机验证以保证您的账户安全。在用户中心首页点击验证邮箱或手机号码后，我们给您发送验证消息，邮箱认证您只需点击我们给您发送的链接即可，手机验证您需要输入我们给您发送的随机验证码。<br>
							注意：有时候该邮件可能会被归类到垃圾邮件中，为避免这种情况，您可以将AnyoneHelps加入您的联系人或白名单中。
							</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q3：我忘记了登陆密码，该怎么办？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：如果您忘记了您的密码，您可以在登陆页面点击“忘记密码？”进行寻回，我们有三种方式帮您找回您的密码：<br>
                            •	邮箱寻回：您只需填写您的注册邮箱地址，我们随后会发送重设密码链接到您邮箱<br>
                            •	手机寻回：填写您注册或认证的手机号码，我们会给您发送验证码，填写正确的验证码并输入新密码即可<br>
                            •	密保问题寻回：如果您提供过您的三个密保问题，您可以通过密保问题寻回密码
                            </span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q4：我没有收到重设密码的邮件或短信怎么办？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：在您提交重设密码要求后，一般您会在15分钟内收到消息，如果您检查了比如您的邮箱垃圾箱等都没有的话，请你再次提交重设密码申请。
                            	如果您始终不能收到邮件，请联系我们的客服（给出客服超链接）
                            </span>
					 	</div>
				 	</div> 
				</div>
		 	</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
			<div class="item-block">
				<div class="question-list">
					<div class="row title" >
				 		<span>账号管理</span>
				 	</div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q1：怎样编辑我的账号基本信息？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：•	登陆AnyoneHelps账号<br>
                            •	点击右上角头像并选择“用户中心”<br>
                            •	更新您的账户信息<br>
                            •	完毕之后，点击页面下方“提交”进行保存
                            </span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q2：AnyoneHelps的账号分雇主和帮助者两个么？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：不，AnyoneHelps是要建立一个互助平台，我们鼓励所有用户在AnyoneHelps的平台上不仅能找到帮助者，同时也利用自己的优势才能去帮助他人，所以AnyoneHelps的账号既可以发任务成为雇主也可以接任务成为帮助者。</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q3：怎样修改我的密码和头像？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：在用户中心右上角，点击“修改头像”和“修改密码”根据页面提示进行操作。</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q4：怎样验证邮箱或者手机号码？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：通过右上方头像进入用户中心，点击“验证”按钮进行相关验证</span>
					 	</div>
				 	</div> 
					<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q5：怎样添加我的银行或者PayPal信息？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：通过右上方头像进入用户中心，在财务中心板块，点击“账号明细与充值”进行相应操作。</span>
					 	</div>
				 	</div> 
					<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q6：我必须提供验证之后才能在网站进行交易么？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：是的，为了保证您的账户安全和交易是您本人操作，有关金钱的所有动向我们都会向你验证过的邮箱和电话号码发送提示消息，保障您的交易安全，是我们的首要责任。</span>
					 	</div>
				 	</div> 
					<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q7：怎样查看账户余额？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：通过右上方头像进入用户中心，在财务中心板块，点击“账号明细与充值”，下一个页面会显示您的“账户总额”“可用余额”“任务冻结金额”和“提现等待金额”。</span>
					 	</div>
				 	</div> 
				</div>
		 	</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
			<div class="item-block">
				<div class="question-list">
					<div class="row title" >
				 		<span>背景资料和技能管理</span>
				 	</div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q1：我有必要提供我的教育经历和工作经历么？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：AnyoneHelps帮助链接雇主和帮助者，双方的匹配遵循自愿原则，为了更好的找到最适合的帮助者和雇主，我们建议您维护好您的背景资料，这有助于您在AnyoneHelps更好的得到体验。</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q2：我如何添加我的教育经历和工作经历？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：点击右上角头像进入用户中心，在我的资料页面下方，点击“添加教育经历”或者“添加工作经历”，在弹出的对话框内，填写并维护您的信息</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q3：作品展示是什么？我如何添加我的作品展示？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：作品展示是AnyoneHelps为用户提供的一块专门展示自己得意的相关作品的区域，在这里用户可以上传与他所选技能相关并具有代表性的作品，这些作品将会成为他最好的广告来吸引更多的发单人选择匹配他或她。这部分信息在“个人主页”将会被单独展示出来。
                            如果需要添加作品展示，点击右上角头像进入用户中心，在我的资料页面下方，在作品展示板块点击“上传附件”即可。
                            </span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q4：我怎么添加我的技能？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：点击右上角头像进入用户中心，点击“技能管理”进入技能管理的相关页面。在“技能选择”区域，选择您具备的技能的分类“学术”“学习与工作”“生活娱乐”并点击在下方出现的技能选项即可。</span>
					 	</div>
				 	</div> 
					<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q5：如果我想要填写的技能在技能列表里没有怎么办？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：如果您没有找到您想要填写的技能，请先根据“学术”“学习与工作”“生活娱乐”定位您的技能分类，然后再该分类中填写“自定义技能”名称并添加。</span>
					 	</div>
				 	</div> 
					<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q6：技能认证是什么？这个服务收费么？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：技能认证是由网站为用户提供技能认证服务的通道，用户提交认证的技能将会通过网站筛选的该领域的大牛和专家对用户所提供的内容进行综合评估，如果通过认证，该技能将会显示为“通过认证”并被证明为有专家级的技能能力。通过认证的技能将有更高的可信度和吸引更多需要该技能的人选择该用户成为匹配者。
                            这个服务网站会收取一定的认证费用，具体的认证费用请参考技能认证页面。
                            </span>
					 	</div>
				 	</div> 
				</div>
		 	</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
			<div class="item-block">
				<div class="question-list">
					<div class="row title" >
				 		<span>费用收取</span>
				 	</div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q1：账户充值手续费？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：1.如果您实用的是PayPal, PayPal对网上交易会产生如下手续费，这笔费用不是AnyoneHelps向用户收取的手续费，而是向PayPal支付的交易手续费：<br>
							美国 PayPal 账户：充值金额2.9%的交易费 + 30美分的固定费用<br>
							非美国PayPal账户：充值金额3.9%的交易费 + 30 美分的固定费用<br>
                            2.如果您使用的是信用卡，AnyoneHelps使用Stripe进行信用卡交易会产生如下手续费，这笔费用不是AnyoneHelps向用户收取的手续费，而是向Stripe支付的交易手续费：<br>
							充值金额2.9%的交易费 + 30美分的固定费用。
                            </span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q2：提现费用？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：AnyoneHelps不对提现进行任何费用的收取。注意：提现使用的第三方机构可能会对提现交易进行费用收取，具体情况请与您的服务提供机构联系。</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q3：交易费用？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：任务完成发单人进行支付后，接单人的收款金额为发单人最终支付金额减去交易费用的净值。AnyoneHelps现阶段只对接单人赚取的金额收取13%手续费用，这个收费比例可能会根据市场情况产生变动，AnyoneHelps对手续费费率的调整具有最终解释权。</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q4：技能认证费用？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：AnyoneHelps提供用户的技能认证服务，具体服务内容和细节请参考“<a href="https://www.anyonehelps.com/agreement/skill.jsp">技能认证使用条款</a>”。该服务由在AnyoneHelps登记注册的各领域的大牛与专家完成，技能认证将产生$50的认证费用作为对该技能认证申请进行审核的大牛与专家的报酬，该费用将会从申请人的AnyoneHelps账户余额扣除。</span>
					 	</div>
				 	</div> 
					
				</div>
		 	</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
			<div class="item-block">
				<div class="question-list">
					<div class="row title" >
				 		<span>其他问题</span>
				 	</div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q1：AnyoneHelps是收费的吗？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：在AnyoneHelps注册是免费的。<br>
                            在AnyoneHelps上达成交易后需要缴纳一定的手续费。
                            </span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q2：如何联系客服？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：联系客服，请点击一下链接，并根据提示进行操作：<a href="https://www.anyonehelps.com/us/contactUs.jsp">点击这里</a></span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q3：是否有必要上传用户头像？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：是否上传取决于您的个人意愿，AnyoneHelps为您提供了一些设计精良的默认头像，但是，为了使您的账户更有特点和其他雇主或帮助者区分开，更快更容易找到匹配，我们建议您上传您的个性化头像。</span>
					 	</div>
				 	</div> 
				 	<div class="line row"></div>
				 	<div class="item">
					 	<div class="row question" >
					 		<span>Q4：我可以修改我注册的邮箱或者手机么？</span>
					 	</div>
					 	<div class="row answer" >
					 		<span>A：如果您设置过您的密保问题，您可以在网站自行操作，具体操作如下：<br>
                             •	登陆账号<br>
                             •	点击右上方头像进入用户中心<br>
                             •	点击“邮箱”或“手机号码”旁的“修改”键<br>
                             •	根据下一页面操作提示提供相关信息进行修改<br>
                            如果您没有设置过您的保密问题，您可以联系我们的客服并提供相关的身份信息进行修改，<a href="https://www.anyonehelps.com/us/contactUs.jsp">联系客服</a>
                            </span>
					 	</div>
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