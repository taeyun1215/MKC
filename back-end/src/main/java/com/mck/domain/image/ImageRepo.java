package com.mck.domain.image;

import com.mck.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {

    List<Image> findByPostOrderByIdAsc(Post post);

}
