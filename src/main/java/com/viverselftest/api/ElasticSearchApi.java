package com.viverselftest.api;

import com.viverselftest.dto.PageDTO;
import com.viverselftest.po.elasticsearch.EsLFPO;
import com.viverselftest.po.elasticsearch.EsSuggestPO;
import com.viverselftest.service.ElasticSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * Created by Congwz on 2019/1/31.
 */
@Api(tags = "搜索引擎接口")
@RestController
@RequestMapping("/api/es")
@Slf4j
public class ElasticSearchApi {

    @Autowired
    private ElasticSearchService elasticSearchService;


    /**
     * 获取搜索建议
     * @param search
     * @param tag
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取搜索建议")
    @GetMapping("/get-suggest")
    public List<EsSuggestPO> getSuggest(@RequestParam(name = "search") String search,
                                        @RequestParam(name = "tag") String tag) throws Exception {
        log.info("tag is: " + tag + " ,search keyword is: " + search);
        return elasticSearchService.getSuggest(search, tag);
    }

    /**
     * 获取搜索结果的分页列表信息
     * @param currentPage
     * @param pageSize
     * @param search
     * @param tag
     * @return
     */
    @ApiOperation(value = "获取搜索结果的分页列表信息")
    @GetMapping("/get-page-list")
    public PageDTO getPageList(@RequestParam(name = "currentPage") int currentPage,
                               @RequestParam(name = "pageSize") int pageSize,
                               @RequestParam(name = "search") String search,
                               @RequestParam(name = "tag") String tag){
        return elasticSearchService.getPageList(currentPage,pageSize,search,tag);
    }

    /**
     * 获取热门搜索
     * @return
     */
    @ApiOperation(value = "获取热门搜索")
    @GetMapping("/get-hot")
    public List<String> getHot() {
        return elasticSearchService.getHot();
    }

    /**
     * 添加热门搜索词
     * @param search
     */
    @ApiOperation(value = "添加热门搜索词")
    @GetMapping("/add-hot")
    public void addHot(@RequestParam(name = "search") String search) {
        elasticSearchService.addHot(search);
    }

    /**
     * 删除热门搜索记录
     */
    @ApiOperation(value = "清空热门搜索记录")
    @GetMapping("/delete-hot-key")
    public void deleteHotKey() {
        elasticSearchService.deleteHotKey();
    }

    /**
     * 根据时间顺序查询最新消息公告
     * @return
     */
    @ApiOperation(value = "获取最新消息公告")
    @GetMapping("/get-lasted-new")
    public List<EsLFPO> getLastedNew() {
        return elasticSearchService.getLastedNew();
    }



}
