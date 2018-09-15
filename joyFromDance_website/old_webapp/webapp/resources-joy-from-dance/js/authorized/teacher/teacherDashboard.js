import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';

export class TeacherDashboard {

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
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/teacher/lessons' +
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
        document.getElementById('activitiesTableBody').innerHTML = '<tr><td colspan="7" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="7" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr>' +
                    '<td></td>' +
                    '<td>' + value[i].schoolName + '</td>' +
                    '<td>' + value[i].roomName + '</td>' +
                    '<td>' + value[i].start + '</td>' +
                    '<td>' + value[i].end + '</td>' +
                    '<td>' + value[i].description + '</td>' +
                    '<td>' +
                    '<div class="btn-group">' +
                    '<button type="button" class="btn btn-primary btn-sm">Action</button>' +
                    '<button type="button" class="btn btn-primary btn-sm dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
                    '<span class="sr-only">Toggle Dropdown</span>' +
                    '</button>' +
                    '<div class="dropdown-menu">' +
                    '<button type="button" class="dropdown-item border-bottom-clear"><i class="icon-cancel-circled"></i> Delete todo!</button>' +
                    '</div>' +
                    '</div>' +
                    '</td>' +
                    '</tr>';
            }
        }
        document.getElementById('activitiesTableBody').innerHTML = rows;
        //     self.bindPreviewButtons();
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

let teacherDashboard = new TeacherDashboard();
