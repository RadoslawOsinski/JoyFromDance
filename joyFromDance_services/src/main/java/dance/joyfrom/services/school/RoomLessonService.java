package dance.joyfrom.services.school;

import dance.joyfrom.db.school.*;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class RoomLessonService {

    private static final Logger LOG = LoggerFactory.getLogger(RoomLessonService.class);

    private final SessionFactory sessionFactory;
    private final RoomLessonRepository roomLessonRepository;
    private final TeacherAssignedLessonRepository teacherAssignedLessonRepository;
    private final ModelMapper modelMapper;

    public RoomLessonService(SessionFactory sessionFactory, RoomLessonRepository roomLessonRepository, TeacherAssignedLessonRepository teacherAssignedLessonRepository) {
        this.sessionFactory = sessionFactory;
        this.roomLessonRepository = roomLessonRepository;
        this.teacherAssignedLessonRepository = teacherAssignedLessonRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(SchoolRoom.class, SchoolRoomEntity.class);
    }

    public void add(RoomLesson roomLesson) {
        LOG.info("Saving room lesson: {}", roomLesson);
        RoomLessonEntity roomLessonEntity = new RoomLessonEntity();
        roomLessonEntity.setRoomId(roomLesson.getRoomId());
        ZonedDateTime start = ZonedDateTime.from(
            DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).parse(roomLesson.getStart())
        );
        roomLessonEntity.setStartTime(start);
        ZonedDateTime end = ZonedDateTime.from(
            DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).parse(roomLesson.getEnd())
        );
        roomLessonEntity.setEndTime(end);
        roomLessonEntity.setDescription(roomLesson.getDescription());
        roomLessonEntity.setPairsRequired(roomLesson.getPairsRequired());
        roomLessonEntity.setColor(roomLesson.getColor());
        roomLessonEntity.setTextColor(roomLesson.getTextColor());
        roomLessonEntity.setPublished(Boolean.FALSE);
        roomLessonRepository.add(sessionFactory.getCurrentSession(), roomLessonEntity);
    }

    public boolean isAuthorizedToAddLesson(String userEmail, Long roomId) {
        return roomLessonRepository.isAuthorizedToAddLesson(sessionFactory.getCurrentSession(), userEmail, roomId);
    }

    public List<RoomLesson> getTimezonedRoomLessonsForSchool(Long roomId, Long startValue, Long endValue, String timezone) {
        ZoneId timezoneId = ZoneId.of(timezone);
        ZonedDateTime start = Instant.ofEpochMilli(startValue).atZone(timezoneId);
        ZonedDateTime end = Instant.ofEpochMilli(endValue).atZone(timezoneId);
        Stream<RoomLessonEntity> roomLessons = roomLessonRepository.get(sessionFactory.getCurrentSession(), roomId, start, end);
        return roomLessons.map(rle -> {
            RoomLesson roomLesson = modelMapper.map(rle, RoomLesson.class);
            roomLesson.setStart(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(rle.getStartTime()));
            roomLesson.setEnd(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(rle.getEndTime()));
            roomLesson.setAssignedTeachers(rle.getAssignedTeachers().stream().map(TeacherAssignedLessonEntity::getEmail).collect(Collectors.toList()));
            roomLesson.setAssignedDancers(rle.getAssignedDancers().stream().map(DancerAssignedLessonEntity::getEmail).map(email -> {
                AssignedDancer assignedDancer = new AssignedDancer();
                assignedDancer.setEmail(email);
                return assignedDancer;
            }).collect(Collectors.toList()));
            roomLesson.setDancerPairs(rle.getDancerPairs().stream().map(dpe -> {
                DancerPair dancerPair = new DancerPair();
                dancerPair.setId(dpe.getId());
                dancerPair.setEmail1(dpe.getEmail1());
                dancerPair.setEmail2(dpe.getEmail2());
                dancerPair.setStatus(dpe.getStatus());
                return dancerPair;
            }).collect(Collectors.toList()));
            return roomLesson;
        }).collect(Collectors.toList());
    }

    public List<RoomLesson> getTimezonedRoomLessonsForDancer(Long roomId, Long startValue, Long endValue, String timezone,
                                                             String dancerEmail
    ) {
        ZoneId timezoneId = ZoneId.of(timezone);
        ZonedDateTime start = Instant.ofEpochMilli(startValue).atZone(timezoneId);
        ZonedDateTime end = Instant.ofEpochMilli(endValue).atZone(timezoneId);
        Stream<RoomLessonEntity> roomLessons = roomLessonRepository.get(sessionFactory.getCurrentSession(), roomId, start, end);
        return roomLessons.map(rle -> convertRoomLesson(dancerEmail, timezoneId, rle)).collect(Collectors.toList());
    }

    public RoomLesson getRoomLessonForDancer(Long lessonId, String timezone, String dancerEmail) {
        ZoneId timezoneId = ZoneId.of(timezone);
        RoomLessonEntity rle = roomLessonRepository.get(sessionFactory.getCurrentSession(), lessonId);
        return convertRoomLesson(dancerEmail, timezoneId, rle);
    }

    private RoomLesson convertRoomLesson(String dancerEmail, ZoneId timezoneId, RoomLessonEntity rle) {
        RoomLesson roomLesson = modelMapper.map(rle, RoomLesson.class);
        roomLesson.setStart(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(rle.getStartTime()));
        roomLesson.setEnd(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(rle.getEndTime()));
        roomLesson.setAssignedTeachers(rle.getAssignedTeachers().stream().map(TeacherAssignedLessonEntity::getEmail).collect(Collectors.toList()));
        roomLesson.setAssignedDancers(rle.getAssignedDancers().stream().map(DancerAssignedLessonEntity::getEmail).map(email -> {
            AssignedDancer assignedDancer = new AssignedDancer();
            assignedDancer.setEmail(email);
            return assignedDancer;
        }).collect(Collectors.toList()));
        roomLesson.setDancerPairs(rle.getDancerPairs().stream().filter(dancerPair ->
            dancerPair.getStatus() == DancerPairStatus.AGREED ||
            dancerPair.getStatus() == DancerPairStatus.ASSIGNED_BY_SCHOOL ||
            (dancerPair.getStatus() == DancerPairStatus.PENDING_ACCEPTANCE &&
                dancerPair.getEmail1().equals(dancerEmail) ||
                dancerPair.getEmail2().equals(dancerEmail)
            )
        ).map(dpe -> {
            DancerPair dancerPair = new DancerPair();
            dancerPair.setId(dpe.getId());
            dancerPair.setEmail1(dpe.getEmail1());
            dancerPair.setEmail2(dpe.getEmail2());
            dancerPair.setStatus(dpe.getStatus());
            return dancerPair;
        }).collect(Collectors.toList()));
        return roomLesson;
    }

    public void update(RoomLesson roomLesson) {
        LOG.info("Update room lesson: {}", roomLesson);
        RoomLessonEntity roomLessonEntity = new RoomLessonEntity();
        roomLessonEntity.setId(roomLesson.getId());
        roomLessonEntity.setRoomId(roomLesson.getRoomId());
        ZonedDateTime start = ZonedDateTime.from(
            DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).parse(roomLesson.getStart())
        );
        roomLessonEntity.setStartTime(start);
        ZonedDateTime end = ZonedDateTime.from(
            DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).parse(roomLesson.getEnd())
        );
        roomLessonEntity.setEndTime(end);
        roomLessonEntity.setDescription(roomLesson.getDescription());
        roomLessonEntity.setColor(roomLesson.getColor());
        roomLessonEntity.setTextColor(roomLesson.getTextColor());
        if (roomLesson.getPublished() == null) {
            roomLessonEntity.setPublished(Boolean.FALSE);
        } else {
            roomLessonEntity.setPublished(roomLesson.getPublished());
        }
        if (roomLesson.getPairsRequired() == null) {
            roomLessonEntity.setPairsRequired(Boolean.FALSE);
        } else {
            roomLessonEntity.setPairsRequired(roomLesson.getPairsRequired());
        }
        roomLessonRepository.update(sessionFactory.getCurrentSession(), roomLessonEntity);
    }

    public void assignTeachersToLesson(Long roomLessonId, List<String> teacherEmails) {
        for (String teacherEmail : teacherEmails) {
            TeacherAssignedLessonEntity teacherAssignedLessonEntity = new TeacherAssignedLessonEntity();
            teacherAssignedLessonEntity.setRoomLessonId(roomLessonId);
            teacherAssignedLessonEntity.setEmail(teacherEmail);
            teacherAssignedLessonRepository.add(sessionFactory.getCurrentSession(), teacherAssignedLessonEntity);
        }
    }

//    @Transactional
//    public void delete(Long schoolId, String name) {
//        LOG.info("Deleting school room: {}, name: {}", schoolId, name);
//        schoolRoomRepository.delete(sessionFactory.getCurrentSession(), schoolId, name);
//    }

}
