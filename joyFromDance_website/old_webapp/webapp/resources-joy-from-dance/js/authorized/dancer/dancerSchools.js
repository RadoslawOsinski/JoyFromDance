import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';

export class DancerSchools {

    constructor() {
        this.currentPage = 0;
        this.displayedRows = 10;
        this.newDancerSchool = null;
        this.i18n = new I18n();
        this.initPage()
    }


    initPage() {
        this.loadDancerSchools();
        this.bindPageButtons();
    }

    loadDancerSchools() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/school' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + self.displayedRows +
            '&searchSchoolName=' + document.getElementById('searchSchoolName').value
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
        document.getElementById('schoolTableBody').innerText = '<tr><td colspan="6" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="6" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr>' +
                    '<td></td>' +
                    '<td>' + value[i].schoolName + '</td>' +
                    '<td>email szkoly</td>' +
                    '<td>telefon szkoly</td>' +
                    '<td>social buttons szkoly</td>' +
                    '<td>' +
                    '<div class="btn-group">' +
                    '<button type="button" class="btn btn-primary btn-sm">Action</button>' +
                    '<button type="button" class="btn btn-primary btn-sm dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
                    '<span class="sr-only">Toggle Dropdown</span>' +
                    '</button>' +
                    '<div class="dropdown-menu">' +
                    '<a class="dropdown-item" href="' + ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/dancer/school/' + value[i].schoolId + '/timetables"><i class="fa fa-cog pr-20"></i>Grafik zajęć</a>' +
                    '<a class="dropdown-item" href="' + ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/dancer/school/' + value[i].schoolId + '/schedule"><i class="fa fa-cog pr-20"></i>Grafik zajęć - głosowanie</a>' +
                    '<button type="button" class="dropdown-item border-bottom-clear"><i class="icon-cancel-circled"></i> ' + this.i18n.i18n('Delete') + '</button>' +
                    '</div>' +
                    '</div>' +
                    '</td>' +
                    '</tr>';
            }
        }
        document.getElementById('schoolTableBody').innerHTML = rows;
        //     self.bindPreviewButtons();
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadDancerSchools();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            self.currentPage++;
            self.loadDancerSchools();
        });
        document.getElementById('resetPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadDancerSchools();
        });
        document.getElementById('searchSchoolName').addEventListener('keyup', function () {
            self.loadDancerSchools();
        });
        // document.getElementById('addNewSchoolForDancerButton').addEventListener('click', function () {
        //     self.addNewSchoolForDancer();
        // });
        $('#assignMySchool').typeahead({
            hint: true,
            highlight: true,
            minLength: 1,
            source: function (query, processSync, processAsync) {
                let items = [];
                let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school' +
                    '?currentPage=0' +
                    '&displayedRows=10' +
                    '&name=' + query
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
                                items.push({id: val.id, name: val.name});
                            }
                            processSync(items);
                        });
                    }
                });
            },
            updater: function (item) {
                self.newDancerSchool = item;
                return item.name;
            }
        })
    }

    addNewSchoolForDancer() {
        let self = this;
        if (self.newDancerSchool !== null) {
            let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/school' +
                '?schoolId=' + self.newDancerSchool.id
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
                    self.loadDancerSchools();
                    self.newDancerSchool = null;
                    $('#assignMySchool').val('')
                } else {
                    console.log('error');
                }
            })
        }
    }

}

let dancerSchools = new DancerSchools();
