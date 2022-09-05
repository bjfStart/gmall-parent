package com.atguigu.gmall.search.repository;

import com.atguigu.gmall.search.bean.Person;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author feng
 * @create 2022-09-04 0:16
 */
@Repository //@Controller @Service @Component @Repository T, ID
public interface PersonRepository extends PagingAndSortingRepository<Person,Long> {
    //springData: 起名工程师

    //1.查询 address 在北京市的人
    List<Person> findAllByAddressLike(String address);

    //2.查询年龄小于等于 19的人
    List<Person> findAllByAgeLessThanEqual(Integer age);

    //3.查询 年龄大于18 且 在北京市的人
    List<Person> findAllByAgeGreaterThanAndAddressLike(Integer age, String address);

    //4.查询 年龄大于18 且 在北京市的人 或 id=3的人
    //有Or以后会有歧义，处理器歧义
    //@Async
    //Future<>
    List<Person> findAllByAgeGreaterThanAndAddressLikeOrIdEquals(Integer age, String address, Long id);


    //DSL
    @Query("{\n" +
            "    \"match\": {\n" +
            "      \"address\": \"?0\"\n" + //?0 代表取出第1个参数  ?1 代表取出第2个参数
            "    }\n" +
            "  }")
    List<Person> aaaa(String add);

//    long countByAgeGreaterThan(Integer age);

}
