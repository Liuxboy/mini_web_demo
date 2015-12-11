<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>京东钱包|管理后台</title>
    <link href="/image/favicon.ico" rel="shortcut icon">
    <link href="/css/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/css/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="/css/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
    <link href="/js/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
    <!--[if IE]>
    <link href="/css/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
    <![endif]-->

    <!--[if lte IE 9]>
    <script src="/js/dwz/speedup.js" type="text/javascript"></script>
    <![endif]-->

    <script src="/js/dwz/jquery-1.7.2.js" type="text/javascript"></script>
    <script src="/js/dwz/jquery.cookie.js" type="text/javascript"></script>
    <script src="/js/dwz/jquery.validate.js" type="text/javascript"></script>
    <script src="/js/dwz/jquery.bgiframe.js" type="text/javascript"></script>
    <script src="/js/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
    <script src="/js/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
    <script src="/js/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

    <!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+
    <script type="text/javascript" src="chart/raphael.js"></script>
    <script type="text/javascript" src="chart/g.raphael.js"></script>
    <script type="text/javascript" src="chart/g.bar.js"></script>
    <script type="text/javascript" src="chart/g.line.js"></script>
    <script type="text/javascript" src="chart/g.pie.js"></script>
    <script type="text/javascript" src="chart/g.dot.js"></script>
    -->

    <!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)-->
    <script src="/js/dwz.min.js" type="text/javascript"></script>
    <script src="/js/dwz/dwz.regional.zh.js" type="text/javascript"></script>

    <script type="text/javascript">
        $(function () {
            DWZ.init("/css/themes/dwz.frag.xml", {
                loginUrl:"/manage/login",	// 跳到登录页面
                statusCode: {ok: 200, error: 300, timeout: 301}, //【可选】
                pageInfo: {pageNum: "pageNum", numPerPage: "numPerPage", orderField: "orderField", orderDirection: "orderDirection"}, //【可选】
                keys: {statusCode: "statusCode", message: "message"}, //【可选】
                debug: false,	// 调试模式 【true|false】
                callback: function () {
                    initEnv();
                    $("#themeList").theme({themeBase: "/css/themes"}); // themeBase 相对于index页面的主题base路径
                }
            });
        });
        function logOutConfirmMsg(url){
            alertMsg.confirm("确定退出吗?", {
                okCall: function(){
                    window.location.href=url;
                }
            });
        }
    </script>
</head>
<body scroll="no">

<div id="layout">
    <div id="header">
        <div class="headerNav">
            <a class="logo" href="http://j-ui.com" target="_blank">标志</a>
            <ul class="nav">
                <li>您好,${user.userName}</li>
                <li>
                    <a href="javascript:;" onclick="logOutConfirmMsg('/manage/exit')">退出</a>
                </li>
            </ul>
            <ul class="themeList" id="themeList">
                <li theme="azure">
                    <div class="selected">天蓝</div>
                </li>
                <li theme="default">
                    <div>蓝色</div>
                </li>
                <li theme="green">
                    <div>绿色</div>
                </li>
                <li theme="purple">
                    <div>紫色</div>
                </li>
                <li theme="silver">
                    <div>银色</div>
                </li>
            </ul>
        </div>
        <!-- navMenu -->

    </div>

    <div id="leftside">
        <div id="sidebar_s">
            <div class="collapse">
                <div class="toggleCollapse">
                    <div></div>
                </div>
            </div>
        </div>

        <div id="sidebar">
            <div class="toggleCollapse"><h2>XXX-管理后台</h2>

                <div>收缩</div>
            </div>
            <!--引入menu-->
            <c:import url="system/menu/menu.jsp"/>
        </div>
    </div>

    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:;"><span><span
                                class="home_icon">我的主页</span></span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft">left</div>
                <!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                <div class="tabsRight">right</div>
                <!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                <div class="tabsMore">more</div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">我的主页</a></li>
            </ul>

            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox">
                    <div class="accountInfo">
                        <p><span>XXX-管理后台</span></p>
                    </div>
                    <div class="pageFormContent" layoutH="80" style="margin-right:230px">
                        漂亮的内容
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>

<div id="footer">Copyright &copy; 2014 <a href="http://www.jdpay.com/" target="_blank">&nbsp;www.xxx.com&nbsp;</a></div>
</body>
</html>