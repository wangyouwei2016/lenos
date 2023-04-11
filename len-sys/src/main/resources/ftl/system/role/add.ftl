<#include "/system/base/formHead.ftl">

<link rel="stylesheet" href="${re.contextPath}/plugin/ztree/css/metroStyle/metroStyle.css">
<script type="text/javascript" src="${re.contextPath}/plugin/ztree/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/ztree/js/jquery.ztree.excheck.js" charset="utf-8"></script>
<script type="text/javascript">

</script>

<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;min-height:400px;overflow: auto;">
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">角色信息</legend>
                </fieldset>
            </div>
            <div class="layui-form-item">
                <label for="roleName" class="layui-form-label">
                    <span class="x-red">*</span>角色名称
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="roleName" name="roleName" lay-verify="rolename"
                           autocomplete="off" class="layui-input">
                </div>
                <div id="ms" class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span><span id="ums">角色名称必填</span>
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="remark" class="layui-form-label">
                        <span class="x-red">*</span>角色备注
                    </label>
                    <div class="layui-input-inline">
                        <input type="text" id="remark" name="remark" lay-verify="remark" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
            </div>
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">权限</legend>
                </fieldset>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label for="remark" class="layui-form-label">
                        <span class="x-red">*</span>权限选择
                    </label>
                    <div class="layui-input-inline">
                        <input type="hidden" name="menus">
                        <ul id="treeDemo" class="ztree"></ul>
                    </div>
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
            <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
                <button type="submit" class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
                    增加
                </button>
                <button type="button" class="layui-btn layui-btn-primary" id="close">
                    取消
                </button>
            </div>
        </div>
    </form>
</div>
<script>

    /**
     * 初始化tree
     */
    function initTree() {
        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
        };
        var zNodes = ${menus};
        $(document).ready(function () {
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        });
    }

    initTree();


    layui.use(['form', 'layer'], function () {
        var $ = layui.jquery, form = layui.form;

        /**
         * 校验
         */
        form.verify({
            rolename: function (value) {
                if (value.trim() === "") {
                    return "角色名不能为空";
                }
            }
        });

        /**
         * 关闭
         */
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        /**
         * 新增
         */
        form.on('submit(add)', function (data) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var jsonArr = zTree.getCheckedNodes(true);
            data.field.menus = jsonArr.map(function(item) {
                return item.id;
            })
            Len.layerAjax('addRole', data.field, 'roleList');
            return false;
        });
    });
</script>
