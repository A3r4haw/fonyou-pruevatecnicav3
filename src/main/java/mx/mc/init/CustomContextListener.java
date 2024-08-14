package mx.mc.init;

import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author hramirez
 */
public class CustomContextListener implements ServletContextListener, Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("contextInitialized");

        try {
            ServletContext ctx = sce.getServletContext();
            String atrAmbiente = ctx.getInitParameter(Constantes.CTX_PARAM_APP_AMBIENTE);
            String atrLevelog = ctx.getInitParameter(Constantes.CTX_PARAM_APP_DEBUG);
            String atrTheme = ctx.getInitParameter(Constantes.CTX_PARAM_APP_THEME);

            LOGGER.info("atrAmbiente: {}", atrAmbiente);
            LOGGER.info("atrLevelog: {}", atrLevelog);
            LOGGER.info("atrTheme: {}", atrTheme);

            WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
            AutowireCapableBeanFactory factory = springContext.getAutowireCapableBeanFactory();
            factory.autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, false);

        } catch (IllegalStateException | BeansException e) {
            LOGGER.error("ServletContextListener contextInitialized : {}", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("contextDestroyed");

    }

}
