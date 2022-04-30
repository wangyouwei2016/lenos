<#--查询文本框-->

<#--description
-->

<div class="search-div">
    <div class="search-div-label">
        <span>${name}</span>
    </div>
    <span class="layui-inline">
        <input class="layui-input" id="${id}" autocomplete="off">
    </span>
</div>

<#--置空变量 防止覆盖后面的引用-->
<#assign name=''>
<#assign id=''>