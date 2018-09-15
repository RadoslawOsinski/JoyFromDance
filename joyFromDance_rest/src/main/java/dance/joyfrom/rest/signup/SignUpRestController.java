package dance.joyfrom.rest.signup;

import dance.joyfrom.services.signup.DanceSignUp;
import dance.joyfrom.services.signup.SignUp;
import dance.joyfrom.services.signup.SignUpService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Created by Radosław Osiński
 */
@RestController
public class SignUpRestController {

    private final SignUpService signUpService;

    public SignUpRestController(SignUpService signUpService) {
        this.signUpService = signUpService;
    }

//    curl -X PUT -H 'Accept: application/json;charset=UTF-8' -H 'Content-Type: application/json;charset=UTF-8' -d '{"email": "ra@wp.pl", "lat": 0, "lng": 0, "distance": 5, "chosenDanceStyles": ["Samba"]}' http://127.0.0.1:8080/rest/public/dancer/signUp
    @RequestMapping(value = "/rest/public/dancer/signUp", method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public SignUpResponse signUpDancer(@RequestBody @Valid DanceSignUp signUpRequest) {
        return new SignUpResponse(signUpService.dancerSignUp(signUpRequest));
    }

//    curl -X PUT -H 'Accept: application/json;charset=UTF-8' -H 'Content-Type: application/json;charset=UTF-8' -d '{"email": "ra@wp.pl", "lat": 0, "lng": 0, "distance": 5, "chosenDanceStyles": ["Samba"]}' http://127.0.0.1:8080/rest/public/teacher/signUp
    @RequestMapping(value = "/rest/public/teacher/signUp", method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public SignUpResponse signUpTeacher(@RequestBody @Valid DanceSignUp signUpRequest) {
        return new SignUpResponse(signUpService.teacherSignUp(signUpRequest));
    }

//    curl -X PUT -H 'Accept: application/json;charset=UTF-8' -H 'Content-Type: application/json;charset=UTF-8' -d '{"email": "ra@wp.pl", "lat": 0, "lng": 0}' http://127.0.0.1:8080/rest/public/danceFloorOwner/signUp
    @RequestMapping(value = "/rest/public/danceFloorOwner/signUp", method = RequestMethod.PUT,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public SignUpResponse signUpDanceFloorOwner(@RequestBody @Valid SignUp signUpRequest) {
        return new SignUpResponse(signUpService.danceFloorOwnerSignUp(signUpRequest));
    }

    //curl -X DELETE -H 'Accept: application/json;charset=UTF-8' -H 'Content-Type: application/json;charset=UTF-8' http://127.0.0.1:8080/rest/public/unSubscribe/rado@
    @RequestMapping(value = "/rest/public/unSubscribe/{email}", method = RequestMethod.DELETE,
        consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    public void unSubscribe(@PathVariable @Valid @NotEmpty @Email String email) {
        signUpService.unSubscribe(email);
    }

}
