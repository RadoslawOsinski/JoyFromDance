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

                        <h2 class="mt-4"><spring:message code="MyLessonsAsTeacher" text="My lessons as teacher"/></h2>
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="School" text="School"/><input type="text" id="searchSchoolName" class="form-control" disabled/></th>
                                    <th><spring:message code="Room" text="Room"/><input type="text" id="searchRoom" class="form-control" disabled/></th>
                                    <th><spring:message code="Start" text="Start"/></th>
                                    <th><spring:message code="End" text="End"/></th>
                                    <th><spring:message code="Description" text="Description"/><input type="text" id="searchDescription" class="form-control" disabled/></th>
                                    <th><spring:message code="Actions" text="Actions"/></th>
                                </tr>
                                </thead>
                                <tbody id="activitiesTableBody">
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
            </div>
        </section>
    </jsp:body>
</tag:genericPage>
