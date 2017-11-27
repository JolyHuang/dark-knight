package com.sharingif.cube.dark.knight.analysis.app.chain.command;

import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.dark.knight.analysis.system.model.entity.User;
import com.sharingif.cube.web.user.CoreUserHttpSessionManage;
import com.sharingif.cube.web.user.IWebUserManage;
import com.sharingif.cube.web.vert.x.request.VertXRequestContext;

public class UserToSessionCommand extends AbstractHandlerMethodCommand {

    public UserToSessionCommand() {
        webUserManage = new CoreUserHttpSessionManage();
    }

    private IWebUserManage webUserManage;

    @Override
    public void execute(HandlerMethodContent content) throws CubeException {

        User user = (User)content.getReturnValue();

        VertXRequestContext vertXRequestContext = content.getRequestContext();
        webUserManage.persistenceUser(vertXRequestContext.getRequest(), user);
    }
}
