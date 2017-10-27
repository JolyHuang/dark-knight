package com.sharingif.cube.dark.knight.analysis.app.chain.command;

import com.sharingif.cube.communication.http.HttpSession;
import com.sharingif.cube.core.exception.CubeException;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.command.AbstractHandlerMethodCommand;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.dark.knight.analysis.system.model.entity.User;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfo;

public class UserToSessionCommand extends AbstractHandlerMethodCommand {
    @Override
    public void execute(HandlerMethodContent content) throws CubeException {

        User user = (User)content.getReturnValue();

        VertXRequestInfo vertXRequestInfo = (VertXRequestInfo) content.getRequestInfo();
        HttpSession httpSession = vertXRequestInfo.getRequest().getSession();
        httpSession.setAttribute(ICoreUser.CORE_USER, user);

    }
}
