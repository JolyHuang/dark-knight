package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

/**
 * 交易错误日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/26 上午11:55
 */
@Component
public class TransactionErrorDataHandler extends AbstractDataHandler {

    @Override
    protected String getType() {
        return TransactionType.TRANSACTION_ERROR.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)transaction error===>(.*)");
    }

    @Override
    protected Pattern getFindPattern() {
        return Pattern.compile("(.{23}) \\[(.*)\\] \\[(.*)\\] ERROR \\[(.*)\\] (.*)thdId:(.*), method:(.*), trsId:(.*), exTime:(.*), message:(.*), localizedMessage:(.*)");
    }

    protected Pattern getErrorCausePattern() {
        return Pattern.compile("^.{4}-.{2}-.{2}");
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
                ,new GroupData(10,"message")
                ,new GroupData(11,"localizedMessage")
        };
        return groupIndexArray;
    }

    @Override
    public LinkedHashMap<String, Object> handle(String data, BufferedReader bufferedReader) {
        LinkedHashMap<String, Object> dataMap = super.handle(data, bufferedReader);
        StringBuilder errorCause = new StringBuilder();
        Boolean firstLineFlag = true;
        getErrorCause(bufferedReader, errorCause, firstLineFlag);
        dataMap.put("errorCause", errorCause.toString());
        return dataMap;
    }

    protected void getErrorCause(BufferedReader bufferedReader, StringBuilder errorCause, Boolean firstLineFlag) {
        try {
            bufferedReader.mark(10);
            char[] cbuf = new char[10];

            bufferedReader.read(cbuf);
            bufferedReader.reset();

            String data = new String(cbuf);
            if(getErrorCausePattern().matcher(data).matches()) {
                return ;
            }
            data = bufferedReader.readLine();
            if(firstLineFlag) {
                errorCause.append(data);
            } else {
                errorCause.append("\n").append(data);
            }

            getErrorCause(bufferedReader, errorCause, false);
        } catch (IOException e) {
            logger.error("get error cause error", e);
        }

    }
}
