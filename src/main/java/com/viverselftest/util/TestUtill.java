package com.viverselftest.util;

import com.viverselftest.dao.jde.InquireOnlineMapper;
import com.viverselftest.dto.inquireonline.InquireOnlineHdDTO;
import com.viverselftest.dto.inquireonline.hcSupplierQuotedGenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Congwz on 2018/7/18.
 */
@Component
public class TestUtill {

    @Autowired
    private InquireOnlineMapper inquireOnlineMapper;

    public List<hcSupplierQuotedGenDTO> genQuoted(List<InquireOnlineHdDTO> quotation_orders){
        List<hcSupplierQuotedGenDTO> res = inquireOnlineMapper.findHcSupplierQuotedInfo(quotation_orders);
        System.out.println(res.toString());
        return res;
    }



}
