package com.dean.my_blog.controllers.responces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.stream.StreamSupport;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {
    private static String OK = "000000";
    private static String PARAMS_INVALID = "000001";
    private String code;
    private T data;
    private Object paging;
    public static<T> BaseResponse<T> ok(T t) {
        return new BaseResponse<T>(OK,t, "this is paging");
    }
    public static<T> BaseResponse<T> error(T t) {
        return new BaseResponse<T>(PARAMS_INVALID,t,"this is paging");
    }
}
