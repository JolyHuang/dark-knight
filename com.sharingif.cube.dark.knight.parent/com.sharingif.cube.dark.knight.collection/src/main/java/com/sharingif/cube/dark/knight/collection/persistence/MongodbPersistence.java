package com.sharingif.cube.dark.knight.collection.persistence;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * mongodb保存数据
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/16 下午4:57
 */
@Component
public class MongodbPersistence implements InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String host;
    private int port;
    private String databaseName;
    private MongoDatabase mongoDatabase;

    @Value("${dataSource.mongodb.host}")
    public void setHost(String host) {
        this.host = host;
    }
    @Value("${dataSource.mongodb.port}")
    public void setPort(int port) {
        this.port = port;
    }
    @Value("${dataSource.mongodb.databaseName}")
    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public MongoCollection<Document> getCollection(String collectionName) {
        return mongoDatabase.getCollection(collectionName);
    }

    protected void initMongoDB() {
        MongoClient mongoClient = new MongoClient(host , port);
        mongoDatabase = mongoClient.getDatabase(databaseName);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initMongoDB();
    }

}
