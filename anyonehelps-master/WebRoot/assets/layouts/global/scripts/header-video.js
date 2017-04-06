var headerVideo = function() {
	var headerHide = function(){
		if(!$(".video-play").hasClass("hide")){
			//视频正在播放中
			setTimeout(headerHide, 10*1000 );
		}else{
			$(".header").addClass("hide");
			$(".pull-up-div").addClass("hide");
			$(".drop-down-div").removeClass("hide");

			$('body,html').animate({'scrollTop':$(window).scrollTop()-420},0)
		}
	}
	//setTimeout(headerHide, 10*1000 );
	$(".header-show").click(function(){
		$(".header").removeClass("hide");
		$(".pull-up-div").removeClass("hide");
		$(".drop-down-div").addClass("hide");
	})
	$(".header-hide").click(function(){
		$(".header").addClass("hide");
		$(".pull-up-div").addClass("hide");
		$(".drop-down-div").removeClass("hide");
	})
	
	/*打开视频窗口*/
	$(".show-player").click(function(){
		$(".video-play").append('<video src="//s3-us-west-1.amazonaws.com/anyonehelps/index_video.mp4" autoplay="autoplay" loop="loop" controls="controls">'
			+'您的浏览器不支持 video 标签。'
			+'</video>')
		$(".video-play").removeClass("hide");
	})
	/*关闭视频窗口*/
	$("a[name='video-play-close-a']").click(function(){
		$(".video-play").addClass("hide");
		$(".video-play video").remove();
	})

    return {
        //main function
        init: function() {
        }
    };

}();
jQuery(document).ready(function() {
	headerVideo.init();
});