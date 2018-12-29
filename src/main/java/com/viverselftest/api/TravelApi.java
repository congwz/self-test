package com.viverselftest.api;

import com.viverselftest.vo.TravelCommonVO;
import com.viverselftest.vo.TravelHomeCityVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Congwz on 2018/12/29.
 */
@Api(tags = "去哪儿")
@RestController
@RequestMapping("/api/travel")
public class TravelApi {

    @ApiOperation(value = "获取首页数据")
    @GetMapping("/home-city")
    public TravelHomeCityVO getLostList(@RequestParam(name="city") String city){
        TravelHomeCityVO res = new TravelHomeCityVO();
        List<TravelCommonVO>  swiperList = new ArrayList<>();
        for(int i=1; i<4; i++) {
            TravelCommonVO item = new TravelCommonVO();
            item.setId("000" + i);
            switch (i) {
                case 1:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1811/42/f11738e550ef8902.jpg_750x200_5b72564d.jpg");
                    break;
                case 2:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1811/a8/cb43c4ac6c215d02.jpg_750x200_83dee994.jpg");
                    break;
                case 3:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1811/7c/8e5c4ab8ee8b7402.jpg_750x200_dd7a38dd.jpg");
            }
            swiperList.add(item);
        }
        List<TravelCommonVO>  iconList = new ArrayList<>();
        for(int i=1; i<6; i++) {
            TravelCommonVO item = new TravelCommonVO();
            item.setId(String.valueOf(i));
            switch (i) {
                case 1:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1803/95/f3dd6c383aeb3b02.png");
                    item.setDesc("景点门票");
                    break;
                case 2:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1803/67/9a1678221b8e0e02.png");
                    item.setDesc("滑雪季");
                    break;
                case 3:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1710/a6/83f636bd75ae6302.png");
                    item.setDesc("泡温泉");
                    break;
                case 4:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1611/35/2640cab202c41b02.png");
                    item.setDesc("动植园");
                    break;
                case 5:
                    item.setImgUrl("http://img1.qunarzz.com/piao/fusion/1611/d0/e09575e66f4aa402.png");
                    item.setDesc("游乐园");
                    break;
            }
            iconList.add(item);
        }
        List<TravelCommonVO>  recommendList = new ArrayList<>();
        for(int i=1; i<3; i++) {
            TravelCommonVO item = new TravelCommonVO();
            item.setId(String.valueOf(i));
            switch (i) {
                case 1:
                    item.setImgUrl("http://img1.qunarzz.com/sight/p0/1409/19/adca619faaab0898245dc4ec482b5722.jpg_140x140_80f63803.jpg");
                    item.setTitle("故宫");
                    item.setDesc("东方宫殿建筑代表，世界宫殿建筑典范");
                    break;
                case 2:
                    item.setImgUrl("http://img1.qunarzz.com/sight/p0/1511/d2/d2aec2dfc5aa771290.water.jpg_140x140_abb362a7.jpg");
                    item.setTitle("南山滑雪场");
                    item.setDesc("北京专业级滑雪圣地");
                    break;
            }
            recommendList.add(item);
        }
        List<TravelCommonVO>  weekendList = new ArrayList<>();
        for(int i=1; i<3; i++) {
            TravelCommonVO item = new TravelCommonVO();
            item.setId(String.valueOf(i));
            switch (i) {
                case 1:
                    item.setImgUrl("http://img1.qunarzz.com/sight/source/1510/6e/1ea71e2f04e.jpg_r_640x214_aa6f091d.jpg");
                    item.setTitle("北京温泉排行榜");
                    item.setDesc("细数北京温泉，温暖你的冬天");
                    break;
                case 2:
                    item.setImgUrl("http://img1.qunarzz.com/sight/source/1505/aa/7baaf8a851d221.jpg_r_640x214_1431200f.jpg");
                    item.setTitle("北京必游TOP10");
                    item.setDesc("来北京必去的景点非这些地方莫属");
                    break;
            }
            weekendList.add(item);
        }
        res.setCity(city);
        res.setSwiperList(swiperList);
        res.setIconList(iconList);
        res.setRecommendList(recommendList);
        res.setWeekendList(weekendList);
        return res;
    }
}
