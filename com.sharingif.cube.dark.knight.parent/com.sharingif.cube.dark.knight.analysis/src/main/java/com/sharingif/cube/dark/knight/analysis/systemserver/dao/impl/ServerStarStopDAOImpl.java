package com.sharingif.cube.dark.knight.analysis.systemserver.dao.impl;

import com.mongodb.client.MongoCollection;
import com.sharingif.cube.dark.knight.analysis.app.dao.impl.CubeMongoDBDAOImpl;
import com.sharingif.cube.dark.knight.analysis.systemserver.dao.ServerStarStopDAO;
import org.bson.Document;

/**
 * 系统启停
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/11 下午7:56
 */
public class ServerStarStopDAOImpl extends CubeMongoDBDAOImpl implements ServerStarStopDAO {

    @Override
    protected MongoCollection<Document> getCollection() {
        return getMongoDatabase().getCollection("SERVER_STAR_STOP");
    }

}
