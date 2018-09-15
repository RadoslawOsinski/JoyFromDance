package dance.joyfrom.services.school;

import dance.joyfrom.db.school.LessonTypeEntity;
import dance.joyfrom.db.school.LessonTypeRepository;
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
@Transactional
@Service
public class SchoolLessonTypesService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolLessonTypesService.class);

    private final SessionFactory sessionFactory;
    private final LessonTypeRepository lessonTypeRepository;
    private final ModelMapper modelMapper;

    public SchoolLessonTypesService(SessionFactory sessionFactory, LessonTypeRepository lessonTypeRepository) {
        this.sessionFactory = sessionFactory;
        this.lessonTypeRepository = lessonTypeRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(LessonType.class, LessonTypeEntity.class);
    }

    public void add(Long schoolId, String lessonTypeName, String lessonTypeDescription) {
        LOG.info("Saving lesson type school id: {}, name: {}, description", schoolId, lessonTypeName, lessonTypeDescription);
        LessonTypeEntity lessonTypeEntity = new LessonTypeEntity();
        lessonTypeEntity.setSchoolId(schoolId);
        lessonTypeEntity.setName(lessonTypeName);
        lessonTypeEntity.setDescription(lessonTypeDescription);
        lessonTypeRepository.add(sessionFactory.getCurrentSession(), lessonTypeEntity);
    }

//    public void delete(Long schoolId, String name) {
//        LOG.info("Deleting school room: {}, name: {}", schoolId, name);
//        schoolRoomRepository.delete(sessionFactory.getCurrentSession(), schoolId, name);
//    }

    public List<LessonType> get(Integer currentPage, Integer displayedRows, Long schoolId, String name) {
        return lessonTypeRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows, schoolId, name)
            .map(lte -> modelMapper.map(lte, LessonType.class)).collect(Collectors.toList());
    }

    public List<LessonType> get(Long schoolId) {
        return lessonTypeRepository.getBySchoolId(sessionFactory.getCurrentSession(), schoolId)
            .map(lte -> modelMapper.map(lte, LessonType.class)).collect(Collectors.toList());
    }

}
