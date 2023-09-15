window.lenosp = {};
/**
 * 菜单
 *
 * 需要提供扩展：增删改查一个菜单
 */
(function ($, lenosp) {

    var _win = $(window),
        _doc = $(document),
        _content = $('.main-content.wrapper.content');

    var dashboard = {
        url: 'dashboard',
        code: 'dashboard'
    };

    var _menu = {
        /**
         * 显示新页面
         * @param option
         */
        load: function (option) {
            _menu._load(option);
        },

        _load: function (option) {
            var that = this;
           // var htmlStr = that.loadHtml(option.url),
            var   _content = $('.wrapper .content');
            _content.find('div[code]').each(function () {
                $(this).hide();
            });
            var exists = _content.find('div[code=' + option.code + ']').length > 0;
            if (exists) {
                _content.find('div[code=' + option.code + ']').each(function () {
                    $(this).show();
                });
            } else {
                var windowHeight = $(window).height();
                const $iframe = $('<iframe>', {
                    id: 'myIframe',
                    width: '100%',
                    height: windowHeight - 108 - 48,
                    src: option.url
                });

                $iframe.css('border', 0);

                var wrapperDiv = $('<div>')
                    .attr('id', option.code)
                    .attr('code', option.code)
                    .attr('data-options', JSON.stringify(option))
                    .html($iframe);
                _content.append(wrapperDiv);
            }

        },

        _close: function (option) {
            var _content = $('.wrapper .content');
            var exists = _content.find('div[code=' + option.code + ']').length > 0;
            if (exists) {
                _content.find('div[code=' + option.code + ']').each(function () {
                    $(this).remove();
                });
            }

        },

        /**
         * 加载html
         * @param path 文件路径
         * @param callback
         * @returns {*}
         */
        loadHtml: function (path, callback) {
            var newPath = 'ftl/new' + path + '.html';
            /*标记-- 需要迁移目录*/
            if (path === 'dashboard') {
                newPath = 'ftl/new/main/dashboard.html';
            }
            var result;
            $.ajax({
                url: 'api/positions/list',//newPath,
                async: false,
                dataType: 'html',
                beforeSend: function (request) {
                    // request.setRequestHeader("IsGetHtml", 'true');
                },
                error: function (xhr, err, msg) {
                    var m = ['<div style="padding: 20px;font-size: 20px;text-align:left;color:#009688;">',
                        '<p>{{msg}}  >>> ' + path + '</p>',
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
         * @param callback 回调
         */
        bind: function (params, callback) {
            var defaults = {
                target: undefined
            };
            $.extend(true, defaults, params);

            var _target = defaults.target === undefined ? _doc : $(defaults.target);
            _target.find('li.len-menu-item').each(function () {
                var _that = $(this);
                _that.off('click').on('click', function () {
                    var item = _that.find('a');
                    var options = item.data('options');
                    typeof callback === 'function' && callback(options);
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
         * @param callback
         */
        bind: function (params, callback) {
            _menu.bind(params, callback);
        },

        load: function (option) {
            _menu.load(option);
        },

        close: function (option) {
            _menu._close(option);
        },

        dashboard: dashboard
    };

})(jQuery, lenosp);