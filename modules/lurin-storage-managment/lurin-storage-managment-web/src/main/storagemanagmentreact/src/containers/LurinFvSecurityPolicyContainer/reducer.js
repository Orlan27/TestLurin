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

import {
  LOAD_SECURITYPOLICY_ACTION,
  LOAD_SECURITYPOLICY_ACTION_SUCCESS,
  LOAD_SECURITYPOLICY_ACTION_FAILURE,
  UPDATE_SECURITYPOLICY_ACTION,
  UPDATE_SECURITYPOLICY_ACTION_SUCCESS,
  UPDATE_SECURITYPOLICY_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  securityPolicyData: [],
  message: '',
  messageType: '',
  showMessage: false
});

export const lurinFvSecurityPolicyContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_SECURITYPOLICY_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_SECURITYPOLICY_ACTION_SUCCESS:
      return state
        .set('securityPolicyData', action.securityPolicyData)
        .set('loading', false);
    case LOAD_SECURITYPOLICY_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('message', MESSAGE_500_ID)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case UPDATE_SECURITYPOLICY_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_SECURITYPOLICY_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_GLOBALPARAMETER_SUCCESSFULL_ID)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_SECURITYPOLICY_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_GLOBALPARAMETER_FAILURE_ID)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case INIT_MESSAGE_ACTION:
        return state
            .set('showMessage', false)
            .set('messageType', '')
            .set('message', '');
    default:
      return state;
  }
};