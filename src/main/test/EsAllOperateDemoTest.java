import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkIndexByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.missing.Missing;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * Created by Congwz on 2019/2/1.
 */
public class EsAllOperateDemoTest {
    private TransportClient client;

    private final static String db = "lagou";

    private final static String table = "work";

    //@Before（Junit中的@Before） 在每个测试方法之前都执行一次, 方法需要声明为public
    @Before
    public void getClient() throws Exception {
        //设置集群名称
        Settings settings = Settings.builder().put("cluster.name", "my-application").build();// 集群名
        //创建client
        client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @After
    public void closeClient() {
        if(null != client) {
            client.close();
        }
    }


    /*******************************************增**************************************/

    //添加文档(如果不存在索引、类型会自动创建索引、类型)
    @Test
    public void addDocument() {
        XContentBuilder doc = null;
        try {
            doc = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("id", "1")
                    .field("title", "java设计模式之装饰模式")
                    .field("content", "在不必改变原类文件和使用继承的情况下，动态的扩展一个对象的功能。")
                    .field("postdate", "2019-02-01")
                    .field("url", "https://www.baidu.com/")
                    .endObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //添加文档
        IndexResponse response = client.prepareIndex("testindex", "blog", "7")
                .setSource(doc).get();
        System.out.println("status: " + response.status());       //status: CREATED，非第一次执行status: OK
        System.out.println("version: " + response.getVersion()); //version: 1，非第一次执行version: 2、3...

        //可以在kibana中查询一下：GET testindex/blog/7
    }

    //bulk批量操作（增删改）
    //批量添加文档（不存在索引、类型、id会自动创建）
    @Test
    public void AddPLByBulk() throws IOException {
        /*创建suggest
        PUT testindex/blog/1?refresh
        {
            "suggest" : [
                {
                    "input": ["模式","java"],
                    "weight" : 10
                },
                {
                    "input": "test",
                    "weight" : 3
                }
            ]
        }
        * */

        /*
        创建suggest后，文档原数据如下：
        {
            "_index": "testindex",
            "_type": "blog",
            "_id": "1",
            "_version": 1,
            "_score": 1,
            "_source": {
               "suggest": [
                  {
                    "input": ["模式","java"],
                    "weight": 10
                  },
                  {
                    "input": "test",
                    "weight": 3
                  }
               ]
            }
        }
        * */

        List<String> suggestInput = new ArrayList<>();
        suggestInput.add("java");
        suggestInput.add("工厂");
        suggestInput.add("中国");
        /*String in = JSONObject.toJSONString(suggestInput);
        System.out.println(in);*/

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex("testindex", "blog", "5")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id",5)
                        .field("title", "java单例模式")
                        .field("content", "单例模式之java必备知识之一")
                        .field("postdate", "2019-02-01")
                        .field("url", "https://blog.csdn.net/u011781521/article/details/77848489")
                        .startObject("suggest")  //搜索建议
                         .field("input",suggestInput)  //数组
                         .field("weight",7)
                        .endObject()
                        .endObject()
                )
        );


        /*bulkRequest.add(client.prepareIndex("testindex", "blog", "1")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id",2)
                        .field("title", "诚心招募java高级开发工程师")
                        .field("content", "我公司正在寻找3~5年的java开发工程师呢")
                        .field("postdate", "2019-02-03")
                        .field("url", "https://www.baidu.com/")
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex("testindex", "blog", "10")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id",10)
                        .field("title", "产品经理")
                        .field("content", "人人都是产品经理")
                        .field("postdate", "2019-02-03")
                        .field("url", "https://baike.baidu.com/item/%E7%99%BE%E5%BA%A6/6699?fr=aladdin")
                        .endObject()
                )
        );*/

        /*bulkRequest.add(client.prepareIndex("testindex", "balala", "1")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id",3)
                        .field("title", "巴啦啦小魔仙")
                        .field("content", "java测是一下哦")
                        .endObject()
                )
        );*/

        /*bulkRequest.add(client.prepareIndex("lagou", "work", "3")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("title", "学习python")
                        .field("salary", 10000)
                        .field("city", "苏州")
                        //company是一个对象
                        .startObject("company")
                          .field("name","瀚川")
                          .field("company_addr","创意产业园")
                        .endObject()
                        .field("publish_date", "2019-02-01")
                        .field("comments", "100")
                        .endObject()
                )
        );*/

        BulkResponse response = bulkRequest.get();
        System.out.println(response.hasFailures()); //false 批量操作成功
        System.out.println(response.getTook()); //28ms  (How long the bulk execution took)
        if (response.hasFailures()) { //true
            System.out.println("bulk批量添加失败了");
        }
    }

    //创建索引并添加映射
    @Test
    public void createIndexAndMapping() throws IOException {
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate("testindex");
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                 .startObject("properties") //设置之定义字段
                  .startObject("id")
                    .field("type","long") //设置数据类型
                  .endObject()
                  .startObject("title")
                     .field("type","text")
                     .field("analyzer","ik_max_word")
                  .endObject()
                  .startObject("postdate")
                     .field("type","date")
                     .field("format","yyyy-mm-dd")
                  .endObject()
                  .startObject("url")
                    .field("type","keyword")
                  .endObject()
                  .startObject("suggest")  //搜索建议
                    .field("type","completion")
                    .field("analyzer","ik_max_word")
                  .endObject()
                 .endObject()
                .endObject();
        cib.addMapping("blog", mapping);

        CreateIndexResponse res = cib.execute().actionGet();
        System.out.println(res.isAcknowledged());
    }



    /*******************************************删**************************************/
    //删除文档
    @Test
    public void deleteDocumnet() {
        //删除文档
        //DeleteResponse response = client.prepareDelete("testindex","blog","7").get(); //方法一
        //方法二
        DeleteResponse response = client.prepareDelete("testindex", "blog", "7").execute().actionGet();
        System.out.println(response.status());
        if ("OK".equals(response.status().toString())) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
    }

    //删除索引（数据库）
    @Test
    public void deleteIndex() {
        //如果传人的索引名不存在，会出现异常.可以先判断索引是否存在

        /* 如同Kibana（http://localhost:5601） Dev Tools中输入：
        DELETE testindex
        * */
        DeleteIndexResponse response = client.admin().indices().prepareDelete("testindex")
                .execute().actionGet();
        System.out.println(response.isAcknowledged());
        if (response.isAcknowledged()) { //true
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }

        //判断是否存在索引
        IndicesExistsRequest request = new IndicesExistsRequest("testindex");

        IndicesExistsResponse existsResponse = client.admin().indices()
                .exists(request).actionGet();
        System.out.println(existsResponse.isExists());
        if (existsResponse.isExists()) {
            System.out.println("存在索引");
        } else { //false
            System.out.println("不存在索引");
        }

    }


    /*******************************************改**************************************/

    //更新文档
    @Test
    public void updateDocumnet() {
        UpdateRequest request = new UpdateRequest();
        request.index("testindex");
        request.type("blog");
        request.id("7");
        try {
            request.doc(
                    XContentFactory.jsonBuilder().startObject()
                            .field("title", "我是title，我被更新了")
                            .field("id", "2")
                            .field("postdate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                            .endObject()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateResponse response = null;
        try {
            response = client.update(request).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println(response.status());  //OK

    }

    //upsert(文档不存在则添加,存在则更新)
    @Test
    public void updateOrAddDocument() throws IOException, ExecutionException, InterruptedException {
        //添加文档
        IndexRequest requestAdd = new IndexRequest("testindex", "blog", "17")
                .source(
                        XContentFactory.jsonBuilder()
                                .startObject()
                                .field("id", "1")
                                .field("title", "java设计模式之装饰模式")
                                .field("content", "在不必改变原类文件和使用继承的情况下，动态的扩展一个对象的功能。")
                                .field("postdate", "2019-01-31")
                                .field("url", "https://www.baidu.com/")
                                .endObject()
                );

        UpdateRequest requestUpdate = new UpdateRequest("testindex", "blog", "17")
                .doc(
                        XContentFactory.jsonBuilder().startObject()
                                .field("title", "更新title2次,doc id is 17")
                                .endObject()
                ).upsert(requestAdd);  //文档不存在则添加,存在则更新

        UpdateResponse response = client.update(requestUpdate).get();
        System.out.println(response.status());  //CREATED, 非第一次执行后输出 OK

        //可以使用：GET testindex/blog/_search 查看文档
    }


    /*******************************************查**************************************/

    //根据index、type、id查询数据
    @Test
    public void searchByIndexTypeId() {
        /* 如同Kibana（http://localhost:5601） Dev Tools中输入：
        GET lagou/work/2
        * */
        GetResponse response = client.prepareGet(db, table, "2").execute().actionGet();
        String json = response.getSourceAsString();
        if (null != json) {
            System.out.println(json);
        } else {
            System.out.println("未查询到任何结果！");
        }
    }

    /*/////////////////////////////////////////批量操作//////////////////////////////////////*/

    //mget批量查询多个文档
    @Test
    public void searchPLByMultiGet() {
        MultiGetResponse multiGetResponses = client.prepareMultiGet()
                .add(db, table, "1","2")
                .add("testindex", "blog", "7", "17", "1")
                .get();
        for (MultiGetItemResponse item : multiGetResponses) {
            GetResponse response = item.getResponse();
            if (response != null && response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println(json);
            }
        }

        /*
        查询结果如下：
        { "title":"python django 开发工程师","salary_min":30000,"city":"上海","company":{"name":"阿里巴巴","company_addr":"北京市软件园A区"},"publish_date":"2019-1-23","comments":20}
{ "title":"python分布式爬虫开发","salary_min":15000,"city":"北京","company":{"name":"百度","company_addr":"北京市软件园"},"publish_date":"2019-1-23","comments":15}
{"id":"2","title":"我是title，我被更新了","content":"在不必改变原类文件和使用继承的情况下，动态的扩展一个对象的功能。","postdate":"2019-02-01","url":"https://www.baidu.com/"}
{"id":"1","title":"更新title2次,doc id is 17","content":"在不必改变原类文件和使用继承的情况下，动态的扩展一个对象的功能。","postdate":"2019-01-31","url":"https://www.baidu.com/"}

        * */
    }

    //从指定index中查询所有数据（无筛选条件）
    @Test
    public void searchAllDataFromIndex() {
        //查询所有记录
        /*如同Kibana（http://localhost:5601） Dev Tools中输入：
        GET lagou/work/_search?pretty
        {
          "query": {
             "match_all": {}
          },
          "size": 2
        }
        * */

        QueryBuilder qb = QueryBuilders.matchAllQuery();
        SearchResponse response = client.prepareSearch(db)
                .setQuery(qb)
                //.addSort("name", SortOrder.fromString("desc"))
                .setSize(2) //默认是一次查询10条数据，设置size,一次查询3个
                .get();

        SearchHits resultHits = response.getHits();
        System.out.println(JSONObject.toJSON(resultHits));
        /*输出结果如下：
        {"hits":[{"fields":{},"highlightFields":{},"id":"2","index":"lagou","matchedQueries":[],"score":1,"shard":{"index":"lagou","nodeId":"epDwP-wVROKfIfo0y39G7w","shardId":{"id":2,"index":{"name":"lagou","uUID":"y4C2PELyT1yepJDbgl7rmA"},"indexName":"lagou"}},"sortValues":[],"source":{"city":"北京","comments":15,"company":{"company_addr":"北京市软件园","name":"百度"},"publish_date":"2019-1-23","salary_min":15000,"title":"python分布式爬虫开发"},"sourceAsString":"{ \"title\":\"python分布式爬虫开发\",\"salary_min\":15000,\"city\":\"北京\",\"company\":{\"name\":\"百度\",\"company_addr\":\"北京市软件园\"},\"publish_date\":\"2019-1-23\",\"comments\":15}","sourceRef":{"childResources":[]},"type":"work","version":-1},{"fields":{},"highlightFields":{},"id":"1","index":"lagou","matchedQueries":[],"score":1,"shard":{"index":"lagou","nodeId":"epDwP-wVROKfIfo0y39G7w","shardId":{"id":3,"index":{"name":"lagou","uUID":"y4C2PELyT1yepJDbgl7rmA"},"indexName":"lagou"}},"sortValues":[],"source":{"city":"上海","comments":20,"company":{"company_addr":"北京市软件园A区","name":"阿里巴巴"},"publish_date":"2019-1-23","salary_min":30000,"title":"python django 开发工程师"},"sourceAsString":"{ \"title\":\"python django 开发工程师\",\"salary_min\":30000,\"city\":\"上海\",\"company\":{\"name\":\"阿里巴巴\",\"company_addr\":\"北京市软件园A区\"},\"publish_date\":\"2019-1-23\",\"comments\":20}","sourceRef":{"childResources":[]},"type":"work","version":-1}],"maxScore":1,"totalHits":3}
        * */
        for(SearchHit hit : resultHits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }

        /*
        输出结果如下：
        { "title":"python分布式爬虫开发","salary_min":15000,"city":"北京","company":{"name":"百度","company_addr":"北京市软件园"},"publish_date":"2019-1-23","comments":15}
comments = 15
city = 北京
salary_min = 15000
company = {company_addr=北京市软件园, name=百度}
title = python分布式爬虫开发
publish_date = 2019-1-23
{ "title":"python django 开发工程师","salary_min":30000,"city":"上海","company":{"name":"阿里巴巴","company_addr":"北京市软件园A区"},"publish_date":"2019-1-23","comments":20}
comments = 20
city = 上海
salary_min = 30000
company = {company_addr=北京市软件园A区, name=阿里巴巴}
title = python django 开发工程师
publish_date = 2019-1-23
        * */
    }

    //match查询、multiMatch查询 【match是分词匹配搜索】
    @Test
    public void searchByMatchOrMultiMatch() {
        //match查询
        //查询lagou这个索引下title字段包含python的数据
        /*QueryBuilder builder = QueryBuilders.matchQuery("title","python");
        SearchResponse response = client.prepareSearch(db)
                                  .setQuery(builder)
                                  .setSize(3)
                                  .get();
        SearchHits hits = response.getHits();
        System.out.println(JSONObject.toJSON(hits));*/



        //multiMatch查询
        //查询lagou这个索引下title、city字段包含开发或者苏州的数据
        /*如同Kibana（http://localhost:5601） Dev Tools中输入：
        GET lagou/_search
        {
            "query": {
                "multi_match": {
                    "query": "开发||苏州",
                    "fields": ["title","city"]
                }
            }
        }
        * */
        QueryBuilder builder = QueryBuilders.multiMatchQuery("开发||苏州","title","city");
        SearchResponse response = client.prepareSearch(db)
                .setQuery(builder)
                .setSize(3)
                .get();
        SearchHits hits = response.getHits();
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }


    }

    //term、terms查询 【term是代表完全匹配/精确查询，搜索前不会再对搜索词进行分词，所以我们的搜索词必须是文档分词集合中的一个】
    @Test
    public void searchByTermOrTerms() {
        //term查询
        //查询lagou索引下comments字段值为20的数据
        /*
        GET lagou/work/_search
        {
            "query": {
                "term": {
                    "comments": {
                        "value": "20"
                    }
                }
            }
        }
        * */
        /*QueryBuilder builder = QueryBuilders.termQuery("comments","20");
        SearchResponse response = client.prepareSearch(db)
                                  .setQuery(builder)
                                  .setSize(15).get();
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());  // 1
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }*/



        //terms查询
        /*
        GET lagou/work/_search
        {
            "query": {
                "terms": {
                    "comments": ["20","100"]
                }
            }
        }
        * */
        QueryBuilder builder = QueryBuilders.termsQuery("comments","20","100");
        SearchResponse response = client.prepareSearch(db)
                .setQuery(builder)
                .setSize(15)
                .get();
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());  // 2
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }
    }

    //其他一些查询【range、prefix、wildcard、type、ids】
    @Test
    public void searchDataByOther() {
        //限定范围查询【range查询】 大于等于15 小于等于20
        /*
        GET lagou/work/_search
        {
            "query": {
                "range": {
                    "comments": {
                        "gte": "15",
                        "lte": "20"
                    }
                }
            }
        }
        * */
        //QueryBuilder builder = QueryBuilders.rangeQuery("comments").from("15").to("20");

        //匹配前缀查询【prefix查询】
        //QueryBuilder builder = QueryBuilders.prefixQuery("city","上");

        //通配符查询【wildcard查询】
        //QueryBuilder builder = QueryBuilders.wildcardQuery("title","pyth*");

        //模糊查询【fuzzy查询】
        //QueryBuilder builder = QueryBuilders.fuzzyQuery("title","pytohn");

        //按类型查询【type查询】
        //QueryBuilder builder = QueryBuilders.typeQuery("work");

        //按照文档的多个id查询【ids查询】
        QueryBuilder builder = QueryBuilders.idsQuery().addIds("3","2");
        SearchResponse response = client.prepareSearch(db)
                .setQuery(builder)
                .setSize(5).get();
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }

    }

    //聚合查询
    @Test
    public void searchDateByJH() {
        //获取lagou索引下comments字段的最大值
        /*AggregationBuilder builder = AggregationBuilders.max("commentMax").field("comments");
        SearchResponse response = client.prepareSearch(db).addAggregation(builder).get();
        Max max = response.getAggregations().get("commentMax");
        System.out.println(max.getValue()); //100.0*/

        //获取lagou索引下comments字段的最小值
        /*AggregationBuilder builder = AggregationBuilders.min("commentMin").field("comments");
        SearchResponse response = client.prepareSearch(db).addAggregation(builder).get();
        Min min = response.getAggregations().get("commentMin");
        System.out.println(min.getValue()); //15.0*/

        //获取lagou索引下comments字段的平均值
        /*AggregationBuilder builder = AggregationBuilders.avg("commentAvg").field("comments");
        SearchResponse response = client.prepareSearch(db).addAggregation(builder).get();
        Avg avg = response.getAggregations().get("commentAvg");
        System.out.println(avg.getValue()); // 45.0*/

        //获取lagou索引下comments字段的总和
        /*AggregationBuilder builder = AggregationBuilders.sum("commentSum").field("comments");
        SearchResponse response = client.prepareSearch(db).addAggregation(builder).get();
        Sum sum = response.getAggregations().get("commentSum");
        System.out.println(sum.getValue()); // 135.0*/

        //获取lagou索引下comments字段的基数(合计这个字段上互不相同的字段值数目)
        AggregationBuilder builder = AggregationBuilders.cardinality("commentCardinality").field("comments");
        SearchResponse response = client.prepareSearch(db).addAggregation(builder).get();
        Cardinality cardinality = response.getAggregations().get("commentCardinality");
        System.out.println(cardinality.getValue()); // 3


    }

    //全文搜索 【query string】
    @Test
    public void searchAllQW() {
        /*
        GET lagou/work/_search?q=title:python
        * */
        //QueryBuilder builder = QueryBuilders.commonTermsQuery("title","python");

        //含20且不含100
        //QueryBuilder builder = QueryBuilders.queryStringQuery("+20 -100");

        //含20或者不含100
        QueryBuilder builder = QueryBuilders.simpleQueryStringQuery("+20 -100");

        SearchResponse response = client.prepareSearch(db)
                .setQuery(builder).get();
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }

    }

    //组合查询
    @Test
    public void searchByAssemble() {
        /*
        GET lagou/work/_search
        {
            "query": {
                "bool": {
                    "must": [
                        {
                            "term": {
                                        "title": "python"
                                    }
                        }
                    ],
                    "must_not": [
                        {
                              "match": {"city":"北京"}
                         }
                    ],
                    "should": [
                        {
                            "match": {
                                        "publish_date": "2019-02-01"
                                     }
                        }
                    ],
                    "filter": {
                            "range": {
                                        "comments": {
                                                "gte": 10,
                                                "lte": 20
                                         }
                             }
                    }
                }//bool
             }//query
        }//GET
        * */
        /*QueryBuilder builder = QueryBuilders.boolQuery()
                               .must(QueryBuilders.matchQuery("title","python"))
                               .mustNot(QueryBuilders.matchQuery("comments","20"))
                               .should(QueryBuilders.matchQuery("city","北京"))
                               .filter(QueryBuilders.rangeQuery("comments").gt(15));*/

        //constantscore 包裹查询, 高于设定分数, 不计算相关性
        QueryBuilder builders = QueryBuilders.constantScoreQuery(QueryBuilders.termQuery("title","python"));

        SearchResponse response = client.prepareSearch(db)
                .setQuery(builders).get();
        SearchHits hits = response.getHits();
        System.out.println(JSONObject.toJSON(hits));
        System.out.println(hits.getTotalHits());
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }
    }




    /*******************************************组合操作**************************************/
    //根据查询条件删除文档的记录
    @Test
    public void deleteBySearchCondition() {
        //查询标题中含有"工厂"的记录并删除
        /*如同Kibana（http://localhost:5601） Dev Tools中输入：
        GET testindex/_search
        {
            "query": {
                "match": {
                    "title": "学"
                }
            }
        }
        * */
        BulkIndexByScrollResponse response = DeleteByQueryAction.INSTANCE
                                             .newRequestBuilder(client)
                                             .filter(QueryBuilders.matchQuery("title","工厂"))
                                             .source("testindex")
                                             .get();

        long counts = response.getDeleted();  // 1
        System.out.println(counts);  //删除个数

    }

    //聚合操作
    @Test
    public void searchByJH() {

        //分组聚合
        //统计评论字段comments分组后对应的个数
        /*AggregationBuilder builder = AggregationBuilders.terms("commentsJH").field("comments");

        SearchResponse response = client.prepareSearch(db).addAggregation(builder).execute().actionGet();
        Terms terms = response.getAggregations().get("commentsJH");
        for(Terms.Bucket entry : terms.getBuckets()){
            System.out.println(entry.getKey() + " : " + entry.getDocCount());
        }*/

        /*
        输出结果如下：
        15 : 1
        20 : 1
        100 : 1
        * */




        //filter聚合
        //统计评论comments字段值为15的个数
        /*QueryBuilder query = QueryBuilders.termQuery("comments",15);
        AggregationBuilder aggBuilder = AggregationBuilders.filter("commentFilter",query);

        SearchResponse response = client.prepareSearch(db).addAggregation(aggBuilder).execute().actionGet();
        Filter filter = response.getAggregations().get("commentFilter");
        System.out.println(filter.getDocCount());  // 1*/

        //filters聚合【多个条件过滤聚合】
        //统计title中有python、评论comments字段值含20的个数
       /* AggregationBuilder aggBuilder = AggregationBuilders.filters("titleAndCommentFilters",
                new FiltersAggregator.KeyedFilter("title",QueryBuilders.termQuery("title","python")),
                new FiltersAggregator.KeyedFilter("comments",QueryBuilders.termQuery("comments",20))
                );

        SearchResponse response = client.prepareSearch(db).addAggregation(aggBuilder).execute().actionGet();
        Filters filters = response.getAggregations().get("titleAndCommentFilters");
        for(Filters.Bucket entry : filters.getBuckets()){
            System.out.println(entry.getKey() + " : " + entry.getDocCount());
        }*/

        /*
        输出结果如下：
        comments : 1
        title : 3
        * */


        //range聚合【范围聚合】
        //统计三个范围的个数
        /*AggregationBuilder builder = AggregationBuilders.range("commentRange")
                                     .field("comments")
                                     .addUnboundedFrom(15)//[from,)
                                     .addRange(15,100) //[from,to)
                                     .addUnboundedTo(100); //(,100)
        SearchResponse response = client.prepareSearch(db).addAggregation(builder).execute().actionGet();
        Range range = response.getAggregations().get("commentRange");
        for(Range.Bucket entry : range.getBuckets()) {
            System.out.println(entry.getKey() + " : " + entry.getDocCount());
        }*/

        /*
        输出结果如下：
        *-100.0 : 2          // <100
        15.0-100.0 : 2      // >=15 && <100
        15.0-* : 3         // >= 15
        * */


        //missing聚合(好像字段上没有空/null的会抛出异常？)
        //统计salary_min字段为空/null的个数
        AggregationBuilder builder = AggregationBuilders.missing("smMissing").field("salary_min");
        SearchResponse response = client.prepareSearch(db).addAggregation(builder).execute().actionGet();
        //Aggregation aggregation = response.getAggregations().get("smMissing");
        Missing missing = response.getAggregations().get("smMissing");
        System.out.println(missing.getDocCount()); // 1

    }


    /**
     * 集群管理
     */
    @Test
    public void manageCluster() {
        //获得集群名称
        ClusterHealthResponse healthResponse = client.admin().cluster().prepareHealth().get();
        String clusterName = healthResponse.getClusterName();
        System.out.println("clusterName = " + clusterName); //clusterName = my-application

        //获得所有节点个数
        int numberOfAllNodes = healthResponse.getNumberOfNodes();
        System.out.println("numberOfAllNodes = " + numberOfAllNodes); //numberOfAllNodes = 1

        //获得存放数据得节点个数
        int numberOfDataNodes = healthResponse.getNumberOfDataNodes();
        System.out.println("numberOfDataNodes = " + numberOfDataNodes); //numberOfDataNodes = 1

        for(ClusterIndexHealth indexHealth : healthResponse.getIndices().values()){
            String indexName = indexHealth.getIndex();
            int numberOfShards = indexHealth.getNumberOfShards();
            int numberOfReplicas = indexHealth.getNumberOfReplicas();
            System.out.printf("indexName=%s,numberOfShards=%d,numberOfReplicas=%d\n",indexName,numberOfShards,numberOfReplicas);

            ClusterHealthStatus status = indexHealth.getStatus();
            System.out.println(status.toString());

            /*
            indexName=.kibana,numberOfShards=1,numberOfReplicas=1
            YELLOW
            * */

            /*
            indexName=lagoumap,numberOfShards=5,numberOfReplicas=1
            YELLOW
            * */

            /*
            indexName=lagou,numberOfShards=5,numberOfReplicas=1
            YELLOW
            * */
        }
    }


    @Test
    public void selfTest() throws IOException {
        //match查询
        /*QueryBuilder builder = QueryBuilders.matchQuery("content","java");
        SearchResponse response = client.prepareSearch("testindex")
                //.setTypes("balala")
                //.setTypes("blog")
                .setQuery(builder)
                //.setSize(3)
                .get();
        SearchHits hits = response.getHits();
        System.out.println(JSONObject.toJSON(hits));
        for(SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());

            Map<String,Object> map = hit.sourceAsMap();
            for(String key : map.keySet()) {
                System.out.println(key + " = " + map.get(key));
            }
        }*/



        //ik分词
        /*
        GET testindex/_analyze?pretty
        {
            "analyzer":"ik_max_word",
            "text":"java设计模式"
        }
        * */
        /*AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(client,AnalyzeAction.INSTANCE,"testindex","java设计模式","python开发&设计工程师");
        ikRequest.setTokenizer("ik_max_word");
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
        //循环赋值
        List<String> searchTermList = new ArrayList<>();
        ikTokenList.forEach(ikToken -> {
            searchTermList.add(ikToken.getTerm());
            System.out.println(ikToken.getTerm());
        });*/


        //搜索建议
        /*
        GET testindex/_search?pretty
        {
            "suggest" : {
                "mySuggest" : {
                    "text" : "java",
                    "completion" : {
                            "field" : "suggest",
                            "size" : 10
                    }
                }
            },
            "_source": "title"
        }
        * */
        //field的名字,前缀(输入的text),以及大小size
        CompletionSuggestionBuilder builder = SuggestBuilders.completionSuggestion("suggest").text("ja").size(10);
        //CompletionSuggestionBuilder builder = SuggestBuilders.completionSuggestion("suggest").prefix("jhav", Fuzziness.TWO).size(10);
        //;  //设置模糊搜索的编辑距离为2步
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("mySuggest", builder);//添加suggest

        //设置查询builder的index,type,以及建议

        /*SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource(new String[]{"title","suggest"}, new String[]{});*/

        SearchRequestBuilder requestBuilder = client.prepareSearch("testindex").setTypes("blog").suggest(suggestBuilder);
        //.setSource(searchSourceBuilder);
        System.out.println("SearchRequestBuilder requestBuilder: " + requestBuilder.toString());

        SearchResponse response = requestBuilder.get();
        Suggest suggest = response.getSuggest();//suggest实体

        Set<String> suggestSet = new HashSet<>();//set
        int maxSuggest = 0;
        if (suggest!=null){
            Suggest.Suggestion result = suggest.getSuggestion("mySuggest");//获取suggest
            for (Object term : result.getEntries()) {

                if (term instanceof CompletionSuggestion.Entry){
                    CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
                    if (!item.getOptions().isEmpty()){
                        //若item的option不为空,循环遍历
                        for (CompletionSuggestion.Entry.Option option : item.getOptions()) {
                            System.out.println("option: " + JSONObject.toJSON(option));
                            String tip = option.getText().toString();
                            System.out.println("tip: " + tip);
                            String title = (String) option.getHit().getSource().get("title");
                            System.out.println("title: " + title);
                            if (!suggestSet.contains(tip)){
                                suggestSet.add(tip);
                                ++maxSuggest;
                            }
                        }
                    }
                }
                if (maxSuggest>=5){
                    break;
                }
            }
        }

        //Set转化为List
        List<String> suggests = Arrays.asList(suggestSet.toArray(new String[]{}));
        /*suggests.forEach(item -> {
            System.out.println(item);
        });*/


        }




}
