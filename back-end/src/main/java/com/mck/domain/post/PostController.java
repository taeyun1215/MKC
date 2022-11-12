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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
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
            bindingResult.reject("notSavedPost", ErrorCode.NOT_SAVE_POST.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // 게시글 수정
    @PostMapping("/edit/{post_id}")
    public ResponseEntity<Post> postEdit(
            @PathVariable Long post_id,
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
            postService.editPost(post_id, postDto, user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BusinessException e) {
            bindingResult.reject("notEditPost", ErrorCode.NOT_EDIT_POST.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
