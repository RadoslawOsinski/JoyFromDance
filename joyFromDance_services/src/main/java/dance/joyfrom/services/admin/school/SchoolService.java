package dance.joyfrom.services.admin.school;

import dance.joyfrom.db.school.SchoolEntity;
import dance.joyfrom.db.school.SchoolRepository;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class SchoolService {

    private static final Logger LOG = LoggerFactory.getLogger(SchoolService.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

    private final SessionFactory sessionFactory;
    private final SchoolRepository schoolRepository;
    private final ModelMapper modelMapper;

    public SchoolService(SessionFactory sessionFactory, SchoolRepository schoolRepository) {
        this.sessionFactory = sessionFactory;
        this.schoolRepository = schoolRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(School.class, SchoolEntity.class);
    }

    public void add(School school) {
        LOG.info("Saving school: {}", school);
        SchoolEntity schoolEntity = modelMapper.map(school, SchoolEntity.class);
        schoolEntity.setCreated(ZonedDateTime.now());
        schoolRepository.add(sessionFactory.getCurrentSession(), schoolEntity);
    }

    public List<School> get(Integer currentPage, Integer displayedRows, String name) {
        return schoolRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows, name)
            .stream().map(se -> {
                School school = modelMapper.map(se, School.class);
                school.setCreated(se.getCreated().format(DATE_TIME_FORMATTER));
                return school;
            }).collect(Collectors.toList());
    }

}
