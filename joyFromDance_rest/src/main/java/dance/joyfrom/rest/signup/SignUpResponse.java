package dance.joyfrom.rest.signup;

/**
 * Created by Radosław Osiński
 */
public class SignUpResponse {

    private final String status;

    public SignUpResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
