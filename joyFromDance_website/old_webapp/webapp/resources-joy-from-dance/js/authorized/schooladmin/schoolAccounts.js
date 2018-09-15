import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';
import {FullScreenSupport} from '../../tests/fullScreenSupport.js';

export class SchoolAccounts {

    constructor() {
        this.currentPage = 0;
        this.totalNumberOfPages = 0;
        this.updatePagingButtonsDisablement();
        this.i18n = new I18n();
        this.initPage()
    }

    initPage() {
        SchoolAccounts.bindTooltips();
        SchoolAccounts.showFullScreenApiButtonIfSupported();
        this.loadPagedSchoolAccounts();
        this.bindPageButtons();
        this.validateFormInputs();
    }

    static showFullScreenApiButtonIfSupported() {
        if (FullScreenSupport.isFullScreenSupported()) {
            document.getElementById('schoolAccountsTableFullScreenButton').classList.remove('hidden-xs-up');
        }
    }

    static bindTooltips() {
        $('[data-toggle="tooltip"]').tooltip();
    }

    loadPagedSchoolAccounts() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/schoolAccounts' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + document.getElementById('displayedRows').value +
            '&schoolNameSearch=' + document.getElementById('schoolNameSearch').value
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
                response.json().then(value => {
                    self.createRows(value.results);
                    self.totalNumberOfPages = value.totalNumberOfPages;
                    document.getElementById('currentPageInput').max = value.totalNumberOfPages;
                    self.updatePagingButtonsDisablement();
                    self.setCurrentPageHint();
                });
                self.setCurrentPageHint();
            } else {
                self.createErrorRow();
            }
        })
    }

    setCurrentPageHint() {
        let self = this;
        document.getElementById('currentPageInput').placeholder = (self.currentPage + 1) + '/' + (self.totalNumberOfPages === 0 ? 1 : self.totalNumberOfPages);
        if (document.getElementById('currentPageInput').value !== '') {
            document.getElementById('currentPageInput').value = self.currentPage + 1;
        }
    }

    addSchoolAccount() {
        let self = this;
        if (SchoolAccounts.isInvalidValidAddSchoolForm()) {
            SchoolAccounts.showAddSchoolValidationMessages();
        } else {
            let schoolName = document.getElementById('addSchoolName').value;
            let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/schoolAccounts?schoolName=' + schoolName;
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
                    self.loadPagedSchoolAccounts();
                    SchoolAccounts.closeAddSchoolAccountModal();
                    document.getElementById('addSchoolName').value = '';
                } else {
                    console.log('error');
                }
            })
        }
    }

    removeCheckedSchoolAccounts() {
        let self = this;
        let checkboxes = document.querySelectorAll('#schoolAccountsTableBody tr td:first-child input[type="checkbox"]');
        for (let checkbox of checkboxes) {
            if (checkbox.checked) {
                self.removeSchoolAccount(checkbox.getAttributeNode('data-school-id').nodeValue);
            }
        }
    }

    removeSchoolAccount(schoolId) {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/schoolAccounts/' + schoolId;
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
                self.loadPagedSchoolAccounts();
            } else {
                console.log('error');
            }
        })
    }

    createErrorRow() {
        document.getElementById('schoolAccountsTableBody').innerHTML = '<td colspan="4" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td>';
    }

    createRows(value) {
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="4" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += `<tr>
                 <td class="text-center"><input type="checkbox" value="${value[i].email}" data-school-id="${value[i].id}"/></td>
                 <td>${value[i].orderNumber}</td>
                 <td>${value[i].name}</td>
                 <td>${value[i].created}</td>
                 </tr>`;
            }
        }
        document.getElementById('schoolAccountsTableBody').innerHTML = rows;
        let checkboxes = document.querySelectorAll('#schoolAccountsTableBody tr td:first-child input[type="checkbox"]');
        for (let checkbox of checkboxes) {
            checkbox.addEventListener('change', function () {
                SchoolAccounts.updateRemoveSchoolAccountsButtonsDisablement();
            })
        }
    }

    static updateRemoveSchoolAccountsButtonsDisablement() {
        let checkboxes = document.querySelectorAll('#schoolAccountsTableBody tr td:first-child input[type="checkbox"]');
        let removeButtonShouldBeDisabled = true;
        for (let checkbox of checkboxes) {
            if (checkbox.checked) {
                removeButtonShouldBeDisabled = false;
                break;
            }
        }
        if (removeButtonShouldBeDisabled) {
            document.getElementById('removeSchoolAccountsButton').setAttribute('disabled', 'disabled');
        } else {
            document.getElementById('removeSchoolAccountsButton').removeAttribute('disabled');
        }
    }

    updatePagingButtonsDisablement() {
        if (this.currentPage <= 0) {
            document.getElementById('previousPageButton').setAttribute('disabled', 'disabled');
            $('#previousPageButton').tooltip('hide');
        } else {
            document.getElementById('previousPageButton').removeAttribute('disabled');
        }
        if (this.currentPage + 1 >= this.totalNumberOfPages) {
            document.getElementById('nextPageButton').setAttribute('disabled', 'disabled');
            $('#nextPageButton').tooltip('hide');
        } else {
            document.getElementById('nextPageButton').removeAttribute('disabled');
        }
        if (this.currentPage <= 0) {
            document.getElementById('firstPageButton').setAttribute('disabled', 'disabled');
            $('#firstPageButton').tooltip('hide');
        } else {
            document.getElementById('firstPageButton').removeAttribute('disabled');
        }
        if (this.currentPage + 1 >= this.totalNumberOfPages) {
            document.getElementById('lastPageButton').setAttribute('disabled', 'disabled');
            $('#lastPageButton').tooltip('hide');
        } else {
            document.getElementById('lastPageButton').removeAttribute('disabled');
        }
    }

    static openAddSchoolAccountModal() {
        document.getElementById('addSchoolName').value = '';
        document.getElementById('schoolNameRow').classList.remove('has-error');
        document.getElementById('schoolNameRow').classList.remove('has-success');
        document.getElementById('schoolNameErrorMessage').classList.add('hidden-xs-up');
        $('#addSchoolAccountPopup').modal('show');
    }

    static closeAddSchoolAccountModal() {
        $('#addSchoolAccountPopup').modal('hide');
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('switchSelectionHeader').addEventListener('click', function () {
            let checkboxes = document.querySelectorAll('#schoolAccountsTableBody tr td:first-child input[type="checkbox"]');
            for (let checkbox of checkboxes) {
                checkbox.checked = !checkbox.checked;
            }
            SchoolAccounts.updateRemoveSchoolAccountsButtonsDisablement();
        });
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadPagedSchoolAccounts();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            if (self.currentPage + 1 < self.totalNumberOfPages) {
                self.currentPage++;
                self.loadPagedSchoolAccounts();
            }
        });
        document.getElementById('firstPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadPagedSchoolAccounts();
        });
        document.getElementById('lastPageButton').addEventListener('click', function () {
            self.currentPage = self.totalNumberOfPages > 0 ? self.totalNumberOfPages - 1 : 0;
            self.loadPagedSchoolAccounts();
        });
        document.getElementById('currentPageInput').addEventListener('change', function () {
            if (document.getElementById('currentPageInput').value <= self.totalNumberOfPages) {
                if (document.getElementById('currentPageInput').value <= 0) {
                    self.currentPage = 0;
                } else {
                    self.currentPage = document.getElementById('currentPageInput').value - 1;
                }
                self.loadPagedSchoolAccounts();
            }
        });
        document.getElementById('displayedRows').addEventListener('change', function () {
            self.loadPagedSchoolAccounts();
        });
        document.getElementById('schoolAccountsTableFullScreenButton').addEventListener('click', function () {
            FullScreenSupport.toggleFullscreen('schoolAccountsScreenArea');
        });
        document.getElementById('defaultFiltersAndSortingButton').addEventListener('click', function () {
            document.getElementById('schoolNameSearch').value = '';
            self.loadPagedSchoolAccounts();
        });
        document.getElementById('additionalFiltersButton').addEventListener('click', function () {
            document.getElementById('schoolAccountsTableAdditionalFilters').classList.toggle('hidden-xs-up');
        });
        document.getElementById('schoolNameSearch').addEventListener('keyup', function () {
            self.loadPagedSchoolAccounts();
        });
        document.getElementById('openAddSchoolAccountPopupButton').addEventListener('click', function () {
            SchoolAccounts.openAddSchoolAccountModal();
        });
        document.getElementById('saveSchoolAccount').addEventListener('click', function () {
            self.addSchoolAccount();
        });
        document.getElementById('removeSchoolAccountsButton').addEventListener('click', function () {
            self.removeCheckedSchoolAccounts();
        });
    }

    validateFormInputs() {
        document.getElementById('addSchoolName').addEventListener('keyup', function () {
            if (document.getElementById('addSchoolName').validity.valid) {
                document.getElementById('schoolNameRow').classList.remove('has-error');
                document.getElementById('schoolNameRow').classList.add('has-success');
                document.getElementById('schoolNameErrorMessage').classList.add('hidden-xs-up');
                document.getElementById('saveSchoolAccount').removeAttribute('disabled');
            } else {
                document.getElementById('schoolNameRow').classList.add('has-error');
                document.getElementById('schoolNameRow').classList.remove('has-success');
                document.getElementById('schoolNameErrorMessage').classList.remove('hidden-xs-up');
                document.getElementById('saveSchoolAccount').setAttribute('disabled', 'disabled');
            }
        });
    }

    static isInvalidValidAddSchoolForm() {
        return !document.getElementById('addSchoolName').validity.valid;
    }

    static showAddSchoolValidationMessages() {
        document.getElementById('schoolNameRow').classList.add('has-error');
        document.getElementById('schoolNameRow').classList.remove('has-success');
        document.getElementById('schoolNameErrorMessage').classList.remove('hidden-xs-up');
    }

}

let schoolAccounts = new SchoolAccounts();
