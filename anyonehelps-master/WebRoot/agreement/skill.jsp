<%@ page language="java" import="java.util.*,com.anyonehelps.common.util.Base64Util,com.anyonehelps.common.util.SeoUtil,com.anyonehelps.model.Seo" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
<% 
	String title = "";
	String keywords = "";
	String description = "";
	Seo s = SeoUtil.getSeoByWebFlag("10");
	if(s != null){
		title = s.getTitle();
		keywords = s.getKeywords();
		description = s.getDescription();
	}
%><!-- protocal.jsp -->
		
<meta charset="UTF-8">
<meta name="keywords" content="<%=keywords %>">
<meta name="description" content="<%=description %>">
<title><%=title %></title>
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/bootstrap/css/bootstrap.css" />
<link rel="shortcut icon" href="/assets/global/img/favicon.ico" /> 
</head>
<style type="text/css">
	.container{
		margin-top:30px;
	}
	.container>p.title{
		text-align:center;
	}
	.container>p.abstract{
		text-align:left;
		text-indent:28px
	}
	.container>p.desc{
		text-align:left;
		margin-top:30px;
	}
	.container>p.content{
		text-align:left;
		margin-left:28px
	}
	
</style>
<body>
<div class="container">
	<p class="title"><span>Certified Skillsets Service Terms</span></p>
	<p class="title"><span>技能认证服务协议</span></p>
	
	<p class="abstract"><span>提供给具有相关技能领域有突出才能和出色经历以及证明的人才提供技能认证的服务，该服务是为了证明用户选择的相关技能是有背书及证明的，通过专家对该用户的技能认证，为该用户所提交的技能进行认证和专业评述，帮助其他用户在进行投标人选择时对有技能要求的任务更能准确选择最适合、最专业的接单人。您在使用技能认证的服务前，请仔细阅读此条款。</span></p>
	<p class="abstract"><span>您在申请技能认证时点击“同意协议”按钮即表示您已经和AnyoneHelps达成协议，完全接受本协议规定的全部内容。您一旦使用该项服务，即视为您已经完全接受并同意本协议条款各项内容，包括AnyoneHelps对条款随时进行的任何修改。原则上，AnyoneHelps没有责任通知关于条款的修改，但是所有的修正将在条款更新发布后30天之后自动生效。</span></p>
	
	<p class="desc"><span>I. 技能认证服务内容：</span></p>
	<p class="content"><span>技能认证（CertifiedSkillsets）指经过网站专家审核认证的在某一技能领域或服务领域等具有专业水准、能提供专业认证、有出色和丰富的实践经验的人才。</span></p>
	<p class="content"><span>AnyoneHelps需要提出申请的用户提供以下内容：</span></p>
	<p class="content"><span>1. 关于相关技能或服务领域的一个专业自述，对自己的专业能力和过往专业经历有清晰的表述</span></p>
	<p class="content"><span>2. 提供由相关权威机构或学校颁发的专业证明或学位学术证明（如果有）</span></p>
	<p class="content"><span>3. 提供完整并专业的简历，能表明该申请用户有相关领域的专业经历</span></p>
	<p class="content"><span>4. 提供相关技能或服务领域过往的优秀作品或者代表作（如果有）</span></p>
	<p class="content"><span>5. 其他能够证明和支持该技能专业性的任何材料</span></p>
	<p class="content"><span>用户必须提供以上资料并保证材料的真实性和可靠性，任何虚假的材料都不会被接受，一旦发现在申请成为大牛过程中有不诚信的行为，AnyoneHelps将对该账号进行永久封号。AnyoneHelps评审团和相关团队具有对认证结果的最终解释权。</span></p>
	<p class="content"><span>技能认证功能产生的费用的支付是用户自主支付的，该部分款项主要用于作为网站所聘请的专家对该技能的认证的报酬，费用的收取是在双方自愿的情况下进行的合理交易。</span></p>
	
	<p class="desc"><span>II. 技能认证使用义务</span></p>
	<p class="content"><span>为了能使用此业务，用户必须做到：</span></p>
	<p class="content"><span>1. 提供真实的个人资料、简历、作品、证书等个人材料，并确保使用过程中的头像、邮箱、用户名等资料具有有效性和合法性。</span></p>
	<p class="content"><span>2. 及时对分配的审核和仲裁做出回应，并保证评审内容属于公正、公平、专业，在评审过程中没有收受贿赂、恶意裁决有失道德和违法的行为。</span></p>
	<p class="content"><span>3. 保持服务使用过程中的专业性和公正性。</span></p>
	<p class="content"><span>在账户使用过程当中，若大牛用户提供任何不真实、不诚信、虚假的个人材料，或者在使用网站过程中有任何违法行为，或者AnyoneHelps怀疑您的账号与操作属于恶意程序或存在恶意操作，以及没有履行以上提到的使用义务，AnyoneHelps有权取消该用户的大牛资格或对该账号进行暂停服务或封号处理，情节严重者将诉诸法律。</span></p>
	
	<p class="desc"><span>III. 用户终止：</span></p>
	<p class="content"><span>AnyoneHelps可能会对服务内容进行变更，也可能会中断、中止或终止服务。该协议建立在用户理解并同意，AnyoneHelps有权自主决定经营策略，关于技能认证的服务内容和运营方法，AnyoneHelps没有义务通知用户相关变更，</span></p>
	<p class="content"><span>如发生下列任何一种情形，AnyoneHelps有权不经通知而中断或终止向您提供的服务：</span></p>
	<p class="content"><span>1.根据法律规定您应提交真实信息，而您提供的个人资料不真实、或与注册时信息不一致又未能提供合理证明；</span></p>
	<p class="content"><span>2.您违反相关法律法规或本协议的约定；</span></p>
	<p class="content"><span>3.按照法律规定或主管部门的要求；</span></p>
	<p class="content"><span>4.出于安全的原因或其他必要的情形。</span></p>
	
	<p class="desc"><span>IV. 其他</span></p>
	<p class="content"><span>其他关于网站的相关使用条款和隐私条款，请参考User Agreementand Privacy Policy。</span></p>
	<p class="desc"><span></span></p>
</div>
</body>
</html>
