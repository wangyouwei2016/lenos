/**
 * 统一处理js方法
 */
/**框架*/
(function ($) {
    //tabs初始化
    lenosp.tabs.init(lenosp.menu.dashboard,
        function (options) {
            //tab点击回调，加载页面
            lenosp.menu.load(options);
        }, function (options) {
            lenosp.menu.close(options);
        });

    //初始化dashboard
    lenosp.tabs.selectHome();

    //菜单绑定tabs
    lenosp.menu.bind({}, function (option) {
        lenosp.tabs.add(option, true);
    });


    //获取浏览器高度
    var windowHeight = $(window).height();
    console.log(windowHeight-108-48)
    $('.wrapper').css('min-height',`${windowHeight-108-48}px`)


})(jQuery);


$.fn.serializeObject = function() {
    var obj = {};
    var arr = this.serializeArray();
    $.each(arr, function() {
        if (obj[this.name] !== undefined) {
            if (!obj[this.name].push) {
                obj[this.name] = [obj[this.name]];
            }
            obj[this.name].push(this.value || '');
        } else {
            obj[this.name] = this.value || '';
        }
    });
    return obj;
};