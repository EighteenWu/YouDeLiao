package wdk0.com.youdeliao.model;

import lombok.Data;
import wdk0.com.youdeliao.mapper.UserMapper;

@Data
public class User  {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;
}
