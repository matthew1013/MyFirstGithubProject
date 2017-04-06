<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta charset="UTF-8">
<meta name="keywords" content="留学生（International Student）,美国留学生活,US International Student Life,自由职业,Free-lancer,兼职,part-time,求助,help,美国威客,US witkey, 作业,assignment,论文,paper,essay,dissertation,兼职导游,tour guide,文书和申请,PS/RL/CV,RL,Recommendation Letter, PS,Personal Statement, CV,Curriculum Vitae,School Application,文章润色,proofreading,paraphrase,essay expansion,项目兼职,project part-time">
<meta name="description" content="Anyonehelps.com 致力于建立一个依托网络而成的C2C互助生态平台，我们提供最专业的服务，连接供需双方，一方面为在任何方面需要帮助的客户提供最优匹配的帮助者为他们解决实际问题，以最合理的成本获得最大的效用；另一方面为身怀绝技的人才们提供平台去帮助他人，以自身才能赚取最多的金钱。">
<title></title>
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/header.css" />
<link rel="stylesheet" type="text/css" href="/assets/layouts/global/css/footer.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/artDialog/css/ui-dialog.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/jquery-ui/jquery-ui.min.css" />
<link rel="stylesheet" type="text/css" href="/assets/global/plugins/icheck-1.x/skins/minimal/blue.css"/>
<link rel="stylesheet" type="text/css" href="/assets/global/iconfont/iconfont.css"/>
<link rel="shortcut icon" href="/assets/global/img/favicon.ico" type="image/x-icon"/> 

</head>
<body>
	
	
	<div class="header-top">
		<div class="top-nav row">
			<div class="logo-2">
				<a href="/">
				<img src="/assets/pages/img/index/logo-bottom.png"></a>
			</div>
			
		</div>
	</div>

<script id="show-evaluate-tmpl" type="html/x-jsrender">
<div class="user-evaluate">
	<div class="row">
		<span>接单人评级  </span><a href="/profile.jsp?userId={{:userId}}#tab_1_2" target="_blank"><span class="evaluate-count">Reviews （{{:evaluateCount}}）</span></a>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			诚信
		</div>
		<div class="middle">
			{{userEvaluateFormat honest evaluateCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat honest evaluateCount/}}
		</div>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			质量
		</div>
		<div class="middle">
			{{userEvaluateFormat quality evaluateCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat quality evaluateCount/}}
		</div>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			准时
		</div>
		<div class="middle">
			{{userEvaluateFormat punctual evaluateCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat punctual evaluateCount/}}
		</div>
	</div>
	<div class="row margin-top-10">
		<span>发单人评级  </span><a href="/profile.jsp?userId=3#tab_1_3" target="_blank"><span class="evaluate-count">Reviews （{{:evaluatePublishCount}}）</span></a>
	</div>
	<div class="row margin-top-10">
		<div class="left">
			诚信
		</div>
		<div class="middle">
			{{userEvaluateFormat honestPublish evaluatePublishCount/}}
		</div>
		<div class="right">
			{{userAvgEvaluateFormat honestPublish evaluatePublishCount/}}
		</div>
	</div>
</div>
</script>