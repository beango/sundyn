<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <!--
    Customize this policy to fit your own app's needs. For more guidance, see:
        https://github.com/apache/cordova-plugin-whitelist/blob/master/README.md#content-security-policy
    Some notes:
        * gap: is required only on iOS (when using UIWebView) and is needed for JS->native communication
        * https://ssl.gstatic.com is required only on Android and is needed for TalkBack to function properly
        * Disables use of inline scripts in order to mitigate risk of XSS vulnerabilities. To change this:
            * Enable inline JS: add 'unsafe-inline' to default-src
    -->
    <meta http-equiv="Content-Security-Policy" content="default-src * 'self' 'unsafe-inline' 'unsafe-eval' data: gap: content:">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui, viewport-fit=cover">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="default">
    <meta name="theme-color" content="#2196f3">
    <meta name="format-detection" content="telephone=no">
    <meta name="msapplication-tap-highlight" content="no">
    <title>My App</title>

    <link rel="stylesheet" href="lib/framework7/css/framework7.bundle.min.css">
    <link rel="stylesheet" href="css/icons.css">
    <link rel="stylesheet" href="css/app.css">
</head>
<body>
<div id="app">
    <!-- Status bar overlay for fullscreen mode-->
    <div class="statusbar"></div>
    <!-- Left panel with cover effect-->
    <div class="panel panel-left panel-cover theme-dark">
        <div class="view">
            <div class="page">
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="title">Left Panel</div>
                    </div>
                </div>
                <div class="page-content">
                    <div class="block">Left panel content goes here</div>
                </div>
            </div>
        </div>
    </div>
    <!-- Right panel with reveal effect-->
    <div class="panel panel-right panel-reveal theme-dark">
        <div class="view">
            <div class="page">
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="title">Right Panel</div>
                    </div>
                </div>
                <div class="page-content">
                    <div class="block">Right panel content goes here</div>
                </div>
            </div>
        </div>
    </div>

    <!-- Views/Tabs container -->
    <div class="views tabs safe-areas">
        <!-- Tabbar for switching views-tabs -->
        <div class="toolbar tabbar-labels toolbar-bottom">
            <div class="toolbar-inner">
                <a href="#view-home" class="tab-link tab-link-active">
                    <i class="icon f7-icons ios-only">home</i>
                    <i class="icon f7-icons ios-only icon-ios-fill">home_fill</i>
                    <i class="icon material-icons md-only">home</i>
                    <span class="tabbar-label">Home</span>
                </a>
                <a href="#view-catalog" class="tab-link">
                    <i class="icon f7-icons ios-only">list</i>
                    <i class="icon f7-icons ios-only icon-ios-fill">list_fill</i>
                    <i class="icon material-icons md-only">view_list</i>
                    <span class="tabbar-label">Catalog</span>
                </a>
                <a href="#view-settings" class="tab-link">
                    <i class="icon f7-icons ios-only">settings</i>
                    <i class="icon f7-icons ios-only icon-ios-fill">settings_fill</i>
                    <i class="icon material-icons md-only">settings</i>
                    <span class="tabbar-label">Settings</span>
                </a>
            </div>
        </div>
        <!-- Your main view/tab, should have "view-main" class. It also has "tab-active" class -->
        <div id="view-home" class="view view-main tab tab-active">
            <!-- Page, data-name contains page name which can be used in page callbacks -->
            <div class="page" data-name="home">
                <!-- Top Navbar -->
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="left">
                            <a href="#" class="link icon-only panel-open" data-panel="left">
                                <i class="icon f7-icons ios-only">menu</i>
                                <i class="icon material-icons md-only">menu</i>
                            </a>
                        </div>
                        <div class="title sliding">Home</div>
                        <div class="right">
                            <a href="#" class="link icon-only panel-open" data-panel="right">
                                <i class="icon f7-icons ios-only">menu</i>
                                <i class="icon material-icons md-only">menu</i>
                            </a>
                        </div>
                    </div>
                </div>

                <!-- Scrollable page content-->
                <div class="page-content">
                    <div class="block-title">Mail App</div>
                    <div class="list media-list">
                        <ul>
                            <c:forEach items="${productlist}" var="entity" varStatus="s">

                                <li>
                                    <%--<div class="item-content">
                                        <div class="item-inner">
                                            <div class="item-title-row">
                                                <div class="item-title">${entity.productname}</div>
                                            </div>
                                            <div class="item-subtitle">Beatles</div>
                                        </div>
                                    </div>--%>
                                    <div class="item-inner">
                                        <div class="item-title-row">
                                            <div class="item-title">${entity.productname}</div>
                                            <div class="item-after"><fmt:formatNumber type="currency" pattern="¥.00" value="${entity.price}" />
                                                <div class="stepper stepper-fill stepper-init" data-wraps="true" data-autorepeat="true" data-autorepeat-dynamic="true" data-decimal-point="2" data-manual-input-mode="true" style="width:40%;">
                                                    <div class="stepper-button-minus"></div>
                                                    <div class="stepper-input-wrap">
                                                        <input type="text" value="0" min="0" max="1000" step="1">
                                                    </div>
                                                    <div class="stepper-button-plus"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="item-subtitle">

                                        </div>
                                        <div class="item-text">

                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                </div>
            </div>
        </div>

        <!-- Catalog View -->
        <div id="view-catalog" class="view tab">
            <!-- Catalog page will be loaded here dynamically from /catalog/ route -->
        </div>

        <!-- Settings View -->
        <div id="view-settings" class="view tab">
            <!-- Settings page will be loaded here dynamically from /settings/ route -->
        </div>
    </div>

    <!-- Popup -->
    <div class="popup" id="my-popup">
        <div class="view">
            <div class="page">
                <div class="navbar">
                    <div class="navbar-inner">
                        <div class="title">Popup</div>
                        <div class="right">
                            <a href="#" class="link popup-close">Close</a>
                        </div>
                    </div>
                </div>
                <div class="page-content">
                    <div class="block">
                        <p>Popup content goes here.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Login Screen -->
    <div class="login-screen" id="my-login-screen">
        <div class="view">
            <div class="page">
                <div class="page-content login-screen-content">
                    <div class="login-screen-title">Login</div>
                    <div class="list">
                        <ul>
                            <li class="item-content item-input">
                                <div class="item-inner">
                                    <div class="item-title item-label">Username</div>
                                    <div class="item-input-wrap">
                                        <input type="text" name="username" placeholder="Your username">
                                    </div>
                                </div>
                            </li>
                            <li class="item-content item-input">
                                <div class="item-inner">
                                    <div class="item-title item-label">Password</div>
                                    <div class="item-input-wrap">
                                        <input type="password" name="password" placeholder="Your password">
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="list">
                        <ul>
                            <li>
                                <a href="#" class="item-link list-button login-button">Sign In</a>
                            </li>
                        </ul>
                        <div class="block-footer">Some text about login information.<br>Click "Sign In" to close Login Screen</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Cordova -->
<!--
<script src="cordova.js"></script>
-->

<!-- Framework7 library -->
<script src="lib/framework7/js/framework7.bundle.min.js"></script>

<!-- App routes -->
<script src="js/routes.js"></script>

<!-- Your custom app scripts -->
<script src="js/app.js"></script>
</body>
</html>
