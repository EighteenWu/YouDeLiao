package wdk0.com.youdeliao.service;


import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdk0.com.youdeliao.dto.PaginationDto;
import wdk0.com.youdeliao.dto.PostDto;
import wdk0.com.youdeliao.exception.CustomizeErrorCode;
import wdk0.com.youdeliao.exception.CustomizeException;
import wdk0.com.youdeliao.mapper.PostMapper;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.Post;
import wdk0.com.youdeliao.model.PostExample;
import wdk0.com.youdeliao.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserMapper userMapper;

/**
* @Description: 首页问题列表
* @Param: [page, size]
* @return: wdk0.com.youdeliao.dto.PaginationDto
*/
    public PaginationDto list(Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();
        Integer totalPage;
        Integer totalCount = (int)postMapper.countByExample(new PostExample());


        if(totalCount%size == 0){
            totalPage  = totalCount /size;
        }else {
            totalPage = totalCount/size +1;
        }

//        页面值异常处理
        if (page < 1){
            page =1;
        }

        if (page>totalPage){
            page = totalPage;
        }

        paginationDto.setPageination(totalPage,page);
//        定义分页
        Integer offset = size * (page - 1);
        List<Post> posts = postMapper.selectByExampleWithRowbounds(new PostExample(),new RowBounds(offset,size));
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            User user = userMapper.selectByPrimaryKey(post.getCreator());
            PostDto postDto = new PostDto();
//           copyProperties 将原来的对象通过迅速拷贝到另外一个对象
            BeanUtils.copyProperties(post, postDto);
            postDto.setUser(user);
            postDtoList.add(postDto);
        }
        paginationDto.setPosts(postDtoList);
        return paginationDto;
    }
/** 
* @Description: 用户发表问题列表 
* @Param: [id, page, size]
* @return: wdk0.com.youdeliao.dto.PaginationDto
*/
    public PaginationDto list(Integer userId, Integer page, Integer size) {
        PaginationDto paginationDto = new PaginationDto();
        PostExample postExample = new PostExample();
        postExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)postMapper.countByExample(postExample);
        Integer totalPage;

        if(totalCount%size == 0){
            totalPage  = totalCount /size;
        }else {
            totalPage = totalCount/size +1;
        }

        if (page < 1){
            page =1;
        }

        if (page>totalPage){
            page = totalPage;
        }
        paginationDto.setPageination(totalPage,page);
//        定义分页
        Integer offset = size * (page - 1);
        PostExample example = new PostExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        List<Post> posts =postMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            User user = userMapper.selectByPrimaryKey(post.getCreator());
            PostDto postDto = new PostDto();
//           copyProperties 将原来的对象通过迅速拷贝到另外一个对象
            BeanUtils.copyProperties(post, postDto);
            postDto.setUser(user);
            postDtoList.add(postDto);
        }
        paginationDto.setPosts(postDtoList);
        return paginationDto;
    }
/** 
* @Description:获取问题的id
* @Param: [id]
* @return: wdk0.com.youdeliao.dto.PostDto
*/
    public PostDto getById(Integer id) {
        Post post = postMapper.selectByPrimaryKey(id);
        if (post == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        PostDto postDto = new PostDto();
        User user = userMapper.selectByPrimaryKey(post.getCreator());
        postDto.setUser(user);
        BeanUtils.copyProperties(post,postDto);
        return postDto;
    }


    /**
    * @Description:创建或更新问题
    * @Param: [post]
    * @return: void
    */
    public void createOrupdate(Post post) {
        if(post.getId() == null){
            post.setGmtCreate(System.currentTimeMillis());
            post.setGmtModified(post.getGmtCreate());
            postMapper.insert(post);
        }else{
            Post updatePost = new Post();
            updatePost.setTitle(post.getTitle());
            updatePost.setDescription(post.getDescription());
            updatePost.setGmtModified(System.currentTimeMillis());
            updatePost.setTag(post.getTag() );
            PostExample postExample = new PostExample();
            postExample.createCriteria()
                    .andCreatorEqualTo(post.getId());
         int updated =  postMapper.updateByExampleSelective(updatePost,postExample);
        if (updated != 1){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        }
    }

    /** 
    * @Description: 删除问题
    * @Param: [post]
    * @return: void
    */
    public void delete(PostDto post) {
        postMapper.deleteByPrimaryKey(post.getId());
    }
}
