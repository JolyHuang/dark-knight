package com.sharingif.cube.dark.knight.collection.file.handler;

import com.sharingif.cube.dark.knight.collection.DarkKnightCollectionApplicationContext;
import com.sharingif.cube.dark.knight.collection.applog.handler.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private String type;
    private Pattern mathPattern;
    private Pattern findPattern;
    private GroupData[] groupIndexArray;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Pattern getMathPattern() {
        return mathPattern;
    }

    public void setMathPattern(Pattern mathPattern) {
        this.mathPattern = mathPattern;
    }

    public Pattern getFindPattern() {
        return findPattern;
    }

    public void setFindPattern(Pattern findPattern) {
        this.findPattern = findPattern;
    }

    public GroupData[] getGroupIndexArray() {
        return groupIndexArray;
    }

    public void setGroupIndexArray(GroupData[] groupIndexArray) {
        this.groupIndexArray = groupIndexArray;
    }

    @Override
    public boolean isMatch(String data) {

        if(getMathPattern().matcher(data).matches()) {
            return true;
        }

        return false;
    }

    @Override
    public LinkedHashMap<String, Object> handle(String data) {
        Matcher matcher = getFindPattern().matcher(data);

        GroupData[] groupDataArray = getGroupIndexArray();

        LinkedHashMap<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("serverIp", DarkKnightCollectionApplicationContext.IP);
        dataMap.put("serverName", DarkKnightCollectionApplicationContext.SERVER_NAME);
        dataMap.put(TransactionType.TRANS_TYPE.toString(), getType());
        if(matcher.find()) {
            for(GroupData groupData : groupDataArray) {
                dataMap.put(groupData.getDataKey(), matcher.group(groupData.getGroupIndex()));
            }

        }
        return dataMap;
    }

}
