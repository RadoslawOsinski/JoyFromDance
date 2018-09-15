package dance.joyfrom.services.dancer;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * Created by Radosław Osiński
 */
public class LessonProposals {

    private List<LessonProposal> lessonProposals;

    public List<LessonProposal> getLessonProposals() {
        return lessonProposals;
    }

    public void setLessonProposals(List<LessonProposal> lessonProposals) {
        this.lessonProposals = lessonProposals;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .append("lessonProposals", lessonProposals)
            .toString();
    }
}
