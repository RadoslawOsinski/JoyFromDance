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

                        <h2 class="mt-4"><spring:message code="Schools" text="Schools"/></h2>
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="Name" text="Name"/> <input type="text" id="nameSearch" class="form-control"/></th>
                                    <th><spring:message code="Created" text="Created"/></th>
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

                        <div id="schoolPreview" class="row hidden-xs-up collapse">
                            <h3 class="mt-4"><spring:message code="SchoolPreview" text="School preview"/></h3>
                            <form class="col-lg-12">
                                <div class="form-group row">
                                    <label for="previewSchoolId" class="col-sm-2 col-form-label"><spring:message code="SchoolId" text="Shool id"/></label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control col-lg-8" id="previewSchoolId" readonly/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-12">
                                        <label for="schoolAdministrators" class="col-sm-2 col-form-label"><spring:message code="SchoolAdministrator" text="School administrator"/>:</label>
                                        <ul id="schoolAdministrators">
                                        </ul>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-12">
                                        <input type="text" id="newSchoolAdministrator" class="form-control" required placeholder="user email in keycloak"/>
                                        <button type="button" id="addSchoolAdminButton" class="btn btn-primary"><spring:message code="AddSchoolAdministrator" text="Add school administrator"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <h2 class="mt-4"><spring:message code="AddSchool" text="Add school"/></h2>
                        <div class="row">
                            <form class="col-lg-12">
                                <div class="form-group row">
                                    <label for="schoolName" class="col-sm-2 col-form-label"><spring:message code="SchoolName" text="School name"/></label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control col-lg-8" id="schoolName" value=""
                                               placeholder="<spring:message code="SchoolName" text="School name"/>" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-12">
                                        <button type="button" id="addSchoolButton" class="btn btn-default"><spring:message code="Add" text="Add"/>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>

                    </div>
                </div>
            </div>
        </section>

    </jsp:body>
</tag:genericPage>
