<#--表单组合按钮-->

<#--description
确定/取消按钮
-->

<#if !detail>
<#--<div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
    <div class="layui-form-item" style=" float: right;margin-right: 30px;margin-top: 8px">
        <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
            确定
        </button>
        <button class="layui-btn layui-btn-primary" id="close">
            取消
        </button>

    </div>
</div>-->
<#-- <div class="layer-footer" style="position: fixed; bottom: 0;background-color:#ddd;width:100%;height:50px" >
     <button type="button" class="layui-btn layui-btn-normal layui-btn-radius">确认</button>
     <button type="button" class="layui-btn layui-btn-primary layui-btn-radius">取消</button>
 </div>-->
    <div class="layer-footer">
        <button class="layui-btn layui-btn-normal" lay-filter="add" lay-submit>
            通过
        </button>
        <button class="layui-btn layui-btn-primary" id="close">
            取消
        </button>
    </div>
</#if>
<#assign detail=''>