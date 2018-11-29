/*
 *
 * LurinUserManagmentContainer actions
 *
 */
import Axios from 'axios';

// import { LURIN_MASTER_API } from '../../Api/config';

// API URL
const url = `${window.currentApiUrl}administrator/users/`;
const urlPost = `${window.currentApiUrl}administrator/`;
const urlSecurity = `${window.currentApiUrl}security/`;
//const currentToken = null;
import {
  LOAD_USERS_CARRIER_ACTION_SUCCESS,
  LOAD_USERS_CARRIER_ACTION_FAILURE,
  LOAD_USERS_ACTION_SUCCESS,
  LOAD_USERS_ACTION_FAILURE,
  LOAD_USERS_LDAP_ACTION_SUCCESS,
  LOAD_USERS_LDAP_ACTION_FAILURE,
  SHOW_USERS_FORM_ACTION,
  CREATE_USERS_ACTION_SUCCESS,
  CREATE_USERS_ACTION_FAILURE,
  UPDATE_USERS_ACTION_SUCCESS,
  UPDATE_USERS_ACTION_FAILURE,
  DELETE_USERS_ACTION_SUCCESS,
  DELETE_USERS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';



export const loadUsersCarrierSuccess = (userCarrierData) => ({
  type: LOAD_USERS_CARRIER_ACTION_SUCCESS,
  userCarrierData
});


export function loadUsersCarrierFailure(error) {
  return {
    type: LOAD_USERS_CARRIER_ACTION_FAILURE,
    error
  };
}

export const loadUsersSuccess = (usersData) => ({
  type: LOAD_USERS_ACTION_SUCCESS,
  usersData
});


export function loadUsersFailure(error) {
  return {
    type: LOAD_USERS_ACTION_FAILURE,
    error
  };
}

export function loadUsers(isAdminLocal) {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${url}get`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken,isAdminLocal :isAdminLocal } })
      .then(response => {
        dispatch(loadUsersSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadUsersFailure(error));
        throw (error);
      });
  };
}

export const loadUsersLdapSuccess = (usersLDAP) => ({
  type: LOAD_USERS_LDAP_ACTION_SUCCESS,
  usersLDAP
});


export function loadUsersLdapFailure(error) {
  return {
    type: LOAD_USERS_LDAP_ACTION_FAILURE,
    error
  };
}

export function loadUsersLdap() {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${urlSecurity}getLdapUsers`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken } })
      .then(response => {
        dispatch(loadUsersLdapSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadUsersLdapFailure(error));
        throw (error);
      });
  };
}

export function loadUsersCarriers() {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${url}carrier/get`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken } })
      .then(response => {
        dispatch(loadUsersCarrierSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadUsersCarrierFailure(error));
        throw (error);
      });
  };
}


export function showForm(hasShowForm) {
  return {
    type: SHOW_USERS_FORM_ACTION,
    hasShowForm
  };
}

export function createUsersSuccess(usersData) {
  return {
    type: CREATE_USERS_ACTION_SUCCESS,
    usersData
  };
}

export function createUsersFailure(error) {
  return {
    type: CREATE_USERS_ACTION_FAILURE,
    error
  };
}

export function createUsers(newUser,isAdminLocal) {
  console.log(currentCompanyId);
  const data = JSON.stringify(newUser);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${urlPost}create`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken,isAdminLocal:isAdminLocal } })
      .then(response => {
        dispatch(createUsersSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadUsers(isAdminLocal));
        dispatch(loadUsersCarriers());
      })
      .catch(error => {
        dispatch(createUsersFailure(error));
        throw (error);
      });
}

export function updateUsersSuccess(usersData) {
  return {
    type: UPDATE_USERS_ACTION_SUCCESS,
    usersData
  };
}

export function updateUsersFailure(error) {
  return {
    type: UPDATE_USERS_ACTION_FAILURE,
    error
  };
}

export function updateUsers(userToUpdate,isAdminLocal) {
  const data = JSON.stringify(userToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${urlPost}update`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken,isAdminLocal:isAdminLocal } })
      .then(response => {
        dispatch(updateUsersSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadUsers(isAdminLocal));
        dispatch(loadUsersCarriers());
      })
      .catch(error => {
        dispatch(updateUsersFailure(error));
        throw (error);
      });
}

export function deleteUsersSuccess() {
  return {
    type: DELETE_USERS_ACTION_SUCCESS
  };
}
export function deleteUsersFailure(error) {
  return {
    type: DELETE_USERS_ACTION_FAILURE,
    error
  };
}

export function deleteUsers(userId) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${urlPost}delete/${userId}`, {
       headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(deleteUsersSuccess());
        dispatch(showForm(false));
        dispatch(loadUsers());
      })
      .catch(error => {
        dispatch(deleteUsersFailure(error));
        throw (error);
      });
}


export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}
