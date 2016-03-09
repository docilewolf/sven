
//鼠标滚动事件
/*function MouseScroll (event) {
    var rolled = 0;
    if ('wheelDelta' in event) {
        rolled = event.wheelDelta;
    }
    else {  // Firefox
        // The measurement units of the detail and wheelDelta properties are different.
        rolled = -40 * event.detail;
    }
    var header = $(".header");
    if(rolled < 0){

    }
    rolled < 0? header.addClass("hide-header"): header.removeClass("hide-header");
}

(function() {
    // for mouse scrolling in Firefox
    var elem = document.body;
    if (elem.addEventListener) {    // all browsers except IE before version 9
        // Internet Explorer, Opera, Google Chrome and Safari
        elem.addEventListener ("mousewheel", MouseScroll, false);
        // Firefox
        elem.addEventListener ("DOMMouseScroll", MouseScroll, false);
    }
    else {
        if (elem.attachEvent) { // IE before version 9
            elem.attachEvent ("onmousewheel", MouseScroll);
        }
    }
})();*/

//滚动条事件
function scrollEvent (delta) {
    if( delta === 0 ) return;
    var header = $(".header");
    delta > 0? header.addClass("hide-header"): header.removeClass("hide-header");
}
(function () {
    var beforeScrollTop = document.body.scrollTop;
    window.addEventListener("scroll", function() {
        var afterScrollTop = document.body.scrollTop,
            delta = afterScrollTop - beforeScrollTop;
        scrollEvent(delta);
        beforeScrollTop = afterScrollTop;
    }, false);
})();

//触发文件上传事件时调用此函数，应注意需要触发angular的数据绑定
function selectFile(){
    $(".file-upload").click();
}

//防止鼠标没有选择区域报错
function initRange(selection){
    var parentNode = $('#content-area')[0];
    if(!selection.rangeCount || $(selection.anchorNode).parents('#content-area').length <= 0){
        selection.collapse(parentNode, parentNode.childNodes.length);
    }
    return selection.getRangeAt(0);
}

//向鼠标选择区域(光标)插入指定html代码
function insertHtml(tag, range){
    var selection = rangy.getSelection();
    if(!range) range = initRange(selection);
    //selection.removeRange(selection.getRangeAt(0));
    selection.removeAllRanges();
    selection.addRange(range);
    document.execCommand("insertHTML",false,tag);
}

//隐藏或显示类别
$(document).on('click', function (event) {
    if(!$('.header-essay').hasClass('hide')) handleHeader($('.header-essay')[0], event);
    if(!$('.header-picture').hasClass('hide')) handleHeader($('.header-picture')[0], event);
});

function handleHeader(div, event){
    var aLeft = $('.header-a')[0].offsetLeft;
    var aTop = $('.header-a')[0].offsetTop;
    var aWith = $('.header-a')[0].clientWidth;
    var aHeight = $('.header-a')[0].clientHeight;
    var wx = event.clientX;
    var wy = event.clientY;
    var d_left = div.offsetLeft < aLeft? div.offsetLeft: aLeft;
    var d_top = div.offsetTop < aTop? div.offsetTop: aTop;
    var d_width = div.clientWidth+aWith;
    var d_height = div.clientHeight+aHeight;
    if(wx < d_left || wy < d_top || wx > (d_left + d_width) || wy > (d_top + d_height))
        $(div).addClass('hide')
}

function toggleDisplayHeader(cssName, hideClassName){
    $(cssName).hasClass('hide')?$(cssName).removeClass('hide'):$(cssName).addClass('hide');
    if(!$(hideClassName).hasClass('hide')) $(hideClassName).addClass('hide')
}