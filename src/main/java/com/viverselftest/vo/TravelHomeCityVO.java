package com.viverselftest.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by Congwz on 2018/12/29.
 */
@Data
public class TravelHomeCityVO {

    private String city;

    private List<TravelCommonVO> swiperList;

    private List<TravelCommonVO> iconList;

    private List<TravelCommonVO> recommendList;

    private List<TravelCommonVO> weekendList;
}
