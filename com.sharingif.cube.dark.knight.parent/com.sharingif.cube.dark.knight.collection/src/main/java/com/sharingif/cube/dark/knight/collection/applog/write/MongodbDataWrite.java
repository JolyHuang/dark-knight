package com.sharingif.cube.dark.knight.collection.applog.write;

import com.mongodb.client.MongoCollection;
import com.sharingif.cube.core.util.DateUtils;
import com.sharingif.cube.dark.knight.collection.applog.handler.TransactionType;
import com.sharingif.cube.dark.knight.collection.file.write.DataWrite;
import com.sharingif.cube.dark.knight.collection.persistence.MongodbPersistence;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
public class MongodbDataWrite implements DataWrite {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private MongodbPersistence mongodbPersistence;

    @Resource
    public void setMongodbPersistence(MongodbPersistence mongodbPersistence) {
        this.mongodbPersistence = mongodbPersistence;
    }

    protected Document convertLinkedHashMapToLinkedHashMap(LinkedHashMap<String, Object> data) {
        Document doc = new Document();
        for(Map.Entry<String, Object> entry : data.entrySet()) {
            doc.append(entry.getKey(), entry.getValue());
        }
        return doc;
    }

    protected void writeServerStarStop(LinkedHashMap<String, Object> data) {
        MongoCollection<Document> collection = mongodbPersistence.getCollection("ServerStarStop");
        collection.insertOne(convertLinkedHashMapToLinkedHashMap(data));
    }

    protected void writeTransaction(LinkedHashMap<String, Object> data) {
        MongoCollection<Document> collection = mongodbPersistence.getCollection("Transaction");
        collection.insertOne(convertLinkedHashMapToLinkedHashMap(data));
    }

    @Override
    public void write(LinkedHashMap<String, Object> data) {

        String startTime = (String)data.get("startTime");
        try {
            data.put("startTime", DateUtils.getDate(startTime, "yyyy-MM-dd HH:mm:ss.SSS"));
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

}
