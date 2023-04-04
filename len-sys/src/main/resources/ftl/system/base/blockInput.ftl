<#--单行文本框-->

<#--description
-->

<#if type=="">
    <#assign type='text'>
</#if>

<div>
    <label for="${for}" class="layui-form-label">
        ${label}
        <#if require=="true">
            <span class="x-red">*</span>
        </#if>
    </label>
    <div class="layui-input-block">
        <input type="${type}" id="${id}"
               name="${name}" lay-verify="${varify}"
               autocomplete="off"
               class="layui-input"/>
    </div>
</div>

<#assign for=''>
<#assign desc=''>
<#assign id=''>
<#assign name=''>
<#assign varify=''>