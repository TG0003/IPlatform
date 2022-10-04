package com.myapp.commcode2api.vo.order;

import java.math.BigDecimal;
import java.util.Date;

public class GetOrderVO {
    private String no;
    private BigDecimal payNum;
    private Date createTime;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public BigDecimal getPayNum() {
        return payNum;
    }

    public void setPayNum(BigDecimal payNum) {
        this.payNum = payNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "GetOrderVO{" +
                "no='" + no + '\'' +
                ", payNum=" + payNum +
                ", createTime=" + createTime +
                '}';
    }
}
