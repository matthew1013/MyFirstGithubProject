<%@ page language="java" import="java.util.*,com.anyonehelps.common.util.Base64Util,com.anyonehelps.common.util.SeoUtil,com.anyonehelps.model.Seo" pageEncoding="UTF-8"%>
<!doctype html>
<html>
	<head>
	<% 
		String title = "";
		String keywords = "";
		String description = "";
		Seo s = SeoUtil.getSeoByWebFlag("9");
		if(s != null){
			title = s.getTitle();
			keywords = s.getKeywords();
			description = s.getDescription();
		}
	%>
			
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
			<p class="title"><span>诚信规范服务承诺</span></p>
			
			<p class="abstract"><span>作为AnyoneHelps网站的使用者，用户接受并遵守AnyoneHelps网站上公示的各项规则，以及本条例所规定的各项要求，承诺AnyoneHelps网站进行诚信规范的交易和服务。用户也同意本网站对本条例所界定的“违规行为”，予以相应的处置和处罚。</span></p>
			
			<p class="desc"><span>I. 严重违规行为</span></p>
			<p class="content"><span>1.严重违规行为的界定 </span></p>
			<p class="content"><span>严重违规行为指下面列举的任何一项行为： </span></p>
			<p class="content"><span>(1) 线下交易</span></p>
			<p class="content"><span>对于在AnyoneHelps上参与的所有交易及以及得知的所有用户，均不得以任何理由诱导和实施线下交易，也不得同意和参与他人提出的线下交易。这些情况包括但不限于： </span></p>
			<p class="content"><span>• 不通过AnyoneHelps托管交易款项； </span></p>
			<p class="content"><span>• 交易中部分款项通过AnyoneHelps支付，而另一部分通过AnyoneHelps以外的途径支付； </span></p>
			<p class="content"><span>• 试图诱导其他用户进行线下交易； </span></p>
			
			<p class="content"><span>(2) 虚假交易 </span></p>
			<p class="content"><span>用户不得发布和参与虚假交易，虚假交易中获得的任何收入，均不得到网站的认可。这些情况包括但不限于：  </span></p>
			<p class="content"><span>• 使用非法获得的或未经授权的身份账号进行任何支付； </span></p>
			<p class="content"><span>• 选择自己或伙同他人作弊投标、中标，套取资金； </span></p>
			<p class="content"><span>• 在明知虚假交易的情况下，参与虚假交易； </span></p>
			<p class="content"><span>• 参与虚假交易并获得收入后，拒不退还虚假交易款项； </span></p>
			<p class="content"><span>• 交易本身没有实际服务或商品购买内容，变相套现、转账；  </span></p>
			
			<p class="content"><span>(3) 不文明用语 </span></p>
			<p class="content"><span>用户不得对网站其他用户以及网站工作人员，使用粗鄙、侵犯性及谩骂用语。用语场合包括但不限于：  </span></p>
			<p class="content"><span>• 站内信/邮件； </span></p>
			<p class="content"><span>• 及时通讯方式，例如：QQ、微信； </span></p>
			<p class="content"><span>• 电话或者会议； </span></p>
			
			<p class="content"><span>(4) 垃圾信息 </span></p>
			<p class="content"><span>服务商不得在网站以任何方式，发布垃圾、广告信息。这些情况包括但不限于：  </span></p>
			<p class="content"><span>• 投标内容与服务要求没有关联； </span></p>
			<p class="content"><span>• 利用网站上任何功能发送与服务和交易没有关系的广告、垃圾信息； </span></p>
			
			<p class="content"><span>(5) 违反法规或者有害他人的需求及服务  </span></p>
			<p class="content"><span>网站有权判定一项需求和服务是否存在涉嫌违法或者有害他人。这些情况包括但不限于：  </span></p>
			<p class="content"><span>• 破译、盗取他人的帐号和密码； </span></p>
			<p class="content"><span>• 入侵他人系统、非法更改数据； </span></p>
			<p class="content"><span>• 涉及黄色、毒品或赌博活动； </span></p>
			<p class="content"><span>• 涉及欺骗、作弊的活动； </span></p>
			<p class="content"><span>• 违反社会道德以及公序良俗； </span></p>
			
			<p class="content"><span>2. 一般违规行为的处理方式： </span></p>
			<p class="content"><span>(1) 第一次违规：暂停用户账号，用户需要支付500美元保证金，才可以继续使用网站的服务。保证金质押时间为6个月。记入不良记录。 </span></p>
			<p class="content"><span>(2) 第二次违规：账号永久封停，用户不能再次使用网站服务，保证金及用户账号余额不予退还。 </span></p>
			
			<p class="desc"><span>II. 一般违规行为</span></p>
			<p class="content"><span>1. 一般违规行为的界定 </span></p>
			<p class="content"><span>一般违规行为指下面列举的任何一项行为： </span></p>
			<p class="content"><span>(1) 不按承诺提供服务 </span></p>
			<p class="content"><span>• 做出服务承诺后不能履行 </span></p>
			<p class="content"><span>• 利用价格和文字误导交易 </span></p>
			<p class="content"><span>• 未按双方制定的工作协议工作 </span></p>
			<p class="content"><span>• 未按双方制定的工作协议交付结果 </span></p>
			<p class="content"><span>(2) 服务响应不及时。交易达成后，服务商的服务响应的时间不得超过2个工作日。这些情况包括但不限于： </span></p>
			<p class="content"><span>• 雇主选择服务商中标时 </span></p>
			<p class="content"><span>• 雇主购买了服务商出售的服务时 </span></p>
			<p class="content"><span>• 雇主委托AnyoneHelps联系服务商时 </span></p>
			<p class="content"><span>• 雇主对交付提出修改意见时 </span></p>
			<p class="content"><span>(3) 涉嫌抄袭／使用未授权作品、成果、素材或资源 </span></p>
			<p class="content"><span>(4) 不按要求投标，投标内容明显偏离任务要求 </span></p>
			
			<p class="content"><span>2. 一般违规行为的处理方式： </span></p>
			<p class="content"><span>• 第一次违规：网站予以书面提示，记入不良记录 </span></p>
			<p class="content"><span>• 第二次违规：网站予以书面警告，记入不良记录 </span></p>
			<p class="content"><span>• 第三次违规：暂停用户账号，用户需要支付500美元保证金，才可以继续使用网站的服务。保证金质押时间为6个月。记入不良记录。 </span></p>
			<p class="content"><span>• 第四次违规：账号永久封停，用户不能再次使用网站服务，保证金及用户账号余额不予退还。 </span></p>
			<p class="content"><span>网站将根据实际情况，不定期对本条例进行修订和完善。用户有责任经常查阅本条例，用户认为不能接受本条例时，应该向网站要求注销帐号，停止使用网站的一切服务 .</span></p>
		
			<p class="desc"><span></span></p>
		</div>
	</body>
</html>
