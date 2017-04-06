<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
	<%@ include file="/include/header.jsp"%>
	<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="/assets/pages/css/us-contactus.css" />
	<!--content-->
	<% 
		String title = "";
		String keywords = "";
		String description = "";
		Seo s = SeoUtil.getSeoByWebFlag("2");
		if(s != null){
			title = s.getTitle();
			keywords = s.getKeywords();
			description = s.getDescription();
		}
	%>
	<input type="hidden" name="seoTitle" value="<%=title %>">
	<input type="hidden" name="seoKeywords" value="<%=keywords %>">
	<input type="hidden" name="seoDescription" value="<%=description %>">
	
	<div style="height:70px" >	
	</div>
	<div class="row bg hidden-xs">	
	</div>
	
	<div class="contact-info">
		<div class="content">
			<div class="row title">
				<span>联系我们</span>
			</div>
			<div class="row title-en">
				<span>Anyonehelps</span>
			</div>
			
			<div class="row detail">
				<div class="col-xs-5 col-sm-5 col-dm-6 col-lg-6">
					<span>Arcadia California </span>
				</div>
				<div class="col-xs-7 col-sm-7 col-dm-6 col-lg-6">
					<img src="/assets/pages/img/contactus/phone.png">
					<span>(626) 662-2748</span>
				</div>
			</div>
			<div class="row detail">
				<div class="col-xs-5 col-sm-5 col-dm-6 col-lg-6">
					<span>zip:91006</span>
				</div>
				<div class="col-xs-7 col-sm-7 col-dm-6 col-lg-6">
					<img src="/assets/pages/img/contactus/email.png">
				<span>help@anyoenhelps.com</span>
				</div>
			</div>
		</div>
	</div> 
	
	
	<div class="row contact-us">
		<div class="row title">
			<span>联系我们</span>
		</div>
	</div>
	
	<div class="row contact-from">
		<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
		<div class="left">
			<div class="name">
				<span class="red-star">*</span>
				<input type="text" name="name" class="form-control" placeholder="请输入您的名字 ">
			</div>
			<div class="email">
				<span class="red-star">*</span>
				<input type="text" name="email" class="form-control" placeholder="请输入您的邮箱 " value="${user_email_session_key}">
			</div>
			<div class="phone">
				<span class="red-star"></span>
				<select class="form-control area-code">
					<option value="+1">(+1)美国/加拿大</option>
                    <option value="+86">(+86)中国</option>
                    <option value="+44">(+44)英国</option>
                    <option value="+61">(+61)澳洲</option>
				</select>
				<input type="text" name="telphone" class="form-control" placeholder="请输入您的手机号码 " value="">
			</div>
			<div class="title">
				<span class="red-star">*</span>
				<input type="text" name="title" class="form-control" placeholder="请输入主题">
			</div>
			<div class="content">
				<span class="red-star">*</span>
				<textarea type="text" name="content" rows="4" placeholder="写点什么…" class="form-control radius0"></textarea>
			</div>
			<div>
				<span class="red-star"></span>
				<a href="javascript:;" class="btn submit-btn">发送消息</a>
			</div>
		</div>
		</div>
		<div class="col-xs-12 col-sm-12 col-dm-6 col-lg-6">
		<div class="right">
			<div class="map"></div>
		</div>
		</div>
	</div>
	


<%@ include file="/include/footer.jsp"%>
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script src='//maps.google.com/maps/api/js?sensor=false&libraries=places' type="text/javascript"></script>
<script src="/assets/global/plugins/locationpicker.jquery.js"></script>

<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>

<script type="text/javascript">
$(function() {
	$("title").html($(":hidden[name='seoTitle']").val());
	$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
	$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
	
	$('.map').locationpicker({
	    location: {latitude: 34.1390081, longitude: -118.0219849},
	    locationName: "440 E Huntington Dr, Arcadia, CA 91006",
	    radius: 0,
	    zoom: 9,
	});
	
	$(".submit-btn").click(function(){
		var name = $(".contact-from").find("input[name='name']").val();
		var email = $(".contact-from").find("input[name='email']").val();
		var areaCode = $(".contact-from").find(".area-code").val();
		var telphone = $(".contact-from").find("input[name='telphone']").val();
		var subject = $(".contact-from").find("input[name='title']").val();
		var description = $(".contact-from").find("textarea[name='content']").val();
		var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(!reg.test(email)&&(telphone==null||telphone=="")) {
			$.errorMsg("邮箱或者手机号码必须填写一项！");
			return false;
		}
		if(name==null||name=="") {
			$.errorMsg("请输入有效的名字！");
			return false;
		}
		if(subject==null||subject=="") {
			$.errorMsg("请输入有效的主题！");
			return false;
		}
		if(description==null||description=="") {
			$.errorMsg("请输入有效的内容！");
			return false;
		}
		$.ajax({  
			type: "POST",  
			url: "/ticket/add",  
			data: $.param({
				"name" : name,
				"areaCode" : areaCode,
				"telphone" : telphone,
				"email" : email,
				"subject" : subject,
				"description" : description
			}, true),
			dataType: "json",  
			success : function(response) {
				var code = response.code;
					if (code == "200") {
						$.successMsg("发送成功");
						$("input[name='name']").val("");
						$("input[name='email']").val("");
						$("input[name='telphone']").val("");
						$("input[name='title']").val("");
						$("textarea[name='content']").val("");
						
					} else {
						$.errorMsg(response.message);
					}
				}
		});  

	})
})
</script>	