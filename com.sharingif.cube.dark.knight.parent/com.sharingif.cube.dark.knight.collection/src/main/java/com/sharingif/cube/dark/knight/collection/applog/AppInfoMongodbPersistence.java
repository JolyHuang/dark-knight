package com.sharingif.cube.dark.knight.collection.applog;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sharingif.cube.dark.knight.collection.DarkKnightCollectionApplicationContext;
import com.sharingif.cube.dark.knight.collection.persistence.MongodbPersistence;
import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * AppInfoMongodbPersistence
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/16 下午7:08
 */
@Component
public class AppInfoMongodbPersistence implements InitializingBean {

    private MongodbPersistence mongodbPersistence;

    @Resource
    public void setMongodbPersistence(MongodbPersistence mongodbPersistence) {
        this.mongodbPersistence = mongodbPersistence;
    }

    public void start() {

        MongoCollection<Document> collection = mongodbPersistence.getCollection("ServerApp");

        Document document = collection.find(
                Filters.and(
                        Filters.eq(DarkKnightCollectionApplicationContext.SERVER_IP_KEY, DarkKnightCollectionApplicationContext.SERVER_IP),
                        Filters.eq(DarkKnightCollectionApplicationContext.SERVER_NAME_KEY, DarkKnightCollectionApplicationContext.SERVER_NAME)
                )
        ).first();

        if(document != null) {
            return;
        }

        Document insertDocument = new Document();
        insertDocument.append(DarkKnightCollectionApplicationContext.SERVER_IP_KEY, DarkKnightCollectionApplicationContext.SERVER_IP);
        insertDocument.append(DarkKnightCollectionApplicationContext.SERVER_NAME_KEY, DarkKnightCollectionApplicationContext.SERVER_NAME);
        collection.insertOne(insertDocument);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

}
