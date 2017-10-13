package com.sharingif.cube.dark.knight.analysis.transaction.service;

import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.*;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.bson.Document;

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
public interface TransactionService {

    PaginationRepertory<Document> getList(PaginationCondition<Transaction> paginationCondition);

    List<Document> getDetailsByTransUniqueId(String transUniqueId);

    TransactionVolumeDay getTransactionVolumeDay();

    TransactionDay getTransactionDay();

    List<TransactionDateTimeStatistics> statisticsByDayHour();

    List<TransactionStatistics> statisticsByTransId();

}
