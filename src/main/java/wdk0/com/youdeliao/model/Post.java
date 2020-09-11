package wdk0.com.youdeliao.model;

import lombok.Data;

import java.security.PrivateKey;

@Data
public class Post {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private String tag;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
}
