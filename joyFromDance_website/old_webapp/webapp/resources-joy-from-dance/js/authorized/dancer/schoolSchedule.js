import {ServerAddress} from '../../serverAddress.js';

export class SchoolSchedule {

    constructor() {
        this.today = moment().format('YYYY-MM-DD');
        this.timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
        this.bindButtons();
        this.initLessonTypesList();
        this.initCalendar();
    }

    initCalendar() {
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
                    document.getElementById('browseDescription').value = event.description;
                    document.getElementById('browsePairsRequired').checked = false;
                    self.browsedRoomLesson = event;
                    $('#browseLessonModal').modal('show');
                },
                disableResizing: true,
                droppable: true,
                drop: function(dateUTC) {
                    let event = this;
                    let newDraggedEvent = {};
                    let start = moment.tz(dateUTC.format('YYYY-MM-DD[T]HH:mm'), self.timezone);
                    newDraggedEvent.start = start;
                    newDraggedEvent.end = moment(start).add(1, 'hours');
                    newDraggedEvent.color = 'FF0000';
                    newDraggedEvent.textColor = '000000';
                    newDraggedEvent.title = event.dataset.name;
                    newDraggedEvent.id = event.dataset.id;
                    newDraggedEvent.lessonTypeId = event.dataset.lessontypeid;
                    newDraggedEvent.description = event.dataset.description;
                    newDraggedEvent.durationEditable = false;
                    newDraggedEvent.overlap = false;
                    $('#schoolTimetable').fullCalendar('renderEvent', newDraggedEvent, false);
                },
                timezone: self.timezone,
                events: function (start, end, timezone, callback) {
                    let events = [];
                    let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/lesson/proposal' +
                        '?timezone=' + timezone
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
                                    lessonProposal.title = lessonProposal.name;
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

    bindButtons() {
        let self = this;
        document.getElementById('saveMyScheduleProposalButton').addEventListener('click', function () {
            self.saveMyScheduleProposal();
        });
    }

    static refreshTimetable() {
        $('#schoolTimetable').fullCalendar('refetchEvents');
    }

    initLessonTypesList() {
        let self = this;
        document.addEventListener('DOMContentLoaded', function () {
            let schoolId = document.getElementById('schoolId').value;
            let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/' + schoolId + '/lesson/type';
            fetch(url, {
                method: 'get',
                headers: new Headers({
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                    'Accept-Charset': 'UTF-8'
                }),
                credentials: 'include'
            }).catch(function () {
                console.log('error');
            }).then(function (response) {
                if (200 === response.status) {
                    response.json().then(value => SchoolSchedule.createLessonTypes(value));
                } else {
                    console.log('error');
                }
            })
        });
    }

    static createLessonTypes(lessonTypes) {
        document.getElementById('lessonTypes').innerHTML = '';
        for (let i = 0; i < lessonTypes.length; i++) {
            let node = document.createElement("LI");
            node.appendChild(document.createTextNode(lessonTypes[i].name));
            node.setAttribute('draggable', 'true');
            node.setAttribute('value', lessonTypes[i].id);
            node.setAttribute('data-lessonTypeId', lessonTypes[i].id);
            node.setAttribute('data-name', lessonTypes[i].name);
            node.setAttribute('data-title', lessonTypes[i].name);
            node.setAttribute('data-description', lessonTypes[i].description);
            node.setAttribute('data-duration', '01:00:00');
            node.addEventListener('dragstart', function (e) {
                e.dataTransfer.effectAllowed = 'copy';
            });
            $(node).draggable({
                revert: true,
                revertDuration: 0
            });
            document.getElementById('lessonTypes').appendChild(node);
        }
    }

    saveMyScheduleProposal() {
        let dancerLessons = $('#schoolTimetable').fullCalendar('clientEvents');
        let lessonProposals = [];
        for (let dancerLesson of dancerLessons) {
            let lessonProposal = {
                id: dancerLesson.id,
                lessonTypeId: dancerLesson.lessonTypeId,
                start: dancerLesson.start,
                end: dancerLesson.end
            };
            lessonProposals.push(lessonProposal);
        }
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/lesson/proposal';
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
            credentials: 'include',
            body: JSON.stringify({
                lessonProposals: lessonProposals
            })
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                SchoolSchedule.refreshTimetable();
            } else {
                console.log('error');
            }
        });
    }

}

let schoolSchedule = new SchoolSchedule();
