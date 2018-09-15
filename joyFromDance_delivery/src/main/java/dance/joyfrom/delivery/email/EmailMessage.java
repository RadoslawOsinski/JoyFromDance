package dance.joyfrom.delivery.email;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Radosław Osiński
 */
public class EmailMessage {

    @NotNull
    @NotBlank
    @Email
    private String to;

    @NotBlank
    private String text;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
