import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';

export class Schedule {

    constructor() {
        this.today = moment().format('YYYY-MM-DD');
        this.timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
        this.currentPage = 0;
        this.displayedRows = 10;
        this.browsedRoomLesson = null;
        this.i18n = new I18n();
        this.initPage()
    }


    initPage() {
        this.loadDancerPropositions();
        this.loadLessonTypes();
        this.bindPageButtons();
    }

    loadDancerPropositions() {
        let self = this;
        document.addEventListener('DOMContentLoaded', function () {

            $('#schoolTimetable').fullCalendar({
                header: {
                    center: 'title'
                },
                allDaySlot:false,
                allDayText:false,
                viewRender: function(currentView) {
                    let navigationContainer = currentView.el.parent().prev();
                    $(".fc-prev-button", navigationContainer).prop('disabled', true);
                    $(".fc-prev-button", navigationContainer).toggleClass('fc-state-disabled', true);
                    $(".fc-next-button", navigationContainer).prop('disabled', true);
                    $(".fc-next-button", navigationContainer).toggleClass('fc-state-disabled', true);
                },
                defaultDate: self.today,
                defaultView: 'agendaWeek',
                firstDay: 1,
                weekends: true,
                navLinks: false,
                editable: true,
                nowIndicator: false,
                weekNumbers: false,
                slotLabelFormat: 'HH:mm',
                slotDuration: '00:30:00',
                eventClick: function (event, element) {
                    document.getElementById('browseStartTime').value = moment(event.start).format('HH:mm');
                    document.getElementById('browseEndTime').value = moment(event.end).format('HH:mm');
                    document.getElementById('browseName').value = event.name;
                    document.getElementById('browseDescription').value = event.description;
                    // document.getElementById('browsePairsRequired').checked = false;
                    self.browsedRoomLesson = event;
                    document.getElementById('interestedDancers').innerHTML = '';
                    for (let email of event.emails) {
                        $('#interestedDancers').append('<li>' + email + '</li>');
                    }
                    $('#browseLessonModal').modal('show');
                },
                disableResizing: true,
                droppable: false,
                timezone: self.timezone,
                events: function (start, end, timezone, callback) {
                    let events = [];
                    let todoSchoolId = '0';
                    let proposalMinDancers = document.getElementById('proposalMinDancers').value;
                    let proposalLessonTypeName = document.getElementById('proposalLessonTypeName').value;
                    let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/' + todoSchoolId  + '/lesson/proposal' +
                        '?timezone=' + timezone +
                        '&minDancers=' + proposalMinDancers +
                        '&lessonTypeName=' + proposalLessonTypeName
                    ;
                    let token = document.querySelector("meta[name='_csrf']").content;
                    let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
                    let headers = new Headers({
                        'Content-Type': 'application/json',
                        'Accept': 'application/json',
                        'Accept-Charset': 'UTF-8'
                    });
                    headers.append(csrfHeader, token);
                    fetch(url, {
                        method: 'get',
                        headers: headers,
                        credentials: 'include'
                    }).catch(function (e) {
                        console.log('error' + e);
                    }).then(function (response) {
                        if (200 === response.status) {
                            response.json().then(lessonProposals => {
                                for (let lessonProposal of lessonProposals) {
                                    lessonProposal.title = lessonProposal.name + ' (' + lessonProposal.emails.length + ')';
                                    events.push(lessonProposal);
                                }
                                callback(events);
                            });
                        } else {
                            console.log('error');
                        }
                    });
                }
            });
        });
    }

    static refreshTimetable() {
        $('#schoolTimetable').fullCalendar('refetchEvents');
    }

    loadLessonTypes() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/lesson/type' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + self.displayedRows +
            '&lessonTypeNameSearch=' + document.getElementById('lessonTypeNameSearch').value
        ;
        fetch(url, {
            method: 'get',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Accept-Charset': 'UTF-8'
            }),
            credentials: 'include'
        }).catch(function () {
            self.createErrorRow();
        }).then(function (response) {
            if (200 === response.status) {
                response.json().then(value => self.createRows(value));
            } else {
                self.createErrorRow();
            }
        })
    }

    addLessonType() {
        let self = this;
        let lessonTypeName = document.getElementById('lessonTypeName').value;
        let lessonTypeDescription = document.getElementById('lessonTypeDescription').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/lesson/type' +
            '?lessonTypeName=' + lessonTypeName +
            '&lessonTypeDescription=' + lessonTypeDescription;
        let token = document.querySelector("meta[name='_csrf']").content;
        let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
        let headers = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Accept-Charset': 'UTF-8'
        });
        headers.append(csrfHeader, token);
        fetch(url, {
            method: 'post',
            headers: headers,
            credentials: 'include'
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                self.loadLessonTypes();
                document.getElementById('lessonTypeName').value = '';
                document.getElementById('lessonTypeDescription').value = '';
            } else {
                console.log('error');
            }
        })
    }

    createErrorRow() {
        document.getElementById('lessonTypesBody').innerHTML = '<tr><td colspan="3" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="3" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr>' +
                    '<td></td>' +
                    '<td>' + value[i].name + '</td>' +
                    '<td>' +
                    '<div class="btn-group">' +
                    '<button type="button" class="btn btn-primary btn-sm">Action</button>\n' +
                    '<button type="button" class="btn btn-primary btn-sm dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
                    '<span class="sr-only">Toggle Dropdown</span>' +
                    '</button>' +
                    '<div class="dropdown-menu">' +
                    '<button type="button" class="dropdown-item border-bottom-clear"><i class="icon-cancel-circled"></i> ' + this.i18n.i18n('Delete') + '</button>' +
                    '</div>' +
                    '</div>' +
                    '</td>' +
                    '</tr>';
            }
        }
        document.getElementById('lessonTypesBody').innerHTML = rows;
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadLessonTypes();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            self.currentPage++;
            self.loadLessonTypes();
        });
        document.getElementById('resetPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadLessonTypes();
        });
        document.getElementById('lessonTypeNameSearch').addEventListener('keyup', function () {
            self.loadLessonTypes();
        });
        document.getElementById('addLessonTypeButton').addEventListener('click', function () {
            self.addLessonType();
        });
        document.getElementById('proposalMinDancers').addEventListener('change', function () {
            Schedule.refreshTimetable();
        });
        document.getElementById('proposalLessonTypeName').addEventListener('keyup', function () {
            Schedule.refreshTimetable();
        });
    }
}

let schedule = new Schedule();
