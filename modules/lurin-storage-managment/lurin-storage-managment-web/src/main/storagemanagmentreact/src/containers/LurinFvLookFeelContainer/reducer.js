/*
 *
 * LurinFvLookFeelContainer reducer
 *
 */
import { fromJS } from 'immutable';
import * as messages from './messages';
import {
  MESSAGE_500,
  MESSAGE_TYPE
 } from '../../utils/messages';

import {
  LOAD_LOOKFEELS_ACTION,
  LOAD_LOOKFEELS_ACTION_SUCCESS,
  LOAD_LOOKFEELS_ACTION_FAILURE,
  SHOW_LOOKFEELS_FORM_ACTION,
  CREATE_LOOKFEELS_ACTION,
  CREATE_LOOKFEELS_ACTION_SUCCESS,
  CREATE_LOOKFEELS_ACTION_FAILURE,
  UPDATE_LOOKFEELS_ACTION,
  UPDATE_LOOKFEELS_ACTION_SUCCESS,
  UPDATE_LOOKFEELS_ACTION_FAILURE,
  DELETE_LOOKFEELS_ACTION,
  DELETE_LOOKFEELS_ACTION_SUCCESS,
  DELETE_LOOKFEELS_ACTION_FAILURE,
  INIT_MESSAGE_ACTION
} from './constants';

const initialState = fromJS({
  loading: false,
  error: false,
  lookFeelsData: [],
  hasShowForm: false,
  lookFeelData: {},
  newLookFeel: 0,
  message: '',
  messageType: '',
  showMessage: false
});

export const lurinFvLookFeelContainerReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOAD_LOOKFEELS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case LOAD_LOOKFEELS_ACTION_SUCCESS:
      return state
        .set('lookFeelsData', action.lookFeelsData)
        .set('loading', false);
    case LOAD_LOOKFEELS_ACTION_FAILURE:
      return state
        .set('error', action.error)
        .set('message', MESSAGE_500)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('loading', false);
    case SHOW_LOOKFEELS_FORM_ACTION:
      return state
        .set('hasShowForm', action.hasShowForm);
    case CREATE_LOOKFEELS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case CREATE_LOOKFEELS_ACTION_SUCCESS:
      return state
        .set('newLookFeel', action.newLookFeel)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_LOOKFEEL_SUCCESSFULL)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case CREATE_LOOKFEELS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.CREATE_LOOKFEEL_FAILURE)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case UPDATE_LOOKFEELS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case UPDATE_LOOKFEELS_ACTION_SUCCESS:
      return state
        .set('lookFeelData', action.lookFeelData)
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_LOOKFEEL_SUCCESSFULL)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case UPDATE_LOOKFEELS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.UPDATE_LOOKFEEL_FAILURE)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('error', true);
    case DELETE_LOOKFEELS_ACTION:
      return state
        .set('loading', true)
        .set('showMessage', false)
        .set('error', false);
    case DELETE_LOOKFEELS_ACTION_SUCCESS:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('message', messages.default.DELETE_LOOKFEEL_SUCCESSFULL)
        .set('messageType', MESSAGE_TYPE.SUCCESS)
        .set('error', false);
    case DELETE_LOOKFEELS_ACTION_FAILURE:
      return state
        .set('loading', false)
        .set('showMessage', true)
        .set('messageType', MESSAGE_TYPE.WARNING)
        .set('message', messages.default.DELETE_LOOKFEEL_FAILURE)
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
