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
  LOAD_USERDATA_BYNAME_ACTION,
  LOAD_USERDATA_BYNAME_ACTION_SUCCESS,
  LOAD_USERDATA_BYNAME_ACTION_FAILURE,
  UPDATE_USERDATA_ACTION,
  UPDATE_USERDATA_ACTION_SUCCESS,
  UPDATE_USERDATA_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  userData: null,
  message: '',
  messageType: '',
  showMessage: false
});

export const lurinChangeUserDataReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_USERDATA_BYNAME_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_USERDATA_BYNAME_ACTION_SUCCESS:
      return state
        .set('userData', action.userData)
        .set('loading', false);
    case LOAD_USERDATA_BYNAME_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('message', MESSAGE_500_ID)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case UPDATE_USERDATA_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_USERDATA_ACTION_SUCCESS: {
      let messageAction = messages.default.UPDATE_USERDATA_SUCCESSFULL_ID;
      let type = MESSAGE_TYPE.SUCCESS;
      if (action.updateData.codeError === 'EC_002') {
        messageAction = messages.default.UPDATE_USERDATA_EC_002;
        type = MESSAGE_TYPE.WARNING;
      }
      return state
      .set('loading', false)
      .set('showMessage', true)
      .set('message', messageAction)
      .set('messageType', type)
      .set('error', false);
    }

    case UPDATE_USERDATA_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_USERDATA_FAILURE_ID)
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
