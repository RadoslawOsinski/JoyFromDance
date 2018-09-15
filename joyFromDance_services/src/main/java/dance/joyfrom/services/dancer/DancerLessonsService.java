package dance.joyfrom.services.dancer;

import dance.joyfrom.db.school.*;
import dance.joyfrom.services.school.RoomLessonForDancer;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class DancerLessonsService {

    private static final Logger LOG = LoggerFactory.getLogger(DancerLessonsService.class);

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    private final SessionFactory sessionFactory;
    private final DancerAssignedLessonRepository dancerAssignedLessonRepository;
    private final AssignedLessonDancerPairRepository assignedLessonDancerPairRepository;
    private final ModelMapper modelMapper;

    public DancerLessonsService(SessionFactory sessionFactory,
                                DancerAssignedLessonRepository dancerAssignedLessonRepository,
                                AssignedLessonDancerPairRepository assignedLessonDancerPairRepository
    ) {
        this.sessionFactory = sessionFactory;
        this.dancerAssignedLessonRepository = dancerAssignedLessonRepository;
        this.assignedLessonDancerPairRepository = assignedLessonDancerPairRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(RoomLessonForDancer.class, RoomLessonEntity.class);
    }

    public List<RoomLessonForDancer> get(Integer currentPage, Integer displayedRows, String searchSchoolName,
                                         String searchRoom, String searchDescription,
                                         String timezone, String email
    ) {
        return dancerAssignedLessonRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows,
            searchSchoolName, searchRoom, searchDescription, email
        ).map(sle -> {
            RoomLessonForDancer roomLesson = modelMapper.map(sle, RoomLessonForDancer.class);
            roomLesson.setRoomName(sle.getSchoolRoomEntity().getName());
            roomLesson.setSchoolName(sle.getSchoolRoomEntity().getSchoolEntity().getName());
            roomLesson.setStart(sle.getStartTime().withZoneSameInstant(ZoneId.of(timezone)).format(FORMATTER));
            roomLesson.setEnd(sle.getEndTime().withZoneSameInstant(ZoneId.of(timezone)).format(FORMATTER));
            return roomLesson;
        }).collect(Collectors.toList());
    }

    public void assignLessonToDancer(Long lessonId, String userEmail) {
        DancerAssignedLessonEntity dancerAssignedLesson = new DancerAssignedLessonEntity();
        dancerAssignedLesson.setEmail(userEmail);
        dancerAssignedLesson.setRoomLessonId(lessonId);
        LOG.info("Assigning dancer lesson: {}", dancerAssignedLesson);
        dancerAssignedLessonRepository.add(sessionFactory.getCurrentSession(), dancerAssignedLesson);
    }

    public void assignPairByDancer(Long lessonId, String dancerEmail, String otherDancerEmail) {
        LOG.info("Assigning dancer pair by dancer. LessonId: {}, dancerEmail: {}, otherDancerEmail: {}",
            lessonId, dancerEmail, otherDancerEmail
        );
        AssignedLessonDancerPairEntity assignedLessonDancerPair = new AssignedLessonDancerPairEntity();
        assignedLessonDancerPair.setRoomLessonId(lessonId);
        assignedLessonDancerPair.setEmail1(dancerEmail);
        assignedLessonDancerPair.setEmail2(otherDancerEmail);
        assignedLessonDancerPair.setStatus(DancerPairStatus.PENDING_ACCEPTANCE);
        assignedLessonDancerPairRepository.add(sessionFactory.getCurrentSession(), assignedLessonDancerPair);
    }

    public void acceptAssignedPairByDancer(Long pairId, String userEmail) {
        Optional<AssignedLessonDancerPairEntity> dancerPair = assignedLessonDancerPairRepository.getByIdAndEmail2(sessionFactory.getCurrentSession(), pairId, userEmail);
        dancerPair.ifPresent(assignedLessonDancerPairEntity -> {
            assignedLessonDancerPairEntity.setStatus(DancerPairStatus.AGREED);
            assignedLessonDancerPairRepository.deleteOtherProposals(
                sessionFactory.getCurrentSession(),
                assignedLessonDancerPairEntity.getRoomLessonId(),
                assignedLessonDancerPairEntity.getEmail2(),
                assignedLessonDancerPairEntity.getId()
            );
        });
    }
}
