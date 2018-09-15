package dance.joyfrom.services.dancer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Radosław Osiński
 */
public class LessonProposal {

    private String id;
    private String email;
    private Long lessonTypeId;
    private String start;
    private String end;
    private String name;
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getLessonTypeId() {
        return lessonTypeId;
    }

    public void setLessonTypeId(Long lessonTypeId) {
        this.lessonTypeId = lessonTypeId;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("email", email)
            .append("lessonTypeId", lessonTypeId)
            .append("start", start)
            .append("end", end)
            .append("name", name)
            .append("description", description)
            .toString();
    }

}
