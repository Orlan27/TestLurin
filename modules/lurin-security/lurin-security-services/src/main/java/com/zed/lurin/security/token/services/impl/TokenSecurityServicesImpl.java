package com.zed.lurin.security.token.services.impl;


import com.zed.lurin.security.token.services.ITokenSecurityServices;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Stateless where the methods that manage the security token are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.lurin.security.token.services.ITokenSecurityServices}
 */
@Stateless
public class TokenSecurityServicesImpl implements ITokenSecurityServices {

    /*
     *Secret Key
     */
    final static String SECRET_KEY = "ZGZzcjM0NTN3ZWZ3czQ1M3JlZndlcjM0c2Z3NDV3ZWZ3ZXRydw==";

    /*
     * Note: url example implements https://stormpath.com/blog/jwt-java-create-verify
     */
    /**
     * method that creates the token based on user information
     * @param userName
     * @param hash
     * @return token String
     */
    @Override
    public String createToken(String userName, String hash){

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(String.format("%s|%s",userName, hash))
                                    .setIssuer(userName)
                                    .signWith(signatureAlgorithm, signingKey);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }


    /**
     * method that decodes the token
     * @param token
     * @return
     */
    @Override
    public String getDecodeToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
        return claims.getIssuer();
    }


}
