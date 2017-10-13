package com.sharingif.cube.dark.knight.analysis.transaction.model.entity;

/**
 * 交易分组统计
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/13 下午8:27
 */
public class TransactionStatistics {

    private String transId;
    private int count;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionStatistics{");
        sb.append("transId='").append(transId).append('\'');
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
