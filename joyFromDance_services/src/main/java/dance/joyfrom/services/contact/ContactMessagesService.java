package dance.joyfrom.services.contact;

import dance.joyfrom.db.contact.MessageEntity;
import dance.joyfrom.db.contact.MessageRepository;
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
@Service
public class ContactMessagesService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactMessagesService.class);

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");

    private final SessionFactory sessionFactory;
    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public ContactMessagesService(SessionFactory sessionFactory, MessageRepository messageRepository) {
        this.sessionFactory = sessionFactory;
        this.messageRepository = messageRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(ContactMessage.class, MessageEntity.class);
    }

    @Transactional
    public void add(ContactMessage contactMessage) {
        LOG.info("Saving contact message: {}", contactMessage);
        MessageEntity messageEntity = modelMapper.map(contactMessage, MessageEntity.class);
        messageEntity.setCreated(ZonedDateTime.now());
        messageRepository.add(sessionFactory.getCurrentSession(), messageEntity);
    }

    @Transactional
    public List<ContactMessage> get(Integer currentPage, Integer displayedRows, String name, String email) {
        return messageRepository.get(sessionFactory.getCurrentSession(), currentPage, displayedRows, name, email)
            .map(me -> {
                ContactMessage contactMessage = modelMapper.map(me, ContactMessage.class);
                contactMessage.setCreated(me.getCreated().format(DATE_TIME_FORMATTER));
                return contactMessage;
            }).collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long messageId) {
        LOG.info("Deleting contact message: {}", messageId);
        messageRepository.delete(sessionFactory.getCurrentSession(), messageId);
    }
}
