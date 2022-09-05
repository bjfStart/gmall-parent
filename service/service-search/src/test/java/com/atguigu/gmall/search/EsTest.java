package com.atguigu.gmall.search;

import com.atguigu.gmall.search.bean.Person;
import com.atguigu.gmall.search.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * @author feng
 * @create 2022-09-04 0:20
 */
@SpringBootTest
public class EsTest {
    @Autowired
    PersonRepository personRepository;

    @Autowired
    ElasticsearchRestTemplate esRestTemplate;

    @Test
    void queryqTest(){
        //索引：数据库

    }

    @Test
    void aaaTest(){
        List<Person> aaaa = personRepository.aaaa("北京市");
        for (Person person : aaaa) {
            System.out.println(person);
        }
    }

    @Test
    void queryTest(){
//        Optional<Person> byId = personRepository.findById(2L);
//        System.out.println(byId.get());

        //1.查询 address 在北京市的人
        List<Person> 北京市 = personRepository.findAllByAddressLike("北京市");
        for (Person person : 北京市) {
            System.out.println("查询 address 在北京市的人");
            System.out.println(person);
        }
        //2.查询年龄小于等于 19的人
        List<Person> all = personRepository.findAllByAgeLessThanEqual(19);
        for (Person person : all) {
            System.out.println("查询年龄小于等于 19的人");
            System.out.println(person);
        }

        //3.查询 年龄大于18 且 在北京市的人
        List<Person> 北京市1 = personRepository.findAllByAgeGreaterThanAndAddressLike(18, "北京市");
        for (Person person : 北京市1) {
            System.out.println("查询 年龄大于18 且 在北京市的人");
            System.out.println(person);
        }

        //4.查询 年龄大于18 且 在北京市的人 或 id=3的人
        //有OR以后会有歧义，处理歧义
        List<Person> 北京市2 = personRepository.findAllByAgeGreaterThanAndAddressLikeOrIdEquals(18, "北京市", 3L);
        for (Person person : 北京市2) {
            System.out.println("查询 年龄大于18 且 在北京市的人 或 id=3的人");
            System.out.println(person);
        }

    }

    @Test
    void saveTest(){
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("类");
        person.setLastName("飞扬");
        person.setAge(18);
        person.setAddress("北京市昌平区");

        Person person1 = new Person();
        person1.setId(2L);
        person1.setFirstName("三");
        person1.setLastName("张");
        person1.setAge(18);
        person1.setAddress("北京市朝阳区");

        Person person2 = new Person();
        person2.setId(3L);
        person2.setFirstName("四");
        person2.setLastName("里");
        person2.setAge(19);
        person2.setAddress("上海市松江区");

        Person person3 = new Person();
        person3.setId(4L);
        person3.setFirstName("三2");
        person3.setLastName("张");
        person3.setAge(19);
        person3.setAddress("北京市天安门");

        personRepository.save(person);
        personRepository.save(person1);
        personRepository.save(person2);
        personRepository.save(person3);
        System.out.println("完成。。。");
    }
}
