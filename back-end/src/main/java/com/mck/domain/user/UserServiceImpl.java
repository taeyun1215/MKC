package com.mck.domain.user;

import com.mck.domain.role.Role;
import com.mck.domain.role.RoleRepo;
import com.mck.global.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final EmailService emailService;

    private final TemplateEngine templateEngine;

    @Override
    public User saveUser(User user) {
        log.info("새로운 유저 정보를 DB에 저장했습니다 : ", user.getUsername());
        Role role = roleRepo.findByName("ROLE_USER");
        user.getRoles().add(role);
        return userRepo.save(user);
    }

    @Override
    public void updateUser(User user) {
        log.info("유저 정보를 수정했습니다 : ", user.getUsername());
        userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("새로운 Role 정보를 DB에 저장했습니다 : ", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public User getUser(String username) {
        log.info("사용자 {}의 상세 정보를 가져왔습니다.", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<User> getUsers() {
        log.info("모든 유저 정보를 가져옵니다.");
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(String username) {
        log.info("사용자 {} 를 삭제하였습니다.", username);
        userRepo.deleteByUsername(username);
    }
}
