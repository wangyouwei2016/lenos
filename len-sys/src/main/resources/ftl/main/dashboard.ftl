<style>
    body {
        background-color: #f8f8f8
    }
</style>
<div class="layui-bg-gray" style="padding: 10px;">
    <#--动态 快捷菜单 代办 body-->
    <div id="panelBody" class="layui-row layui-col-space15">
        <#--主-->
        <div id="panelColumnOne" class="able-col layui-col-md8"></div>
        <#--次-->
        <div id="panelColumnTwo" class="able-col layui-col-md4"></div>
    </div>
</div>

<script type="text/javascript" src="${re.contextPath}/plugin/build/js/dashboard.js" charset="utf-8"></script>

<script type="application/javascript">
    layui.use('dashboard', function () {
        var $ = layui.jquery,
            dashboard = layui.dashboard;

    });


</script>