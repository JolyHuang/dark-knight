package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * transport开始日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午8:14
 */
@Component
public class TransportBeginDataHandler extends TransactionBeginDataHandler {

    @Override
    protected String getType() {
        return TransactionType.TRANSPORT_BEGIN.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)Transport begin===>(.*)");
    }
}
