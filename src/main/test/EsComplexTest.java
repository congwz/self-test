import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.*;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by Congwz on 2019/2/1.
 */
public class EsComplexTest {
    private TransportClient client;

    private final static String db = "complex";

    private final static String table = "test";

    //@Before（Junit中的@Before） 在每个测试方法之前都执行一次, 方法需要声明为public
    @Before
    public void getClient() throws Exception {
        //设置集群名称
        Settings settings = Settings.builder()
                .put("cluster.name", "my-application")
                //.put("number_of_shards", 3)
                //.put("number_of_replicas", 1)
                .build();// 集群名

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



    //创建索引并添加映射
    @Test
    public void createIndexAndMapping() throws IOException {

        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(db);
        XContentBuilder mapping = jsonBuilder()
                .startObject()
                 .startObject("properties") //设置之定义字段
                   .startObject("id")
                     .field("type", "long") //设置数据类型
                   .endObject()
                   .startObject("title")
                     .field("type", "text")
                     .field("analyzer", "ik_max_word")
                   .endObject()
                   .startObject("suggestText")
                     .field("type", "completion")
                     .field("analyzer", "standard")
                     //.field("payloads", true)
                     .field("preserve_separators", false)
                     .field("preserve_position_increments", true)
                     .field("max_input_length", 10)
                   .endObject()
                   .startObject("prefix_pinyin")
                      .field("type", "completion")
                      .field("analyzer", "prefix_pinyin_analyzer")
                      .field("search_analyzer", "standard")
                      .field("preserve_separators", false)
                      //.field("payloads", true)
                   .endObject()
                   .startObject("full_pinyin")
                      .field("type", "completion")
                      .field("analyzer", "full_pinyin_analyzer")
                      .field("search_analyzer", "full_pinyin_analyzer")
                      .field("preserve_separators", false)
                      //.field("payloads", true)
                   .endObject()
                 .endObject()
                .endObject();
        cib.setSettings
                (Settings.builder().loadFromSource(jsonBuilder()
                .startObject()
                 .startObject("analysis")
                   .startObject("analyzer")
                     .startObject("prefix_pinyin_analyzer")
                        .field("tokenizer", "standard")
                        .field("filter", new String[]{"lowercase","prefix_pinyin"})
                     .endObject()
                     .startObject("full_pinyin_analyzer")
                         .field("tokenizer", "standard")
                         .field("filter", new String[]{"lowercase","full_pinyin"})
                     .endObject()
                   .endObject()
                   .startObject("filter")
                     .startObject("_pattern")
                         .field("type", "pattern_capture")
                         .field("preserve_original", 1)
                         .field("patterns", new String[]{"([0-9])","([a-z])"})
                     .endObject()
                     .startObject("prefix_pinyin")
                         .field("type", "pinyin")
                         .field("keep_first_letter", true)
                         .field("keep_full_pinyin", false)
                         .field("none_chinese_pinyin_tokenize", false)
                         .field("keep_original", false)
                     .endObject()
                     .startObject("full_pinyin")
                         .field("type", "pinyin")
                         .field("keep_first_letter", false)
                         .field("keep_full_pinyin", true)
                         .field("keep_original", false)
                         .field("keep_none_chinese_in_first_letter", false)
                     .endObject()
                   .endObject()
                 .endObject()
                .endObject().string())
                )
                .addMapping(table, mapping);
                //.execute().actionGet();

        //cib.addMapping(table, mapping);

        CreateIndexResponse res = cib.execute().actionGet();
        System.out.println(res.isAcknowledged()); //创建成功，输出为true
    }


    //bulk批量操作（增删改）
    //批量添加文档（不存在索引、类型、id会自动创建）
    @Test
    public void AddByBulk() throws IOException {

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        bulkRequest.add(client.prepareIndex(db, table, "7")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id",8)
                        .field("title", "java单例模式")
                        /*.startObject("suggestText")  //搜索建议
                        .field("input",suggestInput)  //数组
                        .field("weight",3)
                        .endObject()*/
                        .field("suggestText", new String[]{"模式","单例"})  //搜索建议
                        .field("prefix_pinyin",new String[]{"ms","dl"})  //搜索建议
                        .field("full_pinyin",new String[]{"moshi","danli"})  //搜索建议
                        .endObject()
                )
        );

        bulkRequest.add(client.prepareIndex(db, table, "9")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("id",9)
                        .field("title", "设计模式")
                        /*.startObject("suggestText")  //搜索建议
                        .field("input",suggestInput)  //数组
                        .field("weight",3)
                        .endObject()*/
                        .field("suggestText", new String[]{"设计","模式"})  //搜索建议
                        .field("prefix_pinyin", new String[]{"sj","ms"})  //搜索建议
                        .field("full_pinyin",new String[]{"sheji","moshi"})  //搜索建议
                        .endObject()
                )
        );


        BulkResponse response = bulkRequest.get();
        System.out.println(response.hasFailures()); //false 批量操作成功
        System.out.println(response.getTook()); //28ms  (How long the bulk execution took)
        if (response.hasFailures()) { //true
            System.out.println("bulk批量添加失败了");
        }
    }

    @Test
    public void findSuggester() {
        LinkedHashSet<String> returnSet = new LinkedHashSet<>();
        String input = "模式";
        //String input = "单例";
        //String input = "单";
        //String input = "java";
        SearchRequestBuilder requestBuilder = null;
        //field的名字,前缀(输入的text),以及大小size
        //全拼前缀匹配
        CompletionSuggestionBuilder fullPinyinSuggest = SuggestBuilders.completionSuggestion("full_pinyin").text(input).size(10);
        //汉字前缀匹配
        CompletionSuggestionBuilder suggestText = SuggestBuilders.completionSuggestion("suggestText").text(input).size(10);
        //拼音搜字母前缀匹配
        CompletionSuggestionBuilder prefixPinyinSuggest = SuggestBuilders.completionSuggestion("prefix_pinyin").text(input).size(10);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("full_pinyin_suggest",fullPinyinSuggest)
        .addSuggestion("suggestText",suggestText)
        .addSuggestion("prefix_pinyin_text",prefixPinyinSuggest);

        requestBuilder = client.prepareSearch(db).suggest(suggestBuilder);

        System.out.println(requestBuilder.toString());
        SearchResponse response = requestBuilder.get();
        //SearchResponse response2 = requestBuilder.execute().actionGet();;

        Suggest.Suggestion resultSuggestText = response.getSuggest().getSuggestion("suggestText");
        Suggest.Suggestion resultFullPinyin = response.getSuggest().getSuggestion("full_pinyin_suggest");
        Suggest.Suggestion resultPrefixPinyin = response.getSuggest().getSuggestion("prefix_pinyin_text");

        List<Suggest.Suggestion.Entry> entries = resultSuggestText.getEntries();

        //汉字前缀匹配
        for (Suggest.Suggestion.Entry entry : entries) {
            List<Suggest.Suggestion.Entry.Option> options = entry.getOptions();
            for (Suggest.Suggestion.Entry.Option option : options) {
                System.out.println("option: " + JSONObject.toJSON(option));
                returnSet.add(option.getText().toString());
            }
        }
        //全拼suggest补充
        if (returnSet.size() < 10) {
            List<Suggest.Suggestion.Entry> fullPinyinEntries = resultFullPinyin.getEntries();
            for (Suggest.Suggestion.Entry entry : fullPinyinEntries) {
                List<Suggest.Suggestion.Entry.Option> options = entry.getOptions();
                for (Suggest.Suggestion.Entry.Option option : options) {
                    if (returnSet.size() < 10) {
                        System.out.println("option2: " + JSONObject.toJSON(option));
                        returnSet.add(option.getText().toString());
                    }
                }
            }
        }
        //首字母拼音suggest补充
        if (returnSet.size() == 0) {
            List<Suggest.Suggestion.Entry> prefixPinyinEntries = resultPrefixPinyin.getEntries();
            for (Suggest.Suggestion.Entry entry : prefixPinyinEntries) {
                List<Suggest.Suggestion.Entry.Option> options = entry.getOptions();
                for (Suggest.Suggestion.Entry.Option option : options) {
                    returnSet.add(option.getText().toString());
                }
            }
        }

        System.out.println("搜索建议结果：" + new ArrayList<>(returnSet).toString());



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


}
