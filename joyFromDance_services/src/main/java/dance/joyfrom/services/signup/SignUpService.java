package dance.joyfrom.services.signup;

import dance.joyfrom.db.signup.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
public class SignUpService {

    private static final Logger LOG = LoggerFactory.getLogger(SignUpService.class);
    private final SessionFactory sessionFactory;
    private final SignUpDancerRepository signUpDancerRepository;
    private final SignUpDancerStyleRepository signUpDancerStyleRepository;
    private final SignUpTeacherRepository signUpTeacherRepository;
    private final SignUpTeacherStyleRepository signUpTeacherStyleRepository;
    private final SignUpDanceFloorOwnerRepository signUpDanceFloorOwnerRepository;

    public SignUpService(
        SessionFactory sessionFactory, SignUpDancerRepository signUpDancerRepository,
        SignUpDancerStyleRepository signUpDancerStyleRepository,
        SignUpTeacherRepository signUpTeacherRepository,
        SignUpTeacherStyleRepository signUpTeacherStyleRepository,
        SignUpDanceFloorOwnerRepository signUpDanceFloorOwnerRepository) {
        this.sessionFactory = sessionFactory;
        this.signUpDancerRepository = signUpDancerRepository;
        this.signUpDancerStyleRepository = signUpDancerStyleRepository;
        this.signUpTeacherRepository = signUpTeacherRepository;
        this.signUpTeacherStyleRepository = signUpTeacherStyleRepository;
        this.signUpDanceFloorOwnerRepository = signUpDanceFloorOwnerRepository;
    }

    public void unSubscribe(String email) {
        LOG.info("UnSubscribe for {}", email);
        Session currentSession = sessionFactory.getCurrentSession();
        signUpDancerStyleRepository.delete(currentSession, email);
        signUpDancerRepository.delete(currentSession, email);
        signUpTeacherStyleRepository.delete(currentSession, email);
        signUpTeacherRepository.delete(currentSession, email);
        signUpDanceFloorOwnerRepository.delete(currentSession, email);
    }

    public String dancerSignUp(DanceSignUp signUpRequest) {
        LOG.info("Dancer sign up: {}", signUpRequest);
        SignUpDancerEntity signUpDancerEntity = new SignUpDancerEntity();
        signUpDancerEntity.setEmail(signUpRequest.getEmail());
        signUpDancerEntity.setLat(signUpRequest.getLat());
        signUpDancerEntity.setLng(signUpRequest.getLng());
        signUpDancerEntity.setDistance(signUpRequest.getDistance());
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<SignUpDancerEntity> existingSignUpDancerOptional = signUpDancerRepository.get(currentSession, signUpDancerEntity.getEmail());
        String status = "ADDED";
        if (existingSignUpDancerOptional.isPresent()) {
            LOG.info("Sign up update dancer. New: {}, old: {}", signUpDancerEntity, existingSignUpDancerOptional.get());
            signUpDancerRepository.update(currentSession, signUpDancerEntity);
            signUpDancerStyleRepository.delete(currentSession, signUpDancerEntity.getEmail());
            status = "UPDATED";
        } else {
            LOG.info("Sign up add dancer. New: {}", signUpDancerEntity);
            signUpDancerRepository.add(currentSession, signUpDancerEntity);
        }
        LOG.info("Sign up dancer {} for: {}", signUpRequest.getEmail(), signUpRequest.getChosenDanceStyles());
        for (String chosenDanceStyle : signUpRequest.getChosenDanceStyles()) {
            SignUpDancerStyleEntity signUpDancerStyleEntity = new SignUpDancerStyleEntity();
            signUpDancerStyleEntity.setEmail(signUpRequest.getEmail());
            signUpDancerStyleEntity.setStyle(chosenDanceStyle);
            signUpDancerStyleRepository.add(currentSession, signUpDancerStyleEntity);
        }
        return status;
    }

    public String teacherSignUp(DanceSignUp signUpRequest) {
        LOG.info("Teacher sign up: {}", signUpRequest);
        SignUpTeacherEntity signUpTeacherEntity = new SignUpTeacherEntity();
        signUpTeacherEntity.setEmail(signUpRequest.getEmail());
        signUpTeacherEntity.setLat(signUpRequest.getLat());
        signUpTeacherEntity.setLng(signUpRequest.getLng());
        signUpTeacherEntity.setDistance(signUpRequest.getDistance());
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<SignUpTeacherEntity> existingSignUpTeacherOptional = signUpTeacherRepository.get(currentSession, signUpTeacherEntity.getEmail());
        String status = "ADDED";
        if (existingSignUpTeacherOptional.isPresent()) {
            LOG.info("Sign up update teacher. New: {}, old: {}", signUpTeacherEntity, existingSignUpTeacherOptional.get());
            signUpTeacherRepository.update(currentSession, signUpTeacherEntity);
            signUpTeacherStyleRepository.delete(currentSession, signUpTeacherEntity.getEmail());
            status = "UPDATED";
        } else {
            LOG.info("Sign up add teacher. New: {}", signUpTeacherEntity);
            signUpTeacherRepository.add(currentSession, signUpTeacherEntity);
        }
        LOG.info("Sign up teacher {} for: {}", signUpRequest.getEmail(), signUpRequest.getChosenDanceStyles());
        for (String chosenDanceStyle : signUpRequest.getChosenDanceStyles()) {
            SignUpTeacherStyleEntity signUpTeacherStyleEntity = new SignUpTeacherStyleEntity();
            signUpTeacherStyleEntity.setEmail(signUpRequest.getEmail());
            signUpTeacherStyleEntity.setStyle(chosenDanceStyle);
            signUpTeacherStyleRepository.add(currentSession, signUpTeacherStyleEntity);
        }
        return status;
    }

    public String danceFloorOwnerSignUp(SignUp signUpRequest) {
        LOG.info("Dance floor owner sign up: {}", signUpRequest);
        SignUpDanceFloorOwnerEntity signUpDanceFloorOwnerEntity = new SignUpDanceFloorOwnerEntity();
        signUpDanceFloorOwnerEntity.setEmail(signUpRequest.getEmail());
        signUpDanceFloorOwnerEntity.setLat(signUpRequest.getLat());
        signUpDanceFloorOwnerEntity.setLng(signUpRequest.getLng());
        Session currentSession = sessionFactory.getCurrentSession();
        Optional<SignUpDanceFloorOwnerEntity> existingSignUpDanceFloorOwnerOptional = signUpDanceFloorOwnerRepository.get(currentSession, signUpDanceFloorOwnerEntity.getEmail());
        String status = "ADDED";
        if (existingSignUpDanceFloorOwnerOptional.isPresent()) {
            LOG.info("Sign up update dance floor owner. New: {}, old: {}", signUpDanceFloorOwnerEntity, existingSignUpDanceFloorOwnerOptional.get());
            signUpDanceFloorOwnerRepository.update(currentSession, signUpDanceFloorOwnerEntity);
            status = "UPDATED";
        } else {
            LOG.info("Sign up add dance floor owner. New: {}", signUpDanceFloorOwnerEntity);
            signUpDanceFloorOwnerRepository.add(currentSession, signUpDanceFloorOwnerEntity);
        }
        return status;
    }

}
