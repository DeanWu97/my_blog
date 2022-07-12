package com.dean.my_blog.controllers.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBlogRequest {
    private String title;
    private String description;
    private String coverUrl;
    private String content;
    private String visible;
}
