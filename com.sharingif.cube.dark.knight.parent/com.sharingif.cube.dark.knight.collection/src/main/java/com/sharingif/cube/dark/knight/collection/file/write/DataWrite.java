package com.sharingif.cube.dark.knight.collection.file.write;

import java.util.LinkedHashMap;

/**
 * 数据输出
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/9/25 下午2:46
 */
public interface DataWrite {

    void write(LinkedHashMap<String, Object> data);

}
