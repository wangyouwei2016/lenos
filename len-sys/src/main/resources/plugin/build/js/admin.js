layui.config({
    base: 'plugin/build/js/',
    version: '1.0.1'
}).define('tab', function (exports) {

    var $ = layui.jquery;
    var $body = $('body');


    var admin = {};
    var events = admin.events = {
        fullscreen: function (othis) {
            var SCREEN_FULL = 'layui-icon-screen-full'
                , SCREEN_REST = 'layui-icon-screen-restore'
                , iconElem = othis.children("i");
            var elem = document.body;
            if (iconElem.hasClass(SCREEN_FULL)) {
                if (elem.webkitRequestFullScreen) {
                    elem.webkitRequestFullScreen();
                } else if (elem.mozRequestFullScreen) {
                    elem.mozRequestFullScreen();
                } else if (elem.requestFullScreen) {
                    elem.requestFullscreen();
                }

                iconElem.addClass(SCREEN_REST).removeClass(SCREEN_FULL);
            } else {
                elem = document;
                if (elem.webkitCancelFullScreen) {
                    elem.webkitCancelFullScreen();
                } else if (elem.mozCancelFullScreen) {
                    elem.mozCancelFullScreen();
                } else if (elem.cancelFullScreen) {
                    elem.cancelFullScreen();
                } else if (elem.exitFullscreen) {
                    elem.exitFullscreen();
                }

                iconElem.addClass(SCREEN_FULL).removeClass(SCREEN_REST);
            }
        }
    };

    $body.on('click', '*[layadmin-event]', function () {
        var othis = $(this)
            , attrEvent = othis.attr('layadmin-event');
        events[attrEvent] && events[attrEvent].call(this, othis);
    });

    exports('admin', admin);


});
