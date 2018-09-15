<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<tag:genericPage>
    <jsp:body>

        <br/>
        <br/>
        <br/>

        <section class="main-container">

            <h2 class="mt-4">Grafik zajęć w sali</h2>

            <div class="row">

                <input type="hidden" id="roomId" value="${roomId}"/>
                <div id="roomTimetable"></div>

            </div>

            <div class="modal fade" id="addLessonModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Dodawanie godzin lekcyjnych</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="Close" text="Close"/></span></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label for="startTime" class="form-control-label">Start:</label>
                                    <input type="datetime-local" class="form-control" id="startTime" value="2000-12-31T00:00" required>
                                </div>
                                <div class="form-group">
                                    <label for="endTime" class="form-control-label">Koniec:</label>
                                    <input type="datetime-local" class="form-control" id="endTime" value="2000-12-31T00:00" required>
                                </div>
                                <div class="form-group">
                                    <label for="description" class="form-control-label">Opis:</label>
                                    <textarea class="form-control" id="description" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="pairsRequired" class="form-control-label">Wymagana para:</label>
                                    <input id="pairsRequired" type="checkbox" required/>
                                </div>
                                <div class="form-group">
                                    <label for="color" class="form-control-label">Kolor lekcji:</label>
                                    <input type="color" class="form-control" id="color" value="" required/>
                                </div>
                                <div class="form-group">
                                    <label for="textColor" class="form-control-label">Kolor tekstu lekcji:</label>
                                    <input type="color" class="form-control" id="textColor" value="" required/>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal"><spring:message code="Close" text="Close"/></button>
                            <button type="button" class="btn btn-sm btn-default" id="saveNewLessonButton"><spring:message code="Save" text="Save"/></button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="editLessonModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Edycja godzin lekcyjnych</h4>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span><span class="sr-only"><spring:message code="Close" text="Close"/></span></button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <div class="form-group">
                                    <label for="editStartTime" class="form-control-label">Start:</label>
                                    <input type="datetime-local" class="form-control" id="editStartTime" value="2000-12-31T00:00" required>
                                </div>
                                <div class="form-group">
                                    <label for="editEndTime" class="form-control-label">Koniec:</label>
                                    <input type="datetime-local" class="form-control" id="editEndTime" value="2000-12-31T00:00" required>
                                </div>
                                <div class="form-group">
                                    <label for="editDescription" class="form-control-label">Opis:</label>
                                    <textarea class="form-control" id="editDescription" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="editPairsRequired" class="form-control-label">Wymagana para:</label>
                                    <input class="" id="editPairsRequired" type="checkbox" required/>
                                </div>
                                <div class="form-group">
                                    <label for="editColor" class="form-control-label">Kolor lekcji:</label>
                                    <input type="color" class="form-control" id="editColor" value="" required/>
                                </div>
                                <div class="form-group">
                                    <label for="editTextColor" class="form-control-label">Kolor tekstu lekcji:</label>
                                    <input type="color" class="form-control" id="editTextColor" value="" required/>
                                </div>
                                <div class="form-group">
                                    <label for="assignedTeachersList" class="form-control-label">Przydzieleni nauczyciele</label>
                                    <ul id="assignedTeachersList">
                                    </ul>
                                </div>
                                <div class="form-group">
                                    <label for="assignNewTeacher" class="form-control-label">Przydziel nauczyciela</label>
                                    <input id="assignNewTeacher" type="text" class="typeahead"/>
                                </div>
                                <div class="form-group">
                                    <label for="published" class="form-control-label">Publikacja dla uczniów</label>
                                    <select id="published" required>
                                        <option value="true">Tak</option>
                                        <option value="false">Nie</option>
                                    </select>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-sm btn-dark" data-dismiss="modal"><spring:message code="Close" text="Close"/></button>
                            <button type="button" class="btn btn-sm btn-default" id="updateLessonButton"><spring:message code="Save" text="Save"/></button>
                        </div>
                    </div>
                </div>
            </div>

        </section>
    </jsp:body>

</tag:genericPage>
