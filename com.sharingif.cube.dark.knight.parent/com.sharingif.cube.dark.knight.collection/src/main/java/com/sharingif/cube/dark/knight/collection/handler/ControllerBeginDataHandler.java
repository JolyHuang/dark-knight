package com.sharingif.cube.dark.knight.collection.handler;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * controller开始日志
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午8:09
 */
@Component
public class ControllerBeginDataHandler extends TransactionBeginDataHandler {

    @Override
    protected String getType() {
        return TransactionType.CONTROLLER_BEGIN.toString();
    }

    @Override
    protected Pattern getMathPattern() {
        return Pattern.compile("(.*)controller begin===>(.*)");
    }

}
