/**
 * 仪表盘面板功能
 */
(function ($, lenosp) {

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
                        var code = resp.data.code,
                            name = resp.data.name,
                            icon = resp.data.icon;
                        var shortCuts = dashboard.createShortCuts(cId, code, name, icon);
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


    var dashboard = {

        panelOpt: {},

        init: function () {
            dashboard.showDashboard();
            _dashboard.initShortcut();
            _dashboard.initDynamic();
            _dashboard.initBacklog();
            dashboard.dragPanel();
        },

        /**
         * 根据配置面板渲染面板功能模块
         */
        showDashboard: function () {
            var data = dashboard.getPanelOptData();
            var panelColumnOne = $('#panelColumnOne'),
                panelColumnTwo = $('#panelColumnTwo');
            $.map(data, function (panelOptArr) {
                panelOptArr.forEach(function (panelOpt) {
                    var panelId = panelOpt.id,
                        code = panelOpt.paneloptCode,
                        column = panelOpt.paneloptColumn;
                    dashboard.panelOpt[panelId] = code;

                    var itemBody = dashboard.getPanelItemBody(code, panelId);
                    if (column === 1) {
                        panelColumnOne.append(itemBody);
                    } else {
                        panelColumnTwo.append(itemBody);
                    }
                });
            });
            //不存在赋一个空拖拽元素
            if (panelColumnOne.find('div').length === 0) {
                panelColumnOne.append(dashboard.panelNullDiv());
            }
            if (panelColumnTwo.find('div').length === 0) {
                panelColumnTwo.append(dashboard.panelNullDiv());
            }
        },

        panelNullDiv: function () {
            return "<div panel-item='true' class=\"dragdropPanel\" style='margin-bottom: 15px' >" +
                "</div>";
        },

        /**
         * 更具code 获取功能面板
         * @param code
         * @param id
         * @returns {string}
         */
        getPanelItemBody(code, id) {
            switch (code) {
                case 'DYNAMIC':
                    return dashboard.dynamicBody(id);
                case 'SHORTCUTS':
                    return dashboard.shortCutsBody(id);
                case 'BACKLOG':
                    return dashboard.backlogBody(id);
            }
            return '';
        },

        /**
         * 获取面板配置
         * @returns {string}
         */
        getPanelOptData: function () {
            var data = '';
            $.ajaxSettings.async = false;
            $.get('dashboard/panel/list', {}, function (resp, status) {
                $.ajaxSettings.async = true;
                data = resp.data;
            });
            $.ajaxSettings.async = true;
            return data;
        },
        /**
         * 快捷菜单body
         * @returns {string}
         */
        shortCutsBody: function (id) {
            return "<div panel-item='true'  class=\"dragdropPanel\" style='margin-bottom: 15px' > " +
                " <section panel-id=" + id + " class=\"panel admin-shortcut\" title=\"可从左侧菜单拖拽\">" +
                "        <header class=\"panel-heading\" style='font-weight: bold'>" +
                "            快捷菜单" +
                "        </header>" +
                "        <div class=\"panel-body admin-shortcut-content\" style='height: 230px'>" +
                " <div carousel-item=\"\">"+
                "   <ul id='admin-shortcut-content' class='row col-lg-12'>" +
                    "</ul>" +
                    "</div>" +
                "        </div>" +
                "        </section>" +
                "        </div>"

            /*return "<div panel-item='true'  class=\"dragdropPanel\" style='margin-bottom: 15px' >" +
                "   <div  panel-id=" + id + " class=\"layui-card  admin-shortcut\">\n" +
                "       <div class=\"layui-card-header\" title=\"可从左侧菜单拖拽\">\n" +
                "           快捷菜单\n" +
                "       </div>\n" +
                "       <div class=\"layui-card-body admin-shortcut-content\" style=\"height: 230px\">\n" +
                "           <div carousel-item=\"\">\n" +
                "               <ul id=\"admin-shortcut-content\" class=\"layui-row layui-col-space10 layui-this\">\n" +
                "               </ul>\n" +
                "           </div>\n" +
                "       </div>\n" +
                "    </div>\n" +
                "</div>";*/
        },

        /**
         * 动态面板 body
         * @returns {string}
         */
        dynamicBody: function (id) {
            return "<div panel-item='true' class=\"dragdropPanel\" style='margin-bottom: 15px' >" +
                " <section panel-id=" + id + " class=\"panel admin-dynamic\">" +
                "        <header class=\"panel-heading\" style='font-weight: bold'>" +
                "            动态" +
                "        </header>" +
                "        <div class=\"panel-body admin-dynamic-content\"  style='height: 530px'>" +
                "        </div>" +
                "        </section>" +
                "        </div>"
            /*return "<div panel-item='true' class=\"dragdropPanel\" style='margin-bottom: 15px' >" +
                "     <div panel-id=" + id + " class=\"layui-card admin-dynamic\">\n" +
                "         <div class=\"layui-card-header\">\n" +
                "             动态\n" +
                "         </div>\n" +
                "         <div class=\"layui-card-body admin-dynamic-content\" style=\"height: 540px\">\n" +
                "             <ul class=\"layui-row layui-col-space10 layui-this\">\n" +
                "             </ul>\n" +
                "         </div>\n" +
                "     </div>\n" +
                "</div>";*/
        },

        /**
         * 待办事项 body
         * @returns {string}
         */
        backlogBody: function (id) {
            return "<div panel-item='true' class=\"dragdropPanel\" style='margin-bottom: 15px' >" +
                " <section panel-id=" + id + " class=\"panel admin-backlog\">" +
                "        <header class=\"panel-heading\" style='font-weight: bold'>" +
                "            代办事项" +
                "        </header>" +
                "        <div class=\"panel-body\" style='height: 230px'>" +
                "        </div>" +
                "        </section>" +
                "        </div>"
            /* return "<div panel-item='true' class=\"dragdropPanel\" style='margin-bottom: 15px' >" +
                 "    <div panel-id=" + id + " class=\"layui-card admin-backlog\">\n" +
                 "        <div class=\"layui-card-header\">\n" +
                 "            代办事项\n" +
                 "        </div>\n" +
                 "        <div class=\"layui-card-body\" style=\"height: 230px\">\n" +
                 "            <ul class=\"layui-row layui-col-space10 layui-this\">\n" +
                 "            </ul>\n" +
                 "        </div>\n" +
                 "    </div>\n" +
                 "    </div>";*/
        },


        /**
         * 面板可拖拽
         */
        dragPanel: function () {
            var dragdropPanel = document.getElementsByClassName('dragdropPanel');
            for (var i = 0; i < dragdropPanel.length; i++) {
                new Sortable(dragdropPanel[i], {
                    group: 'dragdrop',
                    swapThreshold: 0.31,
                    animation: 150,
                    onAdd: function ( /**Event*/ evt) {
                        dashboard.savePanelOpt();
                    }
                });
            }
        },

        /**
         * 保存面板配置
         */
        savePanelOpt: function () {
            //保存拖拽配置
            var panelColumnOne = $('#panelColumnOne'),
                panelColumnTwo = $('#panelColumnTwo');
            var opt = [];
            var oneIndexArr = panelColumnOne.find('div[panel-id]'),
                twoIndexArr = panelColumnTwo.find('div[panel-id]');

            $.each(oneIndexArr, function (index, $el) {
                var id = $($el).attr('panel-id');
                var code = dashboard.panelOpt[id];
                opt.push({
                    id: id,
                    paneloptColumn: 1,
                    paneloptIndex: parseInt(index),
                    paneloptCode: code
                })
            });

            $.each(twoIndexArr, function (index, $el) {
                var id = $($el).attr('panel-id');
                var code = dashboard.panelOpt[id];
                opt.push({
                    id: id,
                    paneloptColumn: 2,
                    paneloptIndex: parseInt(index),
                    paneloptCode: code
                })
            });

            $.ajax({
                url: 'dashboard/panel/save',
                headers: {
                    'Content-Type': 'application/json'
                },
                method: 'POST',
                data: JSON.stringify(opt),
                success: function () {
                }
            });
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
                "<li class=\"col-xs-3 col-sm-3 len-main-menu\">" +
                "   <a  len-shortcut-id = \"" + id + "\" len-shortcut-code=\"" + code + "\">" +
                "<i style='font-size: 30px' class=\"len-icon fa fa-tasks\"></i>" +
                "<span>" + name + "</span>"+
                "</a>"+
                /*"   <a  len-shortcut-id = \"" + id + "\" len-shortcut-code=\"" + code + "\">" +
                "      <i class=\"layui-icon \">" + icon + "</i>" +
                "      <cite>" + name + "</cite>" +
                "    </a>" +
                "    <i len-shortcut-code=\"" + code + "\" class=\"admin-shortcut-remove\" title=\"删除快捷菜单\">" +
                "      <i class=\"layui-icon layui-icon-close\"></i>" +
                "    </i>" +*/
                " </li>";
            /*var shortCuts =
                "<li class=\"layui-col-3 len-main-menu\">" +
                "   <a  len-shortcut-id = \"" + id + "\" len-shortcut-code=\"" + code + "\">" +
                "      <i class=\"layui-icon \">" + icon + "</i>" +
                "      <cite>" + name + "</cite>" +
                "    </a>" +
                "    <i len-shortcut-code=\"" + code + "\" class=\"admin-shortcut-remove\" title=\"删除快捷菜单\">" +
                "      <i class=\"layui-icon layui-icon-close\"></i>" +
                "    </i>" +
                " </li>";*/
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


    lenosp.dashboard = dashboard;
})(jQuery, lenosp);