<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:genericPage>
    <jsp:body>

        <br/>
        <br/>
        <br/>

        <section class="main-container page-wrapper" id="teachersFullScreenArea">

            <div class="container">
                <div class="row">

                    <div class="main col-lg-12">

                        <h2 class="mt-4"><spring:message code="Teachers" text="Teachers"/></h2>

                        <div class="d-flex justify-content-between">
                            <div>
                                <button id="openAddTeacherPopupButton" class="btn btn-primary">
                                    <i class="fa fa-plus-circle"></i> <spring:message code="Add" text="Add"/>
                                </button>
                                <button id="removeTeachersButton" class="btn btn-danger" disabled>
                                    <i class="fa fa-minus-circle"></i> <spring:message code="Delete" text="Delete"/>
                                </button>
                            </div>
                            <div class="d-flex form-inline justify-content-between">
                                <button id="firstPageButton" class="btn btn-gray btn-gray-transparent"
                                        data-toggle="tooltip"
                                        data-placement="top"
                                        data-original-title="<spring:message code="FirstPage" text="First page"/>">
                                    <i class="fa fa-angle-double-left"></i>
                                </button>
                                <button id="previousPageButton" class="btn btn-gray btn-gray-transparent"
                                        data-toggle="tooltip" data-placement="top"
                                        data-original-title="<spring:message code="PreviousPage" text="Previous page"/>">
                                    <i class="fa fa-angle-left"></i>
                                </button>
                                <input id="currentPageInput" type="number" placeholder="1/1" min="1" step="1"
                                       title="currentPage" class="form-control"/>
                                <button id="nextPageButton" class="btn btn-gray btn-gray-transparent"
                                        data-toggle="tooltip"
                                        data-placement="top"
                                        data-original-title="<spring:message code="NextPage" text="Next page"/>">
                                    <i class="fa fa-angle-right"></i>
                                </button>
                                <button id="lastPageButton" class="btn btn-gray btn-gray-transparent"
                                        data-toggle="tooltip"
                                        data-placement="top"
                                        data-original-title="<spring:message code="LastPage" text="Last page"/>">
                                    <i class="fa fa-angle-double-right"></i>
                                </button>
                                <select id="displayedRows" class="form-control mb-2 mr-sm-2 mb-sm-0"
                                        title="<spring:message code="NumberOfRows" text="Number of rows"/>"
                                        data-toggle="tooltip" data-placement="top"
                                        data-original-title="<spring:message code="NumberOfRows" text="Number of rows"/>">
                                    <option value="2">2</option>
                                    <option value="5">5</option>
                                    <option value="10" selected>10</option>
                                    <option value="15">15</option>
                                    <option value="20">20</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select>
                                <button id="teachersTableFullScreenButton"
                                        class="btn btn-gray btn-gray-transparent hidden-xs-up"
                                        data-toggle="tooltip" data-placement="top"
                                        data-original-title="<spring:message code="FullScreenMode" text="Full screen mode"/>">
                                    <i class=" fa fa-desktop"></i>
                                </button>
                            </div>
                        </div>

                        <div class="row">
                            <table class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th id="switchSelectionHeader" data-toggle="tooltip"
                                        data-placement="top"
                                        data-original-title="<spring:message code="SwitchSelection" text="Switch selection"/>"
                                        class="text-center">
                                        <i class="fa fa-refresh"></i></th>
                                    <th>#</th>
                                    <th><spring:message code="FirstName" text="First name"/></th>
                                    <th><spring:message code="LastName" text="Last name"/></th>
                                    <th id="sortByEmailHeader" data-toggle="tooltip"
                                        data-placement="top"
                                        data-original-title="<spring:message code="SortByEmail" text="Sort by email"/>">
                                        <i id="sortByEmailHeaderUpSymbol" class="fa fa-angle-up hidden-xs-up"></i>
                                        <i id="sortByEmailHeaderDownSymbol" class="fa fa-angle-down hidden-xs-up"></i>
                                        &nbsp;<spring:message code="Email" text="Email"/>
                                    </th>
                                </tr>
                                <tr id="teachersTableAdditionalFilters" class="hidden-xs-up">
                                    <th class="text-center"></th>
                                    <th></th>
                                    <th><input type="text" id="firstNameSearch" class="form-control"
                                               title="First name filter"/></th>
                                    <th><input type="text" id="lastNameSearch" class="form-control"
                                               title="Last name filter"/></th>
                                    <th><input type="text" id="emailSearch" class="form-control" title="Email filter"/>
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="teachersTableBody">
                                </tbody>
                            </table>
                        </div>

                        <div class="row form-inline justify-content-end">
                            <button id="defaultFiltersAndSortingButton" class="btn btn-gray btn-gray-transparent"
                                    data-toggle="tooltip" data-placement="top"
                                    data-original-title="<spring:message code="DefaultFiltersAndSortingSettings" text="Default filters and sorting settings"/>">
                                <i class="fa fa-trash"></i>
                            </button>
                            <button id="additionalFiltersButton" class="btn btn-gray btn-gray-transparent"
                                    data-toggle="tooltip" data-placement="top"
                                    data-original-title="<spring:message code="AdditionalFilters" text="Additional filters"/>">
                                <i class=" fa fa-filter"></i>
                            </button>
                        </div>

                        <div class="modal fade" id="addSchoolTeacherPopup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-m" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h4 class="modal-title"><spring:message code="Teacher" text="Teacher"/></h4>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                            aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="Close" text="Close"/></span></button>
                                    </div>
                                    <div class="modal-body">
                                        <form>
                                            <div class="container">
                                                <div class="row">
                                                    <div class="col">
                                                        <div class="form-group">
                                                            <label for="teacherEmail" class="form-control-label"><spring:message code="TeacherEmail" text="Teacher email"/>:</label>
                                                            <input type="email" class="form-control" id="teacherEmail" value=""
                                                                   placeholder="<spring:message code="TeacherEmail" text="Teacher email"/>"
                                                                   required/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal"><spring:message code="Close" text="Close"/></button>
                                        <button type="button" class="btn btn-sm btn-primary" id="addTeacherButton"><spring:message code="Add" text="Add"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </section>
    </jsp:body>
</tag:genericPage>
