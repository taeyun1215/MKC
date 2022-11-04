package com.mck.post;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional  // 테스트는 여러번 반복해서 실행해야 하므로 DB에 반영이 안 되게 하기 위해서 사용함.
@RequiredArgsConstructor
public class PostServiceTest {
}
