package com.sharingif.cube.dark.knight.analysis.transaction.service.impl;

import com.sharingif.cube.dark.knight.analysis.transaction.dao.TransactionDAO;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.Transaction;
import com.sharingif.cube.dark.knight.analysis.transaction.service.TransactionService;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public List<Document> getList(Transaction transaction) {
        transaction.setTransType(Transaction.TRANSACTION_BEGIN);
        return transactionDAO.queryList(transaction);
    }

}
