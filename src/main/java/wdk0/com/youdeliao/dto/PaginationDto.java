package wdk0.com.youdeliao.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDto {
//   页面分页逻辑
    private List<PostDto> posts;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

/**
* @Description:  分页栏
* @Param: [totalCount, page, size] 总条数，页数，每页的数量
* @return: void
*/
    public void setPageination(Integer totalPage,Integer page ) {
        this.totalPage =totalPage;
        this.page = page;
        pages.add(page);
        for (int i =1;i<=3;i++){
            if (page - i >0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

//        是否展示上一页
        if (page ==1) {
            showPrevious = false;
        }else{
            showPrevious =true;
        }
//        是否展示下一页
        if(page == totalPage){
            showNext = false;
        }else{
            showNext = true;
        }
//        是否展示跳转到首页图标
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }
//        是否展示跳转到尾页
        if (pages.contains(totalPage)){
            showEndPage = false;
        }
        else{
            showEndPage = true;
        }
    }
}
