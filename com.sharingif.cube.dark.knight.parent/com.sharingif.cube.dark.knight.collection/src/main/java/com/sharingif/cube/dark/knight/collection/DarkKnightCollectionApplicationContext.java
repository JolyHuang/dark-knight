package com.sharingif.cube.dark.knight.collection;

import com.sharingif.cube.core.exception.CubeRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
public class DarkKnightCollectionApplicationContext {

    private static final Logger logger = LoggerFactory.getLogger(DarkKnightCollectionApplicationContext.class);

    private static final String APP_INFO_FILE_PATH_KEY = "appInfoFilePath";
    private static final String IP_KEY = "ip";
    private static final String SERVER_NAME_KEY = "serverName";

    public static final String APP_INFO_FILE_PATH;
    public static final String IP;
    public static final String SERVER_NAME;

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
            APP_INFO_FILE_PATH = properties.getProperty(APP_INFO_FILE_PATH_KEY).trim();
        } catch (Exception e) {
            notFindLogger(APP_INFO_FILE_PATH_KEY);
            throw new CubeRuntimeException(e);
        }
        initParameterLogger(APP_INFO_FILE_PATH_KEY, APP_INFO_FILE_PATH);

        try {
            IP = properties.getProperty(IP_KEY).trim();
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

    }

    private static void notFindLogger(String key) {
        logger.warn("Could not find key '{}' in CubeConfigure.properties", key);
    }

    private static void initParameterLogger(String key, String value) {
        logger.info("system initialization parameter {}=[{}]", key, value);
    }


}
