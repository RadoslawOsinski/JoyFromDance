import signUpState from './../../reducers/signup/signup.js';
import {BACKEND_ADDRESS} from './../../backend/backend.js';

export const updateDancerPreferredPosition = (lat, lng) =>  {
    return {
        type: 'UPDATE_DANCER_POSITION',
        lat: lat,
        lng: lng
    };
};

export const updateDancerEmail = (email) => {
    return {
        type: 'UPDATE_DANCER_EMAIL',
        email: email
    };
};

export const updateTeacherPreferredPosition = (lat, lng) =>  {
    return {
        type: 'UPDATE_TEACHER_POSITION',
        lat: lat,
        lng: lng
    };
};

export const updateTeacherEmail = (email) => {
    return {
        type: 'UPDATE_TEACHER_EMAIL',
        email: email
    };
};

export const updateDanceFloorOwnerPreferredPosition = (lat, lng) =>  {
    return {
        type: 'UPDATE_DANCE_FLOOR_OWNER_POSITION',
        lat: lat,
        lng: lng
    };
};

export const updateDanceFloorOwnerEmail = (email) => {
    return {
        type: 'UPDATE_DANCE_FLOOR_OWNER_EMAIL',
        email: email
    };
};

export const signUpDancer = (email, lat, lng, distance, chosenDanceStyles) => {
    let url = BACKEND_ADDRESS + '/rest/public/dancer/signUp';
    // let token = document.querySelector("meta[name='_csrf']").content;
    // let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    let token = '';
    let csrfHeader = '';
    let headers = new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
        'Accept': 'application/json;charset=UTF-8',
        'Accept-Charset': 'UTF-8'
    });
    // headers.append(csrfHeader, token);
    fetch(url, {
        // credentials: 'include',
        method: 'PUT',
        body: JSON.stringify({
            email: email,
            lat: lat,
            lng: lng,
            distance: distance,
            chosenDanceStyles: chosenDanceStyles
        }),
        headers: headers,
        mode: 'cors',
        cache: 'default'
    }).catch(function () {
        //this code was using redux with PWA. But it was entirely rewritten. Mess for cleanup.
        signUpState.dispatch({type: 'SUBSCRIBE_DANCER_FAILURE'});
        document.getElementById('subscribeDancerSuccessMessage').style.visibility = 'hidden';
        document.getElementById('subscribeDancerFailureMessage').style.visibility = 'visible';
    }).then(function (response) {
        if (response !== undefined && 200 === response.status) {
            signUpState.dispatch({type: 'SUBSCRIBE_DANCER_SUCCESS'});
            document.getElementById('subscribeDancerSuccessMessage').style.visibility = 'visible';
            document.getElementById('subscribeDancerFailureMessage').style.visibility = 'hidden';
        } else {
            signUpState.dispatch({type: 'SUBSCRIBE_DANCER_FAILURE'});
            document.getElementById('subscribeDancerSuccessMessage').style.visibility = 'hidden';
            document.getElementById('subscribeDancerFailureMessage').style.visibility = 'visible';
        }
    }).finally(function () {
        setTimeout(function () {
            signUpState.dispatch({type: 'RESET_DANCER_SUBSCRIBE_STATUS'});
            document.getElementById('subscribeDancerSuccessMessage').style.visibility = 'hidden';
            document.getElementById('subscribeDancerFailureMessage').style.visibility = 'hidden';
        }, 4000);
    })
};

export const signUpTeacher = (email, lat, lng, distance, chosenDanceStyles) => {
    let url = BACKEND_ADDRESS + '/rest/public/teacher/signUp';
    // let token = document.querySelector("meta[name='_csrf']").content;
    // let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    let token = '';
    let csrfHeader = '';
    let headers = new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
        'Accept': 'application/json;charset=UTF-8',
        'Accept-Charset': 'UTF-8'
    });
    // headers.append(csrfHeader, token);
    fetch(url, {
        // credentials: 'include',
        method: 'PUT',
        body: JSON.stringify({
            email: email,
            lat: lat,
            lng: lng,
            distance: distance,
            chosenDanceStyles: chosenDanceStyles
        }),
        headers: headers,
        mode: 'cors',
        cache: 'default'
    }).catch(function () {
        //this code was using redux with PWA. But it was entirely rewritten. Mess for cleanup.
        signUpState.dispatch({type: 'SUBSCRIBE_TEACHER_FAILURE'});
        document.getElementById('subscribeTeacherSuccessMessage').style.visibility = 'hidden';
        document.getElementById('subscribeTeacherFailureMessage').style.visibility = 'visible';
    }).then(function (response) {
        if (response !== undefined && 200 === response.status) {
            signUpState.dispatch({type: 'SUBSCRIBE_TEACHER_SUCCESS'});
            document.getElementById('subscribeTeacherSuccessMessage').style.visibility = 'visible';
            document.getElementById('subscribeTeacherFailureMessage').style.visibility = 'hidden';
        } else {
            signUpState.dispatch({type: 'SUBSCRIBE_TEACHER_FAILURE'});
            document.getElementById('subscribeTeacherSuccessMessage').style.visibility = 'hidden';
            document.getElementById('subscribeTeacherFailureMessage').style.visibility = 'visible';
        }
    }).finally(function () {
        setTimeout(function () {
            signUpState.dispatch({type: 'RESET_TEACHER_SUBSCRIBE_STATUS'});
            document.getElementById('subscribeTeacherSuccessMessage').style.visibility = 'hidden';
            document.getElementById('subscribeTeacherFailureMessage').style.visibility = 'hidden';
        }, 4000);
    })
};

export const signUpDanceFloorOwner = (email, lat, lng) => {
    let url = BACKEND_ADDRESS + '/rest/public/danceFloorOwner/signUp';
    // let token = document.querySelector("meta[name='_csrf']").content;
    // let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    let token = '';
    let csrfHeader = '';
    let headers = new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
        'Accept': 'application/json;charset=UTF-8',
        'Accept-Charset': 'UTF-8'
    });
    // headers.append(csrfHeader, token);
    fetch(url, {
        // credentials: 'include',
        method: 'PUT',
        body: JSON.stringify({
            email: email,
            lat: lat,
            lng: lng
        }),
        headers: headers,
        mode: 'cors',
        cache: 'default'
    }).catch(function () {
        //this code was using redux with PWA. But it was entirely rewritten. Mess for cleanup.
        signUpState.dispatch({type: 'SUBSCRIBE_DANCE_FLOOR_OWNER_FAILURE'});
        document.getElementById('subscribeDanceFloorOwnerSuccessMessage').style.visibility = 'hidden';
        document.getElementById('subscribeDanceFloorOwnerFailureMessage').style.visibility = 'visible';
    }).then(function (response) {
        if (response !== undefined && 200 === response.status) {
            signUpState.dispatch({type: 'SUBSCRIBE_DANCE_FLOOR_OWNER_SUCCESS'});
            document.getElementById('subscribeDanceFloorOwnerSuccessMessage').style.visibility = 'visible';
            document.getElementById('subscribeDanceFloorOwnerFailureMessage').style.visibility = 'hidden';
        } else {
            signUpState.dispatch({type: 'SUBSCRIBE_DANCE_FLOOR_OWNER_FAILURE'});
            document.getElementById('subscribeDanceFloorOwnerSuccessMessage').style.visibility = 'hidden';
            document.getElementById('subscribeDanceFloorOwnerFailureMessage').style.visibility = 'visible';
        }
    }).finally(function () {
        setTimeout(function () {
            signUpState.dispatch({type: 'RESET_DANCE_FLOOR_OWNER_SUBSCRIBE_STATUS'});
            document.getElementById('subscribeDanceFloorOwnerSuccessMessage').style.visibility = 'hidden';
            document.getElementById('subscribeDanceFloorOwnerFailureMessage').style.visibility = 'hidden';
        }, 4000);
    })
};

export const unsubscribe = (email) => {
    let url = BACKEND_ADDRESS + '/rest/public/unSubscribe/' + email;
    // let token = document.querySelector("meta[name='_csrf']").content;
    // let csrfHeader = document.querySelector("meta[name='_csrf_header']").content;
    let token = '';
    let csrfHeader = '';
    let headers = new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
        'Accept': 'application/json;charset=UTF-8',
        'Accept-Charset': 'UTF-8'
    });
    // headers.append(csrfHeader, token);
    fetch(url, {
        method: 'DELETE',
        mode: "cors",
        headers: headers
        // ,
        // credentials: 'include'
    }).catch(function () {
        signUpState.dispatch({type: 'UNSUBSCRIBE_FAILURE'});
        //this code was using redux with PWA. But it was entirely rewritten. Mess for cleanup.
        document.getElementById('unsubscribeSuccessMessage').style.visibility = 'hidden';
        document.getElementById('unsubscribeFailureMessage').style.visibility = 'visible';
    }).then(function (response) {
        if (response !== undefined && 200 === response.status) {
            signUpState.dispatch({type: 'UNSUBSCRIBE_SUCCESS'});
            document.getElementById('unsubscribeSuccessMessage').style.visibility = 'visible';
            document.getElementById('unsubscribeFailureMessage').style.visibility = 'hidden';
        } else {
            signUpState.dispatch({type: 'UNSUBSCRIBE_FAILURE'});
            document.getElementById('unsubscribeSuccessMessage').style.visibility = 'hidden';
            document.getElementById('unsubscribeFailureMessage').style.visibility = 'visible';
        }
    }).finally(function () {
        setTimeout(function () {
            signUpState.dispatch({type: 'RESET_UNSUBSCRIBE_STATUS'});
            document.getElementById('unsubscribeSuccessMessage').style.visibility = 'hidden';
            document.getElementById('unsubscribeFailureMessage').style.visibility = 'hidden';
        }, 4000);
    })
};
