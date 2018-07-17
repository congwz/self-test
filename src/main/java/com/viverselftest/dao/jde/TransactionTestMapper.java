package com.viverselftest.dao.jde;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

/**
 * Created by Congwz on 2018/7/17.
 */
@Repository
public interface TransactionTestMapper {

    @Insert("insert into PA_VIVER_TEST_USER(userno,username) values ('000000',#{name})")
    void add(String name);

    @Delete("delete from PA_VIVER_TEST_USER where userno = #{delCode}")
    void del(String delCode);
}
