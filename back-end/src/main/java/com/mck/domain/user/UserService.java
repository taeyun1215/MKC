package com.mck.domain.user;

import com.mck.domain.role.Role;

import java.util.List;

public interface UserService {
//    // DB에 유저 저장.
//    User signup(UserSignupDto userRequestDto);
//
//    // 로그인 하기.
//    User login(UserSignupDto userRequestDto);

    // DB에 유저정보 저장
    User saveUser(User user);

    // 유저 정보 수정
    void updateUser(User user);

    // DB에 Role 저장
    Role saveRole(Role role);

    // 유저정보에 Role 추가
    // void addRoleToUser(String username, String roleName);

    // 특정유저 정보 가져오기
    User getUser(String username);

    // 모든 유저 정보 가져오기
    List<User> getUsers();

    void deleteUser(String username) throws Exception;

}
