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


})(jQuery);