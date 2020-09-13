package wdk0.com.youdeliao.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import wdk0.com.youdeliao.model.User;

@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmtCreate,gmtModified,avatarUrl) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insert(User user);

//    Mybatis中，只有当下面的token为类的时候才会自动注入，否则需要些一个@Param的注解
    @Select("select * from user where token = #{token} ")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{id}")
    User findById(@Param("id") Integer id);


}
