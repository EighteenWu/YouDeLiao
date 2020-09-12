package wdk0.com.youdeliao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import wdk0.com.youdeliao.model.Post;

import java.util.List;

@Mapper
public interface PostMapper {

    @Insert("insert into post(title,description,gmtCreate,gmtModified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Post post);

    @Select("select * from post limit #{offset},#{size}")
    List<Post> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);


//    查询所有帖子的总数
    @Select("select count(1) from post")
    Integer count();

    @Select("select * from post where creator = #{userId} limit #{offset},#{size}")
    List<Post> listByUserId(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("SELECT COUNT(1) FROM post WHERE creator = #{userId}")
    Integer countByUserId(@Param(value = "userId")Integer userId);
}
