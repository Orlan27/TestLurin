package com.zed.admin.services.impl;


import com.google.gson.Gson;
import com.zed.admin.services.IAdminServices;
import com.zed.carriers.services.ICarriersServices;
import com.zed.lurin.domain.dto.MessageCustomDTO;
import com.zed.lurin.domain.dto.UserPasswordChangeDTO;
import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.domain.jpa.view.OnlyCarriersByUserNameView;
import com.zed.lurin.domain.jpa.view.RolesByUserNameView;
import com.zed.lurin.domain.jpa.view.UserExpiredPasswordView;
import com.zed.lurin.domain.services.ISecurityStatusServices;
import com.zed.lurin.security.hashing.services.IGenerateShaHash;
import com.zed.lurin.security.keys.Keys;
import com.zed.lurin.security.users.services.IUsersServices;
import com.zed.lurin.security.admin.profiles.services.IAdminProfiles;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;

/**
 * <p>Stateless where the methods that manage the administration are implemented</p>
 * @author Francisco Lujano
 */
@Stateless
public class AdminServicesImpl implements IAdminServices {

	 /*
     * Logger util
     */
    private static Logger LOGGER = Logger.getLogger(AdminServicesImpl.class.getName());
    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName="lurin-security-em")
    private EntityManager em;

    /**
     * Generating hash password
     */
    @EJB
    IGenerateShaHash generateShaHash;

    /**
     * Services the status Methods
     */
    @EJB
    ISecurityStatusServices securityStatusServices;

    /**
     * Security Services
     */
    @EJB
    IUsersServices iUsersServices;

    /**
     * Carrier Services
     */
    @EJB
    ICarriersServices iCarriersServices;

    @EJB
	IAdminProfiles IAdminProfiles;

    /**
     *
     * validate if username exists in database
     *
     * @param userName
     * @return
     */
    @Override
    public boolean isExistUserName(String userName){
        TypedQuery<Users> query =
                this.em.createQuery("SELECT u " +
                        "FROM Users u " +
                        "WHERE u.login = :login", Users.class);

        query.setParameter("login",userName);

        return query.getResultList().size()>0?true:false;
    }

    /**
     * method return list datasource active
     * @return
     */
    @Override
    public List<DataSources> getDataSources(){
        TypedQuery<DataSources> query =
                this.em.createQuery("SELECT u " +
                        "FROM DataSources u join u.status us " +
                        "WHERE u.status=:status", DataSources.class);

        query.setParameter("status", this.securityStatusServices.getCategoryActive());

        return query.getResultList();
    }

    /**
     * method return list carriers active
     *
     * @return
     */
    @Override
    public List<Carriers> getCarriers() {
        TypedQuery<Carriers> query =
                this.em.createQuery("SELECT u " +
                        "FROM Carriers u join u.status us " +
                        "WHERE u.status=:status", Carriers.class);

        query.setParameter("status", this.securityStatusServices.getCategoryActive());
        return query.getResultList();
    }

    /**
     * method return list roles active
     *
     * @return
     */
    @Override
    public List<Roles> getRoles() {
        TypedQuery<Roles> query =
                this.em.createQuery("SELECT u " +
                        "FROM Roles u ", Roles.class);

        return query.getResultList();
    }

    /**
     * method return true if password expired
     * @param userName
     * @return UserExpiredPasswordView Object
     */
    @Override
    public List<UserExpiredPasswordView> getPasswordExpiredForUserName(String userName) {
        TypedQuery<UserExpiredPasswordView> query =
                this.em.createQuery("SELECT u " +
                        "FROM UserExpiredPasswordView u " +
                        "WHERE login=:login", UserExpiredPasswordView.class);

        query.setParameter("login", userName);

        return query.getResultList();
    }


    /**
     *
     * creating user
     *
     * @param users
     * @return
     */
    @Override
    public Users createUsers(Users users){
        users.getUserCarriers().stream().forEach(uc->{
            uc.setUsers(users);
            uc.getUserCarriersRoles().stream().forEach(ucr->ucr.setUserCarriers(uc));
        });

        /**
         * Update persist users
         */
        if(users.getPassword()!=null && users.getPassword().length()>0) {
            users.setPassword(this.generateShaHash.generateSha512(users.getPassword()));
        }

        this.em.persist(users);

        return users;
    }
    
    /**
    *
    * creating user
    *
    * @param users
    * @return
    */
   @Override
   public Users createUsersAdminLocal(Users users){

	   /*users.getUserCarriers().stream().forEach(uc->{
           uc.setUsers(users);
           uc.getUserCarriersRoles().stream().forEach(ucr->ucr.setUserCarriers(uc));
       });*/
		/**
		 * Get profile
		 */

		Profile profile = this.IAdminProfiles.getProfile("PROFILE_ADMIN_LOCAL");
		users.setProfile(profile);
		
		/**
		 * To assign roles and carriers
		 */
		Set<UserCarriers> userCarriersSet = new HashSet<>();
		Set<UserCarriersRoles> userCarriersRolesSet = new HashSet<>();

		/**
		 * Set userCarriers and roles
		 */
		UserCarriers userCarriers =  users.getUserCarriers().iterator().next();
		userCarriers.setUsers(users);
		//userCarriers.setCarriers(carrier);
		userCarriers.setUserCarriersRoles(userCarriersRolesSet);

		/**
		 * Set user carrier
		 */
		userCarriersSet.add(userCarriers);

		/**
		 * Roles Object
		 */

		profile.getProfileRoles().stream().forEach(pr ->{

			UserCarriersRoles userCarriersRoles =  new UserCarriersRoles();
			userCarriersRoles.setUserCarriers(userCarriers);
			userCarriersRoles.setRole(pr.getRoles());

			userCarriersRolesSet.add(userCarriersRoles);
		});


		users.setUserCarriers(userCarriersSet);

       /**
        * Update persist users
        */
       if(users.getPassword()!=null && users.getPassword().length()>0) {
           users.setPassword(this.generateShaHash.generateSha512(users.getPassword()));
       }

       this.em.persist(users);

       return users;
   }

    /**
     *
     * update user
     *
     * @param users
     * @return
     */
    @Override
    public Users updateUsers(Users users){

        /**
         * Delete UserCarrier and UserCarrierRoles
         */
        this.deleteUserCarriersAndCarrierRoles(users, true);

        users.getUserCarriers().stream().forEach(uc->{
            uc.setUsers(users);
            uc.getUserCarriersRoles().stream().forEach(ucr->ucr.setUserCarriers(uc));
        });

        /**
         * Update persist users
         */
        if(users.getPassword()!=null && users.getPassword().length()>0) {
            users.setPassword(this.generateShaHash.generateSha512(users.getPassword()));
        }

        this.em.merge(users);

        return users;
    }

    /**
    *
    * update user
    *
    * @param users
    * @return
    */
   @Override
   public Users updateUsersAdminLocal(Users users){
	   
	   /**
        * Delete UserCarrier and UserCarrierRoles
        */
	   this.deleteUserCarriersAndCarrierRoles(users, true);
       
		/**
		 * Get profile
		 */
		Profile profile = this.IAdminProfiles.getProfile("PROFILE_ADMIN_LOCAL");
		users.setProfile(profile);
		
		/**
		 * To assign roles and carriers
		 */
		Set<UserCarriers> userCarriersSet = new HashSet<>();
		Set<UserCarriersRoles> userCarriersRolesSet = new HashSet<>();

		/**
		 * Set userCarriers and roles
		 */
		UserCarriers userCarriers =  users.getUserCarriers().iterator().next();
		userCarriers.setUsers(users);
		//userCarriers.setCarriers(carrier);
		userCarriers.setUserCarriersRoles(userCarriersRolesSet);

		/**
		 * Set user carrier
		 */
		userCarriersSet.add(userCarriers);

		/**
		 * Roles Object
		 */

		profile.getProfileRoles().stream().forEach(pr ->{

			UserCarriersRoles userCarriersRoles =  new UserCarriersRoles();
			userCarriersRoles.setUserCarriers(userCarriers);
			userCarriersRoles.setRole(pr.getRoles());

			userCarriersRolesSet.add(userCarriersRoles);
		});


		users.setUserCarriers(userCarriersSet);

       /**
        * Update persist users
        */
       if(users.getPassword()!=null && users.getPassword().length()>0) {
           users.setPassword(this.generateShaHash.generateSha512(users.getPassword()));
       }

       this.em.merge(users);

       return users;
   }
   
    /**
     * delete user
     *
     * @param userCode
     * @return
     */
    @Override
    public void deleteUsers(long userCode) {
        /**
         * Delete UserCarrier and UserCarrierRoles
         */
        Users users = this.em.find(Users.class, userCode);

        this.em.remove(users);

    }


    /**
     * method return list users active
     *
     * @return
     */
    @Override
    public List<Users> getUsers(String token, boolean isAdminLocal) {
    	String hqlQuery = "SELECT u " +
                "FROM Users u join u.profile p where u.status=:status";
    	if (isAdminLocal) {
    		hqlQuery = "SELECT u " +
                    "FROM Users u join u.profile p where u.status=:status and p.name = 'PROFILE_ADMIN_LOCAL'";
    	}
        TypedQuery<Users> query =
                this.em.createQuery(hqlQuery, Users.class);
        query.setParameter("status", this.securityStatusServices.getCategoryActive());

        List<Users> users = query.getResultList();

        users.stream().forEach(u->{
            this.em.detach(u);

            u.getProfile().getProfileRoles().stream().forEach(pr->{
                this.em.detach(pr);
                pr.setProfile(null);
            });
            u.setPassword(this.generateShaHash.generatePlainText(u.getPassword()));
        });

        token = token.substring(Keys.AUTHENTICATION_SCHEME.toString().length()).trim();

        List<UsersControlAccess> usersControlAccesses = this.iUsersServices.getUserControlAccessByToken(token);

        if(usersControlAccesses.stream().findFirst().isPresent()) {
            final Users user = usersControlAccesses.stream().findFirst().get().getUser();

            List<RolesByUserNameView> rolesByUserNameViews = this.iUsersServices.getRoleFromUserName(user);

            List<String> rolesByUser = rolesByUserNameViews.stream()
                    .map(RolesByUserNameView::getRole)
                    .flatMap(Stream::of)
                    .map(role -> role.toLowerCase())
                    .collect(Collectors.toList());

            List<String> superUsersRoles = Arrays.asList(com.zed.lurin.security.keys.Roles.ADMIN_LOCAL.toString().toLowerCase());

            final boolean isRoleExist =  superUsersRoles.stream().anyMatch(allow -> rolesByUser.contains(allow));
            if(isRoleExist){

                List<Users> usersNew =  new ArrayList<>();
                List<Users> finalUsers = users;
                user.getUserCarriers().stream().forEach(userCarriersLogueado -> {
                    userCarriersLogueado.getCarriers().getCode(); //carrier del user loguedo

                    finalUsers.stream().forEach(userSystem -> {
                        userSystem.getUserCarriers().stream().forEach(userCarriersLocal -> {

                            if(userCarriersLocal.getCarriers().getCode()==userCarriersLogueado.getCarriers().getCode()){
                                if(userSystem.getCode()!=userCarriersLogueado.getUsers().getCode()){
                                    usersNew.add(userSystem);
                                }

                            }
                        });
                    });

                });

                users = usersNew;
            }

        }


        return users;
    }


    /**
     * method return list users active
     *
     * @return
     */
    @Override
    public List<UserCarriers> getUsersCarriers() {

        TypedQuery<UserCarriers> query =
                this.em.createQuery("SELECT u " +
                        "FROM UserCarriers u", UserCarriers.class);

        return query.getResultList();
    }

    /**
     * Method for search UserCarrier and User Carrier roles
     * @param users
     */
    private void deleteUserCarriersAndCarrierRoles(Users users, boolean isFlush) {
        TypedQuery<UserCarriers> queryUserCarriers =
                this.em.createQuery("SELECT u " +
                        "FROM UserCarriers u join u.users us " +
                        "WHERE u.users=:users", UserCarriers.class);

        queryUserCarriers.setParameter("users", users);

        /**
         * Result to delete
         */
        List<UserCarriers> userCarriers = queryUserCarriers.getResultList();

        userCarriers.stream().forEach(userCarrier->this.em.remove(userCarrier));
        if(isFlush) {
            this.em.flush();
        }

    }

    /**
     * Method for delete UserCarrier and User Carrier roles
     * @param carrier
     */
    private void deleteCarrierRoles(UserCarriers carrier) {
        TypedQuery<UserCarriersRoles> queryUserCarriersRoles =
                this.em.createQuery("SELECT u " +
                        "FROM UserCarriersRoles u join u.userCarriers us " +
                        "WHERE u.userCarriers=:userCarriers", UserCarriersRoles.class);

        queryUserCarriersRoles.setParameter("userCarriers", carrier);

        /**
         * Delete UserCarrier Roles
         */
        queryUserCarriersRoles.getResultList().stream().forEach(carrierRoles -> this.em.remove(carrierRoles));

        /**
         * Delete UserCarrier
         */
        //this.em.remove(carrier);
    }

    /**
     * Method for update password from user
     * @param userPasswordChangeDTO
     * @return
     */
    @Override
    public MessageCustomDTO changePasswordForUser(UserPasswordChangeDTO userPasswordChangeDTO){
        MessageCustomDTO messageCustomDTO = new MessageCustomDTO();
        Users users =  this.em.find(Users.class, userPasswordChangeDTO.getCode());

        if(users!=null && users.getPassword()!=null){
        	if (userPasswordChangeDTO.getOldPassword() != null && userPasswordChangeDTO.getNewPassword() != null) {
	
	            if(users.getPassword().equals(this.generateShaHash.generateSha512(userPasswordChangeDTO.getOldPassword()))){
	
	                users.setPassword(this.generateShaHash.generateSha512(userPasswordChangeDTO.getNewPassword()));
	                this.em.merge(users);
	                messageCustomDTO.setSuccess(true);
	            }else{
	                messageCustomDTO.setSuccess(false);
	                messageCustomDTO.setKeyMessage(MessageCustomDTO.MESSAGES_KEYS.USER_OLD_PASSWORD_INCORRECT.toString());
	                messageCustomDTO.setCodeError(MessageCustomDTO.MESSAGES_CODE.USER_OLD_PASSWORD_INCORRECT.toString());
	
	            }
	        }
        	
        }else{
            messageCustomDTO.setSuccess(false);
            messageCustomDTO.setKeyMessage(MessageCustomDTO.MESSAGES_KEYS.USER_IS_LPAD.toString());
            messageCustomDTO.setCodeError(MessageCustomDTO.MESSAGES_CODE.USER_IS_LPAD.toString());
        }
        if (users!=null && userPasswordChangeDTO.getEmail() != null) {
        	users.setEmail(userPasswordChangeDTO.getEmail());
            this.em.merge(users);
            messageCustomDTO.setSuccess(true);
        }

        return messageCustomDTO;
    }

    /**
    *
    * Get User in database by username
    *
    * @param userName
    * @return
    */
   @Override
   public Users getUserByUserName(String userName){
       TypedQuery<Users> query =
               this.em.createQuery("SELECT u " +
                       "FROM Users u " +
                       "WHERE u.login = :login", Users.class);

       query.setParameter("login",userName);

       return query.getSingleResult();
   }



}
