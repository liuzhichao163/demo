package com.example.demo.service.es;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.api.R;
import com.example.demo.DemoApplication;
import com.example.demo.entity.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 模块名称
 * 模块简单说明
 *
 * @ClassName IndexTest
 * @Author: ytc
 * @Create: 2022/9/27 14:35
 * @Version: v1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class IndexTest {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient restHighLevelClient;

    //测试索引的创建 Request PUT kuang_index
    @Test
    public void testCreateIndex() throws IOException {
        System.out.println("=======test========");
        // 创建索引的请求
        CreateIndexRequest request = new CreateIndexRequest("kuangshen_index");
        // client执行请求
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    //测试获取索引,判断其是否存在
    @Test
    public void testgetIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("kuangshen_index");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //测试删除索引
    @Test
    public void testDelIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("kuangshen_index");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete);
    }

    //测试创建文档
    @Test
    public void testAddDocument() throws IOException {
        //创建对象
        User user = new User("民主党",100);
        //创建请求
        IndexRequest request = new IndexRequest("kuangshen_index");

        //规则 PUT /kuangshen_index/_doc/1
        request.id("2");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");

        //将我们的数据放入请求中 json
        request.source(JSONUtil.toJsonStr(user), XContentType.JSON);

        //客户端发送请求，获取相应结果
        IndexResponse index = restHighLevelClient.index(request, RequestOptions.DEFAULT);

        System.out.println(index.toString());
        System.out.println(index.status()); //对应我们命令返回的状态 CREATED
    }

    //获取文档，判断是否存在 get /index/doc/1
    @Test
    public void testExistesDocument() throws IOException {
        GetRequest request = new GetRequest("kuangshen_index","1");
        //不获取返回的_source的上下文了
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档信息
    @Test
    public void testGetDocument() throws IOException {
        GetRequest request = new GetRequest("kuangshen_index","1");
        GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        System.out.println(documentFields.getSource());
        System.out.println(documentFields.getSourceAsString()); //打印文档内容
        System.out.println(documentFields); //返回全部内容，和命令一样
    }

    //更新文档信息
    @Test
    public void testUpdateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("kuangshen_index","1");
        request.timeout("1s");

        User user = new User("共产党万岁",100000);
        request.doc(JSONUtil.toJsonStr(user),XContentType.JSON);

        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(update.status());
    }

    //删除文档
    @Test
    public void testDelDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("kuangshen_index","1");
        request.timeout("1s");
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.status());
    }

    //批量插入数据
    @Test
    public void testBulkRequrst() throws IOException {
        //构造实体对象
        List<User> list = new ArrayList<>();
        list.add(new User("张三",100));
        list.add(new User("李四",100));
        list.add(new User("王五",100));
        list.add(new User("二麻子",100));
        list.add(new User("周杰伦",100));
        list.add(new User("林俊杰",100));

        BulkRequest request = new BulkRequest();
        request.timeout("1s");

        for(int i = 0; i< list.size(); i++){
            request.add(
                    new IndexRequest("kuangshen_index").id(Convert.toStr(10+i))
                    .source(JSONUtil.toJsonStr(list.get(i)),XContentType.JSON)
            );
        }

        BulkResponse bulk = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(bulk.hasFailures()); // 是否失败，返回 false 代表 成功
    }

    // 查询
    // SearchRequest 搜索请求
    // SearchSourceBuilder 条件构造
    // HighlightBuilder 构建高亮
    // TermQueryBuilder 精确查询
    // MatchAllQueryBuilder
    // xxx QueryBuilder 对应我们刚才看到的命令！
    @Test
    public void testSearch() throws IOException {
        SearchRequest request = new SearchRequest("kuangshen_index");

        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.highlighter();

        //查询条件，可以使用QueryBuilders工具实现
        // QueryBuilders.termQuery 精确
        // QueryBuilders.matchAllQuery() 匹配所有
        TermQueryBuilder queryBuilder = QueryBuilders.termQuery("userName", "张三");
//        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();

        sourceBuilder.query(queryBuilder);
        sourceBuilder.timeout((new TimeValue(60, TimeUnit.SECONDS)));

        request.source(sourceBuilder);
        SearchResponse search = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(JSONUtil.toJsonStr(search.getHits()));
        System.out.println("=================================");
        for (SearchHit documentFields : search.getHits().getHits()) {
            System.out.println(documentFields.getSourceAsMap());
        }



    }

}