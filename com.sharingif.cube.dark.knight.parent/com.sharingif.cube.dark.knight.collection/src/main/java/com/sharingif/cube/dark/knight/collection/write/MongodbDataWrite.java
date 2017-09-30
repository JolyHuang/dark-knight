package com.sharingif.cube.dark.knight.collection.write;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sharingif.cube.core.util.DateUtils;
import com.sharingif.cube.dark.knight.collection.handler.TransactionType;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 写入mongodb数据库
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/26 下午4:36
 */
@Component
public class MongodbDataWrite implements DataWrite, InitializingBean {

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

    protected Document convertLinkedHashMapToLinkedHashMap(LinkedHashMap<String, Object> data) {
        Document doc = new Document();
        for(Map.Entry<String, Object> entry : data.entrySet()) {
            doc.append(entry.getKey(), entry.getValue());
        }
        return doc;
    }

    protected void writeServerStarStop(LinkedHashMap<String, Object> data) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("SERVER_STAR_STOP");
        collection.insertOne(convertLinkedHashMapToLinkedHashMap(data));
    }

    protected void writeTransaction(LinkedHashMap<String, Object> data) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("TRANSACTION");
        collection.insertOne(convertLinkedHashMapToLinkedHashMap(data));
    }

    @Override
    public void write(LinkedHashMap<String, Object> data) {

        String startTime = (String)data.get("startTime");
        try {
            data.put("startTime", DateUtils.getDate(startTime, DateUtils.DATETIME_MILLI_ISO_FORMAT));
        } catch (ParseException e) {
            logger.error("parse date error", e);
        }

        String transType = (String)data.get(TransactionType.TRANS_TYPE.toString());
        if(transType.equals(TransactionType.SERVER_START.toString())
                || transType.equals(TransactionType.SERVER_STOP.toString())
                ) {
            writeServerStarStop(data);

            return;
        }

        writeTransaction(data);

    }

    protected MongoCollection<Document> getCollection(String collectionName) {
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
