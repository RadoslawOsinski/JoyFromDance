import {ServerAddress} from '../../../serverAddress.js';
import {I18n} from '../../../i18n/i18n.js';

export class SchoolsAdmin {

    constructor() {
        this.currentPage = 0;
        this.displayedRows = 10;
        this.i18n = new I18n();
        this.initPage()
    }

    initPage() {
        this.loadPagedSchools();
        this.bindPageButtons();
    }

    loadPagedSchools() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/school' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + self.displayedRows +
            '&name=' + document.getElementById('nameSearch').value
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

    addSchool() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/school';
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
            body: JSON.stringify({
                name: document.getElementById('schoolName').value
            }),
            credentials: 'include'
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                self.loadPagedSchools();
            } else {
                console.log('error');
            }
        })
    }

    addSchoolAdmin() {
        let self = this;
        let schoolId = document.getElementById('previewSchoolId').value;
        let newSchoolAdministrator = document.getElementById('newSchoolAdministrator').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/school/schoolAdmin?schoolId=' + schoolId + '&newSchoolAdministrator=' + newSchoolAdministrator;
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
                SchoolsAdmin.loadSchoolAdmins(document.getElementById('previewSchoolId').value);
                document.getElementById('newSchoolAdministrator').value = '';
            } else {
                console.log('error');
            }
        })
    }

    createErrorRow() {
        document.getElementById('schoolTableBody').innerHTML = '<tr><td colspan="4" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="4" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr>' +
                    '<td></td>' +
                    '<td>' + value[i].name + '</td>' +
                    '<td>' + value[i].created + '</td>' +
                    '<td>' +
                    '<button type="button" name="previewButton" value="' + value[i].id + '" class="btn btn-gray collapsed btn-animated" data-toggle="collapse" href="#schoolPreview" aria-expanded="false" aria-controls="collapseContent">Preview <i class="fa fa-plus"></i></button>' +
                    '</td>' +
                    '</tr>';
            }
        }
        document.getElementById('schoolTableBody').innerHTML = rows;
        self.bindPreviewButtons();
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadPagedSchools();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            self.currentPage++;
            self.loadPagedSchools();
        });
        document.getElementById('resetPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadPagedSchools();
        });
        document.getElementById('nameSearch').addEventListener('keyup', function () {
            self.loadPagedSchools();
        });
        document.getElementById('addSchoolButton').addEventListener('click', function () {
            self.addSchool();
        });
        document.getElementById('addSchoolAdminButton').addEventListener('click', function () {
            self.addSchoolAdmin();
        });
    }

    bindPreviewButtons() {
        let self = this;
        document.getElementsByName('previewButton').forEach(
            button => button.addEventListener('click', function () {
                SchoolsAdmin.previewSchool(button.value);
                SchoolsAdmin.loadSchoolAdmins(button.value)
            })
        );
    }

    static previewSchool(schoolId) {
        document.getElementById('schoolPreview').classList.remove('hidden-xs-up');
        document.getElementById('previewSchoolId').value = schoolId;
    }

    static loadSchoolAdmins(schoolId) {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/school/schoolAdmin?schoolId=' + schoolId;
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
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                response.json().then(value => self.createSchoolAdminRows(value));
            } else {
                console.log('error');
            }
        })
    }

    static createSchoolAdminRows(value) {
        let self = this;
        let rows = '';
        for (let i = 0; i < value.length; i++) {
            rows += '<li>' + value[i].email + '  ' +
                '<button type="button" name="deleteSchoolAdminButton" value="' + value[i].email + '" class="btn btn-danger">' + this.i18n.i18n('Delete') + '</button>' +
                '</li>';
        }
        document.getElementById('schoolAdministrators').innerHTML = rows;
        self.bindDeleteSchoolAdminButtons();
    }

    static bindDeleteSchoolAdminButtons() {
        let self = this;
        document.getElementsByName('deleteSchoolAdminButton').forEach(
            button => button.addEventListener('click', function () {
                SchoolsAdmin.deleteSchoolAdmin(button.value);
            })
        );
    }

    static deleteSchoolAdmin(email) {
        let self = this;
        let schoolId = document.getElementById('previewSchoolId').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/school/schoolAdmin?schoolId=' + schoolId + '&email=' + email;
        let token = document.querySelector("meta[name='_csrf']").content;
        let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
        let headers = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Accept-Charset': 'UTF-8'
        });
        headers.append(csrfHeader, token);
        fetch(url, {
            method: 'delete',
            headers: headers,
            credentials: 'include'
        }).catch(function () {
            console.log('error');
        }).then(function (response) {
            if (200 === response.status) {
                SchoolsAdmin.loadSchoolAdmins(document.getElementById('previewSchoolId').value);
            } else {
                console.log('error');
            }
        })
    }
}

let schoolAdmin = new SchoolsAdmin();
