package com.sharingif.cube.dark.knight.collection.applog.handler;

import com.sharingif.cube.core.util.StringUtils;

import java.util.LinkedHashMap;

/**
 * 交易内容
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/9 下午5:14
 */
public class TransactionInfoDataHandler extends AppLogDataHandler {

    @Override
    public LinkedHashMap<String, Object> handle(String data) {
        LinkedHashMap<String, Object> dataMap = super.handle(data);
        if(StringUtils.isTrimEmpty((String)dataMap.get("transUniqueId"))) {
            return null;
        }
        return dataMap;
    }
}
