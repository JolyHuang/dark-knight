package com.sharingif.cube.dark.knight.analysis.system.model.entity;

import com.sharingif.cube.core.user.ICoreUser;

/**
 * 用户信息
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/26 下午12:47
 */
public class User implements ICoreUser {

    private String uniqueId;
    private String username;
    private String password;

    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    @Override
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
