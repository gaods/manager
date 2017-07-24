function init_infos() {
    $("#tabs").tabs('add', {title: "JFS动态", content: createTabContent("/infos"), closable: true});
}

function OpenTab(obj) {
    title = $(obj).attr("titile");
    url = $(obj).attr("rel");
    // /**    如果这个标题的标签存在，则选择该标签    否则添加一个标签到标签组    */
    // if ($("#tabs").tabs('exists', title)) {
    //     $("#tabs").tabs('close', title);
    //     $("#tabs").tabs('add', {title: title, content: createTabContent(url), closable: true});
    // }
    // else {
    //     $("#tabs").tabs('add', {title: title, content: createTabContent(url), closable: true});
    // }
    menuClick(url);
}
/* 生成标签内容 */
function createTabContent(url) {
    return '<iframe id="ifm" name="ifm" style="width:100%; height: 100%;" scrolling="auto" frameborder="0" src="' + url + '"></iframe>';
}

var menuClick = function(menuUrl) {

    $("#ifm").attr('src',menuUrl);

};


//function adjustIfHt(){
//    var ht = $(window).height();//获取浏览器窗口的整体高度；
//    var topHeader = $("#headerbar").height();//获取头部高度，定义一个变量名为topHeader
//    $("#sidebar").height(ht);
//    $("#ifm").height(ht);
//    $("#sidebar").height(ht-topHeader);//计算左边高度：窗口高度-头部高度
//    $("#ifm").height(ht-topHeader);//计算右边高度：窗口高度-头部高度
//  }