package com.zed.lurin.security.users.services.impl;

import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.domain.jpa.view.DataSourceUserTokenView;
import com.zed.lurin.domain.jpa.view.MenuByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.mail.services.IMailServices;
import com.zed.lurin.security.hashing.services.IGenerateShaHash;
import com.zed.lurin.security.users.services.IUsersServices;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.parameter.services.ICoreParameterServices;
import org.hibernate.Hibernate;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Stateless where the Methods that manage the users  are implemented</p>
 * @author Francisco Lujano
 * {@link com.zed.lurin.security.users.services.IUsersServices}
 */
@Stateless
public class UsersServicesImpl implements IUsersServices {


    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName="lurin-security-em")
    private EntityManager em;

    /**
     * Services the status Methods
     */
    @EJB
    ISecurityStatusServices securityStatusServices;

    @EJB
    IGenerateShaHash generateShaHash;
    
    @EJB
    IMailServices mailServices;
    
    /**
     * Core Parameter Services
     */
    @EJB
    ICoreParameterServices iParameterServices;

    /**
     * Method that creates a new access control for a user
     * @param usersControlAccess
     * @return id generated
     */
    @Override
    public UsersControlAccess createUserControlAccess(UsersControlAccess usersControlAccess){
        usersControlAccess.setStatus(this.securityStatusServices.getCategoryActive());
        Users users = this.em.find(Users.class, usersControlAccess.getUser().getCode());
        usersControlAccess.setUser(users);
        this.em.persist(usersControlAccess);
        return usersControlAccess;
    }


    /**
     * Method that a user returns by his username
     * @param userName
     * @return {@link Users}
     */
    @Override
    public Users getUserForUserName(String userName){
        TypedQuery<Users> query =
                this.em.createQuery("SELECT u " +
                        "FROM Users u " +
                        "WHERE u.login = :login", Users.class);

        query.setParameter("login",userName);

        return query.getSingleResult();
    }

    /**
     * Method that validates if the token exists
     * @param token
     * @return true if exist | false is not exist
     */
    @Override
    public boolean isExistToken(String token){
        TypedQuery<UsersControlAccess> query =
                this.em.createQuery("SELECT u " +
                        "FROM UsersControlAccess u join u.status us " +
                        "WHERE u.token = :token AND u.status=:status", UsersControlAccess.class);

        query.setParameter("token",token);
        query.setParameter("status", this.securityStatusServices.getCategoryActive());

        List<UsersControlAccess> usersControlAccesses =query.getResultList();

        return usersControlAccesses.size()>0?true:false;
    }

    /**
     * Method that returns a list of results based on a token
     * @param token
     * @return List to {@link UsersControlAccess}
     */
    @Override
    public List<UsersControlAccess> getUserControlAccessByToken(String token){
        TypedQuery<UsersControlAccess> query =
                this.em.createQuery("SELECT u " +
                        "FROM UsersControlAccess u join u.status us " +
                        "WHERE u.token = :token AND u.status=:status", UsersControlAccess.class);

        query.setParameter("token",token);
        query.setParameter("status", this.securityStatusServices.getCategoryActive());

        return query.getResultList();
    }

    /**
     * method that deactivates a token
     * @param usersControlAccessList
     * @return List token deactive
     */
    @Override
    public List<UsersControlAccess> deactivateTokens(List<UsersControlAccess> usersControlAccessList){
        return usersControlAccessList
                .stream().map(getUsersControlAccessTransform()).collect(Collectors.toList());

    }

    /**
     * Method that updates a user access control object
     * @param usersControlAccess
     * @return
     */
    @Override
    public UsersControlAccess updateUsersControlAccess(UsersControlAccess usersControlAccess){
        return this.em.merge(usersControlAccess);
    }

    /**
     * Method that look for the inactive token in the system to close them
     * @param now
     * @return List to {@link UsersControlAccess}
     */
    @Override
    public void setUserControlAccessInvalidate(Timestamp now, long codeActiveStatus){
        Query query = this.em.createNativeQuery("CALL INACTIVE_TOKEN_FINISHED_LIFE(:now, :statusActive)");
        query.setParameter("now", now);
        query.setParameter("statusActive", codeActiveStatus);
        query.executeUpdate();
        return;
    }

    /**
     * method that update event time for user control access
     * @param usersControlAccessList
     * @return List the user control update
     */
    @Override
    public List<UsersControlAccess> updateEventTime(List<UsersControlAccess> usersControlAccessList){
        return usersControlAccessList
                .stream().map(setEventTimeUserControlAccess()).collect(Collectors.toList());

    }

    /**
     * Method that validates login and password in system
     * @param user
     * @return
     */
    @Override
    public List<Users> validateUserNameAndPassword(Users user){
        TypedQuery<Users> query =
                this.em.createQuery("SELECT u FROM Users u " +
                        "WHERE u.login = :login AND u.password=:password", Users.class);

        query.setParameter("login",user.getLogin());
        query.setParameter("password", this.generateShaHash.generateSha512(user.getPassword()));

        List<Users> users = query.getResultList();

        return users;
    }

    /**
     * method that validates login and token in system for logout
     * @param user
     * @param token
     * @return
     */
    @Override
    public List<UsersControlAccess> validateUserNameAndToken(Users user, String token){
        TypedQuery<UsersControlAccess> query =
                this.em.createQuery("SELECT u " +
                        "FROM UsersControlAccess u join u.user us join u.status ust " +
                        "WHERE u.token = :token AND u.user=:user AND u.status=:status", UsersControlAccess.class);

        user = this.getUserForUserName(user.getLogin());

        query.setParameter("token",token);
        query.setParameter("user", user);
        query.setParameter("status", this.securityStatusServices.getCategoryActive());

        return query.getResultList();
    }


    /**
     * method that return datasources asigned by user
     * @param user
     * @param token
     * @param carrierId
     * @return
     */
    @Override
    public List<DataSourceUserTokenView> getDataSourceFromUserAndToken(Users user, String token, long carrierId){
        TypedQuery<DataSourceUserTokenView> query =
                this.em.createQuery("SELECT vud " +
                        "FROM DataSourceUserTokenView vud " +
                        "join vud.statusUserCtrlAccess ust " +
                        "WHERE vud.token = :token AND vud.login=:login " +
                        "AND vud.carrierId=:carrierId AND vud.statusUserCtrlAccess=:status",
                        DataSourceUserTokenView.class);

        query.setParameter("token",token);
        query.setParameter("login", user.getLogin());
        query.setParameter("carrierId", carrierId);
        query.setParameter("status", this.securityStatusServices.getCategoryActive());

        return query.getResultList();
    }

    /**
     * method that return menu for username
     * @param user
     * @return
     */
    @Override
    public List<MenuByUserNameView> getMenuFromUserName(Users user){
        TypedQuery<MenuByUserNameView> query =
                this.em.createQuery("SELECT m " +
                        "FROM MenuByUserNameView m " +
                        "WHERE m.login=:login",
                        MenuByUserNameView.class);

        query.setParameter("login", user.getLogin());

        return query.getResultList().stream()
                .map(menuByUserNameTransform(user.getLang()))
                .collect(Collectors.toList());
    }


    /**
     * method that return roles for username
     * @param user
     * @return
     */
    @Override
    public List<RolesByUserNameView> getRoleFromUserName(Users user){
        TypedQuery<RolesByUserNameView> query =
                this.em.createQuery("SELECT m " +
                                "FROM RolesByUserNameView m " +
                                "WHERE m.login=:login",
                        RolesByUserNameView.class);

        query.setParameter("login", user.getLogin());

        return query.getResultList();
    }

    /**
     * Method that returns a list of results based on a user
     *
     * @param user
     * @return List to {@link UsersControlAccess}
     */
    @Override
    public List<UsersControlAccess> getUserControlAccessByUser(Users user) {
        TypedQuery<UsersControlAccess> query =
                this.em.createQuery("SELECT u " +
                        "FROM UsersControlAccess u join u.status us " +
                        "WHERE u.user = :user AND u.status=:status " +
                        "ORDER BY u.code DESC ", UsersControlAccess.class);

        query.setParameter("user",user);
        query.setParameter("status", this.securityStatusServices.getCategoryActive());

        return query.getResultList();
    }

    /**
     * Method Functional Programming
     * @return
     */
    private Function<UsersControlAccess, UsersControlAccess> getUsersControlAccessTransform() {
        return uca->{
            uca.setStatus(this.securityStatusServices.getCategoryInactive());
            uca.setLogoutTime(new Timestamp((new Date()).getTime()));
            this.updateUsersControlAccess(uca);
            return uca;
        };
    }

    /**
     * Method Functional Programming
     * @return
     */
    private Function<UsersControlAccess, UsersControlAccess> setEventTimeUserControlAccess() {
        return uca->{
            uca.setUpdateEventTime(new Timestamp((new Date()).getTime()));
            this.updateUsersControlAccess(uca);
            return uca;
        };
    }

    /**
     * Method Functional Programming for select default languajes
     * @param lang
     * @return
     */
    private Function<MenuByUserNameView, MenuByUserNameView> menuByUserNameTransform(String lang) {
        return i18n-> {
            switch (lang.toLowerCase()) {
                case "es":
                    i18n.setLangAssigned(i18n.getLangES());
                    break;
                case "en":
                    i18n.setLangAssigned(i18n.getLangEN());
                    break;
                case "pr":
                    i18n.setLangAssigned(i18n.getLangPR());
                    break;
                default:
                    i18n.setLangAssigned(i18n.getDefaultI18n());
                    break;
            }
            return i18n;
        };
    }

     /**
     * Method that validates the expiration of the user password
     */
    @Override
    public void validateExpirationUsersPassword() {
     Query query = this.em.createNativeQuery("CALL SP_VALID_EXPIRED_USER_PASSWORD()");
        query.executeUpdate();
        Query query2 = this.em.createNativeQuery("CALL SP_VALID_EXPIRED_URL()");
        query2.executeUpdate();
        return;       
    }

      /**
     * Method that resets the user's password
     * @param user
     */
    @Override
    public void resetUsersPassword(Users user) {
        user.setPassword(this.generateShaHash.generateSha512(user.getPassword()));
        this.em.merge(user);
        TypedQuery<ResetUserPass> query =
                this.em.createQuery("UPDATE ResetUserPass reset " +
                        "SET reset.expiredUrl='Y' " +
                        "WHERE reset.idUser = :idUser" 
                        , ResetUserPass.class);
       query.setParameter("idUser",user.getCode());
        int updateCount = query.executeUpdate();
        if (updateCount > 0) {
         System.out.println("Done...");
        }
    }

    @Override
    public void sendresetUsersPassword(Users user,String url) {
        ResetUserPass reset = new ResetUserPass();
        reset.setIdUser(user.getCode());
        reset.setUrl(url);
        reset.setExpiredUrl("N");
        reset.setCreateUrl(new Date());
        this.em.persist(reset);
        mailServices.send(user.getEmail(), "reset password", url);
        return;  
       
    }

    @Override
    public ResetUserPass getResetUserPass(long idUser) {
        TypedQuery<ResetUserPass> query =
                this.em.createQuery("SELECT u " +
                        "FROM ResetUserPass u " +
                        "WHERE u.idUser = :idUser  AND u.expiredUrl= :expiredUrl", ResetUserPass.class);

        query.setParameter("idUser",idUser);
        query.setParameter("expiredUrl","N");

        return query.getSingleResult();
    }
       /**
     * Method that resets the user's password
     * @param user
     */
    @Override
    public void setUsersPassword(Users user) {
        user.setPassword(this.generateShaHash.generateSha512(user.getPassword()));
        this.em.merge(user);
    }

    /**
     * Method that a user returns by his username
     * @param userName
     * @return {@link Users}
     */
    @Override
    public boolean isUserExitsForUserName(String userName){
        TypedQuery<Users> query =
                this.em.createQuery("SELECT u " +
                        "FROM Users u " +
                        "WHERE u.login = :login", Users.class);

        query.setParameter("login",userName);

        return query.getResultList().size()>0?true:false;
    }
    
    @Override
    public String sendRecoverPasswordMail(String email) {
 	   try {
 		   TypedQuery<Users> query =
 	                this.em.createQuery("SELECT u " +
 	                        "FROM Users u " +
 	                        "WHERE u.email = :email AND u.status=:status", Users.class);

 	        query.setParameter("email",email);
 	        query.setParameter("status", this.securityStatusServices.getCategoryActive());

 	        List<Users> users = query.getResultList();
 	        
 	        if (users.size() > 0) {
 	        	Users user = users.get(0);
 	        	if (user.getPassword()!=null) {
 	        		String body = this.iParameterServices.getCoreParameterString(
 		    		   Keys.RECOVERPASSWORD_MAIL_BODY.toString());
 	        		
 	        		String personalName = user.getFirstName() + " " + user.getLastName();
 	        		body = String.format(body, personalName, this.generateShaHash.generatePlainText(user.getPassword()));
 	        		this.mailServices.send(user.getEmail(), "Recover User Password", body);
 	        		return "001"; //Exitoso
 	        	}else {
 		        	return "003"; //LDAP
 	        	}

 	        }else {
 	        	return "002"; //inválido
 	        }
 	        
 	   } catch(Exception ex) {
 		   ex.printStackTrace();
 		   return "002"; //inválido
 	   }

    }

}
