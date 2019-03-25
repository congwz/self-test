package com.viverselftest.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.viverselftest.consts.ElasticSearchConstants;
import com.viverselftest.dto.PageDTO;
import com.viverselftest.po.elasticsearch.EsLFPO;
import com.viverselftest.po.elasticsearch.EsSuggestPO;
import com.viverselftest.service.ElasticSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;

/**
 * Created by Congwz on 2019/1/31.
 */
@Service
@Slf4j
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private JedisPool jedisPool;

    //@Autowired
    //private ElasticSearchUtils esUtils;


    @Autowired
    private TransportClient esClient;

    /**
     * 获取搜索建议
     *
     * @param search
     * @param tag
     * @return
     */
    @Override
    public List<EsSuggestPO> getSuggest(String search, String tag) throws Exception {
        long star_time = new Date().getTime();
        //TransportClient client = esUtils.getClient();
        List<EsSuggestPO> resList = new ArrayList<>();
        //搜索建议-后台查询
        //resList = getTitleBySuggest(search, tag, client);
        resList = getTitleBySuggest(search, tag, esClient);

        //搜索建议-前端查询
        /*QueryBuilder builder = QueryBuilders.typeQuery("L".equals(tag) ? ElasticSearchConstants.ES_TABLE_LOST : ElasticSearchConstants.ES_TABLE_FIND);
        SearchResponse response = client.prepareSearch(ElasticSearchConstants.ES_DB)
                .setQuery(builder).get();
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        hits.forEach(hit -> {
            EsSuggestPO item = new EsSuggestPO();
            item.setId((String)hit.sourceAsMap().get("id"));
            item.setResult((String)hit.sourceAsMap().get("title"));
            resList.add(item);
        });*/

        //esUtils.closeClient();  //调用关闭客户端的接口会让查询速度变慢

        System.out.println(new Date().getTime() - star_time);
        return resList;

    }

    /**
     * 搜索建议
     *
     * @param search
     * @param tag
     * @param client
     * @return
     */
    private List<EsSuggestPO> getTitleBySuggest(String search, String tag, TransportClient client) {
        SearchRequestBuilder requestBuilder = null;
        List<EsSuggestPO> resList = new ArrayList<>();
        //field的名字,前缀(输入的text),以及大小size
        CompletionSuggestionBuilder builder = SuggestBuilders.completionSuggestion("suggest").text(search).size(10);
        /*CompletionSuggestionBuilder builder = SuggestBuilders.completionSuggestion("suggest").prefix(search,Fuzziness.TWO).size(10);*/
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("mySuggest", builder);//添加suggest
        requestBuilder = client.prepareSearch(ElasticSearchConstants.ES_DB).suggest(suggestBuilder);

        SearchResponse response = requestBuilder.get();
        Suggest suggest = response.getSuggest();//suggest实体
        Set<String> suggestSet = new HashSet<>();

        if (suggest != null) {
            Suggest.Suggestion result = suggest.getSuggestion("mySuggest");//获取suggest
            result.getEntries().forEach(term -> {
                if (term instanceof CompletionSuggestion.Entry) {
                    CompletionSuggestion.Entry item = (CompletionSuggestion.Entry) term;
                    if (!item.getOptions().isEmpty()) {
                        //若item的option不为空,循环遍历
                        item.getOptions().forEach(option -> {
                            //System.out.println("option: " + JSONObject.toJSON(option));
                            String type = option.getHit().getType();
                            if ("L".equals(tag)) {
                                if (ElasticSearchConstants.ES_TABLE_LOST.equals(type)) {
                                    getSuggestTitleList(resList, suggestSet, option);
                                }
                            } else if ("F".equals(tag)) {
                                if (ElasticSearchConstants.ES_TABLE_FIND.equals(type)) {
                                    getSuggestTitleList(resList, suggestSet, option);
                                }
                            }
                        });
                    }
                }
            });
        }
        return resList;
    }

    /**
     * 获取title列表
     *
     * @param resList
     * @param suggestSet
     * @param option
     */
    private void getSuggestTitleList(List<EsSuggestPO> resList, Set<String> suggestSet, CompletionSuggestion.Entry.Option option) {
        String title = (String) option.getHit().getSource().get("title");
        String id = (String) option.getHit().getSource().get("id");
        //System.out.println("title: " + title);
        if (!suggestSet.contains(title)) {
            EsSuggestPO resItem = new EsSuggestPO();
            resItem.setId(id);
            resItem.setResult(title);
            resList.add(resItem);
            suggestSet.add(title);
        }
    }


    /**
     * 获取搜索结果的分页列表信息
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    /*
    GET viver/lost/_search
    {
        "query": {
               "multi_match": {
                    "query": "猫",
                    "fields": ["title","content"]
                }
        },
        "size": 3,
        "from": 0,
        "sort": [
                    {
                        "_score": {
                            "order": "asc"
                        }
                    }
        ],
        "highlight": {
            "pre_tags": "<span style=color: red>",
            "post_tags": "</span>",
            "fields": {
                "title": {},
                "content": {
                   "fragment_size": 10000  //设置要显示出来的fragment文本判断的长度，默认是100
                }
            }
        }

    }
    * */
    @Override
    public PageDTO getPageList(int currentPage, int pageSize, String search, String tag) {
        List<EsLFPO> list = new ArrayList<>();
        PageDTO pd = new PageDTO();
        int total = 0;
        long star_time = System.currentTimeMillis();
        //TransportClient client = null;
        try {
            //client = esUtils.getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //list = getDataByMultiQuerySelf(currentPage, pageSize, search, tag, client);
        list = getDataByMultiQuerySelf(currentPage, pageSize, search, tag, esClient);
        if(!CollectionUtils.isEmpty(list)) {
            total = list.get(0).getTotal();
        }

        /*
        //分词
        AnalyzeRequestBuilder ikRequest = new AnalyzeRequestBuilder(client, AnalyzeAction.INSTANCE, ElasticSearchConstants.ES_DB, search);
        ikRequest.setTokenizer("ik_max_word");
        List<AnalyzeResponse.AnalyzeToken> ikTokenList = ikRequest.execute().actionGet().getTokens();
        List<String> ikList = new ArrayList<>();
        ikTokenList.forEach(ikToken -> {
            if(ikToken.getTerm().length() > 1) {
                if(!(ikToken.getTerm().contains("寻找")
                        || ikToken.getTerm().contains("遗失")
                        || ikToken.getTerm().contains("招领")
                        || ikToken.getTerm().contains("丢失")
                        || ikToken.getTerm().contains("丢了")
                        || ikToken.getTerm().contains("一部")
                        || ikToken.getTerm().contains("一只")
                        || ikToken.getTerm().contains("一个")
                ))
                    ikList.add(ikToken.getTerm());
            }else if (ikToken.getTerm().length() == 1) {
                if(ikToken.getTerm().contains("猫")
                        ||ikToken.getTerm().contains("狗")
                        ||ikToken.getTerm().contains("包")
                        ||ikToken.getTerm().contains("箱")
                        ||ikToken.getTerm().contains("书")
                        ) {
                    ikList.add(ikToken.getTerm());
                }
            }

        });

        //查找结果
        for(String item : ikList) {
            System.out.print(item + " *** ");
            list.addAll(getDataByMultiQuery(item, tag, client));
        }
        //去重
        list = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(EsLFPO::getId))), ArrayList::new));
        total = list.size();
        list = list.stream()
                .sorted(Comparator.comparingDouble(EsLFPO::getScore).reversed())
                .skip((currentPage-1)*pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        */

        //list.get(0).setTotal(total);
        System.out.println("finish search: " + (float)(System.currentTimeMillis() - star_time)/1000 + "s");

        pd.setPageNumber(currentPage);
        pd.setPageSize(pageSize);
        pd.setTotal(total);
        pd.setData(list);


        /*List<ExcelExportBigDataPO> listtest = new ArrayList<>();
        Workbook workbook = null;
        Date startDate = new Date();
        ExportParams exportParams = new ExportParams("大数据导出Excel测试","BigData");
        for(int i = 0; i < 1000000; i++){ // 一百万数据量
            ExcelExportBigDataPO itemtest = new ExcelExportBigDataPO();
            itemtest.setId(i);
            itemtest.setWork_code("0"+i);
            itemtest.setUser_name(i+"TestName"+i);
            itemtest.setBirthday(new Date());
            itemtest.setPhone("1525130526"+i);
            itemtest.setUserRemark(i+"大数据测试备注属性");
            listtest.add(itemtest);

            if(listtest.size() == 10000){
                workbook = ExcelExportUtil.exportBigExcel(exportParams,ExcelExportBigDataPO.class,listtest);
                listtest.clear();
            }
        }//for

        ExcelExportUtil.closeExportBigExcel();
        System.out.println(new Date().getTime() - startDate.getTime());
        File saveFile = new File("C:/excel");
        if(!saveFile.exists()){
            saveFile.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("C:/excel/BigDataExcel.xlsx");
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/


        return pd;
    }

    private List<EsLFPO> getDataByMultiQuery(String search, String tag, TransportClient client) {
        int total;
        List<EsLFPO> list = new ArrayList<>();
        long star_time = System.currentTimeMillis();
        QueryBuilder builder = QueryBuilders.multiMatchQuery(search,"title","content");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style=color:red>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("title");
        highlightBuilder.field("content",10000);
        SearchResponse response = client.prepareSearch(ElasticSearchConstants.ES_DB)
                .setTypes("F".equals(tag)? ElasticSearchConstants.ES_TABLE_FIND : ElasticSearchConstants.ES_TABLE_LOST)
                .setQuery(builder)
                //.setSize(pageSize)
                .setSize(1000)
                //.setFrom(currentPage*pageSize - pageSize)  //分页
                //.addSort("_score", SortOrder.DESC)
                .highlighter(highlightBuilder)
                //.execute().actionGet();
                .get();

        SearchHits hits = response.getHits();
        float searchTime = (float)(System.currentTimeMillis() - star_time)/1000;
        //System.out.println(JSONObject.toJSON(hits));
        total = (int) hits.getTotalHits();
        if("L".equals(tag)) {
            for(SearchHit hit : hits) {
                String title;
                String content;
                EsLFPO item = new EsLFPO();
                HighlightField titleField = hit.getHighlightFields().get("title");
                if (titleField != null) {
                    Text[] text = titleField.getFragments();
                    //title = text[0].toString();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    title = sb.toString();
                }else {
                    title = (String) hit.getSource().get("title");
                }
                HighlightField contentField = hit.getHighlightFields().get("content");
                if (contentField != null) {
                    Text[] text = contentField.getFragments();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    content = sb.toString();
                }else {
                    content = (String) hit.getSource().get("content");
                }
                String id = (String) hit.getSource().get("id");
                String url = (String) hit.getSource().get("url");
                String addr = (String) hit.getSource().get("addr");
                String time = (String) hit.getSource().get("time");
                String money = (String) hit.getSource().get("money");
                String name = (String) hit.getSource().get("name");
                String image = (String) hit.getSource().get("image");
                double score = (double) hit.getScore();
                item.setTitle(title);
                item.setContent(content);
                item.setId(id);
                item.setUrl(url);
                item.setAddr(addr);
                item.setTime(time);
                item.setMoney(money);
                item.setName(name);
                item.setImage(image);
                item.setScore(score);
                //item.setTotal(total);
                item.setSearchTime(searchTime);
                list.add(item);
            }
        }else if("F".equals(tag)) {
            for(SearchHit hit : hits) {
                String title;
                String content;
                EsLFPO item = new EsLFPO();
                HighlightField titleField = hit.getHighlightFields().get("title");
                if (titleField != null) {
                    Text[] text = titleField.getFragments();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    title = sb.toString();
                }else {
                    title = (String) hit.getSource().get("title");
                }
                HighlightField contentField = hit.getHighlightFields().get("content");
                if (contentField != null) {
                    Text[] text = contentField.getFragments();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    content = sb.toString();
                }else {
                    content = (String) hit.getSource().get("content");
                }
                String id = (String) hit.getSource().get("id");
                String url = (String) hit.getSource().get("url");
                String time = (String) hit.getSource().get("time");
                String image = (String) hit.getSource().get("image");
                double score = (double) hit.getScore();
                item.setTitle(title);
                item.setContent(content);
                item.setId(id);
                item.setUrl(url);
                item.setTime(time);
                item.setImage(image);
                item.setScore(score);
                //item.setTotal(total);
                item.setSearchTime(searchTime);
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 添加自定义分词库后，索引时使用 ik_max_word，搜索时用ik_smart
     * @param currentPage
     * @param pageSize
     * @param search
     * @param tag
     * @param client
     * @return
     */
    private List<EsLFPO> getDataByMultiQuerySelf(int currentPage, int pageSize, String search, String tag, TransportClient client) {
        int total;
        List<EsLFPO> list = new ArrayList<>();
        long star_time = System.currentTimeMillis();
        QueryBuilder builder = QueryBuilders.multiMatchQuery(search,"title","content").analyzer("ik_smart");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style=color:red>");
        highlightBuilder.postTags("</span>");
        highlightBuilder.field("title");
        highlightBuilder.field("content",10000);
        SearchResponse response = client.prepareSearch(ElasticSearchConstants.ES_DB)
                .setTypes("F".equals(tag)? ElasticSearchConstants.ES_TABLE_FIND : ElasticSearchConstants.ES_TABLE_LOST)
                .setQuery(builder)
                .setSize(pageSize)
                .setFrom(currentPage*pageSize - pageSize)  //分页
                .addSort("_score", SortOrder.DESC)
                .highlighter(highlightBuilder)
                //.execute().actionGet();
                .get();

        SearchHits hits = response.getHits();
        float searchTime = (float)(System.currentTimeMillis() - star_time)/1000;
        System.out.println(JSONObject.toJSON(hits));
        total = (int) hits.getTotalHits();
        if("L".equals(tag)) {
            for(SearchHit hit : hits) {
                String title;
                String content;
                EsLFPO item = new EsLFPO();
                HighlightField titleField = hit.getHighlightFields().get("title");
                if (titleField != null) {
                    Text[] text = titleField.getFragments();
                    //title = text[0].toString();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    title = sb.toString();
                }else {
                    title = (String) hit.getSource().get("title");
                }
                HighlightField contentField = hit.getHighlightFields().get("content");
                if (contentField != null) {
                    Text[] text = contentField.getFragments();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    content = sb.toString();
                }else {
                    content = (String) hit.getSource().get("content");
                }
                String id = (String) hit.getSource().get("id");
                String url = (String) hit.getSource().get("url");
                String addr = (String) hit.getSource().get("addr");
                String time = (String) hit.getSource().get("time");
                String money = (String) hit.getSource().get("money");
                String name = (String) hit.getSource().get("name");
                String image = (String) hit.getSource().get("image");
                double score = (double) hit.getScore();
                item.setTitle(title);
                item.setContent(content);
                item.setId(id);
                item.setUrl(url);
                item.setAddr(addr);
                item.setTime(time);
                item.setMoney(money);
                item.setName(name);
                item.setImage(image);
                item.setScore(score);
                item.setTotal(total);
                item.setSearchTime(searchTime);
                list.add(item);
            }
        }else if("F".equals(tag)) {
            for(SearchHit hit : hits) {
                String title;
                String content;
                EsLFPO item = new EsLFPO();
                HighlightField titleField = hit.getHighlightFields().get("title");
                if (titleField != null) {
                    Text[] text = titleField.getFragments();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    title = sb.toString();
                }else {
                    title = (String) hit.getSource().get("title");
                }
                HighlightField contentField = hit.getHighlightFields().get("content");
                if (contentField != null) {
                    Text[] text = contentField.getFragments();
                    StringBuilder sb = new StringBuilder();
                    for(Text t : text) {
                        sb.append(t.toString());
                    }
                    content = sb.toString();
                }else {
                    content = (String) hit.getSource().get("content");
                }
                String id = (String) hit.getSource().get("id");
                String url = (String) hit.getSource().get("url");
                String time = (String) hit.getSource().get("time");
                String image = (String) hit.getSource().get("image");
                double score = (double) hit.getScore();
                item.setTitle(title);
                item.setContent(content);
                item.setId(id);
                item.setUrl(url);
                item.setTime(time);
                item.setImage(image);
                item.setScore(score);
                item.setTotal(total);
                item.setSearchTime(searchTime);
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 获取热门搜索  SortedSet（有序集合）
     * @return
     */
    @Override
    public List<String> getHot() {
        Jedis jedis = jedisPool.getResource();

        /*Set<String> res = jedis.zrangeByScore(ElasticSearchConstants.HOT_SEARCH, 0, 100);
        System.out.println("viver_hot_search: " + res.toString());*/

        Set<Tuple> tuples = jedis.zrangeByScoreWithScores(ElasticSearchConstants.HOT_SEARCH, "-inf", "+inf");
        tuples.forEach(tuple -> {
            System.out.println(tuple.getElement() + "=" + tuple.getScore());
        });

        Set<String> searchByScoreList = jedis.zrevrangeByScore(ElasticSearchConstants.HOT_SEARCH, "+inf", "-inf",0,5);

        System.out.println("searchByScoreList: " + searchByScoreList.toString());
        jedis.close();
        return new ArrayList<>(searchByScoreList);
    }

    /**
     * 添加热门搜索词
     * @param search
     */
    @Override
    public void addHot(String search) {
        Jedis jedis = jedisPool.getResource();
        jedis.zincrby(ElasticSearchConstants.HOT_SEARCH, 1, search);
        jedis.close();
    }

    /**
     * 清空热门搜索记录
     */
    @Override
    public void deleteHotKey() {
        Jedis jedis = jedisPool.getResource();
        jedis.del(ElasticSearchConstants.HOT_SEARCH);
        jedis.close();
    }

    /**
     * 根据时间顺序查询最新消息公告
     * @return
     */
    @Override
    public List<EsLFPO> getLastedNew() {
        List<EsLFPO> resList = new ArrayList<>();
        //TransportClient client = null;
        try {
            //client = esUtils.getClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        QueryBuilder qb = QueryBuilders.matchAllQuery();
        //SearchResponse response = client.prepareSearch(ElasticSearchConstants.ES_DB)
        SearchResponse response = esClient.prepareSearch(ElasticSearchConstants.ES_DB)
                .setQuery(qb)
                .addSort("time", SortOrder.DESC)
                .setSize(15) //默认是一次查询10条数据，设置size,一次查询15个
                .get();

        SearchHits resultHits = response.getHits();
        for(SearchHit hit : resultHits) {
            String jStr = hit.getSourceAsString();
            JSONObject jObj = JSONObject.parseObject(jStr); //将json字符串转换为json对象
            EsLFPO item = (EsLFPO) JSONObject.toJavaObject(jObj,EsLFPO.class); //将建json对象转换为Person对象
            resList.add(item);
        }
        return resList;
    }

}
