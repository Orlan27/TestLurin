package com.zed.lurin.security.admin.profiles.services.impl;

import com.zed.lurin.domain.jpa.*;
import com.zed.lurin.security.admin.profiles.services.IAdminProfiles;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Llince
 * @note Refactor Francisco Lujano
 */
@Stateless
public class IAdminProfilesImpl implements IAdminProfiles {

    /**
     * Entity Manager reference.
     */
    @PersistenceContext(unitName = "lurin-security-em")
    private EntityManager em;


    /**
     * method is responsible for returning all profiles
     * @return List {@link Roles}
     */
    @Override
    public List<Roles> getAllRoles() {
        TypedQuery<Roles> query = this.em.createQuery("SELECT r FROM Roles r", Roles.class);
        return query.getResultList();
    }

    /**
     * method is responsible for returning all visible profiles
     *
     * @param lang
     * @return List {@link Roles}
     */
    @Override
    public List<Roles> getAllVisibleRoles(String lang) {
        TypedQuery<Roles> query = this.em.createQuery("SELECT r FROM Roles r WHERE r.visible=true", Roles.class);

        return query.getResultList().stream()
                .map(rolesByLanguagesTransform(lang))
                .collect(Collectors.toList());
    }

    /**
     * Method that creates the profile in the system
     *
     * @param profile
     * @return New Profile
     */
    @Override
    public Profile createProfile(Profile profile) {


        Set<ProfileRoles> profileRolesTemp  =  new HashSet<>();
        profileRolesTemp.addAll(profile.getProfileRoles());

        profile.setProfileRoles(new HashSet<>());

        this.em.persist(profile);

        profileRolesTemp.stream().forEach(p->p.getId().setProfileId(profile.getProfileId()));

        profileRolesTemp.stream().forEach(pr -> this.em.persist(pr));

        profile.setProfileRoles(profileRolesTemp);

        return profile;
    }

    /**
     * Method that updates the profile in the system
     *
     * @param profile
     * @return Update Profile
     */
    @Override
    public Profile updateProfile(Profile profile) {

        List<Users> users= this.getUserCarriersForRoles(profile.getProfileId());

        users.stream().forEach(u->{
            u.getUserCarriers().stream().forEach(uc->{
                this.deleteUserCarrierRoles(uc, true);
            });
        });

        this.em.merge(profile);

        users.stream().forEach(u->{

            profile.getProfileRoles().stream().forEach(pr->{
                u.getUserCarriers().stream().forEach(uc->{
                    UserCarriersRoles userCarriersRoles = new UserCarriersRoles();
                    Roles roles = new Roles();
                    roles.setCode(pr.getId().getRoleId());

                    userCarriersRoles.setRole(roles);
                    userCarriersRoles.setUserCarriers(uc);
                    this.em.persist(userCarriersRoles);
                });
            });


        });

        return profile;
    }


    /**
     * Method for search UserCarrier and User Carrier roles
     * @param userCarriers
     */
    private void deleteUserCarrierRoles(UserCarriers userCarriers, boolean isFlush) {
        TypedQuery<UserCarriersRoles> query =
                this.em.createQuery("SELECT ucr " +
                        "FROM UserCarriersRoles ucr join ucr.userCarriers uc " +
                        "WHERE uc.code=:code", UserCarriersRoles.class);

        query.setParameter("code", userCarriers.getCode());

        /**
         * Result to delete
         */
        List<UserCarriersRoles> userCarriersRoles = query.getResultList();

        userCarriersRoles.stream().forEach(ucr->this.em.remove(ucr));
        if(isFlush) {
            this.em.flush();
        }

    }


    public List<Users> getUserCarriersForRoles(long profileId){
        TypedQuery<Users> query =
                this.em.createQuery("SELECT u FROM Users u " +
                        "join u.profile p " +
                        "WHERE p.profileId=:profileId", Users.class);

        query.setParameter("profileId", profileId);

        return query.getResultList();
    }

    /**
     * Method that erases a profile
     *
     * @param profileId
     */
    @Override
    public void deleteProfile(long profileId) {
        Profile profile = this.em.find(Profile.class, profileId);
        this.em.remove(profile);
    }

    /**
     * Method that lists all profiles
     *
     * @return Profile List
     */
    @Override
    public List<Profile> getProfiles() {
        TypedQuery<Profile> query = this.em.createQuery("SELECT p FROM Profile p join p.categoriesProfile WHERE p.visible=true", Profile.class);

        List<Profile> profiles = query.getResultList();

        profiles.stream().forEach(p->{
            this.em.detach(p);
            p.getProfileRoles().stream().forEach(pr->{
                this.em.detach(pr);
                pr.setProfile(null);
            });
        });

        return profiles;
    }

    /**
     * Method that profile for name
     *
     * @return Profile
     */
    @Override
    public Profile getProfile(String nameProfile) {
        TypedQuery<Profile> query = this.em.createQuery("SELECT p FROM Profile p WHERE p.name=:name", Profile.class);
        query.setParameter("name", nameProfile);

        Profile profile = query.getSingleResult();

        return profile;
    }


    /**
     * Method that lists category profiles
     *
     * @return Profile List
     */
    @Override
    public List<CategoriesProfile> getCategoryProfiles() {
        TypedQuery<CategoriesProfile> query = this.em.createQuery("SELECT p FROM CategoriesProfile p " +
                "ORDER BY p.orderLocation ASC", CategoriesProfile.class);
        return query.getResultList();
    }


    /**
     * Method Functional Programming for select default languajes
     * @param lang
     * @return
     */
    private Function<Roles, Roles> rolesByLanguagesTransform(String lang) {
        return role-> {
            switch (lang.toLowerCase()) {
                case "es":
                    role.getDictionary().setLangAssigned(role.getDictionary().getEs());
                    break;
                case "en":
                    role.getDictionary().setLangAssigned(role.getDictionary().getEn());
                    break;
                case "pr":
                    role.getDictionary().setLangAssigned(role.getDictionary().getPr());
                    break;
                default:
                    role.getDictionary().setLangAssigned(role.getDictionary().getDefaultI18n());
                    break;
            }
            return role;
        };
    }

}
