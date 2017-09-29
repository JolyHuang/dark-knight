package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * 交易开始日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午7:07
 */
@Component
public class TransactionBeginDataHandler extends AbstractDataHandler {

    @Override
    protected String getType() {
        return TransactionType.TRANSACTION_BEGIN.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)transaction begin===>(.*)");
    }

    @Override
    protected Pattern getFindPattern() {
        return Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] INFO  \\[(.*)\\] (.*)thdId:(.*), method:(.*), trsId:(.*)");
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
        };
        return groupIndexArray;
    }
}
