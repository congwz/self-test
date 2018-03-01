package com.viverselftest.service.impl;

import com.viverselftest.dao.jde.MyBatisMapperIfJudgeMapper;
import com.viverselftest.service.MyBatisMapperIfJudgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class MyBatisMapperIfJudgeServiceImpl implements MyBatisMapperIfJudgeService {

    @Autowired
    private MyBatisMapperIfJudgeMapper myBatisMapperIfJudgeMapper;

    @Override
    public int findResCountMybatisMapperIfJudge(String workCode, String status) {

        //方法一：封装为一级map【需要传status】
        Map map1 = new HashMap();
        map1.put("workCode",workCode);
        map1.put("status",status);

        //int resCount = myBatisMapperIfJudgeMapper.findResCountMybatisMapperIfJudge(map1);



        /*==========================================================================================================*/
        //方法二：封装status为二级map【不必传status】
        Map<String,Object> modelMap = new HashMap<>();
        modelMap.put("w","N");
        modelMap.put("f","Y");

        Map<String,Object> map = new HashMap();
        map.put("workCode",workCode);  //value是String类型
        map.put("status",modelMap);   //value是Map类型

        /*for (Entry<String,Object> itemMap : map.entrySet()) {
            if(itemMap.getKey() == null){continue;}
        }*/

        //int resCount = myBatisMapperIfJudgeMapper.findResCountMybatisMapperIfJudge2(map);  //返回合计数


        /*==========================================================================================================*/
        //方法三：两个String参数【需要传status 使用@Param注解】
        int resCount = myBatisMapperIfJudgeMapper.findResCountMybatisMapperIfJudge3(workCode,status);


        return resCount;
    }
}
