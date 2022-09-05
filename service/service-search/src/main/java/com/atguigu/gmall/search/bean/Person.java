package com.atguigu.gmall.search.bean;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author feng
 * @create 2022-09-04 0:06
 */
@Data
@Document(indexName = "person",shards = 1,replicas = 1)
public class Person {
    @Id
    private Long id; //主键
    @Field(value = "first",type = FieldType.Keyword) //TEXT【存的时候会分词】，keyword【关键词不分词】，都是字符串
    private String firstName;
    @Field(value = "last",type = FieldType.Keyword)
    private String lastName;
    @Field(value = "age")
    private Integer age;
    @Field(value = "address",type = FieldType.Text,analyzer = "ik_smart")
    private String address;
}
