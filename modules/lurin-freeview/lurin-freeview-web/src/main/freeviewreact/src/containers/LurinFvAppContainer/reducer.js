/*
 *
 * LurinFvAppContainer reducer
 *
 */

import { fromJS } from 'immutable';
import {
  LOAD_PACKAGES_BY_CARRIER_ACTION,
  LOAD_PACKAGES_BY_CARRIER_ACTION_SUCCESS,
  LOAD_PACKAGES_BY_CARRIER_ACTION_FAILURE,
  LOAD_TECHNOLOGY_ACTION,
  LOAD_TECHNOLOGY_ACTION_SUCCESS,
  LOAD_TECHNOLOGY_ACTION_FAILURE,
  /** * INIT CAMPAIGN   */
  LOAD_ZONES_ACTION,
  LOAD_ZONES_ACTION_SUCCESS,
  LOAD_ZONES_ACTION_FAILURE,
  LOAD_LOADTYPES_ACTION,
  LOAD_LOADTYPES_ACTION_SUCCESS,
  LOAD_LOADTYPES_ACTION_FAILURE,
  LOAD_MESSAGES_ACTION,
  LOAD_MESSAGES_ACTION_SUCCESS,
  LOAD_MESSAGES_ACTION_FAILURE,
  LOAD_CAMPAIGNSTATUS_ACTION,
  LOAD_CAMPAIGNSTATUS_ACTION_SUCCESS,
  LOAD_CAMPAIGNSTATUS_ACTION_FAILURE,
    /** * END CAMPAIGN   */

  LOAD_CARRIERBYCODE_ACTION,
  LOAD_CARRIERBYCODE_ACTION_SUCCESS,
  LOAD_CARRIERBYCODE_ACTION_FAILURE
} from './constants';
import langMessages from '../../utils/langMessages';

const initialState = fromJS({
  loading: false,
  error: false,
  packages: [],
  technologies: [],
  zones: [],
  loadTypes: [],
  messages: [],
  campaignStatus: [],
  currentTheme: window.currentTheme,
  lang: (window.currentLanguage === 'pr') ? 'pt' : window.currentLanguage,
  currentCarrier: {}
});

export const lurinFvAppContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_PACKAGES_BY_CARRIER_ACTION:
      return state
        .set('loading', true)
        .set('error', false);
    case LOAD_PACKAGES_BY_CARRIER_ACTION_SUCCESS:
      return state
        .set('packages', action.packages)
        .set('loading', false);
    case LOAD_PACKAGES_BY_CARRIER_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('loading', false);
    case LOAD_TECHNOLOGY_ACTION:
      return state
          .set('loading', true)
          .set('error', false);
    case LOAD_TECHNOLOGY_ACTION_SUCCESS: {
      const initDataTechnologies = [{ code: '0', technology: langMessages[state.get('lang')]['combobox.first.register'], status: 'S' }];
      return state
          .set('technologies', initDataTechnologies.concat(action.technologies))
          .set('loading', false);
    }
    case LOAD_TECHNOLOGY_ACTION_FAILURE:
      return state
          .set('error', action.error)
          .set('loading', false);
    case LOAD_ZONES_ACTION :
      return state
            .set('loading', true)
            .set('error', false);
    case LOAD_ZONES_ACTION_SUCCESS: {
      const initDataZones = [{
        code: 0,
        name: langMessages[state.get('lang')]['combobox.first.register'],
        status: ''
      }];
      return state
            .set('zones', initDataZones.concat(action.zones))
            .set('loading', false);
    }
    case LOAD_ZONES_ACTION_FAILURE:
      return state
            .set('error', action.error)
            .set('loading', false);
    case LOAD_LOADTYPES_ACTION:
      return state
            .set('loading', true)
            .set('error', false);
    case LOAD_LOADTYPES_ACTION_SUCCESS: {
      const initDataLoadTypes = [{
        code: 0,
        name: langMessages[state.get('lang')]['combobox.first.register']
      }];
      return state
            .set('loadTypes', initDataLoadTypes.concat(action.loadTypes))
            .set('loading', false);
    }
    case LOAD_LOADTYPES_ACTION_FAILURE:
      return state
            .set('error', action.error)
            .set('loading', false);
    case LOAD_MESSAGES_ACTION:
      return state
            .set('loading', true)
            .set('error', false);
    case LOAD_MESSAGES_ACTION_SUCCESS: {
      const initDataMessages = [{
        code: 0,
        name: langMessages[state.get('lang')]['combobox.first.register']
      }];
      return state
            // .set('messages', initDataMessages.concat(action.initDataMessages))
            .set('messages', initDataMessages)
            .set('loading', false);
    }
    case LOAD_MESSAGES_ACTION_FAILURE:
      return state
            .set('error', action.error)
            .set('loading', false);
    case LOAD_CAMPAIGNSTATUS_ACTION:
      return state
            .set('loading', true)
            .set('error', false);
    case LOAD_CAMPAIGNSTATUS_ACTION_SUCCESS: {
      const initDataCampaignStatus = [{
        code: 0,
        name: langMessages[state.get('lang')]['combobox.first.register'],
        status: 'S'
      }];
      return state
            .set('campaignStatus', initDataCampaignStatus.concat(action.campaignStatus))
            .set('loading', false);
    }
    case LOAD_CAMPAIGNSTATUS_ACTION_FAILURE:
      return state
            .set('error', action.error)
            .set('loading', false);
    case LOAD_CARRIERBYCODE_ACTION:
      return state
            .set('loading', true)
            .set('error', false);
    case LOAD_CARRIERBYCODE_ACTION_SUCCESS:
      return state
              .set('currentCarrier', action.carrier)
              .set('loading', false);
    case LOAD_CARRIERBYCODE_ACTION_FAILURE:
      return state
                .set('error', action.error)
                .set('loading', false);
    default:
      return state;
  }
};

