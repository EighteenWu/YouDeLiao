package wdk0.com.youdeliao.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wdk0.com.youdeliao.dto.PaginationDto;
import wdk0.com.youdeliao.dto.PostDto;
import wdk0.com.youdeliao.mapper.PostMapper;
import wdk0.com.youdeliao.mapper.UserMapper;
import wdk0.com.youdeliao.model.Post;
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
        Integer totalCount = postMapper.count();
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

//        当前端页面修改page值得时候，异常处理。
        if (page < 1) {
            page = 1;
        }

        if (page > paginationDto.getTotalPage()) {
            page = paginationDto.getTotalPage();
        }

//        定义分页
        Integer offset = size * (page - 1);
        List<Post> posts = postMapper.list(offset, size);
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            User user = userMapper.findById(post.getCreator());
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
        Integer totalCount = postMapper.countByUserId(userId);
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
        List<Post> posts = postMapper.listByUserId(userId,offset, size);
        List<PostDto> postDtoList = new ArrayList<>();

        for (Post post : posts) {
            User user = userMapper.findById(post.getCreator());
            PostDto postDto = new PostDto();
//           copyProperties 将原来的对象通过迅速拷贝到另外一个对象
            BeanUtils.copyProperties(post, postDto);
            postDto.setUser(user);
            postDtoList.add(postDto);
        }
        paginationDto.setPosts(postDtoList);
        return paginationDto;
    }
}
