package com.atguigu.gmall.model.vo.search;

import lombok.Data;

/**
 * 封装检索条件
 * @author feng
 * @create 2022-09-05 22:34
 */
@Data
public class SearchParamVo {
    Long category3Id;
    Long category2Id;
    Long category1Id;
    String keyword;
    String[] props;
    String trademark;
    String order = "1:desc";
    Integer pageNo = 1;
}
