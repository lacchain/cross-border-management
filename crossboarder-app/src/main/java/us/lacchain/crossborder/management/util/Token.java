package us.lacchain.crossborder.management.util;

import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.security.KeyFactory;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Token{

    Logger logger = LoggerFactory.getLogger(Token.class);

    @Value("${crossborder.oauth.client.web.public-key}")
    private String publicKey;

    public Token(){

    }

    public Jws<Claims> parseJWT(String jwt)throws Exception{
        logger.debug("PublicKey"+publicKey);
        logger.debug("-----Parsing JWT----"+jwt);
            //This line will throw an exception if it is not a signed JWS (as expected)
            PublicKey publicKey = decodePublicKey(pemToDer("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAy2IFXU+NGJCAf3eYN2rmTYFNm9G6agek7jKGkqfnw7vtwblkSGYtQ8vGij02PV0jK4+oPOe3qusEyvka9LprNRKfWJWJ5vjl35kI5ELt3aapKs2OcGJTPPIx/2YdRX8M7YkIVv4XD6F8mLy332QRp4qfi6yitJI9YgOI8LLdgJ+X56KBE4PZy9T+rCke4cQuzobgXCqCX9oyNS0Cm19JaHzUNCL9TkJT93/78YqpjYMTWTyEKaYagLBW+RwWPHZV2kCQ/Rsg7QhbrggnqKbXk4stRCE9Ga2IC4e1JU/zkE+NbMx5Y2Zj/b38ShRNjWTlF34+HjRwoRvwBXgARaafZQIDAQAB"));
            Jws<Claims> claims = Jwts.parser()         
                .setSigningKey(publicKey)
                .parseClaimsJws(jwt);
            return claims;
    }

    private static byte[] pemToDer(String pem) throws IOException {
        return Base64.getDecoder().decode(stripBeginEnd(pem));
    }

    private static String stripBeginEnd(String pem) {

        String stripped = pem.replaceAll("-----BEGIN (.*)-----", "");
        stripped = stripped.replaceAll("-----END (.*)----", "");
        stripped = stripped.replaceAll("\r\n", "");
        stripped = stripped.replaceAll("\n", "");

        return stripped.trim();
    }

    private static PublicKey decodePublicKey(byte[] der) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {

        X509EncodedKeySpec spec = new X509EncodedKeySpec(der);

        KeyFactory kf = KeyFactory.getInstance("RSA");

        return kf.generatePublic(spec);
    }
}