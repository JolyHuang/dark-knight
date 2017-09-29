package com.sharingif.cube.dark.knight.collection.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提供公共方法
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午7:09
 */
public abstract class AbstractDataHandler implements DataHandler {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    abstract protected String getType();

    abstract protected Pattern getMathPattern();

    abstract protected Pattern getFindPattern();

    @Override
    public boolean isMatch(String data) {

        if(getMathPattern().matcher(data).matches()) {
            return true;
        }

        return false;
    }

    abstract protected GroupData[] getGroupIndexArray();

    @Override
    public LinkedHashMap<String, Object> handle(String data, BufferedReader bufferedReader) {
        Matcher matcher = getFindPattern().matcher(data);

        GroupData[] groupDataArray = getGroupIndexArray();

        LinkedHashMap<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put(TransactionType.TRANS_TYPE.toString(), getType());
        if(matcher.find()) {
            for(GroupData groupData : groupDataArray) {
                dataMap.put(groupData.getDataKey(), matcher.group(groupData.getGroupIndex()));
            }

        }
        return dataMap;
    }

}
