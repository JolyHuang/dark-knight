package com.sharingif.cube.dark.knight.analysis.transaction.model.entity;

import java.util.Date;

/**
 * 交易按时间统计
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/12 下午4:01
 */
public class TransactionDateTimeStatistics {

    private int hour;
    private int count;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionDateTimeStatistics{");
        sb.append("hour=").append(hour);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
