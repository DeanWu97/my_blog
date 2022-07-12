package com.dean.my_blog.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.ibatis.type.JdbcType;

import java.util.stream.Stream;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Blogs extends BaseEntity{
    private String title;
    private String description;
    private String coverUrl;
    @TableField(jdbcType = JdbcType.LONGVARCHAR)
    private String content;
    private Long authorId;
    private BlogVisibleEnum visible;

    @JsonIgnoreProperties
    public void setVisible(String visible) {
        this.visible = BlogVisibleEnum.getByValue(visible);
    }
    @JsonIgnoreProperties
    public enum BlogVisibleEnum {
        //自己可见
        SELF("self"),
        //所有人可见
        ALL("all");

        @Getter
        @EnumValue
        private String value;

        BlogVisibleEnum(String value) {
            this.value = value;
        }

        public static BlogVisibleEnum getByValue(String value) {
            return Stream.of(values()).filter(v -> v.getValue().equals(value)).findFirst().orElse(null);
        }
    }

}

