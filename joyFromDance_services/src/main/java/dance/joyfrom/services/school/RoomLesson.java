package dance.joyfrom.services.school;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by Radosław Osiński
 */
public class RoomLesson {

    private Long id;
    private Long roomId;
    private String start;
    private String end;
    private String description;
    private String color;
    private String textColor;
    private Boolean published;
    private Boolean pairsRequired;
    private List<String> assignedTeachers;
    private List<AssignedDancer> assignedDancers;
    private List<DancerPair> dancerPairs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getPairsRequired() {
        return pairsRequired;
    }

    public void setPairsRequired(Boolean pairsRequired) {
        this.pairsRequired = pairsRequired;
    }

    public List<String> getAssignedTeachers() {
        return assignedTeachers;
    }

    public void setAssignedTeachers(List<String> assignedTeachers) {
        this.assignedTeachers = assignedTeachers;
    }

    public List<AssignedDancer> getAssignedDancers() {
        return assignedDancers;
    }

    public void setAssignedDancers(List<AssignedDancer> assignedDancers) {
        this.assignedDancers = assignedDancers;
    }

    public List<DancerPair> getDancerPairs() {
        return dancerPairs;
    }

    public void setDancerPairs(List<DancerPair> dancerPairs) {
        this.dancerPairs = dancerPairs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("roomId", roomId)
            .append("start", start)
            .append("end", end)
            .append("description", description)
            .append("color", color)
            .append("textColor", textColor)
            .append("published", published)
            .append("pairsRequired", pairsRequired)
            .append("assignedTeachers", assignedTeachers)
            .append("assignedDancers", assignedDancers)
            .append("dancerPairs", dancerPairs)
            .toString();
    }

}
