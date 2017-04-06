$(function() {
	$("title").html($(":hidden[name='seoTitle']").val());
	$("meta[name='keywords']").attr("content",$(":hidden[name='seoKeywords']").val());
	$("meta[name='description']").attr("content",$(":hidden[name='seoDescription']").val());
	
	/*打开视频窗口*/
	$(".show-player").click(function(){
		$(".video-play").append('<video src="//s3-us-west-1.amazonaws.com/anyonehelps/aboutus_video.mp4" type="video/mp4" autoplay="autoplay" loop="loop" controls="controls">'
			+'您的浏览器不支持 video 标签。'
			+'</video>')
		$(".video-play").removeClass("hide");
		$(".video-play-close").removeClass("hide");
		
	})
	/*关闭视频窗口*/
	$("a[name='video-play-close-a']").click(function(){
		$(".video-play").addClass("hide");
		$(".video-play video").remove();
		$(".video-play-close").addClass("hide");
	})

});
