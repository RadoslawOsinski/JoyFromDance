package dance.joyfrom.services.school;

import dance.joyfrom.db.school.SchoolTeacherEntity;
import dance.joyfrom.db.school.SchoolTeacherRepository;
import dance.joyfrom.services.paging.TablePageableResponse;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class SchoolTeachersService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolTeachersService.class);

    private final SessionFactory sessionFactory;
    private final SchoolTeacherRepository schoolTeacherRepository;
    private final ModelMapper modelMapper;
    //todo
//    private final KeycloakUserService keycloakUserService;

    public SchoolTeachersService(SessionFactory sessionFactory, SchoolTeacherRepository schoolTeacherRepository/*, KeycloakUserService keycloakUserService*/) {
        this.sessionFactory = sessionFactory;
        this.schoolTeacherRepository = schoolTeacherRepository;
//        this.keycloakUserService = keycloakUserService;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(SchoolTeacher.class, SchoolTeacherEntity.class);
    }

    public void add(Long schoolId, String email) {
        LOG.info("Saving school teacher: {}, email: {}", schoolId, email);
        SchoolTeacherEntity schoolTeacherEntity = new SchoolTeacherEntity();
        schoolTeacherEntity.setSchoolId(schoolId);
        schoolTeacherEntity.setEmail(email);
        schoolTeacherRepository.add(sessionFactory.getCurrentSession(), schoolTeacherEntity);
        //todo!
//        keycloakUserService.addUserRole(email, Roles.TEACHER);
    }

    public void delete(Long schoolId, String email) {
        LOG.info("Deleting school teacher: {}, email: {}", schoolId, email);
        schoolTeacherRepository.delete(sessionFactory.getCurrentSession(), schoolId, email);
        //todo remove role if no such role in any school!
//        keycloakUserService.removeUserRole(email, Roles.TEACHER);
    }


    public List<SchoolTeacher> get(Integer currentPage, Integer displayedRows, Long schoolId, String firstName, String lastName, String email, Boolean ascendingEmailSortOrder) {
        int counter = 0;
        List<SchoolTeacher> schoolTeachers = new ArrayList<>();
        List<SchoolTeacherEntity> schoolTeacherEntities = schoolTeacherRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows, schoolId, email, ascendingEmailSortOrder);
        for (SchoolTeacherEntity schoolTeacherEntity : schoolTeacherEntities) {
            SchoolTeacher schoolTeacher = modelMapper.map(schoolTeacherEntity, SchoolTeacher.class);
            schoolTeacher.setOrderNumber(currentPage * displayedRows + counter + 1);
            ++counter;
            schoolTeachers.add(schoolTeacher);
        }
        return schoolTeachers;
    }

    private Long getTotalNumberOfPages(Long schoolId, String firstNameSearch, String lastNameSearch, String emailSearch) {
        return schoolTeacherRepository.getTotalNumber(sessionFactory.getCurrentSession(), schoolId, emailSearch);
    }

    public TablePageableResponse<SchoolTeacher> getSchoolTeachersResponse(
        Integer currentPage, Integer displayedRows, Long schoolId, String firstNameSearch, String lastNameSearch, String emailSearch,
        Boolean ascendingEmailSortOrder
    ) {
        TablePageableResponse<SchoolTeacher> tablePageableResponse = new TablePageableResponse<>();
        tablePageableResponse.setResults(get(currentPage, displayedRows, schoolId, firstNameSearch, lastNameSearch, emailSearch, ascendingEmailSortOrder));
        Long totalResults = getTotalNumberOfPages(schoolId, firstNameSearch, lastNameSearch, emailSearch);
        tablePageableResponse.setTotalNumberOfPages((totalResults / displayedRows) + (totalResults % displayedRows == 0 ? 0 : 1));
        return tablePageableResponse;
    }

}
