<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<% String picMessage = request.getParameter("picMessage");
   if(picMessage!=null){
	   picMessage = new String(picMessage.getBytes("ISO-8859-1"),"UTF-8");
	}else{
		picMessage = "";
	}
%>
<%@ include file="/include/header.jsp"%>
<!-- BEGIN HEADER & CONTENT DIVIDER -->

<link href="/assets/global/plugins/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/pages/css/profile.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/account-setting.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/jquery-file-upload/css/jquery.fileupload-ui.css" rel="stylesheet" type="text/css"/>
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />

<div class="clearfix"> </div>
<!-- END HEADER & CONTENT DIVIDER -->
<!-- BEGIN CONTAINER -->
<div class="container">
    <%@ include file="/include/leftsider.jsp"%>
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <!-- BEGIN PAGE BASE CONTENT -->
            <div class="row">
                <div class="col-md-12">
                   
                    <!-- BEGIN PROFILE CONTENT -->
                    <div class="profile-content">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="portlet light bordered">
                                    <div class="portlet-title tabbable-line">
                                        <div class="caption caption-md">
                                            <i class="icon-globe theme-font hide"></i>
                                            <span class="caption-subject uppercase">我的资料</span>
                                        </div>
                                        <ul class="nav nav-tabs">
                                            <li class="active">
                                                <a href="#tab_1_1" data-toggle="tab">个人信息</a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_2" data-toggle="tab">修改头像</a>
                                            </li>
                                            <li>
                                                <a href="#tab_1_3" data-toggle="tab">修改密码</a>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="portlet-body">
                                        <div class="tab-content">
                                            <!-- PERSONAL INFO TAB -->
	                                        <div class="tab-pane active" id="tab_1_1">
	                                             <div class="row user" >
	                                            	<div class="user-img hidden-xs">
														<img src="${user_pic_url_session_key}" >
													</div>
													<div class="user-info">
														<div class="row">
															<div class="username">
																${user_username_session_key}
															</div>
															<div class="level">
																<span id="user-grade"> Lv.0 </span>
															</div>
														</div>
														<div class="row">
															<div class="finish-task">
																<span>余额:</span>
																<span id="amount" class="margin-left-10 color-0088ff"> 0 </span>
															</div>
															<div class="finish-task">
																<span>已赚金额:</span>
																<span id="finish-sum-amount" class="color-0088ff"> 0 </span>
															</div>
															<div class="finish-task">
																<span>完成任务数量:</span>
																<span id="finish-count"  class="color-0088ff"> 0 </span>
															</div>
															<div class="finish-task">
																<span>已发布任务数量:</span>
																<span id="publish-count"  class="color-0088ff"> 0 </span>
															</div>
														</div>
														<div class="row">
															<div>
																<ul class="specialty-list">
																</ul>
															</div>
														</div>
														
													</div>
	                                            </div>
	                                            <div class="row line">
	                                            </div>
	                                            <div class="row user-form">
	                                                <form role="form" action="#">
	                                                	<div class="row">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">邮箱：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-6">
	                                                			<input type="text" id="email" placeholder="username@anyonehelps.com" class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4">
	                                                			<div class="verify">
	                                                				<a href="javascript:;" class="btn send-message hide" id="emailverify" data-type="1" data-enable="1">发送验证信息</a>
	                                                				<span id="emailflag" class="hide">已验证</span>
	                                                				<a href="javascript:;" class="emailModify hide"><span style="margin-left: 5px;">修改</span></a>
	                                                			</div>
	                                                		</div>
	                                                        
	                                                       	
	                                                	</div>
	                                                	<div class="row margin-top-30">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">手机号码：</span>
	                                                		</div>
	                                                		<div class="col-md-2 col-sm-2 col-xs-4">
	                                                			<select class="form-control area-code radius0">
																	<option value="+1">(+1)美国/加拿大</option>
											                        <option value="+86">(+86)中国</option>
											                        <option value="+44">(+44)英国</option>
											                        <option value="+61">(+61)澳洲</option>
											                        <!-- <option value="+81">(+81)日本</option> -->
																</select>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4">
	                                                			<input type="text" id="telphone" placeholder="请输入你的手机号码" class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4">
	                                                			<div class="verify">
		                                                			<a href="javascript:;" class="btn send-message hide" id="phoneverify"  data-type="0" data-enable="1">发送验证码</a>
		                                                			<span id="phoneflag" class="hide">已验证</span>
		                                                			<a href="javascript:;" class="phoneModify hide"><span style="margin-left: 5px;">修改</span></a>
																	<input type="text" placeholder="请输入验证码" name="phonecode"	maxlength="6"  class="phonecode hide"/>
																	<a href="javascript:;" class="btn send-message hide" id="phoneverifycode">确定验证</a>
	                                                			</div>
	                                                        </div>
	                                                       	
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-30">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">密保问题：</span>
	                                                		</div>
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<a href="javascript:;" class="btn show-security"> 立即设置 </a>
	                                                			<span class="set-security-question hide ">已设置</span>
	                                                		</div>
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-30">
							                        		<div class="col-md-2 col-sm-2 col-xs-12">
							                             		<span class="left">性别<span class="font-red-flamingo">*</span>：</span>
							                                </div>
							                            	<div class="col-md-9 col-sm-9 col-xs-12">
							                            		<div class="sex-list">
						                                         </div>
							                                </div>
							                           	</div>
	                                                	
	                                                	<div class="row margin-top-30">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">简介：</span>
	                                                		</div>
	                                                		<div class="col-md-10 col-sm-10 col-xs-12">
	                                                			<textarea type="text" id="brief" rows="4" placeholder="请输入你的个人简介" class="form-control radius0"></textarea>
	                                                		</div>
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">职业：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
	                                                			<input type="text" id="occupation" placeholder="请输入您的职业 " class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                	</div>
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">最高学历：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
		                                                		<select name="degree" class="form-control radius0">
																	<option value="0">请选择学历</option>
																	<option value="1">高中毕业或其他同等学历(High School diploma or equivalent)</option>
																	<option value="2">大专学历(College degree)</option>
																	<option value="3">大学本科学历(Undergraduate degree)</option>
																	<option value="4">硕士研究生学历(Master's degree)</option>
																	<option value="5">博士学位(Doctoral or professional degree)</option>
																</select>	
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-12"> </div>
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">所在国家或者地区：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
		                                                		<select name="nationality" class="form-control radius0">
																	<option value="us">美国</option>
																	<option value="cn">中国</option>
																	<option value="au">澳洲</option>
																	<!-- <option value="jp">日本</option> -->
																	<option value="uk">英国</option>
																	<option value="other">其他</option>
																</select>	
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                	</div>
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">省份：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
	                                                			<input type="text" id="province" placeholder="请输入中国省市，或美国等国家的州、特区 " class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                	</div>
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">城市：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
	                                                			<input type="text" id="city" placeholder="请输入你所在的城市 " class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                	</div>
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">邮编/Zip Code：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
	                                                			<input type="text" id="zipCode" maxlength="10" placeholder="请输入你的邮编" class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">微信/WeChat：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
	                                                			<input type="text" id="wechat" placeholder="仅支持6-20个字母、数字、下划线、减号和加号组成" class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                	</div>
	                                                	<div class="row margin-top-30 base-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">实时联系方式 ：</span>
	                                                		</div>
	                                                		<div class="col-md-6 col-sm-6 col-xs-12">
	                                                			<input type="text" id="otherContact" placeholder="请输入你的实际联系方式，如电话，wechat，skype。。。。" class="form-control radius0"/>
	                                                		</div>
	                                                		<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-30">
	                                                		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-6 col-sm-6 col-xs-6">
																<a href="javascript:;" class="show-base-info">
																	<i class="iconfont icon-add"></i>
																	<span>填写基本资料</span>
																</a>
															</div>
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-10">
	                                                		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-6 col-sm-6 col-xs-6">
																<a href="javascript:;" class="show-high-info">
																	<i class="iconfont icon-add"></i>
																	<span>填写高级资料</span>
																</a>
															</div>
	                                                	</div>
	                                                	
	                                                	<!-- 
	                                                	<div class="row margin-top-30">
	                                                    	<div class="col-md-2 col-sm-2 col-xs-2"></div>
	                                                    	<div class="col-md-10 col-sm-10 col-xs-10">
	                                                    		<a href="javascript:;" class="btn update-btn" id="saveInfo"> 更新 </a>
	                                                        	<a href="/dashboard/Account/setting.jsp" class="btn update-btn" > 取消 </a>
	                                                    	</div>
	                                                        
	                                                    </div>
	                                                	 -->
	                                                	<div class="row margin-top-30 high-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left" style="margin-top:10px;">教育经历：</span>
	                                                		</div>
	                                                		<div class="col-md-10 col-sm-10 col-xs-12">
	                                                			<div id="education-list">
		                                                        	
		                                                        </div>
		                                                        <div id="education-add">
		                                                        	
		                                                        </div>
		                                                        <div>
																	<a href="javascript:;" class="btn send-message" id="education-link" style="margin-top:10px;"> 添加教育经历 </a>
																</div>
	                                                		</div>
	                                                	</div>
	                                                	
	                                                    <div class="row margin-top-30 high-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left" style="margin-top:10px;">工作经验：</span>
	                                                		</div>
	                                                		<div class="col-md-10 col-sm-10 col-xs-12">
	                                                			<div id="work-experience-list">
	                                                        	
		                                                        </div>
		                                                        <div id="work-experience-add">
		                                                        	
		                                                        </div>
		                                                        <div>
																	<a href="javascript:;" class="btn send-message" id="work-experience-link" style="margin-top:10px;"> 添加工作经验 </a>
																</div>
	                                                		</div>
	                                                	</div>
	                                                    <div class="row margin-top-30 high-info hide">
	                                                		<div class="col-md-2 col-sm-2 col-xs-12">
	                                                			<span class="left">作品展示：</span>
	                                                		</div>
	                                                		<div class="col-md-10 col-sm-10 col-xs-12">
	                                                			<div id="works">
                            	
                           		 								</div>
	                                                		</div>
	                                                	</div>
	                                                	
	                                                	<div class="row margin-top-30 hide">
	                                                		<div class="col-md-offset-2 col-sm-offset-2 col-xs-offset-2 col-md-6 col-sm-6 col-xs-6">
																<a href="javascript:;" class="hide-high-info">
																	<i class="iconfont icon-minus"></i>
																	<span>收起高级资料</span>
																</a>
															</div>
	                                                	</div>
	                                                	
	                                                	<!--haokun modified 2017/02/21 将更新和取消键下移并将更新改为保存-->
	                                                	<div class="row margin-top-30">
	                                                    	<div class="col-md-2 col-sm-2 col-xs-2"></div>
	                                                    	<div class="col-md-10 col-sm-10 col-xs-10">
	                                                    		<a href="javascript:;" class="btn update-btn" id="saveInfo"> 保存 </a>
	                                                        	<a href="/dashboard/Account/setting.jsp" class="btn update-btn" > 取消 </a>
	                                                    	</div>
	                                                        
	                                                    </div>
	                                                    
	                                                </form>
                                                </div>
                                            </div>
                                            <!-- END PERSONAL INFO TAB -->
                                            <!-- CHANGE AVATAR TAB -->
                                            <div class="tab-pane" id="tab_1_2">
                                            	<div class="row user-form">
	                                                <form action="/user/pic_upload" id="user-pic-upload" role="form" method="POST" enctype="multipart/form-data">
														<div class="form-group">
															<span><%=picMessage%></span>
														</div>  
														<div class="form-group">
															<span>系统头像</span>
														</div>                                                  
	                                                    <div class="form-group">
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team1.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team1.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team2.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team2.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team3.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team3.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team4.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team4.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team5.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team5.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team6.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team6.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team7.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team7.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team8.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team8.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team9.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team9.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team10.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team10.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team11.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team11.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team12.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team12.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team13.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team13.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team14.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team14.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team15.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team15.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team16.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team16.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team17.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team17.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team18.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team18.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team19.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team19.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team20.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team20.jpg" alt="" />
															
															<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team21.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team21.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team22.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team22.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team23.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team23.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team24.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team24.jpg" alt="" />
	                                                    	
															<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team25.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team25.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team26.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team26.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team27.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team27.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team28.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team28.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team29.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team29.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team30.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team30.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team31.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team31.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team32.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team32.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team33.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team33.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team34.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team34.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team35.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team35.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team36.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team36.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team37.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team37.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team38.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team38.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team39.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team39.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team40.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team40.jpg" alt="" />
															<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team41.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team41.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team42.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team42.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team43.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team43.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team44.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team44.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team45.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team45.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team46.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team46.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team47.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team47.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team48.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team48.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team49.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team49.jpg" alt="" />
	                                                    	<img style="height: 55px;margin-top: 12px;cursor:pointer;" src="/assets/pages/img/avatars/team50.jpg" class="system-pic" data-src="/assets/pages/img/avatars/team50.jpg" alt="" />

	                                                    </div>
	                                                    <div class="form-group">
	                                                        <div class="fileinput fileinput-new" data-provides="fileinput">
	                                                            <div class="fileinput-new thumbnail" style="width: 200px; height: 150px;">
	                                                                <img src="/assets/pages/img/avatars/no-image.png" alt="" />
	                                                            </div>
	                                                            <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;"> </div>
	                                                            <div>
	                                                                <span class="btn update-password-btn btn-file" style="width:200px;"> 
	                                                                    <span class="fileinput-new"> 选择本地头像 </span>
	                                                                    <span class="fileinput-exists"> 修改 </span>
	                                                                    <input type="file" name="picFile">
	                                                                </span>
	                                                                <a href="javascript:;" class="btn update-password-btn fileinput-exists" data-dismiss="fileinput"> 移除 </a>
	                                                            </div>
															</div>
	                                                    </div>
	                                                    <div class="margin-top-10">
	                                                    	<button type="submit" class="btn update-password-btn">提交</button>
	                                                        <a href="javascript:;" class="btn update-password-btn"> 取消 </a>
	                                                    </div>
	                                                </form>
	                                           	</div>
                                            </div>
                                            <!-- END CHANGE AVATAR TAB -->
                                            <!-- CHANGE PASSWORD TAB -->
                                            <div class="tab-pane modify-pass" id="tab_1_3">
                                            	<div class="row user-form">
	                                                <form id="modifyPass" action="#">
	                                                	<div class="row margin-top-30">
		                                                	<div class="col-md-3 col-sm-3 col-xs-4">
		                                                		<span class="left">当前密码：</span>
		                                                	</div>
		                                                	<div class="col-md-4 col-sm-4 col-xs-8">
		                                                		<input type="password" id="old_pass" placeholder="请输入当前密码 " class="form-control radius0"/>
		                                                	</div>
		                                                </div>
		                                                <div class="row margin-top-30">
		                                                	<div class="col-md-3 col-sm-3 col-xs-4">
		                                                		<span class="left">新密码：</span>
		                                                	</div>
		                                                	<div class="col-md-4 col-sm-4 col-xs-8">
		                                                		<input type="password" id="new_pass_1" placeholder="请输入新密码" class="form-control radius0"/>
		                                                	</div>
		                                                </div>
		                                                <div class="row margin-top-30">
		                                                	<div class="col-md-3 col-sm-3 col-xs-4">
		                                                		<span class="left">重复新密码：</span>
		                                                	</div>
		                                                	<div class="col-md-4 col-sm-4 col-xs-8">
		                                                		<input type="password" id="new_pass_2" placeholder="请再次输入新密码" class="form-control radius0"/>
		                                                	</div>
		                                                </div>
		                                                <div class="row margin-top-30">
		                                                	<div class="col-md-3 col-sm-3 col-xs-4">
		                                                	</div>
		                                                	<div class="col-md-4 col-sm-4 col-xs-8">
		                                                		<button type="submit" class="btn update-password-btn">提交</button>
	                                            				<button type="reset" class="btn update-password-btn">重置</button>
		                                                	</div>
		                                                </div>
	                                                </form>
	                                          	</div>
                                            </div>
                                            <!-- END CHANGE PASSWORD TAB -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- END PROFILE CONTENT -->
                </div>
            </div>


            <!-- END PAGE BASE CONTENT -->

        </div>
        <!-- END CONTENT BODY -->
    </div>
    <!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<script id="question-tmpl" type="html/x-jsrender">  

	<option value="" {{setOptionSelected "" value/}}>请选择您的密保问题</option>
	{{if index1 !== "1" && index2 !=="1"}}
	<option value="1" {{setOptionSelected "1" value/}}>您母亲的姓名是？</option>
	{{/if}}
	{{if index1 !== "2" && index2 !=="2"}}
	<option value="2" {{setOptionSelected "2" value/}}>您父亲的姓名是？</option>
	{{/if}}
	{{if index1 !== "3" && index2 !=="3"}}
	<option value="3" {{setOptionSelected "3" value/}}>您配偶的姓名是？</option>
	{{/if}}
	{{if index1 !== "4" && index2 !=="4"}}
	<option value="4" {{setOptionSelected "4" value/}}>您的出生地是？</option>
	{{/if}}
	{{if index1 !== "5" && index2 !=="5"}}
	<option value="5" {{setOptionSelected "5" value/}}>您高中班主任的名字是？</option>
	{{/if}}
	{{if index1 !== "6" && index2 !=="6"}}
	<option value="6" {{setOptionSelected "6" value/}}>您初中班主任的名字是？</option>
	{{/if}}
	{{if index1 !== "7" && index2 !=="7"}}
	<option value="7" {{setOptionSelected "7" value/}}>您小学班主任的名字是？</option>
	{{/if}}
	{{if index1 !== "8" && index2 !=="8"}}
	<option value="8" {{setOptionSelected "8" value/}}>您的小学校名是？</option>
	{{/if}}
	{{if index1 !== "9" && index2 !=="9"}}
	<option value="9" {{setOptionSelected "9" value/}}>您父亲的生日是？</option>
	{{/if}}
	{{if index1 !== "10" && index2 !=="10"}}
	<option value="10" {{setOptionSelected "10" value/}}>您母亲的生日是？</option>
	{{/if}}
	{{if index1 !== "11" && index2 !=="11"}}
	<option value="11" {{setOptionSelected "11" value/}}>您配偶的生日是？</option>
	{{/if}}
	{{if index1 !== "12" && index2 !=="12"}}
	<option value="12" {{setOptionSelected "12" value/}}>您的学号（或工号）是？</option>
	{{/if}}
</script> 

<script id="modify-email-tmpl" type="html/x-jsrender">
<div class="modify-email row">
	<div class="row">
		<div class="left">
			<span>新邮箱:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入新邮箱" maxlength="100"  class="email form-control"/>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>密保问题一:</span>
		</div>
		<div class="right">
			<span>{{questionFormat asq.question1/}}</span>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>答案:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>密保问题二:</span>
		</div>
		<div class="right">
			<span>{{questionFormat asq.question2/}}</span>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>答案:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>

	<div class="row question">
		<div class="left">
			<span>密保问题三:</span>
		</div>
		<div class="right">
			<span>{{questionFormat asq.question3/}}</span>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>答案:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
	
</div>
</script> 


<script id="modify-phone-tmpl" type="html/x-jsrender">
<div class="modify-phone row">
	<div class="row">
		<div class="left">
			<span>新手机:</span>
		</div>
		<div class="right">
			<select class="form-control area-code radius0">
				<option value="+1">+1</option>
				<option value="+86">+86</option>
				<option value="+44">+44</option>
				<option value="+61">+61</option>
				<option value="+81">+81</option>
			</select>
			<input type="text" placeholder="请输入新手机号码" maxlength="20"  class="phone form-control"/>
			<a href="javascript:;" class="btn send-message" data-type="0" data-enable="1">发送验证码</a>
		</div>
	</div>
	<div class="row" style="margin-top:15px;">
		<div class="left">
			<span>手机验证码:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入手机验证码" maxlength="6"  class="phonecode form-control"/>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>密保问题一:</span>
		</div>
		<div class="right">
			<span>{{questionFormat asq.question1/}}</span>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>一、回答:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>密保问题二:</span>
		</div>
		<div class="right">
			<span>{{questionFormat asq.question2/}}</span>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>二、回答:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>密保问题三:</span>
		</div>
		<div class="right">
			<span>{{questionFormat asq.question3/}}</span>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>三、回答:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
</div>
</script> 

<script id="security-question-tmpl" type="html/x-jsrender">
<div class="security-question row">
	<div class="row">
		<div class="left">
			<span>手机验证码:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入验证码" maxlength="6"  class="phonecode form-control"/>
			<a href="javascript:;" class="btn send-message" data-type="0" data-enable="1">发送验证码</a>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>一、问题:</span>
		</div>
		<div class="right">
			<select class="form-control radius0">
				{{:question}}
			</select>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>一、回答:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>二、问题:</span>
		</div>
		<div class="right">
			<select class="form-control radius0">
				{{:question}}
			</select>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>二、回答:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>三、问题:</span>
		</div>
		<div class="right">
			<select class="form-control radius0">
				{{:question}}
			</select>
		</div>
	</div>
	<div class="row question">
		<div class="left">
			<span>三、回答:</span>
		</div>
		<div class="right">
			<input type="text" placeholder="请输入回答" maxlength="128"  class="answer form-control"/>
		</div>
	</div>
</div>
</script> 

<script id="works-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="work-item">
	<div class="file-extension row">  
		<a target = "_blank" href="{{:saveFileName}}"><img src="{{fileFormat saveFileName/}}"></a>  <!--haokun modified 2017/02/21 增加点击头像跳转-->
	</div>
	<div class="delete">
		<a class="glyphicon glyphicon-remove" data-id="{{:worksId}}" name="delete-works"></a>
	</div>
	<div class="line"></div>
	<div class="title row">
		<a href="javascript:;" class="glyphicon glyphicon-pencil works-enclosure-edit" data-id="{{:worksId}}"></a>
		<a target = "_blank" href="{{:saveFileName}}" class="title1"><span>{{:fileName}}</span></a> 
		<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
	</div>
</div>
{{/for}}
</script>                                	
<script id="works-items-tmpl" type="html/x-jsrender">
<div id="works-list"  class="row">
	{{for dataList}}
	<!-- <div class="work-item row">
		<div class="col-md-7 col-sm-7 col-xs-7">
			<a href="javascript:;" class="glyphicon glyphicon-pencil works-enclosure-edit" data-id="{{:id}}"></a>
			<a target = "_blank" href="{{:url}}" class="title">{{:title}}</a>
			<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
		</div>
		<div class="col-md-3 col-sm-3 col-xs-3">
			<a class="glyphicon glyphicon-remove delete" data-id="{{:id}}" name="delete-works"></a>
		</div>
	</div> -->
	
	<div class="work-item">
		<div class="file-extension row">  
			{{if ~isPdf(urlPdf)}}
			<img src="{{fileFormat url/}}">
			{{else}}
			<img src="{{fileFormat url/}}">
			{{/if}}
		</div>
		<div class="delete">
			<a class="glyphicon glyphicon-remove" data-id="{{:id}}" name="delete-works"></a>
		</div>
		<div class="line"></div>
		<div class="title row">
			<a href="javascript:;" class="glyphicon glyphicon-pencil works-enclosure-edit" data-id="{{:id}}"></a>
			<a target = "_blank" href="{{:url}}" class="title1"><span>{{:title}}</span></a> 
			<input type="text" class="form-control hide" style="float: left;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 30px);">
		</div>
	</div>

	{{/for}}
</div>

<div {{displayUploadBtn dataList/}} id="works-add" class="row">
	<div class="row">
		<div class="row">
			<span>
				上传你的得意作品，展示你的实战能力
			</span>
		</div>
		<span  id="work-format">
			最大能上传9个附件，支持附件格式：gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar
		</span>
		
	</div>
	<span class="btn send-message fileinput-button add-file-btn">
		<i class="fa fa-plus"></i>
		<span> 上传附件... </span>
		<input type="file" name="files[]" data-url="/works/add">
	</span>
	
</div>
<div class="margin-top-20 hide" id="works-progress">
	<div id="progress" class="progress">
		<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
	</div> 
</div>

</script>
                                	
<script id="education-items-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="education-item">
	
	<div class="btnOperation">
		<a class="glyphicon glyphicon-pencil color-0088ff edit" href="javascript:void(0);" data-id="{{:id}}"></a>
		<a class="glyphicon glyphicon-remove color-0088ff delete" href="javascript:void(0);" data-id="{{:id}}"></a>
	</div>
	<p>
		<span class="start-date">{{:startDate}}</span>
		<span>~</span>
		<span class="end-date" data-data="{{:endDate}}">
		{{if endDate==""}}
			至今
		{{else}}
			{{:endDate}}
		{{/if}}
		</span>
		<span style="margin-left: 20px;" class="school">{{:school}}</span>
		<span style="margin-left: 20px;" class="major">{{:major}}</span>
		<span style="margin-left: 20px;" class="education" data-education="{{:education}}">{{education education/}}</span>
	</p>
	<p><pre  class="major-desc">{{:majorDesc}}</pre></p>
	{{if enclosure!=""&& enclosure!=null}}
	<p>附件:
		<a href="javascript:;" class="glyphicon glyphicon-pencil education-enclosure-edit" data-id="{{:id}}"></a>
		<a target="_blank" href="{{:enclosure}}" class="color-0088ff enclosure">{{:enclosureName}}</a>
		<input type="text" class="form-control hide" style="float: right;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 72px);">
	</p>
	
	{{/if}}
	
</div>
<hr/>
{{/for}}
</script>

<script id="education-items" type="html/x-jsrender">
<div class="education-item">
	<div class="btnOperation">
		<a class="glyphicon glyphicon-pencil color-0088ff edit" href="javascript:void(0);" data-id="{{:id}}"></a>
		<a class="glyphicon glyphicon-remove color-0088ff delete" href="javascript:void(0);" data-id="{{:id}}"></a>
	</div>
	<p>
		<span class="start-date">{{:startDate}}</span>
		<span>~</span>
		<span class="end-date" data-data="{{:endDate}}">
		{{if endDate==""}}
			至今
		{{else}}
			{{:endDate}}
		{{/if}}
		</span>
		<span style="margin-left: 20px;" class="school">{{:school}}</span>
		<span style="margin-left: 20px;" class="major">{{:major}}</span>
		<span style="margin-left: 20px;" class="education" data-education="{{:education}}">{{education education/}}</span>
	</p>
	<p><pre  class="major-desc">{{:majorDesc}}</pre></p>
	{{if enclosure!=""&& enclosure!=null}}
	<p>附件:
		<a href="javascript:;" class="glyphicon glyphicon-pencil education-enclosure-edit" data-id="{{:id}}"></a>
		<a target="_blank" href="{{:enclosure}}" class="color-0088ff enclosure">{{:enclosureName}}</a>
		<input type="text" class="form-control hide" style="float: right;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 72px);">
	</p>
	{{/if}}

</div>
<hr/>
</script>

<script id="education-edit-item" type="html/x-jsrender">
<div data-id={{:id}}>
	<div class="row" >
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">开始时间</th>
					<th width="100">结束时间（不填即至今）</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="startDate" placeholder="格式：yyyy-mm" value="{{:startDate}}">
					</td>
					<td>
						<input type="text" class="form-control" name="endDate" placeholder="格式：yyyy-mm" value="{{:endDate}}">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">学校</th>
					<th width="100">专业</th>
					<th width="100">学历</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="school" value="{{:school}}">
					</td>
					<td>
						<input type="text" class="form-control" name="major" value="{{:major}}">
					</td>
					<td>
						<select class="form-control" name="education">
							<option value="1" {{setOptionSelected "1" education/}}>高中毕业或其他同等学历</option>
							<option value="2" {{setOptionSelected "2" education/}}>大专学历</option>
							<option value="3" {{setOptionSelected "3" education/}}>大学本科学历</option>
							<option value="4" {{setOptionSelected "4" education/}}>硕士研究生学历</option>
							<option value="5" {{setOptionSelected "5" education/}}>博士学位</option>
						</select>	
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
	 	<label class="control-label">专业描述</label>
	    	<textarea class="form-control" rows="3" name="majorDesc" value="{{:majorDesc}}">{{:majorDesc}}</textarea>
	</div>
	<div class="row" style="margin-top: 10px;">
	    <span class="btn send-message fileinput-button add-file-btn" {{if enclosure}}style="display:none"{{/if}}>
			<i class="fa fa-plus"></i>
			<span> 上传附件  </span>
			<input type="file" name="education_upload" data-url="/education/upload">
		</span>
		<span class="font-red-flamingo" style="font-size: 10px; {{if enclosure}} display:none;{{/if}}">附件格式要求是gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar</span>
		<span name="education-file-name" data-link="{{:enclosure}}" style="float: left;">{{:enclosureName}}</span>
		<a class="send-message glyphicon glyphicon-remove enclosure-remove" style="{{if enclosure}}display:block{{else}}display:none{{/if}};float: left;font-size: 10px;color: #ffffff;background-color: #999999;height: 20px;width: 20px;border-radius: 50%;padding-left: 4px;line-height: 20px;margin-top: 6px;margin-left:30px;" href="javascript:;"></a>
 
		<div class="progress" {{if enclosure}}style="display:none;"{{/if}}>
			<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
		</div> 
		
	</div>
	<div class="row" style="width: auto;display: table;margin-left: auto;margin-right: auto;margin-bottom: 10px;">
		<a href="javascript:;" class="btn send-message" name="edit"> 保存 </a>
	    <a href="javascript:;" class="btn send-message" name="cancel" style="margin-left: 10px;"> 取消 </a>
	</div>
</div>
</script>

<script id="education-add-item" type="html/x-jsrender">
<div>
	<div class="row" >
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">开始时间</th>
					<th width="100">结束时间（不填即至今）</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="startDate" placeholder="格式：yyyy-mm">
					</td>
					<td>
						<input type="text" class="form-control" name="endDate" placeholder="格式：yyyy-mm">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">学校</th>
					<th width="100">专业</th>
					<th width="100">学历</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="school">
					</td>
					<td>
						<input type="text" class="form-control" name="major">
					</td>
					<td>
						<select class="form-control" name="education">
							<option value="1">高中毕业或其他同等学历</option>
							<option value="2">大专学历</option>
							<option value="3">大学本科学历</option>
							<option value="4">硕士研究生学历</option>
							<option value="5">博士学位</option>
						</select>	
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
	 	<label class="control-label">专业描述</label>
	    	<textarea class="form-control" rows="3" name="majorDesc"></textarea>
	</div>
	<div class="row" style="margin-top: 10px;">
	    <span class="btn send-message fileinput-button add-file-btn">
			<i class="fa fa-plus"></i>
			<span> 上传附件  </span>
			<input type="file" name="education_upload" data-url="/education/upload">
		</span>
		<span class="font-red-flamingo" style="font-size: 10px;">附件格式要求是gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar</span>
		<span name="education-file-name"></span>
		<a class="send-message glyphicon glyphicon-remove" style="display:none;font-size: 10px;color: #ffffff;background-color: #999999;height: 20px;width: 20px;border-radius: 50%;padding-left: 4px;line-height: 20px;margin-top: 6px;margin-left:30px;" href="javascript:;"></a>

		<div class="progress">
			<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
		</div> 
		
	</div>
	<div class="row" style="width: auto;display: table;margin-left: auto;margin-right: auto;margin-bottom: 10px;">
		<a href="javascript:;" class="btn send-message" name="saveEducation"> 保存 </a>
	    <a href="javascript:;" class="btn send-message" name="cancelEducation" style="margin-left: 10px;"> 取消 </a>
	</div>
</div>
</script>


<!-- work experience -->
<script id="work-experience-items-tmpl" type="html/x-jsrender">
{{for dataList}}
<div class="work-experience-item" >
	<div class="btnOperation">
		<a class="glyphicon glyphicon-pencil color-0088ff edit" href="javascript:void(0);" data-id="{{:id}}"></a>
		<a class="glyphicon glyphicon-remove color-0088ff delete" href="javascript:void(0);" data-id="{{:id}}"></a>
	</div>
	<p>
		<span class="start-date">{{:startDate}}</span>
		<span>~</span>
		<span class="end-date" data-data="{{:endDate}}">
		{{if endDate==""}}
			至今
		{{else}}
			{{:endDate}}
		{{/if}}
		</span>
		<span style="margin-left: 20px;" class="company">{{:company}}</span>
		<span style="margin-left: 20px;" class="job-title">{{:jobTitle}}</span>
	</p>
	<p><pre class="job-desc">{{:jobDesc}}</pre></p>
	{{if enclosure!=""&& enclosure!=null}}
	<p>附件:
		<a href="javascript:;" class="glyphicon glyphicon-pencil work-experience-enclosure-edit" data-id="{{:id}}"></a>
		<a target="_blank" href="{{:enclosure}}" class="color-0088ff enclosure">{{:enclosureName}}</a>
		<input type="text" class="form-control hide" style="float: right;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 72px);">
	</p>
	{{/if}}

</div>
<hr/>
{{/for}}
</script>

<script id="work-experience-items" type="html/x-jsrender">
<div class="work-experience-item" >
	<div class="btnOperation">
		<a class="glyphicon glyphicon-pencil color-0088ff edit" href="javascript:void(0);" data-id="{{:id}}"></a>
		<a class="glyphicon glyphicon-remove color-0088ff delete" href="javascript:void(0);" data-id="{{:id}}">
		</a>
	</div>
	<p>
		<span class="start-date">{{:startDate}}</span>
		<span>~</span>
		<span class="end-date" data-data="{{:endDate}}">
		{{if endDate==""}}
			至今
		{{else}}
			{{:endDate}}
		{{/if}}
		</span>
		<span style="margin-left: 20px;" class="company">{{:company}}</span>
		<span style="margin-left: 20px;" class="job-title">{{:jobTitle}}</span>
	</p>
	<p><pre class="job-desc">{{:jobDesc}}</pre></p>
	{{if enclosure!=""&& enclosure!=null}} 
	<p>附件:
		<a href="javascript:;" class="glyphicon glyphicon-pencil work-experience-enclosure-edit" data-id="{{:id}}"></a>
		<a target="_blank" href="{{:enclosure}}" class="color-0088ff enclosure">{{:enclosureName}}</a>
		<input type="text" class="form-control hide" style="float: right;line-height: 26px;border-radius: 0px;color: #676767;width: calc(100% - 72px);">
	</p>
	{{/if}}
	
</div>
<hr/>
</script>

<script id="work-experience-edit-item" type="html/x-jsrender">
<div data-id={{:id}}>
	<div class="row" >
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">开始时间</th>
					<th width="100">结束时间（不填即至今）</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="startDate" placeholder="格式：yyyy-mm" value="{{:startDate}}">
					</td>
					<td>
						<input type="text" class="form-control" name="endDate" placeholder="格式：yyyy-mm" value="{{:endDate}}">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">公司</th>
					<th width="100">职位名称</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="company" value="{{:company}}">
					</td>
					<td>
						<input type="text" class="form-control" name="jobTitle" value="{{:jobTitle}}">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
	 	<label class="control-label">工作描述</label>
	    	<textarea class="form-control" rows="3" name="jobDesc">{{:jobDesc}}</textarea>
	</div>

	<div class="row" style="margin-top: 10px;">
	    <span class="btn send-message fileinput-button add-file-btn" {{if enclosure}}style="display:none"{{/if}}>
			<i class="fa fa-plus"></i>
			<span> 上传附件  </span>
			<input type="file" name="work_experience_upload" data-url="/work_experience/upload">
		</span>
		<span class="font-red-flamingo" style="font-size: 10px;{{if enclosure}} display:none;{{/if}}">附件格式要求是gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar</span>
		<span name="work-experience-file-name" data-link="{{:enclosure}}" style="float: left;">{{:enclosureName}}</span>
		<a class="send-message glyphicon glyphicon-remove we-enclosure-remove" style="{{if enclosure}}display:block{{else}}display:none{{/if}};float:left;font-size: 10px;color: #ffffff;background-color: #999999;height: 20px;width: 20px;border-radius: 50%;padding-left: 4px;line-height: 20px;margin-top: 6px;margin-left:30px;" href="javascript:;"></a>
		<div class="progress" {{if enclosure}}style="display:none;"{{/if}}>
			<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
		</div> 
		
	</div>

	<div class="row" style="width: auto;display: table;margin-left: auto;margin-top: 10px;margin-right: auto;margin-bottom: 10px;">
		<a href="javascript:;" class="btn send-message" name="editWorkExperience"> 保存 </a>
	    <a href="javascript:;" class="btn send-message" name="cancelWorkExperience" style="margin-left:10px;"> 取消 </a>
	</div>
</div>
</script>

<script id="work-experience-add-item" type="html/x-jsrender">
<div>
	<div class="row" >
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">开始时间</th>
					<th width="100">结束时间（不填即至今）</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="startDate" placeholder="格式：yyyy-mm">
					</td>
					<td>
						<input type="text" class="form-control" name="endDate" placeholder="格式：yyyy-mm">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="100">公司</th>
					<th width="100">职位名称</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<input type="text" class="form-control" name="company">
					</td>
					<td>
						<input type="text" class="form-control" name="jobTitle">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="row">
	 	<label class="control-label">工作描述</label>
	    	<textarea class="form-control" rows="3" name="jobDesc"></textarea>
	</div>

	<div class="row" style="margin-top: 10px;">
	    <span class="btn send-message fileinput-button add-file-btn">
			<i class="fa fa-plus"></i>
			<span> 上传附件  </span>
			<input type="file" name="work_experience_upload" data-url="/work_experience/upload">
		</span>
		<span class="font-red-flamingo" style="font-size: 10px;">附件格式要求是gif、jpeg、png、bmp、doc、docx、pdf、ppt、pptx、xls、xlsx、zip、rar</span>
		<span name="work-experience-file-name"></span>
		<a class="send-message glyphicon glyphicon-remove" style="display:none;font-size: 10px;color: #ffffff;background-color: #999999;height: 20px;width: 20px;border-radius: 50%;padding-left: 4px;line-height: 20px;margin-top: 6px;margin-left:30px;" href="javascript:;"></a>
		<div class="progress">
			<div class="bar" style="width: 0%;height: 18px;background: green;"></div>
		</div> 
		
	</div>

	<div class="row" style="width: auto;display: table;margin-left: auto;margin-top: 10px;margin-right: auto;margin-bottom: 10px;">
		<a href="javascript:;" class="btn send-message" name="saveWorkExperience"> 保存 </a>
	    <a href="javascript:;" class="btn send-message" name="cancelWorkExperience" style="margin-left:10px;"> 取消 </a>
	</div>
</div>
</script>


<script id="tip-dlg" type="html/x-jsrender">
<div class="tip-dlg-content">
	<div class="row title">
		<span>系统提示</span>
	</div>
	<div class="row content">
		<i class="iconfont icon-info-warning"></i>
		<span>温馨提示，重设密码成功！请使用新密码重新登录。</span>
	</div>
	<div class="row operate">
		<button type="button" class="btn">我知道了</button>
	</div>
</div>
</script>

<%@ include file="/include/footer.jsp"%>



<script src="/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-notific8/jquery.notific8.min.js" type="text/javascript"></script>


<!-- END CORE PLUGINS -->  
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->
<!-- BEGIN THEME LAYOUT SCRIPTS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/ui-notific8.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/vendor/jquery.ui.widget.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-process.js " type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-file-upload/js/jquery.fileupload-validate.js" type="text/javascript"></script>
<script src="/assets/pages/scripts/account-setting.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- END THEME LAYOUT SCRIPTS -->
