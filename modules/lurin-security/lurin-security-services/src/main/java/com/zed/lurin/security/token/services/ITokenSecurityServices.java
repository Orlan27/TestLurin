package com.zed.lurin.security.token.services;

import javax.ejb.Local;

/**
 * <p>Abstract Class where the methods that manage the security token are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.lurin.security.token.services.ITokenSecurityServices}
 */
@Local
public interface ITokenSecurityServices {

    String ejbMappedName = "java:global/lurin/com.zed.lurin.security.services/TokenSecurityServicesImpl!com.zed.lurin.security.token.services.ITokenSecurityServices";

    /**
     * method that creates the token based on user information
     * @param userName
     * @param hash
     * @return token String
     */
    String createToken(String userName, String hash);

    /**
     * method that decodes the token
     * @param token
     * @return
     */
    String getDecodeToken(String token);
}
