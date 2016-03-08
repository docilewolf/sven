/**
 * Created by stefan on 16-3-2.
 */
function MouseScroll (event) {
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
})();

//触发文件上传事件时调用此函数，应注意需要触发angular的数据绑定
function selectFile(){
    $(".file-upload").click();
}

//防止鼠标没有选择区域报错
function initRange(selection){
    if(!selection.rangeCount){
        var parentNode = $('#content-area')[0];
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