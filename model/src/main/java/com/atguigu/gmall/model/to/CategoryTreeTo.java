package com.atguigu.gmall.model.to;

import lombok.Data;

import java.util.List;

/**
 * DDD(Domain-Driven Design):领域驱动设计
 *
 * 三级分类树形结构
 * 支持无限层级
 * 当前项目只有三层
 *
 * @author feng
 * @create 2022-08-26 18:35
 */
@Data
public class CategoryTreeTo {
    private long categoryId;
    private String categoryName;
    private List<CategoryTreeTo> categoryChild;
}
