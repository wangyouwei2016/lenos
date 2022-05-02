<style>
    body {
        background-color: #f8f8f8
    }
</style>
<div class="layui-bg-gray" style="padding: 10px;">
    <div class="layui-row layui-col-space15">
        <#--动态-->
        <div class="layui-col-md8 dragdropPanel">
            <div class="layui-card admin-dynamic">
                <div class="layui-card-header">
                    动态
                </div>
                <div class="layui-card-body admin-dynamic-content" style="height: 540px">
                    <ul class="layui-row layui-col-space10 layui-this">
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                        <li class="layui-col-xs12">
                            <a href="javascript:;" class="admin-dynamic-body">
                                <h3>系统</h3>
                                <p><cite style="color: #858585;">张三的请假待审批</cite></p>
                            </a>
                            <i class="admin-dynamic-remove" title="删除动态">
                                <i class="layui-icon layui-icon-close"></i>
                            </i>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <#--快捷菜单-->
        <div class="layui-col-md4 dragdropPanel">
            <div class="layui-card  admin-shortcut">
                <div class="layui-card-header" title="可从左侧菜单拖拽">
                    快捷菜单
                </div>
                <div class="layui-card-body admin-shortcut-content" style="height: 230px">
                    <div carousel-item="">
                        <ul id="admin-shortcut-content" class="layui-row layui-col-space10 layui-this">

                        </ul>

                    </div>
                </div>
            </div>
        </div>
        <#--代办-->
        <div class="layui-col-md4">
            <div class="dragdropPanel">
                <div class="layui-card admin-backlog">
                    <div class="layui-card-header">
                        代办事项
                    </div>
                    <div class="layui-card-body" style="height: 230px">
                        <ul class="layui-row layui-col-space10 layui-this">
                            <li class="layui-col-xs6">
                                <a href="javascript:;" class="admin-backlog-body">
                                    <h3>待审批</h3>
                                    <p><cite style="color: #FF5722;">5</cite></p>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="${re.contextPath}/plugin/build/js/dashboard.js" charset="utf-8"></script>

<script type="application/javascript">
    layui.use('dashboard', function () {
        var $ = layui.jquery,
            dashboard = layui.dashboard;

    });


</script>