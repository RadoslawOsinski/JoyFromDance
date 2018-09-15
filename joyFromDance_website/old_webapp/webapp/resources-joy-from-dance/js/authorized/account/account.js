import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';

export class Account {

    constructor() {
        this.i18n = new I18n();
        this.initPage()
    }

    initPage() {
        this.bindPageButtons();
    }

    updateBasicAccountInfoButton() {
        let self = this;
        let firstName = document.getElementById('firstName').value;
        let lastName = document.getElementById('lastName').value;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/dancer/account?firstName=' + firstName + "&lastName=" + lastName;
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
            Account.showFailedBasicAccountUpdateMessage();
        }).then(function (response) {
            if (200 === response.status) {
                Account.showSuccessfulBasicAccountUpdateMessage();
                location.reload();
            } else {
                Account.showFailedBasicAccountUpdateMessage();
            }
        })
    }

    static showFailedBasicAccountUpdateMessage() {
        if (!document.getElementById('failedBasicAccountUpdateMessage').classList.contains('hidden-xs-up')) {
            document.getElementById('failedBasicAccountUpdateMessage').classList.add('hidden-xs-up');
        }
        document.getElementById('failedBasicAccountUpdateMessage').classList.remove('hidden-xs-up');
    }

    static showSuccessfulBasicAccountUpdateMessage() {
        if (!document.getElementById('successfulBasicAccountUpdateMessage').classList.contains('hidden-xs-up')) {
            document.getElementById('successfulBasicAccountUpdateMessage').classList.add('hidden-xs-up');
        }
        document.getElementById('successfulBasicAccountUpdateMessage').classList.remove('hidden-xs-up');
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('updateBasicAccountInfoButton').addEventListener('click', function () {
            self.updateBasicAccountInfoButton();
        });
    }

}

let account = new Account();
