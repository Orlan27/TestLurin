/*
 *
 * LurinStorageManagmentContainer actions
 *
 */
import Axios from 'axios';

// API URL
const url = `${window.currentApiUrl}operatorsmanagment/`;
import {
  LOAD_OPERATORS_ACTION_SUCCESS,
  LOAD_OPERATORS_ACTION_FAILURE,
  SHOW_OPERATORS_FORM_ACTION,
  CREATE_OPERATORS_ACTION_SUCCESS,
  CREATE_OPERATORS_ACTION_FAILURE,
  UPDATE_OPERATORS_ACTION_SUCCESS,
  UPDATE_OPERATORS_ACTION_FAILURE,
  DELETE_OPERATORS_ACTION_SUCCESS,
  DELETE_OPERATORS_ACTION_FAILURE,
  LOAD_DATASOURCES_ACTION_SUCCESS,
  LOAD_DATASOURCES_ACTION_FAILURE,
  LOAD_JNDI_ACTION_FAILURE,
  LOAD_JNDI_ACTION_SUCCESS,
  LOAD_CAS_ACTION_FAILURE,
  LOAD_CAS_ACTION_SUCCESS,
  INIT_MESSAGE_ACTION
} from './constants';


export const loadOperatorsSuccess = (operatorsData) => ({
  type: LOAD_OPERATORS_ACTION_SUCCESS,
  operatorsData
});


export const loadDataSourcesSuccess = (dataSourceData) => ({
  type: LOAD_DATASOURCES_ACTION_SUCCESS,
  dataSourceData
});


export const loadJndiSuccess = (jndiData) => ({
  type: LOAD_JNDI_ACTION_SUCCESS,
  jndiData
});

export const loadCasSuccess = (casData) => ({
  type: LOAD_CAS_ACTION_SUCCESS,
  casData
});


export function loadJndiFailure(error) {
  return {
    type: LOAD_JNDI_ACTION_FAILURE,
    error
  };
}

export function loadCasFailure(error) {
  return {
    type: LOAD_CAS_ACTION_FAILURE,
    error
  };
}


export function loadOperatorsFailure(error) {
  return {
    type: LOAD_OPERATORS_ACTION_FAILURE,
    error
  };
}

export function loadDataSourcesFailure(error) {
  return {
    type: LOAD_DATASOURCES_ACTION_FAILURE,
    error
  };
}

export function loadOperators() {
  return (dispatch) => {
	  return Axios.get(`${url}get`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken, lang: window.currentLanguage } })
	      .then(response => {
	        dispatch(loadOperatorsSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadOperatorsFailure(error));
	        throw (error);
	      });
  };
}

export function loadDataSources() {
  return (dispatch) => {
    // Returns a promise
	  return Axios.get(`${url}getDataSources`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadDataSourcesSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadDataSourcesFailure(error));
	        throw (error);
	      });
  };
}

export function showForm(hasShowForm) {
  return {
    type: SHOW_OPERATORS_FORM_ACTION,
    hasShowForm
  };
}

export function createOperatorsSuccess(operatorsData) {
  return {
    type: CREATE_OPERATORS_ACTION_SUCCESS,
    operatorsData
  };
}

export function createOperatorsFailure(error) {
  return {
    type: CREATE_OPERATORS_ACTION_FAILURE,
    error
  };
}

export function createOperators(newOperator) {
  const data = JSON.stringify(newOperator);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}create`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(createOperatorsSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadOperators());
        dispatch(loadJndi('J'));
        dispatch(loadCas('T'));
      })
      .catch(error => {
        dispatch(createOperatorsFailure(error));
        throw (error);
      });
}

export function updateOperatorsSuccess(operatorsData) {
  return {
    type: UPDATE_OPERATORS_ACTION_SUCCESS,
    operatorsData
  };
}

export function updateOperatorsFailure(error) {
  return {
    type: UPDATE_OPERATORS_ACTION_FAILURE,
    error
  };
}

export function updateOperators(operatorToUpdate) {
  const data = JSON.stringify(operatorToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}update`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(updateOperatorsSuccess(response.data));
        dispatch(showForm(false));
        dispatch(loadOperators());
        dispatch(loadJndi('J'));
        dispatch(loadCas('T'));
      })
      .catch(error => {
        dispatch(updateOperatorsFailure(error));
        throw (error);
      });
}

export function deleteOperatorsSuccess() {
  return {
    type: DELETE_OPERATORS_ACTION_SUCCESS
  };
}
export function deleteOperatorsFailure(error,data) {
  return {
    type: DELETE_OPERATORS_ACTION_FAILURE,
    error,
    data
  };
}

export function deleteOperators(operatorId) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}delete/${operatorId}`, {
       headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(deleteOperatorsSuccess());
        dispatch(showForm(false));
        dispatch(loadOperators());
        dispatch(loadJndi('J'));
        dispatch(loadCas('T'));
      })
      .catch(error => {
        dispatch(deleteOperatorsFailure(error,error.response.data));
        throw (error);
      });
}


export function loadJndi(type) {
  return (dispatch) => {
    // Returns a promise
	  return Axios.get(`${url}getDataSources/${type}`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadJndiSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadJndiFailure(error));
	        throw (error);
        });
  };
}

export function loadCas(type) {
  return (dispatch) => {
    // Returns a promise
	   return Axios.get(`${url}getDataSources/${type}`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadCasSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadCasFailure(error));
	        throw (error);
        });
  };
}

export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}



