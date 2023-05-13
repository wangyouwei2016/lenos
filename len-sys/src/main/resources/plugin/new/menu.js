window.lenosp = {};
/**
 * 菜单
 *
 * 需要提供扩展：增删改查一个菜单
 */
(function ($, lenosp) {

    var _win = $(window),
        _doc = $(document),
        _content = $('.main-content.wrapper');

    var dashboard = {
        url: 'dashboard',
        openWait: true
    };

    var _menu = {
        /**
         * 显示新页面
         * @param option
         */
        load: function (option) {
            var that = this;
            var htmlStr = that.loadHtml(option.url),
                _content = $('.wrapper');
            _content.empty();
            _content.append(htmlStr);
        },

        /**
         * 加载html
         * @param url
         * @param callback
         * @returns {*}
         */
        loadHtml: function (url, callback) {
            var result;
            $.ajax({
                url: url,
                async: false,
                dataType: 'html',
                beforeSend: function (request) {
                    // request.setRequestHeader("IsGetHtml", 'true');
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

        /**
         * 菜单绑定事件
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
                    var item = _that.find('a');
                    var options = JSON.stringify(item.data('options'));
                    _menu.load(options);
                })

            });
        },
    }


    lenosp.menu = {
        /**
         * 初始化
         */
        init: function () {
            //加载主面板(需要考虑路由)
            _menu.load(dashboard)
        },
        /**
         * 菜单绑定事件
         * @param params
         */
        bind: function (params) {
            _menu.bind();
        },

        load: function (option) {
            _menu.load(option);
        }
    };

})(jQuery, lenosp);