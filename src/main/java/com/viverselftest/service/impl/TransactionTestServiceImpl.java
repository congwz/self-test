package com.viverselftest.service.impl;

import com.viverselftest.dao.jde.TransactionTestMapper;
import com.viverselftest.dto.inquireonline.hcSupplierQuotedGenDTO;
import com.viverselftest.exception.ErrorDataException;
import com.viverselftest.exception.ErrorException;
import com.viverselftest.service.TransactionTestService;
import com.viverselftest.util.ErrorMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by Congwz on 2018/7/17.
 */
@Service
public class TransactionTestServiceImpl implements TransactionTestService {

    @Autowired
    private TransactionTestMapper transactionTestMapper;

    @Autowired
    private ErrorMsgUtils errorMsgUtils;

    @Override
    @Transactional(value="jdeTransactionManager")
    public void testRollBack(String name,String delCode) {
        //添加name的信息
        transactionTestMapper.add(name);
        //删除delCode的信息
        transactionTestMapper.del(delCode);

        if(1 == 2){
            //手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            //throw new ErrorDataException("100001", "对接接口处理失败", name + " " + delCode);
        }

    }




}
