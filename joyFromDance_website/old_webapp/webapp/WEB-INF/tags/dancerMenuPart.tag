<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@tag pageEncoding="UTF-8" %>
<sec:authorize access="hasRole('ADMIN') or hasRole('DANCER')">
    <li class="nav-item dropdown ">
        <a href="${pageContext.request.contextPath}/dance/"
           class="nav-link dropdown-toggle"
           data-toggle="dropdown" aria-haspopup="true"
           aria-expanded="false"><spring:message code="Dancer" text="Dancer"/></a>
        <ul class="dropdown-menu" aria-labelledby="seventh-dropdown">
            <li><a
                href="${pageContext.request.contextPath}/dancer/"><spring:message code="Dashboard" text="Dashboard"/></a>
            </li>
            <li><a
                href="${pageContext.request.contextPath}/dancer/school"><spring:message code="Schools" text="Schools"/></a>
            </li>
        </ul>
    </li>
</sec:authorize>
--%>
