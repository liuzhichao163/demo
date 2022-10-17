package com.example.demo.controller;

import com.example.demo.entity.User;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName TestController
 * @Author: ytc
 * @Create: 2022/9/19 16:24
 * @Version: v1.0
 */
@RestController
public class TestController {

	public static final String AAA = "aaa";

	@Autowired
	@Qualifier("restHighLevelClient")
	private RestHighLevelClient restHighLevelClient;

	@GetMapping("/test")
	public void test() throws IOException {
		System.out.println("=======test========");
		// 创建索引的请求
		CreateIndexRequest nan_index = new CreateIndexRequest("nan_index");
		// client执行请求
		CreateIndexResponse response = restHighLevelClient.indices().create(nan_index, RequestOptions.DEFAULT);
		System.out.println(response);
	}

	static {
		System.out.println("================初始化==================");
		System.out.println("================初始化完毕==================");
	}

	public static void main(String[] args) {
//		User user = User.builder()
//				.id(1234567L).userName("张三").phoneNum("123456789").sex(1)
//				.build();
//		System.out.println(user.toString());
//
//		user.setUserName("李四");
//		System.out.println(user.toString());
//
//		User user1 = new User();
//		user1.setUserName("韩梅梅");
//		System.out.println(user1.toString());

//		List<User> userlist = new ArrayList<>();
//		userlist.add(new User("Tom",10));
//		userlist.add(new User("Jerry",12));
//		userlist.add(new User("Jerry",13));
//		userlist.add(new User("null",9));
//
//		userlist.sort(Comparator.comparing(User::getUserName)
//				.thenComparing(User::getAge));
//		userlist.forEach(s-> System.out.println(s.toString()));
//
//		userlist.sort(Comparator.comparing(s->s.getUserName()));
//
//
//		Function<Integer, Integer> times2 = e -> e * 2;
//		Function<Integer, Integer> squared = e -> e * e;
//
//		Integer apply = times2.compose(squared).apply(4);
//
//		System.out.println("apply == " + apply);

		String key = AAA + "bbb";
		System.out.println(key);
	}

}