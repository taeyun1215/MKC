package com.mck.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long user_id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String userName);
    Optional<User> findByNickname(String nickname);

    @Modifying
    @Query(value = "UPDATE users u SET u.nickname = :nickname, u.password = :password WHERE u.user_id = :user_id", nativeQuery = true)
    void editUser(@Param("nickname") String nickname, @Param("password") String password, @Param("user_id") Long user_id);

//    @Modifying
//    @Query(value = "DELETE from users u where u.user_id = :user_id", nativeQuery = true)
//    void deleteUser(@Param("user_id") Long user_id);

}
