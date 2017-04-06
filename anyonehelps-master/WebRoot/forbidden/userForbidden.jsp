<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>
<style>
#fiveOhThree {
    background: #FFF url('/assets/pages/img/forbidden/503error_bg.jpg') repeat-x;
    color: #000;
    font-family: 'Trebuchet MS', Arial, Helvetica, sans-serif;
	height:80%;
}
#fiveOhThree #wrapper {
    width: 820px;
    margin: 0 auto;
    position: relative;
    z-index: 99;
}
#fiveOhThree .messageWrapper {
    margin-top:70px;
    float: right;
    width: 480px;
}
#fiveOhThree .messageWrapper p {
    margin: 0 0 12px 18px;
}
#fiveOhThree a:link, body#fiveOhThree a:visited, body#fiveOhThree a:active { color: #000; text-decoration: underline; }
#fiveOhThree a:hover { color: #000; text-decoration: none; }
#fiveOhThree .woopsLogo {
    margin-top:200px;
    width: 450px;
    height: 110px;
    background: transparent url('/assets/pages/img/forbidden/503error_woopsLogo.png') no-repeat;
    margin-top: 125px;
    text-align: right;
}
#fiveOhThree .skydiver {
    margin-top:70px;
    float: left;
    width: 338px;
    height: 476px;
    background: transparent url('/assets/pages/img/forbidden/503error_skydiver.png') no-repeat;
}

#fiveOhThree .errorWrapper {
    margin-top:200px;
    color: #000;
}


</style>




        <title>AnyoneHelps - 503 Error</title>

        
    <div id="fiveOhThree">
        <div id="wrapper">		
            <div class="messageWrapper">
                <div class="woopsLogo"></div>
                <p>很抱歉通知您，您目前违反用户协议，现在禁止您登录。</p>
                <p>如果您要申述，或者还有其他问题，请发送邮件至 support@anyonehelps.com<br/>
                                                          或联系客服 (626) 662-2748</p>
                <p>感谢您的理解和支持。</p>
            </div>
            <div class="skydiver"></div>
            <div style="clear: both;"></div>
        </div>
        <div id="Clouds">&nbsp;</div>
    </div>
	
	
	<%@ include file="/include/footer.jsp"%>
