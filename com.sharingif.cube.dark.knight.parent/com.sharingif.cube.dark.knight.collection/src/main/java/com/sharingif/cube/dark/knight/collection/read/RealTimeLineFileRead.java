package com.sharingif.cube.dark.knight.collection.read;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.DateUtils;
import com.sharingif.cube.dark.knight.collection.handler.DataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * 实时行读取
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午12:53
 */
@Component
public class RealTimeLineFileRead implements FileRead {

    private DataHandler dataHandler;

    @Resource(name="compositeDataHandler")
    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String changeFilePath(String filePath) {
        String currentDate = DateUtils.getCurrentDate(DateUtils.DATE_ISO_FORMAT);
        return filePath.substring(0,filePath.length()-4)+"."+currentDate+filePath.substring(filePath.length()-4,filePath.length());
    }

    protected BufferedReader getBufferedReader(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            return new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            logger.error("No such file or directory", e);
            throw new CubeRuntimeException("No such file or directory");
        }
    }

    @Override
    public void read(String filePath) {

        String changeFilePath = changeFilePath(filePath);
        BufferedReader bufferedReader = getBufferedReader(changeFilePath);

        while(true) {
            String data;
            try {
                while((data = bufferedReader.readLine()) != null){
                    if(dataHandler.isMatch(data)) {
                        dataHandler.handle(data, bufferedReader);
                    }
                }
            } catch (IOException e) {
                logger.error("IO Exception", e);
                throw new CubeRuntimeException("IO Exception");
            }

            try {
                TimeUnit.SECONDS.sleep(5);
                String newFilePath = changeFilePath(filePath);
                File file = new File(newFilePath);
                if(file.exists()) {
                    bufferedReader = getBufferedReader(newFilePath);
                }
            } catch (InterruptedException e) {
                logger.error("Interrupted Exception", e);
                throw new CubeRuntimeException("Interrupted Exception");
            }
        }

    }

}
