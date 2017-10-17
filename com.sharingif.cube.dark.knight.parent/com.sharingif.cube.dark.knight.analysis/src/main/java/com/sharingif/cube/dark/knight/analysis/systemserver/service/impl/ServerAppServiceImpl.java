package com.sharingif.cube.dark.knight.analysis.systemserver.service.impl;

import com.sharingif.cube.dark.knight.analysis.systemserver.dao.ServerAppDAO;
import com.sharingif.cube.dark.knight.analysis.systemserver.model.entity.ServerApp;
import com.sharingif.cube.dark.knight.analysis.systemserver.service.ServerAppService;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * ServerAppServiceImpl
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/17 下午12:14
 */
@Service
public class ServerAppServiceImpl implements ServerAppService {

    private ServerAppDAO serverAppDAO;

    @Resource
    public void setServerAppDAO(ServerAppDAO serverAppDAO) {
        this.serverAppDAO = serverAppDAO;
    }

    @Override
    public List<ServerApp> getList() {
        List<Document> documentList = serverAppDAO.queryList();
        List<ServerApp> serverAppList = new ArrayList<ServerApp>();
        ServerApp currentServerApp = null;
        for(Document document : documentList) {
            String serverIp = document.getString(ServerApp.SERVER_IP);
            String serverName = document.getString(ServerApp.SERVER_NAME);
            if((currentServerApp == null) || (!(serverIp.equals(currentServerApp.getServerIp())))) {
                ServerApp serverApp = new ServerApp();
                serverApp.setServerIp(serverIp);
                List<String> serverList = new ArrayList<String>();
                serverApp.setServerList(serverList);
                serverList.add(serverName);

                serverAppList.add(serverApp);

                currentServerApp = serverApp;
            } else {
                currentServerApp.getServerList().add(serverName);
            }
        }

        return serverAppList;
    }

}
