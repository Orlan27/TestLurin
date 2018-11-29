/*
 *
 * LurinStorageManagmentContainer actions
 *
 */
import Axios from 'axios';

// import { LURIN_MASTER_API } from '../../Api/config';

// API URL
const url = `${window.currentApiUrl}security/`;
import {
  LOAD_PROFILES_ACTION_SUCCESS,
  LOAD_PROFILES_ACTION_FAILURE,
  SHOW_PROFILES_FORM_ACTION,
  CREATE_PROFILES_ACTION_SUCCESS,
  CREATE_PROFILES_ACTION_FAILURE,
  UPDATE_PROFILES_ACTION_SUCCESS,
  UPDATE_PROFILES_ACTION_FAILURE,
  DELETE_PROFILES_ACTION_SUCCESS,
  DELETE_PROFILES_ACTION_FAILURE,
  LOAD_MODULES_ACTION_SUCCESS,
  LOAD_MODULES_ACTION_FAILURE,
  INIT_MESSAGE_ACTION,
  LOAD_PROFILES_CATEGORIES_ACTION_SUCCESS,
  LOAD_PROFILES_CATEGORIES_ACTION_FAILURE
} from './constants';


export const loadProfilesCategoriesSuccess = (profilesCategoriesData) => ({
  type: LOAD_PROFILES_CATEGORIES_ACTION_SUCCESS,
  profilesCategoriesData
});


export function loadProfilesCategoriesFailure(error) {
  return {
    type: LOAD_PROFILES_CATEGORIES_ACTION_FAILURE,
    error
  };
}

export const loadProfilesSuccess = (profilesData) => ({
  type: LOAD_PROFILES_ACTION_SUCCESS,
  profilesData
});


export const loadModulesSuccess = (moduleData) => ({
  type: LOAD_MODULES_ACTION_SUCCESS,
  moduleData
});


export function loadProfilesFailure(error) {
  return {
    type: LOAD_PROFILES_ACTION_FAILURE,
    error
  };
}

export function loadModulesFailure(error) {
  return {
    type: LOAD_MODULES_ACTION_FAILURE,
    error
  };
}

export function loadProfilesCategories() {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${url}getCategoryProfiles`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken } })
      .then(response => {
        dispatch(loadProfilesCategoriesSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadProfilesCategoriesFailure(error));
        throw (error);
      });
  
  };
}

export function loadProfiles() {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${url}getProfiles`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken } })
      .then(response => {
        dispatch(loadProfilesSuccess(response.data));
        dispatch(showForm(false));
      })
      .catch(error => {
        dispatch(loadProfilesFailure(error));
        throw (error);
      });

  };
}

export function loadModules() {
  return (dispatch) => {
    // Returns a promise
    // return Axios.get(`${url}getSourceTypes`, { headers: { 'Content-Type': 'application/json',
    //   Authorization: currentToken } })
    //   .then(response => {
    //     dispatch(loadModulesSuccess(response.data));
    //   })
    //   .catch(error => {
    //     dispatch(loadModulesFailure(error));
    //     throw (error);
    //   });
    const data = [
      {
        moduleId: 1,
        name: 'Modulo 1',
        module: 'Modulo'
      },
      {
        moduleId: 2,
        name: 'Modulo 2',
        module: 'Modulo'
      }
    ];
    dispatch(loadModulesSuccess(data));
  };
}

export function showForm(hasShowForm) {
  return {
    type: SHOW_PROFILES_FORM_ACTION,
    hasShowForm
  };
}

export function createProfilesSuccess(profilesData) {
  return {
    type: CREATE_PROFILES_ACTION_SUCCESS,
    profilesData
  };
}

export function createProfilesFailure(error) {
  return {
    type: CREATE_PROFILES_ACTION_FAILURE,
    error
  };
}

export function createProfiles(newProfile) {
  const data = JSON.stringify(newProfile);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}createProfile`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(createProfilesSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadProfiles());
        dispatch(loadProfilesCategories());
      })
      .catch(error => {
        dispatch(createProfilesFailure(error));
        throw (error);
      });
}

export function updateProfilesSuccess(profilesData) {
  return {
    type: UPDATE_PROFILES_ACTION_SUCCESS,
    profilesData
  };
}

export function updateProfilesFailure(error) {
  return {
    type: UPDATE_PROFILES_ACTION_FAILURE,
    error
  };
}

export function updateProfiles(profileToUpdate) {
  const data = JSON.stringify(profileToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}updateProfile`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(updateProfilesSuccess(response.data));
        dispatch(loadProfiles());
        dispatch(loadProfilesCategories());
      })
      .catch(error => {
        dispatch(updateProfilesFailure(error));
        throw (error);
      });
}

export function deleteProfilesSuccess() {
  return {
    type: DELETE_PROFILES_ACTION_SUCCESS
  };
}
export function deleteProfilesFailure(error) {
  return {
    type: DELETE_PROFILES_ACTION_FAILURE,
    error
  };
}

export function deleteProfiles(profileId) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}deleteProfile/${profileId}`, {
       headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(deleteProfilesSuccess());
        dispatch(showForm(false));
        dispatch(loadProfiles());
        dispatch(loadProfilesCategories());
      })
      .catch(error => {
        dispatch(deleteProfilesFailure(error));
        throw (error);
      });
}

export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}
