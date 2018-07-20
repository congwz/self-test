package com.viverselftest.dao.jde;

import com.viverselftest.dto.inquireonline.InquireOnlineHdDTO;
import com.viverselftest.dto.inquireonline.hcSupplierQuotedGenDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Congwz on 2018/7/9.
 */
@Repository
public interface InquireOnlineMapper {




    /*=======================================对接供应商报价系统==========================================*/

    /**
     * 查询生成的报价信息
     * @param quotation_orders
     */
    List<hcSupplierQuotedGenDTO> findHcSupplierQuotedInfo(List<InquireOnlineHdDTO> quotation_orders);




}
