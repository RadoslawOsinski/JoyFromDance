package dance.joyfrom.services.admin.school;

import dance.joyfrom.db.school.SchoolAdminEntity;
import dance.joyfrom.db.school.SchoolAdminRepository;
import dance.joyfrom.db.school.SchoolEntity;
import dance.joyfrom.db.school.SchoolRepository;
import dance.joyfrom.services.paging.TablePageableResponse;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class SchoolAccountService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolAccountService.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    private final SessionFactory sessionFactory;
    private final SchoolRepository schoolRepository;
    private final SchoolAdminRepository schoolAdminRepository;
    private final ModelMapper modelMapper;

    public SchoolAccountService(SessionFactory sessionFactory, SchoolRepository schoolRepository, SchoolAdminRepository schoolAdminRepository) {
        this.sessionFactory = sessionFactory;
        this.schoolRepository = schoolRepository;
        this.schoolAdminRepository = schoolAdminRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(School.class, SchoolEntity.class);
    }

    public void add(String schoolName, String userEmail) {
        LOG.info("Saving school name: {}, email: {}", schoolName, userEmail);
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setName(schoolName);
        schoolEntity.setOwnerEmail(userEmail);
        schoolEntity.setCreated(ZonedDateTime.now(ZoneOffset.UTC));
        SchoolAdminEntity schoolAdmin = new SchoolAdminEntity();
        schoolAdmin.setSchoolId(schoolRepository.add(sessionFactory.getCurrentSession(), schoolEntity));
        schoolAdmin.setEmail(userEmail);
        schoolAdminRepository.add(sessionFactory.getCurrentSession(), schoolAdmin);
    }

    public void remove(Long schoolId, String userEmail) {
        if (schoolRepository.isSchoolOwner(sessionFactory.getCurrentSession(), schoolId, userEmail)) {
            LOG.info("Removing school: {}, email: {}", schoolId, userEmail);
            schoolAdminRepository.deleteBySchoolId(sessionFactory.getCurrentSession(), schoolId);
            schoolRepository.delete(sessionFactory.getCurrentSession(), schoolId);
            LOG.info("Removed school: {}, email: {}", schoolId, userEmail);
        } else {
            LOG.warn("School: {} is not owned by email: {}", schoolId, userEmail);
        }
    }

    public TablePageableResponse<SchoolAccount> get(Integer currentPage, Integer displayedRows, String userEmail, String name) {
        int counter = 0;
        List<SchoolEntity> response = schoolRepository.getOwned(sessionFactory.getCurrentSession(), currentPage, displayedRows, userEmail, name);
        Long totalNumber = schoolRepository.getOwnedTotalNumber(sessionFactory.getCurrentSession(), userEmail, name);
        TablePageableResponse<SchoolAccount> result = new TablePageableResponse<>();
        result.setTotalNumberOfPages((totalNumber / displayedRows) + (totalNumber % displayedRows == 0 ? 0 : 1));
        List<SchoolAccount> elements = new ArrayList<>();
        for (SchoolEntity se : response) {
            SchoolAccount school = modelMapper.map(se, SchoolAccount.class);
            school.setOrderNumber(currentPage * displayedRows + counter + 1);
            school.setOwnerEmail(se.getOwnerEmail());
            school.setCreated(se.getCreated() == null ? "" : se.getCreated().format(DATE_TIME_FORMATTER));
            ++counter;
            elements.add(school);
        }
        result.setResults(elements);
        return result;
    }

}
