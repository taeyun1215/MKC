package com.mck.domain.post;

import com.mck.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByPostId(Long post_id);
    Optional<Post> findByPostIdAndUser(Long post_id, User user);
    Optional<Post> findByTitle(String title);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE post p SET p.title = :title, p.content = :content WHERE p.post_id = :post_id", nativeQuery = true)
    void editPost(@Param("title") String title, @Param("content") String content, @Param("post_id") Long post_id);

}
