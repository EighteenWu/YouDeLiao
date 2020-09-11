package wdk0.com.youdeliao.dto;

import lombok.Data;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.model.User;

@Data
public class PostDto {
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
    private User user;
}
