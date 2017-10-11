package com.sharingif.cube.dark.knight.analysis.transaction.model.entity;

import java.util.Date;

/**
 * 交易量
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/11 下午1:41
 */
public class TransactionVolumeDay {

    private Date date;
    private long total;
    private long success;
    private long fail;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSuccess() {
        return success;
    }

    public void setSuccess(long success) {
        this.success = success;
    }

    public long getFail() {
        return fail;
    }

    public void setFail(long fail) {
        this.fail = fail;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionVolumeDay{");
        sb.append("date=").append(date);
        sb.append(", total=").append(total);
        sb.append(", success=").append(success);
        sb.append(", fail=").append(fail);
        sb.append('}');
        return sb.toString();
    }
}
