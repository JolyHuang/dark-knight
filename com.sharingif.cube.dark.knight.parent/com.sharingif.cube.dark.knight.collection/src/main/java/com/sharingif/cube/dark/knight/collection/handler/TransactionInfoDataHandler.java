package com.sharingif.cube.dark.knight.collection.handler;

import com.sharingif.cube.core.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * 交易内容
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/9 下午5:14
 */
@Component
public class TransactionInfoDataHandler extends AbstractDataHandler {
    @Override
    protected String getType() {
        return TransactionType.TRANSACTION_INFO.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*) \\[(.*)\\] INFO  \\[(.*)\\] (.*)");
    }

    @Override
    protected Pattern getFindPattern() {
        return Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] INFO  \\[(.*)\\] (.*) - (.*)");
    }

    @Override
    protected GroupData[] getGroupIndexArray() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(2,"userId")
                ,new GroupData(3,"thread")
                ,new GroupData(4,"transUniqueId")
                ,new GroupData(6,"info")
        };
        return groupIndexArray;
    }

    @Override
    public LinkedHashMap<String, Object> handle(String data) {
        LinkedHashMap<String, Object> dataMap = super.handle(data);
        if(StringUtils.isTrimEmpty((String)dataMap.get("transUniqueId"))) {
            return null;
        }
        return dataMap;
    }
}
