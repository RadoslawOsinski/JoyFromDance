package dance.joyfrom.services.user;

import dance.joyfrom.db.user.UserEntity;
import dance.joyfrom.db.user.UserRepository;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final SessionFactory sessionFactory;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(SessionFactory sessionFactory, UserRepository userRepository) {
        this.sessionFactory = sessionFactory;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(User.class, UserEntity.class);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(sessionFactory.getCurrentSession(), email)
            .map(u -> modelMapper.map(u, User.class));
    }
}
