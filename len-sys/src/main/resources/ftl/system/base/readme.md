# 封装组件介绍

### 说明

以下为已封装组件，可直接引用使用，您也可以按需自定义封装。

* 文本框 queryBox.ftl  
  可定义文本框名称，id。    
  使用，在列表中添加名称为: 用户名的搜索文本框：

 ~~~~
<@lenInclude path="/system/base/queryBox.ftl" name="用户名" id="uname" ></@lenInclude>
 ~~~~

* 表单确定取消组合按钮 formBtn.ftl    
  使用:

 ~~~~
<@lenInclude path="system/base/formBtn.ftl"></@lenInclude>
~~~~

* 查询重置组合按钮 searth.ftl   
  使用:

~~~~
<#include "/system/base/searth.ftl">
~~~~

* 上传按钮 upload.ftl
  使用:

~~~~
<@lenInclude path="/system/base/upload.ftl" id="uploadFile"></@lenInclude>
~~~~
