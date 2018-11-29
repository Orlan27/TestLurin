/*
 *
 * LurinFvMaintenanceZoneContainer actions
 *
 */
import Axios from 'axios';
//import { LURIN_MASTER_API } from '../../Api/config';

// API URL
const url = `${window.currentApiUrl}commercial/`;

import {
  LOAD_COMMERCIAL_ZONES_ACTION_SUCCESS,
  LOAD_COMMERCIAL_ZONES_ACTION_FAILURE,
  SHOW_ZONES_FORM_ACTION,
  CREATE_COMMERCIAL_ZONES_ACTION_SUCCESS,
  CREATE_COMMERCIAL_ZONES_ACTION_FAILURE,
  UPDATE_COMMERCIAL_ZONES_ACTION_SUCCESS,
  UPDATE_COMMERCIAL_ZONES_ACTION_FAILURE,
  DELETE_COMMERCIAL_ZONES_ACTION_SUCCESS,
  DELETE_COMMERCIAL_ZONES_ACTION_FAILURE
} from './constants';


export const loadCommercialZonesSuccess = (commercialZonesData) => ({
  type: LOAD_COMMERCIAL_ZONES_ACTION_SUCCESS,
  commercialZonesData
});


export function loadCommercialZonesFailure(error) {
  return {
    type: LOAD_COMMERCIAL_ZONES_ACTION_FAILURE,
    error
  };
}


export function loadCommercialZones() {
  return (dispatch) =>
    // Returns a promise
     Axios.get(`${url}get`, { headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(response => {
        dispatch(loadCommercialZonesSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadCommercialZonesFailure(error));
        throw (error);
      });
}

export function showFormZones(hasShowFormZones) {
  return {
    type: SHOW_ZONES_FORM_ACTION,
    hasShowFormZones
  };
}

export function createCommercialZonesSuccess(commercialZoneData) {
  return {
    type: CREATE_COMMERCIAL_ZONES_ACTION_SUCCESS,
    commercialZoneData
  };
}

export function createCommercialZonesFailure(error) {
  return {
    type: CREATE_COMMERCIAL_ZONES_ACTION_FAILURE,
    error
  };
}


export function createCommercialZones(newCommercialZone) {
  const data = JSON.stringify(newCommercialZone);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}create`, data, { headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(response => {
        dispatch(createCommercialZonesSuccess(response.data));
        dispatch(showFormZones(false));
        dispatch(loadCommercialZones());
      })
      .catch(error => {
        dispatch(createCommercialZonesFailure(error));
        throw (error);
      });
}

export function updateCommercialZonesSuccess(commercialZoneData) {
  return {
    type: UPDATE_COMMERCIAL_ZONES_ACTION_SUCCESS,
    commercialZoneData
  };
}

export function updateCommercialZonesFailure(error) {
  return {
    type: UPDATE_COMMERCIAL_ZONES_ACTION_FAILURE,
    error
  };
}

export function updateCommercialZones(commercialZoneToUpdate) {
  const data = JSON.stringify(commercialZoneToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}update`, data, { headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(response => {
        dispatch(updateCommercialZonesSuccess(response.data));
        dispatch(loadCommercialZones());
      })
      .catch(error => {
        dispatch(updateCommercialZonesFailure(error));
        throw (error);
      });
}

export function deleteCommercialZonesSuccess() {
  return {
    type: DELETE_COMMERCIAL_ZONES_ACTION_SUCCESS
  };
}
export function deleteCommercialZonesFailure(error) {
  return {
    type: DELETE_COMMERCIAL_ZONES_ACTION_FAILURE,
    error
  };
}

export function deleteCommercialZones(commercialZoneId) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}delete/${commercialZoneId}`, { headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(deleteCommercialZones());
        dispatch(loadCommercialZones());
      })
      .catch(error => {
        dispatch(deleteCommercialZonesFailure(error));
        throw (error);
      });
}
