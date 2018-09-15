package dance.joyfrom.services.user;

import dance.joyfrom.db.user.UserRoleEntity;
import dance.joyfrom.db.user.UserRolesRepository;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Radosław Osiński
 */
@Transactional
@Service
public class UserRolesService {

    private final SessionFactory sessionFactory;
    private final UserRolesRepository userRolesRepository;
    private final ModelMapper modelMapper;

    public UserRolesService(SessionFactory sessionFactory, UserRolesRepository userRolesRepository) {
        this.sessionFactory = sessionFactory;
        this.userRolesRepository = userRolesRepository;
        this.modelMapper = new ModelMapper();
        modelMapper.createTypeMap(UserRole.class, UserRoleEntity.class);
    }

    public List<UserRole> listUserRoles(long userId) {
        return  userRolesRepository.listUserRoles(sessionFactory.getCurrentSession(), userId)
            .stream().map(ur -> modelMapper.map(ur, UserRole.class)).collect(Collectors.toList());
    }
}
