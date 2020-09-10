package wdk0.com.youdeliao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import wdk0.com.youdeliao.model.Post;

@Mapper
public interface PostMapper {

    @Insert("insert into post(title,description,gmt_create,gmt_modified,creator_id,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Post post);
}
