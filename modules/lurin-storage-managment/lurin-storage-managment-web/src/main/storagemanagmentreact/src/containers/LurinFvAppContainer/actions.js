/*
 *
 * LurinFvAppContainer actions
 *
 */
import Axios from 'axios';
// import { LURIN_MASTER_API } from '../../Api/config';

// API URL
const urlCountries = `${window.currentApiUrl}operatorsmanagment/`;
const urlTables = `${window.currentApiUrl}operatorsmanagment/`;
const urlCompanies = `${window.currentApiUrl}operatorsmanagment/`;
const urlOperatorsStatus = `${window.currentApiUrl}operatorsmanagment/`;
const urlAdminUserStatus = `${window.currentApiUrl}operatorsmanagment/`;
const urlRoles = `${window.currentApiUrl}security/getVisibleRoles`;
const urlLookAndFeel = `${window.currentApiUrl}lookfeelmanagement/`;
const urlAuthenticationType = `${window.currentApiUrl}authenticationType/`;
const urlUserType = `${window.currentApiUrl}userType/`;
const urlTechnology = `${window.currentApiUrl}technologies/`;
const urlCampaignStatus = `${window.currentApiUrl}adminstation/campaignStatus/`;
const urlFreeview = `${window.currentApiUrl}freeview/`;
const urlCampaign = `${window.currentApiUrl}campaign/`;
const urlProfiles = `${window.currentApiUrl}security/getProfiles`;

import {
  LOAD_COUNTRIES_ACTION_SUCCESS,
  LOAD_COUNTRIES_ACTION_FAILURE,

  LOAD_TABLES_ACTION_SUCCESS,
  LOAD_TABLES_ACTION_FAILURE,

  LOAD_COMPANIES_ACTION_SUCCESS,
  LOAD_COMPANIES_ACTION_FAILURE,

  LOAD_OPERATORSTATUS_ACTION_SUCCESS,
  LOAD_OPERATORSTATUS_ACTION_FAILURE,


  LOAD_LOOKFEELS_ACTION_SUCCESS,
  LOAD_LOOKFEELS_ACTION_FAILURE,

  LOAD_AUTHENTICATIONTYPE_ACTION_SUCCESS,
  LOAD_AUTHENTICATIONTYPE_ACTION_FAILURE,

  LOAD_USERTYPE_ACTION_SUCCESS,
  LOAD_USERTYPE_ACTION_FAILURE,
  
  LOAD_ROLE_ACTION_SUCCESS,
  LOAD_ROLE_ACTION_FAILURE,

  LOAD_PROFILE_ACTION_SUCCESS,
  LOAD_PROFILE_ACTION_FAILURE,

  LOAD_CARRIER_ACTION_SUCCESS,
  LOAD_CARRIER_ACTION_FAILURE,

  LOAD_CAMPAIGNSTATUS_ACTION_SUCCESS,
  LOAD_CAMPAIGNSTATUS_ACTION_FAILURE,

  LOAD_TECHNOLOGY_ACTION_SUCCESS,
  LOAD_TECHNOLOGY_ACTION_FAILURE,

  LOAD_FREEVIEWS_ACTION_SUCCESS,
  LOAD_FREEVIEWS_ACTION_FAILURE,

  LOAD_CAMPAINGS_ACTION_SUCCESS,
  LOAD_CAMPAINGS_ACTION_FAILURE

} from './constants';


export const loadCountriesSuccess = (countries) => ({
  type: LOAD_COUNTRIES_ACTION_SUCCESS,
  countries
});

export function loadCountriesFailure(error) {
  return {
    type: LOAD_COUNTRIES_ACTION_FAILURE,
    error
  };
}

export const loadTablesSuccess = (tables) => ({
  type: LOAD_TABLES_ACTION_SUCCESS,
  tables
});

export function loadTablesFailure(error) {
  return {
    type: LOAD_TABLES_ACTION_FAILURE,
    error
  };
}

export const loadCompaniesSuccess = (companies) => ({
  type: LOAD_COMPANIES_ACTION_SUCCESS,
  companies
});

export function loadCompaniesFailure(error) {
  return {
    type: LOAD_COMPANIES_ACTION_FAILURE,
    error
  };
}


export const loadOperatorStatusSuccess = (operatorsStatus) => ({
  type: LOAD_OPERATORSTATUS_ACTION_SUCCESS,
  operatorsStatus
});

export function loadOperatorStatusFailure(error) {
  return {
    type: LOAD_OPERATORSTATUS_ACTION_FAILURE,
    error
  };
}

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


export function loadAunthenticationTypeFailure(error) {
  return {
    type: LOAD_AUTHENTICATIONTYPE_ACTION_FAILURE,
    error
  };
}

export const loadAuthenticationTypeSuccess = (authenticationTypes) => ({
  type: LOAD_AUTHENTICATIONTYPE_ACTION_SUCCESS,
  authenticationTypes
});

export function loadUserTypeFailure(error) {
  return {
    type: LOAD_USERTYPE_ACTION_FAILURE,
    error
  };
}

export const loadUserTypeSuccess = (userTypes) => ({
  type: LOAD_USERTYPE_ACTION_SUCCESS,
  userTypes
});


export const loadProfilesSuccess = (profiles) => ({
  type: LOAD_PROFILE_ACTION_SUCCESS,
  profiles
});

export function loadProfilesFailure(error) {
  return {
    type: LOAD_PROFILE_ACTION_FAILURE,
    error
  };
}

export const loadRoleSuccess = (roles) => ({
  type: LOAD_ROLE_ACTION_SUCCESS,
  roles
});

export function loadRoleFailure(error) {
  return {
    type: LOAD_ROLE_ACTION_FAILURE,
    error
  };
}

export const loadCarrierSuccess = (carriers) => ({
  type: LOAD_CARRIER_ACTION_SUCCESS,
  carriers
});

export function loadCarrierFailure(error) {
  return {
    type: LOAD_CARRIER_ACTION_FAILURE,
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

export function loadCountries() {
  return (dispatch) => {
    // Returns a promise
	  return Axios.get(`${urlCountries}getCountries`, { headers: { 'Content-Type': 'application/json',
	      Authorization: currentToken } })
	      .then(response => {
	        dispatch(loadCountriesSuccess(response.data));
	      })
	      .catch(error => {
	        dispatch(loadCountriesFailure(error));
	        throw (error);
	      });
  };
}

export function loadTables() {
  return (dispatch) => {
	  return Axios.get(`${urlTables}getTables`, { headers: { 'Content-Type': 'application/json',
	        Authorization: currentToken } })
	        .then(response => {
	          dispatch(loadTablesSuccess(response.data));
	        })
	        .catch(error => {
	          dispatch(loadTablesFailure(error));
	          throw (error);
	        });
  };
}

export function loadCompanies() {
  return (dispatch) => {
        // Returns a promise
	  return Axios.get(`${urlCompanies}getCompanies`, { headers: { 'Content-Type': 'application/json',
          Authorization: currentToken } })
          .then(response => {
            dispatch(loadCompaniesSuccess(response.data));
          })
          .catch(error => {
            dispatch(loadCompaniesFailure(error));
            throw (error);
          });
  };
}

export function loadOperatorsStatus() {
  return (dispatch) => {
       // Returns a promise
	  return Axios.get(`${urlOperatorsStatus}getSecurityCategoryStatus`, { headers: { 'Content-Type': 'application/json',
          Authorization: currentToken } })
          .then(response => {
            dispatch(loadOperatorStatusSuccess(response.data));
          })
          .catch(error => {
            dispatch(loadOperatorStatusFailure(error));
            throw (error);
          });
  };
}

export function loadLookFeels() {
  return (dispatch) => {
    // Returns a promise
      return Axios.get(`${urlLookAndFeel}get`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken, lang: window.currentLanguage } })
      .then(response => {
        dispatch(loadLookFeelsSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadLookFeelsFailure(error));
        throw (error);
      });
 
  };
}

export function loadAuthenticationType() {
  return (dispatch) => {
          // Returns a promise
          // return Axios.get(`${urlAuthenticationType}get`, { headers: { 'Content-Type': 'application/json',
          //   Authorization: currentToken } })
          //   .then(response => {
          //     dispatch(loadAuthenticationTypeSuccess(response.data));
          //   })
          //   .catch(error => {
          //     dispatch(loadAunthenticationTypeFailure(error));
          //     throw (error);
          //   });
    const data = [
      {
        code: 1,
        name: 'LDAP'
      },
      {
        code: 2,
        name: 'BD'
      }
    ];
    dispatch(loadAuthenticationTypeSuccess(data));
  };
}

export function loadUserType() {
  return (dispatch) => {
          // Returns a promise
          // return Axios.get(`${urlUserType}get`, { headers: { 'Content-Type': 'application/json',
          //   Authorization: currentToken } })
          //   .then(response => {
          //     dispatch(loadUserTypeSuccess(response.data));
          //   })
          //   .catch(error => {
          //     dispatch(loadUserTypeFailure(error));
          //     throw (error);
          //   });
    const data = [
      {
        code: 1,
        name: 'Gestión Global'
      },
      {
        code: 2,
        name: 'Gestión Local'
      }
    ];
    dispatch(loadUserTypeSuccess(data));
  };
}


export function loadProfiles() {
  return (dispatch) => {
          // Returns a promise
          return Axios.get(`${urlProfiles}`, { headers: { 'Content-Type': 'application/json',
            Authorization: currentToken } })
            .then(response => {
              dispatch(loadProfilesSuccess(response.data));
            })
            .catch(error => {
              dispatch(loadProfilesFailure(error));
              throw (error);
            });
  };
}

export function loadRoles() {
  return (dispatch) => {
          // Returns a promise
          return Axios.get(`${urlRoles}/${currentLanguage}`, { headers: { 'Content-Type': 'application/json',
            Authorization: currentToken } })
            .then(response => {
              dispatch(loadRoleSuccess(response.data));
            })
            .catch(error => {
              dispatch(loadRoleFailure(error));
              throw (error);
            });
  };
}

export function loadCarriers() {
  return (dispatch) => {
          
    dispatch(loadCarrierSuccess(window.currentCarriers));
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



export function loadTechnology(carrierId) {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${urlTechnology}get`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken, Carrier: carrierId } })
      .then(response => {
        dispatch(loadTechnologySuccess(response.data));
      })
      .catch(error => {
        dispatch(loadTechnologyFailure(error));
        throw (error);
      });
  };
}


export function loadFreeviews(carrierId) {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${urlFreeview}get/${carrierId}`, { headers: { 'Content-Type': 'application/json',
      Authorization: window.currentToken, Carrier: carrierId } })
      .then(response => {
        dispatch(loadFreeviewsSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadFreeviewsFailure(error));
        throw (error);
      });
  };
}


export function loadCampaings(carrierId) {
  return (dispatch) => {
    // Returns a promise
    return Axios.get(`${urlCampaign}get`, { headers: { 'Content-Type': 'application/json',
      Authorization: currentToken, Carrier: carrierId  } })
      .then(response => {
        dispatch(loadCampaingsSuccess(response.data));
      })
      .catch(error => {
        dispatch(loadCampaingsFailure(error));
        throw (error);
      });
  };
}
