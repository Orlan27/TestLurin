/*
 *
 * LurinFvFreeviewManagmentContainer actions
 *
 */
import Axios from 'axios';

// API URL
const url = `${window.currentApiUrl}freeview/`;

import {
  LOAD_FREEVIEWS_ACTION_SUCCESS,
  LOAD_FREEVIEWS_ACTION_FAILURE,
  SHOW_FREEVIEWS_FORM_ACTION,
  CREATE_FREEVIEWS_ACTION_SUCCESS,
  CREATE_FREEVIEWS_ACTION_FAILURE,
  UPDATE_FREEVIEWS_ACTION_SUCCESS,
  UPDATE_FREEVIEWS_ACTION_FAILURE,
  DELETE_FREEVIEWS_ACTION_SUCCESS,
  DELETE_FREEVIEWS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';


export const loadFreeviewsSuccess = (freeviewsData) => ({
  type: LOAD_FREEVIEWS_ACTION_SUCCESS,
  freeviewsData
});


export function loadFreeviewsFailure(error) {
  return {
    type: LOAD_FREEVIEWS_ACTION_FAILURE,
    error
  };
}


export function loadFreeviews(idCarrier) {
  return (dispatch) => {
    // Returns a promise
    if (idCarrier) {
      Axios.get(`${url}get/${idCarrier}`, { headers: { 'Content-Type': 'application/json',
        Authorization: window.currentToken, Carrier: idCarrier } })
      .then(response => {
        dispatch(loadFreeviewsSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadFreeviewsFailure(error));
        throw (error);
      });
    }
  };
}

export function showFormFreeviews(hasShowFormFreeviews) {
  return {
    type: SHOW_FREEVIEWS_FORM_ACTION,
    hasShowFormFreeviews
  };
}

export function createFreeviewsSuccess(freeviewsData) {
  return {
    type: CREATE_FREEVIEWS_ACTION_SUCCESS,
    freeviewsData
  };
}

export function createFreeviewsFailure(error) {
  return {
    type: CREATE_FREEVIEWS_ACTION_FAILURE,
    error
  };
}


export function createFreeviews(newFreeview, idCarrier) {
  const data = JSON.stringify(newFreeview);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}create`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: window.currentToken, Carrier: idCarrier } })
      .then(response => {
        dispatch(createFreeviewsSuccess(response.data));
        dispatch(showFormFreeviews(false));
        dispatch(loadFreeviews(idCarrier));
      })
      .catch(error => {
        dispatch(createFreeviewsFailure(error));
        throw (error);
      });
}

export function updateFreeviewsSuccess(freeviewData) {
  return {
    type: UPDATE_FREEVIEWS_ACTION_SUCCESS,
    freeviewData
  };
}

export function updateFreeviewsFailure(error) {
  return {
    type: UPDATE_FREEVIEWS_ACTION_FAILURE,
    error
  };
}

export function updateFreeviews(freeviewToUpdate, idCarrier) {
  const data = JSON.stringify(freeviewToUpdate);
  return (dispatch) =>
    // Returns a promise
     Axios.post(`${url}update`, data, { headers: { 'Content-Type': 'application/json',
       Authorization: window.currentToken, Carrier: idCarrier } })
      .then(response => {
        dispatch(updateFreeviewsSuccess(response.data));
        dispatch(showFormFreeviews(false));
        dispatch(loadFreeviews(idCarrier));
      })
      .catch(error => {
        dispatch(updateFreeviewsFailure(error));
        throw (error);
      });
}

export function deleteFreeviewsSuccess() {
  return {
    type: DELETE_FREEVIEWS_ACTION_SUCCESS
  };
}
export function deleteFreeviewsFailure(error) {
  return {
    type: DELETE_FREEVIEWS_ACTION_FAILURE,
    error
  };
}

export function deleteFreeviews(idCarrier, freeviewId) {
  return (dispatch) =>
    // Returns a promise
     Axios.delete(`${url}delete/${idCarrier}/${freeviewId}`, {
       headers: { 'Content-Type': 'application/json', Authorization: window.currentToken, Carrier: idCarrier } })
      .then(() => {
        dispatch(deleteFreeviewsSuccess());
        dispatch(showFormFreeviews(false));
        dispatch(loadFreeviews(idCarrier));
      })
      .catch(error => {
        dispatch(deleteFreeviewsFailure(error));
        throw (error);
      });
}


export function initMessage() {
  return (dispatch) => dispatch({
    type: INIT_MESSAGE_ACTION
  });
}
