package com.sharingif.cube.dark.knight.collection.file.handler;

import com.sharingif.cube.dark.knight.collection.file.write.DataWrite;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 组合数据加工
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午2:17
 */
public class CompositeDataHandler implements DataHandler {

    private List<DataHandler> dataHandlerList;
    private List<DataWrite> dataWriteList;

    public void setDataHandlerList(List<DataHandler> dataHandlerList) {
        this.dataHandlerList = dataHandlerList;
    }

    public void setDataWriteList(List<DataWrite> dataWriteList) {
        this.dataWriteList = dataWriteList;
    }

    @Override
    public boolean isMatch(String data) {
        return true;
    }

    @Override
    public LinkedHashMap<String, Object> handle(String data) {

        for(DataHandler dataHandler : dataHandlerList) {
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
        for(DataWrite dataWrite : dataWriteList) {
            dataWrite.write(dataMap);
        }
    }

}
