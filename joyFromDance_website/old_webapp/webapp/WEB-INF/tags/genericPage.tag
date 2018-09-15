<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@tag description="Generic joy from dance page template" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<!DOCTYPE HTML>
<!--[if IE 9]> <html lang="pl" class="ie9"> <![endif]-->
<!--[if gt IE 9]> <html lang="pl" class="ie"> <![endif]-->
<!--[if !IE]><!-->
<html dir="ltr" lang="pl">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <meta name="description" content="Joy from dance, dancing schools, dancing lessons"/>
    <meta name="author" content="Radosław Osiński">

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <base href="/">

    <!-- Mobile Meta -->
    <meta name="viewport" content="width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes, shrink-to-fit=no">

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources-joy-from-dance/img/favicon.ico">
    <link rel="icon" href="${pageContext.request.contextPath}/resources-joy-from-dance/img/favicon.ico">
    <link rel="manifest" href="${pageContext.request.contextPath}/resources-joy-from-dance/manifest.json">

    <!-- Add to homescreen for Chrome on Android. Fallback for manifest.json -->
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="application-name" content="Joy from dance">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-mobile-web-app-title" content="Joy from dance">

    <!-- Homescreen icons -->
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-48x48.png">
    <link rel="apple-touch-icon" sizes="72x72" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-72x72.png">
    <link rel="apple-touch-icon" sizes="96x96" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-96x96.png">
    <link rel="apple-touch-icon" sizes="144x144" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-144x144.png">
    <link rel="apple-touch-icon" sizes="192x192" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-192x192.png">

    <!-- Tile icon for Windows 8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="images/manifest/icon-144x144.png">
    <meta name="msapplication-TileColor" content="#3f51b5">
    <meta name="msapplication-tap-highlight" content="no">

    <c:forEach var="additionalCss" items="${additionalCSSs}">
        <link href="${additionalCss}" rel="stylesheet">
    </c:forEach>

    <!-- Load webcomponents-loader.js to check and load any polyfills your browser needs -->
    <script src="${pageContext.request.contextPath}/resources-joy-from-dance/node_modules/@webcomponents/webcomponentsjs/webcomponents-loader.js"></script>
    <script type="javascript">
        window.JoyFromDanceGlobals = {
            rootPath: '/',
            nodeModulesRoot: '/resources-joy-from-dance'
        };
        window.Polymer = {
            rootPath: '/'
        };
        // Load and register pre-caching Service Worker
        if ('serviceWorker' in navigator) {
            window.addEventListener('load', function() {
                navigator.serviceWorker.register('service-worker.js', {
                    scope: JoyFromDanceGlobals.rootPath
                });
            });
        }
    </script>

    <meta name="robots" content=""/>
    <title>Joy from dance</title>

    <%--<tag:googleAnalytics/>--%>
    <link href="${pageContext.request.contextPath}/resources-joy-from-dance/img/favicon.ico" rel="shortcut icon"
          type="image/x-icon"/>
</head>
<body>

    ===========
    <app></app>
    ===========
<%--<div class="scrollToTop circle"><i class="icon-up-open-big"></i></div>--%>

<%--<div class="page-wrapper">--%>
    <%--<div class="header-container">--%>
        <%--<tag:headerTopRowSection/>--%>
        <%--<tag:headerSection/>--%>
    <%--</div>--%>
    <%--<jsp:doBody/>--%>
    <%--<tag:footerSection/>--%>
<%--</div>--%>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources-joy-from-dance/plugins/screenfull/screenfull.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.9.0/moment.min.js"></script>
<script type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/moment-timezone/0.5.14/moment-timezone-with-data.min.js"></script>

<c:if test="${not empty mainJavaScript}">
    <script type="module" src="${mainJavaScript}"></script>
</c:if>

</body>
</html>
