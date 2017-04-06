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

<style>
.container{
	margin-top:30px;
}
.container ul{
	list-style-type: none;
}
   
</style>
</head>

<body>
<div class="container">
	<ul>
	  <li><strong>诚信规范服务承诺</strong><br>
	           作为AnyoneHelps网站的使用者，用户接受并遵守AnyoneHelps网站上公示的各项规则，以及本条例所规定的各项要求，承诺AnyoneHelps网站进行诚信规范的交易和服务。用户也同意本网站对本条例所界定的&ldquo;违规行为&rdquo;，予以相应的处置和处罚。 </li>
	  <li><strong>I. 严重违规行为</strong><br>
	           1.严重违规行为的界定 <br>
	           严重违规行为指下面列举的任何一项行为： <br>
	           (1) 线下交易 <br>
	           对于在AnyoneHelps上参与的所有交易及以及得知的所有用户，均不得以任何理由诱导和实施线下交易，也不得同意和参与他人提出的线下交易。这些情况包括但不限于： <br>
	           • 不通过AnyoneHelps托管交易款项； <br>
	           • 交易中部分款项通过AnyoneHelps支付，而另一部分通过AnyoneHelps以外的途径支付； <br>
	           • 试图诱导其他用户进行线下交易； <br>
	           (2) 虚假交易 <br>
	           用户不得发布和参与虚假交易，虚假交易中获得的任何收入，均不得到网站的认可。这些情况包括但不限于： <br>
	           • 使用非法获得的或未经授权的身份账号进行任何支付； <br>
	           • 选择自己或伙同他人作弊投标、中标，套取资金； <br>
	           • 在明知虚假交易的情况下，参与虚假交易； <br>
	           • 参与虚假交易并获得收入后，拒不退还虚假交易款项； <br>
	           • 交易本身没有实际服务或商品购买内容，变相套现、转账； <br>
	           (3) 不文明用语 <br>
	           用户不得对网站其他用户以及网站工作人员，使用粗鄙、侵犯性及谩骂用语。用语场合包括但不限于： <br>
	           • 站内信/邮件； <br>
	           • 及时通讯方式，例如：QQ、微信； <br>
	           • 电话或者会议； <br>
	           (4) 垃圾信息 <br>
	           服务商不得在网站以任何方式，发布垃圾、广告信息。这些情况包括但不限于： <br>
	           • 投标内容与服务要求没有关联； <br>
	           • 利用网站上任何功能发送与服务和交易没有关系的广告、垃圾信息； <br>
	           (5) 违反法规或者有害他人的需求及服务 <br>
	           网站有权判定一项需求和服务是否存在涉嫌违法或者有害他人。这些情况包括但不限于： <br>
	           • 破译、盗取他人的帐号和密码； <br>
	           • 入侵他人系统、非法更改数据； <br>
	           • 涉及黄色、毒品或赌博活动； <br>
	           • 涉及欺骗、作弊的活动； <br>
	           • 违反社会道德以及公序良俗； <br>
	           2.严重违规行为的处理方式： <br>
	           • 第一次违规：暂停用户账号，用户需要支付500美元保证金，才可以继续使用网站的服务。保证金质押时间为6个月。记入不良记录。 <br>
	           • 第二次违规：账号永久封停，用户不能再次使用网站服务，保证金及用户账号余额不予退还。 <br>
	    <strong>II. 一般违规行为</strong><br>
	           1. 一般违规行为的界定 <br>
	           一般违规行为指下面列举的任何一项行为： <br>
	           (1) 不按承诺提供服务 <br>
	           • 做出服务承诺后不能履行 <br>
	           • 利用价格和文字误导交易 <br>
	           • 未按双方制定的工作协议工作 <br>
	           • 未按双方制定的工作协议交付结果 <br>
	           (2) 服务响应不及时。交易达成后，服务商的服务响应的时间不得超过2个工作日。这些情况包括但不限于： <br>
	           • 雇主选择服务商中标时 <br>
	           • 雇主购买了服务商出售的服务时 <br>
	           • 雇主委托AnyoneHelps联系服务商时 <br>
	           • 雇主对交付提出修改意见时 <br>
	           (3) 涉嫌抄袭／使用未授权作品、成果、素材或资源 <br>
	           (4) 不按要求投标，投标内容明显偏离任务要求 <br>
	           2. 一般违规行为的处理方式： <br>
	           • 第一次违规：网站予以书面提示，记入不良记录 <br>
	           • 第二次违规：网站予以书面警告，记入不良记录 <br>
	           • 第三次违规：暂停用户账号，用户需要支付500美元保证金，才可以继续使用网站的服务。保证金质押时间为6个月。记入不良记录。 <br>
	           • 第四次违规：账号永久封停，用户不能再次使用网站服务，保证金及用户账号余额不予退还。 <br>
	           网站将根据实际情况，不定期对本条例进行修订和完善。用户有责任经常查阅本条例，用户认为不能接受本条例时，应该向网站要求注销帐号，停止使用网站的一切服务 .
	    </li>
	</ul>
	<p>&nbsp;</p>
	<ul>
	  <li>Welcome to Anyonehelps.com, the website and online service which is wholly owned by Tean Corp. in Los Angeles, CA USA. By using AnyoneHelps (including AnyoneHelps.com and its related sites, services and tools), you agree to the following terms with AnyoneHelps and Tean Corp.. This Agreement is effective between you and AnyoneHelps upon your acceptance of this Agreement.&nbsp;<br>
	    <br>
	    This User Agreement is part of AnyoneHelps's&nbsp;<strong>Terms of Service</strong>. This page explains the terms by which you may use the AnyoneHelps website, web widgets, feeds, mobile device software applications, applications for third-party web sites and services, and any other mobile or web services or applications owned, controlled, or offered by AnyoneHelps (collectively the &quot;<strong>Site</strong>&quot;). Before you may become a member of AnyoneHelps, you must read and accept all of the terms in, and linked to, the&nbsp;<strong>Terms of Service</strong>, which includes this User Agreement, Privacy Policy and other policies posted on the Site. AnyoneHelps strongly recommends that, as you read this User Agreement, you also access and read the linked information. By accepting this User Agreement, you agree that the User Agreement and the Privacy Policy will apply whenever you use AnyoneHelps sites or services, or when you use the tools AnyoneHelps makes available to interact with AnyoneHelps sites and services.&nbsp;<br>
	    <br>
	    Certain capitalized terms used in this User Agreement are defined below. AnyoneHelps may amend this Agreement at any time by posting the amended terms on the site. Except as stated elsewhere, all amended terms shall automatically be effective 30 days after they are initially posted, such amendments being agreed to by you by virtue of agreeing to the terms herein.</li>
	  <li><strong align="left"><u>ANYONEHELPS SERVICES</u></strong><br>
	    <br>
	    AnyoneHelps provides an online marketplace and workplace where those seeking non-physical services or online-to-offline services through the internet can find and employ providers of such services, and those providing non-physical services or online-to-offline service through the internet can find and provide such service to the parties seeking such service. Those seeking or purchasing such services are termed &quot;<strong>Task Initiators</strong>&quot;: those offering or providing such services are termed &quot;<strong>Providers</strong>&quot;. Any User may be an Task Initiator or a Provider. The Site contains functions that enable Task Initiators and Providers to do the following:&nbsp;</li>
	  <li><u>Post a Public Contest Pricing Task:&nbsp;</u>An Task Initiator posts a Task with an offered reward (price). Providers can participate by offering bidding price to the relevant Task and the Task Initiator will choose a Provider after evaluating their bidding price, skills, abilities and experience. Once the Task  is complete, the Task Initiator will pay the Provider the agreed upon price.&nbsp;</li>
	  <li><u>Post a Private Task:&nbsp;</u>An Task Initiator posts a Task and invites only following Providers to bid on it. The Task Initiator will choose a Provider after evaluating their bidding price, skills, abilities and experience. Once the Task  is complete, the Task Initiator will pay the Provider the agreed upon price.&nbsp;</li>
	  <li><br align="left">
	    In addition to the services provided above, AnyoneHelps also provides Skill Certificate, Certified Experts Community functions.<br>
	    Certified Experts Community: 中文：大牛社区：The Certified Experts Community is a service provided to AnyoneHelps' users to be certified as experts for certain skills or service realms. To become a Certified Experts Community member, a user must provide:</li>
	  <li>Certificate or Proof of related skills or knowledge</li>
	  <li>Complete and professional resume to support related skill or service experience </li>
	  <li>Personal statement to describe user's own expertise and experience supporting to become an expert</li>
	  <li>In addition, Certified Experts Community member is selected by AnyoneHelps and AnyoneHelps has all the explanatory right about the service. Find more details in Certified Experts Community Service Terms.<br>
	    AnyoneHelps, at its sole discretion, may provide other services upon specific agreement or arrangement with a User.</li>
	  <li><strong align="left"><u>ANYONEHELPS FEES</u></strong> <br>
	    <strong>Fees</strong> <br>
	    AnyoneHelps does charge fees for using some of its services as shown on the AnyoneHelps&nbsp;Fees Charge. When a User uses a service that has a fee, there is an opportunity to review and accept the fees that are to be charged based on the&nbsp;Fees Charge, which may change for every completely paid task. Change rate to that plan are posted on the AnyoneHelps site and are effective with at least fourteen days' notice. AnyoneHelps may temporarily change the Fees Rate due to promotional events (for example, free Task posting days may be offered) or when new services become available, and such changes are effective when the temporary promotional event or new service is posted on the site. By purchasing a service, you agree that the fee for that service as listed in the Fees Charge at the time of purchase is fair and just and may be lawfully collected by AnyoneHelps.<br>
	    Unless otherwise stated, all fees are quoted in US dollars. The User is responsible for paying all fees and applicable taxes associated with the AnyoneHelps sites and services in a timely manner into the User's AnyoneHelps Account.&nbsp;<br>
	    <strong><u>RELATIONSHIP BETWEEN TASK INITIATORS AND PROVIDERS</u></strong> <br>
	    <strong>Member Agreement</strong> <br>
	    Each User who becomes either an Task Initiator or Provider on any given Task  must enter into a Member Agreement with the other party involved on that same Task . AnyoneHelps shall not be a party to the Member Agreement. The Member Agreement shall consist of the following:&nbsp;</li>
	  <li>the&nbsp;<strong>Terms of Service</strong>;</li>
	  <li>the Task terms as awarded and accepted on the Site, to an extent not inconsistent with the&nbsp;<strong>Terms of Service</strong>;and</li>
	  <li>any other agreements between the Task Initiator and the Provider and posted on the Site, to an extent not inconsistent with the&nbsp;<strong>Terms of Service</strong>;</li>
	  <li><br align="left">
	    The order of precedence in resolving disputes among parties to a Member Agreement shall be as follows:&nbsp;</li>
	  <li>the&nbsp;<strong>Terms of Service</strong>;</li>
	  <li>the Task terms, as awarded and accepted on the Site in the Task detail page; and</li>
	  <li>any other agreements between the Task Initiator and the Provider and posted on the Site, to an extent not inconsistent with the&nbsp;<strong>Terms of Service</strong></li>
	  <li>Any terms or provisions of an agreement between Users that conflict with the&nbsp;<strong>Terms of Service</strong>&nbsp;shall be null and void, but all other terms or provisions shall remain in full force and effect.<br>
	    As a User, you agree that any terms of agreement with another User complies and does not conflict with the&nbsp;<strong>Terms of Service</strong>.</li>
	  <li>If a User acts as an Task Initiator, that User assumes full responsibility for managing, inspecting, and accepting the Provider's Services. The Task Initiator accepts that the Provider will offer such services in a time, place and manner determined by the Provider, unless otherwise specified in the Member Agreement. The Task Initiator also accepts full responsibility for paying for the Provider's Services to an extent that such payment is earned as per the Member Agreement. The Task Initiator agrees to pay on a payment schedule as provided in the Member Agreement, or, in the event there are no provisions for a payment schedule, in a timely manner. Any dispute of payment will be submitted to Website Experts Committee for review and arbitration. Website Experts Committee will make arbitration for final decision based on Member Agreement, Task Requirement, Provider's Final Deliverables and Works, and professional review and judgment by Website Experts Committee.</li>
	  <li>If a User acts as a Provider, that User assumes full responsibility for the performance and quality of the Provider's Services in accordance with the Member Agreement. The Provider further agrees that the Provider's Services will be performed in a timely manner consistent with industry practices.&nbsp;<strong>The Provider further acknowledges that some Provider's Services may be performed prior to payment by the Task Initiator and agrees that AnyoneHelps is not responsible for collecting payment for such services. The Provider is solely responsible for collecting any unpaid fees from an Task Initiator to whom services have been provided. </strong>Any dispute of payment in accordance with Member Agreement will be submitted to Website Experts Committee for review and arbitration. Website Experts Committee will make arbitration for final decision based on Member Agreement, Task Requirement, Provider's Final Deliverables and Works, and professional review and judgment by Website Experts Committee.</li>
	  <li></li>
	  <li>Any User, acting as either an Task Initiator or a Provider, covenants and agrees to act with good faith and fair dealing in the execution and performance of the Member Agreement.</li>
	  <li><strong align="left">Independent Contractor</strong> <br>
	    All Users agree that the parties to any Member Agreement are engaged in an independent contractor relationship. Nothing in the Member Agreement or this User Agreement shall be construed as to create a partnership, a joint venture, an agency, or an Employer-employee relationship between any party to any Member Agreement or between a User and AnyoneHelps.</li>
	  <li><strong align="left"><u>RELATIONSHIP WITH ANYONEHELPS</u></strong></li>
	  <li><strong align="left">AnyoneHelps Not a Party to Member Agreements</strong> <br>
	    AnyoneHelps is not a party to any Member Agreement. Any User, whether an Task Initiator or a Provider, agrees that AnyoneHelps is not responsible for the quality, safety or legality of services provided through the Site. Further, AnyoneHelps is not responsible for the truth or accuracy of Task postings, or in any other way for the fulfillment of any obligations of any User. The User agrees that any actions to enforce the terms of a Member Agreement shall be taken exclusively against the other party to the Member Agreement and not against AnyoneHelps.</li>
	  <li>AnyoneHelps makes no representations or guarantees about the credentials, qualifications, capabilities or professional record of any User.</li>
	  <li>Users acknowledge that information posted on the Site about any User or any third party is provided by such User or third party. AnyoneHelps makes no representations as to the veracity of such information. If such information is replied upon, Users hereby agree that AnyoneHelps is not liable for any misrepresentation so posted and that only the User or third party about whom such information is posted shall be liable for any damages incurred as a result of reliance on such information.</li>
	  <li></li>
	  <li><strong align="left">Third-Party Beneficiary of Member Contract</strong> <br>
	    In acknowledgement of the fact that the value of the Site depends on Users performing their obligations with respect to Task s and Member Agreements and in consideration of such fact, Users agree that any Member Agreement entered into with another User shall make AnyoneHelps a third-party beneficiary for purposes of enforcing the obligations created in that Agreement. Users agree that AnyoneHelps, in enforcing its Third Party Beneficiary Rights may suspend or terminate a User's account or right to use the Site along with any other actions AnyoneHelps may take according to Law as a Third Party Beneficiary. AnyoneHelps has sole discretion in determining and exercising its Third Party Beneficiary Rights.</li>
	  <li><strong align="left">Provider Form W-9 Requirements:</strong>&nbsp;A User acting as a Provider through the Site and entering and maintaining timely, complete and accurate Account information on the Site, will need to handle the W-9 form to Task Initiators who pay through the Site.<br>
	    &nbsp;                                                                                            <br>
	    <strong>Task Initiator Form 1099-MISC and Form 1096 Requirements:</strong>&nbsp;A User acting as an Task Initiator through the Site and paying a Provider via the Site, may request AnyoneHelps's 1099-MISC and/or 1096 service. In order to qualify for this service the Task Initiator must enter and maintain timely, complete and accurate Account information on the Site, including a social security number and/or tax identification number. AnyoneHelps retains sole discretion in determining whether an Task Initiator qualifies for the AnyoneHelps 1099-MISC or 1096 service. If an Task Initiator qualifies for this service, AnyoneHelps will automatically fulfill the Form 1099-MISC and Form 1096 requirements, if any, to Providers and to the United States Internal Revenue Service.</li>
	  <li>AnyoneHelps's provision of W-9, 1099-MISC and/or 1096 in no way obligates AnyoneHelps to provide any other services related to any User's tax or governmental recording obligations.</li>
	  <li><strong align="left">No Agency</strong> <br>
	    No agency, partnership, joint venture, employee-Task Initiator or franchiser-franchisee relationship is intended or created by this Agreement. </li>
	  <li><strong align="left"><u>USING ANYONEHELPS</u></strong></li>
	  <li>While using AnyoneHelps sites, services and tools, a User will not:</li>
	  <li>Post content or items of an inappropriate nature for legitimate business to be conducted by Users of the Site as determined by AnyoneHelps at its sole discretion;</li>
	  <li>Violate any laws, third party rights or AnyoneHelps policies;</li>
	  <li>Use AnyoneHelps's sites, services or tools if the User is not able to form legally binding contracts, or is under the age of 18, or is temporarily or indefinitely suspended from using AnyoneHelps's sites, services or tools;</li>
	  <li>When acting as an Task Initiator, fail to deliver payment for items purchased, unless the Provider has materially changed the item's description after the bid, or a clear typographical error is made, or the Task Initiator cannot authenticate the Provider's identity;</li>
	  <li>When acting as a Provider, fail to deliver services purchased, unless the Task Initiator fails to meet the requested services, or the Provider cannot authenticate the Task Initiator's identity;</li>
	  <li>Circumvent or manipulate the Fees Charge, the billing process, or the fees owed to AnyoneHelps;</li>
	  <li>Post false, inaccurate, misleading, defamatory, or libelous content (including personal information);</li>
	  <li>Take any action that may undermine the feedback or ratings systems (including but not limited to displaying, importing or exporting feedback information off of the sites or using it for purposes unrelated to AnyoneHelps);</li>
	  <li>Transfer a AnyoneHelps account (including feedback) and User ID to another party without AnyoneHelps's consent;</li>
	  <li>Distribute or post spam, unsolicited, or bulk electronic communications, chain letters, or pyramid schemes;</li>
	  <li>Distribute viruses or any other technologies that may harm AnyoneHelps, or the interests or property of AnyoneHelps Users;</li>
	  <li>Copy, modify or distribute rights or content from the AnyoneHelps sites, services or tools or AnyoneHelps's copyrights and trademarks; or</li>
	  <li>Harvest or otherwise collect information about Users, including email addresses, without User' consent.</li>
	  <li></li>
	  <li>To register for an Account with AnyoneHelps and become a Member, a User accepts all of the terms and conditions in, and linked to, this Agreement. By becoming a Member, a User agrees to:</li>
	  <li>Abide by this Agreement and the processes, procedures, and guidelines described throughout the Site;</li>
	  <li>Be financially responsible for use of the Site and for the purchase or delivery of Provider Services; and</li>
	  <li>Perform such obligations as specified by any accepted Member Agreement, unless such obligations are prohibited by law or by this Agreement.</li>
	  <li><strong align="left"><u>ABUSING ANYONEHELPS</u></strong></li>
	  <li>AnyoneHelps is working hard to keep its sites and services working properly and to keep the Community safe. Please report problems, offensive content, and policy violations.</li>
	  <li>Without limiting other remedies, AnyoneHelps may limit, suspend or terminate its service and User accounts, prohibit access to its sites and their content, services and tools, delay or remove hosted content, and take technical and legal steps to keep Users off the sites if these are perceived to be creating problems or possible legal liabilities, infringing on the intellectual property rights of third parties, or acting inconsistently with the letter or spirit of AnyoneHelps's policies. Additionally, AnyoneHelps may, in appropriate circumstances and at its discretion, suspend or terminate accounts of Users who may be repeat infringers of intellectual property rights of third parties. AnyoneHelps also reserves the right to cancel unconfirmed accounts or accounts that have been inactive for an extended period, or to modify or discontinue AnyoneHelps sites, services or tools.</li>
	  <li><strong align="left"><u>ACCOUNTS</u></strong></li>
	  <li>To become a Member and access Site Services through the Site you must register for an &quot;<strong>Account.</strong>&quot; You agree to provide true, accurate and complete information as prompted by the registration form and all forms you access on the Site, and to update this information to maintain its truthfulness, accuracy and completeness.</li>
	  <li>You as a Member represent, warrant, and agree to grant access to your Account to Users authorized to act on your behalf and only in accordance with this Agreement. Additionally, you represent, warrant, and agree to be fully responsible and liable for any action of any User who uses your Account. As a Member, you agree:</li>
	  <li>not to use any Account, Username, or password of another User of the Site that you are not authorized to use, and</li>
	  <li>not to allow others who are not authorized to do so to use your Account at any time.</li>
	  <li></li>
	  <li>Your AnyoneHelps Account (including feedback) and Username are not transferable, and any transfer or attempted transfer to another party is null and void.</li>
	  <li><strong align="left"><u>USERNAMES AND PASSWORDS</u></strong></li>
	  <li>When you as a Member register for an Account, you will be asked to choose a Username or Email Address and Password for the Account.</li>
	  <li>As a Member, you agree that you are entirely responsible for safeguarding and maintaining the confidentiality of the Username or Email Address and Password you use to access this Site. You authorize AnyoneHelps to assume that any person using the Site with your Username or Email Address and Password is you or is authorized to act for you. You agree to notify us immediately if you suspect or become aware of any unauthorized use of the Account or access to your Password.</li>
	  <li><strong align="left"><u>THIRD-PARTY CONTENT</u></strong></li>
	  <li><strong align="left">Verification and Monitoring</strong> <br>
	    AnyoneHelps makes available to Members on the Site various services provided by third parties to verify a Member's credentials, or to provide information. Any opinions, advice, statements, services, offers or other information or content expressed or made available by these third parties or any other Members are those of the respective author(s) or distributor(s) and are solely the opinions, advice, statements, services, offers or other information or content made by or provided by those other parties. AnyoneHelps neither endorses nor is responsible for the accuracy or reliability of any opinion, advice, information or statement made on the Site by anyone other than authorized AnyoneHelps employees acting in their official capacities.</li>
	  <li><strong align="left">Links</strong></li>
	  <li>This Site may contain links to third party web sites, services, or resources. Such web sites, services, and resources are owned and operated by the third-parties and/or Providers and their licensors. Your access and use of those web sites, services, and/or resources, including online communication services not limited to chat, email and calls will be governed by the terms and policies of the applicable web site, service, resource or Provider. Users acknowledge and agree that AnyoneHelps is not responsible or liable for:</li>
	  <li>the availability or accuracy of such sites, services, or resources; or</li>
	  <li>the content, advertising, or products on or available from such sites, services, or resources.</li>
	  <li></li>
	  <li>The inclusion of any link on the Site does not imply that AnyoneHelps endorses the linked site. Use of the links and these services is at the User's own risk.</li>
	  <li><strong align="left"><u>LICENSES AND SITE ACCESS</u></strong></li>
	  <li>When a User provides content, AnyoneHelps is granted a non-exclusive, worldwide, perpetual, irrevocable, royalty-free, sub-licensable (through multiple tiers) right to exercise any and all copyright, trademark, publicity, and database rights (but no other rights) with respect to the content, in any media known now or in the future.</li>
	  <li><strong align="left">Access and Interference</strong></li>
	  <li>Users will not use any robot, spider, scraper or other automated means to access the sites for any purpose without express written permission signed by hand.</li>
	  <li>Additionally, Users will not:</li>
	  <li>take any action that imposes or may impose (as determined at AnyoneHelps's sole discretion) an unreasonable or disproportionately large load on the infrastructure;</li>
	  <li>copy, reproduce, modify, create derivative works from, distribute, or publicly display any content (except for your information) from AnyoneHelps sites without the prior express written permission of AnyoneHelps and the appropriate third party, as applicable;</li>
	  <li>interfere or attempt to interfere with the proper working of the sites, services or tools, or any activities conducted on or with AnyoneHelps sites, services or tools; or</li>
	  <li>bypass measures AnyoneHelps may use to prevent or restrict access to the sites.</li>
	  <li></li>
	  <li><strong align="left">Additional User Representations and Warranties</strong></li>
	  <li>The User shall be solely responsible for User Content and the consequences of posting or publishing it. In connection with User Content, the User affirms, represents and warrants, in addition to the other representations and warranties in this Agreement, the following:</li>
	  <li>a. To be at least 18 years of age or a legal entity and be fully able and competent to enter into the terms, conditions, obligations, affirmations, representations, and warranties set forth in this Agreement, and to abide by and comply with this Agreement.<br>
	    b. To have the written consent of each and every identifiable natural person in the User Content to use such person's name or likeness in the manner contemplated by the Site and this Agreement, and each such person has released you from any liability that may arise in relation to such use.<br>
	    c. To not infringe on any rights of any third party, including but not limited to any Intellectual Property Rights, privacy rights and rights of publicity.<br>
	    d. AnyoneHelps may exercise its rights to your User Content granted under this Agreement without liability for payment of any guild fees, residuals, payments, fees, or royalties payable under any collective bargaining agreement or otherwise.</li>
	  <li><strong align="left">AnyoneHelps License to User</strong></li>
	  <li>Subject to and conditional upon compliance with this Agreement, AnyoneHelps grants the User a limited license to access and, if you are a Member, to use this Site internally for the purpose of ordering and receiving the Site Services available and authorized from this Site. Users must not sell, reproduce, distribute, modify, display, publicly perform, prepare derivative works based on, repost or otherwise use any content of this Site in any way for any public or commercial purpose without prior written consent of AnyoneHelps or the rights holder. You must not use any content of this Site on any other Web site or in a networked computer environment for any purpose except your own internal viewing. You will not attempt to reverse engineer or attempt to interfere with the operation of any part of this Site unless expressly permitted by law. This Site or any portion of this Site may not be reproduced, duplicated, copied, sold, resold or otherwise exploited for any commercial purpose that is not expressly permitted by AnyoneHelps.</li>
	  <li>AnyoneHelps and its licensors retain all of its right, title and interest in and to all patent rights, inventions, copyrights, know-how, and trade secrets relating to the Site. The AnyoneHelps logo and name are trademarks of AnyoneHelps, and may be registered in certain jurisdictions. All other product names, company names, marks, logos and symbols on the Site may be the trademarks of their respective owners.</li>
	  <li>Except as expressly stated above, nothing in this Agreement confers any license under any of AnyoneHelps's or any third party's Intellectual Property Rights, whether by estoppels, implication or otherwise.</li>
	  <li><strong align="left"><u>LEGAL DISPUTES</u></strong></li>
	  <li>If a dispute arises between a User and AnyoneHelps, the goal is to have a neutral and cost-effective means of resolving the dispute quickly. Accordingly, the User and AnyoneHelps agree to resolve any claim or controversy at law or equity that arises out of this Agreement or AnyoneHelps's services (a &quot;Claim&quot;) in accordance with one of the subsections below or as is otherwise agreed to in writing. Before resorting to these alternatives, Users are strongly encouraged to first contact AnyoneHelps directly to seek a resolution.<br>
	    &nbsp;&nbsp;<br>
	    <strong>Law for Legal Disputes and Forum for Enforcement</strong></li>
	  <li>This Agreement shall be governed in all respects by the laws of the State of California as they apply to agreements entered into and to be performed entirely within Texas between Texas residents, without regard to conflict of law provisions. </li>
	  <li><strong align="left">Improperly Filed Claims</strong></li>
	  <li>Claims brought against AnyoneHelps must be resolved in accordance with this Legal Disputes Section. All claims filed or brought contrary to the Legal Disputes Section shall be considered improperly filed. For claims filed contrary to the Legal Disputes Section, AnyoneHelps may recover attorneys' fees and costs, if AnyoneHelps has provided written notification of the improperly filed claim, and the claim has not been promptly withdrawn.</li>
	  <li><strong align="left"><u>TERMINATION AND SUSPENSION</u></strong></li>
	  <li>This Agreement shall become effective as the User's contractual agreement upon acceptance or use of the Site, and shall continue until the User's Account is terminated by AnyoneHelps or the User as provided for under the terms of this Section.</li>
	  <li>Unless otherwise agreed to in writing between the parties, either party may terminate the contractual agreement represented by this Agreement at any time upon notice to the other party. In the event a User properly terminates the contractual agreement represented by this Agreement, the User's Account is automatically terminated and</li>
	  <li>AnyoneHelps shall continue to perform those AnyoneHelps Services necessary to complete any open transaction between the User and another Member; and</li>
	  <li>the User shall continue to be obligated to pay any amounts accrued but unpaid as of the date of termination to AnyoneHelps for any Site Services and to any Providers for any Provider Services.</li>
	  <li></li>
	  <li>Without limiting AnyoneHelps's other remedies, AnyoneHelps may issue a warning, or temporarily suspend, indefinitely suspend or terminate an Account or a Task , and refuse to provide any or all Site Services if:</li>
	  <li>A User breaches the letter or spirit of any terms and conditions of this Agreement or the linked policies and information incorporated herein by reference, including the written policies and procedures posted on the Site;</li>
	  <li>AnyoneHelps suspects or becomes aware that a User has provided false or misleading information; or</li>
	  <li>AnyoneHelps believes in its sole discretion that a User's actions may cause legal liability for that User, to Members or to AnyoneHelps or are contrary to the interests of the Site or the AnyoneHelps User Community.</li>
	  <li></li>
	  <li>Once indefinitely suspended or terminated, a User must cease use of the Site under any Account and may not re-register under a new Account.</li>
	  <li>In addition, violations of this Agreement may be prosecuted to the fullest extent of the law and may result in additional penalties and sanctions.</li>
	  <li>Without limiting AnyoneHelps's other remedies, to the extent a User engages in actions or activities which circumvent the AnyoneHelps Site or otherwise reduce fees owed to AnyoneHelps under this Agreement, a User must pay AnyoneHelps for all fees owed to AnyoneHelps and reimburse AnyoneHelps for all losses and costs (including any and all AnyoneHelps employee time) and reasonable expenses (including attorney fees) related to investigating any breach and collecting such fees.</li>
	  <li>When a User's Account is suspended or terminated for any reason, that User may no longer have access to data, messages, files and other material kept on the Site. The material may be deleted along with all previous posts and proposals.</li>
	  <li><strong align="left">Notification of AnyoneHelps Members</strong></li>
	  <li>Users acknowledge and agree that the value, reputation and goodwill of the Site depends on the transparency of Member Account status to all Members, including Members who are participating in Tasks together. Users therefore agree as follows: IF ANYONEHELPS SUSPENDS OR TERMINATES A USER'S ACCOUNT OR TASK , ANYONEHELPS HAS THE RIGHT BUT NOT THE OBLIGATION TO</li>
	  <li>NOTIFY OTHER MEMBERS ENGAGED IN ACTIVE TASKS WITH THAT USER TO INFORM THEM OF THE SUSPENDED OR TERMINATED ACCOUNT OR TASK  STATUS, AND</li>
	  <li>PROVIDE THOSE MEMBERS WITH SUMMARY REASONS FOR THE ACTION.</li>
	  <li></li>
	  <li><strong align="left"><u>LIMITATION OF LIABILITY</u></strong></li>
	  <li>Users will not hold AnyoneHelps responsible for other Users' content, actions or inactions, Tasks and Bids they post or other User's destruction of allegedly fake items. Users acknowledge that AnyoneHelps's sites are marketplaces to allow anyone to request, offer, sell, and buy various services, non-physical products and digital goods, at any time, from anywhere. AnyoneHelps is not involved in the actual transaction between Task Initiators and Providers. While AnyoneHelps may help facilitate the resolution of disputes through various programs, it has no control over and does not guarantee the quality, safety or legality of items advertised, the truth or accuracy of Users' content or listings, the ability of Providers to provide services, the ability of Task Initiators to pay for services, or that an Task Initiator or Provider will actually complete a transaction.</li>
	  <li>AnyoneHelps does not transfer legal ownership of items (e.g. designs and works) from the Provider to the Task Initiator. Further, AnyoneHelps cannot guarantee continuous or secure access to its sites, services or tools, and operation of its sites, services or tools may be interfered with by numerous factors outside of its control. Accordingly, to the extent legally permitted, AnyoneHelps excludes all implied warranties, terms and conditions. AnyoneHelps is not liable for any loss of money, goodwill or reputation, or any special, indirect or consequential damages arising, directly or indirectly, out of the use of its sites, services and tools or the inability to use its sites, services and tools.</li>
	  <li>Regardless of the previous paragraphs, if AnyoneHelps is found to be liable, that liability is limited to the greater of (a) any amounts due up to the full cost of the item and (b) the total fees (under AnyoneHelps Fees and Services) paid to AnyoneHelps in the 12 months prior to the action giving rise to the liability.</li>
	  <li><strong align="left"><u>RELEASE</u></strong></li>
	  <li>When one or more Users has a dispute, AnyoneHelps and its officers, directors, agents, subsidiaries, joint ventures and employees are released from claims, demands and damages (actual and consequential) of every kind and nature, known and unknown, arising out of or in any way connected with such disputes.</li>
	  <li><strong align="left"><u>WARRANTY DISCLAIMER</u></strong></li>
	  <li>THE SITE SERVICES PROVIDED BY ANYONEHELPS OR ANY OF ITS LICENSORS OR THIRD-PARTY SERVICE PROVIDERS ARE PROVIDED &quot;AS IS,&quot; AS AVAILABLE, AND WITHOUT ANY WARRANTIES OR CONDITIONS (EXPRESS OR IMPLIED, INCLUDING THE IMPLIED WARRANTIES OF MERCHANTABILITY, ACCURACY, FITNESS FOR A PARTICULAR PURPOSE, TITLE AND NONINFRINGEMENT, ARISING BY STATUTE OR OTHERWISE IN LAW OR FROM A COURSE OF DEALING OR USAGE OR TRADE). ANYONEHELPS MAKES NO REPRESENTATIONS OR WARRANTIES, OF ANY KIND, EITHER EXPRESS OR IMPLIED, AS TO THE QUALITY, IDENTITY OR RELIABILITY OF ANY THIRD PARTY, OR TO THE ACCURACY OF THE POSTINGS MADE ON THE WEB SITE BY ANY THIRD PARTY. SOME STATES AND JURISDICTIONS DO NOT ALLOW FOR ALL THE FOREGOING LIMITATIONS ON IMPLIED WARRANTIES, SO TO THAT EXTENT, IF ANY, SOME OR ALL OF THE ABOVE LIMITATIONS MAY NOT APPLY TO YOU.</li>
	  <li><strong align="left"><u>INDEMNITY</u></strong></li>
	  <li>You will indemnify and hold AnyoneHelps (and its officers, directors, agents, subsidiaries, joint ventures and employees) harmless from any claim or demand, including reasonable attorneys' fees, made by any third party due to or arising out of any breach of this Agreement, or your violation of any law or the rights of a third party.</li>
	  <li><strong align="left"><u>NOTICES</u></strong></li>
	  <li>Except as explicitly stated otherwise, legal notices shall be served on ZBJ Network Inc (in the case of notices to AnyoneHelps) or to the email address a User provides to AnyoneHelps during the registration process (in the case of notices to a User). Notice shall be deemed given 24 hours after an email is sent, unless the sending party is notified that the email address is invalid. Alternatively, AnyoneHelps may give legal notice by mail to the mailing address a User provides during the registration process. In such cases, notice shall be deemed given three days after the date of mailing.</li>
	  <li><strong align="left"><u>PRIVACY</u></strong></li>
	  <li>AnyoneHelps does not sell or rent personal information to third parties for their marketing purposes without a User's explicit consent. AnyoneHelps uses personal information only as described in the AnyoneHelps Privacy Policy. AnyoneHelps views protection of Users' privacy as a very important community principle. Information is stored and processed on computers located in the United States that are protected by physical as well as technological security devices. A User can access and modify the information provided. AnyoneHelps uses third parties to verify and certify its privacy principles. For a complete description of how personal information is used and protected, see the AnyoneHelps Privacy Policy. If you object to information being transferred or used in this way please do not use AnyoneHelps's services.</li>
	  <li><strong align="left"><u>GENERAL</u></strong></li>
	  <li>AnyoneHelps is a website wholly owned by Tean Corp.. located at 404 E Huntington dr ,Arcadia ,CA91006. If any provision of this Agreement is held to be invalid or unenforceable, such provision shall be struck and the remaining provisions shall be enforced. In AnyoneHelps's sole discretion, it may assign this Agreement in accordance with the Notices Section.</li>
	  <li>Headings are for reference purposes only and do not limit the scope or extent of such section. AnyoneHelps's failure to act with respect to a breach by a User or others doesn't waive its right to act with respect to subsequent or similar breaches. AnyoneHelps does not guarantee it will take action against all breaches of this Agreement.</li>
	  <li>AnyoneHelps may amend this Agreement at any time by posting the amended terms on this site. Except as stated elsewhere, all amended terms shall automatically be effective 30 days after they are initially posted. This Agreement may not be otherwise amended except in writing hand signed by you (or your authorized agent) and an authorized agent of Tean Corp.. For purposes of this provision, &quot;writing&quot; doesn't include an email message and &quot;hand signed&quot; doesn't include an electronic signature.</li>
	  <li>This Agreement sets forth the entire understanding and agreement between us with respect to the subject matter hereof.</li>
	  <li><strong align="left"><u>CONTACTING US</u></strong></li>
	  <li>If you wish to report a violation of the&nbsp;<strong>Terms of Service</strong>, have any questions or need assistance, please contact a AnyoneHelps Customer Support Representative as follows:</li>
	  <li><strong align="left">Web Support:&nbsp;</strong> <br>
	    <strong>Email:&nbsp;</strong><br>
	    <strong>Phone:&nbsp;</strong>(Mon-Fri, 9 a.m. - 6 p.m. Pacific Time): <br>
	    <strong>Online Help Topics:&nbsp;</strong>http://www.AnyoneHelps.com/help <br>
	    <br>
	    <strong><u>DEFINED TERMS</u></strong></li>
	  <li>Any capitalized term not otherwise defined in this Agreement has the meaning given such term on the Site. As used throughout this Agreement:<br>
	    &quot;<strong>Account</strong>&quot; means a User's account with AnyoneHelps established upon registration on the Site.<br>
	    &quot;<strong>Task Initiator</strong>&quot; means a person or party who posts a Task on the Site.<br>
	    &quot;<strong>Provider</strong>&quot; means a person or party who provides non-physical services or online-to-offline service such as designing, engineering, consulting and writing, etc. through the internet via the Site.<br>
	    &quot;<strong>Provider Services</strong>&quot; means all services delivered by Providers.<br>
	    &quot;<strong>AnyoneHelps</strong>&quot; in context of stating or describing a power or right held by &quot;AnyoneHelps&quot; also means &quot;Tean Corp..&quot;<br>
	    &quot;<strong>AnyoneHelps Services</strong>&quot; means AnyoneHelps' provision of a market place, workplace and other services defined above to its Users through the Site. The term AnyoneHelps Services does not include Provider Services or any services provided by any third party.<br>
	    &quot;<strong>Intellectual Property Rights</strong>&quot; means any rights appurtenant to patents, copyrights, , trademarks, trade secrets, and goodwill as well as rights of publicity, trade dress and service mark rights, moral rights, mask work rights, and any other intellectual property rights as now may exist or may come into existence in the future. The term shall also apply to all applications for such rights as well as registrations, renewals and extensions. The term shall apply for such property rights governed by the laws of the United States, any other country, any state, territory or any other jurisdiction.<br>
	    &quot;<strong>Member</strong>&quot; means a person or legal entity that registers an Account with the Site. For purposes of this Agreement, &quot;Member&quot; also means &quot;User&quot;.<br>
	    &quot;<strong>Secure Areas</strong>&quot; means portions of the site that are encrypted using the Hypertext Transfer Protocol Secure (also known as &quot;HTTPS&quot;) or any other encryption mechanism.<br>
	    &quot;<strong>Site Services</strong>&quot; means &quot;AnyoneHelps services&quot;.<br>
	    &quot;<strong>Task </strong>&quot; means a Task or project posted on the Site.<br>
	    &quot;<strong>User</strong>&quot; means (1) a person who is a Member, using the Site on his or her own behalf, or (2) a person who is using the Site on behalf of a Member that is a company or organization.<br>
	    &quot;<strong>Workplace</strong>&quot; means the Site Services that allow for communication and interaction between an Task Initiator and Provide.</li>
	</ul>
	<p>&nbsp;</p>
	
	
	<ul>
	  <li><a name="OLE_LINK4" align="center"></a><a name="OLE_LINK3">Certified Experts Community Service Terms</a><br>
	    大牛社区服务协议 <br>
	           欢迎使用Anyonehelps.com提供的大牛社区（Certified Experts Community）服务，大牛社区是AnyoneHelps提供给具有相关技能领域有突出才能和出色经历以及证明的高级人才的一个服务社区，该社区的建立旨在聚集各领域技能最优秀、最专业、最负责任的人才，为他们提供更多的服务并为他们提供更多在AnyoneHelps平台上服务他人的机会。您在使用大牛社区的服务前，请仔细阅读此条款。 <br>
	           您在申请成为大牛并使用大牛社区时点击&ldquo;同意协议&rdquo;按钮即表示您已经和AnyoneHelps达成协议，完全接受本协议规定的全部内容。您一旦使用该项服务，即视为您已经完全接受并同意本协议条款各项内容，包括AnyoneHelps对条款随时进行的任何修改。原则上，AnyoneHelps没有责任通知关于条款的修改，但是所有的修正将在条款更新发布后30天之后自动生效。 </li>
	  <li>I. 大牛社区服务内容： <br>
	           大牛（Certified Experts）指经过网站专家审核认证的在某一技能领域或服务领域等具有专业水准、能提供专业认证、有出色和丰富的实践经验并且对该领域有相当权威的高级人才。 AnyoneHelps需要提出申请的用户提供以下内容： <br>
	           1. 关于相关技能或服务领域的一个专业自述，对自己的专业能力和过往专业经历有清晰的表述 <br>
	           2. 提供由相关权威机构或学校颁发的专业证明或学位学术证明（如果有） <br>
	           3. 提供完整并专业的简历，能表明该申请用户有相关领域的专业经历 <br>
	           4. 提供相关技能或服务领域过往的优秀作品或者代表作（如果有） <br>
	           5. 其他能够证明和支持证明申请人具有专家级水准的材料 <br>
	           用户必须提供以上资料并保证材料的真实性和可靠性，任何虚假的材料都不会被接受，一旦发现在申请成为大牛过程中有不诚信的行为，AnyoneHelps将对该账号进行永久封号。AnyoneHelps评审团和相关团队具有对认证结果的最终解释权。 </li>
	  <li>
	           大牛（Certified Experts）可使用以下服务： <br>
	           1. 大牛评审：可对申请成为大牛社区成员进行评审和投票 <br>
	           2. 大牛仲裁：可以对普通用户提供的相关领域的仲裁案提供专业仲裁并获取仲裁报酬 <br>
	           3. 大牛搜索：可以在只针对大牛的用户搜索列表出现 <br>
	           4. 技能审核：可以对普通用户申请的技能认证进行审核并获取认证报酬 <br>
	           5. 大牛任务：可优先接受相关任务推送 </li>
	  <li>II. 大牛社区使用义务 <br>
	           为了能使用此业务，大牛用户必须做到： <br>
	           1. 提供真实的个人资料、简历、作品、证书等个人材料，并确保使用过程中的头像、邮箱、用户名等资料具有有效性和合法性。 <br>
	           2. 及时对分配的审核和仲裁做出回应，并保证评审内容属于公正、公平、专业，在评审过程中没有收受贿赂、恶意裁决有失道德和违法的行为。 <br>
	           3. 保持服务使用过程中的专业性和公正性。 <br>
	           在账户使用过程当中，若大牛用户提供任何不真实、不诚信、虚假的个人材料，或者在使用网站过程中有任何违法行为，或者AnyoneHelps怀疑您的账号与操作属于恶意程序或存在恶意操作，以及没有履行以上提到的使用义务，AnyoneHelps有权取消该用户的大牛资格或对该账号进行暂停服务或封号处理，情节严重者将诉诸法律。 </li>
	  <li>III. 大牛用户终止： <br>
	           除上述II.中提到的相关条款外，AnyoneHelps有权根据需要对大牛用户群体进行人员调整或整顿，原则上每个技能领域大牛人数不超过十人。 <br>
	           AnyoneHelps可能会对服务内容进行变更，也可能会中断、中止或终止服务。该协议建立在用户理解并同意，AnyoneHelps有权自主决定经营策略，关于大牛社区的服务内容和运营方法，AnyoneHelps没有义务通知用户相关变更，<br>
	           如发生下列任何一种情形，腾讯有权不经通知而中断或终止向您提供的服务：<br>
	           1. 根据法律规定您应提交真实信息，而您提供的个人资料不真实、或与注册时信息不一致又未能提供合理证明；<br>
	           2. 您违反相关法律法规或本协议的约定；<br>
	           3. 按照法律规定或主管部门的要求；<br>
	           4. 出于安全的原因或其他必要的情形。  </li>
	  <li>IV. 其他 <br>
	           其他关于网站的相关使用条款和隐私条款，请参考User Agreement and Privacy Policy。 </li>
	  <li></li>
	</ul>
</div>
</body>
</html>
