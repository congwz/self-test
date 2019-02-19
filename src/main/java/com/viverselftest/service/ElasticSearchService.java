package com.viverselftest.service;

import com.viverselftest.dto.PageDTO;
import com.viverselftest.po.elasticsearch.EsSuggestPO;

import java.util.List;

/**
 * Created by Congwz on 2019/1/31.
 */
public interface ElasticSearchService {

    /**
     * 获取搜索建议
     * @param search
     * @param tag
     * @return
     */
    List<EsSuggestPO> getSuggest(String search, String tag) throws Exception;

    /**
     * 获取搜索结果的分页列表信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageDTO getPageList(int currentPage, int pageSize, String search, String tag);

    /**
     * 获取热门搜索
     * @return
     */
    List<String> getHot();

    /**
     * 添加热门搜索词
     * @param search
     */
    void addHot(String search);

    void deleteHotKey();
}
