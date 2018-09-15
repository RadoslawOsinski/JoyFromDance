import {ServerAddress} from '../serverAddress.js';

export class AboutContactForm {

    constructor() {
        this.initContactFormBinding();
    }

    initContactFormBinding() {
        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById('aboutContactName').addEventListener('keyup', AboutContactForm.validateContactForm);
            document.getElementById('aboutContactEmail').addEventListener('keyup', AboutContactForm.validateContactForm);
            document.getElementById('aboutContactMessage').addEventListener('keyup', AboutContactForm.validateContactForm);
            document.getElementById('aboutContactSendButton').addEventListener('click', AboutContactForm.submitContactForm);
        });
    };

    static validateContactForm() {
        if (AboutContactForm.isNameValid() && AboutContactForm.isEmailValid() && AboutContactForm.isMessageValid()) {
            AboutContactForm.enableSubmitContactButton();
        } else {
            AboutContactForm.disableSubmitContactButton();
        }
    }

    static enableSubmitContactButton() {
        document.getElementById('aboutContactSendButton').removeAttribute('disabled');
    }

    static disableSubmitContactButton() {
        if (!document.getElementById('aboutContactSendButton').hasAttribute('disabled')) {
            document.getElementById('aboutContactSendButton').setAttribute('disabled', 'disabled');
        }
    }

    static isMessageValid() {
        let messageValue = document.getElementById('aboutContactMessage').value;
        return messageValue.length > 10;
    }

    static isEmailValid() {
        let email = document.getElementById('aboutContactEmail').value;
        let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return re.test(email);
    }

    static isNameValid() {
        let nameValue = document.getElementById('aboutContactName').value;
        return nameValue.length > 2;
    }

    static showFailedMessageSendMessage() {
        if (!document.getElementById('successfulSendContractMessage').classList.contains('hidden-xs-up')) {
            document.getElementById('successfulSendContractMessage').classList.add('hidden-xs-up');
        }
        document.getElementById('failedSendContractMessage').classList.remove('hidden-xs-up');
    }

    static showMessageSendWasSuccessfulMessage() {
        if (!document.getElementById('failedSendContractMessage').classList.contains('hidden-xs-up')) {
            document.getElementById('failedSendContractMessage').classList.add('hidden-xs-up');
        }
        document.getElementById('successfulSendContractMessage').classList.remove('hidden-xs-up');
    }

    static submitContactForm() {
        fetch(ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/rest/contact/message', {
            method: 'post',
            body: JSON.stringify({
                name: document.getElementById('aboutContactName').value,
                email: document.getElementById('aboutContactEmail').value,
                message: document.getElementById('aboutContactMessage').value
            }),
            headers: new Headers({
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }),
            mode: 'cors',
            cache: 'default'
        }).catch(function () {
            AboutContactForm.showFailedMessageSendMessage();
        }).then(function () {
            AboutContactForm.showMessageSendWasSuccessfulMessage();
            AboutContactForm.cleanContactForm();
            AboutContactForm.disableSubmitContactButton();
        });
    }

    static cleanContactForm() {
        document.getElementById('aboutContactName').value = '';
        document.getElementById('aboutContactEmail').value = '';
        document.getElementById('aboutContactMessage').value = '';
    }
}
