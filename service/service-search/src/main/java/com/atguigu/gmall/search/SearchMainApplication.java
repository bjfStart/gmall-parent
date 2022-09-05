package com.atguigu.gmall.search;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author feng
 * @create 2022-09-03 23:51
 */
@EnableElasticsearchRepositories //开启es的自动仓库功能.写Bean，写接口，自动床号索引库并设置好Mapping类型
@SpringCloudApplication
public class SearchMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchMainApplication.class,args);
    }
}
