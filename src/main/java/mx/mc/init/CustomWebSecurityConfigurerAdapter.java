package mx.mc.init;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author hramirez
 */
@EnableWebSecurity
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static final int SALT_LEN = 32;
    private static final int HASH_LEN = 32;
    private static final int PARALLELISM = 1;
    private static final int MEMORY = 65536;
    private static final int ITERATIONS = 3;
    private static final String SEPARATOR = "$";
    private static final String VARIANT = "argon2id";
    private static final String VERSION = "v=19";
    private static String hashPrefix;
    private static final Argon2PasswordEncoder ARG2ENCODER = new Argon2PasswordEncoder(SALT_LEN, HASH_LEN, PARALLELISM, MEMORY, ITERATIONS);
    
    public static String argon2Encode(CharSequence rawPassword) {
        if (rawPassword == null) {
            return null;
        }
        String hash = ARG2ENCODER.encode(rawPassword);
        if (hash != null && !hash.isEmpty()) {
            String parts[] = hash.split("\\" + SEPARATOR);
            if (parts.length == 6)
                hash = parts[4] + SEPARATOR + parts[5];
        }
        return hash;
    }
    
    @Bean
    public static PasswordEncoder customPasswordEncoder() {
        return new PasswordEncoder() {
            
            private final Logger logger = LoggerFactory.getLogger(CustomWebSecurityConfigurerAdapter.class);
            
            @Override
            public String encode(CharSequence rawPassword) {
                return argon2Encode(rawPassword);
            }
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (rawPassword == null || encodedPassword == null) {
                    throw new AuthenticationCredentialsNotFoundException("Usuario no Encontrado.");
                    
                } else {
                    hashPrefix = SEPARATOR + VARIANT + SEPARATOR + VERSION + SEPARATOR
                            + "m=" + String.valueOf(MEMORY) + ",t=" + String.valueOf(ITERATIONS) + ",p=" + String.valueOf(PARALLELISM) + SEPARATOR;
                    encodedPassword = hashPrefix + encodedPassword;
                    boolean res = false;
                    try {
                        res = ARG2ENCODER.matches(rawPassword, encodedPassword);
                    } catch (BadCredentialsException ex) {
                        logger.error("Clave de acceso incorrecta. {}", ex.getMessage());
                    } catch (Exception ex) {
                        logger.error("Error al validar Clave de acceso. {}", ex.getMessage());
                        throw new InternalAuthenticationServiceException("Error al validar Clave de acceso.");
                    }
                    return res;
                }
            }
        };
    }
}