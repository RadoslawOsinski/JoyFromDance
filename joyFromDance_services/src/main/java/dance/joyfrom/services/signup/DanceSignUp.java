package dance.joyfrom.services.signup;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
public class DanceSignUp {

    @NotEmpty
    @Email
    private String email;

    @NotNull
    private Double lat;

    @NotNull
    private Double lng;

    @NotNull
    private Integer distance;

    @NotEmpty
    private List<String> chosenDanceStyles;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public List<String> getChosenDanceStyles() {
        return chosenDanceStyles;
    }

    public void setChosenDanceStyles(List<String> chosenDanceStyles) {
        this.chosenDanceStyles = chosenDanceStyles;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("email", email)
            .append("lat", lat)
            .append("lng", lng)
            .append("distance", distance)
            .append("chosenDanceStyles", chosenDanceStyles)
            .toString();
    }
}
