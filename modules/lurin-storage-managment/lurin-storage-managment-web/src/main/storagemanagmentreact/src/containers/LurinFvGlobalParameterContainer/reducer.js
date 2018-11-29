/*
 *
 * LurinFvGlobalParameterContainer reducer
 *
 */
import { fromJS } from 'immutable';
import * as messages from './messages';
import {
  MESSAGE_500_ID,
  MESSAGE_TYPE
 } from '../../utils/messages';
 import langMessages from '../../utils/langMessages';

import {
  LOAD_GLOBALPARAMETERS_ACTION,
  LOAD_GLOBALPARAMETERS_ACTION_SUCCESS,
  LOAD_GLOBALPARAMETERS_ACTION_FAILURE,
  SHOW_GLOBALPARAMETERS_FORM_ACTION,
  CREATE_GLOBALPARAMETERS_ACTION,
  CREATE_GLOBALPARAMETERS_ACTION_SUCCESS,
  CREATE_GLOBALPARAMETERS_ACTION_FAILURE,
  UPDATE_GLOBALPARAMETERS_ACTION,
  UPDATE_GLOBALPARAMETERS_ACTION_SUCCESS,
  UPDATE_GLOBALPARAMETERS_ACTION_FAILURE,
  DELETE_GLOBALPARAMETERS_ACTION,
  DELETE_GLOBALPARAMETERS_ACTION_SUCCESS,
  DELETE_GLOBALPARAMETERS_ACTION_FAILURE,
  LOAD_GP_CATEGORIES_ACTION,
  LOAD_GP_CATEGORIES_ACTION_SUCCESS,
  LOAD_GP_CATEGORIES_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  globalParametersData: [],
  hasShowForm: false,
  globalParameterData: {},
  newGlobalParameter: 0,
  message: '',
  messageType: '',
  showMessage: false,
  gpCategoriesData:[]
});

export const lurinFvGlobalParameterContainerReducer = (state = initialState, action) => {
  let lang= (window.currentLanguage === 'pr') ? 'pt' : window.currentLanguage;
  switch (action.type) {
    case LOAD_GLOBALPARAMETERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_GLOBALPARAMETERS_ACTION_SUCCESS:
      return state
        .set('globalParametersData', action.globalParametersData)
        .set('loading', false);
    case LOAD_GLOBALPARAMETERS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('message', MESSAGE_500_ID)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case SHOW_GLOBALPARAMETERS_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowForm);
    case CREATE_GLOBALPARAMETERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case CREATE_GLOBALPARAMETERS_ACTION_SUCCESS:
      return state
        .set('newGlobalParameter', action.newGlobalParameter)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_GLOBALPARAMETER_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case CREATE_GLOBALPARAMETERS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_GLOBALPARAMETER_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case UPDATE_GLOBALPARAMETERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_GLOBALPARAMETERS_ACTION_SUCCESS:
      return state
        .set('globalParameterData', action.globalParameterData)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_GLOBALPARAMETER_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_GLOBALPARAMETERS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_GLOBALPARAMETER_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case DELETE_GLOBALPARAMETERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case DELETE_GLOBALPARAMETERS_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.DELETE_GLOBALPARAMETER_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case DELETE_GLOBALPARAMETERS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.DELETE_GLOBALPARAMETER_FAILURE_ID)
        .set('error', true);
    case LOAD_GP_CATEGORIES_ACTION:
        return state
          .set('loading', true)
          .set('showMessage', false)
          .set('error', false);
      case LOAD_GP_CATEGORIES_ACTION_SUCCESS:
      const initCategories = [{ code: 0, name: langMessages[lang]['combobox.first.register'] }];
        return state
          .set('gpCategoriesData', initCategories.concat(action.gpCategoriesData ))
          .set('loading', false);
      case LOAD_GP_CATEGORIES_ACTION_FAILURE:
        return state
          .set('error', action.error)
          .set('message', MESSAGE_500_ID)
          .set('showMessage', true)
          .set('messageType', MESSAGE_TYPE.WARNING)
          .set('loading', false);
      case INIT_MESSAGE_ACTION:
          return state
              .set('showMessage', false)
              .set('messageType', '')
              .set('message', '');
    default:
      return state;
  }
};
