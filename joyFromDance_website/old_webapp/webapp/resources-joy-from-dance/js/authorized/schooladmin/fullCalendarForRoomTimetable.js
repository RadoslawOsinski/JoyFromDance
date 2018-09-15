import {ServerAddress} from '../../serverAddress.js';

export class FullCalendarForRoomTimetable {

    constructor() {
        this.today = moment().format('YYYY-MM-DD');
        this.defaultLessonColor = '#2bb5ed';
        this.defaultLessonTextColor = '#9911ed';
        this.editedRoomLesson = null;
        this.assignedTeachers = [];
        this.timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
        this.initCalendar();
        this.bindButtons();
    }

    initCalendar() {
        let self = this;
        document.addEventListener('DOMContentLoaded', function () {

            $('#roomTimetable').fullCalendar({
                customButtons: {
                    resetTimetableButton: {
                        text: 'Reset',
                        click: function () {
                            alert('clicked reset button!');
                        }
                    }
                },
                header: {
                    left: 'prev,next today',
                    center: 'title',
                    right: 'month,agendaWeek,agendaDay,listWeek'
                },
                footer: {
                    left: '',
                    center: '',
                    right: 'resetTimetableButton'
                },
                defaultDate: self.today,
                defaultView: 'agendaWeek',
                firstDay: 1,
                weekends: true,
                navLinks: true,
                editable: true,
                nowIndicator: true,
                weekNumbers: true,
                slotLabelFormat: 'HH:mm',
                slotDuration: '00:30:00',
                eventClick: function (event, element) {
                    document.getElementById('editStartTime').value = moment(event.start).tz(self.timezone).format('YYYY-MM-DD[T]HH:mm');
                    document.getElementById('editEndTime').value = moment(event.end).tz(self.timezone).format('YYYY-MM-DD[T]HH:mm');
                    document.getElementById('editDescription').value = event.title;
                    document.getElementById('editColor').value = event.color;
                    document.getElementById('editTextColor').value = event.textColor;
                    document.getElementById('editPairsRequired').checked = event.pairsRequired;
                    self.editedRoomLesson = event;
                    self.assignedTeachers = event.assignedTeachers;
                    self.displayAssignedTeachersInLesson();
                    $('#editLessonModal').modal('show');
                },
                dayClick: function (date, jsEvent, view) {
                    document.getElementById('startTime').value = date.format('YYYY-MM-DD[T]HH:mm');
                    document.getElementById('endTime').value = self.addHour(date).format('YYYY-MM-DD[T]HH:mm');
                    document.getElementById('description').value = '';
                    document.getElementById('pairsRequired').checked = true;
                    document.getElementById('color').value = self.defaultLessonColor;
                    document.getElementById('textColor').value = self.defaultLessonTextColor;
                    $('#addLessonModal').modal('show');
                },
                timezone: self.timezone,
                events: function (start, end, timezone, callback) {
                    let roomId = document.getElementById('roomId').value;
                    let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/room/timetable?' +
                        'roomId=' + Number(roomId) +
                        '&start=' + moment.utc(start) +
                        '&end=' + moment.utc(end) +
                        '&timezone=' + timezone
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
                            response.json().then(roomLessons => {
                                let events = [];
                                for (let roomLesson of roomLessons) {
                                    events.push({
                                        id: roomLesson.id,
                                        title: roomLesson.description,
                                        start: roomLesson.start,
                                        end: roomLesson.end,
                                        pairsRequired: roomLesson.pairsRequired,
                                        color: roomLesson.color,
                                        textColor: roomLesson.textColor,
                                        assignedTeachers: roomLesson.assignedTeachers
                                    })
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

    addHour(date) {
        return moment(date).add(1, 'hours');
    }

    bindButtons() {
        let self = this;
        document.getElementById('saveNewLessonButton').addEventListener('click', function () {
            self.addNewLesson();
            $('#addLessonModal').modal('hide');
        });
        document.getElementById('updateLessonButton').addEventListener('click', function () {
            self.updateLesson();
            $('#editLessonModal').modal('hide');
        });
        $('#assignNewTeacher').typeahead({
            hint: true,
            highlight: true,
            minLength: 1,
            source: function (query, processSync, processAsync) {
                let items = [];
                let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/teachers' +
                    '?currentPage=0' +
                    '&displayedRows=10' +
                    '&firstNameSearch=' +
                    '&lastNameSearch=' +
                    '&emailSearch=' + query
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
                    console.log('error');
                }).then(function (response) {
                    if (200 === response.status) {
                        response.json().then(value => {
                            for (let val of value) {
                                items.push(val.email);
                            }
                            processSync(items);
                        });
                    }
                });
            },
            updater: function (item) {
                FullCalendarForRoomTimetable.displayAssignedTeacherInLesson(item);
                self.assignedTeachers.push(item);
                return '';
            }
        })
    }

    addNewLesson() {
        let self = this;
        let roomId = document.getElementById('roomId').value;
        let startTime = moment.tz(document.getElementById('startTime').value, self.timezone);
        let endTime = moment.tz(document.getElementById('endTime').value, self.timezone);
        let description = document.getElementById('description').value;
        let pairsRequired = document.getElementById('pairsRequired').checked;
        let color = document.getElementById('color').value;
        let textColor = document.getElementById('textColor').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/room/timetable';
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
                roomId: roomId,
                start: startTime,
                end: endTime,
                description: description,
                pairsRequired: pairsRequired,
                color: color,
                textColor: textColor
            })
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                FullCalendarForRoomTimetable.refreshTimetable();
            } else {
                console.log('error');
            }
        })
    }

    updateLesson() {
        let self = this;
        let roomId = document.getElementById('roomId').value;
        let startTime = moment.tz(document.getElementById('editStartTime').value, self.timezone);
        let endTime = moment.tz(document.getElementById('editEndTime').value, self.timezone);
        let description = document.getElementById('editDescription').value;
        let pairsRequired = document.getElementById('editPairsRequired').checked;
        let color = document.getElementById('editColor').value;
        let textColor = document.getElementById('editTextColor').value;
        let published = document.getElementById('published').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/rest/school/room/timetable/' + self.editedRoomLesson.id;
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
                roomId: roomId,
                start: startTime,
                end: endTime,
                description: description,
                pairsRequired: pairsRequired,
                color: color,
                textColor: textColor,
                published: published
            })
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                FullCalendarForRoomTimetable.refreshTimetable();
                // $('#roomTimetable').fullCalendar('updateEvent', self.editedRoomLesson);
                self.editedRoomLesson = null;
            } else {
                console.log('error');
            }
        });
        self.assignTeachersToEditedLesson();
    }

    assignTeachersToEditedLesson() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/room/timetable/' + self.editedRoomLesson.id + '/teachers';
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
                emails: self.assignedTeachers
            })
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                self.assignedTeachers = [];
            } else {
                console.log('error');
            }
        });
    }

    static refreshTimetable() {
        $('#roomTimetable').fullCalendar('refetchEvents');
    }

    displayAssignedTeachersInLesson() {
        let self = this;
        document.getElementById('assignedTeachersList').innerHTML = '';
        for (let assignedTeacher of self.assignedTeachers) {
            FullCalendarForRoomTimetable.displayAssignedTeacherInLesson(assignedTeacher);
        }
    }

    static displayAssignedTeacherInLesson(teacherEmail) {
        $('#assignedTeachersList').append('<li>' + teacherEmail + '</li>');
    }

}
