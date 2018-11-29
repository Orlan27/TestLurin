/*
 *
 * LurinFvAppContainer reducer
 *
 */

import { fromJS } from 'immutable';
import {
    LOAD_COUNTRIES_ACTION,
    LOAD_COUNTRIES_ACTION_SUCCESS,
    LOAD_COUNTRIES_ACTION_FAILURE,

    LOAD_TABLES_ACTION,
    LOAD_TABLES_ACTION_SUCCESS,
    LOAD_TABLES_ACTION_FAILURE,

    LOAD_COMPANIES_ACTION,
    LOAD_COMPANIES_ACTION_SUCCESS,
    LOAD_COMPANIES_ACTION_FAILURE,

    LOAD_OPERATORSTATUS_ACTION,
    LOAD_OPERATORSTATUS_ACTION_SUCCESS,
    LOAD_OPERATORSTATUS_ACTION_FAILURE,

    LOAD_LOOKFEELS_ACTION,
    LOAD_LOOKFEELS_ACTION_SUCCESS,
    LOAD_LOOKFEELS_ACTION_FAILURE,

    LOAD_AUTHENTICATIONTYPE_ACTION,
    LOAD_AUTHENTICATIONTYPE_ACTION_FAILURE,
    LOAD_AUTHENTICATIONTYPE_ACTION_SUCCESS,

    LOAD_USERTYPE_ACTION,
    LOAD_USERTYPE_ACTION_FAILURE,
    LOAD_USERTYPE_ACTION_SUCCESS,

    LOAD_ROLE_ACTION,
    LOAD_ROLE_ACTION_SUCCESS,
    LOAD_ROLE_ACTION_FAILURE,

    LOAD_PROFILE_ACTION,
    LOAD_PROFILE_ACTION_SUCCESS,
    LOAD_PROFILE_ACTION_FAILURE,

    LOAD_CARRIER_ACTION,
    LOAD_CARRIER_ACTION_SUCCESS,
    LOAD_CARRIER_ACTION_FAILURE,

    LOAD_CAMPAIGNSTATUS_ACTION,
    LOAD_CAMPAIGNSTATUS_ACTION_SUCCESS,
    LOAD_CAMPAIGNSTATUS_ACTION_FAILURE,

    LOAD_TECHNOLOGY_ACTION,
    LOAD_TECHNOLOGY_ACTION_SUCCESS,
    LOAD_TECHNOLOGY_ACTION_FAILURE,

    LOAD_FREEVIEWS_ACTION,
    LOAD_FREEVIEWS_ACTION_SUCCESS,
    LOAD_FREEVIEWS_ACTION_FAILURE,

    LOAD_CAMPAINGS_ACTION,
    LOAD_CAMPAINGS_ACTION_SUCCESS,
    LOAD_CAMPAINGS_ACTION_FAILURE
} from './constants';
import langMessages from '../../utils/langMessages';
const initialState = fromJS({
  loading: false,
  error: false,
  countries: [],
  companies: [],
  operatorsStatus: [],
  userAdminStatus: [],
  roles: [],
  profiles: [],
  tables: [],
  lookFeelsData: [],
  currentTheme: window.currentTheme,
  lang: (window.currentLanguage === 'pr') ? 'pt' : window.currentLanguage,
  authenticationTypes: [],
  userTypes: [],
  carriers: [],
  campaignStatus: [],
  technologies: [],
  freeviewsData: [],
  campaingsData: []
});

export const lurinFvAppContainerReducer = (state = initialState, action) => {
  const initCatalogData = [{ code: 0, name: '- Seleccionar -' }];
  const initLookFeelsData = [{ themeId: 0, name: '- Seleccionar -' }];
  switch (action.type) {
    case LOAD_COUNTRIES_ACTION:
      return state
        .set('loading', true)
        .set('error', false);
    case LOAD_COUNTRIES_ACTION_SUCCESS: {
      const initCatalogCountry = [{ code: 0, country: '- Seleccionar -' }];
      return state
        .set('countries', initCatalogCountry.concat(action.countries))
        .set('loading', false);
    }
    case LOAD_COUNTRIES_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('loading', false);
    case LOAD_TABLES_ACTION:
      return state
          .set('loading', true)
          .set('error', false);
    case LOAD_TABLES_ACTION_SUCCESS: {
      const initCatalogTable = [{ tableId: 0, name: '- Seleccionar -' }];
      return state
      .set('tables', initCatalogTable.concat(action.tables))
      .set('loading', false);
    }
    case LOAD_TABLES_ACTION_FAILURE:
      return state
          .set('error', action.error)
          .set('loading', false);
    case LOAD_COMPANIES_ACTION:
      return state
              .set('loading', true)
              .set('error', false);
    case LOAD_COMPANIES_ACTION_SUCCESS:
      return state
              .set('companies', initCatalogData.concat(action.companies))
              .set('loading', false);
    case LOAD_COMPANIES_ACTION_FAILURE:
      return state
              .set('error', action.error)
              .set('loading', false);
    case LOAD_OPERATORSTATUS_ACTION:
      return state
               .set('loading', true)
               .set('error', false);
    case LOAD_OPERATORSTATUS_ACTION_SUCCESS:
      return state
               .set('operatorsStatus', initCatalogData.concat(action.operatorsStatus))
               .set('loading', false);
    case LOAD_OPERATORSTATUS_ACTION_FAILURE:
      return state
               .set('error', action.error)
               .set('loading', false);
    case LOAD_LOOKFEELS_ACTION:
      return state
                .set('loading', true)
                .set('error', false);
    case LOAD_LOOKFEELS_ACTION_SUCCESS:
      return state
                .set('lookFeelsData', initLookFeelsData.concat(action.lookFeelsData))
                .set('loading', false);
    case LOAD_LOOKFEELS_ACTION_FAILURE:
      return state
                .set('error', action.error)
                .set('loading', false);
    case LOAD_AUTHENTICATIONTYPE_ACTION_SUCCESS:
      return state
                .set('authenticationTypes', initCatalogData.concat(action.authenticationTypes))
                .set('loading', false);
    case LOAD_AUTHENTICATIONTYPE_ACTION_FAILURE:
      return state
                .set('error', action.error)
                .set('loading', false);
    case LOAD_USERTYPE_ACTION:
      return state
                .set('loading', true)
                .set('error', false);
    case LOAD_USERTYPE_ACTION_SUCCESS:
      return state
                .set('userTypes', initCatalogData.concat(action.userTypes))
                .set('loading', false);
    case LOAD_USERTYPE_ACTION_FAILURE:
      return state
                .set('error', action.error)
                .set('loading', false);
    case LOAD_ROLE_ACTION:
                return state
                    .set('loading', true)
                    .set('error', false);
    case LOAD_ROLE_ACTION_SUCCESS:
                return state
                    .set('roles', initCatalogData.concat(action.roles))
                    .set('loading', false);
    case LOAD_ROLE_ACTION_FAILURE:
                return state
                    .set('error', action.error)
                    .set('loading', false);
    case LOAD_PROFILE_ACTION:
                return state
                    .set('loading', true)
                    .set('error', false);
    case LOAD_PROFILE_ACTION_SUCCESS:
                return state
                    .set('profiles', initCatalogData.concat(action.profiles))
                    .set('loading', false);
    case LOAD_PROFILE_ACTION_FAILURE:
                return state
                    .set('error', action.error)
                    .set('loading', false);
    case LOAD_CARRIER_ACTION:
      return state
                .set('loading', true)
                .set('error', false);
    case LOAD_CARRIER_ACTION_SUCCESS:
      return state
                .set('carriers', action.carriers)
                .set('loading', false);
    case LOAD_CARRIER_ACTION_FAILURE:
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
    case LOAD_FREEVIEWS_ACTION:
      return state
        .set('loading', true)
        .set('error', false);
    case LOAD_FREEVIEWS_ACTION_SUCCESS:
      return state
        .set('freeviewsData', action.freeviewsData)
        .set('loading', false);
    case LOAD_FREEVIEWS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('loading', false);
    case LOAD_CAMPAINGS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_CAMPAINGS_ACTION_SUCCESS:
      return state
        .set('campaingsData', action.campaingsData)
        .set('loading', false);
    case LOAD_CAMPAINGS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('loading', false);
    default:
      return state;
  }
};
