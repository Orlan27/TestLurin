/*
 *
 * LurinUserManagmentContainer reducer
 *
 */
import { fromJS } from 'immutable';
import * as messages from './messages';
import {
  MESSAGE_500,
  MESSAGE_TYPE
 } from '../../utils/messages';
import {
  LOAD_USERS_CARRIER_ACTION_SUCCESS,
  LOAD_USERS_CARRIER_ACTION_FAILURE,
  LOAD_USERS_ACTION,
  LOAD_USERS_ACTION_SUCCESS,
  LOAD_USERS_ACTION_FAILURE,
  LOAD_USERS_LDAP_ACTION,
  LOAD_USERS_LDAP_ACTION_SUCCESS,
  LOAD_USERS_LDAP_ACTION_FAILURE,
  SHOW_USERS_FORM_ACTION,
  CREATE_USERS_ACTION,
  CREATE_USERS_ACTION_SUCCESS,
  CREATE_USERS_ACTION_FAILURE,
  UPDATE_USERS_ACTION,
  UPDATE_USERS_ACTION_SUCCESS,
  UPDATE_USERS_ACTION_FAILURE,
  DELETE_USERS_ACTION,
  DELETE_USERS_ACTION_SUCCESS,
  DELETE_USERS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  usersData: [],
  usersLDAP: [],
  hasShowForm: false,
  message: '',
  messageType: '',
  showMessage: false,
  userData: {},
  newUser: 0,
  userCarrierData: {}
});

export const lurinUserManagmentContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_USERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_USERS_ACTION_SUCCESS:
      return state
        .set('usersData', action.usersData)
        .set('loading', false);
    case LOAD_USERS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('showMessage', true)
        .set('message', MESSAGE_500)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case LOAD_USERS_LDAP_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_USERS_LDAP_ACTION_SUCCESS:
      return state
        .set('usersLDAP', action.usersLDAP)
        .set('showMessage', false)
        .set('loading', false);
    case LOAD_USERS_LDAP_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('showMessage', true)
        .set('message', MESSAGE_500)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case SHOW_USERS_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowForm);
    case CREATE_USERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case CREATE_USERS_ACTION_SUCCESS:
      return state
        .set('newUser', action.newUser)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_USER_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case CREATE_USERS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_USER_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case UPDATE_USERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_USERS_ACTION_SUCCESS:
      return state
        .set('userData', action.userData)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_USER_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_USERS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.UPDATE_USER_FAILURE_ID)
        .set('error', true);
    case DELETE_USERS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case DELETE_USERS_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.DELETE_USER_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case DELETE_USERS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.DELETE_USER_FAILURE_ID)
        .set('error', true);
    case LOAD_USERS_CARRIER_ACTION_SUCCESS:
      return state
        .set('userCarrierData', action.userCarrierData)
        .set('showMessage', false)
        .set('loading', false);
    case LOAD_USERS_CARRIER_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('showMessage', true)
        .set('message', MESSAGE_500)
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
