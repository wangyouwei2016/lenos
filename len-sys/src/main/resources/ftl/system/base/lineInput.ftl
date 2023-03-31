<#--单列文本框-->

<#--description
-->

<#if type=="">
    <#assign type='text'>
</#if>

<div class="layui-inline">
    <label for="${for}" class="layui-form-label">
        ${label}
        <#if require=="true">
            <span class="x-red">*</span>
        </#if>
    </label>
    <div class="layui-input-inline">
        <input type="${type}"
               id="${id}"
               name="${name}"
               lay-verify="${verify}"
               value="${value}"
               autocomplete="off"
               class="layui-input"/>
    </div>
</div>

<#assign for=''>
<#assign desc=''>
<#assign id=''>
<#assign name=''>
<#assign varify=''>