package com.sharingif.cube.dark.knight.analysis.system.controller;

import com.sharingif.cube.core.exception.UnknownCubeException;
import com.sharingif.cube.core.handler.bind.annotation.RequestMapping;
import com.sharingif.cube.core.handler.bind.annotation.RequestMethod;
import com.sharingif.cube.dark.knight.analysis.system.model.entity.User;
import org.springframework.stereotype.Controller;

/**
 * 用户管理
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/26 下午12:46
 */
@Controller
@RequestMapping(value="user")
public class UserController {

    @RequestMapping(value="login", method= RequestMethod.POST)
    public User login(User user) {

        if(!("Dark-Knight".equals(user.getUsername()))) {
            throw new UnknownCubeException();
        }

        if(!("opayP001".equals(user.getPassword()))) {
            throw new UnknownCubeException();
        }

        user.setPassword(null);

        return user;

    }

}
