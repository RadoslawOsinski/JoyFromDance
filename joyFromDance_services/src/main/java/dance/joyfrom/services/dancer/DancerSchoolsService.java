package dance.joyfrom.services.dancer;

import dance.joyfrom.db.dancer.DancerSchoolEntity;
import dance.joyfrom.db.dancer.DancerSchoolRepository;
import dance.joyfrom.services.school.SchoolRoom;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class DancerSchoolsService {

    private final SessionFactory sessionFactory;
    private final DancerSchoolRepository dancerSchoolRepository;

    public DancerSchoolsService(SessionFactory sessionFactory, DancerSchoolRepository dancerSchoolRepository) {
        this.sessionFactory = sessionFactory;
        this.dancerSchoolRepository = dancerSchoolRepository;
    }

    public List<DancerSchool> get(Integer currentPage, Integer displayedRows, String searchSchoolName, String userEmail) {
        return dancerSchoolRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows,
            searchSchoolName, userEmail
        ).map(ds -> {
            DancerSchool dancerSchool = new DancerSchool();
            dancerSchool.setSchoolId(String.valueOf(ds.getSchoolId()));
            dancerSchool.setSchoolName(ds.getSchool().getName());
            dancerSchool.setEmail(userEmail);
            return dancerSchool;
        }).collect(Collectors.toList());
    }

    public void add(Long schoolId, String userEmail) {
        DancerSchoolEntity dancerSchool = new DancerSchoolEntity();
        dancerSchool.setSchoolId(schoolId);
        dancerSchool.setEmail(userEmail);
        dancerSchoolRepository.add(sessionFactory.getCurrentSession(), dancerSchool);
    }

    public List<SchoolRoom> getSchoolRooms(Long schoolId) {
        return dancerSchoolRepository.getSchoolRooms(sessionFactory.getCurrentSession(), schoolId)
            .stream().map(sre -> {
                SchoolRoom schoolRoom = new SchoolRoom();
                schoolRoom.setId(String.valueOf(sre.getId()));
                schoolRoom.setName(sre.getName());
                schoolRoom.setSchoolId(schoolId);
                return schoolRoom;
            }).collect(Collectors.toList());
    }
}
