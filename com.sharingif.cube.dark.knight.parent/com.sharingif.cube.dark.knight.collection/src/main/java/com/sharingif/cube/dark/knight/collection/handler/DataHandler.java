package com.sharingif.cube.dark.knight.collection.handler;

import java.io.BufferedReader;
import java.util.LinkedHashMap;

/**
 * 数据加工
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午2:07
 */
public interface DataHandler {

    /**
     * 数据是否匹配，不匹配的数据过滤掉
     * @param data
     * @return
     */
    boolean isMatch(String data);

    /**
     * 数据加工
     * @param data
     * @param bufferedReader
     * @return
     */
    LinkedHashMap<String, Object> handle(String data, BufferedReader bufferedReader);

}
