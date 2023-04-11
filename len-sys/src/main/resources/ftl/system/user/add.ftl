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
                for="repass"
                label="确认密码"
                id="repass"
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


<script>

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
				if (/[\u4e00-\u9fa5]/.test(value)) { // 正则表达式检测非中文字符
					return "用户名不能为中文";
				}
				if (!/(.+){3,12}$/.test(value)) {
					return "用户名必须3到12位";
				}

			}
			, password: [/(.+){6,12}$/, '密码必须6到12位']
			, repass: function (value) {
				if ($('#password').val() !== $('#repass').val()) {
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
				var imgObj = $('#showImage');
				imgObj.find('img').remove();
				obj.preview(function (index, file, result) {
					imgObj.append('<img src="' + result + '" alt="' + file.name + '" width="100px" height="100px" class="layui-upload-img layui-circle">');
				});
			}, done: function (res) {
				$("#photo").val(res.msg);
			}
		});

		/**
		 * 关闭
		 */
		$('#close').click(function () {
			parent.layer.close(parent.layer.getFrameIndex(window.name));
		});

		/**
		 * 保存
		 */
		form.on('submit(add)', function (data) {
			var r = document.getElementsByName("role");
			const role = Array.from(document.getElementsByName("role"))
				.filter(item => item.checked).map(item => item.value)
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
