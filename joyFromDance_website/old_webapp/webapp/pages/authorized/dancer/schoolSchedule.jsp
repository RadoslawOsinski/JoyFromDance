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
                <h2 class="mt-4">Proponowany grafik dla szkoły ${schoolId}</h2>

                <input type="hidden" id="loggedUserEmail" value="${pageContext.request.userPrincipal.account.keycloakSecurityContext.idToken.email}"/>
                <input type="hidden" id="schoolId" value="${schoolId}"/>

                <div class="row">
                    <div class="col-lg-10">
                        <div id="schoolTimetable"></div>
                    </div>
                    <div class="col-lg">
                        <div class="form-group row">
                            <label for="lessonTypes" class="col-sm-2 col-form-label">Rodzaje lekcji</label>
                        </div>
                        <div class="form-group row">
                           <ul id="lessonTypes">
                           </ul>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12">
                        <div class="form-group row">
                            <label for="saveMyScheduleProposalButton" class="col-sm-2 col-form-label">Zapis mojej propozycji grafiku</label>
                            <button type="button" id="saveMyScheduleProposalButton" class="btn btn-primary">Zapisz</button>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="browseLessonModal" tabindex="-1" role="dialog"
                     aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
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
                                                    <input type="time" class="form-control" id="browseStartTime"
                                                           value="00:00" disabled="disabled">
                                                </div>
                                                <div class="form-group">
                                                    <label for="browseEndTime" class="form-control-label">Koniec:</label>
                                                    <input type="time" class="form-control" id="browseEndTime"
                                                           value="00:00" disabled="disabled">
                                                </div>
                                                <div class="form-group">
                                                    <label for="browseDescription" class="form-control-label">Opis:</label>
                                                    <textarea class="form-control" id="browseDescription" disabled="disabled"></textarea>
                                                </div>
                                                <div class="form-group">
                                                    <label for="browsePairsRequired" class="form-control-label">Wymagana para:</label>
                                                    <input id="browsePairsRequired" type="checkbox" required disabled="disabled"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal"><spring:message code="Close" text="Close"/></button>
                                <button type="button" class="btn btn-sm btn-secondary" id="deleteLesson">Usuń propozycję</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    </jsp:body>
</tag:genericPage>
