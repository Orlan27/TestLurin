/*
 *
 * LurinFvAppContainer actions
 *
 */
import Axios from 'axios';

// API URL
const urlPackages = `${window.currentApiUrl}channel/packages/`;
const urlTechnology = `${window.currentApiUrl}technologies/`;
const urlZone = `${window.currentApiUrl}commercial/`;
const urlLoadType = `${window.currentApiUrl}type/load/`;
const urlMessages = `${window.currentApiUrl}adminstation/messages`;
const urlCampaignStatus = `${window.currentApiUrl}adminstation/campaignStatus`;
const urlTypeLoad = `${window.currentApiUrl}adminstation/campaignStatus`;
const urlCarrier = `${window.currentApiUrl}operatorsmanagment`;

import {
  LOAD_PACKAGES_BY_CARRIER_ACTION_SUCCESS,
  LOAD_PACKAGES_BY_CARRIER_ACTION_FAILURE,
  LOAD_TECHNOLOGY_ACTION_SUCCESS,
  LOAD_TECHNOLOGY_ACTION_FAILURE,
  /** * INIT CAMPAIGN   */
  LOAD_ZONES_ACTION_SUCCESS,
  LOAD_ZONES_ACTION_FAILURE,
  LOAD_LOADTYPES_ACTION_SUCCESS,
  LOAD_LOADTYPES_ACTION_FAILURE,
  LOAD_MESSAGES_ACTION_SUCCESS,
  LOAD_MESSAGES_ACTION_FAILURE,
  LOAD_CAMPAIGNSTATUS_ACTION_SUCCESS,
	LOAD_CAMPAIGNSTATUS_ACTION_FAILURE,

  LOAD_CARRIERBYCODE_ACTION_SUCCESS,
  LOAD_CARRIERBYCODE_ACTION_FAILURE
  /** * END CAMPAIGN   */
} from './constants';


export const loadPackagesByCarrierIdSuccess = (packages) => ({
  type: LOAD_PACKAGES_BY_CARRIER_ACTION_SUCCESS,
  packages
});

export function loadPackagesByCarrierIdFailure(error) {
  return {
    type: LOAD_PACKAGES_BY_CARRIER_ACTION_FAILURE,
    error
  };
}

export const loadTechnologySuccess = (technologies) => ({
  type: LOAD_TECHNOLOGY_ACTION_SUCCESS,
  technologies
});

export function loadTechnologyFailure(error) {
  return {
    type: LOAD_TECHNOLOGY_ACTION_FAILURE,
    error
  };
}

export const loadZonesSuccess = (zones) => ({
  type: LOAD_ZONES_ACTION_SUCCESS,
  zones
});

export function loadZonesFailure(error) {
  return {
    type: LOAD_ZONES_ACTION_FAILURE,
    error
  };
}

export const loadLoadTypesSuccess = (loadTypes) => ({
  type: LOAD_LOADTYPES_ACTION_SUCCESS,
  loadTypes
});

export function loadLoadTypesFailure(error) {
  return {
    type: LOAD_LOADTYPES_ACTION_FAILURE,
    error
  };
}

export const loadMessagesSuccess = (messages) => ({
  type: LOAD_MESSAGES_ACTION_SUCCESS,
  messages
});

export function loadMessagesFailure(error) {
  return {
    type: LOAD_MESSAGES_ACTION_FAILURE,
    error
  };
}

export const loadCampaignStatusSuccess = (campaignStatus) => ({
  type: LOAD_CAMPAIGNSTATUS_ACTION_SUCCESS,
  campaignStatus
});

export function loadCampaignStatusFailure(error) {
  return {
    type: LOAD_CAMPAIGNSTATUS_ACTION_FAILURE,
    error
  };
}


export const loadCarrierByCodeSuccess = (carrier) => ({
  type: LOAD_CARRIERBYCODE_ACTION_SUCCESS,
  carrier
});

export function loadCarrierByCodeFailure(error) {
  return {
    type: LOAD_CAMPAIGNSTATUS_ACTION_FAILURE,
    error
  };
}


export function loadPackagesByCarrierId(carrierId) {
  return (dispatch) =>
   // Returns a promise
     Axios.get(`${urlPackages}get/${carrierId}`, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken, Carrier: carrierId } })
      .then(response => {
        dispatch(loadPackagesByCarrierIdSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadPackagesByCarrierIdFailure(error));
        throw (error);
      });
}

export function loadTechnology(carrierId) {
	  return (dispatch) =>
	     // Returns a promise
	      Axios.get(`${urlTechnology}get`, { headers: { 'Content-Type': 'application/json',
	       Authorization: currentToken, Carrier: carrierId } })
	       .then(response => {
	         dispatch(loadTechnologySuccess(response.data));
	       })
	       .catch(error => {
	         dispatch(loadTechnologyFailure(error));
	         throw (error);
	       });
}

export function loadZones(idCarrier) {
	  return (dispatch) => {
			// Returns a promise
    if (idCarrier) {
	      Axios.get(`${urlZone}get`, { headers: { 'Content-Type': 'application/json',
	       Authorization: currentToken, Carrier: idCarrier } })
	       .then(response => {
	         dispatch(loadZonesSuccess(response.data));
	       })
	       .catch(error => {
	         dispatch(loadZonesFailure(error));
	         throw (error);
				 });
    }
  };
}

export function loadLoadTypes(idCarrier) {
	  return (dispatch) => {
			// Returns a promise
    if (idCarrier) {
	      Axios.get(`${urlLoadType}get/${idCarrier}`, { headers: { 'Content-Type': 'application/json',
	       Authorization: currentToken, Carrier: idCarrier } })
	       .then(response => {
	         dispatch(loadLoadTypesSuccess(response.data));
	       })
	       .catch(error => {
	         dispatch(loadLoadTypesFailure(error));
	         throw (error);
				 });
    }
	  };
}

export function loadMessages() {
	  return (dispatch) => {
	    // Returns a promise
	    // return Axios.get(`${urlMessages}get`, { headers: { 'Content-Type': 'application/json',
	    //   Authorization: currentToken } })
	    //   .then(response => {
	    //     dispatch(loadMessagesSuccess(response.data));
	    //   })
	    //   .catch(error => {
	    //     dispatch(loadMessagesFailure(error));
	    //     throw (error);
	    //   });
	    const messages = [];
	    dispatch(loadMessagesSuccess(messages));
	  };
}

export function loadCampaignStatus() {
	  return (dispatch) => {
	    // Returns a promise
	    // return Axios.get(`${urlCampaignStatus}get`, { headers: { 'Content-Type': 'application/json',
	    //   Authorization: currentToken } })
	    //   .then(response => {
	    //     dispatch(loadCampaignStatusSuccess(response.data));
	    //   })
	    //   .catch(error => {
	    //     dispatch(loadCampaignStatusFailure(error));
	    //     throw (error);
	    //   });
	    const campaignStatus = [{
	      code: 1,
	      name: 'Nueva',
	      status: 'N'
	    },
	    {
	      code: 2,
	      name: 'En Validación',
	      status: 'I'
	    },
	    {
	      code: 3,
	      name: 'Validado',
	      status: 'V'
	    },
	    {
	      code: 4,
	      name: 'En progreso',
	      status: 'P'
	    },
	    {
	      code: 5,
	      name: 'Completado',
	      status: 'T'
	    },
	    {
	      code: 6,
	      name: 'Con Errores',
	      status: 'W'
	    },
	    {
	      code: 7,
	      name: 'Error',
	      status: 'E'
	    },
	    {
	      code: 8,
	      name: 'Cancelada',
	      status: 'C'
	    }
	    ];
	    dispatch(loadCampaignStatusSuccess(campaignStatus));
	  };
}


export function loadCarrierByCode(code) {
  return (dispatch) =>
    // Returns a promise
     Axios.get(`${urlCarrier}/get/${code}`, { headers: { 'Content-Type': 'application/json',
       Authorization: currentToken, lang: window.currentLanguage } })
      .then(response => {
        dispatch(loadCarrierByCodeSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadCarrierByCodeFailure(error));
        throw (error);
      });
}

