<#include "/system/base/formHead.ftl">
<script type="text/javascript" src="${re.contextPath}/plugin/tools/update-setting.js"></script>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div style="width:100%;min-height:400px;overflow: auto;">

            <#--头像-->
            <div class="layui-form-item">
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
                    <legend style="font-size:16px;">头像上传</legend>
                </fieldset>

                <#--上传组件-->
                <@lenInclude path="/system/base/upload.ftl" id="uploadFile"></@lenInclude>

                <div class="layui-input-inline">
                    <div id="showImage" style="margin-top: 20px;margin-left: 50px">
                        <img src="/images/${re.contextPath}/${user.photo}" width="100px" height="100px"
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
                    <span class="x-red">*</span>用户名
                </label>
                <div class="layui-input-inline">
                    <input value="${user.id}" type="hidden" name="id">
                    <input type="text" id="uname" value="${user.username}" readonly lay-verify="username"
                           autocomplete="off" class="layui-input">
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
                value="${user.realName}"
                name="realName"/>

                <#--年龄-->
                <@lenInclude  path="/system/base/lineInput.ftl"
                for="age"
                label="年龄"
                id="age"
                value="${user.age}"
                name="age"/>
            </div>

            <#--邮箱-->
            <div class="layui-form-item">
                <label for="email" class="layui-form-label">
                    <span class="x-red"></span>邮箱
                </label>
                <div class="layui-input-block">
                    <input type="email" id="email" value="${user.email}" style="width: 93%" name="email"
                           lay-verify="email"
                           autocomplete="off" class="layui-input">
                    <input id="photo" value="${user.photo}" name="photo" type="hidden">
                </div>
            </div>

            <#--角色-->
            <div class="layui-form-item">
                <label class="layui-form-label">角色选择</label>
                <div class="layui-input-block">
                    <#list boxJson as json>
                        <input type="checkbox" name="role" lay-filter="check" value="${json.id}" title="${json.name}"
                               <#if json.check?string=='true'>checked</#if>>
                    </#list>
                </div>
            </div>
            <div style="height: 60px"></div>
        </div>
        <#if !detail>
            <@lenInclude path="system/base/formBtn.ftl"></@lenInclude>
        </#if>
    </form>
</div>


<script>

	layui.use(['form', 'layer', 'upload'], function () {
		var $ = layui.jquery, form = layui.form;
		$('#uploadFile').uploadFile({
			before: function (obj) {
				var imgObj = $('#showImage');
				imgObj.find('img').remove();
				obj.preview(function (index, file, result) {
					imgObj.append('<img src="' + result + '" alt="' + file.name + '" width="100px" height="100px" class="layui-upload-img layui-circle">');
				});
			},
			done: function (res) {
				$("#photo").val(res.msg);
			}
		});

		/**
		 * 校验
		 */
		form.verify({
			username: function (value) {
				value = value.trim(); // 去除前后空格
				if (value === "") {
					return "用户名不能为空";
				}
				if (/[\u4e00-\u9fa5]/.test(value)) { // 正则表达式检测非中文字符
					return "用户名不能为中文";
				}
				if (!/(.+){3,12}$/.test(value)) {
					return "用户名必须3到12位";
				}
			},
			email: function (value) {
				if (value !== "") {
					if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/.test(value)) { // 邮箱格式校验正则表达式
						return "邮箱格式不正确";
					}
				}
			}
		});

		/**
		 * 关闭
		 */
		$('#close').click(function () {
			parent.layer.close(parent.layer.getFrameIndex(window.name));
		});

		/**
		 * 更新
		 */
		form.on('submit(add)', function (data) {
			data.field.role = Array.from(document.getElementsByName("role"))
				.filter(item => item.checked).map(item => item.value);
			Len.layerAjax('updateUser', data.field, 'userList');
			return false;
		});
		form.render();
	});
</script>
