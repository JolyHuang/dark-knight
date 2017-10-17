package com.sharingif.cube.dark.knight.analysis.systemserver.model.entity;

import java.util.List;

/**
 * ServerApp
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/17 下午12:03
 */
public class ServerApp {

    public static final String SERVER_IP = "serverIp";
    public static final String SERVER_NAME = "serverName";

    private String serverIp;
    private List<String> serverList;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public List<String> getServerList() {
        return serverList;
    }

    public void setServerList(List<String> serverList) {
        this.serverList = serverList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServerApp{");
        sb.append("serverIp='").append(serverIp).append('\'');
        sb.append(", serverList=").append(serverList);
        sb.append('}');
        return sb.toString();
    }
}
