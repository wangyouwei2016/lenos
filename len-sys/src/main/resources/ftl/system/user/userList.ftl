<#--<#include "/system/base/head.ftl">-->

<#--搜索-->
<div class="len-head">
    <div class="lenos-search" style="background-color: #F8F8F8">
        <div class="search-select">
            <@lenInclude path="/system/base/queryBox.ftl" name="用户名" id="uname" ></@lenInclude>
            <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
            <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
            <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
            <span id="userFilter" style="display: none">
                <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
                <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
                <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
                <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
                <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
                <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
                <@lenInclude path="/system/base/queryBox.ftl" name="邮箱" id="email" ></@lenInclude>
            </span>

        </div>

        <#--收起 展开 begin-->
        <@lenInclude path="/system/base/zoom.ftl" hiddenId="userFilter"></@lenInclude>
        <#--收起 展开 end-->

    </div>

    <#--按钮-->
    <div class="layui-col-md12 len-button"
         style="height: auto;background-color: #f8f8f8;padding: 8px 8px;display: flex">
        <div class="layui-btn-group" style="width: 100%;flex-wrap: wrap">
            <@lenInclude path="/system/base/btn.ftl" hasPermission="user:select"
            type="add" name="新增" icon="&#xe608;"></@lenInclude>
            <@lenInclude path="/system/base/btn.ftl" hasPermission="user:select"
            type="update" name="编辑" icon="&#xe642;"></@lenInclude>
            <@lenInclude path="/system/base/btn.ftl" hasPermission="user:del"
            type="detail" name="查看" icon="&#xe605;"></@lenInclude>
            <@lenInclude path="/system/base/btn.ftl" hasPrmission="user:repass"
            type="changePwd" name="修改密码" icon="&#xe605;"></@lenInclude>

            <#include "/system/base/searth.ftl">
        </div>

    </div>
</div>


<div id="myGrid" style="width: 100%;height: 500px;" class="ag-theme-alpine"></div>

<script>
    //定义表格列
    var columnDefs = [
        { headerName: '姓名', field: 'name','pinned': 'left' },
        { headerName: '性别', field: 'sex' },
        { headerName: '年龄', field: 'age' },
    ];


    //与列对应的数据; 属性名对应上面的field
    var data = [
        { name: 'itxst.com', sex: '男', age: '100', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市古墩路1号' },
        { name: '李四', sex: '女', age: '5', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '女', age: '20', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市古墩路31号' },
        { name: '王五', sex: '女', age: '26', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市古墩路111号' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' },
        { name: '王五', sex: '男', age: '35', 'jg': '中国', 'sf': '浙江', 'dz': '杭州市文一西路' }
    ];

    //将列和数据赋给gridOptions
    var gridOptions = {
        columnDefs: columnDefs,
        // rowData: data,
        onGridReady: function () {
            //表格创建完成后执行的事件
            gridOptions.api.sizeColumnsToFit();//调整表格大小自适应
        },
        defaultColDef: {
            editable: true,//单元表格是否可编辑
            enableRowGroup: true,
            enablePivot: true,
            enableValue: true,
            sortable: true, //开启排序
            resizable: true,//是否可以调整列大小，就是拖动改变列大小
            filter: true  //开启刷选
        },
        pagination: true,  //开启分页（前端分页）
        paginationAutoPageSize: true, //根据网页高度自动分页（前端分页）

    };
    //在dom加载完成后 初始化agGrid完成
    var eGridDiv = document.querySelector('#myGrid');
    new agGrid.Grid(eGridDiv, gridOptions);
    gridOptions.api.setRowData(data);
</script>


<script>
    layui.use('table', function () {
        var $ = layui.$, active = {
            data: function () {
                return table.checkStatus('userList').data;
            },
            /*查询*/
            select: function () {
                table.reload('userList', {
                    where: {
                        username: $('#uname').val(),
                        email: $('#email').val()
                    }
                });
            },
            /*重置*/
            reload: function () {
                $('#uname').val('');
                $('#email').val('');
                table.reload('userList', {
                    where: {
                        username: null,
                        email: null
                    }
                });
            },
            /*添加*/
            add: function () {
                Len.add('/user/showAddUser', '添加用户');
            },
            /*更新*/
            update: function () {
                var data = active.data();
                Len.onlyOne(data.length) ? Len.update('/user/updateUser?id=' + data[0].id, '编辑用户',) : false;
            },
            /*详情*/
            detail: function () {
                var data = active.data();
                Len.onlyOne(data.length) ? Len.detail('/user/updateUser?id=' + data[0].id, '查看用户信息',) : false;
            },
            /*重置密码*/
            changePwd: function () {
                var data = active.data();
                Len.onlyOne(data.length) ? Len.popup('/user/goRePass?id=' + data[0].id, '修改密码', 500, 350) : false;
            }
        };

        Len.btnBind(active);
        Len.keydown(active);

    });
</script>

