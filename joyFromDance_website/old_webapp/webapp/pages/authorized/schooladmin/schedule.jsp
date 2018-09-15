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
                <h2 class="mt-4">Filtrowanie propozycji</h2>
                <div class="row">
                    <form class="col-lg-12">
                        <div class="form-group row">
                            <label for="lessonTypeName" class="col-sm-2 col-form-label">Minimalna liczba uczestników lekcji</label>
                            <div class="col-sm-4">
                                <input type="number" class="form-control col-lg-4" id="proposalMinDancers" value="3" required/>
                            </div>
                            <label for="lessonTypeName" class="col-sm-2 col-form-label">Typ lekcji</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control col-lg-4" id="proposalLessonTypeName" value=""/>
                            </div>
                        </div>
                    </form>
                </div>
                <h2 class="mt-4">Proponowany grafik od uczestników</h2>
                <div class="row">
                    <div class="col-lg-12">
                        <div id="schoolTimetable"></div>
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
                                                    <label for="browseName" class="form-control-label">Nazwa:</label>
                                                    <input type="text" class="form-control" id="browseName" disabled="disabled">
                                                </div>
                                                <div class="form-group">
                                                    <label for="browseDescription" class="form-control-label">Opis:</label>
                                                    <textarea class="form-control" id="browseDescription" disabled="disabled"></textarea>
                                                </div>
                                                <%--<div class="form-group">--%>
                                                    <%--<label for="browsePairsRequired" class="form-control-label">Wymagana para:</label>--%>
                                                    <%--<input id="browsePairsRequired" type="checkbox" required disabled="disabled"/>--%>
                                                <%--</div>--%>
                                                <div class="form-group">
                                                    <label for="interestedDancers" class="form-control-label">Chętne osoby:</label>
                                                    <ul id="interestedDancers">
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal"><spring:message code="Close" text="Close"/></button>
                                <button type="button" class="btn btn-sm btn-primary" id="addToScheduleLesson">Dodaj do grafiku</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="container">
                <div class="row">

                    <div class="main col-lg-12">

                        <h2 class="mt-4">Rodzaje lekcji</h2>
                        <div class="row">
                            <table class="table table-striped">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Nazwa<input type="text" id="lessonTypeNameSearch" class="form-control"/></th>
                                    <th>Akcje</th>
                                </tr>
                                </thead>
                                <tbody id="lessonTypesBody">
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

                        <h2 class="mt-4">Dodawanie rodzaju lekcji</h2>
                        <div class="row">
                            <form class="col-lg-12">
                                <div class="form-group row">
                                    <label for="lessonTypeName" class="col-sm-2 col-form-label">Rodzaj lekcji</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control col-lg-8" id="lessonTypeName" value=""
                                               placeholder="Rodzaj lekcji" maxlength="200" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="lessonTypeDescription" class="col-sm-2 col-form-label">Opis lekcji</label>
                                    <div class="col-sm-10">
                                        <textarea type="text" class="form-control col-lg-8" id="lessonTypeDescription" value=""
                                               placeholder="Rodzaj lekcji" required maxlength="3000">
                                        </textarea>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-12">
                                        <button type="button" id="addLessonTypeButton" class="btn btn-default">Dodaj
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
