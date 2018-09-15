<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:genericPage>
    <jsp:body>

        <br/>
        <br/>
        <br/>
        <section class="main-container">

            <div class="container">
                <div class="row">

                    <div class="main col-lg-12">

                        <h2 class="mt-4"><spring:message code="MyDanceSchools" text="My dance schools"/></h2>
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="School" text="School"/><input type="text" id="searchSchoolName" class="form-control" disabled/></th>
                                    <th><spring:message code="Email" text="Email"/></th>
                                    <th><spring:message code="Phone" text="Phone"/></th>
                                    <th><spring:message code="SocialMedia" text="Social media"/></th>
                                    <th><spring:message code="Actions" text="Actions"/></th>
                                </tr>
                                </thead>
                                <tbody id="schoolTableBody">
                                </tbody>
                            </table>
                            <button id="previousPageButton" class="btn btn-default-transparent btn-sm"><i
                                class="fa fa-chevron-left pr-10"></i> Previous
                            </button>
                            <button id="resetPageButton" class="btn btn-default-transparent btn-sm">0</button>
                            <button id="nextPageButton" class="btn btn-default-transparent btn-sm"><i
                                class="fa fa-chevron-right pr-10"></i> Next
                            </button>
                        </div>
                    </div>

                </div>

                <div class="row">

                    <div class="main col-lg-12">
                        <h2 class="mt-4"><spring:message code="SchoolSearchingIsCurrentlyDisabled" text=""/></h2>
                    </div>

                </div>

                <%--<div class="row">--%>

                    <%--<div class="main col-lg-12">--%>

                        <%--<h2 class="mt-4">Wyszukiwanie szkoły</h2>--%>
                        <%--....--%>
                        <%--skomplikowane wyszukiwanie szkół, pełnotekstowe, po kryteriach:--%>
                        <%--<ul>--%>
                            <%--<li>lokalizacja</li>--%>
                            <%--<li>styl tańca</li>--%>
                            <%--<li>ocena</li>--%>
                            <%--<li>popularność</li>--%>
                            <%--<li>ceny</li>--%>
                        <%--</ul>--%>
                        <%---> Może coś na wzór: https://www.zomato.com/pl/warszawa/restauracje/ ???--%>
                        <%---> snipetty szkół dla googla--%>
                        <%---> obrazki z mediów społecznościowych--%>
                        <%---> komentarze z mediów społecznościowych--%>
                    <%--</div>--%>

                    <%--<div class="main col-lg-12">--%>
                        <%--<h2 class="mt-4">Uproszczone wyszukiwanie szkoły</h2>--%>
                        <%--<form>--%>
                            <%--<div class="form-group">--%>
                                <%--<label for="assignMySchool" class="form-control-label">Nazwa szkoły</label>--%>
                                <%--<input id="assignMySchool" type="text" class="typeahead"/>--%>
                                <%--<button type="button" id="addNewSchoolForDancerButton" class="btn btn-pr">Dodaj</button>--%>
                            <%--</div>--%>
                        <%--</form>--%>
                    <%--</div>--%>

                <%--</div>--%>
            </div>
        </section>

    </jsp:body>
</tag:genericPage>
