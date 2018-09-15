<%--@elvariable id="mainJavaScript" type="java.lang.String"--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="Generic joy from dance page template" pageEncoding="UTF-8" %>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes">
    <title>Joy from dance</title>
    <meta name="description" content="Dancing class, Dancing classes management, dancer meetup, dancing school management, meeting dancing partners, dancing lessons reminder, dancing events browser">
    <%--<meta name="keywords" content="<c:forEach var="blogKeyword" items="${keywords}">--%>
        <%--${blogKeyword.name},--%>
    <%--</c:forEach>"/>--%>
    <%--<c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'pl'}">--%>
        <%--<link rel="canonical" href="https://joyfrom.dance"/>--%>
    <%--</c:if>--%>
    <%--<c:if test="${pageContext.response.locale != null && pageContext.response.locale == 'en'}">--%>
        <%--<link rel="canonical" href="https://joyfrom.dance"/>--%>
    <%--</c:if>--%>

    <link rel="icon" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/favicon.ico">

    <!-- See https://goo.gl/OOhYW5 -->
    <link rel="manifest" href="${pageContext.request.contextPath}/resources-joy-from-dance/manifest.json">

    <!-- See https://goo.gl/qRE0vM -->
    <meta name="theme-color" content="#3f51b5">

    <!-- Add to homescreen for Chrome on Android. Fallback for manifest.json -->
    <meta name="mobile-web-app-capable" content="yes">
    <meta name="application-name" content="Joy from dance">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
    <meta name="apple-mobile-web-app-title" content="My App">

    <!-- Homescreen icons -->
    <link rel="apple-touch-icon" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-48x48.png">
    <link rel="apple-touch-icon" sizes="72x72" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-72x72.png">
    <link rel="apple-touch-icon" sizes="96x96" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-96x96.png">
    <link rel="apple-touch-icon" sizes="144x144" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-144x144.png">
    <link rel="apple-touch-icon" sizes="192x192" href="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-192x192.png">

    <!-- Tile icon for Windows 8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-144x144.png">
    <meta name="msapplication-TileColor" content="#3f51b5">
    <meta name="msapplication-tap-highlight" content="no">

    <!-- Default twitter cards -->
    <meta name="twitter:card" content="summary">
    <meta name="twitter:site" content="@username">
    <meta property="og:type" content="website">
    <meta property="og:site_name" content="Joy from dance">
    <meta property="og:image" content="${pageContext.request.contextPath}/resources-joy-from-dance/images/manifest/icon-144x144.png" />

    <!-- Performace tip: hint to the browser to start the handshake for the fonts site -->
    <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin>

    <link rel="stylesheet" type="text/css" href="https://unpkg.com/material-components-web@0.38.0/dist/material-components-web.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources-joy-from-dance/css/layout-grid.css"/>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources-joy-from-dance/css/global_styles.css"/>--%>
    <style>
        @font-face {
            font-family: 'Pacifico';
            font-style: normal;
            font-weight: 400;
            src: local('Pacifico Regular'), local('Pacifico-Regular'), url(https://fonts.gstatic.com/s/pacifico/v12/Q_Z9mv4hySLTMoMjnk_rCXYhjbSpvc47ee6xR_80Hnw.woff2) format('woff2');
            /* THe browser draws the text immediately in the fallback font if the font
               isn't loaded, then swaps it with the webfont when it eventually loads
               See: https://developers.google.com/web/updates/2016/02/font-display
            */
            font-display: swap;
        }
        body {
            margin: 0;
            /* This is a font-stack that tries to use the system-default sans-serifs first */
            font-family: Helvetica,Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol";
            line-height: 1.5;
            min-height: 100vh;
            -webkit-font-smoothing: antialiased;
        }
    </style>

    <%--<script>--%>
        <%--// Load and register pre-caching Service Worker--%>
        <%--if ('serviceWorker' in navigator) {--%>
            <%--window.addEventListener('load', function() {--%>
                <%--navigator.serviceWorker.register('service-worker.js', {--%>
                    <%--scope: '/',--%>
                <%--});--%>
            <%--});--%>
        <%--}--%>

        <%--// Redux assumes `process.env.NODE_ENV` exists in the ES module build.--%>
        <%--// https://github.com/reactjs/redux/issues/2907--%>
        <%--window.process = { env: { NODE_ENV: 'production' } };--%>
    <%--</script>--%>
    <%--<script type="application/javascript">--%>
        <%--var $contextPath = '${pageContext.request.contextPath}';--%>
    <%--</script>--%>

</head>
<body>

<input type="hidden" id="backendRestAddress" value="${backendRestAddress}"/>
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}"/>
<input type="hidden" id="currentPageUrl" value="${pageContext.request.requestURL}"/>
<input type="hidden" id="currentPageUri" value="${pageContext.request.requestURI}"/>

<%@include file="/pages/layout/Header.jsp" %>
<%@include file="/pages/layout/Navigation.jsp" %>

<main role="main" class="main-content">
    <jsp:doBody/>
</main>

<%@include file="/pages/layout/Footer.jsp" %>

<script src="https://unpkg.com/material-components-web@0.38.0/dist/material-components-web.min.js">
    window.mdc = mdc;
</script>
<!-- Load webcomponents-loader.js to check and load any polyfills your browser needs -->
<%--<script src="${pageContext.request.contextPath}node_modules/@webcomponents/webcomponentsjs/webcomponents-loader.js"></script>--%>
<%--<script type="module" src="${pageContext.request.contextPath}/resources-joy-from-dance/js/components/my-app.js" crossorigin></script>--%>
<script type="module" src="${pageContext.request.contextPath}${mainJavaScript}" crossorigin></script>

<c:if test="${empty googleApiKey}">
    <script async defer src="https://maps.googleapis.com/maps/api/js?libraries=places&callback">
        window.google = google;
    </script>
</c:if>
<c:if test="${!empty googleApiKey}">
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=${googleApiKey}&libraries=places&callback">
        window.google = google;
    </script>
</c:if>

<c:if test="${!empty gtag}">
    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=${gtag}"></script>
    <script>
        window.dataLayer = window.dataLayer || [];
        function gtag(){dataLayer.push(arguments);}
        gtag('js', new Date());
        gtag('config', '${gtag}');
    </script>
</c:if>

</body>
</html>
