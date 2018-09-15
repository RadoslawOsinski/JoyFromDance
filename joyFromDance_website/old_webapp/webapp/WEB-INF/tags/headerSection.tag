<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>--%>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@tag pageEncoding="UTF-8" %>
<header class="header fixed fixed-desktop dark clearfix">

    <div class="container">
        <div class="row">
            <div class="col-lg-auto ml-lg-auto col-12">
                <div class="header-second clearfix">
                    <div class="main-navigation main-navigation--mega-menu  animated">
                        <nav class="navbar navbar-expand-lg navbar-light p-0">
                            <div class="collapse navbar-collapse" id="navbar-collapse-1">
                                <ul class="navbar-nav ml-xl-auto">
                                    <tag:dancerMenuPart/>
                                    <tag:teacherMenuPart/>
                                    <tag:schoolAdminMenuPart/>
                                    <tag:adminMenuPart/>
                                    <%--<sec:authorize access="hasRole('DANCER')">--%>
                                    <%--<li class="nav-item active mega-menu mega-menu--wide">--%>
                                    <%--<a href="${pageContext.request.contextPath}/dance/account" class="nav-link"><spring:message code="Account" text="Account"/></a>--%>
                                    <%--</li>--%>
                                    <%--</sec:authorize>--%>
                                </ul>
                            </div>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>

</header>
