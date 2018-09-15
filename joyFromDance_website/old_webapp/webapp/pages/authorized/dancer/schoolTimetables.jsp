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
                <h2 class="mt-4">Grafik zajęć w szkole ${schoolId}</h2>

                <div class="margin-clear row">
                    <form>
                        <div class="form-group">
                            <input type="hidden" id="loggedUserEmail" value="${pageContext.request.userPrincipal.account.keycloakSecurityContext.idToken.email}"/>
                            <input type="hidden" id="schoolId" value="${schoolId}"/>
                            <label for="schoolRoomId" class="form-control-label">Sala:</label>
                            <select id="schoolRoomId" class="form-control" required></select>
                        </div>
                    </form>
                </div>

                <div class="row">
                    <div id="roomTimetable"></div>
                </div>

                <div class="modal fade" id="browseLessonModal" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Lekcja</h4>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                    aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="Close" text="Close"/></span></button>
                            </div>
                            <div class="modal-body">
                                <form>
                                    <div class="container">
                                        <div class="row">
                                            <div class="col">
                                                <div class="form-group">
                                                    <label for="browseStartTime" class="form-control-label">Start:</label>
                                                    <input type="datetime-local" class="form-control" id="browseStartTime"
                                                           value="2000-12-31T00:00" disabled="disabled">
                                                </div>
                                                <div class="form-group">
                                                    <label for="browseEndTime" class="form-control-label">Koniec:</label>
                                                    <input type="datetime-local" class="form-control" id="browseEndTime"
                                                           value="2000-12-31T00:00" disabled="disabled">
                                                </div>
                                                <div class="form-group">
                                                    <label for="browseDescription" class="form-control-label">Opis:</label>
                                                    <textarea class="form-control" id="browseDescription" disabled="disabled"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label for="browsePairsRequired" class="form-control-label">Wymagana para:</label>
                                                    <input id="browsePairsRequired" type="checkbox" required disabled="disabled"/>
                                                </div>
                                                <div class="form-group">
                                                    <label for="assignedTeachersList" class="form-control-label">Przydzieleni
                                                        nauczyciele</label>
                                                    <ul id="assignedTeachersList">
                                                    </ul>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="form-group d-none" id="lessonPairs">
                                                    <label for="dancerPairs" class="form-control-label">Pary</label>
                                                    <table id="dancerPairs" class="table table-striped">
                                                        <thead>
                                                        <tr>
                                                            <th>#</th>
                                                            <th>1. Imię i Nazwisko</th>
                                                            <th>2. Imię i Nazwisko</th>
                                                            <th>Stan</th>
                                                        </tr>
                                                        </thead>
                                                        <tbody id="dancerPairsTableBody">
                                                        </tbody>
                                                    </table>
                                                </div>
                                                <div class="form-group d-none" id="lessonDancers">
                                                    <label for="assignedDancersList" class="form-control-label">Zapisani tancerze</label>
                                                    <ul id="assignedDancersList">
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal"><spring:message code="Close" text="Close"/></button>
                                <button type="button" class="btn btn-sm btn-primary hidden-xs-up" id="assignToLesson">Zapisz na lekcję</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </jsp:body>
</tag:genericPage>
