/**
 * 仪表盘面板功能
 */
layui.config({
    base: 'plugin/build/js/',
    version: '1.0.1'
}).define(['layer'], function (exports) {
    var $ = layui.jquery,
        layer = layui.layer;


    var dashboard = {

        init: function () {
            _dashboard.initShortcut();
            _dashboard.initDynamic();
            _dashboard.initBacklog();
            dashboard.dragPanel();
        },


        /**
         * 面板可拖拽
         */
        dragPanel: function () {
            var dragdropPanel = document.getElementsByClassName('dragdropPanel');
            for (var i = 0; i < dragdropPanel.length; i++) {
                Sortable.create(dragdropPanel[i], {
                    group: 'dragdropPanel',
                    swapThreshold: 0.31,
                    animation: 150
                });
            }
        },


        /**
         * 创建一个 快捷方式
         * @param id 快捷菜单id
         * @param code 菜单code
         * @param name name
         * @param icon 图标
         */
        createShortCuts: function (id, code, name, icon) {
            var shortCuts =
                "<li class=\"layui-col-xs3 len-main-menu\">" +
                "   <a  len-shortcut-id = \"" + id + "\" len-shortcut-code=\"" + code + "\">" +
                "      <i class=\"layui-icon \">" + icon + "</i>" +
                "      <cite>" + name + "</cite>" +
                "    </a>" +
                "    <i len-shortcut-code=\"" + code + "\" class=\"admin-shortcut-remove\" title=\"删除快捷菜单\">" +
                "      <i class=\"layui-icon layui-icon-close\"></i>" +
                "    </i>" +
                " </li>";
            return $(shortCuts);
        },

        /**
         * 绑定单个 快捷菜单
         */
        singleShortcutBind: function ($that) {
            _dashboard.singleShortcutBind($that);
            _dashboard.delSingleShortcutBind($that);
        },

        /**
         * 根据菜单编码删除快捷方式
         * @param code
         * @param callback 回调
         */
        removeShortcuts: function (code, callback) {
            $.post('dashboard/shortCuts/del/' + code, {}, function (resp, status) {
                if (callback) callback(resp);
            });
        }
    };

    /**
     * 私有
     * @type {{init: init}}
     * @private
     */
    var _dashboard = {


        /**
         * 快捷菜单
         */
        initShortcut: function () {
            //渲染
            $.get('dashboard/shortCuts', {}, function (resp, status) {
                var data = resp.data;
                var shortCutsBody = $('#admin-shortcut-content');
                data.forEach(function (shortCut) {
                    var $shortCut = dashboard.createShortCuts(shortCut.id, shortCut.code, shortCut.name, shortCut.icon);
                    shortCutsBody.append($shortCut);
                });
                _dashboard.dragShortCuts();
                _dashboard.shortcutBind();
            });
        },

        /**
         * 快捷方式拖拽
         */
        dragShortCuts: function () {
            new Sortable(document.getElementById('admin-shortcut-content'), {
                group: {
                    name: 'shortCutsGroup',
                    pull: false,
                    put: ['menu']
                },
                swapThreshold: 0.31,
                animation: 150,
                fallbackOnBody: true,
                // 元素从另一个列表拖放到列表
                onAdd: function ( /**Event*/ evt) {
                    var item = evt.item;
                    var sBody = $(item).parent();
                    var cId = $(item).attr('len-id');
                    item.parentNode.removeChild(item);
                    var children = sBody.children();
                    if (children.length > 8) {
                        layer.msg('快捷方式最多8个！', {icon: 5});
                        return false;
                    }

                    var exists = false;
                    $.each(children, function (index, el) {
                        var id = $(el).find('a').attr('len-shortcut-code');
                        if (cId === id) {
                            exists = true;
                            return false;
                        }
                    });
                    if (exists) {
                        layer.msg('存在重复快捷方式！', {icon: 5});
                        return false;
                    }
                    //保存快捷菜单
                    $.post('dashboard/shortCuts/add', {
                        code: cId
                    }, function (resp, textStatus) {
                        var name = resp.data.name,
                            icon = resp.data.icon;
                        var shortCuts = dashboard.createShortCuts(cId, name, icon);
                        //添加快捷
                        sBody.append(shortCuts);
                        //绑定事件
                        _dashboard.singleShortcutBind(shortCuts.find('a'));
                        _dashboard.delSingleShortcutBind(shortCuts.find('i[class=admin-shortcut-remove]'));
                    });
                },

                // 改变了列表中的排序
                onUpdate: function ( /**Event*/ evt) {
                    var item = evt.item;
                    var sBody = $(item).parent();
                    var children = sBody.children();
                    var shortCutsIds = [];
                    $.each(children, function (index, el) {
                        shortCutsIds.push($(el).find('a').attr('len-shortcut-id'));
                    });

                    //重新排序保存
                    $.post('dashboard/shortCuts/sort', {
                        shortCutsIds: shortCutsIds.join()
                    }, function (resp, textStatus) {
                    });


                },


            });
        },


        /**
         * 快捷菜单绑定事件
         */
        shortcutBind: function () {
            var shortcutsBody = $('div.admin-shortcut-content');
            shortcutsBody.find('a[len-shortcut-code]').each(function () {
                var $that = $(this);
                _dashboard.singleShortcutBind($that);
            });
            //删除事件
            shortcutsBody.find('i[class=admin-shortcut-remove]').each(function () {
                var $that = $(this);
                _dashboard.delSingleShortcutBind($that);
            });
        },

        singleShortcutBind: function ($that) {
            $that.off('click').on('click', function () {
                var menuId = $that.attr('len-shortcut-code');
                var menu = $('div.layui-nav-tree').find('a[len-id=' + menuId + ']');
                if (menu.length > 0) {
                    var cMenu = menu[0];
                    cMenu.click();
                }
            });
        },

        delSingleShortcutBind: function ($that) {
            $that.off('click').on('click', function () {
                layer.confirm('确定删除快捷菜单？', function () {
                    var code = $that.attr('len-shortcut-code');
                    dashboard.removeShortcuts(code, function (resp) {
                        //删除当前快捷方式
                        $that.parent().remove();
                        layer.close(layer.index);
                    });
                });
            });
        },

        /**
         * 动态
         */

        initDynamic: function () {

        },

        /**
         * 待办事项
         */

        initBacklog: function () {

        }

    };

    exports('dashboard', dashboard);
});