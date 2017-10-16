package com.sharingif.cube.dark.knight.collection.applog;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.sharingif.cube.dark.knight.collection.DarkKnightCollectionApplicationContext;
import com.sharingif.cube.dark.knight.collection.persistence.MongodbPersistence;
import org.bson.Document;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

        MongoCollection<Document> collection = mongodbPersistence.getCollection("SERVER_APP");

        Document document = collection.find(Filters.eq("serverIp", DarkKnightCollectionApplicationContext.IP)).first();
        if(document == null) {

            List<Document> serverList = new ArrayList<Document>();
            serverList.add(new Document("serverName", DarkKnightCollectionApplicationContext.SERVER_NAME));

            Document insertDocument = new Document();
            insertDocument.append("serverIp", DarkKnightCollectionApplicationContext.IP);
            insertDocument.append("serverList", serverList);
            collection.insertOne(insertDocument);

            return;
        }

        List<Document> documentList = document.get("serverList", List.class);
        for(Document doc : documentList) {
            String serverName = doc.getString("serverName");
            if(serverName.equals(DarkKnightCollectionApplicationContext.SERVER_NAME)) {
                return;
            }
        }

        documentList.add(new Document("serverName", DarkKnightCollectionApplicationContext.SERVER_NAME));
        collection.updateOne(
                Filters.eq("serverIp", DarkKnightCollectionApplicationContext.IP),
                new Document("$set", new Document("serverList", documentList))
        );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

}
