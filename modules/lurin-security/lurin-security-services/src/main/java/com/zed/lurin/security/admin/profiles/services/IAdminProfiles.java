package com.zed.lurin.security.admin.profiles.services;

import com.zed.lurin.domain.jpa.CategoriesProfile;
import com.zed.lurin.domain.jpa.Profile;
import com.zed.lurin.domain.jpa.Roles;
import java.util.List;

/**
 *
 * @author Llince
 * @note Refactor Francisco Lujano
 *
 */
public interface IAdminProfiles {


  /**
   * method is responsible for returning all profiles
   * @return List {@link Roles}
   */
  List<Roles> getAllRoles();

  /**
   * method is responsible for returning all visible profiles
   * @param lang
   * @return List {@link Roles}
   */
  List<Roles> getAllVisibleRoles(String lang);

  /**
   * Method that creates the profile in the system
   * @param profile
   * @return New Profile
   */
  Profile createProfile(Profile profile);

  /**
   * Method that updates the profile in the system
   * @param profile
   * @return Update Profile
   */
  Profile updateProfile(Profile profile);

  /**
   * Method that erases a profile
   * @param profileId
   */
  void deleteProfile(long profileId);

  /**
   * Method that lists all profiles
   * @return Profile List
   */
  List<Profile> getProfiles();

  /**
   * Method that profile for name
   *
   * @return Profile
   */
  Profile getProfile(String nameProfile);

  /**
   * Method that lists all category profiles
   *
   * @return Profile List
   */
  List<CategoriesProfile> getCategoryProfiles();

}