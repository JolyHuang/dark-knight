package com.sharingif.cube.dark.knight.collection.write;

import com.mongodb.client.MongoDatabase;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

/**
 * 控制台输出
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午5:37
 */
@Component
public class ConsoleDataWrite implements DataWrite {

    private MongoDatabase mongoDatabase;

    @Override
    public void write(LinkedHashMap<String, Object> data) {
        System.out.println(data);
    }

}
