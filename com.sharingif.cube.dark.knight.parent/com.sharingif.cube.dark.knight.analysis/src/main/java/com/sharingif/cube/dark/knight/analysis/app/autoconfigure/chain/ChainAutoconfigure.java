package com.sharingif.cube.dark.knight.analysis.app.autoconfigure.chain;

import com.sharingif.cube.core.chain.ChainImpl;
import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.handler.chain.*;
import com.sharingif.cube.dark.knight.analysis.app.chain.NoUserChain;
import com.sharingif.cube.dark.knight.analysis.app.chain.command.UserToSessionCommand;
import com.sharingif.cube.security.web.handler.chain.access.NoUserAccessChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * OPayServerChainAutoconfigure
 * 2017/5/31 下午7:58
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class ChainAutoconfigure {

    @Bean("loginChain")
    public ChainImpl<HandlerMethodContent> createLoginChain(UserToSessionCommand userToSessionCommand) {
        List<Command<? super HandlerMethodContent>> commands = new ArrayList<Command<? super HandlerMethodContent>>();
        commands.add(userToSessionCommand);

        ChainImpl loginChain = new ChainImpl();
        loginChain.setCommands(commands);

        return loginChain;
    }

    @Bean("noUserChain")
    public NoUserChain createNoUserChain() {

        List<String> excludeMethods = new ArrayList<String>();
        excludeMethods.add("com.sharingif.cube.dark.knight.analysis.system.controller.UserController.login");

        NoUserChain noUserChain = new NoUserChain();
        noUserChain.setExcludeMethods(excludeMethods);

        return noUserChain;
    }

    @Bean(name="vertxControllerChains")
    public MultiHandlerMethodChain createVertxControllerChains(
            MonitorPerformanceChain controllerMonitorPerformanceChain
            ,NoUserChain noUserChain
            ,AnnotationHandlerMethodChain annotationHandlerMethodChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>(3);
        chains.add(controllerMonitorPerformanceChain);
        chains.add(noUserChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain springMVCHandlerMethodContent = new MultiHandlerMethodChain();
        springMVCHandlerMethodContent.setChains(chains);

        return  springMVCHandlerMethodContent;
    }

}
