<%@ include file="/include/header.jsp"%>

<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="/assets/global/css/components-rounded.css" rel="stylesheet" id="style_components" type="text/css" />    
<link href="/assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.css" rel="stylesheet" type="text/css" />
<link href="/assets/pages/css/finance-recharge.css" rel="stylesheet" type="text/css" />
<link href="/assets/global/plugins/bootstrap-toastr/toastr.min.css" rel="stylesheet" type="text/css" />
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN HEADER & CONTENT DIVIDER -->
<div class="clearfix"></div>
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
					<div class="portlet light bordered">
						<div class="portlet-title">
							<div class="caption font-dark">
								<span class="caption-subject uppercase">余额充值</span>
							</div>
						</div>
						<div class="portlet-body">
							<div class="row">
								<div class="col-md-3 col-sm-3 col-xs-3">
									<div class="balance">
										<div class="row balance-desc">
											<span>可用余额</span>
										</div>	
										<div class="row balance-usd">
											<span>$ 0.00</span>
										</div>	
									</div>
	                         	</div>
	                         	<div class="col-md-3 col-sm-3 col-xs-3">
									<div class="balance middle">
										<div class="row balance-desc">
											<span>冻结金额</span>
										</div>	
										<div class="row balance-freeze">
											<span>$ 0.00</span>
										</div>	
									</div>
	                         	</div>
	                         	<div class="col-md-3 col-sm-3 col-xs-3">
									<div class="balance middle">
										<div class="row balance-desc">
											<span>待提现金额</span>
										</div>	
										<div class="row balance-withdrawal">
											<span>$ 0.00</span>
										</div>	
									</div>
	                         	</div>
	                         	<div class="col-md-3 col-sm-3 col-xs-3"> 
									<div class="balance middle">
										<div class="row balance-desc">
											<span>总金额</span>
										</div>	
										<div class="row balance-total">
											<span>$ 0.00</span>
										</div>	
									</div>
	                         	</div>
							</div>
						
						
							<!-- <div class="row">
								<div class="col-md-2 col-sm-2 col-xs-2">
									<span>可用余额</span>
	                         	</div>
	                         	<div class="col-md-2 col-sm-2 col-xs-2"> 
	                         		<span id="usd" class="pull-right" style="color: #4ee267;">0.00</span> 
	                         	</div>
							</div>
							<div class="row">
								<div class="col-md-2 col-sm-2 col-xs-2">
									<span>冻结金额</span>
	                         	</div>
	                         	<div class="col-md-2 col-sm-2 col-xs-2"> 
	                         		<span id="freeze" class="pull-right" style="color: #4ee267;">0.00</span>
	                         	</div>
							</div>
							<div class="row">
								<div class="col-md-2 col-sm-2 col-xs-2">
									<span>待提现金额</span>
	                         	</div>
	                         	<div class="col-md-2 col-sm-2 col-xs-2"> 
	                         		<span id="withdrawal" class="pull-right" style="color: #4ee267;">0.00</span>
	                         	</div>
							</div>
							<div class="row">
								<div class="col-md-2 col-sm-2 col-xs-2">
									<span style="color: blue;">总金额</span>
	                         	</div>
	                         	<div class="col-md-2 col-sm-2 col-xs-2"> 
	                         		<span id="total" class="pull-right" style="color: #4ee267;">0.00</span>
	                         	</div>
							</div>
							 -->
							<div class="row">
								<ul class="nav nav-tabs">
									<!-- <li class="active"><a href="#tab_1_1" data-toggle="tab">支付宝在线充值 </a></li> -->
									<li class="active"><a href="#tab_1_2" data-toggle="tab"> Paypal充值 </a></li>
									<li><a href="#tab_1_3" data-toggle="tab"> 信用卡充值 </a></li>
									<!-- <li><a href="#tab_1_4" data-toggle="tab"> 微信充值 </a></li> -->
									<!-- <li><a href="#tab_1_5" data-toggle="tab"> 信用卡充值 </a></li> --> 
								</ul>
								<div class="tab-content">
									<!-- <div class="tab-pane fade active in" id="tab_1_1">
										<div class="form-horizontal">
											<div class="form-body">
												<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                	<span class="left">人民币：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
														<input name="amount_rmb" type="number"  placeholder="请输入您的充值金额，格式：356.32 " class="form-control radius0"
															maxlength="10" min="30" step="0.1"  /> 
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                	<span class="left">人民币：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
														<a href="#" name="a_alipay_recharge" class="btn btn-0088ff">确认充值</a> 
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                                
											</div>
										</div>
									</div> -->
									<div class="tab-pane fade  active in" id="tab_1_2">
										<form method="post" class="paypal-form" action="https://www.paypal.com/cgi-bin/webscr" target="_blank">

											<input type="hidden" name="upload" value="1" /> 
											<input type="hidden" name="return" value="www.anyonehelps.com/dashboard/Finance/records.jsp" /> 
											<input type="hidden" name="notify_url" value="www.anyonehelps.com/account/paypal_notify_url"/>
											<input type="hidden" name="cmd" value="_cart" /> 
											<input type="hidden" name="business" value="teanjuly@gmail.com" />
											<input type="hidden" name="image_url" value="https://www.anyonehelps.com/assets/pages/img/index/logo-bottom.png"/>   <!--anyone helps logo add by haokun-->
											<!-- Product 1  -->
											<input type="hidden" name="custom" value=""/>
											<input type="hidden" name="item_name_1" value="www.anyonehelps.com recharge" />
											<input type="hidden" name="item_number_1" value="p1" />
											<input type="hidden" name="quantity_1" value="1" />
										
											<div class="form-horizontal">
												<div class="form-body">
													<div class="row margin-top-30"> 
	                                                	<div class="col-md-2 col-sm-2 col-xs-4">
	                                                		<span class="left">充值金额(<em>美元</em>)<span class="font-red">*</span>：</span>
	                                                	</div>
	                                                	<div class="col-md-3 col-sm-3 col-xs-8">
	                                                		<input name="tempAmount" type="number" placeholder="充值金额，格式：356.32 " class="form-control radius0"
																maxlength="10" min="30" step="0.01" /> 
	                                                	</div>
	                                                	<div class="col-md-3 col-sm-3 col-xs-6  col-md-offset-0 col-sm-offset-0  col-xs-offset-4">
	                                                		<span class="desc">(支付$ <span class="poundage">0.00</span><a href="javascript:;" data-toggle="tooltip" title="扣取paypal手续费 2.9%+30美分"> 手续费？</a>)</span>
	                                                	</div> 
	                                                	<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                </div>
	                                                
	                                                <div class="row margin-top-20"> 
	                                                	<div class="col-md-2 col-sm-2 col-xs-4">
	                                                		<span class="left">实际付款总额<span class="font-red">*</span>：</span>
	                                                	</div>
	                                                	<div class="col-md-3 col-sm-3 col-xs-8">
	                                                		<span class="totalAmount" style="color: #ff9a29;font-size: 18px;">$ 0.00</span>
	                                                		<input name="amount_1" type="hidden" class="form-control radius0"
																maxlength="10" min="30" step="0.01" /> 
	                                                	</div>
	                                                	<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                </div>
	                                                
	                                                <div class="row margin-top-30">
	                                                	<div class="col-md-2 col-sm-2 col-xs-4">
	                                                	</div>
	                                                	<div class="col-md-6 col-sm-6 col-xs-8">
	                                                		<input name="Paypal" type="button" class="btn btn-0088ff" value="确认充值" />
	                                                	</div>
	                                                	<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                                </div>
													
													<div class="form-group">
														<label class="col-md-3 control-label"></label>
														<div class="col-md-3">
															
														</div>
													</div>
												</div>
											</div>

										</form>
									</div>
									<div class="tab-pane fade" id="tab_1_3">
										<div class="form-horizontal">
											<div class="form-body">
												<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-4">
	                                                	<span class="left">信用卡类型<span class="font-red">*</span>：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-8">
														<select name="brand" class="form-control radius0">
															<option value="Visa">Visa</option>
															<option value="American Express">American Express</option>
															<option value="Visa">MasterCard</option>
															<option value="American Express">Discover</option>
															<option value="Visa">JCB</option>
															<option value="American Express">Diners Club</option>
															<option value="Visa">Unknown</option>
														</select>
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-4">
	                                                	<span class="left">姓名<span class="font-red">*</span>：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-8">
														<input name="name" class="form-control radius0" type="text"
															placeholder="请输入信用卡持有人姓名" />
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-4">
	                                                	<span class="left">信用卡号码<span class="font-red">*</span>：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-8">
	                                                	<input name="creditNo" type="text" class="form-control radius0"
															placeholder="请输入信用卡号码" />
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-4">
	                                                	<span class="left">信用卡安全码<span class="font-red">*</span>：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-8">
	                                                	<input name="creditSecurityCode" type="text" maxlength="3"
															placeholder="请输入信用卡3位安全码" class="form-control radius0" />
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-4">
	                                                	<span class="left">失效月/年<span class="font-red">*</span>：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-8">
	                                                	<div class="input-group">
	                                                		<select name="expireMonth" class="form-control input-xsmall input-inline radius1">
																<option value="01">01</option>
											                    <option value="02">02</option>
											                    <option value="03">03</option>
											                    <option value="04">04</option>
											                    <option value="05">05</option>
											                    <option value="06">06</option>
											                    <option value="07">07</option>
											                    <option value="08">08</option>
											                    <option value="09">09</option>
											                    <option value="10">10</option>
											                    <option value="11">11</option>
											                    <option value="12">12</option>
											                </select>
															<!-- <input size="2" name="expireMonth" type="text" maxlength="2"
																class="form-control input-xsmall input-inline radius1" placeholder="2位月份" />
															-->
															<span class="help-inline pull-left"> &nbsp;/&nbsp;</span>  
															<select name="expireYear" class="form-control input-xsmall input-inline radius1">
																<option value="2016">16</option>
											                    <option value="2017">17</option>
											                    <option value="2018">18</option>
											                    <option value="2019">19</option>
											                    <option value="2020">20</option>
											                    <option value="2021">21</option>
											                    <option value="2022">22</option>
											                    <option value="2023">23</option>
											                </select>
															<!-- <input size="4" name="expireYear" type="text"
																maxlength="4" placeholder="4位年份"
																class="form-control input-xsmall input-inline radius1" /> 
															-->				
														</div>
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-4">
	                                                	<span class="left">充值金额（<em>美元</em>）<span class="font-red">*</span>：</span>
	                                                </div>
	                                                <div class="col-md-3 col-sm-3 col-xs-8">
	                                                		<input name="amount" type="number" placeholder="充值金额，格式：356.32 " class="form-control radius0"
																maxlength="10" min="30" step="0.01" /> 
	                                                </div>
	                                                <div class="col-md-3 col-sm-3 col-xs-8">
	                                                	<span class="desc">(支付$ <span class="poundage">0.00</span><a href="javascript:;" data-toggle="tooltip" title="扣取信用卡手续费 2.9%+30美分"> 手续费？</a>)</span>
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	<div class="row margin-top-20"> 
	                                                	<div class="col-md-2 col-sm-2 col-xs-4">
	                                                		<span class="left">实际付款总额<span class="font-red">*</span>：</span>
	                                                	</div>
	                                                	<div class="col-md-3 col-sm-3 col-xs-8">
	                                                		<span class="totalAmount" style="color: #ff9a29;font-size: 18px;">$ 0.00</span>
	                                                		<input name="amount_1" type="hidden" class="form-control radius0"
																maxlength="10" min="30" step="0.01" /> 
	                                                	</div>
	                                                	<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                            </div>
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-4">
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-8">
	                                                	<a name="a_creditcard_recharge" class="btn btn-0088ff">确认充值</a>
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
											</div>
										</div>
									</div>
									
									<!-- <div class="tab-pane fade" id="tab_1_4">
										<div class="form-horizontal">
											<div class="form-body">
												<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                	<span class="left">人民币：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
	                                                	<input id="weixinRmbText" type="number" placeholder="请输入信用卡3位安全码" class="form-control radius0"
															maxlength="10" min="0" step="0.01" />
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
	                                                	<a id="a_rmb_weixin_charge_link" class="btn btn-0088ff">确认充值</a>
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
												
											</div>
										</div>
										
									</div> -->
									
									<!-- <div class="tab-pane fade" id="tab_1_5">
										<div class="form-horizontal">
											<div class="form-body">
												
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                	<span class="left">信用卡号码：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
	                                                	<input name="anetCreditNo" type="text" class="form-control radius0"
															placeholder="请输入信用卡号码" />
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                	<span class="left">信用卡安全码：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
	                                                	<input name="anetCreditSecurityCode" type="text" maxlength="3"
															placeholder="请输入信用卡3位安全码" class="form-control radius0" />
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                	<span class="left">失效月/年：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
	                                                	<div class="input-group">
															<input size="2" name="anetExpireMonth" type="text" maxlength="2"
																class="form-control input-xsmall input-inline radius1" placeholder="2位月份" />
															<span class="help-inline pull-left"> &nbsp;/&nbsp;</span>  
															<input size="2" name="anetExpireYear" type="text"
																maxlength="2" placeholder="2位年份"
																class="form-control input-xsmall input-inline radius1" /> 
															<span class="help-inline font-red">*</span>
														</div>
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                	<span class="left">充值金额（<em>美元</em>）：</span>
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
	                                                	<input name="anetAmount" type="number" maxlength="10" min="30" step="0.01"
															placeholder="请输入信用卡3位安全码" class="form-control radius0" />
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
	                                           	<div class="row margin-top-30">
	                                                <div class="col-md-2 col-sm-2 col-xs-2">
	                                                </div>
	                                                <div class="col-md-6 col-sm-6 col-xs-6">
	                                                	<a name="anet_creditcard_recharge" class="btn btn-0088ff">确认充值</a>
	                                                </div>
	                                                <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                                           	</div>
	                                           	
											</div>
										</div>
									</div>
									-->
								</div>
							</div>
							
							<div class="row problem-list">  <!--haokun modify 2017/02/13-->
								<div class="title">
									<span>使用遇到问题？</span>
									<a href="/us/help.jsp" target= "_blank"><i class="glyphicon glyphicon-question-sign"></i><span>帮助中心</span></a>
								</div>
								<div class="problem"> 
									<span>什么是PayPal充值手续费?</span> <!--haokun 删除了问号-->
								</div>
								<div class="answer"> 
									<span>答：PayPal对网上交易会产生如下手续费，这笔费用不是AnyoneHelps向用户收取的手续费，而是向PayPal支付的交易手续费：</span><br>
									<span>1. 美国 PayPal 账户：充值金额2.9%的交易费 + 30美分的固定费用。</span><br>
									<span>2. 非美国PayPal账户：充值金额3.9%的交易费 + 30 美分的固定费用。</span>
								</div>
								<div class="problem"> 
									<span>如何用PayPal进行充值？</span>
								</div>
								<div class="answer"> 
									<span>答：请于“充值金额（美元）”框填写您想充值的金额，例如100，此金额将会成为您充值成功后AnyoneHelps账户内的实际余额；根据您提供的充值金额，AnyoneHelps会帮助您计算出PayPal实际付款的总额，以美国PayPal账户为例，实际付款总额为100 /（1 - 2.9%）+0.30 = 103.29。</span>
								</div>
								
								<div class="problem"> 
									<span>什么是信用卡充值手续费？</span>
								</div>
								<div class="answer"> 
									<span>答：AnyoneHelps使用Stripe进行信用卡交易会产生如下手续费，这笔费用不是AnyoneHelps向用户收取的手续费，而是向Stripe支付的交易手续费：</span><br>
									<span>充值金额2.9%的交易费 + 30美分的固定费用。</span>
								</div>
								
								<div class="problem"> 
									<span>如何用信用卡进行充值？</span>
								</div>
								<div class="answer"> 
									<span>答：请于“充值金额（美元）”框填写您想充值的金额，例如100，此金额将会成为您充值成功后AnyoneHelps账户内的实际余额；根据您提供的充值金额，AnyoneHelps会帮助您计算出信用卡实际付款的总额，实际付款总额为100 / （1 - 2.9%）+0.30 = 103.29。</span><br>
								</div>
							</div>
						</div> 
						
						<!--  
						<div class="portlet-title" style="margin-top:60px;">
							<div class="caption font-dark">
								<i class=" icon-list"></i> <span
									class="caption-subject uppercase">转帐付款</span>
							</div>
						</div>
						<div class="portlet-body">
							<div class="form-horizontal">
								<div class="form-body">
									<div class="row margin-top-30">
	                                	<div class="col-md-2 col-sm-2 col-xs-2">
	                                    	<span class="left">收款人：</span>
	                                    </div>
	                                    <div class="col-md-6 col-sm-6 col-xs-6">
	                                   		<input type="text" class="form-control radius0" name="transfer-user" placeholder="请输入收款人用户名/邮箱/电话"/>
	                                   	</div>
	                                  <div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                               	</div>
	                               	<div class="row margin-top-30">
	                                	<div class="col-md-2 col-sm-2 col-xs-2">
	                                    	<span class="left">转帐金额$：</span>
	                                    </div>
	                                    <div class="col-md-6 col-sm-6 col-xs-6">
	                                    	<input type="number" class="form-control radius0" name="transfer-amount" 
												maxlength="10" min="30" step="0.1" placeholder="最少￥1，格式：356.32" /> 
	                                   	</div>
	                                  	<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                               	</div>
	                               	
	                               	<div class="row margin-top-30">
	                                	<div class="col-md-2 col-sm-2 col-xs-2">
	                                    </div>
	                                    <div class="col-md-6 col-sm-6 col-xs-6">
	                                    	<a href="#" name="transfer-a" class="btn btn-0088ff">付款</a>
	                                   	</div>
	                                  	<div class="col-md-4 col-sm-4 col-xs-4"> </div>
	                               	</div>
									
								</div>
							</div>
						</div> -->
					</div>
					
				</div>
			</div>

			<!-- END PAGE BASE CONTENT -->

		</div>
		<!-- END CONTENT BODY -->
	</div>
	<!-- END CONTENT -->
</div>
<!-- END CONTAINER -->

<script type="html/x-jsrender" id="transfer-tmpl">
<table class="table table-bordered table-striped">
	<thead>
	    <tr>
	        <th width="100">付款金额</th>
 	      	<th width="100">收款人用户名</th>
	   	</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				<span class="font-red-flamingo">\${{:amount}}</span>
			</td>
			<td>
				{{:user.nickName}}
			</td>
		</tr>
	</tbody>
</table>

<table class="table table-bordered table-striped">
	<thead>
	    <tr>
	        <th width="100">收款人邮箱</th>
 	      	<th width="100">收款人手机</th>
	   	</tr>
	</thead>
	<tbody>
		<tr>
			<td>
				{{:user.email}}
			</td>
			<td>
				{{:user.telphone}}
			</td>
		</tr>
	</tbody>
</table>
	<div class="row">
		<div class="col-md-offset-4 col-md-4">
	    	<button type="submit" class="btn green transfer-submit-btn">确定付款</button>
	        <button type="button" class="btn default transfer-cancle-btn">取消</button>
	   	</div>
	</div>
</script>

<%@ include file="/include/footer.jsp"%>
<!-- BEGIN GLOBAL LEVEL PLUGINS -->
<script src="/assets/global/scripts/app.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/bootstrap-toastr/toastr.min.js" type="text/javascript"></script>
<script src="/assets/global/scripts/utils.js" type="text/javascript"></script>
<script src="/assets/global/scripts/datatable.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/datatables.min.js" type="text/javascript"></script>
<script src="/assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap.js" type="text/javascript"></script>
<!-- END GLOBAL LEVEL PLUGINS -->
<script src="/assets/layouts/leftsider/scripts/layout.js" type="text/javascript"></script>
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<!-- <script src="/assets/pages/scripts/table-datatables-managed.js" type="text/javascript"></script> -->
<script src="/assets/pages/scripts/finance-recharge.js" type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
