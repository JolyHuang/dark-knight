package com.sharingif.cube.dark.knight.analysis.transaction.model.entity;

/**
 * 交易平均执行时间
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/1 上午11:32
 */
public class TransactionAvgExcuteTime {

    private String transId;
    private int avgExcuteTime;

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public int getAvgExcuteTime() {
        return avgExcuteTime;
    }

    public void setAvgExcuteTime(int avgExcuteTime) {
        this.avgExcuteTime = avgExcuteTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionAvgExcuteTime{");
        sb.append("transId='").append(transId).append('\'');
        sb.append(", avgExcuteTime=").append(avgExcuteTime);
        sb.append('}');
        return sb.toString();
    }
}
