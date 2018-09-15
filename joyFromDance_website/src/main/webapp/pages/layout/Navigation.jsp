<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c2" uri="http://java.sun.com/jsp/jstl/core" %>
<aside class="mdc-drawer mdc-drawer--temporary">
    <nav class="mdc-drawer__drawer">
        <header class="mdc-drawer__header">
            <div class="mdc-drawer__header-content mdc-theme--on-primary mdc-theme--primary-bg">
            </div>
        </header>
        <nav class="mdc-drawer__content mdc-list-group">
            <div class="mdc-list">
                <a class="mdc-list-item" id="backMenuLink">
                    <i class="mdc-list-item__graphic" aria-hidden="true"><?xml version="1.0" encoding="utf-8"?><svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" width="24px" height="24px" viewBox="0 0 24 24" enable-background="new 0 0 24 24" xml:space="preserve"><g id="Bounding_Boxes"><path fill="none" d="M0,0h24v24H0V0z"/></g><g id="Rounded"><path d="M19,11H7.83l4.88-4.88c0.39-0.39,0.39-1.03,0-1.42l0,0c-0.39-0.39-1.02-0.39-1.41,0l-6.59,6.59 c-0.39,0.39-0.39,1.02,0,1.41l6.59,6.59c0.39,0.39,1.02,0.39,1.41,0l0,0c0.39-0.39,0.39-1.02,0-1.41L7.83,13H19c0.55,0,1-0.45,1-1 v0C20,11.45,19.55,11,19,11z"/></g></svg></i>Back
                </a>
                <a class="mdc-list-item" target="_blank" href="https://gitlab.com/OsinskiRadoslaw/JoyFromDance/issues">
                    <i class="mdc-list-item__graphic" aria-hidden="true">
                        <%@include file="/pages/layout/icons/bug.jsp" %>
                    </i>Report bugs
                </a>
                <a class="mdc-list-item" target="_blank" href="https://gitlab.com/OsinskiRadoslaw/JoyFromDance/issues">
                    <i class="mdc-list-item__graphic" aria-hidden="true">
                        <%@include file="/pages/layout/icons/issues.jsp" %>
                    </i>Feature requests
                </a>
    <%--</div>--%>
        <%--</nav>--%>
        <%--<nav class="mdc-drawer__content mdc-list-group">--%>
            <%--<div class="mdc-list"></div>--%>
        <%--</nav>--%>
    <%--</nav>--%>
</aside>
<%--<spring:url value="${pageContext.request.contextPath}/about" var="aboutUrl" htmlEscape="true"/>--%>
<%--<li <c2:if test="${fn:endsWith(pageContext.request.requestURL, 'About.jsp')}">class="current"</c2:if>>--%>
    <%--<a href="${aboutUrl}"><spring:message code="AboutCompany"/></a>--%>
<%--</li>--%>
