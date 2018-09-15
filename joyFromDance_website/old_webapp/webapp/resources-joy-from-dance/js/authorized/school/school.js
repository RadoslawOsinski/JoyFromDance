import {ServerAddress} from '../../serverAddress.js';

export class Schools {

    constructor() {
        this.currentPage = 0;
        this.displayedRows = 10;
        this.initPage()
    }

    initPage() {
        this.loadPagedTeachers();
        this.bindPageButtons();
    }

    loadPagedTeachers() {
        let self = this;
        let url =  ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/teachers' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + self.displayedRows +
            '&firstNameSearch=' + document.getElementById('firstNameSearch').value +
            '&lastNameSearch=' + document.getElementById('lastNameSearch').value +
            '&emailSearch=' + document.getElementById('emailSearch').value
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
            Schools.createErrorRow();
        }).then(function (response) {
            if (200 === response.status) {
                response.json().then(value => self.createRows(value));
            } else {
                Schools.createErrorRow();
            }
        })
    }

    addTeacher() {
        let self = this;
        let teacherEmail = document.getElementById('teacherEmail').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/teacher?teacherEmail=' + teacherEmail;
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
                self.loadPagedTeachers();
                document.getElementById('teacherEmail').value = '';
            } else {
                console.log('error');
            }
        })
    }

    static createErrorRow() {
        document.getElementById('teachersTableBody').innerHTML = '<tr><td colspan="5" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="5" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr>' +
                    '<td></td>' +
                    '<td>' + value[i].firstName + '</td>' +
                    '<td>' + value[i].lastName + '</td>' +
                    '<td>' + value[i].email + '</td>' +
                    '<td>' +
                    'todo!' +
                    //             '<button type="button" name="previewButton" value="' + value[i].id + '" class="btn btn-gray collapsed btn-animated" data-toggle="collapse" href="#schoolPreview" aria-expanded="false" aria-controls="collapseContent">Preview <i class="fa fa-plus"></i></button>' +
                    '</td>' +
                    '</tr>';
            }
        }
        document.getElementById('teachersTableBody').innerHTML = rows;
    //     self.bindPreviewButtons();
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadPagedTeachers();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            self.currentPage++;
            self.loadPagedTeachers();
        });
        document.getElementById('resetPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadPagedTeachers();
        });
        document.getElementById('firstNameSearch').addEventListener('keyup', function () {
            self.loadPagedTeachers();
        });
        document.getElementById('lastNameSearch').addEventListener('keyup', function () {
            self.loadPagedTeachers();
        });
        document.getElementById('emailSearch').addEventListener('keyup', function () {
            self.loadPagedTeachers();
        });
        document.getElementById('addTeacherButton').addEventListener('click', function () {
            self.addTeacher();
        });
    }
}

let schools = new Schools();
