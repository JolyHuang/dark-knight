package com.sharingif.cube.dark.knight.analysis.app.autoconfigure.chain.command;

import com.sharingif.cube.dark.knight.analysis.app.chain.command.UserToSessionCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CommandAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/10/26 下午4:43
 */
@Configuration
public class CommandAutoconfigure {

    @Bean("userToSessionCommand")
    public UserToSessionCommand createUserToSessionCommand() {
        return new UserToSessionCommand();
    }

}
