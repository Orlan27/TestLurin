/*
 *
 * LurinChangeUserData actions
 *
 */
import Axios from 'axios';

// API URL
const url = `${window.currentApiUrl}administrator`;
//const currentToken = null;
import {
  LOAD_USERDATA_BYNAME_ACTION_SUCCESS,
  LOAD_USERDATA_BYNAME_ACTION_FAILURE,
  UPDATE_USERDATA_ACTION_SUCCESS,
  UPDATE_USERDATA_ACTION_FAILURE,
  INIT_MESSAGE_ACTION

} from './constants';


export const loadUserDataByNameSuccess = (userData) => ({
  type: LOAD_USERDATA_BYNAME_ACTION_SUCCESS,
  userData
});

export function loadUserDataByNameFailure(error) {
  return {
    type: LOAD_USERDATA_BYNAME_ACTION_FAILURE,
    error
  };
}

export function loadUserDataByName(userName) {
  return (dispatch) => {
	   // Returns a promise
	    return Axios.get(`${url}/user/get/${userName}`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadUserDataByNameSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadUserDataByNameFailure(error));
	        throw (error);
     });
    // dispatch(loadUserDataByNameSuccess({ code: 1,
    //   email: 'josemiguel.herrera.ext@zed.com',
    //   firstName: 'Jose',
    //   lastName: 'Herrera',
    //   middleName: 'Miguel',
    //   secondLastName: 'Cortez'
    // }));
  };
}

export function updateUserDataSuccess(updateData) {
  return {
    type: UPDATE_USERDATA_ACTION_SUCCESS,
    updateData
  };
}

export function updateUserDataFailure(error) {
  return {
    type: UPDATE_USERDATA_ACTION_FAILURE,
    error
  };
}

export function updateUserData(userToUpdate) {
  const data = JSON.stringify(userToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}/change/password`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(updateUserDataSuccess(response.data));
        dispatch(loadUserDataByName( window.currentUserSession));
      })
      .catch(error => {
        dispatch(updateUserDataFailure(error));
        throw (error);
      });
}

export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}

