/*
 *
 * LurinFvCampaingManagmentContainer actions
 *
 */
import Axios from 'axios';
// import { LURIN_MASTER_API } from '../../Api/config';

// API URL
const url = `${window.currentApiUrl}campaign/`;
//
import {
  LOAD_CAMPAINGS_ACTION_SUCCESS,
  LOAD_CAMPAINGS_ACTION_FAILURE,
  SHOW_CAMPAINGS_FORM_ACTION,
  CREATE_CAMPAINGS_ACTION_SUCCESS,
  CREATE_CAMPAINGS_ACTION_FAILURE,
  UPDATE_CAMPAINGS_ACTION_SUCCESS,
  UPDATE_CAMPAINGS_ACTION_FAILURE,
  DELETE_CAMPAINGS_ACTION_SUCCESS,
  DELETE_CAMPAINGS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';


export const loadCampaingsSuccess = (campaingsData) => ({
  type: LOAD_CAMPAINGS_ACTION_SUCCESS,
  campaingsData
});

export function loadCampaingsFailure(error) {
  return {
    type: LOAD_CAMPAINGS_ACTION_FAILURE,
    error
  };
}


export function loadCampaings(carrierId) {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${url}get`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken, Carrier: carrierId } })
      .then(response => {
        dispatch(loadCampaingsSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadCampaingsFailure(error));
        throw (error);
      });
    dispatch(loadCampaingsSuccess(data));
  };
}

export function showFormCampaings(hasShowFormCampaings) {
  return {
    type: SHOW_CAMPAINGS_FORM_ACTION,
    hasShowFormCampaings
  };
}

export function createCampaingsSuccess(campaingsData) {
  return {
    type: CREATE_CAMPAINGS_ACTION_SUCCESS,
    campaingsData
  };
}

export function createCampaingsFailure(error) {
  return {
    type: CREATE_CAMPAINGS_ACTION_FAILURE,
    error
  };
}

export function createCampaings(newCampaing, carrierId) {
  const data = newCampaing
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}create`, data, { headers: { 
       Authorization: currentToken, Carrier: carrierId } })
      .then(response => {
        dispatch(createCampaingsSuccess(response.data));
        dispatch(showFormCampaings(false));
        dispatch(loadCampaings(carrierId));
      })
      .catch(error => {
        dispatch(createCampaingsFailure(error));
        throw (error);
      });
}

export function updateCampaingsSuccess(campaingData) {
  return {
    type: UPDATE_CAMPAINGS_ACTION_SUCCESS,
    campaingData
  };
}

export function updateCampaingsFailure(error) {
  return {
    type: UPDATE_CAMPAINGS_ACTION_FAILURE,
    error
  };
}

export function updateCampaings(campaingToUpdate) {
  const data = JSON.stringify(campaingToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}update`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken } })
      .then(response => {
        dispatch(updateCampaingsSuccess(response.data));
        dispatch(loadCampaings());
      })
      .catch(error => {
        dispatch(updateCampaingsFailure(error));
        throw (error);
      });
}

export function deleteCampaingsSuccess() {
  return {
    type: DELETE_CAMPAINGS_ACTION_SUCCESS
  };
}
export function deleteCampaingsFailure(error) {
  return {
    type: DELETE_CAMPAINGS_ACTION_FAILURE,
    error
  };
}

export function deleteCampaings(campaingId) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}delete/${campaingId}`, {
       headers: { 'Content-Type': 'application/json', Authorization: currentToken } })
      .then(() => {
        dispatch(deleteCampaingsSuccess());
        dispatch(showFormCampaings(false));
        dispatch(loadCampaings());
      })
      .catch(error => {
        dispatch(deleteCampaingsFailure(error));
        throw (error);
      });
}

export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}
