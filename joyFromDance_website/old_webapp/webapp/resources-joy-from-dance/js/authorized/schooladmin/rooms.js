import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';

export class SchoolAdminRooms {

    constructor() {
        this.currentPage = 0;
        this.displayedRows = 10;
        this.i18n = new I18n();
        this.initPage()
    }


    initPage() {
        this.loadPagedRooms();
        this.bindPageButtons();
    }

    loadPagedRooms() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/room' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + self.displayedRows +
            '&roomNameSearch=' + document.getElementById('roomNameSearch').value
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
            SchoolAdminRooms.createErrorRow();
        }).then(function (response) {
            if (200 === response.status) {
                response.json().then(value => self.createRows(value));
            } else {
                SchoolAdminRooms.createErrorRow();
            }
        })
    }

    addRoom() {
        let self = this;
        let roomName = document.getElementById('roomName').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/room?roomName=' + roomName;
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
                self.loadPagedRooms();
                document.getElementById('roomName').value = '';
            } else {
                console.log('error');
            }
        })
    }

    static createErrorRow() {
        document.getElementById('roomTableBody').innerHTML = '<tr><td colspan="3" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
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
                    '<a class="dropdown-item" href="' + ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/schooladmin/room/' + value[i].id + '/timetable"><i class="fa fa-cog pr-20"></i>Grafik godzin</a>' +
                    '<button type="button" class="dropdown-item border-bottom-clear"><i class="icon-cancel-circled"></i> ' + this.i18n.i18n('Delete') + '</button>' +
                    '</div>' +
                    '</div>' +
                    '</td>' +
                    '</tr>';
            }
        }
        document.getElementById('roomTableBody').innerHTML = rows;
        //     self.bindPreviewButtons();
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadPagedRooms();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            self.currentPage++;
            self.loadPagedRooms();
        });
        document.getElementById('resetPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadPagedRooms();
        });
        document.getElementById('roomNameSearch').addEventListener('keyup', function () {
            self.loadPagedRooms();
        });
        document.getElementById('addRoomButton').addEventListener('click', function () {
            self.addRoom();
        });
    }
}

let schoolAdminRooms = new SchoolAdminRooms();
