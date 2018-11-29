/*
 *
 * LurinOperatorsManagementContainer reducer
 *
 */
import { fromJS } from 'immutable';
import * as messages from './messages';
import {
  MESSAGE_500,
  MESSAGE_500_ID,
  MESSAGE_TYPE
 } from '../../utils/messages';
import {
  LOAD_OPERATORS_ACTION,
  LOAD_OPERATORS_ACTION_SUCCESS,
  LOAD_OPERATORS_ACTION_FAILURE,
  SHOW_OPERATORS_FORM_ACTION,
  CREATE_OPERATORS_ACTION,
  CREATE_OPERATORS_ACTION_SUCCESS,
  CREATE_OPERATORS_ACTION_FAILURE,
  UPDATE_OPERATORS_ACTION,
  UPDATE_OPERATORS_ACTION_SUCCESS,
  UPDATE_OPERATORS_ACTION_FAILURE,
  DELETE_OPERATORS_ACTION,
  DELETE_OPERATORS_ACTION_SUCCESS,
  DELETE_OPERATORS_ACTION_FAILURE,
  LOAD_DATASOURCES_ACTION,
  LOAD_DATASOURCES_ACTION_SUCCESS,
  LOAD_DATASOURCES_ACTION_FAILURE,


  LOAD_JNDI_ACTION,
  LOAD_JNDI_ACTION_SUCCESS,
  LOAD_JNDI_ACTION_FAILURE,


  LOAD_CAS_ACTION,
  LOAD_CAS_ACTION_SUCCESS,
  LOAD_CAS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

import langMessages from '../../utils/langMessages';

const initialState = fromJS({
  loading: false,
  error: false,
  operatorsData: [],
  hasShowForm: false,
  message: '',
  messageType: '',
  showMessage: false,
  operatorData: {},
  newOperator: 0,
  dataSourcesData: [],
  statusData: [],
  jndiData: [],
  casData: []
});

export const lurinOperatorsManagementContainerReducer = (state = initialState, action) => {
    let lang= (window.currentLanguage === 'pr') ? 'pt' : window.currentLanguage;
   //let lang='en';
  switch (action.type) {
    case LOAD_OPERATORS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_OPERATORS_ACTION_SUCCESS:
      return state
        .set('operatorsData', action.operatorsData)
        .set('loading', false);
    case LOAD_OPERATORS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('showMessage', true)
        .set('message', MESSAGE_500)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case LOAD_DATASOURCES_ACTION:
      return state
          .set('loading', true)
          .set('showMessage', false)
          .set('error', false);
    case LOAD_DATASOURCES_ACTION_SUCCESS: {
      const initDataSourceData = [{ code: 0, jndiName: langMessages[lang]['combobox.first.register'] }];
      return state
          .set('dataSourcesData', initDataSourceData.concat(action.dataSourceData))
          .set('showMessage', false)
          .set('loading', false);
    }
    case LOAD_DATASOURCES_ACTION_FAILURE:
      return state
          .set('error', action.error)
          .set('showMessage', true)
          .set('message', MESSAGE_500)
          .set('messageType', MESSAGE_TYPE.WARNING)
          .set('loading', false);
    case SHOW_OPERATORS_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowForm);
    case CREATE_OPERATORS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case CREATE_OPERATORS_ACTION_SUCCESS:
      return state
        .set('newOperator', action.newOperator)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_OPERATOR_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case CREATE_OPERATORS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_OPERATOR_FAILURE)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case UPDATE_OPERATORS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_OPERATORS_ACTION_SUCCESS:
      return state
        .set('operatorData', action.operatorData)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_OPERATOR_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_OPERATORS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.UPDATE_OPERATOR_FAILURE)
        .set('error', true);
    case DELETE_OPERATORS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case DELETE_OPERATORS_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.DELETE_OPERATOR_SUCCESSFULL)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case DELETE_OPERATORS_ACTION_FAILURE:
    let message = messages.default.DELETE_OPERATOR_FAILURE_ID;
    if (action.data!=undefined){
      if(action.data.code=='0004')
          message = messages.default.DELETE_OPERATOR_FAILURE_0004_ID;
    }
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', message)
        .set('error', true);
    case LOAD_JNDI_ACTION:
      return state
            .set('loading', true)
            .set('showMessage', false)
            .set('error', false);
    case LOAD_JNDI_ACTION_SUCCESS: {
      const initJndiData = [{ code: 0, jndiName: langMessages[lang]['combobox.first.register'] }];
      return state
            .set('jndiData', initJndiData.concat(action.jndiData))
            .set('showMessage', false)
            .set('loading', false);
    }
    case LOAD_JNDI_ACTION_FAILURE:
      return state
            .set('error', action.error)
            .set('showMessage', true)
            .set('message', MESSAGE_500_ID)
            .set('messageType', MESSAGE_TYPE.WARNING)
            .set('loading', false);
    case LOAD_CAS_ACTION:
      return state
                .set('loading', true)
                .set('showMessage', false)
                .set('error', false);
    case LOAD_CAS_ACTION_SUCCESS: {
      const initCasData = [{ code: 0, jndiName: langMessages[lang]['combobox.first.register'] }];
      return state
                .set('casData', initCasData.concat(action.casData))
                .set('showMessage', false)
                .set('loading', false);
    }
    case LOAD_CAS_ACTION_FAILURE:
      return state
                .set('error', action.error)
                .set('showMessage', true)
                .set('message', MESSAGE_500_ID)
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
