package com.sharingif.cube.dark.knight.collection.read;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.core.util.DateUtils;
import com.sharingif.cube.dark.knight.collection.handler.DataHandler;
import com.sharingif.cube.dark.knight.collection.handler.TransactionErrorDataHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

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

    private String currentProcessData;
    private boolean errorDataFlag = false;
    private StringBuilder errorData = null;

    private DataHandler dataHandler;


    @Resource(name="compositeDataHandler")
    public void setDataHandler(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String changeFilePath(String filePath) {
        String currentDate = DateUtils.getCurrentDate(DateUtils.DATE_ISO_FORMAT);
        currentProcessData = currentDate;
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

    protected Pattern getErrorMathPattern() {
        return Pattern.compile("(.*) error===>(.*)");
    }

    protected Pattern getErrorCausePattern() {
        return Pattern.compile("^.{4}-.{2}-.{2}(.*)");
    }

    protected boolean canHandleErrorData(String data) {
        if(!getErrorCausePattern().matcher(data).matches()) {
            errorData.append("\n").append(data);
            return true;
        } else {
            String errorDataStr = errorData.toString();
            if(dataHandler.isMatch(errorDataStr)) {
                dataHandler.handle(errorDataStr);
            }
            errorDataFlag = false;
            return false;
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

                    if(errorDataFlag) {
                        boolean handlerFlag = canHandleErrorData(data);
                        if(handlerFlag) {
                            continue;
                        }
                    }

                    if(getErrorMathPattern().matcher(data).matches()) {
                        errorDataFlag = true;
                        errorData = new StringBuilder(data);
                        errorData.append(", errorCause:");
                        continue;
                    }

                    if(dataHandler.isMatch(data)) {
                        dataHandler.handle(data);
                    }
                }
            } catch (IOException e) {
                logger.error("IO Exception", e);
                throw new CubeRuntimeException("IO Exception");
            }

            try {
                TimeUnit.SECONDS.sleep(5);
                if(!DateUtils.getCurrentDate(DateUtils.DATE_ISO_FORMAT).equals(currentProcessData)) {
                    String newFilePath = changeFilePath(filePath);
                    bufferedReader = getBufferedReader(newFilePath);
                }
            } catch (InterruptedException e) {
                logger.error("Interrupted Exception", e);
                throw new CubeRuntimeException("Interrupted Exception");
            }
        }

    }

}
