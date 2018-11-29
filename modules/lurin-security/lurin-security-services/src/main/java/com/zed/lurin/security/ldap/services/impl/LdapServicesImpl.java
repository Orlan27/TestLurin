package com.zed.lurin.security.ldap.services.impl;

import com.zed.lurin.domain.dto.RegexSpaceDTO;
import com.zed.lurin.domain.dto.UserLdapDTO;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.ldap.services.ILdapServices;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Stateless
public class LdapServicesImpl implements ILdapServices {

    /*
    Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(LdapServicesImpl.class.getName());

    /**
     * Core Parameter Services
     */
    @EJB
    ICoreParameterServices parameterServices;


    /**
     * Method that all user ldap Server
     *
     * @return List<UserLdapDTO
     */
    @Override
    public List<UserLdapDTO> findUserLdap(){

        String ldapHostname = this.parameterServices.getCoreParameterString(
                Keys.LDAP_HOSTNAME.toString());
        String ldapPort = this.parameterServices.getCoreParameterString(
                Keys.LDAP_PORT.toString());
        String ldapSecAuth = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_AUTHENTICATION.toString());
        String userNameRegex = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_COMMON_NAME.toString());
        String domainServer = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_DOMAIN.toString());
        String userName = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_USERNAME.toString());
        String password = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_PASSWORD.toString());
        String baseDomain = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_BASE_DOMAIN.toString());


        String attrIdsValue = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_ATTR_IDS.toString());

        String searchFilter = this.parameterServices.getCoreParameterString(
                Keys.LDAP_SECURITY_SEARCH_FILTER.toString());


        StringBuilder securityPrincipal = new StringBuilder();
        securityPrincipal.append(String.format(userNameRegex,userName));
        securityPrincipal.append(domainServer);

        StringBuilder url = new StringBuilder();
        url.append(ldapHostname);
        url.append(":");
        url.append(ldapPort);

        Hashtable env = new Hashtable();
        env.put(Context.SECURITY_AUTHENTICATION, ldapSecAuth);
        env.put(Context.SECURITY_PRINCIPAL, securityPrincipal.toString());
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url.toString());

        LOGGER.warn("Services LDAP IMPL 1 => "+url.toString());
        LOGGER.warn("Services LDAP IMPL 2 => "+securityPrincipal.toString());
        LOGGER.warn("Services LDAP IMPL 3 => "+password);
        LOGGER.warn("Services LDAP IMPL 4 => "+ldapSecAuth);

        List<UserLdapDTO> userLdapDTOS = new ArrayList<>();
        DirContext ctx = null;
        NamingEnumeration<SearchResult> answer = null;
        try {
            ctx = new InitialDirContext(env);

            String [] attrIDs = attrIdsValue.split(",");
            SearchControls ctls = new SearchControls();
            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);

            answer = ctx.search(baseDomain, searchFilter, ctls);
            while (answer.hasMoreElements()){
                SearchResult searchResult = (SearchResult) answer.next();
                Attributes attributes = searchResult.getAttributes();
                AtomicInteger pos = new AtomicInteger(1);
                UserLdapDTO userLdapDTO = new UserLdapDTO();
                Arrays.stream(attrIDs).forEach(id -> {
                    try {
                        System.out.println(String.format("%s:%s -> %s",
                                attributes.get(id).getID(),
                                attributes.get(id).get(),
                                pos.get()));

                        userLdapDTO.setFirstNameAndSecondName(this.splitText((String) attributes.get(id).get()), pos.get());
                        userLdapDTO.setSurnameAndLastName(this.splitText((String) attributes.get(id).get()), pos.get());
                        userLdapDTO.setUserName((String) attributes.get(id).get(), pos.get());
                        userLdapDTO.setEmail((String) attributes.get(id).get(), pos.get());

                        pos.getAndIncrement();

                    } catch (NamingException e) {
                        e.printStackTrace();
                    }
                } );

                userLdapDTOS.add(userLdapDTO);
            }


        } catch (Exception e) {
            LOGGER.error(String.format("Ldap Services ERROR [%s]", e.getMessage()));
        }finally {
            if(answer!=null){
                try {
                    answer.close();
                } catch (NamingException e) {
                    LOGGER.error(String.format("Ldap Close NamingEnumeration ERROR [%s]", e.getMessage()));
                }
            }
            if(ctx!=null){
                try {
                    ctx.close();
                } catch (NamingException e) {
                    LOGGER.error(String.format("Ldap Close DirContext ERROR [%s]", e.getMessage()));
                }
            }
        }

        return userLdapDTOS;
    }

    /**
     * Methos split space name and surname
     * @param s
     * @return
     */
    private RegexSpaceDTO splitText (String s) {
        s = s.replaceAll("( +)"," ").trim();

        String [] stringParts = s.split(" ");

        RegexSpaceDTO regexSpaceDTO = new RegexSpaceDTO();
        regexSpaceDTO.setFirts(stringParts.length>0?stringParts[0]:"");
        regexSpaceDTO.setSecond(stringParts.length>1?stringParts[1]:"");
        return  regexSpaceDTO;
    }

}
