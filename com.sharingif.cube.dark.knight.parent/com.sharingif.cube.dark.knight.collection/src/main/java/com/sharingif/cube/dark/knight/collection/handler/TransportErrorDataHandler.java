package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * transport错误日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/26 下午12:23
 */
@Component
public class TransportErrorDataHandler extends TransactionErrorDataHandler {

    @Override
    protected String getType() {
        return TransactionType.TRANSPORT_ERROR.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)Transport error===>([\\s\\S]*)");
    }

}
