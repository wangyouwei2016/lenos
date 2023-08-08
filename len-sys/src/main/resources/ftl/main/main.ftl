<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <link rel="shortcut icon" href="${re.contextPath}/plugin/new/img/favicon.png">

    <title>lenosp</title>

    <!-- Bootstrap core CSS -->
    <link href="${re.contextPath}/plugin/new/css/bootstrap.min.css" rel="stylesheet">
    <link href="${re.contextPath}/plugin/new/css/bootstrap-reset.css" rel="stylesheet">
    <!--external css-->
    <link href="${re.contextPath}/plugin/new/assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="${re.contextPath}/plugin/new/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css" media="screen"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/new/css/owl.carousel.css" type="text/css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/new/admin.css" type="text/css">

    <!--right slidebar-->
    <link href="${re.contextPath}/plugin/new/css/slidebars.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="${re.contextPath}/plugin/new/css/style.css" rel="stylesheet">
    <link href="${re.contextPath}/plugin/new/tabs.css" rel="stylesheet">
    <link href="${re.contextPath}/plugin/new/css/style-responsive.css" rel="stylesheet" />
    <link href="${re.contextPath}/plugin/new/assets/toastr-master/toastr.css" rel="stylesheet" type="text/css" />



    <!-- HTML5 shim and Respond.js IE8 support of HTML5 tooltipss and media queries -->
    <!--[if lt IE 9]>
    <script src="${re.contextPath}/plugin/new/js/html5shiv.js"></script>
    <script src="${re.contextPath}/plugin/new/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

<section id="container">
    <#--头部-->
    <header class="header white-bg">
        <div class="sidebar-toggle-box">
            <div class="fa fa-bars tooltips" data-placement="right" data-original-title="Toggle Navigation"></div>
        </div>
        <!--logo start-->
        <a href="index.html" class="logo">Flat<span>lab</span></a>
        <!--logo end-->
        <div class="nav notify-row" id="top_menu">
            <!--  notification start -->
            <ul class="nav top-menu">
                <!-- settings start -->
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="fa fa-tasks"></i>
                        <span class="badge bg-success">6</span>
                    </a>
                    <ul class="dropdown-menu extended tasks-bar">
                        <div class="notify-arrow notify-arrow-green"></div>
                        <li>
                            <p class="green">You have 6 pending tasks</p>
                        </li>
                        <li>
                            <a href="#">
                                <div class="task-info">
                                    <div class="desc">Dashboard v1.3</div>
                                    <div class="percent">40%</div>
                                </div>
                                <div class="progress progress-striped">
                                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                        <span class="sr-only">40% Complete (success)</span>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="task-info">
                                    <div class="desc">Database Update</div>
                                    <div class="percent">60%</div>
                                </div>
                                <div class="progress progress-striped">
                                    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                        <span class="sr-only">60% Complete (warning)</span>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="task-info">
                                    <div class="desc">Iphone Development</div>
                                    <div class="percent">87%</div>
                                </div>
                                <div class="progress progress-striped">
                                    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 87%">
                                        <span class="sr-only">87% Complete</span>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="task-info">
                                    <div class="desc">Mobile App</div>
                                    <div class="percent">33%</div>
                                </div>
                                <div class="progress progress-striped">
                                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 33%">
                                        <span class="sr-only">33% Complete (danger)</span>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="task-info">
                                    <div class="desc">Dashboard v1.3</div>
                                    <div class="percent">45%</div>
                                </div>
                                <div class="progress progress-striped active">
                                    <div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 45%">
                                        <span class="sr-only">45% Complete</span>
                                    </div>
                                </div>

                            </a>
                        </li>
                        <li class="external">
                            <a href="#">See All Tasks</a>
                        </li>
                    </ul>
                </li>
                <!-- settings end -->
                <!-- inbox dropdown start-->
                <li id="header_inbox_bar" class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="fa fa-envelope-o"></i>
                        <span class="badge bg-important">5</span>
                    </a>
                    <ul class="dropdown-menu extended inbox">
                        <div class="notify-arrow notify-arrow-red"></div>
                        <li>
                            <p class="red">You have 5 new messages</p>
                        </li>
                        <li>
                            <a href="#">
                                <span class="photo"><img alt="avatar" src="${re.contextPath}/plugin/new/img/avatar-mini.jpg"></span>
                                <span class="subject">
                                    <span class="from">Jonathan Smith</span>
                                    <span class="time">Just now</span>
                                    </span>
                                <span class="message">
                                        Hello, this is an example msg.
                                    </span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="photo"><img alt="avatar" src="${re.contextPath}/plugin/new/img/avatar-mini2.jpg"></span>
                                <span class="subject">
                                    <span class="from">Jhon Doe</span>
                                    <span class="time">10 mins</span>
                                    </span>
                                <span class="message">
                                     Hi, Jhon Doe Bhai how are you ?
                                    </span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="photo"><img alt="avatar" src="${re.contextPath}/plugin/new/img/avatar-mini3.jpg"></span>
                                <span class="subject">
                                    <span class="from">Jason Stathum</span>
                                    <span class="time">3 hrs</span>
                                    </span>
                                <span class="message">
                                        This is awesome dashboard.
                                    </span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="photo"><img alt="avatar" src="${re.contextPath}/plugin/new/img/avatar-mini4.jpg"></span>
                                <span class="subject">
                                    <span class="from">Jondi Rose</span>
                                    <span class="time">Just now</span>
                                    </span>
                                <span class="message">
                                        Hello, this is metrolab
                                    </span>
                            </a>
                        </li>
                        <li>
                            <a href="#">See all messages</a>
                        </li>
                    </ul>
                </li>
                <!-- inbox dropdown end -->
                <!-- notification dropdown start-->
                <li id="header_notification_bar" class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">

                        <i class="fa fa-bell-o"></i>
                        <span class="badge bg-warning">7</span>
                    </a>
                    <ul class="dropdown-menu extended notification">
                        <div class="notify-arrow notify-arrow-yellow"></div>
                        <li>
                            <p class="yellow">You have 7 new notifications</p>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-danger"><i class="fa fa-bolt"></i></span>
                                Server #3 overloaded.
                                <span class="small italic">34 mins</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-warning"><i class="fa fa-bell"></i></span>
                                Server #10 not respoding.
                                <span class="small italic">1 Hours</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-danger"><i class="fa fa-bolt"></i></span>
                                Database overloaded 24%.
                                <span class="small italic">4 hrs</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-success"><i class="fa fa-plus"></i></span>
                                New user registered.
                                <span class="small italic">Just now</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-info"><i class="fa fa-bullhorn"></i></span>
                                Application error.
                                <span class="small italic">10 mins</span>
                            </a>
                        </li>
                        <li>
                            <a href="#">See all notifications</a>
                        </li>
                    </ul>
                </li>
                <!-- notification dropdown end -->
            </ul>
            <!--  notification end -->
        </div>
        <div class="top-nav ">
            <!--search & user info start-->
            <ul class="nav pull-right top-menu">
                <li>
                    <input type="text" class="form-control search" placeholder="Search">
                </li>
                <!-- user login dropdown start-->
                <li class="dropdown">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <img alt="" src="${re.contextPath}/plugin/new/img/avatar1_small.jpg">
                        <span class="username">Jhon Doue</span>
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu extended logout">
                        <div class="log-arrow-up"></div>
                        <li><a href="#"><i class=" fa fa-suitcase"></i>Profile</a></li>
                        <li><a href="#"><i class="fa fa-cog"></i> Settings</a></li>
                        <li><a href="#"><i class="fa fa-bell-o"></i> Notification</a></li>
                        <li><a href="login.html"><i class="fa fa-key"></i> Log Out</a></li>
                    </ul>
                </li>
                <li class="sb-toggle-right">
                    <i class="fa  fa-align-right"></i>
                </li>
                <!-- user login dropdown end -->
            </ul>
            <!--search & user info end-->
        </div>
    </header>

    <aside>
        <div id="sidebar" class="nav-collapse ">
            <!-- sidebar menu start-->
            <ul class="sidebar-menu" id="nav-accordion">
            <li>
                <a class="active" href="#" onclick="addTab()">
                    <i class="fa fa-dashboard"></i>
                    <span>Dashboard</span>
                </a>
            </li>

                <#--菜单-->
                <#macro tree data start end>
                    <#if (start=="start")>
                    </#if>
                    <#list data as child>
                        <#if child.children?size gt 0>
                            <li class="sub-menu">
                            <a href="#" >
                                <i class="fa fa-laptop"></i>
                                <span>${child.name}</span>
                            </a>
                            <ul class="sub">
                                <@tree data=child.children start="" end=""/>
                            </ul>
                            </li>
                        <#else>
                            <li class="len-menu-item">
                                <#--二级菜单-->
                                <a draggable="false"
                                   href="#${child.router}"
                                   len-id="${child.code}"
                                   data-options='{"url":"${child.url}","icon":"${child.icon}","name":"${child.name}","code":"${child.code}"}'
                                >
                                    <span>${child.name}</span>
                                </a>
                            </li>

                        </#if>
                    </#list>
                    <#if (end=="end")>
                    </#if>
                </#macro>

                <@tree data=menu start="start" end="end"/>

            </ul>
        </div>
    </aside>

    <!-- 内容主体区域 -->
    <section id="main-content">
        <div style="position: sticky;z-index: 100;top: 60px;width: 100%">
            <!--breadcrumbs start -->
            <div class="t-tabs tdesign-starter-layout-tabs-nav"
                 style="position: sticky; top: 0px; width: 100%;">
                <div class="t-tabs__header t-is-top">
                    <div class="t-tabs__nav">
                        <div class="t-tabs__operations t-tabs__operations--left">
                        </div>
                        <div class="t-tabs__operations t-tabs__operations--right">

                        </div>
                        <div class="t-tabs__nav-container t-tabs__nav--card t-is-top">
                            <div class="t-tabs__nav-scroll t-is-scrollable">
                                <div class="t-tabs__nav-wrap t-is-smooth"
                                     style="transform: translate3d(0px, 0px, 0px);">
                                    <div id="tabs-nav-home" class="t-tabs__nav-item t-tabs__nav--card t-size-m" draggable="false">
                                                <span class="t-tabs__nav-item-text-wrapper" style="z-index: 1;">
                                                    <i class="layui-icon fa fa-home"></i>
                                                    <!----></span><!---->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--breadcrumbs end -->
        </div>
        <section class="wrapper">
            <div class="content">
            </div>
        </section>
    </section>

    <footer class="site-footer">
        <div class="text-center">
            2023 &copy; lenosp.
            <a href="#" class="go-top">
                <i class="fa fa-angle-up"></i>
            </a>
        </div>
    </footer>

</section>


<script src="${re.contextPath}/plugin/new/js/jquery.js"></script>
<script src="${re.contextPath}/plugin/new/js/bootstrap.min.js"></script>
<script class="include" type="text/javascript" src="${re.contextPath}/plugin/new/js/jquery.dcjqaccordion.2.7.js"></script>
<script src="${re.contextPath}/plugin/new/js/jquery.scrollTo.min.js"></script>
<script src="${re.contextPath}/plugin/new/js/jquery.nicescroll.js" type="text/javascript"></script>
<script src="${re.contextPath}/plugin/new/js/jquery.sparkline.js" type="text/javascript"></script>
<script src="${re.contextPath}/plugin/new/assets/jquery-easy-pie-chart/jquery.easy-pie-chart.js"></script>
<script src="${re.contextPath}/plugin/new/js/owl.carousel.js" ></script>
<script src="${re.contextPath}/plugin/new/js/jquery.customSelect.min.js" ></script>
<#--排序表格组件-->
<script src="${re.contextPath}/plugin/plugins/sortable/Sortable.js"></script>
<script type="application/javascript">
    var nestedSortables = document.getElementsByClassName('len-menu-item');

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

<script src="${re.contextPath}/plugin/new/js/respond.min.js" ></script>

<!--right slidebar-->
<script src="${re.contextPath}/plugin/new/js/slidebars.min.js"></script>

<!--common script for all pages-->
<script src="${re.contextPath}/plugin/new/js/common-scripts.js"></script>

<!--script for this page-->
<script src="${re.contextPath}/plugin/new/js/sparkline-chart.js"></script>
<script src="${re.contextPath}/plugin/new/js/easy-pie-chart.js"></script>
<script src="${re.contextPath}/plugin/new/js/count.js"></script>
<!--toastr-->
<script src="${re.contextPath}/plugin/new/assets/toastr-master/toastr.js"></script>
<#--菜单组件-->
<script src="${re.contextPath}/plugin/new/menu.js"></script>
<#--面板组件-->
<script src="${re.contextPath}/plugin/new/dashboard.js"></script>
<script src="${re.contextPath}/plugin/new/tabs.js"></script>
<script src="${re.contextPath}/plugin/new/assets/data-tables/jquery.dataTables1.js"></script>
<script src="${re.contextPath}/plugin/new/assets/data-tables/DT_bootstrap.js"></script>
<#--主入口组件-->
<script src="${re.contextPath}/plugin/new/main.js"></script>


<script>

    //owl carousel

    /*$(document).ready(function() {
        $("#owl-demo").owlCarousel({
            navigation : true,
            slideSpeed : 300,
            paginationSpeed : 400,
            singleItem : true,
            autoPlay:true

        });
    });*/

    //custom select box

    $(function(){
        $('select.styled').customSelect();
    });
    $(window).on("resize",function(){

    });

    function addTab(){
        var data={
            code:'1',
            name:'测试1',
            icon:'fa-home',

        }
        lenosp.tabs.add(data, true, function (tabData, data) {
            console.log(tabData, data);
        });

        var byCode = lenosp.tabs.getByCode('1');
        var byIndex = lenosp.tabs.getByIndex(1);
        var current = lenosp.tabs.getCurrent();

        lenosp.tabs.changeByCode('1');
        lenosp.tabs.changeByCode(2);

    }

   /* $(window).on("resize",function(){
        var owl = $("#owl-demo").data("owlCarousel");
        owl.reinit();
    });*/

</script>
</body>
</html>