package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * transport结束日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午8:16
 */
@Component
public class TransportEndDataHandler extends TransactionEndDataHandler {

    @Override
    protected String getType() {
        return TransactionType.TRANSPORT_END.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)Transport end===>(.*)");
    }
}
