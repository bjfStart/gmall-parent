package com.atguigu.gmall.model.list;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

// Index = goods , Type = info  es 7.8.0 逐渐淡化type！  修改！
@Data
@Document(indexName = "goods" , shards = 3,replicas = 2)
public class Goods {
    // 商品Id skuId
    @Id
    private Long id;

    @Field(type = FieldType.Keyword, index = false)
    private String defaultImg;

    //  es 中能分词的字段，这个字段数据类型必须是 text！keyword 不分词！
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Double)
    private Double price;

    //  @Field(type = FieldType.Date)   6.8.1
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime; // 新品

    @Field(type = FieldType.Long)
    private Long tmId;

    @Field(type = FieldType.Keyword) //这些品牌名不需要拆开，所以用keyword
    private String tmName;

    @Field(type = FieldType.Keyword)
    private String tmLogoUrl;

    @Field(type = FieldType.Long)
    private Long category1Id;

    @Field(type = FieldType.Keyword)
    private String category1Name;

    @Field(type = FieldType.Long)
    private Long category2Id;

    @Field(type = FieldType.Keyword)
    private String category2Name;

    @Field(type = FieldType.Long)
    private Long category3Id;

    @Field(type = FieldType.Keyword)
    private String category3Name;

    //  商品的热度！ 我们将商品被用户点查看的次数越多，则说明热度就越高！
    @Field(type = FieldType.Long)
    private Long hotScore = 0L;

    // 平台属性集合对象
    // Nested 支持嵌套查询
    @Field(type = FieldType.Nested)
    private List<SearchAttr> attrs;
    //es底层对集合 扁平化处理集合数据 相同字段的数据会放在一起
    //集合内元素如果未来需要作为检索条件，集合就必须是 Nested映射 + NestedQuery = 正确结果
}
