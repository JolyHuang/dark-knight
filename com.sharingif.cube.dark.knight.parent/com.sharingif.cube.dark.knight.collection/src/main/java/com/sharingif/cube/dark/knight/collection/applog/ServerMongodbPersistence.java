package com.sharingif.cube.dark.knight.collection.applog;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sharingif.cube.dark.knight.collection.DarkKnightCollectionApplicationContext;
import com.sharingif.cube.dark.knight.collection.persistence.MongodbPersistence;
import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;

/**
 * ServerMongodbPersistence
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/16 下午8:18
 */
public class ServerMongodbPersistence implements InitializingBean {

    private MongodbPersistence mongodbPersistence;

    @Resource
    public void setMongodbPersistence(MongodbPersistence mongodbPersistence) {
        this.mongodbPersistence = mongodbPersistence;
    }

    public void start() {
        MongoCollection<Document> collection = mongodbPersistence.getCollection("SERVER");

        Document document = collection.find(Filters.eq(DarkKnightCollectionApplicationContext.SERVER_IP_KEY, DarkKnightCollectionApplicationContext.SERVER_IP)).first();

        if(null != document) {
            return;
        }

        Document insertDocument = new Document();
        insertDocument.append(DarkKnightCollectionApplicationContext.SERVER_IP_KEY, DarkKnightCollectionApplicationContext.SERVER_IP);
        collection.insertOne(insertDocument);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }
}
