package com.sharingif.cube.dark.knight.analysis.systemserver.controller;

import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import com.sharingif.cube.dark.knight.analysis.systemserver.model.entity.ServerApp;
import com.sharingif.cube.dark.knight.analysis.systemserver.service.ServerAppService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * 服务器应用部署情况
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/17 下午12:28
 */
@Controller
@RequestMapping(value="serverApp")
public class ServerAppController {

    private ServerAppService serverAppService;

    @Resource
    public void setServerAppService(ServerAppService serverAppService) {
        this.serverAppService = serverAppService;
    }

    @RequestMapping(value="list", method= RequestMethod.GET)
    public List<ServerApp> list() {
        return serverAppService.getList();
    }

}
