package mx.mc.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.crypto.SecretKey;
import mx.mc.init.Constantes;
import mx.mc.model.Config;
import mx.mc.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class Token {
    private static final int TIME_TO_EXPIRATION = 4;
    private static final String JTI = "MJWT";
    private static final String ISSUER = "MOTIONCORP";
    private static final Logger LOGGER = LoggerFactory.getLogger(Token.class);
    private static final ResourceBundle RESOURCES = ResourceBundle.getBundle(Constantes.GLOBAL_MSG);
    
    private String value;
    private Date expiration;
    private String signatureKey = "";
    
    @Autowired
    private ConfigService configService;
      
    private void getSignatureKey() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        signatureKey = "";
        Config c = new Config();
        c.setNombre(Constantes.PARAM_SYSTEM_SIGNATURE_KEY);
        c.setActiva(Constantes.ACTIVO);
        try {
            List<Config> configList = configService.obtenerLista(c);
            if (!configList.isEmpty()) {
                signatureKey = configList.get(0).getValor();
            }
        } catch (Exception ex) {
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
    }
    
    public Token() {
        value = "";
        expiration = null;
        getSignatureKey();
    }
                                               
    private String getJWTToken(String username, Date issuedAt, Date expirDate) {
        SecretKey key = Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8));
	return Jwts.builder()
                        .setId(JTI)
                        .setSubject(username)
                        .setIssuer(ISSUER)
                        .setIssuedAt(issuedAt)
			.setExpiration(expirDate)
                        .signWith(key)
                        .compact();
    }
    
    public Token(String username) {
        Date issuedAt = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issuedAt);
        calendar.add(Calendar.HOUR_OF_DAY, TIME_TO_EXPIRATION);
        expiration = calendar.getTime();
        getSignatureKey();
        value = getJWTToken(username, issuedAt, expiration);
    }

    public boolean parseJWT(String jwt, StringBuilder result) {
        boolean estatus = false;
        Jws<Claims> jws;
        result.setLength(0);
        SecretKey key = Keys.hmacShaKeyFor(signatureKey.getBytes(StandardCharsets.UTF_8));
        try {
            jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            Claims claims = jws.getBody();
            if (!claims.getId().equals(JTI) || !claims.getIssuer().equals(ISSUER)) {
                result.append("El token no corresponde a este servicio");
            }
            else {
                estatus = true;
                result.append(claims.getSubject());
            }
        }
        catch (SignatureException | ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            if (ex instanceof ExpiredJwtException)
                result.append("El token ha expirado");
            else
                result.append("El token no es válido");
            LOGGER.error(RESOURCES.getString("sys.config.err"), ex);
        }
        return estatus;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}