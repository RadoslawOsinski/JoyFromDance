<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%@tag pageEncoding="UTF-8" %>
<div class="header-top colored">
    <div class="container">
        <div class="row">
            <div class="col-3 col-sm-5 col-lg-4">
                <div class="header-top-first clearfix">
                    <div class="social-links hidden-md-up circle small">
                    </div>
                </div>
            </div>
            <div class="col-5 col-sm-5 col-lg-4">
                <div class="clearfix text-center">
                    <ul class="list-inline">
                        <li class="list-inline-item"><i class="fa fa-phone pr-1 pl-10"></i>+48 791-101-335</li>
                        <li class="list-inline-item"><i class="fa fa-envelope-o pr-1 pl-10"></i> info@joyfrom.dance</li>
                        <li class="list-inline-item"><spring:message code="Language" text="Language"/>: <a href="${pageContext.request.contextPath}/?joyFromDanceLanguage=en">EN</a> | <a href="${pageContext.request.contextPath}/?joyFromDanceLanguage=pl">PL</a></li>
                    </ul>
                </div>
            </div>
            <div class="col-4 col-sm-2 col-lg-4">
                <div class="clearfix text-right">
                    <sec:authorize access="!isAuthenticated()">
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/dance/"
                               class="btn btn-default btn-sm"><i
                                class="fa fa-user pr-2"></i> <spring:message code="SignUp" text="Sign up"/></a>
                        </div>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <div class="btn-group">
                                ${pageContext.request.userPrincipal.account.keycloakSecurityContext.idToken.name}
                        </div>
                        <div class="btn-group">
                            <a href="${pageContext.request.contextPath}/logout" id="header-top-drop-2"
                               class="btn btn-default btn-sm"><i class="fa fa-lock pr-2"></i> Logout</a>
                        </div>
                    </sec:authorize>
                </div>
            </div>
        </div>
    </div>
</div>
