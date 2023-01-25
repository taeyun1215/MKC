package com.mck.domain.user;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mck.domain.role.Role;
import com.mck.domain.user.dto.UserSignUpDto;
import com.mck.domain.useremail.UserEmail;

import com.mck.global.error.ErrorCode;
import com.mck.infra.mail.EmailMessage;
import com.mck.infra.mail.EmailService;
import com.mck.global.utils.ReturnObject;
import com.mck.global.utils.SignUpFormValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final SignUpFormValidator signUpFormValidator;
    private final TemplateEngine templateEngine;

    @InitBinder("userSignUpDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signUpFormValidator);
    }

    // 유저 등록
    @PostMapping("/user")
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserSignUpDto userSignUpDto, HttpServletRequest request, Errors errors) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());

        Map<String, Object> body = new HashMap<>();
        Map<String, Object> error = new HashMap<>();

        if (!StringUtils.equals(userSignUpDto.getPassword(), userSignUpDto.getConfirmPassword())) {
            log.error("검증실패");

            error.put("code", "different.confirmPassword");
            error.put("message", "비밀번호와 비밀번호 확인이 일치하지 않습니다.");

            body.put("success", false);
            body.put("error", error);

//            ReturnObject object = ReturnObject.builder()
//                    .msg("비밀번호와 비밀번호 확인이 일치하지 않습니다.")
//                    .type("different.confirmPassword")
//                    .build();

            return ResponseEntity.ok().body(body);
        }

        if (errors.hasErrors()) {
            System.out.println("검증실패");

            error.put("code", errors.getFieldError().getCode());
            error.put("message", errors.getFieldError().getDefaultMessage());

            body.put("success", false);
            body.put("error", error);

//            ReturnObject object = ReturnObject.builder()
//                    .msg(errors.getFieldError().getDefaultMessage())
//                    .type(errors.getFieldError().getCode())
//                    .build();

            return ResponseEntity.ok().body(body);
        } else {
            User user = userService.newUser(userSignUpDto);
            User saveUser = userService.saveUser(user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userSignUpDto.getUsername(), userSignUpDto.getPassword());

            // 토큰 서명용 키 생성
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            // 최초 접속시 발급하는 토큰
            String access_token = JWT.create()
                    // 토큰 이름
                    .withSubject(user.getUsername())
                    // 토큰 만료일
                    .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                    // .withExpiresAt(new Date(System.currentTimeMillis() + 15 * 1000))
                    // 토큰 발행자
                    .withIssuer(request.getRequestURI().toString())
                    // 토큰 payload 작성
                    .withClaim("roles", authenticationToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                    // 토큰 서명
                    .sign(algorithm);

            // access_token을 재발급 받을 수 있는 토큰
            String refresh_token = JWT.create()
                    // 토큰 이름
                    .withSubject(user.getUsername())
                    // 토큰 만료일
                    .withExpiresAt(new Date(System.currentTimeMillis() + 120 * 60 * 1000))
                    // 토큰 발행자
                    .withIssuer(request.getRequestURI().toString())
                    // 토큰 서명
                    .sign(algorithm);

            Map<String, String> token = new HashMap<>();
            token.put("access_token", access_token);
            token.put("refresh_token", refresh_token);

            body.put("success", true);
            body.put("user", token);

//            ReturnObject object = ReturnObject.builder()
//                    .msg("ok")
//                    .data(token).build();

            return ResponseEntity.created(uri).body(body);
        }
    }

    // 새로운 권한 생성
    @PostMapping("/role")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    // 토큰 재발급
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                // 토크만 추출 하도록 type부분 제거
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                // JWT 검증용 객체 생성(토큰 생성할때와 동일한 알고리즘 적용)
                JWTVerifier verifier = JWT.require(algorithm).build();
                // 토큰 검증
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                String access_token = JWT.create()
                        // 토큰 이름
                        .withSubject(user.getUsername())
                        // 토큰 만료일
                        // 30분
                        .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                        // 토큰 발행자
                        .withIssuer(request.getRequestURI().toString())
                        // 토큰 payload 작성
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        // 토큰 서명
                        .sign(algorithm);

                Map<String, String> token = new HashMap<>();
                token.put("access_token", access_token);
                token.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), token);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                // response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh 토큰이 없습니다.");
        }
    }

    // 회원탈퇴
    @DeleteMapping("/user")
    public ResponseEntity<ReturnObject> deleteUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        ReturnObject object;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();
                userService.deleteUser(username);

                object = ReturnObject.builder()
                        .msg("ok")
                        .build();

            } catch (Exception e) {
                object = ReturnObject.builder()
                        .msg(e.getMessage())
                        .type(APPLICATION_JSON_VALUE)
                        .build();

                return ResponseEntity.status(FORBIDDEN.value()).body(object);
            }
        } else {
            object = ReturnObject.builder()
                    .msg("토큰이 없거나 올바르지 않은 토큰입니다.")
                    .type("invalid.token")
                    .build();
            return ResponseEntity.badRequest().body(object);
        }

        return ResponseEntity.ok().body(object);
    }

    // 인증 메일 확인
    @GetMapping("/check-email-code")
    public ResponseEntity<ReturnObject> checkEmailCode(String code, String email, String username, Model model) {
        User user = userService.getUser(username);
        ReturnObject object;
        if(user == null){
            object = ReturnObject.builder()
                    .type("wrong.username")
                    .msg("이메일 확인 링크가 정확하지 않습니다.")
                    .build();
            return ResponseEntity.badRequest().body(object);
        }

        UserEmail userEmail = UserEmail.builder().email(email).code(code).build();

        if (!emailService.checkCertifyEmail(userEmail)) {
            object = ReturnObject.builder()
                    .type("wrong.code")
                    .msg("인증 코드가 틀립니다.")
                    .build();
            return ResponseEntity.badRequest().body(object);
        }

        user.completeSignUp();
        user.setJoinedAt(LocalDateTime.now());
        userService.updateUser(user);

        model.addAttribute("username", username);

        object = ReturnObject.builder()
                .msg("ok")
                .data(model)
                .build();

        return ResponseEntity.ok().body(object);

    }

    // 사용자 id 찾기
    // 이메일을 입력하면 해당 이메일로 가입된 계정을 찾고 계정이 존재하면 입력한 이메일로 아이디 전달
    @GetMapping("/username")
    public ResponseEntity<ReturnObject> findUsername(@RequestParam String email){
        User result = userService.checkUserEmail(email);
        if (result != null){
            Context context = new Context();
            context.setVariable("link", "/api/username");
            context.setVariable("username", result.getUsername());
            String message = templateEngine.process("mail/findUsernameForm", context);

            EmailMessage emailMessage = EmailMessage.builder()
                    .to(email)
                    .subject("MCK 프로젝트, 아이디 찾기")
                    .message(message)
                    .build();

            emailService.sendEmail(emailMessage);

            ReturnObject object = ReturnObject.builder()
                    .msg("ok").build();

            return ResponseEntity.ok().body(object);

        } else{
            log.error("해당 이메일로 가입된 계정을 찾을 수 없습니다.");

            ReturnObject object = ReturnObject.builder()
                    .msg("해당 이메일로 가입된 계정을 찾을 수 없습니다.")
                    .type("invalid.email")
                    .build();

            return ResponseEntity.badRequest().body(object);
        }
    }

}
