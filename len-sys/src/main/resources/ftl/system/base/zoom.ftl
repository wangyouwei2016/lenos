<#--展开显示层组件-->

<#--description

-->
<div class="search-zoom">
    <div id="userZoomShow" data-type="zoomShow" title="展开全部" class="search-zoom-show">
        <i class="layui-icon layui-icon-down"></i>
    </div>
    <div id="userZoomHidden" data-type="zoomHidden" title="收起" class="search-zoom-hidden">
        <i class="layui-icon layui-icon-up"></i>
    </div>
</div>

<script>
    layui.use('form', function () {
        var $ = layui.$, active = {
            zoomShow: function () {
                $('#${hiddenId},#userZoomHidden').css('display', 'block');
                $('#userZoomShow').css('display', 'none');
            },
            zoomHidden: function () {
                $('#${hiddenId},#userZoomHidden').css('display', 'none');
                $('#userZoomShow').css('display', 'block');
            }
        };
        Len.btnBind(active);
    });
</script>

<#assign hiddenId=''>