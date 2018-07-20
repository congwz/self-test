package com.viverselftest.dto;

import lombok.Data;

/**
 * Created by Congwz on 2018/7/20.
 */
@Data
public class TestNullOrEmptyDTO {

    //部门编号
    private String partno;

    //部门名称
    private String partname;

    //用户编号
    private String userno;

    /*public String getPartno() {
        return partno;
    }

    public void setPartno(String partno) {
        this.partno = partno;
    }

    public String getPartname() {
        return partname;
    }

    public void setPartname(String partname) {
        this.partname = partname;
    }

    public String getUserno() {
        return userno == null ? "" : userno;
    }

    public void setUserno(String userno) {
        //this.userno = userno == null ? "" : userno ;
        this.userno = userno;
    }*/

    /*@Override
    public String toString() {
        return "TestNullOrEmptyDTO{" +
                "partno='" + partno + '\'' +
                ", partname='" + partname + '\'' +
                ", userno='" + userno + '\'' +
                '}';
    }*/
}
