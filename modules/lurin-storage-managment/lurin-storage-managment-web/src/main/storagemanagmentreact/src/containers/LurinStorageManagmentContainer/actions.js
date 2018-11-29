/*
 *
 * LurinStorageManagmentContainer actions
 *
 */
import Axios from 'axios';

 //API URL
const url = `${window.currentApiUrl}storagemanagment/`;
const urlCarrier = `${window.currentApiUrl}operatorsmanagment/`;

import {
  LOAD_STORAGES_ACTION_SUCCESS,
  LOAD_STORAGES_ACTION_FAILURE,
  SHOW_STORAGES_FORM_ACTION,
  RESET_ALERT_MESSAGE_ACTION,
  CREATE_STORAGES_ACTION_SUCCESS,
  CREATE_STORAGES_ACTION_FAILURE,
  UPDATE_STORAGES_ACTION_SUCCESS,
  UPDATE_STORAGES_ACTION_FAILURE,
  DELETE_STORAGES_ACTION_SUCCESS,
  DELETE_STORAGES_ACTION_FAILURE,
  LOAD_SOURCETYPES_ACTION_SUCCESS,
  LOAD_SOURCETYPES_ACTION_FAILURE,
  LOAD_STATUS_ACTION_SUCCESS,
  LOAD_STATUS_ACTION_FAILURE,
  SEND_TESTDS_ACTION_SUCCESS,
  SEND_TESTDS_ACTION_FAILURE,
  INIT_TESTDS_ACTION,
  INIT_MESSAGE_ACTION
} from './constants';


export const loadStoragesSuccess = (storagesData) => ({
  type: LOAD_STORAGES_ACTION_SUCCESS,
  storagesData
});


export const loadSourceTypesSuccess = (sourceTypesData) => ({
  type: LOAD_SOURCETYPES_ACTION_SUCCESS,
  sourceTypesData
});

export const loadStatusSuccess = (statusData) => ({
  type: LOAD_STATUS_ACTION_SUCCESS,
  statusData
});

export const sendTestDSSuccess = (testData) => ({
  type: SEND_TESTDS_ACTION_SUCCESS,
  testData
});

export function loadStoragesFailure(error) {
  return {
    type: LOAD_STORAGES_ACTION_FAILURE,
    error
  };
}

export function loadSourceTypesFailure(error) {
  return {
    type: LOAD_SOURCETYPES_ACTION_FAILURE,
    error
  };
}

export function loadStatusFailure(error) {
  return {
    type: LOAD_STATUS_ACTION_FAILURE,
    error
  };
}


export function sendTestDSFailure(error) {
  return {
    type: SEND_TESTDS_ACTION_FAILURE,
    error
  };
}

export function loadStorages() {
  return (dispatch) => {
     //Returns a promise
     return Axios.get(`${url}get`, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
       .then(response => {
         dispatch(loadStoragesSuccess(response.data));
       })
       .catch(error => {
         dispatch(loadStoragesFailure(error));
         throw (error);
       });
  };
}

export function loadSourceTypes() {
  return (dispatch) => {
     //Returns a promise
     return Axios.get(`${url}getSourceTypes`, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
       .then(response => {
         dispatch(loadSourceTypesSuccess(response.data));
       })
       .catch(error => {
         dispatch(loadSourceTypesFailure(error));
         throw (error);
       });
  };
}

export function loadStatus() {
  return (dispatch) => {
     //Returns a promise
     return Axios.get(`${urlCarrier}getSecurityCategoryStatus`, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
       .then(response => {
         dispatch(loadStatusSuccess(response.data));
       })
       .catch(error => {
         dispatch(loadStatusFailure(error));
         throw (error);
       });
  };
}

export function sendTestDS(storagesData) {
  return (dispatch) =>
     //Returns a promise
     //Axios.get(`${url}testDataSource/${storagesData.ip.replace(new RegExp('.'.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&'), 'g'), ',')}/${storagesData.port}`, { headers: { 'Content-Type': 'application/json',
     //  Authorization: currentToken } })
     Axios.post(`${url}testDataSource`, storagesData, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(resetAlertMessage());
        dispatch(sendTestDSSuccess(response.data));
      })
      .catch(error => {
        dispatch(sendTestDSFailure(error));
        throw (error);
      });
}

export function showFormStorages(hasShowFormStorages) {
  return {
    type: SHOW_STORAGES_FORM_ACTION,
    hasShowFormStorages
  };
}

export function resetAlertMessage() {
  return {
    type: RESET_ALERT_MESSAGE_ACTION
  };
}

export function createStoragesSuccess(storagesData) {
  return {
    type: CREATE_STORAGES_ACTION_SUCCESS,
    storagesData
  };
}

export function createStoragesFailure(error) {
  return {
    type: CREATE_STORAGES_ACTION_FAILURE,
    error
  };
}

export function createStorages(newStorage) {
  const data = JSON.stringify(newStorage);
  return (dispatch) =>
     //Returns a promise
     Axios.post(`${url}create`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(resetAlertMessage());
        dispatch(createStoragesSuccess(response.data));
        dispatch(showFormStorages(false));
        dispatch(loadStorages());
      })
      .catch(error => {
        dispatch(createStoragesFailure(error));
        throw (error);
      });
}

export function updateStoragesSuccess(storagesData) {
  return {
    type: UPDATE_STORAGES_ACTION_SUCCESS,
    storagesData
  };
}

export function updateStoragesFailure(error) {
  return {
    type: UPDATE_STORAGES_ACTION_FAILURE,
    error
  };
}

export function updateStorages(storageToUpdate) {
  const data = JSON.stringify(storageToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}update`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(resetAlertMessage());
        dispatch(updateStoragesSuccess(response.data));
        dispatch(loadStorages());
      })
      .catch(error => {
        dispatch(updateStoragesFailure(error));
        throw (error);
      });
}

export function deleteStoragesSuccess() {
  return {
    type: DELETE_STORAGES_ACTION_SUCCESS
  };
}
export function deleteStoragesFailure(error,data) {
  return {
    type: DELETE_STORAGES_ACTION_FAILURE,
    error,
    data
  };
}

export function deleteStorages(storageId) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}delete/${storageId}`, {
       headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(resetAlertMessage());
        dispatch(deleteStoragesSuccess());
        dispatch(showFormStorages(false));
        dispatch(loadStorages());
      })
      .catch(error => {
        dispatch(deleteStoragesFailure(error,error.response.data));
        throw (error);
      });
}

export function initTestDS() {
  return (dispatch) => dispatch({
          type: INIT_TESTDS_ACTION
  });
}


export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}