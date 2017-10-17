package com.sharingif.cube.dark.knight.analysis.systemserver.dao.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Sorts;
import com.sharingif.cube.dark.knight.analysis.app.dao.impl.CubeMongoDBDAOImpl;
import com.sharingif.cube.dark.knight.analysis.systemserver.dao.ServerAppDAO;
import com.sharingif.cube.dark.knight.analysis.systemserver.model.entity.ServerApp;
import org.bson.Document;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * ServerAppDAOImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/17 下午12:06
 */
@Repository
public class ServerAppDAOImpl extends CubeMongoDBDAOImpl implements ServerAppDAO {

    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoDatabase().getCollection("SERVER_APP");
    }

    @Override
    public List<Document> queryList() {

        MongoCursor<Document> cursor = getCollection().find().sort(Sorts.orderBy(Sorts.ascending(ServerApp.SERVER_IP))).iterator();

        List<Document> documentList = new LinkedList<Document>();
        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                documentList.add(document);
            }
        } finally {
            cursor.close();
        }

        return documentList;
    }
}
