package dance.joyfrom.services.dancer;

import dance.joyfrom.db.dancer.LessonProposalEntity;
import dance.joyfrom.db.dancer.LessonProposalsRepository;
import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class LessonProposalsService {

    private static final Logger LOG = LoggerFactory.getLogger(LessonProposalsService.class);

    private final SessionFactory sessionFactory;
    private final LessonProposalsRepository lessonProposalsRepository;
    private final ModelMapper modelMapper;

    public LessonProposalsService(SessionFactory sessionFactory, LessonProposalsRepository lessonProposalsRepository) {
        this.sessionFactory = sessionFactory;
        this.lessonProposalsRepository = lessonProposalsRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(LessonProposal.class, LessonProposalEntity.class);
    }

    public void add(LessonProposals lessonProposals, String userEmail) {
        for (LessonProposal lessonProposal : lessonProposals.getLessonProposals()) {
            if (lessonProposal.getId() == null) {
                LessonProposalEntity lessonProposalEntity = new LessonProposalEntity();
                lessonProposalEntity.setEmail(userEmail);
                lessonProposalEntity.setLessonTypeId(lessonProposal.getLessonTypeId());
                ZonedDateTime start = ZonedDateTime.from(
                    DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).parse(lessonProposal.getStart())
                );
                lessonProposalEntity.setStartTime(start);
                ZonedDateTime end = ZonedDateTime.from(
                    DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC).parse(lessonProposal.getEnd())
                );
                lessonProposalEntity.setEndTime(end);
                LOG.info("Saving lesson proposal: {}", lessonProposalEntity);
                lessonProposalsRepository.add(sessionFactory.getCurrentSession(), lessonProposalEntity);
            } else {
                LOG.info("Update lesson proposal: {}", lessonProposal);
            }
        }
    }


    private ZonedDateTime convertToCurrentWeekDate(ZonedDateTime zonedDate) {
        DayOfWeek dayOfWeek = zonedDate.getDayOfWeek();
        ZonedDateTime sameDayInCurrentWeek = (ZonedDateTime) dayOfWeek.adjustInto(ZonedDateTime.now());
        return ZonedDateTime.of(sameDayInCurrentWeek.toLocalDate(),
            LocalTime.of(zonedDate.getHour(), zonedDate.getMinute(), zonedDate.getSecond(), zonedDate.getNano()),
            zonedDate.getZone()
        );
    }

    public List<LessonProposal> getTimezonedLessonProposals(String userEmail, String timezone) {
        ZoneId timezoneId = ZoneId.of(timezone);
        Stream<LessonProposalEntity> lessonProposals = lessonProposalsRepository.get(sessionFactory.getCurrentSession(), userEmail);
        return lessonProposals.map(lpe -> {
            LessonProposal lessonProposal = modelMapper.map(lpe, LessonProposal.class);
            lessonProposal.setStart(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(convertToCurrentWeekDate(lpe.getStartTime())));
            lessonProposal.setEnd(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(convertToCurrentWeekDate(lpe.getEndTime())));
            lessonProposal.setName(lpe.getLessonTypeEntity().getName());
            lessonProposal.setDescription(lpe.getLessonTypeEntity().getDescription());
            return lessonProposal;
        }).collect(Collectors.toList());
    }

    public List<AggregatedLessonProposal> getTimezonedSchoolLessonProposals(Long schoolId,
                                                                  String lessonTypeName,
                                                                  Integer minDancers,
                                                                  String timezone
    ) {
        ZoneId timezoneId = ZoneId.of(timezone);
        Stream<LessonProposalEntity> lessonProposals = lessonProposalsRepository.get(
            sessionFactory.getCurrentSession(), schoolId, lessonTypeName
        );
        List<LessonProposal> currentWeekProposals = lessonProposals.map(lpe -> {
            LessonProposal lessonProposal = modelMapper.map(lpe, LessonProposal.class);
            lessonProposal.setStart(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(convertToCurrentWeekDate(lpe.getStartTime())));
            lessonProposal.setEnd(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(timezoneId).format(convertToCurrentWeekDate(lpe.getEndTime())));
            lessonProposal.setName(lpe.getLessonTypeEntity().getName());
            lessonProposal.setDescription(lpe.getLessonTypeEntity().getDescription());
            return lessonProposal;
        }).collect(Collectors.toList());

        MultiValuedMap<String, LessonProposal> aggregatedProposals = new ArrayListValuedHashMap<>();
        for (LessonProposal currentWeekProposal : currentWeekProposals) {
            aggregatedProposals.put(getAggregatedKeyFromProposal(currentWeekProposal), currentWeekProposal);
        }

        for (MapIterator<String, LessonProposal> i = aggregatedProposals.mapIterator(); i.hasNext();) {
            String aggregatedProposalKey = i.next();
            if (aggregatedProposals.get(aggregatedProposalKey).size() < minDancers) {
                i.remove();
            }
        }

        List<AggregatedLessonProposal> aggregatedLessonProposals = new ArrayList<>();
        Set<String> filteredKeys = aggregatedProposals.keys().uniqueSet();
        for (String aggregatedProposalKey : filteredKeys) {
            List<LessonProposal> singleLessonProposals = ((ArrayListValuedHashMap) aggregatedProposals).get(aggregatedProposalKey);
            LessonProposal singleLessonProposal = singleLessonProposals.get(0);
            AggregatedLessonProposal aggregatedLessonProposal = new AggregatedLessonProposal();
            aggregatedLessonProposal.setName(singleLessonProposal.getName());
            aggregatedLessonProposal.setDescription(singleLessonProposal.getDescription());
            aggregatedLessonProposal.setLessonTypeId(singleLessonProposal.getLessonTypeId());
            aggregatedLessonProposal.setStart(singleLessonProposal.getStart());
            aggregatedLessonProposal.setEnd(singleLessonProposal.getEnd());
            aggregatedLessonProposal.setEmails(singleLessonProposals.stream().map(LessonProposal::getEmail).collect(Collectors.toList()));
            aggregatedLessonProposals.add(aggregatedLessonProposal);
        }
        return aggregatedLessonProposals;
    }

    private String getAggregatedKeyFromProposal(LessonProposal proposal) {
        return proposal.getLessonTypeId() + "_" + proposal.getStart() + "_" + proposal.getEnd();
    }
}
