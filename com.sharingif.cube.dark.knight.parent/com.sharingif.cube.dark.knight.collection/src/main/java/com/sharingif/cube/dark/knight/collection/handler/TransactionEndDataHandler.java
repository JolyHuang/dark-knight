package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * 交易结束日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午7:52
 */
@Component
public class TransactionEndDataHandler extends AbstractDataHandler {
    @Override
    protected String getType() {
        return TransactionType.TRANSACTION_END.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)transaction end===>(.*)");
    }

    @Override
    protected Pattern getFindPattern() {
        return Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] INFO  \\[(.*)\\] (.*)thdId:(.*), method:(.*), trsId:(.*), exTime:(.*)");
    }

    @Override
    protected GroupData[] getGroupIndexArray() {
        GroupData[] groupIndexArray = {
                new GroupData(1,"startTime")
                ,new GroupData(2,"userId")
                ,new GroupData(3,"thread")
                ,new GroupData(4,"transUniqueId")
                ,new GroupData(6,"threadId")
                ,new GroupData(7,"method")
                ,new GroupData(8,"transId")
                ,new GroupData(9,"transExcuteTime")
        };
        return groupIndexArray;
    }
}
