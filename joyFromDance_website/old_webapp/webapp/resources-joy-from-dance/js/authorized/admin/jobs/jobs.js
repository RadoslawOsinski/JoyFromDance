import {ServerAddress} from '../../../serverAddress.js';
import {I18n} from '../../../i18n/i18n.js';

export class Jobs {

    constructor() {
        this.i18n = new I18n();
        this.initPage()
    }

    initPage() {
        let self = this;
        let TEN_SECONDS = 10000;
        setInterval(function () {
            self.loadJobsStats();
        }, TEN_SECONDS);
    }

    loadJobsStats() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/jobs/stats';
        fetch(url, {
            method: 'get',
            headers: new Headers({
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Accept-Charset': 'UTF-8'
            }),
            credentials: 'include'
        }).catch(function () {
            Jobs.createErrorRow();
        }).then(function (response) {
            if (200 === response.status) {
                response.json().then(value => Jobs.createRows(value));
            } else {
                Jobs.createErrorRow();
            }
        })
    }

    static createErrorRow() {
        document.getElementById('jobsTableBody').innerHTML = '<tr><td colspan="9" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    static createRows(value) {
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="8" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr><td></td>' +
                    '<td>' + value[i].name + '</td>' +
                    '<td>' + value[i].runs + '</td>' +
                    '<td>' + value[i].member + '</td>' +
                    `<td>${value[i].lastIdleDuration} ms</td>` +
                    `<td>${value[i].totalIdleDuration} ms</td>` +
                    `<td>${value[i].lastRunDuration} ms</td>` +
                    `<td>${value[i].totalRunDuration} ms</td>` +
                    '<td></td>' +
                    '</tr>';
            }
        }
        document.getElementById('jobsTableBody').innerHTML = rows;
    }

}

let jobs = new Jobs();
