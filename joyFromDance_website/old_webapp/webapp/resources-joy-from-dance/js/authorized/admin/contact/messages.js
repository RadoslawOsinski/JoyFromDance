import {ServerAddress} from '../../../serverAddress.js';
import {I18n} from '../../../i18n/i18n.js';

export class ContactMessages {

    constructor() {
        this.currentPage = 0;
        this.displayedRows = 10;
        this.messagesMap = new Map();
        this.i18n = new I18n();
        this.initPage()
    }

    initPage() {
        this.loadPagedMessages();
        this.bindPageButtons();
    }

    loadPagedMessages() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/contact/message' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + self.displayedRows +
            '&name=' + document.getElementById('nameSearch').value +
            '&email=' + document.getElementById('emailSearch').value
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
            ContactMessages.createErrorRow();
        }).then(function (response) {
            if (200 === response.status) {
                self.messagesMap.clear();
                response.json().then(value => self.createRows(value));
            } else {
                ContactMessages.createErrorRow();
            }
        })
    }

    static createErrorRow() {
        document.getElementById('contactMessagesTableBody').innerHTML = '<tr><td colspan="5">' + this.i18n.i18n('DataFetchingProblem') + '</td></tr>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="5" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += '<tr><td></td>' +
                    '<td>' + value[i].name + '</td>' +
                    '<td>' + value[i].email + '</td>' +
                    '<td>' + value[i].created + '</td>' +
                    '<td>' +
                    '<button type="button" name="previewMessageButton" value="' + value[i].id + '" class="btn-primary btn-sm">' + self.i18n.i18n('Preview') + '</button>' +
                    '<button type="button" name="deleteMessageButton" value="' + value[i].id + '" class="btn btn-danger btn-sm">' + self.i18n.i18n('Delete') + '</button>' +
                    '</td>' +
                    '</tr>';
                self.messagesMap.set(value[i].id, value[i].message)
            }
        }
        document.getElementById('contactMessagesTableBody').innerHTML = rows;
        self.bindDeleteButtons();
    }

    static removeRow(messageId) {
        let row = document.querySelector('tr > td > button[name=deleteMessageButton][value="' + messageId + '"]').parentNode.parentNode;
        row.parentNode.removeChild(row);
    }

    bindDeleteButtons() {
        let self = this;
        document.getElementsByName('previewMessageButton').forEach(
            button => button.addEventListener('click', function () {
                self.previewMessage(button.value)
            })
        );
        document.getElementsByName('deleteMessageButton').forEach(
            button => button.addEventListener('click', function () {
                ContactMessages.deleteMessage(button.value)
            })
        );
    }

    static deleteMessage(messageId) {
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/admin/contact/message/' + messageId;
        let token = document.querySelector("meta[name='_csrf']").content;
        let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
        let headers = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Accept-Charset': 'UTF-8'
        });
        headers.append(csrfHeader, token);
        fetch(url, {
            method: 'DELETE',
            headers: headers,
            credentials: 'include'
        }).then(function (response) {
            if (200 === response.status) {
                ContactMessages.removeRow(messageId);
            }
        })
    }

    previewMessage(messageId) {
        document.getElementById('messagePreview').innerText = this.messagesMap.get(messageId);
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadPagedMessages();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            self.currentPage++;
            self.loadPagedMessages();
        });
        document.getElementById('resetPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadPagedMessages();
        });
        document.getElementById('nameSearch').addEventListener('keyup', function () {
            self.loadPagedMessages();
        });
        document.getElementById('emailSearch').addEventListener('keyup', function () {
            self.loadPagedMessages();
        });
    }

}

let contactMessages = new ContactMessages();
