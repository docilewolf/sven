function initTimeline() {
	var $timeline_block = $('.cd-timeline-block');
	//hide timeline blocks which are outside the viewport
	$timeline_block.each(function(){
		if($(this).offset().top > $(window).scrollTop()+$(window).height()*0.75) {
			$(this).find(".cd-timeline-content").addClass("hide-content");
		}
	});

	//on scolling, show/animate timeline blocks when enter the viewport
	$(window).on('scroll', function(){
		$timeline_block.each(function(){
			//动态显示
			if( $(this).offset().top <= $(window).scrollTop()+$(window).height()*0.75) {
				var content = $(this).find(".cd-timeline-content");
				display(content);
				/*console.log(content.offset().left);
				content.removeClass("hide-content");
				var translate = "translateX("+content.offset().left+")";
				content.addClass("show-content");
				content.css("-webkit-transform",translate);
				content.css("-ms-transform",translate);
				content.css("transform",translate);*/
			}
		});
	});

	//动态设置透明度，会有抖动效果
	function display(div){
		if(div.css("opacity") >= 1) return;
		var i = 0;
		function setStyle() {
			i += 50;
			div.css("filter","Alpha(Opacity=" + i + ")");
			div.css("opacity",i / 100);
			if (i > 100) {
				clearInterval(h);
				i = 0;
			}
		}
		var h = setInterval(setStyle, 100);
	}
}