import {
    updateDancerPreferredPosition,
    updateTeacherPreferredPosition,
    updateDanceFloorOwnerPreferredPosition,
    updateDancerEmail,
    updateTeacherEmail,
    updateDanceFloorOwnerEmail,
    signUpDancer,
    signUpTeacher,
    signUpDanceFloorOwner,
    unsubscribe
} from './actions/signup/signup.js';

import {LayoutView} from './components/layout.js';
import signUpState from './reducers/signup/signup.js';
import EmailValidator from './validator/emailValidator.js';

class SignUpView {

    constructor() {
        new LayoutView();
        this.buildDancerSignup();
        this.buildTeacherSignup();
        this.buildDanceFloorOwnerSignup();
        this.prepareUnsubscribeEmailInput();
        this.bindUnsubscribeButton();
        SignUpView.prepareRippleEffect();
    }

    buildDancerSignup() {
        this.whenGoogleLoadedDo(this, SignUpView.dancerAfterGoogleMapsLoaded);
    }

    buildTeacherSignup() {
        this.whenGoogleLoadedDo(this, SignUpView.teacherAfterGoogleMapsLoaded);
    }

    buildDanceFloorOwnerSignup() {
        this.whenGoogleLoadedDo(this, SignUpView.danceFloorOwnerAfterGoogleMapsLoaded);
    }

    whenGoogleLoadedDo(componentSelf, fnt) {
        let self = this;
        if (typeof window.google !== 'undefined') {
            fnt(componentSelf);
        } else {
            setTimeout(function () {
                self.whenGoogleLoadedDo(componentSelf, fnt);
            }, 500);
        }
    }

    static dancerAfterGoogleMapsLoaded(componentSelf) {
        componentSelf.initDancerMap();
        componentSelf.prepareDancerPlaceInput();
        componentSelf.prepareDancerDistanceInput();
        componentSelf.prepareDancerEmailInput();
        componentSelf.prepareDancerStylesValidation();
        componentSelf.bindDancerSignUpButton();
    }

    static teacherAfterGoogleMapsLoaded(componentSelf) {
        componentSelf.initTeacherMap();
        componentSelf.prepareTeacherPlaceInput();
        componentSelf.prepareTeacherDistanceInput();
        componentSelf.prepareTeacherEmailInput();
        componentSelf.prepareTeacherStylesValidation();
        componentSelf.bindTeacherSignUpButton();
    }

    static danceFloorOwnerAfterGoogleMapsLoaded(componentSelf) {
        componentSelf.initDanceFloorOwnerMap();
        componentSelf.prepareDanceFloorOwnerPlaceInput();
        componentSelf.prepareDanceFloorOwnerEmailInput();
        componentSelf.bindDanceFloorOwnerSignUpButton();
    }

    initDancerMap() {
        let self = this;
        let warsawLatLng = {lat: 52.237049, lng: 21.017532};
        let dancerMap = new google.maps.Map(document.getElementById('dancerMap'), {
            zoom: 14,
            center: warsawLatLng
        });
        let marker = new window.google.maps.Marker({position: warsawLatLng, map: dancerMap});

        let autocomplete = new google.maps.places.Autocomplete(document.getElementById('dancerPlaceInput'), {placeholder: undefined});
        autocomplete.bindTo('bounds', dancerMap);
        autocomplete.setFields(['address_components', 'geometry', 'icon', 'name']);

        autocomplete.addListener('place_changed', function () {
            signUpState.dispatch(updateDancerPreferredPosition(null, null));
            marker.setPosition(null);
            marker.setVisible(false);
            let place = autocomplete.getPlace();
            if (!place.geometry) {
                return;
            }

            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
                dancerMap.fitBounds(place.geometry.viewport);
            } else {
                dancerMap.setCenter(place.geometry.location);
                dancerMap.setZoom(17);  // Why 17? Because it looks good.
            }
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);
            signUpState.dispatch(updateDancerPreferredPosition(place.geometry.location.lat(), place.geometry.location.lng()));
            self.validateDancerPlace();
        });
    }

    prepareDancerPlaceInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('dancerPlaceInputWrap'));
        document.getElementById('dancerPlaceInput').addEventListener('input', function () {
            signUpState.dispatch(updateDancerPreferredPosition(null, null));
            self.validateDancerPlace();
        })
    }

    prepareDancerDistanceInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('dancerPlaceDistanceInputWrap'));
        document.getElementById('dancerEmailInput').addEventListener('input', function () {
            signUpState.dispatch(updateDancerEmail(this.value));
            self.validateDancerDistance();
        })
    }

    prepareDancerEmailInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('dancerEmailInputWrap'));
        document.getElementById('dancerEmailInput').addEventListener('input', function () {
            signUpState.dispatch(updateDancerEmail(this.value));
            self.validateDancerEmail();
        })
    }

    prepareDancerStylesValidation() {
        let self = this;
        document.querySelectorAll('#dancer-dancing-styles.mdc-chip-set:not(#input-chip-set)').forEach(function (chipSet) {
            new window.mdc.chips.MDCChipSet(chipSet);
        });
        document.getElementById('dancer-dancing-styles').addEventListener('click', function () {
            self.validateDancerStyles();
        })
    }

    bindDancerSignUpButton() {
        let self = this;
        document.getElementById('dancerSignUpButton').addEventListener('click', function () {
            let dancerSignUpIsValid = self.validateDancerSignUp();
            if (dancerSignUpIsValid) {
                const email = document.getElementById('dancerEmailInput').value;
                const distance = document.getElementById('dancerPlaceDistanceInput').value;
                const chosenDanceStyles = self.getDancerChosenDanceStyles();
                signUpDancer(email, signUpState.state.dancer.mapLat, signUpState.state.dancer.mapLng, distance, chosenDanceStyles);
            }
        })
    }

    prepareUnsubscribeEmailInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('unsubscribeInputWrap'));
        document.getElementById('unsubscribeEmailInput').addEventListener('input', function () {
            self.validateUnsubscribeEmail();
        })
    }

    bindUnsubscribeButton() {
        let self = this;
        document.getElementById('unsubscribeButton').addEventListener('click', function () {
            let unsubscribeIsValid = self.validateUnsubscribeEmail();
            if (unsubscribeIsValid) {
                const email = document.getElementById('unsubscribeEmailInput').value;
                unsubscribe(email);
            }
        })
    }

    static prepareRippleEffect() {
        let buttons = document.querySelectorAll('.mdc-button');
        for (let i = 0, btn; btn = buttons[i]; i++) {
            new window.mdc.ripple.MDCRipple(btn);
        }
    }

    validateDancerSignUp() {
        let validationResult = true;
        validationResult &= this.validateDancerPlace();
        validationResult &= this.validateDancerDistance();
        validationResult &= this.validateDancerEmail();
        validationResult &= this.validateDancerStyles();
        return validationResult;
    }

    validateDancerPlace() {
        let self = this;
        let validationResult = true;
        const place = document.getElementById('dancerPlaceInput').value;
        if (signUpState.state.dancer.mapLat === null || signUpState.state.dancer.mapLng === null || place === null || place.trim() === '') {
            document.getElementById('dancerPlaceInputWrap').classList.add('mdc-text-field--invalid');
            document.getElementById('dancer-place-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            validationResult = false;
        } else {
            document.getElementById('dancerPlaceInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('dancer-place-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        }
        return validationResult;
    }

    validateUnsubscribeEmail() {
        let self = this;
        let validationResult = true;
        const email = document.getElementById('unsubscribeEmailInput').value;
        if (EmailValidator.isValidEmail(email)) {
            document.getElementById('unsubscribeInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('unsubscribe-email-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        } else {
            document.getElementById('unsubscribeInputWrap').classList.add('mdc-text-field--invalid');
            document.getElementById('unsubscribe-email-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            validationResult = false;
        }
        return validationResult;
    }

    validateDancerStyles() {
        let self = this;
        let validationResult = true;
        const dancerChosenStyles = self.getDancerChosenDanceStyles();
        if (dancerChosenStyles.length === 0) {
            document.getElementById('dancer-dancing-styles').classList.add('mdc-text-field--invalid');
            if (!document.getElementById('dancer-dancing-styles-helper-text').classList.contains('mdc-text-field-helper-text--persistent')) {
                document.getElementById('dancer-dancing-styles-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            }
            validationResult = false;
        } else {
            document.getElementById('dancer-dancing-styles').classList.remove('mdc-text-field--invalid');
            document.getElementById('dancer-dancing-styles-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        }
        return validationResult;
    }

    validateDancerDistance() {
        let self = this;
        let validationResult = true;
        const distance = document.getElementById('dancerPlaceDistanceInput').value;
        if (distance === null || distance <= 0) {
            document.getElementById('dancerPlaceDistanceInputWrap').classList.add('mdc-text-field--invalid');
            if (!document.getElementById('dancer-place-distance-helper-text').classList.contains('mdc-text-field-helper-text--persistent')) {
                document.getElementById('dancer-place-distance-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            }
            validationResult = false;
        } else {
            document.getElementById('dancerPlaceDistanceInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('dancer-place-distance-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        }
        return validationResult;
    }

    validateDancerEmail() {
        let self = this;
        let validationResult = true;
        const email = document.getElementById('dancerEmailInput').value;
        if (EmailValidator.isValidEmail(email)) {
            document.getElementById('dancerEmailInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('dancer-email-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        } else {
            document.getElementById('dancerEmailInputWrap').classList.add('mdc-text-field--invalid');
            document.getElementById('dancer-email-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            validationResult = false;
        }
        return validationResult;
    }

    getDancerChosenDanceStyles() {
        let self = this;
        return Array.prototype.map.call(
            document.querySelectorAll('#dancer-dancing-styles.mdc-chip-set--filter .mdc-chip--selected > .mdc-chip__text'),
            function (t) {
                return t.textContent;
            }
        );
    }

    initTeacherMap() {
        let self = this;
        let warsawLatLng = {lat: 52.237049, lng: 21.017532};
        let teacherMap = new google.maps.Map(document.getElementById('teacherMap'), {
            zoom: 14,
            center: warsawLatLng
        });
        let marker = new window.google.maps.Marker({position: warsawLatLng, map: teacherMap});

        let autocomplete = new google.maps.places.Autocomplete(document.getElementById('teacherPlaceInput'), {placeholder: undefined});
        autocomplete.bindTo('bounds', teacherMap);
        autocomplete.setFields(['address_components', 'geometry', 'icon', 'name']);

        autocomplete.addListener('place_changed', function () {
            signUpState.dispatch(updateTeacherPreferredPosition(null, null));
            marker.setPosition(null);
            marker.setVisible(false);
            let place = autocomplete.getPlace();
            if (!place.geometry) {
                return;
            }

            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
                teacherMap.fitBounds(place.geometry.viewport);
            } else {
                teacherMap.setCenter(place.geometry.location);
                teacherMap.setZoom(17);  // Why 17? Because it looks good.
            }
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);
            signUpState.dispatch(updateTeacherPreferredPosition(place.geometry.location.lat(), place.geometry.location.lng()));
            self.validateTeacherPlace();
        });
    }

    prepareTeacherPlaceInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('teacherPlaceInputWrap'));
        document.getElementById('teacherPlaceInput').addEventListener('input', function () {
            signUpState.dispatch(updateTeacherPreferredPosition(null, null));
            self.validateTeacherPlace();
        })
    }

    prepareTeacherDistanceInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('teacherPlaceDistanceInputWrap'));
        document.getElementById('teacherEmailInput').addEventListener('input', function () {
            signUpState.dispatch(updateTeacherEmail(this.value));
            self.validateTeacherDistance();
        })
    }

    prepareTeacherEmailInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('teacherEmailInputWrap'));
        document.getElementById('teacherEmailInput').addEventListener('input', function () {
            signUpState.dispatch(updateTeacherEmail(this.value));
            self.validateTeacherEmail();
        })
    }

    prepareTeacherStylesValidation() {
        let self = this;
        document.querySelectorAll('#teacher-dancing-styles.mdc-chip-set:not(#input-chip-set)').forEach(function (chipSet) {
            new window.mdc.chips.MDCChipSet(chipSet);
        });
        document.getElementById('teacher-dancing-styles').addEventListener('click', function () {
            self.validateTeacherStyles();
        })
    }

    bindTeacherSignUpButton() {
        let self = this;
        document.getElementById('teacherSignUpButton').addEventListener('click', function () {
            let teacherSignUpIsValid = self.validateTeacherSignUp();
            if (teacherSignUpIsValid) {
                const email = document.getElementById('teacherEmailInput').value;
                const distance = document.getElementById('teacherPlaceDistanceInput').value;
                const chosenDanceStyles = self.getTeacherChosenDanceStyles();
                signUpTeacher(email, signUpState.state.teacher.mapLat, signUpState.state.teacher.mapLng, distance, chosenDanceStyles);
            }
        })
    }

    validateTeacherSignUp() {
        let validationResult = true;
        validationResult &= this.validateTeacherPlace();
        validationResult &= this.validateTeacherDistance();
        validationResult &= this.validateTeacherEmail();
        validationResult &= this.validateTeacherStyles();
        return validationResult;
    }

    validateTeacherPlace() {
        let self = this;
        let validationResult = true;
        const place = document.getElementById('teacherPlaceInput').value;
        if (signUpState.state.teacher.mapLat === null || signUpState.state.teacher.mapLng === null || place === null || place.trim() === '') {
            document.getElementById('teacherPlaceInputWrap').classList.add('mdc-text-field--invalid');
            document.getElementById('teacher-place-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            validationResult = false;
        } else {
            document.getElementById('teacherPlaceInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('teacher-place-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        }
        return validationResult;
    }

    validateTeacherStyles() {
        let self = this;
        let validationResult = true;
        const teacherChosenStyles = self.getTeacherChosenDanceStyles();
        if (teacherChosenStyles.length === 0) {
            document.getElementById('teacher-dancing-styles').classList.add('mdc-text-field--invalid');
            if (!document.getElementById('teacher-dancing-styles-helper-text').classList.contains('mdc-text-field-helper-text--persistent')) {
                document.getElementById('teacher-dancing-styles-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            }
            validationResult = false;
        } else {
            document.getElementById('teacher-dancing-styles').classList.remove('mdc-text-field--invalid');
            document.getElementById('teacher-dancing-styles-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        }
        return validationResult;
    }

    validateTeacherDistance() {
        let self = this;
        let validationResult = true;
        const distance = document.getElementById('teacherPlaceDistanceInput').value;
        if (distance === null || distance <= 0) {
            document.getElementById('teacherPlaceDistanceInputWrap').classList.add('mdc-text-field--invalid');
            if (!document.getElementById('teacher-place-distance-helper-text').classList.contains('mdc-text-field-helper-text--persistent')) {
                document.getElementById('teacher-place-distance-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            }
            validationResult = false;
        } else {
            document.getElementById('teacherPlaceDistanceInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('teacher-place-distance-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        }
        return validationResult;
    }

    validateTeacherEmail() {
        let self = this;
        let validationResult = true;
        const email = document.getElementById('teacherEmailInput').value;
        if (EmailValidator.isValidEmail(email)) {
            document.getElementById('teacherEmailInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('teacher-email-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        } else {
            document.getElementById('teacherEmailInputWrap').classList.add('mdc-text-field--invalid');
            document.getElementById('teacher-email-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            validationResult = false;
        }
        return validationResult;
    }

    getTeacherChosenDanceStyles() {
        let self = this;
        return Array.prototype.map.call(
            document.querySelectorAll('#teacher-dancing-styles.mdc-chip-set--filter .mdc-chip--selected > .mdc-chip__text'),
            function (t) {
                return t.textContent;
            }
        );
    }

    initDanceFloorOwnerMap() {
        let self = this;
        let warsawLatLng = {lat: 52.237049, lng: 21.017532};
        let teacherMap = new google.maps.Map(document.getElementById('danceFloorOwnerMap'), {
            zoom: 14,
            center: warsawLatLng
        });
        let marker = new window.google.maps.Marker({position: warsawLatLng, map: teacherMap});

        let autocomplete = new google.maps.places.Autocomplete(document.getElementById('danceFloorOwnerPlaceInput'), {placeholder: undefined});
        autocomplete.bindTo('bounds', teacherMap);
        autocomplete.setFields(['address_components', 'geometry', 'icon', 'name']);

        autocomplete.addListener('place_changed', function () {
            signUpState.dispatch(updateDanceFloorOwnerPreferredPosition(null, null));
            marker.setPosition(null);
            marker.setVisible(false);
            let place = autocomplete.getPlace();
            if (!place.geometry) {
                return;
            }

            // If the place has a geometry, then present it on a map.
            if (place.geometry.viewport) {
                teacherMap.fitBounds(place.geometry.viewport);
            } else {
                teacherMap.setCenter(place.geometry.location);
                teacherMap.setZoom(17);  // Why 17? Because it looks good.
            }
            marker.setPosition(place.geometry.location);
            marker.setVisible(true);
            signUpState.dispatch(updateDanceFloorOwnerPreferredPosition(place.geometry.location.lat(), place.geometry.location.lng()));
            self.validateDanceFloorOwnerPlace();
        });
    }

    prepareDanceFloorOwnerPlaceInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('danceFloorOwnerPlaceInputWrap'));
        document.getElementById('danceFloorOwnerPlaceInput').addEventListener('input', function () {
            signUpState.dispatch(updateDanceFloorOwnerPreferredPosition(null, null));
            self.validateDanceFloorOwnerPlace();
        })
    }

    prepareDanceFloorOwnerEmailInput() {
        let self = this;
        new window.mdc.textField.MDCTextField(document.getElementById('danceFloorOwnerEmailInputWrap'));
        document.getElementById('danceFloorOwnerEmailInput').addEventListener('input', function () {
            signUpState.dispatch(updateDanceFloorOwnerEmail(this.value));
            self.validateDanceFloorOwnerEmail();
        })
    }

    bindDanceFloorOwnerSignUpButton() {
        let self = this;
        document.getElementById('danceFloorOwnerSignUpButton').addEventListener('click', function () {
            let danceFloorOwnerSignUpIsValid = self.validateDanceFloorOwnerSignUp();
            if (danceFloorOwnerSignUpIsValid) {
                const email = document.getElementById('danceFloorOwnerEmailInput').value;
                signUpDanceFloorOwner(email, signUpState.state.danceFloorOwner.mapLat, signUpState.state.danceFloorOwner.mapLng);
            }
        })
    }

    validateDanceFloorOwnerSignUp() {
        let validationResult = true;
        validationResult &= this.validateDanceFloorOwnerPlace();
        validationResult &= this.validateDanceFloorOwnerEmail();
        return validationResult;
    }

    validateDanceFloorOwnerPlace() {
        let self = this;
        let validationResult = true;
        const place = document.getElementById('danceFloorOwnerPlaceInput').value;
        if (signUpState.state.danceFloorOwner.mapLat === null || signUpState.state.danceFloorOwner.mapLng === null || place === null || place.trim() === '') {
            document.getElementById('danceFloorOwnerPlaceInputWrap').classList.add('mdc-text-field--invalid');
            document.getElementById('danceFloorOwner-place-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            validationResult = false;
        } else {
            document.getElementById('danceFloorOwnerPlaceInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('danceFloorOwner-place-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        }
        return validationResult;
    }

    validateDanceFloorOwnerEmail() {
        let self = this;
        let validationResult = true;
        const email = document.getElementById('danceFloorOwnerEmailInput').value;
        if (EmailValidator.isValidEmail(email)) {
            document.getElementById('danceFloorOwnerEmailInputWrap').classList.remove('mdc-text-field--invalid');
            document.getElementById('danceFloorOwner-email-helper-text').classList.remove('mdc-text-field-helper-text--persistent');
        } else {
            document.getElementById('danceFloorOwnerEmailInputWrap').classList.add('mdc-text-field--invalid');
            document.getElementById('danceFloorOwner-email-helper-text').classList.add('mdc-text-field-helper-text--persistent');
            validationResult = false;
        }
        return validationResult;
    }

}

new SignUpView();
