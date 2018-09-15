package dance.joyfrom.services.school;

import dance.joyfrom.db.school.SchoolRoomEntity;
import dance.joyfrom.db.school.SchoolRoomRepository;
import dance.joyfrom.db.school.SchoolTeacherEntity;
import dance.joyfrom.db.school.SchoolTeacherRepository;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Radosław Osiński
 */
@Service
public class SchoolRoomService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolRoomService.class);

    private final SessionFactory sessionFactory;
    private final SchoolRoomRepository schoolRoomRepository;
    private final ModelMapper modelMapper;

    public SchoolRoomService(SessionFactory sessionFactory, SchoolRoomRepository schoolRoomRepository/*, KeycloakUserService keycloakUserService*/) {
        this.sessionFactory = sessionFactory;
        this.schoolRoomRepository = schoolRoomRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(SchoolRoom.class, SchoolRoomEntity.class);
    }

    @Transactional
    public void add(Long schoolId, String name) {
        LOG.info("Saving school room: {}, name: {}", schoolId, name);
        SchoolRoomEntity schoolRoomEntity = new SchoolRoomEntity();
        schoolRoomEntity.setSchoolId(schoolId);
        schoolRoomEntity.setName(name);
        schoolRoomRepository.add(sessionFactory.getCurrentSession(), schoolRoomEntity);
    }

    @Transactional
    public void delete(Long schoolId, String name) {
        LOG.info("Deleting school room: {}, name: {}", schoolId, name);
        schoolRoomRepository.delete(sessionFactory.getCurrentSession(), schoolId, name);
    }

    @Transactional
    public List<SchoolRoom> get(Integer currentPage, Integer displayedRows, Long schoolId, String name) {
        return schoolRoomRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows, schoolId, name)
            .map(sre -> modelMapper.map(sre, SchoolRoom.class)).collect(Collectors.toList());
    }

}
