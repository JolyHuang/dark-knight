package com.sharingif.cube.dark.knight.analysis.systemserver.dao;

import org.bson.Document;

import java.util.List;

/**
 * ServerAppDAO
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/17 下午12:03
 */
public interface ServerAppDAO {

    List<Document> queryList();

}
