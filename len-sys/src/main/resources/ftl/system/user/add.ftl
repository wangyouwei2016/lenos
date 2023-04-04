<#include "/system/base/formHead.ftl">

<body>
<div class="x-body">
    <form id="useradd" class="layui-form layui-form-pane" style="padding-left: 20px">
        <div>
            <#--头像-->
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">头像上传</legend>
                </fieldset>

                <#--上传组件-->
                <@lenInclude path="/system/base/upload.ftl" id="uploadFile"></@lenInclude>
                <input id="photo" name="photo" type="hidden">

                <div class="layui-input-inline">
                    <div id="showImage" style="margin-top: 20px;margin-left: 50px">
                        <img src="${re.contextPath}/plugin/x-admin/images/bg.png" width="100px" height="100px"
                             class="layui-upload-img layui-circle">
                    </div>
                </div>
            </div>

            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">基础信息</legend>
                </fieldset>
            </div>

            <#--用户名-->
            <div class="layui-form-item">
                <label for="uname" class="layui-form-label">
                    用户名<span class="x-red">*</span>
                </label>
                <div class="layui-input-inline">
                    <input type="text" id="uname" name="username"
                           autocomplete="off" class="layui-input" lay-verify="username">
                </div>
                <div id="ms" class="layui-form-mid layui-word-aux">
                    <span class="x-red">*</span><span id="ums">将会成为您唯一的登入名</span>
                </div>
            </div>


            <div class="layui-form-item">

                <#--真实姓名-->
                <@lenInclude  path="/system/base/lineInput.ftl"
                for="realName"
                label="真实姓名"
                id="realName"
                name="realName"/>

                <#--年龄-->
                <@lenInclude  path="/system/base/lineInput.ftl"
                for="age"
                label="年龄"
                id="age"
                name="age"/>
            </div>


            <div class="layui-form-item">
                <#--密码-->
                <@lenInclude  path="/system/base/lineInput.ftl"
                for="password"
                label="密码"
                id="password"
                name="password"
                require="true"
                type="password"
                verify="password"/>

                <#--确认密码-->
                <@lenInclude  path="/system/base/lineInput.ftl"
                for="L_repass"
                label="确认密码"
                id="l_repass"
                name="repass"
                require="true"
                type="password"
                verify="repass"/>
            </div>

            <#--邮箱-->
            <#-- <@lenInclude  path="/system/base/blockInput.ftl"
             for="email"
             label="邮箱"
             id="email"
             name="email"
             require="true"
             verify="email"/>-->

            <#--邮箱-->
            <div>
                <label for="email" class="layui-form-label">
                    <span class="x-red"></span>邮箱
                </label>
                <div class="layui-input-block">
                    <input type="email" id="email" style="width: 93%" name="email"
                           autocomplete="off" class="layui-input">

                </div>
            </div>

            <#--角色-->
            <div class="layui-form-item">
                <label class="layui-form-label">角色选择</label>
                <div class="layui-input-block">
                    <#list boxJson as json>
                        <input type="checkbox" name="role" title="${json.name}" lay-filter="check" value="${json.id}">
                    </#list>
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <@lenInclude path="system/base/formBtn.ftl"></@lenInclude>
    </form>

</div>

<script type="text/javascript" src="/plugin/validator/validator.min.js"></script>

<script>

    var flag, msg;

    $(function () {
        /**
         * 用户名重复校验
         */
        $('#uname').on("blur", function () {
            var uname = $('#uname').val();
            if (uname.match(/[\u4e00-\u9fa5]/)) {
                return;
            }
            if (!/(.+){3,12}$/.test(uname)) {
                return;
            }
            if (!Len.isEmpty(uname)) {
                $.ajax({
                    url: 'checkUser?uname=' + uname, async: false, type: 'get', success: function (data) {
                        flag = data.flag;
                        $('#ms').find('span').remove();
                        if (!data.flag) {
                            msg = data.msg;
                            $('#ms').append("<span style='color: red;'>" + data.msg + "</span>");
                        } else {
                            flag = true;
                            $('#ms').append("<span style='color: green;'>用户名可用</span>");
                        }
                    }, beforeSend: function () {
                        $('#ms').find('span').remove();
                        $('#ms').append("<span>验证ing</span>");
                    }
                });
            }
        });

    });

    layui.use(['form', 'layer', 'upload'], function () {
        $ = layui.jquery;
        var form = layui.form;

        /**
         * 校验
         */
        form.verify({
            username: function (value) {
                if (value.trim() === "") {
                    return "用户名不能为空";
                }
                if (value.match(/[\u4e00-\u9fa5]/)) {
                    return "用户名不能为中文";
                }
                if (!/(.+){3,12}$/.test(value)) {
                    return "用户名必须3到12位";
                }
                if (typeof (flag) == 'undefined') {
                    return "用户名验证ing";
                }
                if (!flag) {
                    return msg;
                }
            }
            , password: [/(.+){6,12}$/, '密码必须6到12位']
            , repass: function (value) {
                if ($('#password').val() !== $('#l_repass').val()) {
                    return '两次密码不一致';
                }
            }
            , email: function (value) {
                if (value !== "") {
                    if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(value)) {
                        return "邮箱格式不正确";
                    }
                }
            }
        });

        /**
         * 头像上传
         */
        $('#uploadFile').uploadFile({
            before: function (obj) {
                obj.preview(function (index, file, result) {
                    var imgObj = $('#showImage');
                    imgObj.find('img').remove();
                    imgObj.append('<img src="' + result + '" alt="' + file.name + '" width="100px" height="100px" class="layui-upload-img layui-circle">');
                });
            }, done: function (res) {
                $("#photo").val(res.msg);
            }
        });

        /**
         * 关闭当前弹框
         */
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        });

        /**
         * 表单保存
         */
        form.on('submit(add)', function (data) {
            var r = document.getElementsByName("role");
            var role = [];
            for (var i = 0; i < r.length; i++) {
                if (r[i].checked) {
                    role.push(r[i].value);
                }
            }
            if (role.length === 0) {
                Len.error('请选择角色');
                return false;
            }
            data.field.role = role;
            Len.layerAjax('addUser', data.field, 'userList');
            return false;
        });

        form.render();
    });
</script>
</body>
