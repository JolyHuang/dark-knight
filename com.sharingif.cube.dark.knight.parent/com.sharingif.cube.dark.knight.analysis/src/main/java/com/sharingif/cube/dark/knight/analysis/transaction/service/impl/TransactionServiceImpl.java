package com.sharingif.cube.dark.knight.analysis.transaction.service.impl;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.DateUtils;
import com.sharingif.cube.dark.knight.analysis.transaction.dao.TransactionDAO;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.*;
import com.sharingif.cube.dark.knight.analysis.transaction.service.TransactionService;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
        transactionTotal.setTransType(Transaction.CONTROLLER_BEGIN);
        long total = transactionDAO.queryCount(transactionTotal);

        Transaction transactionError = new Transaction();
        transactionError.setStartTimeBegin(currentDateBegin);
        transactionError.setStartTimeEnd(currentDateEnd);
        transactionError.setTransType(Transaction.CONTROLLER_ERROR);
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
        registerTransaction.setTransType(Transaction.CONTROLLER_BEGIN);
        long register = transactionDAO.queryCount(registerTransaction);

        Transaction rechargeSubmitTransaction = new Transaction();
        rechargeSubmitTransaction.setStartTimeBegin(currentDateBegin);
        rechargeSubmitTransaction.setStartTimeEnd(currentDateEnd);
        rechargeSubmitTransaction.setTransId(Transaction.RECHARGE_SUBMIT);
        rechargeSubmitTransaction.setTransType(Transaction.CONTROLLER_BEGIN);
        long rechargeSubmit = transactionDAO.queryCount(rechargeSubmitTransaction);

        Transaction withdrawSubmitTransaction = new Transaction();
        withdrawSubmitTransaction.setStartTimeBegin(currentDateBegin);
        withdrawSubmitTransaction.setStartTimeEnd(currentDateEnd);
        withdrawSubmitTransaction.setTransId(Transaction.WITHDRAW_SUBMIT);
        withdrawSubmitTransaction.setTransType(Transaction.CONTROLLER_BEGIN);
        long withdrawSubmit = transactionDAO.queryCount(withdrawSubmitTransaction);

        Transaction loanApplyTransaction = new Transaction();
        loanApplyTransaction.setStartTimeBegin(currentDateBegin);
        loanApplyTransaction.setStartTimeEnd(currentDateEnd);
        loanApplyTransaction.setTransId(Transaction.LOAN_APPLY);
        loanApplyTransaction.setTransType(Transaction.CONTROLLER_BEGIN);
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

        Date currentDate = null;
        try {
            currentDate = DateUtils.getDate(DateUtils.getCurrentDate(DateUtils.DATE_ISO_FORMAT), DateUtils.DATE_ISO_FORMAT);
        } catch (ParseException e) {
            throw new CubeRuntimeException("data fromart error", e);
        }
        Transaction transaction = new Transaction();
        transaction.setStartTimeBegin(currentDate);
        transaction.setTransType(Transaction.CONTROLLER_BEGIN);

        List<TransactionDateTimeStatistics> transactionDateTimeStatisticsList = transactionDAO.statisticsByDayHour(transaction);
        if(transactionDateTimeStatisticsList.size()<24) {
            List<TransactionDateTimeStatistics> fullTransactionDateTimeStatisticsList = new ArrayList<TransactionDateTimeStatistics>(24);
            for(int i = 0; i<24; i++) {
                TransactionDateTimeStatistics transactionDateTimeStatistics = new TransactionDateTimeStatistics();
                transactionDateTimeStatistics.setHour(i);
                transactionDateTimeStatistics.setCount(0);
                fullTransactionDateTimeStatisticsList.add(transactionDateTimeStatistics);
            }

            for(TransactionDateTimeStatistics transactionDateTimeStatistics : transactionDateTimeStatisticsList) {
                fullTransactionDateTimeStatisticsList.get(transactionDateTimeStatistics.getHour()).setCount(transactionDateTimeStatistics.getCount());
            }

            return fullTransactionDateTimeStatisticsList;
        }

        return transactionDateTimeStatisticsList;

    }

    @Override
    public List<TransactionStatistics> statisticsByTransId() {

        Date currentDate = null;
        try {
            currentDate = DateUtils.getDate(DateUtils.getCurrentDate(DateUtils.DATE_ISO_FORMAT), DateUtils.DATE_ISO_FORMAT);
        } catch (ParseException e) {
            throw new CubeRuntimeException("data fromart error", e);
        }
        Transaction transaction = new Transaction();
        transaction.setStartTimeBegin(currentDate);
        transaction.setTransType(Transaction.CONTROLLER_BEGIN);

        return transactionDAO.statisticsByTransId(transaction);
    }

    @Override
    public List<TransactionAvgExcuteTime> avgExcuteTime() {

        Transaction transaction = new Transaction();
        transaction.setStartTimeBegin(DateUtils.add(new Date(), Calendar.HOUR_OF_DAY, -1));
        transaction.setTransType(Transaction.TRANSACTION_END);


        return transactionDAO.avgExcuteTime(transaction);
    }

}
