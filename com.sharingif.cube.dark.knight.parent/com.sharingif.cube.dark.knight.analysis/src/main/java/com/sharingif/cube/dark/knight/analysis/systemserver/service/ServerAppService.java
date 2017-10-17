package com.sharingif.cube.dark.knight.analysis.systemserver.service;

import com.sharingif.cube.dark.knight.analysis.systemserver.model.entity.ServerApp;

import java.util.List;

/**
 * ServerAppService
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/17 下午12:14
 */
public interface ServerAppService {

    List<ServerApp> getList();

}
