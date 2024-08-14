package mx.mc.magedbean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(value = "session")
public class InitMB implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(SesionMB.class);
    private String hostName;
    private String revNumber = "0000";

    @PostConstruct
    public void init() {
        LOGGER.debug("mx.mc.magedbean.InitMB.init()");
        try {
            InetAddress localIp = InetAddress.getLocalHost();
            hostName = localIp.getHostName();
        }
        catch (UnknownHostException uhe) {
            hostName = "";
            LOGGER.error("mx.mc.magedbean.InitMB.init():", uhe);
        }
        try {
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String appServerHome = servletContext.getRealPath("/");
            File manifestFile = new File(appServerHome, "META-INF/MANIFEST.MF");
            Manifest mf = new Manifest();
            mf.read(new FileInputStream(manifestFile));
            Attributes atts = mf.getMainAttributes();
            if (atts.getValue("Implementation-Version") != null)
                revNumber = "v" + atts.getValue("Implementation-Version");
            if (atts.getValue("Implementation-Build") != null)
                revNumber += " Rev " + atts.getValue("Implementation-Build");
        }
        catch (IOException ioex) {
            hostName = "";
            LOGGER.error("mx.mc.magedbean.InitMB.init():", ioex);
        }        
    }

    public String getHostName() {
        return hostName;
    }

    public String getRevNumber() {
        return revNumber;
    }
}
