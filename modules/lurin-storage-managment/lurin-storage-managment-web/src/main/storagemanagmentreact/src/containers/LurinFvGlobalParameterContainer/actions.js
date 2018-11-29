/*
 *
 * LurinFvGlobalParameterContainer actions
 *
 */
import Axios from 'axios';

// API URL
const url = `${window.currentApiUrl}globalparameters/`;
//const currentToken = null;
import {
  LOAD_GLOBALPARAMETERS_ACTION_SUCCESS,
  LOAD_GLOBALPARAMETERS_ACTION_FAILURE,
  SHOW_GLOBALPARAMETERS_FORM_ACTION,
  CREATE_GLOBALPARAMETERS_ACTION_SUCCESS,
  CREATE_GLOBALPARAMETERS_ACTION_FAILURE,
  UPDATE_GLOBALPARAMETERS_ACTION_SUCCESS,
  UPDATE_GLOBALPARAMETERS_ACTION_FAILURE,
  DELETE_GLOBALPARAMETERS_ACTION_SUCCESS,
  DELETE_GLOBALPARAMETERS_ACTION_FAILURE,
  LOAD_GP_CATEGORIES_ACTION_SUCCESS,
  LOAD_GP_CATEGORIES_ACTION_FAILURE,
  INIT_MESSAGE_ACTION

} from './constants';


export const loadGlobalParametersSuccess = (globalParametersData) => ({
  type: LOAD_GLOBALPARAMETERS_ACTION_SUCCESS,
  globalParametersData
});

export function loadGlobalParametersFailure(error) {
  return {
    type: LOAD_GLOBALPARAMETERS_ACTION_FAILURE,
    error
  };
}


export const loadGPCategoriesSuccess = (gpCategoriesData) => ({
  type: LOAD_GP_CATEGORIES_ACTION_SUCCESS,
  gpCategoriesData
});

export function loadGPCategoriesFailure(error) {
  return {
    type: LOAD_GP_CATEGORIES_ACTION_FAILURE,
    error
  };
}

export function loadGlobalParameters() {
  return (dispatch) => {
	   //Returns a promise
	    return Axios.get(`${url}get`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadGlobalParametersSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadGlobalParametersFailure(error));
	        throw (error);
	   });
  };
}

export function loadGPCategories() {
  return (dispatch) => {
	   //Returns a promise
	    return Axios.get(`${url}getAllCategories`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadGPCategoriesSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadGPCategoriesFailure(error));
	        throw (error);
	   });
  };
}

export function showForm(hasShowForm) {
  return {
    type: SHOW_GLOBALPARAMETERS_FORM_ACTION,
    hasShowForm
  };
}

export function createGlobalParametersSuccess(globalParameterData) {
  return {
    type: CREATE_GLOBALPARAMETERS_ACTION_SUCCESS,
    globalParameterData
  };
}

export function createGlobalParametersFailure(error) {
  return {
    type: CREATE_GLOBALPARAMETERS_ACTION_FAILURE,
    error
  };
}

export function createGlobalParameters(newGlobalParameter) {
  const data = JSON.stringify(newGlobalParameter);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}create`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(createGlobalParametersSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadGlobalParameters());
      })
      .catch(error => {
        dispatch(createGlobalParametersFailure(error));
        throw (error);
      });
}

export function updateGlobalParametersSuccess(globalParameterData) {
  return {
    type: UPDATE_GLOBALPARAMETERS_ACTION_SUCCESS,
    globalParameterData
  };
}

export function updateGlobalParametersFailure(error) {
  return {
    type: UPDATE_GLOBALPARAMETERS_ACTION_FAILURE,
    error
  };
}

export function updateGlobalParameters(globalParameterToUpdate) {
  const data = JSON.stringify(globalParameterToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}update`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(updateGlobalParametersSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadGlobalParameters());
      })
      .catch(error => {
        dispatch(updateGlobalParametersFailure(error));
        throw (error);
      });
}

export function deleteGlobalParametersSuccess() {
  return {
    type: DELETE_GLOBALPARAMETERS_ACTION_SUCCESS
  };
}
export function deleteGlobalParametersFailure(error) {
  return {
    type: DELETE_GLOBALPARAMETERS_ACTION_FAILURE,
    error
  };
}

export function deleteGlobalParameters(code) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}delete/${code}`, {
       headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(deleteGlobalParametersSuccess());
        dispatch(showForm(false));
        dispatch(loadGlobalParameters());
      })
      .catch(error => {
        dispatch(deleteGlobalParametersFailure(error));
        throw (error);
      });
}

export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}