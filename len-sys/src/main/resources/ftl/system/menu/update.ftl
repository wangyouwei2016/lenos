<#include "/system/base/formHead.ftl">

<#--body-->
<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;height:500px;overflow: auto;">
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>
            <div style="margin-left:25%">
                <div class="layui-form-item">
                    <label for="menuType" class="layui-form-label">
                        <span class="x-red">*</span>类型
                    </label>
                    <div class="layui-input-block" style="width:190px;">
                        <select disabled id="menuType" lay-verify="menuType" lay-filter="menuType">
                            <option value=""></option>
                            <option <#if (sysMenu.PId)??==null>selected</#if> value="2">一级菜单</option>
                            <option <#if sysMenu.menuType='0'&&(sysMenu.PId)??>selected</#if> value="0">
                                二级菜单
                            </option>
                            <option <#if sysMenu.menuType=='1'>selected</#if> value="1">按钮</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item" id="pDiv">
                    <label for="pName" class="layui-form-label">
                        父级菜单
                    </label>
                    <div class="layui-input-inline">
                        <#-- <input type="hidden"  id="pId" value="${sysMenu.PId}">-->
                        <#--保留 但不做更新-->
                        <input type="text" id="pName" disabled value="${pName}" onclick="showTree();" lay-verify="pName"
                               class="layui-input">
                    </div>
                    <div id="treeNode" style="display:none; position: absolute;z-index:1000;background-color: white;">
                        <div id="tree"></div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="name" class="layui-form-label">
                        <span class="x-red">*</span>名称
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" value="${sysMenu.name}" id="name" name="name" lay-verify="name"
                               autocomplete="off" class="layui-input">
                    </div>
                    <div id="ms" class="layui-form-mid layui-word-aux">
                        <span class="x-red">*</span><span id="ums">必须填写</span>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="url" class="layui-form-label">
                        url
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" value="${sysMenu.url}" id="url" name="url" lay-verify="urls"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">

                    <label for="permission" class="layui-form-label">
                        <span class="x-red">*</span>权限
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" value="${sysMenu.permission}" id="permission" name="permission"
                               lay-verify="permission"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="menu-icon" class="layui-form-label">
                        <span class="x-red">*</span>图标
                    </label>
                    <div class="layui-input-inline">
                        <div style="margin-left: 20px;margin-top:5px">
                            <ul>
                                <li style="display: inline-block;width: 50px;">
                                    <i id="menu-icon" class="layui-icon" style="font-size: 25px;">${sysMenu.icon}</i>
                                </li>
                                <li style="display: inline-block;"><i class="layui-btn layui-btn-primary layui-btn-sm"
                                                                      id="select_icon">选择图标</i></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label for="orderNum" class="layui-form-label">
                        <span class="x-red">*</span>序号
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="orderNum" value="${sysMenu.orderNum}" name="orderNum"
                               lay-verify="orderNum"
                               autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div style="height: 60px"></div>
            </div>
        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
                <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
                    更新
                </button>
                <button class="layui-btn layui-btn-primary" id="close">
                    取消
                </button>
            </div>
        </div>
    </form>
</div>

<#--js-->
<script>
    layui.use(['form', 'layer', 'tree'], function () {
        var$ = layui.jquery, form = layui.form, layer = layui.layer, tree = layui.tree;

        var menus = ${menus};

        /**
         * 过滤掉按钮和兼容
         * @param menus
         */
        var convert = function (menus) {
            menus.forEach(function (v) {
                v.title = v.name;
                if (v.children.length > 0) {
                    var isButton = v.children[0].menuType === 1;
                    if (isButton) {
                        v.children = [];
                    } else {
                        convert(v.children); //递归调用 convert 函数处理子菜单
                    }
                }
            });
        };
        convert(menus);

        tree.render({
            elem: '#tree',
            nodes: menus
            , click: function (node) {
                var data = node.data;
                $('#pId').val(data.id);
                $('#pName').val(data.name);
            }
        });

        $('#select_icon').click(function () {
            parent.layer.open({
                id: 'icon',
                type: 2,
                area: ['800px', '600px'],
                shade: 0.4,
                zIndex: layer.zIndex,
                title: '图标',
                content: '../plugin/html/icon.html?param.parentname=' + window.name,
            });
        });

        //自定义验证规则
        var type = $('#menuType');
        form.verify({
            menuType: function (v) {
                if (Len.isEmpty(v)) {
                    return '类型不能为空';
                }
            }, pName: function (v) {
                if (type.val() !== '2' && v.trim() === '') {
                    return '父菜单不能为空';
                }
            }, name: function (v) {
                if (Len.isEmpty(v.trim())) {
                    return '名称不能为空';
                }
            }, urls: function (v) {
                if (type.val() === '1') {
                    $('#url').val('');
                }
                if (type.val() === '0' && v.trim() === '') {
                    return 'url不能为空';
                }
            }, permission: function (v) {
                if ((type.val() === '1' || type.val() === '0') && v.trim() === '') {
                    return '权限不能为空';
                }
            }, orderNum: [/^[0-9]*[1-9][0-9]*$/, '请填写序号(正整数)']
        });

        form.on('select(menuType)', function (data) {
            var value = data.value;
            var map = {
                '2': {pId: '', pName: true, permission: true, url: false},
                '1': {pId: null, pName: false, permission: false, url: true},
                '0': {pId: null, pName: false, permission: false, url: false}
            };

            var obj = map[value];
            if (!obj) {
                return;
            }
            $('#pId').val(obj.pId);
            disableOrSet('pName', obj.pName);
            disableOrSet('permission', obj.permission);
            disableOrSet('url', obj.url);
        });

        /**
         * 启用或禁用
         * id :元素id
         * flag true:禁止输入，false 允许输入
         */
        function disableOrSet(id, flag) {
            var $id = $("#" + id);
            if (flag) {
                $id.val('');
                $id.prop('disabled', true).css('background', '#e6e6e6');
            } else {
                $id.prop('disabled', false).css('background', 'white');
            }
        }

        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });
        //监听提交
        form.on('submit(add)', function (data) {
            data.field['icon'] = $('#menu-icon').text();
            data.field['id'] = '${sysMenu.id}';
            Len.layerAjax('updateMenu', data.field, 'treeList');
            return false;
        });
        form.render();
    });

    function showTree() {
        var $p = $('#pName');
        var $treeNode = $('#treeNode');
        var cityOffset = $p.offset();
        var width = $p.css('width');

        $treeNode.css({
            left: cityOffset.left + 'px',
            top: cityOffset.top + $p.outerHeight() + 'px',
            width: width,
            border: '1px solid #e6e6e6'
        }).slideDown('fast');

        $('body').on('mousedown', onBodyDown);
        $treeNode.css('display', 'inline');
    }

    function hideMenu() {
        $('#treeNode').fadeOut('fast');
        $('body').unbind('blur', onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id === 'treeNode' || $(event.target).parents('#treeNode').length > 0)) {
            hideMenu();
        }
    }
</script>
</body>
