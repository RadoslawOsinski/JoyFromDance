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

                        <h2 class="mt-4"><spring:message code="Rooms" text="Rooms"/></h2>
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="Name" text="Name"/><input type="text" id="roomNameSearch" class="form-control"/></th>
                                    <th><spring:message code="Actions" text="Actions"/></th>
                                </tr>
                                </thead>
                                <tbody id="roomTableBody">
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

                        <h2 class="mt-4"><spring:message code="AddingRoom" text="Adding room"/></h2>
                        <div class="row">
                            <form class="col-lg-12">
                                <div class="form-group row">
                                    <label for="roomName" class="col-sm-2 col-form-label"><spring:message code="RoomName" text="Room name"/></label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control col-lg-8" id="roomName" value=""
                                               placeholder="<spring:message code="RoomName" text="Room name"/>" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-12">
                                        <button type="button" id="addRoomButton" class="btn btn-default"><spring:message code="Add" text="Add"/></button>
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
