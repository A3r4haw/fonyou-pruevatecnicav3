package mx.mc.init;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 *
 * @author hramirez
 */
public class CustomAccountStatusUserDetailsChecker implements Serializable, UserDetailsChecker {
    private static final long serialVersionUID = 1;

    private static final MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAccountStatusUserDetailsChecker.class);

    @Override
    public void check(UserDetails user) {
        LOGGER.trace("mx.mc.init.CustomAccountStatusUserDetailsChecker.check()");
        
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.notfound", "Usuario no Encontrado."));
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.locked", "Cuenta de usuario bloqueada."));
        } else if (!user.isEnabled()) {
            throw new DisabledException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.disabled", "Cuenta de usuario inactiva."));
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.expired", "Cuenta de usuario expirada."));
        } else if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(messages.getMessage(
                    "AccountStatusUserDetailsChecker.credentialsExpired", "Clave de acceso de usuario expirada."));
        }
    }

}
