package com.sharingif.cube.dark.knight.analysis.transaction.dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.dark.knight.analysis.app.dao.impl.CubeMongoDBDAOImpl;
import com.sharingif.cube.dark.knight.analysis.transaction.dao.TransactionDAO;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.Transaction;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionAvgExcuteTime;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionDateTimeStatistics;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.TransactionStatistics;
import com.sharingif.cube.persistence.database.pagination.PaginationCondition;
import com.sharingif.cube.persistence.database.pagination.PaginationRepertory;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 查询交易信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/29 下午5:32
 */
@Repository
public class TransactionDAOImpl extends CubeMongoDBDAOImpl implements TransactionDAO {


    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoDatabase().getCollection("Transaction");
    }

    @Override
    public PaginationRepertory<Document> queryList(PaginationCondition<Transaction> paginationCondition) {

        Transaction transaction = paginationCondition.getCondition();

        BasicDBObject filter = new BasicDBObject();

        if(!StringUtils.isTrimEmpty(transaction.getTransType())) {
            filter.put(Transaction.TRANS_TYPE_KEY, transaction.getTransType());
        }
        BasicDBObject startTime = new BasicDBObject();
        if(transaction.getStartTimeBegin() != null) {
            filter.put(Transaction.START_TIME_KEY, startTime.append("$gte", transaction.getStartTimeBegin()));

            if(transaction.getStartTimeEnd() != null) {
                startTime.append("$lt", transaction.getStartTimeEnd());
            }
        }
        if((transaction.getStartTimeBegin() == null) && (transaction.getStartTimeEnd() != null)) {
            filter.put(Transaction.START_TIME_KEY, startTime.append("$lt", transaction.getStartTimeEnd()));
        }
        if(!StringUtils.isTrimEmpty(transaction.getTransId())) {
            filter.put(Transaction.TRANS_ID_KEY, transaction.getTransId());
        }
        if(!StringUtils.isTrimEmpty(transaction.getMessage())) {
            filter.put(Transaction.MESSAGE_KEY, transaction.getMessage());
        }
        if(!StringUtils.isTrimEmpty(transaction.getUserId())) {
            filter.put(Transaction.USER_ID_KEY, transaction.getUserId());
        }
        if(!StringUtils.isTrimEmpty(transaction.getTransUniqueId())) {
            filter.put(Transaction.TRANS_UNIQUE_ID_KEY, transaction.getTransUniqueId());
        }

        long totalCount = getCollection().count(filter);

        if(totalCount == 0) {
            return  new PaginationRepertory<Document>(0, null, paginationCondition);
        }

        List<Document> documentList = new LinkedList<Document>();
        MongoCursor<Document> cursor = getCollection().find(filter).sort(new BasicDBObject(Transaction.START_TIME_KEY,-1)).skip((paginationCondition.getCurrentPage()-1)*paginationCondition.getPageSize()).limit(paginationCondition.getPageSize()).iterator();
        try {
            while (cursor.hasNext()) {
                documentList.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        PaginationRepertory<Document> paginationRepertory = new PaginationRepertory<Document>(new Long(totalCount).intValue(), documentList, paginationCondition);

        return paginationRepertory;
    }

    @Override
    public List<Document> queryList(Transaction transaction) {

        BasicDBObject filter = new BasicDBObject();

        BasicDBObject startTime = new BasicDBObject();
        filter.put(Transaction.START_TIME_KEY, startTime.append("$gte", transaction.getStartTimeBegin()));
        startTime.append("$lt", transaction.getStartTimeEnd());

        filter.put(Transaction.TRANS_UNIQUE_ID_KEY, transaction.getTransUniqueId());

        List<Document> documentList = new LinkedList<Document>();
        MongoCursor<Document> cursor = getCollection().find(filter).sort(Sorts.ascending(Transaction.START_TIME_KEY)).iterator();
        try {
            while (cursor.hasNext()) {
                documentList.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return documentList;
    }

    @Override
    public Long queryCount(Transaction transaction) {

        BasicDBObject filter = new BasicDBObject();

        if(!StringUtils.isTrimEmpty(transaction.getTransType())) {
            filter.put(Transaction.TRANS_TYPE_KEY, transaction.getTransType());
        }

        if(!StringUtils.isTrimEmpty(transaction.getTransId())) {
            filter.put(Transaction.TRANS_ID_KEY, transaction.getTransId());
        }

        BasicDBObject startTime = new BasicDBObject();
        if(transaction.getStartTimeBegin() != null) {
            filter.put(Transaction.START_TIME_KEY, startTime.append("$gte", transaction.getStartTimeBegin()));

            if(transaction.getStartTimeEnd() != null) {
                startTime.append("$lt", transaction.getStartTimeEnd());
            }
        }
        if((transaction.getStartTimeBegin() == null) && (transaction.getStartTimeEnd() != null)) {
            filter.put(Transaction.START_TIME_KEY, startTime.append("$lt", transaction.getStartTimeEnd()));
        }

        return getCollection().count(filter);
    }

    @Override
    public List<TransactionDateTimeStatistics> statisticsByDayHour(Transaction transaction) {

        MongoCursor<Document> cursor = getCollection().aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.and(
                                Filters.gte(Transaction.START_TIME_KEY, transaction.getStartTimeBegin())
                                ,Filters.eq(Transaction.TRANS_TYPE_KEY, transaction.getTransType())
                        )),
                        Aggregates.project(Projections.computed("hour", new BasicDBObject("$hour", new BasicDBObject("$add",new Object[]{"$"+Transaction.START_TIME_KEY, 28800000})))),
                        Aggregates.group("$hour",  Accumulators.sum("count", 1)),
                        Aggregates.sort(Sorts.orderBy(Sorts.ascending("_id")))
                )
        ).iterator();
        List<TransactionDateTimeStatistics> documentList = new LinkedList<TransactionDateTimeStatistics>();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                TransactionDateTimeStatistics transactionDateTimeStatistics = new TransactionDateTimeStatistics();
                transactionDateTimeStatistics.setHour(document.getInteger("_id"));
                transactionDateTimeStatistics.setCount(document.getInteger("count"));

                documentList.add(transactionDateTimeStatistics);
            }
        } finally {
            cursor.close();
        }

        return documentList;
    }

    @Override
    public List<TransactionStatistics> statisticsByTransId(Transaction transaction) {
        MongoCursor<Document> cursor = getCollection().aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.and(
                                Filters.gte(Transaction.START_TIME_KEY, transaction.getStartTimeBegin())
                                ,Filters.eq(Transaction.TRANS_TYPE_KEY, transaction.getTransType())
                        )),
                        Aggregates.group("$"+Transaction.TRANS_ID_KEY,  Accumulators.sum("count", 1)),
                        Aggregates.sort(Sorts.orderBy(Sorts.descending("count"))),
                        Aggregates.limit(30)
                )
        ).iterator();
        List<TransactionStatistics> documentList = new LinkedList<TransactionStatistics>();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                TransactionStatistics transactionDateTimeStatistics = new TransactionStatistics();
                transactionDateTimeStatistics.setTransId(document.getString("_id"));
                transactionDateTimeStatistics.setCount(document.getInteger("count"));

                documentList.add(transactionDateTimeStatistics);
            }
        } finally {
            cursor.close();
        }

        return documentList;
    }

    @Override
    public List<TransactionAvgExcuteTime> avgExcuteTime(Transaction transaction) {


        MongoCursor<Document> cursor = getCollection().aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.and(
                                Filters.gte(Transaction.START_TIME_KEY, transaction.getStartTimeBegin())
                                ,Filters.eq(Transaction.TRANS_TYPE_KEY, transaction.getTransType())
                        )),
                        Aggregates.group("$"+Transaction.TRANS_ID_KEY,  Accumulators.avg("avgExcuteTime", "$"+Transaction.TRANS_EXCUTE_TIME)),
                        Aggregates.sort(Sorts.orderBy(Sorts.descending("avgExcuteTime"))),
                        Aggregates.limit(30)
                )
        ).iterator();
        List<TransactionAvgExcuteTime> documentList = new LinkedList<TransactionAvgExcuteTime>();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                TransactionAvgExcuteTime transactionAvgExcuteTime = new TransactionAvgExcuteTime();
                transactionAvgExcuteTime.setTransId(document.getString("_id"));
                transactionAvgExcuteTime.setAvgExcuteTime(document.getDouble("avgExcuteTime").intValue());

                documentList.add(transactionAvgExcuteTime);
            }
        } finally {
            cursor.close();
        }

        return documentList;

    }

}
