package com.sharingif.cube.dark.knight.collection;

import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.CubeRuntimeException;
import com.sharingif.cube.dark.knight.collection.handler.DataHandler;
import com.sharingif.cube.dark.knight.collection.read.FileRead;
import com.sharingif.cube.dark.knight.collection.write.DataWrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 系统入口
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午3:34
 */
@Component
public class DarkKnightCollectionApplicationContext implements ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(DarkKnightCollectionApplicationContext.class);

    private static final String FILE_PATH_KEY = "filePath";
    private static final String IP_KEY = "ip";
    private static final String SERVER_NAME_KEY = "serverName";
    private static final String FILE_READ_NAME_KEY = "fileReadName";
    private static final String DATA_HANDLER_LIST_KEY = "dataHandlerList";
    private static final String DATA_WRITE_LIST_KEY = "dataWriteList";

    public static final String FILE_PATH;
    public static final String IP;
    public static final String SERVER_NAME;
    public static final String FILE_READ_NAME;

    private static final String[] DATA_HANDLER_ARRAY;
    public static List<DataHandler> DATA_HANDLER_LIST;
    private static final String[] DATA_WRITE_ARRAY;
    public static List<DataWrite> DATA_WRITE_LIST;

    private ApplicationContext applicationContext;

    static {
        Properties properties = null;
        InputStream in = null;
        try {
            String filePath = System.getProperty("user.dir")+"/CubeConfigure.properties";
//            String filePath = "/Users/Joly/Work/Joly/project/dark-knight/com.sharingif.cube.dark.knight.parent/com.sharingif.cube.dark.knight.collection/src/main/resources/config/app/CubeConfigure.properties";
            in = new FileInputStream(new File(filePath));
            properties = new Properties();
            properties.load(in);
        } catch (Exception e) {
            logger.error("config.app.CubeConfigure file not found");
            throw new CubeRuntimeException(e);
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.error("close InputStream error");
                }
            }
        }

        try {
            FILE_PATH = properties.getProperty(FILE_PATH_KEY).trim();
        } catch (Exception e) {
            notFindLogger(FILE_PATH_KEY);
            throw new CubeRuntimeException(e);
        }
        initParameterLogger(FILE_PATH_KEY, FILE_PATH);

        try {
            InetAddress addr = InetAddress.getLocalHost();
            IP = addr.getHostAddress();
        } catch (Exception e) {
            throw new CubeRuntimeException(e);
        }
        initParameterLogger(IP_KEY, IP);

        try {
            SERVER_NAME = properties.getProperty(SERVER_NAME_KEY).trim();
        } catch (Exception e) {
            notFindLogger(SERVER_NAME_KEY);
            throw new CubeRuntimeException(e);
        }
        initParameterLogger(SERVER_NAME_KEY, SERVER_NAME);

        try {
            FILE_READ_NAME = properties.getProperty(FILE_READ_NAME_KEY).trim();
        } catch (Exception e) {
            notFindLogger(FILE_READ_NAME_KEY);
            throw new CubeRuntimeException(e);
        }
        initParameterLogger(FILE_READ_NAME_KEY, FILE_READ_NAME);


        try {
            String dataHandlerList = properties.getProperty(DATA_HANDLER_LIST_KEY).trim();
            DATA_HANDLER_ARRAY = dataHandlerList.split(",");
        } catch (Exception e) {
            notFindLogger(DATA_HANDLER_LIST_KEY);
            throw new CubeRuntimeException(e);
        }
        initParameterLogger(DATA_HANDLER_LIST_KEY, Arrays.toString(DATA_HANDLER_ARRAY));

        try {
            String dataWriteList = properties.getProperty(DATA_WRITE_LIST_KEY).trim();
            DATA_WRITE_ARRAY = dataWriteList.split(",");
        } catch (Exception e) {
            notFindLogger(DATA_WRITE_LIST_KEY);
            throw new CubeRuntimeException(e);
        }
        initParameterLogger(DATA_WRITE_LIST_KEY, Arrays.toString(DATA_WRITE_ARRAY));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static void notFindLogger(String key) {
        logger.warn("Could not find key '{}' in CubeConfigure.properties", key);
    }

    private static void initParameterLogger(String key, String value) {
        logger.info("system initialization parameter {}=[{}]", key, value);
    }

    public void start() {

        DATA_HANDLER_LIST = new ArrayList<DataHandler>(DATA_HANDLER_ARRAY.length);
        for(String dataHandlerName : DATA_HANDLER_ARRAY) {
            DATA_HANDLER_LIST.add(applicationContext.getBean(dataHandlerName, DataHandler.class));
        }

        DATA_WRITE_LIST = new ArrayList<DataWrite>(DATA_WRITE_ARRAY.length);
        for(String dataWriteName : DATA_WRITE_ARRAY) {
            DATA_WRITE_LIST.add(applicationContext.getBean(dataWriteName, DataWrite.class));
        }

        FileRead fileRead = applicationContext.getBean(FILE_READ_NAME, FileRead.class);

        fileRead.read(FILE_PATH);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        start();
    }

}
