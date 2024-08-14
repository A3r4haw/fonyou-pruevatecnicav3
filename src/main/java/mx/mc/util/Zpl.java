package mx.mc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Cervanets
 */
public class Zpl implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(Zpl.class);
    
    public void preimpresion() {
        
        String urlParameters  = "param1=a&param2=b&param3=c";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
//        conn.setDoOutput( true );
//        conn.setInstanceFollowRedirects( false );
//        conn.setRequestMethod( "POST" );
//        conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
//        conn.setRequestProperty( "charset", "utf-8");
//        conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
//        conn.setUseCaches( false );
//        try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
//           wr.write( postData );
//        }
        try {
//            URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
            URL url = new URL("http://api.labelary.com/v1/printers/8dpmm/labels/4x6/0/");            
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty( "charset", "utf-8");
            connection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            connection.setUseCaches( false );
            connection.setRequestProperty("Accept", "application/json");
            InputStream responseStream = connection.getInputStream();
            ObjectMapper mapper = new ObjectMapper();
//            APOD apod = mapper.readValue(responseStream, APOD.class);
//            System.out.println(apod.title);
        } catch (MalformedURLException me){
            LOGGER.error("Error en la cadena URL {} " , me.getMessage());
        } catch (Exception ex){
            LOGGER.error("Error en la llamada a la api {} " , ex.getMessage());
        } 
    }
}
