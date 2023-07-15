/**
 * tabs功能
 */
(function ($, lenosp) {

    var _tabs = {
        homeOption: null,
        //初始化 一些点击事件(左移、右移、关闭、点击tab)
        _init: function (tabClickCallback, closeCallback) {
            //向左滚动事件
            $('.t-tabs__operations--left').click(function () {
                //可视区域长度
                var tabShowWidth = $('.t-tabs__nav-scroll').width();
                //tab总长度
                var tab = $('.t-is-smooth');
                var tabWidth = tab.width();
                var transformValue = tab.css('transform');
                //当前移动的长度
                var translate3dWidth = _tabs.getTranslate3dValue(transformValue);

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

                if (translate3dWidth === 0) {
                    $('.t-tabs__btn--left').remove();
                }
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
                //偏移长度
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

                    var leftIcon = $('.t-tabs__operations--left');
                    if (leftIcon.find('.t-tabs__btn--left').length === 0) {
                        leftIcon.append(
                            '<div class="t-tabs__btn--left t-tabs__btn t-size-m">' +
                            '   <i class="layui-icon fa fa-angle-left"></i>' +
                            '</div>'
                        )
                    }
                }
            });

            //tab点击事件
            $('.t-tabs__nav-wrap').on('click', '.t-tabs__nav-item', function () {
                var index = $(this).index();
                var active = $('.t-tabs__nav-wrap').find('.t-is-active');
                var oldIndex = active.index();
                if (index === oldIndex) {
                    return;
                }

                active.removeClass('t-is-active');
                $(this).addClass('t-is-active');

                if ($(this).attr('id') === 'tabs-nav-home') {
                    typeof tabClickCallback === 'function' && tabClickCallback(_tabs.homeOption);
                    return;
                }

                //实现tab遮挡展示全
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
                    _tabs.autoCalShowIcon()
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
                typeof tabClickCallback === 'function' && tabClickCallback(_tabs._getOptionByThis($(this)));

            });

            //关闭点击事件
            $('.t-tabs__nav-wrap').on('click', '.span-icon', function (event) {
                event.stopPropagation();
                //当前tab
                var $navTtem = $(this).parent().parent();
                //当前
                var currentIndex = $navTtem.index();
                var option = _tabs._getOptionByThis($navTtem);
                var isActive = _tabs.isActiveByCode(option.code);

                var navWrap = $(this).closest('.t-tabs__nav-item');
                navWrap.remove();
                _tabs.autoCalRemoveIcon();

                if (!isActive) {
                    return;
                }

                if (currentIndex > 1) {
                    //左侧存在
                    _tabs.changeByIndex(currentIndex - 1);
                    typeof closeCallback === 'function' && closeCallback(option);
                    return
                }

                //总tabs
                var tabSize = _tabs.getTabAllSize() - 1;

                if (tabSize === 0) {
                    //最后一个
                    _tabs.changeByIndex(0);
                    typeof closeCallback === 'function' && closeCallback(option);
                    return;
                }


                _tabs.changeByIndex(1);
                typeof closeCallback === 'function' && closeCallback(option);

            });

        },

        /**
         * 获取option信息
         * @param _this
         * @returns {*}
         * @private
         */
        _getOptionByThis: function (_this) {
            return _this.data('options');

        },

        //删除一个tab
        _removeByCode: function (code, callback) {
            $('.t-tabs__nav-wrap').find('div[len-code=' + code + ']').remove();
            _tabs.autoCalRemoveIcon();
        },

        /**
         * 添加一个tab
         * @param insertIndex 插入位置下标，默认最后一个
         * @param data 格式{"name":"标签","code":"123","url":"xxx","icon":"fa-home"}
         * @param isActive true 默认
         * @private
         */
        _addTabs: function (insertIndex, data, isActive) {
            var tab = _tabs.getByCode(data.code);

            if (tab.length > 0) {
                tab.click();
                return {index: tab.index(), tab: tab, exists: true};
            }
            var navWrap = $('.t-tabs__nav-wrap');
            tab = $('<div data-options=' + JSON.stringify(data) + ' len-code=' + data.code + ' class="t-tabs__nav-item t-tabs__nav--card t-size-m"' +
                '   draggable="true"><span class="t-tabs__nav-item-text-wrapper"' +
                '   style="outline: none; z-index: 1;">' +
                '   <span>' + data.name + '</span>' +
                '   <span class="span-icon"><i class="layui-icon fa fa-times"></i></span>' +
                '  </span>' +
                '</div>');


            if (typeof insertIndex === 'number') {
                var indexTab = navWrap.find('.t-tabs__nav-item').eq(insertIndex);
                if (indexTab.length > 0) {
                    indexTab.after(tab);
                } else {
                    navWrap.append(tab);
                }

            } else {
                navWrap.append(tab);
            }

            if (isActive) {
                tab.click();
            }

            //index：小标，tab:当前jquery对象，exists：是否已存在
            return {index: tab.index(), tab: tab, exists: false};
        },

        autoCalRemoveIcon: function () {
            //可视区域长度
            var tabShowWidth = $('.t-tabs__nav-scroll').width();
            //tab总长度
            var tab = $('.t-is-smooth');
            var tabAllWidth = tab.width();
            var transformValue = tab.css('transform');
            //当前移动的长度
            var translate3dWidth = _tabs.getTranslate3dValue(transformValue);
            //可视区域真实长度
            var visibleWidth = tabAllWidth - (-translate3dWidth);
            var right = $('.t-tabs__operations--right');
            if (tabShowWidth > visibleWidth && right.find('.t-tabs__btn--right').length > 0) {
                $('.t-tabs__btn--right').remove();
            }
        },

        /**
         * 计算是否显示左右侧图标
         */
        autoCalShowIcon: function () {
            //可视区域长度
            var tabShowWidth = $('.t-tabs__nav-scroll').width();
            //tab总长度
            var tab = $('.t-is-smooth');
            var tabAllWidth = tab.width();
            var transformValue = tab.css('transform');
            //当前移动的长度
            var translate3dWidth = _tabs.getTranslate3dValue(transformValue);
            //偏移长度
            var offsetValue = tabAllWidth - tabShowWidth;

            if (translate3dWidth < offsetValue) {
                var leftIcon = $('.t-tabs__operations--left');
                if (leftIcon.find('.t-tabs__btn--left').length === 0) {
                    //显示左侧icon
                    leftIcon.append(
                        '<div class="t-tabs__btn--left t-tabs__btn t-size-m">\n' +
                        '   <i class="layui-icon fa fa-angle-left"></i>\n' +
                        '</div>'
                    )
                }

            } else {
                var right = $('.t-tabs__operations--right');
                if (tabShowWidth <= tabWidth && right.find('.t-tabs__btn--right').length === 0) {
                    //显示右侧icon
                    right.append(
                        '<div class="t-tabs__btn--right t-tabs__btn t-size-m">' +
                        '<i class="layui-icon fa fa-angle-right"></i>' +
                        '</div>'
                    )
                }
            }


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
        },

        getByCode: function (code) {
            return $('.t-tabs__nav-wrap').find('div[len-code=' + code + ']')
        },

        getByIndex: function (index) {
            return $('.t-tabs__nav-wrap').find('.t-tabs__nav-item').eq(index);
        },

        //获取当前点击的tab
        getCurrent: function () {
            return $('.t-tabs__nav-wrap').find('div.t-tabs__nav-item.t-is-active')
        },

        removeAll: function () {
            $('.t-tabs__nav-wrap').find('div.t-tabs__nav-item:not(#tabs-nav-home)').remove();
        },

        changeByCode: function (code) {
            if (code === 'dashboard') {
                _tabs.home();
            } else {
                var tab = $('.t-tabs__nav-wrap').find('div[len-code=' + code + ']');
                if (tab.length === 0) {
                    console.error('tabs code:' + code + ',not exists!');
                    return;
                }
            }

            tab.click();
        },

        changeByIndex: function (index) {
            if (index === 0) {
                _tabs.home();
            } else {
                var tab = $('.t-tabs__nav-wrap').find('.t-tabs__nav-item').eq(index);
                if (tab.length === 0) {
                    console.error('tabs index:' + index + ',not exists!');
                    return;
                }
            }

            tab.click();
        },

        existsByCode: function (code) {
            var tab = $('.t-tabs__nav-wrap').find('div[len-code=' + code + ']');
            return tab.length > 0
        },

        existsByIndex: function (index) {
            var tab = $('.t-tabs__nav-wrap').find('.t-tabs__nav-item').eq(index);
            return tab.length > 0
        },

        home: function (callback) {
            var homeTab = $('.t-tabs__nav-wrap').find('div[id=tabs-nav-home]');
            homeTab.click();
            typeof callback === 'function' && callback();
        },

        getTabAllSize: function () {
            return $('.t-tabs__nav-wrap').find('.t-tabs__nav-item').length;
        },

        isActiveByIndex: function (index) {
            var tab = $('.t-tabs__nav-wrap').find('.t-tabs__nav-item').eq(index);
            if (tab.length > 0) {
                return tab.hasClass('t-is-active');
            }
            return false;
        },

        isActiveByCode: function (code) {
            var tab = $('.t-tabs__nav-wrap').find('div[len-code=' + code + ']');
            if (tab.length > 0) {
                return tab.hasClass('t-is-active');
            }
            return false;
        },

        getData: function () {

        }
    };

    var tabs = {
        //下方已经在类加载时，主动调用，无需再次调用
        /**
         * 初始化
         * @param homeOption home按钮的option
         * @param tabClickCallback tab点击回调
         * @param closeCallback tab关闭回调
         */
        init: function (homeOption, tabClickCallback, closeCallback) {
            _tabs.homeOption = homeOption;
            _tabs._init(tabClickCallback, closeCallback);
        },
        /**
         * 根据code获取一个tab jquery对象
         * @param code
         */
        getByCode: function (code) {
            return _tabs.getByCode(code)
        },

        getByIndex: function (index) {
            return _tabs.getByIndex(index)
        },
        //获取当前选中的tab
        getCurrent: function () {
            return _tabs.getCurrent();
        },
        /**
         *添加一个tab
         * @param data 数据
         * @param isActive 是否激活
         * @param callback 回调
         */
        add: function (data, isActive, callback) {
            if (tabs.existsByIndex(data.code)) {
                tabs.changeByCode(data.code);
                return;
            }
            if (typeof isActive === 'undefined') {
                isActive = true;
            }

            var result = _tabs._addTabs(null, data, isActive);
            if (callback && typeof callback === 'function') {
                /**
                 * 参数1：当前tab下标和对象
                 * 参数2：传入的data数据
                 */
                callback(result, data);
            }
        },

        //根据code 删除一个tab
        remove: function (code) {
            _tabs._removeByCode(code);
        },
        //删除所有tab
        removeAll: function () {
            _tabs.removeAll();
        },
        //插入一个tab
        insertTab: function (index, data, isActive, callback) {
            if (typeof isActive === 'undefined') {
                isActive = true;
            }

            var result = _tabs._addTabs(index, data, isActive);
            if (callback && typeof callback === 'function') {
                /**
                 * 参数1：当前tab下标和对象
                 * 参数2：传入的data数据
                 */
                callback(result, data);
            }
        },

        /**
         * 根据编码选中设置一个tab
         * @param code
         */
        changeByCode: function (code) {
            _tabs.changeByCode(code);
        },
        /**
         * 根据下标选中设置一个tab
         * @param index
         */
        changeByIndex: function (index) {
            _tabs.changeByIndex(index);
        },
        /**
         * 根据 code 判断tab是否存在
         * @param code
         */
        existsByCode: function (code) {
            return _tabs.existsByCode(code);
        },
        /**
         * 根据下标 判断tab是否存在
         * @param index
         */
        existsByIndex: function (index) {
            return _tabs.existsByIndex(index);
        },

        /**
         * 根据下标判断是否为显示状态
         * @param index
         */
        isActiveByIndex: function (index) {
            return _tabs.isActiveByIndex(index);
        },

        /**
         * 选择home
         * @param callback
         */
        selectHome: function (callback) {
            _tabs.home(callback);
        }

    };
    lenosp.tabs = tabs;
})(jQuery, lenosp);