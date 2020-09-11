package wdk0.com.youdeliao.dto;


import lombok.Data;

import java.util.List;

@Data
public class PageDto {
//   页面分页逻辑
    private List<PostDto> postDtos;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private List<Integer> pages;


}
