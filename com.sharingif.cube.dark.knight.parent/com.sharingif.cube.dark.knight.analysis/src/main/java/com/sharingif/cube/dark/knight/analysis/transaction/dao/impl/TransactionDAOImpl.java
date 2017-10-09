package com.sharingif.cube.dark.knight.analysis.transaction.dao.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.dark.knight.analysis.app.dao.impl.CubeMongoDBDAOImpl;
import com.sharingif.cube.dark.knight.analysis.transaction.dao.TransactionDAO;
import com.sharingif.cube.dark.knight.analysis.transaction.model.entity.Transaction;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

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
        return getMongoDatabase().getCollection("TRANSACTION");
    }

    @Override
    public List<Document> queryList(Transaction transaction) {

        BasicDBObject filter = new BasicDBObject();

        if(!StringUtils.isTrimEmpty(transaction.getTransType())) {
            filter.put(Transaction.TRANS_TYPE_KEY, transaction.getTransType());
        }
        BasicDBObject startTime = new BasicDBObject();
        if(transaction.getStartTimeBegin() != null) {
            filter.put(Transaction.STARTTIME_KEY, startTime.append("$gte", transaction.getStartTimeBegin()));

            if(transaction.getStartTimeEnd() != null) {
                startTime.append("$lt", transaction.getStartTimeEnd());
            }
        }
        if((transaction.getStartTimeBegin() == null) && (transaction.getStartTimeEnd() != null)) {
            filter.put(Transaction.STARTTIME_KEY, startTime.append("$lt", transaction.getStartTimeEnd()));
        }
        if(!StringUtils.isTrimEmpty(transaction.getTransId())) {
            filter.put(Transaction.TRANS_ID_KEY, transaction.getTransId());
        }
        if(!StringUtils.isTrimEmpty(transaction.getMessage())) {
            filter.put(Transaction.MESSAGE_KEY, transaction.getMessage());
        }

        List<Document> documentList = new LinkedList<Document>();
        MongoCursor<Document> cursor = getCollection().find(filter).iterator();
        try {
            while (cursor.hasNext()) {
                documentList.add(cursor.next());
            }
        } finally {
            cursor.close();
        }

        return documentList;
    }

}
