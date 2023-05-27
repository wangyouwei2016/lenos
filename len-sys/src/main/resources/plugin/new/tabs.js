/**
 * tabs功能
 */
(function ($, lenosp) {

    var _tabs = {
        //初始化
        _init: function () {
            //向左滚动事件
            $('.t-tabs__operations--left').click(function () {
                // 每隔一段时间更新元素的 transform 属性
                // var left = 0;
                //可视区域长度
                var tabShowWidth = $('.t-tabs__nav-scroll').width();
                //tab总长度
                var tab = $('.t-is-smooth');
                var tabWidth = tab.width();
                var transformValue = tab.css('transform');
                //当前移动的长度
                var translate3dWidth = _tabs.getTranslate3dValue(transformValue);

                var offsetValue = tabWidth - tabShowWidth;

                if (translate3dWidth === 0) {
                    return;
                }
                if (translate3dWidth < 0) {
                    translate3dWidth += 300;
                }
                if (translate3dWidth > 0) {
                    translate3dWidth = 0;
                }
                var right = $('.t-tabs__operations--right');
                if (tabShowWidth <= tabWidth && right.find('.t-tabs__btn--right').length === 0) {
                    right.append(
                        '<div class="t-tabs__btn--right t-tabs__btn t-size-m">' +
                        '<i class="layui-icon fa fa-angle-right"></i>' +
                        '</div>'
                    )
                }

                $('.t-tabs__nav-wrap').css({
                    'transform': 'translate3d(' + translate3dWidth + 'px, 0px, 0px)',
                    'transition': 'transform 0.3s ease'
                });
                /*if (tabWidth < (tabShowWidth + (-translate3dWidth))) {
                    translate3dWidth = translate3dWidth + 100;
                    //左滚动大于0 取0
                    translate3dWidth = translate3dWidth > 0 ? 0 : translate3dWidth;

                }*/
            });

            //向右滚动事件
            $('.t-tabs__operations--right').click(function () {
                //可视区域长度
                var tabShowWidth = $('.t-tabs__nav-scroll').width();
                //tab总长度
                var tab = $('.t-is-smooth');
                var tabWidth = tab.width();
                var transformValue = tab.css('transform');
                //当前移动的长度
                var translate3dWidth = _tabs.getTranslate3dValue(transformValue);
                var offsetValue = tabWidth - tabShowWidth;
                translate3dWidth = -translate3dWidth;
                if (translate3dWidth < offsetValue) {
                    translate3dWidth = translate3dWidth + 300;
                    translate3dWidth = translate3dWidth > offsetValue ? offsetValue : translate3dWidth;

                    if (translate3dWidth === offsetValue) {
                        $('.t-tabs__btn--right').remove();
                    }
                    $('.t-tabs__nav-wrap').css({
                        'transform': 'translate3d(' + -translate3dWidth + 'px, 0px, 0px)',
                        'transition': 'transform 0.3s ease'
                    });
                }
            });
            //tab点击事件
            $('.t-tabs__nav-wrap').find('.t-tabs__nav-item').click(function () {
                var index = $(this).index();
                var active = $('.t-tabs__nav-wrap').find('.t-is-active');
                var oldIndex = active.index();
                if (index === oldIndex) {
                    return;
                }
                active.removeClass('t-is-active');
                $(this).addClass('t-is-active')

                //左侧长度
                var leftTotalWidth = 0;
                $(this).prevAll('.t-tabs__nav-item').addBack().each(function () {
                    leftTotalWidth += $(this).outerWidth(true);
                });
                console.log('左侧选项卡总长度:', leftTotalWidth);
                //（左侧-往左偏移）要小于可视区域长度

                //可视区域长度
                var tabShowWidth = $('.t-tabs__nav-scroll').width();

                var tab = $('.t-is-smooth');
                var transformValue = tab.css('transform');
                //偏移长度
                var translate3dWidth = _tabs.getTranslate3dValue(transformValue);
                //左侧显示的宽度=包含当前tab左侧宽度-偏移量
                var leftShowWidth = leftTotalWidth - (-translate3dWidth);
                var left;
                if (leftShowWidth + 40 > tabShowWidth) {
                    //大于可视宽度则需要往左偏移:左侧宽度-可视宽度
                    left = translate3dWidth - ((leftShowWidth + 40) - tabShowWidth)
                    $('.t-tabs__nav-wrap').css({
                        'transform': 'translate3d(' + left + 'px, 0px, 0px)',
                        'transition': 'transform 0.3s ease'
                    });
                } else {
                    //当前宽度
                    var thisWidth = $(this).outerWidth(true);
                    //当前左侧宽度
                    var excludeCurrentWidth = leftTotalWidth - thisWidth;
                    //偏移量大于左侧宽度且小于包含当前tab的左侧宽度
                    var leftIconWidth = 40;
                    var existsLeftIconWidth = -translate3dWidth + leftIconWidth;
                    if (existsLeftIconWidth > excludeCurrentWidth && existsLeftIconWidth < leftTotalWidth) {
                        left = -excludeCurrentWidth + leftIconWidth;
                        //往右侧偏移左侧宽度+一个左侧图标的宽度
                        $('.t-tabs__nav-wrap').css({
                            'transform': 'translate3d(' + left + 'px, 0px, 0px)',
                            'transition': 'transform 0.3s ease'
                        });
                    }

                }

            })

        },
        //获取x轴偏移量
        getTranslate3dValue(transformValue) {
            var regex = /matrix\((.*?)\)/;
            var match = transformValue.match(regex);

            if (match) {
                var translate3dString = match[1];
                var translate3dValues = translate3dString.split(',');

                return parseFloat(translate3dValues[4]);
            } else {
                return null;
            }
        }
    };

    var tabs = {
        init: function () {
            _tabs._init();
        },
        //获取一个tab
        get: function () {

        },
        //获取当前选中的tab
        getCurrent: function () {

        },
        //添加一个tab
        add: function () {

        },

        //删除一个tab
        remove: function () {

        },
        //删除所有tab
        removeAll: function () {

        },
        //插入一个tab
        insertTab: function () {

        },
        //选中设置一个tab
        change: function () {

        },
        //tab是否存在
        exists: function () {

        }

    };

    tabs.init();
    lenosp.tabs = tabs;
})(jQuery, lenosp);