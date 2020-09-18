package wdk0.com.youdeliao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.model.PostExample;
//阅读量
public interface PostExtMapper {
    int incView(Post record);
}