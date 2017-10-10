package com.sharingif.cube.dark.knight.collection.handler;

import com.sharingif.cube.dark.knight.collection.DarkKnightCollectionApplicationContext;
import com.sharingif.cube.dark.knight.collection.write.DataWrite;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * 组合数据加工
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午2:17
 */
@Component
public class CompositeDataHandler implements DataHandler {

    @Override
    public boolean isMatch(String data) {
        return true;
    }

    @Override
    public LinkedHashMap<String, Object> handle(String data) {

        for(DataHandler dataHandler : DarkKnightCollectionApplicationContext.DATA_HANDLER_LIST) {
            if(dataHandler.isMatch(data)) {
                LinkedHashMap<String, Object> dataMap = dataHandler.handle(data);

                if(dataMap != null) {
                    write(dataMap);
                }

                return null;
            }
        }

        return null;

    }

    /**
     * 输出数据
     * @param dataMap
     */
    protected void write(LinkedHashMap<String, Object> dataMap) {
        for(DataWrite dataWrite : DarkKnightCollectionApplicationContext.DATA_WRITE_LIST) {
            dataWrite.write(dataMap);
        }
    }

}
