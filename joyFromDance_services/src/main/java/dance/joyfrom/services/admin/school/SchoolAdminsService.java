package dance.joyfrom.services.admin.school;

import dance.joyfrom.db.school.SchoolAdminEntity;
import dance.joyfrom.db.school.SchoolAdminRepository;
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
public class SchoolAdminsService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolAdminsService.class);

    private final SessionFactory sessionFactory;
    private final SchoolAdminRepository schoolAdminRepository;
    private final ModelMapper modelMapper;
//    private final KeycloakUserService keycloakUserService;

    public SchoolAdminsService(SessionFactory sessionFactory, SchoolAdminRepository schoolAdminRepository/*, KeycloakUserService keycloakUserService*/) {
        this.sessionFactory = sessionFactory;
        this.schoolAdminRepository = schoolAdminRepository;
//        this.keycloakUserService = keycloakUserService;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(SchoolAdmin.class, SchoolAdminEntity.class);
    }

    @Transactional
    public void add(Long schoolId, String newSchoolAdministrator) {
        LOG.info("Saving school administrator: {}, newSchoolAdministrator: {}", schoolId, newSchoolAdministrator);
        SchoolAdminEntity schoolAdminEntity = new SchoolAdminEntity();
        schoolAdminEntity.setSchoolId(schoolId);
        schoolAdminEntity.setEmail(newSchoolAdministrator);
        schoolAdminRepository.add(sessionFactory.getCurrentSession(), schoolAdminEntity);
//        keycloakUserService.addUserRole(newSchoolAdministrator, Roles.SCHOOL_ADMIN);
    }

    @Transactional
    public void delete(Long schoolId, String email) {
        LOG.info("Deleting school administrator: {}, email: {}", schoolId, email);
        schoolAdminRepository.delete(sessionFactory.getCurrentSession(), schoolId, email);
//        keycloakUserService.removeUserRole(email, Roles.SCHOOL_ADMIN);
    }

    @Transactional
    public List<SchoolAdmin> get(Long schoolId) {
        return schoolAdminRepository.get(sessionFactory.getCurrentSession(), schoolId)
            .map(sae -> modelMapper.map(sae, SchoolAdmin.class)).collect(Collectors.toList());
    }

    @Transactional
    public List<SchoolAdmin> getAdminSchools(String email) {
        return schoolAdminRepository.getAdminSchools(sessionFactory.getCurrentSession(), email)
            .map(sae -> modelMapper.map(sae, SchoolAdmin.class)).collect(Collectors.toList());
    }

}
