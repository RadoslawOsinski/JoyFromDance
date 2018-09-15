package dance.joyfrom.services.dancer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by Radosław Osiński
 */
public class AggregatedLessonProposal {

    private String id;
    private Long lessonTypeId;
    private String start;
    private String end;
    private String name;
    private String description;
    private List<String> emails;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("id", id)
            .append("lessonTypeId", lessonTypeId)
            .append("start", start)
            .append("end", end)
            .append("name", name)
            .append("description", description)
            .append("emails", emails)
            .toString();
    }

}
