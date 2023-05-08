<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>len-脚手架</title>
    <#include "/system/base/head.ftl">

</head>

<body class="kit-theme">
<div class="layui-layout layui-layout-admin kit-layout-admin">
    <div class="layui-header">
        <div class="layui-logo layui-logo1">
            <span>len-脚手架</span>
        </div>
        <div class="layui-logo layui-logo2 kit-logo-mobile">
            <span>len</span>
        </div>
        <div class="layui-hide-xs">
            <ul class="layui-nav layui-layout-left kit-nav kit-header">
                <#-- <li class="layui-nav-item"><a href="javascript:;">会员管理</a></li>
                 <li class="layui-nav-item"><a href="javascript:;">会员管理</a></li>-->
                <li class="layui-nav-item"><a href="javascript:;" kit-target
                                              data-options="{url:'/article/articleList',icon:'&#xe658;',title:'文章管理',id:'966'}">文章管理</a>
                </li>
                <#--<li class="layui-nav-item">
                    <a href="javascript:;">其它系统</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;">邮件管理</a></dd>
                        <dd><a href="javascript:;">消息管理</a></dd>
                        <dd><a href="javascript:;">授权管理</a></dd>
                    </dl>
                </li>-->
            </ul>
        </div>
        <ul class="layui-nav layui-layout-right kit-nav">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <i class="layui-icon">&#xe63f;</i> 皮肤
                </a>
                <dl class="layui-nav-child skin">
                    <dd><a href="javascript:;" data-skin="default" style="color:#393D49;"><i
                                    class="layui-icon">&#xe658;</i> 默认</a></dd>
                    <dd><a href="javascript:;" data-skin="orange" style="color:#ff6700;"><i
                                    class="layui-icon">&#xe658;</i> 橙</a></dd>
                    <dd><a href="javascript:;" data-skin="green" style="color:#00a65a;"><i
                                    class="layui-icon">&#xe658;</i> 绿</a></dd>
                    <dd><a href="javascript:;" data-skin="pink" style="color:#FA6086;"><i
                                    class="layui-icon">&#xe658;</i> 粉</a></dd>
                    <dd><a href="javascript:;" data-skin="blue" style="color:#00c0ef;"><i
                                    class="layui-icon">&#xe658;</i> 蓝</a></dd>
                    <dd><a href="javascript:;" data-skin="red" style="color:#dd4b39;"><i class="layui-icon">&#xe658;</i>
                            红</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;" layadmin-event="fullscreen">
                    <i class="layui-icon layui-icon-screen-full"></i>
                </a>
            </li>
            <li class="layui-nav-item">
                <a href="javascript:;">
                    <#assign currentUser = Session["currentPrincipal"]>
                    <img src="${re.contextPath}/images/${currentUser.photo}"
                         class="layui-nav-img">${currentUser.username}
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;" kit-target
                           data-options="{url:'/person',icon:'&#xe658;',title:'基本资料',id:'966'}"><span>基本资料</span></a>
                    </dd>
                    <dd><a href="javascript:;">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item">
                <a href="logout"><i class="fa fa-sign-out" aria-hidden="true"></i></a></li>
        </ul>
    </div>

    <#--菜单-->
    <#macro tree data start end>
        <#if (start=="start")>
            <div class="layui-side layui-nav-tree layui-bg-black kit-side">
            <div class="layui-side-scroll">
            <div class="kit-side-fold"><i class="fa fa-navicon" aria-hidden="true"></i></div>
            <ul class="layui-nav layui-nav-tree" lay-filter="kitNavbar" kit-navbar>
        </#if>
        <#list data as child>
            <#if child.children?size gt 0>
                <li class="layui-nav-item">
                    <a href="javascript:;" kit-target-parsent>
                        <i aria-hidden="true" class="layui-icon">${child.icon}</i>
                        <span> ${child.name}</span>
                    </a>
                    <ul class="layui-nav-child">
                        <@tree data=child.children start="" end=""/>
                    </ul>
                </li>
            <#else>
                <dd class="len-main-menu">
                    <a draggable="false" href="#${child.router}" kit-target len-id="${child.code}"
                       data-options="{url:'${child.url}',icon:'${child.icon}',title:'${child.name}',id:'${child.code}'}">
                        <i class="layui-icon">${child.icon}</i><span> ${child.name}</span></a>
                </dd>
            </#if>
        </#list>
        <#if (end=="end")>
            </ul>
            </div>
            </div>
        </#if>
    </#macro>

    <@tree data=menu start="start" end="end"/>


    <div class="layui-body" id="container">

        <!-- 内容主体区域 -->
        <div style="padding: 15px;"><i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop">&#xe63e;</i>
            loading...
        </div>

    </div>


</div>


<script src="${re.contextPath}/plugin/layui/layui.js"></script>
<script src="${re.contextPath}/plugin/tools/tool.js" charset="utf-8"></script>
<script src="${re.contextPath}/plugin/plugins/sortable/Sortable.js"></script>

<script type="application/javascript">
    Len.menuArr = ${Session["menuArr"]};
    var nestedSortables = document.getElementsByClassName('len-main-menu');

    for (var i = 0; i < nestedSortables.length; i++) {
        new Sortable(nestedSortables[i], {
            group: {
                name: 'menu',
                pull: 'clone',
                put: false,
            },
            swapThreshold: 0.31,
            animation: 150
        });
    }
</script>
<script src="${re.contextPath}/plugin/tools/main.js"></script>
<script src="${re.contextPath}/plugin/build/js/admin.js"></script>
</body>
</html>
