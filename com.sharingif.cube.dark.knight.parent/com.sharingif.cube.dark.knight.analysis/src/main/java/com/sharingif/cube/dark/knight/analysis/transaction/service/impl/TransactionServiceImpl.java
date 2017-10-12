package com.sharingif.cube.dark.knight.analysis.transaction.service.impl;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.DateUtils;
import com.sharingif.cube.dark.knight.analysis.transaction.dao.TransactionDAO;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.Transaction;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionDateTimeStatistics;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionDay;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionVolumeDay;
import com.sharingif.cube.dark.knight.analysis.transaction.service.TransactionService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 查询交易信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/29 下午5:51
 */
@Service
public class TransactionServiceImpl implements TransactionService {

    private TransactionDAO transactionDAO;

    @Resource
    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public PaginationRepertory<Document> getList(PaginationCondition<Transaction> paginationCondition) {

        return transactionDAO.queryList(paginationCondition);
    }

    @Override
    public List<Document> getDetailsByTransUniqueId(String transUniqueId) {

        return transactionDAO.queryList(transUniqueId);
    }

    @Override
    public TransactionVolumeDay getTransactionVolumeDay() {


        Date currentDate = new Date();
        String currentDateString = DateUtils.getDate(currentDate, DateUtils.DATE_ISO_FORMAT);
        Date currentDateBegin = null;
        Date currentDateEnd = null;
        try {
            currentDateBegin = DateUtils.getDate(currentDateString+" 00:00:00", DateUtils.DATETIME_ISO_FORMAT);
            currentDateEnd = DateUtils.getDate(currentDateString+" 23:59:59", DateUtils.DATETIME_ISO_FORMAT);
        } catch (ParseException e) {
            throw new CubeRuntimeException("data fromart error", e);
        }

        Transaction transactionTotal = new Transaction();
        transactionTotal.setStartTimeBegin(currentDateBegin);
        transactionTotal.setStartTimeEnd(currentDateEnd);
        transactionTotal.setTransType(Transaction.TRANSACTION_BEGIN);
        long total = transactionDAO.queryCount(transactionTotal);

        Transaction transactionError = new Transaction();
        transactionError.setStartTimeBegin(currentDateBegin);
        transactionError.setStartTimeEnd(currentDateEnd);
        transactionError.setTransType(Transaction.TRANSACTION_ERROR);
        long error = transactionDAO.queryCount(transactionError);

        TransactionVolumeDay transactionVolumeDay = new TransactionVolumeDay();
        transactionVolumeDay.setDate(currentDate);
        transactionVolumeDay.setTotal(total);
        transactionVolumeDay.setSuccess(total - error);
        transactionVolumeDay.setFail(error);

        return transactionVolumeDay;
    }

    @Override
    public TransactionDay getTransactionDay() {

        Date currentDate = new Date();
        String currentDateString = DateUtils.getDate(currentDate, DateUtils.DATE_ISO_FORMAT);
        Date currentDateBegin = null;
        Date currentDateEnd = null;
        try {
            currentDateBegin = DateUtils.getDate(currentDateString+" 00:00:00", DateUtils.DATETIME_ISO_FORMAT);
            currentDateEnd = DateUtils.getDate(currentDateString+" 23:59:59", DateUtils.DATETIME_ISO_FORMAT);
        } catch (ParseException e) {
            throw new CubeRuntimeException("data fromart error", e);
        }

        Transaction registerTransaction = new Transaction();
        registerTransaction.setStartTimeBegin(currentDateBegin);
        registerTransaction.setStartTimeEnd(currentDateEnd);
        registerTransaction.setTransId(Transaction.REGISTER);
        long register = transactionDAO.queryCount(registerTransaction);

        Transaction rechargeSubmitTransaction = new Transaction();
        rechargeSubmitTransaction.setStartTimeBegin(currentDateBegin);
        rechargeSubmitTransaction.setStartTimeEnd(currentDateEnd);
        rechargeSubmitTransaction.setTransId(Transaction.RECHARGE_SUBMIT);
        long rechargeSubmit = transactionDAO.queryCount(rechargeSubmitTransaction);

        Transaction withdrawSubmitTransaction = new Transaction();
        withdrawSubmitTransaction.setStartTimeBegin(currentDateBegin);
        withdrawSubmitTransaction.setStartTimeEnd(currentDateEnd);
        withdrawSubmitTransaction.setTransId(Transaction.WITHDRAW_SUBMIT);
        long withdrawSubmit = transactionDAO.queryCount(withdrawSubmitTransaction);

        Transaction loanApplyTransaction = new Transaction();
        loanApplyTransaction.setStartTimeBegin(currentDateBegin);
        loanApplyTransaction.setStartTimeEnd(currentDateEnd);
        loanApplyTransaction.setTransId(Transaction.LOAN_APPLY);
        long loanApply = transactionDAO.queryCount(loanApplyTransaction);

        TransactionDay transactionDay = new TransactionDay();
        transactionDay.setDate(currentDate);
        transactionDay.setRegister(register);
        transactionDay.setRechargeSubmit(rechargeSubmit);
        transactionDay.setWithdrawSubmit(withdrawSubmit);
        transactionDay.setLoanApply(loanApply);

        return transactionDay;
    }

    @Override
    public List<TransactionDateTimeStatistics> statisticsByDayHour() {

        try {
            Date currentDate = DateUtils.getDate(DateUtils.getCurrentDate(DateUtils.DATE_ISO_FORMAT), DateUtils.DATE_ISO_FORMAT);

            Transaction transaction = new Transaction();
            transaction.setStartTimeBegin(currentDate);
            transaction.setTransType(Transaction.TRANSACTION_BEGIN);

            return transactionDAO.statisticsByDayHour(transaction);
        } catch (ParseException e) {
            throw new CubeRuntimeException("data fromart error", e);
        }

    }

}
