package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * controller结束日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午8:11
 */
@Component
public class ControllerEndDataHandler extends TransactionEndDataHandler {

    @Override
    protected String getType() {
        return TransactionType.CONTROLLER_END.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)controller end===>(.*)");
    }

}
