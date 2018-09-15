import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';

export class SchoolTimetables {

    constructor() {
        this.today = moment().format('YYYY-MM-DD');
        this.defaultLessonColor = '#2bb5ed';
        this.defaultLessonTextColor = '#9911ed';
        this.browsedRoomLesson = null;
        this.timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
        this.i18n = new I18n();
        this.bindButtons();
        this.initRoomsSelect();
        this.initCalendar();
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
                editable: false,
                nowIndicator: true,
                weekNumbers: true,
                slotLabelFormat: 'HH:mm',
                slotDuration: '00:30:00',
                eventClick: function (event, element) {
                    document.getElementById('browseStartTime').value = moment(event.start).tz(self.timezone).format('YYYY-MM-DD[T]HH:mm');
                    document.getElementById('browseEndTime').value = moment(event.end).tz(self.timezone).format('YYYY-MM-DD[T]HH:mm');
                    document.getElementById('browseDescription').value = event.description;
                    document.getElementById('browsePairsRequired').checked = event.pairsRequired;
                    self.browsedRoomLesson = event;
                    self.displayAssignedTeachersInLesson();
                    self.displayAssignedDancersInLesson();
                    self.hideOrShowAssignToLessonButton();
                    $('#browseLessonModal').modal('show');
                },
                timezone: self.timezone,
                events: function (start, end, timezone, callback) {
                    let schoolRoomId = document.getElementById('schoolRoomId').value;
                    let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/school/room/timetable' +
                        '?schoolRoomId=' + Number(schoolRoomId) +
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
                                    roomLesson.title = roomLesson.description;
                                    events.push(roomLesson);
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
        document.getElementById('assignToLesson').addEventListener('click', function () {
            self.assignDancerToLesson();
        });
    }

    static refreshTimetable() {
        $('#roomTimetable').fullCalendar('refetchEvents');
    }

    displayAssignedTeachersInLesson() {
        let self = this;
        document.getElementById('assignedTeachersList').innerHTML = '';
        for (let assignedTeacher of self.browsedRoomLesson.assignedTeachers) {
            SchoolTimetables.displayAssignedTeacherInLesson(assignedTeacher);
        }
    }

    static displayAssignedTeacherInLesson(teacherEmail) {
        $('#assignedTeachersList').append('<li>' + teacherEmail + '</li>');
    }

    displayAssignedDancersInLesson() {
        let self = this;
        if (self.browsedRoomLesson.pairsRequired) {
            self.displayAssignedDancersInPairs();
        } else {
            self.displaySingleAssignedDancers();
        }
    }

    displayAssignedDancersInPairs() {
        let self = this;
        document.getElementById('lessonPairs').classList.remove('d-none');
        if (!document.getElementById('lessonDancers').classList.contains('d-none')) {
            document.getElementById('lessonDancers').classList.add('d-none');
        }
        document.getElementById('dancerPairsTableBody').innerHTML = '';
        self.displayRowForDancerPairs();
        for (let assignedDancer of self.browsedRoomLesson.assignedDancers) {
            if (!self.dancerIsInPair(assignedDancer.email)) {
                self.displayRowForAssignment(assignedDancer);
            }
        }
        this.bindAcceptPairButtons();
    }

    bindAcceptPairButtons() {
        let self = this;
        document.getElementsByName('acceptPairButton').forEach(button => {
            button.onclick = function() {
                self.acceptPair(button.value);
            }
        })
    }

    acceptPair(pairId) {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/lesson/pair/' + pairId + '/acceptance';
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
                self.refreshBrowsedLesson();
            } else {
                console.log('error');
            }
        });
    }

    displayRowForDancerPairs() {
        let self = this;
        for (let dancerPair of self.browsedRoomLesson.dancerPairs) {
            let newRow = document.getElementById('dancerPairsTableBody').insertRow(dancerPairs.rowIndex + 1);
            newRow.insertCell(0);
            newRow.insertCell(1).innerText = dancerPair.email1;
            newRow.insertCell(2).innerText = dancerPair.email2;
            if ('PENDING_ACCEPTANCE' === dancerPair.status && dancerPair.email1 === document.getElementById('loggedUserEmail').value) {
                newRow.insertCell(3).innerText = this.i18n.i18n('AcceptanceOfTheOtherPerson');
            } else if ('PENDING_ACCEPTANCE' === dancerPair.status && dancerPair.email2 === document.getElementById('loggedUserEmail').value) {
                newRow.insertCell(3).innerHTML = `<button name="acceptPairButton" type="button" value="${dancerPair.id}" class="btn btn-primary">${this.i18n.i18n('AcceptPair')}</button>`;
            } else if ('AGREED' === dancerPair.status) {
                newRow.insertCell(3).innerText = this.i18n.i18n('AgreedPair');
            } else {
                newRow.insertCell(3).innerText = dancerPair.status;
            }
        }
    }

    displayRowForAssignment(assignedDancer) {
        let self = this;
        let newRow = document.getElementById('dancerPairsTableBody').insertRow(dancerPairs.rowIndex + 1);
        newRow.setAttribute('data-dancer-without-pair', assignedDancer.email);
        newRow.insertCell(0);
        if (document.getElementById('loggedUserEmail').value === assignedDancer.email) {
            let dancerCell = newRow.insertCell(1);
            dancerCell.innerText = assignedDancer.email;
            dancerCell.setAttribute('draggable', 'true');
            dancerCell.addEventListener('dragstart', function (e) {
                e.dataTransfer.effectAllowed = 'link';
                e.dataTransfer.setData('application/json', JSON.stringify({email: assignedDancer.email}));
            });
            newRow.insertCell(2).innerText = '---';
            newRow.insertCell(3).innerText = this.i18n.i18n('MissingPair');
        } else {
            newRow.insertCell(1).innerText = assignedDancer.email;
            let otherDancerCell = newRow.insertCell(2);
            otherDancerCell.innerText = this.i18n.i18n('AssignPairByDroppingYourEmail');
            otherDancerCell.addEventListener('dragover', function (e) {
                if (e.preventDefault) {
                    e.preventDefault();
                }
                e.dataTransfer.dropEffect = 'link';
                return false;
            });
            otherDancerCell.addEventListener('drop', function (e) {
                if (e.stopPropagation) {
                    e.stopPropagation();
                }
                let draggedDancer = JSON.parse(e.dataTransfer.getData('application/json'));
                self.assignPair(assignedDancer.email, draggedDancer.email);
                return false;
            });
            newRow.insertCell(3).innerText = this.i18n.i18n('MissingPair');
        }
    }

    assignPair(otherDancerEmail, dancerEmail) {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/lesson/' + self.browsedRoomLesson.id + '/pair' +
            '?dancerEmail=' + dancerEmail +
            '&otherDancerEmail=' + otherDancerEmail
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
            method: 'post',
            headers: headers,
            credentials: 'include'
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                self.refreshBrowsedLesson();
            } else {
                console.log('error');
            }
        });
    }

    refreshBrowsedLesson() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/school/room/timetable/' + self.browsedRoomLesson.id +
            '?timezone=' + self.timezone
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
                response.json().then(roomLesson => {
                    self.browsedRoomLesson = roomLesson;
                    self.displayAssignedDancersInLesson();
                });
            } else {
                console.log('error');
            }
        });
    }

    dancerIsInPair(assignedDancerEmail) {
        let self = this;
        let dancerIsInPair = false;
        for (let dancerPair of self.browsedRoomLesson.dancerPairs) {
            if (assignedDancerEmail === dancerPair.email1 || assignedDancerEmail === dancerPair.email2) {
                dancerIsInPair = true;
                break;
            }
        }
        return dancerIsInPair;
    }

    displaySingleAssignedDancers() {
        let self = this;
        document.getElementById('lessonDancers').classList.remove('d-none');
        if (!document.getElementById('lessonPairs').classList.contains('d-none')) {
            document.getElementById('lessonPairs').classList.add('d-none');
        }
        document.getElementById('assignedDancersList').innerHTML = '';
        for (let assignedDancer of self.browsedRoomLesson.assignedDancers) {
            SchoolTimetables.displayAssignedDancerInLesson(assignedDancer);
        }
    }

    static displayAssignedDancerInLesson(assignedDancer) {
        $('#assignedDancersList').append(`<li>${assignedDancer.email}</li>`);
    }

    initRoomsSelect() {
        let self = this;
        document.addEventListener('DOMContentLoaded', function () {
            let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/school/room' +
                '?schoolId=' + document.getElementById('schoolId').value
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
                    response.json().then(value => self.createRoomRows(value));
                } else {
                    console.log('error');
                }
            })
        });

        document.getElementById('schoolRoomId').addEventListener('change', function () {
            SchoolTimetables.refreshTimetable();
        });
    }

    createRoomRows(rooms) {
        let rows = '';
        if (rooms.length === 1) {
            rows += `<option value="0">${this.i18n.i18n('ChooseRoom')}</option>`;
            rows += `<option value="${rooms[i].id}" selected="selected">${rooms[i].name}</option>`;
        } else {
            rows += `<option value="0" selected="selected">${this.i18n.i18n('ChooseRoom')}</option>`;
            for (let i = 0; i < rooms.length; i++) {
                rows += `<option value="${rooms[i].id}">${rooms[i].name}</option>`;
            }
        }
        document.getElementById('schoolRoomId').innerHTML = rows;
    }

    assignDancerToLesson() {
        let self = this;
        let dancerEmail = document.getElementById('loggedUserEmail').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/lessons' +
            '?lessonId=' + self.browsedRoomLesson.id +
            '&dancerEmail=' + dancerEmail
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
            method: 'post',
            headers: headers,
            credentials: 'include',
            body: JSON.stringify({
                lessonId: self.browsedRoomLesson.id,
                dancerEmail: dancerEmail
            })
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                SchoolTimetables.refreshTimetable();
            } else {
                console.log('error');
            }
        });
        self.browsedRoomLesson = null;
        $('#browseLessonModal').modal('hide');
    }

    hideOrShowAssignToLessonButton() {
        let self = this;
        let userAlreadyAssigned = false;
        let loggedUserEmail = document.getElementById('loggedUserEmail').value;
        for (let assignedDancer of self.browsedRoomLesson.assignedDancers) {
            if (assignedDancer.email === loggedUserEmail) {
                userAlreadyAssigned = true;
            }
        }
        if (userAlreadyAssigned) {
            if (!document.getElementById('assignToLesson').classList.contains('hidden-xs-up')) {
                document.getElementById('assignToLesson').classList.add('hidden-xs-up');
            }
        } else {
            document.getElementById('assignToLesson').classList.remove('hidden-xs-up');
        }
    }

}

let schoolTimetables = new SchoolTimetables();
