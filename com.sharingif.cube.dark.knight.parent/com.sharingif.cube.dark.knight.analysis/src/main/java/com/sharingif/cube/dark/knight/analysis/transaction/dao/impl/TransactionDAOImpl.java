package com.sharingif.cube.dark.knight.analysis.transaction.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.sharingif.cube.dark.knight.analysis.app.dao.impl.CubeMongoDBDAOImpl;
import com.sharingif.cube.dark.knight.analysis.transaction.dao.TransactionDAO;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

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
    public List<Document> queryList() {
        List<Document> documentList = new LinkedList<Document>();
        MongoCursor<Document> cursor = getCollection().find().iterator();
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
