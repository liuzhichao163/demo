package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName RestElasticSearchClientConfig
 * @Author: ytc
 * @Create: 2022/9/27 14:32
 * @Version: v1.0
 */
@Configuration
public class RestElasticSearchClientConfig {

	// 将方法的返回结果交给spring管理
	@Bean
	public RestHighLevelClient restHighLevelClient(){
		// 主机ip和端口号以及协议
		RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("127.0.0.1",9200,"http")
				)
		);
		return restHighLevelClient;
	}

}