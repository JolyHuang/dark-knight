package com.sharingif.cube.dark.knight.analysis.transaction.model.entity;

import java.util.Date;

/**
 * 交易日统计
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/11 下午2:55
 */
public class TransactionDay {

    private Date date;
    private long register;
    private long rechargeSubmit;
    private long withdrawSubmit;
    private long loanApply;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getRegister() {
        return register;
    }

    public void setRegister(long register) {
        this.register = register;
    }

    public long getRechargeSubmit() {
        return rechargeSubmit;
    }

    public void setRechargeSubmit(long rechargeSubmit) {
        this.rechargeSubmit = rechargeSubmit;
    }

    public long getWithdrawSubmit() {
        return withdrawSubmit;
    }

    public void setWithdrawSubmit(long withdrawSubmit) {
        this.withdrawSubmit = withdrawSubmit;
    }

    public long getLoanApply() {
        return loanApply;
    }

    public void setLoanApply(long loanApply) {
        this.loanApply = loanApply;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TransactionDay{");
        sb.append("date=").append(date);
        sb.append(", register=").append(register);
        sb.append(", rechargeSubmit=").append(rechargeSubmit);
        sb.append(", withdrawSubmit=").append(withdrawSubmit);
        sb.append(", loanApply=").append(loanApply);
        sb.append('}');
        return sb.toString();
    }
}
