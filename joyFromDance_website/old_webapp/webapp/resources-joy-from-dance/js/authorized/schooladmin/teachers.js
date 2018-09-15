import {ServerAddress} from '../../serverAddress.js';
import {I18n} from '../../i18n/i18n.js';
import {FullScreenSupport} from '../../tests/fullScreenSupport.js';

export class SchoolAdminTeachers {

    constructor() {
        this.currentPage = 0;
        this.totalNumberOfPages = 0;
        this.emailSortOrder = null;
        this.updatePagingButtonsDisablement();
        this.i18n = new I18n();
        this.initPage()
    }

    initPage() {
        SchoolAdminTeachers.bindTooltips();
        SchoolAdminTeachers.showFullScreenApiButtonIfSupported();
        this.loadPagedTeachers();
        this.bindPageButtons();
        // this.validateFormInputs();
    }

    static showFullScreenApiButtonIfSupported() {
        if (FullScreenSupport.isFullScreenSupported()) {
            document.getElementById('teachersTableFullScreenButton').classList.remove('hidden-xs-up');
        }
    }

    static bindTooltips() {
        $('[data-toggle="tooltip"]').tooltip();
    }

    loadPagedTeachers() {
        let self = this;
        let url = ServerAddress.SERVER_ADDRESS_WITH_CONTEXT + '/authorized/rest/school/teachers' +
            '?currentPage=' + self.currentPage +
            '&displayedRows=' + document.getElementById('displayedRows').value +
            '&firstNameSearch=' + document.getElementById('firstNameSearch').value +
            '&lastNameSearch=' + document.getElementById('lastNameSearch').value +
            '&emailSearch=' + document.getElementById('emailSearch').value +
            '&ascendingEmailSortOrder=' + (self.emailSortOrder === null || self.emailSortOrder === true)
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

    addTeacher() {
        let self = this;
        // if (SchoolAdminTeachers.isInvalidValidAddSchoolForm()) {
        //     SchoolAdminTeachers.showAddSchoolValidationMessages();
        // } else {
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
        // }
    }

    createErrorRow() {
        document.getElementById('teachersTableBody').innerHTML = '<td colspan="5" class="text-center">' + this.i18n.i18n('DataFetchingProblem') + '</td>';
    }

    createRows(value) {
        let self = this;
        let rows = '';
        if (value.length === 0) {
            rows += '<td colspan="5" class="text-center">' + this.i18n.i18n('NoResults') + '</td>';
        } else {
            for (let i = 0; i < value.length; i++) {
                rows += `<tr>
                <td class="text-center"><input type="checkbox" value="${value[i].email}" data-school-id="${value[i].id}"/></td>
                <td>${value[i].orderNumber}</td>
                <td>${value[i].firstName}</td>
                <td>${value[i].lastName}</td>
                <td>${value[i].email}</td>
                </tr>`;
            }
        }
        document.getElementById('teachersTableBody').innerHTML = rows;
        let checkboxes = document.querySelectorAll('#teachersTableBody tr td:first-child input[type="checkbox"]');
        for (let checkbox of checkboxes) {
            checkbox.addEventListener('change', function () {
                SchoolAdminTeachers.updateRemoveTeacherButtonsDisablement();
            })
        }
    }

    static updateRemoveTeacherButtonsDisablement() {
        let checkboxes = document.querySelectorAll('#teachersTableBody tr td:first-child input[type="checkbox"]');
        let removeButtonShouldBeDisabled = true;
        for (let checkbox of checkboxes) {
            if (checkbox.checked) {
                removeButtonShouldBeDisabled = false;
                break;
            }
        }
        if (removeButtonShouldBeDisabled) {
            document.getElementById('removeTeachersButton').setAttribute('disabled', 'disabled');
        } else {
            document.getElementById('removeTeachersButton').removeAttribute('disabled');
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

    static openAddSchoolTeacherModal() {
        document.getElementById('teacherEmail').value = '';
        $('#addSchoolTeacherPopup').modal('show');
    }

    static closeAddSchoolTeacherModal() {
        $('#addSchoolTeacherPopup').modal('hide');
    }

    bindPageButtons() {
        let self = this;
        document.getElementById('switchSelectionHeader').addEventListener('click', function () {
            let checkboxes = document.querySelectorAll('#teachersTableBody tr td:first-child input[type="checkbox"]');
            for (let checkbox of checkboxes) {
                checkbox.checked = !checkbox.checked;
            }
            SchoolAdminTeachers.updateRemoveTeacherButtonsDisablement();
        });
        document.getElementById('sortByEmailHeader').addEventListener('click', function () {
            if (self.emailSortOrder === null) {
                self.emailSortOrder = true;
                document.getElementById('sortByEmailHeaderUpSymbol').classList.remove('hidden-xs-up');
                document.getElementById('sortByEmailHeaderDownSymbol').classList.add('hidden-xs-up');
            } else if (self.emailSortOrder === true) {
                self.emailSortOrder = false;
                document.getElementById('sortByEmailHeaderUpSymbol').classList.add('hidden-xs-up');
                document.getElementById('sortByEmailHeaderDownSymbol').classList.remove('hidden-xs-up');
            } else if (self.emailSortOrder === false) {
                self.emailSortOrder = null;
                document.getElementById('sortByEmailHeaderUpSymbol').classList.add('hidden-xs-up');
                document.getElementById('sortByEmailHeaderDownSymbol').classList.add('hidden-xs-up');
            }
            self.loadPagedTeachers();
        });
        document.getElementById('previousPageButton').addEventListener('click', function () {
            if (self.currentPage > 0) {
                self.currentPage--;
                self.loadPagedTeachers();
            }
        });
        document.getElementById('nextPageButton').addEventListener('click', function () {
            if (self.currentPage + 1 < self.totalNumberOfPages) {
                self.currentPage++;
                self.loadPagedTeachers();
            }
        });
        document.getElementById('firstPageButton').addEventListener('click', function () {
            self.currentPage = 0;
            self.loadPagedTeachers();
        });
        document.getElementById('lastPageButton').addEventListener('click', function () {
            self.currentPage = self.totalNumberOfPages > 0 ? self.totalNumberOfPages - 1 : 0;
            self.loadPagedTeachers();
        });
        document.getElementById('currentPageInput').addEventListener('change', function () {
            if (document.getElementById('currentPageInput').value <= self.totalNumberOfPages) {
                if (document.getElementById('currentPageInput').value <= 0) {
                    self.currentPage = 0;
                } else {
                    self.currentPage = document.getElementById('currentPageInput').value - 1;
                }
                self.loadPagedTeachers();
            }
        });
        document.getElementById('displayedRows').addEventListener('change', function () {
            self.loadPagedTeachers();
        });
        document.getElementById('teachersTableFullScreenButton').addEventListener('click', function () {
            FullScreenSupport.toggleFullscreen('teachersFullScreenArea');
        });
        document.getElementById('defaultFiltersAndSortingButton').addEventListener('click', function () {
            document.getElementById('firstNameSearch').value = '';
            document.getElementById('lastNameSearch').value = '';
            document.getElementById('emailSearch').value = '';
            self.emailSortOrder = null;
            self.loadPagedTeachers();
        });
        document.getElementById('additionalFiltersButton').addEventListener('click', function () {
            document.getElementById('teachersTableAdditionalFilters').classList.toggle('hidden-xs-up');
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
        document.getElementById('openAddTeacherPopupButton').addEventListener('click', function () {
            SchoolAdminTeachers.openAddSchoolTeacherModal();
        });
        document.getElementById('removeTeachersButton').addEventListener('click', function () {
            console.log('removeTeachersButton');
        });
    }
    //
    // validateFormInputs() {
    //     document.getElementById('addSchoolName').addEventListener('keyup', function () {
    //         if (document.getElementById('addSchoolName').validity.valid) {
    //             document.getElementById('schoolNameRow').classList.remove('has-error');
    //             document.getElementById('schoolNameRow').classList.add('has-success');
    //         } else {
    //             document.getElementById('schoolNameRow').classList.add('has-error');
    //             document.getElementById('schoolNameRow').classList.remove('has-success');
    //         }
    //     });
    // }
    //
    // static isInvalidValidAddSchoolForm() {
    //     return document.getElementById('addSchoolName').validity.valid;
    // }
    //
    // static showAddSchoolValidationMessages() {
    //     document.getElementById('schoolNameRow').classList.add('has-error');
    //     document.getElementById('schoolNameRow').classList.remove('has-success');
    // }

}

let schoolAdminTeachers = new SchoolAdminTeachers();
