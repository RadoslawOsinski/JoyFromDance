package dance.joyfrom.services.teacher;

import dance.joyfrom.db.school.RoomLessonEntity;
import dance.joyfrom.db.school.TeacherAssignedLessonRepository;
import dance.joyfrom.services.school.RoomLessonForTeacher;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Radosław Osiński
 */
@Service
public class TeacherLessonsService {

    private static final Logger LOG = LoggerFactory.getLogger(TeacherLessonsService.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    private final SessionFactory sessionFactory;
    private final TeacherAssignedLessonRepository teacherAssignedLessonRepository;
    private final ModelMapper modelMapper;

    public TeacherLessonsService(SessionFactory sessionFactory, TeacherAssignedLessonRepository teacherAssignedLessonRepository) {
        this.sessionFactory = sessionFactory;
        this.teacherAssignedLessonRepository = teacherAssignedLessonRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(RoomLessonForTeacher.class, RoomLessonEntity.class);
    }

    @Transactional
    public List<RoomLessonForTeacher> get(Integer currentPage, Integer displayedRows, String searchSchoolName,
                                          String searchRoom, String searchDescription,
                                          String timezone, String email
    ) {
        return teacherAssignedLessonRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows,
            searchSchoolName, searchRoom, searchDescription, email
        ).map(sle -> {
            RoomLessonForTeacher roomLesson = modelMapper.map(sle, RoomLessonForTeacher.class);
            roomLesson.setRoomName(sle.getSchoolRoomEntity().getName());
            roomLesson.setSchoolName(sle.getSchoolRoomEntity().getSchoolEntity().getName());
            roomLesson.setStart(sle.getStartTime().withZoneSameInstant(ZoneId.of(timezone)).format(FORMATTER));
            roomLesson.setEnd(sle.getEndTime().withZoneSameInstant(ZoneId.of(timezone)).format(FORMATTER));
            return roomLesson;
        }).collect(Collectors.toList());
    }

}
