package dance.joyfrom.services.school;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by Radosław Osiński
 */
public class RoomLessonForTeacher extends RoomLesson {

    private String roomName;
    private String schoolName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
            .appendSuper(super.toString())
            .append("roomName", roomName)
            .append("schoolName", schoolName)
            .toString();
    }
}
