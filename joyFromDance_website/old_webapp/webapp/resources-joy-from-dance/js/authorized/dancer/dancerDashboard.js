import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';

export class DancerDashboard {

    constructor() {
        this.currentPage = 0;
        this.displayedRows = 10;
        this.timezone = Intl.DateTimeFormat().resolvedOptions().timeZone;
        this.i18n = new I18n();
        this.initPage()
    }

    initPage() {
        this.loadLessons();
        this.bindPageButtons();
    }

    loadLessons() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/lessons' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + self.displayedRows +
            '&searchSchoolName=' + document.getElementById('searchSchoolName').value +
            '&searchRoom=' + document.getElementById('searchRoom').value +
            '&searchDescription=' + document.getElementById('searchDescription').value +
            '&timezone=' + self.timezone
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

    createErrorRow() {
        document.getElementById('activitiesTableBody').innerHTML = '<tr><td colspan="8" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="8" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr>' +
                    '<td></td>' +
                    '<td>' + value[i].schoolName + '</td>' +
                    '<td>' + value[i].roomName + '</td>' +
                    '<td>' + value[i].start + '</td>' +
                    '<td>' + value[i].end + '</td>' +
                    '<td>' + value[i].description + '</td>' +
                    DancerDashboard.renderPairColumn(value[i]) +
                    '<td>' +
                    '<div class="btn-group">' +
                    '<button type="button" class="btn btn-primary btn-sm">Action</button>' +
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
        document.getElementById('activitiesTableBody').innerHTML = rows;
        self.bindAcceptPairButtons();
    }

    bindAcceptPairButtons() {
        let self = this;
        document.getElementsByName('acceptPairButton').forEach(button => {
            button.onclick = function() {
                self.acceptPair(button.value);
            }
        });
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
                self.loadLessons();
            } else {
                console.log('error');
            }
        });
    }

    static renderPairColumn(roomLesson) {
        let otherDancerValue = '';
        let otherDancerValues = [];
        if (roomLesson.pairsRequired) {
            let dancerEmail = document.getElementById('loggedUserEmail').value;
            for (let dancePair of roomLesson.dancerPairs) {
                if (dancePair.email1 === dancerEmail && dancePair.status === 'PENDING_ACCEPTANCE') {
                    otherDancerValues.push('Propozycja: ' + dancePair.email2);
                } else if (dancePair.email1 === dancerEmail) {
                    otherDancerValues.push(dancePair.email2);
                } else if (dancePair.email2 === dancerEmail && dancePair.status === 'PENDING_ACCEPTANCE') {
                    otherDancerValues.push(
                        `<button type="button" name="acceptPairButton" class="btn btn-primary" value="${dancePair.id}"><i class="icon-ok"></i> Akceptuj parę z ${dancePair.email1}</button>`
                    );
                } else if (dancePair.email2 === dancerEmail) {
                    otherDancerValues.push(dancePair.email1);
                }
            }
            if (otherDancerValues.length === 0) {
                otherDancerValue = 'Brak pary';
            } else if (otherDancerValues.length === 1) {
                otherDancerValue = otherDancerValues[0];
            } else {
                otherDancerValues.forEach(value => otherDancerValue += value + '</br>');
            }
        } else {
            otherDancerValue = 'Nie wymagane';
        }
        return `<td>${otherDancerValue}</td>`;
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadLessons();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            self.currentPage++;
            self.loadLessons();
        });
        document.getElementById('resetPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadLessons();
        });
        document.getElementById('searchSchoolName').addEventListener('keyup', function () {
            self.loadLessons();
        });
        document.getElementById('searchRoom').addEventListener('keyup', function () {
            self.loadLessons();
        });
        document.getElementById('searchDescription').addEventListener('keyup', function () {
            self.loadLessons();
        });
    }

}

let dancerDashboard = new DancerDashboard();
