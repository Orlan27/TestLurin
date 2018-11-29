package com.zed.lurin.security.core;

import com.google.gson.Gson;
import com.zed.carriers.services.ICarriersServices;
import com.zed.lurin.domain.jpa.Carriers;
import com.zed.lurin.domain.jpa.Users;
import com.zed.lurin.domain.jpa.UsersControlAccess;
import com.zed.lurin.domain.jpa.view.OnlyCarriersByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import com.zed.lurin.security.hashing.services.IGenerateShaHash;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.keys.Roles;
import com.zed.lurin.security.token.services.ITokenSecurityServices;
import com.zed.lurin.security.users.services.IUsersServices;
import com.zed.lookfeel.management.services.ILookAndFeelManagementServices;
import com.zed.lurin.domain.jpa.Themes;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.xml.security.algorithms.Algorithm;
import org.jboss.security.auth.spi.DatabaseServerLoginModule;

import javax.naming.*;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.registry.infomodel.User;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This class is an interceptor of the user's login and logout by
 * j_secure_check. It notifies all the login and logout events to all the
 * implementations of AbstractLoginServices
 *
 * @author Francisco Lujano
 */
public class DatabaseServerLoginCustom extends DatabaseServerLoginModule {

    /*
    Logger utils
     */
    private static Logger LOGGER = Logger.getLogger(DatabaseServerLoginCustom.class.getName());

    /**
     * Core Parameter Services
     */
    ICoreParameterServices parameterServices;

    /**
     * Token Services
     */
    ITokenSecurityServices tokenSecurityServices;

    /**
     * User Services
     */
    IUsersServices usersServices;

    /**
     * Carriers Services
     */
    ICarriersServices carriersServices;

    /**
     * Look and feel Services
     */
    ILookAndFeelManagementServices lookAndFeelManagementServices;

    /**
     * Generate Hash
     */
    IGenerateShaHash iGenerateShaHash;

    /**
     * I18N form login
     */
    private String lang;

    /**
     * Hash generated form action
     */
    private String hash;


    private synchronized boolean ldapAuthenticated(String user, String password){

        boolean result = false;

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

        StringBuilder securityPrincipal = new StringBuilder();
        securityPrincipal.append(String.format(userNameRegex,user));
        securityPrincipal.append(domainServer);


        StringBuilder url = new StringBuilder();
        url.append(ldapHostname);
        url.append(":");
        url.append(ldapPort);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, url.toString());
        env.put(Context.SECURITY_AUTHENTICATION, ldapSecAuth);
        env.put(Context.SECURITY_PRINCIPAL, securityPrincipal.toString());
        env.put(Context.SECURITY_CREDENTIALS, password);

        LOGGER.warn("SSO LDAP IMPL 1 => "+url.toString());
        LOGGER.warn("SSO LDAP IMPL 1 => "+securityPrincipal.toString());
        LOGGER.warn("SSO LDAP IMPL 1 => "+password);
        LOGGER.warn("SSO LDAP IMPL 1 => "+ldapSecAuth);

        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            ctx.close();
            result = true;

        } catch (AuthenticationNotSupportedException ex) {
            LOGGER.error(String.format("The authentication is not supported by the server [%s]",ex.getMessage()));
        } catch (AuthenticationException ex) {
            LOGGER.error(String.format("incorrect password or username [%s]",ex.getMessage()));
        } catch (NamingException ex) {
            LOGGER.error(String.format("error when trying to create the context [%s]",ex.getMessage()));
        }finally {
            if(ctx!=null){
                try {
                    ctx.close();
                } catch (NamingException e) {
                    LOGGER.error(String.format("SSO LDAP Close DirContext ERROR [%s]", e.getMessage()));
                }
            }
        }

        return result;
    }


    @Override
    protected boolean validatePassword(String inputPassword, String expectedPassword) {
        if(expectedPassword==null){//LDAP

            boolean result = false;
            try {
                result = this.ldapAuthenticated(getUsername(), this.getUsernameAndPassword()[1]);

            }catch (Exception e){
                LOGGER.error(e.getMessage());
            }
            return result;
        }else{//DATABASE
            /**
             * Database
             */
            return super.validatePassword(inputPassword, expectedPassword);
        }


    }

    @Override
    protected String getUsersPassword() throws LoginException {
        HttpServletRequest request = null;
        try {
            request = (HttpServletRequest) PolicyContext.getContext(HttpServletRequest.class.getName());
        } catch (PolicyContextException e) {
            LOGGER.error(String.format("Error in getUsersPassword %s",e.getMessage()));
        }

        this.setLang(request.getParameter("j_lang"));
        this.setHash(request.getParameter("j_hash"));

        return super.getUsersPassword();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean login() throws LoginException {

        boolean isLogged=false;
        try {
            this.initializedServices();

            HttpServletRequest request = (HttpServletRequest) PolicyContext.getContext(HttpServletRequest.class.getName());

            isLogged = super.login();
            if(isLogged) {

                final int timeExpired = this.parameterServices.getCoreParameterInteger(
                        Keys.KEY_SESSION_TIMEOUT_INACTIVE.toString());
                Users user = this.usersServices.getUserForUserName(getUsername());

                if (this.getHash() != null){
                    final String token = this.tokenSecurityServices.createToken(getUsername(), this.getHash());

                    LOGGER.debug(String.format("Token: %s", token));

                    UsersControlAccess usersControlAccess = new UsersControlAccess();
                    usersControlAccess.setUser(user);
                    usersControlAccess.setToken(token);
                    usersControlAccess.setTimeExpired(timeExpired);
                    usersControlAccess.setI18n(this.getLang());

                    this.usersServices.createUserControlAccess(usersControlAccess);

                    setSessionVariables(request, usersControlAccess.getToken(), user, usersControlAccess.getI18n(), timeExpired);

                    LOGGER.debug(String.format("Authenticated User: %s", getUsername()));
                }else{
                    List<UsersControlAccess> usersControlAccessList = this.usersServices.getUserControlAccessByUser(user);

                    if(usersControlAccessList.stream().findFirst().isPresent()){
                        UsersControlAccess usersControlAccess =  usersControlAccessList.stream().findFirst().get();

                        setSessionVariables(request, usersControlAccess.getToken(), user, usersControlAccess.getI18n(), timeExpired);
                    }else{
                        LOGGER.error(String.format("User [%s] Not Logged or token inactive", getUsername()));
                    }
                }

            }
        }catch (FailedLoginException e) {
            LOGGER.error(String.format("User Error Authenticated %s", e.getMessage()));
        }catch (PolicyContextException e) {
            LOGGER.error(String.format("Error Policy Context %s", e.getMessage()));
        }
        return isLogged;
    }


    private void setSessionVariables(HttpServletRequest request, String token, Users user, String lang, int timeExpired) {
        Gson gson = new Gson();

        user.setLang(lang);
        // Convert a Map into JSON string.
        String menuToJson = gson.toJson(this.usersServices.getMenuFromUserName(user));

        List<RolesByUserNameView> rolesByUserNameViews = this.usersServices.getRoleFromUserName(user);

        List<String> rolesByUser = rolesByUserNameViews.stream()
                .map(RolesByUserNameView::getRole)
                .flatMap(Stream::of)
                .map(role -> role.toLowerCase())
                .collect(Collectors.toList());

        List<String> superUsersRoles = Arrays.asList(Roles.ADMIN.toString().toLowerCase());

        final boolean isRoleExist =  superUsersRoles.stream().anyMatch(allow -> rolesByUser.contains(allow));

        List<OnlyCarriersByUserNameView> carriersByUserNameView = new ArrayList<>();
        if(!isRoleExist){
            carriersByUserNameView = this.carriersServices.getOnlyCarriersByUserName(getUsername());
        }else{
            List<Carriers> carriersList = this.carriersServices.getCarriers();
            carriersByUserNameView.addAll(carriersList.stream().map(carrierViewTransform(user)).collect(Collectors.toList()));
        }

        String carriersByUserJson = gson.toJson(carriersByUserNameView);


        Themes theme = new Themes();
        if (carriersByUserNameView.size()==1 && carriersByUserNameView.get(0).getIdTheme() != null) {
            theme = this.lookAndFeelManagementServices.getThemeById(Long.valueOf(carriersByUserNameView.get(0).getIdTheme()), lang);
        }else {
//		    LOGGER.debug("TEMA POR DEFECTO");
            theme = this.carriersServices.getDefaultTheme(lang);
        }

        String themeJson = gson.toJson(theme);

        String urlRestServices = this.parameterServices.getCoreParameterString(Keys.URL_REST_SERVICES.toString());
        /**
         * Set Session Variables
         */
        request.getSession().setAttribute(Keys.TOKEN.toString(), token);
        request.getSession().setAttribute(Keys.I18N_APP.toString(), lang);
        request.getSession().setAttribute(Keys.CARRIES_BY_USER.toString(), carriersByUserJson);
        request.getSession().setAttribute(Keys.MENU.toString(), menuToJson);
        request.getSession().setAttribute(Keys.USER_EXPIRED_PASSWORD.toString(), user.getPasswordExpired());
        request.getSession().setAttribute(Keys.THEME.toString(), themeJson);
        request.getSession().setAttribute(Keys.URL_REST_SERVICES.toString(), urlRestServices);
        request.getSession().setAttribute(Keys.COMPANY_ID.toString(), user.getCompanies().getCode());
        request.getSession().setMaxInactiveInterval(timeExpired * 60);
        
    }

    @Override
    public boolean logout() throws LoginException {
        LOGGER.debug("Execute Logout j_secure_check");
        return super.logout();
    }


    /**
     * Initialize ejbs
     */
    private void initializedServices(){
        try {
            this.parameterServices = (ICoreParameterServices)
                                        new InitialContext()
                                                .lookup(ICoreParameterServices.ejbMappedName);
            this.tokenSecurityServices = (ITokenSecurityServices)
                    new InitialContext()
                            .lookup(ITokenSecurityServices.ejbMappedName);

            this.usersServices = (IUsersServices)
                    new InitialContext()
                            .lookup(IUsersServices.ejbMappedName);

            this.carriersServices = (ICarriersServices) new InitialContext()
                    .lookup(ICarriersServices.ejbMappedName);

            this.iGenerateShaHash = (IGenerateShaHash) new InitialContext()
                    .lookup(IGenerateShaHash.ejbMappedName);

           this.lookAndFeelManagementServices = (ILookAndFeelManagementServices) new InitialContext()
                    .lookup(ILookAndFeelManagementServices.ejbMappedName);

        }catch (NamingException e){
            LOGGER.error(e.getMessage());
        }
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


    @Override
    protected String createPasswordHash(String username, String password, String digestOption) throws LoginException {
        return this.iGenerateShaHash.generateSha(password, MessageDigestAlgorithms.SHA_512);
    }

    /**
     * Method Functional Programming create new view
     * @param user
     * @return
     */
    private Function<Carriers, OnlyCarriersByUserNameView> carrierViewTransform(Users user) {
        return ca-> {
            OnlyCarriersByUserNameView  onlyCarriersByUserNameView = new OnlyCarriersByUserNameView();

            onlyCarriersByUserNameView.setCode(ca.getCode());
            onlyCarriersByUserNameView.setIdCarrier(String.valueOf(ca.getCode()));
            onlyCarriersByUserNameView.setIdTheme(String.valueOf(ca.getTheme()!=null?ca.getTheme().getThemeId():""));
            onlyCarriersByUserNameView.setName(ca.getName());
            onlyCarriersByUserNameView.setLogin(user.getLogin());
            onlyCarriersByUserNameView.setUserId(user.getCode());
            return onlyCarriersByUserNameView;
        };
    }
}
