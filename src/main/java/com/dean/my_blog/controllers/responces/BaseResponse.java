package com.dean.my_blog.controllers.responces;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {
    private String code;
    private T data;
    private Object paging;
    public static<T> BaseResponse<T> ok(T t) {
        return new BaseResponse<T>("000000",t, "this is paging");
//        return new BaseResponce<T>("000000", "this is paging");
//        return new BaseResponce<T>("000000");
    }
}
