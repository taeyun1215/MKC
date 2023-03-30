package com.mck.global.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

// api 결과값 return 할때 이 객체에 담아서 return
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnObject {
    @Builder.Default
    private boolean success = false; // 성공 여부
    private Object data;
    private Object error; // 에러 객체
}
