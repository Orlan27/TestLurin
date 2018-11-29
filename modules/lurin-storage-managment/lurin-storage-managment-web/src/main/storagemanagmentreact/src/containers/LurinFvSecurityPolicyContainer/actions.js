/*
 *
 * LurinFvGlobalParameterContainer actions
 *
 */
import Axios from 'axios';

// API URL
const url = `${window.currentApiUrl}security/`;
//const currentToken = null;
import {
  LOAD_SECURITYPOLICY_ACTION_SUCCESS,
  LOAD_SECURITYPOLICY_ACTION_FAILURE,
  UPDATE_SECURITYPOLICY_ACTION_SUCCESS,
  UPDATE_SECURITYPOLICY_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';


export const loadSecurityPolicySuccess = (securityPolicyData) => ({
  type: LOAD_SECURITYPOLICY_ACTION_SUCCESS,
  securityPolicyData
});

export function loadSecurityPolicyFailure(error) {
  return {
    type: LOAD_SECURITYPOLICY_ACTION_FAILURE,
    error
  };
}

export function loadSecurityPolicy() {
  return (dispatch) => {
	   //Returns a promise
	    return Axios.get(`${url}getPolitics`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadSecurityPolicySuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadSecurityPolicyFailure(error));
	        throw (error);
	   });
  };
}

export function updateSecurityPolicySuccess(securityPolicyData) {
  return {
    type: UPDATE_SECURITYPOLICY_ACTION_SUCCESS,
    securityPolicyData
  };
}

export function updateSecurityPolicyFailure(error) {
  return {
    type: UPDATE_SECURITYPOLICY_ACTION_FAILURE,
    error
  };
}

export function updateSecurityPolicy(securityPolicyToUpdate) {
  return function (dispatch) {
    let hasError = false;
    let currentError = {}
    securityPolicyToUpdate.forEach(function(securityPoliciy) {
      const data = JSON.stringify(securityPoliciy);
      Axios.post(`${url}updatePolitics`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
      })
      .catch(error => {
        hasError = true;
        currentError = error;
        throw (error);
      });

      if(hasError) {
        return;
      }  
     }, this);
     if(hasError) {
      dispatch(updateSecurityPolicyFailure(currentError));
     } else {
      dispatch(updateSecurityPolicySuccess());
      dispatch(loadSecurityPolicy());   
     }
  }
     
}


export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}