package com.viverselftest.dao.jde;

import com.viverselftest.po.lost.LostPO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Congwz on 2018/11/12.
 */
@Repository
public interface LostMapper {

    /**
     * 查询寻物分页信息
     * @return
     */
    @Select("select id,image,title,content,name,user_id,create_date,update_date,province,city,"
            + "is_lost,money,remark "
            + "from PA_VIVER_LOST "
            + "where is_lost = 'Y' "
            + "order by create_date")
    List<LostPO> findLostList();
}
