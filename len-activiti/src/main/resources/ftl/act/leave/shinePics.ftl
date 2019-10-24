<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>流程图</title>
<#include "/system/base/header.ftl">
</head>

<body>
<div class="x-body">
    <form class="layui-form layui-form-pane" style="margin-left: 20px;">
        <div id="image" style="width:100%;height:100%;overflow: auto;">
            <div class="layui-form-item">
                <image id="showImages1" style="display: none;"></image>
                <image id="showImages2"></image>
            </div>
        </div>
    </form>
</div>
<script>
    var countNum = 0;
    // $('#image').css('height',document.body.offsetHeight);
    layui.use(['form', 'layer'], function () {
        $ = layui.jquery;
        //执行AJAX 获取数据
        $.getJSON('${re.contextPath}/leave/getShineProcImage?processInstanceId=${processInstanceId}', function (json) {
            var result = json.images;
            var imgObj1 = document.getElementById("showImages1")
            imgObj1.src = "data:image/png;base64," + result[0];
            var imgObj2 = document.getElementById("showImages2")
            imgObj2.src = "data:image/png;base64," + result[1];
//          $("#showImages1").show();
            window.setInterval(function () {
                //获取网页中id=myImg的图片对象元素
//              var imgObj = document.getElementById("showImages")
//              imgObj.src = "data:image/png;base64,"+result[countNum] ;
                if (countNum == 0) {
                    $("#showImages1").show();
                    $("#showImages2").hide();
                } else {
                    $("#showImages1").hide();
                    $("#showImages2").show();
                }
                countNum++;
                if (countNum == 2) {
                    countNum = 0;//回到了原点
                }
            }, 1000);
        });

    });
</script>
</body>

</html>
