var menuFn = (function ($) {

    var _win = $(window),
        _doc = $(document),
        _content = $('.main-content.wrapper');


    return {
        /**
         * 绑定事件
         * @param params
         */
        bind: function (params) {
            var that = this,
                _config = that.config;
            var defaults = {
                target: undefined
            };
            $.extend(true, defaults, params);

            var _target = defaults.target === undefined ? _doc : $(defaults.target);

            _target.find('li.len-menu-item').each(function () {
                var _that = $(this);
                _that.off('click').on('click', function () {
                    var htmlStr = that.loadHtml('/user/showUser?v=1683539731677');
                    _content = $('.wrapper');
                    _content.append(htmlStr);
                })

            });
        },

        loadHtml: function (url, callback) {
            var result;
            $.ajax({
                url: url,
                async: false,
                dataType: 'html',
                beforeSend: function (request) {
                    request.setRequestHeader("IsGetHtml", 'true');
                },
                error: function (xhr, err, msg) {
                    var m = ['<div style="padding: 20px;font-size: 20px;text-align:left;color:#009688;">',
                        '<p>{{msg}}  >>> ' + url + '</p>',
                        '</div>'
                    ].join('');
                    if (xhr.status === 404) {
                        result = m.replace('{{msg}}', '<i class="layui-icon" style="font-size:70px;">&#xe61c;</i>  ' + msg);
                        return;
                    }
                    result = m.replace('{{msg}}', '<i class="layui-icon" style="font-size:70px;">&#xe69c;</i>  未知错误.');
                },
                success: function (res) {
                    result = res;
                },
                complete: function () {
                    typeof callback === 'function' && callback();
                }
            });
            return result;
        },
    };
})(jQuery);