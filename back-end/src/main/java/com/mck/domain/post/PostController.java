package com.mck.domain.post;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mck.domain.user.User;
import com.mck.domain.user.UserRepo;
import com.mck.domain.user.UserService;
import com.mck.global.error.ErrorCode;
import com.mck.global.service.UserDetailsImpl;
import com.mck.global.utils.ReturnObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Slf4j
public class PostController {

    private final PostService postService;

    private final UserRepo userRepo; // 삭제 예정.

    // Paging 게시글, 10개씩.
    @GetMapping("/all")
    public ResponseEntity<ReturnObject> pagingPost(
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> posts = postService.pagePostList(pageable);

        ReturnObject object = ReturnObject.builder()
                .msg("ok")
                .data(posts)
                .build();

        return ResponseEntity.ok().body(object);
    }

    // 게시글 검색
    @PostMapping("search/{keyword}")
    public ResponseEntity<ReturnObject> searchPost(
            @PathVariable("keyword") String keyword,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<Post> posts = postService.searchPost(keyword, pageable);

        ReturnObject object = ReturnObject.builder()
                .msg("ok")
                .data(posts)
                .build();

        return ResponseEntity.ok().body(object);
    }

    // 게시글 상세 정보 todo : 쿠키나 세션을 이용하여 조회수 중복 카운터를 방지하기
    @GetMapping("/read/{post_id}")
    public ResponseEntity<ReturnObject> readPost(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails

    ) {
        User user = userDetails.getUser();
        Post post = postService.updateViewPost(postId);

        // 자기가 쓴 게시물을 자기가 본다면 수정, 삭제를 할 수 있게 해주는 부분.
        if (post.getUser().getId().equals(user.getId())) {
            ReturnObject object = ReturnObject.builder()
                    .msg("ok")
                    .data(post) // todo : writer = true 로 두고 싶음.
                    .build();

            return ResponseEntity.ok().body(object);
        }

        ReturnObject object = ReturnObject.builder()
                .msg("ok")
                .data(post)
                .build();

        return ResponseEntity.ok().body(object);
    }

    // 게시글 추가
    @PostMapping("/new")
    public ResponseEntity<ReturnObject> savePost(
            @Validated @ModelAttribute("postDto") PostDto postDto,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/post/save").toUriString());

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();

                if (bindingResult.hasErrors()) {
                    ReturnObject object = ReturnObject.builder()
                            .msg(ErrorCode.MISMATCHED_FORMAT.getMessage())
                            .type(bindingResult.getFieldError().getCode())
                            .build();

                    return ResponseEntity.badRequest().body(object);
                } else {
                    Post post = postService.savePost(postDto, username);

                    ReturnObject object = ReturnObject.builder()
                            .msg("ok")
                            .data(post)
                            .build();

                    return ResponseEntity.created(uri).body(object);
                }

            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }

        ReturnObject object = ReturnObject.builder()
                .msg("요청 처리에 필요한 토큰값이 없습니다.")
                .build();

        return ResponseEntity.badRequest().body(object);
    }

    // 게시글 수정
    @PutMapping("/edit/{post_id}")
    public ResponseEntity<ReturnObject> editPost(
            @PathVariable("post_id") Long postId,
            @Validated @ModelAttribute("postDto") PostDto postDto,
            BindingResult bindingResult
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
//        User user = userDetails.getUser();

        Optional<User> userOptional = userRepo.findByEmail("taeyun1215@naver.com"); // 삭제 예정.
        User user = userOptional.get(); // 삭제 예정.

        if (bindingResult.hasErrors()) {
            ReturnObject object = ReturnObject.builder()
                    .msg(ErrorCode.MISMATCHED_FORMAT.getMessage())
                    .type(bindingResult.getFieldError().getCode())
                    .build();

            return ResponseEntity.badRequest().body(object);
        } else {
            postService.editPost(postId, postDto, user);

            ReturnObject object = ReturnObject.builder()
                    .msg("ok")
                    .build();

            return ResponseEntity.ok().body(object);
        }
    }

    // 게시글 삭제
    @DeleteMapping("/delete/{post_id}")
    public ResponseEntity<ReturnObject> deletePost(
//            @PathVariable("post_id") Long postId,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
//        User user = userDetails.getUser();

        Optional<User> userOptional = userRepo.findByEmail("taeyun1215@naver.com"); // 삭제 예정.
        User user = userOptional.get(); // 삭제 예정.
        Long postId = 1L; // 삭제 예정.

        postService.deletePost(postId, user);

        ReturnObject object = ReturnObject.builder()
                .msg("ok")
                .build();

        return ResponseEntity.ok().body(object);

    }

    // 게시글 좋아요
    @PostMapping("like/{post_id}")
    public ResponseEntity<ReturnObject> likePost(
            @PathVariable("post_id") Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

//        User user = userDetails.getUser();

        Optional<User> userOptional = userRepo.findByEmail("taeyun1215@naver.com"); // 삭제 예정.
        User user = userOptional.get(); // 삭제 예정.

        postService.likePost(postId, user);

        ReturnObject object = ReturnObject.builder()
                .msg("ok")
                .build();

        return ResponseEntity.ok().body(object);
    }

}
