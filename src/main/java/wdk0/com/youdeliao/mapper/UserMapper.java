package wdk0.com.youdeliao.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import wdk0.com.youdeliao.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,GMT_create,GMT_modified) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);
}
