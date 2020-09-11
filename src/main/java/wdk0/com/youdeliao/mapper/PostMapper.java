package wdk0.com.youdeliao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import wdk0.com.youdeliao.model.Post;

import java.util.List;

@Mapper
public interface PostMapper {

    @Insert("insert into post(title,description,gmtCreate,gmtModified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Post post);

    @Select("select * from post limit #{offset},#{size}")
    List<Post> list(Integer offset, Integer size);
}
