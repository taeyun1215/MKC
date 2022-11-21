package com.mck.domain.post;

import com.mck.domain.user.User;
import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.global.service.UserDetailsImpl;
import com.mck.global.utils.ReturnObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

    // 모든 게시글 가져오기.
    @GetMapping("/postAll")
    public ResponseEntity<List<Post>> getPostAll() {
        return ResponseEntity.ok().body(postService.getPostAll());
    }

    // 게시글 추가
    @PostMapping("/new")
    public ResponseEntity<ReturnObject> savePost(
            @Validated @ModelAttribute("postDto") PostDto postDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/post/save").toUriString());
        User user = userDetails.getUser();

        if (bindingResult.hasErrors()) {
            ReturnObject object = ReturnObject.builder()
                    .msg(ErrorCode.MISMATCHED_FORMAT.getMessage())
                    .type(bindingResult.getFieldError().getCode())
                    .build();

            return ResponseEntity.badRequest().body(object);
        } else {
            Post post = postService.savePost(postDto, user);

            ReturnObject object = ReturnObject.builder()
                    .msg("ok")
                    .data(post)
                    .build();

            return ResponseEntity.created(uri).body(object);
        }
    }

    // 게시글 수정
    @PutMapping("/edit/{post_id}")
    public ResponseEntity<ReturnObject> editPost(
            @PathVariable("post_id") Long postId,
            @Validated @ModelAttribute("postDto") PostDto postDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();

        if (bindingResult.hasErrors()) {
            ReturnObject object = ReturnObject.builder()
                    .msg(ErrorCode.MISMATCHED_FORMAT.getMessage())
                    .type(bindingResult.getFieldError().getCode())
                    .build();

            return ResponseEntity.badRequest().body(object);
        } else {
            Post post = postService.editPost(postId, postDto, user);

            ReturnObject object = ReturnObject.builder()
                    .msg("ok")
                    .data(post)
                    .build();

            return ResponseEntity.ok().body(object);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{post_id}")
    public ResponseEntity<Post> deletePost(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();

        try {
            postService.deletePost(postId, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
