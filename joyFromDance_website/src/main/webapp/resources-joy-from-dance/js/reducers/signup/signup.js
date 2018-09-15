class SignUpState {

    constructor() {
        this.state = {
            dancer: {
                mapLat: 52.237049,
                mapLng: 21.017532,
                email: '',
                distance: 15,
                danceStyles: [],
                signUpStatus: null,
            },
            teacher: {
                mapLat: 52.237049,
                mapLng: 21.017532,
                email: '',
                distance: 15,
                danceStyles: [],
                signUpStatus: null,
            },
            danceFloorOwner: {
                mapLat: 52.237049,
                mapLng: 21.017532,
                email: '',
                signUpStatus: null,
            },
            unsubscribeStatus: ''
        };
    }

    dispatch(action) {
        switch (action.type) {
            case 'UPDATE_DANCER_POSITION':
                this.state.dancer.mapLat = action.lat;
                this.state.dancer.mapLng = action.lng;
                return this.state;
            case 'UPDATE_DANCER_EMAIL':
                this.state.dancer.email = action.email;
                return this.state;
            case 'SUBSCRIBE_DANCER_SUCCESS':
                this.state.dancer.signUpStatus = 'SUBSCRIBE_DANCER_SUCCESS';
                return this.state;
            case 'SUBSCRIBE_DANCER_FAILURE':
                this.state.dancer.signUpStatus = 'SUBSCRIBE_DANCER_FAILURE';
                return this.state;
            case 'RESET_DANCER_SUBSCRIBE_STATUS':
                this.state.dancer.signUpStatus = null;
                return this.state;
            case 'UPDATE_TEACHER_POSITION':
                this.state.teacher.mapLat = action.lat;
                this.state.teacher.mapLng = action.lng;
                return this.state;
            case 'UPDATE_TEACHER_EMAIL':
                this.state.teacher.email = action.email;
                return this.state;
            case 'SUBSCRIBE_TEACHER_SUCCESS':
                this.state.teacher.signUpStatus = 'SUBSCRIBE_TEACHER_SUCCESS';
                return this.state;
            case 'SUBSCRIBE_TEACHER_FAILURE':
                this.state.teacher.signUpStatus = 'SUBSCRIBE_TEACHER_FAILURE';
                return this.state;
            case 'RESET_TEACHER_SUBSCRIBE_STATUS':
                this.state.teacher.signUpStatus = null;
                return this.state;
            case 'UPDATE_DANCE_FLOOR_OWNER_POSITION':
                this.state.danceFloorOwner.mapLat = action.lat;
                this.state.danceFloorOwner.mapLng = action.lng;
                return this.state;
            case 'UPDATE_DANCE_FLOOR_OWNER_EMAIL':
                this.state.danceFloorOwner.email = action.email;
                return this.state;
            case 'SUBSCRIBE_DANCE_FLOOR_OWNER_SUCCESS':
                this.state.danceFloorOwner.signUpStatus = 'SUBSCRIBE_DANCE_FLOOR_OWNER_SUCCESS';
                return this.state;
            case 'SUBSCRIBE_DANCE_FLOOR_OWNER_FAILURE':
                this.state.danceFloorOwner.signUpStatus = 'SUBSCRIBE_DANCE_FLOOR_OWNER_FAILURE';
                return this.state;
            case 'RESET_DANCE_FLOOR_OWNER_SUBSCRIBE_STATUS':
                this.state.danceFloorOwner.signUpStatus = null;
                return this.state;
            case 'UNSUBSCRIBE_SUCCESS':
                this.state.unsubscribeStatus = 'UNSUBSCRIBE_SUCCESS';
                return this.state;
            case 'UNSUBSCRIBE_FAILURE':
                this.state.unsubscribeStatus = 'UNSUBSCRIBE_FAILURE';
                return this.state;
            case 'RESET_UNSUBSCRIBE_STATUS':
                this.state.unsubscribeStatus = null;
                return this.state;
            default:
                return this.state;
        }
    }
}

const signUpState = new SignUpState();

export default signUpState;
