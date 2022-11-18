package com.mck.domain.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

    // 게시글 추가
//    @PostMapping("/new")
//    public ResponseEntity<Post> postAdd(
//            @Validated @ModelAttribute("postDto") PostDto postDto,
//            BindingResult bindingResult,
//            @AuthenticationPrincipal UserDetailsService userDetailsService
//    ) {
//        if (bindingResult.hasErrors()) {
//            bindingResult.reject("mismatchedFormat", ErrorCode.MISMATCHED_FORMAT.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        String userId = userDetailsService.getUsername();
//
//        try {
//            postService.registerPost(postDto, userId);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (BusinessException e) {
//            bindingResult.reject("notSavedPost", ErrorCode.NOT_SAVE_POST.getMessage());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }






}
