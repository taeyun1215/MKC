package com.mck.domain.post;

import com.mck.domain.post.dto.PostDto;
import com.mck.domain.user.User;
import com.mck.global.error.BusinessException;
import com.mck.global.error.ErrorCode;
import com.mck.global.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    // 게시글 추가
    @PostMapping("/new")
    public ResponseEntity<Post> postAdd(
            @Validated @ModelAttribute("postDto") PostDto postDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsService userDetailsService
    ) {
        User user = userDetailsService.getUser();

        if (bindingResult.hasErrors()) {
            bindingResult.reject("mismatchedFormat", ErrorCode.MISMATCHED_FORMAT.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            postService.registerPost(postDto, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // 게시글 수정
    @PutMapping("/edit/{post_id}")
    public ResponseEntity<Post> postEdit(
            @PathVariable("post_id") Long postId,
            @Validated @ModelAttribute("postDto") PostDto postDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsService userDetailsService
    ) {
        User user = userDetailsService.getUser();

        if (bindingResult.hasErrors()) {
            bindingResult.reject("mismatchedFormat", ErrorCode.MISMATCHED_FORMAT.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            postService.editPost(postId, postDto, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{post_id}")
    public ResponseEntity<Post> postDelete(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal UserDetailsService userDetailsService
    ) {
        User user = userDetailsService.getUser();

        try {
            postService.deletePost(postId, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
