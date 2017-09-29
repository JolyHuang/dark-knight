package com.sharingif.cube.dark.knight.collection.handler;

import com.sharingif.cube.dark.knight.collection.DarkKnightCollectionApplicationContext;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Started Bootstrap 启动信息处理，提取启动完成时间、启动花费时间
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午3:11
 */
@Component
public class StartedBootstrapDataHandler extends AbstractDataHandler {

    @Override
    protected String getType() {
        return TransactionType.SERVER_START.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)Started Bootstrap(.*)");
    }

    @Override
    protected Pattern getFindPattern() {
        return Pattern.compile("(.{23})(.*)Started Bootstrap in (.*) seconds (.*)");
    }

    @Override
    protected GroupData[] getGroupIndexArray() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(3,"takesTime")
            };
        return groupIndexArray;
    }

    @Override
    public LinkedHashMap<String, Object> handle(String data, BufferedReader bufferedReader) {
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
