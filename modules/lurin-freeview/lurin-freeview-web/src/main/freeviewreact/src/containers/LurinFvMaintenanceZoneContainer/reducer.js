// ./src/reducers/bookReducers.js
// For handling array of books
/* import { FETCH_MAINTENANCES_ZONE_SUCCESS } from './actionsType';

export const maintenanceZonesReducer = (state = [], action) => {
  switch (action.type) {
    case FETCH_MAINTENANCES_ZONE_SUCCESS:
      return [
        ...state,
        Object.assign({}, action.maintenanceZone)
      ];
    default:
      return state;
  }
};*/

/*
 *
 * LurinFvMaintenanceZoneContainer reducer
 *
 */

import { fromJS } from 'immutable';
import {
  LOAD_COMMERCIAL_ZONES_ACTION,
  LOAD_COMMERCIAL_ZONES_ACTION_SUCCESS,
  LOAD_COMMERCIAL_ZONES_ACTION_FAILURE,
  SHOW_ZONES_FORM_ACTION,
  CREATE_COMMERCIAL_ZONES_ACTION,
  CREATE_COMMERCIAL_ZONES_ACTION_SUCCESS,
  CREATE_COMMERCIAL_ZONES_ACTION_FAILURE,
  UPDATE_COMMERCIAL_ZONES_ACTION,
  UPDATE_COMMERCIAL_ZONES_ACTION_SUCCESS,
  UPDATE_COMMERCIAL_ZONES_ACTION_FAILURE,
  DELETE_COMMERCIAL_ZONES_ACTION,
  DELETE_COMMERCIAL_ZONES_ACTION_SUCCESS,
  DELETE_COMMERCIAL_ZONES_ACTION_FAILURE
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  commercialZonesData: [],
  hasShowForm: false,
  commercialZoneData: {},
  newComercialZoneNumber: 0
});

export const lurinFvMaintenanceZoneContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_COMMERCIAL_ZONES_ACTION:
      return state
        .set('loading', true)
        .set('error', false);
    case LOAD_COMMERCIAL_ZONES_ACTION_SUCCESS:
      return state
        .set('commercialZonesData', action.commercialZonesData)
        .set('loading', false);
    case LOAD_COMMERCIAL_ZONES_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('loading', false);
    case SHOW_ZONES_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowFormZones);
    case CREATE_COMMERCIAL_ZONES_ACTION:
      return state
        .set('loading', true)
        .set('error', false);
    case CREATE_COMMERCIAL_ZONES_ACTION_SUCCESS:
      return state
        .set('newComercialZoneNumber', action.newComercialZoneNumber)
        .set('loading', false)
        .set('error', false);
    case CREATE_COMMERCIAL_ZONES_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('error', true);
    case UPDATE_COMMERCIAL_ZONES_ACTION:
      return state
        .set('loading', true)
        .set('error', false);
    case UPDATE_COMMERCIAL_ZONES_ACTION_SUCCESS:
      return state
        .set('commercialZoneData', action.commercialZoneData)
        .set('loading', false)
        .set('error', false);
    case UPDATE_COMMERCIAL_ZONES_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('error', true);
    case DELETE_COMMERCIAL_ZONES_ACTION:
      return state
        .set('loading', true)
        .set('error', false);
    case DELETE_COMMERCIAL_ZONES_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('error', false);
    case DELETE_COMMERCIAL_ZONES_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('error', true);
    default:
      return state;
  }
};
