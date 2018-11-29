/*
 *
 * LurinFvLookFeelContainer actions
 *
 */
import Axios from 'axios';

// API URL
const url = `${window.currentApiUrl}lookfeelmanagement/`;
//const currentToken = null;
import {
  LOAD_LOOKFEELS_ACTION_SUCCESS,
  LOAD_LOOKFEELS_ACTION_FAILURE,
  SHOW_LOOKFEELS_FORM_ACTION,
  CREATE_LOOKFEELS_ACTION_SUCCESS,
  CREATE_LOOKFEELS_ACTION_FAILURE,
  UPDATE_LOOKFEELS_ACTION_SUCCESS,
  UPDATE_LOOKFEELS_ACTION_FAILURE,
  DELETE_LOOKFEELS_ACTION_SUCCESS,
  DELETE_LOOKFEELS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';


export const loadLookFeelsSuccess = (lookFeelsData) => ({
  type: LOAD_LOOKFEELS_ACTION_SUCCESS,
  lookFeelsData
});

export function loadLookFeelsFailure(error) {
  return {
    type: LOAD_LOOKFEELS_ACTION_FAILURE,
    error
  };
}


export function loadLookFeels() {
  return (dispatch) => {
    // Returns a promise
	  return Axios.get(`${url}get`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken,lang:window.currentLanguage } })
	      .then(response => {
	        dispatch(loadLookFeelsSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadLookFeelsFailure(error));
	        throw (error);
	      });	 
    
  };
}

export function showForm(hasShowForm) {
  return {
    type: SHOW_LOOKFEELS_FORM_ACTION,
    hasShowForm
  };
}

export function createLookFeelsSuccess(data) {
  return {
    type: CREATE_LOOKFEELS_ACTION_SUCCESS,
    data
  };
}

export function createLookFeelsFailure(error) {
  return {
    type: CREATE_LOOKFEELS_ACTION_FAILURE,
    error
  };
}

export function createLookFeels(newData) {
  const data = JSON.stringify(newData);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}create`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(createLookFeelsSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadLookFeels());
      })
      .catch(error => {
        dispatch(createLookFeelsFailure(error));
        throw (error);
      });
}

export function updateLookFeelsSuccess(data) {
  return {
    type: UPDATE_LOOKFEELS_ACTION_SUCCESS,
    data
  };
}

export function updateLookFeelsFailure(error) {
  return {
    type: UPDATE_LOOKFEELS_ACTION_FAILURE,
    error
  };
}

export function updateLookFeels(dataToUpdate) {
  const data = JSON.stringify(dataToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}update`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(updateLookFeelsSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadLookFeels());
      })
      .catch(error => {
        dispatch(updateLookFeelsFailure(error));
        throw (error);
      });
}

export function deleteLookFeelsSuccess() {
  return {
    type: DELETE_LOOKFEELS_ACTION_SUCCESS
  };
}
export function deleteLookFeelsFailure(error) {
  return {
    type: DELETE_LOOKFEELS_ACTION_FAILURE,
    error
  };
}

export function deleteLookFeels(code) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}delete/${code}`, {
       headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(deleteLookFeelsSuccess());
        dispatch(showForm(false));
        dispatch(loadLookFeels());
      })
      .catch(error => {
        dispatch(deleteLookFeelsFailure(error));
        throw (error);
      });
}


export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}