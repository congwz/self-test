package com.viverselftest.dao.jde;

import com.viverselftest.po.lost.LostPO;
import com.viverselftest.po.lost.UserInfoPO;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 合计用户已注册账号
     * @param user
     * @return
     */
    @Select("select count(1) from pa_viver_lost_reg where is_delete = 'N' and account = #{account} ")
    int findUserIsRegisterCount(UserInfoPO user);

    /**
     * 添加用户注册信息
     * @param user
     */
    void addRegisterInfo(UserInfoPO user);

    @Select("select count(1) from pa_viver_lost_reg where is_delete = 'N' and account = #{account} ")
    int findUserIsRegister(String account);

    @Select("SELECT COUNT(1) FROM pa_viver_lost_reg WHERE IS_DELETE = 'N' AND ACCOUNT = #{account} ")
    int findUserAccountInUse(String account);

    @Select("SELECT count(1) FROM Pa_Viver_Lost_Reg WHERE IS_DELETE = 'N' AND ACCOUNT = #{account} and password = #{password} ")
    int findUserAccountAndPassword(@Param("account") String account,
                                   @Param("password") String password);
}
